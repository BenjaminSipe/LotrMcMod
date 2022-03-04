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

public class LOTRWorldGenOlive extends WorldGenAbstractTree {
   private int minHeight = 4;
   private int maxHeight = 5;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;
   private int extraTrunk;

   public LOTRWorldGenOlive(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood6;
      this.woodMeta = 3;
      this.leafBlock = LOTRMod.leaves6;
      this.leafMeta = 3;
      this.extraTrunk = 0;
   }

   public LOTRWorldGenOlive setMinMaxHeight(int min, int max) {
      this.minHeight = min;
      this.maxHeight = max;
      return this;
   }

   public LOTRWorldGenOlive setBlocks(Block b1, int m1, Block b2, int m2) {
      this.woodBlock = b1;
      this.woodMeta = m1;
      this.leafBlock = b2;
      this.leafMeta = m2;
      return this;
   }

   public LOTRWorldGenOlive setExtraTrunkWidth(int w) {
      this.extraTrunk = w;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      int leafStart = j + height - 3 + random.nextInt(2);
      int leafTop = j + height;
      boolean flag = true;
      if (j >= 1 && j + height + 1 <= 256) {
         int k1;
         int i2;
         for(int j1 = j; j1 <= j + height + 1; ++j1) {
            int range = 1;
            if (j1 == j) {
               range = 0;
            }

            if (j1 >= leafStart) {
               range = 2;
            }

            for(k1 = i - range; k1 <= i + this.extraTrunk + range && flag; ++k1) {
               for(i2 = k - range; i2 <= k + this.extraTrunk + range && flag; ++i2) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, k1, j1, i2)) {
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

            int i1;
            Block below;
            for(i1 = i; i1 <= i + this.extraTrunk && canGrow; ++i1) {
               for(k1 = k; k1 <= k + this.extraTrunk && canGrow; ++k1) {
                  below = world.func_147439_a(i1, j - 1, k1);
                  if (!below.canSustainPlant(world, i1, j - 1, k1, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
                     canGrow = false;
                  }
               }
            }

            if (!canGrow) {
               return false;
            } else {
               for(i1 = i; i1 <= i + this.extraTrunk; ++i1) {
                  for(k1 = k; k1 <= k + this.extraTrunk; ++k1) {
                     below = world.func_147439_a(i1, j - 1, k1);
                     below.onPlantGrow(world, i1, j - 1, k1, i1, j, k1);
                  }
               }

               int k2;
               int rootX;
               int rootY;
               int rootZ;
               for(i1 = leafStart; i1 <= leafTop; ++i1) {
                  int leafRange = false;
                  byte leafRange;
                  if (i1 == leafTop) {
                     leafRange = 2;
                  } else if (i1 == leafStart) {
                     leafRange = 1;
                  } else {
                     leafRange = 3;
                  }

                  for(i2 = i - leafRange; i2 <= i + this.extraTrunk + leafRange; ++i2) {
                     for(k2 = k - leafRange; k2 <= k + this.extraTrunk + leafRange; ++k2) {
                        rootX = Math.abs(i2 - i);
                        rootY = Math.abs(k2 - k);
                        if (this.extraTrunk > 0) {
                           if (i2 > i) {
                              rootX -= this.extraTrunk;
                           }

                           if (k2 > k) {
                              rootY -= this.extraTrunk;
                           }
                        }

                        rootZ = rootX + rootY;
                        if (rootZ <= 4 && (rootX < leafRange && rootY < leafRange || random.nextInt(3) != 0)) {
                           Block block = world.func_147439_a(i2, i1, k2);
                           if (block.isReplaceable(world, i2, i1, k2) || block.isLeaves(world, i2, i1, k2)) {
                              this.func_150516_a(world, i2, i1, k2, this.leafBlock, this.leafMeta);
                           }
                        }
                     }
                  }
               }

               for(i1 = j; i1 < j + height; ++i1) {
                  for(k1 = i; k1 <= i + this.extraTrunk; ++k1) {
                     for(i2 = k; i2 <= k + this.extraTrunk; ++i2) {
                        this.func_150516_a(world, k1, i1, i2, this.woodBlock, this.woodMeta);
                     }
                  }
               }

               if (this.extraTrunk > 0) {
                  for(i1 = i - 1; i1 <= i + this.extraTrunk + 1; ++i1) {
                     for(k1 = k - 1; k1 <= k + this.extraTrunk + 1; ++k1) {
                        i2 = Math.abs(i1 - i);
                        k2 = Math.abs(k1 - k);
                        if (this.extraTrunk > 0) {
                           if (i1 > i) {
                              i2 -= this.extraTrunk;
                           }

                           if (k1 > k) {
                              k2 -= this.extraTrunk;
                           }
                        }

                        if (random.nextInt(4) == 0) {
                           rootX = i1;
                           rootY = j + random.nextInt(2);
                           rootZ = k1;
                           int roots = 0;

                           while(world.func_147439_a(rootX, rootY, k1).isReplaceable(world, rootX, rootY, rootZ)) {
                              this.func_150516_a(world, rootX, rootY, rootZ, this.woodBlock, this.woodMeta | 12);
                              world.func_147439_a(rootX, rootY - 1, rootZ).onPlantGrow(world, rootX, rootY - 1, rootZ, rootX, rootY, rootZ);
                              --rootY;
                              ++roots;
                              if (roots > 4 + random.nextInt(3)) {
                                 break;
                              }
                           }
                        }

                        if (random.nextInt(4) == 0 && (i2 == 0 || k2 == 0)) {
                           Block block = world.func_147439_a(i1, leafStart, k1);
                           if (block.isReplaceable(world, i1, leafStart, k1) || block.isLeaves(world, i1, leafStart, k1)) {
                              this.func_150516_a(world, i1, leafStart, k1, this.woodBlock, this.woodMeta);
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
