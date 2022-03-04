package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenFangornTrees extends WorldGenAbstractTree {
   private Block woodID;
   private int woodMeta;
   private Block leafID;
   private int leafMeta;
   private boolean generateLeaves = true;
   private boolean restrictions = true;
   private float heightFactor = 1.0F;

   public LOTRWorldGenFangornTrees(boolean flag, Block i, int j, Block k, int l) {
      super(flag);
      this.woodID = i;
      this.woodMeta = j;
      this.leafID = k;
      this.leafMeta = l;
   }

   public LOTRWorldGenFangornTrees disableRestrictions() {
      this.restrictions = false;
      return this;
   }

   public LOTRWorldGenFangornTrees setNoLeaves() {
      this.generateLeaves = false;
      return this;
   }

   public LOTRWorldGenFangornTrees setHeightFactor(float f) {
      this.heightFactor = f;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions) {
         Block below = world.func_147439_a(i, j - 1, k);
         boolean isSoil = below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.field_150345_g);
         if (!isSoil) {
            return false;
         }
      }

      float f = 0.5F + random.nextFloat() * 0.5F;
      int height = (int)(f * 40.0F * this.heightFactor);
      int trunkRadiusMin = (int)(f * 5.0F);
      int trunkRadiusMax = trunkRadiusMin + 4;
      int xSlope = 4 + random.nextInt(7);
      if (random.nextBoolean()) {
         xSlope *= -1;
      }

      int zSlope = 4 + random.nextInt(7);
      if (random.nextBoolean()) {
         zSlope *= -1;
      }

      int i1;
      int i1;
      int k1;
      int width;
      int i2;
      int k2;
      if (this.restrictions) {
         boolean flag = true;
         if (j < 1 || j + height + 5 > 256) {
            return false;
         }

         for(i1 = i - 1; i1 <= i + 1; ++i1) {
            for(i1 = k - 1; i1 <= k + 1; ++i1) {
               for(k1 = j; k1 <= j + height; ++k1) {
                  width = trunkRadiusMax;

                  for(i2 = i1 - trunkRadiusMax; i2 <= i1 + width && flag; ++i2) {
                     for(k2 = i1 - width; k2 <= i1 + width && flag; ++k2) {
                        if (k1 >= 0 && k1 < 256) {
                           if (!this.isReplaceable(world, i2, k1, k2)) {
                              flag = false;
                           }
                        } else {
                           flag = false;
                        }
                     }
                  }
               }
            }
         }

         if (!flag) {
            return false;
         }
      }

      int j2;
      int j1;
      for(j1 = 0; j1 < height; ++j1) {
         i1 = trunkRadiusMax - (int)((float)j1 / (float)height * (float)(trunkRadiusMax - trunkRadiusMin));

         for(i1 = i - i1; i1 <= i + i1; ++i1) {
            for(k1 = k - i1; k1 <= k + i1; ++k1) {
               width = i1 - i;
               i2 = k1 - k;
               if (width * width + i2 * i2 < i1 * i1) {
                  Block block = world.func_147439_a(i1, j + j1, k1);
                  if (block == Blocks.field_150350_a || block.isLeaves(world, i1, j + j1, k1)) {
                     this.func_150516_a(world, i1, j + j1, k1, this.woodID, this.woodMeta);
                  }

                  if (j1 == 0) {
                     world.func_147439_a(i1, j - 1, k1).onPlantGrow(world, i1, j - 1, k1, i1, j, k1);

                     for(j2 = j - 1; !LOTRMod.isOpaque(world, i1, j2, k1) && j2 >= 0 && Math.abs(j2 - j) <= 6 + random.nextInt(5); --j2) {
                        this.func_150516_a(world, i1, j2, k1, this.woodID, this.woodMeta);
                        world.func_147439_a(i1, j2 - 1, k1).onPlantGrow(world, i1, j2 - 1, k1, i1, j2, k1);
                     }
                  }
               }
            }
         }

         if (j1 % xSlope == 0) {
            if (xSlope > 0) {
               ++i;
            } else if (xSlope < 0) {
               --i;
            }
         }

         if (j1 % zSlope == 0) {
            if (zSlope > 0) {
               ++k;
            } else if (zSlope < 0) {
               --k;
            }
         }
      }

      j1 = 0;

      while(j1 < 360) {
         j1 += 10 + random.nextInt(20);
         float angleR = (float)j1 / 180.0F * 3.1415927F;
         float sin = MathHelper.func_76126_a(angleR);
         float cos = MathHelper.func_76134_b(angleR);
         width = 12 + random.nextInt(10);
         i2 = Math.round((float)width / 25.0F * 1.5F);
         k2 = j + MathHelper.func_76128_c((double)((float)height * (0.9F + random.nextFloat() * 0.1F)));
         j2 = 3 + random.nextInt(4);

         for(int l = 0; l < width; ++l) {
            int i1 = i + Math.round(sin * (float)l);
            int k1 = k + Math.round(cos * (float)l);
            int j1 = k2 + Math.round((float)l / (float)width * (float)j2);
            int range = i2 - Math.round((float)l / (float)width * (float)i2 * 0.5F);

            int i2;
            for(i2 = i1 - range; i2 <= i1 + range; ++i2) {
               for(int j2 = j1 - range; j2 <= j1 + range; ++j2) {
                  for(int k2 = k1 - range; k2 <= k1 + range; ++k2) {
                     Block block = world.func_147439_a(i2, j2, k2);
                     if (block.isReplaceable(world, i2, j2, k2) || block.isLeaves(world, i2, j2, k2)) {
                        this.func_150516_a(world, i2, j2, k2, this.woodID, this.woodMeta | 12);
                     }
                  }
               }
            }

            i2 = j1 + random.nextInt(360);
            float branch_angleR = (float)i2 / 180.0F * 3.1415927F;
            float branch_sin = MathHelper.func_76126_a(branch_angleR);
            float branch_cos = MathHelper.func_76134_b(branch_angleR);
            int branchLength = 7 + random.nextInt(6);
            int branchHeight = random.nextInt(6);
            int leafRange = 3;

            for(int l1 = 0; l1 < branchLength; ++l1) {
               int i2 = i1 + Math.round(branch_sin * (float)l1);
               int k2 = k1 + Math.round(branch_cos * (float)l1);
               int j2 = j1 + Math.round((float)l1 / (float)branchLength * (float)branchHeight);

               int i3;
               for(i3 = j2; i3 >= j2 - 1; --i3) {
                  Block block = world.func_147439_a(i2, i3, k2);
                  if (block.isReplaceable(world, i2, i3, k2) || block.isLeaves(world, i2, i3, k2)) {
                     this.func_150516_a(world, i2, i3, k2, this.woodID, this.woodMeta | 12);
                  }
               }

               if (this.generateLeaves && l1 == branchLength - 1) {
                  for(i3 = i2 - leafRange; i3 <= i2 + leafRange; ++i3) {
                     for(int j3 = j2 - leafRange; j3 <= j2 + leafRange; ++j3) {
                        for(int k3 = k2 - leafRange; k3 <= k2 + leafRange; ++k3) {
                           int i4 = i3 - i2;
                           int j4 = j3 - j2;
                           int k4 = k3 - k2;
                           int dist = i4 * i4 + j4 * j4 + k4 * k4;
                           if (dist < (leafRange - 1) * (leafRange - 1) || dist < leafRange * leafRange && random.nextInt(3) != 0) {
                              Block block2 = world.func_147439_a(i3, j3, k3);
                              if (block2.func_149688_o() == Material.field_151579_a || block2.isLeaves(world, i3, j3, k3)) {
                                 this.func_150516_a(world, i3, j3, k3, this.leafID, this.leafMeta);
                                 if (random.nextInt(40) == 0 && world.func_147437_c(i3 - 1, j3, k3)) {
                                    this.growVines(world, random, i3 - 1, j3, k3, 8);
                                 }

                                 if (random.nextInt(40) == 0 && world.func_147437_c(i3 + 1, j3, k3)) {
                                    this.growVines(world, random, i3 + 1, j3, k3, 2);
                                 }

                                 if (random.nextInt(40) == 0 && world.func_147437_c(i3, j3, k3 - 1)) {
                                    this.growVines(world, random, i3, j3, k3 - 1, 1);
                                 }

                                 if (random.nextInt(40) == 0 && world.func_147437_c(i3, j3, k3 + 1)) {
                                    this.growVines(world, random, i3, j3, k3 + 1, 4);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }

   private void growVines(World world, Random random, int i, int j, int k, int meta) {
      this.func_150516_a(world, i, j, k, Blocks.field_150395_bd, meta);
      int length = 4 + random.nextInt(12);

      while(true) {
         --j;
         if (!world.func_147437_c(i, j, k) || length <= 0) {
            return;
         }

         this.func_150516_a(world, i, j, k, Blocks.field_150395_bd, meta);
         --length;
      }
   }
}
