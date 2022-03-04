package lotr.common.world.village;

import java.util.Random;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.entity.npc.LOTREntityHarnedorArcher;
import lotr.common.entity.npc.LOTREntityHarnedorWarrior;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenHarnedorFarm;
import lotr.common.world.structure2.LOTRWorldGenHarnedorFort;
import lotr.common.world.structure2.LOTRWorldGenHarnedorHouse;
import lotr.common.world.structure2.LOTRWorldGenHarnedorHouseRuined;
import lotr.common.world.structure2.LOTRWorldGenHarnedorMarket;
import lotr.common.world.structure2.LOTRWorldGenHarnedorPalisade;
import lotr.common.world.structure2.LOTRWorldGenHarnedorPalisadeRuined;
import lotr.common.world.structure2.LOTRWorldGenHarnedorPasture;
import lotr.common.world.structure2.LOTRWorldGenHarnedorSmithy;
import lotr.common.world.structure2.LOTRWorldGenHarnedorStables;
import lotr.common.world.structure2.LOTRWorldGenHarnedorTavern;
import lotr.common.world.structure2.LOTRWorldGenHarnedorTavernRuined;
import lotr.common.world.structure2.LOTRWorldGenHarnedorTower;
import lotr.common.world.structure2.LOTRWorldGenHarnedorVillageSign;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenNearHaradTent;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRVillageGenHarnedor extends LOTRVillageGen {
   private boolean isRuinedVillage;

   public LOTRVillageGenHarnedor(LOTRBiome biome, float f) {
      super(biome);
      this.gridScale = 12;
      this.gridRandomDisplace = 1;
      this.spawnChance = f;
      this.villageChunkRadius = 4;
   }

   public LOTRVillageGenHarnedor setRuined() {
      this.isRuinedVillage = true;
      return this;
   }

   protected LOTRVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
      return new LOTRVillageGenHarnedor.Instance(this, world, i, k, random, loc);
   }

   public static class Instance extends LOTRVillageGen.AbstractInstance {
      public LOTRVillageGenHarnedor.VillageType villageType;
      private boolean isRuined;
      public String[] villageName;
      private int numOuterHouses;
      private static final int pathInner = 17;
      private static final int roadWidth = 5;
      private static final int pathFuzz = 3;
      private static final int rHouses = 25;
      private static final int rFarms = 45;
      private boolean palisade;
      private static final int rPalisade = 61;
      private static final int rTowers = 24;
      private static final int rFortPalisade = 42;

      public Instance(LOTRVillageGenHarnedor village, World world, int i, int k, Random random, LocationInfo loc) {
         super(village, world, i, k, random, loc);
         this.isRuined = village.isRuinedVillage;
      }

      protected void setupVillageProperties(Random random) {
         if (random.nextInt(4) == 0) {
            this.villageType = LOTRVillageGenHarnedor.VillageType.FORTRESS;
         } else {
            this.villageType = LOTRVillageGenHarnedor.VillageType.VILLAGE;
         }

         this.villageName = LOTRNames.getHaradVillageName(random);
         this.numOuterHouses = MathHelper.func_76136_a(random, 5, 8);
         this.palisade = random.nextInt(3) != 0;
      }

      public boolean isFlat() {
         return false;
      }

      protected void addVillageStructures(Random random) {
         if (this.villageType == LOTRVillageGenHarnedor.VillageType.VILLAGE) {
            this.setupVillage(random);
         } else {
            this.setupFortress(random);
         }

      }

      private void setupVillage(Random random) {
         if (!this.isRuined) {
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
               public void setupRespawner(LOTREntityNPCRespawner spawner) {
                  spawner.setSpawnClass(LOTREntityHarnedhrim.class);
                  spawner.setCheckRanges(64, -12, 12, 24);
                  spawner.setSpawnRanges(32, -6, 6, 32);
                  spawner.setBlockEnemySpawnRange(64);
               }
            }, 0, 0, 0);
            this.addStructure(new LOTRWorldGenNPCRespawner(false) {
               public void setupRespawner(LOTREntityNPCRespawner spawner) {
                  spawner.setSpawnClasses(LOTREntityHarnedorWarrior.class, LOTREntityHarnedorArcher.class);
                  spawner.setCheckRanges(64, -12, 12, 12);
                  spawner.setSpawnRanges(32, -6, 6, 32);
                  spawner.setBlockEnemySpawnRange(64);
               }
            }, 0, 0, 0);
         }

         if (this.isRuined) {
            this.addStructure(new LOTRWorldGenHarnedorTavernRuined(false), 3, -7, 0, true);
         } else if (random.nextBoolean()) {
            this.addStructure(new LOTRWorldGenHarnedorMarket(false), 0, -8, 0, true);
         } else {
            this.addStructure(new LOTRWorldGenHarnedorTavern(false), 3, -7, 0, true);
         }

         float frac = 1.0F / (float)this.numOuterHouses;
         float turn = 0.0F;

         while(true) {
            byte r;
            int dSq;
            int i;
            do {
               float turnR;
               float sin;
               if (turn >= 1.0F) {
                  if (!this.isRuined) {
                     int numFarms = this.numOuterHouses * 2;
                     frac = 1.0F / (float)numFarms;
                     turn = 0.0F;

                     label150:
                     while(true) {
                        int k;
                        byte r;
                        do {
                           if (turn >= 1.0F) {
                              break label150;
                           }

                           turn += frac;
                           turnR = (float)Math.toRadians((double)(turn * 360.0F));
                           sin = MathHelper.func_76126_a(turnR);
                           float cos = MathHelper.func_76134_b(turnR);
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

                           int l = 45;
                           i = Math.round((float)l * cos);
                           k = Math.round((float)l * sin);
                        } while(this.palisade && k < 0 && Math.abs(i) < 10);

                        if (random.nextInt(3) == 0) {
                           this.addStructure(new LOTRWorldGenHayBales(false), i, k, r);
                        } else if (random.nextInt(3) == 0) {
                           this.addStructure(new LOTRWorldGenHarnedorPasture(false), i, k, r);
                        } else {
                           this.addStructure(new LOTRWorldGenHarnedorFarm(false), i, k, r);
                        }
                     }
                  }

                  if (!this.isRuined) {
                     if (this.palisade) {
                        this.addStructure((new LOTRWorldGenHarnedorVillageSign(false)).setSignText(this.villageName), 5 * (random.nextBoolean() ? 1 : -1), -56, 0, true);
                     } else {
                        this.addStructure((new LOTRWorldGenHarnedorVillageSign(false)).setSignText(this.villageName), 0, -16, 0, true);
                     }
                  }

                  if (this.palisade) {
                     int rSq = 3721;
                     int rMax = 62;
                     int rSqMax = rMax * rMax;

                     for(int i = -61; i <= 61; ++i) {
                        for(int k = -61; k <= 61; ++k) {
                           int i1 = Math.abs(i);
                           if (i1 > 4 || k >= 0) {
                              dSq = i * i + k * k;
                              if (dSq >= rSq && dSq < rSqMax) {
                                 Object palisade;
                                 if (this.isRuined) {
                                    if (random.nextBoolean()) {
                                       continue;
                                    }

                                    palisade = new LOTRWorldGenHarnedorPalisadeRuined(false);
                                 } else {
                                    palisade = new LOTRWorldGenHarnedorPalisade(false);
                                 }

                                 if (i1 == 5 && k < 0) {
                                    ((LOTRWorldGenHarnedorPalisade)palisade).setTall();
                                 }

                                 this.addStructure((LOTRWorldGenStructureBase2)palisade, i, k, 0);
                              }
                           }
                        }
                     }
                  }

                  return;
               }

               turn += frac;
               float turnR = (float)Math.toRadians((double)(turn * 360.0F));
               turnR = MathHelper.func_76126_a(turnR);
               sin = MathHelper.func_76134_b(turnR);
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

               int l = 25;
               dSq = Math.round((float)l * sin);
               i = Math.round((float)l * turnR);
            } while(this.palisade && i < 0 && Math.abs(dSq) < 10);

            this.addStructure(this.getRandomHouse(random), dSq, i, r);
         }
      }

      private LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
         if (this.isRuined) {
            return new LOTRWorldGenHarnedorHouseRuined(false);
         } else if (random.nextInt(5) == 0) {
            return new LOTRWorldGenHarnedorSmithy(false);
         } else {
            return (LOTRWorldGenStructureBase2)(random.nextInt(4) == 0 ? new LOTRWorldGenHarnedorStables(false) : new LOTRWorldGenHarnedorHouse(false));
         }
      }

      private void setupFortress(Random random) {
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityHarnedhrim.class);
               spawner.setCheckRanges(64, -12, 12, 16);
               spawner.setSpawnRanges(24, -6, 6, 32);
               spawner.setBlockEnemySpawnRange(50);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenHarnedorFort(false), 0, -12, 0, true);
         this.addStructure(new LOTRWorldGenHarnedorTower(false), -24, -24, 0, true);
         this.addStructure(new LOTRWorldGenHarnedorTower(false), 24, -24, 0, true);
         this.addStructure(new LOTRWorldGenHarnedorTower(false), -24, 24, 2, true);
         this.addStructure(new LOTRWorldGenHarnedorTower(false), 24, 24, 2, true);

         for(int l = -1; l <= 1; ++l) {
            int k = l * 10;
            int i = 24;
            this.addStructure(new LOTRWorldGenNearHaradTent(false), -i, k, 1, true);
            this.addStructure(new LOTRWorldGenNearHaradTent(false), i, k, 3, true);
         }

         int rSq = 1764;
         int rMax = 43;
         int rSqMax = rMax * rMax;

         for(int i = -42; i <= 42; ++i) {
            for(int k = -42; k <= 42; ++k) {
               int i1 = Math.abs(i);
               if (i1 > 4 || k >= 0) {
                  int dSq = i * i + k * k;
                  if (dSq >= rSq && dSq < rSqMax) {
                     LOTRWorldGenHarnedorPalisade palisade = new LOTRWorldGenHarnedorPalisade(false);
                     if (i1 == 5 && k < 0) {
                        palisade.setTall();
                     }

                     this.addStructure(palisade, i, k, 0);
                  }
               }
            }
         }

      }

      protected LOTRRoadType getPath(Random random, int i, int k) {
         int i1 = Math.abs(i);
         int k1 = Math.abs(k);
         if (this.villageType == LOTRVillageGenHarnedor.VillageType.VILLAGE) {
            if (this.isRuined && random.nextInt(4) == 0) {
               return null;
            }

            int dSq = i * i + k * k;
            int imn = 17 - random.nextInt(3);
            int imx = 22 + random.nextInt(3);
            if (dSq > imn * imn && dSq < imx * imx) {
               return LOTRRoadType.PATH;
            }

            if (this.palisade && k <= -imx && k >= -66 && i1 < 2 + random.nextInt(3)) {
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
      FORTRESS;
   }
}
