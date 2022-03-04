package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDolGuldurOrcChieftain;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDolGuldurTower extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenDolGuldurTower(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      int radius = 6;
      int radiusPlusOne = radius + 1;
      this.setOriginAndRotation(world, i, j, k, rotation, radiusPlusOne);
      int sections = 3 + random.nextInt(3);
      int sectionHeight = 6;
      int topHeight = sections * sectionHeight;
      double radiusD = (double)radius - 0.5D;
      double radiusDPlusOne = radiusD + 1.0D;
      int wallThresholdMin = (int)(radiusD * radiusD);
      int wallThresholdMax = (int)(radiusDPlusOne * radiusDPlusOne);
      int k1;
      int sectionBase;
      int step;
      int i1;
      int j2;
      int k2;
      if (this.restrictions) {
         k1 = 0;
         sectionBase = 0;

         for(step = -radiusPlusOne; step <= radiusPlusOne; ++step) {
            for(i1 = -radiusPlusOne; i1 <= radiusPlusOne; ++i1) {
               j2 = step * step + i1 * i1;
               if (j2 < wallThresholdMax) {
                  k2 = this.getTopBlock(world, step, i1) - 1;
                  Block block = this.getBlock(world, step, k2, i1);
                  if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150348_b) {
                     return false;
                  }

                  if (k2 < k1) {
                     k1 = k2;
                  }

                  if (k2 > sectionBase) {
                     sectionBase = k2;
                  }

                  if (sectionBase - k1 > 16) {
                     return false;
                  }
               }
            }
         }
      }

      for(k1 = -radius; k1 <= radius; ++k1) {
         for(sectionBase = -radius; sectionBase <= radius; ++sectionBase) {
            step = k1 * k1 + sectionBase * sectionBase;
            if (step < wallThresholdMax) {
               for(i1 = 0; (i1 == 0 || !this.isOpaque(world, k1, i1, sectionBase)) && this.getY(i1) >= 0; --i1) {
                  if (step >= wallThresholdMin) {
                     this.placeRandomBrick(world, random, k1, i1, sectionBase);
                  } else {
                     this.setBlockAndMetadata(world, k1, i1, sectionBase, Blocks.field_150417_aV, 0);
                  }

                  this.setGrassToDirt(world, k1, i1 - 1, sectionBase);
               }
            }
         }
      }

      for(k1 = 0; k1 < sections; ++k1) {
         sectionBase = k1 * sectionHeight;

         for(step = sectionBase + 1; step <= sectionBase + sectionHeight; ++step) {
            for(i1 = -radius; i1 <= radius; ++i1) {
               for(j2 = -radius; j2 <= radius; ++j2) {
                  k2 = i1 * i1 + j2 * j2;
                  if (k2 < wallThresholdMax) {
                     if (k2 >= wallThresholdMin) {
                        this.placeRandomBrick(world, random, i1, step, j2);
                     } else if (step == sectionBase + sectionHeight) {
                        this.setBlockAndMetadata(world, i1, step, j2, Blocks.field_150417_aV, 0);
                     } else {
                        this.setAir(world, i1, step, j2);
                     }
                  }
               }
            }
         }

         for(step = sectionBase + 2; step <= sectionBase + 3; ++step) {
            for(i1 = -1; i1 <= 1; ++i1) {
               this.setBlockAndMetadata(world, -radius, step, i1, LOTRMod.orcSteelBars, 0);
               this.setBlockAndMetadata(world, radius, step, i1, LOTRMod.orcSteelBars, 0);
            }

            for(i1 = -1; i1 <= 1; ++i1) {
               this.setBlockAndMetadata(world, i1, step, -radius, LOTRMod.orcSteelBars, 0);
            }
         }

         if (k1 > 0) {
            this.setAir(world, 0, sectionBase, 0);

            for(step = -1; step <= 1; ++step) {
               for(i1 = -1; i1 <= 1; ++i1) {
                  j2 = Math.abs(step);
                  k2 = Math.abs(i1);
                  if (j2 == 1 || k2 == 1) {
                     this.setBlockAndMetadata(world, step, sectionBase + 1, i1, LOTRMod.wall2, 8);
                  }

                  if (j2 == 1 && k2 == 1) {
                     this.setBlockAndMetadata(world, step, sectionBase + 2, i1, LOTRMod.wall2, 8);
                     this.placeSkull(world, random, step, sectionBase + 2, i1);
                  }
               }
            }
         } else {
            step = -1;

            while(true) {
               if (step > 1) {
                  this.placeRandomStairs(world, random, -1, sectionBase + 3, -radius, 4);
                  this.placeRandomStairs(world, random, 1, sectionBase + 3, -radius, 5);
                  this.placeWallBanner(world, 0, sectionBase + 6, -radius, LOTRItemBanner.BannerType.DOL_GULDUR, 2);

                  for(step = -5; step <= 5; ++step) {
                     this.setBlockAndMetadata(world, step, sectionBase, 0, LOTRMod.guldurilBrick, 4);
                  }

                  for(step = -6; step <= 3; ++step) {
                     this.setBlockAndMetadata(world, 0, sectionBase, step, LOTRMod.guldurilBrick, 4);
                  }

                  this.setBlockAndMetadata(world, 0, sectionBase + 1, 0, LOTRMod.guldurilBrick, 4);
                  this.setBlockAndMetadata(world, 0, sectionBase + 2, 0, LOTRMod.wall2, 8);
                  this.placeSkull(world, random, 0, sectionBase + 3, 0);
                  break;
               }

               for(i1 = sectionBase + 1; i1 <= sectionBase + 3; ++i1) {
                  this.setAir(world, step, i1, -radius);
               }

               this.setBlockAndMetadata(world, step, sectionBase, -radius, Blocks.field_150417_aV, 0);
               ++step;
            }
         }

         for(step = sectionBase + 1; step <= sectionBase + 5; ++step) {
            this.setBlockAndMetadata(world, -2, step, -5, LOTRMod.wood, 2);
            this.setBlockAndMetadata(world, 2, step, -5, LOTRMod.wood, 2);
            this.setBlockAndMetadata(world, 5, step, -2, LOTRMod.wood, 2);
            this.setBlockAndMetadata(world, 5, step, 2, LOTRMod.wood, 2);
            this.setBlockAndMetadata(world, -3, step, 4, LOTRMod.wood, 2);
            this.setBlockAndMetadata(world, 3, step, 4, LOTRMod.wood, 2);
            this.setBlockAndMetadata(world, -5, step, -2, LOTRMod.wood, 2);
            this.setBlockAndMetadata(world, -5, step, 2, LOTRMod.wood, 2);
         }

         this.setBlockAndMetadata(world, -3, sectionBase + 4, 3, LOTRMod.morgulTorch, 4);
         this.setBlockAndMetadata(world, 3, sectionBase + 4, 3, LOTRMod.morgulTorch, 4);
         this.setBlockAndMetadata(world, 4, sectionBase + 4, -2, LOTRMod.morgulTorch, 1);
         this.setBlockAndMetadata(world, 4, sectionBase + 4, 2, LOTRMod.morgulTorch, 1);
         this.setBlockAndMetadata(world, -2, sectionBase + 4, -4, LOTRMod.morgulTorch, 3);
         this.setBlockAndMetadata(world, 2, sectionBase + 4, -4, LOTRMod.morgulTorch, 3);
         this.setBlockAndMetadata(world, -4, sectionBase + 4, -2, LOTRMod.morgulTorch, 2);
         this.setBlockAndMetadata(world, -4, sectionBase + 4, 2, LOTRMod.morgulTorch, 2);
         this.setBlockAndMetadata(world, -3, sectionBase + 5, 3, Blocks.field_150390_bg, 6);
         this.setBlockAndMetadata(world, 3, sectionBase + 5, 3, Blocks.field_150390_bg, 6);
         this.setBlockAndMetadata(world, 4, sectionBase + 5, -2, Blocks.field_150390_bg, 5);
         this.setBlockAndMetadata(world, 5, sectionBase + 5, -1, Blocks.field_150390_bg, 7);
         this.setBlockAndMetadata(world, 5, sectionBase + 5, 1, Blocks.field_150390_bg, 6);
         this.setBlockAndMetadata(world, 4, sectionBase + 5, 2, Blocks.field_150390_bg, 5);
         this.setBlockAndMetadata(world, -2, sectionBase + 5, -4, Blocks.field_150390_bg, 7);
         this.setBlockAndMetadata(world, -1, sectionBase + 5, -5, Blocks.field_150390_bg, 4);
         this.setBlockAndMetadata(world, 1, sectionBase + 5, -5, Blocks.field_150390_bg, 5);
         this.setBlockAndMetadata(world, 2, sectionBase + 5, -4, Blocks.field_150390_bg, 7);
         this.setBlockAndMetadata(world, -4, sectionBase + 5, -2, Blocks.field_150390_bg, 4);
         this.setBlockAndMetadata(world, -5, sectionBase + 5, -1, Blocks.field_150390_bg, 7);
         this.setBlockAndMetadata(world, -5, sectionBase + 5, 1, Blocks.field_150390_bg, 6);
         this.setBlockAndMetadata(world, -4, sectionBase + 5, 2, Blocks.field_150390_bg, 4);

         for(step = 0; step <= 2; ++step) {
            this.setBlockAndMetadata(world, 1 - step, sectionBase + 1 + step, 4, Blocks.field_150390_bg, 0);

            for(i1 = sectionBase + 1; i1 <= sectionBase + step; ++i1) {
               this.setBlockAndMetadata(world, 1 - step, i1, 4, Blocks.field_150417_aV, 0);
            }
         }

         for(step = 4; step <= 5; ++step) {
            for(i1 = sectionBase + 1; i1 <= sectionBase + 3; ++i1) {
               this.setBlockAndMetadata(world, -2, i1, step, Blocks.field_150417_aV, 0);
            }
         }

         for(step = -2; step <= 0; ++step) {
            this.setAir(world, step, sectionBase + sectionHeight, 5);
         }

         for(step = 0; step <= 2; ++step) {
            this.setBlockAndMetadata(world, -1 + step, sectionBase + 4 + step, 5, Blocks.field_150390_bg, 1);
            this.setBlockAndMetadata(world, -1 + step, sectionBase + 3 + step, 5, Blocks.field_150417_aV, 0);
            this.setBlockAndMetadata(world, -1 + step, sectionBase + 2 + step, 5, Blocks.field_150390_bg, 4);
         }

         this.setBlockAndMetadata(world, 2, sectionBase + 5, 5, Blocks.field_150390_bg, 4);
      }

      this.placeChest(world, random, -1, 1, 5, 0, LOTRChestContents.DOL_GULDUR_TOWER);

      for(k1 = -3; k1 <= 3; k1 += 6) {
         for(sectionBase = 0; sectionBase <= 3; ++sectionBase) {
            this.placeBrickSupports(world, random, -9 + sectionBase, k1);
            this.placeBrickSupports(world, random, 9 - sectionBase, k1);
            this.placeRandomStairs(world, random, -9 + sectionBase, 1 + sectionBase * 2, k1, 1);
            this.placeRandomStairs(world, random, 9 - sectionBase, 1 + sectionBase * 2, k1, 0);

            for(step = 1; step <= sectionBase * 2; ++step) {
               this.placeRandomBrick(world, random, -9 + sectionBase, step, k1);
               this.placeRandomBrick(world, random, 9 - sectionBase, step, k1);
            }
         }
      }

      for(k1 = -3; k1 <= 3; k1 += 6) {
         for(sectionBase = 0; sectionBase <= 3; ++sectionBase) {
            this.placeBrickSupports(world, random, k1, -9 + sectionBase);
            this.placeBrickSupports(world, random, k1, 9 - sectionBase);
            this.placeRandomStairs(world, random, k1, 1 + sectionBase * 2, -9 + sectionBase, 2);
            this.placeRandomStairs(world, random, k1, 1 + sectionBase * 2, 9 - sectionBase, 3);

            for(step = 1; step <= sectionBase * 2; ++step) {
               this.placeRandomBrick(world, random, k1, step, -9 + sectionBase);
               this.placeRandomBrick(world, random, k1, step, 9 - sectionBase);
            }
         }
      }

      for(k1 = -radius; k1 <= radius; ++k1) {
         for(sectionBase = -radius; sectionBase <= radius; ++sectionBase) {
            step = k1 * k1 + sectionBase * sectionBase;
            if (step < wallThresholdMax && step >= (int)Math.pow(radiusD - 0.25D, 2.0D)) {
               i1 = Math.abs(k1);
               j2 = Math.abs(sectionBase);
               this.setBlockAndMetadata(world, k1, topHeight + 1, sectionBase, LOTRMod.wall2, 8);
               if (i1 >= 3 && j2 >= 3) {
                  this.setBlockAndMetadata(world, k1, topHeight + 2, sectionBase, LOTRMod.wall2, 8);
                  if (i1 == 4 && j2 == 4) {
                     this.setBlockAndMetadata(world, k1, topHeight + 3, sectionBase, LOTRMod.wall2, 8);
                     this.setBlockAndMetadata(world, k1, topHeight + 4, sectionBase, LOTRMod.wall2, 8);
                     this.setBlockAndMetadata(world, k1, topHeight + 5, sectionBase, LOTRMod.morgulTorch, 5);
                  }
               }
            }
         }
      }

      this.setAir(world, -2, topHeight + 1, 5);

      for(k1 = -2; k1 <= 2; k1 += 4) {
         for(sectionBase = 0; sectionBase <= 4; ++sectionBase) {
            step = topHeight + 1 + sectionBase * 2;
            i1 = -9 + sectionBase;
            this.placeRandomStairs(world, random, k1, step - 2, i1, 7);

            for(j2 = step - 1; j2 <= step + 1; ++j2) {
               this.placeRandomBrick(world, random, k1, j2, i1);
            }

            this.placeRandomStairs(world, random, k1, step + 2, i1, 2);
            i1 = 9 - sectionBase;
            this.placeRandomStairs(world, random, k1, step - 2, i1, 6);

            for(j2 = step - 1; j2 <= step + 1; ++j2) {
               this.placeRandomBrick(world, random, k1, j2, i1);
            }

            this.placeRandomStairs(world, random, k1, step + 2, i1, 3);
         }

         for(sectionBase = topHeight - 4; sectionBase <= topHeight + 2; ++sectionBase) {
            for(step = -9; step <= -8; ++step) {
               this.placeRandomBrick(world, random, k1, sectionBase, step);
            }

            for(step = 8; step <= 9; ++step) {
               this.placeRandomBrick(world, random, k1, sectionBase, step);
            }
         }

         this.placeRandomBrick(world, random, k1, topHeight - 1, -7);
         this.placeRandomBrick(world, random, k1, topHeight, -7);
         this.setBlockAndMetadata(world, k1, topHeight + 1, -7, LOTRMod.wall2, 8);
         this.placeRandomBrick(world, random, k1, topHeight - 1, 7);
         this.placeRandomBrick(world, random, k1, topHeight, 7);
         this.setBlockAndMetadata(world, k1, topHeight + 1, 7, LOTRMod.wall2, 8);
         this.placeRandomStairs(world, random, k1, topHeight - 4, -9, 6);
         this.placeRandomStairs(world, random, k1, topHeight - 5, -8, 6);
         this.placeRandomStairs(world, random, k1, topHeight - 4, 9, 7);
         this.placeRandomStairs(world, random, k1, topHeight - 5, 8, 7);
      }

      for(k1 = -2; k1 <= 2; k1 += 4) {
         for(sectionBase = 0; sectionBase <= 4; ++sectionBase) {
            step = topHeight + 1 + sectionBase * 2;
            i1 = -9 + sectionBase;
            this.placeRandomStairs(world, random, i1, step - 2, k1, 4);

            for(j2 = step - 1; j2 <= step + 1; ++j2) {
               this.placeRandomBrick(world, random, i1, j2, k1);
            }

            this.placeRandomStairs(world, random, i1, step + 2, k1, 1);
            i1 = 9 - sectionBase;
            this.placeRandomStairs(world, random, i1, step - 2, k1, 5);

            for(j2 = step - 1; j2 <= step + 1; ++j2) {
               this.placeRandomBrick(world, random, i1, j2, k1);
            }

            this.placeRandomStairs(world, random, i1, step + 2, k1, 0);
         }

         for(sectionBase = topHeight - 4; sectionBase <= topHeight + 2; ++sectionBase) {
            for(step = -9; step <= -8; ++step) {
               this.placeRandomBrick(world, random, step, sectionBase, k1);
            }

            for(step = 8; step <= 9; ++step) {
               this.placeRandomBrick(world, random, step, sectionBase, k1);
            }
         }

         this.placeRandomBrick(world, random, -7, topHeight - 1, k1);
         this.placeRandomBrick(world, random, -7, topHeight, k1);
         this.setBlockAndMetadata(world, -7, topHeight + 1, k1, LOTRMod.wall2, 8);
         this.placeRandomBrick(world, random, 7, topHeight - 1, k1);
         this.placeRandomBrick(world, random, 7, topHeight, k1);
         this.setBlockAndMetadata(world, 7, topHeight + 1, k1, LOTRMod.wall2, 8);
         this.placeRandomStairs(world, random, -9, topHeight - 4, k1, 5);
         this.placeRandomStairs(world, random, -8, topHeight - 5, k1, 5);
         this.placeRandomStairs(world, random, 9, topHeight - 4, k1, 4);
         this.placeRandomStairs(world, random, 8, topHeight - 5, k1, 4);
      }

      this.spawnNPCAndSetHome(new LOTREntityDolGuldurOrcChieftain(world), world, 0, topHeight + 1, 0, 16);
      this.setBlockAndMetadata(world, 0, topHeight + 1, -4, LOTRMod.commandTable, 0);
      return true;
   }

   private void placeBrickSupports(World world, Random random, int i, int k) {
      for(int j = 0; !this.isOpaque(world, i, j, k) && this.getY(j) >= 0; --j) {
         this.placeRandomBrick(world, random, i, j, k);
         this.setGrassToDirt(world, i, j - 1, k);
      }

   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(6) == 0) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 9);
      } else {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick2, 8);
      }

   }

   private void placeRandomStairs(World world, Random random, int i, int j, int k, int meta) {
      if (random.nextInt(6) == 0) {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsDolGuldurBrickCracked, meta);
      } else {
         this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsDolGuldurBrick, meta);
      }

   }
}
