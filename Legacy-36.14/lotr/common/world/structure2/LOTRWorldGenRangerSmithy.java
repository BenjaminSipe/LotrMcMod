package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDunedainBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRangerSmithy extends LOTRWorldGenRangerStructure {
   public LOTRWorldGenRangerSmithy(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      this.setupRandomBlocks(random);
      int j1;
      int j1;
      int k1;
      int i1;
      int j1;
      if (this.restrictions) {
         j1 = 0;
         j1 = 0;

         for(k1 = -7; k1 <= 9; ++k1) {
            for(i1 = -5; i1 <= 5; ++i1) {
               j1 = this.getTopBlock(world, k1, i1) - 1;
               if (!this.isSurface(world, k1, j1, i1)) {
                  return false;
               }

               if (j1 < j1) {
                  j1 = j1;
               }

               if (j1 > j1) {
                  j1 = j1;
               }

               if (j1 - j1 > 6) {
                  return false;
               }
            }
         }
      }

      for(j1 = -7; j1 <= 8; ++j1) {
         for(j1 = -4; j1 <= 4; ++j1) {
            k1 = Math.abs(j1);

            for(i1 = 1; i1 <= 8; ++i1) {
               this.setAir(world, j1, i1, j1);
            }

            if (j1 <= 1 || j1 == 2 && k1 == 4) {
               for(i1 = 0; (i1 >= 0 || !this.isOpaque(world, j1, i1, j1)) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, j1, i1, j1, this.cobbleBlock, this.cobbleMeta);
                  this.setGrassToDirt(world, j1, i1 - 1, j1);
               }
            } else if ((j1 == 2 || j1 == 8) && k1 <= 3 || j1 >= 3 && j1 <= 7 && k1 <= 4) {
               if ((j1 != 2 && j1 != 8 || k1 > 3) && (j1 < 3 || j1 > 7 || k1 != 4)) {
                  for(i1 = 0; (i1 >= 0 || !this.isOpaque(world, j1, i1, j1)) && this.getY(i1) >= 0; --i1) {
                     this.setBlockAndMetadata(world, j1, i1, j1, this.plankBlock, this.plankMeta);
                     this.setGrassToDirt(world, j1, i1 - 1, j1);
                  }

                  if (random.nextInt(3) == 0) {
                     this.setBlockAndMetadata(world, j1, 1, j1, LOTRMod.thatchFloor, 0);
                  }
               } else {
                  boolean beam = false;
                  if ((j1 == 2 || j1 == 8) && k1 == 3) {
                     beam = true;
                  }

                  if ((j1 == 3 || j1 == 7) && k1 == 4) {
                     beam = true;
                  }

                  if (beam) {
                     for(j1 = 4; (j1 >= 0 || !this.isOpaque(world, j1, j1, j1)) && this.getY(j1) >= 0; --j1) {
                        this.setBlockAndMetadata(world, j1, j1, j1, this.woodBeamBlock, this.woodBeamMeta);
                        this.setGrassToDirt(world, j1, j1 - 1, j1);
                     }
                  } else {
                     for(j1 = 1; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, j1, j1, j1, this.wallBlock, this.wallMeta);
                     }

                     for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, j1, j1, j1)) && this.getY(j1) >= 0; --j1) {
                        this.setBlockAndMetadata(world, j1, j1, j1, this.plankBlock, this.plankMeta);
                        this.setGrassToDirt(world, j1, j1 - 1, j1);
                     }
                  }
               }
            }
         }
      }

      for(j1 = 4; j1 <= 6; ++j1) {
         this.setBlockAndMetadata(world, j1, 4, -4, this.woodBeamBlock, this.woodBeamMeta | 4);
         this.setBlockAndMetadata(world, j1, 4, 4, this.woodBeamBlock, this.woodBeamMeta | 4);
      }

      for(j1 = -2; j1 <= 2; ++j1) {
         this.setBlockAndMetadata(world, 2, 4, j1, this.woodBeamBlock, this.woodBeamMeta | 8);
         this.setBlockAndMetadata(world, 8, 4, j1, this.woodBeamBlock, this.woodBeamMeta | 8);
      }

      this.setBlockAndMetadata(world, 5, 2, -4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 5, 2, 4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 2, -2, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 2, 2, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 1, 0, this.doorBlock, 2);
      this.setBlockAndMetadata(world, 2, 2, 0, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 3, 3, 0, Blocks.field_150478_aa, 2);

      int k1;
      for(j1 = 0; j1 <= 2; ++j1) {
         j1 = 4 + j1;

         for(k1 = 2 + j1; k1 <= 8 - j1; ++k1) {
            this.setBlockAndMetadata(world, k1, j1, -5 + j1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, k1, j1, 5 - j1, this.roofStairBlock, 3);
         }

         int[] var16 = new int[]{1 + j1, 9 - j1};
         i1 = var16.length;

         for(j1 = 0; j1 < i1; ++j1) {
            k1 = var16[j1];
            this.setBlockAndMetadata(world, k1, j1, -4 + j1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, k1, j1, 4 - j1, this.roofStairBlock, 3);
         }

         for(k1 = -3 + j1; k1 <= 3 - j1; ++k1) {
            this.setBlockAndMetadata(world, 1 + j1, j1, k1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 9 - j1, j1, k1, this.roofStairBlock, 0);
         }

         var16 = new int[]{-4 + j1, 4 - j1};
         i1 = var16.length;

         for(j1 = 0; j1 < i1; ++j1) {
            k1 = var16[j1];
            this.setBlockAndMetadata(world, 2 + j1, j1, k1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 8 - j1, j1, k1, this.roofStairBlock, 0);
         }
      }

      for(j1 = -1; j1 <= 1; ++j1) {
         this.setBlockAndMetadata(world, 5, 7, j1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 4, 7, j1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, 6, 7, j1, this.roofSlabBlock, this.roofSlabMeta);
      }

      this.setBlockAndMetadata(world, 5, 7, -2, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 5, 7, 2, this.roofSlabBlock, this.roofSlabMeta);

      for(j1 = 0; j1 <= 1; ++j1) {
         j1 = 5 + j1;

         for(k1 = 4 + j1; k1 <= 6 - j1; ++k1) {
            this.setBlockAndMetadata(world, k1, j1, -3 + j1, this.roofSlabBlock, this.roofSlabMeta | 8);
            this.setBlockAndMetadata(world, k1, j1, 3 - j1, this.roofSlabBlock, this.roofSlabMeta | 8);
         }

         for(k1 = -2 + j1; k1 <= 2 - j1; ++k1) {
            this.setBlockAndMetadata(world, 3 + j1, j1, k1, this.roofSlabBlock, this.roofSlabMeta | 8);
            this.setBlockAndMetadata(world, 7 - j1, j1, k1, this.roofSlabBlock, this.roofSlabMeta | 8);
         }
      }

      for(j1 = 7; j1 <= 9; ++j1) {
         for(j1 = -1; j1 <= 1; ++j1) {
            for(k1 = 5; (k1 >= 0 || !this.isOpaque(world, j1, k1, j1)) && this.getY(k1) >= 0; --k1) {
               this.setBlockAndMetadata(world, j1, k1, j1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, j1, k1 - 1, j1);
            }
         }
      }

      for(j1 = -1; j1 <= 1; ++j1) {
         this.setBlockAndMetadata(world, 9, 5, j1, this.brickStairBlock, 0);
      }

      this.setBlockAndMetadata(world, 8, 5, -1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 8, 5, 1, this.brickStairBlock, 3);

      for(j1 = 6; j1 <= 7; ++j1) {
         this.setBlockAndMetadata(world, 8, j1, 0, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, 8, 8, 0, this.brickWallBlock, this.brickWallMeta);

      for(j1 = -3; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, 5, 4, j1, this.woodBeamBlock, this.woodBeamMeta | 8);
      }

      int[] var18 = new int[]{3, 7};
      j1 = var18.length;

      for(k1 = 0; k1 < j1; ++k1) {
         i1 = var18[k1];
         int[] var19 = new int[]{-3, 3};
         k1 = var19.length;

         for(int var13 = 0; var13 < k1; ++var13) {
            int k1 = var19[var13];
            this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);

            for(int j1 = 2; j1 <= 4; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 3, 1, -2, this.plankBlock, this.plankMeta);
      this.placePlate(world, random, 3, 2, -2, this.plateBlock, LOTRFoods.RANGER);
      this.setBlockAndMetadata(world, 4, 1, -3, this.plankBlock, this.plankMeta);
      this.placePlate(world, random, 4, 2, -3, this.plateBlock, LOTRFoods.RANGER);
      this.setBlockAndMetadata(world, 5, 1, -3, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 6, 1, -3, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, 6, 2, -3, 2, LOTRFoods.RANGER_DRINK);
      this.setBlockAndMetadata(world, 7, 1, -2, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, 7, 2, -2, 5, LOTRFoods.RANGER_DRINK);
      this.setBlockAndMetadata(world, 3, 1, 2, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, 3, 2, 2, 3, LOTRFoods.RANGER_DRINK);
      this.setBlockAndMetadata(world, 5, 1, 3, this.bedBlock, 1);
      this.setBlockAndMetadata(world, 6, 1, 3, this.bedBlock, 9);
      this.placeChest(world, random, 7, 1, 2, 5, this.chestContentsHouse);
      this.setBlockAndMetadata(world, 8, 0, 0, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 8, 1, 0, Blocks.field_150480_ab, 0);

      for(j1 = 2; j1 <= 3; ++j1) {
         this.setAir(world, 8, j1, 0);
      }

      this.setBlockAndMetadata(world, 7, 1, 0, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 7, 2, 0, Blocks.field_150460_al, 5);
      this.spawnItemFrame(world, 7, 3, 0, 3, this.getRangerFramedItem(random));
      this.placeChest(world, random, 1, 1, 2, 5, LOTRChestContents.RANGER_SMITHY);
      this.setBlockAndMetadata(world, 1, 1, -2, this.tableBlock, 0);
      this.setBlockAndMetadata(world, 1, 1, -3, Blocks.field_150462_ai, 0);

      for(j1 = 1; j1 <= 3; ++j1) {
         for(j1 = -6; j1 <= -3; ++j1) {
            for(k1 = 0; k1 <= 3; ++k1) {
               this.setBlockAndMetadata(world, j1, j1, k1, this.brickBlock, this.brickMeta);
            }
         }

         this.setBlockAndMetadata(world, -2, j1, 3, this.brickBlock, this.brickMeta);
      }

      for(j1 = 1; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, -6, j1, -3, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, -2, j1, -3, this.fenceBlock, this.fenceMeta);
      }

      for(j1 = 0; j1 <= 1; ++j1) {
         j1 = 4 + j1;

         for(k1 = -6 + j1; k1 <= -2 - j1; ++k1) {
            this.setBlockAndMetadata(world, k1, j1, -3 + j1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, k1, j1, 3 - j1, this.brickStairBlock, 3);
         }

         for(k1 = -2 + j1; k1 <= 2 - j1; ++k1) {
            this.setBlockAndMetadata(world, -6 + j1, j1, k1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -2 - j1, j1, k1, this.brickStairBlock, 0);
         }
      }

      for(j1 = -2; j1 <= 2; ++j1) {
         for(j1 = -5; j1 <= -3; ++j1) {
            this.setBlockAndMetadata(world, j1, 4, j1, this.brickBlock, this.brickMeta);
         }
      }

      for(j1 = -1; j1 <= 1; ++j1) {
         this.setBlockAndMetadata(world, -4, 5, j1, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, -4, 1, 0, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, -3, 1, 0, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -3, 1, 1, LOTRMod.alloyForge, 4);
      this.setBlockAndMetadata(world, -4, 2, 0, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -3, 2, 0, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -3, 2, 1, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -4, 1, 1, Blocks.field_150353_l, 0);

      for(j1 = 2; j1 <= 5; ++j1) {
         this.setAir(world, -4, j1, 1);
      }

      this.setBlockAndMetadata(world, -2, 1, 2, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, -5, 1, -1, LOTRMod.unsmeltery, 4);
      this.setBlockAndMetadata(world, -5, 1, -3, Blocks.field_150467_bQ, 1);
      this.setBlockAndMetadata(world, -3, 1, -3, Blocks.field_150467_bQ, 1);
      LOTREntityDunedainBlacksmith blacksmith = new LOTREntityDunedainBlacksmith(world);
      this.spawnNPCAndSetHome(blacksmith, world, 0, 1, 0, 8);
      return true;
   }
}
