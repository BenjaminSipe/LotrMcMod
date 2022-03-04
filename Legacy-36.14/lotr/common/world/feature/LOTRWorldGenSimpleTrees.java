package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenSimpleTrees extends WorldGenAbstractTree {
   private int minHeight;
   private int maxHeight;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;
   private int extraTrunkWidth;

   public LOTRWorldGenSimpleTrees(boolean flag, int i, int j, Block k, int l, Block i1, int j1) {
      super(flag);
      this.minHeight = i;
      this.maxHeight = j;
      this.woodBlock = k;
      this.woodMeta = l;
      this.leafBlock = i1;
      this.leafMeta = j1;
   }

   public LOTRWorldGenSimpleTrees setTrunkWidth(int i) {
      this.extraTrunkWidth = i - 1;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      boolean flag = true;
      if (j >= 1 && j + height + 1 <= 256) {
         byte leafStart;
         int k1;
         int j1;
         for(int j1 = j; j1 <= j + 1 + height; ++j1) {
            leafStart = 1;
            if (j1 == j) {
               leafStart = 0;
            }

            if (j1 >= j + 1 + height - 2) {
               leafStart = 2;
            }

            for(k1 = i - leafStart; k1 <= i + leafStart + this.extraTrunkWidth && flag; ++k1) {
               for(j1 = k - leafStart; j1 <= k + leafStart + this.extraTrunkWidth && flag; ++j1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, k1, j1, j1)) {
                        flag = false;
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }

         if (!flag) {
            return false;
         } else {
            boolean flag1 = true;

            int i1;
            for(i1 = i; i1 <= i + this.extraTrunkWidth && flag1; ++i1) {
               for(k1 = k; k1 <= k + this.extraTrunkWidth && flag1; ++k1) {
                  Block block = world.func_147439_a(i1, j - 1, k1);
                  if (!block.canSustainPlant(world, i1, j - 1, k1, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
                     flag1 = false;
                  }
               }
            }

            if (!flag1) {
               return false;
            } else {
               for(i1 = i; i1 <= i + this.extraTrunkWidth; ++i1) {
                  for(k1 = k; k1 <= k + this.extraTrunkWidth; ++k1) {
                     world.func_147439_a(i1, j - 1, k1).onPlantGrow(world, i1, j - 1, k1, i1, j, k1);
                  }
               }

               leafStart = 3;
               byte leafRangeMin = 0;

               int j2;
               int leafRange;
               for(j1 = j - leafStart + height; j1 <= j + height; ++j1) {
                  j2 = j1 - (j + height);
                  leafRange = leafRangeMin + 1 - j2 / 2;

                  for(int i1 = i - leafRange; i1 <= i + leafRange + this.extraTrunkWidth; ++i1) {
                     for(int k1 = k - leafRange; k1 <= k + leafRange + this.extraTrunkWidth; ++k1) {
                        int i2 = i1 - i;
                        int k2 = k1 - k;
                        if (i2 > 0) {
                           i2 -= this.extraTrunkWidth;
                        }

                        if (k2 > 0) {
                           k2 -= this.extraTrunkWidth;
                        }

                        Block block = world.func_147439_a(i1, j1, k1);
                        if ((Math.abs(i2) != leafRange || Math.abs(k2) != leafRange || random.nextInt(2) != 0 && j2 != 0) && (block.isReplaceable(world, i1, j1, k1) || block.isLeaves(world, i1, j1, k1))) {
                           this.func_150516_a(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                        }
                     }
                  }
               }

               for(j1 = j; j1 < j + height; ++j1) {
                  for(j2 = i; j2 <= i + this.extraTrunkWidth; ++j2) {
                     for(leafRange = k; leafRange <= k + this.extraTrunkWidth; ++leafRange) {
                        Block block = world.func_147439_a(j2, j1, leafRange);
                        if (block.isReplaceable(world, j2, j1, leafRange) || block.isLeaves(world, j2, j1, leafRange)) {
                           this.func_150516_a(world, j2, j1, leafRange, this.woodBlock, this.woodMeta);
                        }
                     }
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }
}
