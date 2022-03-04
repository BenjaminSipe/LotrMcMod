package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronFortCorner extends LOTRWorldGenSouthronStructure {
   public LOTRWorldGenSouthronFortCorner(boolean flag) {
      super(flag);
   }

   protected boolean canUseRedBricks() {
      return false;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);

      int k1;
      boolean beam;
      int i3;
      int j1;
      int i3;
      for(k1 = -4; k1 <= 1; ++k1) {
         int i2 = Math.abs(k1);
         int k1 = 0;
         this.findSurface(world, k1, k1);
         if (k1 <= 0) {
            beam = i2 % 4 == 0;
            if (beam) {
               for(i3 = 6; (i3 >= 1 || !this.isOpaque(world, k1, i3, k1)) && this.getY(i3) >= 0; --i3) {
                  this.setBlockAndMetadata(world, k1, i3, k1, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, k1, i3 - 1, k1);
               }
            } else {
               for(i3 = 5; (i3 >= 1 || !this.isOpaque(world, k1, i3, k1)) && this.getY(i3) >= 0; --i3) {
                  this.setBlockAndMetadata(world, k1, i3, k1, this.plankBlock, this.plankMeta);
                  this.setGrassToDirt(world, k1, i3 - 1, k1);
               }

               if (i2 % 2 == 1) {
                  this.setBlockAndMetadata(world, k1, 5, k1, this.plankStairBlock, 2);
               } else {
                  this.setBlockAndMetadata(world, k1, 6, k1, this.fenceBlock, this.fenceMeta);
               }
            }

            if (k1 <= -1) {
               i3 = k1 + 1;
               this.setBlockAndMetadata(world, k1, 2, i3, this.brickStairBlock, 3);

               for(j1 = 1; (j1 >= 1 || !this.isOpaque(world, k1, j1, i3)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, k1, j1, i3, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, k1, j1 - 1, i3);
               }
            }
         }

         if (k1 <= 1) {
            i3 = k1 - 1;
            this.setBlockAndMetadata(world, k1, 2, i3, this.brickStairBlock, 2);

            for(i3 = 1; (i3 >= 1 || !this.isOpaque(world, k1, i3, i3)) && this.getY(i3) >= 0; --i3) {
               this.setBlockAndMetadata(world, k1, i3, i3, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, k1, i3 - 1, i3);
            }
         }
      }

      for(k1 = 0; k1 <= 4; ++k1) {
         int i1 = 0;
         int k2 = Math.abs(k1);
         this.findSurface(world, i1, k1);
         if (k1 >= 1) {
            beam = k2 % 4 == 0;
            if (beam) {
               for(i3 = 6; (i3 >= 1 || !this.isOpaque(world, i1, i3, k1)) && this.getY(i3) >= 0; --i3) {
                  this.setBlockAndMetadata(world, i1, i3, k1, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, i1, i3 - 1, k1);
               }
            } else {
               for(i3 = 5; (i3 >= 1 || !this.isOpaque(world, i1, i3, k1)) && this.getY(i3) >= 0; --i3) {
                  this.setBlockAndMetadata(world, i1, i3, k1, this.plankBlock, this.plankMeta);
                  this.setGrassToDirt(world, i1, i3 - 1, k1);
               }

               if (k2 % 2 == 1) {
                  this.setBlockAndMetadata(world, i1, 5, k1, this.plankStairBlock, 0);
               } else {
                  this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
               }
            }

            if (k1 >= 2) {
               i3 = i1 - 1;
               this.setBlockAndMetadata(world, i3, 2, k1, this.brickStairBlock, 1);

               for(j1 = 1; (j1 >= 1 || !this.isOpaque(world, i3, j1, k1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i3, j1, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i3, j1 - 1, k1);
               }
            }
         }

         if (k1 >= 0) {
            i3 = i1 + 1;
            this.setBlockAndMetadata(world, i3, 2, k1, this.brickStairBlock, 0);

            for(i3 = 1; (i3 >= 1 || !this.isOpaque(world, i3, i3, k1)) && this.getY(i3) >= 0; --i3) {
               this.setBlockAndMetadata(world, i3, i3, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i3, i3 - 1, k1);
            }
         }
      }

      return true;
   }
}
