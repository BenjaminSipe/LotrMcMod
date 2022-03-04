package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityDaleArcher;
import lotr.common.entity.npc.LOTREntityDaleCaptain;
import lotr.common.entity.npc.LOTREntityDaleSoldier;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDaleFortress extends LOTRWorldGenDaleStructure {
   public LOTRWorldGenDaleFortress(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 12);
      this.setupRandomBlocks(random);
      int i1;
      int soldiers;
      int l;
      int j1;
      int j1;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         soldiers = 0;
         l = 0;
         j1 = -12;

         while(true) {
            if (j1 > 12) {
               this.originY = this.getY(l);
               break;
            }

            for(j1 = -12; j1 <= 12; ++j1) {
               j1 = this.getTopBlock(world, j1, j1) - 1;
               Block block = this.getBlock(world, j1, j1, j1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > soldiers) {
                  soldiers = j1;
               }

               if (soldiers - i1 > 8) {
                  return false;
               }

               if ((Math.abs(j1) == 12 || Math.abs(j1) == 12) && j1 > l) {
                  l = j1;
               }
            }

            ++j1;
         }
      }

      for(i1 = -11; i1 <= 11; ++i1) {
         for(soldiers = -11; soldiers <= 11; ++soldiers) {
            l = Math.abs(i1);
            j1 = Math.abs(soldiers);
            this.layFoundation(world, i1, soldiers);
            if (l <= 9 && j1 <= 9) {
               for(j1 = 1; j1 <= 8; ++j1) {
                  this.setAir(world, i1, j1, soldiers);
               }

               if (l <= 8 && j1 <= 8 && (l == 8 && j1 >= 4 || j1 == 8 && l >= 4)) {
                  for(j1 = 1; j1 <= 3; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, soldiers, this.brickBlock, this.brickMeta);
                  }
               }

               if (l == 9 && j1 == 2 || j1 == 9 && l == 2) {
                  for(j1 = 1; j1 <= 5; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, soldiers, this.pillarBlock, this.pillarMeta);
                  }
               } else if (l == 9 && (j1 == 3 || j1 == 5 || j1 == 9) || j1 == 9 && (l == 3 || l == 5 || l == 9)) {
                  for(j1 = 1; j1 <= 5; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, soldiers, this.brickBlock, this.brickMeta);
                  }
               } else if (l == 9 || j1 == 9) {
                  for(j1 = 4; j1 <= 5; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, soldiers, this.brickBlock, this.brickMeta);
                  }
               }

               if (l == 9) {
                  this.setBlockAndMetadata(world, i1, 3, -8, this.brickStairBlock, 7);
                  this.setBlockAndMetadata(world, i1, 3, -6, this.brickStairBlock, 6);
                  this.setBlockAndMetadata(world, i1, 3, 6, this.brickStairBlock, 7);
                  this.setBlockAndMetadata(world, i1, 3, 8, this.brickStairBlock, 6);
               }

               if (j1 == 9) {
                  this.setBlockAndMetadata(world, -8, 3, soldiers, this.brickStairBlock, 4);
                  this.setBlockAndMetadata(world, -6, 3, soldiers, this.brickStairBlock, 5);
                  this.setBlockAndMetadata(world, 6, 3, soldiers, this.brickStairBlock, 4);
                  this.setBlockAndMetadata(world, 8, 3, soldiers, this.brickStairBlock, 5);
               }

               if (l == 9 && j1 <= 5 || j1 == 9 && l <= 5) {
                  this.setBlockAndMetadata(world, i1, 6, soldiers, this.brickWallBlock, this.brickWallMeta);
               }

               if (l == 6 && j1 <= 5 || j1 == 6 && l <= 5) {
                  this.setBlockAndMetadata(world, i1, 6, soldiers, this.brickWallBlock, this.brickWallMeta);
                  if ((l != 6 || j1 > 3) && (j1 != 6 || l > 3)) {
                     this.setBlockAndMetadata(world, i1, 5, soldiers, this.brickBlock, this.brickMeta);
                  } else {
                     this.setBlockAndMetadata(world, i1, 5, soldiers, this.brickSlabBlock, this.brickSlabMeta | 8);
                  }

                  if (i1 == -5) {
                     this.setBlockAndMetadata(world, i1, 4, soldiers, this.brickStairBlock, 4);
                  } else if (i1 == 5) {
                     this.setBlockAndMetadata(world, i1, 4, soldiers, this.brickStairBlock, 5);
                  } else if (soldiers == -5) {
                     this.setBlockAndMetadata(world, i1, 4, soldiers, this.brickStairBlock, 7);
                  } else if (soldiers == 5) {
                     this.setBlockAndMetadata(world, i1, 4, soldiers, this.brickStairBlock, 6);
                  }
               }

               if (l <= 8 && j1 <= 8 && (l >= 7 || j1 >= 7)) {
                  if (l > 2 && j1 > 2) {
                     if (i1 == -3) {
                        this.setBlockAndMetadata(world, i1, 4, soldiers, this.plankStairBlock, 4);
                        this.setBlockAndMetadata(world, i1, 5, soldiers, this.plankStairBlock, 1);
                     } else if (i1 == 3) {
                        this.setBlockAndMetadata(world, i1, 4, soldiers, this.plankStairBlock, 5);
                        this.setBlockAndMetadata(world, i1, 5, soldiers, this.plankStairBlock, 0);
                     } else if (soldiers == -3) {
                        this.setBlockAndMetadata(world, i1, 4, soldiers, this.plankStairBlock, 7);
                        this.setBlockAndMetadata(world, i1, 5, soldiers, this.plankStairBlock, 2);
                     } else if (soldiers == 3) {
                        this.setBlockAndMetadata(world, i1, 4, soldiers, this.plankStairBlock, 6);
                        this.setBlockAndMetadata(world, i1, 5, soldiers, this.plankStairBlock, 3);
                     } else {
                        this.setBlockAndMetadata(world, i1, 4, soldiers, this.plankBlock, this.plankMeta);
                     }
                  } else {
                     this.setBlockAndMetadata(world, i1, 5, soldiers, this.plankBlock, this.plankMeta);
                  }
               }

               if (l == 6 && j1 == 6) {
                  for(j1 = 1; j1 <= 5; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, soldiers, this.brickBlock, this.brickMeta);
                  }
               }

               if ((l == 6 || l == 9) && (j1 == 6 || j1 == 9)) {
                  for(j1 = 6; j1 <= 7; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, soldiers, this.brickBlock, this.brickMeta);
                  }
               }

               if (l == 9 && (j1 == 7 || j1 == 8) || j1 == 9 && (l == 7 || l == 8)) {
                  for(j1 = 6; j1 <= 7; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, soldiers, this.barsBlock, 0);
                  }
               }
            }
         }
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         for(soldiers = -1; soldiers <= 1; ++soldiers) {
            this.setBlockAndMetadata(world, soldiers, i1, -9, LOTRMod.gateWooden, 2);
            this.setBlockAndMetadata(world, soldiers, i1, 9, this.brickBlock, this.brickMeta);
         }

         for(soldiers = -1; soldiers <= 1; ++soldiers) {
            this.setBlockAndMetadata(world, -9, i1, soldiers, LOTRMod.gateWooden, 5);
            this.setBlockAndMetadata(world, 9, i1, soldiers, LOTRMod.gateWooden, 4);
         }
      }

      int[] var17 = new int[]{-9, 6};
      soldiers = var17.length;

      for(l = 0; l < soldiers; ++l) {
         j1 = var17[l];
         int[] var21 = new int[]{-9, 6};
         j1 = var21.length;

         for(int var20 = 0; var20 < j1; ++var20) {
            int k1 = var21[var20];

            int k2;
            for(k2 = j1; k2 <= j1 + 3; ++k2) {
               this.setBlockAndMetadata(world, k2, 8, k1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, k2, 8, k1 + 3, this.brickBlock, this.brickMeta);
            }

            for(k2 = k1 + 1; k2 <= k1 + 2; ++k2) {
               this.setBlockAndMetadata(world, j1, 8, k2, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, j1 + 3, 8, k2, this.brickBlock, this.brickMeta);
            }

            for(k2 = j1; k2 <= j1 + 3; ++k2) {
               for(int k2 = k1; k2 <= k1 + 3; ++k2) {
                  this.setBlockAndMetadata(world, k2, 9, k2, this.roofBlock, this.roofMeta);
                  this.setBlockAndMetadata(world, k2, 10, k2, this.roofSlabBlock, this.roofSlabMeta);
               }
            }

            for(k2 = j1 - 1; k2 <= j1 + 4; ++k2) {
               this.setBlockAndMetadata(world, k2, 9, k1 - 1, this.roofStairBlock, 2);
               this.setBlockAndMetadata(world, k2, 9, k1 + 4, this.roofStairBlock, 3);
            }

            for(k2 = k1; k2 <= k1 + 3; ++k2) {
               this.setBlockAndMetadata(world, j1 - 1, 9, k2, this.roofStairBlock, 1);
               this.setBlockAndMetadata(world, j1 + 4, 9, k2, this.roofStairBlock, 0);
            }

            this.setBlockAndMetadata(world, j1, 8, k1 - 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, j1 + 3, 8, k1 - 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, j1 + 4, 8, k1, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, j1 + 4, 8, k1 + 3, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, j1 + 3, 8, k1 + 4, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, j1, 8, k1 + 4, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, j1 - 1, 8, k1 + 3, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, j1 - 1, 8, k1, this.roofStairBlock, 5);
         }
      }

      this.setBlockAndMetadata(world, -6, 8, -8, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -6, 8, -7, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -7, 8, -6, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -8, 8, -6, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 6, 8, -8, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 6, 8, -7, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 7, 8, -6, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 8, 8, -6, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -6, 8, 8, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -6, 8, 7, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -7, 8, 6, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -8, 8, 6, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 6, 8, 8, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 6, 8, 7, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 7, 8, 6, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 8, 8, 6, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -8, 8, -8, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 8, 8, -8, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -8, 8, 8, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 8, 8, 8, Blocks.field_150478_aa, 1);
      var17 = new int[]{-2, 2};
      soldiers = var17.length;

      for(l = 0; l < soldiers; ++l) {
         j1 = var17[l];

         for(j1 = 6; j1 <= 8; ++j1) {
            this.setBlockAndMetadata(world, j1, j1, -9, this.pillarBlock, this.pillarMeta);
         }

         this.setBlockAndMetadata(world, j1, 9, -9, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, j1, 5, -10, Blocks.field_150478_aa, 4);
         this.placeWallBanner(world, j1, 8, -9, LOTRItemBanner.BannerType.DALE, 2);
      }

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -7, i1, -7, Blocks.field_150468_ap, 4);
         this.setBlockAndMetadata(world, 7, i1, -7, Blocks.field_150468_ap, 5);
      }

      this.setBlockAndMetadata(world, 0, 4, -8, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 0, 4, 8, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -8, 4, 0, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 8, 4, 0, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -6, 3, -5, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -5, 3, -6, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 6, 3, -5, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 5, 3, -6, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -6, 3, 5, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -5, 3, 6, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 6, 3, 5, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 5, 3, 6, Blocks.field_150478_aa, 1);

      for(i1 = -8; i1 <= -4; ++i1) {
         this.setBlockAndMetadata(world, -3, 0, i1, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, -2, 0, i1, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, 2, 0, i1, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, 3, 0, i1, Blocks.field_150349_c, 0);
      }

      for(i1 = 4; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, -3, 0, i1, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, -2, 0, i1, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, 2, 0, i1, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, 3, 0, i1, Blocks.field_150349_c, 0);
      }

      for(i1 = -8; i1 <= -4; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, -3, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, i1, 0, -2, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, i1, 0, 2, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, i1, 0, 3, Blocks.field_150349_c, 0);
      }

      for(i1 = 4; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, -3, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, i1, 0, -2, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, i1, 0, 2, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, i1, 0, 3, Blocks.field_150349_c, 0);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(soldiers = -1; soldiers <= 1; ++soldiers) {
            l = Math.abs(i1);
            j1 = Math.abs(soldiers);
            this.setBlockAndMetadata(world, i1, 1, soldiers, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 2, soldiers, this.brickBlock, this.brickMeta);
            if (l == 1 && j1 == 1) {
               this.setBlockAndMetadata(world, i1, 3, soldiers, this.brickWallBlock, this.brickWallMeta);
            } else if (l == 1 || j1 == 1) {
               this.setBlockAndMetadata(world, i1, 3, soldiers, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1, 4, soldiers, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1, 5, soldiers, this.brickWallBlock, this.brickWallMeta);
            }

            if (i1 == 0 && soldiers == 0) {
               this.setBlockAndMetadata(world, i1, 3, soldiers, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1, 4, soldiers, LOTRMod.hearth, 0);
               this.setBlockAndMetadata(world, i1, 5, soldiers, Blocks.field_150480_ab, 0);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 6, 0, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -1, 6, 0, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 1, 6, 0, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 0, 6, -1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 0, 6, 1, this.roofStairBlock, 3);

      for(i1 = -7; i1 <= -4; ++i1) {
         for(soldiers = -7; soldiers <= -4; ++soldiers) {
            this.setBlockAndMetadata(world, soldiers, 0, i1, LOTRMod.dirtPath, 0);
         }

         for(soldiers = 4; soldiers <= 7; ++soldiers) {
            this.setBlockAndMetadata(world, soldiers, 0, i1, LOTRMod.dirtPath, 0);
         }
      }

      this.setBlockAndMetadata(world, -4, 1, -5, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, -5, 1, -4, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 5, 1, -4, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 4, 1, -5, this.brickWallBlock, this.brickWallMeta);

      for(i1 = 4; i1 <= 7; ++i1) {
         for(soldiers = -7; soldiers <= -4; ++soldiers) {
            this.setBlockAndMetadata(world, soldiers, 0, i1, this.plankBlock, this.plankMeta);
         }

         for(soldiers = 4; soldiers <= 7; ++soldiers) {
            this.setBlockAndMetadata(world, soldiers, 0, i1, this.plankBlock, this.plankMeta);
         }
      }

      for(i1 = 4; i1 <= 6; ++i1) {
         for(soldiers = -6; soldiers <= -4; ++soldiers) {
            this.setBlockAndMetadata(world, soldiers, 4, i1, Blocks.field_150325_L, 11);
         }

         for(soldiers = 4; soldiers <= 6; ++soldiers) {
            this.setBlockAndMetadata(world, soldiers, 4, i1, Blocks.field_150325_L, 11);
         }
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -4, i1, 4, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 4, i1, 4, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -4, 0, -4, Blocks.field_150349_c, 0);
      this.setBlockAndMetadata(world, -3, 0, -3, Blocks.field_150349_c, 0);
      this.setBlockAndMetadata(world, 4, 0, -4, Blocks.field_150349_c, 0);
      this.setBlockAndMetadata(world, 3, 0, -3, Blocks.field_150349_c, 0);
      this.setBlockAndMetadata(world, -4, 0, 4, Blocks.field_150349_c, 0);
      this.setBlockAndMetadata(world, -3, 0, 3, Blocks.field_150349_c, 0);
      this.setBlockAndMetadata(world, 4, 0, 4, Blocks.field_150349_c, 0);
      this.setBlockAndMetadata(world, 3, 0, 3, Blocks.field_150349_c, 0);

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -7, i1, 7, this.plankBlock, this.plankMeta);
      }

      this.setBlockAndMetadata(world, -7, 3, 6, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, -6, 3, 7, this.plankStairBlock, 4);
      var17 = new int[]{1, 2};
      soldiers = var17.length;

      for(l = 0; l < soldiers; ++l) {
         j1 = var17[l];
         this.setBlockAndMetadata(world, -7, j1, 5, LOTRMod.strawBed, 0);
         this.setBlockAndMetadata(world, -7, j1, 6, LOTRMod.strawBed, 8);
         this.setBlockAndMetadata(world, -5, j1, 7, LOTRMod.strawBed, 3);
         this.setBlockAndMetadata(world, -6, j1, 7, LOTRMod.strawBed, 11);
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, 7, i1, 7, this.plankBlock, this.plankMeta);
      }

      this.setBlockAndMetadata(world, 7, 3, 6, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, 6, 3, 7, this.plankStairBlock, 5);

      for(i1 = 4; i1 <= 6; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 7, this.plankBlock, this.plankMeta);
         if (i1 <= 5) {
            this.placeBarrel(world, random, i1, 2, 7, 2, LOTRFoods.DALE_DRINK);
         }
      }

      for(i1 = 4; i1 <= 6; ++i1) {
         this.setBlockAndMetadata(world, 7, 1, i1, this.plankBlock, this.plankMeta);
         if (i1 <= 5) {
            this.placeBarrel(world, random, 7, 2, i1, 5, LOTRFoods.DALE_DRINK);
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, 8, this.floorBlock, this.floorMeta);
      }

      this.setBlockAndMetadata(world, -3, 1, 8, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -2, 1, 8, this.plankBlock, this.plankMeta);
      this.placeChest(world, random, -3, 2, 8, 2, LOTRChestContents.DALE_WATCHTOWER);
      this.placeChest(world, random, -2, 2, 8, 2, LOTRChestContents.DALE_WATCHTOWER);
      this.setBlockAndMetadata(world, 2, 1, 8, LOTRMod.daleTable, 0);
      this.setBlockAndMetadata(world, 3, 1, 8, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 0, 1, 7, LOTRMod.commandTable, 0);
      this.setBlockAndMetadata(world, 6, 1, 6, Blocks.field_150460_al, 2);
      LOTREntityDaleCaptain captain = new LOTREntityDaleCaptain(world);
      captain.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(captain, world, 0, 1, 3, 16);
      soldiers = 3 + random.nextInt(4);

      for(l = 0; l < soldiers; ++l) {
         LOTREntityDaleSoldier soldier = random.nextInt(3) == 0 ? new LOTREntityDaleArcher(world) : new LOTREntityDaleSoldier(world);
         ((LOTREntityDaleSoldier)soldier).spawnRidingHorse = false;
         this.spawnNPCAndSetHome((EntityCreature)soldier, world, 0, 1, 3, 16);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityDaleSoldier.class, LOTREntityDaleArcher.class);
      respawner.setCheckRanges(16, -6, 10, 12);
      respawner.setSpawnRanges(10, -2, 2, 16);
      this.placeNPCRespawner(respawner, world, 0, 0, 0);
      return true;
   }

   private void layFoundation(World world, int i, int k) {
      for(int j = 0; j == 0 || !this.isOpaque(world, i, j, k) && this.getY(j) >= 0; --j) {
         this.setBlockAndMetadata(world, i, j, k, this.floorBlock, this.floorMeta);
         this.setGrassToDirt(world, i, j - 1, k);
      }

   }
}
