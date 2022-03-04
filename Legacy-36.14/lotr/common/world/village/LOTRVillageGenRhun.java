package lotr.common.world.village;

import java.util.Random;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityEasterling;
import lotr.common.entity.npc.LOTREntityEasterlingArcher;
import lotr.common.entity.npc.LOTREntityEasterlingWarrior;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.structure2.LOTRWorldGenEasterlingFortress;
import lotr.common.world.structure2.LOTRWorldGenEasterlingGarden;
import lotr.common.world.structure2.LOTRWorldGenEasterlingGatehouse;
import lotr.common.world.structure2.LOTRWorldGenEasterlingHouse;
import lotr.common.world.structure2.LOTRWorldGenEasterlingLamp;
import lotr.common.world.structure2.LOTRWorldGenEasterlingLargeTownHouse;
import lotr.common.world.structure2.LOTRWorldGenEasterlingMarketStall;
import lotr.common.world.structure2.LOTRWorldGenEasterlingSmithy;
import lotr.common.world.structure2.LOTRWorldGenEasterlingStables;
import lotr.common.world.structure2.LOTRWorldGenEasterlingStatue;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTavern;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTavernTown;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTower;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTownHouse;
import lotr.common.world.structure2.LOTRWorldGenEasterlingTownWall;
import lotr.common.world.structure2.LOTRWorldGenEasterlingVillageFarm;
import lotr.common.world.structure2.LOTRWorldGenEasterlingVillageSign;
import lotr.common.world.structure2.LOTRWorldGenEasterlingWell;
import lotr.common.world.structure2.LOTRWorldGenHayBales;
import lotr.common.world.structure2.LOTRWorldGenNPCRespawner;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.world.World;

public class LOTRVillageGenRhun extends LOTRVillageGen {
   private boolean enableTowns;
   private static final int VILLAGE_CENTRE_SIZE = 15;
   private static final int VILLAGE_PATH_LENGTH = 64;
   private static final int VILLAGE_PATH_WIDTH = 3;
   private static final int PATH_FUZZ = 4;
   private static final int TOWN_ROAD_WIDTH = 6;
   private static final int TOWN_INNER_ROAD = 12;
   private static final int TOWN_OUTER_ROAD = 60;
   private static final int TOWN_HOUSE_GAP = 2;
   private static final int TOWN_EDGE = 86;
   private static final int FORT_ROAD_WIDTH = 4;
   private static final int FORT_EDGE = 54;
   private static final int FORT_INNER_ROAD = 20;
   private static final int FORT_OUTER_ROAD = 48;

   public LOTRVillageGenRhun(LOTRBiome biome, float f, boolean flag) {
      super(biome);
      this.gridScale = 14;
      this.gridRandomDisplace = 1;
      this.spawnChance = f;
      this.villageChunkRadius = 6;
      this.enableTowns = flag;
   }

   protected LOTRVillageGen.AbstractInstance createVillageInstance(World world, int i, int k, Random random, LocationInfo loc) {
      return new LOTRVillageGenRhun.Instance(this, world, i, k, random, loc);
   }

   public static class Instance extends LOTRVillageGen.AbstractInstance {
      public LOTRVillageGenRhun.VillageType villageType;
      private String[] villageName;
      private boolean enableTowns;

      public Instance(LOTRVillageGenRhun village, World world, int i, int k, Random random, LocationInfo loc) {
         super(village, world, i, k, random, loc);
         this.enableTowns = village.enableTowns;
      }

      protected void setupVillageProperties(Random random) {
         this.villageName = LOTRNames.getRhunVillageName(random);
         if (random.nextInt(4) == 0) {
            this.villageType = LOTRVillageGenRhun.VillageType.FORT;
         } else if (this.enableTowns && random.nextInt(4) == 0) {
            this.villageType = LOTRVillageGenRhun.VillageType.TOWN;
         } else {
            this.villageType = LOTRVillageGenRhun.VillageType.VILLAGE;
         }

      }

      public boolean isFlat() {
         LOTRVillageGenRhun.VillageType var10001 = this.villageType;
         return this.villageType == LOTRVillageGenRhun.VillageType.TOWN;
      }

      protected void addVillageStructures(Random random) {
         if (this.villageType == LOTRVillageGenRhun.VillageType.VILLAGE) {
            this.setupVillage(random);
         } else if (this.villageType == LOTRVillageGenRhun.VillageType.TOWN) {
            this.setupTown(random);
         } else if (this.villageType == LOTRVillageGenRhun.VillageType.FORT) {
            this.setupFort(random);
         }

      }

      private void setupVillage(Random random) {
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityEasterling.class);
               spawner.setCheckRanges(40, -12, 12, 40);
               spawner.setSpawnRanges(20, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
               spawner.setCheckRanges(40, -12, 12, 16);
               spawner.setSpawnRanges(20, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         int pathEnd = 68;
         int pathSide = 7;
         int centreSide = 19;
         this.addStructure(new LOTRWorldGenEasterlingWell(false), 0, -2, 0, true);
         int signX = 12;
         this.addStructure((new LOTRWorldGenEasterlingVillageSign(false)).setSignText(this.villageName), -signX, 0, 1, true);
         this.addStructure((new LOTRWorldGenEasterlingVillageSign(false)).setSignText(this.villageName), signX, 0, 3, true);
         this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), 0, -centreSide, 2, true);
         if (random.nextBoolean()) {
            this.addStructure(new LOTRWorldGenEasterlingTavern(false), -pathEnd, 0, 1, true);
            this.addStructure(this.getOtherVillageStructure(random), pathEnd, 0, 3, true);
         } else {
            this.addStructure(this.getOtherVillageStructure(random), -pathEnd, 0, 1, true);
            this.addStructure(new LOTRWorldGenEasterlingTavern(false), pathEnd, 0, 3, true);
         }

         int rowHouses = 3;

         for(int l = -rowHouses; l <= rowHouses; ++l) {
            int i1 = l * 18;
            int k1 = pathSide;
            if (Math.abs(i1) <= 15) {
               k1 = pathSide + (15 - pathSide);
            }

            if (Math.abs(l) >= 1) {
               this.addStructure(this.getRandomHouse(random), i1, -k1, 2);
            }

            this.addStructure(this.getRandomHouse(random), i1, k1, 0);
            int k2 = k1 + 20;
            if (l != 0) {
               if (random.nextInt(3) == 0) {
                  this.addStructure(this.getRandomVillageFarm(random), i1, -k2, 2);
               } else {
                  this.addStructure(new LOTRWorldGenHayBales(false), i1, -k2, 2);
               }
            }

            if (random.nextInt(3) == 0) {
               this.addStructure(this.getRandomVillageFarm(random), i1, k2, 0);
            } else {
               this.addStructure(new LOTRWorldGenHayBales(false), i1, k2, 0);
            }
         }

      }

      private LOTRWorldGenStructureBase2 getRandomHouse(Random random) {
         return new LOTRWorldGenEasterlingHouse(false);
      }

      private LOTRWorldGenStructureBase2 getOtherVillageStructure(Random random) {
         return (LOTRWorldGenStructureBase2)(random.nextBoolean() ? new LOTRWorldGenEasterlingStables(false) : new LOTRWorldGenEasterlingSmithy(false));
      }

      private LOTRWorldGenStructureBase2 getRandomVillageFarm(Random random) {
         return (LOTRWorldGenStructureBase2)(random.nextBoolean() ? new LOTRWorldGenEasterlingVillageFarm.Animals(false) : new LOTRWorldGenEasterlingVillageFarm.Crops(false));
      }

      private void setupTown(Random random) {
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityEasterling.class);
               spawner.setCheckRanges(80, -12, 12, 100);
               spawner.setSpawnRanges(60, -6, 6, 64);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         int spawnerX = 60;
         int[] var3 = new int[]{-spawnerX, spawnerX};
         int var4 = var3.length;

         int l;
         int houseX;
         for(l = 0; l < var4; ++l) {
            houseX = var3[l];
            int[] var7 = new int[]{-spawnerX, spawnerX};
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               int k1 = var7[var9];
               this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                  public void setupRespawner(LOTREntityNPCRespawner spawner) {
                     spawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
                     spawner.setCheckRanges(50, -12, 12, 16);
                     spawner.setSpawnRanges(20, -6, 6, 64);
                     spawner.setBlockEnemySpawnRange(60);
                  }
               }, houseX, k1, 0);
            }
         }

         if (random.nextBoolean()) {
            this.addStructure(new LOTRWorldGenEasterlingGarden(false), 0, 10, 2, true);
         } else {
            this.addStructure(new LOTRWorldGenEasterlingStatue(false), 0, 6, 2, true);
         }

         int mansionX = 12;
         int mansionZ = 20;
         this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionX, -mansionZ, 2, true);
         this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionX, -mansionZ, 2, true);
         this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionX, mansionZ, 0, true);
         this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionX, mansionZ, 0, true);
         this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionZ, -mansionX, 1, true);
         this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), -mansionZ, mansionX, 1, true);
         this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionZ, -mansionX, 3, true);
         this.addStructure(new LOTRWorldGenEasterlingLargeTownHouse(false), mansionZ, mansionX, 3, true);

         byte houseZ1;
         byte houseZ2;
         for(l = 0; l <= 3; ++l) {
            houseX = 10 + 14 * l;
            houseZ1 = 58;
            houseZ2 = 68;
            if (l <= 2) {
               if (l >= 1 && l <= 2) {
                  if (l == 1) {
                     this.addStructure(new LOTRWorldGenEasterlingTavernTown(false), -houseX - 7, -houseZ1, 0, true);
                  }
               } else {
                  this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, -houseZ1, 0, true);
               }

               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseX, -houseZ1, 0, true);
               if (l >= 1) {
                  this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, houseZ1, 2, true);
                  this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseX, houseZ1, 2, true);
               }

               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ1, -houseX, 3, true);
               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ1, houseX, 3, true);
               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ1, -houseX, 1, true);
               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ1, houseX, 1, true);
            }

            if (l == 1) {
               this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseX, -houseZ2, 2, true);
               this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseX, -houseZ2, 2, true);
               this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseX, houseZ2, 0, true);
               this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseX, houseZ2, 0, true);
               this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseZ2, -houseX, 1, true);
               this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseZ2, houseX, 1, true);
               this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseZ2, -houseX, 3, true);
               this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseZ2, houseX, 3, true);
            } else {
               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, -houseZ2, 2, true);
               this.addStructure((LOTRWorldGenStructureBase2)(l == 3 ? new LOTRWorldGenEasterlingSmithy(false) : new LOTRWorldGenEasterlingTownHouse(false)), houseX, -houseZ2, 2, true);
               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseX, houseZ2, 0, true);
               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseX, houseZ2, 0, true);
               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ2, -houseX, 1, true);
               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), -houseZ2, houseX, 1, true);
               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ2, -houseX, 3, true);
               this.addStructure(new LOTRWorldGenEasterlingTownHouse(false), houseZ2, houseX, 3, true);
            }
         }

         int marketX = 4;

         for(int l = 0; l <= 2; ++l) {
            houseX = 56 - l * 7;
            this.addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), -marketX, houseX, 1, true);
            this.addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), marketX, houseX, 3, true);
         }

         marketX = 14;
         int marketZ = 59;
         this.addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), -marketX, marketZ, 2, true);
         this.addStructure(LOTRWorldGenEasterlingMarketStall.getRandomStall(random, false), marketX, marketZ, 2, true);
         houseZ1 = 58;
         this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseZ1 + 5, -houseZ1, 0, true);
         this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseZ1 - 5, -houseZ1, 0, true);
         this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), -houseZ1 + 5, houseZ1, 2, true);
         this.addStructure(new LOTRWorldGenEasterlingVillageFarm.Tree(false), houseZ1 - 5, houseZ1, 2, true);
         houseZ2 = 69;
         int wellZ = 63;
         this.addStructure(new LOTRWorldGenEasterlingWell(false), -houseZ2, -wellZ, 1, true);
         this.addStructure(new LOTRWorldGenEasterlingWell(false), -wellZ, -houseZ2, 2, true);
         this.addStructure(new LOTRWorldGenEasterlingWell(false), houseZ2, -wellZ, 3, true);
         this.addStructure(new LOTRWorldGenEasterlingWell(false), wellZ, -houseZ2, 2, true);
         this.addStructure(new LOTRWorldGenEasterlingWell(false), -houseZ2, wellZ, 1, true);
         this.addStructure(new LOTRWorldGenEasterlingWell(false), -wellZ, houseZ2, 0, true);
         this.addStructure(new LOTRWorldGenEasterlingWell(false), houseZ2, wellZ, 3, true);
         this.addStructure(new LOTRWorldGenEasterlingWell(false), wellZ, houseZ2, 0, true);
         this.addStructure((new LOTRWorldGenEasterlingGatehouse(false)).setSignText(this.villageName), 0, 94, 2, true);
         int towerX = 90;
         this.addStructure((new LOTRWorldGenEasterlingTower(false)).disableDoor().setBackLadder().setLeftLadder(), -towerX, -towerX - 3, 0, true);
         this.addStructure((new LOTRWorldGenEasterlingTower(false)).disableDoor().setBackLadder().setRightLadder(), towerX, -towerX - 3, 0, true);
         this.addStructure((new LOTRWorldGenEasterlingTower(false)).disableDoor().setBackLadder().setRightLadder(), -towerX, towerX + 3, 2, true);
         this.addStructure((new LOTRWorldGenEasterlingTower(false)).disableDoor().setBackLadder().setLeftLadder(), towerX, towerX + 3, 2, true);
         int wallZ = towerX;
         this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), 0, -towerX, 0);
         this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), towerX, 0, 1);
         this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), -towerX, 0, 3);

         for(int l = 0; l <= 9; ++l) {
            int wallX = 11 + l * 8;
            this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), wallX, -wallZ, 0);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -wallX, -wallZ, 0);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), wallZ, wallX, 1);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), wallZ, -wallX, 1);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -wallX, wallZ, 2);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), wallX, wallZ, 2);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -wallZ, -wallX, 3);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -wallZ, wallX, 3);
         }

         int lampX = 7;
         int lampZ = 96;
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampZ, 2, false);
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampZ, 2, false);
      }

      private void setupFort(Random random) {
         this.addStructure(new LOTRWorldGenNPCRespawner(false) {
            public void setupRespawner(LOTREntityNPCRespawner spawner) {
               spawner.setSpawnClass(LOTREntityEasterling.class);
               spawner.setCheckRanges(50, -12, 12, 16);
               spawner.setSpawnRanges(30, -6, 6, 40);
               spawner.setBlockEnemySpawnRange(60);
            }
         }, 0, 0, 0);
         int[] var2 = new int[]{-48, 48};
         int var3 = var2.length;

         int l;
         int farmX;
         for(int var4 = 0; var4 < var3; ++var4) {
            int i1 = var2[var4];
            int[] var6 = new int[]{-48, 48};
            l = var6.length;

            for(farmX = 0; farmX < l; ++farmX) {
               int k1 = var6[farmX];
               this.addStructure(new LOTRWorldGenNPCRespawner(false) {
                  public void setupRespawner(LOTREntityNPCRespawner spawner) {
                     spawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
                     spawner.setCheckRanges(32, -12, 12, 16);
                     spawner.setSpawnRanges(20, -6, 6, 40);
                     spawner.setBlockEnemySpawnRange(40);
                  }
               }, i1, k1, 0);
            }
         }

         this.addStructure(new LOTRWorldGenEasterlingFortress(false), 0, 13, 2, true);
         int stableX = 26;
         int stableZ = 0;
         this.addStructure(new LOTRWorldGenEasterlingStables(false), -stableX, stableZ, 1, true);
         this.addStructure(new LOTRWorldGenEasterlingStables(false), stableX, stableZ, 3, true);
         int wellZ = 18;
         this.addStructure(new LOTRWorldGenEasterlingWell(false), -stableX, wellZ, 1, true);
         this.addStructure(new LOTRWorldGenEasterlingWell(false), stableX, wellZ, 3, true);
         int farmZ = 27;

         for(l = -3; l <= 3; ++l) {
            farmX = l * 10;
            if (random.nextInt(3) == 0) {
               this.addStructure(new LOTRWorldGenHayBales(false), farmX, -farmZ - 5, 2);
            } else {
               this.addStructure(this.getRandomVillageFarm(random), farmX, -farmZ, 2);
            }
         }

         int statueX = 6;
         int statueZ = 36;
         this.addStructure(new LOTRWorldGenEasterlingStatue(false), -statueX, statueZ, 1, true);
         this.addStructure(new LOTRWorldGenEasterlingStatue(false), statueX, statueZ, 3, true);
         this.addStructure((new LOTRWorldGenEasterlingGatehouse(false)).disableSigns(), 0, 62, 2, true);
         int towerX = 58;
         this.addStructure((new LOTRWorldGenEasterlingTower(false)).disableDoor().setBackLadder().setLeftLadder(), -towerX, -towerX - 3, 0, true);
         this.addStructure((new LOTRWorldGenEasterlingTower(false)).disableDoor().setBackLadder().setRightLadder(), towerX, -towerX - 3, 0, true);
         this.addStructure((new LOTRWorldGenEasterlingTower(false)).disableDoor().setBackLadder().setRightLadder(), -towerX, towerX + 3, 2, true);
         this.addStructure((new LOTRWorldGenEasterlingTower(false)).disableDoor().setBackLadder().setLeftLadder(), towerX, towerX + 3, 2, true);
         int wallZ = towerX;
         this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), 0, -towerX, 0);
         this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), towerX, 0, 1);
         this.addStructure(LOTRWorldGenEasterlingTownWall.Centre(false), -towerX, 0, 3);

         for(int l = 0; l <= 5; ++l) {
            int wallX = 11 + l * 8;
            this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), wallX, -wallZ, 0);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -wallX, -wallZ, 0);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), wallZ, wallX, 1);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), wallZ, -wallX, 1);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -wallX, wallZ, 2);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), wallX, wallZ, 2);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Left(false), -wallZ, -wallX, 3);
            this.addStructure(LOTRWorldGenEasterlingTownWall.Right(false), -wallZ, wallX, 3);
         }

         int lampX = 17;
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, -lampX, 2, false);
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, -lampX, 2, false);
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampX, 0, false);
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampX, 0, false);
         lampX = 45;
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, -lampX, 2, false);
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, -lampX, 2, false);
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampX, 0, false);
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampX, 0, false);
         lampX = 7;
         int lampZ = 64;
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), -lampX, lampZ, 2, false);
         this.addStructure(new LOTRWorldGenEasterlingLamp(false), lampX, lampZ, 2, false);
      }

      protected LOTRRoadType getPath(Random random, int i, int k) {
         int i1 = Math.abs(i);
         int k1 = Math.abs(k);
         if (this.villageType == LOTRVillageGenRhun.VillageType.VILLAGE) {
            int dSq = i * i + k * k;
            int imn = 15 + random.nextInt(4);
            if (dSq < imn * imn) {
               return LOTRRoadType.PATH;
            }

            if (i1 <= 64 && k1 <= 3 + random.nextInt(2)) {
               return LOTRRoadType.PATH;
            }
         }

         byte innerOut;
         byte outerOut;
         if (this.villageType == LOTRVillageGenRhun.VillageType.TOWN) {
            innerOut = 18;
            if (i1 <= innerOut && k1 <= innerOut && (i1 >= 12 || k1 >= 12)) {
               return LOTRRoadType.RHUN;
            }

            if (i1 <= 3 && k1 >= innerOut && k1 <= 86 || k1 <= 3 && i1 >= innerOut && i1 <= 86) {
               return LOTRRoadType.RHUN;
            }

            outerOut = 66;
            if (i1 <= outerOut && k1 <= outerOut && (i1 >= 60 || k1 >= 60)) {
               return LOTRRoadType.RHUN;
            }
         }

         if (this.villageType == LOTRVillageGenRhun.VillageType.FORT) {
            innerOut = 24;
            if (i1 <= innerOut && k1 <= innerOut && (i1 >= 20 || k1 >= 20)) {
               return LOTRRoadType.RHUN;
            }

            if (k >= 14 && k <= 54 && i1 <= 2) {
               return LOTRRoadType.RHUN;
            }

            outerOut = 52;
            if (i1 <= outerOut && k1 <= outerOut && (i1 >= 48 || k1 >= 48)) {
               return LOTRRoadType.RHUN;
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
      TOWN,
      FORT;
   }
}
