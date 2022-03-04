package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorLampPost extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorLampPost(boolean flag) {
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
         this.setBlockAndMetadata(world, 0, j1, 0, this.brickBlock, this.brickMeta);
         this.setGrassToDirt(world, 0, j1 - 1, 0);
      }

      this.setBlockAndMetadata(world, 0, 1, 0, this.pillarBlock, this.pillarMeta);

      for(j1 = 2; j1 <= 4; ++j1) {
         this.setBlockAndMetadata(world, 0, j1, 0, this.rockWallBlock, this.rockWallMeta);
      }

      this.setBlockAndMetadata(world, 0, 5, 0, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
      this.setBlockAndMetadata(world, -1, 5, 0, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 1, 5, 0, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 0, 5, -1, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 5, 1, Blocks.field_150478_aa, 3);
      return true;
   }
}
