package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenBushes extends WorldGenerator {
   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      Block leafBlock = null;
      int leafMeta = 0;

      int size;
      int i1;
      int k1;
      int k2;
      for(int l = 0; l < 40; ++l) {
         size = i - random.nextInt(6) + random.nextInt(6);
         i1 = j + random.nextInt(12);
         k1 = k - random.nextInt(6) + random.nextInt(6);
         Block block = world.func_147439_a(size, i1, k1);
         if (block instanceof BlockLeavesBase) {
            k2 = world.func_72805_g(size, i1, k1);
            leafBlock = block;
            leafMeta = k2;
            break;
         }
      }

      if (leafBlock == null) {
         return false;
      } else {
         Block below = world.func_147439_a(i, j - 1, k);
         if (below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
            size = 0;
            if (random.nextInt(3) == 0) {
               ++size;
            }

            for(i1 = -size; i1 <= size; ++i1) {
               for(k1 = -size; k1 <= size; ++k1) {
                  int i2 = i + i1;
                  k2 = k + k1;
                  if (size == 0 || Math.abs(i1) != size || Math.abs(k1) != size || random.nextInt(3) == 0) {
                     Block block = world.func_147439_a(i2, j, k2);
                     if (!block.func_149688_o().func_76224_d() && block.isReplaceable(world, i2, j, k2)) {
                        world.func_147465_d(i2, j, k2, leafBlock, leafMeta | 4, 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
