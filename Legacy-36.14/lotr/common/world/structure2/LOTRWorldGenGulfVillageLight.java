package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGulfVillageLight extends LOTRWorldGenGulfStructure {
   public LOTRWorldGenGulfVillageLight(boolean flag) {
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

      for(int j1 = 0; (j1 >= 0 || !this.isOpaque(world, 0, j1, 0)) && this.getY(j1) >= 0; --j1) {
         this.setBlockAndMetadata(world, 0, j1, 0, this.woodBlock, this.woodMeta);
         this.setGrassToDirt(world, 0, j1 - 1, 0);
      }

      this.setBlockAndMetadata(world, 0, 1, 0, this.woodBlock, this.woodMeta);
      this.setBlockAndMetadata(world, 0, 2, 0, this.boneWallBlock, this.boneWallMeta);
      this.setBlockAndMetadata(world, 0, 3, 0, this.boneWallBlock, this.boneWallMeta);
      this.setBlockAndMetadata(world, 0, 4, 0, this.boneWallBlock, this.boneWallMeta);
      this.placeSkull(world, 0, 5, 0, 0);
      this.setBlockAndMetadata(world, 1, 4, 0, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 1, 5, 0, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, -1, 4, 0, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -1, 5, 0, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 0, 4, -1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 5, -1, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 0, 4, 1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 5, 1, Blocks.field_150478_aa, 5);
      return true;
   }
}
