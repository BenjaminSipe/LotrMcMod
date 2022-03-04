package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingLargeTownHouse extends LOTRWorldGenEasterlingStructureTown {
   public LOTRWorldGenEasterlingLargeTownHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 9);
      this.setupRandomBlocks(random);
      int k1;
      int l;
      int j1;
      int i1;
      int k1;
      if (this.restrictions) {
         k1 = 0;
         l = 0;

         for(j1 = -7; j1 <= 7; ++j1) {
            for(i1 = -9; i1 <= 9; ++i1) {
               k1 = this.getTopBlock(world, j1, i1) - 1;
               if (!this.isSurface(world, j1, k1, i1)) {
                  return false;
               }

               if (k1 < k1) {
                  k1 = k1;
               }

               if (k1 > l) {
                  l = k1;
               }

               if (l - k1 > 8) {
                  return false;
               }
            }
         }
      }

      for(k1 = -6; k1 <= 6; ++k1) {
         for(l = -8; l <= 8; ++l) {
            j1 = Math.abs(k1);
            i1 = Math.abs(l);
            if (i1 == 8 && j1 % 4 == 2 || j1 == 6 && i1 % 4 == 0) {
               for(k1 = 4; (k1 >= 0 || !this.isOpaque(world, k1, k1, l)) && this.getY(k1) >= 0; --k1) {
                  this.setBlockAndMetadata(world, k1, k1, l, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, k1, k1 - 1, l);
               }
            } else if (j1 != 6 && i1 != 8) {
               for(k1 = 0; (k1 >= 0 || !this.isOpaque(world, k1, k1, l)) && this.getY(k1) >= 0; --k1) {
                  if (IntMath.mod(k1, 2) == 1 && IntMath.mod(l, 2) == 1) {
                     this.setBlockAndMetadata(world, k1, k1, l, this.pillarRedBlock, this.pillarRedMeta);
                  } else {
                     this.setBlockAndMetadata(world, k1, k1, l, this.brickRedBlock, this.brickRedMeta);
                  }

                  this.setGrassToDirt(world, k1, k1 - 1, l);
               }

               for(k1 = 1; k1 <= 14; ++k1) {
                  this.setAir(world, k1, k1, l);
               }
            } else {
               for(k1 = 3; (k1 >= 0 || !this.isOpaque(world, k1, k1, l)) && this.getY(k1) >= 0; --k1) {
                  this.setBlockAndMetadata(world, k1, k1, l, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, k1, k1 - 1, l);
               }

               if (i1 == 8) {
                  this.setBlockAndMetadata(world, k1, 4, l, this.woodBeamBlock, this.woodBeamMeta | 4);
               } else if (j1 == 6) {
                  this.setBlockAndMetadata(world, k1, 4, l, this.woodBeamBlock, this.woodBeamMeta | 8);
               }
            }
         }
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         for(l = -3; l <= -1; ++l) {
            this.setBlockAndMetadata(world, k1, 0, l, this.brickGoldBlock, this.brickGoldMeta);
         }
      }

      int[] var16 = new int[]{-4, 0, 4};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];

         for(k1 = -5; k1 <= 5; ++k1) {
            this.setBlockAndMetadata(world, k1, 0, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
         }
      }

      var16 = new int[]{-2, 2};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];

         for(k1 = -7; k1 <= 7; ++k1) {
            this.setBlockAndMetadata(world, i1, 0, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
         }
      }

      var16 = new int[]{-4, 4};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, i1 - 1, 2, -8, this.brickStairBlock, 4);
         this.setAir(world, i1, 2, -8);
         this.setBlockAndMetadata(world, i1 + 1, 2, -8, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, i1, 3, -8, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, i1 - 1, 2, 8, this.brickStairBlock, 4);
         this.setAir(world, i1, 2, 8);
         this.setBlockAndMetadata(world, i1 + 1, 2, 8, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, i1, 3, 8, this.brickStairBlock, 7);
      }

      var16 = new int[]{-6, -2, 2, 6};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, -6, 2, i1 - 1, this.brickStairBlock, 7);
         this.setAir(world, -6, 2, i1);
         this.setBlockAndMetadata(world, -6, 2, i1 + 1, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, -6, 3, i1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, 6, 2, i1 - 1, this.brickStairBlock, 7);
         this.setAir(world, 6, 2, i1);
         this.setBlockAndMetadata(world, 6, 2, i1 + 1, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, 6, 3, i1, this.brickStairBlock, 4);
      }

      var16 = new int[]{-9, 9};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, -6, 3, i1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 6, 3, i1, this.fenceBlock, this.fenceMeta);
      }

      var16 = new int[]{-7, 7};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, i1, 3, -8, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 3, 8, this.fenceBlock, this.fenceMeta);
      }

      var16 = new int[]{-2, 2};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, i1, 3, -9, Blocks.field_150478_aa, 4);
         this.setBlockAndMetadata(world, i1, 3, 9, Blocks.field_150478_aa, 3);
      }

      var16 = new int[]{-4, 0, 4};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, -7, 3, i1, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, 7, 3, i1, Blocks.field_150478_aa, 2);
      }

      for(k1 = -5; k1 <= 5; ++k1) {
         for(l = -7; l <= 7; ++l) {
            this.setBlockAndMetadata(world, k1, 4, l, this.plankSlabBlock, this.plankSlabMeta | 8);
         }
      }

      var16 = new int[]{-4, 4};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];

         for(k1 = -5; k1 <= 5; ++k1) {
            this.setBlockAndMetadata(world, k1, 4, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
         }
      }

      var16 = new int[]{-2, 2};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];

         for(k1 = -7; k1 <= 7; ++k1) {
            if (k1 <= -5 || k1 >= 5) {
               this.setBlockAndMetadata(world, i1, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
            }
         }
      }

      var16 = new int[]{-2, 2};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];

         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, -4, this.woodBeamBlock, this.woodBeamMeta);
         }

         for(k1 = 1; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, 0, this.woodBeamBlock, this.woodBeamMeta);
         }
      }

      for(k1 = 0; k1 <= 4; ++k1) {
         l = 1 + k1;
         j1 = -1 + k1;

         for(i1 = -1; i1 <= 1; ++i1) {
            this.setAir(world, i1, 4, j1);
            if (k1 <= 3) {
               this.setBlockAndMetadata(world, i1, l, j1, this.plankStairBlock, 2);
            }

            for(k1 = l - 1; k1 >= 1; --k1) {
               this.setBlockAndMetadata(world, i1, k1, j1, this.plankBlock, this.plankMeta);
            }
         }
      }

      for(k1 = -1; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, -2, 5, k1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 2, 5, k1, this.fenceBlock, this.fenceMeta);
      }

      for(k1 = -2; k1 <= 2; ++k1) {
         this.setBlockAndMetadata(world, k1, 5, -2, this.fenceBlock, this.fenceMeta);
      }

      var16 = new int[]{-2, 2};
      l = var16.length;

      int j1;
      int i1;
      int i1;
      int[] var17;
      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         var17 = new int[]{0, 4};
         j1 = var17.length;

         for(i1 = 0; i1 < j1; ++i1) {
            i1 = var17[i1];
            this.setBlockAndMetadata(world, i1, 5, i1, this.woodBeamBlock, this.woodBeamMeta);
            this.setBlockAndMetadata(world, i1, 6, i1, this.plankSlabBlock, this.plankSlabMeta);
         }
      }

      for(k1 = -5; k1 <= 5; ++k1) {
         for(l = -7; l <= 7; ++l) {
            j1 = Math.abs(k1);
            i1 = Math.abs(l);
            if ((j1 != 5 || i1 != 0 && i1 != 4 && i1 != 7) && (i1 != 7 || j1 != 2)) {
               if (j1 == 5 || i1 == 7) {
                  for(k1 = 5; k1 <= 7; ++k1) {
                     this.setBlockAndMetadata(world, k1, k1, l, this.brickBlock, this.brickMeta);
                  }

                  if (i1 == 7) {
                     this.setBlockAndMetadata(world, k1, 8, l, this.woodBeamBlock, this.woodBeamMeta | 4);
                  } else if (j1 == 5) {
                     this.setBlockAndMetadata(world, k1, 8, l, this.woodBeamBlock, this.woodBeamMeta | 8);
                  }
               }
            } else {
               for(k1 = 5; k1 <= 8; ++k1) {
                  this.setBlockAndMetadata(world, k1, k1, l, this.woodBeamBlock, this.woodBeamMeta);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 6, -7, LOTRMod.reedBars, 0);
      var16 = new int[]{-5, 5};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, i1, 6, -2, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, i1, 6, 2, LOTRMod.reedBars, 0);
      }

      var16 = new int[]{-8, 8};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, -5, 7, i1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 5, 7, i1, this.fenceBlock, this.fenceMeta);
      }

      var16 = new int[]{-6, 6};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, i1, 7, -7, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 7, 7, this.fenceBlock, this.fenceMeta);
      }

      var16 = new int[]{-2, 2};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, i1, 7, -8, Blocks.field_150478_aa, 4);
         this.setBlockAndMetadata(world, i1, 7, 8, Blocks.field_150478_aa, 3);
      }

      var16 = new int[]{-4, 0, 4};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, -6, 7, i1, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, 6, 7, i1, Blocks.field_150478_aa, 2);
      }

      for(k1 = -6; k1 <= 6; ++k1) {
         this.setBlockAndMetadata(world, k1, 5, -8, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, k1, 5, 8, this.roofStairBlock, 3);
      }

      for(k1 = -7; k1 <= 7; ++k1) {
         this.setBlockAndMetadata(world, -6, 5, k1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 6, 5, k1, this.roofStairBlock, 0);
      }

      var16 = new int[]{-9, 9};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, -7, 5, i1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, -6, 4, i1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, -5, 4, i1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, -4, 5, i1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, -3, 4, i1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, -2, 4, i1, this.roofStairBlock, i1 > 0 ? 3 : 2);
         this.setBlockAndMetadata(world, -1, 4, i1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, 0, 5, i1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, 1, 4, i1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, 2, 4, i1, this.roofStairBlock, i1 > 0 ? 3 : 2);
         this.setBlockAndMetadata(world, 3, 4, i1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, 4, 5, i1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, 5, 4, i1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, 6, 4, i1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, 7, 5, i1, this.roofSlabBlock, this.roofSlabMeta);
      }

      var16 = new int[]{-7, 7};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         var17 = new int[]{-4, 0, 4};
         j1 = var17.length;

         for(i1 = 0; i1 < j1; ++i1) {
            i1 = var17[i1];
            this.setBlockAndMetadata(world, i1, 4, i1 - 1, this.roofStairBlock, 6);
            this.setBlockAndMetadata(world, i1, 4, i1, this.roofStairBlock, i1 > 0 ? 0 : 1);
            this.setBlockAndMetadata(world, i1, 4, i1 + 1, this.roofStairBlock, 7);
         }

         var17 = new int[]{-6, -2, 2, 6};
         j1 = var17.length;

         for(i1 = 0; i1 < j1; ++i1) {
            i1 = var17[i1];
            this.setBlockAndMetadata(world, i1, 5, i1, this.roofSlabBlock, this.roofSlabMeta);
         }

         this.setBlockAndMetadata(world, i1, 4, -8, this.roofStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 4, -7, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 4, 7, this.roofStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 4, 8, this.roofStairBlock, 7);
      }

      var16 = new int[]{-8, 8};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, -6, 9, i1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, -5, 8, i1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, -4, 8, i1, this.roofStairBlock, i1 > 0 ? 3 : 2);
         this.setBlockAndMetadata(world, -3, 8, i1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, -2, 8, i1, this.roofStairBlock, i1 > 0 ? 3 : 2);
         this.setBlockAndMetadata(world, -1, 8, i1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, 0, 9, i1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, 1, 8, i1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, 2, 8, i1, this.roofStairBlock, i1 > 0 ? 3 : 2);
         this.setBlockAndMetadata(world, 3, 8, i1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, 4, 8, i1, this.roofStairBlock, i1 > 0 ? 3 : 2);
         this.setBlockAndMetadata(world, 5, 8, i1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, 6, 9, i1, this.roofSlabBlock, this.roofSlabMeta);
      }

      var16 = new int[]{-6, 6};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, i1, 8, -7, this.roofStairBlock, 6);

         for(k1 = -6; k1 <= -4; ++k1) {
            this.setBlockAndMetadata(world, i1, 8, k1, this.roofStairBlock, i1 > 0 ? 0 : 1);
         }

         this.setBlockAndMetadata(world, i1, 8, -3, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 9, -2, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 8, -1, this.roofStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 9, 0, this.roofStairBlock, i1 > 0 ? 0 : 1);
         this.setBlockAndMetadata(world, i1, 8, 1, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 9, 2, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 8, 3, this.roofStairBlock, 6);

         for(k1 = 4; k1 <= 6; ++k1) {
            this.setBlockAndMetadata(world, i1, 8, k1, this.roofStairBlock, i1 > 0 ? 0 : 1);
         }

         this.setBlockAndMetadata(world, i1, 8, 7, this.roofStairBlock, 7);
      }

      for(k1 = -7; k1 <= 7; ++k1) {
         for(l = 0; l <= 4; ++l) {
            j1 = 9 + l;
            this.setBlockAndMetadata(world, -5 + l, j1, k1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 5 - l, j1, k1, this.roofStairBlock, 0);
            if (l > 0) {
               this.setBlockAndMetadata(world, -5 + l, j1 - 1, k1, this.roofStairBlock, 4);
               this.setBlockAndMetadata(world, 5 - l, j1 - 1, k1, this.roofStairBlock, 5);
            }
         }

         this.setBlockAndMetadata(world, 0, 13, k1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 0, 14, k1, this.roofSlabBlock, this.roofSlabMeta);
      }

      this.setBlockAndMetadata(world, 0, 13, -8, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 0, 14, -8, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 13, 8, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 0, 14, 8, this.roofStairBlock, 2);
      var16 = new int[]{-7, 7};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         var17 = new int[]{-2, 2};
         j1 = var17.length;

         for(i1 = 0; i1 < j1; ++i1) {
            i1 = var17[i1];

            for(int j1 = 5; j1 <= 11; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, i1, this.woodBeamBlock, this.woodBeamMeta);
            }
         }
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         this.setBlockAndMetadata(world, k1, 8, -6, this.plankStairBlock, 7);
         this.setBlockAndMetadata(world, k1, 8, 6, this.plankStairBlock, 6);
      }

      var16 = new int[]{-6, 6};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];

         for(k1 = 0; k1 <= 3; ++k1) {
            j1 = 9 + k1;

            for(i1 = -4 + k1; i1 <= 4 - k1; ++i1) {
               this.setBlockAndMetadata(world, i1, j1, i1, this.plankBlock, this.plankMeta);
            }
         }
      }

      var16 = new int[]{-4, 4};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];

         for(k1 = -4; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, k1, 8, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
         }
      }

      var16 = new int[]{-2, 2};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];

         for(k1 = -6; k1 <= 6; ++k1) {
            this.setBlockAndMetadata(world, i1, 8, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
         }
      }

      this.setBlockAndMetadata(world, 0, 0, -8, this.woodBeamBlock, this.woodBeamMeta | 4);
      this.setBlockAndMetadata(world, 0, 1, -8, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -8, this.doorBlock, 8);
      this.setBlockAndMetadata(world, -2, 3, -5, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 3, -5, Blocks.field_150478_aa, 4);
      this.spawnItemFrame(world, -2, 2, -4, 1, this.getEasterlingFramedItem(random));
      this.spawnItemFrame(world, 2, 2, -4, 3, this.getEasterlingFramedItem(random));
      this.setBlockAndMetadata(world, -3, 1, -6, this.plankStairBlock, 1);
      this.setBlockAndMetadata(world, 3, 1, -6, this.plankStairBlock, 0);

      for(k1 = -7; k1 <= -5; ++k1) {
         this.setBlockAndMetadata(world, -5, 1, k1, this.plankStairBlock, 4);
         if (random.nextBoolean()) {
            this.placePlate(world, random, -5, 2, k1, this.plateBlock, LOTRFoods.RHUN);
         } else {
            this.placeMug(world, random, -5, 2, k1, 3, LOTRFoods.RHUN_DRINK);
         }

         this.setBlockAndMetadata(world, 5, 1, k1, this.plankStairBlock, 5);
         if (random.nextBoolean()) {
            this.placePlate(world, random, 5, 2, k1, this.plateBlock, LOTRFoods.RHUN);
         } else {
            this.placeMug(world, random, 5, 2, k1, 1, LOTRFoods.RHUN_DRINK);
         }
      }

      this.spawnItemFrame(world, -6, 2, 0, 1, this.getEasterlingFramedItem(random));
      this.spawnItemFrame(world, 6, 2, 0, 3, this.getEasterlingFramedItem(random));
      this.setBlockAndMetadata(world, -2, 1, 1, Blocks.field_150486_ae, 5);
      this.setBlockAndMetadata(world, -2, 1, 2, Blocks.field_150486_ae, 5);
      this.setBlockAndMetadata(world, 2, 1, 1, Blocks.field_150486_ae, 4);
      this.setBlockAndMetadata(world, 2, 1, 2, Blocks.field_150486_ae, 4);
      this.setBlockAndMetadata(world, -2, 1, 3, this.plankStairBlock, 5);
      this.placeBarrel(world, random, -2, 2, 3, 5, LOTRFoods.RHUN_DRINK);
      this.setBlockAndMetadata(world, 2, 1, 3, this.plankStairBlock, 4);
      this.placeBarrel(world, random, 2, 2, 3, 4, LOTRFoods.RHUN_DRINK);
      this.setBlockAndMetadata(world, -2, 3, 4, LOTRMod.chandelier, 3);
      this.setBlockAndMetadata(world, 2, 3, 4, LOTRMod.chandelier, 3);
      this.setBlockAndMetadata(world, 0, 1, 3, this.tableBlock, 0);
      this.setAir(world, 0, 2, 3);
      this.setBlockAndMetadata(world, 0, 3, 3, this.plankStairBlock, 7);

      for(k1 = -1; k1 <= 1; ++k1) {
         for(l = 5; l <= 7; ++l) {
            this.setBlockAndMetadata(world, k1, 0, l, this.brickBlock, this.brickMeta);
         }

         for(l = 7; l <= 8; ++l) {
            for(j1 = 1; j1 <= 4; ++j1) {
               this.setBlockAndMetadata(world, k1, j1, l, this.brickBlock, this.brickMeta);
            }
         }

         int k1 = 9;

         for(j1 = 4; (j1 >= 0 || !this.isOpaque(world, k1, j1, k1)) && this.getY(j1) >= 0; --j1) {
            this.setBlockAndMetadata(world, k1, j1, k1, this.brickBlock, this.brickMeta);
            this.setGrassToDirt(world, k1, j1 - 1, k1);
         }

         this.setBlockAndMetadata(world, k1, 5, 9, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, k1, 4, 8, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, k1, 5, 8, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, k1, 6, 8, this.brickStairBlock, 3);
      }

      this.setBlockAndMetadata(world, -1, 1, 7, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, -1, 2, 7, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, -1, 3, 7, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, 1, 1, 7, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 1, 2, 7, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 3, 7, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 0, 7, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 0, 1, 7, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 0, 2, 7, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, 0, 0, 8, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 0, 1, 8, Blocks.field_150480_ab, 0);
      this.setAir(world, 0, 2, 8);
      this.setBlockAndMetadata(world, -5, 1, 5, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, -5, 1, 6, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -5, 1, 7, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -4, 1, 7, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, -3, 1, 7, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, 5, 1, 5, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, 5, 1, 6, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, 5, 1, 7, this.plankStairBlock, 5);
      this.setBlockAndMetadata(world, 4, 1, 7, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, 3, 1, 7, this.plankStairBlock, 6);

      for(k1 = 3; k1 <= 5; ++k1) {
         if (random.nextBoolean()) {
            this.placePlate(world, random, k1, 2, 7, this.plateBlock, LOTRFoods.RHUN);
         } else {
            this.placeMug(world, random, k1, 2, 7, 0, LOTRFoods.RHUN_DRINK);
         }
      }

      for(k1 = 5; k1 <= 6; ++k1) {
         if (random.nextBoolean()) {
            this.placePlate(world, random, 5, 2, k1, this.plateBlock, LOTRFoods.RHUN);
         } else {
            this.placeMug(world, random, 5, 2, k1, 1, LOTRFoods.RHUN_DRINK);
         }
      }

      this.placeWallBanner(world, 0, 7, 7, this.bannerType, 2);
      this.setBlockAndMetadata(world, -2, 7, 4, LOTRMod.chandelier, 3);
      this.setBlockAndMetadata(world, 2, 7, 4, LOTRMod.chandelier, 3);
      this.placeArmorStand(world, -4, 5, 6, 0, (ItemStack[])null);
      this.placeArmorStand(world, 4, 5, 6, 0, (ItemStack[])null);
      this.setBlockAndMetadata(world, -2, 7, -4, LOTRMod.chandelier, 3);
      this.setBlockAndMetadata(world, 2, 7, -4, LOTRMod.chandelier, 3);
      var16 = new int[]{-4, 0, 4};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, i1, 5, -5, this.bedBlock, 2);
         this.setBlockAndMetadata(world, i1, 5, -6, this.bedBlock, 10);
      }

      var16 = new int[]{-2, 2};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.placeChest(world, random, i1, 5, -6, 3, this.chestContents);
      }

      var16 = new int[]{-3, -1, 1, 3};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, i1, 5, -6, this.plankStairBlock, 7);
         if (random.nextBoolean()) {
            this.placePlate(world, random, i1, 6, -6, this.plateBlock, LOTRFoods.RHUN);
         } else {
            this.placeMug(world, random, i1, 6, -6, 2, LOTRFoods.RHUN_DRINK);
         }
      }

      this.placeWeaponRack(world, 0, 7, -6, 4, this.getEasterlingWeaponItem(random));
      if (random.nextBoolean()) {
         this.spawnItemFrame(world, -2, 7, -7, 0, new ItemStack(Items.field_151113_aN));
      } else {
         this.spawnItemFrame(world, 2, 7, -7, 0, new ItemStack(Items.field_151113_aN));
      }

      int men = 2;

      for(l = 0; l < men; ++l) {
         LOTREntityEasterling easterling = new LOTREntityEasterling(world);
         this.spawnNPCAndSetHome(easterling, world, 0, 5, 3, 16);
      }

      return true;
   }
}
