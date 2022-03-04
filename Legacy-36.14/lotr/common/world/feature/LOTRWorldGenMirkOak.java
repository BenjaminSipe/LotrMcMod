package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRWorldGenWoodElfPlatform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenMirkOak extends WorldGenAbstractTree {
   private int minHeight;
   private int maxHeight;
   private int trunkWidth;
   private boolean isMirky;
   private boolean restrictions = true;
   private boolean isDead;
   private boolean hasRoots = true;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenMirkOak(boolean flag, int i, int j, int k, boolean mirk) {
      super(flag);
      this.woodBlock = LOTRMod.wood;
      this.woodMeta = 2;
      this.leafBlock = LOTRMod.leaves;
      this.leafMeta = 2;
      this.minHeight = i;
      this.maxHeight = j;
      this.trunkWidth = k;
      this.isMirky = mirk;
   }

   public LOTRWorldGenMirkOak setBlocks(Block b1, int m1, Block b2, int m2) {
      this.woodBlock = b1;
      this.woodMeta = m1;
      this.leafBlock = b2;
      this.leafMeta = m2;
      return this;
   }

   public LOTRWorldGenMirkOak setGreenOak() {
      return this.setBlocks(LOTRMod.wood7, 1, LOTRMod.leaves7, 1);
   }

   public LOTRWorldGenMirkOak setRedOak() {
      return this.setBlocks(LOTRMod.wood7, 1, LOTRMod.leaves, 3);
   }

   public LOTRWorldGenMirkOak disableRestrictions() {
      this.restrictions = false;
      return this;
   }

   public LOTRWorldGenMirkOak setDead() {
      this.isDead = true;
      return this;
   }

   public LOTRWorldGenMirkOak disableRoots() {
      this.hasRoots = false;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      boolean flag = true;
      if (this.restrictions && (j < 1 || j + height + 5 > 256)) {
         return false;
      } else {
         int j1;
         int platforms;
         int i1;
         int j1;
         if (this.restrictions) {
            for(j1 = j; j1 <= j + height + 5; ++j1) {
               platforms = this.trunkWidth + 1;
               if (j1 == j) {
                  platforms = this.trunkWidth;
               }

               if (j1 >= j + height + 2) {
                  platforms = this.trunkWidth + 2;
               }

               for(i1 = i - platforms; i1 <= i + platforms && flag; ++i1) {
                  for(j1 = k - platforms; j1 <= k + platforms && flag; ++j1) {
                     if (j1 >= 0 && j1 < 256) {
                        if (!this.isReplaceable(world, i1, j1, j1)) {
                           flag = false;
                        }
                     } else {
                        flag = false;
                     }
                  }
               }
            }

            for(j1 = i - this.trunkWidth; j1 <= i + this.trunkWidth && flag; ++j1) {
               for(platforms = k - this.trunkWidth; platforms <= k + this.trunkWidth && flag; ++platforms) {
                  Block block = world.func_147439_a(j1, j - 1, platforms);
                  boolean isSoil = block.canSustainPlant(world, j1, j - 1, platforms, ForgeDirection.UP, (BlockSapling)Blocks.field_150345_g);
                  if (!isSoil) {
                     flag = false;
                  }
               }
            }

            if (!flag) {
               return false;
            }
         }

         if (this.restrictions) {
            for(j1 = i - this.trunkWidth; j1 <= i + this.trunkWidth; ++j1) {
               for(platforms = k - this.trunkWidth; platforms <= k + this.trunkWidth; ++platforms) {
                  world.func_147439_a(j1, j - 1, platforms).onPlantGrow(world, j1, j - 1, platforms, j1, j, platforms);
               }
            }
         }

         for(j1 = 0; j1 < height; ++j1) {
            for(platforms = i - this.trunkWidth; platforms <= i + this.trunkWidth; ++platforms) {
               for(i1 = k - this.trunkWidth; i1 <= k + this.trunkWidth; ++i1) {
                  this.func_150516_a(world, platforms, j + j1, i1, this.woodBlock, this.woodMeta);
               }
            }
         }

         int rootLength;
         int i1;
         int rootBlocks;
         int j2;
         if (this.trunkWidth >= 1) {
            for(j1 = 0; j1 < 360; this.growLeafCanopy(world, random, i1, j2, rootBlocks)) {
               j1 += (40 + random.nextInt(30)) / this.trunkWidth;
               float angle = (float)Math.toRadians((double)j1);
               float cos = MathHelper.func_76134_b(angle);
               float sin = MathHelper.func_76126_a(angle);
               float angleY = random.nextFloat() * (float)Math.toRadians(40.0D);
               float cosY = MathHelper.func_76134_b(angleY);
               float sinY = MathHelper.func_76126_a(angleY);
               rootLength = 3 + random.nextInt(6);
               rootLength *= 1 + random.nextInt(this.trunkWidth);
               i1 = i;
               rootBlocks = k;
               j2 = j + height - 1 - random.nextInt(5);

               for(int l = 0; l < rootLength; ++l) {
                  if (Math.floor((double)(cos * (float)l)) != Math.floor((double)(cos * (float)(l - 1)))) {
                     i1 = (int)((float)i1 + Math.signum(cos));
                  }

                  if (Math.floor((double)(sin * (float)l)) != Math.floor((double)(sin * (float)(l - 1)))) {
                     rootBlocks = (int)((float)rootBlocks + Math.signum(sin));
                  }

                  if (Math.floor((double)(sinY * (float)l)) != Math.floor((double)(sinY * (float)(l - 1)))) {
                     j2 = (int)((float)j2 + Math.signum(sinY));
                  }

                  Block block = world.func_147439_a(i1, j2, rootBlocks);
                  if (!block.isReplaceable(world, i1, j2, rootBlocks) && !block.isWood(world, i1, j2, rootBlocks) && !block.isLeaves(world, i1, j2, rootBlocks)) {
                     break;
                  }

                  this.func_150516_a(world, i1, j2, rootBlocks, this.woodBlock, this.woodMeta);
               }
            }

            if (this.trunkWidth == 2) {
               platforms = 0;
               if (random.nextInt(3) != 0) {
                  if (random.nextBoolean()) {
                     platforms = 1 + random.nextInt(3);
                  } else {
                     platforms = 4 + random.nextInt(5);
                  }
               }

               for(i1 = 0; i1 < platforms; ++i1) {
                  j1 = j + MathHelper.func_76136_a(random, 10, height);
                  (new LOTRWorldGenWoodElfPlatform(false)).func_76484_a(world, random, i, j1, k);
               }
            }
         } else {
            this.growLeafCanopy(world, random, i, j + height - 1, k);
         }

         if (this.hasRoots) {
            j1 = 4 + random.nextInt(5 * this.trunkWidth + 1);

            for(platforms = 0; platforms < j1; ++platforms) {
               j1 = j + 1 + random.nextInt(this.trunkWidth * 2 + 1);
               int xDirection = 0;
               int zDirection = 0;
               rootLength = 1 + random.nextInt(4);
               int k1;
               if (random.nextBoolean()) {
                  if (random.nextBoolean()) {
                     i1 = i - (this.trunkWidth + 1);
                     xDirection = -1;
                  } else {
                     i1 = i + this.trunkWidth + 1;
                     xDirection = 1;
                  }

                  k1 = k - (this.trunkWidth + 1);
                  k1 += random.nextInt(this.trunkWidth * 2 + 2);
               } else {
                  if (random.nextBoolean()) {
                     k1 = k - (this.trunkWidth + 1);
                     zDirection = -1;
                  } else {
                     k1 = k + this.trunkWidth + 1;
                     zDirection = 1;
                  }

                  i1 = i - (this.trunkWidth + 1);
                  i1 += random.nextInt(this.trunkWidth * 2 + 2);
               }

               for(i1 = 0; i1 < rootLength; ++i1) {
                  rootBlocks = 0;

                  for(j2 = j1; !world.func_147439_a(i1, j2, k1).func_149662_c(); --j2) {
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
         }

         return true;
      }
   }

   private void growLeafCanopy(World world, Random random, int i, int j, int k) {
      int leafStart = j + 2;
      int leafTop = j + 5;
      int maxRange = 3;
      int j1;
      int i1;
      int k1;
      int i2;
      int i1;
      int k1;
      if (!this.isDead) {
         for(j1 = leafStart; j1 <= leafTop; ++j1) {
            i1 = j1 - leafTop;
            k1 = maxRange - i1;
            i2 = k1 * k1;

            for(i1 = i - k1; i1 <= i + k1; ++i1) {
               for(k1 = k - k1; k1 <= k + k1; ++k1) {
                  int i2 = Math.abs(i1 - i);
                  int k2 = Math.abs(k1 - k);
                  int dist = i2 * i2 + k2 * k2;
                  boolean grow = dist < i2;
                  if (i2 == k1 - 1 || k2 == k1 - 1) {
                     grow &= random.nextInt(4) > 0;
                  }

                  if (grow) {
                     int below = 0;
                     if (this.isMirky && j1 == leafStart && i2 <= 3 && k2 <= 3 && random.nextInt(3) == 0) {
                        ++below;
                     }

                     for(int j3 = j1; j3 >= j1 - below; --j3) {
                        Block block = world.func_147439_a(i1, j3, k1);
                        if (block.isReplaceable(world, i1, j3, k1) || block.isLeaves(world, i1, j3, k1)) {
                           this.func_150516_a(world, i1, j3, k1, this.leafBlock, this.leafMeta);
                           if (this.isMirky) {
                              if (random.nextInt(20) == 0 && world.func_147437_c(i1 - 1, j3, k1)) {
                                 this.growVines(world, random, i1 - 1, j3, k1, 8);
                              }

                              if (random.nextInt(20) == 0 && world.func_147437_c(i1 + 1, j3, k1)) {
                                 this.growVines(world, random, i1 + 1, j3, k1, 2);
                              }

                              if (random.nextInt(20) == 0 && world.func_147437_c(i1, j3, k1 - 1)) {
                                 this.growVines(world, random, i1, j3, k1 - 1, 1);
                              }

                              if (random.nextInt(20) == 0 && world.func_147437_c(i1, j3, k1 + 1)) {
                                 this.growVines(world, random, i1, j3, k1 + 1, 4);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      for(j1 = j; j1 <= j + 2; ++j1) {
         for(i1 = i - maxRange; i1 <= i + maxRange; ++i1) {
            for(k1 = k - maxRange; k1 <= k + maxRange; ++k1) {
               i2 = Math.abs(i1 - i);
               i1 = Math.abs(k1 - k);
               k1 = j1 - j;
               if (i2 == 0 && i1 == 0 || i2 == i1 && i2 == k1 || (i2 == 0 || i1 == 0) && i2 != i1 && (i2 == k1 + 1 || i1 == k1 + 1)) {
                  Block block = world.func_147439_a(i1, j1, k1);
                  if (block.isReplaceable(world, i1, j1, k1) || block.isLeaves(world, i1, j1, k1)) {
                     this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta);
                  }
               }
            }
         }
      }

   }

   private void growVines(World world, Random random, int i, int j, int k, int meta) {
      this.func_150516_a(world, i, j, k, LOTRMod.mirkVines, meta);
      int length = 4 + random.nextInt(8);

      while(true) {
         --j;
         if (!world.func_147437_c(i, j, k) || length <= 0) {
            return;
         }

         this.func_150516_a(world, i, j, k, LOTRMod.mirkVines, meta);
         --length;
      }
   }
}
