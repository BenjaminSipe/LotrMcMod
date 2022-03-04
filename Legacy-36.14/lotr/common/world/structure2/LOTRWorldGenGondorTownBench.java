package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorTownBench extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorTownBench(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      int j1;
      int i1;
      if (this.restrictions) {
         for(int i1 = -2; i1 <= 2; ++i1) {
            for(j1 = 0; j1 <= 2; ++j1) {
               i1 = this.getTopBlock(world, i1, j1) - 1;
               if (!this.isSurface(world, i1, i1, j1)) {
                  return false;
               }
            }
         }
      }

      int k1 = 0;
      j1 = this.getTopBlock(world, 0, k1);
      this.setBlockAndMetadata(world, 0, j1, k1, this.rockSlabBlock, this.rockSlabMeta);
      this.setBlockAndMetadata(world, -1, j1, k1, this.rockStairBlock, 0);
      this.setBlockAndMetadata(world, 1, j1, k1, this.rockStairBlock, 1);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setGrassToDirt(world, i1, j1 - 1, k1);
         this.layFoundation(world, i1, j1 - 1, k1);
      }

      k1 = 2;
      j1 = this.getTopBlock(world, 0, k1);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, j1, k1, this.rockSlabBlock, this.rockSlabMeta | 8);
      }

      int[] var14 = new int[]{-2, 2};
      int var10 = var14.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         int i1 = var14[var11];
         this.setBlockAndMetadata(world, i1, j1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
         this.setGrassToDirt(world, i1, j1 - 1, k1);
         this.layFoundation(world, i1, j1 - 1, k1);
      }

      return true;
   }

   private void layFoundation(World world, int i, int j, int k) {
      for(int j1 = j; (j1 >= j || !this.isOpaque(world, i, j1, k)) && this.getY(j1) >= 0; --j1) {
         this.setBlockAndMetadata(world, i, j1, k, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
         this.setGrassToDirt(world, i, j1 - 1, k);
      }

   }
}
