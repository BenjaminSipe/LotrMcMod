package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedBeaconTower extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenRuinedBeaconTower(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      int height = 4 + random.nextInt(4);
      j += height + 1;
      this.setOriginAndRotation(world, i, j, k, rotation, 3);

      int i1;
      int k1;
      int i2;
      int i1;
      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            i2 = Math.abs(i1);
            i1 = Math.abs(k1);
            int j1 = 0;
            if (i2 == 2 && i1 < 2 || i1 == 2 && i2 < 2) {
               j1 -= random.nextInt(4);
            }

            while(!this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0) {
               if (i2 == 2 && i1 == 2) {
                  this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.pillar, 6);
               } else {
                  this.placeRandomBrick(world, random, i1, j1, k1);
               }

               this.setGrassToDirt(world, i1, j1 - 1, k1);
               --j1;
            }
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.slabDouble, 2);
         }
      }

      this.setBlockAndMetadata(world, 0, 1, 0, LOTRMod.rock, 1);
      int[] var18 = new int[]{-2, 2};
      k1 = var18.length;

      for(i2 = 0; i2 < k1; ++i2) {
         i1 = var18[i2];
         int[] var19 = new int[]{-2, 2};
         int var13 = var19.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            int k1 = var19[var14];
            int pillarHeight = 1 + random.nextInt(5);

            for(int j1 = 1; j1 <= pillarHeight; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.pillar, 6);
            }
         }
      }

      return true;
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(5) == 0) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
      } else {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 1);
      }

   }

   private void placeRandomSlab(World world, Random random, int i, int j, int k, boolean inverted) {
      if (random.nextInt(5) == 0) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle, 4 + random.nextInt(2) + (inverted ? 8 : 0));
      } else {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle, 3 + (inverted ? 8 : 0));
      }

   }

   private void placeRandomStairs(World world, Random random, int i, int j, int k, int metadata) {
      if (random.nextInt(5) == 0) {
         this.setBlockAndMetadata(world, i, j, k, random.nextBoolean() ? LOTRMod.stairsGondorBrickMossy : LOTRMod.stairsGondorBrickCracked, metadata);
      } else {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsGondorBrick, metadata);
      }

   }
}
