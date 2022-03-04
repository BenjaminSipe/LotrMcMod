package lotr.common.world.map;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.world.World;

public class LOTRWorldGenUtumnoEntrance extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenUtumnoEntrance() {
      super(false);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      int rotation = 2;
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.originY = 0;
      int radius = 8;
      int baseHeight = 40;
      int portalHeight = 10;
      int portalBase = portalHeight - 2 - 1;

      int i1;
      int k1;
      int k1;
      int width;
      int entranceSizeExtra;
      int stairX;
      int stairY;
      for(i1 = -radius; i1 <= radius; ++i1) {
         for(k1 = -radius; k1 <= radius; ++k1) {
            k1 = Math.abs(i1);
            width = Math.abs(k1);
            entranceSizeExtra = 100 + random.nextInt(10);

            for(stairX = baseHeight; stairX <= entranceSizeExtra; ++stairX) {
               if (k1 != radius && width != radius && stairX != baseHeight && stairX < entranceSizeExtra - 10) {
                  this.setAir(world, i1, stairX, k1);
               } else {
                  this.setBlockAndMetadata(world, i1, stairX, k1, LOTRMod.utumnoBrick, 2);
               }
            }

            if (k1 < radius && width < radius && random.nextInt(16) == 0) {
               stairX = 1 + random.nextInt(2);

               for(stairY = baseHeight; stairY <= baseHeight + stairX; ++stairY) {
                  this.setBlockAndMetadata(world, i1, stairY, k1, LOTRMod.utumnoBrick, 2);
               }
            }
         }
      }

      int stairZ;
      int i2;
      int i1;
      for(i1 = 0; i1 < 40; ++i1) {
         k1 = -random.nextInt(radius * 3) + random.nextInt(radius * 3);
         k1 = -random.nextInt(radius * 3) + random.nextInt(radius * 3);
         width = 1 + random.nextInt(3);
         entranceSizeExtra = width * 4 + random.nextInt(4);

         for(stairX = k1 - width; stairX <= k1 + width; ++stairX) {
            for(stairY = k1 - width; stairY <= k1 + width; ++stairY) {
               stairZ = this.getTopBlock(world, stairX, stairY);
               i2 = stairZ + entranceSizeExtra - random.nextInt(3);

               for(i1 = stairZ; i1 < i2; ++i1) {
                  this.setBlockAndMetadata(world, stairX, i1, stairY, LOTRMod.utumnoBrick, 2);
               }
            }
         }
      }

      i1 = -radius;
      k1 = -radius;
      int entranceY = 80;
      int entranceSize = 6;
      entranceSizeExtra = entranceSize + 3;

      int k1;
      for(stairX = i1 - entranceSize; stairX <= i1 + entranceSize; ++stairX) {
         for(stairY = entranceY - entranceSize; stairY <= entranceY + entranceSize; ++stairY) {
            for(stairZ = k1 - entranceSize; stairZ <= k1 + entranceSize; ++stairZ) {
               i2 = stairX - i1;
               i1 = stairY - entranceY;
               k1 = stairZ - k1;
               float dist = (float)(i2 * i2 + i1 * i1 + k1 * k1);
               if (dist < (float)(entranceSize * entranceSize) || dist < (float)(entranceSizeExtra * entranceSizeExtra) && random.nextInt(6) == 0) {
                  this.setAir(world, stairX, stairY, stairZ);
               }
            }
         }
      }

      stairX = i1 + 1;
      stairY = entranceY - entranceSize - 1;
      stairZ = k1 + 1;
      byte stairDirection = 2;

      while(true) {
         this.setBlockAndMetadata(world, stairX, stairY, stairZ, LOTRMod.utumnoBrick, 2);
         if (stairY <= baseHeight) {
            for(i1 = -2; i1 <= 2; ++i1) {
               for(k1 = -2; k1 <= 2; ++k1) {
                  int i2 = Math.abs(i1);
                  int k2 = Math.abs(k1);

                  int j1;
                  for(j1 = portalBase; j1 <= baseHeight + 1; ++j1) {
                     if (i2 < 2 && k2 < 2) {
                        if (j1 == portalBase) {
                           this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.utumnoBrickEntrance, 0);
                        } else if (j1 < portalHeight) {
                           this.setAir(world, i1, j1, k1);
                        } else if (j1 == portalHeight && i2 <= 1 && k2 <= 1) {
                           this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.utumnoPortal, 0);
                        } else {
                           this.setAir(world, i1, j1, k1);
                        }
                     } else {
                        this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.utumnoBrickEntrance, 0);
                     }
                  }

                  if (i2 == 2 && k2 == 2) {
                     j1 = baseHeight + 2;
                     int max = j1 + 2 + random.nextInt(2);

                     int j1;
                     for(j1 = j1; j1 <= max; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.utumnoPillar, 1);
                     }

                     this.setBlockAndMetadata(world, i1, max + 1, k1, LOTRMod.utumnoBrick, 2);
                     j1 = max + 2;
                     max = j1 + 2;

                     for(j1 = j1; j1 <= max; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.utumnoPillar, 1);
                     }
                  }
               }
            }

            return true;
         }

         --stairY;
         if (stairDirection == 0 && this.getBlock(world, stairX, stairY, stairZ + 1).func_149662_c()) {
            stairDirection = 1;
         }

         if (stairDirection == 1 && this.getBlock(world, stairX - 1, stairY, stairZ).func_149662_c()) {
            stairDirection = 2;
         }

         if (stairDirection == 2 && this.getBlock(world, stairX, stairY, stairZ - 1).func_149662_c()) {
            stairDirection = 3;
         }

         if (stairDirection == 3 && this.getBlock(world, stairX + 1, stairY, stairZ).func_149662_c()) {
            stairDirection = 0;
         }

         if (stairDirection == 0) {
            ++stairZ;
         }

         if (stairDirection == 1) {
            --stairX;
         }

         if (stairDirection == 2) {
            --stairZ;
         }

         if (stairDirection == 3) {
            ++stairX;
         }
      }
   }
}
