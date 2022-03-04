package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityRohanBarrowWraith;
import lotr.common.world.biome.LOTRBiomeGenRohan;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanBarrow extends LOTRWorldGenStructureBase {
   public LOTRWorldGenRohanBarrow(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (!this.restrictions || world.func_147439_a(i, j - 1, k) == Blocks.field_150349_c && world.func_72807_a(i, k) instanceof LOTRBiomeGenRohan) {
         --j;
         int radius = 7;
         int height = 4;
         int j1;
         if (!this.restrictions && this.usingPlayer != null) {
            j1 = this.usingPlayerRotation();
            switch(j1) {
            case 0:
               k += radius;
               break;
            case 1:
               i -= radius;
               break;
            case 2:
               k -= radius;
               break;
            case 3:
               i += radius;
            }
         }

         int i1;
         int k1;
         int i2;
         int j1;
         if (this.restrictions) {
            j1 = j;
            i1 = j;

            for(k1 = i - radius; k1 <= i + radius; ++k1) {
               for(i2 = k - radius; i2 <= k + radius; ++i2) {
                  j1 = world.func_72825_h(k1, i2) - 1;
                  if (world.func_147439_a(k1, j1, i2) != Blocks.field_150349_c) {
                     return false;
                  }

                  if (j1 < j1) {
                     j1 = j1;
                  }

                  if (j1 > i1) {
                     i1 = j1;
                  }
               }
            }

            if (i1 - j1 > 3) {
               return false;
            }
         }

         for(j1 = i - radius; j1 <= i + radius; ++j1) {
            for(i1 = j + height; i1 >= j; --i1) {
               for(k1 = k - radius; k1 <= k + radius; ++k1) {
                  i2 = j1 - i;
                  j1 = i1 - j;
                  int k2 = k1 - k;
                  if (i2 * i2 + j1 * j1 + k2 * k2 <= radius * radius) {
                     boolean grass = !LOTRMod.isOpaque(world, j1, i1 + 1, k1);
                     this.func_150516_a(world, j1, i1, k1, (Block)(grass ? Blocks.field_150349_c : Blocks.field_150346_d), 0);
                     this.setGrassToDirt(world, j1, i1 - 1, k1);
                  }
               }
            }
         }

         for(j1 = i - radius; j1 <= i + radius; ++j1) {
            for(i1 = k - radius; i1 <= k + radius; ++i1) {
               for(k1 = j - 1; !LOTRMod.isOpaque(world, j1, k1, i1) && k1 >= 0; --k1) {
                  i2 = j1 - i;
                  j1 = i1 - k;
                  if (i2 * i2 + j1 * j1 <= radius * radius) {
                     this.func_150516_a(world, j1, k1, i1, Blocks.field_150346_d, 0);
                     this.setGrassToDirt(world, j1, k1 - 1, i1);
                  }
               }
            }
         }

         for(j1 = 0; j1 < 12; ++j1) {
            i1 = i - random.nextInt(radius) + random.nextInt(radius);
            k1 = k - random.nextInt(radius) + random.nextInt(radius);
            i2 = world.func_72976_f(i1, k1);
            if (world.func_147439_a(i1, i2 - 1, k1) == Blocks.field_150349_c) {
               this.func_150516_a(world, i1, i2, k1, LOTRMod.simbelmyne, 0);
            }
         }

         j += height;

         for(j1 = i - 1; j1 < i + 1; ++j1) {
            for(i1 = k - 1; i1 <= k + 1; ++i1) {
               this.func_150516_a(world, j1, j - 1, i1, Blocks.field_150350_a, 0);
            }
         }

         for(j1 = i - 2; j1 <= i + 2; ++j1) {
            for(i1 = k - 2; i1 <= k + 2; ++i1) {
               for(k1 = j - 2; k1 >= j - 4; --k1) {
                  this.func_150516_a(world, j1, k1, i1, Blocks.field_150350_a, 0);
               }

               this.func_150516_a(world, j1, j - 5, i1, LOTRMod.slabDouble2, 1);
            }
         }

         for(j1 = j - 3; j1 >= j - 4; --j1) {
            for(i1 = i - 4; i1 <= i + 4; ++i1) {
               for(k1 = k - 1; k1 <= k + 1; ++k1) {
                  this.func_150516_a(world, i1, j1, k1, Blocks.field_150350_a, 0);
                  if (Math.abs(i1 - i) > 2) {
                     this.func_150516_a(world, i1, j - 5, k1, LOTRMod.brick, 4);
                  }
               }
            }

            for(i1 = i - 1; i1 <= i + 1; ++i1) {
               for(k1 = k - 4; k1 <= k + 4; ++k1) {
                  this.func_150516_a(world, i1, j1, k1, Blocks.field_150350_a, 0);
                  if (Math.abs(k1 - k) > 2) {
                     this.func_150516_a(world, i1, j - 5, k1, LOTRMod.brick, 4);
                  }
               }
            }

            this.func_150516_a(world, i - 4, j1, k - 1, LOTRMod.rock, 2);
            this.func_150516_a(world, i - 5, j1, k, LOTRMod.rock, 2);
            this.func_150516_a(world, i - 4, j1, k + 1, LOTRMod.rock, 2);
            this.func_150516_a(world, i + 4, j1, k - 1, LOTRMod.rock, 2);
            this.func_150516_a(world, i + 5, j1, k, LOTRMod.rock, 2);
            this.func_150516_a(world, i + 4, j1, k + 1, LOTRMod.rock, 2);
            this.func_150516_a(world, i - 1, j1, k - 4, LOTRMod.rock, 2);
            this.func_150516_a(world, i, j1, k - 5, LOTRMod.rock, 2);
            this.func_150516_a(world, i + 1, j1, k - 4, LOTRMod.rock, 2);
            this.func_150516_a(world, i - 1, j1, k + 4, LOTRMod.rock, 2);
            this.func_150516_a(world, i, j1, k + 5, LOTRMod.rock, 2);
            this.func_150516_a(world, i + 1, j1, k + 4, LOTRMod.rock, 2);
         }

         this.func_150516_a(world, i - 4, j - 3, k, Blocks.field_150478_aa, 1);
         this.func_150516_a(world, i - 4, j - 4, k, LOTRMod.slabSingle2, 9);
         this.func_150516_a(world, i + 4, j - 3, k, Blocks.field_150478_aa, 2);
         this.func_150516_a(world, i + 4, j - 4, k, LOTRMod.slabSingle2, 9);
         this.func_150516_a(world, i, j - 3, k - 4, Blocks.field_150478_aa, 3);
         this.func_150516_a(world, i, j - 4, k - 4, LOTRMod.slabSingle2, 9);
         this.func_150516_a(world, i, j - 3, k + 4, Blocks.field_150478_aa, 4);
         this.func_150516_a(world, i, j - 4, k + 4, LOTRMod.slabSingle2, 9);

         for(j1 = i - 1; j1 <= i + 1; ++j1) {
            this.func_150516_a(world, j1, j - 4, k - 1, LOTRMod.stairsRohanBrick, 2);
            this.func_150516_a(world, j1, j - 4, k + 1, LOTRMod.stairsRohanBrick, 3);
         }

         this.func_150516_a(world, i - 1, j - 4, k, LOTRMod.stairsRohanBrick, 0);
         this.func_150516_a(world, i + 1, j - 4, k, LOTRMod.stairsRohanBrick, 1);
         this.placeSpawnerChest(world, i, j - 5, k, LOTRMod.spawnerChestStone, 4, LOTREntityRohanBarrowWraith.class);
         LOTRChestContents.fillChest(world, random, i, j - 5, k, LOTRChestContents.ROHAN_BARROWS);
         this.func_150516_a(world, i, j - 3, k, LOTRMod.slabSingle2, 1);
         return true;
      } else {
         return false;
      }
   }
}
