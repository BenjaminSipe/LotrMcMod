package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenDesertTrees extends WorldGenAbstractTree {
   private boolean isNatural;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenDesertTrees(boolean flag, Block b1, int m1, Block b2, int m2) {
      super(flag);
      this.isNatural = !flag;
      this.woodBlock = b1;
      this.woodMeta = m1;
      this.leafBlock = b2;
      this.leafMeta = m2;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = 3 + random.nextInt(3);
      boolean flag = true;
      int branch;
      int k1;
      if (!this.isNatural) {
         if (j >= 1 && height + 1 <= 256) {
            for(int j1 = j; j1 <= j + height + 1; ++j1) {
               int range = 1;
               if (j1 == j) {
                  range = 0;
               }

               if (j1 >= j + height - 1) {
                  range = 2;
               }

               for(branch = i - range; branch <= i + range && flag; ++branch) {
                  for(k1 = k - range; k1 <= k + range && flag; ++k1) {
                     if (j1 >= 0 && j1 < 256) {
                        if (!this.isReplaceable(world, branch, j1, k1)) {
                           flag = false;
                        }
                     } else {
                        flag = false;
                     }
                  }
               }
            }
         } else {
            flag = false;
         }
      }

      Block below = world.func_147439_a(i, j - 1, k);
      boolean isSoil = below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g) || this.isNatural && (below == Blocks.field_150354_m || below == Blocks.field_150348_b);
      if (!isSoil) {
         flag = false;
      }

      if (!flag) {
         return false;
      } else {
         below.onPlantGrow(world, i, j - 1, k, i, j, k);

         for(branch = 0; branch < 4; ++branch) {
            k1 = 1 + random.nextInt(3);
            int i1 = i;
            int j1 = j + height - 1 - random.nextInt(2);
            int k1 = k;

            for(int l = 0; l < k1; ++l) {
               if (random.nextInt(3) != 0) {
                  ++j1;
               }

               if (random.nextInt(3) != 0) {
                  switch(branch) {
                  case 0:
                     --i1;
                     break;
                  case 1:
                     ++k1;
                     break;
                  case 2:
                     ++i1;
                     break;
                  case 3:
                     --k1;
                  }
               }

               if (!this.isReplaceable(world, i1, j1, k1)) {
                  break;
               }

               this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta);
            }

            byte leafStart = 1;
            byte leafRangeMin = 0;

            for(int j2 = j1 - leafStart; j2 <= j1 + 1; ++j2) {
               int j3 = j2 - j1;
               int leafRange = leafRangeMin + 1 - j3 / 2;

               for(int i2 = i1 - leafRange; i2 <= i1 + leafRange; ++i2) {
                  int i3 = i2 - i1;

                  for(int k2 = k1 - leafRange; k2 <= k1 + leafRange; ++k2) {
                     int k3 = k2 - k1;
                     if (Math.abs(i3) != leafRange || Math.abs(k3) != leafRange || random.nextInt(2) != 0 && j3 != 0) {
                        Block block = world.func_147439_a(i2, j2, k2);
                        if (block.isReplaceable(world, i2, j2, k2) || block.isLeaves(world, i2, j2, k2)) {
                           this.func_150516_a(world, i2, j2, k2, this.leafBlock, this.leafMeta);
                        }
                     }
                  }
               }
            }
         }

         for(branch = j; branch < j + height; ++branch) {
            this.func_150516_a(world, i, branch, k, this.woodBlock, this.woodMeta);
         }

         return true;
      }
   }
}
