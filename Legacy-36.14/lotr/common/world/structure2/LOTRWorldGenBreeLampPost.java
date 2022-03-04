package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBreeLampPost extends LOTRWorldGenBreeStructure {
   public LOTRWorldGenBreeLampPost(boolean flag) {
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
         if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, 0, j1, 0, Blocks.field_150347_e, 0);
         } else {
            this.setBlockAndMetadata(world, 0, j1, 0, Blocks.field_150341_Y, 0);
         }

         this.setGrassToDirt(world, 0, j1 - 1, 0);
      }

      if (random.nextBoolean()) {
         this.setBlockAndMetadata(world, 0, 1, 0, Blocks.field_150463_bK, 0);
      } else {
         this.setBlockAndMetadata(world, 0, 1, 0, Blocks.field_150463_bK, 1);
      }

      for(j1 = 2; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, 0, j1, 0, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, 0, 4, 0, Blocks.field_150478_aa, 5);
      return true;
   }
}
