package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenGulfPyramid extends LOTRWorldGenGulfStructure {
   public LOTRWorldGenGulfPyramid(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 11);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      if (this.restrictions) {
         for(i1 = -11; i1 <= 11; ++i1) {
            for(k1 = -11; k1 <= 11; ++k1) {
               i2 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, i2, k1)) {
                  return false;
               }
            }
         }
      }

      for(i1 = -10; i1 <= 10; ++i1) {
         for(k1 = -10; k1 <= 10; ++k1) {
            for(i2 = 1; i2 <= 20; ++i2) {
               this.setAir(world, i1, i2, k1);
            }
         }
      }

      this.loadStrScan("gulf_pyramid");
      this.associateBlockMetaAlias("STONE", Blocks.field_150322_A, 0);
      this.associateBlockAlias("STONE_STAIR", Blocks.field_150372_bz);
      this.associateBlockMetaAlias("STONE2", LOTRMod.redSandstone, 0);
      this.associateBlockAlias("STONE2_STAIR", LOTRMod.stairsRedSandstone);
      this.addBlockMetaAliasOption("BRICK", 8, LOTRMod.brick, 15);
      this.addBlockMetaAliasOption("BRICK", 2, LOTRMod.brick3, 11);
      this.addBlockAliasOption("BRICK_STAIR", 8, LOTRMod.stairsNearHaradBrick);
      this.addBlockAliasOption("BRICK_STAIR", 2, LOTRMod.stairsNearHaradBrickCracked);
      this.addBlockMetaAliasOption("BRICK_WALL", 8, LOTRMod.wall, 15);
      this.addBlockMetaAliasOption("BRICK_WALL", 2, LOTRMod.wall3, 3);
      this.addBlockMetaAliasOption("PILLAR", 10, LOTRMod.pillar, 5);
      this.addBlockMetaAliasOption("BRICK2", 8, LOTRMod.brick3, 13);
      this.addBlockMetaAliasOption("BRICK2", 2, LOTRMod.brick3, 14);
      this.associateBlockMetaAlias("BRICK2_CARVED", LOTRMod.brick3, 15);
      this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockMetaAlias("ROOF_SLAB", this.roofSlabBlock, this.roofSlabMeta);
      this.associateBlockMetaAlias("ROOF_SLAB_INV", this.roofSlabBlock, this.roofSlabMeta | 8);
      this.associateBlockAlias("ROOF_STAIR", this.roofStairBlock);
      this.generateStrScan(world, random, 0, 1, 0);

      int k2;
      for(i1 = -5; i1 <= 5; ++i1) {
         for(k1 = -5; k1 <= 5; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            int j1 = 11;
            if ((i2 > 2 || k2 > 2) && this.isOpaque(world, i1, j1 - 1, k1) && this.isAir(world, i1, j1, k1) && random.nextInt(12) == 0) {
               this.placeChest(world, random, i1, j1, k1, LOTRMod.chestBasket, MathHelper.func_76136_a(random, 2, 5), LOTRChestContents.GULF_PYRAMID);
            }
         }
      }

      int maxStep = 4;
      int[] var17 = new int[]{-11, 11};
      i2 = var17.length;

      int step;
      int k1;
      int j1;
      int j2;
      int i1;
      for(k2 = 0; k2 < i2; ++k2) {
         i1 = var17[k2];

         for(step = 0; step < maxStep; ++step) {
            k1 = -7 - step;
            j1 = 0 - step;
            if (this.isOpaque(world, k1, j1, i1)) {
               break;
            }

            this.setBlockAndMetadata(world, k1, j1, i1, Blocks.field_150372_bz, 1);
            this.setGrassToDirt(world, k1, j1 - 1, i1);

            for(j2 = j1 - 1; !this.isOpaque(world, k1, j2, i1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, k1, j2, i1, Blocks.field_150322_A, 0);
               this.setGrassToDirt(world, k1, j2 - 1, i1);
            }
         }

         for(step = 0; step < maxStep; ++step) {
            k1 = 7 + step;
            j1 = 0 - step;
            if (this.isOpaque(world, k1, j1, i1)) {
               break;
            }

            this.setBlockAndMetadata(world, k1, j1, i1, Blocks.field_150372_bz, 0);
            this.setGrassToDirt(world, k1, j1 - 1, i1);

            for(j2 = j1 - 1; !this.isOpaque(world, k1, j2, i1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, k1, j2, i1, Blocks.field_150322_A, 0);
               this.setGrassToDirt(world, k1, j2 - 1, i1);
            }
         }
      }

      var17 = new int[]{-11, 11};
      i2 = var17.length;

      for(k2 = 0; k2 < i2; ++k2) {
         i1 = var17[k2];

         for(step = 0; step < maxStep; ++step) {
            k1 = -7 - step;
            j1 = 0 - step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150372_bz, 2);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150322_A, 0);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }

         for(step = 0; step < maxStep; ++step) {
            k1 = 7 + step;
            j1 = 0 - step;
            if (this.isOpaque(world, i1, j1, k1)) {
               break;
            }

            this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150372_bz, 3);
            this.setGrassToDirt(world, i1, j1 - 1, k1);

            for(j2 = j1 - 1; !this.isOpaque(world, i1, j2, k1) && this.getY(j2) >= 0; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, Blocks.field_150322_A, 0);
               this.setGrassToDirt(world, i1, j2 - 1, k1);
            }
         }
      }

      return true;
   }
}
