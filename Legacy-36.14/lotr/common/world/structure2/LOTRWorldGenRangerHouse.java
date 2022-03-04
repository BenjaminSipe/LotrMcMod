package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDunedain;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRangerHouse extends LOTRWorldGenRangerStructure {
   public LOTRWorldGenRangerHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int gateX;
      int gateZ;
      int k2;
      int i1;
      int k1;
      if (this.restrictions) {
         gateX = 0;
         gateZ = 0;

         for(k2 = -6; k2 <= 7; ++k2) {
            for(i1 = -4; i1 <= 4; ++i1) {
               k1 = this.getTopBlock(world, k2, i1) - 1;
               if (!this.isSurface(world, k2, k1, i1)) {
                  return false;
               }

               if (k1 < gateX) {
                  gateX = k1;
               }

               if (k1 > gateZ) {
                  gateZ = k1;
               }

               if (gateZ - gateX > 5) {
                  return false;
               }
            }
         }
      }

      int k2;
      for(gateX = -2; gateX <= 7; ++gateX) {
         for(gateZ = -3; gateZ <= 3; ++gateZ) {
            k2 = Math.abs(gateZ);

            for(i1 = 1; i1 <= 8; ++i1) {
               this.setAir(world, gateX, i1, gateZ);
            }

            if (gateX >= 5 && k2 <= 1) {
               for(i1 = 5; (i1 >= 0 || !this.isOpaque(world, gateX, i1, gateZ)) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, gateX, i1, gateZ, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, gateX, i1 - 1, gateZ);
               }
            } else if (gateX == 6 && k2 == 2) {
               for(i1 = 4; (i1 >= 0 || !this.isOpaque(world, gateX, i1, gateZ)) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, gateX, i1, gateZ, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, gateX, i1 - 1, gateZ);
               }
            } else if (gateX <= 5) {
               boolean beam = false;
               boolean wall = false;
               if (gateX == -2 && IntMath.mod(gateZ, 3) == 0) {
                  beam = true;
               }

               if (k2 == 3 && (gateX == 2 || gateX == 5)) {
                  beam = true;
               }

               if (gateX == -2 || k2 == 3) {
                  wall = true;
               }

               if (beam) {
                  for(k2 = 4; (k2 >= 0 || !this.isOpaque(world, gateX, k2, gateZ)) && this.getY(k2) >= 0; --k2) {
                     this.setBlockAndMetadata(world, gateX, k2, gateZ, this.woodBeamBlock, this.woodBeamMeta);
                     this.setGrassToDirt(world, gateX, k2 - 1, gateZ);
                  }
               } else if (wall) {
                  for(k2 = 1; k2 <= 4; ++k2) {
                     this.setBlockAndMetadata(world, gateX, k2, gateZ, this.wallBlock, this.wallMeta);
                  }

                  for(k2 = 0; (k2 >= 0 || !this.isOpaque(world, gateX, k2, gateZ)) && this.getY(k2) >= 0; --k2) {
                     this.setBlockAndMetadata(world, gateX, k2, gateZ, this.plankBlock, this.plankMeta);
                     this.setGrassToDirt(world, gateX, k2 - 1, gateZ);
                  }
               } else {
                  for(k2 = 0; (k2 >= 0 || !this.isOpaque(world, gateX, k2, gateZ)) && this.getY(k2) >= 0; --k2) {
                     this.setBlockAndMetadata(world, gateX, k2, gateZ, this.plankBlock, this.plankMeta);
                     this.setGrassToDirt(world, gateX, k2 - 1, gateZ);
                  }

                  if (random.nextInt(3) == 0) {
                     this.setBlockAndMetadata(world, gateX, 1, gateZ, LOTRMod.thatchFloor, 0);
                  }
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 0, -3, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 1, -3, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -3, this.doorBlock, 8);
      this.setBlockAndMetadata(world, -2, 2, -1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -2, 2, 1, this.fenceBlock, this.fenceMeta);
      int[] var14 = new int[]{-3, 3};
      gateZ = var14.length;

      for(k2 = 0; k2 < gateZ; ++k2) {
         i1 = var14[k2];
         if (i1 >= 0) {
            this.setBlockAndMetadata(world, 0, 2, i1, this.fenceBlock, this.fenceMeta);
         }

         this.setBlockAndMetadata(world, 3, 2, i1, this.fenceBlock, this.fenceMeta);
      }

      for(gateX = -2; gateX <= 5; ++gateX) {
         this.setBlockAndMetadata(world, gateX, 4, -3, this.woodBeamBlock, this.woodBeamMeta | 4);
         this.setBlockAndMetadata(world, gateX, 4, 3, this.woodBeamBlock, this.woodBeamMeta | 4);
         if (gateX <= 4) {
            this.setBlockAndMetadata(world, gateX, 4, 0, this.woodBeamBlock, this.woodBeamMeta | 4);
         }
      }

      for(gateX = -2; gateX <= 5; ++gateX) {
         this.setBlockAndMetadata(world, gateX, 4, -4, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, gateX, 4, 4, this.roofStairBlock, 3);
         this.setBlockAndMetadata(world, gateX, 5, -3, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, gateX, 5, 3, this.roofStairBlock, 3);
      }

      for(gateX = -3; gateX <= 3; ++gateX) {
         gateZ = Math.abs(gateX);
         this.setBlockAndMetadata(world, -3, 4, gateX, this.roofStairBlock, 1);
         if (gateZ <= 2) {
            this.setBlockAndMetadata(world, -2, 5, gateX, this.roofStairBlock, 1);
         }

         if (gateZ >= 2) {
            this.setBlockAndMetadata(world, 6, 4, gateX, this.roofStairBlock, 0);
         }

         if (gateZ == 2) {
            this.setBlockAndMetadata(world, 5, 5, gateX, this.roofStairBlock, 0);
         }
      }

      for(gateX = -1; gateX <= 4; ++gateX) {
         for(gateZ = -2; gateZ <= 2; ++gateZ) {
            k2 = Math.abs(gateZ);
            if (k2 <= 1 && gateX >= 0 && gateX <= 3) {
               this.setBlockAndMetadata(world, gateX, 6, gateZ, this.roofBlock, this.roofMeta);
            } else {
               this.setBlockAndMetadata(world, gateX, 6, gateZ, this.roofSlabBlock, this.roofSlabMeta);
            }
         }
      }

      var14 = new int[]{-2, 2};
      gateZ = var14.length;

      for(k2 = 0; k2 < gateZ; ++k2) {
         i1 = var14[k2];

         for(k1 = -1; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, k1, 5, i1, this.roofBlock, this.roofMeta);
         }
      }

      var14 = new int[]{-1, 4};
      gateZ = var14.length;

      for(k2 = 0; k2 < gateZ; ++k2) {
         i1 = var14[k2];

         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, i1, 5, k1, this.fenceBlock, this.fenceMeta);
         }
      }

      this.setBlockAndMetadata(world, 6, 5, -1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 7, 5, -1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 7, 5, 0, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 7, 5, 1, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 6, 5, 1, this.brickStairBlock, 3);

      for(gateX = 6; gateX <= 7; ++gateX) {
         this.setBlockAndMetadata(world, 6, gateX, 0, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, 6, 8, 0, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 2, 2, -4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 2, 4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 0, 3, -2, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -1, 1, -1, this.tableBlock, 0);
      this.setBlockAndMetadata(world, -1, 1, 0, this.plankBlock, this.plankMeta);
      this.placePlateWithCertainty(world, random, -1, 2, 0, this.plateBlock, LOTRFoods.RANGER);
      this.setBlockAndMetadata(world, -1, 1, 1, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -1, 1, 2, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, -1, 2, 2, random.nextInt(3), LOTRFoods.RANGER_DRINK);
      this.placeChest(world, random, 0, 1, 2, 2, this.chestContentsHouse);
      this.setBlockAndMetadata(world, 1, 1, 2, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, 1, 2, 2, 2, LOTRFoods.RANGER_DRINK);
      var14 = new int[]{-2, 2};
      gateZ = var14.length;

      for(k2 = 0; k2 < gateZ; ++k2) {
         i1 = var14[k2];
         this.setBlockAndMetadata(world, 3, 1, i1, this.bedBlock, 1);
         this.setBlockAndMetadata(world, 4, 1, i1, this.bedBlock, 9);
         this.setBlockAndMetadata(world, 5, 1, i1, this.plankBlock, this.plankMeta);

         for(k1 = 2; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, 5, k1, i1, this.fenceBlock, this.fenceMeta);
         }
      }

      this.setBlockAndMetadata(world, 6, 0, 0, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 6, 1, 0, Blocks.field_150480_ab, 0);

      for(gateX = 2; gateX <= 3; ++gateX) {
         this.setAir(world, 6, gateX, 0);
      }

      this.setBlockAndMetadata(world, 5, 1, 0, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 5, 2, 0, Blocks.field_150460_al, 5);
      this.spawnItemFrame(world, 5, 3, 0, 3, this.getRangerFramedItem(random));
      gateX = 0;
      gateZ = 0;
      int gateMeta = -1;
      i1 = -5;

      label277:
      while(true) {
         byte i1;
         if (i1 > -5) {
            i1 = -2;

            while(true) {
               if (i1 > 2) {
                  break label277;
               }

               i1 = -7;
               if (this.isValidGatePos(world, i1, 1, i1)) {
                  gateX = i1 + 1;
                  gateZ = i1;
                  gateMeta = 3;
                  break label277;
               }

               ++i1;
            }
         }

         i1 = -4;
         if (this.isValidGatePos(world, i1, 1, i1)) {
            gateX = i1;
            gateZ = i1 + 1;
            gateMeta = 0;
            break;
         }

         int k1 = 4;
         if (this.isValidGatePos(world, i1, 1, k1)) {
            gateX = i1;
            gateZ = k1 - 1;
            gateMeta = 2;
            break;
         }

         ++i1;
      }

      if (gateMeta != -1) {
         for(i1 = -6; i1 <= -3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
               k2 = Math.abs(k1);

               int j1;
               for(j1 = 1; j1 <= 3; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }

               for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                  if (j1 == 0) {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
                  } else {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 0);
                  }

                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               if (i1 == -6 || k2 == 3) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
               }
            }
         }

         this.setBlockAndMetadata(world, gateX, 1, gateZ, this.fenceGateBlock, gateMeta);
         this.setBlockAndMetadata(world, gateX, 0, gateZ, LOTRMod.dirtPath, 0);

         for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, -5, 0, i1, LOTRMod.dirtPath, 0);

            for(k1 = -4; k1 <= -3; ++k1) {
               if (i1 != 0 || k1 != -3) {
                  this.setBlockAndMetadata(world, k1, 0, i1, Blocks.field_150458_ak, 7);
                  this.setBlockAndMetadata(world, k1, 1, i1, this.cropBlock, this.cropMeta);
               }
            }
         }

         this.setBlockAndMetadata(world, -3, -1, 0, Blocks.field_150346_d, 0);
         this.setGrassToDirt(world, -3, -2, 0);
         this.setBlockAndMetadata(world, -3, 0, 0, Blocks.field_150355_j, 0);
         this.setBlockAndMetadata(world, -3, 1, 0, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, -3, 2, 0, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, -6, 2, 0, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, -6, 3, 0, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, -6, 4, 0, Blocks.field_150423_aK, 3);
      }

      i1 = 1 + random.nextInt(2);

      for(k1 = 0; k1 < i1; ++k1) {
         LOTREntityDunedain dunedain = new LOTREntityDunedain(world);
         this.spawnNPCAndSetHome(dunedain, world, 2, 1, 0, 16);
      }

      return true;
   }

   private boolean isValidGatePos(World world, int i, int j, int k) {
      return this.isOpaque(world, i, j - 1, k) && this.isAir(world, i, j, k) && this.isAir(world, i, j + 1, k);
   }
}
