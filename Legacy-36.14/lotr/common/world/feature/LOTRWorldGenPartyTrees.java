package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenPartyTrees extends WorldGenAbstractTree {
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;
   private boolean restrictions = true;

   public LOTRWorldGenPartyTrees(Block block, int i, Block block1, int j) {
      super(false);
      this.woodBlock = block;
      this.woodMeta = i;
      this.leafBlock = block1;
      this.leafMeta = j;
   }

   public LOTRWorldGenPartyTrees disableRestrictions() {
      this.restrictions = false;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int trunkWidth = 1;
      int height = random.nextInt(12) + 12;
      boolean flag = true;
      int j1;
      int k1;
      int i1;
      int i1;
      if (this.restrictions) {
         if (j < 1 || j + height + 1 > 256) {
            return false;
         }

         for(j1 = j; j1 <= j + 1 + height; ++j1) {
            k1 = trunkWidth + 1;
            if (j1 == j) {
               k1 = trunkWidth;
            }

            for(i1 = i - k1; i1 <= i + k1 && flag; ++i1) {
               for(i1 = k - k1; i1 <= k + k1 && flag; ++i1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, i1, j1, i1)) {
                        flag = false;
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }

         for(j1 = i - trunkWidth; j1 <= i + trunkWidth && flag; ++j1) {
            for(k1 = k - trunkWidth; k1 <= k + trunkWidth && flag; ++k1) {
               Block block = world.func_147439_a(j1, j - 1, k1);
               if (!block.canSustainPlant(world, j1, j - 1, k1, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
                  flag = false;
               }
            }
         }

         if (!flag) {
            return false;
         }
      }

      for(j1 = i - trunkWidth; j1 <= i + trunkWidth; ++j1) {
         for(k1 = k - trunkWidth; k1 <= k + trunkWidth; ++k1) {
            world.func_147439_a(j1, j - 1, k1).onPlantGrow(world, j1, j - 1, k1, j1, j, k1);
         }
      }

      for(j1 = 0; j1 < height; ++j1) {
         for(k1 = i - trunkWidth; k1 <= i + trunkWidth; ++k1) {
            for(i1 = k - trunkWidth; i1 <= k + trunkWidth; ++i1) {
               this.func_150516_a(world, k1, j + j1, i1, this.woodBlock, this.woodMeta);
            }
         }
      }

      j1 = 0;

      int j1;
      int k1;
      int rootLength;
      int l1;
      int rootBlocks;
      int j2;
      while(j1 < 360) {
         j1 += 20 + random.nextInt(25);
         float angleR = (float)j1 / 180.0F * 3.1415927F;
         float sin = MathHelper.func_76126_a(angleR);
         float cos = MathHelper.func_76134_b(angleR);
         j1 = 6 + random.nextInt(6);
         k1 = Math.round((float)j1 / 20.0F * 1.5F);
         int boughBaseHeight = j + MathHelper.func_76128_c((double)((float)height * (0.75F + random.nextFloat() * 0.25F)));
         int boughHeight = 3 + random.nextInt(4);

         for(rootLength = 0; rootLength < j1; ++rootLength) {
            l1 = i + Math.round(sin * (float)rootLength);
            rootBlocks = k + Math.round(cos * (float)rootLength);
            j2 = boughBaseHeight + Math.round((float)rootLength / (float)j1 * (float)boughHeight);
            int range = k1 - Math.round((float)rootLength / (float)j1 * (float)k1 * 0.5F);

            int i2;
            for(i2 = l1 - range; i2 <= l1 + range; ++i2) {
               for(int j2 = j2 - range; j2 <= j2 + range; ++j2) {
                  for(int k2 = rootBlocks - range; k2 <= rootBlocks + range; ++k2) {
                     Block block = world.func_147439_a(i2, j2, k2);
                     if (block.isReplaceable(world, i2, j2, k2) || block.isLeaves(world, i2, j2, k2)) {
                        this.func_150516_a(world, i2, j2, k2, this.woodBlock, this.woodMeta | 12);
                     }
                  }
               }
            }

            i2 = j1 + random.nextInt(360);
            float branch_angleR = (float)i2 / 180.0F * 3.1415927F;
            float branch_sin = MathHelper.func_76126_a(branch_angleR);
            float branch_cos = MathHelper.func_76134_b(branch_angleR);
            int branchLength = 4 + random.nextInt(4);
            int branchHeight = random.nextInt(5);
            int leafRange = 3;

            for(int l1 = 0; l1 < branchLength; ++l1) {
               int i2 = l1 + Math.round(branch_sin * (float)l1);
               int k2 = rootBlocks + Math.round(branch_cos * (float)l1);
               int j2 = j2 + Math.round((float)l1 / (float)branchLength * (float)branchHeight);

               int i3;
               for(i3 = j2; i3 >= j2 - 1; --i3) {
                  Block block = world.func_147439_a(i2, i3, k2);
                  if (block.isReplaceable(world, i2, i3, k2) || block.isLeaves(world, i2, i3, k2)) {
                     this.func_150516_a(world, i2, i3, k2, this.woodBlock, this.woodMeta | 12);
                  }
               }

               if (l1 == branchLength - 1) {
                  for(i3 = i2 - leafRange; i3 <= i2 + leafRange; ++i3) {
                     for(int j3 = j2 - leafRange; j3 <= j2 + leafRange; ++j3) {
                        for(int k3 = k2 - leafRange; k3 <= k2 + leafRange; ++k3) {
                           int i4 = i3 - i2;
                           int j4 = j3 - j2;
                           int k4 = k3 - k2;
                           int dist = i4 * i4 + j4 * j4 + k4 * k4;
                           if (dist < (leafRange - 1) * (leafRange - 1) || dist < leafRange * leafRange && random.nextInt(3) != 0) {
                              Block block2 = world.func_147439_a(i3, j3, k3);
                              if (block2.isReplaceable(world, i3, j3, k3) || block2.isLeaves(world, i3, j3, k3)) {
                                 this.func_150516_a(world, i3, j3, k3, this.leafBlock, this.leafMeta);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      k1 = 5 + random.nextInt(5);

      for(i1 = 0; i1 < k1; ++i1) {
         j1 = j + 1 + random.nextInt(5);
         int xDirection = 0;
         int zDirection = 0;
         rootLength = 2 + random.nextInt(4);
         if (random.nextBoolean()) {
            if (random.nextBoolean()) {
               i1 = i - (trunkWidth + 1);
               xDirection = -1;
            } else {
               i1 = i + trunkWidth + 1;
               xDirection = 1;
            }

            k1 = k - (trunkWidth + 1);
            k1 += random.nextInt(trunkWidth * 2 + 2);
         } else {
            if (random.nextBoolean()) {
               k1 = k - (trunkWidth + 1);
               zDirection = -1;
            } else {
               k1 = k + trunkWidth + 1;
               zDirection = 1;
            }

            i1 = i - (trunkWidth + 1);
            i1 += random.nextInt(trunkWidth * 2 + 2);
         }

         for(l1 = 0; l1 < rootLength; ++l1) {
            rootBlocks = 0;

            for(j2 = j1; !world.func_147439_a(i1, j2, k1).func_149662_c(); --j2) {
               Block block = world.func_147439_a(i1, j2, k1);
               if (!block.isReplaceable(world, i1, j2, k1) && !block.isLeaves(world, i1, j2, k1)) {
                  break;
               }

               this.func_150516_a(world, i1, j2, k1, this.woodBlock, this.woodMeta | 12);
               world.func_147439_a(i1, j2 - 1, k1).onPlantGrow(world, i1, j2 - 1, k1, i1, j2, k1);
               ++rootBlocks;
               if (rootBlocks > 5) {
                  break;
               }
            }

            --j1;
            if (random.nextBoolean()) {
               if (xDirection == -1) {
                  --i1;
               } else if (xDirection == 1) {
                  ++i1;
               } else if (zDirection == -1) {
                  --k1;
               } else if (zDirection == 1) {
                  ++k1;
               }
            }
         }
      }

      return true;
   }
}
