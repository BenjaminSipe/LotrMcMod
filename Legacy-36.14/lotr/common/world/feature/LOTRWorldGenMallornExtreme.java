package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRWorldGenElfLordHouse;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenMallornExtreme extends WorldGenAbstractTree {
   private static int HEIGHT_MIN = 35;
   private static int HEIGHT_MAX = 70;
   private static int BOUGH_ANGLE_INTERVAL_MIN = 10;
   private static int BOUGH_ANGLE_INTERVAL_MAX = 30;
   private static int BOUGH_LENGTH_MIN = 15;
   private static int BOUGH_LENGTH_MAX = 25;
   private static float BOUGH_THICKNESS_FACTOR = 0.03F;
   private static float BOUGH_BASE_HEIGHT_MIN = 0.9F;
   private static float BOUGH_BASE_HEIGHT_MAX = 1.0F;
   private static int BOUGH_HEIGHT_MIN = 7;
   private static int BOUGH_HEIGHT_MAX = 10;
   private static int BRANCH_LENGTH_MIN = 8;
   private static int BRANCH_LENGTH_MAX = 10;
   private static int BRANCH_HEIGHT_MIN = 6;
   private static int BRANCH_HEIGHT_MAX = 8;
   public static float HOUSE_HEIGHT_MIN = 0.4F;
   public static float HOUSE_HEIGHT_MAX = 0.7F;
   private static float HOUSE_CHANCE = 0.7F;
   private static float HOUSE_ELFLORD_CHANCE = 0.15F;
   private boolean notify;
   private boolean saplingGrowth = false;

   public LOTRWorldGenMallornExtreme(boolean flag) {
      super(flag);
      this.notify = flag;
   }

   public LOTRWorldGenMallornExtreme setSaplingGrowth() {
      this.saplingGrowth = true;
      return this;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      return this.generateAndReturnHeight(world, random, i, j, k, false) > 0;
   }

   public int generateAndReturnHeight(World world, Random random, int i, int j, int k, boolean forceGeneration) {
      int height = MathHelper.func_76136_a(random, HEIGHT_MIN, HEIGHT_MAX);
      int trunkWidth = 2;
      boolean flag = true;
      if ((j < 1 || j + height + 5 > 256) && !forceGeneration) {
         return 0;
      } else {
         int i1;
         int houseHeight;
         int l;
         int i1;
         for(i1 = j; i1 <= j + 1 + height; ++i1) {
            houseHeight = trunkWidth;
            if (i1 == j) {
               houseHeight = 0;
            }

            if (i1 >= j + 1 + height - 2) {
               houseHeight = trunkWidth + 1;
            }

            for(l = i - houseHeight; l <= i + houseHeight && flag; ++l) {
               for(i1 = k - houseHeight; i1 <= k + houseHeight && flag; ++i1) {
                  if (i1 >= 0 && i1 < 256) {
                     Block block = world.func_147439_a(l, i1, i1);
                     if (!forceGeneration && !this.isReplaceable(world, l, i1, i1) && block != LOTRMod.quenditeGrass) {
                        flag = false;
                     }
                  } else if (!forceGeneration) {
                     flag = false;
                  }
               }
            }
         }

         if (!flag && !forceGeneration) {
            return 0;
         } else {
            boolean spawnedElfLord;
            if (!forceGeneration) {
               for(i1 = i - trunkWidth; i1 <= i + trunkWidth; ++i1) {
                  for(houseHeight = k - trunkWidth; houseHeight <= k + trunkWidth; ++houseHeight) {
                     Block block = world.func_147439_a(i1, j - 1, houseHeight);
                     spawnedElfLord = false;
                     if (this.saplingGrowth) {
                        if (block == LOTRMod.quenditeGrass) {
                           spawnedElfLord = true;
                        }
                     } else if (block.canSustainPlant(world, i1, j - 1, houseHeight, ForgeDirection.UP, (IPlantable)LOTRMod.sapling)) {
                        spawnedElfLord = true;
                     }

                     if (!spawnedElfLord) {
                        return 0;
                     }
                  }
               }
            }

            for(i1 = i - trunkWidth; i1 <= i + trunkWidth; ++i1) {
               for(houseHeight = k - trunkWidth; houseHeight <= k + trunkWidth; ++houseHeight) {
                  if (!forceGeneration) {
                     world.func_147439_a(i1, j - 1, houseHeight).onPlantGrow(world, i1, j - 1, houseHeight, i1, j, houseHeight);
                  }

                  for(l = 0; l < height; ++l) {
                     Block block = world.func_147439_a(i1, j + l, houseHeight);
                     if (block.isReplaceable(world, i1, j + l, houseHeight) || block.isLeaves(world, i1, j + l, houseHeight)) {
                        this.func_150516_a(world, i1, j + l, houseHeight, LOTRMod.wood, 1);
                     }
                  }
               }
            }

            i1 = 0;

            int k1;
            int k1;
            int j2;
            int rootLength;
            int l1;
            int rootBlocks;
            int j2;
            int j1;
            while(i1 < 360) {
               i1 += MathHelper.func_76136_a(random, BOUGH_ANGLE_INTERVAL_MIN, BOUGH_ANGLE_INTERVAL_MAX);
               float angleR = (float)Math.toRadians((double)i1);
               float sin = MathHelper.func_76126_a(angleR);
               float cos = MathHelper.func_76134_b(angleR);
               j1 = MathHelper.func_76136_a(random, BOUGH_LENGTH_MIN, BOUGH_LENGTH_MAX);
               k1 = Math.round((float)j1 * BOUGH_THICKNESS_FACTOR);
               k1 = j + MathHelper.func_76128_c((double)((float)height * MathHelper.func_151240_a(random, BOUGH_BASE_HEIGHT_MIN, BOUGH_BASE_HEIGHT_MAX)));
               j2 = MathHelper.func_76136_a(random, BOUGH_HEIGHT_MIN, BOUGH_HEIGHT_MAX);

               for(rootLength = 0; rootLength < j1; ++rootLength) {
                  l1 = i + Math.round(sin * (float)rootLength);
                  rootBlocks = k + Math.round(cos * (float)rootLength);
                  j2 = k1 + Math.round((float)rootLength / (float)j1 * (float)j2);
                  int range = k1 - Math.round((float)rootLength / (float)j1 * (float)k1 * 0.5F);

                  int i2;
                  int j2;
                  for(i2 = l1 - range; i2 <= l1 + range; ++i2) {
                     for(j2 = j2 - range; j2 <= j2 + range; ++j2) {
                        for(int k2 = rootBlocks - range; k2 <= rootBlocks + range; ++k2) {
                           Block block = world.func_147439_a(i2, j2, k2);
                           if (block.isReplaceable(world, i2, j2, k2) || block.isLeaves(world, i2, j2, k2)) {
                              this.func_150516_a(world, i2, j2, k2, LOTRMod.wood, 13);
                           }
                        }
                     }
                  }

                  if (rootLength == j1 - 1) {
                     i2 = MathHelper.func_76136_a(random, 8, 16);

                     for(j2 = 0; j2 < i2; ++j2) {
                        float branch_angle = random.nextFloat() * 2.0F * 3.1415927F;
                        float branch_sin = MathHelper.func_76126_a(branch_angle);
                        float branch_cos = MathHelper.func_76134_b(branch_angle);
                        int branchLength = MathHelper.func_76136_a(random, BRANCH_LENGTH_MIN, BRANCH_LENGTH_MAX);
                        int branchHeight = MathHelper.func_76136_a(random, BRANCH_HEIGHT_MIN, BRANCH_HEIGHT_MAX);

                        for(int b1 = 0; b1 < branchLength; ++b1) {
                           int i2 = l1 + Math.round(branch_sin * (float)b1);
                           int k2 = rootBlocks + Math.round(branch_cos * (float)b1);
                           int j2 = j2 + Math.round((float)b1 / (float)branchLength * (float)branchHeight);
                           Block block = world.func_147439_a(i2, j2, k2);
                           if (block.isReplaceable(world, i2, j2, k2) || block.isLeaves(world, i2, j2, k2)) {
                              this.func_150516_a(world, i2, j2, k2, LOTRMod.wood, 13);
                           }

                           if (b1 == branchLength - 1) {
                              this.spawnLeafCluster(world, random, i2, j2, k2, 3);
                           }
                        }
                     }
                  }
               }
            }

            if (trunkWidth > 0) {
               for(houseHeight = j + (int)((float)height * BOUGH_BASE_HEIGHT_MIN); houseHeight > j + (int)((float)height * 0.67F); houseHeight -= 1 + random.nextInt(3)) {
                  l = 1 + random.nextInt(5);

                  for(i1 = 0; i1 < l; ++i1) {
                     float branchAngle = random.nextFloat() * 3.1415927F * 2.0F;
                     k1 = i + (int)(1.5F + MathHelper.func_76134_b(branchAngle) * 4.0F);
                     k1 = k + (int)(1.5F + MathHelper.func_76126_a(branchAngle) * 4.0F);
                     j2 = houseHeight;
                     rootLength = MathHelper.func_76136_a(random, 10, 20);

                     for(l1 = 0; l1 < rootLength; ++l1) {
                        k1 = i + (int)(1.5F + MathHelper.func_76134_b(branchAngle) * (float)l1);
                        k1 = k + (int)(1.5F + MathHelper.func_76126_a(branchAngle) * (float)l1);
                        j2 = houseHeight - 3 + l1 / 2;
                        if (!this.isReplaceable(world, k1, j2, k1)) {
                           break;
                        }

                        this.func_150516_a(world, k1, j2, k1, LOTRMod.wood, 13);
                     }

                     this.spawnLeafLayer(world, random, k1, j2 + 1, k1, 2);
                     this.spawnLeafLayer(world, random, k1, j2, k1, 3);
                     this.spawnLeafLayer(world, random, k1, j2 - 1, k1, 1);
                  }
               }
            }

            if (trunkWidth > 0) {
               houseHeight = MathHelper.func_76136_a(random, 6, 10);

               for(l = 0; l < houseHeight; ++l) {
                  j1 = j + 1 + random.nextInt(5);
                  int xDirection = 0;
                  int zDirection = 0;
                  rootLength = 1 + random.nextInt(4);
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

                     for(j2 = j1; !LOTRMod.isOpaque(world, i1, j2, k1); --j2) {
                        this.func_150516_a(world, i1, j2, k1, LOTRMod.wood, 13);
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

            if (!this.saplingGrowth && !this.notify && !forceGeneration && random.nextFloat() < HOUSE_CHANCE) {
               houseHeight = MathHelper.func_76128_c((double)((float)height * MathHelper.func_151240_a(random, HOUSE_HEIGHT_MIN, HOUSE_HEIGHT_MAX)));
               boolean isElfLordTree = random.nextFloat() < HOUSE_ELFLORD_CHANCE;
               spawnedElfLord = false;
               if (isElfLordTree) {
                  LOTRWorldGenElfLordHouse house = new LOTRWorldGenElfLordHouse(true);
                  house.restrictions = false;
                  spawnedElfLord = house.func_76484_a(world, random, i, j + houseHeight, k);
               }

               if (!isElfLordTree || !spawnedElfLord) {
                  LOTRWorldGenElfHouse house = new LOTRWorldGenElfHouse(true);
                  house.restrictions = false;
                  house.func_76484_a(world, random, i, j + houseHeight, k);
               }
            }

            return height;
         }
      }
   }

   private void spawnLeafCluster(World world, Random random, int i, int j, int k, int leafRange) {
      int leafRangeSq = leafRange * leafRange;
      int leafRangeSqLess = (int)(((double)leafRange - 0.5D) * ((double)leafRange - 0.5D));

      for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
         for(int j1 = j - leafRange; j1 <= j + leafRange; ++j1) {
            for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
               int i2 = i1 - i;
               int j2 = j1 - j;
               int k2 = k1 - k;
               int dist = i2 * i2 + j2 * j2 + k2 * k2;
               if (dist < leafRangeSqLess || dist < leafRangeSq && random.nextInt(3) == 0) {
                  Block block = world.func_147439_a(i1, j1, k1);
                  if (block.isReplaceable(world, i1, j1, k1) || block.isLeaves(world, i1, j1, k1)) {
                     this.func_150516_a(world, i1, j1, k1, LOTRMod.leaves, 1);
                  }
               }
            }
         }
      }

   }

   private void spawnLeafLayer(World world, Random random, int i, int j, int k, int leafRange) {
      int leafRangeSq = leafRange * leafRange;

      for(int i1 = i - leafRange; i1 <= i + leafRange; ++i1) {
         for(int k1 = k - leafRange; k1 <= k + leafRange; ++k1) {
            int i2 = i1 - i;
            int k2 = k1 - k;
            int dist = i2 * i2 + k2 * k2;
            if (dist <= leafRangeSq) {
               Block block = world.func_147439_a(i1, j, k1);
               if (block.isReplaceable(world, i1, j, k1) || block.isLeaves(world, i1, j, k1)) {
                  this.func_150516_a(world, i1, j, k1, LOTRMod.leaves, 1);
               }
            }
         }
      }

   }
}
