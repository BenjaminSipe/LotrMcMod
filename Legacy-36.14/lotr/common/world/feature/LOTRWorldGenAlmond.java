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

public class LOTRWorldGenAlmond extends WorldGenAbstractTree {
   private int minHeight = 4;
   private int maxHeight = 5;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenAlmond(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood7;
      this.woodMeta = 3;
      this.leafBlock = LOTRMod.leaves7;
      this.leafMeta = 3;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      int leafStart = j + height - 3;
      int leafTop = j + height;
      boolean flag = true;
      if (j >= 1 && j + height + 1 <= 256) {
         int j1;
         for(int j1 = j; j1 <= j + height + 1; ++j1) {
            int range = 1;
            if (j1 == j) {
               range = 0;
            }

            if (j1 >= leafStart) {
               range = 2;
            }

            for(j1 = i - range; j1 <= i + range && flag; ++j1) {
               for(int k1 = k - range; k1 <= k + range && flag; ++k1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, j1, j1, k1)) {
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
            boolean canGrow = true;
            Block below = world.func_147439_a(i, j - 1, k);
            if (!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
               canGrow = false;
            }

            if (!canGrow) {
               return false;
            } else {
               below = world.func_147439_a(i, j - 1, k);
               below.onPlantGrow(world, i, j - 1, k, i, j, k);

               for(j1 = leafStart; j1 <= leafTop; ++j1) {
                  int leafRange = false;
                  int maxRange = 2;
                  int j2 = leafTop - j1;
                  byte leafRange;
                  if (j2 == 0) {
                     leafRange = 1;
                  } else if (j2 == 1) {
                     leafRange = 2;
                  } else if (j2 == 2) {
                     leafRange = 3;
                  } else {
                     leafRange = 1;
                  }

                  for(int i1 = i - maxRange; i1 <= i + maxRange; ++i1) {
                     for(int k1 = k - maxRange; k1 <= k + maxRange; ++k1) {
                        int i2 = Math.abs(i1 - i);
                        int k2 = Math.abs(k1 - k);
                        if (i2 + k2 <= leafRange) {
                           Block block = world.func_147439_a(i1, j1, k1);
                           if (block.isReplaceable(world, i1, j1, k1) || block.isLeaves(world, i1, j1, k1)) {
                              this.func_150516_a(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                           }
                        }
                     }
                  }
               }

               for(j1 = j; j1 < j + height; ++j1) {
                  Block block = world.func_147439_a(i, j1, k);
                  if (block.isReplaceable(world, i, j1, k) || block.isLeaves(world, i, j1, k)) {
                     this.func_150516_a(world, i, j1, k, this.woodBlock, this.woodMeta);
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
