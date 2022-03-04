package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNearHaradBlacksmith;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.npc.LOTREntityUmbarBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronSmithy extends LOTRWorldGenSouthronStructure {
   public LOTRWorldGenSouthronSmithy(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5, -1);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int i1;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(i1 = -4; i1 <= 4; ++i1) {
            for(j1 = -5; j1 <= 13; ++j1) {
               int j1 = this.getTopBlock(world, i1, j1) - 1;
               if (!this.isSurface(world, i1, j1, j1)) {
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

      for(minHeight = -4; minHeight <= 4; ++minHeight) {
         for(maxHeight = -5; maxHeight <= 13; ++maxHeight) {
            i1 = Math.abs(minHeight);
            if (i1 <= 3 && maxHeight >= -4 && maxHeight <= 6 || i1 <= 4 && maxHeight >= 7 && maxHeight <= 12) {
               for(j1 = 0; !this.isOpaque(world, minHeight, j1, maxHeight) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, minHeight, j1, maxHeight, this.stoneBlock, this.stoneMeta);
                  this.setGrassToDirt(world, minHeight, j1 - 1, maxHeight);
               }

               for(j1 = 1; j1 <= 8; ++j1) {
                  this.setAir(world, minHeight, j1, maxHeight);
               }
            }
         }
      }

      this.loadStrScan("southron_smithy");
      this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
      this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
      this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
      this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
      this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
      this.associateBlockMetaAlias("BEAM|4", this.woodBeamBlock, this.woodBeamMeta4);
      this.associateBlockMetaAlias("BEAM|8", this.woodBeamBlock, this.woodBeamMeta8);
      this.associateBlockAlias("DOOR", this.doorBlock);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
      this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
      this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
      this.associateBlockMetaAlias("TABLE", this.tableBlock, 0);
      this.generateStrScan(world, random, 0, 1, 0);
      this.setBlockAndMetadata(world, -1, 5, -2, this.bedBlock, 2);
      this.setBlockAndMetadata(world, -2, 5, -2, this.bedBlock, 2);
      this.setBlockAndMetadata(world, -1, 5, -3, this.bedBlock, 10);
      this.setBlockAndMetadata(world, -2, 5, -3, this.bedBlock, 10);
      this.placeChest(world, random, 3, 1, 6, LOTRMod.chestBasket, 5, LOTRChestContents.NEAR_HARAD_HOUSE);
      this.placeChest(world, random, 2, 5, -3, LOTRMod.chestBasket, 5, LOTRChestContents.NEAR_HARAD_HOUSE);
      this.placePlateWithCertainty(world, random, -1, 6, 3, LOTRMod.ceramicPlateBlock, LOTRFoods.SOUTHRON);
      this.placeMug(world, random, -2, 6, 3, 0, LOTRFoods.SOUTHRON_DRINK);
      this.placeWeaponRack(world, -3, 1, 8, 1, this.getRandomHaradWeapon(random));
      this.placeWeaponRack(world, 3, 1, 8, 3, this.getRandomHaradWeapon(random));
      LOTREntityNearHaradrimBase smith = this.createSmith(world);
      this.spawnNPCAndSetHome(smith, world, 0, 1, 6, 8);
      return true;
   }

   protected LOTREntityNearHaradrimBase createSmith(World world) {
      return (LOTREntityNearHaradrimBase)(this.isUmbar() ? new LOTREntityUmbarBlacksmith(world) : new LOTREntityNearHaradBlacksmith(world));
   }
}
