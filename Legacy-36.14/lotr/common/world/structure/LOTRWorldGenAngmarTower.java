package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityAngmarOrcMercenaryCaptain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.biome.LOTRBiomeGenAngmar;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarTower extends LOTRWorldGenStructureBase {
   public LOTRWorldGenAngmarTower(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions) {
         if (!(world.func_72807_a(i, k) instanceof LOTRBiomeGenAngmar)) {
            return false;
         }

         Block l = world.func_147439_a(i, j - 1, k);
         if (l != Blocks.field_150349_c && l != Blocks.field_150346_d && l != Blocks.field_150348_b) {
            return false;
         }
      }

      --j;
      int rotation = random.nextInt(4);
      if (!this.restrictions && this.usingPlayer != null) {
         rotation = this.usingPlayerRotation();
      }

      switch(rotation) {
      case 0:
         k += 7;
         break;
      case 1:
         i -= 7;
         break;
      case 2:
         k -= 7;
         break;
      case 3:
         i += 7;
      }

      int sections = 2 + random.nextInt(3);
      int k1;
      int k1;
      int j1;
      if (this.restrictions) {
         for(k1 = i - 7; k1 <= i + 7; ++k1) {
            for(k1 = k - 7; k1 <= k + 7; ++k1) {
               j1 = world.func_72976_f(k1, k1) - 1;
               Block block = world.func_147439_a(k1, j1, k1);
               if (block != Blocks.field_150349_c && block != Blocks.field_150348_b && block != Blocks.field_150346_d && !block.isWood(world, k1, j1, k1) && !block.isLeaves(world, k1, j1, k1)) {
                  return false;
               }
            }
         }
      }

      for(k1 = k - 2; k1 <= k + 2; ++k1) {
         for(k1 = j; !LOTRMod.isOpaque(world, i - 6, k1, k1) && k1 >= 0; --k1) {
            this.func_150516_a(world, i - 6, k1, k1, LOTRMod.brick2, 0);
            this.setGrassToDirt(world, i - 6, k1 - 1, k1);
         }

         for(k1 = j; !LOTRMod.isOpaque(world, i + 6, k1, k1) && k1 >= 0; --k1) {
            this.func_150516_a(world, i + 6, k1, k1, LOTRMod.brick2, 0);
            this.setGrassToDirt(world, i + 6, k1 - 1, k1);
         }
      }

      for(k1 = k - 4; k1 <= k + 4; ++k1) {
         for(k1 = j; !LOTRMod.isOpaque(world, i - 5, k1, k1) && k1 >= 0; --k1) {
            this.func_150516_a(world, i - 5, k1, k1, LOTRMod.brick2, 0);
            this.setGrassToDirt(world, i - 5, k1 - 1, k1);
         }

         for(k1 = j; !LOTRMod.isOpaque(world, i + 5, k1, k1) && k1 >= 0; --k1) {
            this.func_150516_a(world, i + 5, k1, k1, LOTRMod.brick2, 0);
            this.setGrassToDirt(world, i + 5, k1 - 1, k1);
         }
      }

      for(k1 = k - 5; k1 <= k + 5; ++k1) {
         for(k1 = i - 4; k1 <= i - 3; ++k1) {
            for(j1 = j; !LOTRMod.isOpaque(world, k1, j1, k1) && j1 >= 0; --j1) {
               this.func_150516_a(world, k1, j1, k1, LOTRMod.brick2, 0);
               this.setGrassToDirt(world, k1, j1 - 1, k1);
            }
         }

         for(k1 = i + 3; k1 <= i + 4; ++k1) {
            for(j1 = j; !LOTRMod.isOpaque(world, k1, j1, k1) && j1 >= 0; --j1) {
               this.func_150516_a(world, k1, j1, k1, LOTRMod.brick2, 0);
               this.setGrassToDirt(world, k1, j1 - 1, k1);
            }
         }
      }

      for(k1 = k - 6; k1 <= k + 6; ++k1) {
         for(k1 = i - 2; k1 <= i + 2; ++k1) {
            for(j1 = j; !LOTRMod.isOpaque(world, k1, j1, k1) && j1 >= 0; --j1) {
               this.func_150516_a(world, k1, j1, k1, LOTRMod.brick2, 0);
               this.setGrassToDirt(world, k1, j1 - 1, k1);
            }
         }
      }

      for(k1 = 0; k1 <= sections; ++k1) {
         this.generateTowerSection(world, random, i, j, k, k1, false);
      }

      this.generateTowerSection(world, random, i, j, k, sections + 1, true);
      LOTREntityAngmarOrcMercenaryCaptain trader = new LOTREntityAngmarOrcMercenaryCaptain(world);
      trader.func_70012_b((double)(i - 2) + 0.5D, (double)(j + (sections + 1) * 8 + 1), (double)k + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
      trader.func_110161_a((IEntityLivingData)null);
      trader.setPersistentAndTraderShouldRespawn();
      trader.func_110171_b(i, j + (sections + 1) * 8, k, 24);
      world.func_72838_d(trader);
      switch(rotation) {
      case 0:
         for(k1 = i - 1; k1 <= i + 1; ++k1) {
            this.func_150516_a(world, k1, j, k - 6, Blocks.field_150417_aV, 0);

            for(j1 = j + 1; j1 <= j + 4; ++j1) {
               this.func_150516_a(world, k1, j1, k - 6, Blocks.field_150350_a, 0);
            }
         }

         this.func_150516_a(world, i, j + 7, k - 6, LOTRMod.brick2, 0);
         this.placeWallBanner(world, i, j + 7, k - 6, 2, LOTRItemBanner.BannerType.ANGMAR);
         break;
      case 1:
         for(k1 = k - 1; k1 <= k + 1; ++k1) {
            this.func_150516_a(world, i + 6, j, k1, Blocks.field_150417_aV, 0);

            for(j1 = j + 1; j1 <= j + 4; ++j1) {
               this.func_150516_a(world, i + 6, j1, k1, Blocks.field_150350_a, 0);
            }
         }

         this.func_150516_a(world, i + 6, j + 7, k, LOTRMod.brick2, 0);
         this.placeWallBanner(world, i + 6, j + 7, k, 3, LOTRItemBanner.BannerType.ANGMAR);
         break;
      case 2:
         for(k1 = i - 1; k1 <= i + 1; ++k1) {
            this.func_150516_a(world, k1, j, k + 6, Blocks.field_150417_aV, 0);

            for(j1 = j + 1; j1 <= j + 4; ++j1) {
               this.func_150516_a(world, k1, j1, k + 6, Blocks.field_150350_a, 0);
            }
         }

         this.func_150516_a(world, i, j + 7, k + 6, LOTRMod.brick2, 0);
         this.placeWallBanner(world, i, j + 7, k + 6, 0, LOTRItemBanner.BannerType.ANGMAR);
         break;
      case 3:
         for(k1 = k - 1; k1 <= k + 1; ++k1) {
            this.func_150516_a(world, i - 6, j, k1, Blocks.field_150417_aV, 0);

            for(j1 = j + 1; j1 <= j + 4; ++j1) {
               this.func_150516_a(world, i - 6, j1, k1, Blocks.field_150350_a, 0);
            }
         }

         this.func_150516_a(world, i - 6, j + 7, k, LOTRMod.brick2, 0);
         this.placeWallBanner(world, i - 6, j + 7, k, 1, LOTRItemBanner.BannerType.ANGMAR);
      }

      int radius = 6;

      for(j1 = 0; j1 < 16; ++j1) {
         int i1 = i - random.nextInt(radius * 2) + random.nextInt(radius * 2);
         int k1 = k - random.nextInt(radius * 2) + random.nextInt(radius * 2);
         int j1 = world.func_72976_f(i1, k1);
         Block id = world.func_147439_a(i1, j1 - 1, k1);
         if (id == Blocks.field_150349_c || id == Blocks.field_150346_d || id == Blocks.field_150348_b) {
            int randomFeature = random.nextInt(4);
            boolean flag = true;
            if (randomFeature == 0) {
               if (!LOTRMod.isOpaque(world, i1, j1, k1)) {
                  if (random.nextInt(3) == 0) {
                     this.func_150516_a(world, i1, j1, k1, LOTRMod.slabSingle3, 4);
                  } else {
                     this.func_150516_a(world, i1, j1, k1, LOTRMod.slabSingle3, 3);
                  }
               }
            } else {
               int j2;
               for(j2 = j1; j2 < j1 + randomFeature && flag; ++j2) {
                  flag = !LOTRMod.isOpaque(world, i1, j2, k1);
               }

               if (flag) {
                  for(j2 = j1; j2 < j1 + randomFeature; ++j2) {
                     if (random.nextBoolean()) {
                        this.func_150516_a(world, i1, j2, k1, LOTRMod.brick2, 0);
                     } else {
                        this.func_150516_a(world, i1, j2, k1, LOTRMod.brick2, 1);
                     }
                  }
               }
            }

            if (world.func_147439_a(i1, j1 - 1, k1) == Blocks.field_150346_d) {
               this.func_150516_a(world, i1, j1 - 1, k1, Blocks.field_150346_d, 0);
            }
         }
      }

      return true;
   }

   private void generateTowerSection(World world, Random random, int i, int j, int k, int section, boolean isTop) {
      j += section * 8;

      int j1;
      for(j1 = section == 0 ? j : j + 1; j1 <= (isTop ? j + 10 : j + 8); ++j1) {
         Block fillBlock = Blocks.field_150350_a;
         int fillMeta = false;
         byte fillMeta;
         if (j1 == j) {
            fillBlock = Blocks.field_150417_aV;
            fillMeta = 0;
         } else {
            fillBlock = Blocks.field_150350_a;
            fillMeta = 0;
         }

         boolean hasCeiling = j1 == j + 8 && !isTop;

         int k1;
         for(k1 = k - 2; k1 <= k + 2; ++k1) {
            this.func_150516_a(world, i - 5, j1, k1, fillBlock, fillMeta);
            this.func_150516_a(world, i + 5, j1, k1, fillBlock, fillMeta);
            if (hasCeiling && random.nextInt(20) != 0) {
               this.func_150516_a(world, i - 5, j1, k1, LOTRMod.slabSingle3, 11);
            }

            if (hasCeiling && random.nextInt(20) != 0) {
               this.func_150516_a(world, i + 5, j1, k1, LOTRMod.slabSingle3, 11);
            }
         }

         int i1;
         for(k1 = k - 4; k1 <= k + 4; ++k1) {
            for(i1 = i - 4; i1 <= i - 3; ++i1) {
               this.func_150516_a(world, i1, j1, k1, fillBlock, fillMeta);
               if (hasCeiling && random.nextInt(20) != 0) {
                  this.func_150516_a(world, i1, j1, k1, LOTRMod.slabSingle3, 11);
               }
            }

            for(i1 = i + 3; i1 <= i + 4; ++i1) {
               this.func_150516_a(world, i1, j1, k1, fillBlock, fillMeta);
               if (hasCeiling && random.nextInt(20) != 0) {
                  this.func_150516_a(world, i1, j1, k1, LOTRMod.slabSingle3, 11);
               }
            }
         }

         for(k1 = k - 5; k1 <= k + 5; ++k1) {
            for(i1 = i - 2; i1 <= i + 2; ++i1) {
               this.func_150516_a(world, i1, j1, k1, fillBlock, fillMeta);
               if (hasCeiling && random.nextInt(20) != 0) {
                  this.func_150516_a(world, i1, j1, k1, LOTRMod.slabSingle3, 11);
               }
            }
         }
      }

      int j1;
      for(j1 = j + 1; j1 <= (isTop ? j + 1 : j + 8); ++j1) {
         for(j1 = k - 2; j1 <= k + 2; ++j1) {
            this.placeRandomBrick(world, random, i - 6, j1, j1);
            this.placeRandomBrick(world, random, i + 6, j1, j1);
         }

         for(j1 = i - 2; j1 <= i + 2; ++j1) {
            this.placeRandomBrick(world, random, j1, j1, k - 6);
            this.placeRandomBrick(world, random, j1, j1, k + 6);
         }

         this.placeRandomBrick(world, random, i - 5, j1, k - 4);
         this.placeRandomBrick(world, random, i - 5, j1, k - 3);
         this.placeRandomBrick(world, random, i - 5, j1, k + 3);
         this.placeRandomBrick(world, random, i - 5, j1, k + 4);
         this.placeRandomBrick(world, random, i - 4, j1, k - 5);
         this.placeRandomBrick(world, random, i - 4, j1, k + 5);
         this.placeRandomBrick(world, random, i - 3, j1, k - 5);
         this.placeRandomBrick(world, random, i - 3, j1, k + 5);
         this.placeRandomBrick(world, random, i + 3, j1, k - 5);
         this.placeRandomBrick(world, random, i + 3, j1, k + 5);
         this.placeRandomBrick(world, random, i + 4, j1, k - 5);
         this.placeRandomBrick(world, random, i + 4, j1, k + 5);
         this.placeRandomBrick(world, random, i + 5, j1, k - 4);
         this.placeRandomBrick(world, random, i + 5, j1, k - 3);
         this.placeRandomBrick(world, random, i + 5, j1, k + 3);
         this.placeRandomBrick(world, random, i + 5, j1, k + 4);
      }

      if (!isTop) {
         for(j1 = j + 2; j1 <= j + 4; ++j1) {
            for(j1 = k - 1; j1 <= k + 1; ++j1) {
               if (random.nextInt(3) != 0) {
                  this.func_150516_a(world, i - 6, j1, j1, LOTRMod.orcSteelBars, 0);
               } else {
                  this.func_150516_a(world, i - 6, j1, j1, Blocks.field_150350_a, 0);
               }

               if (random.nextInt(3) != 0) {
                  this.func_150516_a(world, i + 6, j1, j1, LOTRMod.orcSteelBars, 0);
               } else {
                  this.func_150516_a(world, i + 6, j1, j1, Blocks.field_150350_a, 0);
               }
            }

            for(j1 = i - 1; j1 <= i + 1; ++j1) {
               if (random.nextInt(3) != 0) {
                  this.func_150516_a(world, j1, j1, k - 6, LOTRMod.orcSteelBars, 0);
               } else {
                  this.func_150516_a(world, j1, j1, k - 6, Blocks.field_150350_a, 0);
               }

               if (random.nextInt(3) != 0) {
                  this.func_150516_a(world, j1, j1, k + 6, LOTRMod.orcSteelBars, 0);
               } else {
                  this.func_150516_a(world, j1, j1, k + 6, Blocks.field_150350_a, 0);
               }
            }
         }

         for(j1 = i - 2; j1 <= i + 2; ++j1) {
            for(j1 = k - 2; j1 <= k + 2; ++j1) {
               this.func_150516_a(world, j1, j + 8, j1, Blocks.field_150350_a, 0);
            }
         }

         this.func_150516_a(world, i - 2, j + 1, k + 1, LOTRMod.slabSingle3, 3);
         this.func_150516_a(world, i - 2, j + 1, k + 2, LOTRMod.slabSingle3, 11);
         this.func_150516_a(world, i - 1, j + 2, k + 2, LOTRMod.slabSingle3, 3);
         this.func_150516_a(world, i, j + 2, k + 2, LOTRMod.slabSingle3, 11);
         this.func_150516_a(world, i + 1, j + 3, k + 2, LOTRMod.slabSingle3, 3);
         this.func_150516_a(world, i + 2, j + 3, k + 2, LOTRMod.slabSingle3, 11);
         this.func_150516_a(world, i + 2, j + 4, k + 1, LOTRMod.slabSingle3, 3);
         this.func_150516_a(world, i + 2, j + 4, k, LOTRMod.slabSingle3, 11);
         this.func_150516_a(world, i + 2, j + 5, k - 1, LOTRMod.slabSingle3, 3);
         this.func_150516_a(world, i + 2, j + 5, k - 2, LOTRMod.slabSingle3, 11);
         this.func_150516_a(world, i + 1, j + 6, k - 2, LOTRMod.slabSingle3, 3);
         this.func_150516_a(world, i, j + 6, k - 2, LOTRMod.slabSingle3, 11);
         this.func_150516_a(world, i - 1, j + 7, k - 2, LOTRMod.slabSingle3, 3);
         this.func_150516_a(world, i - 2, j + 7, k - 2, LOTRMod.slabSingle3, 11);
         this.func_150516_a(world, i - 2, j + 8, k - 1, LOTRMod.slabSingle3, 3);
         this.func_150516_a(world, i - 2, j + 8, k, LOTRMod.slabSingle3, 11);
      }

      int i1;
      for(j1 = i - 1; j1 <= i + 1; ++j1) {
         for(j1 = k - 1; j1 <= k + 1; ++j1) {
            for(i1 = j + 1; i1 <= (isTop ? j + 3 : j + 8); ++i1) {
               this.placeRandomBrick(world, random, j1, i1, j1);
            }
         }
      }

      if (isTop) {
         j1 = 4 + random.nextInt(5);

         for(j1 = j + 1; j1 <= j + j1; ++j1) {
            for(i1 = k - 1; i1 <= k + 1; ++i1) {
               this.placeRandomBrick(world, random, i - 7, j1, i1);
               this.placeRandomBrick(world, random, i + 7, j1, i1);
            }

            for(i1 = i - 1; i1 <= i + 1; ++i1) {
               this.placeRandomBrick(world, random, i1, j1, k - 7);
               this.placeRandomBrick(world, random, i1, j1, k + 7);
            }
         }

         for(j1 = k - 1; j1 <= k + 1; ++j1) {
            this.placeRandomStairs(world, random, i - 7, j, j1, 4);
            this.placeRandomStairs(world, random, i - 6, j + 2, j1, 1);
            this.placeRandomStairs(world, random, i + 7, j, j1, 5);
            this.placeRandomStairs(world, random, i + 6, j + 2, j1, 0);
         }

         for(j1 = i - 1; j1 <= i + 1; ++j1) {
            this.placeRandomStairs(world, random, j1, j, k - 7, 6);
            this.placeRandomStairs(world, random, j1, j + 2, k - 6, 3);
            this.placeRandomStairs(world, random, j1, j, k + 7, 7);
            this.placeRandomStairs(world, random, j1, j + 2, k + 6, 2);
         }

         for(j1 = j; j1 <= j + 4; ++j1) {
            this.func_150516_a(world, i - 5, j1, k - 5, LOTRMod.brick2, 0);
            this.func_150516_a(world, i - 5, j1, k + 5, LOTRMod.brick2, 0);
            this.func_150516_a(world, i + 5, j1, k - 5, LOTRMod.brick2, 0);
            this.func_150516_a(world, i + 5, j1, k + 5, LOTRMod.brick2, 0);
         }

         this.placeBanner(world, i - 5, j + 5, k - 5, 0, LOTRItemBanner.BannerType.ANGMAR);
         this.placeBanner(world, i - 5, j + 5, k + 5, 0, LOTRItemBanner.BannerType.ANGMAR);
         this.placeBanner(world, i + 5, j + 5, k - 5, 0, LOTRItemBanner.BannerType.ANGMAR);
         this.placeBanner(world, i + 5, j + 5, k + 5, 0, LOTRItemBanner.BannerType.ANGMAR);
         this.placeRandomStairs(world, random, i - 5, j + 2, k - 4, 3);
         this.placeRandomStairs(world, random, i - 4, j + 2, k - 5, 1);
         this.placeRandomStairs(world, random, i - 5, j + 2, k + 4, 2);
         this.placeRandomStairs(world, random, i - 4, j + 2, k + 5, 1);
         this.placeRandomStairs(world, random, i + 5, j + 2, k - 4, 3);
         this.placeRandomStairs(world, random, i + 4, j + 2, k - 5, 0);
         this.placeRandomStairs(world, random, i + 5, j + 2, k + 4, 2);
         this.placeRandomStairs(world, random, i + 4, j + 2, k + 5, 0);
      }

   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(20) != 0) {
         if (random.nextInt(3) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.brick2, 1);
         } else {
            this.func_150516_a(world, i, j, k, LOTRMod.brick2, 0);
         }

      }
   }

   private void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
      if (random.nextInt(10) != 0) {
         if (random.nextInt(3) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.stairsAngmarBrickCracked, meta);
         } else {
            this.func_150516_a(world, i, j, k, LOTRMod.stairsAngmarBrick, meta);
         }

      }
   }
}
