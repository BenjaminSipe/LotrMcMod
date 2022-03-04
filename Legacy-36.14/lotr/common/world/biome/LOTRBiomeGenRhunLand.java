package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.animal.LOTREntityKineAraw;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantMan;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityNearHaradMerchant;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenDoubleFlower;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.village.LOTRVillageGenRhun;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenRhunLand extends LOTRBiome {
   private WorldGenerator boulderGen;
   private WorldGenerator boulderGenSandstone;
   protected boolean rhunBoulders;

   public LOTRBiomeGenRhunLand(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.boulderGenSandstone = new LOTRWorldGenBoulder(Blocks.field_150322_A, 0, 1, 3);
      this.rhunBoulders = true;
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 5, 2, 6));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityKineAraw.class, 6, 4, 4));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 4, 1, 4));
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[6];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLINGS, 20).setSpawnChance(100);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 20).setSpawnChance(100);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 3).setSpawnChance(100);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10).setConquestOnly();
      var10004 = this.npcSpawnList;
      var10001[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestOnly();
      var10004 = this.npcSpawnList;
      var10001[5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 5).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(1, 0.0F);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WICKED_DWARVES, 10).setSpawnChance(100);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DALE_SOLDIERS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DALE_MEN, 5).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DWARVES, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_MEN, 5).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 1);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_GUARDS, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_MEN, 5).setConquestThreshold(200.0F);
      var10000.add(var10001);
      this.npcSpawnList.conquestGainRate = 0.75F;
      this.variantChance = 0.3F;
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.3F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.3F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_MAPLE, 0.3F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.3F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_DATE, 0.3F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_POMEGRANATE, 0.3F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_ALMOND, 0.1F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.1F);
      this.decorator.setTreeCluster(8, 20);
      this.decorator.willowPerChunk = 1;
      this.decorator.flowersPerChunk = 6;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 8;
      this.decorator.doubleGrassPerChunk = 4;
      this.decorator.addTree(LOTRTreeType.OAK, 100);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.BIRCH, 50);
      this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 50);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.BEECH, 50);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.MAPLE, 50);
      this.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.CYPRESS, 400);
      this.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.OAK_SHRUB, 600);
      this.decorator.addTree(LOTRTreeType.APPLE, 2);
      this.decorator.addTree(LOTRTreeType.PEAR, 2);
      this.decorator.addTree(LOTRTreeType.ALMOND, 5);
      this.decorator.addTree(LOTRTreeType.PLUM, 5);
      this.decorator.addTree(LOTRTreeType.OLIVE, 20);
      this.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 20);
      this.decorator.addTree(LOTRTreeType.DATE_PALM, 25);
      this.decorator.addTree(LOTRTreeType.POMEGRANATE, 25);
      this.registerRhunPlainsFlowers();
      this.biomeColors.setGrass(14538041);
      this.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.RHUN(1, 4), 1000);
      this.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 600);
      this.decorator.addVillage(new LOTRVillageGenRhun(this, 0.75F, true));
      this.registerTravellingTrader(LOTREntityNearHaradMerchant.class);
      this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
      this.registerTravellingTrader(LOTREntityScrapTrader.class);
      this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
      this.invasionSpawns.addInvasion(LOTRInvasions.DALE, LOTREventSpawner.EventChance.RARE);
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterRhunLand;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.RHUN_KHAGANATE;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.RHUN.getSubregion("rhudel");
   }

   public LOTRRoadType getRoadBlock() {
      return LOTRRoadType.RHUN;
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      int chunkX = i & 15;
      int chunkZ = k & 15;
      int xzIndex = chunkX * 16 + chunkZ;
      int ySize = blocks.length / 256;
      double d1 = biomeTerrainNoise.func_151601_a((double)i * 0.08D, (double)k * 0.08D);
      double d2 = biomeTerrainNoise.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      if (d1 + d2 > 0.1D) {
         int minHeight = height - 8 - random.nextInt(6);

         for(int j = height - 1; j >= minHeight; --j) {
            int index = xzIndex * ySize + j;
            Block block = blocks[index];
            if (block == Blocks.field_150348_b) {
               blocks[index] = Blocks.field_150322_A;
               meta[index] = 0;
            }
         }
      }

   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (this.rhunBoulders && random.nextInt(50) == 0) {
         for(int l = 0; l < 3; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            int j1 = world.func_72976_f(i1, k1);
            if (random.nextInt(3) == 0) {
               this.boulderGenSandstone.func_76484_a(world, random, i1, j1, k1);
            } else {
               this.boulderGen.func_76484_a(world, random, i1, j1, k1);
            }
         }
      }

   }

   public WorldGenerator getRandomWorldGenForDoubleFlower(Random random) {
      if (random.nextInt(3) == 0) {
         LOTRWorldGenDoubleFlower doubleFlowerGen = new LOTRWorldGenDoubleFlower();
         doubleFlowerGen.setFlowerType(0);
         return doubleFlowerGen;
      } else {
         return super.getRandomWorldGenForDoubleFlower(random);
      }
   }

   public float getTreeIncreaseChance() {
      return 0.1F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.25F;
   }

   public int spawnCountMultiplier() {
      return 3;
   }
}
