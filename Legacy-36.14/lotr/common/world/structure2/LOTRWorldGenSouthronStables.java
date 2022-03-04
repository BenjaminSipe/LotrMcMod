package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronStables extends LOTRWorldGenSouthronStructure {
   public LOTRWorldGenSouthronStables(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 8);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int k2;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -4; i2 <= 8; ++i2) {
            for(k2 = -7; k2 <= 7; ++k2) {
               j1 = this.getTopBlock(world, i2, k2) - 1;
               if (!this.isSurface(world, i2, j1, k2)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > k1) {
                  k1 = j1;
               }

               if (k1 - i1 > 8) {
                  return false;
               }
            }
         }
      }

      for(i1 = -4; i1 <= 8; ++i1) {
         for(k1 = -7; k1 <= 7; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            if (i2 <= 4 && k2 <= 7 || i1 >= 5 && i1 <= 8 && k2 <= 6) {
               for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.stoneBlock, this.stoneMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               for(j1 = 1; j1 <= 8; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }

            if (i2 <= 3 && k2 <= 6 || i1 >= 4 && i1 <= 7 && k2 <= 5) {
               j1 = random.nextInt(2);
               if (random.nextBoolean()) {
                  this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150354_m, 0);
               } else {
                  this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
               }

               if (random.nextInt(4) == 0) {
                  this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
               }
            }
         }
      }

      this.loadStrScan("southron_stable");
      this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
      this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
      this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
      this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
      this.associateBlockMetaAlias("BEAM|4", this.woodBeamBlock, this.woodBeamMeta4);
      this.associateBlockMetaAlias("BEAM|8", this.woodBeamBlock, this.woodBeamMeta8);
      this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
      this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
      this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
      this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
      this.generateStrScan(world, random, 0, 1, 0);
      this.placeChest(world, random, -3, 1, 6, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
      i1 = 1 + random.nextInt(2);

      for(k1 = 0; k1 < i1; ++k1) {
         LOTREntityNearHaradrimBase haradrim = this.createHaradrim(world);
         this.spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 8);
      }

      int[] var16 = new int[]{-4, 0, 4};
      i2 = var16.length;

      for(k2 = 0; k2 < i2; ++k2) {
         j1 = var16[k2];
         int i1 = 5;
         int j1 = 1;
         LOTREntityHorse horse = new LOTREntityHorse(world);
         this.spawnNPCAndSetHome(horse, world, i1, j1, j1, 0);
         horse.func_110214_p(0);
         horse.saddleMountForWorldGen();
         horse.func_110177_bN();
      }

      return true;
   }
}
