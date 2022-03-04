package lotr.common.world.map;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import lotr.common.LOTRLog;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.util.LOTRUtil;
import lotr.common.util.LazyReference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class MapWaypoint implements Waypoint {
   private final MapSettings mapSettings;
   private final ResourceLocation resourceName;
   private final int assignedId;
   private final String name;
   private final boolean translateName;
   private final String lore;
   private final boolean translateLore;
   private final double mapX;
   private final double mapZ;
   private final int worldX;
   private final int worldZ;
   private final WaypointRegion region;
   private final LazyReference travelFaction;

   public MapWaypoint(MapSettings map, ResourceLocation res, int id, String name, boolean translateName, String lore, boolean translateLore, double x, double z, WaypointRegion region, ResourceLocation facName) {
      this.mapSettings = map;
      this.resourceName = res;
      this.assignedId = id;
      this.name = name;
      this.translateName = translateName;
      this.lore = lore;
      this.translateLore = translateLore;
      this.mapX = x;
      this.mapZ = z;
      this.worldX = map.mapToWorldX(this.mapX);
      this.worldZ = map.mapToWorldZ(this.mapZ);
      this.region = region;
      this.travelFaction = facName == null ? null : LazyReference.of(facName, (ref) -> {
         return FactionSettingsManager.sidedInstance(map.getSide()).getCurrentLoadedFactions().getFactionByName(ref);
      }, (unresolvedRef) -> {
         LOTRLog.warn("Cannot resolve faction name %s in map waypoint %s - ensure the JSON file is correct", unresolvedRef, this.resourceName);
      });
   }

   public static MapWaypoint read(MapSettings map, ResourceLocation resourceName, JsonObject json, int assignedId) {
      if (json.size() == 0) {
         LOTRLog.info("Map waypoint %s has an empty file - not loading it in this world", resourceName);
         return null;
      } else {
         JsonObject nameObj = json.get("name").getAsJsonObject();
         String name = nameObj.get("text").getAsString();
         boolean translateName = nameObj.get("translate").getAsBoolean();
         JsonObject loreObj = json.get("lore").getAsJsonObject();
         String lore = loreObj.get("text").getAsString();
         boolean translateLore = loreObj.get("translate").getAsBoolean();
         double mapX = json.get("x").getAsDouble() + 0.5D;
         double mapZ = json.get("z").getAsDouble() + 0.5D;
         String regionName = json.get("travel_region").getAsString();
         WaypointRegion region = map.getWaypointRegionByName(new ResourceLocation(regionName));
         if (region == null) {
            LOTRLog.warn("Map waypoint %s declares unknown region name %s - no such waypoint region exists in this map", resourceName, regionName);
            return null;
         } else {
            ResourceLocation factionReference = null;
            if (json.has("faction")) {
               String facName = json.get("faction").getAsString();
               if (!facName.isEmpty()) {
                  factionReference = new ResourceLocation(facName);
               }
            }

            return new MapWaypoint(map, resourceName, assignedId, name, translateName, lore, translateLore, mapX, mapZ, region, factionReference);
         }
      }
   }

   public static MapWaypoint read(MapSettings map, PacketBuffer buf) {
      ResourceLocation resourceName = buf.func_192575_l();
      int assignedID = buf.func_150792_a();
      String name = buf.func_218666_n();
      boolean translateName = buf.readBoolean();
      String lore = buf.func_218666_n();
      boolean translateLore = buf.readBoolean();
      double mapX = buf.readDouble();
      double mapZ = buf.readDouble();
      int regionId = buf.func_150792_a();
      WaypointRegion region = map.getWaypointRegionByID(regionId);
      if (region == null) {
         LOTRLog.warn("Received waypoint %s from server with a nonexistent waypoint region ID (%d)", resourceName, regionId);
      }

      boolean hasFaction = buf.readBoolean();
      ResourceLocation factionReference = hasFaction ? buf.func_192575_l() : null;
      return new MapWaypoint(map, resourceName, assignedID, name, translateName, lore, translateLore, mapX, mapZ, region, factionReference);
   }

   public void write(PacketBuffer buf) {
      buf.func_192572_a(this.resourceName);
      buf.func_150787_b(this.assignedId);
      buf.func_180714_a(this.name);
      buf.writeBoolean(this.translateName);
      buf.func_180714_a(this.lore);
      buf.writeBoolean(this.translateLore);
      buf.writeDouble(this.mapX);
      buf.writeDouble(this.mapZ);
      buf.func_150787_b(this.region.getAssignedId());
      boolean hasFaction = this.travelFaction != null;
      buf.writeBoolean(hasFaction);
      if (hasFaction) {
         buf.func_192572_a(this.travelFaction.getReferenceName());
      }

   }

   public ResourceLocation getName() {
      return this.resourceName;
   }

   public String getRawName() {
      return this.resourceName.toString();
   }

   public int getAssignedId() {
      return this.assignedId;
   }

   public ITextComponent getDisplayName() {
      return (ITextComponent)(this.translateName ? new TranslationTextComponent(this.name) : new StringTextComponent(this.name));
   }

   public ITextComponent getDisplayLore() {
      return (ITextComponent)(this.translateLore ? new TranslationTextComponent(this.lore) : new StringTextComponent(this.lore));
   }

   @Nullable
   public ITextComponent getDisplayOwnership() {
      return null;
   }

   public double getMapX() {
      return this.mapX;
   }

   public double getMapZ() {
      return this.mapZ;
   }

   public int getWorldX() {
      return this.worldX;
   }

   public int getWorldZ() {
      return this.worldZ;
   }

   @Nullable
   public BlockPos getTravelPosition(ServerWorld world, PlayerEntity player) {
      int worldY = LOTRUtil.forceLoadChunkAndGetTopBlock(world, this.worldX, this.worldZ);
      return new BlockPos(this.worldX, worldY, this.worldZ);
   }

   public WaypointRegion getTravelRegion() {
      return this.region;
   }

   public boolean hasPlayerUnlocked(PlayerEntity player) {
      LOTRLevelData levelData = LOTRLevelData.sidedInstance((IWorldReader)player.field_70170_p);
      LOTRPlayerData pd = levelData.getData(player);
      return pd.getFastTravelData().isWaypointRegionUnlocked(this.region) && this.isCompatibleAlignment(player);
   }

   public ITextComponent getNotUnlockedMessage(PlayerEntity player) {
      return !this.isCompatibleAlignment(player) ? new TranslationTextComponent("gui.lotr.map.locked.enemy") : new TranslationTextComponent("gui.lotr.map.locked.region");
   }

   private boolean isCompatibleAlignment(PlayerEntity player) {
      Faction fac = this.getTravelFaction();
      if (fac != null) {
         LOTRPlayerData pd = LOTRLevelData.sidedInstance((IWorldReader)player.field_70170_p).getData(player);
         return pd.getAlignmentData().getAlignment(fac) >= 0.0F;
      } else {
         return true;
      }
   }

   private Faction getTravelFaction() {
      return this.travelFaction != null ? (Faction)this.travelFaction.resolveReference() : null;
   }

   public boolean isCustom() {
      return false;
   }

   public boolean isSharedCustom() {
      return false;
   }

   public boolean isSharedHidden() {
      return false;
   }

   public Waypoint.WaypointDisplayState getDisplayState(@Nullable PlayerEntity player) {
      if (player == null) {
         return Waypoint.WaypointDisplayState.STANDARD;
      } else if (this.hasPlayerUnlocked(player)) {
         return Waypoint.WaypointDisplayState.STANDARD;
      } else {
         return !this.isCompatibleAlignment(player) ? Waypoint.WaypointDisplayState.STANDARD_LOCKED_TO_ENEMIES : Waypoint.WaypointDisplayState.STANDARD_LOCKED;
      }
   }

   public WaypointNetworkType getNetworkType() {
      return WaypointNetworkType.MAP;
   }

   public static void writeIdentification(PacketBuffer buf, MapWaypoint wp) {
      buf.func_150787_b(wp.assignedId);
   }

   public static MapWaypoint readFromIdentification(PacketBuffer buf, LOTRPlayerData pd) {
      MapSettings map = MapSettingsManager.sidedInstance(pd.getLogicalSide()).getCurrentLoadedMap();
      int wpId = buf.func_150792_a();
      MapWaypoint wp = map.getWaypointByID(wpId);
      if (wp == null) {
         LOTRLog.warn("Received nonexistent map waypoint ID %d from %s", wpId, pd.getLogicalSide());
      }

      return wp;
   }
}
