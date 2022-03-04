package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorBartender;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.entity.npc.LOTRNames;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorTavern extends LOTRWorldGenGondorStructure {
   private String[] tavernName;
   private String[] tavernNameSign;
   private String tavernNameNPC;

   public LOTRWorldGenGondorTavern(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.bedBlock = Blocks.field_150324_C;
      this.tavernName = LOTRNames.getGondorTavernName(random);
      this.tavernNameSign = new String[]{"", this.tavernName[0], this.tavernName[1], ""};
      this.tavernNameNPC = this.tavernName[0] + " " + this.tavernName[1];
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      int i1;
      int men;
      int l;
      int i1;
      int k1;
      if (this.restrictions) {
         i1 = 0;
         men = 0;

         for(l = -9; l <= 13; ++l) {
            for(i1 = -2; i1 <= 16; ++i1) {
               k1 = this.getTopBlock(world, l, i1) - 1;
               if (!this.isSurface(world, l, k1, i1)) {
                  return false;
               }

               if (k1 < i1) {
                  i1 = k1;
               }

               if (k1 > men) {
                  men = k1;
               }

               if (men - i1 > 6) {
                  return false;
               }
            }
         }
      }

      if (this.restrictions) {
         i1 = this.getTopBlock(world, 0, 15) - 1;
         if (i1 > 0) {
            this.originY = this.getY(i1);
         }
      }

      boolean beam;
      for(i1 = -7; i1 <= 11; ++i1) {
         for(men = 0; men <= 14; ++men) {
            if (!((i1 == -7 || i1 == 11) & (men == 0 || men == 14))) {
               beam = false;
               if (i1 != -7 && i1 != 11) {
                  if (men == 0 || men == 14) {
                     beam = IntMath.mod(i1, 4) == 2;
                  }
               } else {
                  beam = IntMath.mod(men, 4) == 1;
               }

               if (beam) {
                  for(i1 = 4; (i1 >= 0 || !this.isOpaque(world, i1, i1, men)) && this.getY(i1) >= 0; --i1) {
                     this.setBlockAndMetadata(world, i1, i1, men, this.woodBeamBlock, this.woodBeamMeta);
                     this.setGrassToDirt(world, i1, i1 - 1, men);
                  }
               } else if (i1 != -7 && i1 != 11 && men != 0 && men != 14) {
                  for(i1 = 0; (i1 >= 0 || !this.isOpaque(world, i1, i1, men)) && this.getY(i1) >= 0; --i1) {
                     this.setBlockAndMetadata(world, i1, i1, men, this.plankBlock, this.plankMeta);
                     this.setGrassToDirt(world, i1, i1 - 1, men);
                  }

                  for(i1 = 1; i1 <= 4; ++i1) {
                     this.setAir(world, i1, i1, men);
                  }
               } else {
                  for(i1 = 0; (i1 >= 0 || !this.isOpaque(world, i1, i1, men)) && this.getY(i1) >= 0; --i1) {
                     this.setBlockAndMetadata(world, i1, i1, men, this.rockBlock, this.rockMeta);
                     this.setGrassToDirt(world, i1, i1 - 1, men);
                  }

                  for(i1 = 1; i1 <= 4; ++i1) {
                     this.setBlockAndMetadata(world, i1, i1, men, this.wallBlock, this.wallMeta);
                  }
               }
            }
         }
      }

      int[] var15 = new int[]{0, 14};
      men = var15.length;

      for(l = 0; l < men; ++l) {
         i1 = var15[l];

         for(k1 = -4; k1 <= 8; ++k1) {
            if (IntMath.mod(k1, 4) == 0 && k1 != 0) {
               this.setBlockAndMetadata(world, k1, 2, i1, LOTRMod.glassPane, 0);
               this.setBlockAndMetadata(world, k1, 3, i1, LOTRMod.glassPane, 0);
            }
         }
      }

      var15 = new int[]{-7, 11};
      men = var15.length;

      for(l = 0; l < men; ++l) {
         i1 = var15[l];

         for(k1 = 3; k1 <= 11; ++k1) {
            if (IntMath.mod(k1, 4) == 3 && (i1 != -7 || k1 != 7)) {
               this.setBlockAndMetadata(world, i1, 2, k1, LOTRMod.glassPane, 0);
               this.setBlockAndMetadata(world, i1, 3, k1, LOTRMod.glassPane, 0);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 0, 0, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 1, 0, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, 0, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 0, 14, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 0, 1, 14, this.doorBlock, 3);
      this.setBlockAndMetadata(world, 0, 2, 14, this.doorBlock, 8);
      var15 = new int[]{-1, 15};
      men = var15.length;

      for(l = 0; l < men; ++l) {
         i1 = var15[l];
         int i1 = 0;
         int doorHeight = this.getTopBlock(world, i1, i1) - 1;
         if (doorHeight < 0) {
            int j1;
            for(j1 = 0; (j1 == 0 || !this.isOpaque(world, i1, j1, i1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, i1, this.plankBlock, this.plankMeta);
               this.setGrassToDirt(world, i1, j1 - 1, i1);
            }

            k1 = i1 + 1;

            for(j1 = 0; !this.isOpaque(world, k1, j1, i1) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, k1, j1, i1, this.plankStairBlock, 0);
               this.setGrassToDirt(world, k1, j1 - 1, i1);

               for(int j2 = j1 - 1; !this.isOpaque(world, k1, j2, i1) && this.getY(j2) >= 0; --j2) {
                  this.setBlockAndMetadata(world, k1, j2, i1, this.plankBlock, this.plankMeta);
                  this.setGrassToDirt(world, k1, j2 - 1, i1);
               }

               ++k1;
               if (k1 >= 15) {
                  break;
               }
            }
         }
      }

      this.setBlockAndMetadata(world, -2, 3, -1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -2, 4, -1, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 2, 3, -1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 4, -1, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 0, 4, -1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 4, -2, this.plankBlock, this.plankMeta);
      this.placeSign(world, -1, 4, -2, Blocks.field_150444_as, 5, this.tavernNameSign);
      this.placeSign(world, 1, 4, -2, Blocks.field_150444_as, 4, this.tavernNameSign);
      this.placeSign(world, 0, 4, -3, Blocks.field_150444_as, 2, this.tavernNameSign);
      this.setBlockAndMetadata(world, -2, 3, 15, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -2, 4, 15, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 2, 3, 15, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 4, 15, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 0, 4, 15, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 4, 16, this.plankBlock, this.plankMeta);
      this.placeSign(world, -1, 4, 16, Blocks.field_150444_as, 5, this.tavernNameSign);
      this.placeSign(world, 1, 4, 16, Blocks.field_150444_as, 4, this.tavernNameSign);
      this.placeSign(world, 0, 4, 17, Blocks.field_150444_as, 3, this.tavernNameSign);

      for(i1 = -8; i1 <= 12; ++i1) {
         for(men = -1; men <= 15; ++men) {
            if (!((i1 <= -7 || i1 >= 11) & (men <= 0 || men >= 14))) {
               beam = false;
               if (i1 != -8 && i1 != 12) {
                  if (men == -1 || men == 15) {
                     beam = IntMath.mod(i1, 4) == 2;
                  }
               } else {
                  beam = IntMath.mod(men, 4) == 1;
               }

               if (beam) {
                  if (i1 == -8 || i1 == 12) {
                     this.setBlockAndMetadata(world, i1, 5, men, this.woodBeamBlock, this.woodBeamMeta | 4);
                  }

                  if (men == -1 || men == 15) {
                     this.setBlockAndMetadata(world, i1, 5, men, this.woodBeamBlock, this.woodBeamMeta | 8);
                  }

                  for(i1 = 6; i1 <= 8; ++i1) {
                     this.setBlockAndMetadata(world, i1, i1, men, this.woodBeamBlock, this.woodBeamMeta);
                  }
               } else if (i1 != -8 && i1 != 12 && men != -1 && men != 15) {
                  if ((i1 != -7 && i1 != 11 || men != 1 && men != 13) && (i1 != -6 && i1 != 10 || men != 0 && men != 14)) {
                     this.setBlockAndMetadata(world, i1, 5, men, this.plankBlock, this.plankMeta);

                     for(i1 = 6; i1 <= 11; ++i1) {
                        this.setAir(world, i1, i1, men);
                     }
                  } else {
                     if (i1 == -7 || i1 == 11) {
                        this.setBlockAndMetadata(world, i1, 5, men, this.woodBeamBlock, this.woodBeamMeta | 4);
                     }

                     if (men == 0 || men == 14) {
                        this.setBlockAndMetadata(world, i1, 5, men, this.woodBeamBlock, this.woodBeamMeta | 8);
                     }

                     for(i1 = 6; i1 <= 8; ++i1) {
                        this.setBlockAndMetadata(world, i1, i1, men, this.wallBlock, this.wallMeta);
                     }
                  }
               } else {
                  if (i1 == -8 || i1 == 12) {
                     this.setBlockAndMetadata(world, i1, 5, men, this.woodBeamBlock, this.woodBeamMeta | 8);
                  }

                  if (men == -1 || men == 15) {
                     this.setBlockAndMetadata(world, i1, 5, men, this.woodBeamBlock, this.woodBeamMeta | 4);
                  }

                  for(i1 = 6; i1 <= 8; ++i1) {
                     this.setBlockAndMetadata(world, i1, i1, men, this.wallBlock, this.wallMeta);
                  }
               }
            }
         }
      }

      var15 = new int[]{-1, 15};
      men = var15.length;

      for(l = 0; l < men; ++l) {
         i1 = var15[l];

         for(k1 = -4; k1 <= 8; ++k1) {
            if (IntMath.mod(k1, 4) == 0) {
               this.setBlockAndMetadata(world, k1, 7, i1, LOTRMod.glassPane, 0);
            }
         }
      }

      var15 = new int[]{-8, 12};
      men = var15.length;

      for(l = 0; l < men; ++l) {
         i1 = var15[l];

         for(k1 = 3; k1 <= 11; ++k1) {
            if (IntMath.mod(k1, 4) == 3) {
               this.setBlockAndMetadata(world, i1, 7, k1, LOTRMod.glassPane, 0);
            }
         }
      }

      for(i1 = 0; i1 <= 2; ++i1) {
         for(men = -9; men <= 13; ++men) {
            if (men >= -7 + i1 && men <= 11 - i1) {
               this.setBlockAndMetadata(world, men, 8 + i1, -2 + i1, this.roofStairBlock, 2);
               this.setBlockAndMetadata(world, men, 8 + i1, 16 - i1, this.roofStairBlock, 3);
            }

            if (men <= -7 + i1 || men >= 11 - i1) {
               this.setBlockAndMetadata(world, men, 8 + i1, 0 + i1, this.roofStairBlock, 2);
               this.setBlockAndMetadata(world, men, 8 + i1, 14 - i1, this.roofStairBlock, 3);
            }
         }

         this.setBlockAndMetadata(world, -7 + i1, 8 + i1, -1 + i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 11 - i1, 8 + i1, -1 + i1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, -7 + i1, 8 + i1, 15 - i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 11 - i1, 8 + i1, 15 - i1, this.roofStairBlock, 0);
      }

      for(i1 = -9; i1 <= 13; ++i1) {
         this.setBlockAndMetadata(world, i1, 11, 4, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, i1, 12, 5, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 12, 6, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, i1, 12, 7, this.woodBeamBlock, this.woodBeamMeta | 4);
         this.setBlockAndMetadata(world, i1, 13, 7, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 12, 8, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, i1, 12, 9, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 11, 10, this.roofBlock, this.roofMeta);
         if (i1 >= -3 && i1 <= 7) {
            this.setBlockAndMetadata(world, i1, 11, 1, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i1, 11, 2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, i1, 11, 3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, i1, 11, 11, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, i1, 11, 12, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, i1, 11, 13, this.roofSlabBlock, this.roofSlabMeta);
         } else {
            this.setBlockAndMetadata(world, i1, 11, 3, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i1, 11, 11, this.roofSlabBlock, this.roofSlabMeta);
         }

         if (i1 == -4 || i1 == 8) {
            this.setBlockAndMetadata(world, i1, 11, 1, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i1, 11, 2, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i1, 11, 12, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, i1, 11, 13, this.roofSlabBlock, this.roofSlabMeta);
         }

         if (i1 == -9 || i1 == 13) {
            this.setBlockAndMetadata(world, i1, 8, 1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i1, 9, 2, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i1, 10, 3, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i1, 11, 5, this.roofSlabBlock, this.roofSlabMeta | 8);
            this.setBlockAndMetadata(world, i1, 11, 9, this.roofSlabBlock, this.roofSlabMeta | 8);
            this.setBlockAndMetadata(world, i1, 10, 11, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i1, 9, 12, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i1, 8, 13, this.roofStairBlock, 6);
         }
      }

      var15 = new int[]{-8, 12};
      men = var15.length;

      for(l = 0; l < men; ++l) {
         i1 = var15[l];

         for(k1 = 2; k1 <= 12; ++k1) {
            this.setBlockAndMetadata(world, i1, 9, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
         }

         for(k1 = 3; k1 <= 11; ++k1) {
            this.setBlockAndMetadata(world, i1, 10, k1, this.wallBlock, this.wallMeta);
         }

         for(k1 = 5; k1 <= 9; ++k1) {
            this.setBlockAndMetadata(world, i1, 11, k1, this.wallBlock, this.wallMeta);
         }
      }

      for(i1 = 3; i1 <= 5; ++i1) {
         for(men = 6; men <= 8; ++men) {
            for(l = 0; l <= 13; ++l) {
               if (i1 == 4 && men == 7) {
                  this.setAir(world, i1, l, men);
               } else {
                  this.setBlockAndMetadata(world, i1, l, men, this.brickBlock, this.brickMeta);
               }
            }
         }

         this.setBlockAndMetadata(world, i1, 14, 6, this.brickStairBlock, 2);
         this.setBlockAndMetadata(world, i1, 14, 8, this.brickStairBlock, 3);
      }

      this.setBlockAndMetadata(world, 3, 14, 7, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 5, 14, 7, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 4, 15, 7, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 4, 16, 7, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 4, 17, 7, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 4, 18, 7, this.brickWallBlock, this.brickWallMeta);
      this.setBlockAndMetadata(world, 4, 0, 7, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 4, 1, 7, Blocks.field_150480_ab, 0);
      this.setBlockAndMetadata(world, 4, 1, 6, Blocks.field_150411_aY, 0);
      this.setBlockAndMetadata(world, 4, 1, 8, Blocks.field_150411_aY, 0);
      this.setBlockAndMetadata(world, 3, 1, 7, Blocks.field_150411_aY, 0);
      this.setBlockAndMetadata(world, 5, 1, 7, Blocks.field_150411_aY, 0);
      this.setBlockAndMetadata(world, 4, 2, 6, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, 4, 2, 8, Blocks.field_150460_al, 3);
      this.setBlockAndMetadata(world, 3, 2, 7, Blocks.field_150460_al, 5);
      this.setBlockAndMetadata(world, 5, 2, 7, Blocks.field_150460_al, 4);
      this.setBlockAndMetadata(world, 0, 4, 3, LOTRMod.chandelier, 1);
      this.setBlockAndMetadata(world, 0, 4, 11, LOTRMod.chandelier, 1);
      this.setBlockAndMetadata(world, 8, 4, 3, LOTRMod.chandelier, 1);
      this.setBlockAndMetadata(world, 8, 4, 11, LOTRMod.chandelier, 1);
      var15 = new int[]{1, 2};
      men = var15.length;

      for(l = 0; l < men; ++l) {
         i1 = var15[l];
         this.setBlockAndMetadata(world, -4, 1, i1, this.plankBlock, this.plankMeta);
         this.placeMugOrPlate(world, random, -4, 2, i1);
         this.setBlockAndMetadata(world, -6, 1, i1, this.plankStairBlock, 0);
         this.setBlockAndMetadata(world, -2, 1, i1, this.plankStairBlock, 1);
      }

      var15 = new int[]{1, 2, 12, 13};
      men = var15.length;

      for(l = 0; l < men; ++l) {
         i1 = var15[l];
         this.setBlockAndMetadata(world, 2, 1, i1, this.plankBlock, this.plankMeta);
         this.placeMugOrPlate(world, random, 2, 2, i1);
         this.setBlockAndMetadata(world, 3, 1, i1, this.plankBlock, this.plankMeta);
         this.placeMugOrPlate(world, random, 3, 2, i1);
         this.setBlockAndMetadata(world, 5, 1, i1, this.plankStairBlock, 1);
      }

      for(i1 = 6; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, 8, 1, i1, this.plankBlock, this.plankMeta);
         this.placeMugOrPlate(world, random, 8, 2, i1);
         this.setBlockAndMetadata(world, 10, 1, i1, this.plankStairBlock, 1);
      }

      for(i1 = 7; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 1, this.plankStairBlock, 3);
         this.setBlockAndMetadata(world, i1, 1, 13, this.plankStairBlock, 2);
      }

      for(i1 = 2; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 10, 1, i1, this.plankStairBlock, 1);
      }

      for(i1 = 10; i1 <= 12; ++i1) {
         this.setBlockAndMetadata(world, 10, 1, i1, this.plankStairBlock, 1);
      }

      int[] var18;
      for(i1 = 7; i1 <= 8; ++i1) {
         var18 = new int[]{3, 4, 10, 11};
         l = var18.length;

         for(i1 = 0; i1 < l; ++i1) {
            k1 = var18[i1];
            this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);
            this.placeMugOrPlate(world, random, i1, 2, k1);
         }
      }

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -2, i1, 5, this.woodBeamBlock, this.woodBeamMeta);
         this.setBlockAndMetadata(world, -2, i1, 9, this.woodBeamBlock, this.woodBeamMeta);
      }

      for(i1 = -6; i1 <= -3; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 5, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, i1, 3, 5, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 4, 5, this.woodBeamBlock, this.woodBeamMeta | 4);
         this.setBlockAndMetadata(world, i1, 1, 9, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, i1, 3, 9, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 4, 9, this.woodBeamBlock, this.woodBeamMeta | 4);
      }

      for(i1 = 6; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, -2, 1, i1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -2, 3, i1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, -2, 4, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
      }

      this.setBlockAndMetadata(world, -4, 1, 5, this.fenceGateBlock, 0);
      this.placeBarrel(world, random, -6, 2, 5, 3, LOTRFoods.GONDOR_DRINK);
      this.placeMug(world, random, -5, 2, 5, 2, LOTRFoods.GONDOR_DRINK);
      this.placeMug(world, random, -3, 2, 5, 2, LOTRFoods.GONDOR_DRINK);
      this.setBlockAndMetadata(world, -4, 1, 9, this.fenceGateBlock, 2);
      this.placeBarrel(world, random, -6, 2, 9, 2, LOTRFoods.GONDOR_DRINK);
      this.placeMug(world, random, -5, 2, 9, 0, LOTRFoods.GONDOR_DRINK);
      this.placeMug(world, random, -3, 2, 9, 0, LOTRFoods.GONDOR_DRINK);
      this.placeBarrel(world, random, -2, 2, 8, 5, LOTRFoods.GONDOR_DRINK);
      this.placeMug(world, random, -2, 2, 6, 1, LOTRFoods.GONDOR_DRINK);
      this.setBlockAndMetadata(world, -6, 1, 6, this.plankStairBlock, 4);
      this.placePlateWithCertainty(world, random, -6, 2, 6, this.plateBlock, LOTRFoods.GONDOR);
      this.setBlockAndMetadata(world, -6, 1, 7, Blocks.field_150460_al, 4);
      this.setBlockAndMetadata(world, -6, 1, 8, Blocks.field_150383_bp, 3);
      this.placeChest(world, random, -3, 0, 8, 5, LOTRChestContents.GONDOR_HOUSE);

      for(i1 = 6; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, -6, 3, i1, this.plankStairBlock, 4);
         this.placeBarrel(world, random, -6, 4, i1, 4, LOTRFoods.GONDOR_DRINK);
      }

      this.setBlockAndMetadata(world, -4, 4, 7, LOTRMod.chandelier, 1);

      for(i1 = 0; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -3 - i1, 1 + i1, 13, this.plankStairBlock, 0);
         this.setBlockAndMetadata(world, -4 - i1, 1 + i1, 13, this.plankStairBlock, 5);
      }

      this.setBlockAndMetadata(world, -6, 3, 13, this.plankBlock, this.plankMeta);

      for(i1 = 0; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -6, 4 + i1, 12 - i1, this.plankStairBlock, 3);
         this.setBlockAndMetadata(world, -6, 3 + i1, 12 - i1, this.plankStairBlock, 6);
      }

      for(i1 = -6; i1 <= -4; ++i1) {
         this.setAir(world, i1, 5, 13);
      }

      this.setAir(world, -6, 5, 12);

      for(i1 = -5; i1 <= -3; ++i1) {
         this.setBlockAndMetadata(world, i1, 6, 14, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 6, 12, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -3, 6, 13, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -7, 6, 12, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -7, 6, 11, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -5, 6, 11, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -5, 7, 12, Blocks.field_150478_aa, 5);

      for(i1 = -7; i1 <= -3; ++i1) {
         for(men = 10; men <= 14; ++men) {
            if (i1 != -3 || men != 10) {
               if ((i1 >= -4 || men <= 11) && men <= 13) {
                  this.setBlockAndMetadata(world, i1, 10, men, this.wallBlock, this.wallMeta);
               }

               if (i1 >= -5 || men <= 12) {
                  this.setBlockAndMetadata(world, i1, 9, men, this.roofSlabBlock, this.roofSlabMeta | 8);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 4, 7, 6, Blocks.field_150411_aY, 0);
      this.setBlockAndMetadata(world, 4, 7, 8, Blocks.field_150411_aY, 0);
      this.setBlockAndMetadata(world, 3, 7, 7, Blocks.field_150411_aY, 0);
      this.setBlockAndMetadata(world, 5, 7, 7, Blocks.field_150411_aY, 0);
      this.spawnItemFrame(world, 3, 10, 7, 3, this.getGondorFramedItem(random));

      for(i1 = -2; i1 <= 1; ++i1) {
         for(men = 5; men <= 9; ++men) {
            this.setBlockAndMetadata(world, i1, 6, men, Blocks.field_150404_cg, 12);
         }
      }

      for(i1 = -2; i1 <= 6; ++i1) {
         men = IntMath.mod(i1, 4);
         if (men == 2) {
            l = 6;

            label876:
            while(true) {
               if (l > 8) {
                  for(l = 0; l <= 3; ++l) {
                     this.setBlockAndMetadata(world, i1, 9, l, this.woodBeamBlock, this.woodBeamMeta | 8);
                  }

                  l = 11;

                  while(true) {
                     if (l > 14) {
                        break label876;
                     }

                     this.setBlockAndMetadata(world, i1, 9, l, this.woodBeamBlock, this.woodBeamMeta | 8);
                     ++l;
                  }
               }

               this.setBlockAndMetadata(world, i1, l, 3, this.woodBeamBlock, this.woodBeamMeta);

               for(i1 = 0; i1 <= 2; ++i1) {
                  this.setBlockAndMetadata(world, i1, l, i1, this.wallBlock, this.wallMeta);
               }

               this.setBlockAndMetadata(world, i1, l, 11, this.woodBeamBlock, this.woodBeamMeta);

               for(i1 = 12; i1 <= 14; ++i1) {
                  this.setBlockAndMetadata(world, i1, l, i1, this.wallBlock, this.wallMeta);
               }

               ++l;
            }
         } else {
            for(l = 6; l <= 8; ++l) {
               this.setBlockAndMetadata(world, i1, l, 3, this.wallBlock, this.wallMeta);
               this.setBlockAndMetadata(world, i1, l, 11, this.wallBlock, this.wallMeta);
            }

            this.setBlockAndMetadata(world, i1, 9, 3, this.woodBeamBlock, this.woodBeamMeta | 4);
            this.setBlockAndMetadata(world, i1, 9, 11, this.woodBeamBlock, this.woodBeamMeta | 4);

            for(l = 0; l <= 2; ++l) {
               this.setBlockAndMetadata(world, i1, 9, l, this.roofSlabBlock, this.roofSlabMeta | 8);
            }

            for(l = 12; l <= 14; ++l) {
               this.setBlockAndMetadata(world, i1, 9, l, this.roofSlabBlock, this.roofSlabMeta | 8);
            }
         }

         if (men == 0) {
            this.setBlockAndMetadata(world, i1, 6, 3, this.doorBlock, 3);
            this.setBlockAndMetadata(world, i1, 7, 3, this.doorBlock, 8);
            this.setBlockAndMetadata(world, i1, 8, 2, Blocks.field_150478_aa, 4);
            this.setBlockAndMetadata(world, i1, 6, 11, this.doorBlock, 1);
            this.setBlockAndMetadata(world, i1, 7, 11, this.doorBlock, 8);
            this.setBlockAndMetadata(world, i1, 8, 12, Blocks.field_150478_aa, 3);
         }

         if (men == 3) {
            this.setBlockAndMetadata(world, i1, 6, 1, this.bedBlock, 0);
            this.setBlockAndMetadata(world, i1, 6, 2, this.bedBlock, 8);
            this.setBlockAndMetadata(world, i1, 6, 0, Blocks.field_150486_ae, 4);
            this.setBlockAndMetadata(world, i1, 6, 13, this.bedBlock, 2);
            this.setBlockAndMetadata(world, i1, 6, 12, this.bedBlock, 10);
            this.setBlockAndMetadata(world, i1, 6, 14, Blocks.field_150486_ae, 4);
         }

         if (men == 1) {
            this.setBlockAndMetadata(world, i1, 6, 2, this.plankStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 6, 0, this.plankBlock, this.plankMeta);
            this.placeMug(world, random, i1, 7, 0, 2, LOTRFoods.GONDOR_DRINK);
            this.setBlockAndMetadata(world, i1, 6, 12, this.plankStairBlock, 3);
            this.setBlockAndMetadata(world, i1, 6, 14, this.plankBlock, this.plankMeta);
            this.placeMug(world, random, i1, 7, 14, 0, LOTRFoods.GONDOR_DRINK);
         }

         for(l = 1; l <= 3; ++l) {
            this.setBlockAndMetadata(world, i1, 10, l, this.wallBlock, this.wallMeta);
         }

         for(l = 11; l <= 13; ++l) {
            this.setBlockAndMetadata(world, i1, 10, l, this.wallBlock, this.wallMeta);
         }
      }

      for(i1 = 5; i1 <= 9; ++i1) {
         men = IntMath.mod(i1, 4);
         if (men == 1) {
            l = 6;

            label814:
            while(true) {
               if (l > 8) {
                  for(l = -7; l <= -4; ++l) {
                     this.setBlockAndMetadata(world, l, 9, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
                  }

                  l = 8;

                  while(true) {
                     if (l > 11) {
                        break label814;
                     }

                     this.setBlockAndMetadata(world, l, 9, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
                     ++l;
                  }
               }

               this.setBlockAndMetadata(world, -4, l, i1, this.woodBeamBlock, this.woodBeamMeta);

               for(i1 = -7; i1 <= -5; ++i1) {
                  this.setBlockAndMetadata(world, i1, l, i1, this.wallBlock, this.wallMeta);
               }

               this.setBlockAndMetadata(world, 8, l, i1, this.woodBeamBlock, this.woodBeamMeta);

               for(i1 = 9; i1 <= 11; ++i1) {
                  this.setBlockAndMetadata(world, i1, l, i1, this.wallBlock, this.wallMeta);
               }

               ++l;
            }
         } else {
            for(l = 6; l <= 8; ++l) {
               this.setBlockAndMetadata(world, -4, l, i1, this.wallBlock, this.wallMeta);
               this.setBlockAndMetadata(world, 8, l, i1, this.wallBlock, this.wallMeta);
            }

            this.setBlockAndMetadata(world, -4, 9, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
            this.setBlockAndMetadata(world, 8, 9, i1, this.woodBeamBlock, this.woodBeamMeta | 8);

            for(l = -7; l <= -5; ++l) {
               this.setBlockAndMetadata(world, l, 9, i1, this.roofSlabBlock, this.roofSlabMeta | 8);
            }

            for(l = 9; l <= 11; ++l) {
               this.setBlockAndMetadata(world, l, 9, i1, this.roofSlabBlock, this.roofSlabMeta | 8);
            }
         }

         if (men == 3) {
            this.setBlockAndMetadata(world, -4, 6, i1, this.doorBlock, 0);
            this.setBlockAndMetadata(world, -4, 7, i1, this.doorBlock, 8);
            this.setBlockAndMetadata(world, -5, 8, i1, Blocks.field_150478_aa, 1);
            this.setBlockAndMetadata(world, 8, 6, i1, this.doorBlock, 2);
            this.setBlockAndMetadata(world, 8, 7, i1, this.doorBlock, 8);
            this.setBlockAndMetadata(world, 9, 8, i1, Blocks.field_150478_aa, 2);
         }

         if (men == 0) {
            this.setBlockAndMetadata(world, -6, 6, i1, this.bedBlock, 1);
            this.setBlockAndMetadata(world, -5, 6, i1, this.bedBlock, 9);
            this.setBlockAndMetadata(world, -7, 6, i1, Blocks.field_150486_ae, 2);
            this.setBlockAndMetadata(world, 10, 6, i1, this.bedBlock, 3);
            this.setBlockAndMetadata(world, 9, 6, i1, this.bedBlock, 11);
            this.setBlockAndMetadata(world, 11, 6, i1, Blocks.field_150486_ae, 2);
         }

         if (men == 2) {
            this.setBlockAndMetadata(world, -5, 6, i1, this.plankStairBlock, 1);
            this.setBlockAndMetadata(world, -7, 6, i1, this.plankBlock, this.plankMeta);
            this.placeMug(world, random, -7, 7, i1, 3, LOTRFoods.GONDOR_DRINK);
            this.setBlockAndMetadata(world, 9, 6, i1, this.plankStairBlock, 0);
            this.setBlockAndMetadata(world, 11, 6, i1, this.plankBlock, this.plankMeta);
            this.placeMug(world, random, 11, 7, i1, 1, LOTRFoods.GONDOR_DRINK);
         }

         for(l = -7; l <= -4; ++l) {
            this.setBlockAndMetadata(world, l, 10, i1, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, l, 11, i1, this.wallBlock, this.wallMeta);
         }

         for(l = 8; l <= 11; ++l) {
            this.setBlockAndMetadata(world, l, 10, i1, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, l, 11, i1, this.wallBlock, this.wallMeta);
         }
      }

      for(i1 = 7; i1 <= 8; ++i1) {
         for(men = 10; men <= 11; ++men) {
            if (i1 != 7 || men != 10) {
               for(l = 6; l <= 8; ++l) {
                  this.setBlockAndMetadata(world, i1, l, men, this.wallBlock, this.wallMeta);
               }

               this.setBlockAndMetadata(world, i1, 9, men, this.woodBeamBlock, this.woodBeamMeta | (i1 == 8 ? 8 : 4));
               this.setBlockAndMetadata(world, i1, 10, men, this.wallBlock, this.wallMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 8, 6, 10, this.doorBlock, 2);
      this.setBlockAndMetadata(world, 8, 7, 10, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 9, 8, 10, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 7, 8, 13, Blocks.field_150478_aa, 2);

      for(i1 = 7; i1 <= 8; ++i1) {
         for(men = 12; men <= 13; ++men) {
            this.setBlockAndMetadata(world, i1, 10, men, this.wallBlock, this.wallMeta);
         }
      }

      for(i1 = 9; i1 <= 11; ++i1) {
         for(men = 10; men <= 11; ++men) {
            this.setBlockAndMetadata(world, i1, 10, men, this.wallBlock, this.wallMeta);
         }
      }

      for(i1 = 7; i1 <= 9; ++i1) {
         for(men = 12; men <= 14; ++men) {
            this.setBlockAndMetadata(world, i1, 9, men, this.roofSlabBlock, this.roofSlabMeta | 8);
         }
      }

      for(i1 = 9; i1 <= 11; ++i1) {
         for(men = 10; men <= 12; ++men) {
            this.setBlockAndMetadata(world, i1, 9, men, this.roofSlabBlock, this.roofSlabMeta | 8);
         }
      }

      this.setBlockAndMetadata(world, 11, 6, 11, this.bedBlock, 0);
      this.setBlockAndMetadata(world, 11, 6, 12, this.bedBlock, 8);
      this.setBlockAndMetadata(world, 11, 6, 10, Blocks.field_150486_ae, 5);
      this.setBlockAndMetadata(world, 7, 6, 13, this.bedBlock, 2);
      this.setBlockAndMetadata(world, 7, 6, 12, this.bedBlock, 10);
      this.setBlockAndMetadata(world, 7, 6, 14, Blocks.field_150486_ae, 4);
      this.setBlockAndMetadata(world, 9, 6, 14, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, 9, 7, 14, 0, LOTRFoods.GONDOR_DRINK);
      this.setBlockAndMetadata(world, 10, 6, 13, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, 10, 7, 13, 1, LOTRFoods.GONDOR_DRINK);

      for(i1 = 7; i1 <= 8; ++i1) {
         for(men = 3; men <= 4; ++men) {
            if (i1 != 7 || men != 4) {
               for(l = 6; l <= 8; ++l) {
                  this.setBlockAndMetadata(world, i1, l, men, this.wallBlock, this.wallMeta);
               }

               this.setBlockAndMetadata(world, i1, 9, men, this.woodBeamBlock, this.woodBeamMeta | (i1 == 8 ? 8 : 4));
               this.setBlockAndMetadata(world, i1, 10, men, this.wallBlock, this.wallMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 8, 6, 4, this.doorBlock, 2);
      this.setBlockAndMetadata(world, 8, 7, 4, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 9, 8, 4, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 7, 8, 1, Blocks.field_150478_aa, 2);

      for(i1 = 7; i1 <= 8; ++i1) {
         for(men = 1; men <= 2; ++men) {
            this.setBlockAndMetadata(world, i1, 10, men, this.wallBlock, this.wallMeta);
         }
      }

      for(i1 = 9; i1 <= 11; ++i1) {
         for(men = 3; men <= 4; ++men) {
            this.setBlockAndMetadata(world, i1, 10, men, this.wallBlock, this.wallMeta);
         }
      }

      for(i1 = 7; i1 <= 9; ++i1) {
         for(men = 0; men <= 2; ++men) {
            this.setBlockAndMetadata(world, i1, 9, men, this.roofSlabBlock, this.roofSlabMeta | 8);
         }
      }

      for(i1 = 9; i1 <= 11; ++i1) {
         for(men = 2; men <= 4; ++men) {
            this.setBlockAndMetadata(world, i1, 9, men, this.roofSlabBlock, this.roofSlabMeta | 8);
         }
      }

      this.setBlockAndMetadata(world, 11, 6, 3, this.bedBlock, 2);
      this.setBlockAndMetadata(world, 11, 6, 2, this.bedBlock, 10);
      this.setBlockAndMetadata(world, 11, 6, 4, Blocks.field_150486_ae, 5);
      this.setBlockAndMetadata(world, 7, 6, 1, this.bedBlock, 0);
      this.setBlockAndMetadata(world, 7, 6, 2, this.bedBlock, 8);
      this.setBlockAndMetadata(world, 7, 6, 0, Blocks.field_150486_ae, 4);
      this.setBlockAndMetadata(world, 9, 6, 0, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, 9, 7, 0, 2, LOTRFoods.GONDOR_DRINK);
      this.setBlockAndMetadata(world, 10, 6, 1, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, 10, 7, 1, 1, LOTRFoods.GONDOR_DRINK);

      for(i1 = -4; i1 <= -3; ++i1) {
         for(men = 3; men <= 4; ++men) {
            if (i1 != -3 || men != 4) {
               for(l = 6; l <= 8; ++l) {
                  this.setBlockAndMetadata(world, i1, l, men, this.wallBlock, this.wallMeta);
               }

               this.setBlockAndMetadata(world, i1, 9, men, this.woodBeamBlock, this.woodBeamMeta | (i1 == -4 ? 8 : 4));
               this.setBlockAndMetadata(world, i1, 10, men, this.wallBlock, this.wallMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, -4, 6, 4, this.doorBlock, 0);
      this.setBlockAndMetadata(world, -4, 7, 4, this.doorBlock, 8);
      this.setBlockAndMetadata(world, -5, 8, 4, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -3, 8, 1, Blocks.field_150478_aa, 1);

      for(i1 = -4; i1 <= -3; ++i1) {
         for(men = 1; men <= 2; ++men) {
            this.setBlockAndMetadata(world, i1, 10, men, this.wallBlock, this.wallMeta);
         }
      }

      for(i1 = -7; i1 <= -5; ++i1) {
         for(men = 3; men <= 4; ++men) {
            this.setBlockAndMetadata(world, i1, 10, men, this.wallBlock, this.wallMeta);
         }
      }

      for(i1 = -5; i1 <= -3; ++i1) {
         for(men = 0; men <= 2; ++men) {
            this.setBlockAndMetadata(world, i1, 9, men, this.roofSlabBlock, this.roofSlabMeta | 8);
         }
      }

      for(i1 = -7; i1 <= -5; ++i1) {
         for(men = 2; men <= 4; ++men) {
            this.setBlockAndMetadata(world, i1, 9, men, this.roofSlabBlock, this.roofSlabMeta | 8);
         }
      }

      this.setBlockAndMetadata(world, -7, 6, 3, this.bedBlock, 2);
      this.setBlockAndMetadata(world, -7, 6, 2, this.bedBlock, 10);
      this.setBlockAndMetadata(world, -7, 6, 4, Blocks.field_150486_ae, 4);
      this.setBlockAndMetadata(world, -3, 6, 1, this.bedBlock, 0);
      this.setBlockAndMetadata(world, -3, 6, 2, this.bedBlock, 8);
      this.setBlockAndMetadata(world, -3, 6, 0, Blocks.field_150486_ae, 5);
      this.setBlockAndMetadata(world, -5, 6, 0, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, -5, 7, 0, 2, LOTRFoods.GONDOR_DRINK);
      this.setBlockAndMetadata(world, -6, 6, 1, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, -6, 7, 1, 3, LOTRFoods.GONDOR_DRINK);

      for(i1 = -3; i1 <= 7; ++i1) {
         var18 = new int[]{5, 9};
         l = var18.length;

         for(i1 = 0; i1 < l; ++i1) {
            k1 = var18[i1];
            this.setBlockAndMetadata(world, i1, 11, k1, this.roofSlabBlock, this.roofSlabMeta | 8);
         }
      }

      this.setBlockAndMetadata(world, -1, 11, 7, LOTRMod.chandelier, 1);
      this.setBlockAndMetadata(world, 7, 11, 7, LOTRMod.chandelier, 1);
      LOTREntityGondorMan bartender = new LOTREntityGondorBartender(world);
      bartender.setSpecificLocationName(this.tavernNameNPC);
      this.spawnNPCAndSetHome(bartender, world, -4, 1, 7, 2);
      men = 6 + random.nextInt(7);

      for(l = 0; l < men; ++l) {
         LOTREntityGondorMan gondorian = new LOTREntityGondorMan(world);
         this.spawnNPCAndSetHome(gondorian, world, 2, 1, 7, 16);
      }

      return true;
   }

   private void placeMugOrPlate(World world, Random random, int i, int j, int k) {
      if (random.nextBoolean()) {
         this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.GONDOR_DRINK);
      } else {
         this.placePlate(world, random, i, j, k, this.plateBlock, LOTRFoods.GONDOR);
      }

   }
}
