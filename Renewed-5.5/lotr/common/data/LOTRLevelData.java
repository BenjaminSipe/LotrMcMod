package lotr.common.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import lotr.common.LOTRLog;
import lotr.common.config.LOTRConfig;
import lotr.common.init.LOTRWorldTypes;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketAlignment;
import lotr.common.network.SPacketLoginLOTR;
import lotr.common.network.SPacketMapPlayerLocations;
import lotr.common.network.SPacketRingPortalPos;
import lotr.common.network.SPacketWorldWaypointCooldown;
import lotr.common.time.LOTRDate;
import lotr.common.util.UsernameHelper;
import lotr.common.world.map.MapPlayerLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;

public class LOTRLevelData {
   private static final LOTRLevelData SERVER_INSTANCE;
   private static final LOTRLevelData CLIENT_INSTANCE;
   private final LogicalSide side;
   private boolean madeOverworldPortal = false;
   private int overworldPortalX;
   private int overworldPortalY;
   private int overworldPortalZ;
   private boolean madeMiddleEarthPortal = false;
   private int middleEarthPortalX;
   private int middleEarthPortalY;
   private int middleEarthPortalZ;
   private Map playerDataMap = new HashMap();
   private static final int WAYPOINT_COOLDOWN_DEFAULT = 1800;
   private static final int WAYPOINT_COOLDOWN_MIN_DEFAULT = 180;
   private int waypointCooldownMax = 1800;
   private int waypointCooldownMin = 180;
   private boolean needsLoad = true;
   private boolean needsSave = false;

   private LOTRLevelData(LogicalSide side) {
      this.side = side;
   }

   public static LOTRLevelData serverInstance() {
      return SERVER_INSTANCE;
   }

   public static LOTRLevelData clientInstance() {
      return CLIENT_INSTANCE;
   }

   public static LOTRLevelData sidedInstance(IWorldReader world) {
      return !world.func_201670_d() ? SERVER_INSTANCE : CLIENT_INSTANCE;
   }

   public static LOTRLevelData sidedInstance(Entity e) {
      return sidedInstance((IWorldReader)e.field_70170_p);
   }

   public LogicalSide getLogicalSide() {
      return this.side;
   }

   public boolean needsLoad() {
      return this.needsLoad;
   }

   public void resetNeedsLoad() {
      this.needsLoad = true;
   }

   public void markDirty() {
      this.needsSave = true;
   }

   public boolean anyDataNeedsSave() {
      if (this.needsSave) {
         return true;
      } else {
         Iterator var1 = this.playerDataMap.values().iterator();

         LOTRPlayerData pd;
         do {
            if (!var1.hasNext()) {
               return false;
            }

            pd = (LOTRPlayerData)var1.next();
         } while(!pd.needsSave());

         return true;
      }
   }

   private static File getLOTRDat(ServerWorld world) {
      String filename = "lotr".toUpperCase();
      return new File(SaveUtil.getOrCreateLOTRDir(world), filename + ".dat");
   }

   private static File getLOTRPlayerDat(ServerWorld world, UUID player) {
      File playerDir = new File(SaveUtil.getOrCreateLOTRDir(world), "players");
      if (!playerDir.exists()) {
         playerDir.mkdirs();
      }

      return new File(playerDir, player.toString() + ".dat");
   }

   public void save(ServerWorld world) {
      try {
         if (this.needsSave) {
            File LOTR_dat = getLOTRDat(world);
            if (!LOTR_dat.exists()) {
               SaveUtil.saveNBTToFile(LOTR_dat, new CompoundNBT());
            }

            CompoundNBT levelData = new CompoundNBT();
            levelData.func_74757_a("MadePortal", this.madeOverworldPortal);
            levelData.func_74768_a("OverworldX", this.overworldPortalX);
            levelData.func_74768_a("OverworldY", this.overworldPortalY);
            levelData.func_74768_a("OverworldZ", this.overworldPortalZ);
            levelData.func_74757_a("MadeMiddlePortal", this.madeMiddleEarthPortal);
            levelData.func_74768_a("MiddleEarthX", this.middleEarthPortalX);
            levelData.func_74768_a("MiddleEarthY", this.middleEarthPortalY);
            levelData.func_74768_a("MiddleEarthZ", this.middleEarthPortalZ);
            levelData.func_74768_a("WpCdMax", this.waypointCooldownMax);
            levelData.func_74768_a("WpCdMin", this.waypointCooldownMin);
            LOTRDate.saveDates(levelData);
            SaveUtil.saveNBTToFile(LOTR_dat, levelData);
            this.needsSave = false;
         }

         int savedPd = 0;
         Iterator var9 = this.playerDataMap.entrySet().iterator();

         while(var9.hasNext()) {
            Entry e = (Entry)var9.next();
            UUID player = (UUID)e.getKey();
            LOTRPlayerData pd = (LOTRPlayerData)e.getValue();
            if (pd.needsSave()) {
               this.saveData(world, player);
               ++savedPd;
            }
         }
      } catch (Exception var7) {
         LOTRLog.error("Error saving mod data");
         var7.printStackTrace();
      }

   }

   public void load(ServerWorld world) {
      try {
         CompoundNBT levelData = SaveUtil.loadNBTFromFile(getLOTRDat(world));
         this.madeOverworldPortal = levelData.func_74767_n("MadePortal");
         this.overworldPortalX = levelData.func_74762_e("OverworldX");
         this.overworldPortalY = levelData.func_74762_e("OverworldY");
         this.overworldPortalZ = levelData.func_74762_e("OverworldZ");
         this.madeMiddleEarthPortal = levelData.func_74767_n("MadeMiddlePortal");
         this.middleEarthPortalX = levelData.func_74762_e("MiddleEarthX");
         this.middleEarthPortalY = levelData.func_74762_e("MiddleEarthY");
         this.middleEarthPortalZ = levelData.func_74762_e("MiddleEarthZ");
         if (levelData.func_74764_b("WpCdMax") && levelData.func_74764_b("WpCdMin")) {
            int max = levelData.func_74762_e("WpCdMax");
            int min = levelData.func_74762_e("WpCdMin");
            max = Math.max(0, max);
            min = Math.max(0, min);
            if (min > max) {
               min = max;
            }

            this.waypointCooldownMax = max;
            this.waypointCooldownMin = min;
         } else {
            this.waypointCooldownMax = 1800;
            this.waypointCooldownMin = 180;
         }

         this.destroyAllPlayerData();
         LOTRDate.loadDates(levelData);
         this.needsLoad = false;
         this.needsSave = true;
         this.save(world);
      } catch (Exception var5) {
         LOTRLog.error("Error loading mod data");
         var5.printStackTrace();
      }

   }

   public void setMadePortal(boolean flag) {
      this.madeOverworldPortal = flag;
      this.markDirty();
   }

   public boolean madePortal() {
      return this.madeOverworldPortal;
   }

   public void setMadeMiddleEarthPortal(boolean flag) {
      this.madeMiddleEarthPortal = flag;
      this.markDirty();
   }

   public boolean madeMiddleEarthPortal() {
      return this.madeMiddleEarthPortal;
   }

   public void markOverworldPortalLocation(BlockPos pos) {
      this.overworldPortalX = pos.func_177958_n();
      this.overworldPortalY = pos.func_177956_o();
      this.overworldPortalZ = pos.func_177952_p();
      this.markDirty();
   }

   public BlockPos getOverworldPortalLocation() {
      return new BlockPos(this.overworldPortalX, this.overworldPortalY, this.overworldPortalZ);
   }

   public void markMiddleEarthPortalLocation(World world, BlockPos pos) {
      this.middleEarthPortalX = pos.func_177958_n();
      this.middleEarthPortalY = pos.func_177956_o();
      this.middleEarthPortalZ = pos.func_177952_p();
      this.markDirty();
      if (!world.field_72995_K) {
         SPacketRingPortalPos packet = new SPacketRingPortalPos(pos);
         LOTRPacketHandler.sendToAll(packet);
      }

   }

   public BlockPos getMiddleEarthPortalLocation() {
      return new BlockPos(this.middleEarthPortalX, this.middleEarthPortalY, this.middleEarthPortalZ);
   }

   public int getWaypointCooldownMax() {
      return this.waypointCooldownMax;
   }

   public int getWaypointCooldownMin() {
      return this.waypointCooldownMin;
   }

   public void setWaypointCooldown(World world, int max, int min) {
      int prevMax = this.waypointCooldownMax;
      int prevMin = this.waypointCooldownMin;
      max = Math.max(0, max);
      min = Math.max(0, min);
      if (min > max) {
         min = max;
      }

      if (max != prevMax || min != prevMin) {
         this.waypointCooldownMax = max;
         this.waypointCooldownMin = min;
         this.markDirty();
         if (!world.field_72995_K) {
            SPacketWorldWaypointCooldown packet = new SPacketWorldWaypointCooldown(this.waypointCooldownMax, this.waypointCooldownMin);
            LOTRPacketHandler.sendToAll(packet);
         }
      }

   }

   public void sendLoginPacket(ServerPlayerEntity player) {
      SPacketLoginLOTR packet = new SPacketLoginLOTR();
      packet.setMiddleEarthPortalPos(this.middleEarthPortalX, this.middleEarthPortalY, this.middleEarthPortalZ);
      packet.setWaypointCooldownMaxMin(this.waypointCooldownMax, this.waypointCooldownMin);
      packet.setAreasOfInfluence((Boolean)LOTRConfig.COMMON.areasOfInfluence.get());
      packet.setSmallerBees((Boolean)LOTRConfig.COMMON.smallerBees.get());
      packet.setHasMapFeatures(LOTRWorldTypes.hasMapFeatures(player.func_71121_q()));
      packet.setForceFogOfWar((Integer)LOTRConfig.SERVER.forceFogOfWar.get());
      LOTRPacketHandler.sendTo(packet, player);
   }

   public void playerDataHandleLogin(ServerPlayerEntity player) {
      try {
         LOTRPlayerData pd = this.getData(player);
         pd.handleLoginAndSendLoginData(player);
      } catch (Exception var3) {
         LOTRLog.error("Failed to send player data to player %s", UsernameHelper.getRawUsername(player));
         var3.printStackTrace();
      }

   }

   public LOTRPlayerData getData(PlayerEntity player) {
      return this.getData(player.func_130014_f_(), player.func_110124_au());
   }

   public static LOTRPlayerData getSidedData(PlayerEntity player) {
      return sidedInstance((Entity)player).getData(player);
   }

   public LOTRPlayerData getData(World world, UUID player) {
      LOTRPlayerData pd = (LOTRPlayerData)this.playerDataMap.get(player);
      if (pd == null) {
         if (world instanceof ServerWorld) {
            pd = this.loadData((ServerWorld)world, player);
         }

         if (pd == null) {
            pd = new LOTRPlayerData(this, player);
         }

         this.playerDataMap.put(player, pd);
      }

      return pd;
   }

   private LOTRPlayerData loadData(ServerWorld world, UUID player) {
      try {
         CompoundNBT nbt = SaveUtil.loadNBTFromFile(getLOTRPlayerDat(world, player));
         LOTRPlayerData pd = new LOTRPlayerData(this, player);
         pd.load(nbt);
         return pd;
      } catch (Exception var5) {
         LOTRLog.error("Error loading player data for %s", player);
         var5.printStackTrace();
         return null;
      }
   }

   public void saveData(ServerWorld world, UUID player) {
      try {
         CompoundNBT nbt = new CompoundNBT();
         LOTRPlayerData pd = (LOTRPlayerData)this.playerDataMap.get(player);
         pd.save(nbt);
         SaveUtil.saveNBTToFile(getLOTRPlayerDat(world, player), nbt);
      } catch (Exception var5) {
         LOTRLog.error("Error saving player data for %s", player);
         var5.printStackTrace();
      }

   }

   private boolean saveAndClearData(ServerWorld world, UUID player) {
      LOTRPlayerData pd = (LOTRPlayerData)this.playerDataMap.get(player);
      if (pd != null) {
         boolean saved = false;
         if (pd.needsSave()) {
            this.saveData(world, player);
            saved = true;
         }

         this.playerDataMap.remove(player);
         return saved;
      } else {
         LOTRLog.warn("Attempted to clear player data for %s; no data found", player);
         return false;
      }
   }

   public void saveAndClearUnusedPlayerData(ServerWorld world) {
      List playersToClear = new ArrayList();
      PlayerList serverPlayerList = world.func_73046_m().func_184103_al();
      Iterator var4 = this.playerDataMap.keySet().iterator();

      while(var4.hasNext()) {
         UUID player = (UUID)var4.next();
         if (serverPlayerList.func_177451_a(player) == null) {
            playersToClear.add(player);
         }
      }

      int numCleared = playersToClear.size();
      int sizeBefore = this.playerDataMap.size();
      int numSaved = 0;
      Iterator var7 = playersToClear.iterator();

      while(var7.hasNext()) {
         UUID player = (UUID)var7.next();
         boolean saved = this.saveAndClearData(world, player);
         if (saved) {
            ++numSaved;
         }
      }

      int sizeNow = this.playerDataMap.size();
   }

   public void destroyAllPlayerData() {
      this.playerDataMap.clear();
   }

   public void sendAllOtherPlayerAlignmentsToPlayer(ServerPlayerEntity player) {
      MinecraftServer server = player.field_71133_b;
      Iterator var3 = server.func_184103_al().func_181057_v().iterator();

      while(var3.hasNext()) {
         ServerPlayerEntity otherPlayer = (ServerPlayerEntity)var3.next();
         if (!otherPlayer.func_110124_au().equals(player.func_110124_au())) {
            AlignmentDataModule otherAlignData = this.getData(otherPlayer).getAlignmentData();
            LOTRPacketHandler.sendTo(new SPacketAlignment(otherAlignData.getAlignmentsView(), otherPlayer), player);
         }
      }

   }

   public void sendPlayerAlignmentToAllOtherPlayers(ServerPlayerEntity player) {
      AlignmentDataModule alignData = this.getData(player).getAlignmentData();
      LOTRPacketHandler.sendToAllExcept(new SPacketAlignment(alignData.getAlignmentsView(), player), player);
   }

   public void sendPlayerLocationsToPlayer(PlayerEntity targetPlayer, ServerWorld world) {
      List playerLocations = new ArrayList();
      MinecraftServer server = world.func_73046_m();
      boolean isOp = server.func_184103_al().func_152596_g(targetPlayer.func_146103_bH());
      boolean creative = targetPlayer.field_71075_bZ.field_75098_d;
      this.getData(targetPlayer);
      Iterator var8 = world.func_217369_A().iterator();

      while(true) {
         PlayerEntity otherPlayer;
         do {
            if (!var8.hasNext()) {
               SPacketMapPlayerLocations packet = new SPacketMapPlayerLocations(playerLocations);
               LOTRPacketHandler.sendTo(packet, (ServerPlayerEntity)targetPlayer);
               return;
            }

            otherPlayer = (PlayerEntity)var8.next();
         } while(otherPlayer == targetPlayer);

         boolean show = !this.getData(otherPlayer).getHideMapLocation();
         if (!isOp && this.getData(otherPlayer).getAdminHideMap()) {
            show = false;
         } else if ((Integer)LOTRConfig.SERVER.forceMapLocations.get() == 1) {
            show = false;
         } else if ((Integer)LOTRConfig.SERVER.forceMapLocations.get() == 2) {
            show = true;
         } else if (!show && isOp && creative) {
            show = true;
         }

         if (show) {
            playerLocations.add(MapPlayerLocation.ofPlayer(otherPlayer));
         }
      }
   }

   static {
      SERVER_INSTANCE = new LOTRLevelData(LogicalSide.SERVER);
      CLIENT_INSTANCE = new LOTRLevelData(LogicalSide.CLIENT);
   }
}
