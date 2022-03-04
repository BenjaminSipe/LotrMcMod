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

public class LOTRWorldGenLairelosse extends WorldGenAbstractTree {
   private int minHeight = 5;
   private int maxHeight = 8;
   private int extraTrunk = 0;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenLairelosse(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood7;
      this.woodMeta = 2;
      this.leafBlock = LOTRMod.leaves7;
      this.leafMeta = 2;
   }

   public LOTRWorldGenLairelosse setMinMaxHeight(int min, int max) {
      this.minHeight = min;
      this.maxHeight = max;
      return this;
   }

   public LOTRWorldGenLairelosse setExtraTrunkWidth(int i) {
      this.extraTrunk = i;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      int leafStart = j + 1 + this.extraTrunk + random.nextInt(3);
      int leafTop = j + height + 1;
      boolean flag = true;
      if (j >= 1 && j + height + 1 <= 256) {
         int k1;
         int j1;
         for(int j1 = j; j1 <= j + height + 1; ++j1) {
            int range = 1;
            if (j1 == j) {
               range = 0;
            }

            if (j1 >= leafStart) {
               range = 2;
            }

            for(k1 = i - range; k1 <= i + this.extraTrunk + range && flag; ++k1) {
               for(j1 = k - range; j1 <= k + this.extraTrunk + range && flag; ++j1) {
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
            boolean canGrow = true;

            int leafRange;
            Block below;
            for(leafRange = i; leafRange <= i + this.extraTrunk && canGrow; ++leafRange) {
               for(k1 = k; k1 <= k + this.extraTrunk && canGrow; ++k1) {
                  below = world.func_147439_a(leafRange, j - 1, k1);
                  if (!below.canSustainPlant(world, leafRange, j - 1, k1, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
                     canGrow = false;
                  }
               }
            }

            if (!canGrow) {
               return false;
            } else {
               for(leafRange = i; leafRange <= i + this.extraTrunk; ++leafRange) {
                  for(k1 = k; k1 <= k + this.extraTrunk; ++k1) {
                     below = world.func_147439_a(leafRange, j - 1, k1);
                     below.onPlantGrow(world, leafRange, j - 1, k1, leafRange, j, k1);
                  }
               }

               leafRange = 0;
               int maxRange = 2;

               int i1;
               int k1;
               for(j1 = leafTop; j1 >= leafStart; --j1) {
                  if (j1 >= leafTop - 1) {
                     leafRange = 0;
                  } else {
                     ++leafRange;
                     if (leafRange > 2) {
                        leafRange = 1;
                     }
                  }

                  for(i1 = i - maxRange; i1 <= i + this.extraTrunk + maxRange; ++i1) {
                     for(k1 = k - maxRange; k1 <= k + this.extraTrunk + maxRange; ++k1) {
                        int i2 = Math.abs(i1 - i);
                        int k2 = Math.abs(k1 - k);
                        if (i1 > i) {
                           i2 -= this.extraTrunk;
                        }

                        if (k1 > k) {
                           k2 -= this.extraTrunk;
                        }

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
                  for(i1 = i; i1 <= i + this.extraTrunk; ++i1) {
                     for(k1 = k; k1 <= k + this.extraTrunk; ++k1) {
                        this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta);
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
