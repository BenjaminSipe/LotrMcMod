package lotr.common.world.village;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityGulfHaradArcher;
import lotr.common.entity.npc.LOTREntityGulfHaradWarrior;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenGulfAltar;
import lotr.common.world.structure2.LOTRWorldGenGulfBazaar;
import lotr.common.world.structure2.LOTRWorldGenGulfFarm;
import lotr.common.world.structure2.LOTRWorldGenGulfHouse;
import lotr.common.world.structure2.LOTRWorldGenGulfPasture;
import lotr.common.world.structure2.LOTRWorldGenGulfPyramid;
import lotr.common.world.structure2.LOTRWorldGenGulfSmithy;
import lotr.common.world.structure2.LOTRWorldGenGulfTavern;
import lotr.common.world.structure2.LOTRWorldGenGulfTotem;
import lotr.common.world.structure2.LOTRWorldGenGulfTower;
import lotr.common.world.structure2.LOTRWorldGenGulfTownWall;
import lotr.common.world.structure2.LOTRWorldGenGulfVillageLight;
import lotr.common.world.structure2.LOTRWorldGenGulfVillageSign;
import lotr.common.world.structure2.LOTRWorldGenGulfWarCamp;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRVillageGenGulfHarad extends LOTRVillageGen {
   public LOTRVillageGenGulfHarad(LOTRBiome biome, float f) {
      super(biome);
      this.gridScale = 14;
      this.gridRandomDisplace = 1;
      this.spawnChance = f;
      this.villageChunkRadius = 6;
   }

   protected LOTRVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
      return new LOTRVillageGenGulfHarad.Instance(this, world, i, k, random, loc);
   }

   public static class Instance extends LOTRVillageGen.AbstractInstance {
      public LOTRVillageGenGulfHarad.VillageType villageType;
      public String[] villageName;
      private int numOuterHouses;
      private static final int pathInner = 16;
      private static final int roadWidth = 5;
      private static final int pathFuzz = 3;
      private static final int rHouses = 24;
      private static final int rFarms = 52;
      private static final int rEdge = 68;
      private static final int rTownInner = 24;
      private static final int rTownRoadEnd = 74;
      private static final int rTownWall = 98;
      private static final int townRoadWidth = 7;
      private boolean townWall = true;
      int rTownTower = 90;

      public Instance(LOTRVillageGenGulfHarad village, World world, int i, int k, Random random, LocationInfo loc) {
         super(village, world, i, k, random, loc);
      }

      protected void setupVillageProperties(Random random) {
         if (random.nextInt(4) == 0) {
            this.villageType = LOTRVillageGenGulfHarad.VillageType.FORT;
         } else if (random.nextInt(3) == 0) {
            this.villageType = LOTRVillageGenGulfHarad.VillageType.TOWN;
         } else {
            this.villageType = LOTRVillageGenGulfHarad.VillageType.VILLAGE;
         }

         this.villageName = LOTRNames.getHaradVillageName(random);
         this.numOuterHouses = MathHelper.func_76136_a(random, 5, 8);
      }

      public boolean isFlat() {
         return false;
      }

      protected void addVillageStructures(Random random) {
         if (this.villageType == LOTRVillageGenGulfHarad.VillageType.VILLAGE) {
            this.setupVillage(random);
         } else if (this.villageType == LOTRVillageGenGulfHarad.VillageType.TOWN) {
            this.setupTown(random);
         } else if (this.villageType == LOTRVillageGenGulfHarad.VillageType.FORT) {
            this.setupFort(random);
         }

      }

      private void setupVillage(Random random) {
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityGulfHaradrim.class);
               spawner.setCheckRanges(64, -12, 12, 24);
               spawner.setSpawnRanges(32, -6, 6, 32);
               spawner.setBlockEnemySpawnRange(64);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
               spawner.setCheckRanges(64, -12, 12, 12);
               spawner.setSpawnRanges(32, -6, 6, 32);
               spawner.setBlockEnemySpawnRange(64);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenGulfTotem(false), 0, -2, 0, true);
         this.addStructure(new LOTRWorldGenGulfTavern(false), 0, 24, 0, true);
         int rSignsInner = 11;
         this.addStructure((new LOTRWorldGenGulfVillageSign(false)).setSignText(this.villageName), -rSignsInner, 0, 1, true);
         this.addStructure((new LOTRWorldGenGulfVillageSign(false)).setSignText(this.villageName), rSignsInner, 0, 3, true);

         int numFarms;
         float frac;
         float turn;
         float turnR;
         float sin;
         float cos;
         float turn8;
         for(numFarms = 0; numFarms < this.numOuterHouses; ++numFarms) {
            frac = (float)numFarms / (float)(this.numOuterHouses - 1);
            turn = 0.15F;
            turnR = 1.0F - turn;
            sin = turn + (turnR - turn) * frac;
            cos = (sin + 0.25F) % 1.0F;
            float turnR = (float)Math.toRadians((double)(cos * 360.0F));
            turn8 = MathHelper.func_76126_a(turnR);
            float cos = MathHelper.func_76134_b(turnR);
            int r = 0;
            float turn8 = cos * 8.0F;
            if (turn8 >= 1.0F && turn8 < 3.0F) {
               r = 0;
            } else if (turn8 >= 3.0F && turn8 < 5.0F) {
               r = 1;
            } else if (turn8 >= 5.0F && turn8 < 7.0F) {
               r = 2;
            } else if (turn8 >= 7.0F || turn8 < 1.0F) {
               r = 3;
            }

            int l = 24;
            int i = Math.round((float)l * cos);
            int k = Math.round((float)l * turn8);
            this.addStructure(this.getRandomHouse(random), i, k, r);
         }

         numFarms = this.numOuterHouses * 2;
         frac = 1.0F / (float)numFarms;
         turn = 0.0F;

         while(turn < 1.0F) {
            turn += frac;
            turnR = (float)Math.toRadians((double)(turn * 360.0F));
            sin = MathHelper.func_76126_a(turnR);
            cos = MathHelper.func_76134_b(turnR);
            int r = 0;
            turn8 = turn * 8.0F;
            if (turn8 >= 1.0F && turn8 < 3.0F) {
               r = 0;
            } else if (turn8 >= 3.0F && turn8 < 5.0F) {
               r = 1;
            } else if (turn8 >= 5.0F && turn8 < 7.0F) {
               r = 2;
            } else if (turn8 >= 7.0F || turn8 < 1.0F) {
               r = 3;
            }

            int l = 52;
            int i = Math.round((float)l * cos);
            int k = Math.round((float)l * sin);
            if (random.nextInt(3) == 0) {
               this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
            } else {
               this.addStructure(this.getRandomFarm(random), i, k, r);
            }
         }

      }

      private LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
         return (LOTRWorldGenStructureBase2)(random.nextInt(5) == 0 ? new LOTRWorldGenGulfSmithy(false) : new LOTRWorldGenGulfHouse(false));
      }

      private LOTRWorldGenStructureBase2 getRandomFarm(Random random) {
         return (LOTRWorldGenStructureBase2)(random.nextBoolean() ? new LOTRWorldGenGulfFarm(false) : new LOTRWorldGenGulfPasture(false));
      }

      private void setupTown(Random random) {
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityGulfHaradrim.class);
               spawner.setCheckRanges(80, -12, 12, 100);
               spawner.setSpawnRanges(40, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         int[] var2 = new int[]{-40, 40};
         int l = var2.length;

         int turn;
         int k;
         int rSqMax;
         int i;
         for(turn = 0; turn < l; ++turn) {
            k = var2[turn];
            int[] var6 = new int[]{-40, 40};
            int var7 = var6.length;

            for(rSqMax = 0; rSqMax < var7; ++rSqMax) {
               i = var6[rSqMax];
               this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                  public void setupRespawner(LOTREntityNPCRespawner spawner) {
                     spawner.setSpawnClasses(LOTREntityGulfHaradWarrior.class, LOTREntityGulfHaradArcher.class);
                     spawner.setCheckRanges(64, -12, 12, 20);
                     spawner.setSpawnRanges(20, -6, 6, 64);
                     spawner.setBlockEnemySpawnRange(64);
                  }
               }, k, i, 0);
            }
         }

         this.addStructure(new LOTRWorldGenGulfPyramid(false), 0, -11, 0, true);
         int lightR = 15;
         this.addStructure(new LOTRWorldGenGulfVillageLight(false), -lightR, -lightR, 0, true);
         this.addStructure(new LOTRWorldGenGulfVillageLight(false), lightR, -lightR, 0, true);
         this.addStructure(new LOTRWorldGenGulfVillageLight(false), -lightR, lightR, 0, true);
         this.addStructure(new LOTRWorldGenGulfVillageLight(false), lightR, lightR, 0, true);
         this.addStructure(new LOTRWorldGenGulfBazaar(false), -74, 0, 1, true);
         this.addStructure(new LOTRWorldGenGulfAltar(false), 74, 0, 3, true);
         this.addStructure(new LOTRWorldGenGulfTotem(false), 0, 79, 0, true);

         for(l = 0; l <= 2; ++l) {
            int i = 5;
            k = 32 + l * 20;
            this.addStructure(new LOTRWorldGenGulfHouse(false), -i, -k, 1, true);
            this.addStructure(new LOTRWorldGenGulfHouse(false), i, -k, 3, true);
            this.addStructure(new LOTRWorldGenGulfHouse(false), -i, k, 1, true);
            this.addStructure(new LOTRWorldGenGulfHouse(false), i, k, 3, true);
            this.addStructure(new LOTRWorldGenGulfHouse(false), k, -i, 2, true);
            this.addStructure(new LOTRWorldGenGulfHouse(false), k, i, 0, true);
            if (l == 0) {
               this.addStructure(new LOTRWorldGenGulfSmithy(false), -k - 6, -i, 2, true);
               this.addStructure(new LOTRWorldGenGulfTavern(false), -k - 6, i, 0, true);
            }
         }

         l = (int)((double)this.rTownTower / Math.sqrt(2.0D));
         this.addStructure(new LOTRWorldGenGulfTower(false), -l, -l + 4, 2, true);
         this.addStructure(new LOTRWorldGenGulfTower(false), l, -l + 4, 2, true);
         this.addStructure(new LOTRWorldGenGulfTower(false), -l, l - 4, 0, true);
         this.addStructure(new LOTRWorldGenGulfTower(false), l, l - 4, 0, true);
         turn = 0;
         byte numTurns = 24;

         while(true) {
            int dSq;
            do {
               if (turn > numTurns) {
                  this.addStructure((new LOTRWorldGenGulfVillageSign(false)).setSignText(this.villageName), -5, -96, 0, true);
                  this.addStructure((new LOTRWorldGenGulfVillageSign(false)).setSignText(this.villageName), 5, -96, 0, true);
                  if (this.townWall) {
                     int rSq = 9604;
                     int rMax = 99;
                     rSqMax = rMax * rMax;

                     for(i = -98; i <= 98; ++i) {
                        for(int k = -98; k <= 98; ++k) {
                           int i1 = Math.abs(i);
                           if (i1 > 6 || k >= 0) {
                              dSq = i * i + k * k;
                              if (dSq >= rSq && dSq < rSqMax) {
                                 LOTRWorldGenGulfTownWall wall = new LOTRWorldGenGulfTownWall(false);
                                 if (i1 == 7 && k < 0) {
                                    wall.setTall();
                                 }

                                 this.addStructure(wall, i, k, 0);
                              }
                           }
                        }
                     }
                  }

                  return;
               }

               ++turn;
            } while(turn % 3 == 0);

            float turnF = (float)turn / (float)numTurns;
            float turnR = (float)Math.toRadians((double)(turnF * 360.0F));
            float sin = MathHelper.func_76126_a(turnR);
            float cos = MathHelper.func_76134_b(turnR);
            int r = 0;
            float turn8 = turnF * 8.0F;
            if (turn8 >= 1.0F && turn8 < 3.0F) {
               r = 0;
            } else if (turn8 >= 3.0F && turn8 < 5.0F) {
               r = 1;
            } else if (turn8 >= 5.0F && turn8 < 7.0F) {
               r = 2;
            } else if (turn8 >= 7.0F || turn8 < 1.0F) {
               r = 3;
            }

            dSq = this.rTownTower - 6;
            int i = Math.round((float)dSq * cos);
            int k = Math.round((float)dSq * sin);
            if (random.nextInt(3) == 0) {
               this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
            } else {
               this.addStructure(this.getRandomFarm(random), i, k, r);
            }
         }
      }

      private void setupFort(Random random) {
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityGulfHaradrim.class);
               spawner.setCheckRanges(40, -12, 12, 16);
               spawner.setSpawnRanges(24, -6, 6, 32);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenGulfWarCamp(false), 0, -15, 0, true);
         int towerX = 36;
         this.addStructure(new LOTRWorldGenGulfTower(false), -towerX, -towerX + 4, 2, true);
         this.addStructure(new LOTRWorldGenGulfTower(false), towerX, -towerX + 4, 2, true);
         this.addStructure(new LOTRWorldGenGulfTower(false), -towerX, towerX - 4, 0, true);
         this.addStructure(new LOTRWorldGenGulfTower(false), towerX, towerX - 4, 0, true);

         for(int l = -1; l <= 1; ++l) {
            int i = l * 16;
            int k = 28;
            this.addStructure(this.getRandomFarm(random), i, k, 0);
            this.addStructure(this.getRandomFarm(random), -k, i, 1);
            this.addStructure(this.getRandomFarm(random), k, i, 3);
         }

      }

      protected LOTRRoadType getPath(Random random, int i, int k) {
         int i1 = Math.abs(i);
         int k1 = Math.abs(k);
         int dSq;
         if (this.villageType == LOTRVillageGenGulfHarad.VillageType.VILLAGE) {
            dSq = i * i + k * k;
            int imn = 16 - random.nextInt(3);
            int imx = 21 + random.nextInt(3);
            if (dSq > imn * imn && dSq < imx * imx) {
               return LOTRRoadType.PATH;
            }
         }

         if (this.villageType == LOTRVillageGenGulfHarad.VillageType.TOWN) {
            dSq = i * i + k * k;
            if (dSq < 576) {
               return LOTRRoadType.GULF_HARAD;
            }

            if (k1 <= 3 && i1 <= 74 || i1 <= 3 && k <= 74) {
               return LOTRRoadType.GULF_HARAD;
            }
         }

         return null;
      }

      public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
         if (this.villageType == LOTRVillageGenGulfHarad.VillageType.TOWN) {
            Block block = world.func_147439_a(i, j, k);
            int meta = world.func_72805_g(i, j, k);
            if (block == LOTRMod.brick3 && meta == 13 || block == LOTRMod.brick3 && meta == 14) {
               return true;
            }
         }

         return false;
      }
   }

   public static enum VillageType {
      VILLAGE,
      TOWN,
      FORT;
   }
}
