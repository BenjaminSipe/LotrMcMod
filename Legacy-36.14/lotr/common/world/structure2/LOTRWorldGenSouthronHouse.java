package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronHouse extends LOTRWorldGenSouthronStructure {
   public LOTRWorldGenSouthronHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int j1;
      int k1;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(j1 = -3; j1 <= 3; ++j1) {
            for(k1 = -5; k1 <= 6; ++k1) {
               j1 = this.getTopBlock(world, j1, k1) - 1;
               if (!this.isSurface(world, j1, j1, k1)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > k1) {
                  k1 = j1;
               }

               if (k1 - i1 > 6) {
                  return false;
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -6; k1 <= 6; ++k1) {
            j1 = Math.abs(i1);
            k1 = Math.abs(k1);
            if (j1 <= 2 && k1 <= 4) {
               for(j1 = 0; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.stoneBlock, this.stoneMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               for(j1 = 1; j1 <= 7; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }

            if (j1 <= 2 && k1 == 5 || i1 >= -3 && i1 <= 2 && k1 == 6) {
               for(j1 = 0; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               for(j1 = 1; j1 <= 7; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }
         }
      }

      this.loadStrScan("southron_house");
      this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
      this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
      this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
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
      this.generateStrScan(world, random, 0, 0, 0);
      if (!this.isOpaque(world, 0, 0, 7) || this.isOpaque(world, 0, 1, 7)) {
         for(i1 = -4; i1 <= 2; ++i1) {
            for(k1 = 6; k1 <= 7; ++k1) {
               if (k1 == 7 || i1 == -4 && k1 == 6) {
                  for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                     this.setGrassToDirt(world, i1, j1 - 1, k1);
                  }

                  for(j1 = 1; j1 <= 3; ++j1) {
                     this.setAir(world, i1, j1, k1);
                  }
               }
            }
         }
      }

      this.placeWallBanner(world, -2, 3, 0, this.bannerType, 1);
      int[] var13 = new int[]{-2, 0, 2};
      k1 = var13.length;

      for(j1 = 0; j1 < k1; ++j1) {
         k1 = var13[j1];
         int i1 = -1;
         int j1 = 2;
         if (random.nextBoolean()) {
            this.placePlate(world, random, i1, j1, k1, LOTRMod.woodPlateBlock, LOTRFoods.SOUTHRON);
         } else {
            this.placeMug(world, random, i1, j1, k1, 1, LOTRFoods.SOUTHRON_DRINK);
         }
      }

      this.setBlockAndMetadata(world, -1, 5, -2, this.bedBlock, 2);
      this.setBlockAndMetadata(world, -1, 5, -3, this.bedBlock, 10);
      this.setBlockAndMetadata(world, 1, 5, -2, this.bedBlock, 2);
      this.setBlockAndMetadata(world, 1, 5, -3, this.bedBlock, 10);
      this.placeFlowerPot(world, 0, 6, -3, this.getRandomFlower(world, random));
      this.placeChest(world, random, -1, 5, 3, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
      i1 = 1 + random.nextInt(2);

      for(k1 = 0; k1 < i1; ++k1) {
         LOTREntityNearHaradrimBase haradrim = this.createHaradrim(world);
         this.spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 16);
      }

      return true;
   }
}
