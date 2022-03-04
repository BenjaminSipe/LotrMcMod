package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHaradObelisk extends LOTRWorldGenStructureBase {
   public LOTRWorldGenHaradObelisk(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions && world.func_147439_a(i, j - 1, k) != Blocks.field_150354_m && world.func_147439_a(i, j - 1, k) != Blocks.field_150346_d && world.func_147439_a(i, j - 1, k) != Blocks.field_150349_c) {
         return false;
      } else {
         --j;
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

         int i1;
         int k1;
         int i2;
         if (this.restrictions) {
            for(i1 = i - 7; i1 <= i + 7; ++i1) {
               for(k1 = k - 7; k1 <= k + 7; ++k1) {
                  i2 = world.func_72825_h(i1, k1);
                  Block block = world.func_147439_a(i1, i2 - 1, k1);
                  if (block != Blocks.field_150354_m && block != Blocks.field_150346_d && block != Blocks.field_150348_b && block != Blocks.field_150349_c) {
                     return false;
                  }
               }
            }
         }

         for(i1 = i - 7; i1 <= i + 7; ++i1) {
            for(k1 = k - 7; k1 <= k + 7; ++k1) {
               for(i2 = j; (i2 == j || !LOTRMod.isOpaque(world, i1, i2, k1)) && i2 >= 0; --i2) {
                  this.func_150516_a(world, i1, i2, k1, Blocks.field_150322_A, 0);
                  this.setGrassToDirt(world, i1, i2 - 1, k1);
               }
            }
         }

         for(i1 = i - 7; i1 <= i + 7; ++i1) {
            for(k1 = k - 7; k1 <= k + 7; ++k1) {
               i2 = Math.abs(i1 - i);
               int k2 = Math.abs(k1 - k);
               if (i2 == 7 || k2 == 7) {
                  this.func_150516_a(world, i1, j + 1, k1, Blocks.field_150322_A, 0);
               }

               if (i2 == 5 && k2 <= 5 || k2 == 5 && i2 <= 5) {
                  this.func_150516_a(world, i1, j + 1, k1, Blocks.field_150322_A, 0);
                  this.func_150516_a(world, i1, j + 2, k1, Blocks.field_150322_A, 2);
               }

               if (i2 == 3 && k2 <= 3 || k2 == 3 && i2 <= 3) {
                  this.func_150516_a(world, i1, j + 1, k1, Blocks.field_150322_A, 0);
                  this.func_150516_a(world, i1, j + 2, k1, Blocks.field_150322_A, 2);
                  this.placeHaradBrick(world, random, i1, j + 3, k1);
               }

               if (i2 <= 1 && k2 <= 1) {
                  this.func_150516_a(world, i1, j + 1, k1, Blocks.field_150322_A, 0);
                  this.func_150516_a(world, i1, j + 2, k1, Blocks.field_150322_A, 2);
                  this.placeHaradBrick(world, random, i1, j + 3, k1);
                  this.func_150516_a(world, i1, j + 4, k1, Blocks.field_150322_A, 2);
                  this.placeHaradBrick(world, random, i1, j + 5, k1);
               }

               for(int l = 0; l <= 2; ++l) {
                  int l1 = 8 - (l * 2 + 1);
                  if (i2 == l1 && k2 == l1) {
                     this.placeHaradBrick(world, random, i1, j + l + 2, k1);
                     this.placeHaradWall(world, random, i1, j + l + 3, k1);
                     this.placeHaradWall(world, random, i1, j + l + 4, k1);
                  }
               }
            }
         }

         this.placeHaradBrick(world, random, i - 1, j + 6, k);
         this.placeHaradBrick(world, random, i + 1, j + 6, k);
         this.placeHaradBrick(world, random, i, j + 6, k - 1);
         this.placeHaradBrick(world, random, i, j + 6, k + 1);

         for(i1 = j + 6; i1 <= j + 9; ++i1) {
            this.func_150516_a(world, i, i1, k, Blocks.field_150322_A, 2);
         }

         this.func_150516_a(world, i - 1, j + 10, k, Blocks.field_150322_A, 2);
         this.func_150516_a(world, i + 1, j + 10, k, Blocks.field_150322_A, 2);
         this.func_150516_a(world, i, j + 10, k - 1, Blocks.field_150322_A, 2);
         this.func_150516_a(world, i, j + 10, k + 1, Blocks.field_150322_A, 2);
         this.func_150516_a(world, i, j + 10, k, LOTRMod.hearth, 0);
         this.func_150516_a(world, i, j + 11, k, Blocks.field_150480_ab, 0);
         return true;
      }
   }

   private void placeHaradBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(3) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.brick3, 11);
      } else {
         this.func_150516_a(world, i, j, k, LOTRMod.brick, 15);
      }

   }

   private void placeHaradWall(World world, Random random, int i, int j, int k) {
      if (random.nextInt(3) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.wall3, 3);
      } else {
         this.func_150516_a(world, i, j, k, LOTRMod.wall, 15);
      }

   }
}
