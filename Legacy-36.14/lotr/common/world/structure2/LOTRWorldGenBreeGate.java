package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeGate extends LOTRWorldGenBreeStructure {
   public LOTRWorldGenBreeGate(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = 0; k1 <= 0; ++k1) {
               j1 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, j1, k1)) {
                  return false;
               }
            }
         }
      }

      int[] var13 = new int[]{-4, 4};
      k1 = var13.length;

      for(j1 = 0; j1 < k1; ++j1) {
         int i1 = var13[j1];
         int k1 = 0;

         for(int j1 = 4; (j1 >= 0 || !this.isOpaque(world, 0, j1, 0)) && this.getY(j1) >= 0; --j1) {
            this.setBlockAndMetadata(world, i1, j1, k1, this.beamBlock, this.beamMeta);
            this.setGrassToDirt(world, i1, j1 - 1, k1);
         }

         this.setBlockAndMetadata(world, i1, 5, k1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 6, k1, Blocks.field_150478_aa, 5);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         int k1 = 0;

         for(j1 = 0; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
            this.placeRandomFloor(world, random, i1, j1, k1);
            this.setGrassToDirt(world, i1, j1 - 1, k1);
         }

         for(j1 = 1; j1 <= 3; ++j1) {
            this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.gateWoodenCross, 2);
         }

         this.setBlockAndMetadata(world, i1, 4, k1, this.plankSlabBlock, this.plankSlabMeta);
      }

      return true;
   }
}
