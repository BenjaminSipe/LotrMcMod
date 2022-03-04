package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTRWorldGenGondorObelisk extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorObelisk(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int j1;
      int k1;
      int j1;
      if (this.restrictions) {
         for(j1 = -3; j1 <= 3; ++j1) {
            for(k1 = -3; k1 <= 3; ++k1) {
               j1 = this.getTopBlock(world, j1, k1) - 1;
               if (!this.isSurface(world, j1, j1, k1)) {
                  return false;
               }
            }
         }
      }

      for(j1 = -3; j1 <= 3; ++j1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            for(j1 = 3; (j1 >= 0 || !this.isOpaque(world, j1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.placeRandomBrick(world, random, j1, j1, k1);
               this.setGrassToDirt(world, j1, j1 - 1, k1);
            }
         }
      }

      for(j1 = -2; j1 <= 2; ++j1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            for(j1 = 4; j1 <= 8; ++j1) {
               this.setBlockAndMetadata(world, j1, j1, k1, this.rockBlock, this.rockMeta);
            }
         }
      }

      for(j1 = -3; j1 <= 3; ++j1) {
         this.placeRandomStairs(world, random, j1, 4, -3, 2);
         this.placeRandomStairs(world, random, j1, 4, 3, 3);
      }

      for(j1 = -2; j1 <= 2; ++j1) {
         this.placeRandomStairs(world, random, -3, 4, j1, 1);
         this.placeRandomStairs(world, random, 3, 4, j1, 0);
      }

      for(j1 = -1; j1 <= 1; ++j1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            for(j1 = 9; j1 <= 14; ++j1) {
               this.placeRandomBrick(world, random, j1, j1, k1);
            }
         }
      }

      for(j1 = -2; j1 <= 2; ++j1) {
         this.placeRandomStairs(world, random, j1, 9, -2, 2);
         this.placeRandomStairs(world, random, j1, 9, 2, 3);
      }

      for(j1 = -1; j1 <= 1; ++j1) {
         this.placeRandomStairs(world, random, -2, 9, j1, 1);
         this.placeRandomStairs(world, random, 2, 9, j1, 0);
      }

      for(j1 = 15; j1 <= 18; ++j1) {
         this.placeRandomBrick(world, random, 0, j1, 0);
         this.placeRandomBrick(world, random, -1, j1, 0);
         this.placeRandomBrick(world, random, 1, j1, 0);
         this.placeRandomBrick(world, random, 0, j1, -1);
         this.placeRandomBrick(world, random, 0, j1, 1);
      }

      this.placeRandomStairs(world, random, -1, 19, 0, 1);
      this.placeRandomStairs(world, random, 1, 19, 0, 0);
      this.placeRandomStairs(world, random, 0, 19, -1, 2);
      this.placeRandomStairs(world, random, 0, 19, 1, 3);
      this.placeRandomBrick(world, random, 0, 19, 0);
      this.setBlockAndMetadata(world, 0, 20, 0, LOTRMod.beacon, 0);
      return true;
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(4) == 0) {
         if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, i, j, k, this.brickMossyBlock, this.brickMossyMeta);
         } else {
            this.setBlockAndMetadata(world, i, j, k, this.brickCrackedBlock, this.brickCrackedMeta);
         }
      } else {
         this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
      }

   }

   private void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
      if (random.nextInt(4) == 0) {
         if (random.nextBoolean()) {
            this.setBlockAndMetadata(world, i, j, k, this.brickMossyStairBlock, meta);
         } else {
            this.setBlockAndMetadata(world, i, j, k, this.brickCrackedStairBlock, meta);
         }
      } else {
         this.setBlockAndMetadata(world, i, j, k, this.brickStairBlock, meta);
      }

   }
}
