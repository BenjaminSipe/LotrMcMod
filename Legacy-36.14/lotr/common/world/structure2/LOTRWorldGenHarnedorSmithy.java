package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.entity.npc.LOTREntityHarnedorBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorSmithy extends LOTRWorldGenHarnedorStructure {
   public LOTRWorldGenHarnedorSmithy(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(i1 = -12; i1 <= 8; ++i1) {
            for(k1 = -6; k1 <= 6; ++k1) {
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

      for(minHeight = -10; minHeight <= 6; ++minHeight) {
         for(maxHeight = -6; maxHeight <= 6; ++maxHeight) {
            i1 = Math.abs(minHeight);
            k1 = Math.abs(maxHeight);
            if (minHeight >= -8 && minHeight <= 4 && k1 == 4 || minHeight >= -10 && minHeight <= 6 && k1 <= 3) {
               for(j1 = -1; !this.isOpaque(world, minHeight, j1, maxHeight) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, minHeight, j1, maxHeight, this.plank2Block, this.plank2Meta);
                  this.setGrassToDirt(world, minHeight, j1 - 1, maxHeight);
               }

               for(j1 = 1; j1 <= 8; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("harnedor_smithy");
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockAlias("DOOR", this.doorBlock);
      this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.generateStrScan(world, random, 0, 1, 0);
      this.placeWeaponRack(world, -1, 2, -1, 5, this.getRandomHarnedorWeapon(random));
      this.placeWeaponRack(world, -1, 2, 1, 5, this.getRandomHarnedorWeapon(random));
      this.placeArmorStand(world, 3, 1, 3, 2, (ItemStack[])null);
      if (random.nextBoolean()) {
         this.placeArmorStand(world, 0, 1, 3, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetHarnedor), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsHarnedor), new ItemStack(LOTRMod.bootsHarnedor)});
      } else {
         this.placeArmorStand(world, 0, 1, 3, 0, new ItemStack[]{null, new ItemStack(LOTRMod.bodyHarnedor), null, null});
      }

      this.placeChest(world, random, 5, 1, -2, LOTRMod.chestBasket, 5, LOTRChestContents.HARNENNOR_HOUSE);
      this.placeChest(world, random, -7, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.HARNENNOR_HOUSE);
      this.placeBarrel(world, random, -3, 2, -1, 5, LOTRFoods.HARNEDOR_DRINK);
      this.placeMug(world, random, -3, 2, 0, 2, LOTRFoods.HARNEDOR_DRINK);
      this.placeMug(world, random, -9, 2, -2, 3, LOTRFoods.HARNEDOR_DRINK);
      this.placePlate(world, random, -5, 2, 3, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
      this.placePlate(world, random, -3, 2, 3, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
      this.placeFlowerPot(world, -4, 2, 3, this.getRandomFlower(world, random));
      this.setBlockAndMetadata(world, -8, 1, 1, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -9, 1, 1, this.bedBlock, 11);
      LOTREntityHarnedhrim smith = new LOTREntityHarnedorBlacksmith(world);
      this.spawnNPCAndSetHome(smith, world, 0, 1, 0, 8);
      return true;
   }
}
