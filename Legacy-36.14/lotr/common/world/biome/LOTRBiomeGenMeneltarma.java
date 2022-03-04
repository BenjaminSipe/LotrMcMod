package lotr.common.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenMeneltarma extends LOTRBiomeGenOcean {
   private WorldGenerator boulderGen;

   public LOTRBiomeGenMeneltarma(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 4);
      this.field_76762_K.clear();
      this.field_76755_L.clear();
      this.spawnableLOTRAmbientList.clear();
      this.decorator.setTreeCluster(8, 20);
      this.decorator.treesPerChunk = 0;
      this.decorator.flowersPerChunk = 5;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 6;
      this.decorator.doubleGrassPerChunk = 2;
      this.decorator.generateAthelas = true;
      this.decorator.clearTrees();
      this.decorator.addTree(LOTRTreeType.CEDAR, 1000);
      this.decorator.addTree(LOTRTreeType.CEDAR_LARGE, 500);
      this.decorator.addTree(LOTRTreeType.OAK, 200);
      this.decorator.addTree(LOTRTreeType.OAK_TALL, 200);
      this.decorator.addTree(LOTRTreeType.OAK_LARGE, 400);
      this.decorator.addTree(LOTRTreeType.BIRCH, 200);
      this.decorator.addTree(LOTRTreeType.BIRCH_TALL, 200);
      this.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 400);
      this.decorator.addTree(LOTRTreeType.BEECH, 200);
      this.decorator.addTree(LOTRTreeType.BEECH_LARGE, 400);
      List flowerDupes = new ArrayList();

      for(int l = 0; l < 10; ++l) {
         this.flowers.clear();
         this.registerPlainsFlowers();
         flowerDupes.addAll(this.flowers);
      }

      this.flowers.clear();
      this.flowers.addAll(flowerDupes);
      this.addFlower(LOTRMod.athelas, 0, 10);
      this.decorator.clearRandomStructures();
      this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
   }

   public LOTRWaypoint.Region getBiomeWaypoints() {
      return LOTRWaypoint.Region.MENELTARMA;
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterMeneltarma;
   }

   public boolean isHiddenBiome() {
      return true;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.SEA.getSubregion("meneltarma");
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = biomeTerrainNoise.func_151601_a((double)i * 0.1D, (double)k * 0.1D);
      if (d1 > -0.1D) {
         this.field_76752_A = Blocks.field_150348_b;
         this.topBlockMeta = 0;
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
      int i1;
      int j1;
      int k1;
      if (random.nextInt(2) == 0) {
         for(i1 = 0; i1 < 3; ++i1) {
            j1 = i + random.nextInt(16) + 8;
            k1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, j1, world.func_72976_f(j1, k1), k1);
         }
      }

      if (random.nextInt(3) == 0) {
         i1 = i + random.nextInt(16) + 8;
         j1 = random.nextInt(128);
         k1 = k + random.nextInt(16) + 8;
         (new WorldGenFlowers(LOTRMod.athelas)).func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.2F;
   }
}
