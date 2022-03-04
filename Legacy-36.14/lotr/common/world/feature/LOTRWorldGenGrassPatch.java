package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenGrassPatch extends WorldGenerator {
   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (world.func_147439_a(i, j - 1, k) != Blocks.field_150348_b) {
         return false;
      } else {
         int radius = 3 + random.nextInt(3);
         int heightValue = world.func_72976_f(i, k);

         for(int i1 = i - radius; i1 <= i + radius; ++i1) {
            for(int k1 = k - radius; k1 <= k + radius; ++k1) {
               int i2 = i1 - i;
               int k2 = k1 - k;
               if (i2 * i2 + k2 * k2 < radius * radius && world.func_72976_f(i1, k1) == heightValue) {
                  for(int j1 = heightValue - 1; j1 > heightValue - 5; --j1) {
                     Block block = world.func_147439_a(i1, j1, k1);
                     if (block != Blocks.field_150346_d && block != Blocks.field_150348_b) {
                        break;
                     }

                     if (j1 == heightValue - 1) {
                        world.func_147465_d(i1, j1, k1, Blocks.field_150349_c, 0, 2);
                     } else {
                        world.func_147465_d(i1, j1, k1, Blocks.field_150346_d, 0, 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
