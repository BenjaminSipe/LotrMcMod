package lotr.common.world.village;

import java.util.Random;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.npc.LOTREntityRohirrimArcher;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenMeadHall;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenRohanBarn;
import lotr.common.world.structure2.LOTRWorldGenRohanFortCorner;
import lotr.common.world.structure2.LOTRWorldGenRohanFortWall;
import lotr.common.world.structure2.LOTRWorldGenRohanFortress;
import lotr.common.world.structure2.LOTRWorldGenRohanGatehouse;
import lotr.common.world.structure2.LOTRWorldGenRohanHouse;
import lotr.common.world.structure2.LOTRWorldGenRohanMarketStall;
import lotr.common.world.structure2.LOTRWorldGenRohanSmithy;
import lotr.common.world.structure2.LOTRWorldGenRohanStables;
import lotr.common.world.structure2.LOTRWorldGenRohanVillageFarm;
import lotr.common.world.structure2.LOTRWorldGenRohanVillageGarden;
import lotr.common.world.structure2.LOTRWorldGenRohanVillagePalisade;
import lotr.common.world.structure2.LOTRWorldGenRohanVillagePasture;
import lotr.common.world.structure2.LOTRWorldGenRohanVillageSign;
import lotr.common.world.structure2.LOTRWorldGenRohanWatchtower;
import lotr.common.world.structure2.LOTRWorldGenRohanWell;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRVillageGenRohan extends LOTRVillageGen {
   private static final int INNER_MAX = 20;
   private static final int OUTER_MIN = 50;
   private static final int OUTER_MAX = 56;
   private static final int PALISADE_GAP = 25;
   private static final int SPOKE_WIDTH = 2;
   private static final int PATH_FUZZ = 4;
   private static final int FORT_WALL_X = 51;
   private static final int FORT_STABLE_X = 35;
   private static final int FORT_STABLE_Z = 14;
   private static final int FORT_BACK_ROAD_Z = 20;

   public LOTRVillageGenRohan(LOTRBiome biome, float f) {
      super(biome);
      this.gridScale = 14;
      this.gridRandomDisplace = 1;
      this.spawnChance = f;
      this.villageChunkRadius = 5;
   }

   protected LOTRVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
      return new LOTRVillageGenRohan.Instance(this, world, i, k, random, loc);
   }

   public static class Instance extends LOTRVillageGen.AbstractInstance {
      public LOTRVillageGenRohan.VillageType villageType;
      private String[] villageName;
      private boolean palisade;

      public Instance(LOTRVillageGenRohan village, World world, int i, int k, Random random, LocationInfo loc) {
         super(village, world, i, k, random, loc);
      }

      protected void setupVillageProperties(Random random) {
         this.villageName = LOTRNames.getRohanVillageName(random);
         if (random.nextInt(3) == 0) {
            this.villageType = LOTRVillageGenRohan.VillageType.FORT;
         } else {
            this.villageType = LOTRVillageGenRohan.VillageType.VILLAGE;
         }

         this.palisade = random.nextBoolean();
      }

      public boolean isFlat() {
         return false;
      }

      protected void addVillageStructures(Random random) {
         if (this.villageType == LOTRVillageGenRohan.VillageType.VILLAGE) {
            this.setupVillage(random);
         } else if (this.villageType == LOTRVillageGenRohan.VillageType.FORT) {
            this.setupFort(random);
         }

      }

      private void setupVillage(Random random) {
         this.addStructure(new LOTRWorldGenMeadHall(false), 0, 2, 0, true);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityRohanMan.class);
               spawner.setCheckRanges(40, -12, 12, 40);
               spawner.setSpawnRanges(20, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityRohirrimWarrior.class);
               spawner.setCheckRanges(40, -12, 12, 16);
               spawner.setSpawnRanges(20, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         int houses = 20;
         float frac = 1.0F / (float)houses;
         float turn = 0.0F;

         while(true) {
            float sin;
            float cos;
            byte marketX;
            int rSqMax;
            int i;
            do {
               if (turn >= 1.0F) {
                  int farmX = 25;

                  int gardenX;
                  int gardenZ;
                  for(gardenX = -1; gardenX <= 1; ++gardenX) {
                     gardenZ = gardenX * 14;
                     this.addStructure(this.getRandomFarm(random), -farmX, gardenZ, 1);
                     this.addStructure(this.getRandomFarm(random), farmX, gardenZ, 3);
                  }

                  int gardenX = 14;

                  int i;
                  for(i = 0; i <= 2; ++i) {
                     gardenZ = 24 + i * 8;
                     this.addStructure(new LOTRWorldGenRohanVillageGarden(false), -gardenX, gardenZ, 3);
                     this.addStructure(new LOTRWorldGenRohanVillageGarden(false), gardenX, gardenZ, 1);
                  }

                  int gardenZ = 41;

                  for(i = -1; i <= 1; ++i) {
                     gardenX = i * 6;
                     if (i != 0) {
                        this.addStructure(new LOTRWorldGenRohanVillageGarden(false), gardenX, gardenZ, 0);
                     }
                  }

                  this.addStructure(new LOTRWorldGenRohanWell(false), 0, -23, 2, true);
                  this.addStructure((new LOTRWorldGenRohanVillageSign(false)).setSignText(this.villageName), 0, -11, 0, true);
                  int rSq;
                  int marketZ;
                  if (random.nextBoolean()) {
                     marketX = 8;

                     for(rSq = 0; rSq <= 1; ++rSq) {
                        marketZ = 25 + rSq * 10;
                        if (random.nextBoolean()) {
                           this.addStructure(LOTRWorldGenRohanMarketStall.getRandomStall(random, false), -marketX, -marketZ, 1);
                        }

                        if (random.nextBoolean()) {
                           this.addStructure(LOTRWorldGenRohanMarketStall.getRandomStall(random, false), marketX, -marketZ, 3);
                        }
                     }
                  }

                  if (this.palisade) {
                     marketX = 81;
                     rSq = marketX * marketX;
                     marketZ = marketX + 1;
                     rSqMax = marketZ * marketZ;

                     for(i = -marketX; i <= marketX; ++i) {
                        for(int k = -marketX; k <= marketX; ++k) {
                           if (Math.abs(i) > 9 || k >= 0) {
                              int dSq = i * i + k * k;
                              if (dSq >= rSq && dSq < rSqMax) {
                                 this.addStructure(new LOTRWorldGenRohanVillagePalisade(false), i, k, 0);
                              }
                           }
                        }
                     }

                     this.addStructure(new LOTRWorldGenRohanGatehouse(false), 0, -marketX - 2, 0);
                  }

                  return;
               }

               turn += frac;
               float turnR = (float)Math.toRadians((double)(turn * 360.0F));
               sin = MathHelper.func_76126_a(turnR);
               cos = MathHelper.func_76134_b(turnR);
               marketX = 0;
               float turn8 = turn * 8.0F;
               if (turn8 >= 1.0F && turn8 < 3.0F) {
                  marketX = 0;
               } else if (turn8 >= 3.0F && turn8 < 5.0F) {
                  marketX = 1;
               } else if (turn8 >= 5.0F && turn8 < 7.0F) {
                  marketX = 2;
               } else if (turn8 >= 7.0F || turn8 < 1.0F) {
                  marketX = 3;
               }
            } while(this.palisade && sin < 0.0F && Math.abs(cos) <= 0.25F);

            byte l;
            if (random.nextBoolean()) {
               l = 57;
               rSqMax = Math.round((float)l * cos);
               i = Math.round((float)l * sin);
               this.addStructure(this.getRandomHouse(random), rSqMax, i, marketX);
            } else if (random.nextInt(3) != 0) {
               l = 61;
               rSqMax = Math.round((float)l * cos);
               i = Math.round((float)l * sin);
               this.addStructure(new LOTRWorldGenHayBales(false), rSqMax, i, marketX);
            }
         }
      }

      private LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
         if (random.nextInt(4) == 0) {
            int i = random.nextInt(3);
            if (i == 0) {
               return new LOTRWorldGenRohanSmithy(false);
            }

            if (i == 1) {
               return new LOTRWorldGenRohanStables(false);
            }

            if (i == 2) {
               return new LOTRWorldGenRohanBarn(false);
            }
         }

         return new LOTRWorldGenRohanHouse(false);
      }

      private LOTRWorldGenStructureBase2 getRandomFarm(Random random) {
         return (LOTRWorldGenStructureBase2)(random.nextInt(3) == 0 ? new LOTRWorldGenRohanVillagePasture(false) : new LOTRWorldGenRohanVillageFarm(false));
      }

      private void setupFort(Random random) {
         this.addStructure(new LOTRWorldGenRohanFortress(false), 0, -13, 0, true);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClasses(LOTREntityRohirrimWarrior.class, LOTREntityRohirrimArcher.class);
               spawner.setCheckRanges(40, -12, 12, 30);
               spawner.setSpawnRanges(32, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenRohanGatehouse(false), 0, -53, 0, true);
         int towerX = 46;
         int[] var3 = new int[]{-towerX, towerX};
         int l = var3.length;

         int wallX;
         int i1;
         for(wallX = 0; wallX < l; ++wallX) {
            i1 = var3[wallX];
            this.addStructure(new LOTRWorldGenRohanWatchtower(false), i1, -towerX, 0, true);
            this.addStructure(new LOTRWorldGenRohanWatchtower(false), i1, towerX, 2, true);
         }

         var3 = new int[]{-35, 35};
         l = var3.length;

         for(wallX = 0; wallX < l; ++wallX) {
            i1 = var3[wallX];
            this.addStructure(new LOTRWorldGenRohanStables(false), i1, -14, 0, true);
         }

         int farmZ = -20;

         for(l = 0; l <= 1; ++l) {
            wallX = 30 - l * 12;
            this.addStructure(new LOTRWorldGenRohanVillageFarm(false), -wallX, farmZ, 2);
            this.addStructure(new LOTRWorldGenRohanVillageFarm(false), wallX, farmZ, 2);
         }

         int farmZ = 26;

         for(l = -2; l <= 2; ++l) {
            wallX = l * 12;
            this.addStructure(new LOTRWorldGenRohanVillageFarm(false), -wallX, farmZ, 0);
            this.addStructure(new LOTRWorldGenRohanVillageFarm(false), wallX, farmZ, 0);
         }

         int[] var14 = new int[]{-51, 51};
         wallX = var14.length;

         for(i1 = 0; i1 < wallX; ++i1) {
            int i1 = var14[i1];
            int[] var8 = new int[]{-51, 51};
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               int k1 = var8[var10];
               this.addStructure(new LOTRWorldGenRohanFortCorner(false), i1, k1, 0, true);
            }
         }

         for(l = 0; l <= 4; ++l) {
            wallX = 13 + l * 8;
            int wallZ = -51;
            this.addStructure(new LOTRWorldGenRohanFortWall(false, -3, 4), -wallX, wallZ, 0, true);
            this.addStructure(new LOTRWorldGenRohanFortWall(false, -4, 3), wallX, wallZ, 0, true);
         }

         for(l = -5; l <= 5; ++l) {
            wallX = l * 9;
            int wallZ = 51;
            this.addStructure(new LOTRWorldGenRohanFortWall(false), wallX, wallZ, 2, true);
            this.addStructure(new LOTRWorldGenRohanFortWall(false), -wallZ, wallX, 3, true);
            this.addStructure(new LOTRWorldGenRohanFortWall(false), wallZ, wallX, 1, true);
         }

      }

      protected LOTRRoadType getPath(Random random, int i, int k) {
         int i1 = Math.abs(i);
         int k1 = Math.abs(k);
         if (this.villageType == LOTRVillageGenRohan.VillageType.VILLAGE) {
            int dSq = i * i + k * k;
            int imn = 20 + random.nextInt(4);
            if (dSq < imn * imn) {
               return LOTRRoadType.PATH;
            }

            int omn = 50 - random.nextInt(4);
            int omx = 56 + random.nextInt(4);
            if (dSq > omn * omn && dSq < omx * omx) {
               return LOTRRoadType.PATH;
            }

            if (dSq < 2500) {
               int d1 = Math.abs(i1 - k1);
               if (d1 <= 2 + random.nextInt(4)) {
                  return LOTRRoadType.PATH;
               }
            }

            if (this.palisade && k < -56 && k > -81 && i1 <= 2 + random.nextInt(4)) {
               return LOTRRoadType.PATH;
            }
         }

         if (this.villageType == LOTRVillageGenRohan.VillageType.FORT) {
            if (k <= -14 && k >= -49 && i1 <= 2) {
               return LOTRRoadType.ROHAN;
            }

            if (k <= -14 && k >= -17 && i1 <= 37) {
               return LOTRRoadType.PATH;
            }

            if (k >= -14 && k <= 20 && i1 >= 19 && i1 <= 22) {
               return LOTRRoadType.PATH;
            }

            if (k >= 20 && k <= 23 && i1 <= 37) {
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
      VILLAGE,
      FORT;
   }
}
