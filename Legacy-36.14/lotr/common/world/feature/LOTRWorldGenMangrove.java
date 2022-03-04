package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenMangrove extends WorldGenAbstractTree {
   private Block woodID;
   private int woodMeta;
   private Block leafID;
   private int leafMeta;

   public LOTRWorldGenMangrove(boolean flag) {
      super(flag);
      this.woodID = LOTRMod.wood3;
      this.woodMeta = 3;
      this.leafID = LOTRMod.leaves3;
      this.leafMeta = 3;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int height = 6 + random.nextInt(5);
      boolean flag = true;
      if (j >= 1 && j + height + 1 <= 256) {
         for(int j1 = j; j1 <= j + 1 + height; ++j1) {
            int range = 1;
            if (j1 == j) {
               range = 0;
            }

            if (j1 >= j + 1 + height - 2) {
               range = 2;
            }

            for(int i1 = i - range; i1 <= i + range && flag; ++i1) {
               for(int k1 = k - range; k1 <= k + range && flag; ++k1) {
                  if (j1 >= 0 && j1 < 256) {
                     if (!this.isReplaceable(world, i1, j1, k1)) {
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
            Block below = world.func_147439_a(i, j - 1, k);
            boolean canGrow = below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g) || below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, Blocks.field_150330_I);
            if (!canGrow) {
               return false;
            } else {
               world.func_147439_a(i, j - 1, k).onPlantGrow(world, i, j - 1, k, i, j, k);
               int leafStart = 3;
               int leafRangeMin = 0;
               int leafRangeFactor = 2;

               int j1;
               int k1;
               int i2;
               int i1;
               int rootX;
               int k1;
               int rootZ;
               for(j1 = j - leafStart + height; j1 <= j + height; ++j1) {
                  k1 = j1 - (j + height);
                  i2 = leafRangeMin + 1 - k1 / leafRangeFactor;

                  for(i1 = i - i2; i1 <= i + i2; ++i1) {
                     rootX = i1 - i;

                     for(k1 = k - i2; k1 <= k + i2; ++k1) {
                        rootZ = k1 - k;
                        Block block = world.func_147439_a(i1, j1, k1);
                        if ((Math.abs(rootX) != i2 || Math.abs(rootZ) != i2 || random.nextInt(2) != 0 && k1 != 0) && block.canBeReplacedByLeaves(world, i1, j1, k1)) {
                           this.func_150516_a(world, i1, j1, k1, this.leafID, this.leafMeta);
                           if (random.nextInt(8) == 0 && world.func_147439_a(i1 - 1, j1, k1).isAir(world, i1 - 1, j1, k1)) {
                              this.growVines(world, random, i1 - 1, j1, k1, 8);
                           }

                           if (random.nextInt(8) == 0 && world.func_147439_a(i1 + 1, j1, k1).isAir(world, i1 + 1, j1, k1)) {
                              this.growVines(world, random, i1 + 1, j1, k1, 2);
                           }

                           if (random.nextInt(8) == 0 && world.func_147439_a(i1, j1, k1 - 1).isAir(world, i1, j1, k1 - 1)) {
                              this.growVines(world, random, i1, j1, k1 - 1, 1);
                           }

                           if (random.nextInt(8) == 0 && world.func_147439_a(i1, j1, k1 + 1).isAir(world, i1, j1, k1 + 1)) {
                              this.growVines(world, random, i1, j1, k1 + 1, 4);
                           }
                        }
                     }
                  }
               }

               for(j1 = 0; j1 < height; ++j1) {
                  Block block = world.func_147439_a(i, j + j1, k);
                  if (block.isReplaceable(world, i, j + j1, k) || block.isLeaves(world, i, j + j1, k)) {
                     this.func_150516_a(world, i, j + j1, k, this.woodID, this.woodMeta);
                  }
               }

               for(j1 = i - 1; j1 <= i + 1; ++j1) {
                  for(k1 = k - 1; k1 <= k + 1; ++k1) {
                     i2 = j1 - i;
                     i1 = k1 - k;
                     if (Math.abs(i2) != Math.abs(i1)) {
                        rootX = j1;
                        k1 = j + 1 + random.nextInt(3);
                        rootZ = k1;
                        int xWay = Integer.signum(i2);
                        int zWay = Integer.signum(i1);
                        int roots = 0;

                        while(world.func_147439_a(rootX, k1, k1).isReplaceable(world, rootX, k1, rootZ)) {
                           this.func_150516_a(world, rootX, k1, rootZ, this.woodID, this.woodMeta | 12);
                           world.func_147439_a(rootX, k1 - 1, rootZ).onPlantGrow(world, rootX, k1 - 1, rootZ, rootX, k1, rootZ);
                           --k1;
                           if (random.nextInt(3) > 0) {
                              rootX += xWay;
                              rootZ += zWay;
                           }

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

   private void growVines(World world, Random random, int i, int j, int k, int meta) {
      this.func_150516_a(world, i, j, k, Blocks.field_150395_bd, meta);
      int vines = 0;

      while(true) {
         --j;
         if (!world.func_147439_a(i, j, k).isAir(world, i, j, k) || vines >= 2 + random.nextInt(3)) {
            return;
         }

         this.func_150516_a(world, i, j, k, Blocks.field_150395_bd, meta);
         ++vines;
      }
   }
}
