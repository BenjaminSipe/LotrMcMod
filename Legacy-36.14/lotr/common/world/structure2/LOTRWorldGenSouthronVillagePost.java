package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronVillagePost extends LOTRWorldGenSouthronStructure {
   public LOTRWorldGenSouthronVillagePost(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.plankSlabBlock = LOTRMod.woodSlabSingle3;
      this.plankSlabMeta = 2;
      this.woodBeamBlock = LOTRMod.woodBeam4;
      this.woodBeamMeta = 2;
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
         this.setBlockAndMetadata(world, 0, j1, 0, this.woodBeamBlock, this.woodBeamMeta);
         this.setGrassToDirt(world, 0, j1 - 1, 0);
      }

      this.setBlockAndMetadata(world, 0, 1, 0, this.woodBeamBlock, this.woodBeamMeta);
      this.setBlockAndMetadata(world, 0, 2, 0, this.woodBeamBlock, this.woodBeamMeta);
      this.setBlockAndMetadata(world, 0, 3, 0, this.plankSlabBlock, this.plankSlabMeta);
      return true;
   }
}
