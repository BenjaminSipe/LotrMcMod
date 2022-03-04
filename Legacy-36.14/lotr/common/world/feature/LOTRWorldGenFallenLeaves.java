package lotr.common.world.feature;

import java.util.Random;
import lotr.common.block.LOTRBlockFallenLeaves;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenFallenLeaves extends WorldGenerator {
   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      Block fallenLeaf = null;
      int fallenMeta = 0;

      int l;
      int i1;
      int j1;
      int k1;
      for(l = 0; l < 40; ++l) {
         i1 = i - random.nextInt(6) + random.nextInt(6);
         j1 = j + random.nextInt(12);
         k1 = k - random.nextInt(6) + random.nextInt(6);
         Block block = world.func_147439_a(i1, j1, k1);
         if (block instanceof BlockLeavesBase) {
            int meta = world.func_72805_g(i1, j1, k1);
            Object[] obj = LOTRBlockFallenLeaves.fallenBlockMetaFromLeafBlockMeta(block, meta);
            if (obj != null) {
               fallenLeaf = (Block)obj[0];
               fallenMeta = (Integer)obj[1];
               break;
            }
         }
      }

      if (fallenLeaf == null) {
         return false;
      } else {
         for(l = 0; l < 64; ++l) {
            i1 = i - random.nextInt(5) + random.nextInt(5);
            j1 = j - random.nextInt(3) + random.nextInt(3);
            k1 = k - random.nextInt(5) + random.nextInt(5);
            world.func_147439_a(i1, j1 - 1, k1);
            Block block = world.func_147439_a(i1, j1, k1);
            if (fallenLeaf.func_149718_j(world, i1, j1, k1) && !block.func_149688_o().func_76224_d() && block.isReplaceable(world, i1, j1, k1)) {
               world.func_147465_d(i1, j1, k1, fallenLeaf, fallenMeta, 2);
            }
         }

         return true;
      }
   }
}
