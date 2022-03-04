package lotr.common.world.biome;

import java.util.Random;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenNearHaradHills extends LOTRBiomeGenNearHarad {
   private static NoiseGeneratorPerlin noiseSandstone = new NoiseGeneratorPerlin(new Random(8906820602062L), 1);
   private static NoiseGeneratorPerlin noiseStone = new NoiseGeneratorPerlin(new Random(583062262026L), 1);

   public LOTRBiomeGenNearHaradHills(int i, boolean major) {
      super(i, major);
      this.field_76765_S = true;
      this.clearBiomeVariants();
      this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
      this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND_SAND);
   }

   public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, LOTRBiomeVariant variant) {
      Block topBlock_pre = this.field_76752_A;
      int topBlockMeta_pre = this.topBlockMeta;
      Block fillerBlock_pre = this.field_76753_B;
      int fillerBlockMeta_pre = this.fillerBlockMeta;
      double d1 = noiseSandstone.func_151601_a((double)i * 0.09D, (double)k * 0.09D);
      double d2 = noiseSandstone.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      double d3 = noiseStone.func_151601_a((double)i * 0.09D, (double)k * 0.09D);
      double d4 = noiseStone.func_151601_a((double)i * 0.4D, (double)k * 0.4D);
      if (d3 + d4 > 0.6D) {
         this.field_76752_A = Blocks.field_150348_b;
         this.topBlockMeta = 0;
         this.field_76753_B = this.field_76752_A;
         this.fillerBlockMeta = this.topBlockMeta;
      } else if (d1 + d2 > 0.2D) {
         this.field_76752_A = Blocks.field_150322_A;
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
      return 0.01F;
   }
}
