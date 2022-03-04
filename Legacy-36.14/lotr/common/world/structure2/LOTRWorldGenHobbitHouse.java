package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHobbitHouse extends LOTRWorldGenHobbitStructure {
   public LOTRWorldGenHobbitHouse(boolean flag) {
      super(flag);
   }

   protected boolean makeWealthy(Random random) {
      return false;
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      if (random.nextBoolean()) {
         this.bedBlock = LOTRMod.strawBed;
      } else {
         this.bedBlock = Blocks.field_150324_C;
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 11, 1);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int k2;
      int i1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(k2 = -9; k2 <= 8; ++k2) {
            for(i1 = -10; i1 <= 6; ++i1) {
               int j1 = this.getTopBlock(world, k2, i1) - 1;
               if (!this.isSurface(world, k2, j1, i1)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > k1) {
                  k1 = j1;
               }
            }
         }

         if (k1 - i1 > 6) {
            return false;
         }
      }

      for(i1 = -4; i1 <= 3; ++i1) {
         for(k1 = -10; k1 <= -6; ++k1) {
            for(k2 = 0; (k2 >= 0 || !this.isOpaque(world, i1, k2, k1)) && this.getY(k2) >= 0; --k2) {
               if (k2 == 0) {
                  this.setBlockAndMetadata(world, i1, k2, k1, Blocks.field_150349_c, 0);
               } else {
                  this.setBlockAndMetadata(world, i1, k2, k1, Blocks.field_150346_d, 0);
               }

               this.setGrassToDirt(world, i1, k2 - 1, k1);
            }

            for(k2 = 1; k2 <= 5; ++k2) {
               this.setAir(world, i1, k2, k1);
            }

            if (i1 == -4 || i1 == 3 || k1 == -10) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.outFenceBlock, this.outFenceMeta);
            }
         }
      }

      for(i1 = -9; i1 <= 8; ++i1) {
         for(k1 = -6; k1 <= 6; ++k1) {
            k2 = Math.abs(k1);
            boolean beam = false;
            boolean wall = false;
            boolean indoors = false;
            if ((i1 == -7 || i1 == 6) && k2 == 4) {
               beam = true;
            } else if ((i1 == -9 || i1 == 8) && k2 == 0) {
               beam = true;
            } else if ((i1 == -3 || i1 == 2) && k2 == 6) {
               beam = true;
            } else if (i1 >= -6 && i1 <= 5 && k2 <= 5 || k2 <= 3 && i1 >= -8 && i1 <= 7) {
               if (i1 != -8 && i1 != 7 && k2 != 5) {
                  indoors = true;
               } else {
                  wall = true;
               }
            }

            if (beam || wall || indoors) {
               int j1;
               for(j1 = 1; j1 <= 6; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }

               if (beam) {
                  for(j1 = 2; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.beamBlock, this.beamMeta);
                     this.setGrassToDirt(world, i1, j1 - 1, k1);
                  }
               } else if (wall) {
                  for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.plankBlock, this.plankMeta);
                     this.setGrassToDirt(world, i1, j1 - 1, k1);
                  }

                  for(j1 = 1; j1 <= 2; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.wallBlock, this.wallMeta);
                  }
               } else if (indoors) {
                  for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.floorBlock, this.floorMeta);
                     this.setGrassToDirt(world, i1, j1 - 1, k1);
                  }

                  this.setBlockAndMetadata(world, i1, 3, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
               }
            }
         }
      }

      for(i1 = -2; i1 <= 1; ++i1) {
         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, -5, Blocks.field_150336_V, 0);
         }
      }

      for(i1 = -1; i1 <= 0; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, -5, this.floorBlock, this.floorMeta);

         for(k1 = 1; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, -5, this.gateBlock, 2);
         }
      }

      this.setBlockAndMetadata(world, 1, 2, -4, Blocks.field_150479_bC, 0);

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -3, i1, -4, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, -7, i1, 0, this.beamBlock, this.beamMeta);

         for(k1 = -6; k1 <= -4; ++k1) {
            this.setBlockAndMetadata(world, k1, i1, 0, this.plankBlock, this.plankMeta);
         }

         this.setBlockAndMetadata(world, -3, i1, 0, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, -3, i1, 4, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, 2, i1, 4, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, 6, i1, 0, this.beamBlock, this.beamMeta);
         this.setBlockAndMetadata(world, 2, i1, -4, this.beamBlock, this.beamMeta);
      }

      this.setBlockAndMetadata(world, -2, 3, -4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -3, 3, -3, this.plankBlock, this.plankMeta);

      for(i1 = -6; i1 <= -4; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, -4, this.plankBlock, this.plankMeta);
      }

      for(i1 = -3; i1 <= -1; ++i1) {
         this.setBlockAndMetadata(world, -7, 3, i1, this.plankBlock, this.plankMeta);
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -7, 3, i1, this.plankBlock, this.plankMeta);
      }

      for(i1 = -6; i1 <= -4; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, 4, this.plankBlock, this.plankMeta);
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -3, 3, i1, this.plankBlock, this.plankMeta);
      }

      for(i1 = -2; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, 4, this.plankBlock, this.plankMeta);
      }

      this.setBlockAndMetadata(world, 2, 3, 3, this.plankBlock, this.plankMeta);

      for(i1 = 3; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, 4, Blocks.field_150334_T, 0);
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, 6, 3, i1, Blocks.field_150334_T, 0);
      }

      this.setBlockAndMetadata(world, 5, 3, 0, this.plankBlock, this.plankMeta);

      for(i1 = -3; i1 <= -1; ++i1) {
         this.setBlockAndMetadata(world, 6, 3, i1, this.plankBlock, this.plankMeta);
      }

      for(i1 = 3; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, -4, this.plankBlock, this.plankMeta);
      }

      this.setBlockAndMetadata(world, 2, 3, -3, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 1, 3, -4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -4, 1, -4, this.plank2Block, this.plank2Meta);
      this.setBlockAndMetadata(world, -4, 2, -4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -5, 2, -5, LOTRMod.glassPane, 0);
      this.placeChest(world, random, -6, 1, -4, 3, LOTRChestContents.HOBBIT_HOLE_STUDY);

      for(i1 = 1; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -7, i1, -3, Blocks.field_150342_X, 0);
         this.setBlockAndMetadata(world, -7, i1, -1, Blocks.field_150342_X, 0);
      }

      this.setBlockAndMetadata(world, -7, 1, -2, this.plank2SlabBlock, this.plank2SlabMeta | 8);
      this.setBlockAndMetadata(world, -5, 1, -2, Blocks.field_150476_ad, 1);
      this.spawnItemFrame(world, -5, 2, 0, 2, new ItemStack(Items.field_151113_aN));
      this.setBlockAndMetadata(world, -3, 1, 1, this.plankStairBlock, 3);
      this.setBlockAndMetadata(world, -3, 2, 1, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, -3, 1, 3, this.plankStairBlock, 2);
      this.setBlockAndMetadata(world, -3, 2, 3, this.plankStairBlock, 6);
      int[] var16 = new int[]{-6, -5};
      k1 = var16.length;

      for(k2 = 0; k2 < k1; ++k2) {
         i1 = var16[k2];
         this.setBlockAndMetadata(world, i1, 1, 3, this.bedBlock, 0);
         this.setBlockAndMetadata(world, i1, 1, 4, this.bedBlock, 8);
      }

      this.setBlockAndMetadata(world, -4, 1, 4, this.plank2Block, this.plank2Meta);
      this.setBlockAndMetadata(world, -4, 2, 4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -7, 1, 1, this.beamBlock, this.beamMeta);
      this.placeBarrel(world, random, -7, 2, 1, 4, LOTRFoods.HOBBIT_DRINK);
      this.setBlockAndMetadata(world, -1, 2, 5, LOTRMod.glassPane, 0);
      this.setBlockAndMetadata(world, 0, 2, 5, LOTRMod.glassPane, 0);
      this.setBlockAndMetadata(world, 2, 2, 3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 3, 1, 4, this.tableBlock, 0);

      for(i1 = 4; i1 <= 5; ++i1) {
         this.placeChest(world, random, i1, 1, 4, 2, LOTRChestContents.HOBBIT_HOLE_LARDER);
      }

      this.setBlockAndMetadata(world, 6, 1, 3, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 6, 1, 2, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, 6, 1, 1, LOTRMod.hobbitOven, 5);
      this.setBlockAndMetadata(world, 6, 2, -1, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 4, 2, -5, LOTRMod.glassPane, 0);
      this.setBlockAndMetadata(world, 3, 2, -4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 6, 1, -1, this.plankStairBlock, 2);
      this.setBlockAndMetadata(world, 6, 1, -2, this.plankStairBlock, 1);
      this.setBlockAndMetadata(world, 6, 1, -3, this.plankStairBlock, 3);
      this.setBlockAndMetadata(world, 5, 1, -4, this.plankStairBlock, 1);
      this.setBlockAndMetadata(world, 4, 1, -4, this.plankStairBlock, 3);
      this.setBlockAndMetadata(world, 3, 1, -4, this.plankStairBlock, 0);

      for(i1 = 3; i1 <= 4; ++i1) {
         for(k1 = -2; k1 <= -1; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150344_f, 1);
            if (random.nextBoolean()) {
               this.placePlateWithCertainty(world, random, i1, 2, k1, this.plateBlock, LOTRFoods.HOBBIT);
            } else {
               this.placeMug(world, random, i1, 2, k1, random.nextInt(4), LOTRFoods.HOBBIT_DRINK);
            }
         }
      }

      for(i1 = -1; i1 <= 0; ++i1) {
         for(k1 = -2; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, this.carpetBlock, this.carpetMeta);
         }
      }

      for(i1 = -2; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, -6, this.pathBlock, this.pathMeta);
      }

      this.setBlockAndMetadata(world, -1, 0, -7, this.pathBlock, this.pathMeta);
      this.setBlockAndMetadata(world, 0, 0, -7, this.pathBlock, this.pathMeta);
      this.setBlockAndMetadata(world, 0, 0, -8, this.pathBlock, this.pathMeta);
      this.setBlockAndMetadata(world, 1, 0, -8, this.pathBlock, this.pathMeta);
      this.setBlockAndMetadata(world, 1, 0, -9, this.pathBlock, this.pathMeta);
      this.setBlockAndMetadata(world, 1, 0, -10, this.pathBlock, this.pathMeta);
      this.setAir(world, 1, 1, -10);

      for(i1 = -3; i1 <= 2; ++i1) {
         for(k1 = -9; k1 <= -7; ++k1) {
            if (this.getBlock(world, i1, 0, k1) == Blocks.field_150349_c && random.nextInt(4) == 0) {
               this.plantFlower(world, random, i1, 1, k1);
            }
         }
      }

      this.placeHedge(world, -7, 1, -5);
      this.placeHedge(world, -8, 1, -4);

      for(i1 = -3; i1 <= -1; ++i1) {
         this.placeHedge(world, -9, 1, i1);
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.placeHedge(world, -9, 1, i1);
      }

      this.placeHedge(world, -8, 1, 4);
      this.placeHedge(world, -7, 1, 5);

      for(i1 = -6; i1 <= -4; ++i1) {
         this.placeHedge(world, i1, 1, 6);
      }

      for(i1 = 3; i1 <= 5; ++i1) {
         this.placeHedge(world, i1, 1, 6);
      }

      this.placeHedge(world, 6, 1, 5);
      this.placeHedge(world, 7, 1, 4);

      for(i1 = 1; i1 <= 3; ++i1) {
         this.placeHedge(world, 8, 1, i1);
      }

      for(i1 = -3; i1 <= -1; ++i1) {
         this.placeHedge(world, 8, 1, i1);
      }

      this.placeHedge(world, 7, 1, -4);
      this.placeHedge(world, 6, 1, -5);

      for(i1 = -2; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 6, Blocks.field_150336_V, 0);
         this.setGrassToDirt(world, i1, 0, 6);
         this.placeFlowerPot(world, i1, 2, 6, this.getRandomFlower(world, random));
      }

      for(i1 = -6; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, -6, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 3, 6, this.roofSlabBlock, this.roofSlabMeta);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -9, 3, i1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, 8, 3, i1, this.roofSlabBlock, this.roofSlabMeta);
      }

      this.setBlockAndMetadata(world, -7, 3, -5, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -8, 3, -4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -8, 3, 4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -7, 3, 5, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 6, 3, 5, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 7, 3, 4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 7, 3, -4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 6, 3, -5, this.roofSlabBlock, this.roofSlabMeta);

      for(i1 = -6; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, -5, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, i1, 3, 5, this.roofBlock, this.roofMeta);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -8, 3, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 7, 3, i1, this.roofBlock, this.roofMeta);
      }

      this.setBlockAndMetadata(world, -7, 3, -4, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 6, 3, -4, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -7, 3, 4, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 6, 3, 4, this.roofBlock, this.roofMeta);

      for(i1 = -5; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -4, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 4, 4, this.roofStairBlock, 3);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -7, 4, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 6, 4, i1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -6, 4, -4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -6, 4, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -7, 4, -3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 5, 4, -4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 5, 4, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 6, 4, -3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -6, 4, 4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -6, 4, 3, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -7, 4, 3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 5, 4, 4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 5, 4, 3, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 6, 4, 3, this.roofStairBlock, 0);

      for(i1 = -6; i1 <= 5; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            if (i1 >= -5 && i1 <= 4 || k1 >= -2 && k1 <= 2) {
               this.setBlockAndMetadata(world, i1, 4, k1, this.roofBlock, this.roofMeta);
               this.setBlockAndMetadata(world, i1, 5, k1, this.roofSlabBlock, this.roofSlabMeta);
            }
         }
      }

      for(i1 = -5; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, -2, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 5, 2, this.roofStairBlock, 3);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -5, 5, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 4, 5, i1, this.roofStairBlock, 0);
      }

      for(i1 = -4; i1 <= 3; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, i1, 5, k1, this.roofBlock, this.roofMeta);
         }
      }

      this.setBlockAndMetadata(world, 3, 5, 0, Blocks.field_150336_V, 0);
      this.setBlockAndMetadata(world, 3, 6, 0, Blocks.field_150336_V, 0);
      this.setBlockAndMetadata(world, 3, 7, 0, Blocks.field_150457_bL, 0);

      for(i1 = -2; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, -5, Blocks.field_150336_V, 0);
         this.setBlockAndMetadata(world, i1, 4, -5, this.roofSlabBlock, this.roofSlabMeta);
      }

      this.setBlockAndMetadata(world, -3, 3, -6, this.tileSlabBlock, this.tileSlabMeta);
      this.setBlockAndMetadata(world, -2, 3, -6, this.tileSlabBlock, this.tileSlabMeta | 8);
      this.setBlockAndMetadata(world, -1, 4, -6, this.tileSlabBlock, this.tileSlabMeta);
      this.setBlockAndMetadata(world, 0, 4, -6, this.tileSlabBlock, this.tileSlabMeta);
      this.setBlockAndMetadata(world, 1, 3, -6, this.tileSlabBlock, this.tileSlabMeta | 8);
      this.setBlockAndMetadata(world, 2, 3, -6, this.tileSlabBlock, this.tileSlabMeta);
      this.setBlockAndMetadata(world, -3, 2, -7, this.tileSlabBlock, this.tileSlabMeta | 8);
      this.setBlockAndMetadata(world, -2, 3, -7, this.tileSlabBlock, this.tileSlabMeta);
      this.setBlockAndMetadata(world, -1, 3, -7, this.tileSlabBlock, this.tileSlabMeta | 8);
      this.setBlockAndMetadata(world, 0, 3, -7, this.tileSlabBlock, this.tileSlabMeta | 8);
      this.setBlockAndMetadata(world, 1, 3, -7, this.tileSlabBlock, this.tileSlabMeta);
      this.setBlockAndMetadata(world, 2, 2, -7, this.tileSlabBlock, this.tileSlabMeta | 8);
      this.spawnHobbitCouple(world, 0, 1, 0, 16);
      return true;
   }

   private void placeHedge(World world, int i, int j, int k) {
      int j1;
      for(j1 = j; !this.isOpaque(world, i, j1 - 1, k) && j1 >= j - 6; --j1) {
      }

      this.setBlockAndMetadata(world, i, j1, k, this.hedgeBlock, this.hedgeMeta);
   }
}
