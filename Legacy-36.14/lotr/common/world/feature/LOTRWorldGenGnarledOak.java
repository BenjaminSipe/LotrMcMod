package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenGnarledOak extends WorldGenAbstractTree {
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;
   private int minHeight;
   private int maxHeight;

   public LOTRWorldGenGnarledOak(boolean flag) {
      super(flag);
      this.woodBlock = Blocks.field_150364_r;
      this.woodMeta = 0;
      this.leafBlock = Blocks.field_150362_t;
      this.leafMeta = 0;
      this.minHeight = 4;
      this.maxHeight = 9;
   }

   public LOTRWorldGenGnarledOak setBlocks(Block b1, int m1, Block b2, int m2) {
      this.woodBlock = b1;
      this.woodMeta = m1;
      this.leafBlock = b2;
      this.leafMeta = m2;
      return this;
   }

   public LOTRWorldGenGnarledOak setMinMaxHeight(int min, int max) {
      this.minHeight = min;
      this.maxHeight = max;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      boolean flag = true;
      int branches;
      int lastDir;
      if (j >= 1 && height + 1 <= 256) {
         for(int j1 = j; j1 <= j + height + 1; ++j1) {
            int range = 1;
            if (j1 == j) {
               range = 0;
            }

            for(branches = i - range; branches <= i + range && flag; ++branches) {
               for(lastDir = k - range; lastDir <= k + range && flag; ++lastDir) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, branches, j1, lastDir)) {
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

      if (!flag) {
         return false;
      } else {
         boolean canGrow = true;
         Block below = world.func_147439_a(i, j - 1, k);
         if (!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
            canGrow = false;
         }

         if (!canGrow) {
            return false;
         } else {
            below = world.func_147439_a(i, j - 1, k);
            below.onPlantGrow(world, i, j - 1, k, i, j, k);

            for(branches = j; branches < j + height; ++branches) {
               this.func_150516_a(world, i, branches, k, this.woodBlock, this.woodMeta);
            }

            this.generateLeaves(world, random, i, j + height, k);
            branches = 2 + random.nextInt(3);

            for(lastDir = 0; lastDir < branches; ++lastDir) {
               float angle = random.nextFloat() * 3.1415927F * 2.0F;
               float cos = MathHelper.func_76134_b(angle);
               float sin = MathHelper.func_76126_a(angle);
               float angleY = random.nextFloat() * (float)Math.toRadians(40.0D);
               float cosY = MathHelper.func_76134_b(angleY);
               float sinY = MathHelper.func_76126_a(angleY);
               int length = 2 + random.nextInt(3);
               int i1 = i;
               int k1 = k;
               int j1 = j + height - 1 - random.nextInt(3);
               if (j1 < j + 2) {
                  j1 = j + 2;
               }

               for(int l = 0; l < length; ++l) {
                  if (Math.floor((double)(cos * (float)l)) != Math.floor((double)(cos * (float)(l - 1)))) {
                     i1 = (int)((float)i1 + Math.signum(cos));
                  }

                  if (Math.floor((double)(sin * (float)l)) != Math.floor((double)(sin * (float)(l - 1)))) {
                     k1 = (int)((float)k1 + Math.signum(sin));
                  }

                  if (Math.floor((double)(sinY * (float)l)) != Math.floor((double)(sinY * (float)(l - 1)))) {
                     j1 = (int)((float)j1 + Math.signum(sinY));
                  }

                  if (!this.isReplaceable(world, i1, j1, k1)) {
                     break;
                  }

                  this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta | 12);
               }

               this.generateLeaves(world, random, i1, j1, k1);
            }

            lastDir = -1;

            int j1;
            int dir;
            int rootY;
            int i1;
            int k1;
            for(j1 = j + 2; j1 < j + height; ++j1) {
               if (random.nextInt(3) == 0) {
                  dir = random.nextInt(4);
                  if (dir != lastDir) {
                     lastDir = dir;
                     int length = 1;

                     for(rootY = 1; rootY <= length; ++rootY) {
                        i1 = i + Direction.field_71583_a[dir] * rootY;
                        k1 = k + Direction.field_71581_b[dir] * rootY;
                        Block block = world.func_147439_a(i1, j1, k1);
                        if (!block.isReplaceable(world, i1, j1, k1) && !block.isLeaves(world, i1, j1, k1)) {
                           break;
                        }

                        if (dir != 0 && dir != 2) {
                           this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta | 4);
                        } else {
                           this.func_150516_a(world, i1, j1, k1, this.woodBlock, this.woodMeta | 8);
                        }
                     }
                  }
               }
            }

            for(j1 = i - 1; j1 <= i + 1; ++j1) {
               for(dir = k - 1; dir <= k + 1; ++dir) {
                  if ((j1 != i || dir != k) && random.nextInt(4) <= 0) {
                     int rootX = j1;
                     rootY = j + random.nextInt(2);
                     i1 = dir;
                     k1 = 0;

                     while(world.func_147439_a(rootX, rootY, dir).isReplaceable(world, rootX, rootY, i1)) {
                        this.func_150516_a(world, rootX, rootY, i1, this.woodBlock, this.woodMeta | 12);
                        world.func_147439_a(rootX, rootY - 1, i1).onPlantGrow(world, rootX, rootY - 1, i1, rootX, rootY, i1);
                        --rootY;
                        ++k1;
                        if (k1 > 4 + random.nextInt(3)) {
                           break;
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }

   private void generateLeaves(World world, Random random, int i, int j, int k) {
      int leafRange = 3;
      int leafRangeSq = leafRange * leafRange;
      int leafRangeSqLess = (int)(((double)leafRange - 0.5D) * ((double)leafRange - 0.5D));

      for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
         for(int j1 = j - leafRange + 1; j1 <= j + leafRange; ++j1) {
            for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
               int i2 = i1 - i;
               int j2 = j1 - j;
               int k2 = k1 - k;
               int dist = i2 * i2 + j2 * j2 + k2 * k2;
               if (dist < leafRangeSqLess || dist < leafRangeSq && random.nextInt(3) == 0) {
                  Block block = world.func_147439_a(i1, j1, k1);
                  if (block.isReplaceable(world, i1, j1, k1) || block.isLeaves(world, i1, j1, k1)) {
                     this.func_150516_a(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                  }
               }
            }
         }
      }

   }
}
