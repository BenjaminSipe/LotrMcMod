package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenNurnWheatFarm extends LOTRWorldGenNurnFarmBase {
   public LOTRWorldGenNurnWheatFarm(boolean flag) {
      super(flag);
   }

   public void generateCrops(World world, Random random, int i, int j, int k) {
      for(int i1 = i - 4; i1 <= i + 4; ++i1) {
         for(int k1 = k - 4; k1 <= k + 4; ++k1) {
            if (Math.abs(i1 - i) == 4 && Math.abs(k1 - k) == 4) {
               this.func_150516_a(world, i1, j + 1, k1, LOTRMod.brick, 0);
               this.func_150516_a(world, i1, j + 2, k1, LOTRMod.brick, 0);
               this.func_150516_a(world, i1, j + 3, k1, LOTRMod.fence, 3);
               this.func_150516_a(world, i1, j + 4, k1, Blocks.field_150325_L, 12);
               this.placeSkull(world, random, i1, j + 5, k1);
            } else if (Math.abs(i1 - i) <= 1 && Math.abs(k1 - k) <= 1) {
               this.func_150516_a(world, i1, j + 1, k1, LOTRMod.brick, 0);
               if (Math.abs(i1 - i) != 0 && Math.abs(k1 - k) != 0) {
                  this.placeOrcTorch(world, i1, j + 2, k1);
               }
            } else if (i1 != i && k1 != k) {
               this.func_150516_a(world, i1, j, k1, Blocks.field_150458_ak, 7);
               this.func_150516_a(world, i1, j + 1, k1, Blocks.field_150464_aj, 7);
            } else if (Math.abs(i1 - i) <= 3 && Math.abs(k1 - k) <= 3) {
               this.func_150516_a(world, i1, j, k1, Blocks.field_150355_j, 0);
            }
         }
      }

      this.func_150516_a(world, i, j + 1, k, LOTRMod.morgulTable, 0);
   }
}
