package lotr.common.world.spawning;

import cpw.mods.fml.common.eventhandler.Event.Result;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lotr.common.LOTRConfig;
import lotr.common.LOTRSpawnDamping;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeEventFactory;

public class LOTRSpawnerNPCs {
   private static final int chunkRange = 7;
   public static final int expectedChunks = 196;
   private static Set eligibleSpawnChunks = new HashSet();
   private static Map ticksSinceCycle = new HashMap();

   public static ChunkPosition getRandomSpawningPointInChunk(World world, ChunkCoordIntPair chunkCoords) {
      int i = chunkCoords.field_77276_a;
      int k = chunkCoords.field_77275_b;
      return getRandomSpawningPointInChunk(world, i, k);
   }

   public static ChunkPosition getRandomSpawningPointInChunk(World world, int i, int k) {
      if (!world.func_72863_F().func_73149_a(i, k)) {
         return null;
      } else {
         Chunk chunk = world.func_72964_e(i, k);
         int i1 = i * 16 + world.field_73012_v.nextInt(16);
         int k1 = k * 16 + world.field_73012_v.nextInt(16);
         int j = world.field_73012_v.nextInt(chunk == null ? world.func_72940_L() : chunk.func_76625_h() + 16 - 1);
         return new ChunkPosition(i1, j, k1);
      }
   }

   public static void getSpawnableChunks(World world, Set set) {
      set.clear();

      for(int l = 0; l < world.field_73010_i.size(); ++l) {
         EntityPlayer entityplayer = (EntityPlayer)world.field_73010_i.get(l);
         int i = MathHelper.func_76128_c(entityplayer.field_70165_t / 16.0D);
         int k = MathHelper.func_76128_c(entityplayer.field_70161_v / 16.0D);

         for(int i1 = -7; i1 <= 7; ++i1) {
            for(int k1 = -7; k1 <= 7; ++k1) {
               ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i + i1, k + k1);
               set.add(chunkcoordintpair);
            }
         }
      }

   }

   public static void getSpawnableChunksWithPlayerInRange(World world, Set set, int range) {
      getSpawnableChunks(world, set);
      List validPlayers = new ArrayList();
      Iterator var4 = world.field_73010_i.iterator();

      while(var4.hasNext()) {
         Object obj = var4.next();
         EntityPlayer entityplayer = (EntityPlayer)obj;
         if (!entityplayer.field_71075_bZ.field_75098_d) {
            validPlayers.add(entityplayer);
         }
      }

      int height = world.func_72800_K();
      Set removes = new HashSet();
      Iterator var16 = set.iterator();

      while(var16.hasNext()) {
         ChunkCoordIntPair chunkCoords = (ChunkCoordIntPair)var16.next();
         int i = chunkCoords.func_77273_a();
         int k = chunkCoords.func_77274_b();
         AxisAlignedBB playerCheckBox = AxisAlignedBB.func_72330_a((double)(i - range), 0.0D, (double)(k - range), (double)(i + range), (double)height, (double)(k + range));
         boolean anyPlayers = false;
         Iterator var12 = validPlayers.iterator();

         while(var12.hasNext()) {
            EntityPlayer entityplayer = (EntityPlayer)var12.next();
            if (entityplayer.field_70121_D.func_72326_a(playerCheckBox)) {
               anyPlayers = true;
               break;
            }
         }

         if (!anyPlayers) {
            removes.add(chunkCoords);
         }
      }

      set.removeAll(removes);
   }

   public static List shuffle(Set set) {
      List list = new ArrayList(set);
      Collections.shuffle(list);
      return list;
   }

   public static void performSpawning(World world) {
      int interval = LOTRConfig.mobSpawnInterval;
      int totalSpawnCount;
      if (interval > 0) {
         int ticks = 0;
         totalSpawnCount = world.field_73011_w.field_76574_g;
         if (ticksSinceCycle.containsKey(totalSpawnCount)) {
            ticks = (Integer)ticksSinceCycle.get(totalSpawnCount);
         }

         --ticks;
         ticksSinceCycle.put(totalSpawnCount, ticks);
         if (ticks > 0) {
            return;
         }

         ticksSinceCycle.put(totalSpawnCount, interval);
      }

      getSpawnableChunks(world, eligibleSpawnChunks);
      ChunkCoordinates spawnPoint = world.func_72861_E();
      totalSpawnCount = countNPCs(world);
      int maxSpawnCount = LOTRSpawnDamping.getNPCSpawnCap(world) * eligibleSpawnChunks.size() / 196;
      if (totalSpawnCount <= maxSpawnCount) {
         int cycles = Math.max(1, interval);

         label130:
         for(int c = 0; c < cycles; ++c) {
            List shuffled = shuffle(eligibleSpawnChunks);
            Iterator var8 = shuffled.iterator();

            while(true) {
               int i;
               int j;
               int k;
               do {
                  do {
                     ChunkPosition chunkposition;
                     do {
                        if (!var8.hasNext()) {
                           continue label130;
                        }

                        ChunkCoordIntPair chunkCoords = (ChunkCoordIntPair)var8.next();
                        chunkposition = getRandomSpawningPointInChunk(world, chunkCoords);
                     } while(chunkposition == null);

                     i = chunkposition.field_151329_a;
                     j = chunkposition.field_151327_b;
                     k = chunkposition.field_151328_c;
                  } while(world.func_147439_a(i, j, k).func_149721_r());
               } while(world.func_147439_a(i, j, k).func_149688_o() != Material.field_151579_a);

               int groups = 3;

               for(int l = 0; l < groups; ++l) {
                  int i1 = i;
                  int j1 = j;
                  int k1 = k;
                  int range = 5;
                  int yRange = 0;
                  int rangeP1 = range + 1;
                  int yRangeP1 = yRange + 1;
                  LOTRSpawnEntry.Instance spawnEntryInstance = getRandomSpawnListEntry(world, i, j, k);
                  if (spawnEntryInstance != null) {
                     LOTRSpawnEntry spawnEntry = spawnEntryInstance.spawnEntry;
                     boolean isConquestSpawn = spawnEntryInstance.isConquestSpawn;
                     int spawnCount = MathHelper.func_76136_a(world.field_73012_v, spawnEntry.field_76301_c, spawnEntry.field_76299_d);
                     int chance = spawnEntryInstance.spawnChance;
                     if (chance == 0 || world.field_73012_v.nextInt(chance) == 0) {
                        IEntityLivingData entityData = null;
                        int spawned = 0;
                        int attempts = spawnCount * 8;

                        for(int a = 0; a < attempts; ++a) {
                           i1 += world.field_73012_v.nextInt(rangeP1) - world.field_73012_v.nextInt(rangeP1);
                           j1 += world.field_73012_v.nextInt(yRangeP1) - world.field_73012_v.nextInt(yRangeP1);
                           k1 += world.field_73012_v.nextInt(rangeP1) - world.field_73012_v.nextInt(rangeP1);
                           if (world.func_72899_e(i1, j1, k1) && canNPCSpawnAtLocation(world, i1, j1, k1)) {
                              float f = (float)i1 + 0.5F;
                              float f1 = (float)j1;
                              float f2 = (float)k1 + 0.5F;
                              if (world.func_72977_a((double)f, (double)f1, (double)f2, 24.0D) == null) {
                                 float f3 = f - (float)spawnPoint.field_71574_a;
                                 float f4 = f1 - (float)spawnPoint.field_71572_b;
                                 float f5 = f2 - (float)spawnPoint.field_71573_c;
                                 float distSq = f3 * f3 + f4 * f4 + f5 * f5;
                                 if (distSq >= 576.0F) {
                                    EntityLiving entity;
                                    try {
                                       entity = (EntityLiving)spawnEntry.field_76300_b.getConstructor(World.class).newInstance(world);
                                    } catch (Exception var42) {
                                       var42.printStackTrace();
                                       return;
                                    }

                                    entity.func_70012_b((double)f, (double)f1, (double)f2, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
                                    if (entity instanceof LOTREntityNPC && isConquestSpawn) {
                                       LOTREntityNPC npc = (LOTREntityNPC)entity;
                                       npc.setConquestSpawning(true);
                                    }

                                    Result canSpawn = ForgeEventFactory.canEntitySpawn(entity, world, f, f1, f2);
                                    if (canSpawn == Result.ALLOW || canSpawn == Result.DEFAULT && entity.func_70601_bi()) {
                                       world.func_72838_d(entity);
                                       if (entity instanceof LOTREntityNPC) {
                                          LOTREntityNPC npc = (LOTREntityNPC)entity;
                                          npc.isNPCPersistent = false;
                                          npc.setShouldTraderRespawn(false);
                                          npc.setConquestSpawning(false);
                                       }

                                       if (!ForgeEventFactory.doSpecialSpawn(entity, world, f, f1, f2)) {
                                          entityData = entity.func_110161_a(entityData);
                                       }

                                       totalSpawnCount += entity instanceof LOTREntityNPC ? ((LOTREntityNPC)entity).getSpawnCountValue() : 1;
                                       if (c > 0 && totalSpawnCount > maxSpawnCount) {
                                          return;
                                       }

                                       ++spawned;
                                       if (spawned >= spawnCount) {
                                          break;
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

   private static int countNPCs(World world) {
      int count = 0;

      for(int i = 0; i < world.field_72996_f.size(); ++i) {
         Entity entity = (Entity)world.field_72996_f.get(i);
         if (entity instanceof LOTREntityNPC) {
            int spawnCountValue = ((LOTREntityNPC)entity).getSpawnCountValue();
            count += spawnCountValue;
         }
      }

      return count;
   }

   private static boolean canNPCSpawnAtLocation(World world, int i, int j, int k) {
      if (!World.func_147466_a(world, i, j - 1, k)) {
         return false;
      } else {
         Block block = world.func_147439_a(i, j - 1, k);
         world.func_72805_g(i, j - 1, k);
         boolean spawnBlock = block.canCreatureSpawn(EnumCreatureType.monster, world, i, j - 1, k);
         return spawnBlock && block != Blocks.field_150357_h && !world.func_147439_a(i, j, k).func_149721_r() && !world.func_147439_a(i, j, k).func_149688_o().func_76224_d() && !world.func_147439_a(i, j + 1, k).func_149721_r();
      }
   }

   private static LOTRSpawnEntry.Instance getRandomSpawnListEntry(World world, int i, int j, int k) {
      LOTRBiomeSpawnList spawnlist = null;
      BiomeGenBase biome = world.func_72807_a(i, k);
      if (biome instanceof LOTRBiome && world.field_73011_w instanceof LOTRWorldProvider) {
         LOTRBiome lotrbiome = (LOTRBiome)biome;
         LOTRWorldChunkManager worldChunkMgr = (LOTRWorldChunkManager)world.field_73011_w.field_76578_c;
         LOTRBiomeVariant variant = worldChunkMgr.getBiomeVariantAt(i, k);
         spawnlist = lotrbiome.getNPCSpawnList(world, world.field_73012_v, i, j, k, variant);
      }

      return spawnlist != null ? spawnlist.getRandomSpawnEntry(world.field_73012_v, world, i, j, k) : null;
   }
}
