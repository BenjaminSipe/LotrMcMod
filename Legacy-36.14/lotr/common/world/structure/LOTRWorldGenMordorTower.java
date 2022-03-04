package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.entity.npc.LOTREntityMordorOrcMercenaryCaptain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenMordorTower extends LOTRWorldGenStructureBase {
   public LOTRWorldGenMordorTower(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions && !(world.func_72807_a(i, k) instanceof LOTRBiomeGenMordor)) {
         return false;
      } else {
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
         int equipmentSection = 1 + random.nextInt(sections);
         int k1;
         int k1;
         int j1;
         if (this.restrictions) {
            for(k1 = i - 7; k1 <= i + 7; ++k1) {
               for(k1 = k - 7; k1 <= k + 7; ++k1) {
                  j1 = world.func_72976_f(k1, k1) - 1;
                  Block block = world.func_147439_a(k1, j1, k1);
                  int meta = world.func_72805_g(k1, j1, k1);
                  if (block != LOTRMod.mordorDirt && block != LOTRMod.mordorGravel && (block != LOTRMod.rock || meta != 0) && block != Blocks.field_150349_c && block != Blocks.field_150346_d) {
                     return false;
                  }
               }
            }
         }

         for(k1 = k - 2; k1 <= k + 2; ++k1) {
            for(k1 = j; !LOTRMod.isOpaque(world, i - 6, k1, k1) && k1 >= 0; --k1) {
               this.func_150516_a(world, i - 6, k1, k1, LOTRMod.brick, 0);
            }

            for(k1 = j; !LOTRMod.isOpaque(world, i + 6, k1, k1) && k1 >= 0; --k1) {
               this.func_150516_a(world, i + 6, k1, k1, LOTRMod.brick, 0);
            }
         }

         for(k1 = k - 4; k1 <= k + 4; ++k1) {
            for(k1 = j; !LOTRMod.isOpaque(world, i - 5, k1, k1) && k1 >= 0; --k1) {
               this.func_150516_a(world, i - 5, k1, k1, LOTRMod.brick, 0);
            }

            for(k1 = j; !LOTRMod.isOpaque(world, i + 5, k1, k1) && k1 >= 0; --k1) {
               this.func_150516_a(world, i + 5, k1, k1, LOTRMod.brick, 0);
            }
         }

         for(k1 = k - 5; k1 <= k + 5; ++k1) {
            for(k1 = i - 4; k1 <= i - 3; ++k1) {
               for(j1 = j; !LOTRMod.isOpaque(world, k1, j1, k1) && j1 >= 0; --j1) {
                  this.func_150516_a(world, k1, j1, k1, LOTRMod.brick, 0);
               }
            }

            for(k1 = i + 3; k1 <= i + 4; ++k1) {
               for(j1 = j; !LOTRMod.isOpaque(world, k1, j1, k1) && j1 >= 0; --j1) {
                  this.func_150516_a(world, k1, j1, k1, LOTRMod.brick, 0);
               }
            }
         }

         for(k1 = k - 6; k1 <= k + 6; ++k1) {
            for(k1 = i - 2; k1 <= i + 2; ++k1) {
               for(j1 = j; !LOTRMod.isOpaque(world, k1, j1, k1) && j1 >= 0; --j1) {
                  this.func_150516_a(world, k1, j1, k1, LOTRMod.brick, 0);
               }
            }
         }

         for(k1 = 0; k1 <= sections; ++k1) {
            this.generateTowerSection(world, random, i, j, k, k1, false, k1 == equipmentSection);
         }

         this.generateTowerSection(world, random, i, j, k, sections + 1, true, false);
         LOTREntityMordorOrcMercenaryCaptain trader = new LOTREntityMordorOrcMercenaryCaptain(world);
         trader.func_70012_b((double)i + 0.5D, (double)(j + (sections + 1) * 8 + 1), (double)(k - 4) + 0.5D, world.field_73012_v.nextFloat() * 360.0F, 0.0F);
         trader.func_110161_a((IEntityLivingData)null);
         trader.setPersistentAndTraderShouldRespawn();
         trader.func_110171_b(i, j + (sections + 1) * 8, k, 24);
         world.func_72838_d(trader);
         switch(rotation) {
         case 0:
            for(k1 = i - 1; k1 <= i + 1; ++k1) {
               this.func_150516_a(world, k1, j, k - 6, LOTRMod.slabDouble, 0);

               for(j1 = j + 1; j1 <= j + 4; ++j1) {
                  this.func_150516_a(world, k1, j1, k - 6, LOTRMod.gateOrc, 3);
               }
            }

            this.placeWallBanner(world, i, j + 7, k - 6, 2, LOTRItemBanner.BannerType.MORDOR);
            break;
         case 1:
            for(k1 = k - 1; k1 <= k + 1; ++k1) {
               this.func_150516_a(world, i + 6, j, k1, LOTRMod.slabDouble, 0);

               for(j1 = j + 1; j1 <= j + 4; ++j1) {
                  this.func_150516_a(world, i + 6, j1, k1, LOTRMod.gateOrc, 4);
               }
            }

            this.placeWallBanner(world, i + 6, j + 7, k, 3, LOTRItemBanner.BannerType.MORDOR);
            break;
         case 2:
            for(k1 = i - 1; k1 <= i + 1; ++k1) {
               this.func_150516_a(world, k1, j, k + 6, LOTRMod.slabDouble, 0);

               for(j1 = j + 1; j1 <= j + 4; ++j1) {
                  this.func_150516_a(world, k1, j1, k + 6, LOTRMod.gateOrc, 2);
               }
            }

            this.placeWallBanner(world, i, j + 7, k + 6, 0, LOTRItemBanner.BannerType.MORDOR);
            break;
         case 3:
            for(k1 = k - 1; k1 <= k + 1; ++k1) {
               this.func_150516_a(world, i - 6, j, k1, LOTRMod.slabDouble, 0);

               for(j1 = j + 1; j1 <= j + 4; ++j1) {
                  this.func_150516_a(world, i - 6, j1, k1, LOTRMod.gateOrc, 5);
               }
            }

            this.placeWallBanner(world, i - 6, j + 7, k, 1, LOTRItemBanner.BannerType.MORDOR);
         }

         LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
         respawner.setSpawnClass(LOTREntityMordorOrc.class);
         respawner.setCheckRanges(12, -8, 50, 20);
         respawner.setSpawnRanges(5, 1, 40, 16);
         this.placeNPCRespawner(respawner, world, i, j, k);
         return true;
      }
   }

   private void generateTowerSection(World world, Random random, int i, int j, int k, int section, boolean isTop, boolean isEquipmentSection) {
      j += section * 8;

      int j1;
      for(j1 = section == 0 ? j : j + 1; j1 <= (isTop ? j + 10 : j + 8); ++j1) {
         Block fillBlock = Blocks.field_150350_a;
         int fillMeta = false;
         byte fillMeta;
         if (j1 == j) {
            fillBlock = LOTRMod.slabDouble;
            fillMeta = 0;
         } else if (j1 == j + 8 && !isTop) {
            fillBlock = LOTRMod.slabSingle;
            fillMeta = 8;
         } else {
            fillBlock = Blocks.field_150350_a;
            fillMeta = 0;
         }

         int k1;
         for(k1 = k - 2; k1 <= k + 2; ++k1) {
            this.func_150516_a(world, i - 5, j1, k1, fillBlock, fillMeta);
            this.func_150516_a(world, i + 5, j1, k1, fillBlock, fillMeta);
         }

         int i1;
         for(k1 = k - 4; k1 <= k + 4; ++k1) {
            for(i1 = i - 4; i1 <= i - 3; ++i1) {
               this.func_150516_a(world, i1, j1, k1, fillBlock, fillMeta);
            }

            for(i1 = i + 3; i1 <= i + 4; ++i1) {
               this.func_150516_a(world, i1, j1, k1, fillBlock, fillMeta);
            }
         }

         for(k1 = k - 5; k1 <= k + 5; ++k1) {
            for(i1 = i - 2; i1 <= i + 2; ++i1) {
               this.func_150516_a(world, i1, j1, k1, fillBlock, fillMeta);
            }
         }
      }

      int i1;
      for(j1 = j + 1; j1 <= (isTop ? j + 1 : j + 8); ++j1) {
         for(i1 = k - 2; i1 <= k + 2; ++i1) {
            this.func_150516_a(world, i - 6, j1, i1, LOTRMod.brick, 0);
            this.func_150516_a(world, i + 6, j1, i1, LOTRMod.brick, 0);
         }

         for(i1 = i - 2; i1 <= i + 2; ++i1) {
            this.func_150516_a(world, i1, j1, k - 6, LOTRMod.brick, 0);
            this.func_150516_a(world, i1, j1, k + 6, LOTRMod.brick, 0);
         }

         this.func_150516_a(world, i - 5, j1, k - 4, LOTRMod.brick, 0);
         this.func_150516_a(world, i - 5, j1, k - 3, LOTRMod.brick, 0);
         this.func_150516_a(world, i - 5, j1, k + 3, LOTRMod.brick, 0);
         this.func_150516_a(world, i - 5, j1, k + 4, LOTRMod.brick, 0);
         this.func_150516_a(world, i - 4, j1, k - 5, LOTRMod.brick, 0);
         this.func_150516_a(world, i - 4, j1, k + 5, LOTRMod.brick, 0);
         this.func_150516_a(world, i - 3, j1, k - 5, LOTRMod.brick, 0);
         this.func_150516_a(world, i - 3, j1, k + 5, LOTRMod.brick, 0);
         this.func_150516_a(world, i + 3, j1, k - 5, LOTRMod.brick, 0);
         this.func_150516_a(world, i + 3, j1, k + 5, LOTRMod.brick, 0);
         this.func_150516_a(world, i + 4, j1, k - 5, LOTRMod.brick, 0);
         this.func_150516_a(world, i + 4, j1, k + 5, LOTRMod.brick, 0);
         this.func_150516_a(world, i + 5, j1, k - 4, LOTRMod.brick, 0);
         this.func_150516_a(world, i + 5, j1, k - 3, LOTRMod.brick, 0);
         this.func_150516_a(world, i + 5, j1, k + 3, LOTRMod.brick, 0);
         this.func_150516_a(world, i + 5, j1, k + 4, LOTRMod.brick, 0);
      }

      this.placeOrcTorch(world, i - 5, j + 1, k - 2);
      this.placeOrcTorch(world, i - 5, j + 1, k + 2);
      this.placeOrcTorch(world, i + 5, j + 1, k - 2);
      this.placeOrcTorch(world, i + 5, j + 1, k + 2);
      this.placeOrcTorch(world, i - 2, j + 1, k - 5);
      this.placeOrcTorch(world, i + 2, j + 1, k - 5);
      this.placeOrcTorch(world, i - 2, j + 1, k + 5);
      this.placeOrcTorch(world, i + 2, j + 1, k + 5);
      if (!isTop) {
         for(j1 = j + 2; j1 <= j + 4; ++j1) {
            for(i1 = k - 1; i1 <= k + 1; ++i1) {
               this.func_150516_a(world, i - 6, j1, i1, LOTRMod.orcSteelBars, 0);
               this.func_150516_a(world, i + 6, j1, i1, LOTRMod.orcSteelBars, 0);
            }

            for(i1 = i - 1; i1 <= i + 1; ++i1) {
               this.func_150516_a(world, i1, j1, k - 6, LOTRMod.orcSteelBars, 0);
               this.func_150516_a(world, i1, j1, k + 6, LOTRMod.orcSteelBars, 0);
            }
         }

         for(j1 = i - 2; j1 <= i + 2; ++j1) {
            for(i1 = k - 2; i1 <= k + 2; ++i1) {
               this.func_150516_a(world, j1, j + 8, i1, Blocks.field_150350_a, 0);
            }
         }

         this.func_150516_a(world, i - 2, j + 1, k + 1, LOTRMod.slabSingle, 0);
         this.func_150516_a(world, i - 2, j + 1, k + 2, LOTRMod.slabSingle, 8);
         this.func_150516_a(world, i - 1, j + 2, k + 2, LOTRMod.slabSingle, 0);
         this.func_150516_a(world, i, j + 2, k + 2, LOTRMod.slabSingle, 8);
         this.func_150516_a(world, i + 1, j + 3, k + 2, LOTRMod.slabSingle, 0);
         this.func_150516_a(world, i + 2, j + 3, k + 2, LOTRMod.slabSingle, 8);
         this.func_150516_a(world, i + 2, j + 4, k + 1, LOTRMod.slabSingle, 0);
         this.func_150516_a(world, i + 2, j + 4, k, LOTRMod.slabSingle, 8);
         this.func_150516_a(world, i + 2, j + 5, k - 1, LOTRMod.slabSingle, 0);
         this.func_150516_a(world, i + 2, j + 5, k - 2, LOTRMod.slabSingle, 8);
         this.func_150516_a(world, i + 1, j + 6, k - 2, LOTRMod.slabSingle, 0);
         this.func_150516_a(world, i, j + 6, k - 2, LOTRMod.slabSingle, 8);
         this.func_150516_a(world, i - 1, j + 7, k - 2, LOTRMod.slabSingle, 0);
         this.func_150516_a(world, i - 2, j + 7, k - 2, LOTRMod.slabSingle, 8);
         this.func_150516_a(world, i - 2, j + 8, k - 1, LOTRMod.slabSingle, 0);
         this.func_150516_a(world, i - 2, j + 8, k, LOTRMod.slabSingle, 8);
      }

      for(j1 = i - 1; j1 <= i + 1; ++j1) {
         for(i1 = k - 1; i1 <= k + 1; ++i1) {
            for(int j1 = j + 1; j1 <= (isTop ? j + 3 : j + 8); ++j1) {
               this.func_150516_a(world, j1, j1, i1, LOTRMod.brick, 0);
            }
         }
      }

      if (isEquipmentSection) {
         j1 = random.nextInt(4);
         label179:
         switch(j1) {
         case 0:
            i1 = i - 1;

            while(true) {
               if (i1 > i + 1) {
                  break label179;
               }

               this.func_150516_a(world, i1, j + 1, k - 5, LOTRMod.orcBomb, 0);
               this.func_150516_a(world, i1, j + 1, k + 5, LOTRMod.slabSingle, 9);
               this.placeBarrel(world, random, i1, j + 2, k + 5, 2, LOTRFoods.ORC_DRINK);
               ++i1;
            }
         case 1:
            i1 = k - 1;

            while(true) {
               if (i1 > k + 1) {
                  break label179;
               }

               this.func_150516_a(world, i + 5, j + 1, i1, LOTRMod.orcBomb, 0);
               this.func_150516_a(world, i - 5, j + 1, i1, LOTRMod.slabSingle, 9);
               this.placeBarrel(world, random, i - 5, j + 2, i1, 5, LOTRFoods.ORC_DRINK);
               ++i1;
            }
         case 2:
            i1 = i - 1;

            while(true) {
               if (i1 > i + 1) {
                  break label179;
               }

               this.func_150516_a(world, i1, j + 1, k + 5, LOTRMod.orcBomb, 0);
               this.func_150516_a(world, i1, j + 1, k - 5, LOTRMod.slabSingle, 9);
               this.placeBarrel(world, random, i1, j + 2, k - 5, 3, LOTRFoods.ORC_DRINK);
               ++i1;
            }
         case 3:
            for(i1 = k - 1; i1 <= k + 1; ++i1) {
               this.func_150516_a(world, i - 5, j + 1, i1, LOTRMod.orcBomb, 0);
               this.func_150516_a(world, i + 5, j + 1, i1, LOTRMod.slabSingle, 9);
               this.placeBarrel(world, random, i + 5, j + 2, i1, 4, LOTRFoods.ORC_DRINK);
            }
         }
      }

      if (isTop) {
         for(j1 = j + 1; j1 <= j + 8; ++j1) {
            for(i1 = k - 1; i1 <= k + 1; ++i1) {
               this.func_150516_a(world, i - 7, j1, i1, LOTRMod.brick, 0);
               this.func_150516_a(world, i + 7, j1, i1, LOTRMod.brick, 0);
            }

            for(i1 = i - 1; i1 <= i + 1; ++i1) {
               this.func_150516_a(world, i1, j1, k - 7, LOTRMod.brick, 0);
               this.func_150516_a(world, i1, j1, k + 7, LOTRMod.brick, 0);
            }
         }

         for(j1 = k - 1; j1 <= k + 1; ++j1) {
            this.func_150516_a(world, i - 7, j, j1, LOTRMod.stairsMordorBrick, 4);
            this.func_150516_a(world, i - 6, j + 2, j1, LOTRMod.stairsMordorBrick, 1);
            this.func_150516_a(world, i - 7, j + 9, j1, LOTRMod.stairsMordorBrick, 0);
            this.func_150516_a(world, i - 6, j + 9, j1, LOTRMod.stairsMordorBrick, 5);
            this.func_150516_a(world, i - 6, j + 10, j1, LOTRMod.stairsMordorBrick, 0);
            this.func_150516_a(world, i + 7, j, j1, LOTRMod.stairsMordorBrick, 5);
            this.func_150516_a(world, i + 6, j + 2, j1, LOTRMod.stairsMordorBrick, 0);
            this.func_150516_a(world, i + 7, j + 9, j1, LOTRMod.stairsMordorBrick, 1);
            this.func_150516_a(world, i + 6, j + 9, j1, LOTRMod.stairsMordorBrick, 4);
            this.func_150516_a(world, i + 6, j + 10, j1, LOTRMod.stairsMordorBrick, 1);
         }

         for(j1 = i - 1; j1 <= i + 1; ++j1) {
            this.func_150516_a(world, j1, j, k - 7, LOTRMod.stairsMordorBrick, 6);
            this.func_150516_a(world, j1, j + 2, k - 6, LOTRMod.stairsMordorBrick, 3);
            this.func_150516_a(world, j1, j + 9, k - 7, LOTRMod.stairsMordorBrick, 2);
            this.func_150516_a(world, j1, j + 9, k - 6, LOTRMod.stairsMordorBrick, 7);
            this.func_150516_a(world, j1, j + 10, k - 6, LOTRMod.stairsMordorBrick, 2);
            this.func_150516_a(world, j1, j, k + 7, LOTRMod.stairsMordorBrick, 7);
            this.func_150516_a(world, j1, j + 2, k + 6, LOTRMod.stairsMordorBrick, 2);
            this.func_150516_a(world, j1, j + 9, k + 7, LOTRMod.stairsMordorBrick, 3);
            this.func_150516_a(world, j1, j + 9, k + 6, LOTRMod.stairsMordorBrick, 6);
            this.func_150516_a(world, j1, j + 10, k + 6, LOTRMod.stairsMordorBrick, 3);
         }

         for(j1 = j; j1 <= j + 4; ++j1) {
            this.func_150516_a(world, i - 5, j1, k - 5, LOTRMod.brick, 0);
            this.func_150516_a(world, i - 5, j1, k + 5, LOTRMod.brick, 0);
            this.func_150516_a(world, i + 5, j1, k - 5, LOTRMod.brick, 0);
            this.func_150516_a(world, i + 5, j1, k + 5, LOTRMod.brick, 0);
         }

         this.placeBanner(world, i - 5, j + 5, k - 5, 0, LOTRItemBanner.BannerType.MORDOR);
         this.placeBanner(world, i - 5, j + 5, k + 5, 0, LOTRItemBanner.BannerType.MORDOR);
         this.placeBanner(world, i + 5, j + 5, k - 5, 0, LOTRItemBanner.BannerType.MORDOR);
         this.placeBanner(world, i + 5, j + 5, k + 5, 0, LOTRItemBanner.BannerType.MORDOR);
         this.func_150516_a(world, i - 5, j + 2, k - 4, LOTRMod.stairsMordorBrick, 3);
         this.func_150516_a(world, i - 4, j + 2, k - 5, LOTRMod.stairsMordorBrick, 1);
         this.func_150516_a(world, i - 5, j + 2, k + 4, LOTRMod.stairsMordorBrick, 2);
         this.func_150516_a(world, i - 4, j + 2, k + 5, LOTRMod.stairsMordorBrick, 1);
         this.func_150516_a(world, i + 5, j + 2, k - 4, LOTRMod.stairsMordorBrick, 3);
         this.func_150516_a(world, i + 4, j + 2, k - 5, LOTRMod.stairsMordorBrick, 0);
         this.func_150516_a(world, i + 5, j + 2, k + 4, LOTRMod.stairsMordorBrick, 2);
         this.func_150516_a(world, i + 4, j + 2, k + 5, LOTRMod.stairsMordorBrick, 0);
      }

   }
}
