package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHighElvenTurret extends LOTRWorldGenStructureBase {
   public LOTRWorldGenHighElvenTurret(boolean flag) {
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
               this.func_150516_a(world, k1, j1, j1, LOTRMod.brick3, 2);
               this.setGrassToDirt(world, k1, j1 - 1, j1);
            }

            for(j1 = j + 1; j1 <= j + 7; ++j1) {
               if (Math.abs(k1 - i) != 4 && Math.abs(j1 - k) != 4) {
                  this.func_150516_a(world, k1, j1, j1, Blocks.field_150350_a, 0);
               } else {
                  this.func_150516_a(world, k1, j1, j1, LOTRMod.brick3, 2);
               }
            }
         }
      }

      for(k1 = i - 3; k1 <= i + 3; ++k1) {
         for(j1 = k - 3; j1 <= k + 3; ++j1) {
            if (Math.abs(k1 - i) % 2 == Math.abs(j1 - k) % 2) {
               this.func_150516_a(world, k1, j, j1, LOTRMod.pillar, 10);
            } else {
               this.func_150516_a(world, k1, j, j1, Blocks.field_150334_T, 0);
            }
         }
      }

      for(k1 = j + 1; k1 <= j + 7; ++k1) {
         this.func_150516_a(world, i - 3, k1, k - 3, LOTRMod.pillar, 10);
         this.func_150516_a(world, i - 3, k1, k + 3, LOTRMod.pillar, 10);
         this.func_150516_a(world, i + 3, k1, k - 3, LOTRMod.pillar, 10);
         this.func_150516_a(world, i + 3, k1, k + 3, LOTRMod.pillar, 10);
      }

      for(k1 = i - 4; k1 <= i + 4; ++k1) {
         this.func_150516_a(world, k1, j + 7, k - 4, LOTRMod.stairsHighElvenBrick, 2);
         this.func_150516_a(world, k1, j + 7, k + 4, LOTRMod.stairsHighElvenBrick, 3);
      }

      for(k1 = k - 3; k1 <= k + 3; ++k1) {
         this.func_150516_a(world, i - 4, j + 7, k1, LOTRMod.stairsHighElvenBrick, 0);
         this.func_150516_a(world, i + 4, j + 7, k1, LOTRMod.stairsHighElvenBrick, 1);
      }

      for(k1 = i - 3; k1 <= i + 3; ++k1) {
         for(j1 = k - 3; j1 <= k + 3; ++j1) {
            for(j1 = j + 7; j1 <= j + 15; ++j1) {
               if (Math.abs(k1 - i) != 3 && Math.abs(j1 - k) != 3) {
                  this.func_150516_a(world, k1, j1, j1, Blocks.field_150350_a, 0);
               } else if (j1 - j < 10 || j1 - j > 14 || Math.abs(k1 - i) < 3 || Math.abs(j1 - k) < 3) {
                  this.func_150516_a(world, k1, j1, j1, LOTRMod.brick3, 2);
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
                  this.func_150516_a(world, k1, j1, j1, LOTRMod.brick3, 2);
               }
            }
         }
      }

      for(k1 = i - 4; k1 <= i + 4; ++k1) {
         this.func_150516_a(world, k1, j + 16, k - 4, LOTRMod.stairsHighElvenBrick, 6);
         this.func_150516_a(world, k1, j + 16, k + 4, LOTRMod.stairsHighElvenBrick, 7);
      }

      for(k1 = k - 3; k1 <= k + 3; ++k1) {
         this.func_150516_a(world, i - 4, j + 16, k1, LOTRMod.stairsHighElvenBrick, 4);
         this.func_150516_a(world, i + 4, j + 16, k1, LOTRMod.stairsHighElvenBrick, 5);
      }

      if (rotation == 0) {
         for(k1 = i - 1; k1 <= i + 1; ++k1) {
            this.func_150516_a(world, k1, j, k - 5, Blocks.field_150334_T, 0);
            this.func_150516_a(world, k1, j, k - 4, Blocks.field_150334_T, 0);
         }

         for(k1 = j + 1; k1 <= j + 2; ++k1) {
            this.func_150516_a(world, i - 1, k1, k - 5, LOTRMod.brick3, 2);
            this.func_150516_a(world, i, k1, k - 5, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i, k1, k - 4, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i + 1, k1, k - 5, LOTRMod.brick3, 2);
         }

         this.func_150516_a(world, i - 1, j + 3, k - 5, LOTRMod.stairsHighElvenBrick, 0);
         this.func_150516_a(world, i, j + 3, k - 5, LOTRMod.brick3, 2);
         this.func_150516_a(world, i + 1, j + 3, k - 5, LOTRMod.stairsHighElvenBrick, 1);

         for(k1 = i + 1; k1 <= i + 2; ++k1) {
            for(j1 = j + 1; j1 <= j + 7; ++j1) {
               this.func_150516_a(world, k1, j1, k + 3, LOTRMod.brick3, 2);
            }

            for(j1 = j + 1; j1 <= j + 16; ++j1) {
               this.func_150516_a(world, k1, j1, k + 2, Blocks.field_150468_ap, 2);
            }
         }
      } else if (rotation == 1) {
         for(k1 = k - 1; k1 <= k + 1; ++k1) {
            this.func_150516_a(world, i + 5, j, k1, Blocks.field_150334_T, 0);
            this.func_150516_a(world, i + 4, j, k1, Blocks.field_150334_T, 0);
         }

         for(k1 = j + 1; k1 <= j + 2; ++k1) {
            this.func_150516_a(world, i + 5, k1, k - 1, LOTRMod.brick3, 2);
            this.func_150516_a(world, i + 5, k1, k, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i + 4, k1, k, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i + 5, k1, k + 1, LOTRMod.brick3, 2);
         }

         this.func_150516_a(world, i + 5, j + 3, k - 1, LOTRMod.stairsHighElvenBrick, 2);
         this.func_150516_a(world, i + 5, j + 3, k, LOTRMod.brick3, 2);
         this.func_150516_a(world, i + 5, j + 3, k + 1, LOTRMod.stairsHighElvenBrick, 3);

         for(k1 = k - 1; k1 >= k - 2; --k1) {
            for(j1 = j + 1; j1 <= j + 7; ++j1) {
               this.func_150516_a(world, i - 3, j1, k1, LOTRMod.brick3, 2);
            }

            for(j1 = j + 1; j1 <= j + 16; ++j1) {
               this.func_150516_a(world, i - 2, j1, k1, Blocks.field_150468_ap, 5);
            }
         }
      } else if (rotation == 2) {
         for(k1 = i - 1; k1 <= i + 1; ++k1) {
            this.func_150516_a(world, k1, j, k + 5, Blocks.field_150334_T, 0);
            this.func_150516_a(world, k1, j, k + 4, Blocks.field_150334_T, 0);
         }

         for(k1 = j + 1; k1 <= j + 2; ++k1) {
            this.func_150516_a(world, i - 1, k1, k + 5, LOTRMod.brick3, 2);
            this.func_150516_a(world, i, k1, k + 5, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i, k1, k + 4, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i + 1, k1, k + 5, LOTRMod.brick3, 2);
         }

         this.func_150516_a(world, i - 1, j + 3, k + 5, LOTRMod.stairsHighElvenBrick, 0);
         this.func_150516_a(world, i, j + 3, k + 5, LOTRMod.brick3, 2);
         this.func_150516_a(world, i + 1, j + 3, k + 5, LOTRMod.stairsHighElvenBrick, 1);

         for(k1 = i - 1; k1 >= i - 2; --k1) {
            for(j1 = j + 1; j1 <= j + 7; ++j1) {
               this.func_150516_a(world, k1, j1, k - 3, LOTRMod.brick3, 2);
            }

            for(j1 = j + 1; j1 <= j + 16; ++j1) {
               this.func_150516_a(world, k1, j1, k - 2, Blocks.field_150468_ap, 3);
            }
         }
      } else if (rotation == 3) {
         for(k1 = k - 1; k1 <= k + 1; ++k1) {
            this.func_150516_a(world, i - 5, j, k1, Blocks.field_150334_T, 0);
            this.func_150516_a(world, i - 4, j, k1, Blocks.field_150334_T, 0);
         }

         for(k1 = j + 1; k1 <= j + 2; ++k1) {
            this.func_150516_a(world, i - 5, k1, k - 1, LOTRMod.brick3, 2);
            this.func_150516_a(world, i - 5, k1, k, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i - 4, k1, k, Blocks.field_150350_a, 0);
            this.func_150516_a(world, i - 5, k1, k + 1, LOTRMod.brick3, 2);
         }

         this.func_150516_a(world, i - 5, j + 3, k - 1, LOTRMod.stairsHighElvenBrick, 2);
         this.func_150516_a(world, i - 5, j + 3, k, LOTRMod.brick3, 2);
         this.func_150516_a(world, i - 5, j + 3, k + 1, LOTRMod.stairsHighElvenBrick, 3);

         for(k1 = k + 1; k1 <= k + 2; ++k1) {
            for(j1 = j + 1; j1 <= j + 7; ++j1) {
               this.func_150516_a(world, i + 3, j1, k1, LOTRMod.brick3, 2);
            }

            for(j1 = j + 1; j1 <= j + 16; ++j1) {
               this.func_150516_a(world, i + 2, j1, k1, Blocks.field_150468_ap, 4);
            }
         }
      }

      this.func_150516_a(world, i - 3, j + 3, k, LOTRMod.highElvenTorch, 1);
      this.func_150516_a(world, i + 3, j + 3, k, LOTRMod.highElvenTorch, 2);
      this.func_150516_a(world, i, j + 3, k - 3, LOTRMod.highElvenTorch, 3);
      this.func_150516_a(world, i, j + 3, k + 3, LOTRMod.highElvenTorch, 4);
      this.func_150516_a(world, i - 3, j + 18, k, LOTRMod.highElvenTorch, 1);
      this.func_150516_a(world, i + 3, j + 18, k, LOTRMod.highElvenTorch, 2);
      this.func_150516_a(world, i, j + 18, k - 3, LOTRMod.highElvenTorch, 3);
      this.func_150516_a(world, i, j + 18, k + 3, LOTRMod.highElvenTorch, 4);
      return true;
   }
}
