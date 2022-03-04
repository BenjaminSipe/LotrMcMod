package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDaleBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDaleSmithy extends LOTRWorldGenDaleStructure {
   public LOTRWorldGenDaleSmithy(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 2);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int mugMeta;
      int i1;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(mugMeta = -5; mugMeta <= 5; ++mugMeta) {
            for(i1 = -2; i1 <= 12; ++i1) {
               j1 = this.getTopBlock(world, mugMeta, i1) - 1;
               Block block = this.getBlock(world, mugMeta, j1, i1);
               if (block != Blocks.field_150349_c) {
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

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = 0; k1 <= 10; ++k1) {
            for(mugMeta = 0; !this.isOpaque(world, i1, mugMeta, k1) && this.getY(mugMeta) >= 0; --mugMeta) {
               this.setBlockAndMetadata(world, i1, mugMeta, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, mugMeta - 1, k1);
            }

            for(mugMeta = 1; mugMeta <= 10; ++mugMeta) {
               this.setAir(world, i1, mugMeta, k1);
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(k1 = -1; k1 <= 11; ++k1) {
            mugMeta = Math.abs(i1);
            if (mugMeta == 4 && IntMath.mod(k1, 4) == 3 || i1 == 0 && k1 == 11) {
               for(i1 = 3; (i1 >= 1 || !this.isOpaque(world, i1, i1, k1)) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, i1, i1, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i1, i1 - 1, k1);
               }
            }

            if (mugMeta == 4 && IntMath.mod(k1, 4) == 0) {
               this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 7);
            }

            if (mugMeta == 4 && IntMath.mod(k1, 4) == 2) {
               this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 6);
            }

            if (k1 == 11 && IntMath.mod(i1, 4) == 1) {
               this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 4);
            }

            if (k1 == 11 && IntMath.mod(i1, 4) == 3) {
               this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 5);
            }

            if (k1 == -1 && i1 == -3) {
               this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 4);
            }

            if (k1 == -1 && i1 == 3) {
               this.setBlockAndMetadata(world, i1, 3, k1, this.brickStairBlock, 5);
            }

            if (mugMeta == 1 && k1 == -1) {
               for(i1 = 3; (i1 >= 1 || !this.isOpaque(world, i1, i1, k1)) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, i1, i1, k1, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, i1, i1 - 1, k1);
               }
            }

            if (mugMeta == 0 && k1 == -1) {
               for(i1 = 0; (i1 == 0 || !this.isOpaque(world, i1, i1, k1)) && this.getY(i1) >= 0; --i1) {
                  this.setBlockAndMetadata(world, i1, i1, k1, this.floorBlock, this.floorMeta);
                  this.setGrassToDirt(world, i1, i1 - 1, k1);
               }

               for(i1 = 1; i1 <= 3; ++i1) {
                  this.setAir(world, i1, i1, k1);
               }
            }

            if (mugMeta <= 2 && k1 >= 1 && k1 <= 9) {
               this.setBlockAndMetadata(world, i1, 0, k1, this.floorBlock, this.floorMeta);
            }

            if (mugMeta <= 3 && k1 >= 0 && k1 <= 10) {
               if (mugMeta == 3 || k1 == 0 || k1 == 10) {
                  for(i1 = 1; i1 <= 4; ++i1) {
                     this.setBlockAndMetadata(world, i1, i1, k1, this.brickBlock, this.brickMeta);
                  }
               }

               this.setBlockAndMetadata(world, i1, 4, k1, this.brickBlock, this.brickMeta);
            }

            if (mugMeta == 4 || k1 == -1 || k1 == 11) {
               this.setBlockAndMetadata(world, i1, 4, k1, this.brickBlock, this.brickMeta);

               for(i1 = 5; i1 <= 6; ++i1) {
                  this.setBlockAndMetadata(world, i1, i1, k1, this.plankBlock, this.plankMeta);
               }
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -2, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 4, 12, this.brickStairBlock, 7);
         if (IntMath.mod(i1, 2) == 1) {
            this.setBlockAndMetadata(world, i1, 5, -2, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, i1, 5, 12, this.brickWallBlock, this.brickWallMeta);
         }
      }

      for(i1 = -1; i1 <= 11; ++i1) {
         this.setBlockAndMetadata(world, -5, 4, i1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, 5, 4, i1, this.brickStairBlock, 4);
         if (IntMath.mod(i1, 2) == 0) {
            this.setBlockAndMetadata(world, -5, 5, i1, this.brickWallBlock, this.brickWallMeta);
            this.setBlockAndMetadata(world, 5, 5, i1, this.brickWallBlock, this.brickWallMeta);
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 7, -1, this.woodBeamBlock, this.woodBeamMeta | 4);
         this.setBlockAndMetadata(world, i1, 7, 11, this.woodBeamBlock, this.woodBeamMeta | 4);
      }

      for(i1 = 0; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, -4, 7, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
         this.setBlockAndMetadata(world, 4, 7, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         if (IntMath.mod(i1, 4) != 0) {
            if (IntMath.mod(i1, 4) == 2) {
               this.setBlockAndMetadata(world, i1, 6, -1, this.barsBlock, 0);
               this.setBlockAndMetadata(world, i1, 6, 11, this.barsBlock, 0);
            }
         } else {
            for(k1 = 5; k1 <= 7; ++k1) {
               this.setBlockAndMetadata(world, i1, k1, -1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1, k1, 11, this.brickBlock, this.brickMeta);
            }

            this.setBlockAndMetadata(world, i1, 7, -2, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i1, 7, 12, this.brickStairBlock, 7);
         }
      }

      for(i1 = -1; i1 <= 11; ++i1) {
         if (IntMath.mod(i1, 4) != 3) {
            if (IntMath.mod(i1, 4) == 1) {
               this.setBlockAndMetadata(world, -4, 6, i1, this.barsBlock, 0);
               this.setBlockAndMetadata(world, 4, 6, i1, this.barsBlock, 0);
            }
         } else {
            for(k1 = 5; k1 <= 7; ++k1) {
               this.setBlockAndMetadata(world, -4, k1, i1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, 4, k1, i1, this.brickBlock, this.brickMeta);
            }

            this.setBlockAndMetadata(world, -5, 7, i1, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 5, 7, i1, this.brickStairBlock, 4);
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         for(k1 = -2; k1 <= 12; ++k1) {
            if (i1 <= -4 || i1 >= 4 || k1 <= -1 || k1 >= 11) {
               this.setBlockAndMetadata(world, i1, 8, k1, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(k1 = -1; k1 <= 11; ++k1) {
            if (i1 == -4 || i1 == 4 || k1 == -1 || k1 == 11) {
               this.setBlockAndMetadata(world, i1, 9, k1, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = 0; k1 <= 10; ++k1) {
            if (i1 == -3 || i1 == 3 || k1 == 0 || k1 == 10) {
               this.setBlockAndMetadata(world, i1, 8, k1, this.plankBlock, this.plankMeta);
            }

            if (k1 != 3 && k1 != 7) {
               if (i1 == 0) {
                  this.setBlockAndMetadata(world, i1, 8, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
               }
            } else {
               this.setBlockAndMetadata(world, i1, 8, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
            }

            this.setBlockAndMetadata(world, i1, 10, k1, this.brickBlock, this.brickMeta);
         }

         this.setBlockAndMetadata(world, i1, 10, -1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, i1, 10, 11, this.plankBlock, this.plankMeta);
      }

      this.setBlockAndMetadata(world, 0, 9, -2, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 0, 10, -2, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, 0, 9, 12, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 10, 12, this.brickStairBlock, 7);

      for(i1 = -2; i1 <= 12; ++i1) {
         this.setBlockAndMetadata(world, -5, 9, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, -4, 10, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, -3, 11, i1, this.roofStairBlock, 1);

         for(k1 = -2; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, k1, 11, i1, this.roofBlock, this.roofMeta);
         }

         this.setBlockAndMetadata(world, 3, 11, i1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 4, 10, i1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 5, 9, i1, this.roofStairBlock, 0);
         if (i1 == -2 || i1 == 12) {
            this.setBlockAndMetadata(world, -4, 9, i1, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -3, 10, i1, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 3, 10, i1, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 9, i1, this.roofStairBlock, 5);
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = 7; k1 <= 9; ++k1) {
            for(mugMeta = 1; mugMeta <= 12; ++mugMeta) {
               if (i1 == 0 && k1 == 8) {
                  this.setAir(world, i1, mugMeta, k1);
               } else {
                  this.setBlockAndMetadata(world, i1, mugMeta, k1, this.brickBlock, this.brickMeta);
               }
            }

            if (Math.abs(i1) == 1 && (k1 == 7 || k1 == 9)) {
               this.setBlockAndMetadata(world, i1, 13, k1, this.brickSlabBlock, this.brickSlabMeta);
            } else if (Math.abs(i1) != 1 && k1 != 7 && k1 != 9) {
               this.setBlockAndMetadata(world, i1, 13, k1, this.barsBlock, 0);
            } else {
               this.setBlockAndMetadata(world, i1, 13, k1, this.brickBlock, this.brickMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 0, 0, this.floorBlock, this.floorMeta);
      this.setBlockAndMetadata(world, 0, 1, 0, this.doorBlock, 3);
      this.setBlockAndMetadata(world, 0, 2, 0, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 3, 0, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, 0, 3, 1, Blocks.field_150478_aa, 3);
      int[] var17 = new int[]{-2, 2};
      k1 = var17.length;

      for(mugMeta = 0; mugMeta < k1; ++mugMeta) {
         i1 = var17[mugMeta];
         this.setBlockAndMetadata(world, i1, 1, 1, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, i1, 2, 1, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, i1, 3, 1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, i1, 3, 2, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 3, 3, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 1, 4, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, i1, 2, 4, this.pillarBlock, this.pillarMeta);
         this.setBlockAndMetadata(world, i1, 3, 4, this.pillarBlock, this.pillarMeta);
      }

      this.setBlockAndMetadata(world, -1, 3, 1, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 1, 3, 1, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, -2, 1, 2, LOTRMod.unsmeltery, 4);
      this.setBlockAndMetadata(world, -2, 1, 3, LOTRMod.alloyForge, 4);
      this.setBlockAndMetadata(world, -2, 2, 3, Blocks.field_150460_al, 4);
      this.setBlockAndMetadata(world, 2, 1, 2, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 2, 1, 3, LOTRMod.daleTable, 0);

      for(i1 = 0; i1 <= 3; ++i1) {
         k1 = 6 + i1;
         mugMeta = 1 + i1;
         int[] var16 = new int[]{-2, 2};
         j1 = var16.length;

         for(int var15 = 0; var15 < j1; ++var15) {
            int i1 = var16[var15];
            this.setAir(world, i1, 4, k1);
            this.setBlockAndMetadata(world, i1, mugMeta, k1, this.brickStairBlock, 2);

            for(int j2 = mugMeta - 1; j2 >= 1; --j2) {
               this.setBlockAndMetadata(world, i1, j2, k1, this.brickBlock, this.brickMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 1, 6, Blocks.field_150467_bQ, 3);
      this.setBlockAndMetadata(world, -1, 1, 7, Blocks.field_150417_aV, 0);
      this.setBlockAndMetadata(world, 0, 1, 7, Blocks.field_150390_bg, 2);
      this.setBlockAndMetadata(world, 1, 1, 7, Blocks.field_150417_aV, 0);
      this.setBlockAndMetadata(world, -1, 2, 7, LOTRMod.wallStoneV, 1);
      this.setAir(world, 0, 2, 7);
      this.setBlockAndMetadata(world, 1, 2, 7, LOTRMod.wallStoneV, 1);
      this.setBlockAndMetadata(world, 0, 3, 7, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, -1, 1, 8, Blocks.field_150417_aV, 0);
      this.setBlockAndMetadata(world, 0, 1, 8, Blocks.field_150353_l, 0);
      this.setBlockAndMetadata(world, 1, 1, 8, Blocks.field_150417_aV, 0);
      this.setBlockAndMetadata(world, -2, 2, 8, Blocks.field_150417_aV, 0);
      this.setAir(world, -1, 2, 8);
      this.setAir(world, 1, 2, 8);
      this.setBlockAndMetadata(world, 2, 2, 8, Blocks.field_150417_aV, 0);
      this.setBlockAndMetadata(world, 0, 1, 9, Blocks.field_150417_aV, 0);
      this.setBlockAndMetadata(world, -1, 2, 9, Blocks.field_150417_aV, 0);
      this.setBlockAndMetadata(world, 0, 2, 9, Blocks.field_150417_aV, 0);
      this.setBlockAndMetadata(world, 1, 2, 9, Blocks.field_150417_aV, 0);
      this.setBlockAndMetadata(world, 0, 7, 0, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -3, 7, 3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, 7, 3, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -3, 7, 7, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, 7, 7, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 0, 7, 10, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 5, 6, Blocks.field_150462_ai, 0);
      this.placeChest(world, random, -1, 5, 6, 2, LOTRChestContents.DALE_HOUSE);
      this.placeChest(world, random, 1, 5, 6, 2, LOTRChestContents.DALE_HOUSE);

      for(i1 = -2; i1 <= 0; ++i1) {
         for(k1 = 1; k1 <= 3; ++k1) {
            if (i1 != -2 && i1 != 0 || k1 != 1 && k1 != 3) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            } else {
               this.setBlockAndMetadata(world, i1, 5, k1, this.plankBlock, this.plankMeta);
            }

            if (random.nextBoolean()) {
               this.placePlate(world, random, i1, 6, k1, this.plateBlock, LOTRFoods.DALE);
            } else {
               mugMeta = random.nextInt(4);
               this.placeMug(world, random, i1, 6, k1, mugMeta, LOTRFoods.DALE_DRINK);
            }
         }
      }

      var17 = new int[]{2, 3};
      k1 = var17.length;

      for(mugMeta = 0; mugMeta < k1; ++mugMeta) {
         i1 = var17[mugMeta];
         this.setBlockAndMetadata(world, i1, 5, 1, LOTRMod.strawBed, 2);
         this.setBlockAndMetadata(world, i1, 5, 0, LOTRMod.strawBed, 10);
      }

      LOTREntityDaleBlacksmith blacksmith = new LOTREntityDaleBlacksmith(world);
      this.spawnNPCAndSetHome(blacksmith, world, 0, 1, 5, 16);
      return true;
   }
}
