package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEasterling;
import lotr.common.entity.npc.LOTREntityEasterlingBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingSmithy extends LOTRWorldGenEasterlingStructureTown {
   public LOTRWorldGenEasterlingSmithy(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 7);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int j1;
      int i1;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(j1 = -7; j1 <= 7; ++j1) {
            for(i1 = -7; i1 <= 7; ++i1) {
               j1 = this.getTopBlock(world, j1, i1) - 1;
               if (!this.isSurface(world, j1, j1, i1)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > k1) {
                  k1 = j1;
               }

               if (k1 - i1 > 6) {
                  return false;
               }
            }
         }
      }

      for(i1 = -6; i1 <= 6; ++i1) {
         for(k1 = -6; k1 <= 6; ++k1) {
            j1 = Math.abs(i1);
            i1 = Math.abs(k1);
            if (j1 == 6 && i1 % 4 == 2 || i1 == 6 && j1 % 4 == 2) {
               for(j1 = 4; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }
            } else if (j1 != 6 && i1 != 6) {
               for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.brickRedBlock, this.brickRedMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               for(j1 = 1; j1 <= 9; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }

               if (IntMath.mod(i1, 2) == 1 && IntMath.mod(k1, 2) == 1) {
                  this.setBlockAndMetadata(world, i1, 0, k1, this.pillarRedBlock, this.pillarRedMeta);
               }
            } else {
               for(j1 = 3; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               if (i1 == 6) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
               } else if (j1 == 6) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
               }
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, -2, this.woodBeamBlock, this.woodBeamMeta | 4);
         this.setBlockAndMetadata(world, i1, 0, 2, this.woodBeamBlock, this.woodBeamMeta | 4);
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, -2, 0, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
         this.setBlockAndMetadata(world, 2, 0, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
      }

      this.setBlockAndMetadata(world, -4, 2, -6, LOTRMod.reedBars, 0);
      this.setBlockAndMetadata(world, 4, 2, -6, LOTRMod.reedBars, 0);
      int[] var12 = new int[]{-4, 0};
      k1 = var12.length;

      for(j1 = 0; j1 < k1; ++j1) {
         i1 = var12[j1];
         this.setBlockAndMetadata(world, -6, 2, i1 - 1, this.brickStairBlock, 7);
         this.setAir(world, -6, 2, i1);
         this.setBlockAndMetadata(world, -6, 2, i1 + 1, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, -6, 3, i1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, 6, 2, i1 - 1, this.brickStairBlock, 7);
         this.setAir(world, 6, 2, i1);
         this.setBlockAndMetadata(world, 6, 2, i1 + 1, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, 6, 3, i1, this.brickStairBlock, 4);
      }

      var12 = new int[]{-7, 7};
      k1 = var12.length;

      for(j1 = 0; j1 < k1; ++j1) {
         i1 = var12[j1];
         this.setBlockAndMetadata(world, -6, 3, i1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 6, 3, i1, this.fenceBlock, this.fenceMeta);
      }

      var12 = new int[]{-7, 7};
      k1 = var12.length;

      for(j1 = 0; j1 < k1; ++j1) {
         i1 = var12[j1];
         this.setBlockAndMetadata(world, i1, 3, -6, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, i1, 3, 6, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -2, 3, -7, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 3, -7, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -2, 3, 7, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 3, 7, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -7, 3, -2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -7, 3, 2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 7, 3, -2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 7, 3, 2, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 0, 0, -6, this.woodBeamBlock, this.woodBeamMeta | 4);
      this.setBlockAndMetadata(world, 0, 1, -6, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -6, this.doorBlock, 8);

      for(i1 = -7; i1 <= 7; ++i1) {
         for(k1 = -7; k1 <= 7; ++k1) {
            j1 = Math.abs(i1);
            i1 = Math.abs(k1);
            if (j1 == 7 && i1 == 7 || j1 == 7 && (i1 == 0 || i1 == 4) || i1 == 7 && (j1 == 0 || j1 == 4)) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.roofSlabBlock, this.roofSlabMeta);
            }

            if (i1 == 7) {
               if (i1 == -6 || i1 == -3 || i1 == 1 || i1 == 5) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, 5);
               }

               if (i1 == -5 || i1 == -1 || i1 == 3 || i1 == 6) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, 4);
               }

               if (j1 == 2) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, k1 < 0 ? 2 : 3);
               }
            }

            if (j1 == 7) {
               if (k1 == -6 || k1 == -3 || k1 == 1 || k1 == 5) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, 6);
               }

               if (k1 == -5 || k1 == -1 || k1 == 3 || k1 == 6) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, 7);
               }

               if (i1 == 2) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.roofStairBlock, i1 < 0 ? 1 : 0);
               }
            }
         }
      }

      for(i1 = 0; i1 <= 1; ++i1) {
         k1 = 5 + i1;

         for(j1 = -6 + i1; j1 <= 6 - i1; ++j1) {
            this.setBlockAndMetadata(world, j1, k1, -6 + i1, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, j1, k1, 6 - i1, this.roofStairBlock, 3);
         }

         for(j1 = -6 + i1; j1 <= 6 - i1; ++j1) {
            this.setBlockAndMetadata(world, -6 + i1, k1, j1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 6 - i1, k1, j1, this.roofStairBlock, 0);
         }

         for(j1 = -5 + i1; j1 <= 5 - i1; ++j1) {
            this.setBlockAndMetadata(world, j1, k1, -5 + i1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, j1, k1, 5 - i1, this.roofStairBlock, 6);
         }

         for(j1 = -5 + i1; j1 <= 5 - i1; ++j1) {
            this.setBlockAndMetadata(world, -5 + i1, k1, j1, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 5 - i1, k1, j1, this.roofStairBlock, 5);
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            j1 = Math.abs(i1);
            i1 = Math.abs(k1);
            if (j1 <= 1 && i1 >= 3 || i1 <= 1 && j1 >= 3 || j1 >= 2 && i1 >= 2) {
               this.setBlockAndMetadata(world, i1, 6, k1, this.roofSlabBlock, this.roofSlabMeta | 8);
            }

            if (j1 == 2 && i1 == 2) {
               this.setBlockAndMetadata(world, i1, 6, k1, this.roofBlock, this.roofMeta);
               this.setBlockAndMetadata(world, i1, 5, k1, LOTRMod.chandelier, 0);
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         k1 = Math.abs(i1);
         if (k1 >= 2) {
            this.setBlockAndMetadata(world, i1, 7, -2, this.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i1, 7, 2, this.roofStairBlock, 3);
         }

         if (k1 >= 1) {
            this.setBlockAndMetadata(world, i1, 8, -1, this.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i1, 8, 1, this.roofStairBlock, 6);
         }

         if (k1 >= 0) {
            this.setBlockAndMetadata(world, i1, 9, 0, this.roofSlabBlock, this.roofSlabMeta);
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         k1 = Math.abs(i1);
         if (k1 >= 2) {
            this.setBlockAndMetadata(world, -2, 7, i1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 7, i1, this.roofStairBlock, 0);
         }

         if (k1 >= 1) {
            this.setBlockAndMetadata(world, -1, 8, i1, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 8, i1, this.roofStairBlock, 5);
         }

         if (k1 >= 0) {
            this.setBlockAndMetadata(world, 0, 9, i1, this.roofSlabBlock, this.roofSlabMeta);
         }
      }

      this.setBlockAndMetadata(world, 0, 9, -4, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 9, 4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -4, 9, 0, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, 9, 0, this.roofStairBlock, 1);
      var12 = new int[]{-3, 3};
      k1 = var12.length;

      for(j1 = 0; j1 < k1; ++j1) {
         i1 = var12[j1];
         this.setBlockAndMetadata(world, -1, 7, i1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 0, 7, i1, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, 1, 7, i1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -1, 8, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 0, 8, i1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 1, 8, i1, this.roofBlock, this.roofMeta);
      }

      var12 = new int[]{-3, 3};
      k1 = var12.length;

      for(j1 = 0; j1 < k1; ++j1) {
         i1 = var12[j1];
         this.setBlockAndMetadata(world, i1, 7, -1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, i1, 7, 0, LOTRMod.reedBars, 0);
         this.setBlockAndMetadata(world, i1, 7, 1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, i1, 8, -1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, i1, 8, 0, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, i1, 8, 1, this.roofBlock, this.roofMeta);
      }

      this.setBlockAndMetadata(world, 0, 4, -7, this.plankBlock, this.plankMeta);
      this.spawnItemFrame(world, 0, 4, -7, 2, new ItemStack(LOTRMod.blacksmithHammer));
      this.setBlockAndMetadata(world, -2, 3, -5, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 3, -5, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 3, -5, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 5, 3, -2, Blocks.field_150478_aa, 1);

      for(i1 = -5; i1 <= 5; ++i1) {
         k1 = Math.abs(i1);
         if (k1 == 2) {
            this.setBlockAndMetadata(world, i1, 1, -2, this.woodBeamBlock, this.woodBeamMeta);
            this.setBlockAndMetadata(world, i1, 2, -2, this.plankSlabBlock, this.plankSlabMeta);
         } else if (i1 == 4) {
            this.setBlockAndMetadata(world, i1, 1, -2, this.fenceGateBlock, 0);
         } else {
            this.setBlockAndMetadata(world, i1, 1, -2, this.fenceBlock, this.fenceMeta);
         }
      }

      this.setBlockAndMetadata(world, 3, 1, 5, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 4, 1, 5, this.tableBlock, 0);
      this.setBlockAndMetadata(world, 5, 1, 5, this.woodBeamBlock, this.woodBeamMeta);

      for(i1 = 3; i1 <= 4; ++i1) {
         this.placeChest(world, random, 5, 1, i1, 5, LOTRChestContents.EASTERLING_SMITHY);
      }

      this.placeWeaponRack(world, 4, 3, 5, 6, random.nextBoolean() ? null : this.getEasterlingWeaponItem(random));
      this.placeWeaponRack(world, 5, 3, 4, 7, random.nextBoolean() ? null : this.getEasterlingWeaponItem(random));
      this.placeArmorStand(world, 5, 1, 0, 1, (ItemStack[])null);

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = 4; k1 <= 6; ++k1) {
            for(j1 = 1; j1 <= 6; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(i1 = 1; i1 <= 5; ++i1) {
         this.setAir(world, 0, i1, 5);
      }

      this.setBlockAndMetadata(world, 0, 0, 5, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 0, 1, 5, Blocks.field_150480_ab, 0);
      this.setBlockAndMetadata(world, 0, 0, 4, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 1, 4, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 0, 3, 4, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 0, 5, 4, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -1, 6, 6, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 1, 6, 6, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 7, 6, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 8, 6, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 9, 6, Blocks.field_150457_bL, 0);

      for(i1 = -3; i1 <= -2; ++i1) {
         for(k1 = 1; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, 5, this.brickBlock, this.brickMeta);
         }
      }

      this.setBlockAndMetadata(world, -3, 1, 4, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -3, 1, 3, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -3, 2, 3, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -5, 1, 3, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, -4, 1, 3, LOTRMod.alloyForge, 2);
      this.setBlockAndMetadata(world, -5, 2, 3, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -4, 2, 3, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -3, 2, 4, this.barsBlock, 0);

      for(i1 = -5; i1 <= -4; ++i1) {
         for(k1 = 4; k1 <= 5; ++k1) {
            this.setBlockAndMetadata(world, i1, 3, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150353_l, 0);
         }
      }

      this.setBlockAndMetadata(world, -5, 3, 3, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -4, 3, 3, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -3, 3, 3, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -3, 3, 4, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, -3, 3, 5, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, -3, 1, 0, Blocks.field_150467_bQ, 1);
      this.setBlockAndMetadata(world, -5, 1, 0, Blocks.field_150383_bp, 3);
      LOTREntityEasterling blacksmith = new LOTREntityEasterlingBlacksmith(world);
      this.spawnNPCAndSetHome(blacksmith, world, 0, 1, 0, 16);
      return true;
   }
}
