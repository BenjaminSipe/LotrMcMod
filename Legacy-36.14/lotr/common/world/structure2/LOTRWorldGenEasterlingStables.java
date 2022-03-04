package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingStables extends LOTRWorldGenEasterlingStructure {
   public LOTRWorldGenEasterlingStables(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      int k1;
      int k1;
      int i1;
      int j1;
      int randomGround;
      if (this.restrictions) {
         k1 = 0;
         k1 = 0;

         for(i1 = -9; i1 <= 9; ++i1) {
            for(j1 = -1; j1 <= 13; ++j1) {
               randomGround = this.getTopBlock(world, i1, j1) - 1;
               if (!this.isSurface(world, i1, randomGround, j1)) {
                  return false;
               }

               if (randomGround < k1) {
                  k1 = randomGround;
               }

               if (randomGround > k1) {
                  k1 = randomGround;
               }

               if (k1 - k1 > 8) {
                  return false;
               }
            }
         }
      }

      for(k1 = -8; k1 <= 8; ++k1) {
         for(k1 = 0; k1 <= 12; ++k1) {
            i1 = Math.abs(k1);
            j1 = IntMath.mod(k1, 4);

            for(randomGround = 1; randomGround <= 6; ++randomGround) {
               this.setAir(world, k1, randomGround, k1);
            }

            if (i1 == 0 && (k1 == 0 || k1 == 12)) {
               for(randomGround = 5; (randomGround >= 0 || !this.isOpaque(world, k1, randomGround, k1)) && this.getY(randomGround) >= 0; --randomGround) {
                  this.setBlockAndMetadata(world, k1, randomGround, k1, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, k1, randomGround - 1, k1);
               }
            } else if (i1 == 4 && j1 == 0) {
               for(randomGround = 4; (randomGround >= 0 || !this.isOpaque(world, k1, randomGround, k1)) && this.getY(randomGround) >= 0; --randomGround) {
                  this.setBlockAndMetadata(world, k1, randomGround, k1, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, k1, randomGround - 1, k1);
               }
            } else if (i1 == 8 && j1 == 0) {
               for(randomGround = 3; (randomGround >= 0 || !this.isOpaque(world, k1, randomGround, k1)) && this.getY(randomGround) >= 0; --randomGround) {
                  this.setBlockAndMetadata(world, k1, randomGround, k1, this.woodBeamBlock, this.woodBeamMeta);
                  this.setGrassToDirt(world, k1, randomGround - 1, k1);
               }
            } else {
               for(randomGround = 0; (randomGround >= 0 || !this.isOpaque(world, k1, randomGround, k1)) && this.getY(randomGround) >= 0; --randomGround) {
                  this.setBlockAndMetadata(world, k1, randomGround, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, k1, randomGround - 1, k1);
               }

               if (random.nextBoolean()) {
                  this.setBlockAndMetadata(world, k1, 1, k1, LOTRMod.thatchFloor, 0);
               }
            }
         }
      }

      for(k1 = 1; k1 <= 11; ++k1) {
         this.setBlockAndMetadata(world, 0, 5, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
      }

      for(k1 = -3; k1 <= 3; ++k1) {
         this.setBlockAndMetadata(world, k1, 0, 0, this.woodBeamBlock, this.woodBeamMeta | 4);
      }

      for(k1 = -8; k1 <= 8; ++k1) {
         for(k1 = 0; k1 <= 12; ++k1) {
            i1 = Math.abs(k1);
            j1 = IntMath.mod(k1, 4);
            if (i1 >= 5 && i1 <= 7) {
               if (k1 == 0) {
                  this.setBlockAndMetadata(world, k1, 1, k1, this.plankStairBlock, 3);
                  this.setBlockAndMetadata(world, k1, 2, k1, this.plankStairBlock, 2);
               } else if (k1 == 12) {
                  this.setBlockAndMetadata(world, k1, 1, k1, this.plankStairBlock, 2);
                  this.setBlockAndMetadata(world, k1, 2, k1, this.plankStairBlock, 3);
               } else if (j1 == 0) {
                  this.setBlockAndMetadata(world, k1, 1, k1, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, k1, 2, k1, this.plankBlock, this.plankMeta);
               } else {
                  randomGround = random.nextInt(2);
                  if (randomGround == 0) {
                     this.setBlockAndMetadata(world, k1, 0, k1, Blocks.field_150346_d, 1);
                  } else if (randomGround == 1) {
                     this.setBlockAndMetadata(world, k1, 0, k1, LOTRMod.dirtPath, 1);
                  }
               }
            }

            if (i1 >= 1 && i1 <= 3 && k1 == 12) {
               this.setBlockAndMetadata(world, k1, 1, k1, this.plankStairBlock, 2);
               this.setBlockAndMetadata(world, k1, 2, k1, this.plankStairBlock, 3);
               this.setBlockAndMetadata(world, k1, 3, k1, this.fenceBlock, this.fenceMeta);
            }

            if (i1 == 4 && j1 != 0) {
               this.setBlockAndMetadata(world, k1, 1, k1, this.fenceGateBlock, k1 > 0 ? 1 : 3);
            }

            if (i1 == 8 && j1 != 0) {
               this.setBlockAndMetadata(world, k1, 1, k1, this.plankStairBlock, k1 > 0 ? 1 : 0);
               this.setBlockAndMetadata(world, k1, 2, k1, this.plankStairBlock, k1 > 0 ? 0 : 1);
            }

            if (i1 == 6 && j1 == 2) {
               LOTREntityHorse horse = new LOTREntityHorse(world);
               this.spawnNPCAndSetHome(horse, world, k1, 1, k1, 0);
               horse.func_110214_p(0);
               horse.saddleMountForWorldGen();
               horse.func_110177_bN();
            }

            if (i1 == 4) {
               if (j1 == 1) {
                  this.setBlockAndMetadata(world, k1, 3, k1, Blocks.field_150478_aa, 3);
               } else if (j1 == 3) {
                  this.setBlockAndMetadata(world, k1, 3, k1, Blocks.field_150478_aa, 4);
               }
            }

            if (i1 == 0 && j1 == 2) {
               this.setBlockAndMetadata(world, k1, 4, k1, LOTRMod.chandelier, 0);
            }
         }
      }

      for(k1 = 0; k1 <= 12; ++k1) {
         k1 = IntMath.mod(k1, 4);
         this.setBlockAndMetadata(world, -8, 4, k1, this.roofStairBlock, 1);

         for(i1 = -7; i1 <= -5; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.roofBlock, this.roofMeta);
         }

         if (k1 != 0) {
            this.setBlockAndMetadata(world, -4, 4, k1, this.roofStairBlock, 4);
         }

         this.setBlockAndMetadata(world, -5, 5, k1, this.roofStairBlock, 1);

         for(i1 = -4; i1 <= -2; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, k1, this.roofBlock, this.roofMeta);
         }

         this.setBlockAndMetadata(world, -1, 5, k1, this.roofStairBlock, 4);
         this.setBlockAndMetadata(world, -2, 6, k1, this.roofStairBlock, 1);

         for(i1 = -1; i1 <= 1; ++i1) {
            this.setBlockAndMetadata(world, i1, 6, k1, this.roofBlock, this.roofMeta);
         }

         this.setBlockAndMetadata(world, 2, 6, k1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 1, 5, k1, this.roofStairBlock, 5);

         for(i1 = 2; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, k1, this.roofBlock, this.roofMeta);
         }

         this.setBlockAndMetadata(world, 5, 5, k1, this.roofStairBlock, 0);
         if (k1 != 0) {
            this.setBlockAndMetadata(world, 4, 4, k1, this.roofStairBlock, 5);
         }

         for(i1 = 5; i1 <= 7; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.roofBlock, this.roofMeta);
         }

         this.setBlockAndMetadata(world, 8, 4, k1, this.roofStairBlock, 0);
      }

      int[] var12 = new int[]{-1, 13};
      k1 = var12.length;

      for(i1 = 0; i1 < k1; ++i1) {
         j1 = var12[i1];
         this.setBlockAndMetadata(world, -8, 3, j1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, -4, 4, j1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 0, 5, j1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 4, 4, j1, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 8, 3, j1, this.fenceBlock, this.fenceMeta);
      }

      for(k1 = 0; k1 <= 12; ++k1) {
         if (IntMath.mod(k1, 4) == 0) {
            this.setBlockAndMetadata(world, -9, 3, k1, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 9, 3, k1, this.fenceBlock, this.fenceMeta);
         }
      }

      this.setBlockAndMetadata(world, 0, 4, -1, this.plankBlock, this.plankMeta);
      this.spawnItemFrame(world, 0, 4, -1, 2, new ItemStack(Items.field_151141_av));
      this.spawnItemFrame(world, 0, 4, -1, 1, new ItemStack(Items.field_151141_av));
      this.spawnItemFrame(world, 0, 4, -1, 3, new ItemStack(Items.field_151141_av));
      this.placeChest(world, random, -3, 1, 4, 4, this.chestContents);
      this.placeChest(world, random, 3, 1, 4, 5, this.chestContents);
      this.setBlockAndMetadata(world, 0, 1, 4, this.plankStairBlock, 2);
      this.setBlockAndMetadata(world, 0, 1, 5, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, 0, 1, 6, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, 0, 1, 7, this.plankStairBlock, 3);

      for(k1 = -2; k1 <= 2; ++k1) {
         k1 = 3 - Math.abs(k1);

         for(i1 = 1; i1 < 1 + k1; ++i1) {
            this.setBlockAndMetadata(world, k1, i1, 11, Blocks.field_150407_cf, 0);
         }

         i1 = k1 - 1;
         if (i1 >= 1) {
            for(j1 = 1; j1 < 1 + i1; ++j1) {
               this.setBlockAndMetadata(world, k1, j1, 10, Blocks.field_150407_cf, 0);
            }
         }
      }

      k1 = 1 + random.nextInt(2);

      for(k1 = 0; k1 < k1; ++k1) {
         LOTREntityEasterling easterling = new LOTREntityEasterling(world);
         this.spawnNPCAndSetHome(easterling, world, 0, 1, 3, 8);
      }

      return true;
   }
}
