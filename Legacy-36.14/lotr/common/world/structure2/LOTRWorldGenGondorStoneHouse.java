package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGondorStoneHouse extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorStoneHouse(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      if (random.nextBoolean()) {
         this.doorBlock = LOTRMod.doorLebethron;
      }

      this.bedBlock = Blocks.field_150324_C;
      if (random.nextBoolean()) {
         this.plateBlock = LOTRMod.plateBlock;
      } else {
         this.plateBlock = LOTRMod.ceramicPlateBlock;
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 8);
      this.setupRandomBlocks(random);
      int k1;
      int l;
      int i2;
      int k1;
      int j1;
      if (this.restrictions) {
         k1 = 0;
         l = 0;

         for(i2 = -5; i2 <= 5; ++i2) {
            for(k1 = -7; k1 <= 6; ++k1) {
               j1 = this.getTopBlock(world, i2, k1) - 1;
               if (!this.isSurface(world, i2, j1, k1)) {
                  return false;
               }

               if (j1 < k1) {
                  k1 = j1;
               }

               if (j1 > l) {
                  l = j1;
               }

               if (l - k1 > 5) {
                  return false;
               }
            }
         }
      }

      for(k1 = -5; k1 <= 4; ++k1) {
         for(l = -7; l <= 5; ++l) {
            i2 = Math.abs(k1);
            k1 = Math.abs(l);
            if (k1 == -5) {
               for(j1 = 1; j1 <= 8; ++j1) {
                  this.setAir(world, k1, j1, l);
               }

               this.setBlockAndMetadata(world, k1, 0, l, Blocks.field_150349_c, 0);

               for(j1 = -1; !this.isOpaque(world, k1, j1, l) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, k1, j1, l, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, k1, j1 - 1, l);
               }
            } else {
               for(j1 = 0; (j1 == 0 || !this.isOpaque(world, k1, j1, l)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, k1, j1, l, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, k1, j1 - 1, l);
               }

               if (l >= -4) {
                  if ((l == -4 || l == 5) && i2 == 4) {
                     for(j1 = 1; j1 <= 7; ++j1) {
                        this.setBlockAndMetadata(world, k1, j1, l, this.pillarBlock, this.pillarMeta);
                     }
                  } else if (l != -4 && l != 5 && i2 != 4) {
                     if (l >= -3 && l <= 4 && i2 <= 3) {
                        for(j1 = 1; j1 <= 3; ++j1) {
                           this.setAir(world, k1, j1, l);
                        }

                        this.setBlockAndMetadata(world, k1, 4, l, this.plankBlock, this.plankMeta);

                        for(j1 = 5; j1 <= 9; ++j1) {
                           this.setAir(world, k1, j1, l);
                        }
                     }
                  } else {
                     for(j1 = 1; j1 <= 7; ++j1) {
                        this.setBlockAndMetadata(world, k1, j1, l, this.brickBlock, this.brickMeta);
                     }
                  }
               }

               if (l <= -5) {
                  if (l == -7) {
                     if (i2 != 4 && i2 != 2) {
                        for(j1 = 1; j1 <= 3; ++j1) {
                           this.setAir(world, k1, j1, l);
                        }
                     } else {
                        for(j1 = 1; j1 <= 3; ++j1) {
                           this.setBlockAndMetadata(world, k1, j1, l, this.pillarBlock, this.pillarMeta);
                        }
                     }
                  } else if (i2 == 4) {
                     this.setBlockAndMetadata(world, k1, 1, l, this.brickBlock, this.brickMeta);
                     this.placeFlowerPot(world, k1, 2, l, this.getRandomFlower(world, random));
                     this.setBlockAndMetadata(world, k1, 3, l, this.fenceBlock, this.fenceMeta);
                  } else {
                     this.setBlockAndMetadata(world, k1, 0, l, this.plankBlock, this.plankMeta);

                     for(j1 = 1; j1 <= 3; ++j1) {
                        this.setAir(world, k1, j1, l);
                     }
                  }
               }
            }
         }
      }

      for(k1 = -5; k1 <= 5; ++k1) {
         l = Math.abs(k1);

         for(i2 = 0; i2 <= 2; ++i2) {
            k1 = 8 + i2;
            this.setBlockAndMetadata(world, k1, k1, -5 + i2, this.brick2StairBlock, 2);
            this.setBlockAndMetadata(world, k1, k1, -4 + i2, this.brick2Block, this.brick2Meta);
            this.setBlockAndMetadata(world, k1, k1, -3 + i2, this.brick2StairBlock, 7);
            this.setBlockAndMetadata(world, k1, k1, 6 - i2, this.brick2StairBlock, 3);
            this.setBlockAndMetadata(world, k1, k1, 5 - i2, this.brick2Block, this.brick2Meta);
            this.setBlockAndMetadata(world, k1, k1, 4 - i2, this.brick2StairBlock, 6);
            if (l == 4) {
               for(j1 = 8; j1 <= k1 - 1; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, -5 + i2, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, k1, j1, 6 - i2, this.brickBlock, this.brickMeta);
               }
            }
         }

         for(i2 = -2; i2 <= 3; ++i2) {
            int j1 = 10;
            this.setBlockAndMetadata(world, k1, j1, i2, this.brick2Block, this.brick2Meta);
            if (l == 4) {
               for(j1 = 8; j1 <= j1 - 1; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, i2, this.brickBlock, this.brickMeta);
               }
            }
         }
      }

      for(k1 = -5; k1 <= -3; ++k1) {
         for(l = -1; l <= 2; ++l) {
            for(i2 = 1; i2 <= 11; ++i2) {
               this.setBlockAndMetadata(world, k1, i2, l, this.brickBlock, this.brickMeta);
            }

            this.setGrassToDirt(world, k1, 0, l);
         }
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, k1, 4, -7, this.brick2SlabBlock, this.brick2SlabMeta);
         this.setBlockAndMetadata(world, k1, 4, -6, this.brick2Block, this.brick2Meta);
         this.setBlockAndMetadata(world, k1, 4, -5, this.brick2Block, this.brick2Meta);
         this.setBlockAndMetadata(world, k1, 5, -5, this.brick2SlabBlock, this.brick2SlabMeta);
      }

      this.setBlockAndMetadata(world, 0, 3, -6, LOTRMod.chandelier, 2);
      this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
      this.setBlockAndMetadata(world, -2, 2, -3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 2, -3, Blocks.field_150478_aa, 3);

      for(k1 = -1; k1 <= 1; ++k1) {
         for(l = -1; l <= 2; ++l) {
            this.setBlockAndMetadata(world, k1, 1, l, Blocks.field_150404_cg, 15);
         }
      }

      if (random.nextInt(4) == 0) {
         this.placeChest(world, random, 0, 0, 1, LOTRMod.chestLebethron, 2, LOTRChestContents.GONDOR_HOUSE_TREASURE);
      }

      this.setBlockAndMetadata(world, 3, 2, 4, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 0, 3, 1, LOTRMod.chandelier, 2);

      for(k1 = 0; k1 <= 1; ++k1) {
         this.setBlockAndMetadata(world, -3, 1, k1, Blocks.field_150411_aY, 0);
         this.setBlockAndMetadata(world, -3, 2, k1, Blocks.field_150460_al, 4);
         this.setBlockAndMetadata(world, -4, 0, k1, LOTRMod.hearth, 0);
         this.setBlockAndMetadata(world, -4, 1, k1, Blocks.field_150480_ab, 0);

         for(l = 2; l <= 10; ++l) {
            this.setAir(world, -4, l, k1);
         }
      }

      for(k1 = -3; k1 <= -2; ++k1) {
         this.setBlockAndMetadata(world, -3, 1, k1, this.plankBlock, this.plankMeta);
         this.placeMug(world, random, -3, 2, k1, 3, LOTRFoods.GONDOR_DRINK);
         this.setBlockAndMetadata(world, -3, 3, k1, this.plankStairBlock, 4);
      }

      for(k1 = 3; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, -3, 3, k1, this.plankStairBlock, 4);
      }

      this.setBlockAndMetadata(world, -3, 1, 3, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, -3, 1, 4, this.plankBlock, this.plankMeta);
      this.placePlateWithCertainty(world, random, -3, 2, 4, this.plateBlock, LOTRFoods.GONDOR);
      this.setBlockAndMetadata(world, -2, 1, 4, Blocks.field_150462_ai, 0);

      for(k1 = 0; k1 <= 3; ++k1) {
         this.setAir(world, 3, 4, k1);
      }

      for(k1 = 0; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, 3, 1 + k1, 2 - k1, this.plankStairBlock, 3);
      }

      this.setBlockAndMetadata(world, 3, 1, 1, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 3, 1, 0, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 3, 2, 0, this.plankStairBlock, 6);
      this.placeChest(world, random, 3, 1, -1, 5, LOTRChestContents.GONDOR_HOUSE);
      this.setBlockAndMetadata(world, 3, 1, -2, this.tableBlock, 0);
      this.setBlockAndMetadata(world, 3, 1, -3, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 3, 2, -3, this.fenceBlock, this.fenceMeta);

      for(k1 = -3; k1 <= -1; ++k1) {
         this.setBlockAndMetadata(world, 3, 3, k1, this.plankBlock, this.plankMeta);
      }

      this.spawnItemFrame(world, 3, 3, -1, 3, new ItemStack(Items.field_151113_aN));

      for(k1 = 1; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, 0, k1, 5, this.pillarBlock, this.pillarMeta);
      }

      this.placeWallBanner(world, 0, 3, 5, this.bannerType, 2);
      int[] var17 = new int[]{-3, 1};
      l = var17.length;

      for(i2 = 0; i2 < l; ++i2) {
         k1 = var17[i2];
         this.setBlockAndMetadata(world, k1, 2, 5, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, k1, 3, 5, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, k1 + 1, 2, 5, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, k1 + 1, 3, 5, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, k1 + 2, 2, 5, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, k1 + 2, 3, 5, this.brickStairBlock, 5);
      }

      var17 = new int[]{-4, 5};
      l = var17.length;

      for(i2 = 0; i2 < l; ++i2) {
         k1 = var17[i2];
         int[] var19 = new int[]{-3, 1};
         int var12 = var19.length;

         for(int var13 = 0; var13 < var12; ++var13) {
            int i1 = var19[var13];
            this.setBlockAndMetadata(world, i1, 6, k1, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, i1, 7, k1, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, i1 + 1, 6, k1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i1 + 1, 7, k1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i1 + 1, 8, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1 + 2, 6, k1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, i1 + 2, 7, k1, this.brickStairBlock, 5);
         }

         this.setBlockAndMetadata(world, 0, 6, k1, LOTRMod.brick, 5);
      }

      this.setBlockAndMetadata(world, -2, 5, 0, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -2, 6, 0, LOTRMod.plateBlock, 0);
      this.setBlockAndMetadata(world, -2, 5, 1, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, -2, 6, 1, 3, LOTRFoods.GONDOR_DRINK);
      var17 = new int[]{-1, 2};
      l = var17.length;

      for(i2 = 0; i2 < l; ++i2) {
         k1 = var17[i2];
         this.setBlockAndMetadata(world, -2, 5, k1, this.bedBlock, 11);
         this.setBlockAndMetadata(world, -1, 5, k1, this.bedBlock, 3);
         this.spawnItemFrame(world, -3, 7, k1, 1, this.getGondorFramedItem(random));
      }

      for(k1 = 0; k1 <= 1; ++k1) {
         for(l = 7; l <= 8; ++l) {
            this.setBlockAndMetadata(world, -3, l, k1, this.pillarBlock, this.pillarMeta);
         }
      }

      this.placeChest(world, random, -3, 5, -3, 4, LOTRChestContents.GONDOR_HOUSE);
      this.setBlockAndMetadata(world, -3, 5, -2, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -3, 5, 3, this.plankBlock, this.plankMeta);
      this.placeChest(world, random, -3, 5, 4, 4, LOTRChestContents.GONDOR_HOUSE);
      this.setBlockAndMetadata(world, 0, 9, -2, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, 0, 8, -2, LOTRMod.chandelier, 2);
      this.setBlockAndMetadata(world, 0, 9, 3, this.brick2Block, this.brick2Meta);
      this.setBlockAndMetadata(world, 0, 8, 3, LOTRMod.chandelier, 2);
      this.setBlockAndMetadata(world, -3, 7, -2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -3, 7, 3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, 7, -2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 3, 7, 3, Blocks.field_150478_aa, 1);

      for(k1 = -1; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, -5, 12, k1, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, -3, 12, k1, this.brickStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -4, 12, -1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -4, 12, 0, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -4, 12, 1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -4, 12, 2, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, -4, 13, 0, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, -4, 13, 1, this.brickWallBlock, this.brickWallMeta);
      int men = 2;

      for(l = 0; l < men; ++l) {
         LOTREntityGondorMan gondorMan = new LOTREntityGondorMan(world);
         this.spawnNPCAndSetHome(gondorMan, world, 0, 1, 0, 16);
      }

      return true;
   }
}
