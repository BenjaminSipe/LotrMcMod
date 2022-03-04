package lotr.common.fac;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.config.ClientsideCurrentServerConfigSettings;
import lotr.common.config.LOTRConfig;
import lotr.common.entity.npc.NPCPredicates;
import lotr.common.init.LOTRWorldTypes;
import lotr.common.world.map.MapSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class AreasOfInfluence {
   private final MapSettings mapSettings;
   private final Faction theFaction;
   private final boolean isolationist;
   private final List areas;
   private AreaBorders calculatedAreaBorders = null;
   private static final double NPC_LOCAL_INFLUENCE_RANGE = 24.0D;
   public static final int DEFAULT_REDUCED_INFLUENCE_RANGE = 50;

   public AreasOfInfluence(MapSettings map, Faction theFaction, boolean isolationist, List areas) {
      this.mapSettings = map;
      this.theFaction = theFaction;
      this.isolationist = isolationist;
      this.areas = areas;
   }

   public static final AreasOfInfluence makeEmptyAreas(MapSettings map, Faction fac) {
      return new AreasOfInfluence(map, fac, false, ImmutableList.of());
   }

   public static AreasOfInfluence read(Faction theFaction, JsonObject json, MapSettings mapSettings) {
      boolean isolationist = json.get("isolationist").getAsBoolean();
      JsonArray areasArray = json.get("areas").getAsJsonArray();
      List areas = new ArrayList();
      Iterator var6 = areasArray.iterator();

      while(var6.hasNext()) {
         JsonElement areaElement = (JsonElement)var6.next();
         AreaOfInfluence area = AreaOfInfluence.read(mapSettings, theFaction.getName(), areaElement.getAsJsonObject());
         if (area != null) {
            areas.add(area);
         }
      }

      return new AreasOfInfluence(mapSettings, theFaction, isolationist, areas);
   }

   public static AreasOfInfluence read(Faction theFaction, PacketBuffer buf, MapSettings mapSettings) {
      boolean isolationist = buf.readBoolean();
      List areas = new ArrayList();
      int numAreasOfInfluence = buf.func_150792_a();

      for(int i = 0; i < numAreasOfInfluence; ++i) {
         AreaOfInfluence area = AreaOfInfluence.read(mapSettings, theFaction.getName(), buf);
         if (area != null) {
            areas.add(area);
         }
      }

      return new AreasOfInfluence(mapSettings, theFaction, isolationist, areas);
   }

   public void write(PacketBuffer buf) {
      buf.writeBoolean(this.isolationist);
      buf.func_150787_b(this.areas.size());
      this.areas.forEach((area) -> {
         area.write(buf);
      });
   }

   public boolean isIsolationist() {
      return this.isolationist;
   }

   public List getAreas() {
      return this.areas;
   }

   public static boolean areAreasOfInfluenceEnabled(World world) {
      if (world instanceof ServerWorld) {
         ServerWorld sWorld = (ServerWorld)world;
         return (Boolean)LOTRConfig.COMMON.areasOfInfluence.get() && LOTRWorldTypes.hasMapFeatures(sWorld);
      } else {
         return ClientsideCurrentServerConfigSettings.INSTANCE.areasOfInfluence && LOTRWorldTypes.hasMapFeaturesClientside();
      }
   }

   public boolean isInArea(PlayerEntity player) {
      return this.isInArea(player.field_70170_p, player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_());
   }

   public boolean isInArea(World world, double x, double y, double z) {
      if (this.isInDefinedArea(world, x, y, z)) {
         return true;
      } else {
         if (this.theFaction.isPlayableAlignmentFaction()) {
            AxisAlignedBB aabb = AxisAlignedBB.func_241549_a_(new Vector3d(x, y, z)).func_186662_g(24.0D);
            List nearbyNPCs = world.func_225316_b(LivingEntity.class, aabb, NPCPredicates.selectForLocalAreaOfInfluence(this.theFaction));
            if (!nearbyNPCs.isEmpty()) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isInDefinedArea(PlayerEntity player) {
      return this.isInDefinedArea(player, 0);
   }

   public boolean isInDefinedArea(PlayerEntity player, int extraMapRange) {
      return this.isInDefinedArea(player.field_70170_p, player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_(), extraMapRange);
   }

   public boolean isInDefinedArea(World world, double x, double y, double z) {
      return this.isInDefinedArea(world, x, y, z, 0);
   }

   public boolean isInDefinedArea(World world, double x, double y, double z, int extraMapRange) {
      if (this.isFactionDimension(world)) {
         return !areAreasOfInfluenceEnabled(world) ? true : this.areas.stream().anyMatch((area) -> {
            return area.isInArea(x, y, z, extraMapRange);
         });
      } else {
         return false;
      }
   }

   private boolean isFactionDimension(World world) {
      return world.func_234923_W_().func_240901_a_().equals(this.theFaction.getDimension().func_240901_a_());
   }

   public boolean isInAreaToRecordBountyKill(PlayerEntity player) {
      return this.isInDefinedArea(player, Math.max(this.getReducedInfluenceRange(), 50));
   }

   public int getReducedInfluenceRange() {
      return this.isolationist ? 0 : 50;
   }

   public float getAlignmentMultiplier(PlayerEntity player) {
      if (this.isInArea(player)) {
         return 1.0F;
      } else {
         if (this.isFactionDimension(player.field_70170_p)) {
            int reducedRange = this.getReducedInfluenceRange();
            double dist = this.distanceToNearestAreaInRange(player.field_70170_p, player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_(), reducedRange);
            if (dist >= 0.0D) {
               double mapDist = (double)this.mapSettings.worldToMapDistance(dist);
               float frac = (float)mapDist / (float)reducedRange;
               float mplier = 1.0F - frac;
               mplier = MathHelper.func_76131_a(mplier, 0.0F, 1.0F);
               return mplier;
            }
         }

         return 0.0F;
      }
   }

   public double distanceToNearestAreaInRange(World world, double x, double y, double z, int mapRange) {
      double closestDist = -1.0D;
      if (this.isFactionDimension(world)) {
         int coordRange = this.mapSettings.mapToWorldDistance((double)mapRange);
         Iterator var12 = this.areas.iterator();

         while(true) {
            double dToEdge;
            do {
               do {
                  if (!var12.hasNext()) {
                     return closestDist;
                  }

                  AreaOfInfluence area = (AreaOfInfluence)var12.next();
                  double dx = x - area.getWorldX();
                  double dz = z - area.getWorldZ();
                  double dSq = dx * dx + dz * dz;
                  dToEdge = Math.sqrt(dSq) - (double)area.getWorldRadius();
               } while(dToEdge > (double)coordRange);
            } while(closestDist >= 0.0D && dToEdge >= closestDist);

            closestDist = dToEdge;
         }
      } else {
         return closestDist;
      }
   }

   public AreaBorders calculateAreaOfInfluenceBordersIncludingReduced() {
      if (this.calculatedAreaBorders == null) {
         double xMin = 0.0D;
         double xMax = 0.0D;
         double zMin = 0.0D;
         double zMax = 0.0D;
         boolean first = true;
         Iterator var10 = this.areas.iterator();

         while(var10.hasNext()) {
            AreaOfInfluence area = (AreaOfInfluence)var10.next();
            double cxMin = area.getWorldX() - (double)area.getWorldRadius();
            double cxMax = area.getWorldX() + (double)area.getWorldRadius();
            double czMin = area.getWorldZ() - (double)area.getWorldRadius();
            double czMax = area.getWorldZ() + (double)area.getWorldRadius();
            if (first) {
               xMin = cxMin;
               xMax = cxMax;
               zMin = czMin;
               zMax = czMax;
               first = false;
            } else {
               xMin = Math.min(xMin, cxMin);
               xMax = Math.max(xMax, cxMax);
               zMin = Math.min(zMin, czMin);
               zMax = Math.max(zMax, czMax);
            }
         }

         int reducedWorldRange = this.mapSettings.mapToWorldDistance((double)this.getReducedInfluenceRange());
         xMin -= (double)reducedWorldRange;
         xMax += (double)reducedWorldRange;
         zMin -= (double)reducedWorldRange;
         zMax += (double)reducedWorldRange;
         this.calculatedAreaBorders = new AreaBorders(xMin, xMax, zMin, zMax);
      }

      return this.calculatedAreaBorders;
   }

   public boolean sharesAreaWith(Faction other) {
      return this.sharesAreaWith(other, 0);
   }

   public boolean sharesAreaWith(Faction other, int extraMapRadius) {
      if (this.theFaction.isSameDimension(other)) {
         List otherAreas = other.getAreasOfInfluence().getAreas();
         return this.areas.stream().anyMatch((area) -> {
            return otherAreas.stream().anyMatch((otherArea) -> {
               return area.intersectsWith(otherArea, extraMapRadius);
            });
         });
      } else {
         return false;
      }
   }
}
