package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingTownHouse extends LOTRWorldGenEasterlingStructureTown {
   public LOTRWorldGenEasterlingTownHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 7);
      this.setupRandomBlocks(random);
      int men;
      int l;
      int j1;
      int i1;
      int i1;
      if (this.restrictions) {
         men = 0;
         l = 0;

         for(j1 = -5; j1 <= 5; ++j1) {
            for(i1 = -8; i1 <= 8; ++i1) {
               i1 = this.getTopBlock(world, j1, i1) - 1;
               if (!this.isSurface(world, j1, i1, i1)) {
                  return false;
               }

               if (i1 < men) {
                  men = i1;
               }

               if (i1 > l) {
                  l = i1;
               }

               if (l - men > 8) {
                  return false;
               }
            }
         }
      }

      for(men = -4; men <= 4; ++men) {
         for(l = -6; l <= 6; ++l) {
            j1 = Math.abs(men);
            i1 = Math.abs(l);
            if (j1 == 4 && (i1 == 2 || i1 == 6) || j1 == 0 && l == 6) {
               for(i1 = 4; (i1 >= 0 || !this.isOpaque(world, men, i1, l)) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, men, i1, l, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, men, i1 - 1, l);
               }
            } else if (j1 != 4 && i1 != 6) {
               for(i1 = 0; (i1 >= 0 || !this.isOpaque(world, men, i1, l)) && this.getY(i1) >= 0; --i1) {
                  if (IntMath.mod(men, 2) == 1 && IntMath.mod(l, 2) == 1) {
                     this.setBlockAndMetadata(world, men, i1, l, this.pillarRedBlock, this.pillarRedMeta);
                  } else {
                     this.setBlockAndMetadata(world, men, i1, l, this.brickRedBlock, this.brickRedMeta);
                  }

                  this.setGrassToDirt(world, men, i1 - 1, l);
               }

               for(i1 = 1; i1 <= 8; ++i1) {
                  this.setAir(world, men, i1, l);
               }
            } else {
               for(i1 = 3; (i1 >= 0 || !this.isOpaque(world, men, i1, l)) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, men, i1, l, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, men, i1 - 1, l);
               }

               if (i1 == 6) {
                  this.setBlockAndMetadata(world, men, 4, l, this.woodBeamBlock, this.woodBeamMeta | 4);
               } else if (j1 == 4) {
                  this.setBlockAndMetadata(world, men, 4, l, this.woodBeamBlock, this.woodBeamMeta | 8);
               }
            }
         }
      }

      int[] var15 = new int[]{-4, 4};
      l = var15.length;

      int j1;
      int i1;
      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];
         int[] var16 = new int[]{-4, 4};
         j1 = var16.length;

         for(i1 = 0; i1 < j1; ++i1) {
            int i1 = var16[i1];
            this.setBlockAndMetadata(world, i1, 2, i1 - 1, this.brickStairBlock, 7);
            this.setAir(world, i1, 2, i1);
            this.setBlockAndMetadata(world, i1, 2, i1 + 1, this.brickStairBlock, 6);
         }

         this.setBlockAndMetadata(world, -4, 3, i1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, 4, 3, i1, this.brickStairBlock, 4);
      }

      var15 = new int[]{-2, 2};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];
         this.setBlockAndMetadata(world, i1, 2, -6, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, i1, 3, -6, this.brickStairBlock, 6);
      }

      var15 = new int[]{-2, 2};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];
         this.setBlockAndMetadata(world, i1 - 1, 2, 6, this.brickStairBlock, 4);
         this.setAir(world, i1, 2, 6);
         this.setBlockAndMetadata(world, i1 + 1, 2, 6, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, i1, 3, 6, this.brickStairBlock, 7);
      }

      var15 = new int[]{-7, 7};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];
         this.setBlockAndMetadata(world, -4, 3, i1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 4, 3, i1, this.fenceBlock, this.fenceMeta);
      }

      var15 = new int[]{-5, 5};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];
         this.setBlockAndMetadata(world, i1, 3, -6, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 3, 6, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -1, 3, -7, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 1, 3, -7, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -1, 3, 7, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 1, 3, 7, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -5, 3, -2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -5, 3, 2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 5, 3, -2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 5, 3, 2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 0, 0, -6, this.woodBeamBlock, this.woodBeamMeta | 4);
      this.setBlockAndMetadata(world, 0, 1, -6, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -6, this.doorBlock, 8);

      for(men = -5; men <= 5; ++men) {
         this.setBlockAndMetadata(world, 0, 0, men, this.woodBeamBlock, this.woodBeamMeta | 8);
      }

      var15 = new int[]{-2, 2};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];

         for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
         }
      }

      for(men = -6; men <= 6; ++men) {
         for(l = 0; l <= 3; ++l) {
            j1 = 5 + l;
            this.setBlockAndMetadata(world, -4 + l, j1, men, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 4 - l, j1, men, this.roofStairBlock, 0);
            if (l > 0) {
               this.setBlockAndMetadata(world, -4 + l, j1 - 1, men, this.roofStairBlock, 4);
               this.setBlockAndMetadata(world, 4 - l, j1 - 1, men, this.roofStairBlock, 5);
            }
         }

         this.setBlockAndMetadata(world, 0, 8, men, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 0, 9, men, this.roofSlabBlock, this.roofSlabMeta);
      }

      var15 = new int[]{-7, 7};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];

         for(i1 = 0; i1 <= 2; ++i1) {
            j1 = 5 + i1;
            this.setBlockAndMetadata(world, -3 + i1, j1, i1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 3 - i1, j1, i1, this.roofStairBlock, 0);
            if (i1 > 0) {
               this.setBlockAndMetadata(world, -3 + i1, j1 - 1, i1, this.roofStairBlock, 4);
               this.setBlockAndMetadata(world, 3 - i1, j1 - 1, i1, this.roofStairBlock, 5);
            }
         }

         this.setBlockAndMetadata(world, 0, 7, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 0, 8, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 0, 9, i1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, -4, 4, i1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, -3, 4, i1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, -2, 4, i1, this.roofSlabBlock, this.roofSlabMeta | 8);
         this.setBlockAndMetadata(world, -1, 4, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 0, 4, i1, this.roofSlabBlock, this.roofSlabMeta | 8);
         this.setBlockAndMetadata(world, 1, 4, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 2, 4, i1, this.roofSlabBlock, this.roofSlabMeta | 8);
         this.setBlockAndMetadata(world, 3, 4, i1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, 4, 4, i1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, -1, 5, i1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, 0, 5, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 1, 5, i1, this.roofSlabBlock, this.roofSlabMeta);
      }

      this.setBlockAndMetadata(world, 0, 8, -8, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 0, 9, -8, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 8, 8, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 0, 9, 8, this.roofStairBlock, 2);
      var15 = new int[]{-6, 6};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];

         for(i1 = 0; i1 <= 2; ++i1) {
            j1 = 5 + i1;
            this.setBlockAndMetadata(world, -3 + i1, j1, i1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3 - i1, j1, i1, this.roofBlock, this.roofMeta);

            for(i1 = -2 + i1; i1 <= 2 - i1; ++i1) {
               this.setBlockAndMetadata(world, i1, j1, i1, this.plankBlock, this.plankMeta);
            }
         }
      }

      var15 = new int[]{-5, 5};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];
         this.setBlockAndMetadata(world, i1, 5, -7, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 4, -6, this.roofStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 4, -5, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 5, -4, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 4, -3, this.roofStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 4, -1, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 4, 1, this.roofStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 4, 3, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 5, 4, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 4, 5, this.roofStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 4, 6, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 5, 7, this.roofSlabBlock, this.roofSlabMeta);
      }

      var15 = new int[]{-2, 2};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];
         this.setBlockAndMetadata(world, -5, 4, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 5, 4, i1, this.roofStairBlock, 0);
      }

      for(men = -3; men <= 3; ++men) {
         for(l = -2; l <= 5; ++l) {
            this.setBlockAndMetadata(world, men, 4, l, this.plankSlabBlock, this.plankSlabMeta | 8);
            if (Math.abs(men) <= 2 && random.nextInt(3) == 0) {
               this.setBlockAndMetadata(world, men, 5, l, LOTRMod.thatchFloor, 0);
            }
         }
      }

      var15 = new int[]{-2, 2};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];

         for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
         }
      }

      for(men = -5; men <= 5; ++men) {
         this.setBlockAndMetadata(world, 0, 4, men, this.woodBeamBlock, this.woodBeamMeta | 8);
      }

      for(men = 1; men <= 5; ++men) {
         this.setBlockAndMetadata(world, -3, men, 0, this.woodBeamBlock, this.woodBeamMeta);
      }

      for(men = 1; men <= 6; ++men) {
         this.setBlockAndMetadata(world, -2, men, 0, this.woodBeamBlock, this.woodBeamMeta);
         this.setBlockAndMetadata(world, -1, men, 0, Blocks.field_150468_ap, 4);
      }

      for(men = 2; men <= 3; ++men) {
         this.setBlockAndMetadata(world, men, 1, -1, this.brickStairBlock, 2);
         this.setBlockAndMetadata(world, men, 2, -1, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, men, 3, -1, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, men, 1, 1, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, men, 2, 1, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, men, 3, 1, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, men, 3, 0, this.brickBlock, this.brickMeta);

         for(l = -1; l <= 1; ++l) {
            this.setBlockAndMetadata(world, men, 4, l, this.brickBlock, this.brickMeta);
         }
      }

      for(men = -1; men <= 1; ++men) {
         this.setBlockAndMetadata(world, 2, 5, men, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, 2, 6, men, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, 3, 5, men, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, 3, 6, 0, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 3, 7, 0, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 3, 8, 0, Blocks.field_150457_bL, 0);
      this.setBlockAndMetadata(world, 2, 0, 0, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 2, 1, 0, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 2, 2, 0, Blocks.field_150460_al, 5);
      this.setBlockAndMetadata(world, 3, 0, 0, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 3, 1, 0, Blocks.field_150480_ab, 0);
      this.spawnItemFrame(world, 2, 3, 0, 3, this.getEasterlingFramedItem(random));
      this.setBlockAndMetadata(world, -2, 1, -5, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, -3, 1, -5, this.plankStairBlock, 7);
      this.setBlockAndMetadata(world, -3, 1, -4, this.plankStairBlock, 4);
      this.placePlate(world, random, -2, 2, -5, this.plateBlock, LOTRFoods.RHUN);
      this.placePlate(world, random, -3, 2, -5, this.plateBlock, LOTRFoods.RHUN);
      this.placeMug(world, random, -3, 2, -4, 3, LOTRFoods.RHUN_DRINK);
      this.setBlockAndMetadata(world, 3, 1, -4, this.tableBlock, 0);
      var15 = new int[]{-1, 1};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];
         this.setBlockAndMetadata(world, -3, 1, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
         this.placeBarrel(world, random, -3, 2, i1, 4, LOTRFoods.RHUN_DRINK);
      }

      for(men = -3; men <= 3; ++men) {
         this.setBlockAndMetadata(world, men, 1, 5, this.plankStairBlock, 6);
         if (Math.abs(men) >= 2) {
            if (random.nextBoolean()) {
               this.placePlate(world, random, men, 2, 5, this.plateBlock, LOTRFoods.RHUN);
            } else {
               this.placeMug(world, random, men, 2, 5, 0, LOTRFoods.RHUN_DRINK);
            }
         }
      }

      this.setBlockAndMetadata(world, -1, 1, 5, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 1, 1, 5, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, -2, 1, 3, this.plankStairBlock, 3);
      this.setBlockAndMetadata(world, 2, 1, 3, this.plankStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 3, -2, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, 0, 3, 2, LOTRMod.chandelier, 0);
      this.setBlockAndMetadata(world, -3, 5, -2, this.woodBeamBlock, this.woodBeamMeta);
      this.setBlockAndMetadata(world, 3, 5, -2, this.woodBeamBlock, this.woodBeamMeta);

      for(men = -2; men <= 2; ++men) {
         this.setBlockAndMetadata(world, men, 5, -2, this.fenceBlock, this.fenceMeta);
      }

      var15 = new int[]{-1, 1};
      l = var15.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var15[j1];
         this.setBlockAndMetadata(world, i1, 5, 4, this.bedBlock, 0);
         this.setBlockAndMetadata(world, i1, 5, 5, this.bedBlock, 8);
      }

      this.placeChest(world, random, 0, 5, 5, 2, this.chestContents);
      this.setBlockAndMetadata(world, 0, 7, 5, LOTRMod.chandelier, 3);
      men = 1 + random.nextInt(2);

      for(l = 0; l < men; ++l) {
         LOTREntityEasterling easterling = new LOTREntityEasterling(world);
         this.spawnNPCAndSetHome(easterling, world, 0, 1, 0, 16);
      }

      return true;
   }
}
