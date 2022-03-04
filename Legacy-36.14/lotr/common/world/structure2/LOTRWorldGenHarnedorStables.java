package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorStables extends LOTRWorldGenHarnedorStructure {
   public LOTRWorldGenHarnedorStables(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 9);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(i1 = -7; i1 <= 7; ++i1) {
            for(k1 = -10; k1 <= 10; ++k1) {
               j1 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, j1, k1)) {
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

      for(minHeight = -7; minHeight <= 7; ++minHeight) {
         for(maxHeight = -10; maxHeight <= 10; ++maxHeight) {
            i1 = Math.abs(minHeight);
            k1 = Math.abs(maxHeight);
            if (i1 <= 5 && k1 <= 4 || i1 <= 4 && k1 <= 6 || i1 <= 3 && k1 <= 7 || i1 <= 2 && k1 <= 8 || i1 <= 1 && k1 <= 9) {
               for(j1 = 1; j1 <= 6; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("harnedor_stables");
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.addBlockMetaAliasOption("GROUND", 5, Blocks.field_150349_c, 0);
      this.addBlockMetaAliasOption("GROUND", 4, Blocks.field_150346_d, 1);
      this.addBlockMetaAliasOption("GROUND", 1, Blocks.field_150354_m, 0);
      this.generateStrScan(world, random, 0, 0, 0);
      this.placeWallBanner(world, -2, 4, -4, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
      this.placeWallBanner(world, 2, 4, -4, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
      this.placeWallBanner(world, -2, 4, 4, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
      this.placeWallBanner(world, 2, 4, 4, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
      this.spawnItemFrame(world, -2, 2, 0, 1, new ItemStack(Items.field_151141_av));
      this.spawnItemFrame(world, 2, 2, 0, 3, new ItemStack(Items.field_151058_ca));
      this.setBlockAndMetadata(world, -3, 1, 6, this.bedBlock, 0);
      this.setBlockAndMetadata(world, -3, 1, 7, this.bedBlock, 8);
      this.placeChest(world, random, -4, 1, 6, LOTRMod.chestBasket, 4, LOTRChestContents.HARNENNOR_HOUSE);
      this.placePlateWithCertainty(world, random, 4, 2, 6, LOTRMod.woodPlateBlock, LOTRFoods.HARNEDOR);
      this.placeMug(world, random, 4, 2, 5, 1, LOTRFoods.HARNEDOR_DRINK);
      LOTREntityHarnedhrim harnedhrim = new LOTREntityHarnedhrim(world);
      this.spawnNPCAndSetHome(harnedhrim, world, 0, 1, 0, 12);
      int[] var19 = new int[]{-2, 2};
      i1 = var19.length;

      for(k1 = 0; k1 < i1; ++k1) {
         j1 = var19[k1];
         int[] var12 = new int[]{-4, 4};
         int var13 = var12.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            int i1 = var12[var14];
            int j1 = 1;
            LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome(horse, world, i1, j1, j1, 0);
            horse.func_110214_p(0);
            horse.saddleMountForWorldGen();
            horse.func_110177_bN();
         }
      }

      return true;
   }
}
