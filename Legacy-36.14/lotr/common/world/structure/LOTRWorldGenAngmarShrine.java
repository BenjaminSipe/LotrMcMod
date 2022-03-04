package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenAngmarShrine extends LOTRWorldGenStructureBase {
   public LOTRWorldGenAngmarShrine(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions && world.func_147439_a(i, j - 1, k) != Blocks.field_150349_c) {
         return false;
      } else {
         --j;
         int rotation = random.nextInt(4);
         if (!this.restrictions && this.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
         }

         switch(rotation) {
         case 0:
            k += 4;
            break;
         case 1:
            i -= 4;
            break;
         case 2:
            k -= 4;
            break;
         case 3:
            i += 4;
         }

         int l;
         int maxHeight;
         int i1;
         int k1;
         int height;
         if (this.restrictions) {
            l = j;
            maxHeight = j;

            for(i1 = i - 3; i1 <= i + 3; ++i1) {
               for(k1 = k - 3; k1 <= k + 3; ++k1) {
                  height = world.func_72825_h(i1, k1) - 1;
                  Block l = world.func_147439_a(i1, height, k1);
                  if (l != Blocks.field_150349_c && l != Blocks.field_150346_d && l != Blocks.field_150348_b) {
                     return false;
                  }

                  if (height < l) {
                     l = height;
                  }

                  if (height > maxHeight) {
                     maxHeight = height;
                  }
               }
            }

            if (maxHeight - l > 3) {
               return false;
            }
         }

         for(l = i - 3; l <= i + 3; ++l) {
            for(maxHeight = k - 3; maxHeight <= k + 3; ++maxHeight) {
               for(i1 = j; !LOTRMod.isOpaque(world, l, i1, maxHeight) && i1 >= 0; --i1) {
                  this.placeRandomBrick(world, random, l, i1, maxHeight);
                  this.setGrassToDirt(world, l, i1 - 1, maxHeight);
               }
            }
         }

         for(l = 0; l <= 2; ++l) {
            for(maxHeight = i - 3 + l; maxHeight <= i + 3 - l; ++maxHeight) {
               for(i1 = k - 3 + l; i1 <= k + 3 - l; ++i1) {
                  this.placeRandomBrick(world, random, maxHeight, j + 1 + l, i1);
               }
            }

            this.placeRandomStairs(world, random, i - 3 + l, j + 1 + l, k, 0);
            this.placeRandomStairs(world, random, i + 3 - l, j + 1 + l, k, 1);
            this.placeRandomStairs(world, random, i, j + 1 + l, k - 3 + l, 2);
            this.placeRandomStairs(world, random, i, j + 1 + l, k + 3 - l, 3);
         }

         this.func_150516_a(world, i, j + 4, k, LOTRMod.angmarTable, 0);
         this.func_150516_a(world, i - 2, j + 3, k - 2, LOTRMod.morgulTorch, 5);
         this.func_150516_a(world, i - 2, j + 3, k + 2, LOTRMod.morgulTorch, 5);
         this.func_150516_a(world, i + 2, j + 3, k - 2, LOTRMod.morgulTorch, 5);
         this.func_150516_a(world, i + 2, j + 3, k + 2, LOTRMod.morgulTorch, 5);
         l = 4 + random.nextInt(5);

         for(maxHeight = 0; maxHeight < l; ++maxHeight) {
            i1 = 4 + random.nextInt(4);
            k1 = 4 + random.nextInt(4);
            if (random.nextBoolean()) {
               i1 *= -1;
            }

            if (random.nextBoolean()) {
               k1 *= -1;
            }

            i1 += i;
            k1 += k;
            height = 2 + random.nextInt(3);
            int j1 = world.func_72825_h(i1, k1) - 1;
            Block l = world.func_147439_a(i1, j1, k1);
            if (l == Blocks.field_150349_c || l == Blocks.field_150346_d || l == Blocks.field_150348_b) {
               this.setGrassToDirt(world, i1, j1, k1);

               for(int j2 = j1; j2 < j1 + height; ++j2) {
                  this.placeRandomBrick(world, random, i1, j2, k1);
               }

               this.func_150516_a(world, i1, j1 + height, k1, LOTRMod.guldurilBrick, 2);
            }
         }

         return true;
      }
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(4) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.brick2, 1);
      } else {
         this.func_150516_a(world, i, j, k, LOTRMod.brick2, 0);
      }

   }

   private void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
      if (random.nextInt(4) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.stairsAngmarBrickCracked, meta);
      } else {
         this.func_150516_a(world, i, j, k, LOTRMod.stairsAngmarBrick, meta);
      }

   }
}
