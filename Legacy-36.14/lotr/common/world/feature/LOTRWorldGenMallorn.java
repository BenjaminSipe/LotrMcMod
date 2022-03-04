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

public class LOTRWorldGenMallorn extends WorldGenAbstractTree {
   private int minHeight = 10;
   private int maxHeight = 14;
   private Block woodBlock;
   private int woodMeta;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenMallorn(boolean flag) {
      super(flag);
      this.woodBlock = LOTRMod.wood;
      this.woodMeta = 1;
      this.leafBlock = LOTRMod.leaves;
      this.leafMeta = 1;
   }

   public LOTRWorldGenMallorn setMinMaxHeight(int min, int max) {
      this.minHeight = min;
      this.maxHeight = max;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = MathHelper.func_76136_a(random, this.minHeight, this.maxHeight);
      int leafMin = j + (int)((float)height * 0.6F);
      boolean flag = true;
      if (j >= 1 && j + height + 1 <= 256) {
         int deg;
         int i1;
         for(int j1 = j; j1 <= j + height + 1; ++j1) {
            int range = 1;
            if (j1 == j) {
               range = 0;
            }

            if (j1 >= leafMin) {
               range = 2;
            }

            for(deg = i - range; deg <= i + range && flag; ++deg) {
               for(i1 = k - range; i1 <= k + range && flag; ++i1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, deg, j1, i1)) {
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
            Block below = world.func_147439_a(i, j - 1, k);
            if (!below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g)) {
               canGrow = false;
            }

            if (!canGrow) {
               return false;
            } else {
               below = world.func_147439_a(i, j - 1, k);
               below.onPlantGrow(world, i, j - 1, k, i, j, k);
               deg = 0;

               int k1;
               int i2;
               for(i1 = j + height; i1 >= leafMin; --i1) {
                  k1 = 1 + random.nextInt(2);

                  for(i2 = 0; i2 < k1; ++i2) {
                     deg += 50 + random.nextInt(70);
                     float angle = (float)Math.toRadians((double)deg);
                     float cos = MathHelper.func_76134_b(angle);
                     float sin = MathHelper.func_76126_a(angle);
                     float angleY = random.nextFloat() * (float)Math.toRadians(50.0D);
                     float cosY = MathHelper.func_76134_b(angleY);
                     float sinY = MathHelper.func_76126_a(angleY);
                     int length = 4 + random.nextInt(6);
                     int i1 = i;
                     int k1 = k;
                     int j2 = i1;

                     for(int l = 0; l < length; ++l) {
                        if (Math.floor((double)(cos * (float)l)) != Math.floor((double)(cos * (float)(l - 1)))) {
                           i1 = (int)((float)i1 + Math.signum(cos));
                        }

                        if (Math.floor((double)(sin * (float)l)) != Math.floor((double)(sin * (float)(l - 1)))) {
                           k1 = (int)((float)k1 + Math.signum(sin));
                        }

                        if (Math.floor((double)(sinY * (float)l)) != Math.floor((double)(sinY * (float)(l - 1)))) {
                           j2 = (int)((float)j2 + Math.signum(sinY));
                        }

                        Block block = world.func_147439_a(i1, j2, k1);
                        if (!block.isReplaceable(world, i1, j2, k1) && !block.isWood(world, i1, j2, k1) && !block.isLeaves(world, i1, j2, k1)) {
                           break;
                        }

                        this.func_150516_a(world, i1, j2, k1, this.woodBlock, this.woodMeta | 12);
                     }

                     this.growLeafCanopy(world, random, i1, j2, k1);
                  }
               }

               for(i1 = j; i1 < j + height; ++i1) {
                  this.func_150516_a(world, i, i1, k, this.woodBlock, this.woodMeta);
               }

               for(i1 = i - 1; i1 <= i + 1; ++i1) {
                  for(k1 = k - 1; k1 <= k + 1; ++k1) {
                     i2 = i1 - i;
                     int k2 = k1 - k;
                     if (Math.abs(i2) != Math.abs(k2)) {
                        int rootX = i1;
                        int rootY = j + random.nextInt(2);
                        int rootZ = k1;
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
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   private void growLeafCanopy(World world, Random random, int i, int j, int k) {
      int leafStart = j - 1;
      int leafTop = j + 2;
      int maxRange = 3 + random.nextInt(2);
      int[] ranges = new int[]{-2, 0, -1, -2};

      for(int j1 = leafStart; j1 <= leafTop; ++j1) {
         int leafRange = maxRange + ranges[j1 - leafStart];
         int leafRangeSq = leafRange * leafRange;

         for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
            for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
               int i2 = Math.abs(i1 - i);
               int k2 = Math.abs(k1 - k);
               int j2 = Math.abs(j1 - j);
               int dSq = i2 * i2 + k2 * k2;
               int dCh = i2 + j2 + k2;
               boolean grow = dSq < leafRangeSq && dCh <= 4;
               if (i2 == leafRange - 1 || k2 == leafRange - 1) {
                  grow &= random.nextInt(4) != 0;
               }

               if (grow) {
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
