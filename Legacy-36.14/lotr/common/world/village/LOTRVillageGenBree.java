package lotr.common.world.village;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityBreeGuard;
import lotr.common.entity.npc.LOTREntityBreeHobbit;
import lotr.common.entity.npc.LOTREntityBreeMan;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.structure2.LOTRWorldGenBreeBarn;
import lotr.common.world.structure2.LOTRWorldGenBreeGarden;
import lotr.common.world.structure2.LOTRWorldGenBreeGate;
import lotr.common.world.structure2.LOTRWorldGenBreeGatehouse;
import lotr.common.world.structure2.LOTRWorldGenBreeHedgePart;
import lotr.common.world.structure2.LOTRWorldGenBreeHobbitBurrow;
import lotr.common.world.structure2.LOTRWorldGenBreeHouse;
import lotr.common.world.structure2.LOTRWorldGenBreeInn;
import lotr.common.world.structure2.LOTRWorldGenBreeLampPost;
import lotr.common.world.structure2.LOTRWorldGenBreeMarket;
import lotr.common.world.structure2.LOTRWorldGenBreeMarketStall;
import lotr.common.world.structure2.LOTRWorldGenBreeOffice;
import lotr.common.world.structure2.LOTRWorldGenBreeRuffianHouse;
import lotr.common.world.structure2.LOTRWorldGenBreeSmithy;
import lotr.common.world.structure2.LOTRWorldGenBreeStable;
import lotr.common.world.structure2.LOTRWorldGenBreeWell;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRVillageGenBree extends LOTRVillageGen {
   public LOTRVillageGenBree(LOTRBiome biome, float f) {
      super(biome);
      this.gridScale = 12;
      this.gridRandomDisplace = 1;
      this.spawnChance = f;
      this.villageChunkRadius = 4;
      this.fixedVillageChunkRadius = 13;
   }

   protected LOTRVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
      return new LOTRVillageGenBree.Instance(this, world, i, k, random, loc);
   }

   public static class Instance extends LOTRVillageGen.AbstractInstance {
      public LOTRVillageGenBree.VillageType villageType;
      private int innerSize;
      private static final int innerSizeMin = 12;
      private static final int innerSizeMax = 14;
      private static final int roadWidth = 2;
      private static final int pathFuzz = 3;
      private static final int hamletHedgeGap = 32;
      private boolean hamletHedge;
      private static boolean[][] hobbitPathLookup;
      private static final int hobbitPathLookupRadius = 180;
      private static int[] hobbitBurrowPathPoints;
      private static int[] hobbitBurrowEndPoints;

      public Instance(LOTRVillageGenBree village, World world, int i, int k, Random random, LocationInfo loc) {
         super(village, world, i, k, random, loc);
         if (hobbitPathLookup == null) {
            int size = 361;
            hobbitPathLookup = new boolean[size][size];
            int numBurrows = 20;
            List burrowCoords = new ArrayList();
            List burrowEndCoords = new ArrayList();
            int samples = 300;
            int burrowInterval = samples / numBurrows;
            float[] pathPointsX = new float[samples];
            float[] pathPointsZ = new float[samples];
            int zStart = 50;
            int zEnd = 150;
            float cycles = 1.0F;
            int amp = 80;
            int endpointInterval = samples / MathHelper.func_76128_c((double)(cycles * 4.0F));

            int l;
            for(l = 0; l < samples; ++l) {
               float t = (float)l / (float)samples;
               float z = (float)zStart + (float)(zEnd - zStart) * t;
               float x = MathHelper.func_76126_a((z - (float)zStart) / (float)(zEnd - zStart) * cycles * 3.1415927F * 2.0F) * (float)amp;
               pathPointsX[l] = x;
               pathPointsZ[l] = z;
               if (l % burrowInterval == 0 && Math.abs(x) <= (float)amp * 0.8F) {
                  burrowCoords.add(MathHelper.func_76128_c((double)x));
                  burrowCoords.add(MathHelper.func_76128_c((double)z));
               }

               if (l % endpointInterval == 0 && l / endpointInterval % 2 == 1) {
                  burrowEndCoords.add(MathHelper.func_76128_c((double)x));
                  burrowEndCoords.add(MathHelper.func_76128_c((double)z));
               }
            }

            hobbitBurrowPathPoints = new int[burrowCoords.size()];

            for(l = 0; l < burrowCoords.size(); ++l) {
               hobbitBurrowPathPoints[l] = (Integer)burrowCoords.get(l);
            }

            hobbitBurrowEndPoints = new int[burrowEndCoords.size()];

            for(l = 0; l < burrowEndCoords.size(); ++l) {
               hobbitBurrowEndPoints[l] = (Integer)burrowEndCoords.get(l);
            }

            int pathWidth = 3;

            for(int z = -180; z <= 180; ++z) {
               for(int x = -180; x <= 180; ++x) {
                  int xi = x + 180;
                  int zi = z + 180;
                  hobbitPathLookup[zi][xi] = false;
                  float xMid = (float)x + 0.5F;
                  float zMid = (float)z + 0.5F;

                  for(int l = 0; l < samples; ++l) {
                     float pathX = pathPointsX[l];
                     float pathZ = pathPointsZ[l];
                     float dx = xMid - pathX;
                     float dz = zMid - pathZ;
                     if (dx * dx + dz * dz <= (float)(pathWidth * pathWidth)) {
                        hobbitPathLookup[zi][xi] = true;
                        break;
                     }
                  }
               }
            }
         }

      }

      protected void setupVillageProperties(Random random) {
         if (this.locationInfo.isFixedLocation()) {
            this.villageType = LOTRVillageGenBree.VillageType.VILLAGE;
         } else {
            this.villageType = LOTRVillageGenBree.VillageType.HAMLET;
            this.innerSize = MathHelper.func_76136_a(random, 12, 14);
            this.hamletHedge = random.nextBoolean();
         }

      }

      public boolean isFlat() {
         return false;
      }

      protected void addVillageStructures(Random random) {
         if (this.villageType == LOTRVillageGenBree.VillageType.HAMLET) {
            this.setupHamlet(random);
         } else if (this.villageType == LOTRVillageGenBree.VillageType.VILLAGE) {
            this.setupVillage(random);
         }

      }

      private void setupHamlet(Random random) {
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityBreeMan.class);
               spawner.setCheckRanges(40, -12, 12, 20);
               spawner.setSpawnRanges(20, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityBreeHobbit.class);
               spawner.setCheckRanges(40, -12, 12, 6);
               spawner.setSpawnRanges(20, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityBreeGuard.class);
               spawner.setCheckRanges(40, -12, 12, 8);
               spawner.setSpawnRanges(20, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenBreeWell(false), 0, -2, 0, true);
         int lampX = 9;
         int[] var3 = new int[]{-lampX, lampX};
         int hayX = var3.length;

         int pathHouseZ;
         int stallX;
         int rSq;
         int rMax;
         int rSqMax;
         for(pathHouseZ = 0; pathHouseZ < hayX; ++pathHouseZ) {
            stallX = var3[pathHouseZ];
            int[] var7 = new int[]{-lampX, lampX};
            rSq = var7.length;

            for(rMax = 0; rMax < rSq; ++rMax) {
               rSqMax = var7[rMax];
               this.addStructure(new LOTRWorldGenBreeLampPost(false), stallX, rSqMax, 0);
            }
         }

         int rHouse = this.innerSize + 3;
         if (this.hamletHedge) {
            this.addStructure(this.getHamletHouseOrOther(random), -rHouse, 0, 1);
            this.addStructure(this.getHamletHouseOrOther(random), rHouse, 0, 3);
            this.addStructure(this.getHamletHouseOrOther(random), 0, rHouse, 0);
            int pathHouseX = 8;
            pathHouseZ = this.innerSize + 16;
            this.addStructure(this.getHamletHouse(random), -pathHouseX, -pathHouseZ, 1);
            this.addStructure(this.getHamletHouse(random), pathHouseX, -pathHouseZ, 3);
         } else {
            this.addStructure(this.getHamletHouseOrOther(random), -rHouse, 0, 1);
            this.addStructure(this.getHamletHouseOrOther(random), rHouse, 0, 3);
            this.addStructure(this.getHamletHouseOrOther(random), 0, rHouse, 0);
            this.addStructure(this.getHamletHouseOrOther(random), 0, -rHouse, 2);
         }

         hayX = this.innerSize + 16;
         int[] var16 = new int[]{-hayX, hayX};
         stallX = var16.length;

         int i;
         int k;
         int rHedge;
         for(rHedge = 0; rHedge < stallX; ++rHedge) {
            rSq = var16[rHedge];
            int[] var19 = new int[]{-hayX, hayX};
            rSqMax = var19.length;

            for(i = 0; i < rSqMax; ++i) {
               k = var19[i];
               if (random.nextBoolean()) {
                  this.addStructure(new LOTRWorldGenHayBales(false), rSq, k, 0);
               }
            }
         }

         LOTRWorldGenBreeMarketStall[] stalls = LOTRWorldGenBreeMarketStall.getRandomStalls(random, false, 4);
         stallX = this.innerSize + 1;
         if (random.nextInt(6) == 0) {
            this.addStructure(stalls[0], -stallX + 3, -stallX, 1);
         }

         if (random.nextInt(6) == 0) {
            this.addStructure(stalls[1], stallX, -stallX + 3, 2);
         }

         if (random.nextInt(6) == 0) {
            this.addStructure(stalls[2], stallX - 3, stallX, 3);
         }

         if (random.nextInt(6) == 0) {
            this.addStructure(stalls[3], -stallX, stallX - 3, 0);
         }

         if (this.hamletHedge) {
            rHedge = this.innerSize + 32;
            rSq = rHedge * rHedge;
            rMax = rHedge + 2;
            rSqMax = rMax * rMax;

            for(i = -rMax; i <= rMax; ++i) {
               for(k = -rMax; k <= rMax; ++k) {
                  if (Math.abs(i) > 5 || k >= 0) {
                     int dSq = i * i + k * k;
                     if (dSq >= rSq && dSq < rSqMax) {
                        this.addStructure(new LOTRWorldGenBreeHedgePart(false), i, k, 0);
                     }
                  }
               }
            }
         }

      }

      private LOTRWorldGenStructureBase2 getHamletHouse(Random random) {
         if (random.nextInt(3) == 0) {
            return new LOTRWorldGenBreeHobbitBurrow(false);
         } else {
            return (LOTRWorldGenStructureBase2)(random.nextInt(8) == 0 ? new LOTRWorldGenBreeRuffianHouse(false) : new LOTRWorldGenBreeHouse(false));
         }
      }

      private LOTRWorldGenStructureBase2 getHamletHouseOrOther(Random random) {
         if (random.nextInt(3) == 0) {
            float f = random.nextFloat();
            if (f < 0.08F) {
               return new LOTRWorldGenBreeBarn(false);
            } else if (f < 0.16F) {
               return new LOTRWorldGenBreeStable(false);
            } else if (f < 0.4F) {
               return new LOTRWorldGenBreeSmithy(false);
            } else {
               return (LOTRWorldGenStructureBase2)(f < 0.7F ? new LOTRWorldGenBreeOffice(false) : new LOTRWorldGenBreeInn(false));
            }
         } else {
            return this.getHamletHouse(random);
         }
      }

      private void setupVillage(Random random) {
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityBreeMan.class);
               spawner.setCheckRanges(64, -24, 24, 32);
               spawner.setSpawnRanges(32, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(64);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityBreeMan.class);
               spawner.setCheckRanges(64, -24, 24, 32);
               spawner.setSpawnRanges(32, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(64);
            }
         }, -120, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityBreeMan.class);
               spawner.setCheckRanges(64, -24, 24, 32);
               spawner.setSpawnRanges(32, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(64);
            }
         }, 120, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityBreeHobbit.class);
               spawner.setCheckRanges(64, -24, 24, 40);
               spawner.setSpawnRanges(32, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(64);
            }
         }, 0, 80, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityBreeGuard.class);
               spawner.setCheckRanges(64, -24, 24, 8);
               spawner.setSpawnRanges(32, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(64);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityBreeGuard.class);
               spawner.setCheckRanges(64, -24, 24, 8);
               spawner.setSpawnRanges(32, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(64);
            }
         }, -120, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityBreeGuard.class);
               spawner.setCheckRanges(64, -24, 24, 8);
               spawner.setSpawnRanges(32, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(64);
            }
         }, 120, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityBreeGuard.class);
               spawner.setCheckRanges(64, -24, 24, 8);
               spawner.setSpawnRanges(32, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(64);
            }
         }, 0, -120, 0);
         LOTRWorldGenBreeInn inn = new LOTRWorldGenBreeInn(false);
         if (this.locationInfo.getAssociatedWaypoint() == LOTRWaypoint.BREE) {
            inn.setPresets(new String[]{"The Prancing", "Pony"}, "Barliman Butterbur", true, false);
         }

         this.addStructure(inn, 15, 8, 0, true);
         this.addStructure(new LOTRWorldGenBreeOffice(false), -15, 8, 0, true);
         int houses = 9;

         int i;
         int k;
         for(int i1 = -houses; i1 <= houses; ++i1) {
            int houseX = i1 * 18;
            int houseZ = 5;
            LOTRWorldGenStructureBase2 house1 = new LOTRWorldGenBreeHouse(false);
            LOTRWorldGenStructureBase2 house2 = new LOTRWorldGenBreeHouse(false);
            boolean forceHouse1 = false;
            boolean forceHouse2 = false;
            if (i1 <= -houses + 2 || i1 >= houses) {
               house1 = new LOTRWorldGenBreeRuffianHouse(false);
               house2 = new LOTRWorldGenBreeRuffianHouse(false);
               if (this.locationInfo.getAssociatedWaypoint() == LOTRWaypoint.BREE && i1 == -houses) {
                  house1 = (new LOTRWorldGenBreeRuffianHouse(false)).setRuffianName("Bill Ferny");
                  forceHouse1 = true;
               }
            }

            if (Math.abs(i1) >= 2) {
               this.addStructure((LOTRWorldGenStructureBase2)house1, houseX, houseZ, 0, forceHouse1);
               if (Math.abs(i1) == 4) {
                  this.addStructure(new LOTRWorldGenBreeSmithy(false), houseX, -houseZ, 2);
               } else {
                  this.addStructure((LOTRWorldGenStructureBase2)house2, houseX, -houseZ, 2, forceHouse2);
               }

               i = houseX - Integer.signum(i1) * 9;
               k = houseZ - 1;
               this.addStructure(new LOTRWorldGenBreeLampPost(false), i, k, 0);
               this.addStructure(new LOTRWorldGenBreeLampPost(false), i, -k, 2);
            }
         }

         LOTRWorldGenBreeMarketStall[] stalls = LOTRWorldGenBreeMarketStall.getRandomStalls(random, false, 8);
         LOTRWorldGenBreeMarket market1 = (new LOTRWorldGenBreeMarket(false)).setFrontStepsOnly(true);
         LOTRWorldGenBreeMarket market2 = (new LOTRWorldGenBreeMarket(false)).setFrontStepsOnly(true);
         market1.setStalls(stalls[0], stalls[1], stalls[2], stalls[3]);
         market2.setStalls(stalls[4], stalls[5], stalls[6], stalls[7]);
         this.addStructure(market1, -15, -3, 2, true);
         this.addStructure(market2, 15, -3, 2, true);
         this.addStructure(new LOTRWorldGenBreeWell(false), 5, -32, 3, true);
         this.addStructure(new LOTRWorldGenBreeWell(false), -5, -32, 1, true);
         this.addStructure(new LOTRWorldGenBreeGarden(false), 6, -42, 3, true);
         this.addStructure(new LOTRWorldGenBreeGarden(false), -6, -42, 1, true);

         int l;
         int rHedgeMax;
         int rHedgeSqMax;
         for(l = 0; l <= 5; ++l) {
            int houseX = 5;
            rHedgeMax = -64 - l * 18;
            if (l != 2) {
               this.addStructure(new LOTRWorldGenBreeHouse(false), houseX, rHedgeMax, 3);
               this.addStructure(new LOTRWorldGenBreeHouse(false), -houseX, rHedgeMax, 1);
            }

            rHedgeSqMax = houseX - 1;
            i = rHedgeMax - 9;
            this.addStructure(new LOTRWorldGenBreeLampPost(false), rHedgeSqMax, i, 3);
            this.addStructure(new LOTRWorldGenBreeLampPost(false), -rHedgeSqMax, i, 1);
         }

         this.addStructure(new LOTRWorldGenBreeBarn(false), -72, -100, 1, true);
         this.addStructure(new LOTRWorldGenBreeBarn(false), 72, -100, 3, true);
         this.addStructure(new LOTRWorldGenBreeStable(false), -40, -106, 2, true);
         this.addStructure(new LOTRWorldGenBreeStable(false), 40, -106, 2, true);
         this.addStructure(new LOTRWorldGenBreeWell(false), -40, -94, 0, true);
         this.addStructure(new LOTRWorldGenBreeWell(false), 40, -94, 0, true);
         this.addStructure(new LOTRWorldGenBreeWell(false), 5, 28, 3, true);
         this.addStructure(new LOTRWorldGenBreeWell(false), -5, 28, 1, true);
         this.addStructure(new LOTRWorldGenBreeGarden(false), 6, 38, 3, true);
         this.addStructure(new LOTRWorldGenBreeGarden(false), -6, 38, 1, true);

         int rHedgeSq;
         for(l = 0; l < hobbitBurrowPathPoints.length; l += 2) {
            rHedgeSq = hobbitBurrowPathPoints[l];
            rHedgeMax = hobbitBurrowPathPoints[l + 1];
            this.addStructure(new LOTRWorldGenBreeHobbitBurrow(false), rHedgeSq, rHedgeMax + 6, 0, true);
         }

         for(l = 0; l < hobbitBurrowEndPoints.length; l += 2) {
            rHedgeSq = hobbitBurrowEndPoints[l];
            rHedgeMax = hobbitBurrowEndPoints[l + 1];
            if (Integer.signum(rHedgeSq) == -1) {
               this.addStructure(new LOTRWorldGenBreeHobbitBurrow(false), rHedgeSq - 6, rHedgeMax, 1, true);
            } else {
               this.addStructure(new LOTRWorldGenBreeHobbitBurrow(false), rHedgeSq + 6, rHedgeMax, 3, true);
            }
         }

         this.addStructure(new LOTRWorldGenBreeGate(false), -182, 0, 3, true);
         this.addStructure(new LOTRWorldGenBreeGate(false), 182, 0, 1, true);
         this.addStructure((new LOTRWorldGenBreeGatehouse(false)).setName(this.locationInfo.name), 0, -182, 0, true);
         int rHedge = 180;
         rHedgeSq = rHedge * rHedge;
         rHedgeMax = rHedge + 3;
         rHedgeSqMax = rHedgeMax * rHedgeMax;

         for(i = -rHedgeMax; i <= rHedgeMax; ++i) {
            for(k = -rHedgeMax; k <= rHedgeMax; ++k) {
               int dSq = i * i + k * k;
               if (dSq >= rHedgeSq && dSq < rHedgeSqMax) {
                  int i1 = Math.abs(i);
                  int k1 = Math.abs(k);
                  if ((i1 > 192 || k1 > 4) && (k < -192 || k > 50 || i1 > 4)) {
                     this.addStructure(new LOTRWorldGenBreeHedgePart(false), i, k, 0);
                  }
               }
            }
         }

      }

      protected LOTRRoadType getPath(Random random, int i, int k) {
         int i1 = Math.abs(i);
         int k1 = Math.abs(k);
         int xi;
         int zi;
         if (this.villageType == LOTRVillageGenBree.VillageType.HAMLET) {
            xi = i * i + k * k;
            zi = this.innerSize + random.nextInt(3);
            if (xi < zi * zi) {
               return LOTRRoadType.PATH;
            }

            if (this.hamletHedge && k < 0 && k > -(this.innerSize + 32 + 2) && i1 <= 2 + random.nextInt(3)) {
               return LOTRRoadType.PATH;
            }
         }

         if (this.villageType == LOTRVillageGenBree.VillageType.VILLAGE) {
            if (i1 <= 192 && k1 <= 3) {
               return LOTRRoadType.PAVED_PATH;
            }

            if (k >= -192 && k <= 50 && i1 <= 3) {
               return LOTRRoadType.PAVED_PATH;
            }

            if (i1 <= 70 && Math.abs(k - -100) <= 3) {
               return LOTRRoadType.PAVED_PATH;
            }

            if (i >= -180 && i <= 180 && k >= -180 && k <= 180) {
               xi = i + 180;
               zi = k + 180;
               if (hobbitPathLookup[zi][xi]) {
                  return LOTRRoadType.PAVED_PATH;
               }
            }
         }

         return null;
      }

      public boolean isVillageSpecificSurface(World world, int i, int j, int k) {
         return false;
      }
   }

   public static enum VillageType {
      HAMLET,
      VILLAGE;
   }
}
