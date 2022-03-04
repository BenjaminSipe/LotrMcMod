package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenCharredTrees extends WorldGenAbstractTree {
   public LOTRWorldGenCharredTrees() {
      super(false);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      Block below = world.func_147439_a(i, j - 1, k);
      world.func_72805_g(i, j - 1, k);
      if (!LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k) && below != Blocks.field_150348_b && below != Blocks.field_150354_m && below != Blocks.field_150351_n && !below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (BlockSapling)Blocks.field_150345_g)) {
         return false;
      } else {
         below.onPlantGrow(world, i, j - 1, k, i, j, k);
         int height = 2 + random.nextInt(5);

         int branch;
         for(branch = j; branch < j + height; ++branch) {
            world.func_147465_d(i, branch, k, LOTRMod.wood, 3, 2);
         }

         if (height >= 4) {
            for(branch = 0; branch < 4; ++branch) {
               int branchLength = 2 + random.nextInt(4);
               int branchHorizontalPos = 0;
               int branchVerticalPos = j + height - random.nextInt(2);

               for(int l = 0; l < branchLength; ++l) {
                  if (random.nextInt(4) == 0) {
                     ++branchHorizontalPos;
                  }

                  if (random.nextInt(3) != 0) {
                     ++branchVerticalPos;
                  }

                  switch(branch) {
                  case 0:
                     world.func_147465_d(i - branchHorizontalPos, branchVerticalPos, k, LOTRMod.wood, 15, 2);
                     break;
                  case 1:
                     world.func_147465_d(i, branchVerticalPos, k + branchHorizontalPos, LOTRMod.wood, 15, 2);
                     break;
                  case 2:
                     world.func_147465_d(i + branchHorizontalPos, branchVerticalPos, k, LOTRMod.wood, 15, 2);
                     break;
                  case 3:
                     world.func_147465_d(i, branchVerticalPos, k - branchHorizontalPos, LOTRMod.wood, 15, 2);
                  }
               }
            }
         }

         return true;
      }
   }
}
