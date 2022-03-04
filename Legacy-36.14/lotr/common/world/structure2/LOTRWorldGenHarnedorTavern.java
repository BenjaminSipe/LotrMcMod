package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.entity.npc.LOTREntityHarnedorBartender;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorTavern extends LOTRWorldGenHarnedorStructure {
   public LOTRWorldGenHarnedorTavern(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 7, -3);
      this.setupRandomBlocks(random);
      int minHeight;
      int i1;
      int step;
      int j1;
      int k1;
      if (this.restrictions) {
         minHeight = 0;
         i1 = 0;

         for(step = -15; step <= 15; ++step) {
            for(j1 = -8; j1 <= 8; ++j1) {
               k1 = this.getTopBlock(world, step, j1) - 1;
               if (!this.isSurface(world, step, k1, j1)) {
                  return false;
               }

               if (k1 < minHeight) {
                  minHeight = k1;
               }

               if (k1 > i1) {
                  i1 = k1;
               }

               if (i1 - minHeight > 12) {
                  return false;
               }
            }
         }
      }

      for(minHeight = -13; minHeight <= 13; ++minHeight) {
         for(i1 = -6; i1 <= 6; ++i1) {
            step = Math.abs(minHeight);
            j1 = Math.abs(i1);
            if (step <= 8 && j1 == 6 || step <= 11 && j1 <= 5 || step <= 13 && j1 <= 4) {
               for(k1 = 1; k1 <= 8; ++k1) {
                  this.setAir(world, minHeight, k1, i1);
               }

               for(k1 = -1; !this.isOpaque(world, minHeight, k1, i1) && this.getY(k1) >= 0; --k1) {
                  this.setBlockAndMetadata(world, minHeight, k1, i1, this.plank2Block, this.plank2Meta);
                  this.setGrassToDirt(world, minHeight, k1 - 1, i1);
               }
            }
         }
      }

      if (this.isRuined()) {
         this.loadStrScan("harnedor_tavern_ruined");
      } else {
         this.loadStrScan("harnedor_tavern");
      }

      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
      if (this.isRuined()) {
         this.setBlockAliasChance("PLANK2", 0.8F);
      }

      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.generateStrScan(world, random, 0, 1, 0);
      if (!this.isRuined()) {
         this.placeWeaponRack(world, -3, 3, -1, 6, this.getRandomHarnedorWeapon(random));
         this.spawnItemFrame(world, -3, 3, 0, 0, this.getHarnedorFramedItem(random));
         this.placeWeaponRack(world, 3, 3, 1, 4, this.getRandomHarnedorWeapon(random));
         this.spawnItemFrame(world, 3, 3, 0, 2, this.getHarnedorFramedItem(random));
         this.placeFoodOrDrink(world, random, -4, 2, -1);
         this.placeFoodOrDrink(world, random, -3, 2, -1);
         this.placeFoodOrDrink(world, random, -2, 2, -1);
         this.placeFoodOrDrink(world, random, -2, 2, 0);
         this.placeFoodOrDrink(world, random, -2, 2, 1);
         this.placeFoodOrDrink(world, random, -3, 2, 1);
         this.placeFoodOrDrink(world, random, -4, 2, 1);
         this.placeFoodOrDrink(world, random, -4, 2, 0);
         this.placeFoodOrDrink(world, random, 4, 2, -1);
         this.placeFoodOrDrink(world, random, 3, 2, -1);
         this.placeFoodOrDrink(world, random, 2, 2, -1);
         this.placeFoodOrDrink(world, random, 2, 2, 0);
         this.placeFoodOrDrink(world, random, 2, 2, 1);
         this.placeFoodOrDrink(world, random, 3, 2, 1);
         this.placeFoodOrDrink(world, random, 4, 2, 1);
         this.placeFoodOrDrink(world, random, 4, 2, 0);
         this.placeFoodOrDrink(world, random, -7, 2, -5);
         this.placeFoodOrDrink(world, random, -8, 2, 5);
         this.placeFoodOrDrink(world, random, -7, 2, 5);
         this.placeFoodOrDrink(world, random, -6, 2, 5);
         this.placeFoodOrDrink(world, random, 6, 2, -5);
         this.placeFoodOrDrink(world, random, 7, 2, -5);
         this.placeFoodOrDrink(world, random, 6, 2, 5);
         this.placeFoodOrDrink(world, random, 7, 2, 5);
         this.placeFoodOrDrink(world, random, -9, 2, -2);
         this.placeFoodOrDrink(world, random, -9, 2, -1);
         this.placeFoodOrDrink(world, random, -9, 2, 1);
         this.placeFoodOrDrink(world, random, -9, 2, 2);
         this.placeFlowerPot(world, -12, 2, -3, this.getRandomFlower(world, random));
         this.placeFoodOrDrink(world, random, -12, 2, -2);
         this.placeFoodOrDrink(world, random, -12, 2, 1);
         this.placeFoodOrDrink(world, random, -12, 2, 2);
         this.placeBarrel(world, random, -12, 2, 3, 4, LOTRFoods.HARNEDOR_DRINK);
         this.placeBarrel(world, random, -11, 2, 4, 2, LOTRFoods.HARNEDOR_DRINK);
         this.placeKebabStand(world, random, -10, 2, -4, LOTRMod.kebabStand, 3);
         this.setBlockAndMetadata(world, 11, 1, -3, this.bedBlock, 2);
         this.setBlockAndMetadata(world, 11, 1, -4, this.bedBlock, 10);
         this.setBlockAndMetadata(world, 11, 1, 3, this.bedBlock, 0);
         this.setBlockAndMetadata(world, 11, 1, 4, this.bedBlock, 8);
         this.placeChest(world, random, 12, 1, -3, LOTRMod.chestBasket, 3, LOTRChestContents.HARNENNOR_HOUSE);
         this.placeChest(world, random, 12, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.HARNENNOR_HOUSE);
         this.placeFlowerPot(world, 12, 2, -1, this.getRandomFlower(world, random));
         this.placeFoodOrDrink(world, random, 11, 2, -1);
         this.placeFlowerPot(world, 11, 2, 1, this.getRandomFlower(world, random));
         this.placeFoodOrDrink(world, random, 12, 2, 1);
         String[] tavernName = LOTRNames.getHaradTavernName(random);
         String tavernNameNPC = tavernName[0] + " " + tavernName[1];
         this.placeSign(world, -1, 2, -6, Blocks.field_150444_as, 5, new String[]{"", tavernName[0], tavernName[1], ""});
         this.placeSign(world, 1, 2, -6, Blocks.field_150444_as, 4, new String[]{"", tavernName[0], tavernName[1], ""});
         this.placeSign(world, -1, 2, 6, Blocks.field_150444_as, 5, new String[]{"", tavernName[0], tavernName[1], ""});
         this.placeSign(world, 1, 2, 6, Blocks.field_150444_as, 4, new String[]{"", tavernName[0], tavernName[1], ""});
         this.placeWallBanner(world, -6, 4, -8, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
         this.placeWallBanner(world, 6, 4, -8, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
         this.placeWallBanner(world, -6, 4, 8, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
         this.placeWallBanner(world, 6, 4, 8, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
         this.placeWallBanner(world, 0, 6, -4, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
         this.placeWallBanner(world, 0, 6, 4, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
         this.placeWallBanner(world, -9, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 1);
         this.placeWallBanner(world, 9, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 3);
         LOTREntityHarnedorBartender bartender = new LOTREntityHarnedorBartender(world);
         bartender.setSpecificLocationName(tavernNameNPC);
         this.spawnNPCAndSetHome(bartender, world, -10, 1, 0, 4);
         j1 = MathHelper.func_76136_a(random, 3, 8);

         for(k1 = 0; k1 < j1; ++k1) {
            LOTREntityHarnedhrim haradrim = new LOTREntityHarnedhrim(world);
            this.spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 16);
         }
      }

      int maxSteps = true;

      int j2;
      for(i1 = -5; i1 <= -1; ++i1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            k1 = -7 - step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, this.plank2StairBlock, 2);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.plank2Block, this.plank2Meta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      for(i1 = 1; i1 <= 5; ++i1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            k1 = -7 - step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, this.plank2StairBlock, 2);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.plank2Block, this.plank2Meta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      for(i1 = -5; i1 <= -1; ++i1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            k1 = 7 + step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, this.plank2StairBlock, 3);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.plank2Block, this.plank2Meta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      for(i1 = 1; i1 <= 5; ++i1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            k1 = 7 + step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, this.plank2StairBlock, 3);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.plank2Block, this.plank2Meta);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      return true;
   }

   private void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
      if (random.nextBoolean()) {
         if (random.nextBoolean()) {
            this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.HARNEDOR_DRINK);
         } else {
            Block plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
            if (random.nextBoolean()) {
               this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
            } else {
               this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.HARNEDOR);
            }
         }
      }

   }
}
