package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenDeadTrees extends WorldGenAbstractTree {
   private Block woodBlock;
   private int woodMeta;

   public LOTRWorldGenDeadTrees(Block block, int i) {
      super(false);
      this.woodBlock = block;
      this.woodMeta = i;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      Block below = world.func_147439_a(i, j - 1, k);
      if (!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g) && below != Blocks.field_150348_b && below != Blocks.field_150354_m && below != Blocks.field_150351_n) {
         return false;
      } else {
         below.onPlantGrow(world, i, j - 1, k, i, j, k);
         int height = 3 + random.nextInt(4);

         int branch;
         for(branch = j; branch < j + height; ++branch) {
            this.func_150516_a(world, i, branch, k, this.woodBlock, this.woodMeta);
         }

         for(branch = 0; branch < 4; ++branch) {
            int branchLength = 3 + random.nextInt(5);
            int branchHorizontalPos = 0;
            int branchVerticalPos = j + height - 1 - random.nextInt(2);

            for(int l = 0; l < branchLength; ++l) {
               if (random.nextInt(4) == 0) {
                  ++branchHorizontalPos;
               }

               if (random.nextInt(3) != 0) {
                  ++branchVerticalPos;
               }

               switch(branch) {
               case 0:
                  this.func_150516_a(world, i - branchHorizontalPos, branchVerticalPos, k, this.woodBlock, this.woodMeta | 12);
                  break;
               case 1:
                  this.func_150516_a(world, i, branchVerticalPos, k + branchHorizontalPos, this.woodBlock, this.woodMeta | 12);
                  break;
               case 2:
                  this.func_150516_a(world, i + branchHorizontalPos, branchVerticalPos, k, this.woodBlock, this.woodMeta | 12);
                  break;
               case 3:
                  this.func_150516_a(world, i, branchVerticalPos, k - branchHorizontalPos, this.woodBlock, this.woodMeta | 12);
               }
            }
         }

         return true;
      }
   }
}
