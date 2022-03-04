package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRAchievement;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.world.feature.LOTRWorldGenObsidianGravel;
import lotr.common.world.feature.LOTRWorldGenStreams;
import lotr.common.world.feature.LOTRWorldGenVolcanoCrater;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenFarHaradVolcano extends LOTRBiomeGenFarHarad {
   private WorldGenerator boulderGen;
   private WorldGenerator obsidianGen;
   private static NoiseGeneratorPerlin noiseDirt = new NoiseGeneratorPerlin(new Random(5286926989260620260L), 1);

   public LOTRBiomeGenFarHaradVolcano(int i, boolean major) {
      super(i, major);
      this.boulderGen = new LOTRWorldGenBoulder(Blocks.field_150348_b, 0, 1, 3);
      this.obsidianGen = new LOTRWorldGenObsidianGravel();
      this.func_76745_m();
      this.field_76752_A = Blocks.field_150348_b;
      this.field_76753_B = Blocks.field_150348_b;
      this.field_76762_K.clear();
      this.field_76755_L.clear();
      this.field_76761_J.clear();
      this.spawnableLOTRAmbientList.clear();
      this.npcSpawnList.clear();
      LOTRBiomeSpawnList.FactionContainer var10000 = this.npcSpawnList.newFactionList(100);
      LOTRBiomeSpawnList.SpawnListContainer[] var10001 = new LOTRBiomeSpawnList.SpawnListContainer[1];
      LOTRBiomeSpawnList var10004 = this.npcSpawnList;
      var10001[0] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10).setSpawnChance(200);
      var10000.add(var10001);
      this.decorator.treesPerChunk = 0;
      this.decorator.grassPerChunk = 0;
      this.decorator.doubleGrassPerChunk = 0;
      this.decorator.flowersPerChunk = 0;
      this.decorator.clearTrees();
      this.decorator.addTree(LOTRTreeType.OAK_DEAD, 100);
      this.decorator.addTree(LOTRTreeType.ACACIA_DEAD, 200);
      this.decorator.addTree(LOTRTreeType.CHARRED, 500);
      this.biomeColors.setSky(5986904);
      this.biomeColors.setClouds(3355443);
      this.biomeColors.setFog(6710886);
      this.biomeColors.setWater(4009759);
   }

   public boolean getEnableRiver() {
      return false;
   }

   public LOTRAchievement getBiomeAchievement() {
      return LOTRAchievement.enterFarHaradVolcano;
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.FAR_HARAD.getSubregion("volcano");
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = noiseDirt.func_151601_a((double)i * 0.09D, (double)k * 0.09D);
      double d2 = noiseDirt.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      if (d1 + d2 > 0.2D) {
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
      int i1;
      int k1;
      int j1;
      if (random.nextInt(32) == 0) {
         int boulders = 1 + random.nextInt(4);

         for(i1 = 0; i1 < boulders; ++i1) {
            k1 = i + random.nextInt(16) + 8;
            j1 = k + random.nextInt(16) + 8;
            this.boulderGen.func_76484_a(world, random, k1, world.func_72976_f(k1, j1), j1);
         }
      }

      WorldGenerator lavaGen = new LOTRWorldGenStreams(Blocks.field_150356_k);

      for(i1 = 0; i1 < 50; ++i1) {
         k1 = i + random.nextInt(16) + 8;
         j1 = 40 + random.nextInt(120);
         int k1 = k + random.nextInt(16) + 8;
         lavaGen.func_76484_a(world, random, k1, j1, k1);
      }

      if (random.nextInt(1) == 0) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         j1 = world.func_72976_f(i1, k1);
         (new LOTRWorldGenVolcanoCrater()).func_76484_a(world, random, i1, j1, k1);
      }

      if (random.nextInt(50) == 0) {
         i1 = i + random.nextInt(16) + 8;
         k1 = k + random.nextInt(16) + 8;
         j1 = world.func_72825_h(i1, k1);
         this.obsidianGen.func_76484_a(world, random, i1, j1, k1);
      }

   }

   public float getTreeIncreaseChance() {
      return 0.5F;
   }

   public boolean canSpawnHostilesInDay() {
      return true;
   }

   public int spawnCountMultiplier() {
      return 2;
   }
}
