package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDorwinionElfVintner;
import lotr.common.item.LOTRItemBanner;
import lotr.common.item.LOTRItemMug;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenDorwinionBrewery extends LOTRWorldGenDorwinionHouse {
   public LOTRWorldGenDorwinionBrewery(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      int k1;
      int k1;
      int i1;
      if (this.restrictions) {
         for(k1 = -6; k1 <= 6; ++k1) {
            for(k1 = 0; k1 <= 19; ++k1) {
               i1 = this.getTopBlock(world, k1, k1) - 1;
               Block block = this.getBlock(world, k1, i1, k1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }
            }
         }
      }

      for(k1 = -6; k1 <= 6; ++k1) {
         for(k1 = 0; k1 <= 19; ++k1) {
            this.setBlockAndMetadata(world, k1, 0, k1, Blocks.field_150349_c, 0);

            for(i1 = -1; !this.isOpaque(world, k1, i1, k1) && this.getY(i1) >= 0; --i1) {
               this.setBlockAndMetadata(world, k1, i1, k1, Blocks.field_150346_d, 0);
               this.setGrassToDirt(world, k1, i1 - 1, k1);
            }

            for(i1 = 1; i1 <= 10; ++i1) {
               this.setAir(world, k1, i1, k1);
            }

            if (k1 >= -5 && k1 <= 5 && k1 >= 1 && k1 <= 18) {
               if ((k1 == -5 || k1 == 5) && (k1 == 1 || k1 == 18)) {
                  for(i1 = 0; i1 <= 5; ++i1) {
                     this.setBlockAndMetadata(world, k1, i1, k1, this.woodBeamBlock, this.woodBeamMeta);
                  }
               } else if (k1 != -5 && k1 != 5 && k1 != 1 && k1 != 18) {
                  if (k1 >= -2 && k1 <= 2) {
                     this.setBlockAndMetadata(world, k1, 0, k1, this.floorBlock, this.floorMeta);
                  } else {
                     this.setBlockAndMetadata(world, k1, 0, k1, this.plankBlock, this.plankMeta);
                  }
               } else {
                  for(i1 = 0; i1 <= 5; ++i1) {
                     this.setBlockAndMetadata(world, k1, i1, k1, this.brickBlock, this.brickMeta);
                  }
               }
            }
         }
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         this.setBlockAndMetadata(world, k1, 0, 1, this.floorBlock, this.floorMeta);

         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, k1, k1, 1, LOTRMod.gateWooden, 2);
         }
      }

      for(k1 = 2; k1 <= 17; ++k1) {
         if (k1 % 3 == 2) {
            this.setBlockAndMetadata(world, -6, 1, k1, this.brickStairBlock, 1);
            this.setGrassToDirt(world, -6, 0, k1);
            this.setBlockAndMetadata(world, 6, 1, k1, this.brickStairBlock, 0);
            this.setGrassToDirt(world, 6, 0, k1);
         } else {
            this.setBlockAndMetadata(world, -6, 1, k1, this.leafBlock, this.leafMeta);
            this.setBlockAndMetadata(world, 6, 1, k1, this.leafBlock, this.leafMeta);
         }
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         if (Math.abs(k1) == 4) {
            this.setBlockAndMetadata(world, k1, 1, 19, this.brickStairBlock, 3);
            this.setGrassToDirt(world, k1, 0, 19);
         } else {
            this.setBlockAndMetadata(world, k1, 1, 19, this.leafBlock, this.leafMeta);
         }
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         if (Math.abs(k1) != 4 && Math.abs(k1) != 2) {
            if (Math.abs(k1) == 3) {
               this.setBlockAndMetadata(world, k1, 1, 0, this.leafBlock, this.leafMeta);
            }
         } else {
            this.setBlockAndMetadata(world, k1, 1, 0, this.brickStairBlock, 2);
            this.setGrassToDirt(world, k1, 0, 0);
         }
      }

      for(k1 = -5; k1 <= 5; ++k1) {
         this.setBlockAndMetadata(world, k1, 5, 0, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, k1, 5, 19, this.brickStairBlock, 7);
      }

      for(k1 = 0; k1 <= 19; ++k1) {
         if (k1 >= 3 && k1 <= 16) {
            if (k1 % 3 == 0) {
               this.setAir(world, -5, 3, k1);
               this.setBlockAndMetadata(world, -5, 4, k1, this.brickStairBlock, 7);
               this.setAir(world, 5, 3, k1);
               this.setBlockAndMetadata(world, 5, 4, k1, this.brickStairBlock, 7);
            } else if (k1 % 3 == 1) {
               this.setAir(world, -5, 3, k1);
               this.setBlockAndMetadata(world, -5, 4, k1, this.brickStairBlock, 6);
               this.setAir(world, 5, 3, k1);
               this.setBlockAndMetadata(world, 5, 4, k1, this.brickStairBlock, 6);
            }
         }

         this.setBlockAndMetadata(world, -6, 5, k1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, 6, 5, k1, this.brickStairBlock, 4);
         if (k1 <= 7 && k1 % 2 == 0 || k1 >= 12 && k1 % 2 == 1) {
            this.setBlockAndMetadata(world, -6, 6, k1, this.brickSlabBlock, this.brickSlabMeta);
            this.setBlockAndMetadata(world, 6, 6, k1, this.brickSlabBlock, this.brickSlabMeta);
         }

         if (k1 == 8 || k1 == 11) {
            this.setBlockAndMetadata(world, -6, 4, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, -6, 5, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -6, 6, k1, this.brickSlabBlock, this.brickSlabMeta);
            this.setBlockAndMetadata(world, 6, 4, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, 6, 5, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 6, 6, k1, this.brickSlabBlock, this.brickSlabMeta);
            this.placeWallBanner(world, -5, 3, k1, LOTRItemBanner.BannerType.DORWINION, 3);
            this.placeWallBanner(world, 5, 3, k1, LOTRItemBanner.BannerType.DORWINION, 1);
         }

         if (k1 == 9 || k1 == 10) {
            this.setBlockAndMetadata(world, -6, 6, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 6, 6, k1, this.brickBlock, this.brickMeta);
         }
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         if (Math.abs(k1) == 3) {
            this.setBlockAndMetadata(world, k1, 2, 1, this.brickSlabBlock, this.brickSlabMeta);
            this.setAir(world, k1, 3, 1);
         }

         if (Math.abs(k1) == 2) {
            this.placeWallBanner(world, k1, 4, 1, LOTRItemBanner.BannerType.DORWINION, 2);
         }

         if (IntMath.mod(k1, 2) == 1) {
            this.setBlockAndMetadata(world, k1, 2, 18, this.brickSlabBlock, this.brickSlabMeta);
            this.setAir(world, k1, 3, 18);
         }
      }

      int[] var18 = new int[]{1, 18};
      k1 = var18.length;

      int wines;
      int k1;
      for(i1 = 0; i1 < k1; ++i1) {
         k1 = var18[i1];
         this.setBlockAndMetadata(world, -4, 6, k1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -3, 6, k1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -2, 6, k1, this.brickSlabBlock, this.brickSlabMeta);
         this.setBlockAndMetadata(world, -1, 6, k1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 0, 6, k1, this.brickSlabBlock, this.brickSlabMeta);
         this.setBlockAndMetadata(world, 1, 6, k1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 2, 6, k1, this.brickSlabBlock, this.brickSlabMeta);
         this.setBlockAndMetadata(world, 3, 6, k1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 4, 6, k1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -3, 7, k1, this.brickBlock, this.brickMeta);
         this.setAir(world, -2, 7, k1);
         this.setBlockAndMetadata(world, -1, 7, k1, this.brickBlock, this.brickMeta);
         this.setAir(world, 0, 7, k1);
         this.setBlockAndMetadata(world, 1, 7, k1, this.brickBlock, this.brickMeta);
         this.setAir(world, 2, 7, k1);
         this.setBlockAndMetadata(world, 3, 7, k1, this.brickBlock, this.brickMeta);

         for(wines = -2; wines <= 2; ++wines) {
            this.setBlockAndMetadata(world, wines, 8, k1, this.brickBlock, this.brickMeta);
         }

         for(wines = -1; wines <= 1; ++wines) {
            this.setBlockAndMetadata(world, wines, 9, k1, this.brickBlock, this.brickMeta);
         }

         this.setBlockAndMetadata(world, 0, 10, k1, this.brickBlock, this.brickMeta);
      }

      for(k1 = 2; k1 <= 17; ++k1) {
         this.setBlockAndMetadata(world, -4, 6, k1, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, -3, 7, k1, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, -2, 8, k1, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, -1, 9, k1, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, 0, 10, k1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 1, 9, k1, this.plankStairBlock, 5);
         this.setBlockAndMetadata(world, 2, 8, k1, this.plankStairBlock, 5);
         this.setBlockAndMetadata(world, 3, 7, k1, this.plankStairBlock, 5);
         this.setBlockAndMetadata(world, 4, 6, k1, this.plankStairBlock, 5);
      }

      for(k1 = 0; k1 <= 19; ++k1) {
         this.setBlockAndMetadata(world, -5, 6, k1, this.clayStairBlock, 1);
         this.setBlockAndMetadata(world, -4, 7, k1, this.clayStairBlock, 1);
         this.setBlockAndMetadata(world, -3, 8, k1, this.clayStairBlock, 1);
         this.setBlockAndMetadata(world, -2, 9, k1, this.clayStairBlock, 1);
         this.setBlockAndMetadata(world, -1, 10, k1, this.clayStairBlock, 1);
         this.setBlockAndMetadata(world, 0, 11, k1, this.claySlabBlock, this.claySlabMeta);
         this.setBlockAndMetadata(world, 1, 10, k1, this.clayStairBlock, 0);
         this.setBlockAndMetadata(world, 2, 9, k1, this.clayStairBlock, 0);
         this.setBlockAndMetadata(world, 3, 8, k1, this.clayStairBlock, 0);
         this.setBlockAndMetadata(world, 4, 7, k1, this.clayStairBlock, 0);
         this.setBlockAndMetadata(world, 5, 6, k1, this.clayStairBlock, 0);
      }

      var18 = new int[]{0, 19};
      k1 = var18.length;

      for(i1 = 0; i1 < k1; ++i1) {
         k1 = var18[i1];
         this.setBlockAndMetadata(world, -4, 6, k1, this.clayStairBlock, 4);
         this.setBlockAndMetadata(world, -3, 7, k1, this.clayStairBlock, 4);
         this.setBlockAndMetadata(world, -2, 8, k1, this.clayStairBlock, 4);
         this.setBlockAndMetadata(world, -1, 9, k1, this.clayStairBlock, 4);
         this.setBlockAndMetadata(world, 0, 10, k1, this.clayBlock, this.clayMeta);
         this.setBlockAndMetadata(world, 1, 9, k1, this.clayStairBlock, 5);
         this.setBlockAndMetadata(world, 2, 8, k1, this.clayStairBlock, 5);
         this.setBlockAndMetadata(world, 3, 7, k1, this.clayStairBlock, 5);
         this.setBlockAndMetadata(world, 4, 6, k1, this.clayStairBlock, 5);
      }

      for(k1 = 2; k1 <= 17; ++k1) {
         if (k1 % 3 == 2) {
            for(k1 = -4; k1 <= 4; ++k1) {
               this.setBlockAndMetadata(world, k1, 6, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
            }

            this.setBlockAndMetadata(world, -4, 5, k1, Blocks.field_150478_aa, 2);
            this.setBlockAndMetadata(world, 4, 5, k1, Blocks.field_150478_aa, 1);
         }
      }

      this.setBlockAndMetadata(world, -2, 5, 2, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 5, 2, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 5, 17, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 5, 17, Blocks.field_150478_aa, 4);
      this.placeWallBanner(world, 0, 5, 1, LOTRItemBanner.BannerType.DORWINION, 0);
      this.placeWallBanner(world, 0, 5, 18, LOTRItemBanner.BannerType.DORWINION, 2);
      ItemStack drink = LOTRFoods.DORWINION_DRINK.getRandomBrewableDrink(random);

      for(k1 = 2; k1 <= 17; ++k1) {
         for(i1 = -4; i1 <= 4; ++i1) {
            if (Math.abs(i1) >= 3) {
               if (k1 != 2 && k1 != 17) {
                  if (k1 % 3 == 0) {
                     this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150485_bF, 6);
                     this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150485_bF, 2);
                  } else if (k1 % 3 == 1) {
                     this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150485_bF, 7);
                     this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150485_bF, 3);
                  }
               } else {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);
               }
            }
         }

         if (k1 >= 5 && k1 <= 15 && k1 % 3 == 2) {
            this.setBlockAndMetadata(world, -4, 1, k1, this.plankBlock, this.plankMeta);
            this.placeBarrel(world, random, -4, 2, k1, 4, drink);
            this.setBlockAndMetadata(world, 4, 1, k1, this.plankBlock, this.plankMeta);
            this.placeBarrel(world, random, 4, 2, k1, 5, drink);
         }
      }

      for(k1 = 8; k1 <= 11; ++k1) {
         for(i1 = -1; i1 <= 1; ++i1) {
            if (Math.abs(i1) != 1 || k1 != 8 && k1 != 11) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            } else {
               this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);
            }
         }

         this.placeMug(world, random, -1, 2, k1, 1, drink, LOTRFoods.DORWINION_DRINK);
         this.placeMug(world, random, 1, 2, k1, 3, drink, LOTRFoods.DORWINION_DRINK);
      }

      this.setBlockAndMetadata(world, 0, 1, 17, Blocks.field_150462_ai, 0);

      for(k1 = -2; k1 <= 2; ++k1) {
         if (Math.abs(k1) >= 1) {
            this.setBlockAndMetadata(world, k1, 1, 17, Blocks.field_150486_ae, 2);
            TileEntity tileentity = this.getTileEntity(world, k1, 1, 17);
            if (tileentity instanceof TileEntityChest) {
               TileEntityChest chest = (TileEntityChest)tileentity;
               wines = MathHelper.func_76136_a(random, 3, 6);

               for(int l = 0; l < wines; ++l) {
                  ItemStack chestDrinkItem = drink.func_77946_l();
                  chestDrinkItem.field_77994_a = 1;
                  LOTRItemMug.setStrengthMeta(chestDrinkItem, MathHelper.func_76136_a(random, 1, 4));
                  LOTRItemMug.Vessel[] chestVessels = LOTRFoods.DORWINION_DRINK.getDrinkVessels();
                  LOTRItemMug.Vessel v = chestVessels[random.nextInt(chestVessels.length)];
                  LOTRItemMug.setVessel(chestDrinkItem, v, true);
                  chest.func_70299_a(random.nextInt(chest.func_70302_i_()), chestDrinkItem);
               }
            }
         }
      }

      LOTREntityDorwinionElfVintner vintner = new LOTREntityDorwinionElfVintner(world);
      this.spawnNPCAndSetHome(vintner, world, 0, 1, 13, 16);
      return true;
   }
}
