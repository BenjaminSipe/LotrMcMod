package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenClover extends WorldGenerator {
   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      Block block = null;

      do {
         block = world.func_147439_a(i, j, k);
         if (!block.isAir(world, i, j, k) && !block.isLeaves(world, i, j, k)) {
            break;
         }

         --j;
      } while(j > 0);

      for(int l = 0; l < 128; ++l) {
         int i1 = i + random.nextInt(8) - random.nextInt(8);
         int j1 = j + random.nextInt(4) - random.nextInt(4);
         int k1 = k + random.nextInt(8) - random.nextInt(8);
         if (world.func_147437_c(i1, j1, k1) && LOTRMod.clover.func_149718_j(world, i1, j1, k1)) {
            if (random.nextInt(500) == 0) {
               world.func_147465_d(i1, j1, k1, LOTRMod.clover, 1, 2);
            } else {
               world.func_147465_d(i1, j1, k1, LOTRMod.clover, 0, 2);
            }
         }
      }

      return true;
   }
}
