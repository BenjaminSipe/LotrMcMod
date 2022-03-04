package lotr.common.world.biome;

import java.awt.Color;
import lotr.common.LOTRMod;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GrassColors;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience.GrassColorModifier;
import net.minecraft.world.biome.BiomeAmbience.GrassColorModifier.ColorModifier;
import net.minecraft.world.gen.PerlinNoiseGenerator;

public class LOTRGrassColorModifiers {
   public static final GrassColorModifier NORTHLANDS = createModifier("northlands", noiseGrassModifier(11501635, 13945160, 0.05D, 0.003D));
   public static final GrassColorModifier FOROCHEL = createModifier("forochel", noiseGrassModifier(12365701, 6312501, 0.05D, 0.03D));
   public static final GrassColorModifier SEA = createModifier("sea", (x, z, grassColor) -> {
      World world = LOTRMod.PROXY.getClientWorld();
      Biome biome = world.func_226691_t_(new BlockPos(MathHelper.func_76128_c(x), 0, MathHelper.func_76128_c(z)));
      float adjustedTemp = SeaBiome.getAdjustedTemperatureForGrassAndFoliage(world, biome, (int)z);
      adjustedTemp = MathHelper.func_76131_a(adjustedTemp, 0.0F, 1.0F);
      float downfall = MathHelper.func_76131_a(biome.func_76727_i(), 0.0F, 1.0F);
      return GrassColors.func_77480_a((double)adjustedTemp, (double)downfall);
   });
   private static final PerlinNoiseGenerator NOISE_GEN_TUNDRA_GRASS = LOTRBiomeBase.makeSingleLayerPerlinNoise(2971944500256852478L);

   private static GrassColorModifier createModifier(String name, ColorModifier func) {
      String fullName = "lotr_" + name;
      return GrassColorModifier.create(fullName.toUpperCase(), fullName, func);
   }

   private static ColorModifier noiseGrassModifier(int color1, int color2, double scale1, double scale2) {
      float[] rgb1 = (new Color(color1)).getColorComponents((float[])null);
      float[] rgb2 = (new Color(color2)).getColorComponents((float[])null);
      return (x, z, grassColor) -> {
         double d1 = NOISE_GEN_TUNDRA_GRASS.func_215464_a(x * scale1, z * scale1, false);
         double d2 = NOISE_GEN_TUNDRA_GRASS.func_215464_a(x * scale2, z * scale2, false);
         d1 *= 0.4D;
         float noise = (float)MathHelper.func_151237_a(d1 + d2, -1.0D, 1.0D);
         ++noise;
         noise /= 2.0F;
         float[] rgbNoise = new float[rgb1.length];

         for(int l = 0; l < rgbNoise.length; ++l) {
            rgbNoise[l] = MathHelper.func_219799_g(noise, rgb1[l], rgb2[l]);
         }

         return (new Color(rgbNoise[0], rgbNoise[1], rgbNoise[2])).getRGB();
      };
   }
}
