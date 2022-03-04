package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorRuin extends LOTRWorldGenStructureBase {
   public LOTRWorldGenGondorRuin(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int rotation = random.nextInt(4);
      if (!this.restrictions && this.usingPlayer != null) {
         rotation = this.usingPlayerRotation();
      }

      switch(rotation) {
      case 0:
         k += 8;
         break;
      case 1:
         i -= 8;
         break;
      case 2:
         k -= 8;
         break;
      case 3:
         i += 8;
      }

      j = world.func_72825_h(i, k);
      if (this.restrictions && world.func_147439_a(i, j - 1, k) != Blocks.field_150349_c) {
         return false;
      } else {
         int i1;
         int k1;
         int j1;
         for(i1 = i - 7; i1 <= i + 7; ++i1) {
            for(k1 = k - 7; k1 <= k + 7; ++k1) {
               j1 = world.func_72825_h(i1, k1);
               Block block = world.func_147439_a(i1, j1 - 1, k1);
               if (block.func_149662_c()) {
                  if (random.nextInt(3) == 0) {
                     this.func_150516_a(world, i1, j1 - 1, k1, LOTRMod.rock, 1);
                  }

                  if (random.nextInt(3) == 0) {
                     if (random.nextInt(3) == 0) {
                        this.placeRandomSlab(world, random, i1, j1, k1);
                        this.setGrassToDirt(world, i1, j1 - 1, k1);
                     } else {
                        this.placeRandomBrick(world, random, i1, j1, k1);
                        this.setGrassToDirt(world, i1, j1 - 1, k1);
                     }
                  }

                  if (LOTRMod.isOpaque(world, i1, j1, k1) && random.nextInt(4) == 0) {
                     if (random.nextInt(5) == 0) {
                        this.func_150516_a(world, i1, j1 + 1, k1, LOTRMod.wall, 3);
                        this.placeSkull(world, random, i1, j1 + 2, k1);
                     } else if (random.nextInt(3) == 0) {
                        this.placeRandomSlab(world, random, i1, j1 + 1, k1);
                     } else {
                        this.placeRandomBrick(world, random, i1, j1 + 1, k1);
                     }
                  }
               }
            }
         }

         for(i1 = i - 7; i1 <= i + 7; i1 += 7) {
            for(k1 = k - 7; k1 <= k + 7; k1 += 7) {
               j1 = world.func_72825_h(i1, k1);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
               int j2 = j1;

               while(true) {
                  this.placeRandomBrick(world, random, i1, j2, k1);
                  if (random.nextInt(4) == 0 || j2 > j1 + 4) {
                     if (i1 == i && k1 == k) {
                        this.func_150516_a(world, i1, j2 + 1, k1, LOTRMod.beacon, 0);
                     } else {
                        this.func_150516_a(world, i1, j2 + 1, k1, LOTRMod.brick, 5);
                     }
                     break;
                  }

                  ++j2;
               }
            }
         }

         return true;
      }
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(20) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.brick, 5);
      } else if (random.nextInt(4) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
      } else {
         this.func_150516_a(world, i, j, k, LOTRMod.brick, 1);
      }

   }

   private void placeRandomSlab(World world, Random random, int i, int j, int k) {
      if (random.nextInt(5) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.slabSingle, 2);
      } else if (random.nextInt(4) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.slabSingle, 4 + random.nextInt(2));
      } else {
         this.func_150516_a(world, i, j, k, LOTRMod.slabSingle, 3);
      }

   }

   private void placeRandomStairs(World world, Random random, int i, int j, int k, int metadata) {
      if (random.nextInt(4) == 0) {
         this.func_150516_a(world, i, j, k, random.nextBoolean() ? LOTRMod.stairsGondorBrickMossy : LOTRMod.stairsGondorBrickCracked, metadata);
      } else {
         this.func_150516_a(world, i, j, k, LOTRMod.stairsGondorBrick, metadata);
      }

   }
}
