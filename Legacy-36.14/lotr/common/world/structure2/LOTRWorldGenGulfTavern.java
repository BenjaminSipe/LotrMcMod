package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGulfBartender;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenGulfTavern extends LOTRWorldGenGulfStructure {
   public LOTRWorldGenGulfTavern(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 10);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int i1;
      int numHaradrim;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(i1 = -10; i1 <= 10; ++i1) {
            for(numHaradrim = -10; numHaradrim <= 10; ++numHaradrim) {
               j1 = this.getTopBlock(world, i1, numHaradrim) - 1;
               if (!this.isSurface(world, i1, j1, numHaradrim)) {
                  return false;
               }

               if (j1 < minHeight) {
                  minHeight = j1;
               }

               if (j1 > maxHeight) {
                  maxHeight = j1;
               }

               if (maxHeight - minHeight > 8) {
                  return false;
               }
            }
         }
      }

      for(minHeight = -10; minHeight <= 10; ++minHeight) {
         for(maxHeight = -10; maxHeight <= 10; ++maxHeight) {
            i1 = Math.abs(minHeight);
            numHaradrim = Math.abs(maxHeight);
            if (i1 * i1 + numHaradrim * numHaradrim < 100) {
               for(j1 = 1; j1 <= 6; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("gulf_tavern");
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
      this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
      this.associateBlockAlias("PLANK2_STAIR", this.plank2StairBlock);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
      this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
      this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
      this.generateStrScan(world, random, 0, 0, 0);
      String[] tavernName = LOTRNames.getHaradTavernName(random);
      String tavernNameNPC = tavernName[0] + " " + tavernName[1];
      this.placeSign(world, 0, 3, -10, Blocks.field_150444_as, 2, new String[]{"", tavernName[0], tavernName[1], ""});
      this.placeSign(world, 0, 3, 10, Blocks.field_150444_as, 3, new String[]{"", tavernName[0], tavernName[1], ""});
      this.placeBarrel(world, random, -3, 2, -2, 4, LOTRFoods.GULF_HARAD_DRINK);
      this.placeBarrel(world, random, 3, 2, 1, 5, LOTRFoods.GULF_HARAD_DRINK);
      this.placeFlowerPot(world, 3, 2, -2, this.getRandomFlower(world, random));
      this.placeFlowerPot(world, -3, 2, 1, this.getRandomFlower(world, random));
      this.placeKebabStand(world, random, -2, 2, 2, LOTRMod.kebabStand, 2);
      this.placeKebabStand(world, random, 2, 2, 2, LOTRMod.kebabStand, 2);
      this.placeWallBanner(world, -2, 4, -3, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, 2, 4, -3, LOTRItemBanner.BannerType.HARAD_GULF, 2);
      this.placeWallBanner(world, -2, 4, 3, LOTRItemBanner.BannerType.HARAD_GULF, 0);
      this.placeWallBanner(world, 2, 4, 3, LOTRItemBanner.BannerType.HARAD_GULF, 0);
      this.placeFoodOrDrink(world, random, -5, 2, -7);
      this.placeFoodOrDrink(world, random, -5, 2, -6);
      this.placeFoodOrDrink(world, random, -6, 2, -6);
      this.placeFoodOrDrink(world, random, -6, 2, -5);
      this.placeFoodOrDrink(world, random, -7, 2, -5);
      this.placeFoodOrDrink(world, random, -6, 2, -1);
      this.placeFoodOrDrink(world, random, -6, 2, 0);
      this.placeFoodOrDrink(world, random, -6, 2, 1);
      this.placeFoodOrDrink(world, random, -5, 2, 7);
      this.placeFoodOrDrink(world, random, -5, 2, 6);
      this.placeFoodOrDrink(world, random, -6, 2, 6);
      this.placeFoodOrDrink(world, random, -6, 2, 5);
      this.placeFoodOrDrink(world, random, -7, 2, 5);
      this.placeFoodOrDrink(world, random, 5, 2, -7);
      this.placeFoodOrDrink(world, random, 5, 2, -6);
      this.placeFoodOrDrink(world, random, 6, 2, -6);
      this.placeFoodOrDrink(world, random, 6, 2, -5);
      this.placeFoodOrDrink(world, random, 7, 2, -5);
      this.placeFoodOrDrink(world, random, 6, 2, -1);
      this.placeFoodOrDrink(world, random, 6, 2, 0);
      this.placeFoodOrDrink(world, random, 6, 2, 1);
      this.placeFoodOrDrink(world, random, 5, 2, 7);
      this.placeFoodOrDrink(world, random, 5, 2, 6);
      this.placeFoodOrDrink(world, random, 6, 2, 6);
      this.placeFoodOrDrink(world, random, 6, 2, 5);
      this.placeFoodOrDrink(world, random, 7, 2, 5);

      for(i1 = -2; i1 <= 2; ++i1) {
         this.placeFoodOrDrink(world, random, i1, 2, -3);
         this.placeFoodOrDrink(world, random, i1, 2, 3);
      }

      LOTREntityGulfBartender bartender = new LOTREntityGulfBartender(world);
      bartender.setSpecificLocationName(tavernNameNPC);
      this.spawnNPCAndSetHome(bartender, world, 0, 1, 0, 4);
      numHaradrim = MathHelper.func_76136_a(random, 3, 8);

      for(j1 = 0; j1 < numHaradrim; ++j1) {
         LOTREntityGulfHaradrim haradrim = new LOTREntityGulfHaradrim(world);
         this.spawnNPCAndSetHome(haradrim, world, random.nextBoolean() ? -5 : 5, 1, 0, 16);
      }

      int maxSteps = true;

      int step;
      int j1;
      int k1;
      int j2;
      int i1;
      for(i1 = -1; i1 <= 1; ++i1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            k1 = -10 - step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.stairsRedSandstone, 2);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, LOTRMod.redSandstone, 0);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(step = 0; step < 12; ++step) {
            j1 = 0 - step;
            k1 = 10 + step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.stairsRedSandstone, 3);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, LOTRMod.redSandstone, 0);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      return true;
   }

   private void placeFoodOrDrink(World world, Random random, int i, int j, int k) {
      if (random.nextBoolean()) {
         if (random.nextBoolean()) {
            this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.GULF_HARAD_DRINK);
         } else {
            Block plateBlock = random.nextBoolean() ? LOTRMod.woodPlateBlock : LOTRMod.ceramicPlateBlock;
            if (random.nextBoolean()) {
               this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
            } else {
               this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.GULF_HARAD);
            }
         }
      }

   }
}
