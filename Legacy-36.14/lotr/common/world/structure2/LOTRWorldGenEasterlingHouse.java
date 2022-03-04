package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingHouse extends LOTRWorldGenEasterlingStructure {
   public LOTRWorldGenEasterlingHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      this.setupRandomBlocks(random);
      int men;
      int l;
      int j1;
      int i1;
      int k1;
      if (this.restrictions) {
         men = 0;
         l = 0;

         for(j1 = -7; j1 <= 7; ++j1) {
            for(i1 = -5; i1 <= 5; ++i1) {
               k1 = this.getTopBlock(world, j1, i1) - 1;
               if (!this.isSurface(world, j1, k1, i1)) {
                  return false;
               }

               if (k1 < men) {
                  men = k1;
               }

               if (k1 > l) {
                  l = k1;
               }

               if (l - men > 6) {
                  return false;
               }
            }
         }
      }

      for(men = -6; men <= 6; ++men) {
         for(l = -4; l <= 4; ++l) {
            j1 = Math.abs(men);
            i1 = Math.abs(l);
            if ((j1 != 2 && j1 != 6 || i1 != 4) && (i1 != 0 && i1 != 4 || j1 != 6)) {
               if (j1 != 6 && i1 != 4) {
                  for(k1 = 0; (k1 >= 0 || !this.isOpaque(world, men, k1, l)) && this.getY(k1) >= 0; --k1) {
                     this.setBlockAndMetadata(world, men, k1, l, this.plankBlock, this.plankMeta);
                     this.setGrassToDirt(world, men, k1 - 1, l);
                  }

                  for(k1 = 1; k1 <= 6; ++k1) {
                     this.setAir(world, men, k1, l);
                  }

                  if (random.nextInt(3) == 0) {
                     this.setBlockAndMetadata(world, men, 1, l, LOTRMod.thatchFloor, 0);
                  }
               } else {
                  for(k1 = 1; (k1 >= 0 || !this.isOpaque(world, men, k1, l)) && this.getY(k1) >= 0; --k1) {
                     this.setBlockAndMetadata(world, men, k1, l, this.brickBlock, this.brickMeta);
                     this.setGrassToDirt(world, men, k1 - 1, l);
                  }

                  for(k1 = 2; k1 <= 3; ++k1) {
                     this.setBlockAndMetadata(world, men, k1, l, this.plankBlock, this.plankMeta);
                  }

                  if (i1 == 4) {
                     this.setBlockAndMetadata(world, men, 4, l, this.woodBeamBlock, this.woodBeamMeta | 4);
                     this.setBlockAndMetadata(world, men, 5, l, this.woodBeamBlock, this.woodBeamMeta | 4);
                  } else if (j1 == 6) {
                     this.setBlockAndMetadata(world, men, 4, l, this.woodBeamBlock, this.woodBeamMeta | 8);
                     this.setBlockAndMetadata(world, men, 5, l, this.woodBeamBlock, this.woodBeamMeta | 8);
                  }
               }
            } else {
               for(k1 = 5; (k1 >= 0 || !this.isOpaque(world, men, k1, l)) && this.getY(k1) >= 0; --k1) {
                  this.setBlockAndMetadata(world, men, k1, l, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, men, k1 - 1, l);
               }
            }
         }
      }

      for(men = -7; men <= 7; ++men) {
         this.setBlockAndMetadata(world, men, 4, 0, this.woodBeamBlock, this.woodBeamMeta | 4);
      }

      this.setBlockAndMetadata(world, -7, 3, 0, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 7, 3, 0, this.fenceBlock, this.fenceMeta);
      int[] var12 = new int[]{-2, 2};
      l = var12.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var12[j1];

         for(k1 = -5; k1 <= 5; ++k1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
         }

         this.setBlockAndMetadata(world, i1, 3, -5, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 3, 5, this.fenceBlock, this.fenceMeta);
      }

      var12 = new int[]{-6, 6};
      l = var12.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var12[j1];
         this.setBlockAndMetadata(world, i1, 4, -5, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 4, 5, this.fenceBlock, this.fenceMeta);
      }

      var12 = new int[]{-4, 4};
      l = var12.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var12[j1];
         this.setBlockAndMetadata(world, -7, 4, i1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 7, 4, i1, this.fenceBlock, this.fenceMeta);
      }

      var12 = new int[]{-4, 4};
      l = var12.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var12[j1];
         this.setBlockAndMetadata(world, i1, 2, -4, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, i1, 3, -4, this.plankStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 2, 4, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, i1, 3, 4, this.plankStairBlock, 7);
      }

      var12 = new int[]{-2, 2};
      l = var12.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var12[j1];
         this.setBlockAndMetadata(world, -6, 2, i1, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, -6, 3, i1, this.plankStairBlock, 5);
         this.setBlockAndMetadata(world, 6, 2, i1, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, 6, 3, i1, this.plankStairBlock, 4);
      }

      for(men = -5; men <= 5; ++men) {
         this.setBlockAndMetadata(world, men, 5, -5, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, men, 5, 5, this.roofStairBlock, 3);
      }

      for(men = -3; men <= 3; ++men) {
         this.setBlockAndMetadata(world, -7, 5, men, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 7, 5, men, this.roofStairBlock, 0);
      }

      for(men = -6; men <= 6; ++men) {
         this.setBlockAndMetadata(world, men, 6, -4, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, men, 6, 4, this.roofStairBlock, 3);
      }

      for(men = -3; men <= 3; ++men) {
         this.setBlockAndMetadata(world, -6, 6, men, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 6, 6, men, this.roofStairBlock, 0);
      }

      for(men = -5; men <= 5; ++men) {
         for(l = -3; l <= 3; ++l) {
            this.setBlockAndMetadata(world, men, 6, l, this.roofBlock, this.roofMeta);
         }
      }

      for(men = -5; men <= 5; ++men) {
         this.setBlockAndMetadata(world, men, 7, -2, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, men, 7, 2, this.roofStairBlock, 3);
      }

      for(men = -1; men <= 1; ++men) {
         this.setBlockAndMetadata(world, -5, 7, men, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 5, 7, men, this.roofStairBlock, 0);
      }

      for(men = -4; men <= 4; ++men) {
         for(l = -1; l <= 1; ++l) {
            this.setBlockAndMetadata(world, men, 7, l, this.roofBlock, this.roofMeta);
         }
      }

      this.setBlockAndMetadata(world, -6, 5, -5, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -7, 6, -5, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -7, 5, -4, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 6, 5, -5, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 7, 6, -5, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 7, 5, -4, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -6, 5, 5, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -7, 6, 5, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -7, 5, 4, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 6, 5, 5, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 7, 6, 5, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 7, 5, 4, this.roofStairBlock, 7);

      for(men = -5; men <= 5; ++men) {
         this.setBlockAndMetadata(world, men, 5, -3, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, men, 5, 3, this.roofStairBlock, 6);
      }

      for(men = -2; men <= 2; ++men) {
         this.setBlockAndMetadata(world, -5, 5, men, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, 5, 5, men, this.roofStairBlock, 5);
      }

      this.setBlockAndMetadata(world, -1, 1, -4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 1, 1, -4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 0, -4, this.woodBeamBlock, this.woodBeamMeta | 4);
      this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);

      for(men = -2; men <= 2; ++men) {
         for(l = 1; l <= 3; ++l) {
            this.setBlockAndMetadata(world, men, 0, l, this.brickBlock, this.brickMeta);
         }
      }

      for(men = -1; men <= 1; ++men) {
         for(l = 2; l <= 4; ++l) {
            for(j1 = 1; j1 <= 6; ++j1) {
               this.setBlockAndMetadata(world, men, j1, l, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(men = -1; men <= 1; ++men) {
         this.setBlockAndMetadata(world, men, 6, 4, this.brickStairBlock, 3);
      }

      for(men = 7; men <= 8; ++men) {
         this.setBlockAndMetadata(world, 0, men, 3, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, 0, 9, 3, Blocks.field_150457_bL, 0);
      this.setBlockAndMetadata(world, 0, 0, 3, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 0, 1, 3, Blocks.field_150480_ab, 0);
      this.setAir(world, 0, 2, 3);
      this.setBlockAndMetadata(world, 0, 1, 2, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 0, 2, 2, Blocks.field_150460_al, 2);
      this.spawnItemFrame(world, 0, 3, 2, 2, this.getEasterlingFramedItem(random));
      this.setBlockAndMetadata(world, -2, 3, 0, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, 2, 3, 0, LOTRMod.chandelier, 0);

      for(men = -5; men <= 5; ++men) {
         this.setBlockAndMetadata(world, men, 0, 0, this.woodBeamBlock, this.woodBeamMeta | 4);
      }

      var12 = new int[]{-2, 2};
      l = var12.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var12[j1];

         for(k1 = -3; k1 <= -1; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
         }
      }

      for(men = -5; men <= -3; ++men) {
         this.setBlockAndMetadata(world, men, 1, -3, this.plankStairBlock, 7);
         if (random.nextBoolean()) {
            this.placePlate(world, random, men, 2, -3, this.plateBlock, LOTRFoods.RHUN);
         } else {
            this.placeMug(world, random, men, 2, -3, 2, LOTRFoods.RHUN_DRINK);
         }
      }

      this.setBlockAndMetadata(world, -4, 1, -1, this.plankStairBlock, 2);
      this.setBlockAndMetadata(world, -4, 1, 2, this.bedBlock, 0);
      this.setBlockAndMetadata(world, -4, 1, 3, this.bedBlock, 8);
      this.setBlockAndMetadata(world, -5, 1, 3, this.plankBlock, this.plankMeta);
      this.placePlateWithCertainty(world, random, -5, 2, 3, this.plateBlock, LOTRFoods.RHUN);
      this.setBlockAndMetadata(world, 5, 1, -3, this.tableBlock, 0);
      this.placeChest(world, random, 5, 1, -2, 5, this.chestContents);
      this.setBlockAndMetadata(world, 5, 1, -1, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 5, 1, 0, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 5, 2, 0, LOTRMod.ceramicPlateBlock, 0);
      this.setBlockAndMetadata(world, 5, 1, 1, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, 5, 1, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.placeMug(world, random, 5, 2, 2, 1, LOTRFoods.RHUN_DRINK);
      this.setBlockAndMetadata(world, 5, 1, 3, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, 5, 2, 3, 5, LOTRFoods.RHUN_DRINK);
      this.setBlockAndMetadata(world, -2, 2, -3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 2, -3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 2, 3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 2, 3, Blocks.field_150478_aa, 4);
      men = 1 + random.nextInt(2);

      for(l = 0; l < men; ++l) {
         LOTREntityEasterling easterling = new LOTREntityEasterling(world);
         this.spawnNPCAndSetHome(easterling, world, 0, 1, 0, 16);
      }

      return true;
   }
}
