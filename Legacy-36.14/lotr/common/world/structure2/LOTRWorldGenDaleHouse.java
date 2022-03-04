package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDaleMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenDaleHouse extends LOTRWorldGenDaleStructure {
   public LOTRWorldGenDaleHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int chestDir;
      int i1;
      int k1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(chestDir = -8; chestDir <= 3; ++chestDir) {
            for(i1 = -1; i1 <= 9; ++i1) {
               k1 = this.getTopBlock(world, chestDir, i1) - 1;
               Block block = this.getBlock(world, chestDir, k1, i1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }

               if (k1 < i1) {
                  i1 = k1;
               }

               if (k1 > k1) {
                  k1 = k1;
               }

               if (k1 - i1 > 5) {
                  return false;
               }
            }
         }
      }

      for(i1 = -7; i1 <= 2; ++i1) {
         for(k1 = 0; k1 <= 8; ++k1) {
            if (i1 >= -2 || k1 <= 4) {
               for(chestDir = 1; chestDir <= 10; ++chestDir) {
                  this.setAir(world, i1, chestDir, k1);
               }

               Block fillBlock = null;
               int fillMeta = false;
               if ((i1 == -2 || i1 == 2) && (k1 == 0 || k1 == 4 || k1 == 8)) {
                  fillBlock = this.brickBlock;
                  i1 = this.brickMeta;

                  for(k1 = 1; k1 <= 7; ++k1) {
                     this.setBlockAndMetadata(world, i1, k1, k1, this.brickBlock, this.brickMeta);
                  }
               } else if ((k1 == 0 || k1 == 4) && i1 >= -6 && i1 <= -4) {
                  fillBlock = this.brickBlock;
                  i1 = this.brickMeta;

                  for(k1 = 1; k1 <= 4; ++k1) {
                     this.setBlockAndMetadata(world, i1, k1, k1, this.brickBlock, this.brickMeta);
                  }

                  for(k1 = 5; k1 <= 6; ++k1) {
                     this.setBlockAndMetadata(world, i1, k1, k1, this.plankBlock, this.plankMeta);
                  }

                  this.setBlockAndMetadata(world, i1, 7, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
               } else if (i1 == -7 && k1 >= 1 && k1 <= 3) {
                  fillBlock = this.brickBlock;
                  i1 = this.brickMeta;

                  for(k1 = 1; k1 <= 4; ++k1) {
                     this.setBlockAndMetadata(world, i1, k1, k1, this.brickBlock, this.brickMeta);
                  }

                  for(k1 = 5; k1 <= 6; ++k1) {
                     this.setBlockAndMetadata(world, i1, k1, k1, this.plankBlock, this.plankMeta);
                  }

                  this.setBlockAndMetadata(world, i1, 7, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
               } else if (k1 != 0 && k1 != 4 || i1 != -7 && i1 != -3) {
                  fillBlock = this.floorBlock;
                  i1 = this.floorMeta;
               } else {
                  fillBlock = this.woodBlock;
                  i1 = this.woodMeta;

                  for(k1 = 1; k1 <= 7; ++k1) {
                     this.setBlockAndMetadata(world, i1, k1, k1, this.woodBlock, this.woodMeta);
                  }
               }

               for(k1 = 0; (k1 == 0 || !this.isOpaque(world, i1, k1, k1)) && this.getY(k1) >= 0; --k1) {
                  this.setBlockAndMetadata(world, i1, k1, k1, fillBlock, i1);
                  this.setGrassToDirt(world, i1, k1 - 1, k1);
               }
            }
         }
      }

      int[][] var14 = new int[][]{{-3, -1}, {-7, -1}, {-8, 0}, {-8, 4}, {-7, 5}};
      k1 = var14.length;

      for(chestDir = 0; chestDir < k1; ++chestDir) {
         int[] pos = var14[chestDir];
         k1 = pos[0];
         int k1 = pos[1];

         for(int j1 = 7; (j1 >= 4 || !this.isOpaque(world, k1, j1, k1)) && this.getY(j1) >= 0; --j1) {
            this.setBlockAndMetadata(world, k1, j1, k1, this.fenceBlock, this.fenceMeta);
         }
      }

      int[] var15 = new int[]{0, 4, 8};
      k1 = var15.length;

      for(chestDir = 0; chestDir < k1; ++chestDir) {
         i1 = var15[chestDir];
         this.setBlockAndMetadata(world, -1, 3, i1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 1, 3, i1, this.brickStairBlock, 5);
      }

      var15 = new int[]{-2, 2};
      k1 = var15.length;

      for(chestDir = 0; chestDir < k1; ++chestDir) {
         i1 = var15[chestDir];
         this.setBlockAndMetadata(world, i1, 3, 1, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 3, 3, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 3, 5, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 3, 7, this.brickStairBlock, 6);
      }

      for(i1 = 1; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -2, i1, 1, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, -2, i1, 3, this.brickWallBlock, this.brickWallMeta);
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, -3, k1, i1, this.brickBlock, this.brickMeta);
         }
      }

      this.setBlockAndMetadata(world, -3, 1, 2, this.doorBlock, 0);
      this.setBlockAndMetadata(world, -3, 2, 2, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 1, 7, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 1, 1, 7, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 1, 2, 7, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 1, 1, 6, Blocks.field_150407_cf, 0);

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -1, this.brickStairBlock, 6);
         if (IntMath.mod(i1, 2) == 1) {
            this.setBlockAndMetadata(world, i1, 5, -1, this.brickWallBlock, this.brickWallMeta);
         }
      }

      for(i1 = -1; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, 3, 4, i1, this.brickStairBlock, 4);
         if (IntMath.mod(i1, 2) == 1) {
            this.setBlockAndMetadata(world, 3, 5, i1, this.brickWallBlock, this.brickWallMeta);
         }
      }

      for(i1 = 2; i1 >= -2; --i1) {
         this.setBlockAndMetadata(world, i1, 4, 9, this.brickStairBlock, 7);
         if (IntMath.mod(i1, 2) == 1) {
            this.setBlockAndMetadata(world, i1, 5, 9, this.brickWallBlock, this.brickWallMeta);
         }
      }

      for(i1 = 9; i1 >= 5; --i1) {
         this.setBlockAndMetadata(world, -3, 4, i1, this.brickStairBlock, 5);
         if (IntMath.mod(i1, 2) == 1) {
            this.setBlockAndMetadata(world, -3, 5, i1, this.brickWallBlock, this.brickWallMeta);
         }
      }

      for(i1 = -4; i1 >= -6; --i1) {
         this.setBlockAndMetadata(world, i1, 4, 5, this.brickStairBlock, 7);
      }

      this.setBlockAndMetadata(world, -7, 4, 5, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -8, 4, 5, this.brickSlabBlock, this.brickSlabMeta | 8);
      this.setBlockAndMetadata(world, -8, 4, 4, this.brickBlock, this.brickMeta);

      for(i1 = 3; i1 >= 1; --i1) {
         this.setBlockAndMetadata(world, -8, 4, i1, this.brickStairBlock, 5);
      }

      this.setBlockAndMetadata(world, -8, 4, 0, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -8, 4, -1, this.brickSlabBlock, this.brickSlabMeta | 8);
      this.setBlockAndMetadata(world, -7, 4, -1, this.brickBlock, this.brickMeta);

      for(i1 = -6; i1 <= -4; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -1, this.brickStairBlock, 6);
      }

      this.setBlockAndMetadata(world, -3, 4, -1, this.brickBlock, this.brickMeta);
      var15 = new int[]{0, 4, 8};
      k1 = var15.length;

      for(chestDir = 0; chestDir < k1; ++chestDir) {
         i1 = var15[chestDir];

         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, k1, 4, i1, this.brickBlock, this.brickMeta);
            if (i1 == 0 || i1 == 8) {
               this.setBlockAndMetadata(world, k1, 5, i1, this.plankBlock, this.plankMeta);
               this.setBlockAndMetadata(world, k1, 6, i1, this.plankBlock, this.plankMeta);
               this.setBlockAndMetadata(world, k1, 7, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
            }
         }
      }

      var15 = new int[]{-2, 2};
      k1 = var15.length;

      for(chestDir = 0; chestDir < k1; ++chestDir) {
         i1 = var15[chestDir];

         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 5, k1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, 6, k1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, 7, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
         }

         for(k1 = 5; k1 <= 7; ++k1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 5, k1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, 6, k1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, 7, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.plankBlock, this.plankMeta);
         }

         for(k1 = 5; k1 <= 7; ++k1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.plankBlock, this.plankMeta);
         }
      }

      this.setBlockAndMetadata(world, -5, 2, 0, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 6, 0, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 6, 2, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 6, 6, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 6, 8, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -2, 6, 6, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -5, 6, 4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -7, 6, 2, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -5, 6, 0, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -2, 7, -1, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, 2, 7, -1, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, 3, 7, 0, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 3, 7, 4, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 3, 7, 8, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 2, 7, 9, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, -2, 7, 9, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, -3, 7, 8, this.brickStairBlock, 5);

      for(i1 = -8; i1 <= 3; ++i1) {
         for(k1 = -1; k1 <= 9; ++k1) {
            if (i1 >= -3 || k1 <= 5) {
               this.setBlockAndMetadata(world, i1, 8, k1, this.brickBlock, this.brickMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, -8, 8, -1, this.brickSlabBlock, this.brickSlabMeta | 8);
      this.setBlockAndMetadata(world, -8, 8, 5, this.brickSlabBlock, this.brickSlabMeta | 8);

      for(i1 = -8; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 9, -1, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 10, 0, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 11, 1, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 11, 2, this.roofBlock, this.roofMeta);
         if (i1 <= -1 || i1 >= 1) {
            this.setBlockAndMetadata(world, i1, 11, 3, this.roofStairBlock, 3);
         }

         if (i1 <= -2 || i1 >= 2) {
            this.setBlockAndMetadata(world, i1, 10, 4, this.roofStairBlock, 3);
         }

         if (i1 <= -3 || i1 >= 3) {
            this.setBlockAndMetadata(world, i1, 9, 5, this.roofStairBlock, 3);
         }
      }

      for(i1 = 3; i1 <= 9; ++i1) {
         if (i1 >= 6) {
            this.setBlockAndMetadata(world, -3, 9, i1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 3, 9, i1, this.roofStairBlock, 0);
         }

         if (i1 >= 5) {
            this.setBlockAndMetadata(world, -2, 10, i1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 10, i1, this.roofStairBlock, 0);
         }

         if (i1 >= 4) {
            this.setBlockAndMetadata(world, -1, 11, i1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 1, 11, i1, this.roofStairBlock, 0);
         }

         this.setBlockAndMetadata(world, 0, 11, i1, this.roofBlock, this.roofMeta);
      }

      var15 = new int[]{-7, 2};
      k1 = var15.length;

      for(chestDir = 0; chestDir < k1; ++chestDir) {
         i1 = var15[chestDir];

         for(k1 = 0; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, i1, 9, k1, this.brickBlock, this.brickMeta);
         }

         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 10, k1, this.brickBlock, this.brickMeta);
         }
      }

      var15 = new int[]{-8, 3};
      k1 = var15.length;

      for(chestDir = 0; chestDir < k1; ++chestDir) {
         i1 = var15[chestDir];
         this.setBlockAndMetadata(world, i1, 9, 0, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 10, 1, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 10, 3, this.roofStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 9, 4, this.roofStairBlock, 6);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 9, 8, this.brickBlock, this.brickMeta);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 10, 8, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, -2, 9, 9, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -1, 10, 9, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 10, 9, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 2, 9, 9, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 0, 12, 2, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 0, 13, 2, Blocks.field_150480_ab, 0);
      this.setBlockAndMetadata(world, 0, 11, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 12, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -1, 12, 2, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 1, 12, 2, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 12, 3, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 13, 1, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, -1, 13, 2, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 1, 13, 2, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 0, 13, 3, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 0, 14, 1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -1, 14, 2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 1, 14, 2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 0, 14, 3, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 14, 2, this.roofBlock, this.roofMeta);

      for(i1 = 1; i1 <= 7; ++i1) {
         this.setBlockAndMetadata(world, -5, i1, 2, this.woodBeamBlock, this.woodBeamMeta);
      }

      this.setBlockAndMetadata(world, -4, 3, 1, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -6, 7, 2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -3, 7, 2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -5, 1, 1, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, -6, 1, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -6, 1, 2, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, -6, 2, 2, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -6, 2, 3, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -5, 2, 3, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, -5, 3, 3, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, -4, 3, 3, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -4, 3, 2, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, -4, 4, 2, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, -4, 4, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -5, 4, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -5, 5, 1, this.fenceBlock, this.fenceMeta);

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -3, 4, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
         this.setBlockAndMetadata(world, -2, 5, i1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -2, 6, i1, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, -2, 5, 2, this.doorBlock, 2);
      this.setBlockAndMetadata(world, -2, 6, 2, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 7, 1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -1, 7, 3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 1, 7, 3, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -1, 7, 5, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 0, 5, 1, LOTRMod.strawBed, 1);
      this.setBlockAndMetadata(world, 1, 5, 1, LOTRMod.strawBed, 9);
      this.setBlockAndMetadata(world, 1, 5, 2, this.woodBeamBlock, this.woodBeamMeta);
      this.placeMug(world, random, 1, 6, 2, 1, LOTRFoods.DALE_DRINK);
      this.placeChest(world, random, 1, 5, 3, 5, LOTRChestContents.DALE_HOUSE);
      this.spawnItemFrame(world, 2, 7, 1, 3, new ItemStack(Items.field_151113_aN));
      this.setBlockAndMetadata(world, 1, 5, 4, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 1, 6, 4, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 1, 7, 4, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 7, 4, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, -1, 7, 4, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 5, 5, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 1, 5, 6, Blocks.field_150460_al, 5);
      this.setBlockAndMetadata(world, 1, 5, 7, this.brickBlock, this.brickMeta);
      this.placePlateWithCertainty(world, random, 1, 6, 7, this.plateBlock, LOTRFoods.DALE);
      this.setBlockAndMetadata(world, 0, 5, 7, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, -1, 5, 7, LOTRMod.daleTable, 0);
      this.setBlockAndMetadata(world, -1, 7, 7, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 0, 7, 7, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 1, 7, 7, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 1, 7, 6, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 1, 7, 5, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, -3, 10, 2, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, 0, 10, 5, LOTRMod.chandelier, 0);
      if (random.nextInt(3) == 0) {
         i1 = MathHelper.func_76136_a(random, -6, 1);
         k1 = MathHelper.func_76136_a(random, 0, 4);
         chestDir = Direction.field_71582_c[random.nextInt(4)];
         this.placeChest(world, random, i1, 9, k1, chestDir, LOTRChestContents.DALE_HOUSE_TREASURE);
      }

      LOTREntityDaleMan daleMan = new LOTREntityDaleMan(world);
      this.spawnNPCAndSetHome(daleMan, world, -1, 5, 2, 16);
      return true;
   }
}
