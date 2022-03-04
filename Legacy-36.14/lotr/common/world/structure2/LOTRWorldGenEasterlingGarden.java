package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingGarden extends LOTRWorldGenEasterlingStructure {
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenEasterlingGarden(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.leafBlock = LOTRMod.leaves6;
      this.leafMeta = 2;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 10);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int k2;
      int sum;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -10; i2 <= 10; ++i2) {
            for(k2 = -10; k2 <= 10; ++k2) {
               sum = this.getTopBlock(world, i2, k2) - 1;
               if (!this.isSurface(world, i2, sum, k2)) {
                  return false;
               }

               if (sum < i1) {
                  i1 = sum;
               }

               if (sum > k1) {
                  k1 = sum;
               }

               if (k1 - i1 > 8) {
                  return false;
               }
            }
         }
      }

      int k1;
      for(i1 = -10; i1 <= 10; ++i1) {
         for(k1 = -10; k1 <= 10; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);

            for(sum = 0; (sum >= 0 || !this.isOpaque(world, i1, sum, k1)) && this.getY(sum) >= 0; --sum) {
               if (sum == 0) {
                  this.setBlockAndMetadata(world, i1, sum, k1, Blocks.field_150349_c, 0);
               } else {
                  this.setBlockAndMetadata(world, i1, sum, k1, Blocks.field_150346_d, 0);
               }

               this.setGrassToDirt(world, i1, sum - 1, k1);
            }

            for(sum = 1; sum <= 9; ++sum) {
               this.setAir(world, i1, sum, k1);
            }

            if (i2 <= 9 && k2 <= 9 && (i2 == 9 && k2 >= 2 && k2 <= 8 || k2 == 9 && i2 >= 2 && i2 <= 8)) {
               this.setBlockAndMetadata(world, i1, 0, k1, this.brickBlock, this.brickMeta);
               if (i2 == 9 && k2 == 2 || k2 == 9 && i2 == 2) {
                  for(sum = 1; sum <= 6; ++sum) {
                     this.setBlockAndMetadata(world, i1, sum, k1, this.pillarBlock, this.pillarMeta);
                  }
               } else {
                  for(sum = 1; sum <= 2; ++sum) {
                     this.setBlockAndMetadata(world, i1, sum, k1, this.brickBlock, this.brickMeta);
                  }

                  if (i2 == 9 && k2 % 2 == 0 || k2 == 9 && i2 % 2 == 0) {
                     for(sum = 3; sum <= 6; ++sum) {
                        this.setBlockAndMetadata(world, i1, sum, k1, this.pillarBlock, this.pillarMeta);
                     }
                  }
               }
            }

            if (i2 >= 2 && i2 <= 8 && k2 >= 2 && k2 <= 8) {
               if ((i2 != 2 || k2 < 5) && (k2 != 2 || i2 < 5)) {
                  if (i2 == 3 && k2 == 3) {
                     for(sum = 1; sum <= 3; ++sum) {
                        this.setBlockAndMetadata(world, i1, sum, k1, this.fenceBlock, this.fenceMeta);
                     }
                  } else {
                     sum = i2 + k2;
                     if (sum >= 4 && sum <= 7) {
                        if (random.nextBoolean()) {
                           this.plantFlower(world, random, i1, 1, k1);
                        }
                     } else if (sum >= 8 && sum <= 9) {
                        this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150349_c, 0);
                        this.setGrassToDirt(world, i1, 0, k1);
                        if (random.nextBoolean()) {
                           this.plantFlower(world, random, i1, 2, k1);
                        }
                     } else {
                        this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150349_c, 0);
                        this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150346_d, 0);
                        this.setGrassToDirt(world, i1, 0, k1);
                        if (sum >= 12 && i2 <= 7 && k2 <= 7) {
                           this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150355_j, 0);
                        } else if (random.nextBoolean()) {
                           this.plantFlower(world, random, i1, 3, k1);
                        }
                     }
                  }
               } else {
                  sum = 0;
                  if (i2 == 2) {
                     sum = k2 - 4;
                  } else if (k2 == 2) {
                     sum = i2 - 4;
                  }

                  for(k1 = 1; k1 <= sum; ++k1) {
                     this.setBlockAndMetadata(world, i1, k1, k1, this.leafBlock, this.leafMeta | 4);
                  }
               }
            }

            if (k2 == 10 && i2 <= 9) {
               this.setBlockAndMetadata(world, i1, 7, k1, this.brickStairBlock, k1 > 0 ? 7 : 6);
               this.setBlockAndMetadata(world, i1, 8, k1, this.brickBlock, this.brickMeta);
            }

            if (i2 == 10 && k2 <= 9) {
               this.setBlockAndMetadata(world, i1, 7, k1, this.brickStairBlock, i1 > 0 ? 4 : 5);
               this.setBlockAndMetadata(world, i1, 8, k1, this.brickBlock, this.brickMeta);
            }

            if (k2 == 8 && i2 <= 7) {
               this.setBlockAndMetadata(world, i1, 7, k1, this.brickStairBlock, k1 > 0 ? 6 : 7);
               this.setBlockAndMetadata(world, i1, 8, k1, this.brickWallBlock, this.brickWallMeta);
            }

            if (i2 == 8 && k2 <= 7) {
               this.setBlockAndMetadata(world, i1, 7, k1, this.brickStairBlock, i1 > 0 ? 5 : 4);
               this.setBlockAndMetadata(world, i1, 8, k1, this.brickWallBlock, this.brickWallMeta);
            }

            if (i2 == 9 && k2 == 9) {
               this.setBlockAndMetadata(world, i1, 7, k1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1, 8, k1, this.brickBlock, this.brickMeta);
            }

            if (i2 == 8 && k2 == 8) {
               this.setBlockAndMetadata(world, i1, 7, k1, this.brickStairBlock, k1 > 0 ? 6 : 7);
            }

            if (k2 == 9 && i2 <= 8 || i2 == 9 && k2 <= 8) {
               this.setBlockAndMetadata(world, i1, 7, k1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1, 8, k1, Blocks.field_150355_j, 0);
            }

            if (i2 <= 1 && k2 <= 1) {
               if (i2 == 0 && k2 == 0) {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.brickCarvedBlock, this.brickCarvedMeta);
               } else if (i2 + k2 == 1) {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.brickFloweryBlock, this.brickFloweryMeta);
               } else if (i2 + k2 == 2) {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.brickRedBlock, this.brickRedMeta);
               }
            }

            if (i2 <= 1 && k2 >= 2 && k2 <= 9) {
               if (i2 == 0) {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.brickRedBlock, this.brickRedMeta);
               } else {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.brickBlock, this.brickMeta);
               }
            }

            if (k2 <= 1 && i2 >= 2 && i2 <= 9) {
               if (k2 == 0) {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.brickRedBlock, this.brickRedMeta);
               } else {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.brickBlock, this.brickMeta);
               }
            }

            if (k2 == 8 && i2 >= 7 && i2 <= 8 || i2 == 8 && k2 >= 7 && k2 <= 8) {
               this.setBlockAndMetadata(world, i1, 8, k1, Blocks.field_150355_j, 0);
            }
         }
      }

      int domeRadius = 4;
      k1 = domeRadius * domeRadius;
      int domeX = 0;
      int domeY = 4;
      int domeZ = 0;

      int k2;
      int j1;
      int k1;
      int dy;
      int i1;
      for(k1 = -3; k1 <= 3; ++k1) {
         for(k2 = -3; k2 <= 3; ++k2) {
            for(j1 = 4; j1 <= 8; ++j1) {
               k1 = k1 - domeX;
               dy = j1 - domeY;
               i1 = k2 - domeZ;
               float dSq = (float)(k1 * k1 + dy * dy + i1 * i1);
               if ((double)Math.abs(dSq - (float)k1) <= 3.0D) {
                  this.setBlockAndMetadata(world, k1, j1, k2, this.leafBlock, this.leafMeta | 4);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -9, 7, -8, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, -8, 7, -8, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, 8, 7, -8, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, 9, 7, -8, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, -9, 7, 8, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, -8, 7, 8, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, 8, 7, 8, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, 9, 7, 8, this.brickStairBlock, 7);
      int[] var22 = new int[]{-9, 9};
      k2 = var22.length;

      for(j1 = 0; j1 < k2; ++j1) {
         k1 = var22[j1];
         this.setBlockAndMetadata(world, -1, 5, k1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 1, 5, k1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, -1, 6, k1, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, 0, 6, k1, this.brickSlabBlock, this.brickSlabMeta);
         this.setBlockAndMetadata(world, 1, 6, k1, this.brickStairBlock, 1);
      }

      var22 = new int[]{-9, 9};
      k2 = var22.length;

      for(j1 = 0; j1 < k2; ++j1) {
         k1 = var22[j1];
         this.setBlockAndMetadata(world, k1, 5, -1, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, k1, 5, 1, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, k1, 6, -1, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, k1, 6, 0, this.brickSlabBlock, this.brickSlabMeta);
         this.setBlockAndMetadata(world, k1, 6, 1, this.brickStairBlock, 2);
      }

      var22 = new int[]{-2, 2};
      k2 = var22.length;

      for(j1 = 0; j1 < k2; ++j1) {
         k1 = var22[j1];
         this.setBlockAndMetadata(world, k1, 6, -8, Blocks.field_150478_aa, 3);
         this.setBlockAndMetadata(world, k1, 6, 8, Blocks.field_150478_aa, 4);
      }

      var22 = new int[]{-2, 2};
      k2 = var22.length;

      for(j1 = 0; j1 < k2; ++j1) {
         k1 = var22[j1];
         this.setBlockAndMetadata(world, -8, 6, k1, Blocks.field_150478_aa, 2);
         this.setBlockAndMetadata(world, 8, 6, k1, Blocks.field_150478_aa, 1);
      }

      int[] var24;
      for(k1 = -8; k1 <= 8; ++k1) {
         k2 = Math.abs(k1);
         if (k2 == 0) {
            this.setBlockAndMetadata(world, k1, 8, -10, this.brickRedCarvedBlock, this.brickRedCarvedMeta);
            this.setBlockAndMetadata(world, k1, 9, -10, this.brickRedStairBlock, 3);
            this.setBlockAndMetadata(world, k1, 8, 10, this.brickRedCarvedBlock, this.brickRedCarvedMeta);
            this.setBlockAndMetadata(world, k1, 9, 10, this.brickRedStairBlock, 2);
         }

         if (k2 == 3 || k2 == 7) {
            var24 = new int[]{-10, 10};
            k1 = var24.length;

            for(dy = 0; dy < k1; ++dy) {
               i1 = var24[dy];
               this.setBlockAndMetadata(world, k1 - 1, 9, i1, this.brickStairBlock, 1);
               this.setBlockAndMetadata(world, k1, 9, i1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, k1 + 1, 9, i1, this.brickStairBlock, 0);
            }
         }
      }

      for(k1 = -8; k1 <= 8; ++k1) {
         k2 = Math.abs(k1);
         if (k2 == 0) {
            this.setBlockAndMetadata(world, -10, 8, k1, this.brickRedCarvedBlock, this.brickRedCarvedMeta);
            this.setBlockAndMetadata(world, -10, 9, k1, this.brickRedStairBlock, 0);
            this.setBlockAndMetadata(world, 10, 8, k1, this.brickRedCarvedBlock, this.brickRedCarvedMeta);
            this.setBlockAndMetadata(world, 10, 9, k1, this.brickRedStairBlock, 1);
         }

         if (k2 == 3 || k2 == 7) {
            var24 = new int[]{-10, 10};
            k1 = var24.length;

            for(dy = 0; dy < k1; ++dy) {
               i1 = var24[dy];
               this.setBlockAndMetadata(world, i1, 9, k1 - 1, this.brickStairBlock, 2);
               this.setBlockAndMetadata(world, i1, 9, k1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1, 9, k1 + 1, this.brickStairBlock, 3);
            }
         }
      }

      return true;
   }
}
