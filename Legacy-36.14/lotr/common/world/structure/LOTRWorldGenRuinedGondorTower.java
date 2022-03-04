package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedGondorTower extends LOTRWorldGenStructureBase {
   public LOTRWorldGenRuinedGondorTower(boolean flag) {
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

         int i1;
         int k1;
         int j1;
         Block id;
         if (this.restrictions) {
            for(i1 = i - 3; i1 <= i + 3; ++i1) {
               for(k1 = k + 3; k1 <= k + 3; ++k1) {
                  j1 = world.func_72976_f(i1, k1);
                  id = world.func_147439_a(i1, j1 - 1, k1);
                  if (id != Blocks.field_150349_c && !id.isWood(world, i1, j1 - 1, k1) && !id.isLeaves(world, i1, j1 - 1, k1)) {
                     return false;
                  }
               }
            }
         }

         for(i1 = i - 3; i1 <= i + 3; ++i1) {
            for(k1 = k - 3; k1 <= k + 3; ++k1) {
               if (Math.abs(i1 - i) != 3 && Math.abs(k1 - k) != 3) {
                  for(j1 = j; !LOTRMod.isOpaque(world, i1, j1, k1) && j1 >= 0; --j1) {
                     this.placeRandomBrick(world, random, i1, j1, k1);
                     this.setGrassToDirt(world, i1, j1 - 1, k1);
                  }

                  for(j1 = j + 1; j1 <= j + 8; ++j1) {
                     this.func_150516_a(world, i1, j1, k1, Blocks.field_150350_a, 0);
                  }
               } else {
                  for(j1 = j + 8; (j1 >= j || !LOTRMod.isOpaque(world, i1, j1, k1)) && j1 >= 0; --j1) {
                     this.placeRandomBrick(world, random, i1, j1, k1);
                     this.setGrassToDirt(world, i1, j1 - 1, k1);
                  }
               }

               if (Math.abs(i1 - i) < 3 && Math.abs(k1 - k) < 3 && random.nextInt(20) != 0) {
                  this.func_150516_a(world, i1, j + 5, k1, Blocks.field_150344_f, 0);
               }
            }
         }

         this.func_150516_a(world, i - 2, j + 1, k - 2, LOTRMod.chestLebethron, 0);
         if (random.nextInt(3) == 0) {
            LOTRChestContents.fillChest(world, random, i - 2, j + 1, k - 2, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
         }

         this.func_150516_a(world, i + 2, j + 1, k - 2, LOTRMod.gondorianTable, 0);
         if (random.nextInt(3) != 0) {
            this.func_150516_a(world, i + 2, j + 6, k - 2, LOTRMod.strawBed, 10);
            this.func_150516_a(world, i + 2, j + 6, k - 1, LOTRMod.strawBed, 2);
         }

         if (random.nextBoolean()) {
            this.func_150516_a(world, i + 2, j + 6, k + 2, Blocks.field_150467_bQ, 8);
         }

         for(i1 = k; i1 <= k + 2; ++i1) {
            this.func_150516_a(world, i - 2, j + 6, i1, LOTRMod.slabSingle, 10);
         }

         if (random.nextBoolean()) {
            this.func_150516_a(world, i - 2, j + 7, k, LOTRMod.mugBlock, 1);
         }

         if (random.nextBoolean()) {
            this.func_150516_a(world, i - 2, j + 7, k + 1, LOTRMod.plateBlock, 0);
         }

         if (random.nextBoolean()) {
            this.func_150516_a(world, i - 2, j + 7, k + 2, LOTRMod.barrel, 5);
         }

         for(i1 = i - 4; i1 <= i + 4; ++i1) {
            for(k1 = k - 4; k1 <= k + 4; ++k1) {
               this.placeRandomBrick(world, random, i1, j + 9, k1);
               if ((Math.abs(i1 - i) == 4 && Math.abs(k1 - k) % 2 == 0 || Math.abs(k1 - k) == 4 && Math.abs(i1 - i) % 2 == 0) && LOTRMod.isOpaque(world, i1, j + 9, k1)) {
                  this.placeRandomBrick(world, random, i1, j + 10, k1);
               }
            }
         }

         for(i1 = j + 1; i1 <= j + 9; ++i1) {
            if (rotation == 2) {
               if (LOTRMod.isOpaque(world, i + 3, i1, k)) {
                  this.func_150516_a(world, i + 2, i1, k, Blocks.field_150468_ap, 4);
               }
            } else if (LOTRMod.isOpaque(world, i, i1, k + 3)) {
               this.func_150516_a(world, i, i1, k + 2, Blocks.field_150468_ap, 2);
            }
         }

         if (rotation == 0) {
            for(i1 = j + 1; i1 <= j + 2; ++i1) {
               this.func_150516_a(world, i - 1, i1, k - 3, LOTRMod.brick, 5);
               this.func_150516_a(world, i, i1, k - 3, Blocks.field_150350_a, 0);
               this.func_150516_a(world, i + 1, i1, k - 3, LOTRMod.brick, 5);
            }

            for(i1 = k - 4; i1 >= k - 7; --i1) {
               for(k1 = i - 2; k1 <= i + 2; k1 += 4) {
                  j1 = world.func_72976_f(k1, i1);
                  id = world.func_147439_a(k1, j1 - 1, i1);
                  if (id == Blocks.field_150349_c && random.nextInt(4) != 0) {
                     this.func_150516_a(world, k1, j1, i1, LOTRMod.wall, 3 + random.nextInt(3));
                  }
               }
            }
         }

         if (rotation == 1) {
            for(i1 = j + 1; i1 <= j + 2; ++i1) {
               this.func_150516_a(world, i + 3, i1, k - 1, LOTRMod.brick, 5);
               this.func_150516_a(world, i + 3, i1, k, Blocks.field_150350_a, 0);
               this.func_150516_a(world, i + 3, i1, k + 1, LOTRMod.brick, 5);
            }

            for(i1 = i + 4; i1 <= i + 7; ++i1) {
               for(k1 = k - 2; k1 <= k + 2; k1 += 4) {
                  j1 = world.func_72976_f(i1, k1);
                  id = world.func_147439_a(i1, j1 - 1, k1);
                  if (id == Blocks.field_150349_c && random.nextInt(4) != 0) {
                     this.func_150516_a(world, i1, j1, k1, LOTRMod.wall, 3 + random.nextInt(3));
                  }
               }
            }
         }

         if (rotation == 2) {
            for(i1 = j + 1; i1 <= j + 2; ++i1) {
               this.func_150516_a(world, i - 1, i1, k + 3, LOTRMod.brick, 5);
               this.func_150516_a(world, i, i1, k + 3, Blocks.field_150350_a, 0);
               this.func_150516_a(world, i + 1, i1, k + 3, LOTRMod.brick, 5);
            }

            for(i1 = k + 4; i1 <= k + 7; ++i1) {
               for(k1 = i - 2; k1 <= i + 2; k1 += 4) {
                  j1 = world.func_72976_f(k1, i1);
                  id = world.func_147439_a(k1, j1 - 1, i1);
                  if (id == Blocks.field_150349_c && random.nextInt(4) != 0) {
                     this.func_150516_a(world, k1, j1, i1, LOTRMod.wall, 3 + random.nextInt(3));
                  }
               }
            }
         }

         if (rotation == 3) {
            for(i1 = j + 1; i1 <= j + 2; ++i1) {
               this.func_150516_a(world, i - 3, i1, k - 1, LOTRMod.brick, 5);
               this.func_150516_a(world, i - 3, i1, k, Blocks.field_150350_a, 0);
               this.func_150516_a(world, i - 3, i1, k + 1, LOTRMod.brick, 5);
            }

            for(i1 = i - 4; i1 >= i - 7; --i1) {
               for(k1 = k - 2; k1 <= k + 2; k1 += 4) {
                  j1 = world.func_72976_f(i1, k1);
                  id = world.func_147439_a(i1, j1 - 1, k1);
                  if (id == Blocks.field_150349_c && random.nextInt(4) != 0) {
                     this.func_150516_a(world, i1, j1, k1, LOTRMod.wall, 3 + random.nextInt(3));
                  }
               }
            }
         }

         int radius = 8;
         k1 = 2 + random.nextInt(6);

         for(j1 = 0; j1 < k1; ++j1) {
            int i1 = i - random.nextInt(radius * 2) + random.nextInt(radius * 2);
            int k1 = k - random.nextInt(radius * 2) + random.nextInt(radius * 2);
            int j1 = world.func_72976_f(i1, k1);
            Block id = world.func_147439_a(i1, j1 - 1, k1);
            if (id == Blocks.field_150349_c) {
               int height = 1 + random.nextInt(3);
               boolean flag = true;

               int j2;
               for(j2 = j1; j2 < j1 + height && flag; ++j2) {
                  flag = !LOTRMod.isOpaque(world, i1, j2, k1);
               }

               if (flag) {
                  for(j2 = j1; j2 < j1 + height; ++j2) {
                     this.func_150516_a(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
                  }

                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }
            }
         }

         return true;
      }
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(16) != 0) {
         if (random.nextInt(4) == 0) {
            this.func_150516_a(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
         } else {
            this.func_150516_a(world, i, j, k, LOTRMod.brick, 1);
         }

      }
   }
}
