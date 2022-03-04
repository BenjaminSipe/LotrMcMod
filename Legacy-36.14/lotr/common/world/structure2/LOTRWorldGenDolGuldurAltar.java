package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDolGuldurAltar extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenDolGuldurAltar(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      int k1;
      int z;
      int i1;
      if (this.restrictions) {
         for(k1 = -5; k1 <= 5; ++k1) {
            for(z = -5; z <= 5; ++z) {
               i1 = this.getTopBlock(world, k1, z);
               Block block = this.getBlock(world, k1, i1 - 1, z);
               if (block != Blocks.field_150348_b && block != Blocks.field_150346_d && block != Blocks.field_150349_c) {
                  return false;
               }
            }
         }
      }

      int k1;
      for(k1 = -5; k1 <= 5; ++k1) {
         for(z = -5; z <= 5; ++z) {
            for(i1 = 0; !this.isOpaque(world, k1, i1, z) && this.getY(i1) >= 0; --i1) {
               this.placeRandomBrick(world, random, k1, i1, z);
               this.setGrassToDirt(world, k1, i1 - 1, z);
            }

            for(i1 = 1; i1 <= 8; ++i1) {
               this.setAir(world, k1, i1, z);
            }

            i1 = Math.abs(k1);
            k1 = Math.abs(z);
            if (i1 <= 4 && k1 <= 4) {
               this.placeRandomBrick(world, random, k1, 1, z);
            }

            if (i1 <= 3 && k1 <= 3) {
               if (random.nextInt(10) == 0) {
                  this.setBlockAndMetadata(world, k1, 2, z, LOTRMod.guldurilBrick, 4);
               } else {
                  this.placeRandomBrick(world, random, k1, 2, z);
               }
            }
         }
      }

      for(k1 = -5; k1 <= 5; ++k1) {
         this.placeRandomStairs(world, random, k1, 1, -5, 2);
         this.placeRandomStairs(world, random, k1, 1, 5, 3);
      }

      for(k1 = -5; k1 <= 5; ++k1) {
         this.placeRandomStairs(world, random, -5, 1, k1, 1);
         this.placeRandomStairs(world, random, 5, 1, k1, 0);
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         this.placeRandomStairs(world, random, k1, 2, -4, 2);
         this.placeRandomStairs(world, random, k1, 2, 4, 3);
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         this.placeRandomStairs(world, random, -4, 2, k1, 1);
         this.placeRandomStairs(world, random, 4, 2, k1, 0);
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         this.placeRandomStairs(world, random, k1, 3, -1, 2);
         this.placeRandomStairs(world, random, k1, 3, 1, 3);
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         this.placeRandomStairs(world, random, -1, 3, k1, 1);
         this.placeRandomStairs(world, random, 1, 3, k1, 0);
      }

      this.placeRandomBrick(world, random, 0, 3, 0);
      this.setBlockAndMetadata(world, 0, 4, 0, LOTRMod.dolGuldurTable, 0);

      for(k1 = -4; k1 <= 3; k1 += 7) {
         for(z = -4; z <= 3; z += 7) {
            for(i1 = k1; i1 <= k1 + 1; ++i1) {
               for(k1 = z; k1 <= z + 1; ++k1) {
                  for(int j1 = 2; j1 <= 5; ++j1) {
                     this.placeRandomBrick(world, random, i1, j1, k1);
                  }
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -4, 6, -4, LOTRMod.wall2, 8);
      this.setBlockAndMetadata(world, -4, 7, -4, LOTRMod.morgulTorch, 5);
      this.setBlockAndMetadata(world, 4, 6, -4, LOTRMod.wall2, 8);
      this.setBlockAndMetadata(world, 4, 7, -4, LOTRMod.morgulTorch, 5);
      this.setBlockAndMetadata(world, -4, 6, 4, LOTRMod.wall2, 8);
      this.setBlockAndMetadata(world, -4, 7, 4, LOTRMod.morgulTorch, 5);
      this.setBlockAndMetadata(world, 4, 6, 4, LOTRMod.wall2, 8);
      this.setBlockAndMetadata(world, 4, 7, 4, LOTRMod.morgulTorch, 5);

      for(k1 = -4; k1 <= 4; k1 += 8) {
         this.placeRandomStairs(world, random, k1, 6, -3, 2);
         this.placeRandomStairs(world, random, k1, 6, -2, 7);
         this.setBlockAndMetadata(world, k1, 7, -2, LOTRMod.guldurilBrick, 4);
         this.placeRandomStairs(world, random, k1, 8, -2, 2);
         this.placeRandomStairs(world, random, k1, 8, -1, 7);
         this.placeRandomStairs(world, random, k1, 6, 3, 3);
         this.placeRandomStairs(world, random, k1, 6, 2, 6);
         this.setBlockAndMetadata(world, k1, 7, 2, LOTRMod.guldurilBrick, 4);
         this.placeRandomStairs(world, random, k1, 8, 2, 3);
         this.placeRandomStairs(world, random, k1, 8, 1, 6);
      }

      for(k1 = -4; k1 <= 4; k1 += 8) {
         this.placeRandomStairs(world, random, -3, 6, k1, 1);
         this.placeRandomStairs(world, random, -2, 6, k1, 4);
         this.setBlockAndMetadata(world, -2, 7, k1, LOTRMod.guldurilBrick, 4);
         this.placeRandomStairs(world, random, -2, 8, k1, 1);
         this.placeRandomStairs(world, random, -1, 8, k1, 4);
         this.placeRandomStairs(world, random, 3, 6, k1, 0);
         this.placeRandomStairs(world, random, 2, 6, k1, 5);
         this.setBlockAndMetadata(world, 2, 7, k1, LOTRMod.guldurilBrick, 4);
         this.placeRandomStairs(world, random, 2, 8, k1, 0);
         this.placeRandomStairs(world, random, 1, 8, k1, 5);
      }

      return true;
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(4) == 0) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 9);
      } else {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 8);
      }

   }

   private void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
      if (random.nextInt(4) == 0) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsDolGuldurBrickCracked, meta);
      } else {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsDolGuldurBrick, meta);
      }

   }
}
