package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityDolAmrothCaptain;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDolAmrothStables extends LOTRWorldGenStructureBase2 {
   private Block brickBlock;
   private int brickMeta;
   private Block slabBlock;
   private int slabMeta;
   private Block stairBlock;
   private Block wallBlock;
   private int wallMeta;
   private Block rockSlabBlock;
   private Block doubleRockSlabBlock;
   private int rockSlabMeta;
   private Block pillarBlock;
   private int pillarMeta;
   private Block logBlock;
   private int logMeta;
   private Block plankBlock;
   private int plankMeta;
   private Block plankSlabBlock;
   private int plankSlabMeta;
   private Block fenceBlock;
   private int fenceMeta;
   private Block gateBlock;
   private Block woodBeamBlock;
   private int woodBeamMeta;
   private Block roofBlock;
   private int roofMeta;
   private Block roofSlabBlock;
   private int roofSlabMeta;
   private Block roofStairBlock;
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenDolAmrothStables(boolean flag) {
      super(flag);
      this.brickBlock = LOTRMod.brick3;
      this.brickMeta = 9;
      this.slabBlock = LOTRMod.slabSingle6;
      this.slabMeta = 7;
      this.stairBlock = LOTRMod.stairsDolAmrothBrick;
      this.wallBlock = LOTRMod.wall2;
      this.wallMeta = 14;
      this.rockSlabBlock = LOTRMod.slabSingle;
      this.doubleRockSlabBlock = LOTRMod.slabDouble;
      this.rockSlabMeta = 2;
      this.pillarBlock = LOTRMod.pillar;
      this.pillarMeta = 6;
      this.logBlock = Blocks.field_150364_r;
      this.logMeta = 0;
      this.plankBlock = Blocks.field_150344_f;
      this.plankMeta = 0;
      this.plankSlabBlock = Blocks.field_150376_bx;
      this.plankSlabMeta = 0;
      this.fenceBlock = Blocks.field_150422_aJ;
      this.fenceMeta = 0;
      this.woodBeamBlock = LOTRMod.woodBeamV1;
      this.woodBeamMeta = 0;
      this.gateBlock = Blocks.field_150396_be;
      this.roofBlock = LOTRMod.clayTileDyed;
      this.roofMeta = 11;
      this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle2;
      this.roofSlabMeta = 3;
      this.roofStairBlock = LOTRMod.stairsClayTileDyedBlue;
      this.leafBlock = LOTRMod.leaves4;
      this.leafMeta = 6;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 2);
      int i1;
      int i2;
      int k1;
      int k1;
      int i2;
      if (this.restrictions) {
         i1 = 0;
         i2 = 0;

         for(k1 = -9; k1 <= 9; ++k1) {
            for(k1 = -1; k1 <= 19; ++k1) {
               i2 = this.getTopBlock(world, k1, k1);
               Block block = this.getBlock(world, k1, i2 - 1, k1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }

               if (i2 < i1) {
                  i1 = i2;
               }

               if (i2 > i2) {
                  i2 = i2;
               }

               if (i2 - i1 > 7) {
                  return false;
               }
            }
         }
      }

      int k1;
      int i1;
      for(i1 = 0; i1 <= 2; ++i1) {
         i2 = i1 * 4;

         for(k1 = -8; k1 <= 8; ++k1) {
            for(k1 = i2; k1 <= i2 + 3; ++k1) {
               for(i2 = 0; (i2 == 0 || !this.isOpaque(world, k1, i2, k1)) && this.getY(i2) >= 0; --i2) {
                  this.setBlockAndMetadata(world, k1, i2, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, k1, i2 - 1, k1);
               }

               for(i2 = 1; i2 <= 7; ++i2) {
                  this.setAir(world, k1, i2, k1);
               }
            }
         }

         this.placeWoodPillar(world, -8, i2);
         this.placeWoodPillar(world, 8, i2);
         int[] var17 = new int[]{-3, 3};
         k1 = var17.length;

         for(i2 = 0; i2 < k1; ++i2) {
            i1 = var17[i2];
            if (i1 == 0) {
               this.placeWoodPillar(world, i1, i2);
            } else {
               for(k1 = 1; k1 <= 3; ++k1) {
                  this.setBlockAndMetadata(world, i1, k1, i2, this.logBlock, this.logMeta);
               }
            }
         }

         for(k1 = -7; k1 <= -4; ++k1) {
            this.setBlockAndMetadata(world, k1, 1, i2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, k1, 2, i2, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, k1, 4, i2, this.brickBlock, this.brickMeta);
         }

         for(k1 = 4; k1 <= 7; ++k1) {
            this.setBlockAndMetadata(world, k1, 1, i2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, k1, 2, i2, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, k1, 4, i2, this.brickBlock, this.brickMeta);
         }

         this.setBlockAndMetadata(world, -4, 2, i2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -4, 3, i2, this.wallBlock, this.wallMeta);
         this.setBlockAndMetadata(world, 4, 2, i2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 4, 3, i2, this.wallBlock, this.wallMeta);

         for(k1 = i2 + 1; k1 <= i2 + 3; ++k1) {
            this.setBlockAndMetadata(world, -8, 1, k1, this.doubleRockSlabBlock, this.rockSlabMeta);
            this.setBlockAndMetadata(world, -8, 2, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -8, 3, k1, this.rockSlabBlock, this.rockSlabMeta);
            this.setBlockAndMetadata(world, -8, 4, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 8, 1, k1, this.doubleRockSlabBlock, this.rockSlabMeta);
            this.setBlockAndMetadata(world, 8, 2, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 8, 3, k1, this.rockSlabBlock, this.rockSlabMeta);
            this.setBlockAndMetadata(world, 8, 4, k1, this.brickBlock, this.brickMeta);
         }

         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, -2, k1, i2, this.fenceBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 2, k1, i2, this.fenceBlock, this.brickMeta);
         }

         for(k1 = i2 + 1; k1 <= i2 + 3; ++k1) {
            for(k1 = -7; k1 <= -3; ++k1) {
               this.setBlockAndMetadata(world, k1, 0, k1, this.doubleRockSlabBlock, this.rockSlabMeta);
               if (random.nextInt(3) != 0) {
                  this.setBlockAndMetadata(world, k1, 1, k1, LOTRMod.thatchFloor, 0);
               }
            }

            for(k1 = 3; k1 <= 7; ++k1) {
               this.setBlockAndMetadata(world, k1, 0, k1, this.doubleRockSlabBlock, this.rockSlabMeta);
               if (random.nextInt(3) != 0) {
                  this.setBlockAndMetadata(world, k1, 1, k1, LOTRMod.thatchFloor, 0);
               }
            }
         }

         var17 = new int[]{-7, 7};
         k1 = var17.length;

         for(i2 = 0; i2 < k1; ++i2) {
            i1 = var17[i2];
            this.setBlockAndMetadata(world, i1, 1, i2 + 1, Blocks.field_150407_cf, 0);
            this.setBlockAndMetadata(world, i1, 1, i2 + 2, Blocks.field_150407_cf, 0);
            this.setBlockAndMetadata(world, i1, 1, i2 + 3, this.fenceBlock, this.fenceMeta);
         }

         var17 = new int[]{-3, 3};
         k1 = var17.length;

         for(i2 = 0; i2 < k1; ++i2) {
            i1 = var17[i2];
            this.setBlockAndMetadata(world, i1, 1, i2 + 1, this.gateBlock, 1);
            this.setBlockAndMetadata(world, i1, 1, i2 + 2, this.gateBlock, 1);
            this.setBlockAndMetadata(world, i1, 1, i2 + 3, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i1, 2, i2 + 3, Blocks.field_150478_aa, 5);
         }

         var17 = new int[]{-1, 1};
         k1 = var17.length;

         for(i2 = 0; i2 < k1; ++i2) {
            i1 = var17[i2];
            LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome(horse, world, 5 * i1, 1, i2 + 2, 0);
            horse.func_110214_p(0);
            horse.saddleMountForWorldGen();
            horse.func_110177_bN();
         }

         for(k1 = -7; k1 <= 7; ++k1) {
            for(k1 = i2 + 1; k1 <= i2 + 3; ++k1) {
               this.setBlockAndMetadata(world, k1, 4, k1, this.rockSlabBlock, this.rockSlabMeta | 8);
            }

            if (i1 > 0) {
               if (Math.abs(k1) <= 1) {
                  this.setBlockAndMetadata(world, k1, 4, i2, this.rockSlabBlock, this.rockSlabMeta | 8);
               } else {
                  this.setBlockAndMetadata(world, k1, 4, i2, this.brickBlock, this.brickMeta);
               }
            }
         }

         var17 = new int[]{-3, 3};
         k1 = var17.length;

         for(i2 = 0; i2 < k1; ++i2) {
            i1 = var17[i2];
            this.setBlockAndMetadata(world, i1, 4, i2 + 1, this.doubleRockSlabBlock, this.rockSlabMeta);
            this.setBlockAndMetadata(world, i1, 4, i2 + 3, this.doubleRockSlabBlock, this.rockSlabMeta);
         }

         for(k1 = -8; k1 <= 8; ++k1) {
            this.setBlockAndMetadata(world, k1, 5, i2, this.brickBlock, this.brickMeta);
         }

         var17 = new int[]{-7, 4};
         k1 = var17.length;

         for(i2 = 0; i2 < k1; ++i2) {
            i1 = var17[i2];
            this.setBlockAndMetadata(world, i1, 6, i2, this.stairBlock, 1);
            this.setBlockAndMetadata(world, i1 + 1, 6, i2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1 + 1, 7, i2, this.stairBlock, 1);
            this.setBlockAndMetadata(world, i1 + 2, 6, i2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1 + 2, 7, i2, this.stairBlock, 0);
            this.setBlockAndMetadata(world, i1 + 3, 6, i2, this.stairBlock, 0);

            for(k1 = i2 + 1; k1 <= i2 + 3; ++k1) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.roofStairBlock, 1);
               this.setBlockAndMetadata(world, i1 + 1, 5, k1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1 + 1, 6, k1, this.roofStairBlock, 1);
               this.setBlockAndMetadata(world, i1 + 2, 5, k1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1 + 2, 6, k1, this.roofStairBlock, 0);
               this.setBlockAndMetadata(world, i1 + 3, 5, k1, this.roofStairBlock, 0);
            }
         }

         this.setBlockAndMetadata(world, -2, 6, i2, this.stairBlock, 1);
         this.setBlockAndMetadata(world, -1, 6, i2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -1, 7, i2, this.stairBlock, 1);
         this.setBlockAndMetadata(world, 0, 6, i2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 0, 7, i2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 0, 8, i2, this.slabBlock, this.slabMeta);
         this.setBlockAndMetadata(world, 1, 6, i2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 1, 7, i2, this.stairBlock, 0);
         this.setBlockAndMetadata(world, 2, 6, i2, this.stairBlock, 0);

         for(k1 = i2 + 1; k1 <= i2 + 3; ++k1) {
            this.setBlockAndMetadata(world, -2, 5, k1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -1, 5, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, -1, 6, k1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 0, 5, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 0, 6, k1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 0, 7, k1, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, 1, 5, k1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 1, 6, k1, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 2, 5, k1, this.roofStairBlock, 0);
         }

         var17 = new int[]{-8, -3, 3, 8};
         k1 = var17.length;

         for(i2 = 0; i2 < k1; ++i2) {
            i1 = var17[i2];

            for(k1 = i2 + 1; k1 <= i2 + 3; ++k1) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.wallBlock, this.wallMeta);
            }
         }
      }

      for(i1 = -9; i1 <= 9; ++i1) {
         i2 = Math.abs(i1);
         if (i2 <= 2) {
            this.setBlockAndMetadata(world, i1, 0, -1, this.doubleRockSlabBlock, this.rockSlabMeta);
            this.setBlockAndMetadata(world, i1, 0, -2, this.doubleRockSlabBlock, this.rockSlabMeta);
         } else {
            this.placeGrassFoundation(world, i1, -1);
            if (i2 != 3 && i2 != 8) {
               this.setBlockAndMetadata(world, i1, 1, -1, this.leafBlock, this.leafMeta);
            } else {
               for(k1 = 1; k1 <= 4; ++k1) {
                  this.setBlockAndMetadata(world, i1, k1, -1, this.wallBlock, this.wallMeta);
               }
            }
         }
      }

      int[] var16;
      for(i1 = 0; i1 <= 11; ++i1) {
         var16 = new int[]{-9, 9};
         k1 = var16.length;

         for(k1 = 0; k1 < k1; ++k1) {
            i2 = var16[k1];
            this.placeGrassFoundation(world, i2, i1);
            if (i1 % 4 == 0) {
               for(i1 = 1; i1 <= 4; ++i1) {
                  this.setBlockAndMetadata(world, i2, i1, i1, this.wallBlock, this.wallMeta);
               }
            } else {
               this.setBlockAndMetadata(world, i2, 1, i1, this.leafBlock, this.leafMeta);
            }
         }

         if (i1 % 4 == 0) {
            this.setBlockAndMetadata(world, -9, 5, i1, this.stairBlock, 1);
            this.setBlockAndMetadata(world, 9, 5, i1, this.stairBlock, 0);
         }
      }

      this.setBlockAndMetadata(world, -1, 5, 0, this.stairBlock, 4);
      this.setBlockAndMetadata(world, 0, 5, 0, this.stairBlock, 6);
      this.setBlockAndMetadata(world, 1, 5, 0, this.stairBlock, 5);
      this.setBlockAndMetadata(world, 0, 6, 0, LOTRMod.brick, 5);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, 0, this.doubleRockSlabBlock, this.rockSlabMeta);

         for(i2 = 1; i2 <= 4; ++i2) {
            this.setBlockAndMetadata(world, i1, i2, 0, LOTRMod.gateDolAmroth, 2);
         }
      }

      int[] var18 = new int[]{-2, 2};
      i2 = var18.length;

      for(k1 = 0; k1 < i2; ++k1) {
         k1 = var18[k1];

         for(i2 = 1; i2 <= 4; ++i2) {
            this.setBlockAndMetadata(world, k1, i2, 0, this.brickBlock, this.brickMeta);
         }

         this.placeWallBanner(world, k1, 4, 0, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
      }

      this.setBlockAndMetadata(world, -8, 5, -1, this.stairBlock, 1);
      this.setBlockAndMetadata(world, 8, 5, -1, this.stairBlock, 0);
      var18 = new int[]{-7, 4};
      i2 = var18.length;

      for(k1 = 0; k1 < i2; ++k1) {
         k1 = var18[k1];
         this.setBlockAndMetadata(world, k1 + 0, 5, -1, this.stairBlock, 4);
         this.setBlockAndMetadata(world, k1 + 0, 6, -1, this.stairBlock, 1);
         this.setBlockAndMetadata(world, k1 + 1, 6, -1, this.stairBlock, 4);
         this.setBlockAndMetadata(world, k1 + 1, 7, -1, this.stairBlock, 1);
         this.setBlockAndMetadata(world, k1 + 2, 6, -1, this.stairBlock, 5);
         this.setBlockAndMetadata(world, k1 + 2, 7, -1, this.stairBlock, 0);
         this.setBlockAndMetadata(world, k1 + 3, 5, -1, this.stairBlock, 5);
         this.setBlockAndMetadata(world, k1 + 3, 6, -1, this.stairBlock, 0);
         this.setBlockAndMetadata(world, k1 + 1, 5, 0, this.stairBlock, 4);
         this.setBlockAndMetadata(world, k1 + 2, 5, 0, this.stairBlock, 5);
      }

      this.setBlockAndMetadata(world, -3, 5, -1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -2, 5, -1, this.stairBlock, 4);
      this.setBlockAndMetadata(world, -2, 6, -1, this.stairBlock, 1);
      this.setBlockAndMetadata(world, -1, 6, -1, this.stairBlock, 4);
      this.setBlockAndMetadata(world, -1, 7, -1, this.stairBlock, 1);
      this.setBlockAndMetadata(world, 0, 7, -1, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 8, -1, this.slabBlock, this.slabMeta);
      this.setBlockAndMetadata(world, 1, 6, -1, this.stairBlock, 5);
      this.setBlockAndMetadata(world, 1, 7, -1, this.stairBlock, 0);
      this.setBlockAndMetadata(world, 2, 5, -1, this.stairBlock, 5);
      this.setBlockAndMetadata(world, 2, 6, -1, this.stairBlock, 0);
      this.setBlockAndMetadata(world, 3, 5, -1, this.brickBlock, this.brickMeta);

      for(i1 = 1; i1 <= 3; ++i1) {
         for(i2 = -1; i2 <= 1; ++i2) {
            if (i1 != 3 || Math.abs(i2) < 1) {
               this.setAir(world, i2, 4, i1);
            }
         }
      }

      for(i1 = 1; i1 <= 11; ++i1) {
         this.setBlockAndMetadata(world, 0, 0, i1, this.doubleRockSlabBlock, this.rockSlabMeta);
      }

      for(i1 = -9; i1 <= 9; ++i1) {
         for(i2 = 12; i2 <= 18; ++i2) {
            for(k1 = 0; (k1 == 0 || !this.isOpaque(world, i1, k1, i2)) && this.getY(k1) >= 0; --k1) {
               this.setBlockAndMetadata(world, i1, k1, i2, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, k1 - 1, i2);
            }

            for(k1 = 1; k1 <= 9; ++k1) {
               this.setAir(world, i1, k1, i2);
            }
         }
      }

      var18 = new int[]{12, 18};
      i2 = var18.length;

      int k1;
      int[] var21;
      for(k1 = 0; k1 < i2; ++k1) {
         k1 = var18[k1];

         for(i2 = -9; i2 <= 9; ++i2) {
            this.setBlockAndMetadata(world, i2, 1, k1, this.doubleRockSlabBlock, this.rockSlabMeta);

            for(i1 = 2; i1 <= 6; ++i1) {
               this.setBlockAndMetadata(world, i2, i1, k1, this.brickBlock, this.brickMeta);
            }
         }

         var21 = new int[]{-9, 9};
         i1 = var21.length;

         for(k1 = 0; k1 < i1; ++k1) {
            k1 = var21[k1];
            this.placeWoodPillar(world, k1, k1);
            this.setBlockAndMetadata(world, k1, 4, k1, this.doubleRockSlabBlock, this.rockSlabMeta);

            for(int j1 = 5; j1 <= 7; ++j1) {
               this.setBlockAndMetadata(world, k1, j1, k1, this.pillarBlock, this.pillarMeta);
            }

            this.setBlockAndMetadata(world, k1, 8, k1, this.rockSlabBlock, this.rockSlabMeta);
         }

         for(i2 = -8; i2 <= 8; ++i2) {
            i1 = Math.abs(i2);
            if (i1 >= 5) {
               if (i1 % 2 == 0) {
                  this.setBlockAndMetadata(world, i2, 7, k1, this.slabBlock, this.slabMeta);
               } else {
                  this.setBlockAndMetadata(world, i2, 7, k1, this.brickBlock, this.brickMeta);
               }
            }

            if (i1 == 4) {
               for(k1 = 5; k1 <= 10; ++k1) {
                  this.setBlockAndMetadata(world, i2, k1, k1, this.pillarBlock, this.pillarMeta);
               }

               this.setBlockAndMetadata(world, i2, 11, k1, this.slabBlock, this.slabMeta);
            }

            if (i1 <= 3) {
               for(k1 = 7; k1 <= 8; ++k1) {
                  this.setBlockAndMetadata(world, i2, k1, k1, this.brickBlock, this.brickMeta);
               }

               this.setBlockAndMetadata(world, i2, 9, k1, this.doubleRockSlabBlock, this.rockSlabMeta);
               if (i1 >= 1) {
                  this.setBlockAndMetadata(world, i2, 10, k1, this.brickBlock, this.brickMeta);
                  if (i1 % 2 == 0) {
                     this.setBlockAndMetadata(world, i2, 11, k1, this.slabBlock, this.slabMeta);
                  }
               }

               if (i1 == 0) {
                  this.setBlockAndMetadata(world, i2, 10, k1, this.slabBlock, this.slabMeta);
               }
            }
         }
      }

      for(i1 = 13; i1 <= 17; ++i1) {
         var16 = new int[]{-4, 4};
         k1 = var16.length;

         for(k1 = 0; k1 < k1; ++k1) {
            i2 = var16[k1];
            this.setBlockAndMetadata(world, i2, 9, i1, this.doubleRockSlabBlock, this.rockSlabMeta);
            this.setBlockAndMetadata(world, i2, 10, i1, this.brickBlock, this.brickMeta);
            if (i1 % 2 == 0) {
               this.setBlockAndMetadata(world, i2, 11, i1, this.slabBlock, this.slabMeta);
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         i2 = Math.abs(i1);

         for(k1 = 13; k1 <= 17; ++k1) {
            this.setBlockAndMetadata(world, i1, 9, k1, this.doubleRockSlabBlock, this.rockSlabMeta);
         }

         for(k1 = 14; k1 <= 16; ++k1) {
            this.setBlockAndMetadata(world, i1, 10, k1, this.doubleRockSlabBlock, this.rockSlabMeta);
            if (i2 <= 2) {
               this.setBlockAndMetadata(world, i1, 11, k1, this.brickBlock, this.brickMeta);
            }

            if (i2 <= 1) {
               this.setBlockAndMetadata(world, i1, 12, k1, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(i1 = 13; i1 <= 17; ++i1) {
         this.setBlockAndMetadata(world, -3, 11, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, -2, 12, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, -1, 13, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 0, 13, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 0, 14, i1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, 1, 13, i1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 2, 12, i1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 3, 11, i1, this.roofStairBlock, 0);
      }

      var18 = new int[]{13, 17};
      i2 = var18.length;

      for(k1 = 0; k1 < i2; ++k1) {
         k1 = var18[k1];
         this.setBlockAndMetadata(world, -3, 10, k1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, -2, 11, k1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, -1, 12, k1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, 1, 12, k1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, 2, 11, k1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, 3, 10, k1, this.roofStairBlock, 5);
      }

      this.placeWallBanner(world, 0, 12, 14, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
      this.placeWallBanner(world, 0, 12, 16, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
      this.placeWallBanner(world, -4, 9, 12, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
      this.setBlockAndMetadata(world, -3, 9, 12, this.doubleRockSlabBlock, this.rockSlabMeta);
      this.setBlockAndMetadata(world, -2, 9, 12, this.stairBlock, 6);
      this.setBlockAndMetadata(world, -2, 8, 12, LOTRMod.stainedGlassPane, 11);
      this.setBlockAndMetadata(world, -1, 9, 12, this.doubleRockSlabBlock, this.rockSlabMeta);
      this.setBlockAndMetadata(world, 0, 9, 12, this.stairBlock, 6);
      this.setBlockAndMetadata(world, 0, 8, 12, LOTRMod.stainedGlassPane, 0);
      this.setBlockAndMetadata(world, 1, 9, 12, this.doubleRockSlabBlock, this.rockSlabMeta);
      this.setBlockAndMetadata(world, 2, 9, 12, this.stairBlock, 6);
      this.setBlockAndMetadata(world, 2, 8, 12, LOTRMod.stainedGlassPane, 11);
      this.setBlockAndMetadata(world, 3, 9, 12, this.doubleRockSlabBlock, this.rockSlabMeta);
      this.placeWallBanner(world, 4, 9, 12, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
      this.placeWoodPillar(world, -3, 12);
      this.placeWoodPillar(world, 3, 12);
      this.placeWoodPillar(world, -6, 18);
      this.placeWoodPillar(world, -2, 18);
      this.placeWoodPillar(world, 2, 18);
      this.placeWoodPillar(world, 6, 18);

      for(i1 = 13; i1 <= 17; ++i1) {
         var16 = new int[]{-9, 9};
         k1 = var16.length;

         for(k1 = 0; k1 < k1; ++k1) {
            i2 = var16[k1];
            this.setBlockAndMetadata(world, i2, 1, i1, this.doubleRockSlabBlock, this.rockSlabMeta);

            for(i1 = 2; i1 <= 3; ++i1) {
               this.setBlockAndMetadata(world, i2, i1, i1, this.brickBlock, this.brickMeta);
            }

            this.setBlockAndMetadata(world, i2, 4, i1, this.doubleRockSlabBlock, this.rockSlabMeta);

            for(i1 = 5; i1 <= 6; ++i1) {
               this.setBlockAndMetadata(world, i2, i1, i1, this.brickBlock, this.brickMeta);
            }

            if (i1 % 2 == 1) {
               this.setBlockAndMetadata(world, i2, 7, i1, this.slabBlock, this.slabMeta);
            } else {
               this.setBlockAndMetadata(world, i2, 7, i1, this.brickBlock, this.brickMeta);
            }
         }
      }

      for(i1 = 13; i1 <= 17; ++i1) {
         for(i2 = 0; i2 <= 4; ++i2) {
            this.setBlockAndMetadata(world, -8 + i2, 4 + i2, i1, this.stairBlock, 4);
            this.setBlockAndMetadata(world, 8 - i2, 4 + i2, i1, this.stairBlock, 5);
         }

         this.setBlockAndMetadata(world, -8, 5, i1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, -8, 6, i1, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, -7, 6, i1, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, 8, 5, i1, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 8, 6, i1, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, 7, 6, i1, Blocks.field_150349_c, 0);
      }

      for(i1 = -8; i1 <= 8; ++i1) {
         i2 = Math.abs(i1);
         if (i2 == 8) {
            for(k1 = 13; k1 <= 17; ++k1) {
               this.setBlockAndMetadata(world, i1, 7, k1, this.leafBlock, this.leafMeta);
            }

            for(k1 = 14; k1 <= 16; ++k1) {
               this.setBlockAndMetadata(world, i1, 8, k1, this.leafBlock, this.leafMeta);
            }
         } else if (i2 != 7) {
            if (i2 == 6) {
               this.setBlockAndMetadata(world, i1, 7, 13, this.leafBlock, this.leafMeta);
               this.setBlockAndMetadata(world, i1, 7, 17, this.leafBlock, this.leafMeta);
               this.setBlockAndMetadata(world, i1, 8, 13, this.leafBlock, this.leafMeta);
               this.setBlockAndMetadata(world, i1, 8, 17, this.leafBlock, this.leafMeta);
               this.setBlockAndMetadata(world, i1, 7, 14, this.rockSlabBlock, this.rockSlabMeta);
               this.setBlockAndMetadata(world, i1, 7, 15, this.rockSlabBlock, this.rockSlabMeta);
               this.setBlockAndMetadata(world, i1, 7, 16, this.rockSlabBlock, this.rockSlabMeta);
            } else if (i2 == 5) {
               this.setBlockAndMetadata(world, i1, 8, 13, this.leafBlock, this.leafMeta);
               this.setBlockAndMetadata(world, i1, 8, 14, this.leafBlock, this.leafMeta);
               this.setBlockAndMetadata(world, i1, 8, 16, this.leafBlock, this.leafMeta);
               this.setBlockAndMetadata(world, i1, 8, 17, this.leafBlock, this.leafMeta);
            }
         } else {
            for(k1 = 13; k1 <= 17; ++k1) {
               this.setBlockAndMetadata(world, i1, 7, k1, this.leafBlock, this.leafMeta);
            }

            this.setBlockAndMetadata(world, i1, 8, 13, this.leafBlock, this.leafMeta);
            this.setBlockAndMetadata(world, i1, 8, 17, this.leafBlock, this.leafMeta);
            this.setBlockAndMetadata(world, i1, 7, 15, this.rockSlabBlock, this.rockSlabMeta);
            this.setBlockAndMetadata(world, i1, 6, 15, this.brickBlock, this.brickMeta);
         }
      }

      for(i1 = -8; i1 <= 8; ++i1) {
         for(i2 = 13; i2 <= 17; ++i2) {
            this.setBlockAndMetadata(world, i1, 0, i2, this.doubleRockSlabBlock, this.rockSlabMeta);
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, 12, this.doubleRockSlabBlock, this.rockSlabMeta);

         for(i2 = 1; i2 <= 3; ++i2) {
            this.setAir(world, i1, i2, 12);
         }
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         var16 = new int[]{-2, 2};
         k1 = var16.length;

         for(k1 = 0; k1 < k1; ++k1) {
            i2 = var16[k1];
            this.setBlockAndMetadata(world, i2, i1, 12, this.brickBlock, this.brickMeta);
         }

         for(i2 = -1; i2 <= 1; ++i2) {
            this.setBlockAndMetadata(world, i2, i1, 12, LOTRMod.gateDolAmroth, 2);
         }
      }

      var18 = new int[]{-1, 1};
      i2 = var18.length;

      for(k1 = 0; k1 < i2; ++k1) {
         k1 = var18[k1];
         this.setBlockAndMetadata(world, 4 * k1, 4, 13, this.slabBlock, this.slabMeta | 8);
         this.setBlockAndMetadata(world, 3 * k1, 5, 13, this.slabBlock, this.slabMeta);
         this.setBlockAndMetadata(world, 2 * k1, 5, 13, this.slabBlock, this.slabMeta | 8);
         this.setBlockAndMetadata(world, 1 * k1, 6, 13, this.slabBlock, this.slabMeta);
         this.setBlockAndMetadata(world, 0 * k1, 6, 13, this.slabBlock, this.slabMeta);
         this.placeWallBanner(world, 3 * k1, 4, 12, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
         this.setBlockAndMetadata(world, 6 * k1, 3, 13, Blocks.field_150478_aa, 3);

         for(i2 = 5; i2 <= 6; ++i2) {
            this.setBlockAndMetadata(world, 6 * k1, i2, 18, this.woodBeamBlock, this.woodBeamMeta);
         }

         for(i2 = 5; i2 <= 7; ++i2) {
            this.setBlockAndMetadata(world, 2 * k1, i2, 18, this.woodBeamBlock, this.woodBeamMeta);
         }

         this.placeWallBanner(world, 6 * k1, 5, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
         this.placeWallBanner(world, 6 * k1, 5, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
         this.placeWallBanner(world, 2 * k1, 6, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 0);
         this.placeWallBanner(world, 2 * k1, 6, 18, LOTRItemBanner.BannerType.DOL_AMROTH, 2);
         this.setBlockAndMetadata(world, 6 * k1, 3, 17, Blocks.field_150478_aa, 4);
         var21 = new int[]{13, 17};
         i1 = var21.length;

         for(k1 = 0; k1 < i1; ++k1) {
            k1 = var21[k1];
            this.setBlockAndMetadata(world, 4 * k1, 1, k1, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 5 * k1, 1, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            this.setBlockAndMetadata(world, 7 * k1, 1, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            this.placeChest(world, random, 6 * k1, 1, k1, 0, LOTRChestContents.DOL_AMROTH_STABLES);
         }
      }

      this.setBlockAndMetadata(world, -8, 1, 13, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -8, 1, 17, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, 8, 1, 13, LOTRMod.gondorianTable, 0);
      this.setBlockAndMetadata(world, 8, 1, 17, LOTRMod.dolAmrothTable, 0);
      var18 = new int[]{-9, 9};
      i2 = var18.length;

      for(k1 = 0; k1 < i2; ++k1) {
         k1 = var18[k1];

         for(i2 = 14; i2 <= 16; ++i2) {
            this.setBlockAndMetadata(world, k1, 1, i2, this.doubleRockSlabBlock, this.rockSlabMeta);
            this.setAir(world, k1, 2, i2);
         }

         this.setBlockAndMetadata(world, k1, 3, 14, this.stairBlock, 7);
         this.setBlockAndMetadata(world, k1, 3, 15, this.slabBlock, this.slabMeta | 8);
         this.setBlockAndMetadata(world, k1, 3, 16, this.stairBlock, 6);
      }

      var18 = new int[]{-8, 7};
      i2 = var18.length;

      for(k1 = 0; k1 < i2; ++k1) {
         k1 = var18[k1];

         for(i2 = k1; i2 <= k1 + 1; ++i2) {
            this.setBlockAndMetadata(world, i2, 1, 18, this.doubleRockSlabBlock, this.rockSlabMeta);
            this.setAir(world, i2, 2, 18);
         }

         this.setBlockAndMetadata(world, k1, 3, 18, this.stairBlock, 4);
         this.setBlockAndMetadata(world, k1 + 1, 3, 18, this.stairBlock, 5);
      }

      for(i1 = -8; i1 <= 8; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 15, Blocks.field_150404_cg, 11);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 14, Blocks.field_150404_cg, 11);
         this.setBlockAndMetadata(world, i1, 1, 16, Blocks.field_150404_cg, 11);
      }

      this.generateWindow(world, -4, 3, 18);
      this.generateWindow(world, 4, 3, 18);
      var18 = new int[]{14, 16};
      i2 = var18.length;

      for(k1 = 0; k1 < i2; ++k1) {
         k1 = var18[k1];
         this.setBlockAndMetadata(world, -1, 9, k1, Blocks.field_150406_ce, 3);
         this.setBlockAndMetadata(world, 0, 9, k1, Blocks.field_150406_ce, 11);
         this.setBlockAndMetadata(world, 1, 9, k1, Blocks.field_150406_ce, 3);
      }

      this.setBlockAndMetadata(world, -2, 9, 15, Blocks.field_150406_ce, 3);
      this.setBlockAndMetadata(world, -1, 9, 15, Blocks.field_150406_ce, 11);
      this.setBlockAndMetadata(world, 0, 9, 15, Blocks.field_150406_ce, 11);
      this.setBlockAndMetadata(world, 1, 9, 15, Blocks.field_150406_ce, 11);
      this.setBlockAndMetadata(world, 2, 9, 15, Blocks.field_150406_ce, 3);
      this.setBlockAndMetadata(world, 0, 8, 15, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 7, 15, LOTRMod.chandelier, 2);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, 18, this.doubleRockSlabBlock, this.rockSlabMeta);
         this.setBlockAndMetadata(world, i1, 7, 18, this.doubleRockSlabBlock, this.rockSlabMeta);

         for(i2 = 4; i2 <= 6; ++i2) {
            if (IntMath.mod(i1 + i2, 2) == 0) {
               this.setBlockAndMetadata(world, i1, i2, 18, LOTRMod.stainedGlassPane, 0);
            } else {
               this.setBlockAndMetadata(world, i1, i2, 18, LOTRMod.stainedGlassPane, 11);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 8, 18, this.doubleRockSlabBlock, this.rockSlabMeta);

      for(i1 = -6; i1 <= 6; ++i1) {
         i2 = Math.abs(i1);
         this.placeGrassFoundation(world, i1, 19);
         if (i2 % 4 == 2) {
            this.setBlockAndMetadata(world, i1, 1, 19, this.stairBlock, 3);
            this.setGrassToDirt(world, i1, 0, 19);
         } else {
            this.setBlockAndMetadata(world, i1, 1, 19, this.leafBlock, this.leafMeta);
         }

         if (i2 >= 6) {
            this.setBlockAndMetadata(world, i1, 6, 19, this.slabBlock, this.slabMeta | 8);
         } else if (i2 >= 3) {
            this.setBlockAndMetadata(world, i1, 7, 19, this.slabBlock, this.slabMeta);
         } else if (i2 >= 2) {
            this.setBlockAndMetadata(world, i1, 7, 19, this.slabBlock, this.slabMeta | 8);
         } else {
            this.setBlockAndMetadata(world, i1, 8, 19, this.slabBlock, this.slabMeta);
         }
      }

      LOTREntityNPC captain = new LOTREntityDolAmrothCaptain(world);
      captain.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(captain, world, 0, 1, 15, 8);
      return true;
   }

   private void placeWoodPillar(World world, int i, int k) {
      int j;
      for(j = 0; (!this.isOpaque(world, i, j, k) || this.getBlock(world, i, j, k) == this.brickBlock && this.getMeta(world, i, j, k) == this.brickMeta) && this.getY(j) >= 0; --j) {
         this.setBlockAndMetadata(world, i, j, k, this.woodBeamBlock, this.woodBeamMeta);
         this.setGrassToDirt(world, i, j - 1, k);
      }

      for(j = 1; j <= 4; ++j) {
         this.setBlockAndMetadata(world, i, j, k, this.woodBeamBlock, this.woodBeamMeta);
      }

   }

   private void generateWindow(World world, int i, int j, int k) {
      this.setBlockAndMetadata(world, i - 1, j, k, this.stairBlock, 0);
      this.setBlockAndMetadata(world, i, j, k, this.slabBlock, this.slabMeta);
      this.setBlockAndMetadata(world, i + 1, j, k, this.stairBlock, 1);

      for(int i1 = i - 1; i1 <= i + 1; ++i1) {
         this.setAir(world, i1, j + 1, k);
         this.setAir(world, i1, j + 2, k);
      }

      this.setBlockAndMetadata(world, i - 1, j + 3, k, this.stairBlock, 4);
      this.setBlockAndMetadata(world, i, j + 3, k, this.slabBlock, this.slabMeta | 8);
      this.setBlockAndMetadata(world, i + 1, j + 3, k, this.stairBlock, 5);
   }

   private void placeGrassFoundation(World world, int i, int k) {
      for(int j1 = 6; (j1 >= 0 || !this.isOpaque(world, i, j1, k)) && this.getY(j1) >= 0; --j1) {
         if (j1 > 0) {
            this.setAir(world, i, j1, k);
         } else if (j1 == 0) {
            this.setBlockAndMetadata(world, i, j1, k, Blocks.field_150349_c, 0);
            this.setGrassToDirt(world, i, j1 - 1, k);
         } else {
            this.setBlockAndMetadata(world, i, j1, k, Blocks.field_150346_d, 0);
            this.setGrassToDirt(world, i, j1 - 1, k);
         }
      }

   }
}
