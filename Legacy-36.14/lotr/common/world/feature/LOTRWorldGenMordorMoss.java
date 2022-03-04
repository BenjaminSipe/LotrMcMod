package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenMordorMoss extends WorldGenerator {
   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int numberOfMoss = 32 + random.nextInt(80);
      float f = random.nextFloat() * 3.1415927F;
      double d = (double)((float)(i + 8) + MathHelper.func_76126_a(f) * (float)numberOfMoss / 8.0F);
      double d1 = (double)((float)(i + 8) - MathHelper.func_76126_a(f) * (float)numberOfMoss / 8.0F);
      double d2 = (double)((float)(k + 8) + MathHelper.func_76134_b(f) * (float)numberOfMoss / 8.0F);
      double d3 = (double)((float)(k + 8) - MathHelper.func_76134_b(f) * (float)numberOfMoss / 8.0F);

      for(int l = 0; l <= numberOfMoss; ++l) {
         double d5 = d + (d1 - d) * (double)l / (double)numberOfMoss;
         double d6 = d2 + (d3 - d2) * (double)l / (double)numberOfMoss;
         double d7 = random.nextDouble() * (double)numberOfMoss / 16.0D;
         double d8 = (double)(MathHelper.func_76126_a((float)l * 3.1415927F / (float)numberOfMoss) + 1.0F) * d7 + 1.0D;
         int i1 = MathHelper.func_76128_c(d5 - d8 / 2.0D);
         int k1 = MathHelper.func_76128_c(d6 - d8 / 2.0D);
         int i2 = MathHelper.func_76128_c(d5 + d8 / 2.0D);
         int k2 = MathHelper.func_76128_c(d6 + d8 / 2.0D);

         for(int i3 = i1; i3 <= i2; ++i3) {
            double d9 = ((double)i3 + 0.5D - d5) / (d8 / 2.0D);
            if (d9 * d9 < 1.0D) {
               for(int k3 = k1; k3 <= k2; ++k3) {
                  int j1 = world.func_72976_f(i3, k3);
                  if (j1 == j) {
                     double d10 = ((double)k3 + 0.5D - d6) / (d8 / 2.0D);
                     if (d9 * d9 + d10 * d10 < 1.0D && LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i3, j1 - 1, k3) && world.func_72805_g(i3, j1 - 1, k3) == 0 && world.func_147437_c(i3, j1, k3)) {
                        world.func_147465_d(i3, j1, k3, LOTRMod.mordorMoss, 0, 2);
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}
