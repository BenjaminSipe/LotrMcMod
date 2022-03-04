package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.entity.npc.LOTREntityRohanStablemaster;
import lotr.common.entity.npc.LOTREntityRohirrimWarrior;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanStables extends LOTRWorldGenRohanStructure {
   public LOTRWorldGenRohanStables(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int k2;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -8; i2 <= 8; ++i2) {
            for(k2 = -1; k2 <= 26; ++k2) {
               j1 = this.getTopBlock(world, i2, k2) - 1;
               if (!this.isSurface(world, i2, j1, k2)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > k1) {
                  k1 = j1;
               }

               if (k1 - i1 > 8) {
                  return false;
               }
            }
         }
      }

      int j1;
      int hayDist;
      for(i1 = -7; i1 <= 7; ++i1) {
         for(k1 = 0; k1 <= 26; ++k1) {
            i2 = Math.abs(i1);
            k2 = IntMath.mod(k1, 4);
            if (k1 <= 12) {
               for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               for(j1 = 1; j1 <= 7; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            } else {
               this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150349_c, 0);

               for(j1 = -1; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
                  j1 = random.nextInt(4);
                  if (j1 == 0) {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 0);
                  } else if (j1 == 1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150351_n, 0);
                  } else if (j1 == 2) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.cobbleBlock, this.cobbleMeta);
                  } else if (j1 == 3) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.rockBlock, this.rockMeta);
                  }

                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               for(j1 = 1; j1 <= 6; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }

            if (k1 >= 0 && k1 <= 12) {
               if (k1 >= 1 && k1 <= 11) {
                  if (i2 >= 1 && i2 <= 2) {
                     this.setBlockAndMetadata(world, i1, 0, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                  }

                  if (i2 <= 2 && random.nextBoolean()) {
                     this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
                  }
               }

               if (i2 == 7 && k2 != 0) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, i1, 2, k1, this.fenceBlock, this.fenceMeta);
                  if (k2 == 2) {
                     this.setBlockAndMetadata(world, i1, 3, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
                  } else {
                     this.setBlockAndMetadata(world, i1, 3, k1, this.brickBlock, this.brickMeta);
                  }
               } else if ((k1 == 0 || k1 == 12) && i2 >= 4 && i2 <= 6) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, i1, 2, k1, this.fenceBlock, this.fenceMeta);
                  if (i2 == 5) {
                     this.setBlockAndMetadata(world, i1, 3, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
                  } else {
                     this.setBlockAndMetadata(world, i1, 3, k1, this.brickBlock, this.brickMeta);
                  }
               }

               if (i2 >= 3 && i2 <= 6 && k1 >= 1 && k1 <= 11) {
                  if (k2 == 0) {
                     if (i2 >= 4) {
                        this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);
                        this.setBlockAndMetadata(world, i1, 2, k1, this.fenceBlock, this.fenceMeta);
                     }
                  } else {
                     if (i2 >= 4) {
                        this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150346_d, 1);
                        if (i2 == 6) {
                           if (k2 == 3) {
                              this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150383_bp, 3);
                           } else {
                              this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150407_cf, 0);
                           }
                        } else {
                           this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
                        }
                     }

                     if (i2 == 3) {
                        if (k2 == 1) {
                           this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
                        } else {
                           this.setBlockAndMetadata(world, i1, 1, k1, this.fenceGateBlock, i1 > 0 ? 3 : 1);
                        }

                        if (k2 == 2) {
                           this.setBlockAndMetadata(world, i1, 4, k1, this.brickSlabBlock, this.brickSlabMeta);
                        } else {
                           this.setBlockAndMetadata(world, i1, 3, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
                        }
                     }
                  }

                  if (k2 == 2 && i2 == 5) {
                     LOTREntityHorse horse = new LOTREntityHorse(world);
                     this.spawnNPCAndSetHome(horse, world, i1, 1, k1, 0);
                     horse.func_110214_p(0);
                     horse.saddleMountForWorldGen();
                     horse.func_110177_bN();
                  }
               }

               if (k1 == 0 && i2 >= 1 && i2 <= 2) {
                  for(j1 = 1; j1 <= 3; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.gateBlock, 2);
                  }

                  this.setBlockAndMetadata(world, i1, 4, k1, this.plank2SlabBlock, this.plank2SlabMeta);
               }

               if (k1 == 12 && i2 >= 1 && i2 <= 2) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.fenceGateBlock, 0);
                  this.setBlockAndMetadata(world, i1, 3, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
                  this.setBlockAndMetadata(world, i1, 4, k1, this.plank2SlabBlock, this.plank2SlabMeta);
               }
            } else if (i2 != 7 && k1 != 26) {
               if (random.nextInt(3) == 0) {
                  this.plantTallGrass(world, random, i1, 1, k1);
               }

               int hayX = 0;
               int hayZ = 20;
               hayDist = 1 + random.nextInt(3);
               int dx = i1 - hayX;
               int dz = k1 - hayZ;
               int distSq = dx * dx + dz * dz;
               int horses;
               int l;
               if (distSq < hayDist * hayDist && random.nextInt(3) != 0) {
                  horses = 1 + random.nextInt(3);

                  for(l = 1; l <= horses; ++l) {
                     this.setBlockAndMetadata(world, i1, l, k1, Blocks.field_150407_cf, 0);
                  }

                  this.setGrassToDirt(world, i1, 0, k1);
               }

               if (i2 == 4 && k1 == 23) {
                  horses = 2 + random.nextInt(3);

                  for(l = 0; l < horses; ++l) {
                     LOTREntityHorse horse = new LOTREntityHorse(world);
                     this.spawnNPCAndSetHome(horse, world, i1, 1, k1, 0);
                     horse.func_110214_p(0);
                     horse.func_70873_a(0);
                     horse.func_110177_bN();
                  }
               }
            } else {
               boolean beam = false;
               if (k1 == 19 && i2 == 7) {
                  beam = true;
               }

               if (k1 == 26 && i2 % 7 == 0) {
                  beam = true;
               }

               if (!beam) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
               } else {
                  for(j1 = 1; j1 <= 2; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                  }

                  this.setGrassToDirt(world, i1, 0, k1);
                  this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
                  this.setBlockAndMetadata(world, i1, 4, k1, Blocks.field_150478_aa, 5);
               }
            }
         }
      }

      for(i1 = 0; i1 <= 12; ++i1) {
         for(k1 = 0; k1 <= 7; ++k1) {
            i2 = 8 - k1;
            k2 = 4 + k1 / 2;
            Block block = this.roofSlabBlock;
            j1 = this.roofSlabMeta;
            if (i1 == 6) {
               block = this.plank2SlabBlock;
               j1 = this.plank2SlabMeta;
            }

            if (k1 % 2 == 1) {
               j1 |= 8;
            }

            this.setBlockAndMetadata(world, -i2, k2, i1, block, j1);
            this.setBlockAndMetadata(world, i2, k2, i1, block, j1);
            if (k1 >= 2) {
               block = this.plankSlabBlock;
               j1 = this.plankSlabMeta;
               if (k1 % 2 == 1) {
                  j1 |= 8;
               }

               this.setBlockAndMetadata(world, -i2, k2 - 1, i1, block, j1);
               this.setBlockAndMetadata(world, i2, k2 - 1, i1, block, j1);
            }
         }
      }

      int[] var20 = new int[]{-1, 13};
      k1 = var20.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var20[i2];

         for(j1 = 0; j1 <= 7; ++j1) {
            j1 = 8 - j1;
            hayDist = 4 + j1 / 2;
            if (j1 % 2 == 0) {
               this.setBlockAndMetadata(world, -j1, hayDist, k2, this.plank2SlabBlock, this.plank2SlabMeta);
               this.setBlockAndMetadata(world, -j1, hayDist - 1, k2, this.plank2SlabBlock, this.plank2SlabMeta | 8);
               this.setBlockAndMetadata(world, j1, hayDist, k2, this.plank2SlabBlock, this.plank2SlabMeta);
               this.setBlockAndMetadata(world, j1, hayDist - 1, k2, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            } else {
               this.setBlockAndMetadata(world, -j1, hayDist, k2, this.plank2Block, this.plank2Meta);
               this.setBlockAndMetadata(world, j1, hayDist, k2, this.plank2Block, this.plank2Meta);
            }
         }
      }

      for(i1 = -2; i1 <= 14; ++i1) {
         this.setBlockAndMetadata(world, 0, 7, i1, this.logBlock, this.logMeta | 8);
         this.setBlockAndMetadata(world, 0, 8, i1, this.plank2SlabBlock, this.plank2SlabMeta);
      }

      var20 = new int[]{-1, 6, 13};
      k1 = var20.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var20[i2];
         this.setBlockAndMetadata(world, -1, 8, k2, this.plank2StairBlock, 5);
         this.setBlockAndMetadata(world, 1, 8, k2, this.plank2StairBlock, 4);
      }

      var20 = new int[]{0, 12};
      k1 = var20.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var20[i2];
         this.setBlockAndMetadata(world, -5, 4, k2, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -4, 4, k2, this.plankSlabBlock, this.plankSlabMeta | 8);
         this.setBlockAndMetadata(world, 4, 4, k2, this.plankSlabBlock, this.plankSlabMeta | 8);
         this.setBlockAndMetadata(world, 5, 4, k2, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -2, 5, k2, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, -1, 5, k2, this.plankStairBlock, 5);
         this.setBlockAndMetadata(world, -2, 6, k2, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, -1, 6, k2, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 1, 5, k2, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, 2, 5, k2, this.plankStairBlock, 5);
         this.setBlockAndMetadata(world, 1, 6, k2, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, 2, 6, k2, this.plankBlock, this.plankMeta);
      }

      for(i1 = -7; i1 <= 7; ++i1) {
         for(k1 = 0; k1 <= 12; ++k1) {
            i2 = Math.abs(i1);
            k2 = IntMath.mod(k1, 4);
            if ((i2 == 0 || i2 == 3 || i2 == 7) && k2 == 0) {
               if (i2 == 0 && (k1 == 4 || k1 == 8)) {
                  for(j1 = 1; j1 <= 2; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.rockWallBlock, this.rockWallMeta);
                  }
               } else {
                  for(j1 = 1; j1 <= 2; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                  }
               }

               this.setBlockAndMetadata(world, i1, 3, k1, this.brickCarvedBlock, this.brickCarvedMeta);
               if (i2 == 3) {
                  for(j1 = 4; j1 <= 5; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                  }
               }

               if (i2 == 0) {
                  for(j1 = 4; j1 <= 6; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
                  }
               }
            }

            if (k1 >= 1 && k1 <= 11 && (i2 == 0 && k2 != 0 || i2 >= 1 && i2 <= 2 && k2 == 0)) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         if (IntMath.mod(i1, 3) == 0) {
            this.setBlockAndMetadata(world, i1, 3, -1, Blocks.field_150478_aa, 4);
            this.setBlockAndMetadata(world, i1, 3, 13, Blocks.field_150478_aa, 3);
         }
      }

      var20 = new int[]{4, 8};
      k1 = var20.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var20[i2];
         this.setBlockAndMetadata(world, -1, 3, k2, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, 1, 3, k2, Blocks.field_150478_aa, 2);
      }

      LOTREntityRohanStablemaster stablemaster = new LOTREntityRohanStablemaster(world);
      this.spawnNPCAndSetHome(stablemaster, world, 0, 1, 6, 8);
      k1 = 1 + random.nextInt(3);

      for(i2 = 0; i2 < k1; ++i2) {
         LOTREntityRohanMan stabler = random.nextBoolean() ? new LOTREntityRohirrimWarrior(world) : new LOTREntityRohanMan(world);
         this.spawnNPCAndSetHome((EntityCreature)stabler, world, 0, 1, 6, 16);
      }

      return true;
   }
}
