package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDorwinionElf;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenDorwinionElfHouse extends LOTRWorldGenDorwinionHouse {
   private Block grapevineBlock;
   private Item wineItem;
   private Item grapeItem;

   public LOTRWorldGenDorwinionElfHouse(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      if (random.nextBoolean()) {
         this.grapevineBlock = LOTRMod.grapevineRed;
         this.wineItem = LOTRMod.mugRedWine;
         this.grapeItem = LOTRMod.grapeRed;
      } else {
         this.grapevineBlock = LOTRMod.grapevineWhite;
         this.wineItem = LOTRMod.mugWhiteWine;
         this.grapeItem = LOTRMod.grapeWhite;
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      int i1;
      int j1;
      if (this.restrictions) {
         for(int i1 = -4; i1 <= 8; ++i1) {
            for(i1 = -1; i1 <= 20; ++i1) {
               j1 = this.getTopBlock(world, i1, i1) - 1;
               Block block = this.getBlock(world, i1, j1, i1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }
            }
         }
      }

      boolean generateBackGate = true;

      int j1;
      for(i1 = 1; i1 <= 3; ++i1) {
         int k1 = 20;
         j1 = this.getTopBlock(world, i1, k1) - 1;
         if (j1 != 0) {
            generateBackGate = false;
         }
      }

      for(i1 = -4; i1 <= 8; ++i1) {
         for(j1 = 0; j1 <= 20; ++j1) {
            for(j1 = 1; j1 <= 6; ++j1) {
               this.setAir(world, i1, j1, j1);
            }

            this.setBlockAndMetadata(world, i1, 0, j1, Blocks.field_150349_c, 0);

            for(j1 = -1; !this.isOpaque(world, i1, j1, j1) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, j1, Blocks.field_150346_d, 0);
               this.setGrassToDirt(world, i1, j1 - 1, j1);
            }
         }
      }

      for(i1 = -3; i1 <= 7; ++i1) {
         for(j1 = 0; j1 <= 8; ++j1) {
            if (i1 >= 3 && j1 <= 2) {
               if (random.nextInt(3) == 0) {
                  this.plantFlower(world, random, i1, 1, j1);
               }
            } else if (j1 == 0 && (i1 == -3 || i1 == 2) || j1 == 3 && (i1 == 2 || i1 == 7) || j1 == 8 && (i1 == -3 || i1 == 7)) {
               for(j1 = 0; j1 <= 4; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, j1, this.woodBeamBlock, this.woodBeamMeta);
               }
            } else if (i1 != -3 && (i1 != 2 || j1 > 3) && i1 != 7 && j1 != 0 && (j1 != 3 || i1 < 2) && j1 != 8) {
               this.setBlockAndMetadata(world, i1, 0, j1, this.floorBlock, this.floorMeta);
            } else {
               for(j1 = 0; j1 <= 1; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, j1, this.wallBlock, this.wallMeta);
               }

               for(j1 = 2; j1 <= 4; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, j1, this.brickBlock, this.brickMeta);
               }
            }
         }
      }

      for(i1 = 1; i1 <= 7; ++i1) {
         j1 = IntMath.mod(i1, 3);
         if (j1 == 1) {
            this.setBlockAndMetadata(world, -4, 1, i1, this.brickStairBlock, 1);
            this.setGrassToDirt(world, -4, 0, i1);
         } else if (j1 == 2) {
            this.setAir(world, -3, 2, i1);
            this.setBlockAndMetadata(world, -3, 3, i1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, -4, 1, i1, this.leafBlock, this.leafMeta);
         } else if (j1 == 0) {
            this.setAir(world, -3, 2, i1);
            this.setBlockAndMetadata(world, -3, 3, i1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, -4, 1, i1, this.leafBlock, this.leafMeta);
         }
      }

      int[] var16 = new int[]{0, 8};
      j1 = var16.length;

      int i1;
      for(j1 = 0; j1 < j1; ++j1) {
         i1 = var16[j1];
         this.setAir(world, -1, 2, i1);
         this.setAir(world, 0, 2, i1);
         this.setBlockAndMetadata(world, -1, 3, i1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 0, 3, i1, this.brickStairBlock, 5);
      }

      var16 = new int[]{3, 8};
      j1 = var16.length;

      for(j1 = 0; j1 < j1; ++j1) {
         i1 = var16[j1];
         this.setAir(world, 4, 2, i1);
         this.setAir(world, 5, 2, i1);
         this.setBlockAndMetadata(world, 4, 3, i1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 5, 3, i1, this.brickStairBlock, 5);
      }

      this.setBlockAndMetadata(world, 3, 1, 2, this.brickStairBlock, 2);
      this.setGrassToDirt(world, 3, 0, 2);
      this.setBlockAndMetadata(world, 4, 1, 2, this.leafBlock, this.leafMeta);
      this.setBlockAndMetadata(world, 5, 1, 2, this.leafBlock, this.leafMeta);
      this.setBlockAndMetadata(world, 6, 1, 2, this.brickStairBlock, 2);
      this.setGrassToDirt(world, 6, 0, 2);
      this.setBlockAndMetadata(world, 8, 1, 4, this.brickStairBlock, 0);
      this.setGrassToDirt(world, 8, 0, 4);
      this.setBlockAndMetadata(world, 8, 1, 5, this.leafBlock, this.leafMeta);
      this.setBlockAndMetadata(world, 8, 1, 6, this.leafBlock, this.leafMeta);
      this.setBlockAndMetadata(world, 8, 1, 7, this.brickStairBlock, 0);
      this.setGrassToDirt(world, 8, 0, 7);
      this.setAir(world, 7, 2, 5);
      this.setAir(world, 7, 2, 6);
      this.setBlockAndMetadata(world, 7, 3, 5, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, 7, 3, 6, this.brickStairBlock, 6);
      var16 = new int[]{-1, 0};
      j1 = var16.length;

      for(j1 = 0; j1 < j1; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, i1, 0, 0, this.floorBlock, this.floorMeta);
         this.setAir(world, i1, 1, 0);
      }

      for(i1 = -3; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -1, this.brickStairBlock, 6);
      }

      for(i1 = -1; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, 3, 4, i1, this.brickStairBlock, 4);
         if (IntMath.mod(i1, 2) == 1) {
            this.setBlockAndMetadata(world, 3, 5, i1, this.brickSlabBlock, this.brickSlabMeta);
         }
      }

      for(i1 = 4; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, 2, this.brickStairBlock, 6);
         if (IntMath.mod(i1, 2) == 0) {
            this.setBlockAndMetadata(world, i1, 5, 2, this.brickSlabBlock, this.brickSlabMeta);
         }
      }

      for(i1 = 3; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, 8, 4, i1, this.brickStairBlock, 4);
      }

      for(i1 = 8; i1 >= -4; --i1) {
         this.setBlockAndMetadata(world, i1, 4, 9, this.brickStairBlock, 7);
         if (IntMath.mod(i1, 2) == 0) {
            this.setBlockAndMetadata(world, i1, 5, 9, this.brickSlabBlock, this.brickSlabMeta);
         }
      }

      for(i1 = 8; i1 >= -1; --i1) {
         this.setBlockAndMetadata(world, -4, 4, i1, this.brickStairBlock, 5);
         if (IntMath.mod(i1, 2) == 1) {
            this.setBlockAndMetadata(world, -4, 5, i1, this.brickSlabBlock, this.brickSlabMeta);
         }
      }

      for(i1 = 1; i1 <= 7; ++i1) {
         this.setBlockAndMetadata(world, -2, 4, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
         if (i1 <= 3) {
            this.setBlockAndMetadata(world, 1, 4, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
         }

         if (i1 >= 4) {
            this.setBlockAndMetadata(world, 2, 4, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
         }
      }

      for(i1 = -2; i1 <= 6; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, 7, this.plankSlabBlock, this.plankSlabMeta | 8);
         if (i1 <= 1) {
            this.setBlockAndMetadata(world, i1, 4, 3, this.plankSlabBlock, this.plankSlabMeta | 8);
         }

         if (i1 >= 2) {
            this.setBlockAndMetadata(world, i1, 4, 4, this.plankSlabBlock, this.plankSlabMeta | 8);
         }
      }

      for(i1 = 1; i1 <= 6; ++i1) {
         this.setBlockAndMetadata(world, -2, 5, i1, this.plankStairBlock, 4);
         if (i1 <= 5) {
            this.setBlockAndMetadata(world, -1, 6, i1, this.plankStairBlock, 4);
         }

         if (i1 <= 4) {
            this.setBlockAndMetadata(world, 0, 6, i1, this.plankStairBlock, 5);
         }

         if (i1 <= 3) {
            this.setBlockAndMetadata(world, 1, 5, i1, this.plankStairBlock, 5);
         }
      }

      for(i1 = -2; i1 <= 6; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, 7, this.plankStairBlock, 6);
         if (i1 >= -1) {
            this.setBlockAndMetadata(world, i1, 6, 6, this.plankStairBlock, 6);
         }

         if (i1 >= 0) {
            this.setBlockAndMetadata(world, i1, 6, 5, this.plankStairBlock, 7);
         }

         if (i1 >= 1) {
            this.setBlockAndMetadata(world, i1, 5, 4, this.plankStairBlock, 7);
         }
      }

      this.setBlockAndMetadata(world, -2, 5, 0, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -1, 5, 0, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, -1, 6, 0, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 6, 0, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 5, 0, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, 1, 5, 0, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 7, 5, 4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 7, 5, 5, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, 7, 6, 5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 7, 6, 6, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 7, 5, 6, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, 7, 5, 7, this.plankBlock, this.plankMeta);

      for(i1 = -1; i1 <= 7; ++i1) {
         this.setBlockAndMetadata(world, -3, 5, i1, this.clayStairBlock, 1);
         if (i1 <= 6) {
            this.setBlockAndMetadata(world, -2, 6, i1, this.clayStairBlock, 1);
         }

         if (i1 <= 5) {
            this.setBlockAndMetadata(world, -1, 7, i1, this.clayStairBlock, 1);
         }

         if (i1 <= 4) {
            this.setBlockAndMetadata(world, 0, 7, i1, this.clayStairBlock, 0);
         }

         if (i1 <= 3) {
            this.setBlockAndMetadata(world, 1, 6, i1, this.clayStairBlock, 0);
         }

         if (i1 <= 2) {
            this.setBlockAndMetadata(world, 2, 5, i1, this.clayStairBlock, 0);
         }
      }

      for(i1 = -3; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, 8, this.clayStairBlock, 3);
         if (i1 >= -2) {
            this.setBlockAndMetadata(world, i1, 6, 7, this.clayStairBlock, 3);
         }

         if (i1 >= -1) {
            this.setBlockAndMetadata(world, i1, 7, 6, this.clayStairBlock, 3);
         }

         if (i1 >= 0) {
            this.setBlockAndMetadata(world, i1, 7, 5, this.clayStairBlock, 2);
         }

         if (i1 >= 1) {
            this.setBlockAndMetadata(world, i1, 6, 4, this.clayStairBlock, 2);
         }

         if (i1 >= 2) {
            this.setBlockAndMetadata(world, i1, 5, 3, this.clayStairBlock, 2);
         }
      }

      this.setBlockAndMetadata(world, -2, 5, -1, this.clayStairBlock, 4);
      this.setBlockAndMetadata(world, -1, 6, -1, this.clayStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 6, -1, this.clayStairBlock, 5);
      this.setBlockAndMetadata(world, 1, 5, -1, this.clayStairBlock, 5);
      this.setBlockAndMetadata(world, 8, 5, 4, this.clayStairBlock, 7);
      this.setBlockAndMetadata(world, 8, 6, 5, this.clayStairBlock, 7);
      this.setBlockAndMetadata(world, 8, 6, 6, this.clayStairBlock, 6);
      this.setBlockAndMetadata(world, 8, 5, 7, this.clayStairBlock, 6);
      this.setBlockAndMetadata(world, -2, 3, 1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 3, 4, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -2, 3, 7, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 6, 3, 7, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 6, 3, 4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 1, 3, 1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 1, 4, Blocks.field_150462_ai, 0);
      this.placeChest(world, random, -2, 1, 5, 5, LOTRChestContents.DORWINION_HOUSE);
      this.placeChest(world, random, -2, 1, 6, 5, LOTRChestContents.DORWINION_HOUSE);
      this.setBlockAndMetadata(world, -2, 1, 7, LOTRMod.dorwinionTable, 0);
      this.setBlockAndMetadata(world, -1, 1, 6, Blocks.field_150324_C, 0);
      this.setBlockAndMetadata(world, -1, 1, 7, Blocks.field_150324_C, 8);
      this.setBlockAndMetadata(world, 2, 1, 4, Blocks.field_150460_al, 3);
      this.setBlockAndMetadata(world, 3, 1, 4, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, 4, 1, 4, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 5, 1, 4, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 6, 1, 4, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 6, 1, 5, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 6, 1, 6, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 6, 1, 7, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 5, 1, 7, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 4, 1, 7, this.plankStairBlock, 4);
      var16 = new int[]{4, 7};
      j1 = var16.length;

      for(j1 = 0; j1 < j1; ++j1) {
         i1 = var16[j1];

         for(int i1 = 4; i1 <= 5; ++i1) {
            this.placePlate(world, random, i1, 2, i1, this.plateBlock, LOTRFoods.DORWINION);
         }

         this.placeBarrel(world, random, 6, 2, i1, 5, new ItemStack(this.wineItem));
      }

      this.placeMug(world, random, 6, 2, 5, 1, new ItemStack(this.wineItem), LOTRFoods.DORWINION_DRINK);
      this.placeMug(world, random, 6, 2, 6, 1, new ItemStack(this.wineItem), LOTRFoods.DORWINION_DRINK);
      this.setBlockAndMetadata(world, 2, 0, 8, this.floorBlock, this.floorMeta);
      this.setBlockAndMetadata(world, 2, 1, 8, this.doorBlock, 3);
      this.setBlockAndMetadata(world, 2, 2, 8, this.doorBlock, 8);
      this.spawnItemFrame(world, 2, 3, 8, 2, new ItemStack(this.grapeItem));
      this.setBlockAndMetadata(world, 2, 3, 9, Blocks.field_150478_aa, 3);

      for(i1 = -3; i1 <= 7; ++i1) {
         for(j1 = 9; j1 <= 19; ++j1) {
            if (i1 != -3 && i1 != 7 && j1 != 19) {
               this.setBlockAndMetadata(world, i1, 0, j1, LOTRMod.dirtPath, 0);
               if (IntMath.mod(i1, 2) == 1) {
                  if (j1 == 14) {
                     this.setBlockAndMetadata(world, i1, 0, j1, Blocks.field_150355_j, 0);
                     this.setBlockAndMetadata(world, i1, 1, j1, this.fenceBlock, this.fenceMeta);
                     this.setBlockAndMetadata(world, i1, 2, j1, Blocks.field_150478_aa, 5);
                  } else if (j1 >= 11 && j1 <= 17) {
                     this.setBlockAndMetadata(world, i1, 0, j1, Blocks.field_150458_ak, 7);
                     this.setBlockAndMetadata(world, i1, 1, j1, this.grapevineBlock, 7);
                     this.setBlockAndMetadata(world, i1, 2, j1, this.grapevineBlock, 7);
                  }
               }
            } else {
               this.setGrassToDirt(world, i1, 0, j1);
               this.setBlockAndMetadata(world, i1, 1, j1, this.wallBlock, this.wallMeta);
               this.setBlockAndMetadata(world, i1, 2, j1, this.brickBlock, this.brickMeta);
               if (IntMath.mod(i1 + j1, 2) == 0) {
                  this.setBlockAndMetadata(world, i1, 3, j1, this.brickSlabBlock, this.brickSlabMeta);
               }
            }
         }
      }

      for(i1 = 0; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, 19, this.brickBlock, this.brickMeta);
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, 19, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, 0, 4, 19, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 4, 4, 19, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 1, 5, 19, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, 3, 5, 19, this.brickSlabBlock, this.brickSlabMeta);
      var16 = new int[]{-3, 4};
      j1 = var16.length;

      for(j1 = 0; j1 < j1; ++j1) {
         i1 = var16[j1];
         this.setGrassToDirt(world, i1, 0, 20);
         this.setBlockAndMetadata(world, i1, 1, 20, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, i1 + 1, 1, 20, this.leafBlock, this.leafMeta);
         this.setBlockAndMetadata(world, i1 + 2, 1, 20, this.leafBlock, this.leafMeta);
         this.setGrassToDirt(world, i1 + 3, 0, 20);
         this.setBlockAndMetadata(world, i1 + 3, 1, 20, this.brickStairBlock, 3);
      }

      if (generateBackGate) {
         for(i1 = 1; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, 19, LOTRMod.dirtPath, 0);

            for(j1 = 1; j1 <= 3; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, 19, LOTRMod.gateWooden, 2);
            }
         }
      } else {
         for(i1 = 1; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 1, 20, this.leafBlock, this.leafMeta);
         }
      }

      LOTREntityDorwinionElf dorwinionElf = new LOTREntityDorwinionElf(world);
      this.spawnNPCAndSetHome(dorwinionElf, world, 0, 1, 5, 16);
      return true;
   }
}
