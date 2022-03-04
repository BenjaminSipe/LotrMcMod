package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDaleVillageTower extends LOTRWorldGenDaleStructure {
   public LOTRWorldGenDaleVillageTower(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 3);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      if (this.restrictions) {
         for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
               i2 = this.getTopBlock(world, i1, k1) - 1;
               Block block = this.getBlock(world, i1, i2, k1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }
            }
         }
      }

      int k2;
      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            int j1;
            if (i2 <= 2 && k2 <= 2) {
               for(j1 = 0; (j1 == 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.floorBlock, this.floorMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               for(j1 = 1; j1 <= 10; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }

            if (i2 == 2 && k2 == 3 || k2 == 2 && i2 == 3) {
               for(j1 = 1; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.brickWallBlock, this.brickWallMeta);
               }
            }

            if (i2 <= 2 && k2 <= 2) {
               if (i2 == 2 && k2 == 2) {
                  for(j1 = 1; j1 <= 6; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                  }
               } else if (i2 == 2 || k2 == 2) {
                  for(j1 = 4; j1 <= 6; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                  }
               }

               if (i2 == 2 && k2 == 2) {
                  this.setBlockAndMetadata(world, i1, 7, k1, this.brickWallBlock, this.brickWallMeta);
               }

               if (i2 == 2 && k2 == 1 || k2 == 2 && i2 == 1) {
                  this.setBlockAndMetadata(world, i1, 7, k1, this.brickBlock, this.brickMeta);
               }

               if (i2 == 2 && k2 == 0 || k2 == 2 && i2 == 0) {
                  this.setBlockAndMetadata(world, i1, 7, k1, this.barsBlock, 0);
                  this.setBlockAndMetadata(world, i1, 8, k1, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, i1, 9, k1, this.brickSlabBlock, this.brickSlabMeta);
               }

               if (i2 <= 1 && k2 <= 1) {
                  if (i2 == 1 && k2 == 1) {
                     this.setBlockAndMetadata(world, i1, 6, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
                     this.setBlockAndMetadata(world, i1, 7, k1, this.brickWallBlock, this.brickWallMeta);

                     for(j1 = 8; j1 <= 11; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                     }

                     this.setBlockAndMetadata(world, i1, 12, k1, this.brickWallBlock, this.brickWallMeta);
                  }

                  if (i2 == 1 && k2 == 0 || k2 == 1 && i2 == 0) {
                     this.setBlockAndMetadata(world, i1, 9, k1, this.brickBlock, this.brickMeta);
                     this.setBlockAndMetadata(world, i1, 10, k1, this.brickBlock, this.brickMeta);
                  }

                  if (i2 == 0 && k2 == 0) {
                     this.setBlockAndMetadata(world, i1, 9, k1, this.brickBlock, this.brickMeta);
                     this.setBlockAndMetadata(world, i1, 10, k1, this.brickBlock, this.brickMeta);
                     this.setBlockAndMetadata(world, i1, 11, k1, LOTRMod.hearth, 0);
                     this.setBlockAndMetadata(world, i1, 12, k1, Blocks.field_150480_ab, 0);
                     this.setBlockAndMetadata(world, i1, 13, k1, this.roofBlock, this.roofMeta);
                     this.setBlockAndMetadata(world, i1, 14, k1, this.roofSlabBlock, this.roofSlabMeta);
                  }
               }
            }
         }
      }

      int[] var12 = new int[]{-2, 2};
      k1 = var12.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var12[i2];
         this.setBlockAndMetadata(world, k2, 3, -1, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, k2, 3, 1, this.brickStairBlock, 6);
      }

      var12 = new int[]{-2, 2};
      k1 = var12.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var12[i2];
         this.setBlockAndMetadata(world, -1, 3, k2, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 1, 3, k2, this.brickStairBlock, 5);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, -3, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 5, 3, this.brickStairBlock, 7);
         if (IntMath.mod(i1, 2) == 1) {
            this.setBlockAndMetadata(world, i1, 6, -3, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i1, 6, 3, this.brickWallBlock, this.brickWallMeta);
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -3, 5, i1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, 3, 5, i1, this.brickStairBlock, 4);
         if (IntMath.mod(i1, 2) == 1) {
            this.setBlockAndMetadata(world, -3, 6, i1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, 3, 6, i1, this.brickWallBlock, this.brickWallMeta);
         }
      }

      var12 = new int[]{-2, 2};
      k1 = var12.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var12[i2];
         this.setBlockAndMetadata(world, k2, 8, -1, this.brickStairBlock, 2);
         this.setBlockAndMetadata(world, k2, 8, 1, this.brickStairBlock, 3);
      }

      var12 = new int[]{-2, 2};
      k1 = var12.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var12[i2];
         this.setBlockAndMetadata(world, -1, 8, k2, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, 1, 8, k2, this.brickStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -1, 8, 0, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 8, 0, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, 0, 8, -1, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, 0, 8, 1, this.brickStairBlock, 6);

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 10, -2, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 10, 2, this.roofStairBlock, 3);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -2, 10, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 2, 10, i1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -1, 11, 0, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 1, 11, 0, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 0, 11, -1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 0, 11, 1, this.brickStairBlock, 3);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 13, -1, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 13, 1, this.roofStairBlock, 3);
      }

      this.setBlockAndMetadata(world, -1, 13, 0, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 1, 13, 0, this.roofStairBlock, 0);
      this.placeWallBanner(world, 0, 4, -2, LOTRItemBanner.BannerType.DALE, 2);
      this.placeWallBanner(world, -2, 4, 0, LOTRItemBanner.BannerType.DALE, 3);
      this.placeWallBanner(world, 0, 4, 2, LOTRItemBanner.BannerType.DALE, 0);
      this.placeWallBanner(world, 2, 4, 0, LOTRItemBanner.BannerType.DALE, 1);
      this.setBlockAndMetadata(world, -1, 5, 0, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 5, 0, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 0, 5, -1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 0, 5, 1, Blocks.field_150478_aa, 4);
      return true;
   }
}
