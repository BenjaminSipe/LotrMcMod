package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeHedgePart extends LOTRWorldGenBreeStructure {
   private boolean grassOnly = false;

   public LOTRWorldGenBreeHedgePart(boolean flag) {
      super(flag);
   }

   public LOTRWorldGenBreeHedgePart setGrassOnly() {
      this.grassOnly = true;
      return this;
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.fenceBlock = Blocks.field_150422_aJ;
      this.fenceMeta = 0;
      this.beamBlock = LOTRMod.woodBeamV1;
      this.beamMeta = 0;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      int j1;
      if (this.restrictions) {
         int i1 = 0;
         int k1 = 0;
         j1 = this.getTopBlock(world, i1, k1) - 1;
         if (!this.isSurface(world, i1, j1, k1) || this.grassOnly && this.getBlock(world, i1, j1, k1) != Blocks.field_150349_c) {
            return false;
         }
      }

      for(int j1 = 0; !this.isOpaque(world, 0, j1, 0) && this.getY(j1) >= 0; --j1) {
         this.setBlockAndMetadata(world, 0, j1, 0, LOTRMod.dirtPath, 0);
         this.setGrassToDirt(world, 0, j1 - 1, 0);
      }

      boolean hasBeams = random.nextInt(4) == 0;
      int height = 3 + random.nextInt(2);

      for(j1 = 1; j1 <= height; ++j1) {
         if (hasBeams && j1 <= 2) {
            this.setBlockAndMetadata(world, 0, j1, 0, this.beamBlock, this.beamMeta);
            this.setGrassToDirt(world, 0, j1 - 1, 0);
         } else if (random.nextInt(4) == 0) {
            this.setBlockAndMetadata(world, 0, j1, 0, this.fenceBlock, this.fenceMeta);
         } else {
            int randLeaf = random.nextInt(4);
            if (randLeaf == 0) {
               this.setBlockAndMetadata(world, 0, j1, 0, Blocks.field_150362_t, 4);
            } else if (randLeaf == 1) {
               this.setBlockAndMetadata(world, 0, j1, 0, LOTRMod.leaves2, 5);
            } else if (randLeaf == 2) {
               this.setBlockAndMetadata(world, 0, j1, 0, LOTRMod.leaves4, 4);
            } else if (randLeaf == 3) {
               this.setBlockAndMetadata(world, 0, j1, 0, LOTRMod.leaves7, 4);
            }
         }
      }

      return true;
   }
}
