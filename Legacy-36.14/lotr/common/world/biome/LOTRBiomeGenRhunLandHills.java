package lotr.common.world.biome;

import java.util.Random;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class LOTRBiomeGenRhunLandHills extends LOTRBiomeGenRhunLand {
   private static NoiseGeneratorPerlin noiseStone = new NoiseGeneratorPerlin(new Random(528592609698295062L), 1);
   private static NoiseGeneratorPerlin noiseSand = new NoiseGeneratorPerlin(new Random(23849150950915615L), 1);

   public LOTRBiomeGenRhunLandHills(int i, boolean major) {
      super(i, major);
      this.npcSpawnList.clear();
      this.clearBiomeVariants();
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
      this.decorator.addOre(new WorldGenMinable(Blocks.field_150352_o, 4), 2.0F, 0, 48);
      this.decorator.resetTreeCluster();
      this.decorator.flowersPerChunk = 2;
      this.decorator.doubleFlowersPerChunk = 0;
      this.decorator.grassPerChunk = 5;
      this.decorator.doubleGrassPerChunk = 1;
      this.decorator.clearVillages();
      this.biomeColors.resetGrass();
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = noiseStone.func_151601_a((double)i * 0.09D, (double)k * 0.09D);
      double d2 = noiseStone.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      double d3 = noiseSand.func_151601_a((double)i * 0.09D, (double)k * 0.09D);
      double d4 = noiseSand.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      if (d3 + d4 > 1.1D) {
         this.field_76752_A = Blocks.field_150354_m;
         this.topBlockMeta = 0;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d1 + d2 > 0.4D) {
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

   public float getTreeIncreaseChance() {
      return 0.25F;
   }
}
