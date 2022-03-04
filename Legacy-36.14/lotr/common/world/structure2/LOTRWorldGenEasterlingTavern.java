package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEasterling;
import lotr.common.entity.npc.LOTREntityEasterlingBartender;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingTavern extends LOTRWorldGenEasterlingStructure {
   private String[] tavernName;
   private String[] tavernNameSign;
   private String tavernNameNPC;

   public LOTRWorldGenEasterlingTavern(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.bedBlock = LOTRMod.strawBed;
      this.tavernName = LOTRNames.getRhunTavernName(random);
      this.tavernNameSign = new String[]{"", this.tavernName[0], this.tavernName[1], ""};
      this.tavernNameNPC = this.tavernName[0] + " " + this.tavernName[1];
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 11);
      this.setupRandomBlocks(random);
      int i1;
      int men;
      int l;
      int i1;
      int step;
      if (this.restrictions) {
         i1 = 0;
         men = 0;

         for(l = -9; l <= 9; ++l) {
            for(i1 = -12; i1 <= 11; ++i1) {
               step = this.getTopBlock(world, l, i1) - 1;
               if (!this.isSurface(world, l, step, i1)) {
                  return false;
               }

               if (step < i1) {
                  i1 = step;
               }

               if (step > men) {
                  men = step;
               }

               if (men - i1 > 8) {
                  return false;
               }
            }
         }
      }

      for(i1 = -8; i1 <= 8; ++i1) {
         for(men = -10; men <= 10; ++men) {
            l = Math.abs(i1);
            i1 = Math.abs(men);

            for(step = 1; step <= 12; ++step) {
               this.setAir(world, i1, step, men);
            }

            if (l == 8 && i1 % 4 == 2 || i1 == 10 && l % 4 == 0) {
               for(step = 4; (step >= 0 || !this.isOpaque(world, i1, step, men)) && this.getY(step) >= 0; --step) {
                  this.setBlockAndMetadata(world, i1, step, men, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, i1, step - 1, men);
               }
            } else if (l != 8 && i1 != 10) {
               for(step = 0; (step >= 0 || !this.isOpaque(world, i1, step, men)) && this.getY(step) >= 0; --step) {
                  this.setBlockAndMetadata(world, i1, step, men, this.plankBlock, this.plankMeta);
                  this.setGrassToDirt(world, i1, step - 1, men);
               }

               if (l % 4 == 2 && i1 % 4 == 0) {
                  this.setBlockAndMetadata(world, i1, 0, men, this.logBlock, this.logMeta);
               }
            } else {
               for(step = 3; (step >= 0 || !this.isOpaque(world, i1, step, men)) && this.getY(step) >= 0; --step) {
                  this.setBlockAndMetadata(world, i1, step, men, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i1, step - 1, men);
               }

               if (i1 == 10) {
                  this.setBlockAndMetadata(world, i1, 4, men, this.woodBeamBlock, this.woodBeamMeta | 4);
               } else if (l == 8) {
                  this.setBlockAndMetadata(world, i1, 4, men, this.woodBeamBlock, this.woodBeamMeta | 8);
               }
            }
         }
      }

      for(i1 = -7; i1 <= 7; ++i1) {
         for(men = -9; men <= 9; ++men) {
            l = Math.abs(i1);
            i1 = Math.abs(men);
            if (l <= 4 && i1 <= 9) {
               this.setBlockAndMetadata(world, i1, 4, men, this.plankSlabBlock, this.plankSlabMeta | 8);
            }

            if (l % 4 == 0 && i1 <= 9) {
               this.setBlockAndMetadata(world, i1, 0, men, this.woodBeamBlock, this.woodBeamMeta | 8);
               this.setBlockAndMetadata(world, i1, 4, men, this.woodBeamBlock, this.woodBeamMeta | 8);
            }

            if (i1 % 4 == 2 && l <= 7) {
               this.setBlockAndMetadata(world, i1, 0, men, this.woodBeamBlock, this.woodBeamMeta | 4);
               if (i1 == 2) {
                  this.setBlockAndMetadata(world, i1, 4, men, this.woodBeamBlock, this.woodBeamMeta | 4);
               }
            }

            if (i1 == 2 && l % 4 == 0) {
               for(step = 1; step <= 3; ++step) {
                  this.setBlockAndMetadata(world, i1, step, men, this.woodBeamBlock, this.woodBeamMeta);
               }
            }
         }
      }

      for(i1 = -8; i1 <= 8; ++i1) {
         men = Math.abs(i1);
         if (men == 2) {
            this.setBlockAndMetadata(world, i1, 2, -10, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i1, 3, -10, this.brickStairBlock, 6);
         }

         if (men == 6) {
            this.setBlockAndMetadata(world, i1 - 1, 2, -10, this.brickStairBlock, 4);
            this.setAir(world, i1, 2, -10);
            this.setBlockAndMetadata(world, i1 + 1, 2, -10, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, i1, 3, -10, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i1 - 1, 2, 10, this.brickStairBlock, 4);
            this.setAir(world, i1, 2, 10);
            this.setBlockAndMetadata(world, i1 + 1, 2, 10, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, i1, 3, 10, this.brickStairBlock, 7);
         }

         if (men == 4) {
            this.setBlockAndMetadata(world, i1, 3, -11, Blocks.field_150478_aa, 4);
            this.setBlockAndMetadata(world, i1, 3, 11, Blocks.field_150478_aa, 3);
         }

         if (men == 0) {
            this.setBlockAndMetadata(world, i1, 3, 11, Blocks.field_150478_aa, 3);
         }

         if (men == 8) {
            this.setBlockAndMetadata(world, i1, 3, -11, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i1, 3, 11, this.fenceBlock, this.fenceMeta);
         }
      }

      for(i1 = -10; i1 <= 10; ++i1) {
         men = Math.abs(i1);
         if (men % 4 == 0) {
            this.setBlockAndMetadata(world, -8, 2, i1 - 1, this.brickStairBlock, 7);
            this.setAir(world, -8, 2, i1);
            this.setBlockAndMetadata(world, -8, 2, i1 + 1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, -8, 3, i1, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 8, 2, i1 - 1, this.brickStairBlock, 7);
            this.setAir(world, 8, 2, i1);
            this.setBlockAndMetadata(world, 8, 2, i1 + 1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, 8, 3, i1, this.brickStairBlock, 4);
         }

         if (men % 4 == 2) {
            this.setBlockAndMetadata(world, -9, 3, i1, Blocks.field_150478_aa, 1);
            this.setBlockAndMetadata(world, 9, 3, i1, Blocks.field_150478_aa, 2);
         }

         if (men == 10) {
            this.setBlockAndMetadata(world, -9, 3, i1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 9, 3, i1, this.fenceBlock, this.fenceMeta);
         }
      }

      this.setBlockAndMetadata(world, 0, 0, -10, this.woodBeamBlock, this.woodBeamMeta | 8);
      this.setBlockAndMetadata(world, 0, 1, -10, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -10, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 3, -10, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 4, -11, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 4, -12, this.plankBlock, this.plankMeta);
      this.placeSign(world, -1, 4, -12, Blocks.field_150444_as, 5, this.tavernNameSign);
      this.placeSign(world, 1, 4, -12, Blocks.field_150444_as, 4, this.tavernNameSign);
      this.placeSign(world, 0, 4, -13, Blocks.field_150444_as, 2, this.tavernNameSign);

      for(i1 = -4; i1 <= 4; ++i1) {
         for(men = -9; men <= 9; ++men) {
            l = Math.abs(i1);
            i1 = Math.abs(men);
            if (l == 4 && i1 == 2 || i1 == 9 && l % 4 == 0) {
               for(step = 5; step <= 8; ++step) {
                  this.setBlockAndMetadata(world, i1, step, men, this.woodBeamBlock, this.woodBeamMeta);
               }

               if (l == 0) {
                  for(step = 9; step <= 11; ++step) {
                     this.setBlockAndMetadata(world, i1, step, men, this.woodBeamBlock, this.woodBeamMeta);
                  }
               }
            } else if (l == 4 || i1 == 9) {
               for(step = 5; step <= 7; ++step) {
                  this.setBlockAndMetadata(world, i1, step, men, this.brickBlock, this.brickMeta);
               }

               if (i1 == 9) {
                  this.setBlockAndMetadata(world, i1, 8, men, this.woodBeamBlock, this.woodBeamMeta | 4);
               } else if (l == 4) {
                  this.setBlockAndMetadata(world, i1, 8, men, this.woodBeamBlock, this.woodBeamMeta | 8);
               }
            }
         }
      }

      int[] var14 = new int[]{-2, 2};
      men = var14.length;

      for(l = 0; l < men; ++l) {
         i1 = var14[l];
         this.setBlockAndMetadata(world, i1, 6, -9, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, i1, 7, -9, this.brickStairBlock, 6);
         if (i1 >= 0) {
            this.setBlockAndMetadata(world, i1, 6, 9, LOTRMod.reedBars, 0);
            this.setBlockAndMetadata(world, i1, 7, 9, this.brickStairBlock, 7);
         }
      }

      var14 = new int[]{-4, 4};
      men = var14.length;

      for(l = 0; l < men; ++l) {
         i1 = var14[l];
         this.setBlockAndMetadata(world, i1, 8, -10, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 8, 10, this.fenceBlock, this.fenceMeta);
      }

      var14 = new int[]{-9, 9};
      men = var14.length;

      for(l = 0; l < men; ++l) {
         i1 = var14[l];
         this.setBlockAndMetadata(world, -5, 8, i1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 5, 8, i1, this.fenceBlock, this.fenceMeta);
      }

      for(i1 = 0; i1 <= 1; ++i1) {
         men = 5 + i1;

         for(l = -10 + i1; l <= 10 - i1; ++l) {
            this.setBlockAndMetadata(world, -8 + i1, men, l, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -7 + i1, men, l, this.roofStairBlock, 4);
         }

         for(l = -7 + i1; l <= -5; ++l) {
            this.setBlockAndMetadata(world, l, men, -10 + i1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, l, men, -9 + i1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, l, men, 10 - i1, this.roofStairBlock, 3);
            this.setBlockAndMetadata(world, l, men, 9 - i1, this.roofStairBlock, 6);
         }

         for(l = -10 + i1; l <= 10 - i1; ++l) {
            this.setBlockAndMetadata(world, 8 - i1, men, l, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 7 - i1, men, l, this.roofStairBlock, 5);
         }

         for(l = 5; l <= 7 - i1; ++l) {
            this.setBlockAndMetadata(world, l, men, -10 + i1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, l, men, -9 + i1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, l, men, 10 - i1, this.roofStairBlock, 3);
            this.setBlockAndMetadata(world, l, men, 9 - i1, this.roofStairBlock, 6);
         }

         if (i1 == 1) {
            for(l = -9 + i1; l <= 9 - i1; ++l) {
               for(i1 = -7 + i1; i1 <= -5; ++i1) {
                  this.setBlockAndMetadata(world, i1, men + 1, l, this.roofSlabBlock, this.roofSlabMeta);
               }

               for(i1 = 5; i1 <= 7 - i1; ++i1) {
                  this.setBlockAndMetadata(world, i1, men + 1, l, this.roofSlabBlock, this.roofSlabMeta);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -4, 5, -10, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -3, 5, -10, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -2, 5, -10, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -1, 5, -10, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 0, 5, -10, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 1, 5, -10, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 2, 5, -10, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 3, 5, -10, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 4, 5, -10, this.roofStairBlock, 1);

      for(i1 = -4; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, 10, this.roofStairBlock, 3);
      }

      this.setBlockAndMetadata(world, 0, 5, 10, this.roofBlock, this.roofMeta);

      for(i1 = -9; i1 <= 9; ++i1) {
         men = Math.abs(i1);
         if (men == 9 || men == 6 || men == 2) {
            this.setBlockAndMetadata(world, i1, 5, -11, this.roofSlabBlock, this.roofSlabMeta);
         }

         if (i1 == -8 || i1 == 7) {
            this.setBlockAndMetadata(world, i1, 4, -11, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, i1 + 1, 4, -11, this.roofStairBlock, 4);
         }

         if (men == 4) {
            this.setBlockAndMetadata(world, i1 - 1, 4, -11, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, i1, 4, -11, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1 + 1, 4, -11, this.roofStairBlock, 4);
         }

         if (men <= 1) {
            this.setBlockAndMetadata(world, i1, 5, -11, this.roofSlabBlock, this.roofSlabMeta | 8);
         }

         if (men == 9 || men == 6 || men == 2) {
            this.setBlockAndMetadata(world, i1, 5, 11, this.roofSlabBlock, this.roofSlabMeta);
         }

         if (i1 == -8 || i1 == 7) {
            this.setBlockAndMetadata(world, i1, 4, 11, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, i1 + 1, 4, 11, this.roofStairBlock, 4);
         }

         if (men == 4 || men == 0) {
            this.setBlockAndMetadata(world, i1 - 1, 4, 11, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, i1, 4, 11, this.roofStairBlock, 3);
            this.setBlockAndMetadata(world, i1 + 1, 4, 11, this.roofStairBlock, 4);
         }
      }

      for(i1 = -10; i1 <= 10; ++i1) {
         men = Math.abs(i1);
         if (men % 4 == 0) {
            this.setBlockAndMetadata(world, -9, 5, i1, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, 9, 5, i1, this.roofSlabBlock, this.roofSlabMeta);
         }

         if (i1 == -10 || i1 == 9) {
            this.setBlockAndMetadata(world, -9, 4, i1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, -9, 4, i1 + 1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, 9, 4, i1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, 9, 4, i1 + 1, this.roofStairBlock, 7);
         }

         if (men <= 6 && men % 4 == 2) {
            this.setBlockAndMetadata(world, -9, 4, i1 - 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, -9, 4, i1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -9, 4, i1 + 1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, 9, 4, i1 - 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, 9, 4, i1, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 9, 4, i1 + 1, this.roofStairBlock, 7);
         }
      }

      int j1;
      for(i1 = -5; i1 <= 5; ++i1) {
         men = Math.abs(i1);
         int[] var15 = new int[]{-10, 10};
         i1 = var15.length;

         for(step = 0; step < i1; ++step) {
            j1 = var15[step];
            if (men == 2 || men == 5) {
               this.setBlockAndMetadata(world, i1, 9, j1, this.roofSlabBlock, this.roofSlabMeta);
            }

            if (men == 0) {
               this.setBlockAndMetadata(world, i1 - 1, 8, j1, this.roofStairBlock, 5);
               this.setBlockAndMetadata(world, i1, 8, j1, this.roofBlock, this.roofMeta);
               this.setBlockAndMetadata(world, i1 + 1, 8, j1, this.roofStairBlock, 4);
               this.setBlockAndMetadata(world, i1, 6, j1, this.roofWallBlock, this.roofWallMeta);
               this.setBlockAndMetadata(world, i1, 7, j1, this.roofWallBlock, this.roofWallMeta);
            }

            if (i1 == -4 || i1 == 3) {
               this.setBlockAndMetadata(world, i1, 8, j1, this.roofStairBlock, 5);
               this.setBlockAndMetadata(world, i1 + 1, 8, j1, this.roofStairBlock, 4);
            }
         }
      }

      for(i1 = -9; i1 <= 9; ++i1) {
         men = Math.abs(i1);
         if (men == 0 || men == 4 || men == 7) {
            this.setBlockAndMetadata(world, -5, 9, i1, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, 5, 9, i1, this.roofSlabBlock, this.roofSlabMeta);
         }

         if (men == 2) {
            this.setBlockAndMetadata(world, -5, 8, i1 - 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, -5, 8, i1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -5, 8, i1 + 1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, 5, 8, i1 - 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, 5, 8, i1, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 5, 8, i1 + 1, this.roofStairBlock, 7);
         }

         if (i1 == -9 || i1 == -6 || i1 == 5 || i1 == 8) {
            this.setBlockAndMetadata(world, -5, 8, i1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, -5, 8, i1 + 1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, 5, 8, i1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, 5, 8, i1 + 1, this.roofStairBlock, 7);
         }
      }

      for(i1 = -9; i1 <= 9; ++i1) {
         for(men = 0; men <= 3; ++men) {
            l = 9 + men;
            this.setBlockAndMetadata(world, -4 + men, l, i1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 4 - men, l, i1, this.roofStairBlock, 0);
            if (men > 0) {
               this.setBlockAndMetadata(world, -4 + men, l - 1, i1, this.roofStairBlock, 4);
               this.setBlockAndMetadata(world, 4 - men, l - 1, i1, this.roofStairBlock, 5);
            }
         }

         this.setBlockAndMetadata(world, 0, 12, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 0, 13, i1, this.roofSlabBlock, this.roofSlabMeta);
      }

      this.setBlockAndMetadata(world, 0, 12, -10, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 0, 13, -10, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 12, 10, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 0, 13, 10, this.roofStairBlock, 2);
      var14 = new int[]{-8, 8};
      men = var14.length;

      for(l = 0; l < men; ++l) {
         i1 = var14[l];

         for(step = 0; step <= 2; ++step) {
            j1 = 9 + step;

            for(int i1 = -3 + step; i1 <= 3 - step; ++i1) {
               this.setBlockAndMetadata(world, i1, j1, i1, this.plankBlock, this.plankMeta);
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 8, -8, this.plankStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 8, 8, this.plankStairBlock, 6);
      }

      this.setBlockAndMetadata(world, -4, 3, -6, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, 0, 3, -6, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, 4, 3, -6, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, -6, 3, -2, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, 6, 3, -2, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, -6, 3, 2, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, 6, 3, 2, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, -4, 3, 6, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, 4, 3, 6, LOTRMod.chandelier, 0);
      this.placeTable(world, random, -5, -4, 1, -7, -6);

      for(i1 = -7; i1 <= -4; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, -9, this.plankStairBlock, 3);
      }

      for(i1 = -8; i1 <= -6; ++i1) {
         this.setBlockAndMetadata(world, -7, 1, i1, this.plankStairBlock, 0);
      }

      this.placeTable(world, random, 4, 5, 1, -7, -6);

      for(i1 = 4; i1 <= 7; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, -9, this.plankStairBlock, 3);
      }

      for(i1 = -8; i1 <= -6; ++i1) {
         this.setBlockAndMetadata(world, 7, 1, i1, this.plankStairBlock, 1);
      }

      this.placeTable(world, random, -7, -6, 1, 0, 0);

      for(i1 = -7; i1 <= -6; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, -2, this.plankStairBlock, 3);
         this.setBlockAndMetadata(world, i1, 1, 2, this.plankStairBlock, 2);
      }

      this.placeTable(world, random, 4, 5, 1, -1, 1);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, 7, 1, i1, this.plankStairBlock, 1);
      }

      this.placeTable(world, random, -7, -6, 1, 8, 9);

      for(i1 = -7; i1 <= -6; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 6, this.plankStairBlock, 3);
      }

      for(i1 = 8; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, -4, 1, i1, this.plankStairBlock, 1);
      }

      this.placeTable(world, random, 6, 7, 1, 8, 9);

      for(i1 = 6; i1 <= 7; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 6, this.plankStairBlock, 3);
      }

      for(i1 = 8; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, 4, 1, i1, this.plankStairBlock, 0);
      }

      for(i1 = -3; i1 <= -1; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, -2, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 3, -2, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 3, 2, this.fenceBlock, this.fenceMeta);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -4, 1, i1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, -4, 3, i1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 0, 1, i1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 0, 3, i1, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -3, 1, 2, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, -2, 1, 2, this.fenceGateBlock, 0);
      this.setBlockAndMetadata(world, -1, 1, 2, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -4, 1, 1, Blocks.field_150383_bp, 3);
      this.placeChest(world, random, -1, 0, -1, 3, LOTRChestContents.EASTERLING_HOUSE);
      this.placeBarrel(world, random, -3, 2, -2, 2, LOTRFoods.RHUN_DRINK);
      this.placeBarrel(world, random, 0, 2, -1, 4, LOTRFoods.RHUN_DRINK);

      for(i1 = -4; i1 <= 0; ++i1) {
         for(men = -2; men <= 2; ++men) {
            if (i1 == -4 && men >= -1 && men <= 0 || men == -2 && i1 >= -2 && i1 <= -1 || i1 == 0 && men >= 0 && men <= 1) {
               if (random.nextBoolean()) {
                  this.placeMug(world, random, i1, 2, men, random.nextInt(4), LOTRFoods.RHUN_DRINK);
               } else {
                  this.placePlate(world, random, i1, 2, men, this.plateBlock, LOTRFoods.RHUN);
               }
            }
         }
      }

      for(i1 = -3; i1 <= -1; ++i1) {
         for(men = 8; men <= 10; ++men) {
            for(l = 0; l <= 4; ++l) {
               this.setBlockAndMetadata(world, i1, l, men, this.brickBlock, this.brickMeta);
            }
         }

         for(men = 8; men <= 9; ++men) {
            for(l = 5; l <= 8; ++l) {
               this.setBlockAndMetadata(world, i1, l, men, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(i1 = 1; i1 <= 7; ++i1) {
         this.setAir(world, -2, i1, 9);
      }

      this.setBlockAndMetadata(world, -2, 0, 9, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, -2, 1, 9, Blocks.field_150480_ab, 0);
      this.setBlockAndMetadata(world, -2, 1, 8, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -2, 2, 8, Blocks.field_150460_al, 2);
      this.spawnItemFrame(world, -2, 3, 8, 2, this.getEasterlingFramedItem(random));
      this.setBlockAndMetadata(world, -2, 6, 8, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -2, 7, 8, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -3, 8, 8, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, -1, 8, 8, this.brickStairBlock, 0);

      for(i1 = 5; i1 <= 7; ++i1) {
         this.setBlockAndMetadata(world, -2, i1, 10, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, -2, 8, 10, this.brickStairBlock, 3);

      for(i1 = 9; i1 <= 13; ++i1) {
         this.setBlockAndMetadata(world, -2, i1, 9, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, -2, 14, 9, Blocks.field_150457_bL, 0);

      for(i1 = 0; i1 <= 3; ++i1) {
         men = 1 + i1;
         l = 4 + i1;

         for(i1 = 2; i1 <= 3; ++i1) {
            this.setAir(world, i1, 4, l);
            this.setBlockAndMetadata(world, i1, men, l, this.plankStairBlock, 2);
            this.setBlockAndMetadata(world, i1, men, l + 1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, i1, men, l + 2, this.plankStairBlock, 7);
         }
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, 3, this.fenceBlock, this.fenceMeta);
      }

      for(i1 = 4; i1 <= 6; ++i1) {
         this.setBlockAndMetadata(world, 1, 5, i1, this.fenceBlock, this.fenceMeta);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(men = 5; men <= 7; ++men) {
            this.setBlockAndMetadata(world, i1, men, -2, this.plankBlock, this.plankMeta);
         }

         this.setBlockAndMetadata(world, i1, 8, -2, this.woodBeamBlock, this.woodBeamMeta | 4);
      }

      for(i1 = -8; i1 <= 8; ++i1) {
         if (i1 <= -2) {
            for(men = 5; men <= 7; ++men) {
               this.setBlockAndMetadata(world, 0, men, i1, this.plankBlock, this.plankMeta);
            }
         }

         this.setBlockAndMetadata(world, 0, 8, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
      }

      for(i1 = 5; i1 <= 7; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, -2, this.woodBeamBlock, this.woodBeamMeta);
      }

      this.placeTable(world, random, -3, -2, 5, 4, 5);

      for(i1 = -3; i1 <= -2; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, 2, this.plankStairBlock, 3);
         this.setBlockAndMetadata(world, i1, 5, 7, this.plankStairBlock, 2);
      }

      this.setBlockAndMetadata(world, -3, 7, 2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, 7, 2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 0, 7, 8, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -2, 5, -2, this.doorBlock, 3);
      this.setBlockAndMetadata(world, -2, 6, -2, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 2, 5, -2, this.doorBlock, 3);
      this.setBlockAndMetadata(world, 2, 6, -2, this.doorBlock, 8);
      this.setBlockAndMetadata(world, -3, 5, -3, this.plankStairBlock, 2);
      this.setBlockAndMetadata(world, -3, 5, -5, this.plankBlock, this.plankMeta);
      this.placePlateWithCertainty(world, random, -3, 6, -5, this.plateBlock, LOTRFoods.RHUN);
      this.setBlockAndMetadata(world, -3, 5, -6, Blocks.field_150486_ae, 4);
      var14 = new int[]{-3, -1};
      men = var14.length;

      for(l = 0; l < men; ++l) {
         i1 = var14[l];
         this.setBlockAndMetadata(world, i1, 5, -7, this.bedBlock, 2);
         this.setBlockAndMetadata(world, i1, 5, -8, this.bedBlock, 10);
      }

      this.spawnItemFrame(world, 0, 6, -5, 3, LOTRFoods.RHUN_DRINK.getRandomVessel(random).getEmptyVessel());
      this.setBlockAndMetadata(world, -3, 6, -3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -1, 6, -3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -3, 6, -8, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -1, 6, -8, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 3, 5, -3, this.plankStairBlock, 2);
      this.setBlockAndMetadata(world, 3, 5, -5, this.plankBlock, this.plankMeta);
      this.placePlateWithCertainty(world, random, 3, 6, -5, this.plateBlock, LOTRFoods.RHUN);
      this.setBlockAndMetadata(world, 3, 5, -6, Blocks.field_150486_ae, 5);
      var14 = new int[]{1, 3};
      men = var14.length;

      for(l = 0; l < men; ++l) {
         i1 = var14[l];
         this.setBlockAndMetadata(world, i1, 5, -7, this.bedBlock, 2);
         this.setBlockAndMetadata(world, i1, 5, -8, this.bedBlock, 10);
      }

      this.spawnItemFrame(world, 0, 6, -5, 1, LOTRFoods.RHUN_DRINK.getRandomVessel(random).getEmptyVessel());
      this.setBlockAndMetadata(world, 3, 6, -3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 1, 6, -3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 3, 6, -8, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 1, 6, -8, Blocks.field_150478_aa, 3);
      LOTREntityEasterling bartender = new LOTREntityEasterlingBartender(world);
      bartender.setSpecificLocationName(this.tavernNameNPC);
      this.spawnNPCAndSetHome(bartender, world, -2, 1, 0, 2);
      men = 6 + random.nextInt(5);

      for(l = 0; l < men; ++l) {
         LOTREntityEasterling easterling = new LOTREntityEasterling(world);
         this.spawnNPCAndSetHome(easterling, world, 2, 1, 0, 16);
      }

      return true;
   }

   private void placeTable(World world, Random random, int i1, int i2, int j, int k1, int k2) {
      for(int i = i1; i <= i2; ++i) {
         for(int k = k1; k <= k2; ++k) {
            this.setBlockAndMetadata(world, i, j, k, this.plankBlock, this.plankMeta);
            if (random.nextInt(3) != 0) {
               if (random.nextBoolean()) {
                  this.placeMug(world, random, i, j + 1, k, random.nextInt(4), LOTRFoods.RHUN_DRINK);
               } else {
                  this.placePlate(world, random, i, j + 1, k, this.plateBlock, LOTRFoods.RHUN);
               }
            }
         }
      }

   }
}
