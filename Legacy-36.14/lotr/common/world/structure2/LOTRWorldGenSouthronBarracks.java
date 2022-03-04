package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNearHaradrimArcher;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.npc.LOTREntityNearHaradrimWarrior;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronBarracks extends LOTRWorldGenSouthronStructure {
   public LOTRWorldGenSouthronBarracks(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 8);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(j1 = -6; j1 <= 5; ++j1) {
            for(int k1 = -8; k1 <= 8; ++k1) {
               int j1 = this.getTopBlock(world, j1, k1) - 1;
               if (!this.isSurface(world, j1, j1, k1)) {
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

      for(minHeight = -5; minHeight <= 4; ++minHeight) {
         for(maxHeight = -7; maxHeight <= 7; ++maxHeight) {
            for(j1 = 1; j1 <= 5; ++j1) {
               this.setAir(world, minHeight, j1, maxHeight);
            }
         }
      }

      this.loadStrScan("southron_barracks");
      this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
      this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
      this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
      this.associateBlockMetaAlias("BEAM|4", this.woodBeamBlock, this.woodBeamMeta4);
      this.associateBlockMetaAlias("BEAM|8", this.woodBeamBlock, this.woodBeamMeta8);
      this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
      this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
      this.generateStrScan(world, random, 0, 0, 0);

      for(minHeight = -4; minHeight <= 4; minHeight += 2) {
         if (random.nextBoolean()) {
            this.placeChest(world, random, -4, 1, minHeight, LOTRMod.chestBasket, 4, LOTRChestContents.NEAR_HARAD_TOWER, 1 + random.nextInt(2));
         } else {
            this.setBlockAndMetadata(world, -4, 1, minHeight, LOTRMod.chestBasket, 4);
         }

         if (random.nextBoolean()) {
            this.placeChest(world, random, 3, 1, minHeight, LOTRMod.chestBasket, 5, LOTRChestContents.NEAR_HARAD_TOWER, 1 + random.nextInt(2));
         } else {
            this.setBlockAndMetadata(world, 3, 1, minHeight, LOTRMod.chestBasket, 5);
         }
      }

      for(minHeight = -5; minHeight <= 5; minHeight += 2) {
         for(maxHeight = 1; maxHeight <= 2; ++maxHeight) {
            this.setBlockAndMetadata(world, -3, maxHeight, minHeight, this.bedBlock, 3);
            this.setBlockAndMetadata(world, -4, maxHeight, minHeight, this.bedBlock, 11);
            this.setBlockAndMetadata(world, 2, maxHeight, minHeight, this.bedBlock, 1);
            this.setBlockAndMetadata(world, 3, maxHeight, minHeight, this.bedBlock, 9);
         }
      }

      minHeight = 2 + random.nextInt(3);

      for(maxHeight = 0; maxHeight < minHeight; ++maxHeight) {
         LOTREntityNearHaradrimBase haradrim = this.createWarrior(world, random);
         this.spawnNPCAndSetHome(haradrim, world, random.nextBoolean() ? -1 : 0, 1, 0, 16);
      }

      return true;
   }

   protected LOTREntityNearHaradrimBase createWarrior(World world, Random random) {
      return (LOTREntityNearHaradrimBase)(random.nextInt(3) == 0 ? new LOTREntityNearHaradrimArcher(world) : new LOTREntityNearHaradrimWarrior(world));
   }
}
