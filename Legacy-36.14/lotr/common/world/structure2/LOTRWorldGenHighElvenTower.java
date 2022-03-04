package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityHighElfLord;
import lotr.common.entity.npc.LOTREntityHighElfWarrior;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenHighElvenTower extends LOTRWorldGenStructureBase2 {
   protected Block brickBlock;
   protected int brickMeta;
   protected Block brickSlabBlock;
   protected int brickSlabMeta;
   protected Block brickStairBlock;
   protected Block brickWallBlock;
   protected int brickWallMeta;
   protected Block pillarBlock;
   protected int pillarMeta;
   protected Block floorBlock;
   protected int floorMeta;
   protected Block roofBlock;
   protected int roofMeta;
   protected Block roofSlabBlock;
   protected int roofSlabMeta;
   protected Block roofStairBlock;
   protected Block plankBlock;
   protected int plankMeta;
   protected Block plankSlabBlock;
   protected int plankSlabMeta;
   protected Block plankStairBlock;
   protected Block fenceBlock;
   protected int fenceMeta;
   protected Block plateBlock;
   protected Block leafBlock;
   protected int leafMeta;

   public LOTRWorldGenHighElvenTower(boolean flag) {
      super(flag);
      this.brickBlock = LOTRMod.brick3;
      this.brickMeta = 2;
      this.brickSlabBlock = LOTRMod.slabSingle5;
      this.brickSlabMeta = 5;
      this.brickStairBlock = LOTRMod.stairsHighElvenBrick;
      this.brickWallBlock = LOTRMod.wall2;
      this.brickWallMeta = 11;
      this.pillarBlock = LOTRMod.pillar;
      this.pillarMeta = 10;
      this.floorBlock = Blocks.field_150334_T;
      this.floorMeta = 0;
      this.roofBlock = LOTRMod.clayTileDyed;
      this.roofMeta = 3;
      this.roofSlabBlock = LOTRMod.slabClayTileDyedSingle;
      this.roofSlabMeta = 3;
      this.roofStairBlock = LOTRMod.stairsClayTileDyedLightBlue;
      this.plankBlock = Blocks.field_150344_f;
      this.plankMeta = 2;
      this.plankSlabBlock = Blocks.field_150376_bx;
      this.plankSlabMeta = 2;
      this.plankStairBlock = Blocks.field_150487_bG;
      this.fenceBlock = Blocks.field_150422_aJ;
      this.fenceMeta = 2;
      this.plateBlock = LOTRMod.plateBlock;
      this.leafBlock = Blocks.field_150362_t;
      this.leafMeta = 6;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      int radius = 7;
      int radiusPlusOne = radius + 1;
      int sections = 2 + random.nextInt(3);
      int sectionHeight = 8;
      this.setOriginAndRotation(world, i, j, k, rotation, radius + 3);
      double radiusD = (double)radius - 0.5D;
      double radiusDPlusOne = radiusD + 1.0D;
      int wallThresholdMin = (int)(radiusD * radiusD);
      int wallThresholdMax = (int)(radiusDPlusOne * radiusDPlusOne);
      int sRadius;
      int sRadiusPlusOne;
      int i2;
      int k1;
      int j1;
      int j1;
      if (this.restrictions) {
         sRadius = 0;
         sRadiusPlusOne = 0;

         for(i2 = -radiusPlusOne; i2 <= radiusPlusOne; ++i2) {
            for(k1 = -radiusPlusOne; k1 <= radiusPlusOne; ++k1) {
               j1 = i2 * i2 + k1 * k1;
               if (j1 < wallThresholdMax) {
                  j1 = this.getTopBlock(world, i2, k1) - 1;
                  if (!this.isSurface(world, i2, j1, k1)) {
                     return false;
                  }

                  if (j1 < sRadius) {
                     sRadius = j1;
                  }

                  if (j1 > sRadiusPlusOne) {
                     sRadiusPlusOne = j1;
                  }

                  if (sRadiusPlusOne - sRadius > 16) {
                     return false;
                  }
               }
            }
         }
      }

      for(sRadius = -radius; sRadius <= radius; ++sRadius) {
         for(sRadiusPlusOne = -radius; sRadiusPlusOne <= radius; ++sRadiusPlusOne) {
            i2 = Math.abs(sRadius);
            k1 = Math.abs(sRadiusPlusOne);
            j1 = sRadius * sRadius + sRadiusPlusOne * sRadiusPlusOne;
            if (j1 < wallThresholdMax) {
               this.layFoundation(world, sRadius, sRadiusPlusOne);
               if (j1 >= wallThresholdMin) {
                  this.setBlockAndMetadata(world, sRadius, 1, sRadiusPlusOne, this.pillarBlock, this.pillarMeta);

                  for(j1 = 2; j1 <= 6; ++j1) {
                     if ((i2 != 5 || k1 != 5) && (i2 != radius || k1 != 2) && (k1 != radius || i2 != 2)) {
                        this.setBlockAndMetadata(world, sRadius, j1, sRadiusPlusOne, this.brickBlock, this.brickMeta);
                     } else {
                        this.setBlockAndMetadata(world, sRadius, j1, sRadiusPlusOne, this.pillarBlock, this.pillarMeta);
                     }
                  }

                  this.setBlockAndMetadata(world, sRadius, 7, sRadiusPlusOne, this.pillarBlock, this.pillarMeta);
               } else {
                  this.setBlockAndMetadata(world, sRadius, 1, sRadiusPlusOne, this.brickBlock, this.brickMeta);

                  for(j1 = 2; j1 <= 6; ++j1) {
                     this.setAir(world, sRadius, j1, sRadiusPlusOne);
                  }

                  this.setBlockAndMetadata(world, sRadius, 7, sRadiusPlusOne, this.brickBlock, this.brickMeta);
               }
            }
         }
      }

      for(sRadius = -4; sRadius <= 4; ++sRadius) {
         for(sRadiusPlusOne = -4; sRadiusPlusOne <= 4; ++sRadiusPlusOne) {
            i2 = Math.abs(sRadius);
            k1 = Math.abs(sRadiusPlusOne);
            if (i2 != 4 && k1 != 4) {
               this.setBlockAndMetadata(world, sRadius, 1, sRadiusPlusOne, this.pillarBlock, this.pillarMeta);
            } else {
               this.setBlockAndMetadata(world, sRadius, 1, sRadiusPlusOne, this.floorBlock, this.floorMeta);
            }
         }
      }

      for(sRadius = -1; sRadius <= 1; ++sRadius) {
         for(sRadiusPlusOne = 2; sRadiusPlusOne <= 4; ++sRadiusPlusOne) {
            this.setBlockAndMetadata(world, sRadius, sRadiusPlusOne, -radius, LOTRMod.gateHighElven, 2);
         }

         for(sRadiusPlusOne = -radius; sRadiusPlusOne <= -4; ++sRadiusPlusOne) {
            this.setBlockAndMetadata(world, sRadius, 1, sRadiusPlusOne, this.pillarBlock, this.pillarMeta);
         }
      }

      for(sRadius = -6; sRadius <= -5; ++sRadius) {
         this.setBlockAndMetadata(world, -2, 1, sRadius, this.floorBlock, this.floorMeta);
         this.setBlockAndMetadata(world, 2, 1, sRadius, this.floorBlock, this.floorMeta);
      }

      this.setBlockAndMetadata(world, 0, 1, -radius - 1, this.brickBlock, this.brickMeta);
      this.layFoundation(world, 0, -radius - 1);
      this.setBlockAndMetadata(world, 0, 1, -radius - 2, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -1, 1, -radius - 1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 1, 1, -radius - 1, this.brickStairBlock, 2);
      this.layFoundation(world, 0, -radius - 2);
      this.layFoundation(world, -1, -radius - 1);
      this.layFoundation(world, 1, -radius - 1);
      this.setBlockAndMetadata(world, -2, 1, -radius - 1, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, -1, 1, -radius - 2, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 1, 1, -radius - 2, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 2, 1, -radius - 1, this.brickStairBlock, 0);
      this.layFoundation(world, -2, -radius - 1);
      this.layFoundation(world, -1, -radius - 2);
      this.layFoundation(world, 1, -radius - 2);
      this.layFoundation(world, 2, -radius - 1);
      int[] var33 = new int[]{-radius + 1, radius - 1};
      sRadiusPlusOne = var33.length;

      for(i2 = 0; i2 < sRadiusPlusOne; ++i2) {
         k1 = var33[i2];
         this.setBlockAndMetadata(world, k1, 2, -2, this.plankStairBlock, 7);
         this.setBlockAndMetadata(world, k1, 2, 2, this.plankStairBlock, 6);

         for(j1 = -1; j1 <= 1; ++j1) {
            this.setBlockAndMetadata(world, k1, 2, j1, this.plankSlabBlock, this.plankSlabMeta | 8);
         }
      }

      this.setBlockAndMetadata(world, -2, 2, radius - 1, this.plankStairBlock, 4);
      this.setBlockAndMetadata(world, 2, 2, radius - 1, this.plankStairBlock, 5);

      for(sRadius = -1; sRadius <= 1; ++sRadius) {
         this.setBlockAndMetadata(world, sRadius, 2, radius - 1, this.plankSlabBlock, this.plankSlabMeta | 8);
      }

      var33 = new int[]{-radius + 2, radius - 2};
      sRadiusPlusOne = var33.length;

      for(i2 = 0; i2 < sRadiusPlusOne; ++i2) {
         k1 = var33[i2];
         this.setBlockAndMetadata(world, k1, 2, -4, this.plankStairBlock, 7);
         this.setBlockAndMetadata(world, k1, 2, -3, this.plankStairBlock, 6);
         this.setBlockAndMetadata(world, k1, 2, 3, this.plankStairBlock, 7);
         this.setBlockAndMetadata(world, k1, 2, 4, this.plankStairBlock, 6);
      }

      var33 = new int[]{-radius + 2, radius - 2};
      sRadiusPlusOne = var33.length;

      for(i2 = 0; i2 < sRadiusPlusOne; ++i2) {
         k1 = var33[i2];
         this.setBlockAndMetadata(world, -4, 2, k1, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, -3, 2, k1, this.plankStairBlock, 5);
         this.setBlockAndMetadata(world, 3, 2, k1, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, 4, 2, k1, this.plankStairBlock, 5);
      }

      for(sRadius = -radius; sRadius <= radius; ++sRadius) {
         for(sRadiusPlusOne = -radius; sRadiusPlusOne <= radius; ++sRadiusPlusOne) {
            i2 = Math.abs(sRadius);
            k1 = Math.abs(sRadiusPlusOne);
            if ((i2 == radius - 1 && k1 <= 2 || sRadiusPlusOne == radius - 1 && i2 <= 2 || i2 == radius - 2 && k1 >= 3 && k1 <= 4 || k1 == radius - 2 && i2 >= 3 && i2 <= 4) && random.nextInt(3) == 0) {
               if (random.nextInt(3) == 0) {
                  this.placeMug(world, random, sRadius, 3, sRadiusPlusOne, random.nextInt(4), LOTRFoods.ELF_DRINK);
               } else {
                  this.placePlate(world, random, sRadius, 3, sRadiusPlusOne, this.plateBlock, LOTRFoods.ELF);
               }
            }

            if (sRadiusPlusOne == -radius + 1 && i2 == 2) {
               for(j1 = 2; j1 <= 4; ++j1) {
                  this.setBlockAndMetadata(world, sRadius, j1, sRadiusPlusOne, this.brickWallBlock, this.brickWallMeta);
               }

               this.setBlockAndMetadata(world, sRadius, 5, sRadiusPlusOne, LOTRMod.highElvenTorch, 5);
            }

            if (i2 == radius && k1 == 0 || sRadiusPlusOne == radius && i2 == 0) {
               this.setBlockAndMetadata(world, sRadius, 3, sRadiusPlusOne, LOTRMod.highElfWoodBars, 0);
               this.setBlockAndMetadata(world, sRadius, 4, sRadiusPlusOne, LOTRMod.highElfWoodBars, 0);
            }

            if (i2 == radius - 1 && k1 == 1 || sRadiusPlusOne == radius - 1 && i2 == 1) {
               this.setBlockAndMetadata(world, sRadius, 4, sRadiusPlusOne, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, sRadius, 5, sRadiusPlusOne, LOTRMod.highElvenTorch, 5);
            }
         }
      }

      for(sRadius = -2; sRadius <= 2; ++sRadius) {
         this.setBlockAndMetadata(world, sRadius, 6, -radius + 1, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, sRadius, 6, radius - 1, this.brickStairBlock, 6);
      }

      for(sRadius = -2; sRadius <= 2; ++sRadius) {
         this.setBlockAndMetadata(world, -radius + 1, 6, sRadius, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, radius - 1, 6, sRadius, this.brickStairBlock, 5);
      }

      for(sRadius = -4; sRadius <= -3; ++sRadius) {
         this.setBlockAndMetadata(world, sRadius, 6, -radius + 2, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, sRadius, 6, radius - 2, this.brickStairBlock, 6);
      }

      for(sRadius = 3; sRadius <= 4; ++sRadius) {
         this.setBlockAndMetadata(world, sRadius, 6, -radius + 2, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, sRadius, 6, radius - 2, this.brickStairBlock, 6);
      }

      this.setBlockAndMetadata(world, -radius + 2, 6, -4, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, -radius + 2, 6, -3, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, radius - 2, 6, -4, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, radius - 2, 6, -3, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, -radius + 2, 6, 3, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, -radius + 2, 6, 4, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, radius - 2, 6, 3, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, radius - 2, 6, 4, this.brickStairBlock, 6);
      var33 = new int[]{-radius + 2, radius - 2};
      sRadiusPlusOne = var33.length;

      for(i2 = 0; i2 < sRadiusPlusOne; ++i2) {
         k1 = var33[i2];
         this.setBlockAndMetadata(world, -2, 6, k1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 2, 6, k1, this.brickStairBlock, 5);
      }

      var33 = new int[]{-radius + 2, radius - 2};
      sRadiusPlusOne = var33.length;

      for(i2 = 0; i2 < sRadiusPlusOne; ++i2) {
         k1 = var33[i2];
         this.setBlockAndMetadata(world, k1, 6, -2, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, k1, 6, 2, this.brickStairBlock, 6);
      }

      var33 = new int[]{-4, 4};
      sRadiusPlusOne = var33.length;

      for(i2 = 0; i2 < sRadiusPlusOne; ++i2) {
         k1 = var33[i2];
         this.setBlockAndMetadata(world, -4, 6, k1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, 4, 6, k1, this.brickStairBlock, 5);
      }

      for(sRadius = -2; sRadius <= 2; ++sRadius) {
         this.setBlockAndMetadata(world, sRadius, 8, -radius, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, sRadius, 8, radius, this.roofStairBlock, 3);
      }

      for(sRadius = -2; sRadius <= 2; ++sRadius) {
         this.setBlockAndMetadata(world, -radius, 8, sRadius, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, radius, 8, sRadius, this.roofStairBlock, 0);
      }

      for(sRadius = -4; sRadius <= -3; ++sRadius) {
         this.setBlockAndMetadata(world, sRadius, 8, -radius + 1, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, sRadius, 8, radius - 1, this.roofStairBlock, 3);
      }

      for(sRadius = 3; sRadius <= 4; ++sRadius) {
         this.setBlockAndMetadata(world, sRadius, 8, -radius + 1, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, sRadius, 8, radius - 1, this.roofStairBlock, 3);
      }

      this.setBlockAndMetadata(world, -radius + 1, 8, -3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 1, 8, 3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, radius - 1, 8, -3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 1, 8, 3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -radius + 1, 8, -2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -radius + 1, 8, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -radius + 2, 8, -5, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 1, 8, -2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 1, 8, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 2, 8, -5, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -radius + 1, 8, 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -radius + 1, 8, 4, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -radius + 2, 8, 5, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, radius - 1, 8, 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, radius - 1, 8, 4, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, radius - 2, 8, 5, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -radius + 2, 8, -4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -4, 8, -radius + 2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -2, 8, -radius + 1, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, radius - 2, 8, -4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, 8, -radius + 2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 2, 8, -radius + 1, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -radius + 2, 8, 4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -4, 8, radius - 2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -2, 8, radius - 1, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, radius - 2, 8, 4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, 8, radius - 2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 2, 8, radius - 1, this.roofStairBlock, 0);
      sRadius = radius - 1;
      sRadiusPlusOne = sRadius + 1;
      double sRadiusD = (double)sRadius - 0.7D;
      double sRadiusDPlusOne = sRadiusD + 1.0D;
      int sWallThresholdMin = (int)(sRadiusD * sRadiusD);
      int sWallThresholdMax = (int)(sRadiusDPlusOne * sRadiusDPlusOne);

      int sectionTopHeight;
      int i1;
      int k1;
      int k1;
      int k1;
      int k2;
      int j1;
      for(sectionTopHeight = 0; sectionTopHeight < sections; ++sectionTopHeight) {
         i1 = 7 + sectionTopHeight * sectionHeight;

         for(k1 = -sRadius; k1 <= sRadius; ++k1) {
            for(k1 = -sRadius; k1 <= sRadius; ++k1) {
               k1 = Math.abs(k1);
               k2 = Math.abs(k1);
               j1 = k1 * k1 + k1 * k1;
               if (j1 < sWallThresholdMax) {
                  for(int j1 = i1 + 1; j1 <= i1 + sectionHeight; ++j1) {
                     if (j1 < sWallThresholdMin) {
                        if (j1 == i1 + sectionHeight) {
                           this.setBlockAndMetadata(world, k1, j1, k1, this.brickBlock, this.brickMeta);
                        } else {
                           this.setAir(world, k1, j1, k1);
                        }
                     } else if ((k1 != 4 || k2 != 4) && (k1 != sRadius || k2 != 1) && (k2 != sRadius || k1 != 1)) {
                        this.setBlockAndMetadata(world, k1, j1, k1, this.brickBlock, this.brickMeta);
                     } else {
                        this.setBlockAndMetadata(world, k1, j1, k1, this.pillarBlock, this.pillarMeta);
                     }
                  }
               }

               if (k1 == 0 && k2 == sRadius || k2 == 0 && k1 == sRadius) {
                  this.setBlockAndMetadata(world, k1, i1 + 1, k1, this.pillarBlock, this.pillarMeta);
                  this.setBlockAndMetadata(world, k1, i1 + 3, k1, LOTRMod.highElfWoodBars, 0);
                  this.setBlockAndMetadata(world, k1, i1 + 4, k1, LOTRMod.highElfWoodBars, 0);
                  this.setBlockAndMetadata(world, k1, i1 + 6, k1, this.pillarBlock, this.pillarMeta);
               }

               if (k1 == 1 && k2 == sRadius - 1 || k2 == 1 && k1 == sRadius - 1) {
                  this.setBlockAndMetadata(world, k1, i1 + 4, k1, this.fenceBlock, this.fenceMeta);
                  this.setBlockAndMetadata(world, k1, i1 + 5, k1, LOTRMod.highElvenTorch, 5);
               }

               if (k1 == 3 && k2 == 4 || k2 == 3 && k1 == 4) {
                  this.setBlockAndMetadata(world, k1, i1 + 1, k1, this.plankBlock, this.plankMeta);
                  if (random.nextInt(4) == 0) {
                     if (random.nextBoolean()) {
                        this.placeMug(world, random, k1, i1 + 2, k1, random.nextInt(4), LOTRFoods.ELF_DRINK);
                     } else {
                        this.placePlate(world, random, k1, i1 + 2, k1, this.plateBlock, LOTRFoods.ELF);
                     }
                  }

                  this.setBlockAndMetadata(world, k1, i1 + 6, k1, this.fenceBlock, this.fenceMeta);
                  this.setBlockAndMetadata(world, k1, i1 + 7, k1, this.leafBlock, this.leafMeta);
               }
            }
         }

         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, k1, i1 + 1, -sRadius + 1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, k1, i1 + 1, sRadius - 1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, k1, i1 + 7, -sRadius + 1, this.plankStairBlock, 7);
            this.setBlockAndMetadata(world, k1, i1 + 7, sRadius - 1, this.plankStairBlock, 6);
         }

         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, -sRadius + 1, i1 + 1, k1, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, sRadius - 1, i1 + 1, k1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -sRadius + 1, i1 + 7, k1, this.plankStairBlock, 4);
            this.setBlockAndMetadata(world, sRadius - 1, i1 + 7, k1, this.plankStairBlock, 5);
         }

         this.setBlockAndMetadata(world, -sRadius, i1 + 2, 0, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, sRadius, i1 + 2, 0, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, 0, i1 + 2, -sRadius, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, 0, i1 + 2, sRadius, this.brickStairBlock, 2);
         this.setBlockAndMetadata(world, -sRadius, i1 + 5, 0, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, sRadius, i1 + 5, 0, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, 0, i1 + 5, -sRadius, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, 0, i1 + 5, sRadius, this.brickStairBlock, 6);
         LOTREntityElf warrior = new LOTREntityHighElfWarrior(world);
         warrior.spawnRidingHorse = false;
         this.spawnNPCAndSetHome(warrior, world, 3, i1 + 1, 0, 16);
      }

      sectionTopHeight = 7 + sections * sectionHeight;

      for(i1 = 2; i1 <= sectionTopHeight; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, 0, this.pillarBlock, this.pillarMeta);
         k1 = (i1 + 2) % 4;
         if (k1 == 0) {
            for(k1 = -2; k1 <= -1; ++k1) {
               this.setBlockAndMetadata(world, k1, i1, 0, this.brickSlabBlock, this.brickSlabMeta);
               this.setBlockAndMetadata(world, k1, i1, 1, this.brickSlabBlock, this.brickSlabMeta | 8);
               this.setBlockAndMetadata(world, k1, i1, 2, this.brickSlabBlock, this.brickSlabMeta | 8);

               for(k1 = i1 + 1; k1 <= i1 + 3; ++k1) {
                  this.setAir(world, k1, k1, 0);
                  this.setAir(world, k1, k1, 1);
                  this.setAir(world, k1, k1, 2);
               }
            }

            this.setBlockAndMetadata(world, 0, i1, 1, LOTRMod.highElvenTorch, 3);
         } else if (k1 == 1) {
            for(k1 = 1; k1 <= 2; ++k1) {
               this.setBlockAndMetadata(world, 0, i1, k1, this.brickSlabBlock, this.brickSlabMeta);
               this.setBlockAndMetadata(world, 1, i1, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
               this.setBlockAndMetadata(world, 2, i1, k1, this.brickSlabBlock, this.brickSlabMeta | 8);

               for(k1 = i1 + 1; k1 <= i1 + 3; ++k1) {
                  this.setAir(world, 0, k1, k1);
                  this.setAir(world, 1, k1, k1);
                  this.setAir(world, 2, k1, k1);
               }
            }
         } else if (k1 == 2) {
            for(k1 = 1; k1 <= 2; ++k1) {
               this.setBlockAndMetadata(world, k1, i1, 0, this.brickSlabBlock, this.brickSlabMeta);
               this.setBlockAndMetadata(world, k1, i1, -1, this.brickSlabBlock, this.brickSlabMeta | 8);
               this.setBlockAndMetadata(world, k1, i1, -2, this.brickSlabBlock, this.brickSlabMeta | 8);

               for(k1 = i1 + 1; k1 <= i1 + 3; ++k1) {
                  this.setAir(world, k1, k1, 0);
                  this.setAir(world, k1, k1, -1);
                  this.setAir(world, k1, k1, -2);
               }
            }

            this.setBlockAndMetadata(world, 0, i1, -1, LOTRMod.highElvenTorch, 4);
         } else if (k1 == 3) {
            for(k1 = -2; k1 <= -1; ++k1) {
               this.setBlockAndMetadata(world, 0, i1, k1, this.brickSlabBlock, this.brickSlabMeta);
               this.setBlockAndMetadata(world, -1, i1, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
               this.setBlockAndMetadata(world, -2, i1, k1, this.brickSlabBlock, this.brickSlabMeta | 8);

               for(k1 = i1 + 1; k1 <= i1 + 3; ++k1) {
                  this.setAir(world, 0, k1, k1);
                  this.setAir(world, -1, k1, k1);
                  this.setAir(world, -2, k1, k1);
               }
            }
         }
      }

      for(i1 = -radius; i1 <= radius; ++i1) {
         for(k1 = -radius; k1 <= radius; ++k1) {
            k1 = Math.abs(i1);
            k1 = Math.abs(k1);
            k2 = i1 * i1 + k1 * k1;
            if (k2 < wallThresholdMax) {
               if (k2 >= wallThresholdMin) {
                  this.setBlockAndMetadata(world, i1, sectionTopHeight + 1, k1, this.pillarBlock, this.pillarMeta);

                  for(j1 = sectionTopHeight + 2; j1 <= sectionTopHeight + 5; ++j1) {
                     if ((k1 != 5 || k1 != 5) && (k1 != radius || k1 != 2) && (k1 != radius || k1 != 2)) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                     } else {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.pillarBlock, this.pillarMeta);
                     }
                  }

                  this.setBlockAndMetadata(world, i1, sectionTopHeight + 6, k1, this.pillarBlock, this.pillarMeta);
                  this.setBlockAndMetadata(world, i1, sectionTopHeight + 7, k1, this.roofBlock, this.roofMeta);
                  this.setBlockAndMetadata(world, i1, sectionTopHeight + 8, k1, this.roofBlock, this.roofMeta);
               } else {
                  for(j1 = sectionTopHeight + 1; j1 <= sectionTopHeight + 6; ++j1) {
                     this.setAir(world, i1, j1, k1);
                  }
               }

               if (k1 == 2 && k1 == radius - 1 || k1 == 2 && k1 == radius - 1) {
                  this.setBlockAndMetadata(world, i1, sectionTopHeight + 4, k1, this.fenceBlock, this.fenceMeta);
                  this.setBlockAndMetadata(world, i1, sectionTopHeight + 5, k1, LOTRMod.highElvenTorch, 5);
               }

               if (k1 <= 1 && k1 == radius - 1 || k1 <= 1 && k1 == radius - 1 || k1 >= 3 && k1 <= 4 && k1 == radius - 2 || k1 >= 3 && k1 <= 4 && k1 == radius - 2) {
                  this.setBlockAndMetadata(world, i1, sectionTopHeight + 6, k1, this.fenceBlock, this.fenceMeta);
                  this.setBlockAndMetadata(world, i1, sectionTopHeight + 7, k1, this.leafBlock, this.leafMeta);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 0, sectionTopHeight + 1, 0, this.pillarBlock, this.pillarMeta);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 2, 0, this.pillarBlock, this.pillarMeta);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 3, 0, this.roofSlabBlock, this.roofSlabMeta);

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight, -radius, this.roofStairBlock, 6);
         this.setBlockAndMetadata(world, i1, sectionTopHeight, radius, this.roofStairBlock, 7);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -radius, sectionTopHeight, i1, this.roofStairBlock, 5);
         this.setBlockAndMetadata(world, radius, sectionTopHeight, i1, this.roofStairBlock, 4);
      }

      for(i1 = -4; i1 <= -3; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight, -radius + 1, this.roofStairBlock, 6);
         this.setBlockAndMetadata(world, i1, sectionTopHeight, radius - 1, this.roofStairBlock, 7);
      }

      for(i1 = 3; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight, -radius + 1, this.roofStairBlock, 6);
         this.setBlockAndMetadata(world, i1, sectionTopHeight, radius - 1, this.roofStairBlock, 7);
      }

      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, -3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, 3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, -3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, 3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, -2, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, -4, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, -5, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, -2, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, -4, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, -5, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, 2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight, 4, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, 5, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, 2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight, 4, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, 5, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, -4, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -4, sectionTopHeight, -radius + 2, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -2, sectionTopHeight, -radius + 1, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, -4, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 4, sectionTopHeight, -radius + 2, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 2, sectionTopHeight, -radius + 1, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight, 4, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -4, sectionTopHeight, radius - 2, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, -2, sectionTopHeight, radius - 1, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight, 4, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 4, sectionTopHeight, radius - 2, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 2, sectionTopHeight, radius - 1, this.roofStairBlock, 4);

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 1, -radius + 1, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 1, radius - 1, this.brickStairBlock, 2);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 1, i1, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 1, i1, this.brickStairBlock, 1);
      }

      for(i1 = -4; i1 <= -3; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 1, -radius + 2, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 1, radius - 2, this.brickStairBlock, 2);
      }

      for(i1 = 3; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 1, -radius + 2, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 1, radius - 2, this.brickStairBlock, 2);
      }

      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, -4, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, -3, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, -4, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, -3, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, 3, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 1, 4, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, 3, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 1, 4, this.brickStairBlock, 2);
      int[] var37 = new int[]{-radius + 2, radius - 2};
      k1 = var37.length;

      for(k1 = 0; k1 < k1; ++k1) {
         k1 = var37[k1];
         this.setBlockAndMetadata(world, -2, sectionTopHeight + 1, k1, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, 2, sectionTopHeight + 1, k1, this.brickStairBlock, 1);
      }

      var37 = new int[]{-radius + 2, radius - 2};
      k1 = var37.length;

      for(k1 = 0; k1 < k1; ++k1) {
         k1 = var37[k1];
         this.setBlockAndMetadata(world, k1, sectionTopHeight + 1, -2, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, k1, sectionTopHeight + 1, 2, this.brickStairBlock, 2);
      }

      var37 = new int[]{-4, 4};
      k1 = var37.length;

      for(k1 = 0; k1 < k1; ++k1) {
         k1 = var37[k1];
         this.setBlockAndMetadata(world, -4, sectionTopHeight + 1, k1, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, 4, sectionTopHeight + 1, k1, this.brickStairBlock, 1);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 2, -radius, this.brickStairBlock, 3);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 3, -radius, LOTRMod.highElfWoodBars, 0);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 4, -radius, LOTRMod.highElfWoodBars, 0);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 5, -radius, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 2, radius, this.brickStairBlock, 2);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 3, radius, LOTRMod.highElfWoodBars, 0);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 4, radius, LOTRMod.highElfWoodBars, 0);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 5, radius, this.brickStairBlock, 6);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -radius, sectionTopHeight + 2, i1, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, -radius, sectionTopHeight + 3, i1, LOTRMod.highElfWoodBars, 0);
         this.setBlockAndMetadata(world, -radius, sectionTopHeight + 4, i1, LOTRMod.highElfWoodBars, 0);
         this.setBlockAndMetadata(world, -radius, sectionTopHeight + 5, i1, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, radius, sectionTopHeight + 2, i1, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, radius, sectionTopHeight + 3, i1, LOTRMod.highElfWoodBars, 0);
         this.setBlockAndMetadata(world, radius, sectionTopHeight + 4, i1, LOTRMod.highElfWoodBars, 0);
         this.setBlockAndMetadata(world, radius, sectionTopHeight + 5, i1, this.brickStairBlock, 5);
      }

      this.placeWallBanner(world, -radius + 1, sectionTopHeight + 4, -4, LOTRItemBanner.BannerType.HIGH_ELF, 1);
      this.placeWallBanner(world, -radius + 1, sectionTopHeight + 4, 4, LOTRItemBanner.BannerType.HIGH_ELF, 1);
      this.placeWallBanner(world, radius - 1, sectionTopHeight + 4, -4, LOTRItemBanner.BannerType.HIGH_ELF, 3);
      this.placeWallBanner(world, radius - 1, sectionTopHeight + 4, 4, LOTRItemBanner.BannerType.HIGH_ELF, 3);

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 7, -radius - 1, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 7, radius + 1, this.roofStairBlock, 3);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -radius - 1, sectionTopHeight + 7, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, radius + 1, sectionTopHeight + 7, i1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, -4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, -radius, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 7, -radius, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 7, -radius, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, -4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, -radius + 2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, -radius + 1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, -radius, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 7, -radius, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 7, -radius, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, 3, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, 4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 7, radius, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 7, radius, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 7, radius, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, 3, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, 4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, radius - 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, radius - 1, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 7, radius, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 7, radius, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 7, radius, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -4, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, -2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, 2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 4, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, radius - 2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, radius - 1, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, radius - 1, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, radius - 2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 4, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, 2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, -2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -4, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, -radius + 2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, -radius + 1, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, -radius + 1, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, -radius + 2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, -radius + 1, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 8, -radius + 1, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, -radius + 1, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, -4, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, -4, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, -3, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -2, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, -1, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, -1, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, 0, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 8, 1, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 1, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 2, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 8, 3, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, 3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 8, 4, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, 4, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 8, radius - 1, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 8, radius - 1, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, radius - 1, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, radius - 2, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, 4, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, 4, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, 3, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 2, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, 1, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, 1, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, 0, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 8, -1, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -1, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -2, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 8, -3, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, -3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 8, -4, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, -4, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 8, -radius + 2, this.roofStairBlock, 4);

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 9, -radius, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 9, radius, this.roofStairBlock, 3);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -radius, sectionTopHeight + 9, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, radius, sectionTopHeight + 9, i1, this.roofStairBlock, 0);
      }

      for(i1 = -4; i1 <= -3; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 3);
      }

      for(i1 = 3; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 3);
      }

      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, -3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, -3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, 3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, 3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 9, -radius + 1, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 9, radius - 1, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, -2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, -2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, -4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, -radius + 2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, -4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 9, 4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 9, 4, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, radius - 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 9, 4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 9, 4, this.roofStairBlock, 3);

      for(i1 = sectionTopHeight + 9; i1 <= sectionTopHeight + 10; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, k1, i1, -radius + 1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, k1, i1, radius - 1, this.roofBlock, this.roofMeta);
         }

         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, -radius + 1, i1, k1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, radius - 1, i1, k1, this.roofBlock, this.roofMeta);
         }

         for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, k1, i1, -radius + 2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, k1, i1, radius - 2, this.roofBlock, this.roofMeta);
         }

         for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, -radius + 2, i1, k1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, radius - 2, i1, k1, this.roofBlock, this.roofMeta);
         }

         this.setBlockAndMetadata(world, -4, i1, -3, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -4, i1, -4, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -3, i1, -4, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 4, i1, -3, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 4, i1, -4, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 3, i1, -4, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -4, i1, 3, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -4, i1, 4, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -3, i1, 4, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 4, i1, 3, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 4, i1, 4, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 3, i1, 4, this.roofBlock, this.roofMeta);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 10, -4, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 10, 4, this.roofStairBlock, 6);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -4, sectionTopHeight + 10, i1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, 4, sectionTopHeight + 10, i1, this.roofStairBlock, 5);
      }

      this.setBlockAndMetadata(world, -2, sectionTopHeight + 10, -3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, -3, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, -2, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 10, -2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 10, -3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, -3, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, -2, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 10, -2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 10, 3, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, 3, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 10, 2, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 10, 2, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 10, 3, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, 3, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 10, 2, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 10, 2, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, -radius + 1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, -radius + 1, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, -4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, -3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, -2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 11, 0, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 1, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, 3, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 11, 4, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, 4, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 11, radius - 1, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, radius - 1, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, radius - 1, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, radius - 2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, 4, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, 4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, 3, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 11, 1, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 11, 0, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 1, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, -1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, -2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, -3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 11, -4, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, -4, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, -radius + 2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 11, -radius + 1, this.roofStairBlock, 1);

      for(i1 = sectionTopHeight + 11; i1 <= sectionTopHeight + 12; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, k1, i1, -4, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, k1, i1, 4, this.roofBlock, this.roofMeta);
         }

         for(k1 = -2; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, -4, i1, k1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 4, i1, k1, this.roofBlock, this.roofMeta);
         }

         this.setBlockAndMetadata(world, -3, i1, -2, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -3, i1, -3, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -2, i1, -3, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 3, i1, -2, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 3, i1, -3, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 2, i1, -3, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -3, i1, 2, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -3, i1, 3, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -2, i1, 3, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 3, i1, 2, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 3, i1, 3, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 2, i1, 3, this.roofBlock, this.roofMeta);
      }

      this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, -radius + 2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, -radius + 2, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 11, radius - 2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, radius - 2, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 11, 0, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -radius + 2, sectionTopHeight + 12, 0, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 11, 0, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, radius - 2, sectionTopHeight + 12, 0, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, -3, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, -3, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, -2, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, -2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, -1, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 12, -1, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 12, 0, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 12, 1, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, 1, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 12, 2, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, 2, this.roofStairBlock, 5);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 12, 3, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 12, 3, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, 3, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, 2, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, 2, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, 1, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 12, 1, this.roofStairBlock, 6);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 12, 0, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 12, -1, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, -1, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 12, -2, this.roofStairBlock, 7);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, -2, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 12, -3, this.roofStairBlock, 7);

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 13, -4, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 13, 4, this.roofStairBlock, 3);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, -4, sectionTopHeight + 13, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 4, sectionTopHeight + 13, i1, this.roofStairBlock, 0);
      }

      this.setBlockAndMetadata(world, -2, sectionTopHeight + 13, -3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, -2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 13, -2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 13, -3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, -3, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, -2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 13, -2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 13, 3, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, 3, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 13, 2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -4, sectionTopHeight + 13, 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 13, 3, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, 3, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 13, 2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 4, sectionTopHeight + 13, 2, this.roofStairBlock, 3);

      for(i1 = sectionTopHeight + 13; i1 <= sectionTopHeight + 14; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, k1, i1, -3, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, k1, i1, 3, this.roofBlock, this.roofMeta);
         }

         for(k1 = -2; k1 <= 2; ++k1) {
            this.setBlockAndMetadata(world, k1, i1, -2, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, k1, i1, 2, this.roofBlock, this.roofMeta);
         }

         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, -3, i1, k1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -2, i1, k1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 2, i1, k1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 3, i1, k1, this.roofBlock, this.roofMeta);
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 14, -1, this.roofStairBlock, 7);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 14, 1, this.roofStairBlock, 6);
      }

      this.setBlockAndMetadata(world, -1, sectionTopHeight + 14, 0, this.roofStairBlock, 4);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 14, 0, this.roofStairBlock, 5);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 15, -3, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 15, 3, this.roofStairBlock, 3);
      }

      this.setBlockAndMetadata(world, -1, sectionTopHeight + 15, -2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, -2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, -1, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 15, -1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 15, 0, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -3, sectionTopHeight + 15, 1, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, 1, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, -1, sectionTopHeight + 15, 2, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 15, -2, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, -2, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, -1, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 15, -1, this.roofStairBlock, 2);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 15, 0, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 3, sectionTopHeight + 15, 1, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, 1, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, 2, this.roofStairBlock, 3);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 15, 2, this.roofStairBlock, 0);

      for(i1 = sectionTopHeight + 15; i1 <= sectionTopHeight + 16; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            for(k1 = -1; k1 <= 1; ++k1) {
               this.setBlockAndMetadata(world, k1, i1, k1, this.roofBlock, this.roofMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, 0, sectionTopHeight + 15, -2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 16, -2, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 15, 2, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 16, 2, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 15, 0, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, -2, sectionTopHeight + 16, 0, this.roofSlabBlock, this.roofSlabMeta);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 15, 0, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 2, sectionTopHeight + 16, 0, this.roofSlabBlock, this.roofSlabMeta);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 17, -1, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, i1, sectionTopHeight + 17, 1, this.roofStairBlock, 3);
      }

      this.setBlockAndMetadata(world, -1, sectionTopHeight + 17, 0, this.roofStairBlock, 1);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 17, 0, this.roofStairBlock, 0);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 17, 0, this.roofBlock, this.roofMeta);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 18, 0, this.roofSlabBlock, this.roofSlabMeta);

      for(i1 = sectionTopHeight + 10; i1 <= sectionTopHeight + 14; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, 0, Blocks.field_150422_aJ, 2);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            if (i1 == 0 || k1 == 0) {
               this.setBlockAndMetadata(world, i1, sectionTopHeight + 10, k1, Blocks.field_150422_aJ, 2);
            }

            if (i1 == 0 && Math.abs(k1) == 2 || k1 == 0 && Math.abs(i1) == 2) {
               this.setBlockAndMetadata(world, i1, sectionTopHeight + 9, k1, LOTRMod.chandelier, 10);
            }
         }
      }

      this.setBlockAndMetadata(world, -1, sectionTopHeight + 1, 6, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 1, 6, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 1, sectionTopHeight + 1, 6, LOTRMod.highElvenTable, 0);
      this.setBlockAndMetadata(world, 0, sectionTopHeight + 1, -4, LOTRMod.commandTable, 0);
      LOTREntityHighElfLord elfLord = new LOTREntityHighElfLord(world);
      elfLord.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(elfLord, world, 0, sectionTopHeight + 1, 1, 16);
      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClass(LOTREntityHighElfWarrior.class);
      respawner.setCheckRanges(12, -16, 50, 12);
      respawner.setSpawnRanges(6, 0, 20, 16);
      this.placeNPCRespawner(respawner, world, 0, 0, 0);
      return true;
   }

   private void layFoundation(World world, int i, int k) {
      for(int j = 0; (j == 0 || !this.isOpaque(world, i, j, k)) && this.getY(j) >= 0; --j) {
         this.setBlockAndMetadata(world, i, j, k, this.brickBlock, this.brickMeta);
         this.setGrassToDirt(world, i, j - 1, k);
      }

   }
}
