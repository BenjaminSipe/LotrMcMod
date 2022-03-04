package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenHolly extends WorldGenAbstractTree {
   private int extraTrunkWidth = 0;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenHolly(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood2;
      this.woodMeta = 2;
      this.leafBlock = LOTRMod.leaves2;
      this.leafMeta = 2;
   }

   public LOTRWorldGenHolly setLarge() {
      this.extraTrunkWidth = 1;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = 9 + random.nextInt(6);
      if (this.extraTrunkWidth > 0) {
         height += 10 + random.nextInt(4);
      }

      boolean flag = true;
      if (j >= 1 && j + height + 1 <= 256) {
         int leafStop;
         int j1;
         int i1;
         for(int j1 = j; j1 <= j + 1 + height; ++j1) {
            leafStop = 1;
            if (j1 == j) {
               leafStop = 0;
            }

            if (j1 > j + 2 && j1 < j + height - 2) {
               leafStop = 2;
               if (this.extraTrunkWidth > 0) {
                  ++leafStop;
               }
            }

            for(j1 = i - leafStop; j1 <= i + leafStop + this.extraTrunkWidth && flag; ++j1) {
               for(i1 = k - leafStop; i1 <= k + leafStop + this.extraTrunkWidth && flag; ++i1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, j1, j1, i1)) {
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

            for(leafStop = i; leafStop <= i + this.extraTrunkWidth && flag1; ++leafStop) {
               for(j1 = k; j1 <= k + this.extraTrunkWidth && flag1; ++j1) {
                  Block block = world.func_147439_a(leafStop, j - 1, j1);
                  if (!block.canSustainPlant(world, leafStop, j - 1, j1, ForgeDirection.UP, (IPlantable)LOTRMod.sapling2)) {
                     flag1 = false;
                  }
               }
            }

            if (!flag1) {
               return false;
            } else {
               for(leafStop = i; leafStop <= i + this.extraTrunkWidth; ++leafStop) {
                  for(j1 = k; j1 <= k + this.extraTrunkWidth; ++j1) {
                     world.func_147439_a(leafStop, j - 1, j1).onPlantGrow(world, leafStop, j - 1, j1, leafStop, j, j1);
                  }
               }

               leafStop = 2 + random.nextInt(2);

               int k1;
               for(j1 = height; j1 > leafStop; --j1) {
                  if (j1 == height) {
                     for(i1 = 0; i1 <= this.extraTrunkWidth; ++i1) {
                        for(k1 = 0; k1 <= this.extraTrunkWidth; ++k1) {
                           this.growLeaves(world, i + i1, j + j1, k + k1);
                        }
                     }
                  } else {
                     int i2;
                     int k2;
                     if (j1 <= height - 3 && j1 != leafStop + 1) {
                        for(i1 = -3; i1 <= 3 + this.extraTrunkWidth; ++i1) {
                           for(k1 = -3; k1 <= 3 + this.extraTrunkWidth; ++k1) {
                              i2 = i1;
                              if (i1 > 0) {
                                 i2 = i1 - this.extraTrunkWidth;
                              }

                              k2 = k1;
                              if (k1 > 0) {
                                 k2 = k1 - this.extraTrunkWidth;
                              }

                              if ((j1 % 2 == 0 || Math.abs(i2) != 2 || Math.abs(k2) != 2) && (Math.abs(i2) < 3 && Math.abs(k2) < 3 || this.extraTrunkWidth > 0 && j1 % 2 == 0 && (i2 == 0 || k2 == 0))) {
                                 this.growLeaves(world, i + i1, j + j1, k + k1);
                              }
                           }
                        }
                     } else {
                        for(i1 = -1; i1 <= 1 + this.extraTrunkWidth; ++i1) {
                           for(k1 = -1; k1 <= 1 + this.extraTrunkWidth; ++k1) {
                              i2 = i1;
                              if (i1 > 0) {
                                 i2 = i1 - this.extraTrunkWidth;
                              }

                              k2 = k1;
                              if (k1 > 0) {
                                 k2 = k1 - this.extraTrunkWidth;
                              }

                              if (j1 != height - 1 || Math.abs(i2) != 1 || Math.abs(k2) != 1) {
                                 this.growLeaves(world, i + i1, j + j1, k + k1);
                              }
                           }
                        }
                     }
                  }
               }

               for(j1 = 0; j1 < height; ++j1) {
                  for(i1 = 0; i1 <= this.extraTrunkWidth; ++i1) {
                     for(k1 = 0; k1 <= this.extraTrunkWidth; ++k1) {
                        world.func_147439_a(i + i1, j + j1, k + k1);
                        if (this.isReplaceable(world, i + i1, j + j1, k + k1)) {
                           this.func_150516_a(world, i + i1, j + j1, k + k1, this.woodBlock, this.woodMeta);
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

   private void growLeaves(World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j, k);
      if (block.isReplaceable(world, i, j, k) || block.isLeaves(world, i, j, k)) {
         this.func_150516_a(world, i, j, k, this.leafBlock, this.leafMeta);
      }

   }
}
