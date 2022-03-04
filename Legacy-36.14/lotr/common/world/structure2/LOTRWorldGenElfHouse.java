package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityGaladhrimElf;
import lotr.common.world.feature.LOTRWorldGenMallornExtreme;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenElfHouse extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenElfHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, this.usingPlayer != null ? 2 : 0);
      byte stairX;
      int i2;
      if (this.usingPlayer != null) {
         LOTRWorldGenMallornExtreme treeGen = new LOTRWorldGenMallornExtreme(true);
         stairX = 0;
         int j1 = 0;
         int k1 = 0;
         i2 = treeGen.generateAndReturnHeight(world, random, this.getX(stairX, k1), this.getY(j1), this.getZ(stairX, k1), true);
         this.originY += MathHelper.func_76128_c((double)((float)i2 * MathHelper.func_151240_a(random, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MIN, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MAX)));
      }

      int bough;
      int j1;
      int i1;
      if (this.restrictions) {
         for(bough = -8; bough <= 8; ++bough) {
            for(j1 = -3; j1 <= 6; ++j1) {
               for(i1 = -8; i1 <= 8; ++i1) {
                  if ((Math.abs(bough) > 2 || Math.abs(i1) > 2) && !this.isAir(world, bough, j1, i1)) {
                     return false;
                  }
               }
            }
         }
      } else if (this.usingPlayer != null) {
         for(bough = -2; bough <= 2; ++bough) {
            for(j1 = -2; j1 <= 2; ++j1) {
               for(i1 = 0; !this.isOpaque(world, bough, i1, j1) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, bough, i1, j1, LOTRMod.wood, 1);
               }
            }
         }
      }

      for(bough = -7; bough <= 7; ++bough) {
         for(j1 = 1; j1 <= 4; ++j1) {
            for(i1 = -7; i1 <= 7; ++i1) {
               this.setBlockAndMetadata(world, bough, j1, i1, Blocks.field_150350_a, 0);
            }
         }
      }

      for(bough = -2; bough <= 2; ++bough) {
         for(j1 = -1; j1 <= 5; ++j1) {
            for(i1 = -2; i1 <= 2; ++i1) {
               this.setBlockAndMetadata(world, bough, j1, i1, LOTRMod.wood, 1);
               if (j1 >= 1 && j1 <= 2 && Math.abs(bough) == 2 && Math.abs(i1) == 2) {
                  this.setBlockAndMetadata(world, bough, j1, i1, LOTRMod.fence, 1);
               }
            }
         }
      }

      for(bough = -6; bough <= 6; ++bough) {
         for(j1 = -6; j1 <= 6; ++j1) {
            if ((Math.abs(bough) > 2 || Math.abs(j1) > 2) && Math.abs(bough) != 6 && Math.abs(j1) != 6) {
               this.setBlockAndMetadata(world, bough, 0, j1, LOTRMod.planks, 1);
            }
         }
      }

      for(bough = -5; bough <= 5; ++bough) {
         this.setBlockAndMetadata(world, bough, 0, -6, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, bough, 0, 6, LOTRMod.planks, 1);
      }

      for(bough = -5; bough <= 5; ++bough) {
         this.setBlockAndMetadata(world, -6, 0, bough, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, 6, 0, bough, LOTRMod.planks, 1);
      }

      for(bough = -3; bough <= 3; ++bough) {
         this.setBlockAndMetadata(world, bough, 0, -7, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, bough, 0, 7, LOTRMod.planks, 1);
      }

      for(bough = -3; bough <= 3; ++bough) {
         this.setBlockAndMetadata(world, -7, 0, bough, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, 7, 0, bough, LOTRMod.planks, 1);
      }

      for(bough = 1; bough <= 4; ++bough) {
         this.setBlockAndMetadata(world, -5, bough, -5, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, 5, bough, -5, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, -5, bough, 5, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, 5, bough, 5, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, -6, bough, -3, LOTRMod.wood, 1);
         this.setBlockAndMetadata(world, -6, bough, 3, LOTRMod.wood, 1);
         this.setBlockAndMetadata(world, 6, bough, -3, LOTRMod.wood, 1);
         this.setBlockAndMetadata(world, 6, bough, 3, LOTRMod.wood, 1);
         this.setBlockAndMetadata(world, -3, bough, -6, LOTRMod.wood, 1);
         this.setBlockAndMetadata(world, -3, bough, 6, LOTRMod.wood, 1);
         this.setBlockAndMetadata(world, 3, bough, -6, LOTRMod.wood, 1);
         this.setBlockAndMetadata(world, 3, bough, 6, LOTRMod.wood, 1);
      }

      this.setBlockAndMetadata(world, -4, 2, -5, getRandomTorch(random), 2);
      this.setBlockAndMetadata(world, -5, 2, -4, getRandomTorch(random), 3);
      this.setBlockAndMetadata(world, 4, 2, -5, getRandomTorch(random), 1);
      this.setBlockAndMetadata(world, 5, 2, -4, getRandomTorch(random), 3);
      this.setBlockAndMetadata(world, -4, 2, 5, getRandomTorch(random), 2);
      this.setBlockAndMetadata(world, -5, 2, 4, getRandomTorch(random), 4);
      this.setBlockAndMetadata(world, 4, 2, 5, getRandomTorch(random), 1);
      this.setBlockAndMetadata(world, 5, 2, 4, getRandomTorch(random), 4);

      for(bough = -3; bough <= 3; ++bough) {
         this.setBlockAndMetadata(world, bough, 1, -7, LOTRMod.fence, 1);
         this.setBlockAndMetadata(world, bough, 1, 7, LOTRMod.fence, 1);
      }

      for(bough = -3; bough <= 3; ++bough) {
         this.setBlockAndMetadata(world, -7, 1, bough, LOTRMod.fence, 1);
         this.setBlockAndMetadata(world, 7, 1, bough, LOTRMod.fence, 1);
      }

      this.setBlockAndMetadata(world, -4, 1, -6, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, -5, 1, -6, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, -4, 1, 6, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, -5, 1, 6, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, 4, 1, -6, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, 5, 1, -6, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, 4, 1, 6, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, 5, 1, 6, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, -6, 1, -4, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, -6, 1, -5, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, 6, 1, -4, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, 6, 1, -5, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, -6, 1, 4, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, -6, 1, 5, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, 6, 1, 4, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, 6, 1, 5, LOTRMod.fence, 1);
      this.setBlockAndMetadata(world, -6, 4, -2, LOTRMod.stairsMallorn, 7);
      this.setBlockAndMetadata(world, -6, 4, -1, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, -6, 4, 0, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, -6, 4, 1, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, -6, 4, 2, LOTRMod.stairsMallorn, 6);
      this.setBlockAndMetadata(world, 6, 4, -2, LOTRMod.stairsMallorn, 7);
      this.setBlockAndMetadata(world, 6, 4, -1, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, 6, 4, 0, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, 6, 4, 1, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, 6, 4, 2, LOTRMod.stairsMallorn, 6);
      this.setBlockAndMetadata(world, -2, 4, -6, LOTRMod.stairsMallorn, 4);
      this.setBlockAndMetadata(world, -1, 4, -6, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, 0, 4, -6, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, 1, 4, -6, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, 2, 4, -6, LOTRMod.stairsMallorn, 5);
      this.setBlockAndMetadata(world, -2, 4, 6, LOTRMod.stairsMallorn, 4);
      this.setBlockAndMetadata(world, -1, 4, 6, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, 0, 4, 6, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, 1, 4, 6, LOTRMod.woodSlabSingle, 9);
      this.setBlockAndMetadata(world, 2, 4, 6, LOTRMod.stairsMallorn, 5);

      for(bough = -6; bough <= -4; ++bough) {
         this.setBlockAndMetadata(world, bough, 4, -6, LOTRMod.stairsMallorn, 6);
         this.setBlockAndMetadata(world, bough, 4, 6, LOTRMod.stairsMallorn, 7);
      }

      for(bough = 4; bough <= 6; ++bough) {
         this.setBlockAndMetadata(world, bough, 4, -6, LOTRMod.stairsMallorn, 6);
         this.setBlockAndMetadata(world, bough, 4, 6, LOTRMod.stairsMallorn, 7);
      }

      for(bough = -6; bough <= -4; ++bough) {
         this.setBlockAndMetadata(world, -6, 4, bough, LOTRMod.stairsMallorn, 5);
         this.setBlockAndMetadata(world, 6, 4, bough, LOTRMod.stairsMallorn, 4);
      }

      for(bough = 4; bough <= 6; ++bough) {
         this.setBlockAndMetadata(world, -6, 4, bough, LOTRMod.stairsMallorn, 5);
         this.setBlockAndMetadata(world, 6, 4, bough, LOTRMod.stairsMallorn, 4);
      }

      for(bough = -4; bough <= 4; ++bough) {
         if (Math.abs(bough) > 1) {
            this.setBlockAndMetadata(world, bough, 4, -5, LOTRMod.stairsMallorn, 7);
            this.setBlockAndMetadata(world, bough, 4, 5, LOTRMod.stairsMallorn, 6);
         }
      }

      for(bough = -4; bough <= 4; ++bough) {
         if (Math.abs(bough) > 1) {
            this.setBlockAndMetadata(world, -5, 4, bough, LOTRMod.stairsMallorn, 4);
            this.setBlockAndMetadata(world, 5, 4, bough, LOTRMod.stairsMallorn, 5);
         }
      }

      for(bough = -6; bough <= 6; ++bough) {
         for(j1 = -6; j1 <= 6; ++j1) {
            if ((!this.restrictions || bough < -2 || bough > 2 || j1 < -2 || j1 > 2) && (bough != -6 && bough != 6 || j1 != -6 && j1 != 6)) {
               this.setBlockAndMetadata(world, bough, 5, j1, LOTRMod.planks, 1);
            }
         }
      }

      for(bough = -3; bough <= 3; ++bough) {
         this.setBlockAndMetadata(world, bough, 5, -7, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, bough, 5, 7, LOTRMod.planks, 1);
      }

      for(bough = -3; bough <= 3; ++bough) {
         this.setBlockAndMetadata(world, -7, 5, bough, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, 7, 5, bough, LOTRMod.planks, 1);
      }

      for(bough = -5; bough <= 5; ++bough) {
         for(j1 = -5; j1 <= 5; ++j1) {
            if ((!this.restrictions || bough < -2 || bough > 2 || j1 < -2 || j1 > 2) && (bough != -5 && bough != 5 || j1 != -5 && j1 != 5)) {
               this.setBlockAndMetadata(world, bough, 6, j1, LOTRMod.planks, 1);
            }
         }
      }

      for(bough = -2; bough <= 2; ++bough) {
         this.setBlockAndMetadata(world, bough, 6, -6, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, bough, 6, 6, LOTRMod.planks, 1);
      }

      for(bough = -2; bough <= 2; ++bough) {
         this.setBlockAndMetadata(world, -6, 6, bough, LOTRMod.planks, 1);
         this.setBlockAndMetadata(world, 6, 6, bough, LOTRMod.planks, 1);
      }

      int k1;
      boolean stairX;
      for(bough = -8; bough <= 8; ++bough) {
         stairX = false;
         k1 = Math.abs(bough);
         if (k1 <= 3) {
            stairX = 8;
         } else if (k1 <= 5) {
            stairX = 7;
         } else if (k1 <= 7) {
            stairX = 6;
         } else {
            stairX = 4;
         }

         this.setBlockAndMetadata(world, bough, 5, -stairX, LOTRMod.stairsMallorn, 2);
         this.setBlockAndMetadata(world, bough, 5, stairX, LOTRMod.stairsMallorn, 3);
         j1 = stairX - 1;
         i1 = Integer.signum(bough) * (Math.abs(bough) - 1);
         this.setBlockAndMetadata(world, i1, 6, -j1, LOTRMod.stairsMallorn, 2);
         this.setBlockAndMetadata(world, i1, 6, j1, LOTRMod.stairsMallorn, 3);
      }

      for(bough = -8; bough <= 8; ++bough) {
         stairX = false;
         k1 = Math.abs(bough);
         if (k1 <= 3) {
            stairX = 8;
         } else if (k1 <= 5) {
            stairX = 7;
         } else if (k1 <= 7) {
            stairX = 6;
         } else {
            stairX = 4;
         }

         this.setBlockAndMetadata(world, -stairX, 5, bough, LOTRMod.stairsMallorn, 1);
         this.setBlockAndMetadata(world, stairX, 5, bough, LOTRMod.stairsMallorn, 0);
         j1 = stairX - 1;
         i1 = Integer.signum(bough) * (Math.abs(bough) - 1);
         this.setBlockAndMetadata(world, -j1, 6, i1, LOTRMod.stairsMallorn, 1);
         this.setBlockAndMetadata(world, j1, 6, i1, LOTRMod.stairsMallorn, 0);
      }

      for(bough = -3; bough <= 3; ++bough) {
         this.setBlockAndMetadata(world, bough, 4, -3, LOTRMod.stairsMallorn, 6);
         this.setBlockAndMetadata(world, bough, 4, 3, LOTRMod.stairsMallorn, 7);
      }

      for(bough = -2; bough <= 2; ++bough) {
         this.setBlockAndMetadata(world, -3, 4, bough, LOTRMod.stairsMallorn, 5);
         this.setBlockAndMetadata(world, 3, 4, bough, LOTRMod.stairsMallorn, 4);
      }

      for(bough = 0; bough <= 2; ++bough) {
         j1 = -3 + bough;
         i1 = 0 + bough;
         k1 = 3 + bough;

         for(i2 = -i1; i2 <= i1; ++i2) {
            for(int k2 = -k1; k2 <= k1; ++k2) {
               this.setBlockAndMetadata(world, i2, j1, k2, LOTRMod.wood, 13);
               this.setBlockAndMetadata(world, k2, j1, i2, LOTRMod.wood, 13);
            }
         }
      }

      Block ladder = random.nextBoolean() ? LOTRMod.hithlainLadder : LOTRMod.mallornLadder;

      for(j1 = 3; j1 >= -3 || !this.isOpaque(world, 0, j1, -3) && this.getY(j1) >= 0; --j1) {
         this.setBlockAndMetadata(world, 0, j1, -3, ladder, 2);
      }

      this.setBlockAndMetadata(world, -2, 1, 0, LOTRMod.elvenTable, 0);
      this.setBlockAndMetadata(world, -2, 2, 0, Blocks.field_150350_a, 0);
      this.setBlockAndMetadata(world, -2, 3, 0, Blocks.field_150350_a, 0);
      this.setBlockAndMetadata(world, -2, 4, 0, LOTRMod.wood, 5);
      this.setBlockAndMetadata(world, 2, 1, 0, LOTRMod.elvenTable, 0);
      this.setBlockAndMetadata(world, 2, 2, 0, Blocks.field_150350_a, 0);
      this.setBlockAndMetadata(world, 2, 3, 0, Blocks.field_150350_a, 0);
      this.setBlockAndMetadata(world, 2, 4, 0, LOTRMod.wood, 5);
      this.placeChest(world, random, 0, 1, 2, LOTRMod.chestMallorn, 0, LOTRChestContents.ELF_HOUSE);
      this.setBlockAndMetadata(world, 0, 2, 2, Blocks.field_150350_a, 0);
      this.setBlockAndMetadata(world, 0, 3, 2, Blocks.field_150350_a, 0);
      this.setBlockAndMetadata(world, 0, 4, 2, LOTRMod.wood, 9);
      this.tryPlaceLight(world, -7, -1, -3, random);
      this.tryPlaceLight(world, -7, -1, 3, random);
      this.tryPlaceLight(world, 7, -1, -3, random);
      this.tryPlaceLight(world, 7, -1, 3, random);
      this.tryPlaceLight(world, -3, -1, -7, random);
      this.tryPlaceLight(world, 3, -1, -7, random);
      this.tryPlaceLight(world, -3, -1, 7, random);
      this.tryPlaceLight(world, 3, -1, 7, random);
      this.placeFlowerPot(world, -4, 1, -5, getRandomPlant(random));
      this.placeFlowerPot(world, -5, 1, -4, getRandomPlant(random));
      this.placeFlowerPot(world, -5, 1, 4, getRandomPlant(random));
      this.placeFlowerPot(world, -4, 1, 5, getRandomPlant(random));
      this.placeFlowerPot(world, 4, 1, -5, getRandomPlant(random));
      this.placeFlowerPot(world, 5, 1, -4, getRandomPlant(random));
      this.placeFlowerPot(world, 5, 1, 4, getRandomPlant(random));
      this.placeFlowerPot(world, 4, 1, 5, getRandomPlant(random));
      this.setBlockAndMetadata(world, -2, 1, 5, LOTRMod.elvenBed, 3);
      this.setBlockAndMetadata(world, -3, 1, 5, LOTRMod.elvenBed, 11);
      LOTREntityElf elf = new LOTREntityGaladhrimElf(world);
      elf.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(elf, world, 0, 1, 4, 8);
      return true;
   }

   private void tryPlaceLight(World world, int i, int j, int k, Random random) {
      int height = 2 + random.nextInt(6);

      int j1;
      for(j1 = j; j1 >= -height; --j1) {
         if (this.restrictions) {
            if (!this.isAir(world, i, j1, k)) {
               return;
            }

            if (j1 == -height && (!this.isAir(world, i, j1, k - 1) || !this.isAir(world, i, j1, k + 1) || !this.isAir(world, i - 1, j1, k) || !this.isAir(world, i + 1, j1, k))) {
               return;
            }
         }
      }

      for(j1 = j; j1 >= j - height; --j1) {
         if (j1 == j - height) {
            this.setBlockAndMetadata(world, i, j1, k, LOTRMod.planks, 1);
            this.setBlockAndMetadata(world, i, j1, k - 1, getRandomTorch(random), 4);
            this.setBlockAndMetadata(world, i, j1, k + 1, getRandomTorch(random), 3);
            this.setBlockAndMetadata(world, i - 1, j1, k, getRandomTorch(random), 1);
            this.setBlockAndMetadata(world, i + 1, j1, k, getRandomTorch(random), 2);
         } else {
            this.setBlockAndMetadata(world, i, j1, k, LOTRMod.fence, 1);
         }
      }

   }

   public static ItemStack getRandomPlant(Random random) {
      return random.nextBoolean() ? new ItemStack(LOTRMod.elanor) : new ItemStack(LOTRMod.niphredil);
   }

   public static Block getRandomTorch(Random random) {
      if (random.nextBoolean()) {
         int i = random.nextInt(3);
         if (i == 0) {
            return LOTRMod.mallornTorchBlue;
         }

         if (i == 1) {
            return LOTRMod.mallornTorchGold;
         }

         if (i == 2) {
            return LOTRMod.mallornTorchGreen;
         }
      }

      return LOTRMod.mallornTorchSilver;
   }

   public static ItemStack getRandomChandelier(Random random) {
      if (random.nextBoolean()) {
         int i = random.nextInt(3);
         if (i == 0) {
            return new ItemStack(LOTRMod.chandelier, 1, 13);
         }

         if (i == 1) {
            return new ItemStack(LOTRMod.chandelier, 1, 14);
         }

         if (i == 2) {
            return new ItemStack(LOTRMod.chandelier, 1, 15);
         }
      }

      return new ItemStack(LOTRMod.chandelier, 1, 5);
   }
}
