package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorWatchfort extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorWatchfort(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.barsBlock = Blocks.field_150411_aY;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 9);
      this.setupRandomBlocks(random);
      byte beaconX;
      byte beaconZ;
      int j2;
      int soldiers;
      int l;
      if (this.restrictions) {
         int x1 = -6;
         beaconX = 6;
         int z1 = -6;
         beaconZ = 34;

         for(j2 = x1; j2 <= beaconX; ++j2) {
            for(soldiers = z1; soldiers <= beaconZ; ++soldiers) {
               l = this.getTopBlock(world, j2, soldiers) - 1;
               if (!this.isSurface(world, j2, l, soldiers)) {
                  return false;
               }
            }
         }
      }

      int i1;
      int j1;
      int i1;
      for(i1 = -5; i1 <= 5; ++i1) {
         for(j1 = 1; j1 <= 11; ++j1) {
            for(i1 = -5; i1 <= 5; ++i1) {
               if (Math.abs(i1) == 5 && Math.abs(i1) == 5) {
                  this.setBlockAndMetadata(world, i1, j1, i1, this.pillar2Block, this.pillar2Meta);
               } else {
                  this.placeRandomBrick(world, random, i1, j1, i1);
               }
            }
         }
      }

      for(i1 = -6; i1 <= 6; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, -6, this.brick2StairBlock, 2);
         this.setBlockAndMetadata(world, i1, 1, 6, this.brick2StairBlock, 3);
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, -6, 1, i1, this.brick2StairBlock, 1);
         this.setBlockAndMetadata(world, 6, 1, i1, this.brick2StairBlock, 0);
      }

      for(i1 = -6; i1 <= 6; ++i1) {
         for(j1 = -6; j1 <= 6; ++j1) {
            for(i1 = 0; !this.isOpaque(world, i1, i1, j1) && this.getY(i1) >= 0; --i1) {
               this.placeRandomBrick(world, random, i1, i1, j1);
               this.setGrassToDirt(world, i1, i1 - 1, j1);
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(j1 = -4; j1 <= 4; ++j1) {
            for(i1 = 2; i1 <= 5; ++i1) {
               this.setAir(world, i1, i1, j1);
            }

            for(i1 = 7; i1 <= 10; ++i1) {
               this.setAir(world, i1, i1, j1);
            }
         }
      }

      int[] var20 = new int[]{4, 9};
      j1 = var20.length;

      int i1;
      for(i1 = 0; i1 < j1; ++i1) {
         i1 = var20[i1];
         this.setBlockAndMetadata(world, -4, i1, -2, Blocks.field_150478_aa, 2);
         this.setBlockAndMetadata(world, -4, i1, 2, Blocks.field_150478_aa, 2);
         this.setBlockAndMetadata(world, 4, i1, -2, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, 4, i1, 2, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, -2, i1, -4, Blocks.field_150478_aa, 3);
         this.setBlockAndMetadata(world, 2, i1, -4, Blocks.field_150478_aa, 3);
         this.setBlockAndMetadata(world, -2, i1, 4, Blocks.field_150478_aa, 4);
         this.setBlockAndMetadata(world, 2, i1, 4, Blocks.field_150478_aa, 4);
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(j1 = 12; j1 <= 16; ++j1) {
            for(i1 = -4; i1 <= 4; ++i1) {
               if (Math.abs(i1) == 4 && Math.abs(i1) == 4) {
                  this.setBlockAndMetadata(world, i1, j1, i1, this.pillar2Block, this.pillar2Meta);
               } else {
                  this.placeRandomBrick(world, random, i1, j1, i1);
               }
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 12, -5, this.brick2StairBlock, 2);
         this.setBlockAndMetadata(world, i1, 12, 5, this.brick2StairBlock, 3);
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -5, 12, i1, this.brick2StairBlock, 1);
         this.setBlockAndMetadata(world, 5, 12, i1, this.brick2StairBlock, 0);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(j1 = -3; j1 <= 3; ++j1) {
            for(i1 = 12; i1 <= 15; ++i1) {
               this.setAir(world, i1, i1, j1);
            }
         }
      }

      this.setBlockAndMetadata(world, -3, 14, -2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -3, 14, 2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, 14, -2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 3, 14, 2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -2, 14, -3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 14, -3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 14, 3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 14, 3, Blocks.field_150478_aa, 4);

      for(i1 = -4; i1 <= 4; ++i1) {
         this.placeRandomWall(world, random, i1, 17, -4);
         this.placeRandomWall(world, random, i1, 17, 4);
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         this.placeRandomWall(world, random, -4, 17, i1);
         this.placeRandomWall(world, random, 4, 17, i1);
      }

      var20 = new int[]{-4, 4};
      j1 = var20.length;

      int j2;
      int k2;
      int[] var21;
      for(i1 = 0; i1 < j1; ++i1) {
         i1 = var20[i1];
         var21 = new int[]{-4, 4};
         soldiers = var21.length;

         for(l = 0; l < soldiers; ++l) {
            j2 = var21[l];

            for(k2 = 17; k2 <= 19; ++k2) {
               this.setBlockAndMetadata(world, i1, k2, j2, this.pillar2Block, this.pillar2Meta);
            }

            this.placeRandomBrick(world, random, i1, 20, j2);
         }
      }

      this.setBlockAndMetadata(world, -4, 19, -3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 4, 19, -3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -4, 19, 3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 4, 19, 3, Blocks.field_150478_aa, 4);

      for(i1 = -2; i1 <= 2; ++i1) {
         for(j1 = -2; j1 <= 2; ++j1) {
            this.setBlockAndMetadata(world, i1, 21, j1, this.brick2Block, this.brick2Meta);
            if (Math.abs(i1) <= 1 && Math.abs(j1) <= 1) {
               this.setBlockAndMetadata(world, i1, 22, j1, this.brick2Block, this.brick2Meta);
            } else {
               this.setBlockAndMetadata(world, i1, 22, j1, this.brick2SlabBlock, this.brick2SlabMeta);
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         for(j1 = -5; j1 <= 5; ++j1) {
            this.setBlockAndMetadata(world, i1, 21, j1, this.brick2SlabBlock, this.brick2SlabMeta);
         }
      }

      var20 = new int[]{-4, 4};
      j1 = var20.length;

      for(i1 = 0; i1 < j1; ++i1) {
         i1 = var20[i1];
         var21 = new int[]{-4, 4};
         soldiers = var21.length;

         for(l = 0; l < soldiers; ++l) {
            j2 = var21[l];
            this.setBlockAndMetadata(world, i1, 20, j2 - 1, this.brick2StairBlock, 6);
            this.setBlockAndMetadata(world, i1, 20, j2 + 1, this.brick2StairBlock, 7);

            for(k2 = j2 - 1; k2 <= j2 + 1; ++k2) {
               this.setBlockAndMetadata(world, i1 - 1, 20, k2, this.brick2StairBlock, 5);
               this.setBlockAndMetadata(world, i1 + 1, 20, k2, this.brick2StairBlock, 4);
            }
         }
      }

      this.setBlockAndMetadata(world, -4, 21, -4, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, -4, 21, -3, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, -4, 21, -2, this.brick2StairBlock, 1);
      this.setBlockAndMetadata(world, -4, 21, -1, this.brick2StairBlock, 3);
      this.setBlockAndMetadata(world, -4, 21, 0, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, -4, 21, 1, this.brick2StairBlock, 2);
      this.setBlockAndMetadata(world, -4, 21, 2, this.brick2StairBlock, 1);
      this.setBlockAndMetadata(world, -4, 21, 3, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, -4, 21, 4, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, -3, 21, -4, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, -3, 21, -3, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, -3, 21, -2, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, -3, 21, -1, this.brick2StairBlock, 1);
      this.setBlockAndMetadata(world, -3, 21, 0, this.brick2StairBlock, 1);
      this.setBlockAndMetadata(world, -3, 21, 1, this.brick2StairBlock, 1);
      this.setBlockAndMetadata(world, -3, 21, 2, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, -3, 21, 3, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, -3, 21, 4, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, 4, 21, -4, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, 4, 21, -3, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, 4, 21, -2, this.brick2StairBlock, 0);
      this.setBlockAndMetadata(world, 4, 21, -1, this.brick2StairBlock, 3);
      this.setBlockAndMetadata(world, 4, 21, 0, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, 4, 21, 1, this.brick2StairBlock, 2);
      this.setBlockAndMetadata(world, 4, 21, 2, this.brick2StairBlock, 0);
      this.setBlockAndMetadata(world, 4, 21, 3, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, 4, 21, 4, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, 3, 21, -4, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, 3, 21, -3, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, 3, 21, -2, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, 3, 21, -1, this.brick2StairBlock, 0);
      this.setBlockAndMetadata(world, 3, 21, 0, this.brick2StairBlock, 0);
      this.setBlockAndMetadata(world, 3, 21, 1, this.brick2StairBlock, 0);
      this.setBlockAndMetadata(world, 3, 21, 2, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, 3, 21, 3, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, 3, 21, 4, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, -2, 21, 4, this.brick2StairBlock, 3);
      this.setBlockAndMetadata(world, -1, 21, 4, this.brick2StairBlock, 0);
      this.setBlockAndMetadata(world, 0, 21, 4, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, 1, 21, 4, this.brick2StairBlock, 1);
      this.setBlockAndMetadata(world, 2, 21, 4, this.brick2StairBlock, 3);
      this.setBlockAndMetadata(world, -2, 21, 3, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, -1, 21, 3, this.brick2StairBlock, 3);
      this.setBlockAndMetadata(world, 0, 21, 3, this.brick2StairBlock, 3);
      this.setBlockAndMetadata(world, 1, 21, 3, this.brick2StairBlock, 3);
      this.setBlockAndMetadata(world, 2, 21, 3, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, -2, 21, -4, this.brick2StairBlock, 2);
      this.setBlockAndMetadata(world, -1, 21, -4, this.brick2StairBlock, 0);
      this.setBlockAndMetadata(world, 0, 21, -4, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, 1, 21, -4, this.brick2StairBlock, 1);
      this.setBlockAndMetadata(world, 2, 21, -4, this.brick2StairBlock, 2);
      this.setBlockAndMetadata(world, -2, 21, -3, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, -1, 21, -3, this.brick2StairBlock, 2);
      this.setBlockAndMetadata(world, 0, 21, -3, this.brick2StairBlock, 2);
      this.setBlockAndMetadata(world, 1, 21, -3, this.brick2StairBlock, 2);
      this.setBlockAndMetadata(world, 2, 21, -3, this.brick2Block, this.brick2Meta);
      this.placeBarredWindowOnZ(world, -5, 3, 0);
      this.placeBarredWindowOnZ(world, 5, 3, 0);
      this.placeBarredWindowOnX(world, 0, 3, -5);
      this.placeBarredWindowOnX(world, 0, 3, 5);
      this.placeBarredWindowOnZ(world, -5, 8, 0);
      this.placeBarredWindowOnZ(world, 5, 8, 0);
      this.placeBarredWindowOnX(world, 0, 8, -5);
      this.placeBarredWindowOnX(world, 0, 8, 5);
      this.placeBarredWindowOnZ(world, -4, 13, 0);
      this.placeBarredWindowOnZ(world, 4, 13, 0);
      this.placeBarredWindowOnX(world, 0, 13, -4);
      this.placeBarredWindowOnX(world, 0, 13, 4);

      for(i1 = -2; i1 <= 2; ++i1) {
         for(j1 = -8; j1 <= -7; ++j1) {
            for(i1 = 0; !this.isOpaque(world, i1, i1, j1) && this.getY(i1) >= 0; --i1) {
               this.placeRandomBrick(world, random, i1, i1, j1);
               this.setGrassToDirt(world, i1, i1 - 1, j1);
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         if (Math.abs(i1) == 2) {
            for(j1 = 1; j1 <= 4; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, -6, this.pillarBlock, this.pillarMeta);
            }
         }

         this.setBlockAndMetadata(world, i1, 5, -6, this.brick2SlabBlock, this.brick2SlabMeta);
         this.placeRandomStairs(world, random, i1, 1, -8, 2);
      }

      this.placeWallBanner(world, 0, 7, -5, this.bannerType, 2);

      for(i1 = -1; i1 <= 1; ++i1) {
         for(j1 = -7; j1 <= -6; ++j1) {
            this.placeRandomBrick(world, random, i1, 1, j1);
         }

         this.placeRandomBrick(world, random, i1, 1, -5);
         this.setAir(world, i1, 2, -5);
         this.setAir(world, i1, 3, -5);
         this.setAir(world, i1, 4, -5);
      }

      this.placeRandomStairs(world, random, -2, 1, -7, 1);
      this.placeRandomStairs(world, random, 2, 1, -7, 0);

      for(i1 = -1; i1 <= 1; ++i1) {
         for(j1 = 2; j1 <= 4; ++j1) {
            this.setBlockAndMetadata(world, i1, j1, -6, this.gateBlock, 3);
         }
      }

      this.placeRandomSlab(world, random, -4, 2, -4, true);
      this.placeBarrel(world, random, -4, 3, -4, 4, LOTRFoods.GONDOR_DRINK);
      this.placeRandomSlab(world, random, -4, 2, -3, true);
      this.placeBarrel(world, random, -4, 3, -3, 4, LOTRFoods.GONDOR_DRINK);
      this.placeChest(world, random, -4, 2, -2, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
      this.placeRandomSlab(world, random, 4, 2, -4, true);
      this.placeBarrel(world, random, 4, 3, -4, 5, LOTRFoods.GONDOR_DRINK);
      this.placeRandomSlab(world, random, 4, 2, -3, true);
      this.placeBarrel(world, random, 4, 3, -3, 5, LOTRFoods.GONDOR_DRINK);
      this.placeChest(world, random, 4, 2, -2, LOTRMod.chestLebethron, 5, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
      this.setBlockAndMetadata(world, -4, 2, 4, this.tableBlock, 0);
      this.setBlockAndMetadata(world, 4, 2, 4, this.tableBlock, 0);

      for(i1 = -1; i1 <= 1; ++i1) {
         for(j1 = 0; j1 <= 3; ++j1) {
            i1 = -1 + j1;
            i1 = 2 + j1;
            this.setAir(world, i1, 6, i1);

            for(j2 = 2; j2 < i1; ++j2) {
               this.placeRandomBrick(world, random, i1, j2, i1);
            }

            this.placeRandomStairs(world, random, i1, i1, i1, 2);
         }

         this.placeRandomStairs(world, random, i1, 6, 3, 2);
      }

      this.placeChest(world, random, 0, 2, 2, LOTRMod.chestLebethron, 3, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
      this.setAir(world, 0, 3, 2);
      this.setBlockAndMetadata(world, 0, 7, -4, LOTRMod.commandTable, 0);
      var20 = new int[]{-3, 3};
      j1 = var20.length;

      for(i1 = 0; i1 < j1; ++i1) {
         i1 = var20[i1];

         for(j2 = 0; j2 <= 4; ++j2) {
            soldiers = 2 - j2;
            l = 7 + j2;
            this.setAir(world, i1, 11, soldiers);

            for(j2 = 7; j2 < l; ++j2) {
               this.placeRandomBrick(world, random, i1, j2, soldiers);
            }

            this.placeRandomStairs(world, random, i1, l, soldiers, 3);
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(j1 = 0; j1 <= 3; ++j1) {
            i1 = -2 + j1;
            i1 = 12 + j1;
            this.setAir(world, i1, 16, i1);

            for(j2 = 12; j2 < i1; ++j2) {
               this.placeRandomBrick(world, random, i1, j2, i1);
            }

            this.placeRandomStairs(world, random, i1, i1, i1, 2);
         }

         this.placeRandomStairs(world, random, i1, 16, 2, 2);
      }

      for(i1 = 5; i1 <= 28; ++i1) {
         for(j1 = 12; j1 <= 15; ++j1) {
            for(i1 = -2; i1 <= 2; ++i1) {
               this.setAir(world, i1, j1, i1);
            }
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.placeRandomBrick(world, random, i1, 13, 4);
         this.placeRandomBrick(world, random, i1, 14, 4);
      }

      var20 = new int[]{-2, 2};
      j1 = var20.length;

      for(i1 = 0; i1 < j1; ++i1) {
         i1 = var20[i1];
         this.placeRandomBrick(world, random, i1, 12, 5);
         this.placeRandomBrick(world, random, i1, 13, 5);
         this.setBlockAndMetadata(world, i1, 14, 5, this.brick2WallBlock, this.brick2WallMeta);
         this.setBlockAndMetadata(world, i1, 15, 5, Blocks.field_150478_aa, 5);
      }

      this.setBlockAndMetadata(world, 0, 12, 4, this.doorBlock, 3);
      this.setBlockAndMetadata(world, 0, 13, 4, this.doorBlock, 8);

      for(i1 = 6; i1 <= 28; ++i1) {
         for(j1 = -1; j1 <= 1; ++j1) {
            this.placeRandomBrick(world, random, j1, 11, i1);
         }

         this.placeRandomWall(world, random, -2, 12, i1);
         this.placeRandomWall(world, random, 2, 12, i1);
         this.placeRandomStairs(world, random, -2, 11, i1, 5);
         this.placeRandomStairs(world, random, 2, 11, i1, 4);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.placeRandomStairs(world, random, i1, 10, 6, 7);
         this.placeRandomStairs(world, random, i1, 10, 16, 6);
         this.placeRandomStairs(world, random, i1, 10, 18, 7);
         this.placeRandomStairs(world, random, i1, 10, 28, 6);

         for(j1 = 10; !this.isOpaque(world, i1, j1, 17) && this.getY(j1) >= 0; --j1) {
            this.placeRandomBrick(world, random, i1, j1, 17);
            this.setGrassToDirt(world, i1, j1 - 1, 17);
         }
      }

      for(i1 = 12; i1 <= 13; ++i1) {
         this.placeRandomBrick(world, random, -2, i1, 11);
         this.placeRandomBrick(world, random, 2, i1, 11);
         this.placeRandomBrick(world, random, -2, i1, 23);
         this.placeRandomBrick(world, random, 2, i1, 23);
      }

      this.setBlockAndMetadata(world, -1, 13, 11, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 13, 11, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -1, 13, 23, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 13, 23, Blocks.field_150478_aa, 1);
      this.placeBanner(world, -2, 14, 11, this.bannerType, 3);
      this.placeBanner(world, 2, 14, 11, this.bannerType, 1);
      this.placeBanner(world, -2, 14, 23, this.bannerType, 3);
      this.placeBanner(world, 2, 14, 23, this.bannerType, 1);

      for(i1 = 12; i1 <= 14; ++i1) {
         this.placeRandomBrick(world, random, -2, i1, 17);
         this.placeRandomBrick(world, random, 2, i1, 17);
      }

      this.placeRandomBrick(world, random, -2, 15, 17);
      this.placeRandomBrick(world, random, 2, 15, 17);
      this.placeRandomStairs(world, random, -1, 15, 17, 4);
      this.placeRandomStairs(world, random, 1, 15, 17, 5);
      this.placeRandomSlab(world, random, 0, 15, 17, true);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 16, 17, this.brick2Block, this.brick2Meta);
      }

      this.setBlockAndMetadata(world, -2, 16, 17, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, 2, 16, 17, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, 0, 17, 17, this.brick2SlabBlock, this.brick2SlabMeta);
      this.setBlockAndMetadata(world, -2, 14, 16, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 14, 16, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -2, 14, 18, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 14, 18, Blocks.field_150478_aa, 3);
      LOTRWorldGenBeaconTower beaconTower = new LOTRWorldGenBeaconTower(this.notifyChanges);
      beaconTower.restrictions = false;
      beaconTower.generateRoom = false;
      beaconTower.strFief = this.strFief;
      beaconX = 0;
      int beaconY = 12;
      beaconZ = 34;
      beaconTower.generateWithSetRotationAndHeight(world, random, this.getX(beaconX, beaconZ), this.getY(beaconY), this.getZ(beaconX, beaconZ), (this.getRotationMode() + 2) % 4, -8);
      this.setAir(world, -1, 12, 29);
      this.setAir(world, 0, 12, 29);
      this.setAir(world, 1, 12, 29);
      Class[] soldierClasses = this.strFief.getSoldierClasses();
      soldiers = 4 + random.nextInt(4);

      for(l = 0; l < soldiers; ++l) {
         Class entityClass = soldierClasses[0];
         if (random.nextInt(3) == 0) {
            entityClass = soldierClasses[1];
         }

         LOTREntityGondorMan soldier = (LOTREntityGondorMan)LOTREntities.createEntityByClass(entityClass, world);
         soldier.spawnRidingHorse = false;
         this.spawnNPCAndSetHome(soldier, world, 0, 2, -3, 32);
      }

      LOTREntityGondorMan captain = this.strFief.createCaptain(world);
      captain.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(captain, world, 0, 15, 0, 8);
      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(soldierClasses[0], soldierClasses[1]);
      respawner.setCheckRanges(24, -8, 18, 12);
      respawner.setSpawnRanges(4, 2, 17, 32);
      this.placeNPCRespawner(respawner, world, 0, 2, 0);
      return true;
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(10) == 0) {
         if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, i, j, k, this.brickMossyBlock, this.brickMossyMeta);
         } else {
            this.setBlockAndMetadata(world, i, j, k, this.brickCrackedBlock, this.brickCrackedMeta);
         }
      } else {
         this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
      }

   }

   private void placeRandomSlab(World world, Random random, int i, int j, int k, boolean inverted) {
      int flag = inverted ? 8 : 0;
      if (random.nextInt(10) == 0) {
         if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, i, j, k, this.brickMossySlabBlock, this.brickMossySlabMeta | flag);
         } else {
            this.setBlockAndMetadata(world, i, j, k, this.brickCrackedSlabBlock, this.brickCrackedSlabMeta | flag);
         }
      } else {
         this.setBlockAndMetadata(world, i, j, k, this.brickSlabBlock, this.brickSlabMeta | flag);
      }

   }

   private void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
      if (random.nextInt(10) == 0) {
         if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, i, j, k, this.brickMossyStairBlock, meta);
         } else {
            this.setBlockAndMetadata(world, i, j, k, this.brickCrackedStairBlock, meta);
         }
      } else {
         this.setBlockAndMetadata(world, i, j, k, this.brickStairBlock, meta);
      }

   }

   private void placeRandomWall(World world, Random random, int i, int j, int k) {
      if (random.nextInt(10) == 0) {
         if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, i, j, k, this.brickMossyWallBlock, this.brickMossyWallMeta);
         } else {
            this.setBlockAndMetadata(world, i, j, k, this.brickCrackedWallBlock, this.brickCrackedWallMeta);
         }
      } else {
         this.setBlockAndMetadata(world, i, j, k, this.brickWallBlock, this.brickWallMeta);
      }

   }

   private void placeBarredWindowOnX(World world, int i, int j, int k) {
      for(int i1 = -1; i1 <= 1; ++i1) {
         for(int j1 = 0; j1 <= 1; ++j1) {
            this.setBlockAndMetadata(world, i + i1, j + j1, k, this.barsBlock, 0);
         }
      }

   }

   private void placeBarredWindowOnZ(World world, int i, int j, int k) {
      for(int k1 = -1; k1 <= 1; ++k1) {
         for(int j1 = 0; j1 <= 1; ++j1) {
            this.setBlockAndMetadata(world, i, j + j1, k + k1, this.barsBlock, 0);
         }
      }

   }
}
