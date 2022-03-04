package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityHighElf;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHighElfHouse extends LOTRWorldGenStructureBase2 {
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickSlabBlock;
   protected int brickSlabMeta;
   protected Block brickStairBlock;
   protected Block brickWallBlock;
   protected int brickWallMeta;
   protected Block brickCarvedBlock;
   protected int brickCarvedMeta;
   protected Block pillarBlock;
   protected int pillarMeta;
   protected Block stoneBlock;
   protected int stoneMeta;
   protected Block stoneSlabBlock;
   protected int stoneSlabMeta;
   protected Block roofBlock;
   protected int roofMeta;
   protected Block roofSlabBlock;
   protected int roofSlabMeta;
   protected Block roofStairBlock;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block plankSlabBlock;
   protected int plankSlabMeta;
   protected Block plankStairBlock;
   protected Block fenceBlock;
   protected int fenceMeta;
   protected Block trapdoorBlock;
   protected Block leafBlock;
   protected int leafMeta;
   protected Block tableBlock;
   protected Block bedBlock;
   protected Block barsBlock;
   protected Block torchBlock;
   protected Block chandelierBlock;
   protected int chandelierMeta;
   protected Block plateBlock;
   protected LOTRItemBanner.BannerType bannerType;
   protected LOTRChestContents chestContents;

   public LOTRWorldGenHighElfHouse(boolean flag) {
      super(flag);
   }

   protected LOTREntityElf createElf(World world) {
      return new LOTREntityHighElf(world);
   }

   protected void setupRandomBlocks(Random random) {
      this.brickBlock = LOTRMod.brick3;
      this.brickMeta = 2;
      this.brickSlabBlock = LOTRMod.slabSingle5;
      this.brickSlabMeta = 5;
      this.brickStairBlock = LOTRMod.stairsHighElvenBrick;
      this.brickWallBlock = LOTRMod.wall2;
      this.brickWallMeta = 11;
      this.brickCarvedBlock = LOTRMod.brick2;
      this.brickCarvedMeta = 13;
      this.pillarBlock = LOTRMod.pillar;
      this.pillarMeta = 10;
      this.stoneBlock = LOTRMod.smoothStoneV;
      this.stoneMeta = 0;
      this.stoneSlabBlock = Blocks.field_150333_U;
      this.stoneSlabMeta = 0;
      int randomRoof = random.nextInt(5);
      if (randomRoof == 0) {
         this.roofBlock = LOTRMod.clayTileDyed;
         this.roofMeta = 11;
         this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
         this.roofSlabMeta = 3;
         this.roofStairBlock = LOTRMod.stairsClayTileDyedBlue;
      } else if (randomRoof == 1) {
         this.roofBlock = LOTRMod.clayTileDyed;
         this.roofMeta = 3;
         this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
         this.roofSlabMeta = 3;
         this.roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
      } else if (randomRoof == 2) {
         this.roofBlock = LOTRMod.clayTileDyed;
         this.roofMeta = 9;
         this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
         this.roofSlabMeta = 1;
         this.roofStairBlock = LOTRMod.stairsClayTileDyedCyan;
      } else if (randomRoof == 3) {
         this.roofBlock = LOTRMod.clayTileDyed;
         this.roofMeta = 8;
         this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
         this.roofSlabMeta = 0;
         this.roofStairBlock = LOTRMod.stairsClayTileDyedLightGray;
      } else if (randomRoof == 4) {
         this.roofBlock = LOTRMod.clayTileDyed;
         this.roofMeta = 7;
         this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
         this.roofSlabMeta = 7;
         this.roofStairBlock = LOTRMod.stairsClayTileDyedGray;
      }

      int randomWood = random.nextInt(4);
      if (randomWood == 0) {
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 0;
         this.plankSlabBlock = Blocks.field_150376_bx;
         this.plankSlabMeta = 0;
         this.plankStairBlock = Blocks.field_150476_ad;
         this.fenceBlock = Blocks.field_150422_aJ;
         this.fenceMeta = 0;
         this.trapdoorBlock = Blocks.field_150415_aT;
      } else if (randomWood == 1) {
         this.plankBlock = Blocks.field_150344_f;
         this.plankMeta = 2;
         this.plankSlabBlock = Blocks.field_150376_bx;
         this.plankSlabMeta = 2;
         this.plankStairBlock = Blocks.field_150487_bG;
         this.fenceBlock = Blocks.field_150422_aJ;
         this.fenceMeta = 2;
         this.trapdoorBlock = LOTRMod.trapdoorBirch;
      } else if (randomWood == 2) {
         this.plankBlock = LOTRMod.planks;
         this.plankMeta = 9;
         this.plankSlabBlock = LOTRMod.woodSlabSingle2;
         this.plankSlabMeta = 1;
         this.plankStairBlock = LOTRMod.stairsBeech;
         this.fenceBlock = LOTRMod.fence;
         this.fenceMeta = 9;
         this.trapdoorBlock = LOTRMod.trapdoorBeech;
      } else if (randomWood == 3) {
         this.plankBlock = LOTRMod.planks;
         this.plankMeta = 4;
         this.plankSlabBlock = LOTRMod.woodSlabSingle;
         this.plankSlabMeta = 4;
         this.plankStairBlock = LOTRMod.stairsApple;
         this.fenceBlock = LOTRMod.fence;
         this.fenceMeta = 4;
         this.trapdoorBlock = LOTRMod.trapdoorApple;
      }

      int randomLeaf = random.nextInt(3);
      if (randomLeaf == 0) {
         this.leafBlock = Blocks.field_150362_t;
         this.leafMeta = 4;
      } else if (randomLeaf == 1) {
         this.leafBlock = Blocks.field_150362_t;
         this.leafMeta = 6;
      } else if (randomLeaf == 2) {
         this.leafBlock = LOTRMod.leaves2;
         this.leafMeta = 5;
      }

      this.tableBlock = LOTRMod.highElvenTable;
      this.bedBlock = LOTRMod.highElvenBed;
      this.barsBlock = LOTRMod.highElfWoodBars;
      this.torchBlock = LOTRMod.highElvenTorch;
      this.chandelierBlock = LOTRMod.chandelier;
      this.chandelierMeta = 10;
      this.plateBlock = LOTRMod.plateBlock;
      this.bannerType = LOTRItemBanner.BannerType.HIGH_ELF;
      this.chestContents = LOTRChestContents.HIGH_ELVEN_HALL;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      boolean leafy = random.nextBoolean();
      int i1;
      int k1;
      int j1;
      int k2;
      int i1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(j1 = -4; j1 <= 4; ++j1) {
            for(k2 = -1; k2 <= 14; ++k2) {
               i1 = this.getTopBlock(world, j1, k2) - 1;
               if (!this.isSurface(world, j1, i1, k2)) {
                  return false;
               }

               if (i1 < i1) {
                  i1 = i1;
               }

               if (i1 > k1) {
                  k1 = i1;
               }

               if (k1 - i1 > 6) {
                  return false;
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = 0; k1 <= 13; ++k1) {
            j1 = Math.abs(i1);

            for(k2 = 0; (k2 >= 0 || !this.isOpaque(world, i1, k2, k1)) && this.getY(k2) >= 0; --k2) {
               this.setBlockAndMetadata(world, i1, k2, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, k2 - 1, k1);
            }

            for(k2 = 1; k2 <= 12; ++k2) {
               this.setAir(world, i1, k2, k1);
            }

            if (j1 <= 2 && k1 >= 1 && k1 <= 12) {
               this.setBlockAndMetadata(world, i1, 0, k1, this.stoneBlock, this.stoneMeta);
            }

            if (j1 <= 2 && k1 == 0) {
               this.setBlockAndMetadata(world, i1, 0, k1, this.pillarBlock, this.pillarMeta);
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         k1 = Math.abs(i1);
         if (k1 % 2 == 1) {
            this.setBlockAndMetadata(world, i1, 1, 0, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, i1, 2, 0, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i1, 3, 0, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i1, 4, 0, this.pillarBlock, this.pillarMeta);
            if (k1 == 1) {
               this.setBlockAndMetadata(world, i1, 5, 0, this.pillarBlock, this.pillarMeta);
            }
         }

         if (k1 == 0) {
            this.setBlockAndMetadata(world, i1, 6, 0, this.pillarBlock, this.pillarMeta);

            for(j1 = 7; j1 <= 9; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, 0, this.brickWallBlock, this.brickWallMeta);
            }

            for(j1 = 10; j1 <= 11; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, 0, this.pillarBlock, this.pillarMeta);
            }
         } else if (k1 <= 2) {
            this.setBlockAndMetadata(world, i1, 6, 0, this.brickWallBlock, this.brickWallMeta);
         }
      }

      this.setBlockAndMetadata(world, -2, 5, 0, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 5, 0, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, 2, 5, 0, this.brickStairBlock, 5);
      int[] var14 = new int[]{-3, 3};
      k1 = var14.length;

      for(j1 = 0; j1 < k1; ++j1) {
         k2 = var14[j1];

         for(i1 = 1; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, k2, i1, 1, this.pillarBlock, this.pillarMeta);
         }

         this.setBlockAndMetadata(world, k2 + Integer.signum(k2), 4, 1, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, k2, 1, 2, this.brickStairBlock, k2 > 0 ? 4 : 5);
         this.setBlockAndMetadata(world, k2, 2, 2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, k2, 3, 2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, k2, 4, 2, this.plankStairBlock, k2 > 0 ? 0 : 1);
         this.setBlockAndMetadata(world, k2, 1, 3, this.brickStairBlock, k2 > 0 ? 0 : 1);
         this.setBlockAndMetadata(world, k2, 3, 3, this.barsBlock, 0);
         this.setBlockAndMetadata(world, k2, 4, 3, this.plankStairBlock, k2 > 0 ? 4 : 5);
         this.setBlockAndMetadata(world, k2, 1, 4, this.brickStairBlock, k2 > 0 ? 4 : 5);
         this.setBlockAndMetadata(world, k2, 2, 4, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, k2, 3, 4, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, k2, 4, 4, this.plankStairBlock, k2 > 0 ? 0 : 1);

         for(i1 = 1; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, k2, i1, 5, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, k2, i1, 8, this.pillarBlock, this.pillarMeta);
         }

         this.setBlockAndMetadata(world, k2, 4, 6, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, k2, 4, 7, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, k2 + Integer.signum(k2), 4, 5, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, k2 + Integer.signum(k2), 4, 8, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, k2, 1, 9, this.brickStairBlock, k2 > 0 ? 4 : 5);
         this.setBlockAndMetadata(world, k2, 2, 9, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, k2, 3, 9, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, k2, 4, 9, this.plankStairBlock, k2 > 0 ? 0 : 1);
         this.setBlockAndMetadata(world, k2, 1, 10, this.brickStairBlock, k2 > 0 ? 0 : 1);
         this.setBlockAndMetadata(world, k2, 3, 10, this.barsBlock, 0);
         this.setBlockAndMetadata(world, k2, 4, 10, this.plankStairBlock, k2 > 0 ? 4 : 5);
         this.setBlockAndMetadata(world, k2, 1, 11, this.brickStairBlock, k2 > 0 ? 4 : 5);
         this.setBlockAndMetadata(world, k2, 2, 11, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, k2, 3, 11, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, k2, 4, 11, this.plankStairBlock, k2 > 0 ? 0 : 1);

         for(i1 = 1; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, k2, i1, 12, this.pillarBlock, this.pillarMeta);
         }

         this.setBlockAndMetadata(world, k2 + Integer.signum(k2), 4, 12, this.brickWallBlock, this.brickWallMeta);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         k1 = Math.abs(i1);
         if (k1 != 0) {
            if (k1 == 1) {
               this.setBlockAndMetadata(world, i1, 1, 13, this.brickStairBlock, 7);
               this.setBlockAndMetadata(world, i1, 3, 13, this.barsBlock, 0);
               this.setBlockAndMetadata(world, i1, 4, 13, this.plankStairBlock, 7);
            } else if (k1 == 2) {
               this.setBlockAndMetadata(world, i1, 1, 13, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1, 2, 13, this.plankBlock, this.plankMeta);
               this.setBlockAndMetadata(world, i1, 3, 13, this.plankBlock, this.plankMeta);
               this.setBlockAndMetadata(world, i1, 4, 13, this.plankStairBlock, i1 > 0 ? 0 : 1);
            }
         } else {
            for(j1 = 1; j1 <= 6; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, 13, this.pillarBlock, this.pillarMeta);
            }

            for(j1 = 7; j1 <= 9; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, 13, this.brickWallBlock, this.brickWallMeta);
            }

            for(j1 = 10; j1 <= 11; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, 13, this.pillarBlock, this.pillarMeta);
            }
         }

         if (k1 >= 1 && k1 <= 2) {
            this.setBlockAndMetadata(world, i1, 5, 13, this.stoneSlabBlock, this.stoneSlabMeta | 8);
            this.setBlockAndMetadata(world, i1, 6, 13, this.brickWallBlock, this.brickWallMeta);
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = 1; k1 <= 12; ++k1) {
            this.setBlockAndMetadata(world, i1, 5, k1, this.stoneBlock, this.stoneMeta);
         }
      }

      for(i1 = 0; i1 <= 13; ++i1) {
         Block block = this.roofBlock;
         j1 = this.roofMeta;
         Block slabBlock = this.roofSlabBlock;
         i1 = this.roofSlabMeta;
         Block stairBlock = this.roofStairBlock;
         if (i1 == 1 || i1 == 12) {
            block = this.brickBlock;
            j1 = this.brickMeta;
            slabBlock = this.brickSlabBlock;
            i1 = this.brickSlabMeta;
            stairBlock = this.brickStairBlock;
         }

         this.setBlockAndMetadata(world, -4, 5, i1, stairBlock, 1);
         this.setBlockAndMetadata(world, -3, 5, i1, stairBlock, 4);
         this.setBlockAndMetadata(world, -3, 6, i1, block, j1);
         this.setBlockAndMetadata(world, -3, 7, i1, block, j1);
         this.setBlockAndMetadata(world, -3, 8, i1, stairBlock, 1);
         this.setBlockAndMetadata(world, -2, 8, i1, stairBlock, 4);
         this.setBlockAndMetadata(world, -2, 9, i1, block, j1);
         this.setBlockAndMetadata(world, -2, 10, i1, stairBlock, 1);
         this.setBlockAndMetadata(world, -1, 10, i1, stairBlock, 4);
         this.setBlockAndMetadata(world, 4, 5, i1, stairBlock, 0);
         this.setBlockAndMetadata(world, 3, 5, i1, stairBlock, 5);
         this.setBlockAndMetadata(world, 3, 6, i1, block, j1);
         this.setBlockAndMetadata(world, 3, 7, i1, block, j1);
         this.setBlockAndMetadata(world, 3, 8, i1, stairBlock, 0);
         this.setBlockAndMetadata(world, 2, 8, i1, stairBlock, 5);
         this.setBlockAndMetadata(world, 2, 9, i1, block, j1);
         this.setBlockAndMetadata(world, 2, 10, i1, stairBlock, 0);
         this.setBlockAndMetadata(world, 1, 10, i1, stairBlock, 5);
         if (i1 > 1 && i1 < 12) {
            if (i1 > 4 && i1 < 9) {
               if (i1 == 5) {
                  this.setBlockAndMetadata(world, -1, 11, i1, stairBlock, 3);
                  this.setBlockAndMetadata(world, 1, 11, i1, stairBlock, 3);
               } else if (i1 == 8) {
                  this.setBlockAndMetadata(world, -1, 11, i1, stairBlock, 2);
                  this.setBlockAndMetadata(world, 1, 11, i1, stairBlock, 2);
               } else {
                  this.setBlockAndMetadata(world, -1, 11, i1, slabBlock, i1);
                  this.setBlockAndMetadata(world, 1, 11, i1, slabBlock, i1);
               }
            } else {
               this.setBlockAndMetadata(world, -1, 11, i1, stairBlock, 1);
               this.setBlockAndMetadata(world, 1, 11, i1, stairBlock, 0);
            }
         } else {
            this.setBlockAndMetadata(world, -1, 11, i1, block, j1);
            this.setBlockAndMetadata(world, -1, 12, i1, stairBlock, 1);
            this.setBlockAndMetadata(world, 1, 11, i1, block, j1);
            this.setBlockAndMetadata(world, 1, 12, i1, stairBlock, 0);
         }
      }

      for(i1 = 0; i1 <= 13; ++i1) {
         this.setBlockAndMetadata(world, 0, 11, i1, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, 0, 12, -1, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, 0, 13, -1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 14, -1, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 0, 12, 0, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 13, 0, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 12, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 13, 1, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, 0, 12, 2, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 12, 3, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 12, 4, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, 0, 12, 5, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, 0, 12, 8, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, 0, 12, 9, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, 0, 12, 10, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 12, 11, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 12, 12, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 13, 12, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, 0, 12, 13, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 13, 13, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 0, 12, 14, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, 0, 13, 14, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 14, 14, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, -2, 4, 1, this.brickStairBlock, 4);

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -2, i1, 2, Blocks.field_150342_X, 0);
      }

      this.setBlockAndMetadata(world, -2, 1, 3, this.brickStairBlock, 4);
      this.placeFlowerPot(world, -2, 2, 3, this.getRandomFlower(world, random));
      this.setBlockAndMetadata(world, -2, 4, 3, this.stoneSlabBlock, this.stoneSlabMeta | 8);
      this.setBlockAndMetadata(world, -2, 1, 4, Blocks.field_150349_c, 0);
      this.setBlockAndMetadata(world, -1, 1, 4, this.trapdoorBlock, 6);
      this.setBlockAndMetadata(world, -2, 1, 5, this.trapdoorBlock, 5);
      this.setBlockAndMetadata(world, -2, 2, 4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -2, 3, 4, this.leafBlock, this.leafMeta);
      this.setBlockAndMetadata(world, -2, 4, 4, this.leafBlock, this.leafMeta);
      this.setBlockAndMetadata(world, 2, 4, 1, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, 2, 1, 2, this.brickStairBlock, 5);
      this.placeMug(world, random, 2, 2, 2, 1, LOTRFoods.ELF_DRINK);
      this.setBlockAndMetadata(world, 2, 1, 3, this.tableBlock, 0);
      this.setBlockAndMetadata(world, 2, 4, 3, this.stoneSlabBlock, this.stoneSlabMeta | 8);

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 2, i1, 4, Blocks.field_150342_X, 0);
      }

      for(i1 = -1; i1 <= 0; ++i1) {
         for(k1 = 2; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150404_cg, 3);
         }
      }

      var14 = new int[]{5, 8};
      k1 = var14.length;

      for(j1 = 0; j1 < k1; ++j1) {
         k2 = var14[j1];
         this.setBlockAndMetadata(world, -2, 4, k2, this.brickStairBlock, 4);

         for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, k2, this.brickSlabBlock, this.brickSlabMeta | 8);
         }

         this.setBlockAndMetadata(world, 2, 4, k2, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, -2, 3, k2, this.torchBlock, 2);
         this.setBlockAndMetadata(world, 2, 3, k2, this.torchBlock, 1);
      }

      for(i1 = 0; i1 <= 1; ++i1) {
         for(k1 = 7; k1 <= 8; ++k1) {
            this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150404_cg, 11);
         }
      }

      this.setBlockAndMetadata(world, -2, 4, 10, this.stoneSlabBlock, this.stoneSlabMeta | 8);
      this.setBlockAndMetadata(world, 2, 4, 10, this.stoneSlabBlock, this.stoneSlabMeta | 8);
      this.setBlockAndMetadata(world, -2, 4, 12, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 2, 4, 12, this.brickStairBlock, 5);
      this.spawnItemFrame(world, -3, 2, 9, 1, this.getElfFramedItem(random));
      this.spawnItemFrame(world, 3, 2, 9, 3, this.getElfFramedItem(random));
      this.spawnItemFrame(world, -3, 2, 11, 1, this.getElfFramedItem(random));
      this.spawnItemFrame(world, 3, 2, 11, 3, this.getElfFramedItem(random));
      if (leafy) {
         for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = 6; k1 <= 7; ++k1) {
               if (random.nextInt(3) != 0) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.leafBlock, this.leafMeta);
               }
            }
         }
      }

      for(i1 = 0; i1 <= 1; ++i1) {
         for(k1 = 9; k1 <= 11; ++k1) {
            this.setAir(world, i1, 5, k1);
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = 1; k1 <= 9; ++k1) {
            this.setBlockAndMetadata(world, i1, 10, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
         }

         for(k1 = 10; k1 <= 12; ++k1) {
            this.setBlockAndMetadata(world, i1, 9, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 10, k1, this.brickBlock, this.brickMeta);
         }
      }

      for(i1 = 9; i1 <= 12; ++i1) {
         for(k1 = 6; k1 <= 8; ++k1) {
            this.setBlockAndMetadata(world, 2, k1, i1, this.brickBlock, this.brickMeta);
         }
      }

      for(i1 = 11; i1 <= 12; ++i1) {
         for(k1 = 6; k1 <= 8; ++k1) {
            this.setBlockAndMetadata(world, -2, k1, i1, this.brickBlock, this.brickMeta);
         }
      }

      this.setBlockAndMetadata(world, -2, 6, 1, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, -2, 7, 1, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, -2, 8, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -1, 9, 1, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, -1, 10, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 2, 6, 1, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, 2, 7, 1, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 2, 8, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 1, 9, 1, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 1, 10, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 10, 1, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, -1, 10, 2, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 10, 2, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, 0, 10, 3, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 9, 3, this.chandelierBlock, this.chandelierMeta);
      this.setBlockAndMetadata(world, -2, 6, 4, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, -2, 7, 4, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, -2, 8, 4, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 2, 6, 4, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 2, 7, 4, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 2, 8, 4, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, 0, 10, 6, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 9, 6, this.chandelierBlock, this.chandelierMeta);
      this.setBlockAndMetadata(world, -1, 10, 7, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 10, 7, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, -1, 10, 8, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, 1, 10, 8, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, -1, 9, 9, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, -1, 10, 9, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 1, 9, 9, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, 1, 10, 9, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -1, 8, 12, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 8, 12, this.brickStairBlock, 5);

      for(i1 = 1; i1 <= 11; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, 10, this.pillarBlock, this.pillarMeta);
      }

      this.setBlockAndMetadata(world, 0, 8, 10, this.brickCarvedBlock, this.brickCarvedMeta);
      this.placeWallBanner(world, 0, 3, 10, this.bannerType, 2);
      this.setBlockAndMetadata(world, -1, 1, 9, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, -1, 1, 10, this.brickSlabBlock, this.brickSlabMeta | 8);
      this.setBlockAndMetadata(world, -1, 2, 11, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, 0, 2, 11, this.brickSlabBlock, this.brickSlabMeta | 8);
      this.setBlockAndMetadata(world, 1, 3, 11, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, 1, 3, 10, this.brickSlabBlock, this.brickSlabMeta | 8);
      this.setBlockAndMetadata(world, 1, 4, 9, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, 0, 4, 9, this.brickSlabBlock, this.brickSlabMeta | 8);
      this.setBlockAndMetadata(world, -1, 5, 9, this.brickSlabBlock, this.brickSlabMeta);

      for(i1 = 0; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 6, 8, this.brickWallBlock, this.brickWallMeta);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = 1; k1 <= 7; ++k1) {
            j1 = Math.abs(i1);
            k2 = Math.abs(k1 - 4);
            if (j1 == 0 && k2 == 0) {
               this.setBlockAndMetadata(world, i1, 6, k1, Blocks.field_150404_cg, 0);
            } else if ((j1 != 0 || k2 > 2) && (j1 != 1 || k2 != 0)) {
               if (j1 == 0 && k2 == 3 || j1 == 1 && k2 == 1) {
                  this.setBlockAndMetadata(world, i1, 6, k1, Blocks.field_150404_cg, 11);
               }
            } else {
               this.setBlockAndMetadata(world, i1, 6, k1, Blocks.field_150404_cg, 3);
            }
         }
      }

      var14 = new int[]{-2, 2};
      k1 = var14.length;

      for(j1 = 0; j1 < k1; ++j1) {
         k2 = var14[j1];
         this.setBlockAndMetadata(world, k2, 6, 3, this.bedBlock, 2);
         this.setBlockAndMetadata(world, k2, 6, 2, this.bedBlock, 10);
      }

      this.setBlockAndMetadata(world, -2, 6, 5, this.plankStairBlock, 4);
      this.placeMug(world, random, -2, 7, 5, 3, LOTRFoods.ELF_DRINK);
      this.setBlockAndMetadata(world, 2, 6, 5, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, 2, 6, 6, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 2, 6, 7, this.plankStairBlock, 6);
      this.placeChest(world, random, 2, 7, 5, 5, this.chestContents);
      this.placePlateWithCertainty(world, random, 2, 7, 6, this.plateBlock, LOTRFoods.ELF);
      this.placeBarrel(world, random, 2, 7, 7, 5, LOTRFoods.ELF_DRINK);
      if (leafy) {
         for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = 0; k1 <= 13; ++k1) {
               for(j1 = 5; j1 <= 12; ++j1) {
                  if ((Math.abs(i1) >= 3 || j1 >= 11) && random.nextInt(4) == 0 && this.isAir(world, i1, j1, k1) && (this.isSolidRoofBlock(world, i1, j1 - 1, k1) || this.isSolidRoofBlock(world, i1 - 1, j1, k1) || this.isSolidRoofBlock(world, i1 + 1, j1, k1))) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.leafBlock, this.leafMeta);
                  }
               }
            }
         }
      }

      i1 = 1 + random.nextInt(2);

      for(k1 = 0; k1 < i1; ++k1) {
         LOTREntityElf elf = this.createElf(world);
         this.spawnNPCAndSetHome(elf, world, 0, 1, 7, 16);
      }

      return true;
   }

   private boolean isSolidRoofBlock(World world, int i, int j, int k) {
      return this.getBlock(world, i, j, k).func_149688_o().func_76218_k();
   }

   protected ItemStack getElfFramedItem(Random random) {
      ItemStack[] items = new ItemStack[]{new ItemStack(LOTRMod.helmetHighElven), new ItemStack(LOTRMod.bodyHighElven), new ItemStack(LOTRMod.legsHighElven), new ItemStack(LOTRMod.bootsHighElven), new ItemStack(LOTRMod.daggerHighElven), new ItemStack(LOTRMod.swordHighElven), new ItemStack(LOTRMod.spearHighElven), new ItemStack(LOTRMod.longspearHighElven), new ItemStack(LOTRMod.highElvenBow), new ItemStack(Items.field_151032_g), new ItemStack(Items.field_151008_G), new ItemStack(LOTRMod.swanFeather), new ItemStack(LOTRMod.quenditeCrystal), new ItemStack(LOTRMod.goldRing), new ItemStack(LOTRMod.silverRing)};
      return items[random.nextInt(items.length)].func_77946_l();
   }
}
