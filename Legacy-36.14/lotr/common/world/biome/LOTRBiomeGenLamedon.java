package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.structure2.LOTRWorldGenGondorStructure;
import lotr.common.world.structure2.LOTRWorldGenLamedonWatchfort;
import lotr.common.world.village.LOTRVillageGenGondor;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenLamedon extends LOTRBiomeGenGondor {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenLamedon(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.field_76762_K.add(new SpawnListEntry(LOTREntityHorse.class, 30, 2, 6));
      this.field_76762_K.add(new SpawnListEntry(EntitySheep.class, 40, 4, 4));
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100, 0.0F);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LAMEDON_HILLMEN, 20).setSpawnChance(50);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LAMEDON_SOLDIERS, 25).setSpawnChance(50);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LAMEDON_HILLMEN, 20);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LAMEDON_SOLDIERS, 25);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[5];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 20);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 4).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 1).setConquestThreshold(50.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[2];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[4];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDHRIM, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.COAST_SOUTHRONS, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[3];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLINGS, 2).setConquestThreshold(100.0F);
      var10004 = this.npcSpawnList;
      var10001[1] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
      var10004 = this.npcSpawnList;
      var10001[2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0F);
      var10000.add(var10001);
      var10000 = this.npcSpawnList.newFactionList(0);
      var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
      var10000.add(var10001);
      this.clearBiomeVariants();
      this.variantChance = 0.3F;
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.5F);
      this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.5F);
      this.decorator.setTreeCluster(10, 20);
      this.decorator.treesPerChunk = 0;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 6;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.clearTrees();
      this.decorator.addTree(LOTRTreeType.OAK, 500);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
      this.decorator.addTree(LOTRTreeType.BIRCH, 50);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.BEECH, 50);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 10);
      this.decorator.addTree(LOTRTreeType.CHESTNUT, 200);
      this.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
      this.decorator.addTree(LOTRTreeType.LARCH, 300);
      this.decorator.addTree(LOTRTreeType.ASPEN, 300);
      this.decorator.addTree(LOTRTreeType.APPLE, 5);
      this.decorator.addTree(LOTRTreeType.PEAR, 5);
      this.decorator.addTree(LOTRTreeType.PLUM, 5);
      this.decorator.addTree(LOTRTreeType.OLIVE, 1);
      this.decorator.addTree(LOTRTreeType.ALMOND, 1);
      this.biomeColors.setGrass(11646287);
      this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
   }

   protected void addFiefdomStructures() {
      this.decorator.addRandomStructure(new LOTRWorldGenLamedonWatchfort(false), 700);
      this.decorator.addVillage(new LOTRVillageGenGondor(this, LOTRWorldGenGondorStructure.GondorFiefdom.LAMEDON, 0.8F));
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterLamedon;
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.LAMEDON;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.GONDOR.getSubregion("lamedon");
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = biomeTerrainNoise.func_151601_a((double)i * 0.07D, (double)k * 0.07D);
      double d2 = biomeTerrainNoise.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      if (d1 + d2 > 0.7D) {
         this.field_76752_A = Blocks.field_150346_d;
         this.topBlockMeta = 1;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      }

      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      this.field_76752_A = topBlock_pre;
      this.topBlockMeta = topBlockMeta_pre;
      this.field_76753_B = fillerBlock_pre;
      this.fillerBlockMeta = fillerBlockMeta_pre;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);
      if (random.nextInt(16) == 0) {
         for(int l = 0; l < 4; ++l) {
            int i1 = i + random.nextInt(16) + 8;
            int k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, i1, world.func_72976_f(i1, k1), k1);
         }
      }

   }

   public float getTreeIncreaseChance() {
      return 0.1F;
   }

   public float getChanceToSpawnAnimals() {
      return 0.75F;
   }
}
