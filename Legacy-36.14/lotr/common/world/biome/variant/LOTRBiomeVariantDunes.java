package lotr.common.world.biome.variant;

import java.util.Random;
import lotr.common.util.LOTRFunctions;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeVariantDunes extends LOTRBiomeVariant {
   private static NoiseGeneratorPerlin duneWaveNoise = new NoiseGeneratorPerlin(new Random(305620668206968L), 1);
   private static NoiseGeneratorPerlin duneHeightNoise = new NoiseGeneratorPerlin(new Random(5729475867259682L), 1);

   public LOTRBiomeVariantDunes(int i, String s) {
      super(i, s, LOTRBiomeVariant.VariantScale.ALL);
      this.setTemperatureRainfall(0.1F, -0.1F);
   }

   public float getHeightBoostAt(int i, int k) {
      return (float)this.getDuneHeightAt(i, k) / 22.0F;
   }

   private int getDuneHeightAt(int i, int k) {
      double d1 = duneWaveNoise.func_151601_a((double)i * 0.02D, (double)k * 0.02D);
      double d2 = duneWaveNoise.func_151601_a((double)i * 0.7D, (double)k * 0.7D);
      double d3 = d1 * 0.9D + d2 * 0.1D;
      d3 = MathHelper.func_151237_a(d3, -1.0D, 1.0D);
      d3 *= 15.0D;
      int maxDuneHeight = 12;
      int duneHeight = Math.round(LOTRFunctions.normalisedSin(((float)i + (float)d3) * 0.09F) * (float)maxDuneHeight);
      return duneHeight;
   }
}
