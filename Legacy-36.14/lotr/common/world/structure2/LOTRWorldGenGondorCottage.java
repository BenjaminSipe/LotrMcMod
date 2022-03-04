package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorCottage extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorCottage(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.wallBlock = LOTRMod.daub;
      this.wallMeta = 0;
      if (random.nextInt(3) == 0) {
         this.roofBlock = this.brick2Block;
         this.roofMeta = this.brick2Meta;
         this.roofSlabBlock = this.brick2SlabBlock;
         this.roofSlabMeta = this.brick2SlabMeta;
         this.roofStairBlock = this.brick2StairBlock;
         this.bedBlock = Blocks.field_150324_C;
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      this.setupRandomBlocks(random);
      int men;
      int l;
      int i2;
      int i1;
      int i2;
      if (this.restrictions) {
         men = 0;
         l = 0;

         for(i2 = -6; i2 <= 6; ++i2) {
            for(i1 = -7; i1 <= 10; ++i1) {
               i2 = this.getTopBlock(world, i2, i1) - 1;
               if (!this.isSurface(world, i2, i2, i1)) {
                  return false;
               }

               if (i2 < men) {
                  men = i2;
               }

               if (i2 > l) {
                  l = i2;
               }

               if (l - men > 5) {
                  return false;
               }
            }
         }
      }

      for(men = -5; men <= 5; ++men) {
         for(l = -5; l <= 5; ++l) {
            i2 = Math.abs(men);
            i1 = Math.abs(l);
            if (i2 == 5 && i1 == 5) {
               for(i2 = 3; (i2 >= 0 || !this.isOpaque(world, men, i2, l)) && this.getY(i2) >= 0; --i2) {
                  this.setBlockAndMetadata(world, men, i2, l, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, men, i2 - 1, l);
               }
            } else if (i2 != 5 && i1 != 5) {
               for(i2 = 0; (i2 >= 0 || !this.isOpaque(world, men, i2, l)) && this.getY(i2) >= 0; --i2) {
                  this.setBlockAndMetadata(world, men, i2, l, this.rockBlock, this.rockMeta);
                  this.setGrassToDirt(world, men, i2 - 1, l);
               }

               for(i2 = 1; i2 <= 7; ++i2) {
                  this.setAir(world, men, i2, l);
               }

               if (random.nextInt(3) != 0) {
                  this.setBlockAndMetadata(world, men, 1, l, LOTRMod.thatchFloor, 0);
               }

               if (i2 == 4 && i1 == 4) {
                  for(i2 = 1; i2 <= 4; ++i2) {
                     this.setBlockAndMetadata(world, men, i2, l, this.woodBeamBlock, this.woodBeamMeta);
                  }
               }
            } else {
               for(i2 = 1; (i2 >= 0 || !this.isOpaque(world, men, i2, l)) && this.getY(i2) >= 0; --i2) {
                  this.setBlockAndMetadata(world, men, i2, l, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, men, i2 - 1, l);
               }

               this.setBlockAndMetadata(world, men, 2, l, this.wallBlock, this.wallMeta);
               this.setBlockAndMetadata(world, men, 3, l, this.wallBlock, this.wallMeta);
            }
         }
      }

      for(men = -5; men <= 5; ++men) {
         for(l = -7; l <= -6; ++l) {
            for(i2 = 0; (i2 >= 0 || !this.isOpaque(world, men, i2, l)) && this.getY(i2) >= 0; --i2) {
               this.setBlockAndMetadata(world, men, i2, l, LOTRMod.dirtPath, 0);
               this.setGrassToDirt(world, men, i2 - 1, l);
            }

            for(i2 = 1; i2 <= 8; ++i2) {
               this.setAir(world, men, i2, l);
            }
         }
      }

      for(men = -4; men <= 4; ++men) {
         for(l = 6; l <= 10; ++l) {
            if (l != 10 || Math.abs(men) < 3) {
               for(i2 = 0; (i2 >= 0 || !this.isOpaque(world, men, i2, l)) && this.getY(i2) >= 0; --i2) {
                  this.setBlockAndMetadata(world, men, i2, l, LOTRMod.dirtPath, 0);
                  this.setGrassToDirt(world, men, i2 - 1, l);
               }

               for(i2 = 1; i2 <= 8; ++i2) {
                  this.setAir(world, men, i2, l);
               }
            }
         }
      }

      int[] var13 = new int[]{-5, 5};
      l = var13.length;

      for(i2 = 0; i2 < l; ++i2) {
         i1 = var13[i2];
         this.setBlockAndMetadata(world, i1, 2, -3, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 2, -2, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 2, 0, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 2, 2, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 2, 3, this.fenceBlock, this.fenceMeta);
      }

      var13 = new int[]{-5, 5};
      l = var13.length;

      int k1;
      for(i2 = 0; i2 < l; ++i2) {
         i1 = var13[i2];

         for(i2 = -1; i2 <= 1; ++i2) {
            for(k1 = 2; k1 <= 3; ++k1) {
               this.setBlockAndMetadata(world, i2, k1, i1, this.brickBlock, this.brickMeta);
            }
         }

         for(i2 = -4; i2 <= 4; ++i2) {
            this.setBlockAndMetadata(world, i2, 4, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
         }
      }

      for(men = -3; men <= 3; ++men) {
         if (Math.abs(men) > 1) {
            this.setBlockAndMetadata(world, men, 2, -5, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, men, 3, -5, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, men, 2, 5, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, men, 3, 5, this.wallBlock, this.wallMeta);
         }
      }

      this.setBlockAndMetadata(world, 0, 0, -5, this.rockBlock, this.rockMeta);
      this.setBlockAndMetadata(world, 0, 1, -5, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -5, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 3, -6, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 0, 5, this.rockBlock, this.rockMeta);
      this.setBlockAndMetadata(world, 0, 1, 5, this.doorBlock, 3);
      this.setBlockAndMetadata(world, 0, 2, 5, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 3, 6, Blocks.field_150478_aa, 3);
      var13 = new int[]{-5, 5};
      l = var13.length;

      for(i2 = 0; i2 < l; ++i2) {
         i1 = var13[i2];

         for(i2 = 0; i2 <= 2; ++i2) {
            for(k1 = -3 + i2; k1 <= 3 - i2; ++k1) {
               this.setBlockAndMetadata(world, k1, 5 + i2, i1, this.wallBlock, this.wallMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 5, -5, this.wallBlock, this.wallMeta);
      this.setBlockAndMetadata(world, 0, 6, -5, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 7, -5, this.wallBlock, this.wallMeta);
      this.setBlockAndMetadata(world, 0, 5, 5, this.wallBlock, this.wallMeta);
      this.setBlockAndMetadata(world, 0, 6, 5, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 7, 5, this.wallBlock, this.wallMeta);

      for(men = -6; men <= 6; ++men) {
         for(l = 0; l <= 5; ++l) {
            this.setBlockAndMetadata(world, -6 + l, 3 + l, men, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 6 - l, 3 + l, men, this.roofStairBlock, 0);
         }

         this.setBlockAndMetadata(world, 0, 8, men, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 0, 9, men, this.roofSlabBlock, this.roofSlabMeta);
         if (Math.abs(men) == 6) {
            for(l = 0; l <= 4; ++l) {
               this.setBlockAndMetadata(world, -5 + l, 3 + l, men, this.roofStairBlock, 4);
               this.setBlockAndMetadata(world, 5 - l, 3 + l, men, this.roofStairBlock, 5);
            }
         }
      }

      for(men = -4; men <= 4; ++men) {
         this.setBlockAndMetadata(world, men, 4, 0, this.woodBeamBlock, this.woodBeamMeta | 4);
      }

      for(men = -4; men <= 4; ++men) {
         this.setBlockAndMetadata(world, 0, 4, men, this.woodBeamBlock, this.woodBeamMeta | 8);
      }

      for(men = 1; men <= 7; ++men) {
         this.setBlockAndMetadata(world, 0, men, 0, this.woodBeamBlock, this.woodBeamMeta);
      }

      this.setBlockAndMetadata(world, -1, 3, 0, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 1, 3, 0, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 3, -1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 3, 1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -4, 3, 0, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 4, 3, 0, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 0, 3, -4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 0, 3, 4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 5, -4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 6, -4, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 0, 5, 4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 6, 4, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, -2, 1, -4, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -3, 1, -4, this.bedBlock, 11);
      this.setBlockAndMetadata(world, 2, 1, -4, this.bedBlock, 1);
      this.setBlockAndMetadata(world, 3, 1, -4, this.bedBlock, 9);
      this.setBlockAndMetadata(world, -4, 1, -2, this.bedBlock, 2);
      this.setBlockAndMetadata(world, -4, 1, -3, this.bedBlock, 10);
      this.setBlockAndMetadata(world, 4, 1, -2, this.bedBlock, 2);
      this.setBlockAndMetadata(world, 4, 1, -3, this.bedBlock, 10);
      this.setBlockAndMetadata(world, -4, 1, 0, Blocks.field_150460_al, 4);
      this.setBlockAndMetadata(world, -4, 1, 1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
      this.placePlateWithCertainty(world, random, -4, 2, 1, this.plateBlock, LOTRFoods.GONDOR);
      this.setBlockAndMetadata(world, -4, 1, 2, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, -4, 1, 3, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
      this.placeMug(world, random, -4, 2, 3, 3, LOTRFoods.GONDOR_DRINK);
      this.setBlockAndMetadata(world, -3, 1, 4, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
      this.placeFlowerPot(world, -3, 2, 4, this.getRandomFlower(world, random));
      this.setBlockAndMetadata(world, -2, 1, 4, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 4, 1, 0, this.tableBlock, 0);
      this.placeChest(world, random, 4, 1, 1, 5, this.chestContents);
      this.placeChest(world, random, 4, 1, 2, 5, this.chestContents);
      this.setBlockAndMetadata(world, 4, 1, 3, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 3, 1, 4, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
      this.placeFlowerPot(world, 3, 2, 4, this.getRandomFlower(world, random));
      this.setBlockAndMetadata(world, 2, 1, 4, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -5, 1, -6, LOTRMod.reedBars, 0);

      for(men = -5; men <= -3; ++men) {
         this.setBlockAndMetadata(world, men, 1, -7, LOTRMod.reedBars, 0);
      }

      this.placeFlowerPot(world, -4, 1, -6, this.getRandomFlower(world, random));
      this.placeFlowerPot(world, -3, 1, -6, this.getRandomFlower(world, random));
      this.placeFlowerPot(world, 2, 1, -6, this.getRandomFlower(world, random));
      this.setBlockAndMetadata(world, 3, 1, -6, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 4, 1, -6, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 5, 1, -6, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 4, 2, -6, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 4, 1, -7, Blocks.field_150407_cf, 0);

      for(men = 1; men <= 2; ++men) {
         for(l = 6; l <= 9; ++l) {
            this.setBlockAndMetadata(world, -4, men, l, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, 4, men, l, LOTRMod.reedBars, 0);
         }

         this.setBlockAndMetadata(world, -3, men, 9, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, -2, men, 9, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, 2, men, 9, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, 3, men, 9, LOTRMod.reedBars, 0);

         for(l = -2; l <= 2; ++l) {
            this.setBlockAndMetadata(world, l, men, 10, LOTRMod.reedBars, 0);
         }
      }

      var13 = new int[]{-2, 1};
      l = var13.length;

      for(i2 = 0; i2 < l; ++i2) {
         i1 = var13[i2];

         for(i2 = i1; i2 <= i1 + 1; ++i2) {
            for(k1 = 7; k1 <= 8; ++k1) {
               this.setBlockAndMetadata(world, i2, 0, k1, Blocks.field_150458_ak, 7);
               this.setBlockAndMetadata(world, i2, 1, k1, this.cropBlock, this.cropMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, -1, 9, Blocks.field_150346_d, 0);
      this.setGrassToDirt(world, 0, -2, 9);
      this.setBlockAndMetadata(world, 0, 0, 9, Blocks.field_150355_j, 0);
      this.setBlockAndMetadata(world, 0, 1, 9, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 2, 9, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 3, 9, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 0, 4, 9, Blocks.field_150423_aK, 0);
      men = 1 + random.nextInt(2);

      for(l = 0; l < men; ++l) {
         LOTREntityGondorMan gondorMan = new LOTREntityGondorMan(world);
         this.spawnNPCAndSetHome(gondorMan, world, 0, 1, -1, 16);
      }

      return true;
   }
}
