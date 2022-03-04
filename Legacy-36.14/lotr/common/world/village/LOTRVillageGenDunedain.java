package lotr.common.world.village;

import java.util.Random;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDunedain;
import lotr.common.entity.npc.LOTREntityRangerNorth;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenRangerHouse;
import lotr.common.world.structure2.LOTRWorldGenRangerLodge;
import lotr.common.world.structure2.LOTRWorldGenRangerSmithy;
import lotr.common.world.structure2.LOTRWorldGenRangerStables;
import lotr.common.world.structure2.LOTRWorldGenRangerVillageLight;
import lotr.common.world.structure2.LOTRWorldGenRangerVillagePalisade;
import lotr.common.world.structure2.LOTRWorldGenRangerWell;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRVillageGenDunedain extends LOTRVillageGen {
   public LOTRVillageGenDunedain(LOTRBiome biome, float f) {
      super(biome);
      this.gridScale = 12;
      this.gridRandomDisplace = 1;
      this.spawnChance = f;
      this.villageChunkRadius = 4;
   }

   protected LOTRVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
      return new LOTRVillageGenDunedain.Instance(this, world, i, k, random, loc);
   }

   public static class Instance extends LOTRVillageGen.AbstractInstance {
      public LOTRVillageGenDunedain.VillageType villageType;
      private int innerSize;
      private static final int innerSizeMin = 12;
      private static final int innerSizeMax = 20;
      private static final int roadWidth = 2;
      private static final int pathFuzz = 3;
      private static final int outerGap = 12;
      private boolean palisade;
      private static final int palisadeGap = 16;

      public Instance(LOTRVillageGenDunedain village, World world, int i, int k, Random random, LocationInfo loc) {
         super(village, world, i, k, random, loc);
      }

      protected void setupVillageProperties(Random random) {
         this.villageType = LOTRVillageGenDunedain.VillageType.VILLAGE;
         this.innerSize = MathHelper.func_76136_a(random, 12, 20);
         this.palisade = random.nextBoolean();
      }

      public boolean isFlat() {
         return false;
      }

      protected void addVillageStructures(Random random) {
         if (this.villageType == LOTRVillageGenDunedain.VillageType.VILLAGE) {
            this.setupVillage(random);
         }

      }

      private void setupVillage(Random random) {
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityDunedain.class);
               spawner.setCheckRanges(40, -12, 12, 30);
               spawner.setSpawnRanges(20, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityRangerNorth.class);
               spawner.setCheckRanges(40, -12, 12, 12);
               spawner.setSpawnRanges(20, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenRangerWell(false), 0, -2, 0, true);
         int lampX = 8;
         int[] var3 = new int[]{-lampX, lampX};
         int var4 = var3.length;

         int rPalisade;
         int rMax;
         int rSqMax;
         int i;
         for(int var5 = 0; var5 < var4; ++var5) {
            rPalisade = var3[var5];
            int[] var7 = new int[]{-lampX, lampX};
            rMax = var7.length;

            for(rSqMax = 0; rSqMax < rMax; ++rSqMax) {
               i = var7[rSqMax];
               this.addStructure(new LOTRWorldGenRangerVillageLight(false), rPalisade, i, 0);
            }
         }

         int houses = 20;
         float frac = 1.0F / (float)houses;
         float turn = 0.0F;

         while(true) {
            int k;
            int i;
            float sin;
            float cos;
            byte r;
            do {
               if (turn >= 1.0F) {
                  if (this.palisade) {
                     rPalisade = this.innerSize + 12 + 16;
                     int rSq = rPalisade * rPalisade;
                     rMax = rPalisade + 1;
                     rSqMax = rMax * rMax;

                     for(i = -rPalisade; i <= rPalisade; ++i) {
                        for(k = -rPalisade; k <= rPalisade; ++k) {
                           if (Math.abs(i) > 5 || k >= 0) {
                              i = i * i + k * k;
                              if (i >= rSq && i < rSqMax) {
                                 this.addStructure(new LOTRWorldGenRangerVillagePalisade(false), i, k, 0);
                              }
                           }
                        }
                     }
                  }

                  return;
               }

               turn += frac;
               float turnR = (float)Math.toRadians((double)(turn * 360.0F));
               sin = MathHelper.func_76126_a(turnR);
               cos = MathHelper.func_76134_b(turnR);
               r = 0;
               float turn8 = turn * 8.0F;
               if (turn8 >= 1.0F && turn8 < 3.0F) {
                  r = 0;
               } else if (turn8 >= 3.0F && turn8 < 5.0F) {
                  r = 1;
               } else if (turn8 >= 5.0F && turn8 < 7.0F) {
                  r = 2;
               } else if (turn8 >= 7.0F || turn8 < 1.0F) {
                  r = 3;
               }
            } while(this.palisade && sin < 0.0F && Math.abs(cos) <= 0.5F);

            int k;
            if (random.nextInt(3) != 0) {
               k = this.innerSize + 3;
               if (random.nextInt(3) == 0) {
                  k += 12;
               }

               i = Math.round((float)k * cos);
               k = Math.round((float)k * sin);
               this.addStructure(this.getRandomHouse(random), i, k, r);
            } else if (random.nextInt(4) == 0) {
               k = this.innerSize + 5;
               if (random.nextInt(3) == 0) {
                  k += 12;
               }

               i = Math.round((float)k * cos);
               k = Math.round((float)k * sin);
               this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
            }
         }
      }

      private LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
         if (random.nextInt(3) == 0) {
            int i = random.nextInt(3);
            if (i == 0) {
               return new LOTRWorldGenRangerSmithy(false);
            }

            if (i == 1) {
               return new LOTRWorldGenRangerStables(false);
            }

            if (i == 2) {
               return new LOTRWorldGenRangerLodge(false);
            }
         }

         return new LOTRWorldGenRangerHouse(false);
      }

      protected LOTRRoadType getPath(Random random, int i, int k) {
         int i1 = Math.abs(i);
         int k1 = Math.abs(k);
         if (this.villageType == LOTRVillageGenDunedain.VillageType.VILLAGE) {
            int dSq = i * i + k * k;
            if (i1 <= 2 && k1 <= 2) {
               return null;
            }

            int imn = this.innerSize + random.nextInt(3);
            if (dSq < imn * imn) {
               return LOTRRoadType.PATH;
            }

            if (this.palisade && k < 0 && k > -(this.innerSize + 12 + 16) && i1 <= 2 + random.nextInt(3)) {
               return LOTRRoadType.PATH;
            }
         }

         return null;
      }

      public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
         return false;
      }
   }

   public static enum VillageType {
      VILLAGE;
   }
}
