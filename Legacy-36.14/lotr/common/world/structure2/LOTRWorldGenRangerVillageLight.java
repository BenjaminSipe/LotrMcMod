package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRangerVillageLight extends LOTRWorldGenRangerStructure {
   public LOTRWorldGenRangerVillageLight(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      if (this.restrictions) {
         int i1 = 0;
         int k1 = 0;
         int j1 = this.getTopBlock(world, i1, k1) - 1;
         if (!this.isSurface(world, i1, j1, k1)) {
            return false;
         }
      }

      int j1;
      for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, 0, j1, 0)) && this.getY(j1) >= 0; --j1) {
         this.setBlockAndMetadata(world, 0, j1, 0, this.logBlock, this.logMeta);
         this.setGrassToDirt(world, 0, j1 - 1, 0);
      }

      for(j1 = 1; j1 <= 2; ++j1) {
         this.setBlockAndMetadata(world, 0, j1, 0, this.logBlock, this.logMeta);
      }

      this.setBlockAndMetadata(world, 0, 3, 0, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 4, 0, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 5, 0, Blocks.field_150478_aa, 5);
      return true;
   }
}
