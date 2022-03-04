package lotr.common.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import lotr.common.init.LOTRTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.Feature;

public class MordorMossFeature extends Feature {
   public MordorMossFeature(Codec codec) {
      super(codec);
   }

   public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, MordorMossFeatureConfig config) {
      int numberOfMoss = MathHelper.func_76136_a(rand, config.minSize, config.maxSize);
      float randAngle = rand.nextFloat() * 3.1415927F;
      int x = pos.func_177958_n();
      int z = pos.func_177952_p();
      int y = pos.func_177956_o();
      Mutable movingPos = (new Mutable()).func_189533_g(pos);
      double d = (double)((float)(x + 8) + MathHelper.func_76126_a(randAngle) * (float)numberOfMoss / 8.0F);
      double d1 = (double)((float)(x + 8) - MathHelper.func_76126_a(randAngle) * (float)numberOfMoss / 8.0F);
      double d2 = (double)((float)(z + 8) + MathHelper.func_76134_b(randAngle) * (float)numberOfMoss / 8.0F);
      double d3 = (double)((float)(z + 8) - MathHelper.func_76134_b(randAngle) * (float)numberOfMoss / 8.0F);

      for(int l = 0; l <= numberOfMoss; ++l) {
         double d5 = d + (d1 - d) * (double)l / (double)numberOfMoss;
         double d6 = d2 + (d3 - d2) * (double)l / (double)numberOfMoss;
         double d7 = rand.nextDouble() * (double)numberOfMoss / 16.0D;
         double d8 = (double)(MathHelper.func_76126_a((float)l * 3.1415927F / (float)numberOfMoss) + 1.0F) * d7 + 1.0D;
         int i1 = MathHelper.func_76128_c(d5 - d8 / 2.0D);
         int k1 = MathHelper.func_76128_c(d6 - d8 / 2.0D);
         int i2 = MathHelper.func_76128_c(d5 + d8 / 2.0D);
         int k2 = MathHelper.func_76128_c(d6 + d8 / 2.0D);

         for(int i3 = i1; i3 <= i2; ++i3) {
            double d9 = ((double)i3 + 0.5D - d5) / (d8 / 2.0D);
            if (d9 * d9 < 1.0D) {
               for(int k3 = k1; k3 <= k2; ++k3) {
                  int j1 = world.func_201676_a(Type.WORLD_SURFACE_WG, i3, k3);
                  if (j1 == y) {
                     double d10 = ((double)k3 + 0.5D - d6) / (d8 / 2.0D);
                     if (d9 * d9 + d10 * d10 < 1.0D) {
                        movingPos.func_181079_c(i3, j1, k3);
                        BlockPos below = movingPos.func_177977_b();
                        if (world.func_175623_d(movingPos) && world.func_180495_p(below).func_235714_a_(LOTRTags.Blocks.MORDOR_PLANT_SURFACES)) {
                           world.func_180501_a(movingPos, config.blockState, 2);
                           world.func_180501_a(below, world.func_180495_p(below).func_196956_a(Direction.UP, config.blockState, world, below, movingPos), 2);
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}
