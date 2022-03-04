package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityRohanBlacksmith;
import lotr.common.entity.npc.LOTREntityRohirrimArcher;
import lotr.common.entity.npc.LOTREntityRohirrimMarshal;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanFortress extends LOTRWorldGenRohanStructure {
   public LOTRWorldGenRohanFortress(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.gateBlock = LOTRMod.gateRohan;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 13);
      this.setupRandomBlocks(random);
      int j1;
      int i1;
      int j1;
      if (this.restrictions) {
         for(j1 = -12; j1 <= 12; ++j1) {
            for(i1 = -12; i1 <= 12; ++i1) {
               j1 = this.getTopBlock(world, j1, i1) - 1;
               if (!this.isSurface(world, j1, j1, i1)) {
                  return false;
               }
            }
         }
      }

      int stairHeight;
      int j1;
      int randomGround;
      for(j1 = -12; j1 <= 12; ++j1) {
         for(i1 = -12; i1 <= 12; ++i1) {
            j1 = Math.abs(j1);
            stairHeight = Math.abs(i1);

            for(j1 = 1; j1 <= 10; ++j1) {
               this.setAir(world, j1, j1, i1);
            }

            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, j1, j1, i1)) && this.getY(j1) >= 0; --j1) {
               if (j1 == 12 && (stairHeight == 12 || stairHeight == 9 || stairHeight == 2) || stairHeight == 12 && (j1 == 12 || j1 == 9 || j1 == 2)) {
                  this.setBlockAndMetadata(world, j1, j1, i1, this.woodBeam2Block, this.woodBeam2Meta);
               } else if (j1 <= 9 && stairHeight <= 9) {
                  if (j1 == 0) {
                     randomGround = random.nextInt(3);
                     if (randomGround == 0) {
                        this.setBlockAndMetadata(world, j1, j1, i1, Blocks.field_150349_c, 0);
                     } else if (randomGround == 1) {
                        this.setBlockAndMetadata(world, j1, j1, i1, Blocks.field_150346_d, 1);
                     } else if (randomGround == 2) {
                        this.setBlockAndMetadata(world, j1, j1, i1, LOTRMod.dirtPath, 0);
                     }

                     if (random.nextInt(3) == 0) {
                        this.setBlockAndMetadata(world, j1, j1 + 1, i1, LOTRMod.thatchFloor, 0);
                     }
                  } else {
                     this.setBlockAndMetadata(world, j1, j1, i1, Blocks.field_150346_d, 0);
                  }
               } else {
                  this.setBlockAndMetadata(world, j1, j1, i1, this.plankBlock, this.plankMeta);
               }

               this.setGrassToDirt(world, j1, j1 - 1, i1);
            }
         }
      }

      for(j1 = -12; j1 <= 12; ++j1) {
         for(i1 = -12; i1 <= 12; ++i1) {
            j1 = Math.abs(j1);
            stairHeight = Math.abs(i1);
            int yBoost = 0;
            if (i1 < 8 && j1 < 7) {
               yBoost = 1;
            }

            if (j1 != 9 && j1 != 12 || stairHeight != 9 && stairHeight != 12) {
               if ((j1 == 12 || stairHeight == 12) && (stairHeight == 2 || j1 == 2)) {
                  for(randomGround = 1; randomGround <= 6 + yBoost; ++randomGround) {
                     this.setBlockAndMetadata(world, j1, randomGround, i1, this.woodBeam2Block, this.woodBeam2Meta);
                  }
               } else if (j1 != 12 && stairHeight != 12) {
                  if (j1 <= 9 && stairHeight <= 9) {
                     if (j1 == 9 || stairHeight == 9) {
                        this.setBlockAndMetadata(world, j1, 5 + yBoost, i1, this.fenceBlock, this.fenceMeta);
                        if (j1 == 9 && IntMath.mod(i1, 3) == 0 || stairHeight == 9 && IntMath.mod(j1, 3) == 0) {
                           this.setBlockAndMetadata(world, j1, 6 + yBoost, i1, Blocks.field_150478_aa, 5);
                        }

                        if (i1 == -9) {
                           this.setBlockAndMetadata(world, j1, 4 + yBoost, i1, this.plank2StairBlock, 7);
                        } else if (i1 == 9) {
                           this.setBlockAndMetadata(world, j1, 4 + yBoost, i1, this.plank2StairBlock, 6);
                        } else if (j1 == -9) {
                           this.setBlockAndMetadata(world, j1, 4 + yBoost, i1, this.plank2StairBlock, 4);
                        } else if (j1 == 9) {
                           this.setBlockAndMetadata(world, j1, 4 + yBoost, i1, this.plank2StairBlock, 5);
                        }
                     }
                  } else {
                     for(randomGround = 1; randomGround <= 4 + yBoost; ++randomGround) {
                        this.setBlockAndMetadata(world, j1, randomGround, i1, this.plankBlock, this.plankMeta);
                     }
                  }
               } else {
                  for(randomGround = 1; randomGround <= 5 + yBoost; ++randomGround) {
                     this.setBlockAndMetadata(world, j1, randomGround, i1, this.plankBlock, this.plankMeta);
                  }

                  if ((j1 != 12 || stairHeight < 10 || stairHeight > 11) && (stairHeight != 12 || j1 < 10 || j1 > 11)) {
                     if (IntMath.mod(j1 + stairHeight, 2) == 0) {
                        this.setBlockAndMetadata(world, j1, 6 + yBoost, i1, this.plankBlock, this.plankMeta);
                     } else {
                        this.setBlockAndMetadata(world, j1, 6 + yBoost, i1, this.plankSlabBlock, this.plankSlabMeta);
                     }
                  } else {
                     this.setBlockAndMetadata(world, j1, 5 + yBoost, i1, this.fenceBlock, this.fenceMeta);
                  }
               }
            } else {
               for(randomGround = 1; randomGround <= 8; ++randomGround) {
                  this.setBlockAndMetadata(world, j1, randomGround, i1, this.woodBeam2Block, this.woodBeam2Meta);
               }
            }
         }
      }

      int[] var17 = new int[]{-12, 9};
      i1 = var17.length;

      for(j1 = 0; j1 < i1; ++j1) {
         stairHeight = var17[j1];
         int[] var20 = new int[]{-12, 9};
         randomGround = var20.length;

         for(int var13 = 0; var13 < randomGround; ++var13) {
            int k1 = var20[var13];
            this.setBlockAndMetadata(world, stairHeight + 1, 8, k1, this.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, stairHeight + 2, 8, k1, this.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, stairHeight + 1, 8, k1 + 3, this.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, stairHeight + 2, 8, k1 + 3, this.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, stairHeight, 8, k1 + 1, this.plank2StairBlock, 7);
            this.setBlockAndMetadata(world, stairHeight, 8, k1 + 2, this.plank2StairBlock, 6);
            this.setBlockAndMetadata(world, stairHeight + 3, 8, k1 + 1, this.plank2StairBlock, 7);
            this.setBlockAndMetadata(world, stairHeight + 3, 8, k1 + 2, this.plank2StairBlock, 6);

            int i2;
            for(i2 = stairHeight; i2 <= stairHeight + 3; ++i2) {
               this.setBlockAndMetadata(world, i2, 9, k1 - 1, this.roofSlabBlock, this.roofSlabMeta);
               this.setBlockAndMetadata(world, i2, 9, k1 + 4, this.roofSlabBlock, this.roofSlabMeta);
            }

            for(i2 = k1; i2 <= k1 + 3; ++i2) {
               this.setBlockAndMetadata(world, stairHeight - 1, 9, i2, this.roofSlabBlock, this.roofSlabMeta);
               this.setBlockAndMetadata(world, stairHeight + 4, 9, i2, this.roofSlabBlock, this.roofSlabMeta);
            }

            for(i2 = stairHeight; i2 <= stairHeight + 3; ++i2) {
               for(int k2 = k1; k2 <= k1 + 3; ++k2) {
                  if (i2 >= stairHeight + 1 && i2 <= stairHeight + 2 && k2 >= k1 + 1 && k2 <= k1 + 2) {
                     this.setBlockAndMetadata(world, i2, 9, k2, this.roofSlabBlock, this.roofSlabMeta | 8);
                     this.setBlockAndMetadata(world, i2, 10, k2, this.roofSlabBlock, this.roofSlabMeta);
                  } else {
                     this.setBlockAndMetadata(world, i2, 9, k2, this.roofBlock, this.roofMeta);
                  }
               }
            }
         }
      }

      for(j1 = -12; j1 <= 12; ++j1) {
         i1 = Math.abs(j1);
         if (i1 >= 10 && i1 <= 11 || i1 >= 3 && i1 <= 8) {
            this.setBlockAndMetadata(world, -12, 1, j1, this.plank2StairBlock, 1);

            for(j1 = 2; j1 <= 3; ++j1) {
               this.setAir(world, -12, j1, j1);
            }

            this.setBlockAndMetadata(world, -12, 4, j1, this.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 12, 1, j1, this.plank2StairBlock, 0);

            for(j1 = 2; j1 <= 3; ++j1) {
               this.setAir(world, 12, j1, j1);
            }

            this.setBlockAndMetadata(world, 12, 4, j1, this.plank2StairBlock, 4);
         }

         if (i1 == 12 && j1 > 0) {
            for(j1 = -1; j1 <= 1; ++j1) {
               this.setBlockAndMetadata(world, j1, 1, j1, this.plank2Block, this.plank2Meta);
               this.setBlockAndMetadata(world, j1, 4, j1, this.plank2Block, this.plank2Meta);
               this.setBlockAndMetadata(world, j1, 5, j1, this.woodBeam2Block, this.woodBeam2Meta | 4);
               this.setBlockAndMetadata(world, j1, 6, j1, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, j1, 5, j1 - 1 * Integer.signum(j1), this.plankSlabBlock, this.plankSlabMeta);
            }

            this.setBlockAndMetadata(world, -2, 7, j1, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, 2, 7, j1, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, -1, 2, j1, this.plank2StairBlock, 0);
            this.setAir(world, 0, 2, j1);
            this.setBlockAndMetadata(world, 1, 2, j1, this.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -1, 3, j1, this.plank2StairBlock, 4);
            this.setAir(world, 0, 3, j1);
            this.setBlockAndMetadata(world, 1, 3, j1, this.plank2StairBlock, 5);
         }
      }

      for(j1 = -12; j1 <= 12; ++j1) {
         i1 = Math.abs(j1);
         if (i1 >= 10 && i1 <= 11 || i1 >= 3 && i1 <= 8) {
            this.setBlockAndMetadata(world, j1, 1, -12, this.plank2StairBlock, 2);

            for(j1 = 2; j1 <= 3; ++j1) {
               this.setAir(world, j1, j1, -12);
            }

            this.setBlockAndMetadata(world, j1, 4, -12, this.plank2StairBlock, 6);
            this.setBlockAndMetadata(world, j1, 1, 12, this.plank2StairBlock, 3);

            for(j1 = 2; j1 <= 3; ++j1) {
               this.setAir(world, j1, j1, 12);
            }

            this.setBlockAndMetadata(world, j1, 4, 12, this.plank2StairBlock, 7);
         }

         if (i1 == 12) {
            for(j1 = -1; j1 <= 1; ++j1) {
               this.setBlockAndMetadata(world, j1, 1, j1, this.plank2Block, this.plank2Meta);
               this.setBlockAndMetadata(world, j1, 4, j1, this.plank2Block, this.plank2Meta);
               this.setBlockAndMetadata(world, j1, 5, j1, this.woodBeam2Block, this.woodBeam2Meta | 8);
               this.setBlockAndMetadata(world, j1, 6, j1, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, j1 - 1 * Integer.signum(j1), 5, j1, this.plankSlabBlock, this.plankSlabMeta);
            }

            this.setBlockAndMetadata(world, j1, 7, -2, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, j1, 7, 2, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, j1, 2, -1, this.plank2StairBlock, 3);
            this.setAir(world, j1, 2, 0);
            this.setBlockAndMetadata(world, j1, 2, 1, this.plank2StairBlock, 2);
            this.setBlockAndMetadata(world, j1, 3, -1, this.plank2StairBlock, 7);
            this.setAir(world, j1, 3, 0);
            this.setBlockAndMetadata(world, j1, 3, 1, this.plank2StairBlock, 6);
         }
      }

      for(j1 = -11; j1 <= -10; ++j1) {
         this.setAir(world, -6, 5, j1);
         this.setBlockAndMetadata(world, -5, 5, j1, this.plankStairBlock, 1);
         this.setBlockAndMetadata(world, 5, 5, j1, this.plankStairBlock, 0);
         this.setAir(world, 6, 5, j1);
      }

      for(j1 = 4; j1 <= 7; ++j1) {
         this.setBlockAndMetadata(world, -6, j1, -9, this.woodBeam2Block, this.woodBeam2Meta);
         this.setBlockAndMetadata(world, 6, j1, -9, this.woodBeam2Block, this.woodBeam2Meta);
      }

      this.setBlockAndMetadata(world, -6, 8, -9, Blocks.field_150478_aa, 5);
      this.setBlockAndMetadata(world, 6, 8, -9, Blocks.field_150478_aa, 5);

      for(j1 = -12; j1 <= -10; ++j1) {
         for(i1 = 1; i1 <= 4; ++i1) {
            for(j1 = -1; j1 <= 1; ++j1) {
               this.setAir(world, j1, i1, j1);
            }

            this.setBlockAndMetadata(world, -2, i1, j1, this.woodBeam2Block, this.woodBeam2Meta);
            this.setBlockAndMetadata(world, 2, i1, j1, this.woodBeam2Block, this.woodBeam2Meta);
         }

         for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, j1, this.woodBeam2Block, this.woodBeam2Meta | 4);
         }
      }

      for(j1 = 1; j1 <= 3; ++j1) {
         for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, j1, -11, this.gateBlock, 3);
         }
      }

      for(j1 = -1; j1 <= 1; ++j1) {
         this.setBlockAndMetadata(world, j1, 4, -12, this.plank2StairBlock, 6);
         this.setBlockAndMetadata(world, j1, 5, -12, this.woodBeam2Block, this.woodBeam2Meta | 4);
         this.setBlockAndMetadata(world, j1, 6, -12, this.woodBeam2Block, this.woodBeam2Meta | 4);
         this.setBlockAndMetadata(world, j1, 6, -11, this.plankSlabBlock, this.plankSlabMeta);
      }

      for(j1 = 6; j1 <= 8; ++j1) {
         this.setBlockAndMetadata(world, 0, j1, -12, this.woodBeam2Block, this.woodBeam2Meta);
      }

      this.setBlockAndMetadata(world, -2, 8, -12, this.plankStairBlock, 1);
      this.setBlockAndMetadata(world, 0, 9, -12, this.plankSlabBlock, this.plankSlabMeta);
      this.setBlockAndMetadata(world, 2, 8, -12, this.plankStairBlock, 0);
      this.setBlockAndMetadata(world, -1, 7, -12, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 1, 7, -12, this.fenceBlock, this.fenceMeta);
      this.placeWallBanner(world, -2, 6, -12, this.bannerType, 2);
      this.placeWallBanner(world, 0, 7, -12, this.bannerType, 2);
      this.placeWallBanner(world, 2, 6, -12, this.bannerType, 2);
      this.setBlockAndMetadata(world, -2, 3, -13, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 3, -13, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -2, 3, -9, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 3, -9, Blocks.field_150478_aa, 3);

      for(j1 = -13; j1 <= 9; ++j1) {
         for(i1 = -1; i1 <= 1; ++i1) {
            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, j1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, j1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, j1 - 1, j1);
            }
         }

         if (j1 > -10) {
            this.setBlockAndMetadata(world, -2, 0, j1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
            this.setBlockAndMetadata(world, 2, 0, j1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
            if (IntMath.mod(j1, 4) == 2) {
               this.setBlockAndMetadata(world, -2, 1, j1, this.brickWallBlock, this.brickWallMeta);
               this.setBlockAndMetadata(world, -2, 2, j1, Blocks.field_150478_aa, 5);
               this.setBlockAndMetadata(world, 2, 1, j1, this.brickWallBlock, this.brickWallMeta);
               this.setBlockAndMetadata(world, 2, 2, j1, Blocks.field_150478_aa, 5);
            }
         }
      }

      for(j1 = 1; j1 <= 3; ++j1) {
         this.setBlockAndMetadata(world, -2, j1, 10, this.woodBeam2Block, this.woodBeam2Meta);
         this.setBlockAndMetadata(world, 2, j1, 10, this.woodBeam2Block, this.woodBeam2Meta);
      }

      this.setBlockAndMetadata(world, -2, 3, 9, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 3, 9, Blocks.field_150478_aa, 4);
      var17 = new int[]{-7, 7};
      i1 = var17.length;

      for(j1 = 0; j1 < i1; ++j1) {
         stairHeight = var17[j1];
         this.setBlockAndMetadata(world, stairHeight, 1, 0, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, stairHeight, 2, 0, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, stairHeight, 3, 0, Blocks.field_150478_aa, 5);

         for(j1 = 0; j1 < 2; ++j1) {
            LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome(horse, world, stairHeight - Integer.signum(stairHeight) * 3, 1, 0, 0);
            horse.func_110214_p(0);
            horse.saddleMountForWorldGen();
            horse.func_110177_bN();
            this.leashEntityTo(horse, world, stairHeight, 2, 0);
         }
      }

      for(j1 = -9; j1 <= -5; ++j1) {
         for(i1 = -9; i1 <= -5; ++i1) {
            this.setBlockAndMetadata(world, i1, 3, j1, this.plank2SlabBlock, this.plank2SlabMeta);
         }
      }

      this.setBlockAndMetadata(world, -9, 3, -9, this.plank2Block, this.plank2Meta);
      this.setBlockAndMetadata(world, -6, 3, -9, this.plank2Block, this.plank2Meta);

      for(j1 = 1; j1 <= 2; ++j1) {
         if (j1 == 1) {
            this.setBlockAndMetadata(world, -7, j1, -9, Blocks.field_150460_al, 3);
            this.setBlockAndMetadata(world, -9, j1, -7, Blocks.field_150460_al, 4);
         } else {
            this.setBlockAndMetadata(world, -7, j1, -9, LOTRMod.alloyForge, 3);
            this.setBlockAndMetadata(world, -9, j1, -7, LOTRMod.alloyForge, 4);
         }

         this.setBlockAndMetadata(world, -8, j1, -9, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -9, j1, -9, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -9, j1, -8, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -5, j1, -5, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -5, 1, -9, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -5, 2, -9, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -6, 1, -9, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -6, 2, -9, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -9, 1, -6, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -9, 2, -6, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -9, 1, -5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -9, 2, -5, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -6, 1, -5, Blocks.field_150467_bQ, 1);
      this.setBlockAndMetadata(world, -5, 1, -6, Blocks.field_150383_bp, 3);
      LOTREntityRohanBlacksmith blacksmith = new LOTREntityRohanBlacksmith(world);
      this.spawnNPCAndSetHome(blacksmith, world, -4, 1, -4, 8);

      for(i1 = 5; i1 <= 9; ++i1) {
         for(j1 = -9; j1 <= -5; ++j1) {
            this.setBlockAndMetadata(world, j1, 3, i1, this.plank2SlabBlock, this.plank2SlabMeta);
         }
      }

      this.setBlockAndMetadata(world, -9, 3, 9, this.plank2Block, this.plank2Meta);

      for(i1 = 1; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -9, i1, 9, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -5, i1, 5, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -5, 1, 9, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -5, 2, 9, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -6, 1, 9, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -6, 2, 9, Blocks.field_150478_aa, 4);
      this.placeChest(world, random, -7, 1, 9, 2, LOTRChestContents.ROHAN_WATCHTOWER);
      this.placeChest(world, random, -8, 1, 9, 2, LOTRChestContents.ROHAN_WATCHTOWER);
      this.setBlockAndMetadata(world, -9, 1, 8, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -9, 1, 7, this.tableBlock, 0);
      this.setBlockAndMetadata(world, -9, 1, 6, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -9, 2, 6, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, -9, 1, 5, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, -9, 2, 5, this.fenceBlock, this.fenceMeta);

      for(i1 = 5; i1 <= 10; ++i1) {
         for(j1 = 5; j1 <= 10; ++j1) {
            this.setBlockAndMetadata(world, j1, 0, i1, this.plank2Block, this.plank2Meta);
            this.setAir(world, j1, 1, i1);
            this.setAir(world, j1, 2, i1);
            this.setBlockAndMetadata(world, j1, 3, i1, this.plank2Block, this.plank2Meta);
         }
      }

      for(i1 = 4; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, 4, 3, i1, this.plank2StairBlock, 1);
      }

      for(i1 = 5; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, i1, 3, 4, this.plank2StairBlock, 2);
      }

      for(i1 = 5; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, 5, 1, i1, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 5, 2, i1, this.plankBlock, this.plankMeta);
      }

      for(i1 = 6; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, 5, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, i1, 2, 5, this.plankBlock, this.plankMeta);
      }

      this.setBlockAndMetadata(world, 5, 0, 8, this.plank2Block, this.plank2Meta);
      this.setBlockAndMetadata(world, 5, 1, 8, this.doorBlock, 2);
      this.setBlockAndMetadata(world, 5, 2, 8, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 5, 1, 5, this.woodBeam2Block, this.woodBeam2Meta);
      this.setBlockAndMetadata(world, 5, 2, 5, this.woodBeam2Block, this.woodBeam2Meta);

      for(i1 = 6; i1 <= 10; ++i1) {
         if (IntMath.mod(i1, 2) == 0) {
            this.setBlockAndMetadata(world, i1, 2, 6, Blocks.field_150478_aa, 3);
            this.setBlockAndMetadata(world, i1, 2, 10, Blocks.field_150478_aa, 4);
         }

         for(j1 = 6; j1 <= 10; ++j1) {
            if (random.nextBoolean()) {
               this.setBlockAndMetadata(world, i1, 1, j1, LOTRMod.thatchFloor, 0);
            }
         }
      }

      int[] var23 = new int[]{6, 10};
      j1 = var23.length;

      for(stairHeight = 0; stairHeight < j1; ++stairHeight) {
         j1 = var23[stairHeight];
         this.setBlockAndMetadata(world, 7, 1, j1, this.bedBlock, 3);
         this.setBlockAndMetadata(world, 6, 1, j1, this.bedBlock, 11);
         this.setBlockAndMetadata(world, 9, 1, j1, this.bedBlock, 1);
         this.setBlockAndMetadata(world, 10, 1, j1, this.bedBlock, 9);
      }

      this.placeChest(world, random, 8, 1, 6, 3, this.chestContents);
      this.placeChest(world, random, 8, 1, 10, 2, this.chestContents);
      this.setBlockAndMetadata(world, 7, 1, 8, this.carpetBlock, this.carpetMeta);
      this.setBlockAndMetadata(world, 8, 1, 8, this.carpetBlock, this.carpetMeta);
      this.setBlockAndMetadata(world, 10, 1, 8, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, 10, 2, 8, 5, LOTRFoods.ROHAN_DRINK);

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 6, i1, -9, this.woodBeam2Block, this.woodBeam2Meta);
         this.setBlockAndMetadata(world, 7, i1, -9, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 8, i1, -9, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 9, i1, -9, this.woodBeam2Block, this.woodBeam2Meta);
      }

      for(i1 = -8; i1 <= -7; ++i1) {
         for(j1 = 6; j1 <= 9; ++j1) {
            stairHeight = j1 - 5;

            for(j1 = 0; j1 < stairHeight; ++j1) {
               this.setBlockAndMetadata(world, j1, j1, i1, this.plankBlock, this.plankMeta);
            }

            this.setBlockAndMetadata(world, j1, stairHeight, i1, this.plankStairBlock, 1);
         }

         this.setAir(world, 9, 5, i1);
      }

      this.placeWallBanner(world, -10, 3, 0, this.bannerType, 1);
      this.placeWallBanner(world, 10, 3, 0, this.bannerType, 3);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 0, 10, this.brickBlock, this.brickMeta);

         for(j1 = 1; j1 <= 3; ++j1) {
            this.setAir(world, i1, j1, 10);
         }
      }

      this.setBlockAndMetadata(world, 0, 1, 9, LOTRMod.commandTable, 0);
      this.placeWallBanner(world, 0, 3, 11, this.bannerType, 2);
      LOTREntityRohirrimMarshal marshal = new LOTREntityRohirrimMarshal(world);
      marshal.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(marshal, world, 0, 1, 0, 8);

      for(j1 = 0; j1 < 8; ++j1) {
         LOTREntityRohirrimWarrior rohirrim = world.field_73012_v.nextInt(3) == 0 ? new LOTREntityRohirrimArcher(world) : new LOTREntityRohirrimWarrior(world);
         ((LOTREntityRohirrimWarrior)rohirrim).spawnRidingHorse = false;
         this.spawnNPCAndSetHome((EntityCreature)rohirrim, world, 0, 1, 0, 20);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(LOTREntityRohirrimWarrior.class, LOTREntityRohirrimArcher.class);
      respawner.setCheckRanges(16, -8, 10, 12);
      respawner.setSpawnRanges(11, 1, 6, 20);
      this.placeNPCRespawner(respawner, world, 0, 0, 0);
      return true;
   }
}
