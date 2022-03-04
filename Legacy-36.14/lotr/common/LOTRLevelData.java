package lotr.common;

import com.google.common.base.Optional;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.network.LOTRPacketAlignment;
import lotr.common.network.LOTRPacketEnableAlignmentZones;
import lotr.common.network.LOTRPacketFTCooldown;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketLogin;
import lotr.common.network.LOTRPacketPortalPos;
import lotr.common.network.LOTRPacketShield;
import lotr.common.network.LOTRPacketUpdatePlayerLocations;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRTravellingTraderSpawner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import org.apache.commons.lang3.StringUtils;

public class LOTRLevelData {
   private static final int WAYPOINT_COOLDOWN_DEFAULT = 1800;
   private static final int WAYPOINT_COOLDOWN_MIN_DEFAULT = 180;
   public static int madePortal;
   public static int madeMiddleEarthPortal;
   public static int overworldPortalX;
   public static int overworldPortalY;
   public static int overworldPortalZ;
   public static int middleEarthPortalX;
   public static int middleEarthPortalY;
   public static int middleEarthPortalZ;
   private static int structuresBanned;
   private static int waypointCooldownMax;
   private static int waypointCooldownMin;
   private static boolean gollumSpawned;
   private static boolean enableAlignmentZones;
   private static float conquestRate = 1.0F;
   public static boolean clientside_thisServer_feastMode;
   public static boolean clientside_thisServer_fellowshipCreation;
   public static int clientside_thisServer_fellowshipMaxSize;
   public static boolean clientside_thisServer_enchanting;
   public static boolean clientside_thisServer_enchantingLOTR;
   public static boolean clientside_thisServer_strictFactionTitleRequirements;
   public static int clientside_thisServer_customWaypointMinY;
   private static boolean commemorateEmpressShamiir;
   public static boolean clientside_thisServer_commemorateEmpressShamiir = true;
   private static EnumDifficulty difficulty;
   private static boolean difficultyLock = false;
   private static Map playerDataMap = new HashMap();
   private static Map playerTitleOfflineCacheMap = new HashMap();
   private static int loadsAvertedByOfflineTitleCache = 0;
   public static boolean needsLoad = true;
   private static boolean needsSave = false;
   private static Random rand = new Random();

   public static void markDirty() {
      needsSave = true;
   }

   public static boolean anyDataNeedsSave() {
      if (needsSave) {
         return true;
      } else if (LOTRSpawnDamping.needsSave) {
         return true;
      } else {
         Iterator var0 = playerDataMap.values().iterator();

         LOTRPlayerData pd;
         do {
            if (!var0.hasNext()) {
               return false;
            }

            pd = (LOTRPlayerData)var0.next();
         } while(!pd.needsSave());

         return true;
      }
   }

   public static File getOrCreateLOTRDir() {
      File file = new File(DimensionManager.getCurrentSaveRootDirectory(), "LOTR");
      if (!file.exists()) {
         file.mkdirs();
      }

      return file;
   }

   private static File getLOTRDat() {
      return new File(getOrCreateLOTRDir(), "LOTR.dat");
   }

   private static File getLOTRPlayerDat(UUID player) {
      File playerDir = new File(getOrCreateLOTRDir(), "players");
      if (!playerDir.exists()) {
         playerDir.mkdirs();
      }

      return new File(playerDir, player.toString() + ".dat");
   }

   public static NBTTagCompound loadNBTFromFile(File file) throws FileNotFoundException, IOException {
      if (file.exists()) {
         FileInputStream fis = new FileInputStream(file);
         NBTTagCompound nbt = CompressedStreamTools.func_74796_a(fis);
         fis.close();
         return nbt;
      } else {
         return new NBTTagCompound();
      }
   }

   public static void saveNBTToFile(File file, NBTTagCompound nbt) throws FileNotFoundException, IOException {
      CompressedStreamTools.func_74799_a(nbt, new FileOutputStream(file));
   }

   public static void save() {
      try {
         if (needsSave) {
            File LOTR_dat = getLOTRDat();
            if (!LOTR_dat.exists()) {
               saveNBTToFile(LOTR_dat, new NBTTagCompound());
            }

            NBTTagCompound levelData = new NBTTagCompound();
            levelData.func_74768_a("MadePortal", madePortal);
            levelData.func_74768_a("MadeMiddlePortal", madeMiddleEarthPortal);
            levelData.func_74768_a("OverworldX", overworldPortalX);
            levelData.func_74768_a("OverworldY", overworldPortalY);
            levelData.func_74768_a("OverworldZ", overworldPortalZ);
            levelData.func_74768_a("MiddleEarthX", middleEarthPortalX);
            levelData.func_74768_a("MiddleEarthY", middleEarthPortalY);
            levelData.func_74768_a("MiddleEarthZ", middleEarthPortalZ);
            levelData.func_74768_a("StructuresBanned", structuresBanned);
            levelData.func_74768_a("WpCdMax", waypointCooldownMax);
            levelData.func_74768_a("WpCdMin", waypointCooldownMin);
            levelData.func_74757_a("GollumSpawned", gollumSpawned);
            levelData.func_74757_a("AlignmentZones", enableAlignmentZones);
            levelData.func_74776_a("ConqRate", conquestRate);
            levelData.func_74757_a("CommemorateEmpressShamiir", commemorateEmpressShamiir);
            if (difficulty != null) {
               levelData.func_74768_a("SavedDifficulty", difficulty.func_151525_a());
            }

            levelData.func_74757_a("DifficultyLock", difficultyLock);
            NBTTagCompound travellingTraderData = new NBTTagCompound();
            Iterator var3 = LOTREventSpawner.travellingTraders.iterator();

            while(true) {
               if (!var3.hasNext()) {
                  levelData.func_74782_a("TravellingTraders", travellingTraderData);
                  LOTRGreyWandererTracker.save(levelData);
                  LOTRDate.saveDates(levelData);
                  saveNBTToFile(LOTR_dat, levelData);
                  needsSave = false;
                  break;
               }

               LOTRTravellingTraderSpawner trader = (LOTRTravellingTraderSpawner)var3.next();
               NBTTagCompound nbt = new NBTTagCompound();
               trader.writeToNBT(nbt);
               travellingTraderData.func_74782_a(trader.entityClassName, nbt);
            }
         }

         int i = 0;
         int j = 0;

         for(Iterator var9 = playerDataMap.entrySet().iterator(); var9.hasNext(); ++j) {
            Entry e = (Entry)var9.next();
            UUID player = (UUID)e.getKey();
            LOTRPlayerData pd = (LOTRPlayerData)e.getValue();
            if (pd.needsSave()) {
               saveData(player);
               ++i;
            }
         }

         if (LOTRSpawnDamping.needsSave) {
            LOTRSpawnDamping.saveAll();
         }
      } catch (Exception var6) {
         FMLLog.severe("Error saving LOTR data", new Object[0]);
         var6.printStackTrace();
      }

   }

   public static void load() {
      try {
         NBTTagCompound levelData = loadNBTFromFile(getLOTRDat());
         File oldLOTRDat = new File(DimensionManager.getCurrentSaveRootDirectory(), "LOTR.dat");
         if (oldLOTRDat.exists()) {
            levelData = loadNBTFromFile(oldLOTRDat);
            oldLOTRDat.delete();
            if (levelData.func_74764_b("PlayerData")) {
               NBTTagList playerDataTags = levelData.func_150295_c("PlayerData", 10);

               for(int i = 0; i < playerDataTags.func_74745_c(); ++i) {
                  NBTTagCompound nbt = playerDataTags.func_150305_b(i);
                  UUID player = UUID.fromString(nbt.func_74779_i("PlayerUUID"));
                  saveNBTToFile(getLOTRPlayerDat(player), nbt);
               }
            }
         }

         madePortal = levelData.func_74762_e("MadePortal");
         madeMiddleEarthPortal = levelData.func_74762_e("MadeMiddlePortal");
         overworldPortalX = levelData.func_74762_e("OverworldX");
         overworldPortalY = levelData.func_74762_e("OverworldY");
         overworldPortalZ = levelData.func_74762_e("OverworldZ");
         middleEarthPortalX = levelData.func_74762_e("MiddleEarthX");
         middleEarthPortalY = levelData.func_74762_e("MiddleEarthY");
         middleEarthPortalZ = levelData.func_74762_e("MiddleEarthZ");
         structuresBanned = levelData.func_74762_e("StructuresBanned");
         if (levelData.func_74764_b("FastTravel")) {
            waypointCooldownMax = levelData.func_74762_e("FastTravel") / 20;
         } else if (levelData.func_74764_b("WpCdMax")) {
            waypointCooldownMax = levelData.func_74762_e("WpCdMax");
         } else {
            waypointCooldownMax = 1800;
         }

         if (levelData.func_74764_b("FastTravelMin")) {
            waypointCooldownMin = levelData.func_74762_e("FastTravelMin") / 20;
         } else if (levelData.func_74764_b("WpCdMin")) {
            waypointCooldownMin = levelData.func_74762_e("WpCdMin");
         } else {
            waypointCooldownMin = 180;
         }

         gollumSpawned = levelData.func_74767_n("GollumSpawned");
         if (levelData.func_74764_b("AlignmentZones")) {
            enableAlignmentZones = levelData.func_74767_n("AlignmentZones");
         } else {
            enableAlignmentZones = true;
         }

         if (levelData.func_74764_b("ConqRate")) {
            conquestRate = levelData.func_74760_g("ConqRate");
         } else {
            conquestRate = 1.0F;
         }

         commemorateEmpressShamiir = levelData.func_74767_n("CommemorateEmpressShamiir");
         if (levelData.func_74764_b("SavedDifficulty")) {
            int id = levelData.func_74762_e("SavedDifficulty");
            EnumDifficulty d = EnumDifficulty.func_151523_a(id);
            difficulty = d;
            LOTRMod.proxy.setClientDifficulty(difficulty);
         } else {
            difficulty = null;
         }

         difficultyLock = levelData.func_74767_n("DifficultyLock");
         NBTTagCompound travellingTraderData = levelData.func_74775_l("TravellingTraders");
         Iterator var10 = LOTREventSpawner.travellingTraders.iterator();

         while(var10.hasNext()) {
            LOTRTravellingTraderSpawner trader = (LOTRTravellingTraderSpawner)var10.next();
            NBTTagCompound nbt = travellingTraderData.func_74775_l(trader.entityClassName);
            trader.readFromNBT(nbt);
         }

         LOTRGreyWandererTracker.load(levelData);
         destroyAllPlayerData();
         LOTRDate.loadDates(levelData);
         LOTRSpawnDamping.loadAll();
         needsLoad = false;
         needsSave = true;
         save();
      } catch (Exception var6) {
         FMLLog.severe("Error loading LOTR data", new Object[0]);
         var6.printStackTrace();
      }

   }

   public static void setMadePortal(int i) {
      madePortal = i;
      markDirty();
   }

   public static void setMadeMiddleEarthPortal(int i) {
      madeMiddleEarthPortal = i;
      markDirty();
   }

   public static void markOverworldPortalLocation(int i, int j, int k) {
      overworldPortalX = i;
      overworldPortalY = j;
      overworldPortalZ = k;
      markDirty();
   }

   public static void markMiddleEarthPortalLocation(int i, int j, int k) {
      LOTRPacketPortalPos packet = new LOTRPacketPortalPos(i, j, k);
      LOTRPacketHandler.networkWrapper.sendToAll(packet);
      markDirty();
   }

   public static void sendLoginPacket(EntityPlayerMP entityplayer) {
      LOTRPacketLogin packet = new LOTRPacketLogin();
      packet.ringPortalX = middleEarthPortalX;
      packet.ringPortalY = middleEarthPortalY;
      packet.ringPortalZ = middleEarthPortalZ;
      packet.ftCooldownMax = waypointCooldownMax;
      packet.ftCooldownMin = waypointCooldownMin;
      packet.difficulty = difficulty;
      packet.difficultyLocked = difficultyLock;
      packet.alignmentZones = enableAlignmentZones;
      packet.feastMode = LOTRConfig.canAlwaysEat;
      packet.fellowshipCreation = LOTRConfig.enableFellowshipCreation;
      packet.fellowshipMaxSize = LOTRConfig.fellowshipMaxSize;
      packet.enchanting = LOTRConfig.enchantingVanilla;
      packet.enchantingLOTR = LOTRConfig.enchantingLOTR;
      packet.strictFactionTitleRequirements = LOTRConfig.strictFactionTitleRequirements;
      packet.customWaypointMinY = LOTRConfig.customWaypointMinY;
      packet.commemorateEmpressShamiir = commemorateEmpressShamiir;
      LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
   }

   public static int getWaypointCooldownMax() {
      return waypointCooldownMax;
   }

   public static int getWaypointCooldownMin() {
      return waypointCooldownMin;
   }

   public static void setWaypointCooldown(int max, int min) {
      max = Math.max(0, max);
      min = Math.max(0, min);
      if (min > max) {
         min = max;
      }

      waypointCooldownMax = max;
      waypointCooldownMin = min;
      markDirty();
      if (!LOTRMod.proxy.isClient()) {
         List players = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b;

         for(int i = 0; i < players.size(); ++i) {
            EntityPlayerMP entityplayer = (EntityPlayerMP)players.get(i);
            LOTRPacketFTCooldown packet = new LOTRPacketFTCooldown(waypointCooldownMax, waypointCooldownMin);
            LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
         }
      }

   }

   public static boolean enableAlignmentZones() {
      return enableAlignmentZones;
   }

   public static void setEnableAlignmentZones(boolean flag) {
      enableAlignmentZones = flag;
      markDirty();
      if (!LOTRMod.proxy.isClient()) {
         List players = MinecraftServer.func_71276_C().func_71203_ab().field_72404_b;

         for(int i = 0; i < players.size(); ++i) {
            EntityPlayerMP entityplayer = (EntityPlayerMP)players.get(i);
            LOTRPacketEnableAlignmentZones packet = new LOTRPacketEnableAlignmentZones(enableAlignmentZones);
            LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
         }
      }

   }

   public static float getConquestRate() {
      return conquestRate;
   }

   public static void setConquestRate(float f) {
      conquestRate = f;
      markDirty();
   }

   public static void sendPlayerData(EntityPlayerMP entityplayer) {
      try {
         LOTRPlayerData pd = getData((EntityPlayer)entityplayer);
         pd.sendPlayerData(entityplayer);
      } catch (Exception var2) {
         FMLLog.severe("Failed to send player data to player " + entityplayer.func_70005_c_(), new Object[0]);
         var2.printStackTrace();
      }

   }

   public static LOTRPlayerData getData(EntityPlayer entityplayer) {
      return getData(entityplayer.func_110124_au());
   }

   public static LOTRPlayerData getData(UUID player) {
      LOTRPlayerData pd = (LOTRPlayerData)playerDataMap.get(player);
      if (pd == null) {
         pd = loadData(player);
         playerTitleOfflineCacheMap.remove(player);
         if (pd == null) {
            pd = new LOTRPlayerData(player);
         }

         playerDataMap.put(player, pd);
      }

      return pd;
   }

   private static LOTRPlayerData loadData(UUID player) {
      try {
         NBTTagCompound nbt = loadNBTFromFile(getLOTRPlayerDat(player));
         LOTRPlayerData pd = new LOTRPlayerData(player);
         pd.load(nbt);
         return pd;
      } catch (Exception var3) {
         FMLLog.severe("Error loading LOTR player data for %s", new Object[]{player});
         var3.printStackTrace();
         return null;
      }
   }

   public static void saveData(UUID player) {
      try {
         NBTTagCompound nbt = new NBTTagCompound();
         LOTRPlayerData pd = (LOTRPlayerData)playerDataMap.get(player);
         pd.save(nbt);
         saveNBTToFile(getLOTRPlayerDat(player), nbt);
      } catch (Exception var3) {
         FMLLog.severe("Error saving LOTR player data for %s", new Object[]{player});
         var3.printStackTrace();
      }

   }

   private static boolean saveAndClearData(UUID player) {
      LOTRPlayerData pd = (LOTRPlayerData)playerDataMap.get(player);
      if (pd != null) {
         boolean saved = false;
         if (pd.needsSave()) {
            saveData(player);
            saved = true;
         }

         playerTitleOfflineCacheMap.put(player, Optional.fromNullable(pd.getPlayerTitle()));
         playerDataMap.remove(player);
         return saved;
      } else {
         FMLLog.severe("Attempted to clear LOTR player data for %s; no data found", new Object[]{player});
         return false;
      }
   }

   public static void saveAndClearUnusedPlayerData() {
      List clearing = new ArrayList();
      Iterator var1 = playerDataMap.keySet().iterator();

      while(var1.hasNext()) {
         UUID player = (UUID)var1.next();
         boolean foundPlayer = false;
         WorldServer[] var4 = MinecraftServer.func_71276_C().field_71305_c;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            WorldServer world = var4[var6];
            if (world.func_152378_a(player) != null) {
               foundPlayer = true;
               break;
            }
         }

         if (!foundPlayer) {
            clearing.add(player);
         }
      }

      int numCleared = clearing.size();
      int sizeBefore = playerDataMap.size();
      int numSaved = 0;
      Iterator var11 = clearing.iterator();

      while(var11.hasNext()) {
         UUID player = (UUID)var11.next();
         boolean saved = saveAndClearData(player);
         if (saved) {
            ++numSaved;
         }
      }

      int sizeNow = playerDataMap.size();
   }

   public static void destroyAllPlayerData() {
      playerDataMap.clear();
   }

   public static LOTRTitle.PlayerTitle getPlayerTitleWithOfflineCache(UUID player) {
      if (playerDataMap.containsKey(player)) {
         return ((LOTRPlayerData)playerDataMap.get(player)).getPlayerTitle();
      } else if (playerTitleOfflineCacheMap.containsKey(player)) {
         ++loadsAvertedByOfflineTitleCache;
         return (LOTRTitle.PlayerTitle)((Optional)playerTitleOfflineCacheMap.get(player)).orNull();
      } else {
         LOTRPlayerData pd = loadData(player);
         LOTRTitle.PlayerTitle playerTitle = pd.getPlayerTitle();
         playerTitleOfflineCacheMap.put(player, Optional.fromNullable(playerTitle));
         return playerTitle;
      }
   }

   public static boolean structuresBanned() {
      return structuresBanned == 1;
   }

   public static void setStructuresBanned(boolean banned) {
      structuresBanned = banned ? 1 : 0;
      markDirty();
   }

   public static void setPlayerBannedForStructures(String username, boolean flag) {
      UUID uuid = UUID.fromString(PreYggdrasilConverter.func_152719_a(username));
      if (uuid != null) {
         getData(uuid).setStructuresBanned(flag);
      }

   }

   public static boolean isPlayerBannedForStructures(EntityPlayer entityplayer) {
      return getData(entityplayer).getStructuresBanned();
   }

   public static Set getBannedStructurePlayersUsernames() {
      Set players = new HashSet();
      Iterator it = playerDataMap.keySet().iterator();

      while(it.hasNext()) {
         UUID uuid = (UUID)it.next();
         if (getData(uuid).getStructuresBanned()) {
            GameProfile profile = MinecraftServer.func_71276_C().func_152358_ax().func_152652_a(uuid);
            if (StringUtils.isBlank(profile.getName())) {
               MinecraftServer.func_71276_C().func_147130_as().fillProfileProperties(profile, true);
            }

            String username = profile.getName();
            if (!StringUtils.isBlank(username)) {
               players.add(username);
            }
         }
      }

      return players;
   }

   public static void sendAlignmentToAllPlayersInWorld(EntityPlayer entityplayer, World world) {
      for(int i = 0; i < world.field_73010_i.size(); ++i) {
         EntityPlayer worldPlayer = (EntityPlayer)world.field_73010_i.get(i);
         LOTRPacketAlignment packet = new LOTRPacketAlignment(entityplayer.func_110124_au());
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)worldPlayer);
      }

   }

   public static void sendAllAlignmentsInWorldToPlayer(EntityPlayer entityplayer, World world) {
      for(int i = 0; i < world.field_73010_i.size(); ++i) {
         EntityPlayer worldPlayer = (EntityPlayer)world.field_73010_i.get(i);
         LOTRPacketAlignment packet = new LOTRPacketAlignment(worldPlayer.func_110124_au());
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   public static void selectDefaultShieldForPlayer(EntityPlayer entityplayer) {
      if (getData(entityplayer).getShield() == null) {
         LOTRShields[] var1 = LOTRShields.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            LOTRShields shield = var1[var3];
            if (shield.canPlayerWear(entityplayer)) {
               getData(entityplayer).setShield(shield);
               return;
            }
         }
      }

   }

   public static void sendShieldToAllPlayersInWorld(EntityPlayer entityplayer, World world) {
      for(int i = 0; i < world.field_73010_i.size(); ++i) {
         EntityPlayer worldPlayer = (EntityPlayer)world.field_73010_i.get(i);
         LOTRPacketShield packet = new LOTRPacketShield(entityplayer.func_110124_au());
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)worldPlayer);
      }

   }

   public static void sendAllShieldsInWorldToPlayer(EntityPlayer entityplayer, World world) {
      for(int i = 0; i < world.field_73010_i.size(); ++i) {
         EntityPlayer worldPlayer = (EntityPlayer)world.field_73010_i.get(i);
         LOTRPacketShield packet = new LOTRPacketShield(worldPlayer.func_110124_au());
         LOTRPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP)entityplayer);
      }

   }

   public static void sendPlayerLocationsToPlayer(EntityPlayer sendPlayer, World world) {
      LOTRPacketUpdatePlayerLocations packetLocations = new LOTRPacketUpdatePlayerLocations();
      boolean isOp = MinecraftServer.func_71276_C().func_71203_ab().func_152596_g(sendPlayer.func_146103_bH());
      boolean creative = sendPlayer.field_71075_bZ.field_75098_d;
      LOTRPlayerData playerData = getData(sendPlayer);
      List fellowshipsMapShow = new ArrayList();
      Iterator var7 = playerData.getFellowshipIDs().iterator();

      while(var7.hasNext()) {
         UUID fsID = (UUID)var7.next();
         LOTRFellowship fs = LOTRFellowshipData.getActiveFellowship(fsID);
         if (fs != null && fs.getShowMapLocations()) {
            fellowshipsMapShow.add(fs);
         }
      }

      for(int i = 0; i < world.field_73010_i.size(); ++i) {
         EntityPlayer otherPlayer = (EntityPlayer)world.field_73010_i.get(i);
         if (otherPlayer != sendPlayer) {
            boolean show = !getData(otherPlayer).getHideMapLocation();
            if (!isOp && getData(otherPlayer).getAdminHideMap()) {
               show = false;
            } else if (LOTRConfig.forceMapLocations == 1) {
               show = false;
            } else if (LOTRConfig.forceMapLocations == 2) {
               show = true;
            } else if (!show) {
               if (isOp && creative) {
                  show = true;
               } else if (!playerData.isSiegeActive()) {
                  Iterator var10 = fellowshipsMapShow.iterator();

                  while(var10.hasNext()) {
                     LOTRFellowship fs = (LOTRFellowship)var10.next();
                     if (fs.containsPlayer(otherPlayer.func_110124_au())) {
                        show = true;
                        break;
                     }
                  }
               }
            }

            if (show) {
               packetLocations.addPlayerLocation(otherPlayer.func_146103_bH(), otherPlayer.field_70165_t, otherPlayer.field_70161_v);
            }
         }
      }

      LOTRPacketHandler.networkWrapper.sendTo(packetLocations, (EntityPlayerMP)sendPlayer);
   }

   public static boolean gollumSpawned() {
      return gollumSpawned;
   }

   public static void setGollumSpawned(boolean flag) {
      gollumSpawned = flag;
      markDirty();
   }

   public static EnumDifficulty getSavedDifficulty() {
      return difficulty;
   }

   public static void setSavedDifficulty(EnumDifficulty d) {
      difficulty = d;
      markDirty();
   }

   public static boolean isDifficultyLocked() {
      return difficultyLock;
   }

   public static void setDifficultyLocked(boolean flag) {
      difficultyLock = flag;
      markDirty();
   }

   public static String getHMSTime_Seconds(int secs) {
      return getHMSTime_Ticks(secs * 20);
   }

   public static String getHMSTime_Ticks(int ticks) {
      int hours = ticks / 72000;
      int minutes = ticks % 72000 / 1200;
      int seconds = ticks % 72000 % 1200 / 20;
      String sHours = StatCollector.func_74837_a("lotr.gui.time.hours", new Object[]{hours});
      String sMinutes = StatCollector.func_74837_a("lotr.gui.time.minutes", new Object[]{minutes});
      String sSeconds = StatCollector.func_74837_a("lotr.gui.time.seconds", new Object[]{seconds});
      if (hours > 0) {
         return StatCollector.func_74837_a("lotr.gui.time.format.hms", new Object[]{sHours, sMinutes, sSeconds});
      } else {
         return minutes > 0 ? StatCollector.func_74837_a("lotr.gui.time.format.ms", new Object[]{sMinutes, sSeconds}) : StatCollector.func_74837_a("lotr.gui.time.format.s", new Object[]{sSeconds});
      }
   }
}
