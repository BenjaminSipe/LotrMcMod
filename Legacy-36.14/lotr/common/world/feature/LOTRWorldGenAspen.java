package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenAspen extends WorldGenAbstractTree {
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;
   private int minHeight;
   private int maxHeight;
   private int extraTrunk;

   public LOTRWorldGenAspen(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood7;
      this.woodMeta = 0;
      this.leafBlock = LOTRMod.leaves7;
      this.leafMeta = 0;
      this.minHeight = 8;
      this.maxHeight = 15;
      this.extraTrunk = 0;
   }

   public LOTRWorldGenAspen setMinMaxHeight(int min, int max) {
      this.minHeight = min;
      this.maxHeight = max;
      return this;
   }

   public LOTRWorldGenAspen setBlocks(Block b1, int m1, Block b2, int m2) {
      this.woodBlock = b1;
      this.woodMeta = m1;
      this.leafBlock = b2;
      this.leafMeta = m2;
      return this;
   }

   public LOTRWorldGenAspen setExtraTrunkWidth(int w) {
      this.extraTrunk = w;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      int leafMin = 3 + random.nextInt(3);
      leafMin = j + leafMin - 1;
      int leafTop = j + height + 1;
      boolean flag = true;
      int i1;
      int k1;
      if (j >= 1 && height + 1 <= 256) {
         for(int j1 = j; j1 <= j + height + 1; ++j1) {
            int range = 1;
            if (j1 == j) {
               range = 0;
            }

            if (j1 >= leafMin) {
               range = 2;
            }

            for(i1 = i - range; i1 <= i + this.extraTrunk + range && flag; ++i1) {
               for(k1 = k - range; k1 <= k + this.extraTrunk + range && flag; ++k1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, i1, j1, k1)) {
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

      if (!flag) {
         return false;
      } else {
         boolean canGrow = true;

         int j1;
         Block below;
         for(j1 = i; j1 <= i + this.extraTrunk && canGrow; ++j1) {
            for(i1 = k; i1 <= k + this.extraTrunk && canGrow; ++i1) {
               below = world.func_147439_a(j1, j - 1, i1);
               if (!below.canSustainPlant(world, j1, j - 1, i1, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
                  canGrow = false;
               }
            }
         }

         if (!canGrow) {
            return false;
         } else {
            for(j1 = i; j1 <= i + this.extraTrunk; ++j1) {
               for(i1 = k; i1 <= k + this.extraTrunk; ++i1) {
                  below = world.func_147439_a(j1, j - 1, i1);
                  below.onPlantGrow(world, j1, j - 1, i1, j1, j, i1);
               }
            }

            for(j1 = leafMin; j1 <= leafTop; ++j1) {
               int leafWidth = 2;
               if (j1 >= leafTop - 1) {
                  leafWidth = 0;
               } else if (j1 >= leafTop - 3 || j1 <= leafMin + 1 || random.nextInt(4) == 0) {
                  leafWidth = 1;
               }

               k1 = 4 + random.nextInt(5);

               for(int b = 0; b < k1; ++b) {
                  int i1 = i;
                  int k1 = k;
                  if (this.extraTrunk > 0) {
                     i1 = i + random.nextInt(this.extraTrunk + 1);
                     k1 = k + random.nextInt(this.extraTrunk + 1);
                  }

                  int i2 = i1;
                  int k2 = k1;
                  int length = 4 + random.nextInt(8);
                  length *= this.extraTrunk + 1;

                  for(int l = 0; l < length && Math.abs(i2 - i1) <= leafWidth && Math.abs(k2 - k1) <= leafWidth; ++l) {
                     Block block = world.func_147439_a(i2, j1, k2);
                     if (!block.isReplaceable(world, i2, j1, k2) && !block.isLeaves(world, i2, j1, k2)) {
                        break;
                     }

                     this.func_150516_a(world, i2, j1, k2, this.leafBlock, this.leafMeta);
                     int dir = random.nextInt(4);
                     if (dir == 0) {
                        --i2;
                     } else if (dir == 1) {
                        ++i2;
                     } else if (dir == 2) {
                        --k2;
                     } else if (dir == 3) {
                        ++k2;
                     }
                  }
               }
            }

            for(j1 = j; j1 < j + height; ++j1) {
               for(i1 = i; i1 <= i + this.extraTrunk; ++i1) {
                  for(k1 = k; k1 <= k + this.extraTrunk; ++k1) {
                     this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta);
                  }
               }
            }

            return true;
         }
      }
   }
}
