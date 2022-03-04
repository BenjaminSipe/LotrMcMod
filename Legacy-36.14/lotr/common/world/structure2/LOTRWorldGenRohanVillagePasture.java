package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanVillagePasture extends LOTRWorldGenRohanStructure {
   public LOTRWorldGenRohanVillagePasture(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int j2;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -4; i2 <= 4; ++i2) {
            for(j2 = -4; j2 <= 4; ++j2) {
               j1 = this.getTopBlock(world, i2, j2) - 1;
               if (!this.isSurface(world, i2, j1, j2)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > k1) {
                  k1 = j1;
               }

               if (k1 - i1 > 4) {
                  return false;
               }
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(k1 = -4; k1 <= 4; ++k1) {
            i2 = Math.abs(i1);
            j2 = Math.abs(k1);

            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               if (j1 == 0) {
                  int randomFloor = random.nextInt(3);
                  if (randomFloor == 0) {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150349_c, 0);
                  } else if (randomFloor == 1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 1);
                  } else if (randomFloor == 2) {
                     this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
                  }
               } else {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 0);
               }

               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 5; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            if (i2 == 4 && j2 == 4) {
               this.setGrassToDirt(world, i1, -1, k1);

               for(j1 = 1; j1 <= 2; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.logBlock, this.logMeta);
               }

               this.setBlockAndMetadata(world, i1, 3, k1, Blocks.field_150478_aa, 5);
            } else if (i2 == 4 || j2 == 4) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 1, -4, this.fenceGateBlock, 0);

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            if (random.nextInt(3) == 0) {
               int j1 = 1;
               j2 = 1;
               if (i1 == 0 && k1 == 0 && random.nextBoolean()) {
                  ++j2;
               }

               for(j1 = j1; j1 <= j2; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150407_cf, 0);
               }
            }
         }
      }

      i1 = 4 + random.nextInt(5);

      for(k1 = 0; k1 < i1; ++k1) {
         EntityCreature animal = LOTRWorldGenGondorBarn.getRandomAnimal(world, random);
         j2 = 3 * (random.nextBoolean() ? 1 : -1);
         j1 = 3 * (random.nextBoolean() ? 1 : -1);
         if (random.nextBoolean()) {
            this.spawnNPCAndSetHome(animal, world, j2, 1, 0, 0);
         } else {
            this.spawnNPCAndSetHome(animal, world, 0, 1, j1, 0);
         }

         animal.func_110177_bN();
      }

      return true;
   }
}
