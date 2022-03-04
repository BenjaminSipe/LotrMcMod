package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityRohanFarmer;
import lotr.common.entity.npc.LOTREntityRohanMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanBarn extends LOTRWorldGenRohanStructure {
   public LOTRWorldGenRohanBarn(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      int k1;
      int i2;
      int i1;
      int k1;
      int i1;
      if (this.restrictions) {
         k1 = 0;
         i2 = 0;

         for(i1 = -7; i1 <= 7; ++i1) {
            for(k1 = -1; k1 <= 16; ++k1) {
               i1 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, i1, k1)) {
                  return false;
               }

               if (i1 < k1) {
                  k1 = i1;
               }

               if (i1 > i2) {
                  i2 = i1;
               }

               if (i2 - k1 > 6) {
                  return false;
               }
            }
         }
      }

      int i2;
      for(k1 = -5; k1 <= 5; ++k1) {
         for(i2 = 0; i2 <= 15; ++i2) {
            i1 = Math.abs(k1);
            k1 = IntMath.mod(i2, 3);

            for(i1 = 0; (i1 >= 0 || !this.isOpaque(world, k1, i1, i2)) && this.getY(i1) >= 0; --i1) {
               this.setBlockAndMetadata(world, k1, i1, i2, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, k1, i1 - 1, i2);
            }

            for(i1 = 1; i1 <= 11; ++i1) {
               this.setAir(world, k1, i1, i2);
            }

            boolean beam = false;
            if (i1 == 5 && k1 == 0) {
               beam = true;
            }

            if ((i2 == 0 || i2 == 15) && i1 == 2) {
               beam = true;
            }

            if (beam) {
               for(i2 = 1; i2 <= 5; ++i2) {
                  this.setBlockAndMetadata(world, k1, i2, i2, this.woodBeamBlock, this.woodBeamMeta);
               }

               if (i2 == 0 || i2 == 15) {
                  for(i2 = 6; i2 <= 7; ++i2) {
                     this.setBlockAndMetadata(world, k1, i2, i2, this.woodBeamBlock, this.woodBeamMeta);
                  }
               }
            } else if (i1 == 5 || i2 == 0 || i2 == 15) {
               this.setBlockAndMetadata(world, k1, 1, i2, this.plank2Block, this.plank2Meta);

               for(i2 = 2; i2 <= 5; ++i2) {
                  this.setBlockAndMetadata(world, k1, i2, i2, this.plankBlock, this.plankMeta);
               }

               if (i2 == 0 || i2 == 15) {
                  for(i2 = 6; i2 <= 7; ++i2) {
                     this.setBlockAndMetadata(world, k1, i2, i2, this.plankBlock, this.plankMeta);
                  }
               }

               this.setBlockAndMetadata(world, k1, 5, i2, this.woodBeamBlock, this.woodBeamMeta | 4);
               this.setBlockAndMetadata(world, k1, 8, i2, this.woodBeamBlock, this.woodBeamMeta | 4);
            }

            if (i1 <= 4 && i2 >= 1 && i2 <= 14) {
               if (i2 >= 3 && i2 <= 12) {
                  this.setBlockAndMetadata(world, k1, 0, i2, Blocks.field_150346_d, 1);
               }

               if (random.nextBoolean()) {
                  this.setBlockAndMetadata(world, k1, 1, i2, LOTRMod.thatchFloor, 0);
               }

               if (i1 >= 2 || i2 <= 3) {
                  this.setBlockAndMetadata(world, k1, 5, i2, this.plankBlock, this.plankMeta);
                  if (random.nextBoolean()) {
                     this.setBlockAndMetadata(world, k1, 6, i2, LOTRMod.thatchFloor, 0);
                  }
               }
            }
         }
      }

      int[] var17;
      for(k1 = -5; k1 <= 5; ++k1) {
         i2 = Math.abs(k1);
         if (i2 != 2 && i2 != 5) {
            var17 = new int[]{0, 15};
            k1 = var17.length;

            for(i1 = 0; i1 < k1; ++i1) {
               i2 = var17[i1];
               this.setBlockAndMetadata(world, k1, 3, i2, this.plank2SlabBlock, this.plank2SlabMeta);
               if (k1 != -4 && k1 != 3) {
                  if (k1 == -3 || k1 == 4) {
                     this.setBlockAndMetadata(world, k1, 4, i2, this.plankStairBlock, 5);
                  }
               } else {
                  this.setBlockAndMetadata(world, k1, 4, i2, this.plankStairBlock, 4);
               }

               if (k1 == -1) {
                  this.setBlockAndMetadata(world, k1, 4, i2, this.plankStairBlock, 4);
               } else if (k1 == 1) {
                  this.setBlockAndMetadata(world, k1, 4, i2, this.plankStairBlock, 5);
               } else if (k1 == 0) {
                  this.setBlockAndMetadata(world, k1, 4, i2, this.plankSlabBlock, this.plankSlabMeta | 8);
               }

               this.setBlockAndMetadata(world, k1, 7, i2, this.fenceBlock, this.fenceMeta);
            }

            var17 = new int[]{-1, 16};
            k1 = var17.length;

            for(i1 = 0; i1 < k1; ++i1) {
               i2 = var17[i1];
               if (i2 >= 3 || i2 != -1) {
                  this.setBlockAndMetadata(world, k1, 1, i2, this.plank2SlabBlock, this.plank2SlabMeta | 8);
               }

               this.setBlockAndMetadata(world, k1, 5, i2, this.plank2SlabBlock, this.plank2SlabMeta | 8);
               this.setBlockAndMetadata(world, k1, 8, i2, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            }
         } else {
            for(i1 = -1; i1 <= 16; ++i1) {
               this.setBlockAndMetadata(world, k1, 5, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
               this.setBlockAndMetadata(world, k1, 8, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
               if (i1 == -1 || i1 == 16) {
                  this.setBlockAndMetadata(world, k1, 1, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
                  this.setGrassToDirt(world, k1, 0, i1);

                  for(k1 = 2; k1 <= 4; ++k1) {
                     this.setBlockAndMetadata(world, k1, k1, i1, this.fenceBlock, this.fenceMeta);
                  }

                  for(k1 = 6; k1 <= 7; ++k1) {
                     this.setBlockAndMetadata(world, k1, k1, i1, this.fenceBlock, this.fenceMeta);
                  }
               }
            }
         }
      }

      for(k1 = 0; k1 <= 15; ++k1) {
         i2 = IntMath.mod(k1, 3);
         if (i2 == 0) {
            for(i1 = -7; i1 <= 7; ++i1) {
               k1 = Math.abs(i1);
               if (k1 == 6) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
                  this.setGrassToDirt(world, i1, 0, k1);

                  for(i1 = 2; i1 <= 4; ++i1) {
                     this.setBlockAndMetadata(world, i1, i1, k1, this.fenceBlock, this.fenceMeta);
                  }
               }

               if (k1 >= 6) {
                  this.setBlockAndMetadata(world, i1, 5, k1, this.woodBeamBlock, this.woodBeamMeta | 4);
               }
            }
         } else {
            var17 = new int[]{-6, 6};
            k1 = var17.length;

            for(i1 = 0; i1 < k1; ++i1) {
               i2 = var17[i1];
               this.setBlockAndMetadata(world, i2, 1, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            }

            this.setBlockAndMetadata(world, -7, 5, k1, this.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -6, 5, k1, this.plank2StairBlock, 4);
            this.setBlockAndMetadata(world, 6, 5, k1, this.plank2StairBlock, 5);
            this.setBlockAndMetadata(world, 7, 5, k1, this.plank2StairBlock, 0);
            if (k1 >= 3) {
               var17 = new int[]{-5, 5};
               k1 = var17.length;

               for(i1 = 0; i1 < k1; ++i1) {
                  i2 = var17[i1];
                  this.setBlockAndMetadata(world, i2, 3, k1, this.plank2SlabBlock, this.plank2SlabMeta);
                  if (i2 == 1) {
                     this.setBlockAndMetadata(world, i2, 4, k1, this.plankStairBlock, 7);
                  } else if (i2 == 2) {
                     this.setBlockAndMetadata(world, i2, 4, k1, this.plankStairBlock, 6);
                  }
               }
            }
         }
      }

      int[] var16 = new int[]{-1, 16};
      i2 = var16.length;

      for(i1 = 0; i1 < i2; ++i1) {
         k1 = var16[i1];
         this.setBlockAndMetadata(world, -7, 5, k1, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, -6, 5, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 6, 5, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 7, 5, k1, this.plank2StairBlock, 0);
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         for(i2 = 1; i2 <= 4; ++i2) {
            this.setBlockAndMetadata(world, k1, i2, 0, this.gateBlock, 2);
         }
      }

      this.setBlockAndMetadata(world, 0, 3, 0, LOTRMod.gateIronBars, 2);

      for(k1 = 1; k1 <= 14; ++k1) {
         if (IntMath.mod(k1, 3) == 0) {
            this.setBlockAndMetadata(world, -6, 6, k1, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, -6, 7, k1, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, -6, 8, k1, this.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -5, 9, k1, this.plank2StairBlock, 1);
            this.setBlockAndMetadata(world, -4, 9, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, -3, 10, k1, this.plank2SlabBlock, this.plank2SlabMeta);

            for(i2 = -2; i2 <= 2; ++i2) {
               this.setBlockAndMetadata(world, i2, 10, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            }

            this.setBlockAndMetadata(world, 3, 10, k1, this.plank2SlabBlock, this.plank2SlabMeta);
            this.setBlockAndMetadata(world, 4, 9, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            this.setBlockAndMetadata(world, 5, 9, k1, this.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, 6, 8, k1, this.plank2StairBlock, 0);
            this.setBlockAndMetadata(world, 6, 6, k1, this.plank2Block, this.plank2Meta);
            this.setBlockAndMetadata(world, 6, 7, k1, this.plank2Block, this.plank2Meta);
         } else {
            this.setBlockAndMetadata(world, -6, 6, k1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -6, 7, k1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, -6, 8, k1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -5, 9, k1, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -4, 9, k1, this.roofSlabBlock, this.roofSlabMeta | 8);
            this.setBlockAndMetadata(world, -3, 10, k1, this.roofSlabBlock, this.roofSlabMeta);

            for(i2 = -2; i2 <= 2; ++i2) {
               this.setBlockAndMetadata(world, i2, 10, k1, this.roofSlabBlock, this.roofSlabMeta | 8);
            }

            this.setBlockAndMetadata(world, 3, 10, k1, this.roofSlabBlock, this.roofSlabMeta);
            this.setBlockAndMetadata(world, 4, 9, k1, this.roofSlabBlock, this.roofSlabMeta | 8);
            this.setBlockAndMetadata(world, 5, 9, k1, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 6, 8, k1, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 6, 6, k1, this.roofBlock, this.roofMeta);
            this.setBlockAndMetadata(world, 6, 7, k1, this.roofBlock, this.roofMeta);
         }
      }

      var16 = new int[]{0, 15};
      i2 = var16.length;

      for(i1 = 0; i1 < i2; ++i1) {
         k1 = var16[i1];
         this.setBlockAndMetadata(world, -6, 6, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -6, 7, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -6, 8, k1, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, -5, 9, k1, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, -4, 9, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -3, 9, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -3, 10, k1, this.plank2SlabBlock, this.plank2SlabMeta);
         this.setBlockAndMetadata(world, -2, 9, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, -2, 10, k1, this.plank2Block, this.plank2Meta);

         for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 10, k1, this.plank2Block, this.plank2Meta);
         }

         this.setBlockAndMetadata(world, 2, 9, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, 2, 10, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 3, 9, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 3, 10, k1, this.plank2SlabBlock, this.plank2SlabMeta);
         this.setBlockAndMetadata(world, 4, 9, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 5, 9, k1, this.plank2StairBlock, 0);
         this.setBlockAndMetadata(world, 6, 8, k1, this.plank2StairBlock, 0);
         this.setBlockAndMetadata(world, 6, 6, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 6, 7, k1, this.plank2Block, this.plank2Meta);
      }

      var16 = new int[]{-1, 16};
      i2 = var16.length;

      for(i1 = 0; i1 < i2; ++i1) {
         k1 = var16[i1];
         this.setBlockAndMetadata(world, -6, 8, k1, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, -5, 9, k1, this.plank2StairBlock, 1);
         this.setBlockAndMetadata(world, -4, 9, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, -3, 9, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, -3, 10, k1, this.plank2SlabBlock, this.plank2SlabMeta);
         this.setBlockAndMetadata(world, -2, 10, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, -1, 10, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, -1, 11, k1, this.plank2StairBlock, 5);
         this.setBlockAndMetadata(world, 0, 11, k1, this.plank2SlabBlock, this.plank2SlabMeta);
         this.setBlockAndMetadata(world, 1, 10, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, 1, 11, k1, this.plank2StairBlock, 4);
         this.setBlockAndMetadata(world, 2, 10, k1, this.plank2Block, this.plank2Meta);
         this.setBlockAndMetadata(world, 3, 9, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, 3, 10, k1, this.plank2SlabBlock, this.plank2SlabMeta);
         this.setBlockAndMetadata(world, 4, 9, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         this.setBlockAndMetadata(world, 5, 9, k1, this.plank2StairBlock, 0);
         this.setBlockAndMetadata(world, 6, 8, k1, this.plank2StairBlock, 0);
      }

      for(k1 = 0; k1 <= 15; ++k1) {
         this.setBlockAndMetadata(world, 0, 11, k1, this.plank2SlabBlock, this.plank2SlabMeta);
      }

      this.setBlockAndMetadata(world, -4, 1, 1, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -3, 1, 1, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 3, 1, 1, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 4, 1, 1, Blocks.field_150407_cf, 0);

      for(k1 = 1; k1 <= 7; ++k1) {
         if (k1 >= 6) {
            this.setBlockAndMetadata(world, -5, k1, 2, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, 5, k1, 2, this.plankBlock, this.plankMeta);
         }

         this.setBlockAndMetadata(world, -4, k1, 2, Blocks.field_150468_ap, 4);
         this.setBlockAndMetadata(world, 4, k1, 2, Blocks.field_150468_ap, 5);
      }

      for(k1 = 3; k1 <= 12; ++k1) {
         i2 = IntMath.mod(k1, 3);

         for(i1 = -4; i1 <= 4; ++i1) {
            k1 = Math.abs(i1);
            if (i2 == 0) {
               if (k1 >= 2) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
                  this.setBlockAndMetadata(world, i1, 2, k1, this.fenceBlock, this.fenceMeta);
               }

               if (k1 == 2) {
                  this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
                  this.setBlockAndMetadata(world, i1, 4, k1, this.fenceBlock, this.fenceMeta);
               }
            }

            if (i2 == 1) {
               if (k1 == 2) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
               }

               if (k1 == 4) {
                  this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150407_cf, 0);
                  this.setBlockAndMetadata(world, i1, 2, k1, this.fenceBlock, this.fenceMeta);
               }
            }

            if (i2 == 2) {
               if (k1 == 2) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.fenceGateBlock, i1 > 0 ? 3 : 1);
               }

               if (k1 == 4) {
                  this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150383_bp, 3);
                  this.setBlockAndMetadata(world, i1, 2, k1, this.fenceBlock, this.fenceMeta);
               }

               if (k1 == 3) {
                  EntityAnimal animal = getRandomAnimal(world, random);
                  this.spawnNPCAndSetHome(animal, world, i1, 1, k1, 0);
                  animal.func_110177_bN();
               }
            }

            if (k1 == 4) {
               this.setBlockAndMetadata(world, i1, 3, k1, this.plank2SlabBlock, this.plank2SlabMeta);
            }
         }
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         i2 = 1 + random.nextInt(2);

         for(i1 = 1; i1 <= i2; ++i1) {
            this.setBlockAndMetadata(world, k1, i1, 14, Blocks.field_150407_cf, 0);
         }
      }

      this.placeChest(world, random, -4, 1, 13, 4, LOTRChestContents.ROHAN_HOUSE);
      this.placeChest(world, random, -4, 1, 14, 4, LOTRChestContents.ROHAN_HOUSE);
      this.setBlockAndMetadata(world, 4, 1, 13, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 4, 1, 14, this.tableBlock, 0);
      this.setBlockAndMetadata(world, -2, 3, 1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 3, 1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 3, 14, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 3, 14, Blocks.field_150478_aa, 4);

      for(k1 = 3; k1 <= 14; ++k1) {
         this.setBlockAndMetadata(world, -2, 6, k1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 2, 6, k1, this.fenceBlock, this.fenceMeta);
      }

      for(k1 = -1; k1 <= 1; ++k1) {
         this.setBlockAndMetadata(world, k1, 6, 3, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -2, 6, 1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 6, 1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -2, 7, 1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 7, 1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 7, 14, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 7, 14, Blocks.field_150478_aa, 4);
      var16 = new int[]{1, 14};
      i2 = var16.length;

      for(i1 = 0; i1 < i2; ++i1) {
         k1 = var16[i1];

         for(i1 = -4; i1 <= 4; ++i1) {
            i2 = Math.abs(i1);
            if (i2 <= 1 || i2 >= 3) {
               this.setBlockAndMetadata(world, i1, 8, k1, this.plank2SlabBlock, this.plank2SlabMeta | 8);
            }
         }
      }

      for(k1 = 1; k1 <= 14; ++k1) {
         int[] var19;
         if (k1 != 1 && IntMath.mod(k1, 3) != 0) {
            if (k1 != 2) {
               var19 = new int[]{-5, 5};
               i1 = var19.length;

               for(k1 = 0; k1 < i1; ++k1) {
                  i1 = var19[k1];
                  int j1 = 6;
                  if (random.nextBoolean()) {
                     int j2 = j1;
                     if (random.nextBoolean()) {
                        j2 = j1 + 1;
                     }

                     int i2;
                     for(i2 = j1; i2 <= j2; ++i2) {
                        this.setBlockAndMetadata(world, i1, i2, k1, Blocks.field_150407_cf, 0);
                     }

                     if (j2 >= j1 + 1 && random.nextBoolean()) {
                        i2 = (Math.abs(i1) - 1) * Integer.signum(i1);
                        j2 = j1;
                        if (random.nextBoolean()) {
                           j2 = j1 + 1;
                        }

                        for(int j3 = j1; j3 <= j2; ++j3) {
                           this.setBlockAndMetadata(world, i2, j3, k1, Blocks.field_150407_cf, 0);
                        }
                     }
                  }
               }
            }
         } else {
            var19 = new int[]{-5, 5};
            i1 = var19.length;

            for(k1 = 0; k1 < i1; ++k1) {
               i1 = var19[k1];
               this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, i1, 7, k1, this.fenceBlock, this.fenceMeta);
            }
         }
      }

      for(k1 = -4; k1 <= 4; ++k1) {
         i2 = Math.abs(k1);
         if (i2 != 2 && random.nextBoolean()) {
            this.setBlockAndMetadata(world, k1, 6, 1, Blocks.field_150407_cf, 0);
         }
      }

      LOTREntityRohanMan farmer = new LOTREntityRohanFarmer(world);
      this.spawnNPCAndSetHome(farmer, world, 0, 1, 8, 16);
      return true;
   }

   public static EntityAnimal getRandomAnimal(World world, Random random) {
      int animal = random.nextInt(4);
      if (animal == 0) {
         return new EntityCow(world);
      } else if (animal == 1) {
         return new EntityPig(world);
      } else if (animal == 2) {
         return new EntitySheep(world);
      } else {
         return animal == 3 ? new EntityChicken(world) : null;
      }
   }
}
