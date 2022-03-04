package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanVillagePalisade extends LOTRWorldGenRohanStructure {
   public LOTRWorldGenRohanVillagePalisade(boolean flag) {
      super(flag);
   }

   protected boolean oneWoodType() {
      return true;
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

      int height;
      for(height = 1; (height >= 0 || !this.isOpaque(world, 0, height, 0)) && this.getY(height) >= 0; --height) {
         this.setBlockAndMetadata(world, 0, height, 0, this.cobbleBlock, this.cobbleMeta);
         this.setGrassToDirt(world, 0, height - 1, 0);
      }

      height = 5 + random.nextInt(2);

      for(int j1 = 2; j1 <= height; ++j1) {
         this.setBlockAndMetadata(world, 0, j1, 0, this.logBlock, this.logMeta);
      }

      return true;
   }
}
