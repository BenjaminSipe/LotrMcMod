package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenNomadWell extends LOTRWorldGenNomadStructure {
   public LOTRWorldGenNomadWell(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      int waterDepth;
      int i1;
      int k1;
      int k1;
      int k2;
      if (this.restrictions) {
         waterDepth = 0;
         i1 = 0;

         for(k1 = -2; k1 <= 2; ++k1) {
            for(k1 = -2; k1 <= 2; ++k1) {
               k2 = this.getTopBlock(world, k1, k1) - 1;
               if (!this.isSurface(world, k1, k2, k1)) {
                  return false;
               }

               if (k2 < waterDepth) {
                  waterDepth = k2;
               }

               if (k2 > i1) {
                  i1 = k2;
               }

               if (i1 - waterDepth > 2) {
                  return false;
               }
            }
         }
      }

      for(waterDepth = -2; waterDepth <= 2; ++waterDepth) {
         for(i1 = -2; i1 <= 2; ++i1) {
            if (!this.isSurface(world, waterDepth, 0, i1)) {
               this.laySandBase(world, waterDepth, 0, i1);
            }

            for(k1 = 1; k1 <= 4; ++k1) {
               this.setAir(world, waterDepth, k1, i1);
            }
         }
      }

      waterDepth = 3 + random.nextInt(3);

      int minY;
      int j1;
      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            k1 = Math.abs(i1);
            k2 = Math.abs(k1);
            if (k1 == 2 || k2 == 2) {
               this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150349_c, 0);
               this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
               if (k1 == 2 && k2 == 2) {
                  this.setBlockAndMetadata(world, i1, 2, k1, this.fenceBlock, this.fenceMeta);
                  this.setBlockAndMetadata(world, i1, 3, k1, Blocks.field_150478_aa, 5);
               }
            }

            for(minY = 0; minY >= -waterDepth; --minY) {
               if (!this.isOpaque(world, i1, minY, k1)) {
                  this.setBlockAndMetadata(world, i1, minY, k1, Blocks.field_150322_A, 0);
                  this.setGrassToDirt(world, i1, minY - 1, k1);
               }
            }

            if (k1 <= 1 && k2 <= 1) {
               minY = -waterDepth + 1 + random.nextInt(2);

               for(j1 = 0; j1 >= minY; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150355_j, 0);
               }
            }
         }
      }

      int grassRadius = 5;

      for(k1 = -grassRadius; k1 <= grassRadius; ++k1) {
         for(k1 = -grassRadius; k1 <= grassRadius; ++k1) {
            k2 = Math.abs(k1);
            minY = Math.abs(k1);
            if ((k2 >= 3 || minY >= 3) && k2 * k2 + minY * minY < grassRadius * grassRadius && random.nextInt(3) != 0) {
               j1 = this.getTopBlock(world, k1, k1) - 1;
               if (this.isSurface(world, k1, j1, k1)) {
                  this.setBlockAndMetadata(world, k1, j1, k1, Blocks.field_150349_c, 0);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -2, 1, 0, this.fenceGateBlock, 3);
      this.setBlockAndMetadata(world, 2, 1, 0, this.fenceGateBlock, 1);
      this.setBlockAndMetadata(world, 0, 1, -2, this.fenceGateBlock, 2);
      this.setBlockAndMetadata(world, 0, 1, 2, this.fenceGateBlock, 0);
      return true;
   }
}
