package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorRuinsWraith;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorRuins extends LOTRWorldGenStructureBase {
   public LOTRWorldGenGondorRuins() {
      super(false);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (world.func_147439_a(i, j - 1, k) != Blocks.field_150349_c) {
         return false;
      } else {
         int slabRuinParts = 3 + random.nextInt(4);

         int smallRuinParts;
         int largeRuinParts;
         int j1;
         int k1;
         for(smallRuinParts = 0; smallRuinParts < slabRuinParts; ++smallRuinParts) {
            largeRuinParts = i - 5 + random.nextInt(10);
            j1 = k - 5 + random.nextInt(10);
            if (largeRuinParts != i || j1 != k) {
               k1 = world.func_72976_f(largeRuinParts, j1);
               if (world.func_147439_a(largeRuinParts, k1 - 1, j1).func_149662_c()) {
                  this.placeRandomSlab(world, random, largeRuinParts, k1, j1);
               }

               this.setGrassToDirt(world, largeRuinParts, k1 - 1, j1);
            }
         }

         smallRuinParts = 3 + random.nextInt(4);

         int k1;
         int j1;
         int height;
         for(largeRuinParts = 0; largeRuinParts < smallRuinParts; ++largeRuinParts) {
            j1 = i - 5 + random.nextInt(10);
            k1 = k - 5 + random.nextInt(10);
            if (j1 != i || k1 != k) {
               k1 = world.func_72976_f(j1, k1);
               if (world.func_147439_a(j1, k1 - 1, k1).func_149662_c()) {
                  j1 = 1 + random.nextInt(3);

                  for(height = 0; height < j1; ++height) {
                     this.placeRandomBrick(world, random, j1, k1 + height, k1);
                  }
               }

               this.setGrassToDirt(world, j1, k1 - 1, k1);
            }
         }

         largeRuinParts = 3 + random.nextInt(5);

         for(j1 = 0; j1 < largeRuinParts; ++j1) {
            k1 = i - 5 + random.nextInt(10);
            k1 = k - 5 + random.nextInt(10);
            if (k1 != i || k1 != k) {
               j1 = world.func_72976_f(k1, k1);
               if (world.func_147439_a(k1, j1 - 1, k1).func_149662_c()) {
                  height = 4 + random.nextInt(7);

                  for(int j2 = 0; j2 < height; ++j2) {
                     this.placeRandomBrick(world, random, k1, j1 + j2, k1);
                  }
               }

               this.setGrassToDirt(world, k1, j1 - 1, k1);
            }
         }

         for(j1 = i - 1; j1 <= i + 1; ++j1) {
            for(k1 = j - 2; k1 >= j - 5; --k1) {
               for(k1 = k - 1; k1 <= k + 1; ++k1) {
                  if (!LOTRMod.isOpaque(world, j1, k1, k1)) {
                     return true;
                  }
               }
            }
         }

         for(j1 = i - 1; j1 <= i + 8; ++j1) {
            for(k1 = j - 6; k1 >= j - 11; --k1) {
               for(k1 = k - 3; k1 <= k + 3; ++k1) {
                  if (!LOTRMod.isOpaque(world, j1, k1, k1)) {
                     return true;
                  }
               }
            }
         }

         for(j1 = i - 1; j1 <= i + 8; ++j1) {
            for(k1 = j - 6; k1 >= j - 11; --k1) {
               for(k1 = k - 3; k1 <= k + 3; ++k1) {
                  if (k1 != j - 6 && k1 >= j - 9) {
                     world.func_147465_d(j1, k1, k1, LOTRMod.brick, 1, 2);
                  } else {
                     world.func_147465_d(j1, k1, k1, LOTRMod.rock, 1, 2);
                  }
               }
            }
         }

         for(j1 = i; j1 <= i + 7; ++j1) {
            for(k1 = j - 7; k1 >= j - 9; --k1) {
               for(k1 = k - 2; k1 <= k + 2; ++k1) {
                  world.func_147465_d(j1, k1, k1, Blocks.field_150350_a, 0, 2);
               }
            }
         }

         for(j1 = k - 2; j1 <= k + 2; ++j1) {
            world.func_147465_d(i + 7, j - 9, j1, LOTRMod.brick, 1, 2);
            world.func_147465_d(i + 7, j - 7, j1, LOTRMod.slabSingle, 11, 2);
            world.func_147465_d(i, j - 7, j1, LOTRMod.slabSingle, 11, 2);
         }

         for(j1 = i + 1; j1 <= i + 5; ++j1) {
            for(k1 = k - 1; k1 <= k + 1; ++k1) {
               world.func_147465_d(j1, j - 10, k1, LOTRMod.slabDouble, 2, 2);
            }
         }

         world.func_147465_d(i + 2, j - 9, k, LOTRMod.slabSingle, 2, 2);
         world.func_147465_d(i + 3, j - 9, k, LOTRMod.slabSingle, 2, 2);
         world.func_147465_d(i + 4, j - 9, k, LOTRMod.slabSingle, 2, 2);
         this.placeSpawnerChest(world, i + 4, j - 10, k, LOTRMod.spawnerChestStone, 4, LOTREntityGondorRuinsWraith.class);
         LOTRChestContents.fillChest(world, random, i + 4, j - 10, k, LOTRChestContents.GONDOR_RUINS_TREASURE);
         world.func_147465_d(i + 2, j - 10, k, LOTRMod.chestStone, 4, 2);
         LOTRChestContents.fillChest(world, random, i + 2, j - 10, k, LOTRChestContents.GONDOR_RUINS_BONES);

         for(j1 = j - 2; j1 >= j - 9; --j1) {
            world.func_147465_d(i, j1, k, Blocks.field_150468_ap, 5, 2);
         }

         world.func_147465_d(i, j - 1, k, LOTRMod.brick, 5, 2);
         return true;
      }
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(4) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
      } else {
         this.func_150516_a(world, i, j, k, LOTRMod.brick, 1);
      }

   }

   private void placeRandomSlab(World world, Random random, int i, int j, int k) {
      if (random.nextInt(4) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.slabSingle, 4 + random.nextInt(2));
      } else {
         this.func_150516_a(world, i, j, k, LOTRMod.slabSingle, 3);
      }

   }
}
