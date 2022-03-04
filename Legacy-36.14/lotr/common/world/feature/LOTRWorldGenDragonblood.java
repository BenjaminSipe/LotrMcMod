package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenDragonblood extends WorldGenAbstractTree {
   private int minHeight;
   private int maxHeight;
   private int trunkWidth;
   private boolean hasRoots = true;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenDragonblood(boolean flag, int i, int j, int k) {
      super(flag);
      this.woodBlock = LOTRMod.wood9;
      this.woodMeta = 0;
      this.leafBlock = LOTRMod.leaves9;
      this.leafMeta = 0;
      this.minHeight = i;
      this.maxHeight = j;
      this.trunkWidth = k;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      boolean flag = true;
      if (j >= 1 && j + height + 5 <= 256) {
         int i1;
         int k1;
         int i2;
         int k2;
         for(i1 = j; i1 <= j + height + 5; ++i1) {
            k1 = this.trunkWidth + 1;
            if (i1 == j) {
               k1 = this.trunkWidth;
            }

            if (i1 >= j + height + 2) {
               k1 = this.trunkWidth + 2;
            }

            for(i2 = i - k1; i2 <= i + k1 && flag; ++i2) {
               for(k2 = k - k1; k2 <= k + k1 && flag; ++k2) {
                  if (i1 >= 0 && i1 < 256) {
                     if (!this.isReplaceable(world, i2, i1, k2)) {
                        flag = false;
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }

         for(i1 = i - this.trunkWidth; i1 <= i + this.trunkWidth && flag; ++i1) {
            for(k1 = k - this.trunkWidth; k1 <= k + this.trunkWidth && flag; ++k1) {
               Block block = world.func_147439_a(i1, j - 1, k1);
               boolean isSoil = block.canSustainPlant(world, i1, j - 1, k1, ForgeDirection.UP, (BlockSapling)Blocks.field_150345_g);
               if (!isSoil) {
                  flag = false;
               }
            }
         }

         if (!flag) {
            return false;
         } else {
            for(i1 = i - this.trunkWidth; i1 <= i + this.trunkWidth; ++i1) {
               for(k1 = k - this.trunkWidth; k1 <= k + this.trunkWidth; ++k1) {
                  world.func_147439_a(i1, j - 1, k1).onPlantGrow(world, i1, j - 1, k1, i1, j, k1);
               }
            }

            for(i1 = 0; i1 < height; ++i1) {
               for(k1 = i - this.trunkWidth; k1 <= i + this.trunkWidth; ++k1) {
                  for(i2 = k - this.trunkWidth; i2 <= k + this.trunkWidth; ++i2) {
                     this.func_150516_a(world, k1, j + i1, i2, this.woodBlock, this.woodMeta);
                  }
               }
            }

            int roots;
            int i1;
            int k1;
            int j1;
            if (this.trunkWidth >= 1) {
               for(i1 = 0; i1 < 360; this.growLeafCanopy(world, random, i1, j1, k1)) {
                  i1 += (40 + random.nextInt(30)) / this.trunkWidth;
                  float angle = (float)Math.toRadians((double)i1);
                  float cos = MathHelper.func_76134_b(angle);
                  float sin = MathHelper.func_76126_a(angle);
                  float angleY = random.nextFloat() * (float)Math.toRadians(40.0D);
                  float cosY = MathHelper.func_76134_b(angleY);
                  float sinY = MathHelper.func_76126_a(angleY);
                  roots = 3 + random.nextInt(6);
                  roots *= 1 + random.nextInt(this.trunkWidth);
                  i1 = i;
                  k1 = k;
                  j1 = j + height - 1 - random.nextInt(5);

                  for(int l = 0; l < roots; ++l) {
                     if (Math.floor((double)(cos * (float)l)) != Math.floor((double)(cos * (float)(l - 1)))) {
                        i1 = (int)((float)i1 + Math.signum(cos));
                     }

                     if (Math.floor((double)(sin * (float)l)) != Math.floor((double)(sin * (float)(l - 1)))) {
                        k1 = (int)((float)k1 + Math.signum(sin));
                     }

                     if (Math.floor((double)(sinY * (float)l)) != Math.floor((double)(sinY * (float)(l - 1)))) {
                        j1 = (int)((float)j1 + Math.signum(sinY));
                     }

                     Block block = world.func_147439_a(i1, j1, k1);
                     if (!block.isReplaceable(world, i1, j1, k1) && !block.isWood(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) {
                        break;
                     }

                     this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta | 12);
                  }
               }
            } else {
               this.growLeafCanopy(world, random, i, j + height - 1, k);
            }

            for(i1 = i - 1 - this.trunkWidth; i1 <= i + 1 + this.trunkWidth; ++i1) {
               for(k1 = k - 1 - this.trunkWidth; k1 <= k + 1 + this.trunkWidth; ++k1) {
                  i2 = i1 - i;
                  k2 = k1 - k;
                  if (Math.abs(i2) != Math.abs(k2)) {
                     int rootX = i1;
                     int rootY = j + random.nextInt(2 + this.trunkWidth);
                     int rootZ = k1;
                     roots = 0;

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
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void growLeafCanopy(World world, Random random, int i, int j, int k) {
      int leafStart = j + 2;
      int leafTop = j + 4;
      int maxRange = 3;

      int j1;
      int i1;
      int k1;
      int i2;
      int i1;
      int k1;
      for(j1 = leafStart; j1 <= leafTop; ++j1) {
         i1 = j1 - (leafTop + 1);
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

                  for(int j3 = j1; j3 >= j1 - below; --j3) {
                     Block block = world.func_147439_a(i1, j3, k1);
                     if (block.isReplaceable(world, i1, j3, k1) || block.isLeaves(world, i1, j3, k1)) {
                        this.func_150516_a(world, i1, j3, k1, this.leafBlock, this.leafMeta);
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
                     this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta | 12);
                  }
               }
            }
         }
      }

   }
}
