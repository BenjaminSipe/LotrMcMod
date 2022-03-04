package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanGatehouse extends LOTRWorldGenRohanStructure {
   public LOTRWorldGenRohanGatehouse(boolean flag) {
      super(flag);
   }

   protected boolean oneWoodType() {
      return true;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 2);
      this.setupRandomBlocks(random);
      int k1;
      int k1;
      int step;
      if (this.restrictions) {
         for(k1 = -9; k1 <= 9; ++k1) {
            for(k1 = -2; k1 <= 2; ++k1) {
               step = this.getTopBlock(world, k1, k1) - 1;
               if (!this.isSurface(world, k1, step, k1)) {
                  return false;
               }
            }
         }
      }

      int i1;
      int j1;
      for(k1 = -9; k1 <= 9; ++k1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            step = Math.abs(k1);
            i1 = Math.abs(k1);
            if (step >= 3 || i1 <= 1) {
               for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, k1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, k1, j1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                  this.setGrassToDirt(world, k1, j1 - 1, k1);
               }

               for(j1 = 1; j1 <= 12; ++j1) {
                  this.setAir(world, k1, j1, k1);
               }
            }

            if (step == 2 && i1 == 0) {
               for(j1 = 1; j1 <= 4; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
               }
            }

            if (step == 2 && i1 == 1) {
               for(j1 = 1; j1 <= 3; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, k1, this.fenceBlock, this.fenceMeta);
               }

               this.setBlockAndMetadata(world, k1, 4, k1, this.plankBlock, this.plankMeta);
            }

            if (step == 1 && i1 == 1) {
               this.setBlockAndMetadata(world, k1, 4, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            }

            if (step >= 3 && step <= 9 && i1 <= 2) {
               this.setBlockAndMetadata(world, k1, 1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);

               for(j1 = 2; j1 <= 4; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, k1, this.brickBlock, this.brickMeta);
               }
            }

            if ((step == 4 || step == 8) && i1 == 2) {
               for(j1 = 2; j1 <= 8; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
               }

               this.setBlockAndMetadata(world, k1, 9, k1, this.plankBlock, this.plankMeta);
            }

            if (step >= 5 && step <= 7 && i1 == 2 || step == 4 | step == 8 && i1 <= 1) {
               this.setBlockAndMetadata(world, k1, 9, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            }

            if (step == 9 && i1 <= 1) {
               for(j1 = 2; j1 <= 5; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
               }

               this.setBlockAndMetadata(world, k1, 6, k1, this.fenceBlock, this.fenceMeta);
            }

            if (step >= 5 && step <= 7 && i1 == 2) {
               this.setBlockAndMetadata(world, k1, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
               this.setBlockAndMetadata(world, k1, 5, k1, this.woodBeamBlock, this.woodBeamMeta);
               if (k1 < 0) {
                  this.setBlockAndMetadata(world, k1, 6, k1, this.fenceBlock, this.fenceMeta);
               }
            }

            if (step == 3 && i1 == 2) {
               this.setBlockAndMetadata(world, k1, 2, k1, this.brickWallBlock, this.brickWallMeta);
               this.setBlockAndMetadata(world, k1, 3, k1, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, k1, 4, k1, this.brickWallBlock, this.brickWallMeta);
            }

            if (step == 9 && i1 == 2) {
               this.setBlockAndMetadata(world, k1, 2, k1, this.brickWallBlock, this.brickWallMeta);

               for(j1 = 3; j1 <= 6; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, k1, this.fenceBlock, this.fenceMeta);
               }
            }

            if (step <= 8 && i1 <= 1) {
               this.setBlockAndMetadata(world, k1, 5, k1, this.plankBlock, this.plankMeta);
            }

            if (step <= 3 && i1 == 2) {
               if (step == 3) {
                  this.setBlockAndMetadata(world, k1, 5, k1, this.plankBlock, this.plankMeta);
               } else {
                  this.setBlockAndMetadata(world, k1, 5, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
               }

               this.setBlockAndMetadata(world, k1, 6, k1, this.fenceBlock, this.fenceMeta);
            }
         }
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         for(k1 = 1; k1 <= 5; ++k1) {
            this.setBlockAndMetadata(world, k1, k1, 0, this.gateBlock, 2);
         }
      }

      this.setBlockAndMetadata(world, 0, 6, 0, this.fenceBlock, this.fenceMeta);
      int[] var14 = new int[]{-1, 1};
      k1 = var14.length;

      for(step = 0; step < k1; ++step) {
         i1 = var14[step];
         this.setBlockAndMetadata(world, i1, 6, 0, this.woodBeamBlock, this.woodBeamMeta);
         this.setBlockAndMetadata(world, i1, 7, 0, Blocks.field_150442_at, 5);
      }

      var14 = new int[]{-3, 3};
      k1 = var14.length;

      for(step = 0; step < k1; ++step) {
         i1 = var14[step];
         this.placeWallBanner(world, i1, 5, -2, this.bannerType, 2);
      }

      var14 = new int[]{-2, 2};
      k1 = var14.length;

      for(step = 0; step < k1; ++step) {
         i1 = var14[step];
         this.setBlockAndMetadata(world, i1, 4, -2, Blocks.field_150478_aa, 4);
         this.setBlockAndMetadata(world, i1, 4, 2, Blocks.field_150478_aa, 3);
      }

      var14 = new int[]{-2, 2};
      k1 = var14.length;

      for(step = 0; step < k1; ++step) {
         i1 = var14[step];
         this.setBlockAndMetadata(world, -9, 8, i1, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, -3, 8, i1, Blocks.field_150478_aa, 2);
         this.setBlockAndMetadata(world, 3, 8, i1, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, 9, 8, i1, Blocks.field_150478_aa, 2);
      }

      var14 = new int[]{-6, 6};
      k1 = var14.length;

      int j2;
      for(step = 0; step < k1; ++step) {
         i1 = var14[step];

         for(j1 = -3; j1 <= 3; ++j1) {
            j2 = Math.abs(j1);
            if (j2 == 3) {
               this.setBlockAndMetadata(world, i1 - 1, 10, j1, this.roofSlabBlock, this.roofSlabMeta);
               this.setBlockAndMetadata(world, i1, 10, j1, this.roofSlabBlock, this.roofSlabMeta);
               this.setBlockAndMetadata(world, i1 + 1, 10, j1, this.roofSlabBlock, this.roofSlabMeta);
            }

            if (j2 == 2) {
               this.setBlockAndMetadata(world, i1 - 2, 10, j1, this.roofSlabBlock, this.roofSlabMeta);
               this.setBlockAndMetadata(world, i1 - 1, 10, j1, this.roofBlock, this.roofMeta);
               this.setBlockAndMetadata(world, i1, 10, j1, this.roofBlock, this.roofMeta);
               this.setBlockAndMetadata(world, i1 + 1, 10, j1, this.roofBlock, this.roofMeta);
               this.setBlockAndMetadata(world, i1 + 2, 10, j1, this.roofSlabBlock, this.roofSlabMeta);
            }

            if (j2 <= 1) {
               this.setBlockAndMetadata(world, i1 - 3, 10, j1, this.roofSlabBlock, this.roofSlabMeta);
               this.setBlockAndMetadata(world, i1 - 2, 10, j1, this.roofBlock, this.roofMeta);
               this.setBlockAndMetadata(world, i1 - 1, 10, j1, this.roofSlabBlock, this.roofSlabMeta | 8);
               this.setBlockAndMetadata(world, i1 - 1, 11, j1, this.roofSlabBlock, this.roofSlabMeta);
               this.setBlockAndMetadata(world, i1, 10, j1, this.roofSlabBlock, this.roofSlabMeta | 8);
               this.setBlockAndMetadata(world, i1, 11, j1, this.roofSlabBlock, this.roofSlabMeta);
               this.setBlockAndMetadata(world, i1 + 1, 10, j1, this.roofSlabBlock, this.roofSlabMeta | 8);
               this.setBlockAndMetadata(world, i1 + 1, 11, j1, this.roofSlabBlock, this.roofSlabMeta);
               this.setBlockAndMetadata(world, i1 + 2, 10, j1, this.roofBlock, this.roofMeta);
               this.setBlockAndMetadata(world, i1 + 3, 10, j1, this.roofSlabBlock, this.roofSlabMeta);
            }
         }
      }

      var14 = new int[]{-3, 3};
      k1 = var14.length;

      for(step = 0; step < k1; ++step) {
         i1 = var14[step];
         int j1 = 6;
         int k1 = 0;
         LOTREntityRohanMan guard = new LOTREntityRohirrimWarrior(world);
         this.spawnNPCAndSetHome(guard, world, i1, j1, k1, 8);
      }

      for(k1 = 3; k1 <= 4; ++k1) {
         int maxStep = 16;

         for(step = 0; step < maxStep; ++step) {
            i1 = -6 - step;
            j1 = 5 - (step <= 1 ? 0 : step - 2);
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            if (step <= 1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.plankBlock, this.plankMeta);
            } else {
               this.setBlockAndMetadata(world, i1, j1, k1, this.plankStairBlock, 1);
            }

            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.plankBlock, this.plankMeta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }

         for(step = 0; step < maxStep; ++step) {
            i1 = 6 + step;
            j1 = 5 - (step <= 1 ? 0 : step - 2);
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            if (step <= 1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.plankBlock, this.plankMeta);
            } else {
               this.setBlockAndMetadata(world, i1, j1, k1, this.plankStairBlock, 0);
            }

            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.plankBlock, this.plankMeta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      return true;
   }
}
