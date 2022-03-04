package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorStables extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorStables(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.bedBlock = Blocks.field_150324_C;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 6);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int k1;
      int i1;
      int k1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(k1 = -5; k1 <= 5; ++k1) {
            for(i1 = -6; i1 <= 6; ++i1) {
               k1 = this.getTopBlock(world, k1, i1) - 1;
               if (!this.isSurface(world, k1, k1, i1)) {
                  return false;
               }

               if (k1 < i1) {
                  i1 = k1;
               }

               if (k1 > k1) {
                  k1 = k1;
               }

               if (k1 - i1 > 5) {
                  return false;
               }
            }
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(k1 = -5; k1 <= 5; ++k1) {
            k1 = Math.abs(i1);
            i1 = Math.abs(k1);

            for(k1 = 0; (k1 == 0 || !this.isOpaque(world, i1, k1, k1)) && this.getY(k1) >= 0; --k1) {
               this.setBlockAndMetadata(world, i1, k1, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, k1 - 1, k1);
            }

            for(k1 = 1; k1 <= 7; ++k1) {
               this.setAir(world, i1, k1, k1);
            }
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, -6, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 4, -5, this.roofSlabBlock, this.roofSlabMeta | 8);
         this.setBlockAndMetadata(world, i1, 5, -4, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 5, -3, this.roofSlabBlock, this.roofSlabMeta | 8);
         this.setBlockAndMetadata(world, i1, 6, -2, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, i1, 6, -1, this.roofSlabBlock, this.roofSlabMeta | 8);
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         k1 = Math.abs(i1);
         if (k1 != 4 && k1 != 0) {
            this.setBlockAndMetadata(world, i1, 2, -5, this.fenceGateBlock, 2);

            for(k1 = -4; k1 <= -1; ++k1) {
               this.setBlockAndMetadata(world, i1, 0, k1, this.rockBlock, this.rockMeta);
               if (random.nextInt(3) != 0) {
                  this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.thatchFloor, 0);
               }
            }
         } else {
            for(k1 = 1; k1 <= 3; ++k1) {
               this.setBlockAndMetadata(world, i1, k1, -5, this.woodBeamBlock, this.woodBeamMeta);
            }

            this.setBlockAndMetadata(world, i1, 3, -6, this.plankStairBlock, 6);

            for(k1 = -4; k1 <= -1; ++k1) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);
               this.setBlockAndMetadata(world, i1, 2, k1, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, i1, 3, k1, this.fenceBlock, this.fenceMeta);
            }

            for(k1 = -5; k1 <= -1; ++k1) {
               this.setBlockAndMetadata(world, i1, 4, k1, this.roofBlock, this.roofMeta);
               if (k1 >= -3) {
                  this.setBlockAndMetadata(world, i1, 5, k1, this.roofBlock, this.roofMeta);
               }

               if (k1 >= -1) {
                  this.setBlockAndMetadata(world, i1, 6, k1, this.roofBlock, this.roofMeta);
               }
            }
         }
      }

      int[] var13 = new int[]{-4, 4};
      k1 = var13.length;

      for(k1 = 0; k1 < k1; ++k1) {
         i1 = var13[k1];

         for(k1 = 1; k1 <= 6; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, 0, this.pillarBlock, this.pillarMeta);
         }

         for(k1 = 1; k1 <= 5; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, 5, this.pillarBlock, this.pillarMeta);
         }

         int j1;
         for(k1 = 1; k1 <= 4; ++k1) {
            for(j1 = 1; j1 <= 6; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
            }
         }

         for(k1 = 2; k1 <= 3; ++k1) {
            for(j1 = 1; j1 <= 2; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }

         this.setBlockAndMetadata(world, i1, 3, 2, this.brickStairBlock, 7);
         this.setBlockAndMetadata(world, i1, 3, 3, this.brickStairBlock, 6);

         for(k1 = 1; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.woodBeamBlock, this.woodBeamMeta | 8);
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = 1; k1 <= 6; ++k1) {
            if (k1 >= 2 && k1 <= 4) {
               this.setBlockAndMetadata(world, i1, k1, 0, this.plankBlock, this.plankMeta);
            } else {
               this.setBlockAndMetadata(world, i1, k1, 0, this.brickBlock, this.brickMeta);
            }
         }

         for(k1 = 1; k1 <= 5; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, 5, this.brickBlock, this.brickMeta);
         }
      }

      var13 = new int[]{-2, 2};
      k1 = var13.length;

      for(k1 = 0; k1 < k1; ++k1) {
         i1 = var13[k1];
         this.setBlockAndMetadata(world, i1, 1, -1, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, i1, 4, -1, Blocks.field_150478_aa, 4);
         LOTREntityHorse horse = new LOTREntityHorse(world);
         this.spawnNPCAndSetHome(horse, world, i1, 1, -3, 0);
         horse.func_110214_p(0);
         horse.saddleMountForWorldGen();
         horse.func_110177_bN();
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = 1; k1 <= 4; ++k1) {
            this.setBlockAndMetadata(world, i1, 4, k1, this.plankBlock, this.plankMeta);
         }
      }

      for(i1 = -5; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, i1, 7, 0, this.brick2StairBlock, 2);

         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 7, k1, this.brick2Block, this.brick2Meta);
         }

         this.setBlockAndMetadata(world, i1, 7, 4, this.brick2StairBlock, 3);
         this.setBlockAndMetadata(world, i1, 6, 5, this.brick2StairBlock, 3);
         this.setBlockAndMetadata(world, i1, 5, 6, this.brick2StairBlock, 3);
         if (Math.abs(i1) == 5) {
            this.setBlockAndMetadata(world, i1, 6, 4, this.brick2StairBlock, 6);
            this.setBlockAndMetadata(world, i1, 5, 5, this.brick2StairBlock, 6);
         }
      }

      var13 = new int[]{-3, 1};
      k1 = var13.length;

      for(k1 = 0; k1 < k1; ++k1) {
         i1 = var13[k1];
         this.setBlockAndMetadata(world, i1, 2, 5, this.brickStairBlock, 0);
         this.setBlockAndMetadata(world, i1, 3, 5, this.brickStairBlock, 4);
         this.setBlockAndMetadata(world, i1 + 1, 2, 5, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, i1 + 1, 3, 5, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, i1 + 2, 2, 5, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, i1 + 2, 3, 5, this.brickStairBlock, 5);
      }

      this.setBlockAndMetadata(world, 0, 2, 5, LOTRMod.brick, 5);
      this.setBlockAndMetadata(world, -3, 1, 4, this.plankBlock, this.plankMeta);
      this.placeFlowerPot(world, -3, 2, 4, this.getRandomFlower(world, random));
      this.setBlockAndMetadata(world, -2, 1, 4, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.placeMug(world, random, -2, 2, 4, 0, LOTRFoods.GONDOR_DRINK);
      this.setBlockAndMetadata(world, -1, 1, 4, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, 0, 1, 4, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.placePlateWithCertainty(world, random, 0, 2, 4, this.plateBlock, LOTRFoods.GONDOR);
      this.setBlockAndMetadata(world, 1, 1, 4, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, 2, 1, 4, this.plankSlabBlock, this.plankSlabMeta | 8);
      this.placeMug(world, random, 2, 2, 4, 0, LOTRFoods.GONDOR_DRINK);
      this.setBlockAndMetadata(world, 3, 1, 4, this.plankBlock, this.plankMeta);
      this.placeFlowerPot(world, 3, 2, 4, this.getRandomFlower(world, random));
      this.setBlockAndMetadata(world, -3, 1, 1, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -2, 1, 1, this.tableBlock, 0);
      this.setBlockAndMetadata(world, 3, 1, 1, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, 3, 2, 1, 2, LOTRFoods.GONDOR_DRINK);
      this.setBlockAndMetadata(world, 0, 3, 3, LOTRMod.chandelier, 1);

      for(i1 = 1; i1 <= 6; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, 0, this.pillarBlock, this.pillarMeta);
      }

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, 1, Blocks.field_150468_ap, 3);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 6, 4, this.brick2StairBlock, 6);
      }

      this.setBlockAndMetadata(world, 3, 5, 1, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 3, 5, 2, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 3, 5, 3, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 3, 6, 1, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 2, 5, 1, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 2, 5, 2, Blocks.field_150407_cf, 0);
      if (random.nextInt(3) == 0) {
         this.placeChest(world, random, 3, 5, 1, 5, LOTRChestContents.GONDOR_HOUSE_TREASURE);
      }

      this.setBlockAndMetadata(world, 3, 6, 2, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -2, 5, 3, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -3, 5, 3, this.bedBlock, 11);
      this.placeChest(world, random, -3, 5, 1, 3, LOTRChestContents.GONDOR_HOUSE);
      this.placeBarrel(world, random, -2, 5, 1, 3, LOTRFoods.GONDOR_DRINK);
      LOTREntityGondorMan gondorian = new LOTREntityGondorMan(world);
      this.spawnNPCAndSetHome(gondorian, world, 0, 1, 2, 8);
      return true;
   }
}
