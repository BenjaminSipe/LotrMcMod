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

public class LOTRWorldGenCedar extends WorldGenAbstractTree {
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;
   private int minHeight;
   private int maxHeight;
   private boolean hangingLeaves;

   public LOTRWorldGenCedar(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood4;
      this.woodMeta = 2;
      this.leafBlock = LOTRMod.leaves4;
      this.leafMeta = 2;
      this.minHeight = 10;
      this.maxHeight = 16;
      this.hangingLeaves = false;
   }

   public LOTRWorldGenCedar setMinMaxHeight(int min, int max) {
      this.minHeight = min;
      this.maxHeight = max;
      return this;
   }

   public LOTRWorldGenCedar setBlocks(Block b1, int m1, Block b2, int m2) {
      this.woodBlock = b1;
      this.woodMeta = m1;
      this.leafBlock = b2;
      this.leafMeta = m2;
      return this;
   }

   public LOTRWorldGenCedar setHangingLeaves() {
      this.hangingLeaves = true;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      boolean flag = true;
      int i1;
      int i1;
      if (j >= 1 && height + 1 <= 256) {
         for(int j1 = j; j1 <= j + height + 1; ++j1) {
            int range = 1;
            if (j1 == j) {
               range = 0;
            }

            if (j1 >= j + height - 1) {
               range = 2;
            }

            for(i1 = i - range; i1 <= i + range && flag; ++i1) {
               for(i1 = k - range; i1 <= k + range && flag; ++i1) {
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
      } else {
         flag = false;
      }

      Block below = world.func_147439_a(i, j - 1, k);
      boolean isSoil = below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g);
      if (!isSoil) {
         flag = false;
      }

      if (!flag) {
         return false;
      } else {
         below.onPlantGrow(world, i, j - 1, k, i, j, k);
         i1 = j + height - 2;

         int k1;
         int i1;
         int k1;
         for(i1 = i1; i1 <= j + height; ++i1) {
            k1 = 2 - (i1 - (j + height));
            this.spawnLeaves(world, random, i, i1, k, k1);
            if (i1 == i1) {
               for(i1 = i - 1; i1 <= i + 1; ++i1) {
                  for(k1 = k - 1; k1 <= k + 1; ++k1) {
                     if (i1 == i || k1 == k) {
                        Block block = world.func_147439_a(i1, i1, k1);
                        if (block.isReplaceable(world, i1, i1, k1) || block.isLeaves(world, i1, i1, k1)) {
                           this.func_150516_a(world, i1, i1, k1, this.woodBlock, this.woodMeta);
                        }
                     }
                  }
               }
            }
         }

         int k1;
         int j2;
         int roots;
         int i1;
         for(i1 = j + height - 1; i1 > j + height / 2; i1 -= 1 + random.nextInt(3)) {
            k1 = 1 + random.nextInt(3);

            label167:
            for(i1 = 0; i1 < k1; ++i1) {
               float angle = random.nextFloat() * 3.1415927F * 2.0F;
               i1 = i;
               k1 = k;
               j2 = i1;
               roots = MathHelper.func_76136_a(random, 4, 7);
               int leafMin = 1 + random.nextInt(2);

               int l1;
               for(l1 = 0; l1 < roots; ++l1) {
                  i1 = i + (int)(0.5F + MathHelper.func_76134_b(angle) * (float)(l1 + 1));
                  k1 = k + (int)(0.5F + MathHelper.func_76126_a(angle) * (float)(l1 + 1));
                  j2 = i1 - 3 + l1 / 2;
                  Block block = world.func_147439_a(i1, j2, k1);
                  if (!block.isReplaceable(world, i1, j2, k1) && !block.isWood(world, i1, j2, k1) && !block.isLeaves(world, i1, j2, k1)) {
                     continue label167;
                  }

                  this.func_150516_a(world, i1, j2, k1, this.woodBlock, this.woodMeta);
                  if (l1 == roots - 1 && leafMin >= 2) {
                     for(int i2 = i1 - 1; i2 <= i1 + 1; ++i2) {
                        for(int k2 = k1 - 1; k2 <= k1 + 1; ++k2) {
                           if (i2 == i1 || k2 == k1) {
                              int j3 = j2 - 1;
                              Block block1 = world.func_147439_a(i2, j3, k2);
                              if (block1.isReplaceable(world, i2, j3, k2) || block1.isLeaves(world, i2, j3, k2)) {
                                 this.func_150516_a(world, i2, j3, k2, this.woodBlock, this.woodMeta);
                              }
                           }
                        }
                     }
                  }
               }

               for(l1 = j2 - leafMin; l1 <= j2; ++l1) {
                  int leafRange = 1 - (l1 - j2);
                  this.spawnLeaves(world, random, i1, l1, k1, leafRange);
               }
            }
         }

         for(i1 = 0; i1 < height; ++i1) {
            this.func_150516_a(world, i, j + i1, k, this.woodBlock, this.woodMeta);
         }

         for(i1 = i - 1; i1 <= i + 1; ++i1) {
            for(k1 = k - 1; k1 <= k + 1; ++k1) {
               i1 = i1 - i;
               k1 = k1 - k;
               if (Math.abs(i1) != Math.abs(k1)) {
                  i1 = i1;
                  k1 = j + random.nextInt(2);
                  j2 = k1;
                  roots = 0;

                  while(world.func_147439_a(i1, k1, k1).isReplaceable(world, i1, k1, j2)) {
                     this.func_150516_a(world, i1, k1, j2, this.woodBlock, this.woodMeta | 12);
                     world.func_147439_a(i1, k1 - 1, j2).onPlantGrow(world, i1, k1 - 1, j2, i1, k1, j2);
                     --k1;
                     ++roots;
                     if (roots > 4 + random.nextInt(3)) {
                        break;
                     }
                  }
               }
            }
         }

         return true;
      }
   }

   private void spawnLeaves(World world, Random random, int i, int j, int k, int leafRange) {
      int leafRangeSq = leafRange * leafRange;

      for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
         for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
            int i2 = i1 - i;
            int k2 = k1 - k;
            if (i2 * i2 + k2 * k2 <= leafRangeSq) {
               Block block = world.func_147439_a(i1, j, k1);
               if (block.isReplaceable(world, i1, j, k1) || block.isLeaves(world, i1, j, k1)) {
                  this.func_150516_a(world, i1, j, k1, this.leafBlock, this.leafMeta);
                  if (this.hangingLeaves && random.nextInt(10) == 0) {
                     this.func_150516_a(world, i1, j, k1, this.woodBlock, this.woodMeta);
                     Block block1 = world.func_147439_a(i1, j + 1, k1);
                     if (block1.isReplaceable(world, i1, j + 1, k1) || block1.isLeaves(world, i1, j + 1, k1)) {
                        this.func_150516_a(world, i1, j + 1, k1, this.leafBlock, this.leafMeta);
                     }

                     int hang = 2 + random.nextInt(3);

                     for(int j1 = j - 1; j1 >= j - hang; --j1) {
                        Block block2 = world.func_147439_a(i1, j1, k1);
                        if (block2.isReplaceable(world, i1, j1, k1) || block2.isLeaves(world, i1, j1, k1)) {
                           this.func_150516_a(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                        }
                     }
                  }
               }
            }
         }
      }

   }
}
