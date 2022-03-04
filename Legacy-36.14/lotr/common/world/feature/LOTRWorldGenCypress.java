package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenCypress extends WorldGenAbstractTree {
   private int extraTrunkWidth = 0;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenCypress(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood6;
      this.woodMeta = 2;
      this.leafBlock = LOTRMod.leaves6;
      this.leafMeta = 2;
   }

   public LOTRWorldGenCypress setLarge() {
      this.extraTrunkWidth = 1;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = 8 + random.nextInt(6);
      if (this.extraTrunkWidth > 0) {
         height += 5 + random.nextInt(5);
      }

      boolean flag = true;
      if (j >= 1 && j + height + 1 <= 256) {
         int leafStop;
         int leafStopHeight;
         int j1;
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

            for(leafStopHeight = i - leafStop; leafStopHeight <= i + leafStop + this.extraTrunkWidth && flag; ++leafStopHeight) {
               for(j1 = k - leafStop; j1 <= k + leafStop + this.extraTrunkWidth && flag; ++j1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, leafStopHeight, j1, j1)) {
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

            for(leafStop = i; leafStop <= i + this.extraTrunkWidth && canGrow; ++leafStop) {
               for(leafStopHeight = k; leafStopHeight <= k + this.extraTrunkWidth && canGrow; ++leafStopHeight) {
                  Block block = world.func_147439_a(leafStop, j - 1, leafStopHeight);
                  if (!block.canSustainPlant(world, leafStop, j - 1, leafStopHeight, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
                     canGrow = false;
                  }
               }
            }

            if (!canGrow) {
               return false;
            } else {
               for(leafStop = i; leafStop <= i + this.extraTrunkWidth; ++leafStop) {
                  for(leafStopHeight = k; leafStopHeight <= k + this.extraTrunkWidth; ++leafStopHeight) {
                     world.func_147439_a(leafStop, j - 1, leafStopHeight).onPlantGrow(world, leafStop, j - 1, leafStopHeight, leafStop, j, leafStopHeight);
                  }
               }

               leafStop = 2 + random.nextInt(2);
               leafStopHeight = random.nextInt(3);
               if (this.extraTrunkWidth > 0) {
                  leafStop += 2 + random.nextInt(2);
                  ++leafStopHeight;
               }

               int i1;
               int k1;
               for(j1 = height + 1; j1 > leafStop; --j1) {
                  if (j1 >= height) {
                     for(i1 = 0; i1 <= this.extraTrunkWidth; ++i1) {
                        for(k1 = 0; k1 <= this.extraTrunkWidth; ++k1) {
                           this.growLeaves(world, i + i1, j + j1, k + k1);
                        }
                     }
                  } else if (j1 < height - 2 && j1 > leafStop + leafStopHeight) {
                     for(i1 = -1; i1 <= 1 + this.extraTrunkWidth; ++i1) {
                        for(k1 = -1; k1 <= 1 + this.extraTrunkWidth; ++k1) {
                           this.growLeaves(world, i + i1, j + j1, k + k1);
                        }
                     }
                  } else {
                     for(i1 = -1; i1 <= 1 + this.extraTrunkWidth; ++i1) {
                        for(k1 = -1; k1 <= 1 + this.extraTrunkWidth; ++k1) {
                           int i2 = i1;
                           if (i1 > 0) {
                              i2 = i1 - this.extraTrunkWidth;
                           }

                           int k2 = k1;
                           if (k1 > 0) {
                              k2 = k1 - this.extraTrunkWidth;
                           }

                           if (Math.abs(i2) != 1 || Math.abs(k2) != 1) {
                              this.growLeaves(world, i + i1, j + j1, k + k1);
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
