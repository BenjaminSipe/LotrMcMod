package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityEasterling;
import lotr.common.entity.npc.LOTREntityEasterlingArcher;
import lotr.common.entity.npc.LOTREntityEasterlingBlacksmith;
import lotr.common.entity.npc.LOTREntityEasterlingGoldWarrior;
import lotr.common.entity.npc.LOTREntityEasterlingWarlord;
import lotr.common.entity.npc.LOTREntityEasterlingWarrior;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingFortress extends LOTRWorldGenEasterlingStructureTown {
   public LOTRWorldGenEasterlingFortress(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.bedBlock = LOTRMod.strawBed;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 13);
      this.setupRandomBlocks(random);
      int k1;
      int i1;
      int k1;
      int h;
      int j1;
      if (this.restrictions) {
         k1 = 0;
         i1 = 0;

         for(k1 = -12; k1 <= 12; ++k1) {
            for(h = -12; h <= 12; ++h) {
               j1 = this.getTopBlock(world, k1, h) - 1;
               if (!this.isSurface(world, k1, j1, h)) {
                  return false;
               }

               if (j1 < k1) {
                  k1 = j1;
               }

               if (j1 > i1) {
                  i1 = j1;
               }

               if (i1 - k1 > 12) {
                  return false;
               }
            }
         }
      }

      for(k1 = -12; k1 <= 12; ++k1) {
         for(i1 = -12; i1 <= 12; ++i1) {
            k1 = Math.abs(k1);
            h = Math.abs(i1);

            for(j1 = 1; j1 <= 10; ++j1) {
               this.setAir(world, k1, j1, i1);
            }

            if (k1 <= 9 && h <= 9) {
               for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, k1, j1, i1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, k1, j1, i1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, k1, j1 - 1, i1);
               }

               j1 = random.nextInt(3);
               if (j1 == 0) {
                  this.setBlockAndMetadata(world, k1, 0, i1, Blocks.field_150349_c, 0);
               } else if (j1 == 1) {
                  this.setBlockAndMetadata(world, k1, 0, i1, Blocks.field_150346_d, 1);
               } else if (j1 == 2) {
                  this.setBlockAndMetadata(world, k1, 0, i1, LOTRMod.dirtPath, 0);
               }

               if (random.nextInt(3) == 0) {
                  this.setBlockAndMetadata(world, k1, 1, i1, LOTRMod.thatchFloor, 0);
               }
            }

            if (k1 != 12 && k1 != 9 || h != 12 && h != 9) {
               if (k1 == 3 && (h == 9 || h == 12) || h == 3 && (k1 == 9 || k1 == 12)) {
                  for(j1 = 7; (j1 >= 0 || !this.isOpaque(world, k1, j1, i1)) && this.getY(j1) >= 0; --j1) {
                     this.setBlockAndMetadata(world, k1, j1, i1, this.pillarBlock, this.pillarMeta);
                     this.setGrassToDirt(world, k1, j1 - 1, i1);
                  }

                  if (k1 == 12 || h == 12) {
                     this.setBlockAndMetadata(world, k1, 8, i1, this.brickWallBlock, this.brickWallMeta);
                     this.setBlockAndMetadata(world, k1, 9, i1, Blocks.field_150478_aa, 5);
                  }
               } else {
                  if (k1 >= 10 || h >= 10) {
                     for(j1 = 4; (j1 >= 0 || !this.isOpaque(world, k1, j1, i1)) && this.getY(j1) >= 0; --j1) {
                        this.setBlockAndMetadata(world, k1, j1, i1, this.brickBlock, this.brickMeta);
                        this.setGrassToDirt(world, k1, j1 - 1, i1);
                     }

                     this.setBlockAndMetadata(world, k1, 5, i1, this.brickGoldBlock, this.brickGoldMeta);
                  }

                  if (k1 <= 11 && h <= 11 && (k1 >= 10 || h >= 10)) {
                     this.setBlockAndMetadata(world, k1, 5, i1, this.pillarBlock, this.pillarMeta);
                  }

                  if (k1 <= 12 && h <= 12 && (k1 == 12 || h == 12) || k1 <= 9 && h <= 9 && (k1 == 9 || h == 9)) {
                     this.setBlockAndMetadata(world, k1, 6, i1, this.brickRedWallBlock, this.brickRedWallMeta);
                  }

                  if (k1 == -9 && h <= 8) {
                     this.setBlockAndMetadata(world, k1, 5, i1, this.brickStairBlock, 4);
                  }

                  if (k1 == 9 && h <= 8) {
                     this.setBlockAndMetadata(world, k1, 5, i1, this.brickStairBlock, 5);
                  }

                  if (i1 == -9 && k1 <= 8) {
                     this.setBlockAndMetadata(world, k1, 5, i1, this.brickStairBlock, 7);
                  }

                  if (i1 == 9 && k1 <= 8) {
                     this.setBlockAndMetadata(world, k1, 5, i1, this.brickStairBlock, 6);
                  }
               }
            } else {
               for(j1 = 8; (j1 >= 0 || !this.isOpaque(world, k1, j1, i1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, k1, j1, i1, this.pillarBlock, this.pillarMeta);
                  this.setGrassToDirt(world, k1, j1 - 1, i1);
               }
            }
         }
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         int k1 = -13;

         for(k1 = 0; (k1 >= 0 || !this.isOpaque(world, k1, k1, k1)) && this.getY(k1) >= 0; --k1) {
            this.setBlockAndMetadata(world, k1, k1, k1, this.pillarBlock, this.pillarMeta);
            this.setGrassToDirt(world, k1, k1 - 1, k1);
         }
      }

      int[] var17 = new int[]{-6, 0, 6};
      i1 = var17.length;

      for(k1 = 0; k1 < i1; ++k1) {
         h = var17[k1];
         if (h != 0) {
            this.setBlockAndMetadata(world, h - 1, 0, -12, this.pillarBlock, this.pillarMeta);
            this.setBlockAndMetadata(world, h, 0, -12, this.pillarRedBlock, this.pillarRedMeta);
            this.setBlockAndMetadata(world, h + 1, 0, -12, this.pillarBlock, this.pillarMeta);

            for(j1 = h - 1; j1 <= h + 1; ++j1) {
               this.setBlockAndMetadata(world, j1, 1, -12, this.barsBlock, 0);
               this.setBlockAndMetadata(world, j1, 2, -12, this.barsBlock, 0);
            }

            this.setBlockAndMetadata(world, h - 1, 3, -12, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, h, 3, -12, this.barsBlock, 0);
            this.setBlockAndMetadata(world, h + 1, 3, -12, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, h, 4, -12, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, h, 0, -11, LOTRMod.hearth, 0);
            this.setBlockAndMetadata(world, h, 1, -11, Blocks.field_150480_ab, 0);
            this.setBlockAndMetadata(world, h, 2, -11, this.brickStairBlock, 6);
         }

         this.setBlockAndMetadata(world, h - 1, 0, 12, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, h, 0, 12, this.pillarRedBlock, this.pillarRedMeta);
         this.setBlockAndMetadata(world, h + 1, 0, 12, this.pillarBlock, this.pillarMeta);

         for(j1 = h - 1; j1 <= h + 1; ++j1) {
            this.setAir(world, j1, 1, 12);
            this.setAir(world, j1, 2, 12);
         }

         this.setBlockAndMetadata(world, h - 1, 3, 12, this.brickStairBlock, 4);
         this.setAir(world, h, 3, 12);
         this.setBlockAndMetadata(world, h + 1, 3, 12, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, h, 4, 12, this.brickStairBlock, 7);
      }

      var17 = new int[]{-6, 0, 6};
      i1 = var17.length;

      for(k1 = 0; k1 < i1; ++k1) {
         h = var17[k1];
         this.setBlockAndMetadata(world, -12, 0, h - 1, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, -12, 0, h, this.pillarRedBlock, this.pillarRedMeta);
         this.setBlockAndMetadata(world, -12, 0, h + 1, this.pillarBlock, this.pillarMeta);

         for(j1 = h - 1; j1 <= h + 1; ++j1) {
            this.setAir(world, -12, 1, j1);
            this.setAir(world, -12, 2, j1);
         }

         this.setBlockAndMetadata(world, -12, 3, h - 1, this.brickStairBlock, 7);
         this.setAir(world, -12, 3, h);
         this.setBlockAndMetadata(world, -12, 3, h + 1, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, -12, 4, h, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, 12, 0, h - 1, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, 12, 0, h, this.pillarRedBlock, this.pillarRedMeta);
         this.setBlockAndMetadata(world, 12, 0, h + 1, this.pillarBlock, this.pillarMeta);

         for(j1 = h - 1; j1 <= h + 1; ++j1) {
            this.setAir(world, 12, 1, j1);
            this.setAir(world, 12, 2, j1);
         }

         this.setBlockAndMetadata(world, 12, 3, h - 1, this.brickStairBlock, 7);
         this.setAir(world, 12, 3, h);
         this.setBlockAndMetadata(world, 12, 3, h + 1, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, 12, 4, h, this.brickStairBlock, 4);
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         for(i1 = 1; i1 <= 3; ++i1) {
            this.setAir(world, k1, i1, -12);
            this.setBlockAndMetadata(world, k1, i1, -11, this.gateBlock, 2);
            this.setAir(world, k1, i1, -10);
         }
      }

      this.setBlockAndMetadata(world, -1, 4, -12, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 4, -12, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 1, 4, -12, this.brickStairBlock, 5);

      for(k1 = -1; k1 <= 1; ++k1) {
         this.setBlockAndMetadata(world, k1, 6, -12, this.brickGoldBlock, this.brickGoldMeta);
      }

      this.setBlockAndMetadata(world, -2, 6, -12, this.brickCarvedBlock, this.brickCarvedMeta);
      this.setBlockAndMetadata(world, 2, 6, -12, this.brickCarvedBlock, this.brickCarvedMeta);
      this.setBlockAndMetadata(world, -2, 7, -12, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, -1, 7, -12, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 0, 7, -12, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 8, -12, this.brickSlabBlock, this.brickSlabMeta);
      this.setBlockAndMetadata(world, 1, 7, -12, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 2, 7, -12, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, -1, 4, -10, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 4, -10, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, 1, 4, -10, this.brickStairBlock, 5);
      var17 = new int[]{-3, 3};
      i1 = var17.length;

      for(k1 = 0; k1 < i1; ++k1) {
         h = var17[k1];
         this.placeWallBanner(world, h, 4, -12, this.bannerType, 2);
         this.placeWallBanner(world, h, 4, 12, this.bannerType, 0);
      }

      var17 = new int[]{-3, 3};
      i1 = var17.length;

      for(k1 = 0; k1 < i1; ++k1) {
         h = var17[k1];
         this.placeWallBanner(world, -12, 4, h, this.bannerType, 3);
         this.placeWallBanner(world, 12, 4, h, this.bannerType, 1);
      }

      this.placeWallBanner(world, 0, 6, -12, this.bannerType, 2);
      var17 = new int[]{-12, 9};
      i1 = var17.length;

      int l;
      int j1;
      int k1;
      int i2;
      int j2;
      for(k1 = 0; k1 < i1; ++k1) {
         h = var17[k1];
         int[] var21 = new int[]{-12, 9};
         l = var21.length;

         for(j1 = 0; j1 < l; ++j1) {
            k1 = var21[j1];
            this.setBlockAndMetadata(world, h + 1, 8, k1, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, h + 2, 8, k1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, h + 1, 8, k1 + 3, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, h + 2, 8, k1 + 3, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, h, 8, k1 + 1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, h, 8, k1 + 2, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, h + 3, 8, k1 + 1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, h + 3, 8, k1 + 2, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, h, 9, k1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, h + 3, 9, k1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, h, 9, k1 + 3, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, h + 3, 9, k1 + 3, this.brickWallBlock, this.brickWallMeta);

            for(i2 = h + 1; i2 <= h + 2; ++i2) {
               for(j2 = k1 + 1; j2 <= k1 + 2; ++j2) {
                  this.setBlockAndMetadata(world, i2, 10, j2, this.roofBlock, this.roofMeta);
               }
            }

            for(i2 = h; i2 <= h + 3; ++i2) {
               this.setBlockAndMetadata(world, i2, 10, k1, this.roofStairBlock, 2);
               this.setBlockAndMetadata(world, i2, 10, k1 + 3, this.roofStairBlock, 3);
            }

            for(i2 = k1 + 1; i2 <= k1 + 2; ++i2) {
               this.setBlockAndMetadata(world, h, 10, i2, this.roofStairBlock, 1);
               this.setBlockAndMetadata(world, h + 3, 10, i2, this.roofStairBlock, 0);
            }

            if (k1 == -12) {
               this.setBlockAndMetadata(world, h + 1, 6, k1, this.brickStairBlock, 0);
               this.setBlockAndMetadata(world, h + 2, 6, k1, this.brickStairBlock, 1);
            }

            if (k1 == 9) {
               this.setBlockAndMetadata(world, h + 1, 6, k1 + 3, this.brickStairBlock, 0);
               this.setBlockAndMetadata(world, h + 2, 6, k1 + 3, this.brickStairBlock, 1);
            }

            if (h == -12) {
               this.setBlockAndMetadata(world, h, 6, k1 + 1, this.brickStairBlock, 3);
               this.setBlockAndMetadata(world, h, 6, k1 + 2, this.brickStairBlock, 2);
            }

            if (h == 9) {
               this.setBlockAndMetadata(world, h + 3, 6, k1 + 1, this.brickStairBlock, 3);
               this.setBlockAndMetadata(world, h + 3, 6, k1 + 2, this.brickStairBlock, 2);
            }
         }
      }

      this.setBlockAndMetadata(world, -9, 7, -10, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -10, 7, -9, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 9, 7, -10, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 10, 7, -9, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -9, 7, 10, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -10, 7, 9, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 9, 7, 10, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 10, 7, 9, Blocks.field_150478_aa, 2);

      for(k1 = -1; k1 <= 1; ++k1) {
         for(i1 = -12; i1 <= -4; ++i1) {
            if (k1 == 0) {
               this.setBlockAndMetadata(world, k1, 0, i1, this.pillarRedBlock, this.pillarRedMeta);
            } else {
               this.setBlockAndMetadata(world, k1, 0, i1, this.pillarBlock, this.pillarMeta);
            }
         }
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         for(i1 = -9; i1 <= 9; ++i1) {
            if (i1 <= -4 || i1 >= 4) {
               if (k1 == 0) {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.pillarRedBlock, this.pillarRedMeta);
               } else {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.pillarBlock, this.pillarMeta);
               }
            }
         }
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         for(i1 = -3; i1 <= 3; ++i1) {
            k1 = Math.abs(k1);
            h = Math.abs(i1);
            if (k1 == 3 && h == 3) {
               for(j1 = 0; j1 <= 5; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, i1, this.pillarBlock, this.pillarMeta);
               }
            } else if (k1 != 3 && h != 3) {
               this.setBlockAndMetadata(world, k1, 0, i1, this.plankBlock, this.plankMeta);
            } else {
               for(j1 = 0; j1 <= 5; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, i1, this.brickBlock, this.brickMeta);
               }
            }

            if (k1 <= 2 && h <= 2) {
               if (k1 == 2 && h == 2) {
                  for(j1 = 6; j1 <= 11; ++j1) {
                     this.setBlockAndMetadata(world, k1, j1, i1, this.pillarBlock, this.pillarMeta);
                  }
               } else if (k1 != 2 && h != 2) {
                  this.setBlockAndMetadata(world, k1, 6, i1, this.pillarBlock, this.pillarMeta);
               } else {
                  for(j1 = 6; j1 <= 11; ++j1) {
                     if (j1 == 11) {
                        this.setBlockAndMetadata(world, k1, j1, i1, this.brickGoldBlock, this.brickGoldMeta);
                     } else {
                        this.setBlockAndMetadata(world, k1, j1, i1, this.brickBlock, this.brickMeta);
                     }
                  }
               }
            }
         }
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, k1, 6, -3, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, k1, 6, 3, this.roofStairBlock, 3);
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, -3, 6, k1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 3, 6, k1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -4, 6, -4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -3, 5, -4, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -2, 5, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -1, 5, -4, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 6, -4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 1, 5, -4, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 2, 5, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 3, 5, -4, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 4, 6, -4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 4, 5, -3, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 4, 5, -2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, 5, -1, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 4, 6, 0, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 4, 5, 1, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 4, 5, 2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, 5, 3, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 4, 6, 4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 3, 5, 4, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 2, 5, 4, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 1, 5, 4, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 0, 6, 4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -1, 5, 4, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -2, 5, 4, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -3, 5, 4, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -4, 6, 4, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -4, 5, 3, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -4, 5, 2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -4, 5, 1, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -4, 6, 0, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -4, 5, -1, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -4, 5, -2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -4, 5, -3, this.roofStairBlock, 6);

      for(k1 = -2; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, k1, 12, -2, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, k1, 12, 2, this.roofStairBlock, 3);
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         this.setBlockAndMetadata(world, -2, 12, k1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 2, 12, k1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -3, 12, -3, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -2, 11, -3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -1, 11, -3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 12, -3, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 1, 11, -3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 2, 11, -3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 3, 12, -3, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 3, 11, -2, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 3, 11, -1, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 3, 12, 0, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 3, 11, 1, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 3, 11, 2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 3, 12, 3, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 2, 11, 3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 11, 3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 0, 12, 3, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -1, 11, 3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -2, 11, 3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -3, 12, 3, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -3, 11, 2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -3, 11, 1, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -3, 12, 0, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -3, 11, -1, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -3, 11, -2, this.roofStairBlock, 6);

      for(k1 = -1; k1 <= 1; ++k1) {
         this.setBlockAndMetadata(world, k1, 12, -1, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, k1, 12, 1, this.roofStairBlock, 6);
      }

      this.setBlockAndMetadata(world, -1, 12, 0, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 12, 0, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -1, 13, -1, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 0, 13, -1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 1, 13, -1, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 1, 13, 0, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 1, 13, 1, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 0, 13, 1, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -1, 13, 1, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -1, 13, 0, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 0, 13, 0, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 0, 14, 0, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 0, 15, 0, this.roofWallBlock, this.roofWallMeta);
      this.setBlockAndMetadata(world, 0, 16, 0, this.roofWallBlock, this.roofWallMeta);
      this.setBlockAndMetadata(world, -3, 4, -4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 3, 4, -4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -3, 4, 4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 3, 4, 4, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -4, 4, -3, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -4, 4, 3, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 4, 4, -3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 4, 4, 3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -2, 10, -3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 10, -3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -2, 10, 3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 10, 3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -3, 10, -2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -3, 10, 2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 3, 10, -2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, 10, 2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 0, 1, -3, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -3, this.doorBlock, 8);
      this.setBlockAndMetadata(world, -3, 1, 0, this.doorBlock, 2);
      this.setBlockAndMetadata(world, -3, 2, 0, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 3, 1, 0, this.doorBlock, 0);
      this.setBlockAndMetadata(world, 3, 2, 0, this.doorBlock, 8);

      for(k1 = -3; k1 <= 0; ++k1) {
         this.setBlockAndMetadata(world, 0, 0, k1, this.pillarRedBlock, this.pillarRedMeta);
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, k1, 0, 0, this.pillarRedBlock, this.pillarRedMeta);
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, k1, 5, -2, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, k1, 5, 2, this.brickStairBlock, 6);
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, -2, 5, k1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 2, 5, k1, this.brickStairBlock, 5);
      }

      for(k1 = 1; k1 <= 6; ++k1) {
         this.setBlockAndMetadata(world, 0, k1, 1, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, 0, k1, 0, Blocks.field_150468_ap, 2);
         if (k1 <= 5) {
            this.setBlockAndMetadata(world, -1, k1, 1, Blocks.field_150468_ap, 5);
            this.setBlockAndMetadata(world, 1, k1, 1, Blocks.field_150468_ap, 4);
         }
      }

      for(k1 = 1; k1 <= 5; ++k1) {
         this.setBlockAndMetadata(world, 0, k1, 2, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, -2, 2, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, -1, 2, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 1, 2, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.setBlockAndMetadata(world, 2, 2, 2, this.plankSlabBlock, this.plankSlabMeta | 8);
      var17 = new int[]{1, 3};
      i1 = var17.length;

      for(k1 = 0; k1 < i1; ++k1) {
         h = var17[k1];
         this.setBlockAndMetadata(world, -2, h, 2, this.bedBlock, 1);
         this.setBlockAndMetadata(world, -1, h, 2, this.bedBlock, 9);
         this.setBlockAndMetadata(world, 2, h, 2, this.bedBlock, 3);
         this.setBlockAndMetadata(world, 1, h, 2, this.bedBlock, 11);
      }

      this.setBlockAndMetadata(world, -2, 4, -2, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 4, -2, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 4, 2, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 4, 2, Blocks.field_150478_aa, 4);
      this.placeArmorStand(world, -2, 1, -2, 2, (ItemStack[])null);
      this.placeArmorStand(world, 2, 1, -2, 2, (ItemStack[])null);
      this.placeWeaponRack(world, 0, 3, -2, 4, this.getEasterlingWeaponItem(random));
      this.placeWeaponRack(world, -2, 3, 0, 5, this.getEasterlingWeaponItem(random));
      this.placeWeaponRack(world, 2, 3, 0, 7, this.getEasterlingWeaponItem(random));

      for(k1 = 8; k1 <= 9; ++k1) {
         this.setBlockAndMetadata(world, 0, k1, -2, this.barsBlock, 0);
         this.setBlockAndMetadata(world, 0, k1, 2, this.barsBlock, 0);
         this.setBlockAndMetadata(world, -2, k1, 0, this.barsBlock, 0);
         this.setBlockAndMetadata(world, 2, k1, 0, this.barsBlock, 0);
      }

      this.setBlockAndMetadata(world, -1, 11, -1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 1, 11, -1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -1, 11, 1, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 1, 11, 1, Blocks.field_150478_aa, 4);

      for(k1 = -9; k1 <= -6; ++k1) {
         for(i1 = -9; i1 <= -6; ++i1) {
            this.setBlockAndMetadata(world, k1, 0, i1, this.pillarBlock, this.pillarMeta);
         }
      }

      for(k1 = 1; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, -6, k1, -6, this.woodBeamBlock, this.woodBeamMeta);
      }

      this.setBlockAndMetadata(world, -6, 5, -6, this.plankSlabBlock, this.plankSlabMeta);
      this.setBlockAndMetadata(world, -6, 2, -5, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -5, 2, -6, Blocks.field_150478_aa, 2);

      for(k1 = 1; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, -9, k1, -6, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, -6, k1, -9, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -7, 3, -6, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -6, 3, -7, this.fenceBlock, this.fenceMeta);

      for(k1 = -9; k1 <= -7; ++k1) {
         for(i1 = -9; i1 <= -7; ++i1) {
            if (k1 >= -8 || i1 >= -8) {
               this.setBlockAndMetadata(world, k1, 4, i1, this.plankBlock, this.plankMeta);
            }
         }
      }

      for(k1 = -9; k1 <= -7; ++k1) {
         this.setBlockAndMetadata(world, k1, 4, -6, this.plankStairBlock, 3);
      }

      for(k1 = -9; k1 <= -7; ++k1) {
         this.setBlockAndMetadata(world, -6, 4, k1, this.plankStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -8, 1, -10, Blocks.field_150417_aV, 0);
      this.setBlockAndMetadata(world, -8, 2, -10, LOTRMod.alloyForge, 3);
      this.setBlockAndMetadata(world, -10, 1, -8, Blocks.field_150417_aV, 0);
      this.setBlockAndMetadata(world, -10, 2, -8, Blocks.field_150460_al, 4);
      this.setBlockAndMetadata(world, -7, 1, -6, Blocks.field_150467_bQ, 1);
      this.setBlockAndMetadata(world, -6, 1, -7, Blocks.field_150383_bp, 3);
      LOTREntityEasterling blacksmith = new LOTREntityEasterlingBlacksmith(world);
      this.spawnNPCAndSetHome(blacksmith, world, -8, 1, -8, 8);

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 6, i1, -6, this.woodBeamBlock, this.woodBeamMeta);
      }

      this.setBlockAndMetadata(world, 6, 5, -6, this.plankSlabBlock, this.plankSlabMeta);
      this.setBlockAndMetadata(world, 6, 2, -5, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 5, 2, -6, Blocks.field_150478_aa, 1);

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, 9, i1, -6, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 6, i1, -9, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, 7, 3, -6, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 6, 3, -7, this.fenceBlock, this.fenceMeta);

      for(i1 = 7; i1 <= 9; ++i1) {
         for(k1 = -9; k1 <= -7; ++k1) {
            if (i1 <= 8 || k1 >= -8) {
               this.setBlockAndMetadata(world, i1, 4, k1, this.plankBlock, this.plankMeta);
            }
         }
      }

      for(i1 = 7; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -6, this.plankStairBlock, 3);
      }

      for(i1 = -9; i1 <= -7; ++i1) {
         this.setBlockAndMetadata(world, 6, 4, i1, this.plankStairBlock, 1);
      }

      for(i1 = 7; i1 <= 8; ++i1) {
         this.placeChest(world, random, i1, 1, -9, 3, LOTRChestContents.EASTERLING_TOWER);
      }

      this.setBlockAndMetadata(world, 9, 1, -8, this.tableBlock, 0);
      this.setBlockAndMetadata(world, 9, 1, -7, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 8, 1, -5, LOTRMod.commandTable, 0);
      this.placeWeaponRack(world, -8, 2, -3, 5, new ItemStack(LOTRMod.rhunBow));
      this.placeWeaponRack(world, -8, 2, 3, 5, new ItemStack(LOTRMod.rhunBow));
      this.setBlockAndMetadata(world, -9, 1, -1, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, -9, 1, 0, Blocks.field_150325_L, 0);
      this.setBlockAndMetadata(world, -9, 1, 1, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, -9, 2, -1, Blocks.field_150325_L, 0);
      this.setBlockAndMetadata(world, -9, 2, 0, Blocks.field_150325_L, 14);
      this.setBlockAndMetadata(world, -9, 2, 1, Blocks.field_150325_L, 0);
      this.setBlockAndMetadata(world, -9, 3, -1, this.plankStairBlock, 2);
      this.setBlockAndMetadata(world, -9, 3, 0, Blocks.field_150325_L, 0);
      this.setBlockAndMetadata(world, -9, 3, 1, this.plankStairBlock, 3);
      this.setBlockAndMetadata(world, -8, 2, 0, Blocks.field_150471_bO, 2);
      this.placeWeaponRack(world, 8, 2, -3, 7, new ItemStack(LOTRMod.swordRhun));
      this.placeWeaponRack(world, 8, 2, 3, 7, new ItemStack(LOTRMod.swordRhun));
      this.setBlockAndMetadata(world, 8, 1, 0, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 8, 2, 0, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 8, 3, 0, Blocks.field_150423_aK, 3);
      this.setBlockAndMetadata(world, 8, 2, -1, Blocks.field_150442_at, 4);
      this.setBlockAndMetadata(world, 8, 2, 1, Blocks.field_150442_at, 3);

      for(i1 = 0; i1 <= 7; ++i1) {
         this.setBlockAndMetadata(world, -1, i1, 9, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, 1, i1, 9, this.pillarBlock, this.pillarMeta);
      }

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, 9, Blocks.field_150468_ap, 2);
      }

      this.setAir(world, 0, 5, 9);
      this.setAir(world, 0, 6, 9);
      this.setBlockAndMetadata(world, 0, 5, 10, this.brickStairBlock, 2);
      int[] var22 = new int[]{-6, 6};
      k1 = var22.length;

      for(h = 0; h < k1; ++h) {
         j1 = var22[h];

         for(l = 0; l <= 4; ++l) {
            j1 = 1 + l;
            k1 = 6 + l;

            for(i2 = j1 - 1; i2 <= j1 + 1; ++i2) {
               this.setBlockAndMetadata(world, i2, j1, k1, this.brickStairBlock, 2);

               for(j2 = j1 - 1; j2 >= 1 && !this.isOpaque(world, i2, j2, k1); --j2) {
                  this.setBlockAndMetadata(world, i2, j2, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i2, j2 - 1, k1);
               }

               if (k1 == 9) {
                  this.setAir(world, i2, 5, k1);
                  this.setAir(world, i2, 6, k1);
               }
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         if (Math.abs(i1) >= 2) {
            for(k1 = 8; k1 <= 9; ++k1) {
               if (!this.isOpaque(world, i1, 1, k1)) {
                  h = 1;
                  if (random.nextInt(4) == 0) {
                     ++h;
                  }

                  this.setGrassToDirt(world, i1, 0, k1);

                  for(j1 = 1; j1 < 1 + h; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150407_cf, 0);
                  }
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 4, 1, 7, Blocks.field_150383_bp, 3);
      var22 = new int[]{-2, 2};
      k1 = var22.length;

      for(h = 0; h < k1; ++h) {
         j1 = var22[h];
         this.setBlockAndMetadata(world, j1, 1, 6, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, j1, 2, 6, this.fenceBlock, this.fenceMeta);
         LOTREntityHorse horse = new LOTREntityHorse(world);
         this.spawnNPCAndSetHome(horse, world, j1, 1, 5, 0);
         horse.func_110214_p(0);
         horse.saddleMountForWorldGen();
         horse.func_110177_bN();
         this.leashEntityTo(horse, world, j1, 2, 6);
      }

      LOTREntityEasterling captain = new LOTREntityEasterlingWarlord(world);
      captain.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(captain, world, 0, 1, 0, 12);
      k1 = 4 + random.nextInt(4);

      for(h = 0; h < k1; ++h) {
         LOTREntityEasterling soldier = random.nextInt(3) == 0 ? new LOTREntityEasterlingArcher(world) : new LOTREntityEasterlingWarrior(world);
         if (random.nextInt(3) == 0) {
            soldier = new LOTREntityEasterlingGoldWarrior(world);
         }

         ((LOTREntityEasterling)soldier).spawnRidingHorse = false;
         this.spawnNPCAndSetHome((EntityCreature)soldier, world, 0, 1, 0, 16);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
      respawner.setCheckRanges(20, -8, 12, 10);
      respawner.setSpawnRanges(10, 0, 8, 16);
      this.placeNPCRespawner(respawner, world, 0, 0, 0);
      LOTREntityNPCRespawner respawnerGold = new LOTREntityNPCRespawner(world);
      respawnerGold.setSpawnClass(LOTREntityEasterlingGoldWarrior.class);
      respawnerGold.setCheckRanges(20, -8, 12, 5);
      respawnerGold.setSpawnRanges(10, 0, 8, 16);
      this.placeNPCRespawner(respawnerGold, world, 0, 0, 0);
      return true;
   }
}
