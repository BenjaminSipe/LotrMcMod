package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityTauredain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainHouseLarge extends LOTRWorldGenTauredainHouse {
   public LOTRWorldGenTauredainHouseLarge(boolean flag) {
      super(flag);
   }

   protected int getOffset() {
      return 5;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
         return false;
      } else {
         int k1;
         int k1;
         int j1;
         for(k1 = -6; k1 <= 5; ++k1) {
            for(k1 = -4; k1 <= 4; ++k1) {
               this.layFoundation(world, k1, k1);

               for(j1 = 1; j1 <= 11; ++j1) {
                  this.setAir(world, k1, j1, k1);
               }
            }
         }

         for(k1 = -6; k1 <= 5; ++k1) {
            for(k1 = -4; k1 <= 4; ++k1) {
               if (k1 >= -5 && k1 <= 4 && k1 >= -3 && k1 <= 3) {
                  this.setBlockAndMetadata(world, k1, 0, k1, this.floorBlock, this.floorMeta);
               }

               if ((k1 == -5 || k1 == 4) && k1 >= -3 && k1 <= 3 || (k1 == -3 || k1 == 3) && k1 >= -5 && k1 <= 4) {
                  this.setBlockAndMetadata(world, k1, 3, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
                  this.setBlockAndMetadata(world, k1, 4, k1, LOTRMod.mudGrass, 0);
                  this.setBlockAndMetadata(world, k1, 5, k1, Blocks.field_150329_H, 1);
               }

               if ((k1 == -4 || k1 == 3) && k1 >= -2 && k1 <= 2 || (k1 == -2 || k1 == 2) && k1 >= -4 && k1 <= 3) {
                  this.setBlockAndMetadata(world, k1, 4, k1, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, k1, 5, k1, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, k1, 8, k1, this.plankBlock, this.plankMeta);
               }
            }
         }

         int[] var16 = new int[]{-6, 5};
         k1 = var16.length;

         int k1;
         int[] var11;
         int var12;
         int var13;
         int k1;
         for(j1 = 0; j1 < k1; ++j1) {
            k1 = var16[j1];
            var11 = new int[]{-4, 4};
            var12 = var11.length;

            for(var13 = 0; var13 < var12; ++var13) {
               k1 = var11[var13];

               for(int j1 = 1; j1 <= 5; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, k1, this.woodBlock, this.woodMeta);
               }
            }
         }

         var16 = new int[]{-4, 4};
         k1 = var16.length;

         int i1;
         for(j1 = 0; j1 < k1; ++j1) {
            k1 = var16[j1];

            for(i1 = -5; i1 <= 4; ++i1) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.brickSlabBlock, this.brickSlabMeta);
               if (IntMath.mod(i1, 3) == 1) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.woodBlock, this.woodMeta | 8);
               } else {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.plankBlock, this.plankMeta);
               }
            }

            for(i1 = 1; i1 <= 3; ++i1) {
               this.setBlockAndMetadata(world, -5, i1, k1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, 4, i1, k1, this.brickBlock, this.brickMeta);
            }

            var11 = new int[]{-4, 2};
            var12 = var11.length;

            for(var13 = 0; var13 < var12; ++var13) {
               k1 = var11[var13];
               this.setBlockAndMetadata(world, k1, 1, k1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, k1 + 1, 1, k1, this.brickBlock, this.brickMeta);
               if (random.nextInt(3) == 0) {
                  this.placeTauredainFlowerPot(world, k1, 2, k1, random);
               }

               if (random.nextInt(3) == 0) {
                  this.placeTauredainFlowerPot(world, k1 + 1, 2, k1, random);
               }

               this.setBlockAndMetadata(world, k1, 3, k1, this.brickStairBlock, 5);
               this.setBlockAndMetadata(world, k1 + 1, 3, k1, this.brickStairBlock, 4);
            }
         }

         this.setBlockAndMetadata(world, -1, 3, -4, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 0, 3, -4, this.brickBlock, this.brickMeta);

         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, -1, k1, 4, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 0, k1, 4, this.brickBlock, this.brickMeta);
         }

         var16 = new int[]{-6, 5};
         k1 = var16.length;

         for(j1 = 0; j1 < k1; ++j1) {
            k1 = var16[j1];

            for(i1 = -3; i1 <= 3; ++i1) {
               this.setBlockAndMetadata(world, k1, 5, i1, this.brickSlabBlock, this.brickSlabMeta);
               if (i1 % 2 == 0) {
                  this.setBlockAndMetadata(world, k1, 4, i1, this.woodBlock, this.woodMeta | 4);
               } else {
                  this.setBlockAndMetadata(world, k1, 4, i1, this.plankBlock, this.plankMeta);
               }
            }

            for(i1 = 1; i1 <= 3; ++i1) {
               this.setBlockAndMetadata(world, k1, i1, -3, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, k1, i1, 3, this.brickBlock, this.brickMeta);
            }

            var11 = new int[]{-2, 1};
            var12 = var11.length;

            for(var13 = 0; var13 < var12; ++var13) {
               k1 = var11[var13];
               this.setBlockAndMetadata(world, k1, 1, k1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, k1, 1, k1 + 1, this.brickBlock, this.brickMeta);
               if (random.nextInt(3) == 0) {
                  this.placeTauredainFlowerPot(world, k1, 2, k1, random);
               }

               if (random.nextInt(3) == 0) {
                  this.placeTauredainFlowerPot(world, k1, 2, k1 + 1, random);
               }

               this.setBlockAndMetadata(world, k1, 3, k1, this.brickStairBlock, 6);
               this.setBlockAndMetadata(world, k1, 3, k1 + 1, this.brickStairBlock, 7);
            }
         }

         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, -2, k1, -4, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 1, k1, -4, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, -2, k1, 4, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 1, k1, 4, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, -6, k1, 0, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 5, k1, 0, this.woodBlock, this.woodMeta);
         }

         for(k1 = -3; k1 <= 2; ++k1) {
            for(k1 = -1; k1 <= 1; ++k1) {
               this.setBlockAndMetadata(world, k1, 4, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            }
         }

         for(k1 = 5; k1 <= 9; ++k1) {
            this.setBlockAndMetadata(world, -4, k1, -2, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 3, k1, -2, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, -4, k1, 2, this.woodBlock, this.woodMeta);
            this.setBlockAndMetadata(world, 3, k1, 2, this.woodBlock, this.woodMeta);
         }

         for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, -2, 8, k1, this.woodBlock, this.woodMeta | 8);
            this.setBlockAndMetadata(world, 1, 8, k1, this.woodBlock, this.woodMeta | 8);
         }

         this.setBlockAndMetadata(world, -5, 8, 0, this.woodBlock, this.woodMeta | 4);
         this.setBlockAndMetadata(world, -4, 8, 0, this.woodBlock, this.woodMeta | 4);
         this.setBlockAndMetadata(world, 3, 8, 0, this.woodBlock, this.woodMeta | 4);
         this.setBlockAndMetadata(world, 4, 8, 0, this.woodBlock, this.woodMeta | 4);
         var16 = new int[]{-2, 2};
         k1 = var16.length;

         for(j1 = 0; j1 < k1; ++j1) {
            k1 = var16[j1];
            this.setBlockAndMetadata(world, -3, 6, k1, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, -3, 7, k1, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 7, k1, this.brickStairBlock, 5);

            for(i1 = -2; i1 <= 1; ++i1) {
               if (random.nextInt(3) == 0) {
                  this.placeTauredainFlowerPot(world, i1, 6, k1, random);
               }
            }
         }

         var16 = new int[]{-4, 3};
         k1 = var16.length;

         for(j1 = 0; j1 < k1; ++j1) {
            k1 = var16[j1];
            this.setBlockAndMetadata(world, k1, 6, -1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, k1, 7, -1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, k1, 6, 1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, k1, 7, 1, this.brickStairBlock, 6);
            if (random.nextInt(3) == 0) {
               this.placeTauredainFlowerPot(world, k1, 6, 0, random);
            }
         }

         for(k1 = -3; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, k1, 9, -2, LOTRMod.stairsTauredainBrickGold, 2);
            this.setBlockAndMetadata(world, k1, 10, -1, LOTRMod.stairsTauredainBrickGold, 2);
            this.setBlockAndMetadata(world, k1, 9, 2, LOTRMod.stairsTauredainBrickGold, 3);
            this.setBlockAndMetadata(world, k1, 10, 1, LOTRMod.stairsTauredainBrickGold, 3);
         }

         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, -4, 9, k1, LOTRMod.stairsTauredainBrickGold, 1);
            this.setBlockAndMetadata(world, -3, 10, k1, LOTRMod.stairsTauredainBrickGold, 1);
            this.setBlockAndMetadata(world, 3, 9, k1, LOTRMod.stairsTauredainBrickGold, 0);
            this.setBlockAndMetadata(world, 2, 10, k1, LOTRMod.stairsTauredainBrickGold, 0);
         }

         for(k1 = -2; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, k1, 10, 0, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, k1, 11, 0, this.brickSlabBlock, this.brickSlabMeta);
         }

         for(k1 = 0; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, -2, 5, k1, this.bedBlock, 3);
            this.setBlockAndMetadata(world, -3, 5, k1, this.bedBlock, 11);
         }

         this.setBlockAndMetadata(world, -3, 5, -1, this.woodBlock, this.woodMeta);
         this.placeTauredainFlowerPot(world, -3, 6, -1, random);
         this.placeSkull(world, random, -2, 9, 0);
         this.placeSkull(world, random, 1, 9, 0);
         var16 = new int[]{-1, 1};
         k1 = var16.length;

         for(j1 = 0; j1 < k1; ++j1) {
            k1 = var16[j1];
            this.setBlockAndMetadata(world, -3, 8, k1, Blocks.field_150478_aa, 2);
            this.setBlockAndMetadata(world, 2, 8, k1, Blocks.field_150478_aa, 1);
         }

         for(k1 = 1; k1 <= 5; ++k1) {
            if (k1 <= 3) {
               this.setBlockAndMetadata(world, 3, k1, 1, this.woodBlock, this.woodMeta);
            }

            this.setBlockAndMetadata(world, 2, k1, 1, Blocks.field_150468_ap, 5);
         }

         this.placeWallBanner(world, 3, 3, 1, LOTRItemBanner.BannerType.TAUREDAIN, 2);

         for(k1 = -1; k1 <= 0; ++k1) {
            for(k1 = 0; k1 <= 2; ++k1) {
               this.setBlockAndMetadata(world, k1, 3, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
               this.setBlockAndMetadata(world, k1, 4, k1, this.plankBlock, this.plankMeta);
            }

            for(k1 = 0; k1 <= 3; ++k1) {
               this.setBlockAndMetadata(world, k1, 1, k1, this.plankBlock, this.plankMeta);
               if ((k1 + k1) % 2 == 0) {
                  this.placePlateWithCertainty(world, random, k1, 2, k1, this.plateBlock, LOTRFoods.TAUREDAIN);
               } else {
                  this.placeMug(world, random, k1, 2, k1, random.nextInt(4), LOTRFoods.TAUREDAIN_DRINK);
               }
            }
         }

         for(k1 = -5; k1 <= -4; ++k1) {
            this.setBlockAndMetadata(world, k1, 1, 3, Blocks.field_150460_al, 2);
         }

         this.setBlockAndMetadata(world, -3, 1, 3, Blocks.field_150462_ai, 0);
         this.setBlockAndMetadata(world, -2, 1, 3, LOTRMod.tauredainTable, 0);

         for(k1 = 1; k1 <= 2; ++k1) {
            this.placeChest(world, random, k1, 1, 3, 2, LOTRChestContents.TAUREDAIN_HOUSE);
         }

         for(k1 = 3; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, k1, 1, 3, this.woodBlock, this.woodMeta);
         }

         this.setBlockAndMetadata(world, -5, 1, -3, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, -5, 1, -2, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -5, 1, -1, this.woodBlock, this.woodMeta);
         this.placeBarrel(world, random, -5, 2, -3, 4, LOTRFoods.TAUREDAIN_DRINK);
         this.placeMug(world, random, -5, 2, -2, 3, LOTRFoods.TAUREDAIN_DRINK);
         this.placeMug(world, random, -5, 2, -1, 3, LOTRFoods.TAUREDAIN_DRINK);
         this.setBlockAndMetadata(world, 4, 1, -3, this.woodBlock, this.woodMeta);
         this.setBlockAndMetadata(world, 4, 1, -2, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 4, 1, -1, this.woodBlock, this.woodMeta);

         for(k1 = -3; k1 <= -1; ++k1) {
            this.placePlate(world, random, 4, 2, k1, this.plateBlock, LOTRFoods.TAUREDAIN);
         }

         this.setBlockAndMetadata(world, -1, 0, -4, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 0, 0, -4, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -2, 2, -3, Blocks.field_150478_aa, 3);
         this.setBlockAndMetadata(world, 1, 2, -3, Blocks.field_150478_aa, 3);
         this.setBlockAndMetadata(world, -5, 2, 0, Blocks.field_150478_aa, 2);
         this.setBlockAndMetadata(world, 4, 2, 0, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, -5, 2, 3, Blocks.field_150478_aa, 4);
         this.setBlockAndMetadata(world, -2, 2, 3, Blocks.field_150478_aa, 4);
         this.setBlockAndMetadata(world, 1, 2, 3, Blocks.field_150478_aa, 4);
         this.setBlockAndMetadata(world, 4, 2, 3, Blocks.field_150478_aa, 4);
         this.placeTauredainTorch(world, -6, 6, -4);
         this.placeTauredainTorch(world, 5, 6, -4);
         this.placeTauredainTorch(world, -6, 6, 4);
         this.placeTauredainTorch(world, 5, 6, 4);
         this.setBlockAndMetadata(world, -1, 1, -4, this.doorBlock, 1);
         this.setBlockAndMetadata(world, -1, 2, -4, this.doorBlock, 8);
         this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
         this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 9);
         LOTREntityTauredain male = new LOTREntityTauredain(world);
         male.familyInfo.setMale(true);
         male.setupNPCName();
         LOTREntityTauredain female = new LOTREntityTauredain(world);
         female.familyInfo.setMale(false);
         female.setupNPCName();
         this.spawnNPCAndSetHome(male, world, 0, 1, -1, 16);
         this.spawnNPCAndSetHome(female, world, 0, 1, -1, 16);
         return true;
      }
   }
}
