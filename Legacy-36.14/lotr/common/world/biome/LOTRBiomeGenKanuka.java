package lotr.common.world.biome;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenKanuka extends LOTRBiomeGenFarHarad {
   private static NoiseGeneratorPerlin noisePaths1 = new NoiseGeneratorPerlin(new Random(22L), 1);
   private static NoiseGeneratorPerlin noisePaths2 = new NoiseGeneratorPerlin(new Random(11L), 1);
   private static final int FOREST_HEIGHT = 75;

   public LOTRBiomeGenKanuka(int i, boolean major) {
      super(i, major);
      this.spawnableLOTRAmbientList.clear();
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityBird.class, 10, 4, 4));
      this.spawnableLOTRAmbientList.add(new SpawnListEntry(LOTREntityButterfly.class, 10, 4, 4));
      this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
      this.addBiomeVariant(LOTRBiomeVariant.HILLS);
      this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
      this.enablePodzol = false;
      this.decorator.treesPerChunk = 0;
      this.decorator.setTreeCluster(8, 3);
      this.decorator.vinesPerChunk = 0;
      this.decorator.flowersPerChunk = 3;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.grassPerChunk = 4;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.enableFern = true;
      this.decorator.melonPerChunk = 0.0F;
      this.decorator.clearTrees();
      this.decorator.addTree(LOTRTreeType.KANUKA, 100);
      this.biomeColors.setGrass(11915563);
   }

   public LOTRMusicRegion.Sub getBiomeMusic() {
      return LOTRMusicRegion.FAR_HARAD.getSubregion("kanuka");
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      double d1 = noisePaths1.func_151601_a((double)i * 0.008D, (double)k * 0.008D);
      double d2 = noisePaths2.func_151601_a((double)i * 0.008D, (double)k * 0.008D);
      if (d1 > 0.0D && d1 < 0.1D || d2 > 0.0D && d2 < 0.1D) {
         this.field_76752_A = LOTRMod.dirtPath;
         this.topBlockMeta = 1;
      }

      this.enablePodzol = height > 75;
      super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
      this.enablePodzol = false;
      this.field_76752_A = topBlock_pre;
      this.topBlockMeta = topBlockMeta_pre;
   }

   public void func_76728_a(World world, Random random, int i, int k) {
      super.func_76728_a(world, random, i, k);

      int grasses;
      int doubleGrasses;
      int l;
      for(int count = 0; count < 4; ++count) {
         grasses = i + random.nextInt(16) + 8;
         doubleGrasses = k + random.nextInt(16) + 8;
         l = world.func_72976_f(grasses, doubleGrasses);
         if (l > 75) {
            this.decorator.genTree(world, random, grasses, l, doubleGrasses);
         }
      }

      LOTRBiomeVariant variant = ((LOTRWorldChunkManager)world.func_72959_q()).getBiomeVariantAt(i + 8, k + 8);
      int grasses = 12;
      grasses = Math.round((float)grasses * variant.grassFactor);

      int i1;
      int j1;
      for(doubleGrasses = 0; doubleGrasses < grasses; ++doubleGrasses) {
         l = i + random.nextInt(16) + 8;
         i1 = random.nextInt(128);
         j1 = k + random.nextInt(16) + 8;
         if (world.func_72976_f(l, j1) > 75) {
            WorldGenerator grassGen = this.func_76730_b(random);
            grassGen.func_76484_a(world, random, l, i1, j1);
         }
      }

      int doubleGrasses = 4;
      doubleGrasses = Math.round((float)doubleGrasses * variant.grassFactor);

      for(l = 0; l < doubleGrasses; ++l) {
         i1 = i + random.nextInt(16) + 8;
         j1 = random.nextInt(128);
         int k1 = k + random.nextInt(16) + 8;
         if (world.func_72976_f(i1, k1) > 75) {
            WorldGenerator grassGen = this.getRandomWorldGenForDoubleGrass(random);
            grassGen.func_76484_a(world, random, i1, j1, k1);
         }
      }

   }

   public LOTRBiome.GrassBlockAndMeta getRandomGrass(Random random) {
      return random.nextInt(5) != 0 ? new LOTRBiome.GrassBlockAndMeta(Blocks.field_150329_H, 2) : super.getRandomGrass(random);
   }

   public WorldGenerator getRandomWorldGenForDoubleGrass(Random random) {
      WorldGenDoublePlant generator = new WorldGenDoublePlant();
      generator.func_150548_a(3);
      return generator;
   }

   public float getChanceToSpawnAnimals() {
      return super.getChanceToSpawnAnimals() * 0.25F;
   }
}
