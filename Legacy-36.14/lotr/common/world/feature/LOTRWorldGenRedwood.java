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

public class LOTRWorldGenRedwood extends WorldGenAbstractTree {
   private int trunkWidth = 0;
   private int extraTrunkWidth = 0;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenRedwood(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood8;
      this.woodMeta = 1;
      this.leafBlock = LOTRMod.leaves8;
      this.leafMeta = 1;
   }

   public LOTRWorldGenRedwood setTrunkWidth(int i) {
      this.trunkWidth = i;
      return this;
   }

   public LOTRWorldGenRedwood setExtraTrunkWidth(int i) {
      this.extraTrunkWidth = i;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int fullWidth = 1 + this.extraTrunkWidth + this.trunkWidth * 2;
      int height = fullWidth * MathHelper.func_76136_a(random, 15, 20);
      if (fullWidth > 1) {
         height += (fullWidth - 1) * MathHelper.func_76136_a(random, 0, 8);
      }

      boolean flag = true;
      if (j >= 1 && j + height + 1 <= 256) {
         int narrowHeight;
         int leafStart;
         int leafTop;
         for(int j1 = j; j1 <= j + 1 + height; ++j1) {
            narrowHeight = 1;
            if (j1 == j) {
               narrowHeight = 0;
            }

            if (j1 > j + 2 && j1 < j + height - 2) {
               narrowHeight = 2;
               if (this.extraTrunkWidth > 0) {
                  ++narrowHeight;
               }
            }

            for(leafStart = i - this.trunkWidth - narrowHeight; leafStart <= i + this.trunkWidth + this.extraTrunkWidth + narrowHeight && flag; ++leafStart) {
               for(leafTop = k - this.trunkWidth - narrowHeight; leafTop <= k + this.trunkWidth + this.extraTrunkWidth + narrowHeight && flag; ++leafTop) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, leafStart, j1, leafTop)) {
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

            for(narrowHeight = i - this.trunkWidth; narrowHeight <= i + this.trunkWidth + this.extraTrunkWidth && canGrow; ++narrowHeight) {
               for(leafStart = k - this.trunkWidth; leafStart <= k + this.trunkWidth + this.extraTrunkWidth && canGrow; ++leafStart) {
                  Block block = world.func_147439_a(narrowHeight, j - 1, leafStart);
                  if (!block.canSustainPlant(world, narrowHeight, j - 1, leafStart, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
                     canGrow = false;
                  }
               }
            }

            if (!canGrow) {
               return false;
            } else {
               for(narrowHeight = i - this.trunkWidth; narrowHeight <= i + this.trunkWidth + this.extraTrunkWidth; ++narrowHeight) {
                  for(leafStart = k - this.trunkWidth; leafStart <= k + this.trunkWidth + this.extraTrunkWidth; ++leafStart) {
                     world.func_147439_a(narrowHeight, j - 1, leafStart).onPlantGrow(world, narrowHeight, j - 1, leafStart, narrowHeight, j, leafStart);
                  }
               }

               narrowHeight = -1;
               if (fullWidth > 3) {
                  narrowHeight = j + (int)((float)height * MathHelper.func_151240_a(random, 0.3F, 0.4F));
               }

               leafStart = j + (int)((float)height * MathHelper.func_151240_a(random, 0.45F, 0.6F));
               leafTop = j + height + 1;
               int leafRange = 0;
               int maxRange = 2;
               boolean increasing = true;

               int j1;
               int k1;
               int i1;
               int k1;
               int rootX;
               int rootY;
               int i3;
               for(j1 = leafTop; j1 >= leafStart; --j1) {
                  if (j1 >= leafTop - 1) {
                     leafRange = 0;
                  } else if (increasing) {
                     ++leafRange;
                     if (leafRange >= 3) {
                        increasing = false;
                     }
                  } else {
                     --leafRange;
                     if (leafRange <= 1) {
                        increasing = true;
                     }
                  }

                  leafRange = Math.min(leafRange, 4);
                  k1 = this.trunkWidth;
                  if (narrowHeight > -1 && j1 >= narrowHeight) {
                     --k1;
                  }

                  for(i1 = i - k1 - maxRange; i1 <= i + k1 + this.extraTrunkWidth + maxRange; ++i1) {
                     for(k1 = k - k1 - maxRange; k1 <= k + k1 + this.extraTrunkWidth + maxRange; ++k1) {
                        rootX = Math.abs(i1 - i);
                        rootY = Math.abs(k1 - k);
                        rootX -= k1;
                        rootY -= k1;
                        if (i1 > i) {
                           rootX -= this.extraTrunkWidth;
                        }

                        if (k1 > k) {
                           rootY -= this.extraTrunkWidth;
                        }

                        i3 = rootX + rootY;
                        if (j1 < leafTop - 2) {
                           i3 += random.nextInt(2);
                        }

                        if (i3 <= leafRange) {
                           Block block = world.func_147439_a(i1, j1, k1);
                           if (block.isReplaceable(world, i1, j1, k1) || block.isLeaves(world, i1, j1, k1)) {
                              this.func_150516_a(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                           }
                        }
                     }
                  }
               }

               for(j1 = 0; j1 < height; ++j1) {
                  k1 = this.trunkWidth;
                  if (narrowHeight > -1 && j + j1 >= narrowHeight) {
                     --k1;
                  }

                  for(i1 = -k1; i1 <= k1 + this.extraTrunkWidth; ++i1) {
                     for(k1 = -k1; k1 <= k1 + this.extraTrunkWidth; ++k1) {
                        rootX = Math.abs(i1);
                        rootY = Math.abs(k1);
                        if (i1 > 0) {
                           rootX -= this.extraTrunkWidth;
                        }

                        if (k1 > 0) {
                           rootY -= this.extraTrunkWidth;
                        }

                        i3 = i + i1;
                        int j3 = j + j1;
                        int k3 = k + k1;
                        if (narrowHeight <= -1 || j3 >= narrowHeight || j3 <= j + 15 || j3 >= leafStart || rootX != k1 || rootY != k1) {
                           world.func_147439_a(i3, j3, k3);
                           if (this.isReplaceable(world, i3, j3, k3)) {
                              this.func_150516_a(world, i3, j3, k3, this.woodBlock, this.woodMeta);
                           }
                        }
                     }
                  }
               }

               for(j1 = i - this.trunkWidth - 1; j1 <= i + this.trunkWidth + this.extraTrunkWidth + 1; ++j1) {
                  for(k1 = k - this.trunkWidth - 1; k1 <= k + this.trunkWidth + this.extraTrunkWidth + 1; ++k1) {
                     i1 = Math.abs(j1 - i);
                     k1 = Math.abs(k1 - k);
                     i1 -= this.trunkWidth;
                     k1 -= this.trunkWidth;
                     if (j1 > i) {
                        i1 -= this.extraTrunkWidth;
                     }

                     if (k1 > k) {
                        k1 -= this.extraTrunkWidth;
                     }

                     if ((i1 == 1 || k1 == 1) && i1 != k1) {
                        rootX = j1;
                        rootY = j + fullWidth / 2 + random.nextInt(2 + fullWidth / 2);
                        i3 = k1;

                        while(world.func_147439_a(rootX, rootY, k1).isReplaceable(world, rootX, rootY, i3)) {
                           this.func_150516_a(world, rootX, rootY, i3, this.woodBlock, this.woodMeta | 12);
                           world.func_147439_a(rootX, rootY - 1, i3).onPlantGrow(world, rootX, rootY - 1, i3, rootX, rootY, i3);
                           --rootY;
                           if (rootY < j - 3 - random.nextInt(3)) {
                              break;
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

   private void growLeaves(World world, int i, int j, int k) {
      Block block = world.func_147439_a(i, j, k);
      if (block.isReplaceable(world, i, j, k) || block.isLeaves(world, i, j, k)) {
         this.func_150516_a(world, i, j, k, this.leafBlock, this.leafMeta);
      }

   }
}
