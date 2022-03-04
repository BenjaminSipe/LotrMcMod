package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedDunedainTower extends LOTRWorldGenStructureBase {
   public LOTRWorldGenRuinedDunedainTower(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions && world.func_147439_a(i, j - 1, k) != Blocks.field_150349_c) {
         return false;
      } else {
         --j;
         int rotation = random.nextInt(4);
         int radius = 4 + random.nextInt(2);
         if (!this.restrictions && this.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
            switch(rotation) {
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

         int sections = 4 + random.nextInt(3);
         int sectionHeight = 4 + random.nextInt(4);
         int maxHeight = (sections - 1) * sectionHeight;
         int wallThresholdMin = radius * radius;
         int wallThresholdMax = radius + 1;
         wallThresholdMax *= wallThresholdMax;

         int i1;
         int i1;
         int k1;
         int j1;
         int randomFeature;
         int j2;
         for(i1 = i - radius; i1 <= i + radius; ++i1) {
            for(i1 = k - radius; i1 <= k + radius; ++i1) {
               k1 = i1 - i;
               j1 = i1 - k;
               randomFeature = k1 * k1 + j1 * j1;
               if (randomFeature < wallThresholdMax) {
                  int j2;
                  if (randomFeature < wallThresholdMin) {
                     for(j2 = j + sectionHeight; j2 <= j + maxHeight; j2 += sectionHeight) {
                        if (random.nextInt(6) != 0) {
                           this.func_150516_a(world, i1, j2, i1, Blocks.field_150333_U, 8);
                        }
                     }
                  } else {
                     for(j2 = j - 1; j2 >= 0; --j2) {
                        Block block = world.func_147439_a(i1, j2, i1);
                        this.placeRandomBrick(world, random, i1, j2, i1);
                        if (block == Blocks.field_150349_c || block == Blocks.field_150346_d || block == Blocks.field_150348_b || !this.restrictions && block.func_149662_c()) {
                           break;
                        }
                     }

                     j2 = j + maxHeight;

                     for(j2 = j; j2 <= j2; ++j2) {
                        if (random.nextInt(20) != 0) {
                           this.placeRandomBrick(world, random, i1, j2, i1);
                        }
                     }

                     j2 = j2 + 1 + random.nextInt(3);

                     for(int j1 = j2; j1 <= j2; ++j1) {
                        this.placeRandomBrick(world, random, i1, j1, i1);
                     }
                  }
               }
            }
         }

         for(i1 = j + sectionHeight; i1 < j + maxHeight; i1 += sectionHeight) {
            for(i1 = i1 + 2; i1 <= i1 + 3; ++i1) {
               for(k1 = i - 1; k1 <= i + 1; ++k1) {
                  this.placeIronBars(world, random, k1, i1, k - radius);
                  this.placeIronBars(world, random, k1, i1, k + radius);
               }

               for(k1 = k - 1; k1 <= k + 1; ++k1) {
                  this.placeIronBars(world, random, i - radius, i1, k1);
                  this.placeIronBars(world, random, i + radius, i1, k1);
               }
            }
         }

         this.func_150516_a(world, i, j + maxHeight, k, Blocks.field_150333_U, 8);
         this.func_150516_a(world, i, j + maxHeight + 1, k, LOTRMod.chestStone, rotation + 2);
         LOTRChestContents.fillChest(world, random, i, j + maxHeight + 1, k, LOTRChestContents.DUNEDAIN_TOWER);
         label197:
         switch(rotation) {
         case 0:
            i1 = i - 1;

            while(true) {
               if (i1 > i + 1) {
                  break label197;
               }

               i1 = j + 1 + random.nextInt(3);

               for(k1 = j; k1 <= i1; ++k1) {
                  this.func_150516_a(world, i1, k1, k - radius, Blocks.field_150350_a, 0);
               }

               ++i1;
            }
         case 1:
            i1 = k - 1;

            while(true) {
               if (i1 > k + 1) {
                  break label197;
               }

               i1 = j + 1 + random.nextInt(3);

               for(k1 = j; k1 <= i1; ++k1) {
                  this.func_150516_a(world, i + radius, k1, i1, Blocks.field_150350_a, 0);
               }

               ++i1;
            }
         case 2:
            i1 = i - 1;

            while(true) {
               if (i1 > i + 1) {
                  break label197;
               }

               i1 = j + 1 + random.nextInt(3);

               for(k1 = j; k1 <= i1; ++k1) {
                  this.func_150516_a(world, i1, k1, k + radius, Blocks.field_150350_a, 0);
               }

               ++i1;
            }
         case 3:
            for(i1 = k - 1; i1 <= k + 1; ++i1) {
               i1 = j + 1 + random.nextInt(3);

               for(k1 = j; k1 <= i1; ++k1) {
                  this.func_150516_a(world, i - radius, k1, i1, Blocks.field_150350_a, 0);
               }
            }
         }

         for(i1 = 0; i1 < 16; ++i1) {
            i1 = i - random.nextInt(radius * 2) + random.nextInt(radius * 2);
            k1 = k - random.nextInt(radius * 2) + random.nextInt(radius * 2);
            j1 = world.func_72976_f(i1, k1);
            if (world.func_147439_a(i1, j1 - 1, k1) == Blocks.field_150349_c) {
               randomFeature = random.nextInt(4);
               boolean flag = true;
               if (randomFeature == 0) {
                  if (!LOTRMod.isOpaque(world, i1, j1, k1)) {
                     this.func_150516_a(world, i1, j1, k1, Blocks.field_150333_U, random.nextBoolean() ? 0 : 5);
                  }
               } else {
                  for(j2 = j1; j2 < j1 + randomFeature && flag; ++j2) {
                     flag = !LOTRMod.isOpaque(world, i1, j2, k1);
                  }

                  if (flag) {
                     for(j2 = j1; j2 < j1 + randomFeature; ++j2) {
                        this.placeRandomBrick(world, random, i1, j2, k1);
                     }
                  }
               }

               if (flag) {
                  world.func_147439_a(i1, j1 - 1, k1).onPlantGrow(world, i1, j1 - 1, k1, i1, j1, k1);
               }
            }
         }

         return true;
      }
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(5) == 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.brick2, 4 + random.nextInt(2));
      } else {
         this.func_150516_a(world, i, j, k, LOTRMod.brick2, 3);
      }

   }

   private void placeIronBars(World world, Random random, int i, int j, int k) {
      if (random.nextInt(4) == 0) {
         this.func_150516_a(world, i, j, k, Blocks.field_150350_a, 0);
      } else {
         this.func_150516_a(world, i, j, k, Blocks.field_150411_aY, 0);
      }

   }
}
