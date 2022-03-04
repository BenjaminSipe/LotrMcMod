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

public class LOTRWorldGenBaobab extends WorldGenAbstractTree {
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenBaobab(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood4;
      this.woodMeta = 1;
      this.leafBlock = LOTRMod.leaves4;
      this.leafMeta = 1;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int trunkCircleWidth = 4;
      int height = 16 + random.nextInt(10);
      int xSlope = 5 + random.nextInt(10);
      if (random.nextBoolean()) {
         xSlope *= -1;
      }

      int zSlope = 5 + random.nextInt(10);
      if (random.nextBoolean()) {
         zSlope *= -1;
      }

      boolean flag = true;
      int i1;
      int k1;
      int k1;
      int k2;
      int j1;
      if (j >= 1 && j + height + 5 <= 256) {
         for(i1 = i - trunkCircleWidth - 1; i1 <= i + trunkCircleWidth + 1 && flag; ++i1) {
            for(k1 = k - trunkCircleWidth - 1; k1 <= k + trunkCircleWidth + 1 && flag; ++k1) {
               k1 = Math.abs(i1 - i);
               k2 = Math.abs(k1 - k);
               if (k1 * k1 + k2 * k2 <= trunkCircleWidth * trunkCircleWidth) {
                  for(j1 = j; j1 <= j + 1 + height; ++j1) {
                     if (j1 >= 0 && j1 < 256) {
                        Block block = world.func_147439_a(i1, j1, k1);
                        if (!this.isReplaceable(world, i1, j1, k1) && !block.isReplaceable(world, i1, j1, k1)) {
                           flag = false;
                        }
                     } else {
                        flag = false;
                     }
                  }

                  Block below = world.func_147439_a(i1, j - 1, k1);
                  boolean isSoil = below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g);
                  if (!isSoil) {
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
         for(i1 = 0; i1 < height; ++i1) {
            for(k1 = i - trunkCircleWidth - 1; k1 <= i + trunkCircleWidth + 1; ++k1) {
               for(k1 = k - trunkCircleWidth - 1; k1 <= k + trunkCircleWidth + 1; ++k1) {
                  k2 = Math.abs(k1 - i);
                  j1 = Math.abs(k1 - k);
                  if (k2 * k2 + j1 * j1 <= trunkCircleWidth * trunkCircleWidth) {
                     if (i1 == 0) {
                        world.func_147439_a(k1, j - 1, k1).onPlantGrow(world, k1, j - 1, k1, k1, j, k1);
                     }

                     this.func_150516_a(world, k1, j + i1, k1, this.woodBlock, this.woodMeta);
                  }
               }
            }

            if (i1 % xSlope == 0) {
               if (xSlope > 0) {
                  ++i;
               } else if (xSlope < 0) {
                  --i;
               }
            }

            if (i1 % zSlope == 0) {
               if (zSlope > 0) {
                  ++k;
               } else if (zSlope < 0) {
                  --k;
               }
            }
         }

         int j2;
         int j2;
         int leafRange;
         int k1;
         for(i1 = j + height - 1; i1 > j + (int)((float)height * 0.75F); --i1) {
            k1 = 2 + random.nextInt(3);

            for(k1 = 0; k1 < k1; ++k1) {
               float angle = random.nextFloat() * 3.1415927F * 2.0F;
               j1 = i;
               k1 = k;
               j2 = i1;
               j2 = MathHelper.func_76136_a(random, 4, 6);

               for(leafRange = trunkCircleWidth; leafRange < trunkCircleWidth + j2; ++leafRange) {
                  j1 = i + (int)(1.5F + MathHelper.func_76134_b(angle) * (float)leafRange);
                  k1 = k + (int)(1.5F + MathHelper.func_76126_a(angle) * (float)leafRange);
                  j2 = i1 - 3 + leafRange / 2;
                  if (!this.isReplaceable(world, j1, j2, k1)) {
                     break;
                  }

                  this.func_150516_a(world, j1, j2, k1, this.woodBlock, this.woodMeta);
               }

               leafRange = 1 + random.nextInt(2);

               for(int j3 = j2 - leafRange; j3 <= j2; ++j3) {
                  int leafRange = 1 - (j3 - j2);
                  this.spawnLeaves(world, j1, j3, k1, leafRange);
               }
            }
         }

         for(i1 = i - trunkCircleWidth - 1; i1 <= i + trunkCircleWidth + 1; ++i1) {
            for(k1 = k - trunkCircleWidth - 1; k1 <= k + trunkCircleWidth + 1; ++k1) {
               k1 = Math.abs(i1 - i);
               k2 = Math.abs(k1 - k);
               if (k1 * k1 + k2 * k2 <= trunkCircleWidth * trunkCircleWidth && random.nextInt(5) == 0) {
                  j1 = j + height;
                  k1 = 2 + random.nextInt(3);

                  for(j2 = 0; j2 < k1; ++j2) {
                     this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta);
                     ++j1;
                  }

                  int leafMin = 2;

                  for(j2 = j1 - leafMin; j2 <= j1; ++j2) {
                     leafRange = 1 - (j2 - j1);
                     this.spawnLeaves(world, i1, j2, k1, leafRange);
                  }
               }
            }
         }

         return true;
      }
   }

   private void spawnLeaves(World world, int i, int j, int k, int leafRange) {
      int leafRangeSq = leafRange * leafRange;

      for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
         for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
            int i2 = i1 - i;
            int k2 = k1 - k;
            if (i2 * i2 + k2 * k2 <= leafRangeSq) {
               Block block = world.func_147439_a(i1, j, k1);
               if (block.isReplaceable(world, i1, j, k1) || block.isLeaves(world, i1, j, k1)) {
                  this.func_150516_a(world, i1, j, k1, this.leafBlock, this.leafMeta);
               }
            }
         }
      }

   }
}
