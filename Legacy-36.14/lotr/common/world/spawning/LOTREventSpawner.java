package lotr.common.world.spawning;

import cpw.mods.fml.common.eventhandler.Event.Result;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lotr.common.LOTRConfig;
import lotr.common.LOTRGreyWandererTracker;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityInvasionSpawner;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.ForgeEventFactory;

public class LOTREventSpawner {
   private static Set eligibleSpawnChunks = new HashSet();
   private static final int playerRange = 32;
   private static final int expectedChunks = 16;
   public static List travellingTraders = new ArrayList();
   private static Set traderClasses = new HashSet();

   public static void createTraderSpawner(Class entityClass) {
      if (!traderClasses.contains(entityClass)) {
         traderClasses.add(entityClass);
         travellingTraders.add(new LOTRTravellingTraderSpawner(entityClass));
      }

   }

   public static void performSpawning(World world) {
      Iterator var1 = travellingTraders.iterator();

      while(var1.hasNext()) {
         LOTRTravellingTraderSpawner trader = (LOTRTravellingTraderSpawner)var1.next();
         trader.performSpawning(world);
      }

      if (world.func_82737_E() % 20L == 0L) {
         LOTRSpawnerNPCs.getSpawnableChunksWithPlayerInRange(world, eligibleSpawnChunks, 32);
         List shuffled = LOTRSpawnerNPCs.shuffle(eligibleSpawnChunks);
         if (LOTRConfig.enableBandits && world.field_73013_u != EnumDifficulty.PEACEFUL) {
            spawnBandits(world, shuffled);
         }

         if (LOTRConfig.enableInvasions && world.field_73013_u != EnumDifficulty.PEACEFUL) {
            spawnInvasions(world, shuffled);
         }
      }

      LOTRGollumSpawner.performSpawning(world);
      LOTRGreyWandererTracker.performSpawning(world);
   }

   private static void spawnInvasions(World world, List spawnChunks) {
      Random rand = world.field_73012_v;
      Iterator var3 = spawnChunks.iterator();

      while(true) {
         label70:
         while(true) {
            int i;
            int k;
            BiomeGenBase biome;
            do {
               ChunkPosition chunkposition;
               do {
                  if (!var3.hasNext()) {
                     return;
                  }

                  ChunkCoordIntPair chunkCoords = (ChunkCoordIntPair)var3.next();
                  chunkposition = LOTRSpawnerNPCs.getRandomSpawningPointInChunk(world, chunkCoords);
               } while(chunkposition == null);

               i = chunkposition.field_151329_a;
               k = chunkposition.field_151328_c;
               biome = world.func_72807_a(i, k);
            } while(!(biome instanceof LOTRBiome));

            LOTRBiomeInvasionSpawns invasionSpawns = ((LOTRBiome)biome).invasionSpawns;
            LOTREventSpawner.EventChance[] var10 = LOTREventSpawner.EventChance.values();
            int var11 = var10.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               LOTREventSpawner.EventChance invChance = var10[var12];
               List invList = invasionSpawns.getInvasionsForChance(invChance);
               if (!invList.isEmpty()) {
                  final LOTRInvasions invasionType = (LOTRInvasions)invList.get(rand.nextInt(invList.size()));
                  double chance = invChance.chancesPerSecondPerChunk[16];
                  if (!world.func_72935_r() && LOTRWorldProvider.isLunarEclipse()) {
                     chance *= 5.0D;
                  }

                  if (rand.nextDouble() < chance) {
                     int range = 48;
                     AxisAlignedBB playerCheckBox = AxisAlignedBB.func_72330_a((double)(i - range), 0.0D, (double)(k - range), (double)(i + range), (double)world.func_72800_K(), (double)(k + range));
                     List nearbyPlayers = world.func_82733_a(EntityPlayer.class, playerCheckBox, new IEntitySelector() {
                        public boolean func_82704_a(Entity entity) {
                           if (entity instanceof EntityPlayer) {
                              EntityPlayer entityplayer = (EntityPlayer)entity;
                              if (entityplayer.func_70089_S() && !entityplayer.field_71075_bZ.field_75098_d) {
                                 return LOTRLevelData.getData(entityplayer).getAlignment(invasionType.invasionFaction) < 0.0F;
                              }
                           }

                           return false;
                        }
                     });
                     if (!nearbyPlayers.isEmpty()) {
                        for(int attempts = 0; attempts < 16; ++attempts) {
                           int i1 = i + MathHelper.func_76136_a(rand, -32, 32);
                           int k1 = k + MathHelper.func_76136_a(rand, -32, 32);
                           int j1 = world.func_72976_f(i1, k1);
                           if (j1 > 60) {
                              Block block = world.func_147439_a(i1, j1 - 1, k1);
                              if ((block == biome.field_76752_A || block == biome.field_76753_B) && !world.func_147439_a(i1, j1, k1).func_149721_r() && !world.func_147439_a(i1, j1 + 1, k1).func_149721_r()) {
                                 j1 += 3 + rand.nextInt(3);
                                 LOTREntityInvasionSpawner invasion = new LOTREntityInvasionSpawner(world);
                                 invasion.setInvasionType(invasionType);
                                 invasion.func_70012_b((double)i1 + 0.5D, (double)j1, (double)k1 + 0.5D, 0.0F, 0.0F);
                                 if (invasion.canInvasionSpawnHere()) {
                                    world.func_72838_d(invasion);
                                    invasion.selectAppropriateBonusFactions();
                                    invasion.startInvasion();
                                    continue label70;
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

   private static void spawnBandits(World world, List spawnChunks) {
      Random rand = world.field_73012_v;
      Iterator var3 = spawnChunks.iterator();

      while(true) {
         int i;
         int k;
         BiomeGenBase biome;
         Class banditClass;
         List nearbyPlayers;
         do {
            double chance;
            do {
               do {
                  do {
                     ChunkPosition chunkposition;
                     do {
                        if (!var3.hasNext()) {
                           return;
                        }

                        ChunkCoordIntPair chunkCoords = (ChunkCoordIntPair)var3.next();
                        chunkposition = LOTRSpawnerNPCs.getRandomSpawningPointInChunk(world, chunkCoords);
                     } while(chunkposition == null);

                     i = chunkposition.field_151329_a;
                     k = chunkposition.field_151328_c;
                     biome = world.func_72807_a(i, k);
                  } while(!(biome instanceof LOTRBiome));

                  LOTRBiome lotrbiome = (LOTRBiome)biome;
                  banditClass = lotrbiome.getBanditEntityClass();
                  chance = lotrbiome.getBanditChance().chancesPerSecondPerChunk[16];
               } while(chance <= 0.0D);
            } while(world.field_73012_v.nextDouble() >= chance);

            int range = 48;
            AxisAlignedBB playerCheckBox = AxisAlignedBB.func_72330_a((double)(i - range), 0.0D, (double)(k - range), (double)(i + range), (double)world.func_72800_K(), (double)(k + range));
            nearbyPlayers = world.func_82733_a(EntityPlayer.class, playerCheckBox, LOTRMod.selectNonCreativePlayers());
         } while(nearbyPlayers.isEmpty());

         int banditsSpawned = 0;
         int maxBandits = MathHelper.func_76136_a(world.field_73012_v, 1, 4);

         for(int attempts = 0; attempts < 32; ++attempts) {
            int i1 = i + MathHelper.func_76136_a(rand, -32, 32);
            int k1 = k + MathHelper.func_76136_a(rand, -32, 32);
            int j1 = world.func_72976_f(i1, k1);
            if (j1 > 60) {
               Block block = world.func_147439_a(i1, j1 - 1, k1);
               if ((block == biome.field_76752_A || block == biome.field_76753_B) && !world.func_147439_a(i1, j1, k1).func_149721_r() && !world.func_147439_a(i1, j1 + 1, k1).func_149721_r()) {
                  String entityName = LOTREntities.getStringFromClass(banditClass);
                  LOTREntityBandit bandit = (LOTREntityBandit)EntityList.func_75620_a(entityName, world);
                  if (bandit != null) {
                     bandit.func_70012_b((double)i1 + 0.5D, (double)j1, (double)k1 + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
                     Result canSpawn = ForgeEventFactory.canEntitySpawn(bandit, world, (float)bandit.field_70165_t, (float)bandit.field_70163_u, (float)bandit.field_70161_v);
                     if (canSpawn == Result.ALLOW || canSpawn == Result.DEFAULT && bandit.func_70601_bi()) {
                        bandit.func_110161_a((IEntityLivingData)null);
                        world.func_72838_d(bandit);
                        bandit.isNPCPersistent = false;
                        ++banditsSpawned;
                        if (banditsSpawned >= maxBandits) {
                           break;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static enum EventChance {
      NEVER(0.0F, 0),
      RARE(0.1F, 3600),
      UNCOMMON(0.3F, 3600),
      COMMON(0.9F, 3600),
      BANDIT_RARE(0.1F, 3600),
      BANDIT_UNCOMMON(0.3F, 3600),
      BANDIT_COMMON(0.8F, 3600);

      public final double chancePerSecond;
      public final double[] chancesPerSecondPerChunk;

      private EventChance(float prob, int s) {
         this.chancePerSecond = getChance((double)prob, s);
         this.chancesPerSecondPerChunk = new double[64];

         for(int i = 0; i < this.chancesPerSecondPerChunk.length; ++i) {
            this.chancesPerSecondPerChunk[i] = getChance(this.chancePerSecond, i);
         }

      }

      public static double getChance(double prob, int trials) {
         if (prob != 0.0D && trials != 0) {
            double d = 1.0D - prob;
            d = Math.pow(d, 1.0D / (double)trials);
            d = 1.0D - d;
            return d;
         } else {
            return 0.0D;
         }
      }
   }
}
