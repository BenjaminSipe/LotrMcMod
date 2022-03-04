package lotr.common.world.map;

import com.google.common.math.IntMath;
import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRConfig;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRank;
import lotr.common.network.LOTRPacketConquestGrid;
import lotr.common.network.LOTRPacketConquestNotification;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTRConquestGrid {
   private static final int MAP_GRID_POW2 = 3;
   private static final int MAP_GRID_SCALE = IntMath.pow(2, 3);
   private static Map zoneMap = new HashMap();
   private static final LOTRConquestZone dummyZone = (new LOTRConquestZone(-999, -999)).setDummyZone();
   public static boolean needsLoad = true;
   private static final Set dirtyZones = new HashSet();
   public static final float MIN_CONQUEST = 0.0F;
   public static final float MAX_CONQUEST_SET = 100000.0F;
   private static Map cachedZoneFactions = new HashMap();

   private static int worldToGridX(int i) {
      int mapX = i >> 7;
      mapX += 810;
      return mapX >> 3;
   }

   private static int worldToGridZ(int k) {
      int mapZ = k >> 7;
      mapZ += 730;
      return mapZ >> 3;
   }

   private static int gridToMapCoord(int i) {
      return i << 3;
   }

   public static LOTRConquestZone getZoneByWorldCoords(int i, int k) {
      int x = worldToGridX(i);
      int z = worldToGridZ(k);
      return getZoneByGridCoords(x, z);
   }

   public static LOTRConquestZone getZoneByEntityCoords(Entity entity) {
      int i = MathHelper.func_76128_c(entity.field_70165_t);
      int k = MathHelper.func_76128_c(entity.field_70161_v);
      return getZoneByWorldCoords(i, k);
   }

   public static LOTRConquestZone getZoneByGridCoords(int i, int k) {
      if (i >= 0 && i < MathHelper.func_76123_f((float)LOTRGenLayerWorld.imageWidth / (float)MAP_GRID_SCALE)) {
         if (k >= 0 && k < MathHelper.func_76123_f((float)LOTRGenLayerWorld.imageHeight / (float)MAP_GRID_SCALE)) {
            LOTRConquestGrid.GridCoordPair key = new LOTRConquestGrid.GridCoordPair(i, k);
            LOTRConquestZone zone = (LOTRConquestZone)zoneMap.get(key);
            if (zone == null) {
               File zoneDat = getZoneDat(key);
               zone = loadZoneFromFile(zoneDat);
               if (zone == null) {
                  zone = new LOTRConquestZone(i, k);
               }

               zoneMap.put(key, zone);
            }

            return zone;
         } else {
            return dummyZone;
         }
      } else {
         return dummyZone;
      }
   }

   public static int[] getMinCoordsOnMap(LOTRConquestZone zone) {
      return new int[]{gridToMapCoord(zone.gridX), gridToMapCoord(zone.gridZ)};
   }

   public static int[] getMaxCoordsOnMap(LOTRConquestZone zone) {
      return new int[]{gridToMapCoord(zone.gridX + 1), gridToMapCoord(zone.gridZ + 1)};
   }

   public static boolean conquestEnabled(World world) {
      return LOTRConfig.enableConquest && world.func_72912_H().func_76067_t() != LOTRMod.worldTypeMiddleEarthClassic;
   }

   public static float onConquestKill(EntityPlayer entityplayer, LOTRFaction pledgeFaction, LOTRFaction enemyFaction, float alignBonus) {
      World world = entityplayer.field_70170_p;
      if (!world.field_72995_K && conquestEnabled(world) && LOTRLevelData.getData(entityplayer).getEnableConquestKills() && entityplayer.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) {
         LOTRConquestZone centralZone = getZoneByEntityCoords(entityplayer);
         float conqAmount = alignBonus * LOTRLevelData.getConquestRate();
         float conqGain = conqAmount * getConquestGainRate(entityplayer);
         float conqCleanse = conqAmount * getConquestGainRate(entityplayer);
         return doRadialConquest(world, centralZone, entityplayer, pledgeFaction, enemyFaction, conqGain, conqCleanse);
      } else {
         return 0.0F;
      }
   }

   private static float getConquestGainRate(EntityPlayer entityplayer) {
      int i = MathHelper.func_76128_c(entityplayer.field_70165_t);
      int k = MathHelper.func_76128_c(entityplayer.field_70161_v);
      BiomeGenBase bgb = entityplayer.field_70170_p.func_72807_a(i, k);
      if (bgb instanceof LOTRBiome) {
         LOTRBiome biome = (LOTRBiome)bgb;
         return biome.npcSpawnList.conquestGainRate;
      } else {
         return 1.0F;
      }
   }

   public static float doRadialConquest(World world, LOTRConquestZone centralZone, EntityPlayer killingPlayer, LOTRFaction pledgeFaction, LOTRFaction enemyFaction, float conqGain, float conqCleanse) {
      if (centralZone.isDummyZone) {
         return 0.0F;
      } else {
         int range = true;
         float radius = 3.5F;
         float centralConqBonus = 0.0F;

         for(int i1 = -3; i1 <= 3; ++i1) {
            for(int k1 = -3; k1 <= 3; ++k1) {
               int distSq = i1 * i1 + k1 * k1;
               if ((float)distSq <= 12.25F) {
                  int zoneX = centralZone.gridX + i1;
                  int zoneZ = centralZone.gridZ + k1;
                  float dist = MathHelper.func_76129_c((float)distSq);
                  float frac = 1.0F - dist / 3.5F;
                  float conqGainHere = frac * conqGain;
                  float conqCleanseHere = frac * conqCleanse;
                  LOTRConquestZone zone = getZoneByGridCoords(zoneX, zoneZ);
                  if (!zone.isDummyZone) {
                     boolean doneEnemyCleansing = false;
                     float prevZoneConq;
                     float newZoneConq;
                     if (enemyFaction != null) {
                        prevZoneConq = zone.getConquestStrength(enemyFaction, world);
                        if (prevZoneConq > 0.0F) {
                           zone.addConquestStrength(enemyFaction, -conqCleanseHere, world);
                           newZoneConq = zone.getConquestStrength(enemyFaction, world);
                           if (zone == centralZone) {
                              centralConqBonus = newZoneConq - prevZoneConq;
                           }

                           if (killingPlayer != null) {
                              checkNotifyConquest(zone, killingPlayer, enemyFaction, newZoneConq, prevZoneConq, true);
                           }

                           doneEnemyCleansing = true;
                        }
                     }

                     if (!doneEnemyCleansing && pledgeFaction != null) {
                        prevZoneConq = zone.getConquestStrength(pledgeFaction, world);
                        zone.addConquestStrength(pledgeFaction, conqGainHere, world);
                        newZoneConq = zone.getConquestStrength(pledgeFaction, world);
                        if (zone == centralZone) {
                           centralConqBonus = newZoneConq - prevZoneConq;
                        }

                        if (killingPlayer != null) {
                           checkNotifyConquest(zone, killingPlayer, pledgeFaction, newZoneConq, prevZoneConq, false);
                        }
                     }
                  }
               }
            }
         }

         return centralConqBonus;
      }
   }

   private static void checkNotifyConquest(LOTRConquestZone zone, EntityPlayer originPlayer, LOTRFaction faction, float newConq, float prevConq, boolean isCleansing) {
      float notifInterval = 50.0F;
      double notifRange = 200.0D;
      if (MathHelper.func_76128_c((double)(newConq / 50.0F)) != MathHelper.func_76128_c((double)(prevConq / 50.0F)) || newConq == 0.0F && prevConq != newConq) {
         World world = originPlayer.field_70170_p;
         List playerEntities = world.field_73010_i;
         Iterator var11 = playerEntities.iterator();

         while(true) {
            EntityPlayerMP player;
            LOTRPlayerData pd;
            do {
               do {
                  if (!var11.hasNext()) {
                     return;
                  }

                  Object obj = var11.next();
                  player = (EntityPlayerMP)obj;
                  pd = LOTRLevelData.getData((EntityPlayer)player);
               } while(player.func_70068_e(originPlayer) > 40000.0D);
            } while(getZoneByEntityCoords(player) != zone);

            boolean playerApplicable = false;
            if (!isCleansing) {
               playerApplicable = pd.isPledgedTo(faction);
            } else {
               LOTRFaction pledgeFac = pd.getPledgeFaction();
               playerApplicable = pledgeFac != null && pledgeFac.isBadRelation(faction);
            }

            if (playerApplicable) {
               LOTRPacketConquestNotification pkt = new LOTRPacketConquestNotification(faction, newConq, isCleansing);
               LOTRPacketHandler.networkWrapper.sendTo(pkt, player);
            }
         }
      }
   }

   public static void updateZones(World world) {
      if (conquestEnabled(world)) {
         MinecraftServer srv = MinecraftServer.func_71276_C();
         if (srv != null) {
            int tick = srv.func_71259_af();
            int interval = 6000;
            Iterator var4 = zoneMap.values().iterator();

            while(var4.hasNext()) {
               LOTRConquestZone zone = (LOTRConquestZone)var4.next();
               if (!zone.isEmpty() && IntMath.mod(tick, interval) == IntMath.mod(zone.hashCode(), interval)) {
                  zone.checkForEmptiness(world);
               }
            }
         }
      }

   }

   public static LOTRConquestGrid.ConquestViewableQuery canPlayerViewConquest(EntityPlayer entityplayer, LOTRFaction fac) {
      LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
      LOTRFaction pledged = pd.getPledgeFaction();
      if (pledged != null) {
         if (fac == pledged) {
            return LOTRConquestGrid.ConquestViewableQuery.canView();
         } else {
            float align = pd.getAlignment(pledged);
            LOTRFactionRank pledgeRank = pledged.getPledgeRank();
            if (!fac.isAlly(pledged) && !fac.isBadRelation(pledged)) {
               LOTRFactionRank higherRank = pledged.getRankNAbove(pledgeRank, 2);
               return align >= higherRank.alignment ? LOTRConquestGrid.ConquestViewableQuery.canView() : new LOTRConquestGrid.ConquestViewableQuery(LOTRConquestGrid.ConquestViewable.NEED_RANK, higherRank);
            } else {
               return LOTRConquestGrid.ConquestViewableQuery.canView();
            }
         }
      } else {
         return new LOTRConquestGrid.ConquestViewableQuery(LOTRConquestGrid.ConquestViewable.UNPLEDGED, (LOTRFactionRank)null);
      }
   }

   public static void sendConquestGridTo(EntityPlayerMP entityplayer, LOTRFaction fac) {
      LOTRPacketConquestGrid pkt = new LOTRPacketConquestGrid(fac, zoneMap.values(), entityplayer.field_70170_p);
      LOTRPacketHandler.networkWrapper.sendTo(pkt, entityplayer);
      LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
      if (fac == pd.getPledgeFaction()) {
         pd.addAchievement(LOTRAchievement.factionConquest);
      }

   }

   public static LOTRConquestGrid.ConquestEffective getConquestEffectIn(World world, LOTRConquestZone zone, LOTRFaction theFaction) {
      if (!LOTRGenLayerWorld.loadedBiomeImage()) {
         new LOTRGenLayerWorld();
      }

      LOTRConquestGrid.GridCoordPair gridCoords = LOTRConquestGrid.GridCoordPair.forZone(zone);
      List cachedFacs = (List)cachedZoneFactions.get(gridCoords);
      if (cachedFacs == null) {
         cachedFacs = new ArrayList();
         List includedBiomes = new ArrayList();
         int[] mapMin = getMinCoordsOnMap(zone);
         int[] mapMax = getMaxCoordsOnMap(zone);
         int mapXMin = mapMin[0];
         int mapXMax = mapMax[0];
         int mapZMin = mapMin[1];
         int mapZMax = mapMax[1];

         for(int i = mapXMin; i < mapXMax; ++i) {
            for(int k = mapZMin; k < mapZMax; ++k) {
               LOTRBiome biome = LOTRGenLayerWorld.getBiomeOrOcean(i, k);
               if (!includedBiomes.contains(biome)) {
                  includedBiomes.add(biome);
               }
            }
         }

         Iterator var18 = LOTRFaction.getPlayableAlignmentFactions().iterator();

         while(true) {
            while(var18.hasNext()) {
               LOTRFaction fac = (LOTRFaction)var18.next();
               Iterator var20 = includedBiomes.iterator();

               while(var20.hasNext()) {
                  LOTRBiome biome = (LOTRBiome)var20.next();
                  if (biome.npcSpawnList.isFactionPresent(world, fac)) {
                     ((List)cachedFacs).add(fac);
                     break;
                  }
               }
            }

            cachedZoneFactions.put(gridCoords, cachedFacs);
            break;
         }
      }

      if (((List)cachedFacs).contains(theFaction)) {
         return LOTRConquestGrid.ConquestEffective.EFFECTIVE;
      } else {
         Iterator var16 = theFaction.getConquestBoostRelations().iterator();

         LOTRFaction allyFac;
         do {
            if (!var16.hasNext()) {
               return LOTRConquestGrid.ConquestEffective.NO_EFFECT;
            }

            allyFac = (LOTRFaction)var16.next();
         } while(!((List)cachedFacs).contains(allyFac));

         return LOTRConquestGrid.ConquestEffective.ALLY_BOOST;
      }
   }

   private static File getConquestDir() {
      File dir = new File(LOTRLevelData.getOrCreateLOTRDir(), "conquest_zones");
      if (!dir.exists()) {
         dir.mkdirs();
      }

      return dir;
   }

   private static File getZoneDat(LOTRConquestZone zone) {
      LOTRConquestGrid.GridCoordPair key = LOTRConquestGrid.GridCoordPair.forZone(zone);
      return getZoneDat(key);
   }

   private static File getZoneDat(LOTRConquestGrid.GridCoordPair key) {
      return new File(getConquestDir(), key.gridX + "." + key.gridZ + ".dat");
   }

   public static void markZoneDirty(LOTRConquestZone zone) {
      LOTRConquestGrid.GridCoordPair key = LOTRConquestGrid.GridCoordPair.forZone(zone);
      if (zoneMap.containsKey(key)) {
         dirtyZones.add(key);
      }

   }

   public static boolean anyChangedZones() {
      return !dirtyZones.isEmpty();
   }

   public static void saveChangedZones() {
      try {
         Set removes = new HashSet();
         Iterator var1 = dirtyZones.iterator();

         LOTRConquestGrid.GridCoordPair key;
         while(var1.hasNext()) {
            key = (LOTRConquestGrid.GridCoordPair)var1.next();
            LOTRConquestZone zone = (LOTRConquestZone)zoneMap.get(key);
            if (zone != null) {
               saveZoneToFile(zone);
               if (zone.isEmpty()) {
                  removes.add(key);
               }
            }
         }

         dirtyZones.clear();
         var1 = removes.iterator();

         while(var1.hasNext()) {
            key = (LOTRConquestGrid.GridCoordPair)var1.next();
            zoneMap.remove(key);
         }
      } catch (Exception var4) {
         FMLLog.severe("Error saving LOTR conquest zones", new Object[0]);
         var4.printStackTrace();
      }

   }

   public static void loadAllZones() {
      try {
         zoneMap.clear();
         dirtyZones.clear();
         File dir = getConquestDir();
         if (dir.exists()) {
            File[] subfiles = dir.listFiles();
            File[] var2 = subfiles;
            int var3 = subfiles.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               File zoneDat = var2[var4];
               if (!zoneDat.isDirectory() && zoneDat.getName().endsWith(".dat")) {
                  LOTRConquestZone zone = loadZoneFromFile(zoneDat);
                  if (zone != null) {
                     LOTRConquestGrid.GridCoordPair key = LOTRConquestGrid.GridCoordPair.forZone(zone);
                     zoneMap.put(key, zone);
                  }
               }
            }
         }

         needsLoad = false;
         FMLLog.info("LOTR: Loaded %s conquest zones", new Object[]{zoneMap.size()});
      } catch (Exception var8) {
         FMLLog.severe("Error loading LOTR conquest zones", new Object[0]);
         var8.printStackTrace();
      }

   }

   private static LOTRConquestZone loadZoneFromFile(File zoneDat) {
      try {
         NBTTagCompound nbt = LOTRLevelData.loadNBTFromFile(zoneDat);
         if (nbt.func_82582_d()) {
            return null;
         } else {
            LOTRConquestZone zone = LOTRConquestZone.readFromNBT(nbt);
            return zone.isEmpty() ? null : zone;
         }
      } catch (Exception var3) {
         FMLLog.severe("Error loading LOTR conquest zone from %s", new Object[]{zoneDat.getName()});
         var3.printStackTrace();
         return null;
      }
   }

   public static void saveZoneToFile(LOTRConquestZone zone) {
      File zoneDat = getZoneDat(zone);

      try {
         if (zone.isEmpty()) {
            zoneDat.delete();
         } else {
            NBTTagCompound nbt = new NBTTagCompound();
            zone.writeToNBT(nbt);
            LOTRLevelData.saveNBTToFile(zoneDat, nbt);
         }
      } catch (Exception var3) {
         FMLLog.severe("Error saving LOTR conquest zone to %s", new Object[]{zoneDat.getName()});
         var3.printStackTrace();
      }

   }

   public static class GridCoordPair {
      public final int gridX;
      public final int gridZ;

      public GridCoordPair(int i, int k) {
         this.gridX = i;
         this.gridZ = k;
      }

      public static LOTRConquestGrid.GridCoordPair forZone(LOTRConquestZone zone) {
         return new LOTRConquestGrid.GridCoordPair(zone.gridX, zone.gridZ);
      }

      public int hashCode() {
         int i = 1664525 * this.gridX + 1013904223;
         int j = 1664525 * (this.gridZ ^ -559038737) + 1013904223;
         return i ^ j;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof LOTRConquestGrid.GridCoordPair)) {
            return false;
         } else {
            LOTRConquestGrid.GridCoordPair otherPair = (LOTRConquestGrid.GridCoordPair)other;
            return this.gridX == otherPair.gridX && this.gridZ == otherPair.gridZ;
         }
      }
   }

   public static enum ConquestEffective {
      EFFECTIVE,
      ALLY_BOOST,
      NO_EFFECT;
   }

   public static class ConquestViewableQuery {
      public final LOTRConquestGrid.ConquestViewable result;
      public final LOTRFactionRank needRank;

      public ConquestViewableQuery(LOTRConquestGrid.ConquestViewable res, LOTRFactionRank rank) {
         this.result = res;
         this.needRank = rank;
      }

      public static LOTRConquestGrid.ConquestViewableQuery canView() {
         return new LOTRConquestGrid.ConquestViewableQuery(LOTRConquestGrid.ConquestViewable.CAN_VIEW, (LOTRFactionRank)null);
      }
   }

   public static enum ConquestViewable {
      UNPLEDGED,
      CAN_VIEW,
      NEED_RANK;
   }
}
