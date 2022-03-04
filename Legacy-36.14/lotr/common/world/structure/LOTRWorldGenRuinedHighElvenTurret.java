package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedHighElvenTurret extends LOTRWorldGenStructureBase {
   public LOTRWorldGenRuinedHighElvenTurret(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions) {
         Block block = world.func_147439_a(i, j - 1, k);
         if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150348_b) {
            return false;
         }
      }

      --j;
      int rotation = random.nextInt(4);
      if (!this.restrictions && this.usingPlayer != null) {
         rotation = this.usingPlayerRotation();
      }

      switch(rotation) {
      case 0:
         k += 6;
         break;
      case 1:
         i -= 6;
         break;
      case 2:
         k -= 6;
         break;
      case 3:
         i += 6;
      }

      int k1;
      int j1;
      int j1;
      for(k1 = i - 4; k1 <= i + 4; ++k1) {
         for(j1 = k - 4; j1 <= k + 4; ++j1) {
            for(j1 = j; (j1 == j || !LOTRMod.isOpaque(world, k1, j1, j1)) && j1 >= 0; --j1) {
               this.placeRandomBrick(world, random, k1, j1, j1);
               this.setGrassToDirt(world, k1, j1 - 1, j1);
            }

            for(j1 = j + 1; j1 <= j + 7; ++j1) {
               if (Math.abs(k1 - i) != 4 && Math.abs(j1 - k) != 4) {
                  this.func_150516_a(world, k1, j1, j1, Blocks.field_150350_a, 0);
               } else {
                  this.placeRandomBrick(world, random, k1, j1, j1);
               }
            }
         }
      }

      for(k1 = i - 3; k1 <= i + 3; ++k1) {
         for(j1 = k - 3; j1 <= k + 3; ++j1) {
            if (Math.abs(k1 - i) % 2 == Math.abs(j1 - k) % 2) {
               this.placeRandomPillar(world, random, k1, j, j1);
            } else {
               this.func_150516_a(world, k1, j, j1, Blocks.field_150334_T, 0);
            }
         }
      }

      for(k1 = j + 1; k1 <= j + 7; ++k1) {
         this.placeRandomPillar(world, random, i - 3, k1, k - 3);
         this.placeRandomPillar(world, random, i - 3, k1, k + 3);
         this.placeRandomPillar(world, random, i + 3, k1, k - 3);
         this.placeRandomPillar(world, random, i + 3, k1, k + 3);
      }

      for(k1 = i - 4; k1 <= i + 4; ++k1) {
         this.placeRandomStairs(world, random, k1, j + 7, k - 4, 2);
         this.placeRandomStairs(world, random, k1, j + 7, k + 4, 3);
      }

      for(k1 = k - 3; k1 <= k + 3; ++k1) {
         this.placeRandomStairs(world, random, i - 4, j + 7, k1, 0);
         this.placeRandomStairs(world, random, i + 4, j + 7, k1, 1);
      }

      for(k1 = i - 3; k1 <= i + 3; ++k1) {
         for(j1 = k - 3; j1 <= k + 3; ++j1) {
            for(j1 = j + 7; j1 <= j + 15; ++j1) {
               if (Math.abs(k1 - i) != 3 && Math.abs(j1 - k) != 3) {
                  this.func_150516_a(world, k1, j1, j1, Blocks.field_150350_a, 0);
               } else if (j1 - j < 10 || j1 - j > 14 || Math.abs(k1 - i) < 3 || Math.abs(j1 - k) < 3) {
                  this.placeRandomBrick(world, random, k1, j1, j1);
               }
            }
         }
      }

      for(k1 = i - 4; k1 <= i + 4; ++k1) {
         for(j1 = k - 4; j1 <= k + 4; ++j1) {
            for(j1 = j + 16; j1 <= j + 18; ++j1) {
               if (j1 - j != 16 && Math.abs(k1 - i) != 4 && Math.abs(j1 - k) != 4) {
                  this.func_150516_a(world, k1, j1, j1, Blocks.field_150350_a, 0);
               } else if (j1 - j == 18 && (Math.abs(k1 - i) % 2 == 1 || Math.abs(j1 - k) % 2 == 1)) {
                  this.func_150516_a(world, k1, j1, j1, Blocks.field_150350_a, 0);
               } else {
                  this.placeRandomBrick(world, random, k1, j1, j1);
               }
            }
         }
      }

      for(k1 = i - 4; k1 <= i + 4; ++k1) {
         this.placeRandomStairs(world, random, k1, j + 16, k - 4, 6);
         this.placeRandomStairs(world, random, k1, j + 16, k + 4, 7);
      }

      for(k1 = k - 3; k1 <= k + 3; ++k1) {
         this.placeRandomStairs(world, random, i - 4, j + 16, k1, 4);
         this.placeRandomStairs(world, random, i + 4, j + 16, k1, 5);
      }

      if (rotation == 0) {
         for(k1 = i - 1; k1 <= i + 1; ++k1) {
            this.func_150516_a(world, k1, j, k - 5, Blocks.field_150334_T, 0);
            this.func_150516_a(world, k1, j, k - 4, Blocks.field_150334_T, 0);
         }

         for(k1 = j + 1; k1 <= j + 2; ++k1) {
            this.placeRandomBrick(world, random, i - 1, k1, k - 5);
            this.func_150516_a(world, i, k1, k - 5, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i, k1, k - 4, Blocks.field_150350_a, 0);
            this.placeRandomBrick(world, random, i + 1, k1, k - 5);
         }

         this.placeRandomStairs(world, random, i - 1, j + 3, k - 5, 0);
         this.placeRandomBrick(world, random, i, j + 3, k - 5);
         this.placeRandomStairs(world, random, i + 1, j + 3, k - 5, 1);

         for(k1 = i + 1; k1 <= i + 2; ++k1) {
            for(j1 = j + 1; j1 <= j + 7; ++j1) {
               this.placeRandomBrick(world, random, k1, j1, k + 3);
            }
         }
      } else if (rotation == 1) {
         for(k1 = k - 1; k1 <= k + 1; ++k1) {
            this.func_150516_a(world, i + 5, j, k1, Blocks.field_150334_T, 0);
            this.func_150516_a(world, i + 4, j, k1, Blocks.field_150334_T, 0);
         }

         for(k1 = j + 1; k1 <= j + 2; ++k1) {
            this.placeRandomBrick(world, random, i + 5, k1, k - 1);
            this.func_150516_a(world, i + 5, k1, k, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i + 4, k1, k, Blocks.field_150350_a, 0);
            this.placeRandomBrick(world, random, i + 5, k1, k + 1);
         }

         this.placeRandomStairs(world, random, i + 5, j + 3, k - 1, 2);
         this.placeRandomBrick(world, random, i + 5, j + 3, k);
         this.placeRandomStairs(world, random, i + 5, j + 3, k + 1, 3);

         for(k1 = k - 1; k1 >= k - 2; --k1) {
            for(j1 = j + 1; j1 <= j + 7; ++j1) {
               this.placeRandomBrick(world, random, i - 3, j1, k1);
            }
         }
      } else if (rotation == 2) {
         for(k1 = i - 1; k1 <= i + 1; ++k1) {
            this.func_150516_a(world, k1, j, k + 5, Blocks.field_150334_T, 0);
            this.func_150516_a(world, k1, j, k + 4, Blocks.field_150334_T, 0);
         }

         for(k1 = j + 1; k1 <= j + 2; ++k1) {
            this.placeRandomBrick(world, random, i - 1, k1, k + 5);
            this.func_150516_a(world, i, k1, k + 5, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i, k1, k + 4, Blocks.field_150350_a, 0);
            this.placeRandomBrick(world, random, i + 1, k1, k + 5);
         }

         this.placeRandomStairs(world, random, i - 1, j + 3, k + 5, 0);
         this.placeRandomBrick(world, random, i, j + 3, k + 5);
         this.placeRandomStairs(world, random, i + 1, j + 3, k + 5, 1);

         for(k1 = i - 1; k1 >= i - 2; --k1) {
            for(j1 = j + 1; j1 <= j + 7; ++j1) {
               this.placeRandomBrick(world, random, k1, j1, k - 3);
            }
         }
      } else if (rotation == 3) {
         for(k1 = k - 1; k1 <= k + 1; ++k1) {
            this.func_150516_a(world, i - 5, j, k1, Blocks.field_150334_T, 0);
            this.func_150516_a(world, i - 4, j, k1, Blocks.field_150334_T, 0);
         }

         for(k1 = j + 1; k1 <= j + 2; ++k1) {
            this.placeRandomBrick(world, random, i - 5, k1, k - 1);
            this.func_150516_a(world, i - 5, k1, k, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i - 4, k1, k, Blocks.field_150350_a, 0);
            this.placeRandomBrick(world, random, i - 5, k1, k + 1);
         }

         this.placeRandomStairs(world, random, i - 5, j + 3, k - 1, 2);
         this.placeRandomBrick(world, random, i - 5, j + 3, k);
         this.placeRandomStairs(world, random, i - 5, j + 3, k + 1, 3);

         for(k1 = k + 1; k1 <= k + 2; ++k1) {
            for(j1 = j + 1; j1 <= j + 7; ++j1) {
               this.placeRandomBrick(world, random, i + 3, j1, k1);
            }
         }
      }

      return true;
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(20) != 0) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.func_150516_a(world, i, j, k, LOTRMod.brick3, 2);
            break;
         case 1:
            this.func_150516_a(world, i, j, k, LOTRMod.brick3, 3);
            break;
         case 2:
            this.func_150516_a(world, i, j, k, LOTRMod.brick3, 4);
         }

      }
   }

   private void placeRandomPillar(World world, Random random, int i, int j, int k) {
      if (random.nextInt(8) != 0) {
         if (random.nextInt(3) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.pillar, 11);
         } else {
            this.func_150516_a(world, i, j, k, LOTRMod.pillar, 10);
         }

      }
   }

   private void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
      if (random.nextInt(8) != 0) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.func_150516_a(world, i, j, k, LOTRMod.stairsHighElvenBrick, meta);
            break;
         case 1:
            this.func_150516_a(world, i, j, k, LOTRMod.stairsHighElvenBrickMossy, meta);
            break;
         case 2:
            this.func_150516_a(world, i, j, k, LOTRMod.stairsHighElvenBrickCracked, meta);
         }

      }
   }
}
