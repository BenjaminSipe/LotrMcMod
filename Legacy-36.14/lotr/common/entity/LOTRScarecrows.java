package lotr.common.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockSkull;
import net.minecraft.world.World;

public class LOTRScarecrows {
   private static int RANGE = 16;
   private static int Y_RANGE = 8;

   public static boolean anyScarecrowsNearby(World world, int i, int j, int k) {
      for(int i1 = i - RANGE; i1 <= i + RANGE; ++i1) {
         for(int k1 = k - RANGE; k1 <= k + RANGE; ++k1) {
            for(int j1 = j - Y_RANGE; j1 <= j + Y_RANGE; ++j1) {
               if (isScarecrowBase(world, i1, j1, k1)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static boolean isScarecrowBase(World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j, k);
      if (block instanceof BlockFence) {
         for(int j1 = j + 2; j1 <= j + 6; ++j1) {
            Block above = world.func_147439_a(i, j1, k);
            if (above instanceof BlockPumpkin || above instanceof BlockSkull) {
               Block belowAbove = world.func_147439_a(i, j1 - 1, k);
               if (belowAbove.func_149721_r()) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
