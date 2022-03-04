package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGulfBlacksmith;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGulfSmithy extends LOTRWorldGenGulfStructure {
   public LOTRWorldGenGulfSmithy(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int step;
      int k1;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(step = -14; step <= 6; ++step) {
            for(k1 = -6; k1 <= 6; ++k1) {
               j1 = this.getTopBlock(world, step, k1) - 1;
               if (!this.isSurface(world, step, j1, k1)) {
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

      for(minHeight = -14; minHeight <= 6; ++minHeight) {
         for(maxHeight = -6; maxHeight <= 6; ++maxHeight) {
            step = Math.abs(minHeight);
            k1 = Math.abs(maxHeight);
            if (step * step + k1 * k1 < 25 || minHeight <= -7 && k1 <= 5) {
               for(j1 = 1; j1 <= 5; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("gulf_smithy");
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
      this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
      this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockAlias("DOOR", this.doorBlock);
      this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
      this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
      this.associateBlockMetaAlias("PLANK2_SLAB", this.plank2SlabBlock, this.plank2SlabMeta);
      this.associateBlockMetaAlias("PLANK2_SLAB_INV", this.plank2SlabBlock, this.plank2SlabMeta | 8);
      this.associateBlockAlias("PLANK2_STAIR", this.plank2StairBlock);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
      this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
      this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
      this.associateBlockMetaAlias("FLAG", this.flagBlock, this.flagMeta);
      this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
      this.generateStrScan(world, random, 0, 0, 0);
      this.setBlockAndMetadata(world, 0, 1, 3, this.bedBlock, 0);
      this.setBlockAndMetadata(world, 0, 1, 4, this.bedBlock, 8);
      this.placeChest(world, random, -4, 1, -2, LOTRMod.chestBasket, 3, LOTRChestContents.GULF_HOUSE);
      this.placeFlowerPot(world, 2, 2, -4, this.getRandomFlower(world, random));
      this.placeFlowerPot(world, -2, 2, 4, this.getRandomFlower(world, random));
      this.placeFlowerPot(world, -4, 1, 1, new ItemStack(Blocks.field_150434_aF));
      this.placeMug(world, random, 4, 2, -1, 1, LOTRFoods.GULF_HARAD_DRINK);
      this.placeMug(world, random, 2, 2, 4, 0, LOTRFoods.GULF_HARAD_DRINK);
      this.placePlate(world, random, 4, 2, 0, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
      this.placePlate(world, random, 4, 2, 1, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
      if (random.nextBoolean()) {
         this.placeArmorStand(world, -7, 1, -2, 1, new ItemStack[]{new ItemStack(LOTRMod.helmetGulfHarad), new ItemStack(LOTRMod.bodyGulfHarad), new ItemStack(LOTRMod.legsGulfHarad), new ItemStack(LOTRMod.bootsGulfHarad)});
      } else {
         this.placeArmorStand(world, -7, 1, -2, 1, new ItemStack[]{null, new ItemStack(LOTRMod.bodyGulfHarad), null, null});
      }

      this.placeWeaponRack(world, -13, 3, 0, 5, this.getRandomGulfWeapon(random));
      LOTREntityGulfHaradrim smith = new LOTREntityGulfBlacksmith(world);
      this.spawnNPCAndSetHome(smith, world, -6, 1, 0, 8);
      int maxSteps = 12;

      for(step = 0; step < maxSteps; ++step) {
         int i1 = -9;
         j1 = 0 - step;
         int k1 = -5 - step;
         if (this.isOpaque(world, i1, j1, k1)) {
            break;
         }

         this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.stairsRedSandstone, 2);
         this.setGrassToDirt(world, i1, j1 - 1, k1);

         for(int j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
            this.setBlockAndMetadata(world, i1, j2, k1, LOTRMod.redSandstone, 0);
            this.setGrassToDirt(world, i1, j2 - 1, k1);
         }
      }

      return true;
   }
}
