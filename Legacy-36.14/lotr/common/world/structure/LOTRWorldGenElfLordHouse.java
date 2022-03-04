package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityGaladhrimLord;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.feature.LOTRWorldGenMallornExtreme;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenElfLordHouse extends LOTRWorldGenStructureBase {
   public LOTRWorldGenElfLordHouse(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int i1;
      int k1;
      int i2;
      int i1;
      int distSq;
      int i3;
      if (this.restrictions) {
         for(i1 = i - 14; i1 <= i + 14; ++i1) {
            for(k1 = j; k1 <= j + 7; ++k1) {
               for(i2 = k - 14; i2 <= k + 14; ++i2) {
                  if ((Math.abs(i1 - i) > 2 || Math.abs(i2 - k) > 2) && !world.func_147437_c(i1, k1, i2)) {
                     return false;
                  }
               }
            }
         }

         i1 = 0;
         k1 = 0;

         for(i2 = i - 5; i2 <= i + 5; ++i2) {
            for(i1 = k - 5; i1 <= k + 5; ++i1) {
               if (Math.abs(i2 - i) > 2 || Math.abs(i1 - k) > 2) {
                  for(distSq = j; distSq >= 0; --distSq) {
                     if (world.func_147439_a(i2, distSq, i1) == Blocks.field_150349_c) {
                        i1 += distSq;
                        ++k1;
                        break;
                     }
                  }
               }
            }
         }

         i2 = i1 / k1;

         for(i1 = i - 5; i1 <= i + 5; ++i1) {
            for(distSq = k - 5; distSq <= k + 5; ++distSq) {
               if (Math.abs(i1 - i) > 2 || Math.abs(distSq - k) > 2) {
                  for(i3 = j; i3 > i2; --i3) {
                     this.func_150516_a(world, i1, i3, distSq, Blocks.field_150350_a, 0);
                  }

                  this.func_150516_a(world, i1, i2, distSq, Blocks.field_150349_c, 0);
               }
            }
         }
      } else if (this.usingPlayer != null) {
         for(i1 = i - 2; i1 <= i + 2; ++i1) {
            for(k1 = k - 2; k1 <= k + 2; ++k1) {
               for(i2 = j; !LOTRMod.isOpaque(world, i1, i2, k1) && i2 >= 0; --i2) {
                  this.func_150516_a(world, i1, i2, k1, LOTRMod.wood, 1);
               }
            }
         }

         --j;
         LOTRWorldGenMallornExtreme treeGen = new LOTRWorldGenMallornExtreme(true);
         k1 = treeGen.generateAndReturnHeight(world, random, i, j, k, true);
         j += MathHelper.func_76128_c((double)((float)k1 * MathHelper.func_151240_a(random, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MIN, LOTRWorldGenMallornExtreme.HOUSE_HEIGHT_MAX)));
      }

      this.buildStaircase(world, random, i, j, k);

      for(i1 = i - 14; i1 <= i + 14; ++i1) {
         for(k1 = j; k1 <= j + 6; ++k1) {
            for(i2 = k - 14; i2 <= k + 14; ++i2) {
               this.func_150516_a(world, i1, k1, i2, Blocks.field_150350_a, 0);
            }
         }
      }

      for(i1 = i - 2; i1 <= i + 2; ++i1) {
         for(k1 = j; k1 <= j + 7; ++k1) {
            for(i2 = k - 2; i2 <= k + 2; ++i2) {
               this.func_150516_a(world, i1, k1, i2, LOTRMod.wood, 1);
            }
         }
      }

      for(i1 = i - 12; i1 <= i + 12; ++i1) {
         for(k1 = k - 12; k1 <= k + 12; ++k1) {
            i2 = i1 - i;
            i1 = k1 - k;
            if (Math.abs(i2) > 2 || Math.abs(i1) > 2) {
               distSq = i2 * i2 + i1 * i1;
               if (distSq < 100) {
                  this.func_150516_a(world, i1, j, k1, LOTRMod.planks, 1);
               } else if (distSq < 169) {
                  this.func_150516_a(world, i1, j + 1, k1, LOTRMod.planks, 1);
                  if (distSq > 132) {
                     this.func_150516_a(world, i1, j + 2, k1, LOTRMod.fence, 1);
                  }
               }
            }
         }
      }

      for(i1 = i - 12; i1 <= i + 12; ++i1) {
         for(k1 = k - 12; k1 <= k + 12; ++k1) {
            i2 = i1 - i;
            i1 = k1 - k;
            distSq = i2 * i2 + i1 * i1;
            if ((Math.abs(i2) > 2 || Math.abs(i1) > 2) && distSq < 169) {
               this.func_150516_a(world, i1, j + 6, k1, LOTRMod.planks, 1);
               i3 = i1;
               int k3 = k1;
               if (i1 > i) {
                  i3 = i1 - 1;
               }

               if (i3 < i) {
                  ++i3;
               }

               if (k1 > k) {
                  k3 = k1 - 1;
               }

               if (k3 < k) {
                  ++k3;
               }

               this.func_150516_a(world, i3, j + 7, k3, LOTRMod.planks, 1);
            }
         }
      }

      this.buildStairCircle(world, i, j, k, 10, true, false);
      this.buildStairCircle(world, i, j + 1, k, 9, false, true);
      this.buildStairCircle(world, i, j + 6, k, 13, false, false);
      this.buildStairCircle(world, i, j + 7, k, 12, false, false);
      this.func_150516_a(world, i + 3, j + 3, k, LOTRWorldGenElfHouse.getRandomTorch(random), 1);
      this.func_150516_a(world, i - 3, j + 3, k, LOTRWorldGenElfHouse.getRandomTorch(random), 2);
      this.func_150516_a(world, i, j + 3, k + 3, LOTRWorldGenElfHouse.getRandomTorch(random), 3);
      this.func_150516_a(world, i, j + 3, k - 3, LOTRWorldGenElfHouse.getRandomTorch(random), 4);

      for(i1 = i - 3; i1 <= i + 3; ++i1) {
         this.func_150516_a(world, i1, j + 5, k - 3, LOTRMod.stairsMallorn, 6);
         this.func_150516_a(world, i1, j + 5, k + 3, LOTRMod.stairsMallorn, 7);
      }

      for(i1 = k - 2; i1 <= k + 2; ++i1) {
         this.func_150516_a(world, i - 3, j + 5, i1, LOTRMod.stairsMallorn, 4);
         this.func_150516_a(world, i + 3, j + 5, i1, LOTRMod.stairsMallorn, 5);
      }

      for(i1 = i - 4; i1 <= i + 4; i1 += 8) {
         for(k1 = k - 4; k1 <= k + 4; k1 += 8) {
            for(i2 = j + 1; i2 <= j + 5; ++i2) {
               this.func_150516_a(world, i1, i2, k1, LOTRMod.wood, 1);
            }

            this.func_150516_a(world, i1 + 1, j + 3, k1, LOTRWorldGenElfHouse.getRandomTorch(random), 1);
            this.func_150516_a(world, i1 - 1, j + 3, k1, LOTRWorldGenElfHouse.getRandomTorch(random), 2);
            this.func_150516_a(world, i1, j + 3, k1 + 1, LOTRWorldGenElfHouse.getRandomTorch(random), 3);
            this.func_150516_a(world, i1, j + 3, k1 - 1, LOTRWorldGenElfHouse.getRandomTorch(random), 4);
         }
      }

      this.func_150516_a(world, i - 5, j + 1, k - 5, LOTRMod.elvenTable, 0);
      this.func_150516_a(world, i - 5, j + 1, k - 4, LOTRMod.stairsMallorn, 7);
      this.placeFlowerPot(world, i - 5, j + 2, k - 4, LOTRWorldGenElfHouse.getRandomPlant(random));
      this.func_150516_a(world, i - 4, j + 1, k - 5, LOTRMod.stairsMallorn, 5);
      this.placeFlowerPot(world, i - 4, j + 2, k - 5, LOTRWorldGenElfHouse.getRandomPlant(random));
      this.func_150516_a(world, i + 5, j + 1, k - 5, LOTRMod.elvenTable, 0);
      this.func_150516_a(world, i + 5, j + 1, k - 4, LOTRMod.stairsMallorn, 7);
      this.placeFlowerPot(world, i + 5, j + 2, k - 4, LOTRWorldGenElfHouse.getRandomPlant(random));
      this.func_150516_a(world, i + 4, j + 1, k - 5, LOTRMod.stairsMallorn, 4);
      this.placeFlowerPot(world, i + 4, j + 2, k - 5, LOTRWorldGenElfHouse.getRandomPlant(random));
      this.func_150516_a(world, i - 5, j + 1, k + 5, LOTRMod.elvenTable, 0);
      this.func_150516_a(world, i - 5, j + 1, k + 4, LOTRMod.stairsMallorn, 6);
      this.placeFlowerPot(world, i - 5, j + 2, k + 4, LOTRWorldGenElfHouse.getRandomPlant(random));
      this.func_150516_a(world, i - 4, j + 1, k + 5, LOTRMod.stairsMallorn, 5);
      this.placeFlowerPot(world, i - 4, j + 2, k + 5, LOTRWorldGenElfHouse.getRandomPlant(random));
      this.func_150516_a(world, i + 5, j + 1, k + 5, LOTRMod.elvenTable, 0);
      this.func_150516_a(world, i + 5, j + 1, k + 4, LOTRMod.stairsMallorn, 6);
      this.placeFlowerPot(world, i + 5, j + 2, k + 4, LOTRWorldGenElfHouse.getRandomPlant(random));
      this.func_150516_a(world, i + 4, j + 1, k + 5, LOTRMod.stairsMallorn, 4);
      this.placeFlowerPot(world, i + 4, j + 2, k + 5, LOTRWorldGenElfHouse.getRandomPlant(random));
      this.placeRandomChandelier(world, random, i - 8, j + 5, k);
      this.placeRandomChandelier(world, random, i + 8, j + 5, k);
      this.placeRandomChandelier(world, random, i, j + 5, k - 8);
      this.placeRandomChandelier(world, random, i, j + 5, k + 8);

      for(i1 = i - 8; i1 <= i + 8; i1 += 16) {
         for(k1 = k - 8; k1 <= k + 8; k1 += 16) {
            for(i2 = j + 2; i2 <= j + 5; ++i2) {
               this.func_150516_a(world, i1, i2, k1, LOTRMod.planks, 1);
            }

            for(i2 = i1 - 1; i2 <= i1 + 1; ++i2) {
               this.func_150516_a(world, i2, j + 5, k1 - 1, LOTRMod.stairsMallorn, 6);
               this.func_150516_a(world, i2, j + 5, k1 + 1, LOTRMod.stairsMallorn, 7);
            }

            this.func_150516_a(world, i1 - 1, j + 5, k1, LOTRMod.stairsMallorn, 4);
            this.func_150516_a(world, i1 + 1, j + 5, k1, LOTRMod.stairsMallorn, 5);
         }
      }

      for(i1 = j + 2; i1 <= j + 5; ++i1) {
         this.func_150516_a(world, i - 12, i1, k - 4, LOTRMod.wood, 1);
         this.func_150516_a(world, i - 12, i1, k + 4, LOTRMod.wood, 1);
         this.func_150516_a(world, i + 12, i1, k - 4, LOTRMod.wood, 1);
         this.func_150516_a(world, i + 12, i1, k + 4, LOTRMod.wood, 1);
         this.func_150516_a(world, i - 4, i1, k - 12, LOTRMod.wood, 1);
         this.func_150516_a(world, i + 4, i1, k - 12, LOTRMod.wood, 1);
         this.func_150516_a(world, i - 4, i1, k + 12, LOTRMod.wood, 1);
         this.func_150516_a(world, i + 4, i1, k + 12, LOTRMod.wood, 1);
      }

      for(i1 = k - 5; i1 <= k + 5; ++i1) {
         if (Math.abs(i1 - k) <= 2) {
            this.func_150516_a(world, i - 12, j + 5, i1, LOTRMod.woodSlabSingle, 9);
            this.func_150516_a(world, i + 12, j + 5, i1, LOTRMod.woodSlabSingle, 9);
         } else {
            this.func_150516_a(world, i - 11, j + 5, i1, LOTRMod.stairsMallorn, 5);
            this.func_150516_a(world, i + 11, j + 5, i1, LOTRMod.stairsMallorn, 4);
         }

         if (i1 - k != -5 && i1 - k != 3) {
            if (i1 - k == -3 || i1 - k == 5) {
               this.func_150516_a(world, i - 12, j + 5, i1, LOTRMod.stairsMallorn, 7);
               this.func_150516_a(world, i + 12, j + 5, i1, LOTRMod.stairsMallorn, 7);
            }
         } else {
            this.func_150516_a(world, i - 12, j + 5, i1, LOTRMod.stairsMallorn, 6);
            this.func_150516_a(world, i + 12, j + 5, i1, LOTRMod.stairsMallorn, 6);
         }
      }

      for(i1 = i - 5; i1 <= i + 5; ++i1) {
         if (Math.abs(i1 - i) <= 2) {
            this.func_150516_a(world, i1, j + 5, k - 12, LOTRMod.woodSlabSingle, 9);
            this.func_150516_a(world, i1, j + 5, k + 12, LOTRMod.woodSlabSingle, 9);
         } else {
            this.func_150516_a(world, i1, j + 5, k - 11, LOTRMod.stairsMallorn, 7);
            this.func_150516_a(world, i1, j + 5, k + 11, LOTRMod.stairsMallorn, 6);
         }

         if (i1 - i != -5 && i1 - i != 3) {
            if (i1 - i == -3 || i1 - i == 5) {
               this.func_150516_a(world, i1, j + 5, k - 12, LOTRMod.stairsMallorn, 5);
               this.func_150516_a(world, i1, j + 5, k + 12, LOTRMod.stairsMallorn, 5);
            }
         } else {
            this.func_150516_a(world, i1, j + 5, k - 12, LOTRMod.stairsMallorn, 4);
            this.func_150516_a(world, i1, j + 5, k + 12, LOTRMod.stairsMallorn, 4);
         }
      }

      this.func_150516_a(world, i + 6, j + 1, k, LOTRMod.elvenBed, 3);
      this.func_150516_a(world, i + 7, j + 1, k, LOTRMod.elvenBed, 11);
      this.func_150516_a(world, i, j + 1, k + 7, LOTRMod.commandTable, 0);
      this.placeBanner(world, i, j + 2, k - 11, 0, LOTRItemBanner.BannerType.GALADHRIM);
      this.placeBanner(world, i + 11, j + 2, k, 1, LOTRItemBanner.BannerType.GALADHRIM);
      this.placeBanner(world, i, j + 2, k + 11, 2, LOTRItemBanner.BannerType.GALADHRIM);
      this.placeBanner(world, i - 11, j + 2, k, 3, LOTRItemBanner.BannerType.GALADHRIM);
      this.tryPlaceLight(world, i - 12, j, k - 2, random);
      this.tryPlaceLight(world, i - 12, j, k + 2, random);
      this.tryPlaceLight(world, i - 9, j, k + 9, random);
      this.tryPlaceLight(world, i - 2, j, k + 12, random);
      this.tryPlaceLight(world, i + 2, j, k + 12, random);
      this.tryPlaceLight(world, i + 9, j, k + 9, random);
      this.tryPlaceLight(world, i + 12, j, k + 2, random);
      this.tryPlaceLight(world, i + 12, j, k - 2, random);
      this.tryPlaceLight(world, i + 9, j, k - 9, random);
      this.tryPlaceLight(world, i + 2, j, k - 12, random);
      this.tryPlaceLight(world, i - 2, j, k - 12, random);
      this.tryPlaceLight(world, i - 9, j, k - 9, random);

      for(i1 = i - 4; i1 <= i - 3; ++i1) {
         for(k1 = k - 3; k1 <= k; ++k1) {
            this.func_150516_a(world, i1, j, k1, Blocks.field_150350_a, 0);
         }

         this.func_150516_a(world, i1, j, k - 3, LOTRMod.stairsMallorn, 3);
      }

      LOTREntityElf elfLord = new LOTREntityGaladhrimLord(world);
      elfLord.func_70012_b((double)i + 0.5D, (double)(j + 1), (double)k + 3.5D, 0.0F, 0.0F);
      elfLord.spawnRidingHorse = false;
      elfLord.func_110161_a((IEntityLivingData)null);
      elfLord.setPersistentAndTraderShouldRespawn();
      elfLord.func_110171_b(i, j, k, 8);
      world.func_72838_d(elfLord);
      return true;
   }

   private void buildStaircase(World world, Random random, int i, int j, int k) {
      int i1 = i - 3;
      int j1 = j - 1;
      int k1 = k - 2;

      for(int l = 0; j1 >= 0; ++l) {
         Block block = world.func_147439_a(i1, j1, k1);
         if (block.func_149662_c() && !block.isWood(world, i1, j1, k1)) {
            break;
         }

         int l1 = l % 24;
         int k2;
         int k2;
         if (l1 < 5) {
            for(k2 = i1; k2 >= i1 - 2; --k2) {
               for(k2 = j1 + 1; k2 <= j1 + 3; ++k2) {
                  this.func_150516_a(world, k2, k2, k1, Blocks.field_150350_a, 0);
               }
            }

            this.func_150516_a(world, i1, j1, k1, LOTRMod.stairsMallorn, 3);
            this.func_150516_a(world, i1 - 1, j1, k1, LOTRMod.stairsMallorn, 3);
            this.func_150516_a(world, i1 - 2, j1, k1, LOTRMod.stairsMallorn, 4);
            this.func_150516_a(world, i1 - 2, j1 + 1, k1, LOTRMod.fence, 1);
            if (l1 > 0) {
               this.func_150516_a(world, i1 - 2, j1 + 2, k1, LOTRMod.fence, 1);
            }

            --j1;
            ++k1;
         } else {
            int k2;
            if (l1 == 5) {
               for(k2 = i1; k2 >= i1 - 2; --k2) {
                  for(k2 = j1 + 1; k2 <= j1 + 3; ++k2) {
                     for(k2 = k1; k2 <= k1 + 2; ++k2) {
                        this.func_150516_a(world, k2, k2, k2, Blocks.field_150350_a, 0);
                     }
                  }
               }

               for(k2 = i1; k2 >= i1 - 1; --k2) {
                  for(k2 = k1; k2 <= k1 + 1; ++k2) {
                     this.func_150516_a(world, k2, j1, k2, LOTRMod.planks, 1);
                  }
               }

               for(k2 = k1; k2 <= k1 + 2; ++k2) {
                  this.func_150516_a(world, i1 - 2, j1, k2, LOTRMod.stairsMallorn, 4);
                  this.func_150516_a(world, i1 - 2, j1 + 1, k2, LOTRMod.fence, 1);
               }

               for(k2 = i1; k2 >= i1 - 1; --k2) {
                  this.func_150516_a(world, k2, j1, k1 + 2, LOTRMod.stairsMallorn, 7);
                  this.func_150516_a(world, k2, j1 + 1, k1 + 2, LOTRMod.fence, 1);
               }

               this.func_150516_a(world, i1 - 2, j1 + 2, k1, LOTRMod.fence, 1);
               this.func_150516_a(world, i1 - 2, j1 + 2, k1 + 2, LOTRWorldGenElfHouse.getRandomTorch(random), 5);
               ++i1;
            } else if (l1 < 11) {
               for(k2 = k1; k2 <= k1 + 2; ++k2) {
                  for(k2 = j1 + 1; k2 <= j1 + 3; ++k2) {
                     this.func_150516_a(world, i1, k2, k2, Blocks.field_150350_a, 0);
                  }
               }

               this.func_150516_a(world, i1, j1, k1, LOTRMod.stairsMallorn, 1);
               this.func_150516_a(world, i1, j1, k1 + 1, LOTRMod.stairsMallorn, 1);
               this.func_150516_a(world, i1, j1, k1 + 2, LOTRMod.stairsMallorn, 7);
               this.func_150516_a(world, i1, j1 + 1, k1 + 2, LOTRMod.fence, 1);
               if (l1 > 6) {
                  this.func_150516_a(world, i1, j1 + 2, k1 + 2, LOTRMod.fence, 1);
               }

               --j1;
               ++i1;
            } else if (l1 == 11) {
               for(k2 = i1; k2 <= i1 + 2; ++k2) {
                  for(k2 = j1 + 1; k2 <= j1 + 3; ++k2) {
                     for(k2 = k1; k2 <= k1 + 2; ++k2) {
                        this.func_150516_a(world, k2, k2, k2, Blocks.field_150350_a, 0);
                     }
                  }
               }

               for(k2 = i1; k2 <= i1 + 1; ++k2) {
                  for(k2 = k1; k2 <= k1 + 1; ++k2) {
                     this.func_150516_a(world, k2, j1, k2, LOTRMod.planks, 1);
                  }
               }

               for(k2 = i1; k2 <= i1 + 2; ++k2) {
                  this.func_150516_a(world, k2, j1, k1 + 2, LOTRMod.stairsMallorn, 7);
                  this.func_150516_a(world, k2, j1 + 1, k1 + 2, LOTRMod.fence, 1);
               }

               for(k2 = k1; k2 <= k1 + 1; ++k2) {
                  this.func_150516_a(world, i1 + 2, j1, k2, LOTRMod.stairsMallorn, 5);
                  this.func_150516_a(world, i1 + 2, j1 + 1, k2, LOTRMod.fence, 1);
               }

               this.func_150516_a(world, i1, j1 + 2, k1 + 2, LOTRMod.fence, 1);
               this.func_150516_a(world, i1 + 2, j1 + 2, k1 + 2, LOTRWorldGenElfHouse.getRandomTorch(random), 5);
               --k1;
            } else if (l1 < 17) {
               for(k2 = i1; k2 <= i1 + 2; ++k2) {
                  for(k2 = j1 + 1; k2 <= j1 + 3; ++k2) {
                     this.func_150516_a(world, k2, k2, k1, Blocks.field_150350_a, 0);
                  }
               }

               this.func_150516_a(world, i1, j1, k1, LOTRMod.stairsMallorn, 2);
               this.func_150516_a(world, i1 + 1, j1, k1, LOTRMod.stairsMallorn, 2);
               this.func_150516_a(world, i1 + 2, j1, k1, LOTRMod.stairsMallorn, 5);
               this.func_150516_a(world, i1 + 2, j1 + 1, k1, LOTRMod.fence, 1);
               if (l1 > 12) {
                  this.func_150516_a(world, i1 + 2, j1 + 2, k1, LOTRMod.fence, 1);
               }

               --j1;
               --k1;
            } else if (l1 == 17) {
               for(k2 = i1; k2 <= i1 + 2; ++k2) {
                  for(k2 = j1 + 1; k2 <= j1 + 3; ++k2) {
                     for(k2 = k1; k2 >= k1 - 2; --k2) {
                        this.func_150516_a(world, k2, k2, k2, Blocks.field_150350_a, 0);
                     }
                  }
               }

               for(k2 = i1; k2 <= i1 + 1; ++k2) {
                  for(k2 = k1; k2 >= k1 - 1; --k2) {
                     this.func_150516_a(world, k2, j1, k2, LOTRMod.planks, 1);
                  }
               }

               for(k2 = k1; k2 >= k1 - 2; --k2) {
                  this.func_150516_a(world, i1 + 2, j1, k2, LOTRMod.stairsMallorn, 5);
                  this.func_150516_a(world, i1 + 2, j1 + 1, k2, LOTRMod.fence, 1);
               }

               for(k2 = i1; k2 <= i1 + 1; ++k2) {
                  this.func_150516_a(world, k2, j1, k1 - 2, LOTRMod.stairsMallorn, 6);
                  this.func_150516_a(world, k2, j1 + 1, k1 - 2, LOTRMod.fence, 1);
               }

               this.func_150516_a(world, i1 + 2, j1 + 2, k1, LOTRMod.fence, 1);
               this.func_150516_a(world, i1 + 2, j1 + 2, k1 - 2, LOTRWorldGenElfHouse.getRandomTorch(random), 5);
               --i1;
            } else if (l1 < 23) {
               for(k2 = k1; k2 >= k1 - 2; --k2) {
                  for(k2 = j1 + 1; k2 <= j1 + 3; ++k2) {
                     this.func_150516_a(world, i1, k2, k2, Blocks.field_150350_a, 0);
                  }
               }

               this.func_150516_a(world, i1, j1, k1, LOTRMod.stairsMallorn, 0);
               this.func_150516_a(world, i1, j1, k1 - 1, LOTRMod.stairsMallorn, 0);
               this.func_150516_a(world, i1, j1, k1 - 2, LOTRMod.stairsMallorn, 6);
               this.func_150516_a(world, i1, j1 + 1, k1 - 2, LOTRMod.fence, 1);
               if (l1 > 18) {
                  this.func_150516_a(world, i1, j1 + 2, k1 - 2, LOTRMod.fence, 1);
               }

               --j1;
               --i1;
            } else if (l1 == 23) {
               for(k2 = i1; k2 >= i1 - 2; --k2) {
                  for(k2 = j1 + 1; k2 <= j1 + 3; ++k2) {
                     for(k2 = k1; k2 >= k1 - 2; --k2) {
                        this.func_150516_a(world, k2, k2, k2, Blocks.field_150350_a, 0);
                     }
                  }
               }

               for(k2 = i1; k2 >= i1 - 1; --k2) {
                  for(k2 = k1; k2 >= k1 - 1; --k2) {
                     this.func_150516_a(world, k2, j1, k2, LOTRMod.planks, 1);
                  }
               }

               for(k2 = i1; k2 >= i1 - 2; --k2) {
                  this.func_150516_a(world, k2, j1, k1 - 2, LOTRMod.stairsMallorn, 6);
                  this.func_150516_a(world, k2, j1 + 1, k1 - 2, LOTRMod.fence, 1);
               }

               for(k2 = k1; k2 >= k1 - 1; --k2) {
                  this.func_150516_a(world, i1 - 2, j1, k2, LOTRMod.stairsMallorn, 4);
                  this.func_150516_a(world, i1 - 2, j1 + 1, k2, LOTRMod.fence, 1);
               }

               this.func_150516_a(world, i1, j1 + 2, k1 - 2, LOTRMod.fence, 1);
               this.func_150516_a(world, i1 - 2, j1 + 2, k1 - 2, LOTRWorldGenElfHouse.getRandomTorch(random), 5);
               ++k1;
            }
         }
      }

   }

   private void buildStairCircle(World world, int i, int j, int k, int range, boolean upsideDown, boolean insideOut) {
      for(int i1 = i - range; i1 <= i + range; ++i1) {
         for(int k1 = k - range; k1 <= k + range; ++k1) {
            if (world.func_147437_c(i1, j, k1)) {
               int direction = -1;
               if (this.isMallornPlanks(world, i1 - 1, j, k1)) {
                  direction = 1;
               } else if (this.isMallornPlanks(world, i1 + 1, j, k1)) {
                  direction = 3;
               } else if (this.isMallornPlanks(world, i1, j, k1 - 1)) {
                  direction = 2;
               } else if (this.isMallornPlanks(world, i1, j, k1 + 1)) {
                  direction = 0;
               } else if (!this.isMallornPlanks(world, i1 - 1, j, k1 - 1) && !this.isMallornPlanks(world, i1 + 1, j, k1 - 1)) {
                  if (this.isMallornPlanks(world, i1 - 1, j, k1 + 1) || this.isMallornPlanks(world, i1 + 1, j, k1 + 1)) {
                     direction = 0;
                  }
               } else {
                  direction = 2;
               }

               if (direction != -1) {
                  if (insideOut) {
                     direction += 4;
                     direction &= 3;
                  }

                  int meta = 0;
                  switch(direction) {
                  case 0:
                     meta = 2;
                     break;
                  case 1:
                     meta = 1;
                     break;
                  case 2:
                     meta = 3;
                     break;
                  case 3:
                     meta = 0;
                  }

                  if (upsideDown) {
                     meta |= 4;
                  }

                  this.func_150516_a(world, i1, j, k1, LOTRMod.stairsMallorn, meta);
               }
            }
         }
      }

   }

   private boolean isMallornPlanks(World world, int i, int j, int k) {
      return world.func_147439_a(i, j, k) == LOTRMod.planks && world.func_72805_g(i, j, k) == 1;
   }

   private void tryPlaceLight(World world, int i, int j, int k, Random random) {
      int height = 3 + random.nextInt(7);

      int j1;
      for(j1 = j; j1 >= j - height; --j1) {
         if (this.restrictions) {
            if (!world.func_147437_c(i, j1, k)) {
               return;
            }

            if (j1 == j - height && (!world.func_147437_c(i, j1, k - 1) || !world.func_147437_c(i, j1, k + 1) || !world.func_147437_c(i - 1, j1, k) || !world.func_147437_c(i + 1, j1, k))) {
               return;
            }
         }
      }

      for(j1 = j; j1 >= j - height; --j1) {
         if (j1 == j - height) {
            this.func_150516_a(world, i, j1, k, LOTRMod.planks, 1);
            this.func_150516_a(world, i, j1, k - 1, LOTRWorldGenElfHouse.getRandomTorch(random), 4);
            this.func_150516_a(world, i, j1, k + 1, LOTRWorldGenElfHouse.getRandomTorch(random), 3);
            this.func_150516_a(world, i - 1, j1, k, LOTRWorldGenElfHouse.getRandomTorch(random), 2);
            this.func_150516_a(world, i + 1, j1, k, LOTRWorldGenElfHouse.getRandomTorch(random), 1);
         } else {
            this.func_150516_a(world, i, j1, k, LOTRMod.fence, 1);
         }
      }

   }

   private void placeRandomChandelier(World world, Random random, int i, int j, int k) {
      ItemStack itemstack = LOTRWorldGenElfHouse.getRandomChandelier(random);
      this.func_150516_a(world, i, j, k, Block.func_149634_a(itemstack.func_77973_b()), itemstack.func_77960_j());
   }
}
