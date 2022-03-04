package lotr.common.world.structure2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanHouse extends LOTRWorldGenRohanStructure {
   private boolean hasStoneBase;
   private boolean setHasBase = false;

   public LOTRWorldGenRohanHouse(boolean flag) {
      super(flag);
   }

   public LOTRWorldGenRohanHouse setHasBase(boolean flag) {
      this.hasStoneBase = flag;
      this.setHasBase = true;
      return this;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      if (!this.setHasBase) {
         this.hasStoneBase = random.nextBoolean();
      }

      this.setOriginAndRotation(world, i, j, k, rotation, this.hasStoneBase ? 10 : 6);
      this.setupRandomBlocks(random);
      int j1;
      int i1;
      int k1;
      int j1;
      int j2;
      if (this.restrictions) {
         j1 = 0;
         i1 = 0;

         for(k1 = -6; k1 <= 5; ++k1) {
            for(j1 = -10; j1 <= 10; ++j1) {
               j2 = this.getTopBlock(world, k1, j1) - 1;
               if (!this.isSurface(world, k1, j2, j1)) {
                  return false;
               }

               if (j2 < j1) {
                  j1 = j2;
               }

               if (j2 > i1) {
                  i1 = j2;
               }

               if (i1 - j1 > 5) {
                  return false;
               }
            }
         }
      }

      int j3;
      boolean beam;
      if (this.hasStoneBase) {
         for(j1 = -6; j1 <= 5; ++j1) {
            for(i1 = -9; i1 <= 8; ++i1) {
               beam = (j1 == -6 || j1 == 5) && Math.abs(i1) == 8;
               boolean stairSide = (j1 == -2 || j1 == 1) && i1 == -9;
               boolean stair = j1 >= -1 && j1 <= 0 && i1 == -9;
               if (!beam && !stairSide) {
                  if (stair || i1 >= -8) {
                     for(j3 = 1; (j3 >= 1 || !this.isOpaque(world, j1, j3, i1)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, j1, j3, i1, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, j1, j3 - 1, i1);
                     }

                     if (stair) {
                        this.setBlockAndMetadata(world, j1, 1, i1, this.brickStairBlock, 2);
                     }
                  }
               } else {
                  for(j3 = 1; (j3 >= 1 || !this.isOpaque(world, j1, j3, i1)) && this.getY(j3) >= 0; --j3) {
                     this.setBlockAndMetadata(world, j1, j3, i1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                     this.setGrassToDirt(world, j1, j3 - 1, i1);
                  }

                  if (beam) {
                     this.setBlockAndMetadata(world, j1, 2, i1, this.rockSlabBlock, this.rockSlabMeta);
                  }
               }
            }
         }

         ++this.originY;
      } else {
         for(j1 = -3; j1 <= 2; ++j1) {
            for(i1 = -5; i1 <= 4; ++i1) {
               if (i1 >= -4 || j1 >= -1 && j1 <= 0) {
                  for(k1 = 0; (k1 >= 0 || !this.isOpaque(world, j1, k1, i1)) && this.getY(k1) >= 0; --k1) {
                     this.setBlockAndMetadata(world, j1, k1, i1, this.plank2Block, this.plank2Meta);
                     this.setGrassToDirt(world, j1, k1 - 1, i1);
                  }
               }
            }
         }
      }

      for(j1 = -5; j1 <= 4; ++j1) {
         for(i1 = -7; i1 <= 7; ++i1) {
            for(k1 = 1; k1 <= 8; ++k1) {
               this.setAir(world, j1, k1, i1);
            }
         }
      }

      for(j1 = -3; j1 <= 2; ++j1) {
         for(i1 = -4; i1 <= 4; ++i1) {
            if (random.nextBoolean()) {
               this.setBlockAndMetadata(world, j1, 1, i1, LOTRMod.thatchFloor, 0);
            }
         }
      }

      for(j1 = -4; j1 <= 3; ++j1) {
         for(i1 = -7; i1 <= 5; ++i1) {
            beam = false;
            if (i1 == -7 && (j1 == -4 || j1 == -2 || j1 == 1 || j1 == 3)) {
               beam = true;
            } else if (Math.abs(i1) == 5 && (j1 == -4 || j1 == 3)) {
               beam = true;
            }

            if (beam) {
               for(j1 = 3; (j1 >= 1 || !this.isOpaque(world, j1, j1, i1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, j1, j1, i1, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, j1, j1 - 1, i1);
               }
            } else if (i1 >= -5) {
               if (j1 != -4 && j1 != 3) {
                  if (Math.abs(i1) == 5) {
                     this.setBlockAndMetadata(world, j1, 1, i1, this.plank2Block, this.plank2Meta);
                     this.setGrassToDirt(world, j1, 0, i1);

                     for(j1 = 2; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, j1, j1, i1, this.plankBlock, this.plankMeta);
                     }

                     this.setBlockAndMetadata(world, j1, 4, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
                  }
               } else {
                  this.setBlockAndMetadata(world, j1, 1, i1, this.plank2Block, this.plank2Meta);
                  this.setGrassToDirt(world, j1, 0, i1);

                  for(j1 = 2; j1 <= 3; ++j1) {
                     this.setBlockAndMetadata(world, j1, j1, i1, this.plankBlock, this.plankMeta);
                  }
               }
            }
         }
      }

      for(j1 = -7; j1 <= 6; ++j1) {
         boolean roofEdge = j1 == -7 || j1 == 6;

         for(k1 = 0; k1 <= 4; ++k1) {
            j1 = 3 + k1;
            Block stairBlock = this.roofStairBlock;
            if (k1 == 4 || roofEdge) {
               stairBlock = this.plank2StairBlock;
            }

            this.setBlockAndMetadata(world, -5 + k1, j1, j1, stairBlock, 1);
            this.setBlockAndMetadata(world, 4 - k1, j1, j1, stairBlock, 0);
            if (roofEdge && k1 <= 3) {
               this.setBlockAndMetadata(world, -4 + k1, j1, j1, stairBlock, 4);
               this.setBlockAndMetadata(world, 3 - k1, j1, j1, stairBlock, 5);
            }

            if (j1 >= -4 && j1 <= 4 && k1 >= 1 && k1 <= 3) {
               this.setBlockAndMetadata(world, -4 + k1, j1, j1, stairBlock, 4);
               this.setBlockAndMetadata(world, 3 - k1, j1, j1, stairBlock, 5);
            }
         }
      }

      int[] var20 = new int[]{-6, -5, 5};
      i1 = var20.length;

      for(k1 = 0; k1 < i1; ++k1) {
         j1 = var20[k1];
         this.setBlockAndMetadata(world, -2, 5, j1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -1, 5, j1, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, 0, 5, j1, this.plankStairBlock, 5);
         this.setBlockAndMetadata(world, 1, 5, j1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -1, 6, j1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 0, 6, j1, this.plankBlock, this.plankMeta);
      }

      var20 = new int[]{-7, 6};
      i1 = var20.length;

      for(k1 = 0; k1 < i1; ++k1) {
         j1 = var20[k1];
         this.setBlockAndMetadata(world, -1, 8, j1, this.plank2StairBlock, 0);
         this.setBlockAndMetadata(world, 0, 8, j1, this.plank2StairBlock, 1);
      }

      for(j1 = -4; j1 <= 3; ++j1) {
         if (j1 != -4 && j1 != -2 && j1 != 1 && j1 != 3) {
            this.setBlockAndMetadata(world, j1, 3, -7, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         } else {
            this.setBlockAndMetadata(world, j1, 3, -7, this.plank2Block, this.plank2Meta);
         }

         if (j1 >= -3 && j1 <= 2) {
            this.setBlockAndMetadata(world, j1, 3, 6, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         }
      }

      for(j1 = -3; j1 <= 2; ++j1) {
         this.setBlockAndMetadata(world, j1, 4, -6, this.plankBlock, this.plankMeta);
      }

      this.setBlockAndMetadata(world, -4, 3, -6, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      this.setBlockAndMetadata(world, 3, 3, -6, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      this.setBlockAndMetadata(world, -1, 4, -6, this.rockSlabBlock, this.rockSlabMeta);
      this.setBlockAndMetadata(world, 0, 4, -6, this.rockSlabBlock, this.rockSlabMeta);
      this.setBlockAndMetadata(world, -2, 4, -7, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 1, 4, -7, this.fenceBlock, this.fenceMeta);

      for(j1 = -1; j1 <= 0; ++j1) {
         for(i1 = 1; i1 <= 2; ++i1) {
            this.setAir(world, j1, i1, -5);
         }
      }

      this.setBlockAndMetadata(world, -1, 3, -5, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 3, -5, this.plankStairBlock, 5);
      var20 = new int[]{-5, 4};
      i1 = var20.length;

      int var13;
      int k1;
      int j1;
      int[] var22;
      for(k1 = 0; k1 < i1; ++k1) {
         j1 = var20[k1];
         var22 = new int[]{-7, 6};
         j3 = var22.length;

         for(var13 = 0; var13 < j3; ++var13) {
            k1 = var22[var13];

            for(j1 = 2; (j1 >= 1 || !this.isOpaque(world, j1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, j1, j1, k1, this.fenceBlock, this.fenceMeta);
            }
         }
      }

      var20 = new int[]{-4, 3};
      i1 = var20.length;

      for(k1 = 0; k1 < i1; ++k1) {
         j1 = var20[k1];
         this.setAir(world, j1, 2, -2);
         this.setBlockAndMetadata(world, j1, 3, -2, this.plankSlabBlock, this.plankSlabMeta | 8);
         this.setBlockAndMetadata(world, j1, 4, -2, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, j1, 2, -3, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, j1, 2, -1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, j1, 3, -3, this.plankStairBlock, 7);
         this.setBlockAndMetadata(world, j1, 3, -1, this.plankStairBlock, 6);
      }

      var20 = new int[]{-5, 4};
      i1 = var20.length;

      for(k1 = 0; k1 < i1; ++k1) {
         j1 = var20[k1];
         this.setBlockAndMetadata(world, j1, 1, -3, this.plankStairBlock, 7);
         this.setBlockAndMetadata(world, j1, 1, -2, this.plankSlabBlock, this.plankSlabMeta | 8);
         this.setBlockAndMetadata(world, j1, 1, -1, this.plankStairBlock, 6);

         for(j2 = -3; j2 <= -1; ++j2) {
            if (random.nextBoolean()) {
               this.placeFlowerPot(world, j1, 2, j2, this.getRandomFlower(world, random));
            }
         }

         this.setBlockAndMetadata(world, j1, 3, -4, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, j1, 3, -3, this.roofSlabBlock, this.roofSlabMeta | 8);
         this.setBlockAndMetadata(world, j1, 4, -3, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, j1, 4, -2, this.roofBlock, this.roofMeta);
         this.setAir(world, j1, 3, -2);
         this.setBlockAndMetadata(world, j1, 3, -1, this.roofSlabBlock, this.roofSlabMeta | 8);
         this.setBlockAndMetadata(world, j1, 4, -1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, j1, 3, 0, this.roofBlock, this.roofMeta);
         var22 = new int[]{-4, 0};
         j3 = var22.length;

         for(var13 = 0; var13 < j3; ++var13) {
            k1 = var22[var13];

            for(j1 = 2; (j1 >= 1 || !this.isOpaque(world, j1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, j1, j1, k1, this.fenceBlock, this.fenceMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, -4, 2, 3, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -2, 2, 5, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 1, 2, 5, this.fenceBlock, this.fenceMeta);

      for(j1 = 1; j1 <= 3; ++j1) {
         for(i1 = 2; i1 <= 3; ++i1) {
            for(k1 = 5; (k1 >= 0 || !this.isOpaque(world, i1, k1, j1)) && this.getY(k1) >= 0; --k1) {
               this.setBlockAndMetadata(world, i1, k1, j1, this.brickBlock, this.brickMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 3, 5, 1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 3, 5, 3, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 2, 6, 1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 2, 6, 3, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 3, 6, 2, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 1, 6, 2, this.brickBlock, this.brickMeta);

      for(j1 = 6; j1 <= 8; ++j1) {
         this.setBlockAndMetadata(world, 2, j1, 2, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, 2, 9, 2, this.rockSlabBlock, this.rockSlabMeta);

      for(j1 = 0; j1 <= 4; ++j1) {
         this.setBlockAndMetadata(world, 2, 4, j1, this.brickBlock, this.brickMeta);

         for(i1 = 0; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, 1 - i1, 5 + i1, j1, this.brickStairBlock, 5);
         }
      }

      var20 = new int[]{0, 4};
      i1 = var20.length;

      for(k1 = 0; k1 < i1; ++k1) {
         j1 = var20[k1];

         for(j2 = 1; j2 <= 3; ++j2) {
            this.setBlockAndMetadata(world, 2, j2, j1, this.rockWallBlock, this.rockWallMeta);
         }
      }

      this.setBlockAndMetadata(world, 2, 0, 2, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 2, 1, 2, Blocks.field_150480_ab, 0);
      this.setBlockAndMetadata(world, 2, 2, 2, Blocks.field_150460_al, 5);
      this.setBlockAndMetadata(world, 2, 3, 2, this.brickCarvedBlock, this.brickCarvedMeta);
      this.setBlockAndMetadata(world, 1, 0, 2, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
      this.setBlockAndMetadata(world, 1, 1, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 1, 1, 2, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 1, 1, 3, this.brickBlock, this.brickMeta);

      for(j1 = 1; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, 1, 2, j1, this.rockSlabBlock, this.rockSlabMeta);
      }

      this.placeWeaponRack(world, 1, 3, 2, 7, this.getRandomRohanWeapon(random));
      this.spawnItemFrame(world, 2, 4, 2, 3, this.getRohanFramedItem(random));

      for(j1 = -2; j1 <= 1; ++j1) {
         this.setBlockAndMetadata(world, j1, 5, -4, this.plank2SlabBlock, this.plank2SlabMeta);
      }

      this.setBlockAndMetadata(world, -3, 1, -4, this.plankStairBlock, 3);
      this.setBlockAndMetadata(world, -3, 1, -3, this.plankStairBlock, 2);
      this.setBlockAndMetadata(world, -3, 1, -2, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -3, 1, -1, this.tableBlock, 0);
      this.placeChest(world, random, -3, 1, 0, 4, LOTRChestContents.ROHAN_HOUSE);
      this.setBlockAndMetadata(world, 2, 1, -4, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, 2, 1, -3, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 2, 1, -2, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, 2, 1, -1, Blocks.field_150383_bp, 3);
      this.placeBarrel(world, random, 2, 2, -4, 5, LOTRFoods.ROHAN_DRINK);
      this.placeMug(world, random, 2, 2, -3, 1, LOTRFoods.ROHAN_DRINK);
      if (random.nextBoolean()) {
         this.placePlateWithCertainty(world, random, 2, 2, -2, this.plateBlock, LOTRFoods.ROHAN);
      } else {
         this.setBlockAndMetadata(world, 2, 2, -2, this.plateBlock, 0);
      }

      if (random.nextBoolean()) {
         this.setBlockAndMetadata(world, 3, 2, -2, this.getRandomCakeBlock(random), 0);
      }

      for(j1 = 2; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, -2, 1, j1, this.bedBlock, 3);
         this.setBlockAndMetadata(world, -3, 1, j1, this.bedBlock, 11);
         this.setBlockAndMetadata(world, -3, 3, j1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      }

      var20 = new int[]{1, 4};
      i1 = var20.length;

      for(k1 = 0; k1 < i1; ++k1) {
         j1 = var20[k1];

         for(j2 = 1; j2 <= 2; ++j2) {
            this.setBlockAndMetadata(world, -3, j2, j1, this.fenceBlock, this.fenceMeta);
         }

         this.setBlockAndMetadata(world, -3, 3, j1, this.plank2Block, this.plank2Meta);
      }

      this.setBlockAndMetadata(world, -3, 3, -4, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 2, 3, -4, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -2, 4, 4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 1, 4, 4, Blocks.field_150478_aa, 4);
      this.spawnItemFrame(world, -2, 3, -5, 0, this.getRohanFramedItem(random));
      this.spawnItemFrame(world, 1, 3, -5, 0, this.getRohanFramedItem(random));
      if (random.nextInt(3) != 0) {
         for(j1 = -1; j1 <= 0; ++j1) {
            for(i1 = -3; i1 <= -1; ++i1) {
               this.setBlockAndMetadata(world, j1, 1, i1, this.carpetBlock, this.carpetMeta);
            }
         }
      }

      if (random.nextInt(3) != 0) {
         boolean hayOrWood = random.nextBoolean();

         for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = 6; k1 <= 7; ++k1) {
               if (k1 == 6 || i1 == 0) {
                  for(j1 = 1; !this.isOpaque(world, i1, j1 - 1, k1) && this.getY(j1) >= 0; --j1) {
                  }

                  j2 = j1;
                  if (i1 == 0 && k1 == 6) {
                     j2 = j1 + 1;
                  }

                  for(j3 = j1; j3 <= j2; ++j3) {
                     if (hayOrWood) {
                        this.setBlockAndMetadata(world, i1, j3, k1, Blocks.field_150407_cf, 0);
                     } else {
                        this.setBlockAndMetadata(world, i1, j3, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
                     }
                  }

                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }
            }
         }
      }

      if (random.nextBoolean()) {
         j1 = 2;
         int k1 = 6;
         List chestCoords = new ArrayList();

         for(j1 = -4; j1 <= 3; ++j1) {
            if (!this.isOpaque(world, j1, j1, k1)) {
               chestCoords.add(j1);
            }
         }

         if (!chestCoords.isEmpty()) {
            for(j1 = (Integer)chestCoords.get(random.nextInt(chestCoords.size())); !this.isOpaque(world, j1, j1 - 1, k1) && this.getY(j1) >= 0; --j1) {
            }

            this.placeChest(world, random, j1, j1, k1, 3, LOTRChestContents.ROHAN_HOUSE);
         }
      }

      j1 = 1 + random.nextInt(2);

      for(i1 = 0; i1 < j1; ++i1) {
         LOTREntityRohanMan rohirrim = new LOTREntityRohanMan(world);
         this.spawnNPCAndSetHome(rohirrim, world, 0, 1, 0, 16);
      }

      return true;
   }
}
