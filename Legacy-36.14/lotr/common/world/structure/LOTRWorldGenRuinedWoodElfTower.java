package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMirkwoodSpider;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.feature.LOTRWorldGenWebOfUngoliant;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedWoodElfTower extends LOTRWorldGenStructureBase {
   public LOTRWorldGenRuinedWoodElfTower(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (!this.restrictions || world.func_147439_a(i, j - 1, k) == Blocks.field_150349_c && world.func_72807_a(i, k) == LOTRBiome.mirkwoodCorrupted) {
         --j;
         int rotation = random.nextInt(4);
         int radius = 6;
         int radiusPlusOne = radius + 1;
         if (!this.restrictions && this.usingPlayer != null) {
            rotation = this.usingPlayerRotation();
         }

         switch(rotation) {
         case 0:
            k += radiusPlusOne;
            break;
         case 1:
            i -= radiusPlusOne;
            break;
         case 2:
            k -= radiusPlusOne;
            break;
         case 3:
            i += radiusPlusOne;
         }

         int sections;
         int topHeight;
         int wallThresholdMin;
         int wallThresholdMax;
         if (this.restrictions) {
            for(sections = i - radiusPlusOne; sections <= i + radiusPlusOne; ++sections) {
               for(int k1 = k - radiusPlusOne; k1 <= k + radiusPlusOne; ++k1) {
                  topHeight = sections - i;
                  wallThresholdMin = k1 - k;
                  if (topHeight * topHeight + wallThresholdMin * wallThresholdMin <= radiusPlusOne * radiusPlusOne) {
                     wallThresholdMax = world.func_72825_h(sections, k1) - 1;
                     Block block = world.func_147439_a(sections, wallThresholdMax, k1);
                     if (block != Blocks.field_150349_c && block != Blocks.field_150346_d && block != Blocks.field_150348_b && !block.isWood(world, sections, wallThresholdMax, k1) && !block.isLeaves(world, sections, wallThresholdMax, k1)) {
                        return false;
                     }
                  }
               }
            }
         }

         sections = 3 + random.nextInt(3);
         int sectionHeight = 8;
         topHeight = j + sections * sectionHeight;
         wallThresholdMin = radius * radius;
         wallThresholdMax = radiusPlusOne * radiusPlusOne;

         int i1;
         int j1;
         int k1;
         int k1;
         int i2;
         int k2;
         int j1;
         for(j1 = i - radius; j1 <= i + radius; ++j1) {
            for(i1 = k - radius; i1 <= k + radius; ++i1) {
               j1 = j1 - i;
               k1 = i1 - k;
               k1 = j1 * j1 + k1 * k1;
               if (k1 < wallThresholdMax) {
                  i2 = j - sectionHeight;

                  for(k2 = i2; (k2 == i2 || !LOTRMod.isOpaque(world, j1, k2, i1)) && k2 >= 0; --k2) {
                     if (k2 == i2 && k1 < wallThresholdMin) {
                        this.placeDungeonBlock(world, random, j1, k2, i1);
                     } else {
                        this.placeRandomStoneBrick(world, random, j1, k2, i1);
                     }

                     this.setGrassToDirt(world, j1, k2 - 1, i1);
                  }
               }
            }
         }

         for(j1 = -1; j1 < sections; ++j1) {
            i1 = j + j1 * sectionHeight;

            for(j1 = i1 + 1; j1 <= i1 + sectionHeight; ++j1) {
               for(k1 = i - radius; k1 <= i + radius; ++k1) {
                  for(k1 = k - radius; k1 <= k + radius; ++k1) {
                     i2 = k1 - i;
                     k2 = k1 - k;
                     int distSq = i2 * i2 + k2 * k2;
                     if (distSq < wallThresholdMax) {
                        if (distSq >= wallThresholdMin) {
                           this.placeRandomStoneBrick(world, random, k1, j1, k1);
                           if (j1 == sections - 1 && j1 == i1 + sectionHeight) {
                              this.placeRandomStoneBrick(world, random, k1, j1 + 1, k1);
                              this.placeRandomStoneSlab(world, random, k1, j1 + 2, k1, false);
                           }
                        } else if (j1 != i1 + sectionHeight || Math.abs(i2) <= 2 && Math.abs(k2) <= 2) {
                           this.func_150516_a(world, k1, j1, k1, Blocks.field_150350_a, 0);
                        } else {
                           this.placeDungeonBlock(world, random, k1, j1, k1);
                        }

                        this.setGrassToDirt(world, k1, j1 - 1, k1);
                     }
                  }
               }

               this.placeDungeonBlock(world, random, i, j1, k);
            }

            for(j1 = 0; j1 < 2; ++j1) {
               k1 = i1 + j1 * 4;
               this.placeRandomStoneSlab(world, random, i, k1 + 1, k + 1, false);
               this.placeRandomStoneSlab(world, random, i, k1 + 1, k + 2, false);
               this.placeRandomStoneSlab(world, random, i + 1, k1 + 2, k, false);
               this.placeRandomStoneSlab(world, random, i + 2, k1 + 2, k, false);
               this.placeRandomStoneSlab(world, random, i, k1 + 3, k - 1, false);
               this.placeRandomStoneSlab(world, random, i, k1 + 3, k - 2, false);
               this.placeRandomStoneSlab(world, random, i - 1, k1 + 4, k, false);
               this.placeRandomStoneSlab(world, random, i - 2, k1 + 4, k, false);

               for(k1 = 0; k1 <= 1; ++k1) {
                  for(i2 = 0; i2 <= 1; ++i2) {
                     this.placeRandomStoneSlab(world, random, i + 1 + k1, k1 + 1, k + 1 + i2, true);
                     this.placeRandomStoneSlab(world, random, i + 1 + k1, k1 + 2, k - 2 + i2, true);
                     this.placeRandomStoneSlab(world, random, i - 2 + k1, k1 + 3, k - 2 + i2, true);
                     this.placeRandomStoneSlab(world, random, i - 2 + k1, k1 + 4, k + 1 + i2, true);
                  }
               }
            }

            if (j1 > 0) {
               for(j1 = i1 + 1; j1 <= i1 + 4; ++j1) {
                  for(k1 = i - 1; k1 <= i + 1; ++k1) {
                     this.func_150516_a(world, k1, j1, k - 6, Blocks.field_150350_a, 0);
                     this.func_150516_a(world, k1, j1, k + 6, Blocks.field_150350_a, 0);
                  }

                  for(k1 = k - 1; k1 <= k + 1; ++k1) {
                     this.func_150516_a(world, i - 6, j1, k1, Blocks.field_150350_a, 0);
                     this.func_150516_a(world, i + 6, j1, k1, Blocks.field_150350_a, 0);
                  }
               }

               this.placeRandomStoneStairs(world, random, i - 1, i1 + 4, k - 6, 5);
               this.placeRandomStoneStairs(world, random, i + 1, i1 + 4, k - 6, 4);
               this.placeRandomStoneStairs(world, random, i - 1, i1 + 4, k + 6, 5);
               this.placeRandomStoneStairs(world, random, i + 1, i1 + 4, k + 6, 4);
               this.placeRandomStoneStairs(world, random, i - 6, i1 + 4, k - 1, 7);
               this.placeRandomStoneStairs(world, random, i - 6, i1 + 4, k + 1, 6);
               this.placeRandomStoneStairs(world, random, i + 6, i1 + 4, k - 1, 7);
               this.placeRandomStoneStairs(world, random, i + 6, i1 + 4, k + 1, 6);
            }
         }

         for(j1 = topHeight + 2; j1 <= topHeight + 3; ++j1) {
            this.placeRandomStoneBrick(world, random, i + 6, j1, k - 3);
            this.placeRandomStoneBrick(world, random, i + 6, j1, k);
            this.placeRandomStoneBrick(world, random, i + 6, j1, k + 3);
            this.placeRandomStoneBrick(world, random, i - 3, j1, k + 6);
            this.placeRandomStoneBrick(world, random, i, j1, k + 6);
            this.placeRandomStoneBrick(world, random, i + 3, j1, k + 6);
            this.placeRandomStoneBrick(world, random, i - 6, j1, k - 3);
            this.placeRandomStoneBrick(world, random, i - 6, j1, k);
            this.placeRandomStoneBrick(world, random, i - 6, j1, k + 3);
            this.placeRandomStoneBrick(world, random, i - 3, j1, k - 6);
            this.placeRandomStoneBrick(world, random, i, j1, k - 6);
            this.placeRandomStoneBrick(world, random, i + 3, j1, k - 6);
         }

         this.placeRandomStoneBrick(world, random, i + 6, topHeight + 2, k - 2);
         this.placeRandomStoneBrick(world, random, i + 6, topHeight + 2, k + 2);
         this.placeRandomStoneBrick(world, random, i - 2, topHeight + 2, k + 6);
         this.placeRandomStoneBrick(world, random, i + 2, topHeight + 2, k + 6);
         this.placeRandomStoneBrick(world, random, i - 6, topHeight + 2, k - 2);
         this.placeRandomStoneBrick(world, random, i - 6, topHeight + 2, k + 2);
         this.placeRandomStoneBrick(world, random, i - 2, topHeight + 2, k - 6);
         this.placeRandomStoneBrick(world, random, i + 2, topHeight + 2, k - 6);

         for(j1 = j - sectionHeight - 6; j1 <= j - sectionHeight - 1; ++j1) {
            this.placeDungeonBlock(world, random, i - 6, j1, k);
            this.placeDungeonBlock(world, random, i - 5, j1, k - 2);
            this.placeDungeonBlock(world, random, i - 5, j1, k - 1);
            this.placeDungeonBlock(world, random, i - 5, j1, k + 1);
            this.placeDungeonBlock(world, random, i - 5, j1, k + 2);
            this.placeDungeonBlock(world, random, i - 4, j1, k - 3);
            this.placeDungeonBlock(world, random, i - 4, j1, k + 3);
            this.placeDungeonBlock(world, random, i - 3, j1, k - 5);
            this.placeDungeonBlock(world, random, i - 3, j1, k - 4);
            this.placeDungeonBlock(world, random, i - 3, j1, k + 4);
            this.placeDungeonBlock(world, random, i - 3, j1, k + 5);
            this.placeDungeonBlock(world, random, i - 2, j1, k - 6);
            this.placeDungeonBlock(world, random, i - 2, j1, k + 6);
            this.placeDungeonBlock(world, random, i - 1, j1, k - 6);
            this.placeDungeonBlock(world, random, i - 1, j1, k + 6);
            this.placeDungeonBlock(world, random, i, j1, k - 6);
            this.placeDungeonBlock(world, random, i, j1, k + 6);
            this.placeDungeonBlock(world, random, i + 1, j1, k - 5);
            this.placeDungeonBlock(world, random, i + 1, j1, k - 4);
            this.placeDungeonBlock(world, random, i + 1, j1, k + 4);
            this.placeDungeonBlock(world, random, i + 1, j1, k + 5);
            this.placeDungeonBlock(world, random, i + 2, j1, k - 3);
            this.placeDungeonBlock(world, random, i + 2, j1, k + 3);
            this.placeDungeonBlock(world, random, i + 3, j1, k - 2);
            this.placeDungeonBlock(world, random, i + 3, j1, k + 2);
            this.placeDungeonBlock(world, random, i + 4, j1, k - 2);
            this.placeDungeonBlock(world, random, i + 4, j1, k + 2);
            this.placeDungeonBlock(world, random, i + 5, j1, k - 1);
            this.placeDungeonBlock(world, random, i + 5, j1, k);
            this.placeDungeonBlock(world, random, i + 5, j1, k + 1);
            if (j1 != j - sectionHeight - 6 && j1 != j - sectionHeight - 1) {
               this.func_150516_a(world, i - 5, j1, k, Blocks.field_150350_a, 0);

               for(i1 = k - 2; i1 <= k + 2; ++i1) {
                  this.func_150516_a(world, i - 4, j1, i1, Blocks.field_150350_a, 0);
               }

               for(i1 = k - 3; i1 <= k + 3; ++i1) {
                  this.func_150516_a(world, i - 3, j1, i1, Blocks.field_150350_a, 0);
               }

               for(i1 = k - 5; i1 <= k + 5; ++i1) {
                  this.func_150516_a(world, i - 2, j1, i1, Blocks.field_150350_a, 0);
                  this.func_150516_a(world, i - 1, j1, i1, Blocks.field_150350_a, 0);
                  this.func_150516_a(world, i, j1, i1, Blocks.field_150350_a, 0);
               }

               for(i1 = k - 3; i1 <= k + 3; ++i1) {
                  this.func_150516_a(world, i + 1, j1, i1, Blocks.field_150350_a, 0);
               }

               for(i1 = k - 2; i1 <= k + 2; ++i1) {
                  this.func_150516_a(world, i + 2, j1, i1, Blocks.field_150350_a, 0);
               }

               for(i1 = k - 1; i1 <= k + 1; ++i1) {
                  this.func_150516_a(world, i + 3, j1, i1, Blocks.field_150350_a, 0);
                  this.func_150516_a(world, i + 4, j1, i1, Blocks.field_150350_a, 0);
               }
            } else {
               this.placeDungeonBlock(world, random, i - 5, j1, k);

               for(i1 = k - 2; i1 <= k + 2; ++i1) {
                  this.placeDungeonBlock(world, random, i - 4, j1, i1);
               }

               for(i1 = k - 3; i1 <= k + 3; ++i1) {
                  this.placeDungeonBlock(world, random, i - 3, j1, i1);
               }

               for(i1 = k - 5; i1 <= k + 5; ++i1) {
                  this.placeDungeonBlock(world, random, i - 2, j1, i1);
                  this.placeDungeonBlock(world, random, i - 1, j1, i1);
                  this.placeDungeonBlock(world, random, i, j1, i1);
               }

               for(i1 = k - 3; i1 <= k + 3; ++i1) {
                  this.placeDungeonBlock(world, random, i + 1, j1, i1);
               }

               for(i1 = k - 2; i1 <= k + 2; ++i1) {
                  this.placeDungeonBlock(world, random, i + 2, j1, i1);
               }

               for(i1 = k - 1; i1 <= k + 1; ++i1) {
                  this.placeDungeonBlock(world, random, i + 3, j1, i1);
                  this.placeDungeonBlock(world, random, i + 4, j1, i1);
               }
            }
         }

         for(j1 = i - 2; j1 <= i; ++j1) {
            this.placeDungeonBlock(world, random, j1, j - sectionHeight - 2, k - 5);
            this.placeDungeonBlock(world, random, j1, j - sectionHeight - 2, k - 4);
            this.placeDungeonBlock(world, random, j1, j - sectionHeight - 2, k + 4);
            this.placeDungeonBlock(world, random, j1, j - sectionHeight - 2, k + 5);
         }

         for(j1 = k - 1; j1 <= k + 1; ++j1) {
            this.placeDungeonBlock(world, random, i + 3, j - sectionHeight - 2, j1);
            this.placeDungeonBlock(world, random, i + 4, j - sectionHeight - 2, j1);
         }

         for(j1 = j - sectionHeight - 5; j1 <= j - sectionHeight - 3; ++j1) {
            for(i1 = i - 2; i1 <= i; ++i1) {
               if (random.nextInt(4) == 0) {
                  this.func_150516_a(world, i1, j1, k - 4, LOTRMod.woodElfBars, 0);
               }

               if (random.nextInt(4) == 0) {
                  this.func_150516_a(world, i1, j1, k + 4, LOTRMod.woodElfBars, 0);
               }
            }

            for(i1 = k - 1; i1 <= k + 1; ++i1) {
               if (random.nextInt(4) == 0) {
                  this.func_150516_a(world, i + 3, j1, i1, LOTRMod.woodElfBars, 0);
               }
            }
         }

         this.placeSkull(world, random, i - 2, j - sectionHeight - 5, k - 5, 3, 1);
         this.placeSkull(world, random, i + 2, j - sectionHeight - 5, k + 5, 3, 1);
         this.placeSkull(world, random, i + 4, j - sectionHeight - 5, k - 1, 1, 3);

         for(j1 = MathHelper.func_76136_a(random, 4, 6); j1 > 0; --j1) {
            LOTREntityMirkwoodSpider spider = new LOTREntityMirkwoodSpider(world);
            spider.func_70012_b((double)(i - 1) + 0.5D, (double)(j - sectionHeight - 5), (double)k + 0.5D, 0.0F, 0.0F);
            spider.func_110171_b(i, j + 1, k, 8);
            spider.func_110161_a((IEntityLivingData)null);
            spider.isNPCPersistent = true;
            world.func_72838_d(spider);
         }

         (new LOTRWorldGenWebOfUngoliant(this.notifyChanges, 24)).func_76484_a(world, random, i - 1, j - sectionHeight - 5, k);
         this.placeDungeonBlock(world, random, i + 4, j - sectionHeight - 5, k);
         this.func_150516_a(world, i + 4, j - sectionHeight - 5, k - 1, Blocks.field_150486_ae, 0);
         LOTRChestContents.fillChest(world, random, i + 4, j - sectionHeight - 5, k - 1, LOTRChestContents.MIRKWOOD_LOOT);
         this.func_150516_a(world, i + 4, j - sectionHeight - 5, k + 1, Blocks.field_150486_ae, 0);
         LOTRChestContents.fillChest(world, random, i + 4, j - sectionHeight - 5, k + 1, LOTRChestContents.MIRKWOOD_LOOT);
         this.func_150516_a(world, i - 5, j - sectionHeight - 1, k, Blocks.field_150350_a, 0);
         this.func_150516_a(world, i - 5, j - sectionHeight, k, Blocks.field_150350_a, 0);
         switch(rotation) {
         case 0:
            k1 = k - radius;

            for(i1 = i - 1; i1 <= i + 1; ++i1) {
               for(j1 = j + 1; j1 <= j + 3; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }

            return true;
         case 1:
            i1 = i + radius;

            for(k1 = k - 1; k1 <= k + 1; ++k1) {
               for(j1 = j + 1; j1 <= j + 3; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }

            return true;
         case 2:
            k1 = k + radius;

            for(i1 = i - 1; i1 <= i + 1; ++i1) {
               for(j1 = j + 1; j1 <= j + 3; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }

            return true;
         case 3:
            i1 = i - radius;

            for(k1 = k - 1; k1 <= k + 1; ++k1) {
               for(j1 = j + 1; j1 <= j + 3; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void placeRandomStoneBrick(World world, Random random, int i, int j, int k) {
      if (random.nextInt(20) != 0) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.func_150516_a(world, i, j, k, LOTRMod.brick3, 5);
            break;
         case 1:
            this.func_150516_a(world, i, j, k, LOTRMod.brick3, 6);
            break;
         case 2:
            this.func_150516_a(world, i, j, k, LOTRMod.brick3, 7);
         }

      }
   }

   private void placeRandomStoneSlab(World world, Random random, int i, int j, int k, boolean inverted) {
      if (random.nextInt(8) != 0) {
         this.func_150516_a(world, i, j, k, LOTRMod.slabSingle6, 2 + random.nextInt(3) | (inverted ? 8 : 0));
      }
   }

   private void placeRandomStoneStairs(World world, Random random, int i, int j, int k, int meta) {
      if (random.nextInt(8) != 0) {
         int l = random.nextInt(3);
         switch(l) {
         case 0:
            this.func_150516_a(world, i, j, k, LOTRMod.stairsWoodElvenBrick, meta);
            break;
         case 1:
            this.func_150516_a(world, i, j, k, LOTRMod.stairsWoodElvenBrickMossy, meta);
            break;
         case 2:
            this.func_150516_a(world, i, j, k, LOTRMod.stairsWoodElvenBrickCracked, meta);
         }

      }
   }

   private void placeDungeonBlock(World world, Random random, int i, int j, int k) {
      int l = random.nextInt(3);
      switch(l) {
      case 0:
         this.func_150516_a(world, i, j, k, LOTRMod.brick3, 5);
         break;
      case 1:
         this.func_150516_a(world, i, j, k, LOTRMod.brick3, 6);
         break;
      case 2:
         this.func_150516_a(world, i, j, k, LOTRMod.brick3, 7);
      }

   }

   private void placeSkull(World world, Random random, int i, int j, int k, int xRange, int zRange) {
      i += random.nextInt(xRange);
      k += random.nextInt(zRange);
      if (random.nextBoolean()) {
         this.placeSkull(world, random, i, j, k);
      }

   }
}
