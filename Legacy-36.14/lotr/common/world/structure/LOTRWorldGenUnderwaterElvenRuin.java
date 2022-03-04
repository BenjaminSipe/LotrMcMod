package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenUnderwaterElvenRuin extends LOTRWorldGenStructureBase {
   public LOTRWorldGenUnderwaterElvenRuin(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions && world.func_147439_a(i, j, k).func_149688_o() != Material.field_151586_h) {
         return false;
      } else {
         --j;
         int width = 3 + random.nextInt(3);
         int rotation = random.nextInt(4);
         if (!this.restrictions && this.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
         }

         switch(rotation) {
         case 0:
            k += width + 1;
            break;
         case 1:
            i -= width + 1;
            break;
         case 2:
            k -= width + 1;
            break;
         case 3:
            i += width + 1;
         }

         int i1;
         int k1;
         int i2;
         int k2;
         int j1;
         if (this.restrictions) {
            i1 = j + 1;
            k1 = j + 1;

            for(i2 = i - width; i2 <= i + width; ++i2) {
               for(k2 = k - width; k2 <= k + width; ++k2) {
                  j1 = world.func_72825_h(i2, k2);
                  if (world.func_147439_a(i2, j1, k2).func_149688_o() != Material.field_151586_h) {
                     return false;
                  }

                  Block block = world.func_147439_a(i2, j1 - 1, k2);
                  if (block != Blocks.field_150346_d && block != Blocks.field_150354_m && block != Blocks.field_150435_aG) {
                     return false;
                  }

                  if (j1 > k1) {
                     k1 = j1;
                  }

                  if (j1 < i1) {
                     i1 = j1;
                  }
               }
            }

            if (Math.abs(k1 - i1) > 5) {
               return false;
            }
         }

         for(i1 = i - width - 3; i1 <= i + width + 3; ++i1) {
            for(k1 = k - width - 3; k1 <= k + width + 3; ++k1) {
               i2 = Math.abs(i1 - i);
               k2 = Math.abs(k1 - k);
               if (i2 <= width && k2 <= width || random.nextInt(Math.max(1, i2 + k2)) == 0) {
                  j1 = world.func_72825_h(i1, k1);
                  this.placeRandomBrick(world, random, i1, j1, k1);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
                  if (random.nextInt(5) == 0) {
                     this.placeRandomBrick(world, random, i1, j1 + 1, k1);
                     if (random.nextInt(4) == 0) {
                        this.placeRandomBrick(world, random, i1, j1 + 2, k1);
                        if (random.nextInt(3) == 0) {
                           this.placeRandomBrick(world, random, i1, j1 + 3, k1);
                        }
                     }
                  }

                  if (i2 == width && k2 == width || random.nextInt(20) == 0) {
                     int height = 2 + random.nextInt(4);

                     for(int j2 = j1; j2 < j1 + height; ++j2) {
                        this.placeRandomPillar(world, random, i1, j2, k1);
                     }
                  }
               }
            }
         }

         i1 = world.func_72825_h(i, k);
         this.func_150516_a(world, i, i1, k, Blocks.field_150426_aN, 0);
         this.func_150516_a(world, i, i1 + 1, k, LOTRMod.chestStone, 0);
         LOTRChestContents.fillChest(world, random, i, i1 + 1, k, LOTRChestContents.UNDERWATER_ELVEN_RUIN);
         return true;
      }
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
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

   private void placeRandomPillar(World world, Random random, int i, int j, int k) {
      if (random.nextInt(3) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.pillar, 11);
      } else {
         this.func_150516_a(world, i, j, k, LOTRMod.pillar, 10);
      }

   }
}
