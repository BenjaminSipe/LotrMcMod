package lotr.common.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lotr.common.LOTRGameRules;
import lotr.common.LOTRLog;
import lotr.common.init.LOTRWorldTypes;
import lotr.common.network.CPacketToggleShowWaypoints;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketAdoptCustomWaypoint;
import lotr.common.network.SPacketCreateCustomWaypoint;
import lotr.common.network.SPacketCustomWaypointAdoptedCount;
import lotr.common.network.SPacketDeleteAdoptedCustomWaypoint;
import lotr.common.network.SPacketDeleteCustomWaypoint;
import lotr.common.network.SPacketFastTravel;
import lotr.common.network.SPacketShowWaypoints;
import lotr.common.network.SPacketTimeSinceFT;
import lotr.common.network.SPacketUpdateAdoptedCustomWaypoint;
import lotr.common.network.SPacketUpdateCustomWaypoint;
import lotr.common.network.SPacketWaypointRegion;
import lotr.common.network.SPacketWaypointUseCount;
import lotr.common.stat.LOTRStats;
import lotr.common.util.LOTRUtil;
import lotr.common.util.LookupList;
import lotr.common.util.UsernameHelper;
import lotr.common.world.map.AbstractCustomWaypoint;
import lotr.common.world.map.AdoptedCustomWaypoint;
import lotr.common.world.map.AdoptedCustomWaypointKey;
import lotr.common.world.map.CustomWaypoint;
import lotr.common.world.map.MapWaypoint;
import lotr.common.world.map.Waypoint;
import lotr.common.world.map.WaypointRegion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.tuple.Pair;

public class FastTravelDataModule extends PlayerDataModule {
   private Set unlockedWaypointRegions = new HashSet();
   private Map waypointUseCounts = new HashMap();
   private static final int START_CWP_ID = 20000;
   private int nextCustomWaypointId = 20000;
   private LookupList customWaypoints = new LookupList(AbstractCustomWaypoint::getCustomId);
   private Map customWaypointUseCounts = new HashMap();
   private LookupList adoptedCustomWaypoints = new LookupList(AdoptedCustomWaypoint::getAdoptedKey);
   private Map adoptedCustomWaypointUseCounts = new HashMap();
   private boolean showMapWaypoints = true;
   private boolean showCustomWaypoints = true;
   private int ftSinceTick = this.getLevelData().getWaypointCooldownMax() * 20;
   private long lastOnlineTime = -1L;
   private Waypoint targetFTWaypoint;
   private int ticksUntilFT;
   private double lastPosX;
   private double lastPosY;
   private double lastPosZ;
   private static final int FT_COUNTDOWN_TICKS = 200;
   private UUID uuidToMount;
   private int uuidToMountTime;

   protected FastTravelDataModule(LOTRPlayerData pd) {
      super(pd);
   }

   public void save(CompoundNBT playerNBT) {
      if (!this.unlockedWaypointRegions.isEmpty()) {
         playerNBT.func_218657_a("UnlockedFTRegions", DataUtil.saveCollectionAsPrimitiveListNBT(this.unlockedWaypointRegions, (region) -> {
            return StringNBT.func_229705_a_(region.getName().toString());
         }));
      }

      if (!this.waypointUseCounts.isEmpty()) {
         playerNBT.func_218657_a("WPUses", DataUtil.saveMapAsListNBT(this.waypointUseCounts, (nbt, wpName, count) -> {
            nbt.func_74778_a("WPName", wpName.toString());
            nbt.func_74768_a("Count", count);
         }));
      }

      playerNBT.func_74768_a("NextCWPID", this.nextCustomWaypointId);
      if (!this.customWaypoints.isEmpty()) {
         playerNBT.func_218657_a("CustomWaypoints", DataUtil.saveCollectionAsCompoundListNBT(this.customWaypoints, (nbt, cwp) -> {
            cwp.save(nbt);
         }));
      }

      if (!this.customWaypointUseCounts.isEmpty()) {
         playerNBT.func_218657_a("CWPUses", DataUtil.saveMapAsListNBT(this.customWaypointUseCounts, (nbt, cwpId, count) -> {
            nbt.func_74768_a("CustomID", cwpId);
            nbt.func_74768_a("Count", count);
         }));
      }

      if (!this.adoptedCustomWaypoints.isEmpty()) {
         playerNBT.func_218657_a("AdoptedCustomWaypoints", DataUtil.saveCollectionAsCompoundListNBT(this.adoptedCustomWaypoints, (nbt, cwp) -> {
            cwp.save(nbt);
         }));
      }

      if (!this.adoptedCustomWaypointUseCounts.isEmpty()) {
         playerNBT.func_218657_a("AdoptedCWPUses", DataUtil.saveMapAsListNBT(this.adoptedCustomWaypointUseCounts, (nbt, cwpKey, count) -> {
            cwpKey.save(nbt);
            nbt.func_74768_a("Count", count);
         }));
      }

      playerNBT.func_74757_a("ShowWP", this.showMapWaypoints);
      playerNBT.func_74757_a("ShowCWP", this.showCustomWaypoints);
      playerNBT.func_74768_a("FTSince", this.ftSinceTick);
      playerNBT.func_74772_a("LastOnlineTime", this.lastOnlineTime);
      if (this.uuidToMount != null) {
         playerNBT.func_186854_a("MountUUID", this.uuidToMount);
      }

      playerNBT.func_74768_a("MountUUIDTime", this.uuidToMountTime);
   }

   public void load(CompoundNBT playerNBT) {
      DataUtil.loadCollectionFromPrimitiveListNBT(this.unlockedWaypointRegions, playerNBT.func_150295_c("UnlockedFTRegions", 8), ListNBT::func_150307_f, (regionName) -> {
         WaypointRegion region = this.currentMapSettings().getWaypointRegionByName(new ResourceLocation(regionName));
         if (region != null) {
            return region;
         } else {
            this.playerData.logPlayerError("Loaded nonexistent waypoint region ID %s", regionName);
            return null;
         }
      });
      DataUtil.loadMapFromListNBT(this.waypointUseCounts, playerNBT.func_150295_c("WPUses", 10), (nbt) -> {
         ResourceLocation wpName = new ResourceLocation(nbt.func_74779_i("WPName"));
         MapWaypoint waypoint = this.currentMapSettings().getWaypointByName(wpName);
         if (waypoint != null) {
            int count = nbt.func_74762_e("Count");
            return Pair.of(wpName, count);
         } else {
            this.playerData.logPlayerError("Loaded nonexistent map waypoint %s", wpName.toString());
            return null;
         }
      });
      this.nextCustomWaypointId = (Integer)DataUtil.getIfNBTContains(this.nextCustomWaypointId, playerNBT, "NextCWPID", CompoundNBT::func_74762_e);
      DataUtil.loadCollectionFromCompoundListNBT(this.customWaypoints, playerNBT.func_150295_c("CustomWaypoints", 10), (nbt) -> {
         CustomWaypoint cwp = CustomWaypoint.load(this.currentMapSettings(), nbt);
         return cwp;
      });
      DataUtil.loadMapFromListNBT(this.customWaypointUseCounts, playerNBT.func_150295_c("CWPUses", 10), (nbt) -> {
         int cwpId = nbt.func_74762_e("CustomID");
         if (this.customWaypoints.hasKey(cwpId)) {
            int count = nbt.func_74762_e("Count");
            return Pair.of(cwpId, count);
         } else {
            this.playerData.logPlayerError("Loaded nonexistent custom waypoint (ID %d)", cwpId);
            return null;
         }
      });
      DataUtil.loadCollectionFromCompoundListNBT(this.adoptedCustomWaypoints, playerNBT.func_150295_c("AdoptedCustomWaypoints", 10), (nbt) -> {
         AdoptedCustomWaypoint cwp = AdoptedCustomWaypoint.load(this.currentMapSettings(), nbt);
         return cwp;
      });
      DataUtil.loadMapFromListNBT(this.adoptedCustomWaypointUseCounts, playerNBT.func_150295_c("AdoptedCWPUses", 10), (nbt) -> {
         AdoptedCustomWaypointKey key = AdoptedCustomWaypointKey.load(nbt);
         if (this.adoptedCustomWaypoints.hasKey(key)) {
            int count = nbt.func_74762_e("Count");
            return Pair.of(key, count);
         } else {
            this.playerData.logPlayerError("Loaded nonexistent adopted custom waypoint (creator %s, ID %d)", key.getCreatedPlayer(), key.getWaypointId());
            return null;
         }
      });
      this.showMapWaypoints = (Boolean)DataUtil.getIfNBTContains(this.showMapWaypoints, playerNBT, "ShowWP", CompoundNBT::func_74767_n);
      this.showCustomWaypoints = (Boolean)DataUtil.getIfNBTContains(this.showCustomWaypoints, playerNBT, "ShowCWP", CompoundNBT::func_74767_n);
      this.ftSinceTick = (Integer)DataUtil.getIfNBTContains(this.ftSinceTick, playerNBT, "FTSince", CompoundNBT::func_74762_e);
      this.lastOnlineTime = (Long)DataUtil.getIfNBTContains(this.lastOnlineTime, playerNBT, "LastOnlineTime", CompoundNBT::func_74763_f);
      this.targetFTWaypoint = null;
      this.uuidToMount = (UUID)DataUtil.getIfNBTContains(this.uuidToMount, playerNBT, "MountUUID", DataUtil::getUniqueIdBackCompat);
      this.uuidToMountTime = playerNBT.func_74762_e("MountUUIDTime");
   }

   protected void sendLoginData(PacketBuffer buf) {
      DataUtil.writeCollectionToBuffer(buf, this.unlockedWaypointRegions, (region) -> {
         buf.func_150787_b(region.getAssignedId());
      });
      DataUtil.writeMapToBuffer(buf, this.waypointUseCounts, (wpName, count) -> {
         MapWaypoint wp = this.currentMapSettings().getWaypointByName(wpName);
         buf.func_150787_b(wp.getAssignedId());
         buf.func_150787_b(count);
      });
      DataUtil.writeCollectionToBuffer(buf, this.customWaypoints, (cwp) -> {
         cwp.write(buf);
      });
      DataUtil.writeMapToBuffer(buf, this.customWaypointUseCounts, (cwpId, count) -> {
         buf.func_150787_b(cwpId);
         buf.func_150787_b(count);
      });
      DataUtil.writeCollectionToBuffer(buf, this.adoptedCustomWaypoints, (cwp) -> {
         cwp.write(buf);
      });
      DataUtil.writeMapToBuffer(buf, this.adoptedCustomWaypointUseCounts, (cwpKey, count) -> {
         cwpKey.write(buf);
         buf.func_150787_b(count);
      });
      buf.writeBoolean(this.showMapWaypoints);
      buf.writeBoolean(this.showCustomWaypoints);
      buf.writeInt(this.ftSinceTick);
   }

   protected void receiveLoginData(PacketBuffer buf) {
      DataUtil.fillCollectionFromBuffer(buf, this.unlockedWaypointRegions, () -> {
         int regionID = buf.func_150792_a();
         WaypointRegion region = this.currentMapSettings().getWaypointRegionByID(regionID);
         if (region != null) {
            return region;
         } else {
            LOTRLog.warn("Received nonexistent region ID %d from server", regionID);
            return null;
         }
      });
      DataUtil.fillMapFromBuffer(buf, this.waypointUseCounts, () -> {
         int wpID = buf.func_150792_a();
         int count = buf.func_150792_a();
         MapWaypoint wp = this.currentMapSettings().getWaypointByID(wpID);
         if (wp != null) {
            return Pair.of(wp.getName(), count);
         } else {
            LOTRLog.warn("Received nonexistent map waypoint ID %d from server", wpID);
            return null;
         }
      });
      DataUtil.fillCollectionFromBuffer(buf, this.customWaypoints, () -> {
         CustomWaypoint cwp = CustomWaypoint.read(this.currentMapSettings(), buf);
         return cwp;
      });
      DataUtil.fillMapFromBuffer(buf, this.customWaypointUseCounts, () -> {
         int cwpId = buf.func_150792_a();
         int count = buf.func_150792_a();
         if (this.customWaypoints.hasKey(cwpId)) {
            return Pair.of(cwpId, count);
         } else {
            LOTRLog.warn("Received nonexistent custom waypoint ID %d from server", cwpId);
            return null;
         }
      });
      DataUtil.fillCollectionFromBuffer(buf, this.adoptedCustomWaypoints, () -> {
         AdoptedCustomWaypoint cwp = AdoptedCustomWaypoint.read(this.currentMapSettings(), buf);
         return cwp;
      });
      DataUtil.fillMapFromBuffer(buf, this.adoptedCustomWaypointUseCounts, () -> {
         AdoptedCustomWaypointKey key = AdoptedCustomWaypointKey.read(buf);
         int count = buf.func_150792_a();
         if (this.adoptedCustomWaypoints.hasKey(key)) {
            return Pair.of(key, count);
         } else {
            LOTRLog.warn("Received nonexistent adopted custom waypoint key (creator %s, ID %d) from server", key.getCreatedPlayer(), key.getWaypointId());
            return null;
         }
      });
      this.showMapWaypoints = buf.readBoolean();
      this.showCustomWaypoints = buf.readBoolean();
      this.ftSinceTick = buf.readInt();
   }

   protected void handleLogin(ServerPlayerEntity player) {
      this.updateFastTravelClockFromLastOnlineTime(player);
   }

   protected void onUpdate(ServerPlayerEntity player, ServerWorld world, int tick) {
      Biome biome = world.func_226691_t_(player.func_233580_cy_());
      List waypointRegions = this.currentMapSettings().getWaypointRegionsForBiome(biome, world);
      waypointRegions.forEach(this::unlockWaypointRegion);
      this.setTimeSinceFT(this.ftSinceTick + 1);
      this.lastOnlineTime = this.getCurrentOnlineTime(world);
      double curPosX = player.func_226277_ct_();
      double curPosY = player.func_226278_cu_();
      double curPosZ = player.func_226281_cx_();
      double mountHasTravelledRange;
      if (this.targetFTWaypoint != null) {
         if (player.func_70608_bn()) {
            this.setTargetWaypoint((Waypoint)null);
            LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.ft.inBed"));
         } else {
            mountHasTravelledRange = curPosX - this.lastPosX;
            double dy = curPosY - this.lastPosY;
            double dz = curPosZ - this.lastPosZ;
            double dSqToLastPos = mountHasTravelledRange * mountHasTravelledRange + dy * dy + dz * dz;
            if (dSqToLastPos > 0.001D) {
               this.setTargetWaypoint((Waypoint)null);
               LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.ft.motion"));
            } else if (this.ticksUntilFT > 0) {
               int seconds = this.ticksUntilFT / 20;
               if (this.ticksUntilFT == 200) {
                  LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.ft.ticksStart", new Object[]{this.targetFTWaypoint.getDisplayName(), seconds}));
               } else if (this.ticksUntilFT % 20 == 0 && seconds <= 5) {
                  LOTRUtil.sendMessage(player, new TranslationTextComponent("chat.lotr.ft.ticks", new Object[]{seconds}));
               }

               --this.ticksUntilFT;
               this.setTicksUntilFT(this.ticksUntilFT);
            } else {
               this.fastTravelTo(player, this.targetFTWaypoint);
               this.setTargetWaypoint((Waypoint)null);
            }
         }
      } else {
         this.setTicksUntilFT(0);
      }

      this.lastPosX = curPosX;
      this.lastPosY = curPosY;
      this.lastPosZ = curPosZ;
      if (this.uuidToMount != null) {
         if (this.uuidToMountTime > 0) {
            --this.uuidToMountTime;
         } else {
            mountHasTravelledRange = 32.0D;
            List mountMatches = world.func_175647_a(Entity.class, player.func_174813_aQ().func_186662_g(mountHasTravelledRange), (entity) -> {
               return this.uuidToMount.equals(entity.func_110124_au());
            });
            if (!mountMatches.isEmpty()) {
               Entity travelledMount = (Entity)mountMatches.get(0);
               player.func_184220_m(travelledMount);
            } else {
               LOTRLog.warn("Tried to remount player %s after fast travel, but couldn't find the mount nearby", UsernameHelper.getRawUsername(player));
            }

            this.setUUIDToMount((UUID)null);
         }
      }

   }

   private long getCurrentOnlineTime(ServerWorld serverWorld) {
      return serverWorld.func_73046_m().func_71218_a(World.field_234918_g_).func_82737_E();
   }

   private void updateFastTravelClockFromLastOnlineTime(ServerPlayerEntity player) {
      if (this.lastOnlineTime > 0L) {
         ServerWorld world = player.func_71121_q();
         if (!world.func_73046_m().func_71264_H()) {
            long currentOnlineTime = this.getCurrentOnlineTime(world);
            int diff = (int)(currentOnlineTime - this.lastOnlineTime);
            double offlineFactor = 0.1D;
            int ftClockIncrease = (int)((double)diff * offlineFactor);
            if (ftClockIncrease > 0) {
               this.setTimeSinceFTWithUpdate(this.ftSinceTick + ftClockIncrease);
               if (world.func_82736_K().func_223586_b(LOTRGameRules.FAST_TRAVEL) && LOTRWorldTypes.hasMapFeatures(world)) {
                  int ftClockIncreaseSecs = ftClockIncrease / 20;
                  if (ftClockIncreaseSecs > 0) {
                     ITextComponent msg = new TranslationTextComponent("chat.lotr.ft.offlineTick", new Object[]{LOTRUtil.getHMSTime_Ticks(diff), LOTRUtil.getHMSTime_Seconds(ftClockIncreaseSecs)});
                     LOTRUtil.sendMessage(player, msg);
                  }
               }
            }
         }

      }
   }

   public boolean isWaypointRegionUnlocked(WaypointRegion region) {
      return this.unlockedWaypointRegions.contains(region);
   }

   public void unlockWaypointRegion(WaypointRegion region) {
      if (!this.unlockedWaypointRegions.contains(region)) {
         this.unlockedWaypointRegions.add(region);
         this.markDirty();
         this.sendPacketToClient(new SPacketWaypointRegion(region, true));
      }

   }

   public void lockWaypointRegion(WaypointRegion region) {
      if (this.unlockedWaypointRegions.contains(region)) {
         this.unlockedWaypointRegions.remove(region);
         this.markDirty();
         this.sendPacketToClient(new SPacketWaypointRegion(region, false));
      }

   }

   public List getCustomWaypoints() {
      return this.customWaypoints;
   }

   public CustomWaypoint getCustomWaypointById(int customId) {
      return (CustomWaypoint)this.customWaypoints.lookup(customId);
   }

   public int getNumCustomWaypoints() {
      return this.customWaypoints.size() + this.adoptedCustomWaypoints.size();
   }

   public int getMaxCustomWaypoints() {
      return 20;
   }

   public boolean canCreateOrAdoptMoreCustomWaypoints() {
      return this.getNumCustomWaypoints() < this.getMaxCustomWaypoints();
   }

   public CustomWaypoint createNewCustomWaypoint(String name, String lore, boolean isPublic, BlockPos pos) {
      int cwpId = this.nextCustomWaypointId++;
      CustomWaypoint waypoint = new CustomWaypoint(this.currentMapSettings(), this.getPlayerUUID(), cwpId, name, lore, pos, isPublic);
      this.customWaypoints.add(waypoint);
      this.markDirty();
      this.sendPacketToClient(new SPacketCreateCustomWaypoint(waypoint));
      return waypoint;
   }

   public void addCreatedCustomWaypointFromServer(CustomWaypoint waypoint) {
      this.customWaypoints.add(waypoint);
      this.markDirty();
   }

   public boolean updateCustomWaypoint(World world, CustomWaypoint waypoint, String name, String lore, boolean isPublic) {
      if (this.checkCustomWaypointBelongsToPlayer(waypoint)) {
         CustomWaypoint existingMatch = (CustomWaypoint)this.customWaypoints.lookup(waypoint.getCustomId());
         existingMatch.update(world, name, lore, isPublic);
         this.markDirty();
         this.sendPacketToClient(new SPacketUpdateCustomWaypoint(waypoint));
         return true;
      } else {
         this.playerData.logPlayerError("Tried to update a custom waypoint (%s) that actually belongs to %s", waypoint.getDisplayName(), waypoint.getCreatedPlayer());
         return false;
      }
   }

   private boolean checkCustomWaypointBelongsToPlayer(CustomWaypoint waypoint) {
      return waypoint.getCreatedPlayer().equals(this.getPlayerUUID());
   }

   public boolean removeCustomWaypoint(World world, CustomWaypoint waypoint) {
      if (this.checkCustomWaypointBelongsToPlayer(waypoint)) {
         int waypointId = waypoint.getCustomId();
         CustomWaypoint existingMatch = (CustomWaypoint)this.customWaypoints.lookup(waypointId);
         existingMatch.removeFromAllAdoptedPlayersWhenDestroyed(world);
         this.customWaypoints.remove(existingMatch);
         this.customWaypointUseCounts.remove(waypointId);
         this.markDirty();
         this.sendPacketToClient(new SPacketDeleteCustomWaypoint(existingMatch));
         return true;
      } else {
         this.playerData.logPlayerError("Tried to delete a custom waypoint (%s) that actually belongs to %s", waypoint.getDisplayName(), waypoint.getCreatedPlayer());
         return false;
      }
   }

   public void updateCustomWaypointAdoptedCount(CustomWaypoint customWaypoint, int adoptedCount) {
      this.markDirty();
      this.sendPacketToClient(new SPacketCustomWaypointAdoptedCount(customWaypoint, adoptedCount));
   }

   public List getAdoptedCustomWaypoints() {
      return this.adoptedCustomWaypoints;
   }

   public AdoptedCustomWaypoint getAdoptedCustomWaypointByKey(AdoptedCustomWaypointKey key) {
      return (AdoptedCustomWaypoint)this.adoptedCustomWaypoints.lookup(key);
   }

   public boolean adoptCustomWaypoint(ServerWorld world, CustomWaypoint originalWaypoint) {
      if (this.checkCustomWaypointBelongsToPlayer(originalWaypoint)) {
         this.playerData.logPlayerError("Tried to adopt a custom waypoint (%s) that is their own waypoint", originalWaypoint.getDisplayName());
         return false;
      } else {
         AdoptedCustomWaypoint waypoint = AdoptedCustomWaypoint.adopt(this.currentMapSettings(), originalWaypoint);
         this.adoptedCustomWaypoints.add(waypoint);
         this.markDirty();
         originalWaypoint.onAdoptedBy(this.getPlayerUUID(), world);
         this.sendPacketToClient(new SPacketAdoptCustomWaypoint(waypoint));
         return true;
      }
   }

   public void addAdoptedCustomWaypointFromServer(AdoptedCustomWaypoint waypoint) {
      this.adoptedCustomWaypoints.add(waypoint);
      this.markDirty();
   }

   public void updateAdoptedCustomWaypointFromOriginal(CustomWaypoint originalWaypoint) {
      AdoptedCustomWaypointKey key = AdoptedCustomWaypointKey.of(originalWaypoint.getCreatedPlayer(), originalWaypoint.getCustomId());
      AdoptedCustomWaypoint adoptedWaypoint = (AdoptedCustomWaypoint)this.adoptedCustomWaypoints.lookup(key);
      if (adoptedWaypoint != null) {
         adoptedWaypoint.updateFromOriginal(originalWaypoint);
         this.markDirty();
         this.sendPacketToClient(new SPacketUpdateAdoptedCustomWaypoint(adoptedWaypoint));
      } else {
         this.playerData.logPlayerError("Tried to update an adopted custom waypoint from its original (creator %s, ID %d) but could not find it!", key.getCreatedPlayer(), key.getWaypointId());
      }

   }

   public void removeAdoptedCustomWaypoint(World world, AdoptedCustomWaypoint waypoint) {
      AdoptedCustomWaypointKey key = waypoint.getAdoptedKey();
      AdoptedCustomWaypoint existingMatch = (AdoptedCustomWaypoint)this.adoptedCustomWaypoints.lookup(key);
      this.adoptedCustomWaypoints.remove(existingMatch);
      this.adoptedCustomWaypointUseCounts.remove(key);
      this.markDirty();
      if (world instanceof ServerWorld) {
         ServerWorld sWorld = (ServerWorld)world;
         this.findOriginalWaypoint(sWorld, waypoint).onForsakenBy(this.getPlayerUUID(), sWorld);
      }

      this.sendPacketToClient(new SPacketDeleteAdoptedCustomWaypoint(existingMatch));
   }

   private CustomWaypoint findOriginalWaypoint(ServerWorld world, AdoptedCustomWaypoint waypoint) {
      FastTravelDataModule creatorFtData = this.getLevelData().getData(world, waypoint.getCreatedPlayer()).getFastTravelData();
      return creatorFtData.getCustomWaypointById(waypoint.getCustomId());
   }

   public void removeAdoptedCustomWaypointWhenOriginalDestroyed(World world, CustomWaypoint originalWaypoint) {
      AdoptedCustomWaypointKey key = AdoptedCustomWaypointKey.of(originalWaypoint.getCreatedPlayer(), originalWaypoint.getCustomId());
      AdoptedCustomWaypoint adoptedWaypoint = (AdoptedCustomWaypoint)this.adoptedCustomWaypoints.lookup(key);
      if (adoptedWaypoint != null) {
         this.removeAdoptedCustomWaypoint(world, adoptedWaypoint);
         this.executeIfPlayerExistsServerside((player) -> {
            ITextComponent msg = (new TranslationTextComponent("chat.lotr.cwp.adopted.destroyed", new Object[]{originalWaypoint.getDisplayName()})).func_240699_a_(TextFormatting.RED);
            LOTRUtil.sendMessage(player, msg);
         });
      } else {
         this.playerData.logPlayerError("Tried to remove an adopted custom waypoint when its original was destroyed (creator %s, ID %d) but could not find it!", key.getCreatedPlayer(), key.getWaypointId());
      }

   }

   public int getTimeSinceFT() {
      return this.ftSinceTick;
   }

   public void setTimeSinceFT(int i) {
      this.setTimeSinceFT(i, false);
   }

   public void setTimeSinceFTWithUpdate(int i) {
      this.setTimeSinceFT(i, true);
   }

   private void setTimeSinceFT(int i, boolean forceUpdate) {
      int preTick = this.ftSinceTick;
      i = Math.max(0, i);
      this.ftSinceTick = i;
      boolean bigChange = (this.ftSinceTick == 0 || preTick == 0) && this.ftSinceTick != preTick || preTick < 0 && this.ftSinceTick >= 0;
      if (forceUpdate) {
         bigChange = true;
      }

      if (bigChange || isTimerAutosaveTick()) {
         this.markDirty();
      }

      if (bigChange || this.ftSinceTick % 5 == 0) {
         this.sendPacketToClient(new SPacketTimeSinceFT(this.ftSinceTick));
      }

   }

   public int getWaypointFTTime(Waypoint waypoint, PlayerEntity player) {
      int baseMin = this.getLevelData().getWaypointCooldownMin();
      int baseMax = this.getLevelData().getWaypointCooldownMax();
      int useCount = this.getWPUseCount(waypoint);
      double dist = waypoint.getDistanceFromPlayer(player);
      double time = (double)baseMin;
      double added = (double)(baseMax - baseMin) * Math.pow(0.9D, (double)useCount);
      time += added;
      time *= Math.max(1.0D, dist * 1.2E-5D);
      int seconds = (int)Math.round(time);
      seconds = Math.max(seconds, 0);
      return seconds * 20;
   }

   public int getWPUseCount(Waypoint waypoint) {
      if (waypoint instanceof MapWaypoint) {
         MapWaypoint mapWp = (MapWaypoint)waypoint;
         ResourceLocation wpName = mapWp.getName();
         if (this.waypointUseCounts.containsKey(wpName)) {
            return (Integer)this.waypointUseCounts.get(wpName);
         }
      } else if (waypoint instanceof CustomWaypoint) {
         CustomWaypoint cwp = (CustomWaypoint)waypoint;
         int cwpId = cwp.getCustomId();
         if (cwp.getCreatedPlayer().equals(this.getPlayerUUID()) && this.customWaypointUseCounts.containsKey(cwpId)) {
            return (Integer)this.customWaypointUseCounts.get(cwpId);
         }
      } else if (waypoint instanceof AdoptedCustomWaypoint) {
         AdoptedCustomWaypoint cwp = (AdoptedCustomWaypoint)waypoint;
         AdoptedCustomWaypointKey key = cwp.getAdoptedKey();
         if (this.adoptedCustomWaypointUseCounts.containsKey(key)) {
            return (Integer)this.adoptedCustomWaypointUseCounts.get(key);
         }
      } else {
         LOTRLog.error("Tried to fetch the use count for an unknown waypoint type %s", waypoint.getClass());
      }

      return 0;
   }

   public void setWPUseCount(Waypoint waypoint, int count) {
      if (waypoint instanceof MapWaypoint) {
         MapWaypoint mapWp = (MapWaypoint)waypoint;
         ResourceLocation wpName = mapWp.getName();
         this.waypointUseCounts.put(wpName, count);
      } else if (waypoint instanceof CustomWaypoint) {
         CustomWaypoint cwp = (CustomWaypoint)waypoint;
         if (cwp.getCreatedPlayer().equals(this.getPlayerUUID())) {
            this.customWaypointUseCounts.put(cwp.getCustomId(), count);
         }
      } else if (waypoint instanceof AdoptedCustomWaypoint) {
         AdoptedCustomWaypoint cwp = (AdoptedCustomWaypoint)waypoint;
         this.adoptedCustomWaypointUseCounts.put(cwp.getAdoptedKey(), count);
      } else {
         LOTRLog.error("Tried to update the use count for an unknown waypoint type %s", waypoint.getClass());
      }

      this.markDirty();
      this.sendPacketToClient(new SPacketWaypointUseCount(waypoint, count));
   }

   public void incrementWPUseCount(Waypoint waypoint) {
      this.setWPUseCount(waypoint, this.getWPUseCount(waypoint) + 1);
   }

   public boolean isUnderAttack(ServerPlayerEntity player) {
      World world = player.field_70170_p;
      if (!player.field_71075_bZ.field_75098_d) {
         double range = 16.0D;
         List attackingEntities = world.func_225316_b(MobEntity.class, player.func_174813_aQ().func_186662_g(range), (entity) -> {
            return entity.func_70638_az() == player;
         });
         return !attackingEntities.isEmpty();
      } else {
         return false;
      }
   }

   public void setTargetWaypoint(Waypoint waypoint) {
      this.targetFTWaypoint = waypoint;
      this.markDirty();
      if (waypoint != null) {
         this.setTicksUntilFT(200);
      } else {
         this.setTicksUntilFT(0);
      }

   }

   public int getTicksUntilFT() {
      return this.ticksUntilFT;
   }

   public void setTicksUntilFT(int i) {
      if (this.ticksUntilFT != i) {
         this.ticksUntilFT = i;
         if (this.ticksUntilFT == 200 || this.ticksUntilFT == 0) {
            this.markDirty();
         }
      }

   }

   private void fastTravelTo(ServerPlayerEntity player, Waypoint waypoint) {
      ServerWorld world = player.func_71121_q();
      BlockPos travelPos = waypoint.getTravelPosition(world, player);
      if (travelPos == null) {
         LOTRLog.warn("Player %s fast travel to %s was cancelled because the waypoint returned a null travel position.", UsernameHelper.getRawUsername(player), waypoint.getRawName());
      } else {
         double startXF = player.func_226277_ct_();
         double startYF = player.func_226278_cu_();
         double startZF = player.func_226281_cx_();
         int startX = MathHelper.func_76128_c(startXF);
         int startZ = MathHelper.func_76128_c(startZF);
         double followerRange = 256.0D;
         List entities = world.func_217357_a(MobEntity.class, player.func_174813_aQ().func_186662_g(256.0D));
         Set entitiesToTransport = new HashSet();
         Iterator var17 = entities.iterator();

         while(true) {
            while(var17.hasNext()) {
               MobEntity entity = (MobEntity)var17.next();
               if (entity instanceof TameableEntity) {
                  TameableEntity pet = (TameableEntity)entity;
                  if (pet.func_70902_q() == player && !pet.func_233685_eM_()) {
                     entitiesToTransport.add(pet);
                     continue;
                  }
               }

               if (entity.func_110167_bD() && entity.func_110166_bE() == player) {
                  entitiesToTransport.add(entity);
               }
            }

            Set transportExclusions = new HashSet();
            Iterator var29 = entitiesToTransport.iterator();

            Entity mount;
            while(var29.hasNext()) {
               MobEntity entity = (MobEntity)var29.next();
               Iterator var20 = entity.func_184188_bt().iterator();

               while(var20.hasNext()) {
                  mount = (Entity)var20.next();
                  if (entitiesToTransport.contains(mount)) {
                     transportExclusions.add(mount);
                  }
               }
            }

            entitiesToTransport.removeAll(transportExclusions);
            Entity playerMount = player.func_184187_bx();
            player.func_184210_p();
            player.func_70634_a((double)travelPos.func_177958_n() + 0.5D, (double)travelPos.func_177956_o(), (double)travelPos.func_177952_p() + 0.5D);
            player.field_70143_R = 0.0F;
            if (playerMount instanceof MobEntity) {
               playerMount = this.fastTravelEntity(world, (MobEntity)playerMount, (double)travelPos.func_177958_n() + 0.5D, (double)travelPos.func_177956_o(), (double)travelPos.func_177952_p() + 0.5D);
            }

            if (playerMount != null) {
               this.setUUIDToMount(((Entity)playerMount).func_110124_au());
            }

            Iterator var32 = entitiesToTransport.iterator();

            while(var32.hasNext()) {
               MobEntity entity = (MobEntity)var32.next();
               mount = entity.func_184187_bx();
               entity.func_184210_p();
               entity = this.fastTravelEntity(world, entity, (double)travelPos.func_177958_n() + 0.5D, (double)travelPos.func_177956_o(), (double)travelPos.func_177952_p() + 0.5D);
               if (mount instanceof MobEntity) {
                  Entity mount = this.fastTravelEntity(world, (MobEntity)mount, (double)travelPos.func_177958_n() + 0.5D, (double)travelPos.func_177956_o(), (double)travelPos.func_177952_p() + 0.5D);
                  entity.func_184220_m(mount);
               }
            }

            this.sendFTScreenPacket(player, waypoint, startX, startZ);
            this.setTimeSinceFTWithUpdate(0);
            this.incrementWPUseCount(waypoint);
            player.func_195066_a(LOTRStats.FAST_TRAVEL);
            double dx = player.func_226277_ct_() - startXF;
            double dy = player.func_226278_cu_() - startYF;
            double dz = player.func_226281_cx_() - startZF;
            int distanceInM = Math.round(MathHelper.func_76133_a(dx * dx + dy * dy + dz * dz));
            if (distanceInM > 0) {
               player.func_195067_a(LOTRStats.FAST_TRAVEL_ONE_M, distanceInM);
            }

            return;
         }
      }
   }

   private void sendFTScreenPacket(ServerPlayerEntity player, Waypoint waypoint, int startX, int startZ) {
      LOTRPacketHandler.sendTo(new SPacketFastTravel(waypoint, startX, startZ), player);
   }

   private MobEntity fastTravelEntity(ServerWorld world, MobEntity entity, double x, double y, double z) {
      entity.func_70012_b(x, y, z, entity.field_70177_z, entity.field_70125_A);
      entity.field_70143_R = 0.0F;
      entity.func_70661_as().func_75499_g();
      entity.func_70624_b((LivingEntity)null);
      ServerChunkProvider scp = world.func_72863_F();
      scp.func_217226_b(entity);
      scp.func_217230_c(entity);
      return entity;
   }

   private void setUUIDToMount(UUID uuid) {
      this.uuidToMount = uuid;
      if (this.uuidToMount != null) {
         this.uuidToMountTime = 20;
      } else {
         this.uuidToMountTime = 0;
      }

      this.markDirty();
   }

   public boolean getShowMapWaypoints() {
      return this.showMapWaypoints;
   }

   public boolean getShowCustomWaypoints() {
      return this.showCustomWaypoints;
   }

   public void setShowMapWaypoints(boolean flag) {
      if (this.showMapWaypoints != flag) {
         this.showMapWaypoints = flag;
         this.markDirty();
         this.sendShowWaypointsToClient();
      }

   }

   public void setShowCustomWaypoints(boolean flag) {
      if (this.showCustomWaypoints != flag) {
         this.showCustomWaypoints = flag;
         this.markDirty();
         this.sendShowWaypointsToClient();
      }

   }

   private void sendShowWaypointsToClient() {
      this.sendPacketToClient(new SPacketShowWaypoints(this.showMapWaypoints, this.showCustomWaypoints));
   }

   public void toggleShowMapWaypointsAndSendToServer() {
      this.showMapWaypoints = !this.showMapWaypoints;
      this.sendShowWaypointsToServer();
   }

   public void toggleShowCustomWaypointsAndSendToServer() {
      this.showCustomWaypoints = !this.showCustomWaypoints;
      this.sendShowWaypointsToServer();
   }

   private void sendShowWaypointsToServer() {
      LOTRPacketHandler.sendToServer(new CPacketToggleShowWaypoints(this.showMapWaypoints, this.showCustomWaypoints));
   }
}
