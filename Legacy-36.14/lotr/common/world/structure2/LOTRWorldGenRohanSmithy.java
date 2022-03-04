package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityRohanBlacksmith;
import lotr.common.entity.npc.LOTREntityRohanMan;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenRohanSmithy extends LOTRWorldGenRohanStructure {
   public LOTRWorldGenRohanSmithy(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int i1;
      int i2;
      int k1;
      int j1;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         i2 = 0;

         for(k1 = -10; k1 <= 5; ++k1) {
            for(j1 = -3; j1 <= 4; ++j1) {
               j1 = this.getTopBlock(world, k1, j1) - 1;
               if (!this.isSurface(world, k1, j1, j1)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > i2) {
                  i2 = j1;
               }

               if (i2 - i1 > 5) {
                  return false;
               }
            }
         }
      }

      for(i1 = -10; i1 <= 5; ++i1) {
         for(i2 = -3; i2 <= 4; ++i2) {
            for(k1 = 2; k1 <= 8; ++k1) {
               this.setAir(world, i1, k1, i2);
            }

            boolean corner = (i1 == -10 || i1 == 5) && (i2 == -3 || i2 == 4);
            if (!corner) {
               for(j1 = 1; (j1 >= 1 || !this.isOpaque(world, i1, j1, i2)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, i2, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, i2);
               }
            } else {
               for(j1 = 1; (j1 >= 1 || !this.isOpaque(world, i1, j1, i2)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, i2, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, i2);
               }

               this.setBlockAndMetadata(world, i1, 2, i2, this.rockSlabBlock, this.rockSlabMeta);
            }
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 1, -3, this.brickStairBlock, 2);
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         i2 = Math.abs(i1);
         if (i2 == 2) {
            for(k1 = 1; k1 <= 2; ++k1) {
               this.setBlockAndMetadata(world, i1, k1, -3, this.logBlock, this.logMeta);
            }

            for(k1 = 3; k1 <= 4; ++k1) {
               this.setBlockAndMetadata(world, i1, k1, -3, this.fenceBlock, this.fenceMeta);
            }
         }

         if (i2 == 3) {
            for(k1 = 2; k1 <= 4; ++k1) {
               this.setBlockAndMetadata(world, i1, k1, -2, this.plankBlock, this.plankMeta);
            }
         }

         if (i2 == 4) {
            int[] var17 = new int[]{-2, 3};
            j1 = var17.length;
            j1 = 0;

            label355:
            while(true) {
               if (j1 >= j1) {
                  k1 = -1;

                  while(true) {
                     if (k1 > 2) {
                        break label355;
                     }

                     this.setBlockAndMetadata(world, i1, 5, k1, this.roofSlabBlock, this.roofSlabMeta);
                     ++k1;
                  }
               }

               int k1 = var17[j1];
               this.setBlockAndMetadata(world, i1, 2, k1, this.logBlock, this.logMeta);

               for(int j1 = 3; j1 <= 4; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
               }

               this.setBlockAndMetadata(world, i1, 5, k1, this.plank2Block, this.plank2Meta);
               ++j1;
            }
         }

         if (i1 == 4) {
            for(k1 = -1; k1 <= 2; ++k1) {
               for(j1 = 2; j1 <= 4; ++j1) {
                  if (k1 >= 0 && k1 <= 1 && j1 >= 3) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.barsBlock, 0);
                  } else {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.plankBlock, this.plankMeta);
                  }
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         i2 = Math.abs(i1);
         this.setBlockAndMetadata(world, i1, 5, 3, this.roofSlabBlock, this.roofSlabMeta);

         for(k1 = -2; k1 <= 2; ++k1) {
            if (i2 == 3 && k1 == -2) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.roofSlabBlock, this.roofSlabMeta);
            } else {
               this.setBlockAndMetadata(world, i1, 5, k1, this.roofBlock, this.roofMeta);
            }
         }

         if (i2 <= 2) {
            for(k1 = -2; k1 <= 2; ++k1) {
               boolean slab = false;
               if (i2 == 0 && k1 == -2) {
                  slab = true;
               }

               if (i2 == 1 && (k1 == -1 || k1 == 2)) {
                  slab = true;
               }

               if (i2 == 2 && k1 >= 0 && k1 <= 1) {
                  slab = true;
               }

               if (slab) {
                  this.setBlockAndMetadata(world, i1, 6, k1, this.roofSlabBlock, this.roofSlabMeta);
               }

               boolean full = false;
               if (i2 == 0 && k1 >= -1 && k1 <= 2) {
                  full = true;
               }

               if (i2 == 1 && k1 >= 0 && k1 <= 1) {
                  full = true;
               }

               if (full) {
                  this.setBlockAndMetadata(world, i1, 6, k1, this.roofBlock, this.roofMeta);
               }

               slab = false;
               if (i2 == 0 && k1 >= 0 && k1 <= 1) {
                  slab = true;
               }

               if (slab) {
                  this.setBlockAndMetadata(world, i1, 7, k1, this.roofSlabBlock, this.roofSlabMeta);
               }
            }
         }
      }

      for(i1 = -3; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, 0, 5, i1, this.logBlock, this.logMeta | 8);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         i2 = Math.abs(i1);
         if (i2 == 0) {
            this.setBlockAndMetadata(world, i1, 6, -3, this.plank2SlabBlock, this.plank2SlabMeta);
         }

         if (i2 == 1) {
            this.setBlockAndMetadata(world, i1, 5, -3, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         }

         if (i2 == 2) {
            this.setBlockAndMetadata(world, i1, 5, -3, this.plank2SlabBlock, this.plank2SlabMeta);
         }

         if (i2 == 3) {
            this.setBlockAndMetadata(world, i1, 4, -3, this.plank2SlabBlock, this.plank2SlabMeta | 8);
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         i2 = Math.abs(i1);
         if (i2 <= 1) {
            for(k1 = 2; k1 <= 6; ++k1) {
               this.setBlockAndMetadata(world, i1, k1, 3, this.brickBlock, this.brickMeta);
            }
         }

         if (i2 == 2) {
            this.setBlockAndMetadata(world, i1, 2, 3, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 4, 3, this.fenceBlock, this.fenceMeta);
         }

         if (i2 == 3) {
            for(k1 = 2; k1 <= 4; ++k1) {
               this.setBlockAndMetadata(world, i1, k1, 3, this.brickBlock, this.brickMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, -1, 7, 3, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 1, 7, 3, this.brickStairBlock, 0);

      for(i1 = 7; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, 3, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, 0, 10, 3, Blocks.field_150457_bL, 0);

      for(i1 = -1; i1 <= 1; ++i1) {
         for(i2 = 2; i2 <= 6; ++i2) {
            this.setBlockAndMetadata(world, i1, i2, 4, this.brickBlock, this.brickMeta);
         }
      }

      this.setBlockAndMetadata(world, 0, 3, 4, this.brickCarvedBlock, this.brickCarvedMeta);
      this.setBlockAndMetadata(world, -1, 6, 4, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 1, 6, 4, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 0, 7, 4, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 0, 1, 3, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 0, 2, 3, Blocks.field_150480_ab, 0);
      this.setAir(world, 0, 3, 3);
      this.setBlockAndMetadata(world, 0, 4, 2, Blocks.field_150478_aa, 4);

      for(i1 = -1; i1 <= 1; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, 2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, i1, 6, 2, this.brickBlock, this.brickMeta);
      }

      this.setBlockAndMetadata(world, 0, 7, 2, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -1, 2, 2, LOTRMod.alloyForge, 2);
      this.setBlockAndMetadata(world, 0, 2, 2, LOTRMod.unsmeltery, 2);
      this.setBlockAndMetadata(world, 1, 2, 2, Blocks.field_150460_al, 2);

      for(i1 = 3; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -1, i1, 2, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, 1, i1, 2, this.brickWallBlock, this.brickWallMeta);
      }

      this.setBlockAndMetadata(world, 2, 2, 2, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 3, 2, 2, this.logBlock, this.logMeta);
      this.setBlockAndMetadata(world, 3, 2, 1, this.tableBlock, 0);

      for(i1 = 3; i1 <= 4; ++i1) {
         ItemStack weapon = random.nextBoolean() ? this.getRandomRohanWeapon(random) : null;
         this.placeWeaponRack(world, 3, i1, 2, 6, weapon);
      }

      this.placeArmorStand(world, 3, 2, -1, 1, this.getRohanArmourItems());
      this.setBlockAndMetadata(world, -7, 2, 0, Blocks.field_150467_bQ, 1);
      this.setBlockAndMetadata(world, -8, 2, 3, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
      this.setBlockAndMetadata(world, -7, 2, 3, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, -6, 2, 3, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
      LOTREntityRohanMan blacksmith = new LOTREntityRohanBlacksmith(world);
      this.spawnNPCAndSetHome(blacksmith, world, 0, 2, 0, 8);
      return true;
   }
}
