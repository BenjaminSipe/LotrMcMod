package lotr.common.world.spawning;

import cpw.mods.fml.common.eventhandler.Event.Result;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import lotr.common.LOTRConfig;
import lotr.common.LOTRSpawnDamping;
import lotr.common.entity.animal.LOTRAnimalSpawnConditions;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.event.ForgeEventFactory;

public class LOTRSpawnerAnimals {
   private static Set eligibleSpawnChunks = new HashSet();
   private static Map ticksSinceCycle = new HashMap();
   private static Map dimInfos = new HashMap();
   private static final int FAIL_THRESHOLD = 10;
   private static final int FAIL_BLOCKINGS = 100;

   private static LOTRSpawnerAnimals.TypeInfo forDimAndType(World world, EnumCreatureType type) {
      int dimID = world.field_73011_w.field_76574_g;
      LOTRSpawnerAnimals.DimInfo dimInfo = (LOTRSpawnerAnimals.DimInfo)dimInfos.get(dimID);
      if (dimInfo == null) {
         dimInfo = new LOTRSpawnerAnimals.DimInfo();
         dimInfos.put(dimID, dimInfo);
      }

      LOTRSpawnerAnimals.TypeInfo typeInfo = (LOTRSpawnerAnimals.TypeInfo)dimInfo.types.get(type);
      if (typeInfo == null) {
         typeInfo = new LOTRSpawnerAnimals.TypeInfo();
         dimInfo.types.put(type, typeInfo);
      }

      return typeInfo;
   }

   public static int performSpawning(WorldServer world, boolean hostiles, boolean peacefuls, boolean rareTick) {
      int interval = rareTick ? 0 : LOTRConfig.mobSpawnInterval;
      int totalSpawned;
      if (interval > 0) {
         totalSpawned = 0;
         int dimID = world.field_73011_w.field_76574_g;
         if (ticksSinceCycle.containsKey(dimID)) {
            totalSpawned = (Integer)ticksSinceCycle.get(dimID);
         }

         --totalSpawned;
         ticksSinceCycle.put(dimID, totalSpawned);
         if (totalSpawned > 0) {
            return 0;
         }

         ticksSinceCycle.put(dimID, interval);
      }

      if (!hostiles && !peacefuls) {
         return 0;
      } else {
         totalSpawned = 0;
         LOTRSpawnerNPCs.getSpawnableChunks(world, eligibleSpawnChunks);
         ChunkCoordinates spawnPoint = world.func_72861_E();
         EnumCreatureType[] var7 = EnumCreatureType.values();
         int var8 = var7.length;

         label165:
         for(int var9 = 0; var9 < var8; ++var9) {
            EnumCreatureType creatureType = var7[var9];
            LOTRSpawnerAnimals.TypeInfo typeInfo = forDimAndType(world, creatureType);
            boolean canSpawnType = true;
            if (creatureType.func_75599_d()) {
               canSpawnType = peacefuls;
            } else {
               canSpawnType = hostiles;
            }

            if (creatureType.func_82705_e()) {
               canSpawnType = rareTick;
            }

            if (canSpawnType) {
               int count = world.countEntities(creatureType, true);
               int maxCount = LOTRSpawnDamping.getCreatureSpawnCap(creatureType, world) * eligibleSpawnChunks.size() / 196;
               if (count <= maxCount) {
                  int cycles = Math.max(1, interval);

                  label162:
                  for(int c = 0; c < cycles; ++c) {
                     if (typeInfo.blockedCycles > 0) {
                        --typeInfo.blockedCycles;
                     } else {
                        int newlySpawned = 0;
                        List shuffled = LOTRSpawnerNPCs.shuffle(eligibleSpawnChunks);
                        Iterator var19 = shuffled.iterator();

                        label159:
                        while(true) {
                           int i;
                           int j;
                           int k;
                           do {
                              do {
                                 do {
                                    ChunkPosition chunkposition;
                                    do {
                                       if (!var19.hasNext()) {
                                          if (newlySpawned == 0) {
                                             ++typeInfo.failedCycles;
                                             if (typeInfo.failedCycles >= 10) {
                                                typeInfo.failedCycles = 0;
                                                typeInfo.blockedCycles = 100;
                                             }
                                          } else if (typeInfo.failedCycles > 0) {
                                             --typeInfo.failedCycles;
                                          }
                                          continue label162;
                                       }

                                       ChunkCoordIntPair chunkCoords = (ChunkCoordIntPair)var19.next();
                                       chunkposition = LOTRSpawnerNPCs.getRandomSpawningPointInChunk(world, chunkCoords);
                                    } while(chunkposition == null);

                                    i = chunkposition.field_151329_a;
                                    j = chunkposition.field_151327_b;
                                    k = chunkposition.field_151328_c;
                                 } while(world.func_73057_a(creatureType, i, j, k) == null);
                              } while(world.func_147439_a(i, j, k).func_149721_r());
                           } while(world.func_147439_a(i, j, k).func_149688_o() != creatureType.func_75600_c());

                           for(int groupsSpawned = 0; groupsSpawned < 3; ++groupsSpawned) {
                              int i1 = i;
                              int j1 = j;
                              int k1 = k;
                              int range = 6;
                              SpawnListEntry spawnEntry = null;
                              IEntityLivingData entityData = null;

                              for(int attempts = 0; attempts < 4; ++attempts) {
                                 i1 += world.field_73012_v.nextInt(range) - world.field_73012_v.nextInt(range);
                                 j1 += world.field_73012_v.nextInt(1) - world.field_73012_v.nextInt(1);
                                 k1 += world.field_73012_v.nextInt(range) - world.field_73012_v.nextInt(range);
                                 if (world.func_72899_e(i1, j1, k1) && SpawnerAnimals.func_77190_a(creatureType, world, i1, j1, k1)) {
                                    float f = (float)i1 + 0.5F;
                                    float f1 = (float)j1;
                                    float f2 = (float)k1 + 0.5F;
                                    if (world.func_72977_a((double)f, (double)f1, (double)f2, 24.0D) == null) {
                                       float f3 = f - (float)spawnPoint.field_71574_a;
                                       float f4 = f1 - (float)spawnPoint.field_71572_b;
                                       float f5 = f2 - (float)spawnPoint.field_71573_c;
                                       float distSq = f3 * f3 + f4 * f4 + f5 * f5;
                                       if (distSq >= 576.0F) {
                                          if (spawnEntry == null) {
                                             spawnEntry = world.func_73057_a(creatureType, i1, j1, k1);
                                             if (spawnEntry == null) {
                                                continue label159;
                                             }
                                          }

                                          EntityLiving entity;
                                          try {
                                             entity = (EntityLiving)spawnEntry.field_76300_b.getConstructor(World.class).newInstance(world);
                                          } catch (Exception var42) {
                                             var42.printStackTrace();
                                             return totalSpawned;
                                          }

                                          entity.func_70012_b((double)f, (double)f1, (double)f2, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
                                          Result canSpawn = ForgeEventFactory.canEntitySpawn(entity, world, f, f1, f2);
                                          if (canSpawn == Result.ALLOW || canSpawn == Result.DEFAULT && entity.func_70601_bi()) {
                                             ++totalSpawned;
                                             world.func_72838_d(entity);
                                             if (!ForgeEventFactory.doSpecialSpawn(entity, world, f, f1, f2)) {
                                                entityData = entity.func_110161_a(entityData);
                                             }

                                             ++newlySpawned;
                                             ++count;
                                             if (c > 0 && count > maxCount) {
                                                continue label165;
                                             }

                                             if (groupsSpawned >= ForgeEventFactory.getMaxSpawnPackSize(entity)) {
                                                continue label159;
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return totalSpawned;
      }
   }

   public static void worldGenSpawnAnimals(World world, LOTRBiome biome, LOTRBiomeVariant variant, int i, int k, Random rand) {
      int spawnRange = 16;
      int spawnFuzz = 5;
      List spawnList = biome.func_76747_a(EnumCreatureType.creature);
      if (!spawnList.isEmpty()) {
         while(rand.nextFloat() < biome.func_76741_f()) {
            SpawnListEntry spawnEntry = (SpawnListEntry)WeightedRandom.func_76271_a(world.field_73012_v, spawnList);
            int count = MathHelper.func_76136_a(rand, spawnEntry.field_76301_c, spawnEntry.field_76299_d);
            IEntityLivingData entityData = null;
            int packX = i + rand.nextInt(spawnRange);
            int packZ = k + rand.nextInt(spawnRange);
            int i1 = packX;
            int k1 = packZ;

            for(int l = 0; l < count; ++l) {
               int attempts = 4;
               boolean spawned = false;

               for(int a = 0; !spawned && a < attempts; ++a) {
                  int j1 = world.func_72825_h(i1, k1);
                  if (SpawnerAnimals.func_77190_a(EnumCreatureType.creature, world, i1, j1, k1)) {
                     float f = (float)i1 + 0.5F;
                     float f1 = (float)j1;
                     float f2 = (float)k1 + 0.5F;

                     EntityLiving entity;
                     try {
                        entity = (EntityLiving)spawnEntry.field_76300_b.getConstructor(World.class).newInstance(world);
                     } catch (Exception var27) {
                        var27.printStackTrace();
                        continue;
                     }

                     boolean canSpawn = true;
                     if (entity instanceof LOTRAnimalSpawnConditions) {
                        LOTRAnimalSpawnConditions conditions = (LOTRAnimalSpawnConditions)entity;
                        if (!conditions.canWorldGenSpawnAt(i1, j1, k1, biome, variant)) {
                           canSpawn = false;
                        }
                     }

                     if (canSpawn) {
                        entity.func_70012_b((double)f, (double)f1, (double)f2, rand.nextFloat() * 360.0F, 0.0F);
                        world.func_72838_d(entity);
                        entityData = entity.func_110161_a(entityData);
                        spawned = true;
                     }
                  }

                  i1 += rand.nextInt(spawnFuzz) - rand.nextInt(spawnFuzz);

                  for(k1 += rand.nextInt(spawnFuzz) - rand.nextInt(spawnFuzz); i1 < i || i1 >= i + spawnRange || k1 < k || k1 >= k + spawnRange; k1 = packZ + rand.nextInt(spawnFuzz) - rand.nextInt(spawnFuzz)) {
                     i1 = packX + rand.nextInt(spawnFuzz) - rand.nextInt(spawnFuzz);
                  }
               }
            }
         }
      }

   }

   private static class TypeInfo {
      public int failedCycles;
      public int blockedCycles;

      private TypeInfo() {
      }

      // $FF: synthetic method
      TypeInfo(Object x0) {
         this();
      }
   }

   private static class DimInfo {
      public Map types;

      private DimInfo() {
         this.types = new HashMap();
      }

      // $FF: synthetic method
      DimInfo(Object x0) {
         this();
      }
   }
}
