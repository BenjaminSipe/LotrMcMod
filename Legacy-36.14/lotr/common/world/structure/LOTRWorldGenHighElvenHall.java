package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityHighElf;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHighElvenHall extends LOTRWorldGenStructureBase {
   private Block plankBlock;
   private int plankMeta;
   private Block slabBlock;
   private int slabMeta;
   private Block stairBlock;
   private Block roofBlock;
   private int roofMeta;
   private Block roofStairBlock;
   protected Block tableBlock;
   protected Block plateBlock;
   protected LOTRItemBanner.BannerType bannerType;
   protected LOTRChestContents chestContents;

   public LOTRWorldGenHighElvenHall(boolean flag) {
      super(flag);
      this.tableBlock = LOTRMod.highElvenTable;
      this.plateBlock = LOTRMod.plateBlock;
      this.bannerType = LOTRItemBanner.BannerType.HIGH_ELF;
      this.chestContents = LOTRChestContents.HIGH_ELVEN_HALL;
   }

   protected LOTREntityElf createElf(World world) {
      return new LOTREntityHighElf(world);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions && world.func_147439_a(i, j - 1, k) != Blocks.field_150349_c) {
         return false;
      } else {
         --j;
         int rotation = random.nextInt(4);
         if (!this.restrictions && this.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
         }

         switch(rotation) {
         case 0:
            i -= 8;
            ++k;
            break;
         case 1:
            i -= 16;
            k -= 8;
            break;
         case 2:
            i -= 7;
            k -= 16;
            break;
         case 3:
            ++i;
            k -= 7;
         }

         int minHeight;
         int randomRoof;
         int roofWidth;
         int roofX;
         int roofY;
         int roofZ;
         if (this.restrictions) {
            minHeight = j + 1;
            randomRoof = j + 1;

            for(roofWidth = i - 1; roofWidth <= i + 16; ++roofWidth) {
               for(roofX = k - 1; roofX <= k + 16; ++roofX) {
                  roofY = world.func_72825_h(roofWidth, roofX);
                  Block block = world.func_147439_a(roofWidth, roofY - 1, roofX);
                  if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150348_b) {
                     return false;
                  }

                  if (roofY > randomRoof) {
                     randomRoof = roofY;
                  }

                  if (roofY < minHeight) {
                     minHeight = roofY;
                  }
               }
            }

            if (Math.abs(randomRoof - minHeight) > 5) {
               return false;
            }

            roofWidth = j + 1;

            for(roofX = i - 1; roofX <= i + 16; ++roofX) {
               for(roofY = k - 1; roofY <= k + 16; ++roofY) {
                  if ((roofX == i - 1 || roofX == i + 16) && (roofY == k - 1 || roofY == k + 16)) {
                     roofZ = world.func_72825_h(roofX, roofY);
                     if (roofZ > roofWidth) {
                        roofWidth = roofZ;
                     }
                  }
               }
            }

            j = roofWidth - 1;
         }

         minHeight = random.nextInt(4);
         switch(minHeight) {
         case 0:
            this.plankBlock = Blocks.field_150344_f;
            this.plankMeta = 0;
            this.slabBlock = Blocks.field_150376_bx;
            this.slabMeta = 0;
            this.stairBlock = Blocks.field_150476_ad;
            break;
         case 1:
            this.plankBlock = Blocks.field_150344_f;
            this.plankMeta = 2;
            this.slabBlock = Blocks.field_150376_bx;
            this.slabMeta = 2;
            this.stairBlock = Blocks.field_150487_bG;
            break;
         case 2:
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 9;
            this.slabBlock = LOTRMod.woodSlabSingle2;
            this.slabMeta = 1;
            this.stairBlock = LOTRMod.stairsBeech;
            break;
         case 3:
            this.plankBlock = LOTRMod.planks;
            this.plankMeta = 4;
            this.slabBlock = LOTRMod.woodSlabSingle;
            this.slabMeta = 4;
            this.stairBlock = LOTRMod.stairsApple;
         }

         randomRoof = random.nextInt(5);
         if (randomRoof == 0) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 11;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedBlue;
         } else if (randomRoof == 1) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 3;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
         } else if (randomRoof == 2) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 9;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedCyan;
         } else if (randomRoof == 3) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 8;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedLightGray;
         } else if (randomRoof == 4) {
            this.roofBlock = LOTRMod.clayTileDyed;
            this.roofMeta = 7;
            this.roofStairBlock = LOTRMod.stairsClayTileDyedGray;
         }

         for(roofWidth = i; roofWidth <= i + 15; ++roofWidth) {
            for(roofX = k; roofX <= k + 15; ++roofX) {
               for(roofY = j; (roofY == j || !LOTRMod.isOpaque(world, roofWidth, roofY, roofX)) && roofY >= 0; --roofY) {
                  this.func_150516_a(world, roofWidth, roofY, roofX, LOTRMod.brick3, 2);
                  this.setGrassToDirt(world, roofWidth, roofY - 1, roofX);
               }

               for(roofY = j + 1; roofY <= j + 4; ++roofY) {
                  this.func_150516_a(world, roofWidth, roofY, roofX, Blocks.field_150350_a, 0);
               }

               if (roofWidth >= i + 2 && roofWidth <= i + 13 && roofX >= k + 2 && roofX <= k + 13) {
                  this.func_150516_a(world, roofWidth, j + 5, roofX, this.plankBlock, this.plankMeta);
               } else {
                  this.func_150516_a(world, roofWidth, j + 5, roofX, LOTRMod.brick3, 2);
               }

               for(roofY = j + 6; roofY <= j + 9; ++roofY) {
                  this.func_150516_a(world, roofWidth, roofY, roofX, Blocks.field_150350_a, 0);
               }
            }
         }

         for(roofWidth = i + 1; roofWidth <= i + 14; ++roofWidth) {
            this.func_150516_a(world, roofWidth, j + 6, k, LOTRMod.wall2, 11);
            this.func_150516_a(world, roofWidth, j + 6, k + 15, LOTRMod.wall2, 11);
         }

         for(roofWidth = k + 1; roofWidth <= k + 14; ++roofWidth) {
            this.func_150516_a(world, i, j + 6, roofWidth, LOTRMod.brick3, 2);
            this.func_150516_a(world, i, j + 7, roofWidth, LOTRMod.wall2, 11);
            this.func_150516_a(world, i + 15, j + 6, roofWidth, LOTRMod.wall2, 11);
         }

         for(roofWidth = j; roofWidth <= j + 5; roofWidth += 5) {
            for(roofX = k; roofX <= k + 15; roofX += 15) {
               for(roofY = i; roofY <= i + 15; roofY += 3) {
                  for(roofZ = roofWidth + 1; roofZ <= roofWidth + 4; ++roofZ) {
                     this.func_150516_a(world, roofY, roofZ, roofX, LOTRMod.pillar, 10);
                  }

                  this.func_150516_a(world, roofY, roofWidth + 5, roofX, LOTRMod.brick3, 2);
               }

               for(roofY = i + 1; roofY <= i + 13; roofY += 3) {
                  this.func_150516_a(world, roofY, roofWidth + 5, roofX, LOTRMod.stairsHighElvenBrick, 5);
                  this.func_150516_a(world, roofY + 1, roofWidth + 5, roofX, LOTRMod.stairsHighElvenBrick, 4);
               }
            }

            for(roofX = i; roofX <= i + 15; roofX += 15) {
               for(roofY = k + 3; roofY <= k + 12; roofY += 3) {
                  for(roofZ = roofWidth + 1; roofZ <= roofWidth + 4; ++roofZ) {
                     this.func_150516_a(world, roofX, roofZ, roofY, LOTRMod.pillar, 10);
                  }

                  this.func_150516_a(world, roofX, roofWidth + 5, roofY, LOTRMod.brick3, 2);
               }

               for(roofY = k + 1; roofY <= k + 13; roofY += 3) {
                  this.func_150516_a(world, roofX, roofWidth + 5, roofY, LOTRMod.stairsHighElvenBrick, 7);
                  this.func_150516_a(world, roofX, roofWidth + 5, roofY + 1, LOTRMod.stairsHighElvenBrick, 6);
               }
            }

            for(roofX = i; roofX <= i + 15; roofX += 3) {
               this.func_150516_a(world, roofX, roofWidth + 4, k + 1, LOTRMod.highElvenTorch, 3);
               this.func_150516_a(world, roofX, roofWidth + 4, k + 14, LOTRMod.highElvenTorch, 4);
            }

            for(roofX = k; roofX <= k + 15; roofX += 3) {
               this.func_150516_a(world, i + 1, roofWidth + 4, roofX, LOTRMod.highElvenTorch, 1);
               this.func_150516_a(world, i + 14, roofWidth + 4, roofX, LOTRMod.highElvenTorch, 2);
            }
         }

         roofWidth = 18;
         roofX = i - 1;
         roofY = j + 11;

         int j1;
         int k1;
         for(roofZ = k - 1; roofWidth > 2; ++roofZ) {
            for(j1 = roofX; j1 < roofX + roofWidth; ++j1) {
               this.func_150516_a(world, j1, roofY, roofZ, this.roofStairBlock, 2);
               this.func_150516_a(world, j1, roofY, roofZ + roofWidth - 1, this.roofStairBlock, 3);
            }

            for(j1 = roofZ; j1 < roofZ + roofWidth; ++j1) {
               this.func_150516_a(world, roofX, roofY, j1, this.roofStairBlock, 0);
               this.func_150516_a(world, roofX + roofWidth - 1, roofY, j1, this.roofStairBlock, 1);
            }

            for(j1 = roofX + 1; j1 < roofX + roofWidth - 2; ++j1) {
               for(k1 = roofZ + 1; k1 < roofZ + roofWidth - 2; ++k1) {
                  this.func_150516_a(world, j1, roofY, k1, Blocks.field_150350_a, 0);
               }
            }

            for(j1 = roofX + 1; j1 < roofX + roofWidth - 1; ++j1) {
               if (roofWidth > 16) {
                  this.func_150516_a(world, j1, roofY, roofZ + 1, this.roofBlock, this.roofMeta);
                  this.func_150516_a(world, j1, roofY, roofZ + roofWidth - 2, this.roofBlock, this.roofMeta);
               } else {
                  this.func_150516_a(world, j1, roofY, roofZ + 1, this.roofStairBlock, 7);
                  this.func_150516_a(world, j1, roofY, roofZ + roofWidth - 2, this.roofStairBlock, 6);
               }
            }

            for(j1 = roofZ + 1; j1 < roofZ + roofWidth - 1; ++j1) {
               if (roofWidth > 16) {
                  this.func_150516_a(world, roofX + 1, roofY, j1, this.roofBlock, this.roofMeta);
                  this.func_150516_a(world, roofX + roofWidth - 2, roofY, j1, this.roofBlock, this.roofMeta);
               } else {
                  this.func_150516_a(world, roofX + 1, roofY, j1, this.roofStairBlock, 5);
                  this.func_150516_a(world, roofX + roofWidth - 2, roofY, j1, this.roofStairBlock, 4);
               }
            }

            roofWidth -= 2;
            ++roofX;
            ++roofY;
         }

         for(j1 = roofX; j1 < roofX + roofWidth; ++j1) {
            for(k1 = roofZ; k1 < roofZ + roofWidth; ++k1) {
               this.func_150516_a(world, j1, roofY - 1, k1, LOTRMod.glass, 0);
            }
         }

         this.func_150516_a(world, i + 2, j + 6, k + 9, LOTRMod.highElvenBed, 1);
         this.func_150516_a(world, i + 1, j + 6, k + 9, LOTRMod.highElvenBed, 9);
         this.func_150516_a(world, i + 1, j + 6, k + 10, this.plankBlock, this.plankMeta);
         this.placeFlowerPot(world, i + 1, j + 7, k + 10, this.getRandomPlant(random));
         this.func_150516_a(world, i + 1, j + 6, k + 8, this.plankBlock, this.plankMeta);
         this.placeFlowerPot(world, i + 1, j + 7, k + 8, this.getRandomPlant(random));
         this.func_150516_a(world, i + 1, j + 6, k + 6, Blocks.field_150342_X, 0);
         this.func_150516_a(world, i + 1, j + 6, k + 5, this.plankBlock, this.plankMeta);
         this.func_150516_a(world, i + 1, j + 6, k + 4, this.plankBlock, this.plankMeta);
         this.func_150516_a(world, i + 1, j + 6, k + 3, Blocks.field_150342_X, 0);
         this.func_150516_a(world, i + 3, j + 6, k + 4, this.stairBlock, 0);
         this.placeMug(world, random, i + 1, j + 7, k + 4, 1, LOTRFoods.ELF_DRINK);
         this.func_150516_a(world, i + 11, j + 6, k + 10, this.plankBlock, this.plankMeta);
         this.func_150516_a(world, i + 11, j + 6, k + 11, this.plankBlock, this.plankMeta);

         for(j1 = k + 10; j1 <= k + 12; ++j1) {
            this.func_150516_a(world, i + 13, j + 6, j1, this.stairBlock, 0);
         }

         for(j1 = i + 11; j1 <= i + 13; ++j1) {
            this.func_150516_a(world, j1, j + 6, k + 13, this.stairBlock, 2);
         }

         for(j1 = k + 5; j1 <= k + 9; ++j1) {
            for(k1 = i + 7; k1 <= i + 10; ++k1) {
               this.func_150516_a(world, k1, j + 5, j1, Blocks.field_150350_a, 0);
            }
         }

         for(j1 = k + 5; j1 <= k + 6; ++j1) {
            for(k1 = j + 1; k1 <= j + 4; ++k1) {
               this.func_150516_a(world, i + 7, k1, j1, LOTRMod.brick3, 2);
            }

            this.func_150516_a(world, i + 7, j + 5, j1, LOTRMod.stairsHighElvenBrick, 1);

            for(k1 = i + 8; k1 <= i + 10; ++k1) {
               for(int j1 = j + 1; j1 <= j + 3; ++j1) {
                  this.func_150516_a(world, k1, j1, j1, LOTRMod.brick3, 2);
               }
            }

            this.func_150516_a(world, i + 8, j + 4, j1, LOTRMod.stairsHighElvenBrick, 1);
         }

         for(j1 = i + 9; j1 <= i + 10; ++j1) {
            for(k1 = j + 1; k1 <= j + 2; ++k1) {
               this.func_150516_a(world, j1, k1, k + 7, LOTRMod.brick3, 2);
            }

            this.func_150516_a(world, j1, j + 3, k + 7, LOTRMod.stairsHighElvenBrick, 3);
            this.func_150516_a(world, j1, j + 1, k + 8, LOTRMod.brick3, 2);
            this.func_150516_a(world, j1, j + 2, k + 8, LOTRMod.stairsHighElvenBrick, 3);
            this.func_150516_a(world, j1, j + 1, k + 9, LOTRMod.stairsHighElvenBrick, 3);
         }

         this.func_150516_a(world, i + 11, j + 1, k + 5, LOTRMod.pillar, 10);
         this.func_150516_a(world, i + 11, j + 1, k + 6, this.tableBlock, 0);
         this.func_150516_a(world, i + 11, j + 1, k + 7, LOTRMod.pillar, 10);
         this.placeFlowerPot(world, i + 11, j + 2, k + 5, this.getRandomPlant(random));
         this.placeFlowerPot(world, i + 11, j + 2, k + 7, this.getRandomPlant(random));
         this.func_150516_a(world, i + 11, j + 3, k + 6, LOTRMod.highElvenTorch, 1);
         this.func_150516_a(world, i + 6, j + 3, k + 6, LOTRMod.highElvenTorch, 2);
         this.func_150516_a(world, i + 8, j + 3, k + 7, LOTRMod.highElvenTorch, 3);
         this.func_150516_a(world, i + 10, j + 1, k + 4, LOTRMod.pillar, 10);
         this.placeBarrel(world, random, i + 10, j + 2, k + 4, 2, LOTRFoods.ELF_DRINK);
         this.func_150516_a(world, i + 7, j + 1, k + 4, LOTRMod.pillar, 10);
         this.placeBarrel(world, random, i + 7, j + 2, k + 4, 2, LOTRFoods.ELF_DRINK);
         this.func_150516_a(world, i + 8, j + 1, k + 4, Blocks.field_150486_ae, 0);
         this.func_150516_a(world, i + 9, j + 1, k + 4, Blocks.field_150486_ae, 0);
         LOTRChestContents.fillChest(world, random, i + 8, j + 1, k + 4, this.chestContents);
         LOTRChestContents.fillChest(world, random, i + 9, j + 1, k + 4, this.chestContents);
         this.func_150516_a(world, i + 8, j + 2, k + 5, Blocks.field_150460_al, 2);
         this.func_150516_a(world, i + 9, j + 2, k + 5, Blocks.field_150460_al, 2);
         this.setBlockMetadata(world, i + 8, j + 2, k + 5, 2);
         this.setBlockMetadata(world, i + 9, j + 2, k + 5, 2);
         this.func_150516_a(world, i + 7, j + 1, k + 7, this.plankBlock, this.plankMeta);
         this.func_150516_a(world, i + 8, j + 1, k + 7, this.plankBlock, this.plankMeta);
         this.func_150516_a(world, i + 8, j + 1, k + 8, this.plankBlock, this.plankMeta);
         this.placePlateWithCertainty(world, random, i + 7, j + 2, k + 7, this.plateBlock, LOTRFoods.ELF);
         this.placePlateWithCertainty(world, random, i + 8, j + 2, k + 7, this.plateBlock, LOTRFoods.ELF);
         this.placePlateWithCertainty(world, random, i + 8, j + 2, k + 8, this.plateBlock, LOTRFoods.ELF);

         for(j1 = k + 6; j1 <= k + 12; ++j1) {
            for(k1 = i + 2; k1 <= i + 4; ++k1) {
               this.func_150516_a(world, k1, j + 1, j1, this.slabBlock, this.slabMeta | 8);
            }
         }

         for(j1 = k + 6; j1 <= k + 12; j1 += 3) {
            this.func_150516_a(world, i + 2, j + 1, j1, this.plankBlock, this.plankMeta);
            this.func_150516_a(world, i + 4, j + 1, j1, this.plankBlock, this.plankMeta);
            this.func_150516_a(world, i + 1, j + 1, j1, this.stairBlock, 1);
            this.func_150516_a(world, i + 5, j + 1, j1, this.stairBlock, 0);
         }

         this.func_150516_a(world, i + 3, j + 1, k + 13, this.stairBlock, 2);
         this.func_150516_a(world, i + 3, j + 1, k + 5, this.stairBlock, 3);

         for(j1 = k + 6; j1 <= k + 12; j1 += 2) {
            this.placePlateWithCertainty(world, random, i + 2, j + 2, j1, this.plateBlock, LOTRFoods.ELF);
            this.placePlateWithCertainty(world, random, i + 4, j + 2, j1, this.plateBlock, LOTRFoods.ELF);
         }

         for(j1 = k + 7; j1 <= k + 11; j1 += 2) {
            k1 = random.nextInt(3);
            if (k1 == 0) {
               this.func_150516_a(world, i + 3, j + 2, j1, LOTRMod.appleCrumble, 0);
            } else if (k1 == 1) {
               this.func_150516_a(world, i + 3, j + 2, j1, LOTRMod.cherryPie, 0);
            } else if (k1 == 2) {
               this.func_150516_a(world, i + 3, j + 2, j1, LOTRMod.berryPie, 0);
            }

            this.placeMug(world, random, i + 2, j + 2, j1, 3, LOTRFoods.ELF_DRINK);
            this.placeMug(world, random, i + 4, j + 2, j1, 1, LOTRFoods.ELF_DRINK);
         }

         this.placeMug(world, random, i + 3, j + 2, k + 6, 0, LOTRFoods.ELF_DRINK);
         this.placeMug(world, random, i + 3, j + 2, k + 12, 2, LOTRFoods.ELF_DRINK);
         this.placeFlowerPot(world, i + 3, j + 2, k + 8, this.getRandomPlant(random));
         this.placeFlowerPot(world, i + 3, j + 2, k + 10, this.getRandomPlant(random));

         for(j1 = j + 3; j1 <= j + 8; j1 += 5) {
            for(k1 = i + 3; k1 <= i + 12; k1 += 3) {
               this.placeWallBanner(world, k1, j1, k, 0, this.bannerType);
               this.placeWallBanner(world, k1, j1, k + 15, 2, this.bannerType);
            }

            for(k1 = k + 3; k1 <= k + 12; k1 += 3) {
               this.placeWallBanner(world, i, j1, k1, 3, this.bannerType);
               this.placeWallBanner(world, i + 15, j1, k1, 1, this.bannerType);
            }
         }

         j1 = 2 + random.nextInt(4);

         for(k1 = 0; k1 < j1; ++k1) {
            LOTREntityElf elf = this.createElf(world);
            elf.func_70012_b((double)(i + 6), (double)(j + 6), (double)(k + 6), 0.0F, 0.0F);
            elf.spawnRidingHorse = false;
            elf.func_110161_a((IEntityLivingData)null);
            elf.isNPCPersistent = true;
            world.func_72838_d(elf);
            elf.func_110171_b(i + 7, j + 3, k + 7, 24);
         }

         return true;
      }
   }

   private ItemStack getRandomPlant(Random random) {
      int l = random.nextInt(5);
      switch(l) {
      case 0:
         return new ItemStack(Blocks.field_150345_g, 1, 0);
      case 1:
         return new ItemStack(Blocks.field_150345_g, 1, 2);
      case 2:
         return new ItemStack(Blocks.field_150345_g, 1, 2);
      case 3:
         return new ItemStack(Blocks.field_150328_O);
      case 4:
         return new ItemStack(Blocks.field_150327_N);
      default:
         return new ItemStack(Blocks.field_150345_g, 1, 0);
      }
   }
}
