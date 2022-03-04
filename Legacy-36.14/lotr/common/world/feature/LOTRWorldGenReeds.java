package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenReeds extends WorldGenerator {
   private Block reedBlock;

   public LOTRWorldGenReeds(Block block) {
      this.reedBlock = block;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      label46:
      for(int l = 0; l < 16; ++l) {
         int i1 = i + random.nextInt(8) - random.nextInt(8);
         int j1 = j + random.nextInt(4) - random.nextInt(4);
         int k1 = k + random.nextInt(8) - random.nextInt(8);
         int maxDepth = 5;

         int reedHeight;
         for(reedHeight = j1 - 1; reedHeight > 0 && world.func_147439_a(i1, reedHeight, k1).func_149688_o() == Material.field_151586_h; --reedHeight) {
            if (reedHeight < j1 - maxDepth) {
               continue label46;
            }
         }

         if (!world.func_72884_u(i1, j1 - 1, k1)) {
            reedHeight = 1 + random.nextInt(3);

            for(int j2 = j1; j2 < j1 + reedHeight; ++j2) {
               if (world.func_147437_c(i1, j2, k1) && this.reedBlock.func_149718_j(world, i1, j2, k1)) {
                  world.func_147465_d(i1, j2, k1, this.reedBlock, 0, 2);
               }
            }
         }
      }

      return true;
   }
}
