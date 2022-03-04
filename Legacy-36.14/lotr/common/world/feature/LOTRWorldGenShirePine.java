package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenShirePine extends WorldGenAbstractTree {
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;
   private int minHeight;
   private int maxHeight;

   public LOTRWorldGenShirePine(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood;
      this.woodMeta = 0;
      this.leafBlock = LOTRMod.leaves;
      this.leafMeta = 0;
      this.minHeight = 10;
      this.maxHeight = 20;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      int leafHeight = 6 + random.nextInt(4);
      int minLeafHeight = j + height - leafHeight;
      int maxLeafWidth = 2 + random.nextInt(2);
      boolean flag = true;
      if (j >= 1 && j + height + 1 <= 256) {
         int leafWidthLimit;
         int leafWidth;
         for(int j1 = j; j1 <= j + 1 + height && flag; ++j1) {
            int checkRange = false;
            if (j1 < minLeafHeight) {
               leafWidth = 0;
            } else {
               leafWidth = maxLeafWidth;
            }

            for(leafWidthLimit = i - leafWidth; leafWidthLimit <= i + leafWidth && flag; ++leafWidthLimit) {
               for(int k1 = k - leafWidth; k1 <= k + leafWidth && flag; ++k1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, leafWidthLimit, j1, k1)) {
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
            Block below = world.func_147439_a(i, j - 1, k);
            if (!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
               return false;
            } else {
               below.onPlantGrow(world, i, j - 1, k, i, j, k);
               leafWidth = random.nextInt(2);
               leafWidthLimit = 1;
               int nextLeafWidth = 0;

               int j1;
               int j1;
               int dir;
               int l;
               for(j1 = j + height; j1 >= minLeafHeight; --j1) {
                  for(j1 = i - leafWidth; j1 <= i + leafWidth; ++j1) {
                     for(dir = k - leafWidth; dir <= k + leafWidth; ++dir) {
                        int i2 = j1 - i;
                        l = dir - k;
                        if (leafWidth <= 0 || Math.abs(i2) != leafWidth || Math.abs(l) != leafWidth) {
                           Block block = world.func_147439_a(j1, j1, dir);
                           if (block.isReplaceable(world, j1, j1, dir) || block.isLeaves(world, j1, j1, dir)) {
                              this.func_150516_a(world, j1, j1, dir, this.leafBlock, this.leafMeta);
                           }
                        }
                     }
                  }

                  if (leafWidth >= leafWidthLimit) {
                     leafWidth = nextLeafWidth;
                     nextLeafWidth = 1;
                     ++leafWidthLimit;
                     if (leafWidthLimit > maxLeafWidth) {
                        leafWidthLimit = maxLeafWidth;
                     }
                  } else {
                     ++leafWidth;
                  }
               }

               j1 = -1;

               for(j1 = j; j1 < j + height; ++j1) {
                  this.func_150516_a(world, i, j1, k, this.woodBlock, this.woodMeta);
                  if (j1 >= j + 3 && j1 < minLeafHeight && random.nextInt(3) == 0) {
                     dir = random.nextInt(4);
                     if (dir != j1) {
                        j1 = dir;
                        int length = 1;

                        for(l = 1; l <= length; ++l) {
                           int i1 = i + Direction.field_71583_a[dir] * l;
                           int k1 = k + Direction.field_71581_b[dir] * l;
                           if (!this.isReplaceable(world, i1, j1, k1)) {
                              break;
                           }

                           if (dir != 0 && dir != 2) {
                              this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta | 4);
                           } else {
                              this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta | 8);
                           }
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
