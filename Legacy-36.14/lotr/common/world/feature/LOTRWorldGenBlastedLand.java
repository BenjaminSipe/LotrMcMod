package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenBlastedLand extends WorldGenerator {
   private boolean aflame;

   public LOTRWorldGenBlastedLand(boolean flag) {
      this.aflame = flag;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      Block block = world.func_147439_a(i, j - 1, k);
      if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150348_b) {
         return false;
      } else {
         int radius = 5 + random.nextInt(8);

         for(int i1 = i - radius; i1 <= i + radius; ++i1) {
            for(int j1 = j - radius / 2; j1 <= j + radius / 2; ++j1) {
               for(int k1 = k - radius; k1 <= k + radius; ++k1) {
                  Block block2 = world.func_147439_a(i1, j1, k1);
                  if (block2 == Blocks.field_150349_c || block2 == Blocks.field_150346_d || block2 == Blocks.field_150348_b) {
                     int i2 = i1 - i;
                     int j2 = j1 - j;
                     int k2 = k1 - k;
                     double d = Math.sqrt((double)(i2 * i2 + j2 * j2 + k2 * k2));
                     int chance = MathHelper.func_76128_c(d / 2.0D);
                     if (chance < 1) {
                        chance = 1;
                     }

                     if (random.nextInt(chance) == 0) {
                        world.func_147465_d(i1, j1, k1, LOTRMod.wasteBlock, 0, 2);
                     }

                     if (this.aflame && d < (double)radius / 2.0D && random.nextInt(10) == 0 && !world.func_147439_a(i1, j1 + 1, k1).func_149662_c()) {
                        world.func_147465_d(i1, j1, k1, LOTRMod.wasteBlock, 0, 2);
                        world.func_147465_d(i1, j1 + 1, k1, Blocks.field_150480_ab, 0, 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
