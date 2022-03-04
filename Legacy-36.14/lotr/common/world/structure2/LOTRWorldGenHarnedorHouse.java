package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityHarnedhrim;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorHouse extends LOTRWorldGenHarnedorStructure {
   public LOTRWorldGenHarnedorHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
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
            for(k1 = -7; k1 <= 7; ++k1) {
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

      for(minHeight = -6; minHeight <= 6; ++minHeight) {
         for(maxHeight = -6; maxHeight <= 6; ++maxHeight) {
            i1 = Math.abs(minHeight);
            k1 = Math.abs(maxHeight);
            if (i1 <= 2 && k1 <= 6 || k1 <= 2 && i1 <= 6) {
               for(j1 = 1; j1 <= 6; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      if (this.isRuined()) {
         this.loadStrScan("harnedor_house_ruined");
      } else {
         this.loadStrScan("harnedor_house");
      }

      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("DOOR", this.doorBlock);
      this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
      if (this.isRuined()) {
         this.setBlockAliasChance("PLANK2", 0.8F);
      }

      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.generateStrScan(world, random, 0, 1, 0);
      if (!this.isRuined()) {
         this.setBlockAndMetadata(world, 0, 1, 3, this.bedBlock, 0);
         this.setBlockAndMetadata(world, 0, 1, 4, this.bedBlock, 8);
         this.placeWeaponRack(world, 0, 3, -4, 4, this.getRandomHarnedorWeapon(random));
         this.placeWeaponRack(world, 0, 3, 4, 6, this.getRandomHarnedorWeapon(random));
         this.placeChest(world, random, -4, 1, 0, LOTRMod.chestBasket, 4, LOTRChestContents.HARNENNOR_HOUSE);
         this.placePlate(world, random, 4, 2, 0, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
         this.placePlate(world, random, -1, 2, 4, LOTRMod.ceramicPlateBlock, LOTRFoods.HARNEDOR);
         this.placeMug(world, random, 1, 2, 4, 0, LOTRFoods.HARNEDOR_DRINK);
         minHeight = 1 + random.nextInt(2);

         for(maxHeight = 0; maxHeight < minHeight; ++maxHeight) {
            LOTREntityHarnedhrim haradrim = new LOTREntityHarnedhrim(world);
            this.spawnNPCAndSetHome(haradrim, world, 0, 1, -1, 16);
         }
      }

      return true;
   }
}
