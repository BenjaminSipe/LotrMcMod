package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityBlueDwarf;
import lotr.common.entity.npc.LOTREntityBlueDwarfAxeThrower;
import lotr.common.entity.npc.LOTREntityBlueDwarfCommander;
import lotr.common.entity.npc.LOTREntityBlueDwarfWarrior;
import lotr.common.entity.npc.LOTREntityDwarf;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBlueMountainsStronghold extends LOTRWorldGenStructureBase {
   public LOTRWorldGenBlueMountainsStronghold(boolean flag) {
      super(flag);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (this.restrictions) {
         Block block = world.func_147439_a(i, j - 1, k);
         if (block != Blocks.field_150349_c && block != Blocks.field_150348_b && block != Blocks.field_150346_d && block != LOTRMod.rock && block != Blocks.field_150433_aE) {
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

      int l;
      int i1;
      int i2;
      int j1;
      int l;
      if (this.restrictions) {
         l = j;
         i1 = j;

         for(i2 = i - 6; i2 <= i + 6; ++i2) {
            for(j1 = k - 6; j1 <= k + 6; ++j1) {
               l = world.func_72825_h(i2, j1) - 1;
               Block block = world.func_147439_a(i2, l, j1);
               if (block != Blocks.field_150349_c && block != Blocks.field_150348_b && block != Blocks.field_150346_d && block != LOTRMod.rock && block != Blocks.field_150433_aE) {
                  return false;
               }

               if (l < l) {
                  l = l;
               }

               if (l > i1) {
                  i1 = l;
               }
            }
         }

         if (i1 - l > 10) {
            return false;
         }
      }

      for(l = k - 6; l <= k + 6; ++l) {
         for(i1 = i - 6; i1 <= i + 6; ++i1) {
            boolean flag = Math.abs(l - k) == 6 && Math.abs(i1 - i) == 6;

            for(j1 = j + 7; (j1 >= j || !LOTRMod.isOpaque(world, i1, j1, l)) && j1 >= 0; --j1) {
               if (flag) {
                  this.func_150516_a(world, i1, j1, l, LOTRMod.pillar, 3);
               } else {
                  if (Math.abs(i1 - i) < 6 && Math.abs(l - k) < 6) {
                     if (j1 >= j + 1 && j1 <= j + 3) {
                        this.setAir(world, i1, j1, l);
                        continue;
                     }

                     if (j1 >= j + 4 && j1 <= j + 7) {
                        this.setAir(world, i1, j1, l);
                        continue;
                     }

                     if (j1 == j) {
                        this.func_150516_a(world, i1, j1, l, Blocks.field_150344_f, 1);
                        continue;
                     }
                  }

                  this.func_150516_a(world, i1, j1, l, LOTRMod.brick, 14);
               }

               if (j1 <= j) {
                  this.setGrassToDirt(world, i1, j1 - 1, l);
               }
            }
         }
      }

      for(l = i - 6; l <= i + 6; ++l) {
         this.func_150516_a(world, l, j + 8, k - 6, LOTRMod.stairsDwarvenBrick, 2);
         this.func_150516_a(world, l, j + 8, k + 6, LOTRMod.stairsDwarvenBrick, 3);
      }

      for(l = k - 6; l <= k + 6; ++l) {
         this.func_150516_a(world, i - 6, j + 8, l, LOTRMod.stairsDwarvenBrick, 0);
         this.func_150516_a(world, i + 6, j + 8, l, LOTRMod.stairsDwarvenBrick, 1);
      }

      for(l = k - 5; l <= k + 5; ++l) {
         for(i1 = i - 5; i1 <= i + 5; ++i1) {
            this.func_150516_a(world, i1, j + 4, l, LOTRMod.slabDouble3, 0);
            this.func_150516_a(world, i1, j + 8, l, LOTRMod.slabDouble3, 0);
            i2 = Math.abs(i1 - i);
            j1 = Math.abs(l - k);
            l = -1;
            if (i2 == 5) {
               l = j1 % 2;
            } else if (j1 == 5) {
               l = i2 % 2;
            }

            if (l > -1) {
               if (l == 1) {
                  for(int j1 = j + 9; j1 <= j + 11; ++j1) {
                     this.func_150516_a(world, i1, j1, l, LOTRMod.pillar, 3);
                  }
               } else {
                  this.func_150516_a(world, i1, j + 9, l, LOTRMod.wall, 14);
               }
            }
         }
      }

      for(l = i - 5; l <= i + 5; ++l) {
         this.func_150516_a(world, l, j + 12, k - 5, LOTRMod.stairsDwarvenBrick, 2);
         this.func_150516_a(world, l, j + 12, k + 5, LOTRMod.stairsDwarvenBrick, 3);
      }

      for(l = k - 5; l <= k + 5; ++l) {
         this.func_150516_a(world, i - 5, j + 12, l, LOTRMod.stairsDwarvenBrick, 0);
         this.func_150516_a(world, i + 5, j + 12, l, LOTRMod.stairsDwarvenBrick, 1);
      }

      for(l = k - 4; l <= k + 4; ++l) {
         for(i1 = i - 4; i1 <= i + 4; ++i1) {
            this.func_150516_a(world, i1, j + 12, l, LOTRMod.slabSingle, 15);
         }
      }

      this.func_150516_a(world, i, j + 7, k, LOTRMod.chandelier, 11);
      this.func_150516_a(world, i, j + 11, k, LOTRMod.chandelier, 11);
      this.func_150516_a(world, i, j + 12, k, LOTRMod.brick, 6);
      switch(rotation) {
      case 0:
         this.generateFacingSouth(world, random, i, j, k);
         break;
      case 1:
         this.generateFacingWest(world, random, i, j, k);
         break;
      case 2:
         this.generateFacingNorth(world, random, i, j, k);
         break;
      case 3:
         this.generateFacingEast(world, random, i, j, k);
      }

      this.spawnDwarfCommander(world, i, j + 9, k);

      for(l = 0; l < 4; ++l) {
         this.spawnDwarf(world, i, j + 5, k);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityBlueDwarfWarrior.class, LOTREntityBlueDwarfAxeThrower.class);
      respawner.setCheckRanges(8, -8, 16, 8);
      respawner.setSpawnRanges(8, 1, 10, 16);
      this.placeNPCRespawner(respawner, world, i, j, k);
      return true;
   }

   private void generateFacingSouth(World world, Random random, int i, int j, int k) {
      int stairX;
      int j1;
      for(stairX = i - 6; stairX <= i + 6; ++stairX) {
         this.func_150516_a(world, stairX, j + 1, k - 7, LOTRMod.slabSingle3, 0);
         this.setGrassToDirt(world, stairX, j, k - 7);

         for(j1 = j; !LOTRMod.isOpaque(world, stairX, j1, k - 7) && j1 >= 0; --j1) {
            this.func_150516_a(world, stairX, j1, k - 7, LOTRMod.pillar, 3);
            this.setGrassToDirt(world, stairX, j1 - 1, k - 7);
         }
      }

      for(stairX = j + 1; stairX <= j + 2; ++stairX) {
         this.setAir(world, i, stairX, k - 6);
         this.func_150516_a(world, i - 1, stairX, k - 7, LOTRMod.pillar, 3);
         this.func_150516_a(world, i + 1, stairX, k - 7, LOTRMod.pillar, 3);
      }

      this.func_150516_a(world, i, j, k - 7, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i, j, k - 6, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i, j + 1, k - 7, LOTRMod.doorSpruce, 1);
      this.func_150516_a(world, i, j + 2, k - 7, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i - 1, j + 3, k - 7, LOTRMod.stairsDwarvenBrick, 0);
      this.func_150516_a(world, i, j + 3, k - 7, LOTRMod.brick3, 12);
      this.func_150516_a(world, i + 1, j + 3, k - 7, LOTRMod.stairsDwarvenBrick, 1);
      this.func_150516_a(world, i, j + 4, k - 7, LOTRMod.slabSingle, 7);
      this.placeWallBanner(world, i, j + 6, k - 6, 2, LOTRItemBanner.BannerType.BLUE_MOUNTAINS);

      int i1;
      for(stairX = j + 1; stairX <= j + 3; ++stairX) {
         for(j1 = k - 4; j1 <= k - 1; ++j1) {
            for(i1 = i - 5; i1 <= i - 1; ++i1) {
               this.func_150516_a(world, i1, stairX, j1, LOTRMod.brick, 14);
            }

            for(i1 = i + 1; i1 <= i + 5; ++i1) {
               this.func_150516_a(world, i1, stairX, j1, LOTRMod.brick, 14);
            }
         }

         for(j1 = k - 2; j1 <= k + 5; ++j1) {
            this.func_150516_a(world, i - 1, stairX, j1, LOTRMod.brick, 14);
            this.func_150516_a(world, i + 1, stairX, j1, LOTRMod.brick, 14);
         }
      }

      this.func_150516_a(world, i - 1, j + 1, k + 3, LOTRMod.doorSpruce, 0);
      this.func_150516_a(world, i - 1, j + 2, k + 3, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i + 1, j + 1, k + 3, LOTRMod.doorSpruce, 2);
      this.func_150516_a(world, i + 1, j + 2, k + 3, LOTRMod.doorSpruce, 8);

      for(stairX = i - 5; stairX <= i + 2; stairX += 7) {
         this.func_150516_a(world, stairX, j + 1, k + 1, LOTRMod.dwarvenBed, 2);
         this.func_150516_a(world, stairX, j + 1, k, LOTRMod.dwarvenBed, 10);
         this.func_150516_a(world, stairX + 3, j + 1, k + 1, LOTRMod.dwarvenBed, 2);
         this.func_150516_a(world, stairX + 3, j + 1, k, LOTRMod.dwarvenBed, 10);
         this.func_150516_a(world, stairX + 1, j + 1, k, Blocks.field_150486_ae, 0);
         this.func_150516_a(world, stairX + 2, j + 1, k, Blocks.field_150486_ae, 0);
         LOTRChestContents.fillChest(world, random, stairX + 1, j + 1, k, LOTRChestContents.DWARF_HOUSE_LARDER);
         LOTRChestContents.fillChest(world, random, stairX + 2, j + 1, k, LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD);
         this.func_150516_a(world, stairX + 1, j + 3, k + 3, LOTRMod.chandelier, 11);
         this.func_150516_a(world, stairX, j + 1, k + 5, Blocks.field_150344_f, 1);
         this.func_150516_a(world, stairX + 3, j + 1, k + 5, Blocks.field_150344_f, 1);
         this.placeBarrel(world, random, stairX, j + 2, k + 5, 2, LOTRFoods.DWARF_DRINK);
         this.placeBarrel(world, random, stairX + 3, j + 2, k + 5, 2, LOTRFoods.DWARF_DRINK);
         this.func_150516_a(world, stairX + 1, j + 1, k + 5, Blocks.field_150460_al, 0);
         this.setBlockMetadata(world, stairX + 1, j + 1, k + 5, 2);
         this.func_150516_a(world, stairX + 2, j + 1, k + 5, Blocks.field_150460_al, 0);
         this.setBlockMetadata(world, stairX + 2, j + 1, k + 5, 2);
      }

      this.setAir(world, i, j + 4, k - 5);
      stairX = 1;

      for(j1 = j + 1; j1 <= j + 4; ++j1) {
         this.setAir(world, i - stairX, j + 4, k - 5);
         this.setAir(world, i + stairX, j + 4, k - 5);
         this.func_150516_a(world, i - stairX, j1, k - 5, LOTRMod.stairsDwarvenBrick, 1);
         this.func_150516_a(world, i + stairX, j1, k - 5, LOTRMod.stairsDwarvenBrick, 0);

         for(i1 = j1 - 1; i1 > j; --i1) {
            this.func_150516_a(world, i - stairX, i1, k - 5, LOTRMod.brick, 6);
            this.func_150516_a(world, i + stairX, i1, k - 5, LOTRMod.brick, 6);
         }

         ++stairX;
      }

      for(j1 = j + 1; j1 <= j + 3; ++j1) {
         this.func_150516_a(world, i - stairX, j1, k - 5, LOTRMod.brick, 6);
         this.func_150516_a(world, i + stairX, j1, k - 5, LOTRMod.brick, 6);
      }

      for(j1 = i - 5; j1 <= i + 5; j1 += 10) {
         this.func_150516_a(world, j1, j + 5, k - 2, Blocks.field_150344_f, 1);
         this.func_150516_a(world, j1, j + 6, k - 2, Blocks.field_150376_bx, 1);
         this.func_150516_a(world, j1, j + 5, k + 2, Blocks.field_150344_f, 1);
         this.func_150516_a(world, j1, j + 6, k + 2, Blocks.field_150376_bx, 1);
         this.func_150516_a(world, j1, j + 5, k, LOTRMod.blueDwarvenTable, 0);
         this.func_150516_a(world, j1, j + 5, k - 1, LOTRMod.dwarvenForge, 0);
         this.func_150516_a(world, j1, j + 5, k + 1, LOTRMod.dwarvenForge, 0);
      }

      this.func_150516_a(world, i - 3, j + 6, k + 6, LOTRMod.brick3, 12);
      this.func_150516_a(world, i + 3, j + 6, k + 6, LOTRMod.brick3, 12);
      stairX = 4;

      for(j1 = j + 5; j1 <= j + 8; ++j1) {
         this.setAir(world, i - stairX, j + 8, k - 4);
         this.setAir(world, i + stairX, j + 8, k - 4);
         this.func_150516_a(world, i - stairX, j1, k - 4, LOTRMod.stairsDwarvenBrick, 0);
         this.func_150516_a(world, i + stairX, j1, k - 4, LOTRMod.stairsDwarvenBrick, 1);

         for(i1 = j1 - 1; i1 > j + 4; --i1) {
            this.func_150516_a(world, i - stairX, i1, k - 4, LOTRMod.brick, 6);
            this.func_150516_a(world, i + stairX, i1, k - 4, LOTRMod.brick, 6);
         }

         --stairX;
      }

      for(j1 = j + 5; j1 <= j + 7; ++j1) {
         this.func_150516_a(world, i, j1, k - 4, LOTRMod.brick, 6);
      }

      this.func_150516_a(world, i, j + 6, k - 4, LOTRMod.brick3, 12);

      for(j1 = k + 7; j1 <= k + 8; ++j1) {
         for(i1 = i - 4; i1 <= i + 4; ++i1) {
            this.placeBalconySection(world, i1, j, j1, false, false);
         }

         this.placeBalconySection(world, i - 5, j, j1, true, false);
         this.placeBalconySection(world, i + 5, j, j1, true, false);
      }

      for(j1 = i - 2; j1 <= i + 2; ++j1) {
         this.placeBalconySection(world, j1, j, k + 9, false, false);
      }

      for(j1 = i - 5; j1 <= i + 5; ++j1) {
         if (Math.abs(j1 - i) >= 3) {
            this.placeBalconySection(world, j1, j, k + 9, true, false);
         }
      }

      for(j1 = i - 1; j1 <= i + 1; ++j1) {
         this.placeBalconySection(world, j1, j, k + 10, false, false);
      }

      for(j1 = i - 3; j1 <= i + 3; ++j1) {
         if (Math.abs(j1 - i) >= 2) {
            this.placeBalconySection(world, j1, j, k + 10, true, false);
         }
      }

      for(j1 = i - 2; j1 <= i + 2; ++j1) {
         if (Math.abs(j1 - i) == 0) {
            this.placeBalconySection(world, j1, j, k + 11, true, true);
         } else {
            this.placeBalconySection(world, j1, j, k + 11, true, false);
         }
      }

      this.func_150516_a(world, i, j + 4, k + 6, LOTRMod.slabDouble3, 0);
      this.setAir(world, i, j + 5, k + 6);
      this.setAir(world, i, j + 6, k + 6);
      this.func_150516_a(world, i, j, k + 6, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i, j + 1, k + 6, LOTRMod.doorSpruce, 3);
      this.func_150516_a(world, i, j + 2, k + 6, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i, j + 3, k + 8, LOTRMod.chandelier, 11);

      for(j1 = j + 1; j1 <= j + 2; ++j1) {
         for(i1 = k + 7; i1 <= k + 8; ++i1) {
            this.placeRandomOre(world, random, i - 4, j1, i1);
            this.placeRandomOre(world, random, i - 3, j1, i1);
            this.placeRandomOre(world, random, i + 3, j1, i1);
            this.placeRandomOre(world, random, i + 4, j1, i1);
         }

         this.placeRandomOre(world, random, i - 2, j1, k + 9);
         this.placeRandomOre(world, random, i + 2, j1, k + 9);

         for(i1 = i - 1; i1 <= i + 1; ++i1) {
            this.placeRandomOre(world, random, i1, j1, k + 10);
         }
      }

      this.func_150516_a(world, i, j + 9, k + 3, LOTRMod.commandTable, 0);
   }

   private void generateFacingWest(World world, Random random, int i, int j, int k) {
      int stairX;
      int j1;
      for(stairX = k - 6; stairX <= k + 6; ++stairX) {
         this.func_150516_a(world, i + 7, j + 1, stairX, LOTRMod.slabSingle3, 0);
         this.setGrassToDirt(world, i + 7, j, stairX);

         for(j1 = j; !LOTRMod.isOpaque(world, i + 7, j1, stairX) && j1 >= 0; --j1) {
            this.func_150516_a(world, i + 7, j1, stairX, LOTRMod.pillar, 3);
            this.setGrassToDirt(world, i + 7, j1 - 1, stairX);
         }
      }

      for(stairX = j + 1; stairX <= j + 2; ++stairX) {
         this.setAir(world, i + 6, stairX, k);
         this.func_150516_a(world, i + 7, stairX, k - 1, LOTRMod.pillar, 3);
         this.func_150516_a(world, i + 7, stairX, k + 1, LOTRMod.pillar, 3);
      }

      this.func_150516_a(world, i + 7, j, k, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i + 6, j, k, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i + 7, j + 1, k, LOTRMod.doorSpruce, 2);
      this.func_150516_a(world, i + 7, j + 2, k, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i + 7, j + 3, k - 1, LOTRMod.stairsDwarvenBrick, 2);
      this.func_150516_a(world, i + 7, j + 3, k, LOTRMod.brick3, 12);
      this.func_150516_a(world, i + 7, j + 3, k + 1, LOTRMod.stairsDwarvenBrick, 3);
      this.func_150516_a(world, i + 7, j + 4, k, LOTRMod.slabSingle, 7);
      this.placeWallBanner(world, i + 6, j + 6, k, 3, LOTRItemBanner.BannerType.BLUE_MOUNTAINS);

      int k1;
      for(stairX = j + 1; stairX <= j + 3; ++stairX) {
         for(j1 = i + 4; j1 >= i + 1; --j1) {
            for(k1 = k - 5; k1 <= k - 1; ++k1) {
               this.func_150516_a(world, j1, stairX, k1, LOTRMod.brick, 14);
            }

            for(k1 = k + 1; k1 <= k + 5; ++k1) {
               this.func_150516_a(world, j1, stairX, k1, LOTRMod.brick, 14);
            }
         }

         for(j1 = i + 2; j1 >= i - 5; --j1) {
            this.func_150516_a(world, j1, stairX, k - 1, LOTRMod.brick, 14);
            this.func_150516_a(world, j1, stairX, k + 1, LOTRMod.brick, 14);
         }
      }

      this.func_150516_a(world, i - 3, j + 1, k - 1, LOTRMod.doorSpruce, 1);
      this.func_150516_a(world, i - 3, j + 2, k - 1, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i - 3, j + 1, k + 1, LOTRMod.doorSpruce, 3);
      this.func_150516_a(world, i - 3, j + 2, k + 1, LOTRMod.doorSpruce, 8);

      for(stairX = k - 5; stairX <= k + 2; stairX += 7) {
         this.func_150516_a(world, i - 1, j + 1, stairX, LOTRMod.dwarvenBed, 3);
         this.func_150516_a(world, i, j + 1, stairX, LOTRMod.dwarvenBed, 11);
         this.func_150516_a(world, i - 1, j + 1, stairX + 3, LOTRMod.dwarvenBed, 3);
         this.func_150516_a(world, i, j + 1, stairX + 3, LOTRMod.dwarvenBed, 11);
         this.func_150516_a(world, i, j + 1, stairX + 1, Blocks.field_150486_ae, 0);
         this.func_150516_a(world, i, j + 1, stairX + 2, Blocks.field_150486_ae, 0);
         LOTRChestContents.fillChest(world, random, i, j + 1, stairX + 1, LOTRChestContents.DWARF_HOUSE_LARDER);
         LOTRChestContents.fillChest(world, random, i, j + 1, stairX + 2, LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD);
         this.func_150516_a(world, i - 3, j + 3, stairX + 1, LOTRMod.chandelier, 11);
         this.func_150516_a(world, i - 5, j + 1, stairX, Blocks.field_150344_f, 1);
         this.func_150516_a(world, i - 5, j + 1, stairX + 3, Blocks.field_150344_f, 1);
         this.placeBarrel(world, random, i - 5, j + 2, stairX, 5, LOTRFoods.DWARF_DRINK);
         this.placeBarrel(world, random, i - 5, j + 2, stairX + 3, 5, LOTRFoods.DWARF_DRINK);
         this.func_150516_a(world, i - 5, j + 1, stairX + 1, Blocks.field_150460_al, 0);
         this.setBlockMetadata(world, i - 5, j + 1, stairX + 1, 5);
         this.func_150516_a(world, i - 5, j + 1, stairX + 2, Blocks.field_150460_al, 0);
         this.setBlockMetadata(world, i - 5, j + 1, stairX + 2, 5);
      }

      this.setAir(world, i + 5, j + 4, k);
      stairX = 1;

      for(j1 = j + 1; j1 <= j + 4; ++j1) {
         this.setAir(world, i + 5, j + 4, k - stairX);
         this.setAir(world, i + 5, j + 4, k + stairX);
         this.func_150516_a(world, i + 5, j1, k - stairX, LOTRMod.stairsDwarvenBrick, 3);
         this.func_150516_a(world, i + 5, j1, k + stairX, LOTRMod.stairsDwarvenBrick, 2);

         for(k1 = j1 - 1; k1 > j; --k1) {
            this.func_150516_a(world, i + 5, k1, k - stairX, LOTRMod.brick, 6);
            this.func_150516_a(world, i + 5, k1, k + stairX, LOTRMod.brick, 6);
         }

         ++stairX;
      }

      for(j1 = j + 1; j1 <= j + 3; ++j1) {
         this.func_150516_a(world, i + 5, j1, k - stairX, LOTRMod.brick, 6);
         this.func_150516_a(world, i + 5, j1, k + stairX, LOTRMod.brick, 6);
      }

      for(j1 = k - 5; j1 <= k + 5; j1 += 10) {
         this.func_150516_a(world, i - 2, j + 5, j1, Blocks.field_150344_f, 1);
         this.func_150516_a(world, i - 2, j + 6, j1, Blocks.field_150376_bx, 1);
         this.func_150516_a(world, i + 2, j + 5, j1, Blocks.field_150344_f, 1);
         this.func_150516_a(world, i + 2, j + 6, j1, Blocks.field_150376_bx, 1);
         this.func_150516_a(world, i, j + 5, j1, LOTRMod.blueDwarvenTable, 0);
         this.func_150516_a(world, i - 1, j + 5, j1, LOTRMod.dwarvenForge, 0);
         this.func_150516_a(world, i + 1, j + 5, j1, LOTRMod.dwarvenForge, 0);
      }

      this.func_150516_a(world, i - 6, j + 6, k - 3, LOTRMod.brick3, 12);
      this.func_150516_a(world, i - 6, j + 6, k + 3, LOTRMod.brick3, 12);
      stairX = 4;

      for(j1 = j + 5; j1 <= j + 8; ++j1) {
         this.setAir(world, i + 4, j + 8, k - stairX);
         this.setAir(world, i + 4, j + 8, k + stairX);
         this.func_150516_a(world, i + 4, j1, k - stairX, LOTRMod.stairsDwarvenBrick, 2);
         this.func_150516_a(world, i + 4, j1, k + stairX, LOTRMod.stairsDwarvenBrick, 3);

         for(k1 = j1 - 1; k1 > j + 4; --k1) {
            this.func_150516_a(world, i + 4, k1, k - stairX, LOTRMod.brick, 6);
            this.func_150516_a(world, i + 4, k1, k + stairX, LOTRMod.brick, 6);
         }

         --stairX;
      }

      for(j1 = j + 5; j1 <= j + 7; ++j1) {
         this.func_150516_a(world, i + 4, j1, k, LOTRMod.brick, 6);
      }

      this.func_150516_a(world, i + 4, j + 6, k, LOTRMod.brick3, 12);

      for(j1 = i - 7; j1 >= i - 8; --j1) {
         for(k1 = k - 4; k1 <= k + 4; ++k1) {
            this.placeBalconySection(world, j1, j, k1, false, false);
         }

         this.placeBalconySection(world, j1, j, k - 5, true, false);
         this.placeBalconySection(world, j1, j, k + 5, true, false);
      }

      for(j1 = k - 2; j1 <= k + 2; ++j1) {
         this.placeBalconySection(world, i - 9, j, j1, false, false);
      }

      for(j1 = k - 5; j1 <= k + 5; ++j1) {
         if (Math.abs(j1 - k) >= 3) {
            this.placeBalconySection(world, i - 9, j, j1, true, false);
         }
      }

      for(j1 = k - 1; j1 <= k + 1; ++j1) {
         this.placeBalconySection(world, i - 10, j, j1, false, false);
      }

      for(j1 = k - 3; j1 <= k + 3; ++j1) {
         if (Math.abs(j1 - k) >= 2) {
            this.placeBalconySection(world, i - 10, j, j1, true, false);
         }
      }

      for(j1 = k - 2; j1 <= k + 2; ++j1) {
         if (Math.abs(j1 - k) == 0) {
            this.placeBalconySection(world, i - 11, j, j1, true, true);
         } else {
            this.placeBalconySection(world, i - 11, j, j1, true, false);
         }
      }

      this.func_150516_a(world, i - 6, j + 4, k, LOTRMod.slabDouble3, 0);
      this.setAir(world, i - 6, j + 5, k);
      this.setAir(world, i - 6, j + 6, k);
      this.func_150516_a(world, i - 6, j, k, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i - 6, j + 1, k, LOTRMod.doorSpruce, 0);
      this.func_150516_a(world, i - 6, j + 2, k, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i - 8, j + 3, k, LOTRMod.chandelier, 11);

      for(j1 = j + 1; j1 <= j + 2; ++j1) {
         for(k1 = i - 7; k1 >= i - 8; --k1) {
            this.placeRandomOre(world, random, k1, j1, k - 4);
            this.placeRandomOre(world, random, k1, j1, k - 3);
            this.placeRandomOre(world, random, k1, j1, k + 3);
            this.placeRandomOre(world, random, k1, j1, k + 4);
         }

         this.placeRandomOre(world, random, i - 9, j1, k - 2);
         this.placeRandomOre(world, random, i - 9, j1, k + 2);

         for(k1 = k - 1; k1 <= k + 1; ++k1) {
            this.placeRandomOre(world, random, i - 10, j1, k1);
         }
      }

      this.func_150516_a(world, i - 3, j + 9, k, LOTRMod.commandTable, 0);
   }

   private void generateFacingNorth(World world, Random random, int i, int j, int k) {
      int stairX;
      int j1;
      for(stairX = i - 6; stairX <= i + 6; ++stairX) {
         this.func_150516_a(world, stairX, j + 1, k + 7, LOTRMod.slabSingle3, 0);
         this.setGrassToDirt(world, stairX, j, k + 7);

         for(j1 = j; !LOTRMod.isOpaque(world, stairX, j1, k + 7) && j1 >= 0; --j1) {
            this.func_150516_a(world, stairX, j1, k + 7, LOTRMod.pillar, 3);
            this.setGrassToDirt(world, stairX, j1 - 1, k + 7);
         }
      }

      for(stairX = j + 1; stairX <= j + 2; ++stairX) {
         this.setAir(world, i, stairX, k + 6);
         this.func_150516_a(world, i - 1, stairX, k + 7, LOTRMod.pillar, 3);
         this.func_150516_a(world, i + 1, stairX, k + 7, LOTRMod.pillar, 3);
      }

      this.func_150516_a(world, i, j, k + 7, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i, j, k + 6, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i, j + 1, k + 7, LOTRMod.doorSpruce, 3);
      this.func_150516_a(world, i, j + 2, k + 7, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i - 1, j + 3, k + 7, LOTRMod.stairsDwarvenBrick, 0);
      this.func_150516_a(world, i, j + 3, k + 7, LOTRMod.brick3, 12);
      this.func_150516_a(world, i + 1, j + 3, k + 7, LOTRMod.stairsDwarvenBrick, 1);
      this.func_150516_a(world, i, j + 4, k + 7, LOTRMod.slabSingle, 7);
      this.placeWallBanner(world, i, j + 6, k + 6, 0, LOTRItemBanner.BannerType.BLUE_MOUNTAINS);

      int i1;
      for(stairX = j + 1; stairX <= j + 3; ++stairX) {
         for(j1 = k + 4; j1 >= k + 1; --j1) {
            for(i1 = i - 5; i1 <= i - 1; ++i1) {
               this.func_150516_a(world, i1, stairX, j1, LOTRMod.brick, 14);
            }

            for(i1 = i + 1; i1 <= i + 5; ++i1) {
               this.func_150516_a(world, i1, stairX, j1, LOTRMod.brick, 14);
            }
         }

         for(j1 = k + 2; j1 >= k - 5; --j1) {
            this.func_150516_a(world, i - 1, stairX, j1, LOTRMod.brick, 14);
            this.func_150516_a(world, i + 1, stairX, j1, LOTRMod.brick, 14);
         }
      }

      this.func_150516_a(world, i - 1, j + 1, k - 3, LOTRMod.doorSpruce, 0);
      this.func_150516_a(world, i - 1, j + 2, k - 3, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i + 1, j + 1, k - 3, LOTRMod.doorSpruce, 2);
      this.func_150516_a(world, i + 1, j + 2, k - 3, LOTRMod.doorSpruce, 8);

      for(stairX = i - 5; stairX <= i + 2; stairX += 7) {
         this.func_150516_a(world, stairX, j + 1, k - 1, LOTRMod.dwarvenBed, 0);
         this.func_150516_a(world, stairX, j + 1, k, LOTRMod.dwarvenBed, 8);
         this.func_150516_a(world, stairX + 3, j + 1, k - 1, LOTRMod.dwarvenBed, 0);
         this.func_150516_a(world, stairX + 3, j + 1, k, LOTRMod.dwarvenBed, 8);
         this.func_150516_a(world, stairX + 1, j + 1, k, Blocks.field_150486_ae, 0);
         this.func_150516_a(world, stairX + 2, j + 1, k, Blocks.field_150486_ae, 0);
         LOTRChestContents.fillChest(world, random, stairX + 1, j + 1, k, LOTRChestContents.DWARF_HOUSE_LARDER);
         LOTRChestContents.fillChest(world, random, stairX + 2, j + 1, k, LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD);
         this.func_150516_a(world, stairX + 1, j + 3, k - 3, LOTRMod.chandelier, 11);
         this.func_150516_a(world, stairX, j + 1, k - 5, Blocks.field_150344_f, 1);
         this.func_150516_a(world, stairX + 3, j + 1, k - 5, Blocks.field_150344_f, 1);
         this.placeBarrel(world, random, stairX, j + 2, k - 5, 3, LOTRFoods.DWARF_DRINK);
         this.placeBarrel(world, random, stairX + 3, j + 2, k - 5, 3, LOTRFoods.DWARF_DRINK);
         this.func_150516_a(world, stairX + 1, j + 1, k - 5, Blocks.field_150460_al, 0);
         this.setBlockMetadata(world, stairX + 1, j + 1, k - 5, 3);
         this.func_150516_a(world, stairX + 2, j + 1, k - 5, Blocks.field_150460_al, 0);
         this.setBlockMetadata(world, stairX + 2, j + 1, k - 5, 3);
      }

      this.setAir(world, i, j + 4, k + 5);
      stairX = 1;

      for(j1 = j + 1; j1 <= j + 4; ++j1) {
         this.setAir(world, i - stairX, j + 4, k + 5);
         this.setAir(world, i + stairX, j + 4, k + 5);
         this.func_150516_a(world, i - stairX, j1, k + 5, LOTRMod.stairsDwarvenBrick, 1);
         this.func_150516_a(world, i + stairX, j1, k + 5, LOTRMod.stairsDwarvenBrick, 0);

         for(i1 = j1 - 1; i1 > j; --i1) {
            this.func_150516_a(world, i - stairX, i1, k + 5, LOTRMod.brick, 6);
            this.func_150516_a(world, i + stairX, i1, k + 5, LOTRMod.brick, 6);
         }

         ++stairX;
      }

      for(j1 = j + 1; j1 <= j + 3; ++j1) {
         this.func_150516_a(world, i - stairX, j1, k + 5, LOTRMod.brick, 6);
         this.func_150516_a(world, i + stairX, j1, k + 5, LOTRMod.brick, 6);
      }

      for(j1 = i - 5; j1 <= i + 5; j1 += 10) {
         this.func_150516_a(world, j1, j + 5, k + 2, Blocks.field_150344_f, 1);
         this.func_150516_a(world, j1, j + 6, k + 2, Blocks.field_150376_bx, 1);
         this.func_150516_a(world, j1, j + 5, k - 2, Blocks.field_150344_f, 1);
         this.func_150516_a(world, j1, j + 6, k - 2, Blocks.field_150376_bx, 1);
         this.func_150516_a(world, j1, j + 5, k, LOTRMod.blueDwarvenTable, 0);
         this.func_150516_a(world, j1, j + 5, k + 1, LOTRMod.dwarvenForge, 0);
         this.func_150516_a(world, j1, j + 5, k - 1, LOTRMod.dwarvenForge, 0);
      }

      this.func_150516_a(world, i - 3, j + 6, k - 6, LOTRMod.brick3, 12);
      this.func_150516_a(world, i + 3, j + 6, k - 6, LOTRMod.brick3, 12);
      stairX = 4;

      for(j1 = j + 5; j1 <= j + 8; ++j1) {
         this.setAir(world, i - stairX, j + 8, k + 4);
         this.setAir(world, i + stairX, j + 8, k + 4);
         this.func_150516_a(world, i - stairX, j1, k + 4, LOTRMod.stairsDwarvenBrick, 0);
         this.func_150516_a(world, i + stairX, j1, k + 4, LOTRMod.stairsDwarvenBrick, 1);

         for(i1 = j1 - 1; i1 > j + 4; --i1) {
            this.func_150516_a(world, i - stairX, i1, k + 4, LOTRMod.brick, 6);
            this.func_150516_a(world, i + stairX, i1, k + 4, LOTRMod.brick, 6);
         }

         --stairX;
      }

      for(j1 = j + 5; j1 <= j + 7; ++j1) {
         this.func_150516_a(world, i, j1, k + 4, LOTRMod.brick, 6);
      }

      this.func_150516_a(world, i, j + 6, k + 4, LOTRMod.brick3, 12);

      for(j1 = k - 7; j1 >= k - 8; --j1) {
         for(i1 = i - 4; i1 <= i + 4; ++i1) {
            this.placeBalconySection(world, i1, j, j1, false, false);
         }

         this.placeBalconySection(world, i - 5, j, j1, true, false);
         this.placeBalconySection(world, i + 5, j, j1, true, false);
      }

      for(j1 = i - 2; j1 <= i + 2; ++j1) {
         this.placeBalconySection(world, j1, j, k - 9, false, false);
      }

      for(j1 = i - 5; j1 <= i + 5; ++j1) {
         if (Math.abs(j1 - i) >= 3) {
            this.placeBalconySection(world, j1, j, k - 9, true, false);
         }
      }

      for(j1 = i - 1; j1 <= i + 1; ++j1) {
         this.placeBalconySection(world, j1, j, k - 10, false, false);
      }

      for(j1 = i - 3; j1 <= i + 3; ++j1) {
         if (Math.abs(j1 - i) >= 2) {
            this.placeBalconySection(world, j1, j, k - 10, true, false);
         }
      }

      for(j1 = i - 2; j1 <= i + 2; ++j1) {
         if (Math.abs(j1 - i) == 0) {
            this.placeBalconySection(world, j1, j, k - 11, true, true);
         } else {
            this.placeBalconySection(world, j1, j, k - 11, true, false);
         }
      }

      this.func_150516_a(world, i, j + 4, k - 6, LOTRMod.slabDouble3, 0);
      this.setAir(world, i, j + 5, k - 6);
      this.setAir(world, i, j + 6, k - 6);
      this.func_150516_a(world, i, j, k - 6, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i, j + 1, k - 6, LOTRMod.doorSpruce, 1);
      this.func_150516_a(world, i, j + 2, k - 6, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i, j + 3, k - 8, LOTRMod.chandelier, 11);

      for(j1 = j + 1; j1 <= j + 2; ++j1) {
         for(i1 = k - 7; i1 >= k - 8; --i1) {
            this.placeRandomOre(world, random, i - 4, j1, i1);
            this.placeRandomOre(world, random, i - 3, j1, i1);
            this.placeRandomOre(world, random, i + 3, j1, i1);
            this.placeRandomOre(world, random, i + 4, j1, i1);
         }

         this.placeRandomOre(world, random, i - 2, j1, k - 9);
         this.placeRandomOre(world, random, i + 2, j1, k - 9);

         for(i1 = i - 1; i1 <= i + 1; ++i1) {
            this.placeRandomOre(world, random, i1, j1, k - 10);
         }
      }

      this.func_150516_a(world, i, j + 9, k - 3, LOTRMod.commandTable, 0);
   }

   private void generateFacingEast(World world, Random random, int i, int j, int k) {
      int stairX;
      int j1;
      for(stairX = k - 6; stairX <= k + 6; ++stairX) {
         this.func_150516_a(world, i - 7, j + 1, stairX, LOTRMod.slabSingle3, 0);
         this.setGrassToDirt(world, i - 7, j, stairX);

         for(j1 = j; !LOTRMod.isOpaque(world, i - 7, j1, stairX) && j1 >= 0; --j1) {
            this.func_150516_a(world, i - 7, j1, stairX, LOTRMod.pillar, 3);
            this.setGrassToDirt(world, i - 7, j1 - 1, stairX);
         }
      }

      for(stairX = j + 1; stairX <= j + 2; ++stairX) {
         this.setAir(world, i - 6, stairX, k);
         this.func_150516_a(world, i - 7, stairX, k - 1, LOTRMod.pillar, 3);
         this.func_150516_a(world, i - 7, stairX, k + 1, LOTRMod.pillar, 3);
      }

      this.func_150516_a(world, i - 7, j, k, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i - 6, j, k, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i - 7, j + 1, k, LOTRMod.doorSpruce, 0);
      this.func_150516_a(world, i - 7, j + 2, k, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i - 7, j + 3, k - 1, LOTRMod.stairsDwarvenBrick, 2);
      this.func_150516_a(world, i - 7, j + 3, k, LOTRMod.brick3, 12);
      this.func_150516_a(world, i - 7, j + 3, k + 1, LOTRMod.stairsDwarvenBrick, 3);
      this.func_150516_a(world, i - 7, j + 4, k, LOTRMod.slabSingle, 7);
      this.placeWallBanner(world, i - 6, j + 6, k, 1, LOTRItemBanner.BannerType.BLUE_MOUNTAINS);

      int k1;
      for(stairX = j + 1; stairX <= j + 3; ++stairX) {
         for(j1 = i - 4; j1 <= i - 1; ++j1) {
            for(k1 = k - 5; k1 <= k - 1; ++k1) {
               this.func_150516_a(world, j1, stairX, k1, LOTRMod.brick, 14);
            }

            for(k1 = k + 1; k1 <= k + 5; ++k1) {
               this.func_150516_a(world, j1, stairX, k1, LOTRMod.brick, 14);
            }
         }

         for(j1 = i - 2; j1 <= i + 5; ++j1) {
            this.func_150516_a(world, j1, stairX, k - 1, LOTRMod.brick, 14);
            this.func_150516_a(world, j1, stairX, k + 1, LOTRMod.brick, 14);
         }
      }

      this.func_150516_a(world, i + 3, j + 1, k - 1, LOTRMod.doorSpruce, 1);
      this.func_150516_a(world, i + 3, j + 2, k - 1, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i + 3, j + 1, k + 1, LOTRMod.doorSpruce, 3);
      this.func_150516_a(world, i + 3, j + 2, k + 1, LOTRMod.doorSpruce, 8);

      for(stairX = k - 5; stairX <= k + 2; stairX += 7) {
         this.func_150516_a(world, i + 1, j + 1, stairX, LOTRMod.dwarvenBed, 1);
         this.func_150516_a(world, i, j + 1, stairX, LOTRMod.dwarvenBed, 9);
         this.func_150516_a(world, i + 1, j + 1, stairX + 3, LOTRMod.dwarvenBed, 1);
         this.func_150516_a(world, i, j + 1, stairX + 3, LOTRMod.dwarvenBed, 9);
         this.func_150516_a(world, i, j + 1, stairX + 1, Blocks.field_150486_ae, 0);
         this.func_150516_a(world, i, j + 1, stairX + 2, Blocks.field_150486_ae, 0);
         LOTRChestContents.fillChest(world, random, i, j + 1, stairX + 1, LOTRChestContents.DWARF_HOUSE_LARDER);
         LOTRChestContents.fillChest(world, random, i, j + 1, stairX + 2, LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD);
         this.func_150516_a(world, i + 3, j + 3, stairX + 1, LOTRMod.chandelier, 11);
         this.func_150516_a(world, i + 5, j + 1, stairX, Blocks.field_150344_f, 1);
         this.func_150516_a(world, i + 5, j + 1, stairX + 3, Blocks.field_150344_f, 1);
         this.placeBarrel(world, random, i + 5, j + 2, stairX, 4, LOTRFoods.DWARF_DRINK);
         this.placeBarrel(world, random, i + 5, j + 2, stairX + 3, 4, LOTRFoods.DWARF_DRINK);
         this.func_150516_a(world, i + 5, j + 1, stairX + 1, Blocks.field_150460_al, 0);
         this.setBlockMetadata(world, i + 5, j + 1, stairX + 1, 4);
         this.func_150516_a(world, i + 5, j + 1, stairX + 2, Blocks.field_150460_al, 0);
         this.setBlockMetadata(world, i + 5, j + 1, stairX + 2, 4);
      }

      this.setAir(world, i - 5, j + 4, k);
      stairX = 1;

      for(j1 = j + 1; j1 <= j + 4; ++j1) {
         this.setAir(world, i - 5, j + 4, k - stairX);
         this.setAir(world, i - 5, j + 4, k + stairX);
         this.func_150516_a(world, i - 5, j1, k - stairX, LOTRMod.stairsDwarvenBrick, 3);
         this.func_150516_a(world, i - 5, j1, k + stairX, LOTRMod.stairsDwarvenBrick, 2);

         for(k1 = j1 - 1; k1 > j; --k1) {
            this.func_150516_a(world, i - 5, k1, k - stairX, LOTRMod.brick, 6);
            this.func_150516_a(world, i - 5, k1, k + stairX, LOTRMod.brick, 6);
         }

         ++stairX;
      }

      for(j1 = j + 1; j1 <= j + 3; ++j1) {
         this.func_150516_a(world, i - 5, j1, k - stairX, LOTRMod.brick, 6);
         this.func_150516_a(world, i - 5, j1, k + stairX, LOTRMod.brick, 6);
      }

      for(j1 = k - 5; j1 <= k + 5; j1 += 10) {
         this.func_150516_a(world, i - 2, j + 5, j1, Blocks.field_150344_f, 1);
         this.func_150516_a(world, i - 2, j + 6, j1, Blocks.field_150376_bx, 1);
         this.func_150516_a(world, i + 2, j + 5, j1, Blocks.field_150344_f, 1);
         this.func_150516_a(world, i + 2, j + 6, j1, Blocks.field_150376_bx, 1);
         this.func_150516_a(world, i, j + 5, j1, LOTRMod.blueDwarvenTable, 0);
         this.func_150516_a(world, i - 1, j + 5, j1, LOTRMod.dwarvenForge, 0);
         this.func_150516_a(world, i + 1, j + 5, j1, LOTRMod.dwarvenForge, 0);
      }

      this.func_150516_a(world, i + 6, j + 6, k - 3, LOTRMod.brick3, 12);
      this.func_150516_a(world, i + 6, j + 6, k + 3, LOTRMod.brick3, 12);
      stairX = 4;

      for(j1 = j + 5; j1 <= j + 8; ++j1) {
         this.setAir(world, i - 4, j + 8, k - stairX);
         this.setAir(world, i - 4, j + 8, k + stairX);
         this.func_150516_a(world, i - 4, j1, k - stairX, LOTRMod.stairsDwarvenBrick, 2);
         this.func_150516_a(world, i - 4, j1, k + stairX, LOTRMod.stairsDwarvenBrick, 3);

         for(k1 = j1 - 1; k1 > j + 4; --k1) {
            this.func_150516_a(world, i - 4, k1, k - stairX, LOTRMod.brick, 6);
            this.func_150516_a(world, i - 4, k1, k + stairX, LOTRMod.brick, 6);
         }

         --stairX;
      }

      for(j1 = j + 5; j1 <= j + 7; ++j1) {
         this.func_150516_a(world, i - 4, j1, k, LOTRMod.brick, 6);
      }

      this.func_150516_a(world, i - 4, j + 6, k, LOTRMod.brick3, 12);

      for(j1 = i + 7; j1 <= i + 8; ++j1) {
         for(k1 = k - 4; k1 <= k + 4; ++k1) {
            this.placeBalconySection(world, j1, j, k1, false, false);
         }

         this.placeBalconySection(world, j1, j, k - 5, true, false);
         this.placeBalconySection(world, j1, j, k + 5, true, false);
      }

      for(j1 = k - 2; j1 <= k + 2; ++j1) {
         this.placeBalconySection(world, i + 9, j, j1, false, false);
      }

      for(j1 = k - 5; j1 <= k + 5; ++j1) {
         if (Math.abs(j1 - k) >= 3) {
            this.placeBalconySection(world, i + 9, j, j1, true, false);
         }
      }

      for(j1 = k - 1; j1 <= k + 1; ++j1) {
         this.placeBalconySection(world, i + 10, j, j1, false, false);
      }

      for(j1 = k - 3; j1 <= k + 3; ++j1) {
         if (Math.abs(j1 - k) >= 2) {
            this.placeBalconySection(world, i + 10, j, j1, true, false);
         }
      }

      for(j1 = k - 2; j1 <= k + 2; ++j1) {
         if (Math.abs(j1 - k) == 0) {
            this.placeBalconySection(world, i + 11, j, j1, true, true);
         } else {
            this.placeBalconySection(world, i + 11, j, j1, true, false);
         }
      }

      this.func_150516_a(world, i + 6, j + 4, k, LOTRMod.slabDouble3, 0);
      this.setAir(world, i + 6, j + 5, k);
      this.setAir(world, i + 6, j + 6, k);
      this.func_150516_a(world, i + 6, j, k, Blocks.field_150344_f, 1);
      this.func_150516_a(world, i + 6, j + 1, k, LOTRMod.doorSpruce, 2);
      this.func_150516_a(world, i + 6, j + 2, k, LOTRMod.doorSpruce, 8);
      this.func_150516_a(world, i + 8, j + 3, k, LOTRMod.chandelier, 11);

      for(j1 = j + 1; j1 <= j + 2; ++j1) {
         for(k1 = i + 7; k1 <= i + 8; ++k1) {
            this.placeRandomOre(world, random, k1, j1, k - 4);
            this.placeRandomOre(world, random, k1, j1, k - 3);
            this.placeRandomOre(world, random, k1, j1, k + 3);
            this.placeRandomOre(world, random, k1, j1, k + 4);
         }

         this.placeRandomOre(world, random, i + 9, j1, k - 2);
         this.placeRandomOre(world, random, i + 9, j1, k + 2);

         for(k1 = k - 1; k1 <= k + 1; ++k1) {
            this.placeRandomOre(world, random, i + 10, j1, k1);
         }
      }

      this.func_150516_a(world, i + 3, j + 9, k, LOTRMod.commandTable, 0);
   }

   private void spawnDwarfCommander(World world, int i, int j, int k) {
      LOTREntityDwarf dwarf = new LOTREntityBlueDwarfCommander(world);
      dwarf.func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);
      dwarf.func_110161_a((IEntityLivingData)null);
      dwarf.setPersistentAndTraderShouldRespawn();
      dwarf.func_110171_b(i, j, k, 16);
      world.func_72838_d(dwarf);
   }

   private void spawnDwarf(World world, int i, int j, int k) {
      LOTREntityBlueDwarf dwarf = world.field_73012_v.nextInt(3) == 0 ? new LOTREntityBlueDwarfAxeThrower(world) : new LOTREntityBlueDwarfWarrior(world);
      ((LOTREntityBlueDwarf)dwarf).func_70012_b((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);
      ((LOTREntityBlueDwarf)dwarf).func_110161_a((IEntityLivingData)null);
      ((LOTREntityBlueDwarf)dwarf).isNPCPersistent = true;
      ((LOTREntityBlueDwarf)dwarf).func_110171_b(i, j, k, 16);
      world.func_72838_d((Entity)dwarf);
   }

   private void placeBalconySection(World world, int i, int j, int k, boolean isEdge, boolean isPillar) {
      int j1;
      if (isEdge) {
         for(j1 = j + 4; (j1 >= j || !LOTRMod.isOpaque(world, i, j1, k)) && j1 >= 0; --j1) {
            if (isPillar) {
               this.func_150516_a(world, i, j1, k, LOTRMod.pillar, 3);
            } else {
               this.func_150516_a(world, i, j1, k, LOTRMod.brick, 14);
            }

            this.setGrassToDirt(world, i, j1 - 1, k);
         }

         if (isPillar) {
            this.func_150516_a(world, i, j + 4, k, LOTRMod.brick3, 12);
         }

         this.func_150516_a(world, i, j + 5, k, LOTRMod.brick, 6);
         this.func_150516_a(world, i, j + 6, k, LOTRMod.wall, 14);
      } else {
         for(j1 = j - 1; !LOTRMod.isOpaque(world, i, j1, k) && j1 >= 0; --j1) {
            this.func_150516_a(world, i, j1, k, LOTRMod.brick, 14);
            this.setGrassToDirt(world, i, j1 - 1, k);
         }

         this.func_150516_a(world, i, j, k, Blocks.field_150344_f, 1);

         for(j1 = j + 1; j1 <= j + 6; ++j1) {
            this.setAir(world, i, j1, k);
         }

         this.func_150516_a(world, i, j + 4, k, LOTRMod.slabDouble3, 0);
      }

   }

   private void placeRandomOre(World world, Random random, int i, int j, int k) {
      if (LOTRMod.isOpaque(world, i, j - 1, k) && random.nextBoolean()) {
         int l = random.nextInt(5);
         Block block = null;
         switch(l) {
         case 0:
            block = Blocks.field_150366_p;
            break;
         case 1:
            block = Blocks.field_150352_o;
            break;
         case 2:
            block = LOTRMod.oreCopper;
            break;
         case 3:
            block = LOTRMod.oreTin;
            break;
         case 4:
            block = LOTRMod.oreSilver;
         }

         this.func_150516_a(world, i, j, k, block, 0);
      }
   }
}
