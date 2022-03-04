package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorFarmer;
import lotr.common.entity.npc.LOTREntityGondorFarmhand;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorBarn extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorBarn(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      int i1;
      int i1;
      int k1;
      int l;
      int k1;
      if (this.restrictions) {
         i1 = 0;
         i1 = 0;

         for(k1 = -12; k1 <= 5; ++k1) {
            for(l = -2; l <= 15; ++l) {
               k1 = this.getTopBlock(world, k1, l) - 1;
               if (!this.isSurface(world, k1, k1, l)) {
                  return false;
               }

               if (k1 < i1) {
                  i1 = k1;
               }

               if (k1 > i1) {
                  i1 = k1;
               }

               if (i1 - i1 > 5) {
                  return false;
               }
            }
         }
      }

      for(i1 = -12; i1 <= 4; ++i1) {
         for(i1 = -2; i1 <= 15; ++i1) {
            this.setBlockAndMetadata(world, i1, 0, i1, Blocks.field_150349_c, 0);

            for(k1 = -1; !this.isOpaque(world, i1, k1, i1) && this.getY(k1) >= 0; --k1) {
               this.setBlockAndMetadata(world, i1, k1, i1, Blocks.field_150346_d, 0);
               this.setGrassToDirt(world, i1, k1 - 1, i1);
            }

            for(k1 = 1; k1 <= 10; ++k1) {
               this.setAir(world, i1, k1, i1);
            }
         }
      }

      int[] var14 = new int[]{-4, 4};
      i1 = var14.length;

      int i1;
      for(k1 = 0; k1 < i1; ++k1) {
         l = var14[k1];

         for(k1 = 0; k1 <= 13; ++k1) {
            this.setBlockAndMetadata(world, l, 1, k1, this.rockBlock, this.rockMeta);
            if (k1 != 0 && k1 != 4 && k1 != 9 && k1 != 13) {
               for(i1 = 2; i1 <= 5; ++i1) {
                  this.setBlockAndMetadata(world, l, i1, k1, this.plankBlock, this.plankMeta);
               }
            } else {
               for(i1 = 2; i1 <= 5; ++i1) {
                  this.setBlockAndMetadata(world, l, i1, k1, this.woodBeamBlock, this.woodBeamMeta);
               }
            }
         }

         this.setBlockAndMetadata(world, l, 4, 1, this.plankStairBlock, 7);
         this.setBlockAndMetadata(world, l, 4, 2, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, l, 4, 3, this.plankStairBlock, 6);
         this.setBlockAndMetadata(world, l, 4, 10, this.plankStairBlock, 7);
         this.setBlockAndMetadata(world, l, 4, 11, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, l, 4, 12, this.plankStairBlock, 6);
      }

      var14 = new int[]{0, 13};
      i1 = var14.length;

      for(k1 = 0; k1 < i1; ++k1) {
         l = var14[k1];

         for(k1 = -3; k1 <= 3; ++k1) {
            i1 = Math.abs(k1);
            if (i1 <= 1) {
               this.setBlockAndMetadata(world, k1, 1, l, this.fenceGateBlock, 0);
            } else {
               this.setBlockAndMetadata(world, k1, 1, l, this.rockBlock, this.rockMeta);
            }

            int j1;
            if (i1 == 2) {
               for(j1 = 2; j1 <= 7; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, l, this.woodBeamBlock, this.woodBeamMeta);
               }
            }

            if (i1 == 3) {
               for(j1 = 2; j1 <= 5; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, l, this.plankBlock, this.plankMeta);
               }

               for(j1 = 6; j1 <= 8; ++j1) {
                  this.setBlockAndMetadata(world, k1, j1, l, this.wallBlock, this.wallMeta);
               }
            }
         }

         this.setBlockAndMetadata(world, -1, 4, l, this.plankStairBlock, 4);
         this.setBlockAndMetadata(world, 1, 4, l, this.plankStairBlock, 5);

         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, k1, 5, l, this.plankBlock, this.plankMeta);
            this.setBlockAndMetadata(world, k1, 6, l, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, k1, 8, l, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, k1, 9, l, this.wallBlock, this.wallMeta);
         }

         this.setBlockAndMetadata(world, -1, 7, l, this.wallBlock, this.wallMeta);
         this.setBlockAndMetadata(world, 0, 7, l, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 1, 7, l, this.wallBlock, this.wallMeta);
      }

      var14 = new int[]{-1, 14};
      i1 = var14.length;

      for(k1 = 0; k1 < i1; ++k1) {
         l = var14[k1];

         for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, k1, 6, l, this.woodBeamBlock, this.woodBeamMeta | 4);
         }
      }

      this.setBlockAndMetadata(world, 0, 5, -1, this.plankStairBlock, 6);
      this.setBlockAndMetadata(world, 0, 5, 14, this.plankStairBlock, 7);

      for(i1 = -1; i1 <= 14; ++i1) {
         this.setBlockAndMetadata(world, -2, 8, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
         this.setBlockAndMetadata(world, 2, 8, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
         this.setBlockAndMetadata(world, 0, 10, i1, this.woodBeamBlock, this.woodBeamMeta | 8);
         this.setBlockAndMetadata(world, -5, 5, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, -4, 6, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -4, 7, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, -3, 8, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, -2, 9, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, -1, 10, i1, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 0, 11, i1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, 1, 10, i1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 2, 9, i1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 3, 8, i1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 4, 7, i1, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 4, 6, i1, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 5, 5, i1, this.roofStairBlock, 0);
         if (i1 == -1 || i1 == 14) {
            this.setBlockAndMetadata(world, -4, 5, i1, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -3, 7, i1, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -1, 9, i1, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 9, i1, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 7, i1, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 5, i1, this.roofStairBlock, 5);
         }
      }

      for(i1 = 1; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -3, 1, i1, Blocks.field_150407_cf, 0);
      }

      for(i1 = 1; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -3, 2, i1, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, -2, 1, i1, Blocks.field_150407_cf, 0);
      }

      for(i1 = 10; i1 <= 12; ++i1) {
         this.setBlockAndMetadata(world, -3, 1, i1, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, 3, 1, i1, Blocks.field_150407_cf, 0);
      }

      for(i1 = 11; i1 <= 12; ++i1) {
         this.setBlockAndMetadata(world, -3, 2, i1, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, -2, 1, i1, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, 2, 1, i1, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, 3, 2, i1, Blocks.field_150407_cf, 0);
      }

      this.setBlockAndMetadata(world, -3, 1, 4, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -3, 1, 9, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 3, 1, 9, Blocks.field_150462_ai, 0);

      for(i1 = 2; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -3, i1, 4, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, -3, i1, 9, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 3, i1, 9, this.fenceBlock, this.fenceMeta);
      }

      for(i1 = 1; i1 <= 12; ++i1) {
         for(i1 = -3; i1 <= 3; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, i1, this.plankBlock, this.plankMeta);
         }

         this.setBlockAndMetadata(world, -3, 7, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
         this.setBlockAndMetadata(world, -1, 9, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
         this.setBlockAndMetadata(world, 1, 9, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
         this.setBlockAndMetadata(world, 3, 7, i1, this.plankSlabBlock, this.plankSlabMeta | 8);
      }

      this.setBlockAndMetadata(world, 0, 4, 4, LOTRMod.chandelier, 1);
      this.setBlockAndMetadata(world, 0, 4, 9, LOTRMod.chandelier, 1);

      for(i1 = 0; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, 3, 1 + i1, 2 + i1, this.plankStairBlock, 2);
         this.setBlockAndMetadata(world, 3, 1 + i1, 3 + i1, this.plankStairBlock, 7);
      }

      this.setBlockAndMetadata(world, 2, 4, 6, this.plankStairBlock, 5);

      for(i1 = 3; i1 <= 5; ++i1) {
         this.setAir(world, 3, 5, i1);
      }

      this.setAir(world, 3, 5, 6);
      this.setAir(world, 3, 7, 6);
      this.setBlockAndMetadata(world, 2, 5, 6, this.plankStairBlock, 0);
      this.setBlockAndMetadata(world, 3, 6, 2, this.fenceBlock, this.fenceMeta);

      for(i1 = 2; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, 2, 6, i1, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, 1, 6, 5, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 1, 6, 6, this.fenceGateBlock, 1);
      this.setBlockAndMetadata(world, 1, 6, 7, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 6, 7, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 3, 6, 7, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 3, 6, 9, this.bedBlock, 2);
      this.setBlockAndMetadata(world, 3, 6, 8, this.bedBlock, 10);
      this.setBlockAndMetadata(world, 2, 6, 12, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 3, 6, 12, this.tableBlock, 0);
      this.placeChest(world, random, 3, 6, 11, 5, LOTRChestContents.GONDOR_HOUSE);

      for(i1 = -3; i1 <= -2; ++i1) {
         for(i1 = 7; i1 <= 8; ++i1) {
            this.setBlockAndMetadata(world, i1, 6, i1, this.plankBlock, this.plankMeta);
         }
      }

      this.placeBarrel(world, random, -3, 6, 6, 4, LOTRFoods.GONDOR_DRINK);
      this.placeMug(world, random, -2, 7, 7, 3, LOTRFoods.GONDOR_DRINK);
      this.placePlateWithCertainty(world, random, -2, 7, 8, this.plateBlock, LOTRFoods.GONDOR);
      this.setBlockAndMetadata(world, 0, 9, 4, LOTRMod.chandelier, 1);
      this.setBlockAndMetadata(world, 0, 9, 9, LOTRMod.chandelier, 1);

      for(i1 = 1; i1 <= 5; ++i1) {
         this.setBlockAndMetadata(world, -3, 6, i1, Blocks.field_150407_cf, 0);
      }

      for(i1 = 1; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -2, 6, i1, Blocks.field_150407_cf, 0);
      }

      for(i1 = 2; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, -2, 7, i1, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, -1, 6, i1, Blocks.field_150407_cf, 0);
      }

      this.setBlockAndMetadata(world, -3, 6, 11, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -3, 6, 12, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -3, 7, 12, Blocks.field_150407_cf, 0);

      for(i1 = 10; i1 <= 12; ++i1) {
         this.setBlockAndMetadata(world, -2, 6, i1, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, -1, 6, i1, Blocks.field_150407_cf, 0);
      }

      this.setBlockAndMetadata(world, -2, 7, 11, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -1, 7, 11, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, 0, 6, 11, Blocks.field_150407_cf, 0);
      if (random.nextInt(3) == 0) {
         if (random.nextBoolean()) {
            this.placeChest(world, random, -2, 6, 3, 4, LOTRChestContents.GONDOR_HOUSE_TREASURE);
         } else {
            this.placeChest(world, random, -1, 6, 11, 4, LOTRChestContents.GONDOR_HOUSE_TREASURE);
         }
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         for(i1 = 0; i1 <= 13; ++i1) {
            if (this.isOpaque(world, i1, 1, i1)) {
               this.setGrassToDirt(world, i1, 0, i1);
            }
         }
      }

      i1 = 3 + random.nextInt(6);

      for(i1 = 0; i1 < i1; ++i1) {
         EntityCreature animal = getRandomAnimal(world, random);
         this.spawnNPCAndSetHome(animal, world, 0, 1, 6, 0);
         animal.func_110177_bN();
      }

      for(i1 = 1; i1 <= 12; ++i1) {
         this.setBlockAndMetadata(world, -10, 1, i1, this.rockWallBlock, this.rockWallMeta);
      }

      int[] var16 = new int[]{0, 13};
      k1 = var16.length;

      for(l = 0; l < k1; ++l) {
         k1 = var16[l];
         this.setBlockAndMetadata(world, -10, 1, k1, this.rockWallBlock, this.rockWallMeta);
         this.setBlockAndMetadata(world, -10, 2, k1, Blocks.field_150478_aa, 5);
         this.setBlockAndMetadata(world, -9, 1, k1, this.fenceGateBlock, 0);

         for(i1 = -8; i1 <= -5; ++i1) {
            this.setBlockAndMetadata(world, i1, 1, k1, this.rockWallBlock, this.rockWallMeta);
         }

         this.setBlockAndMetadata(world, -5, 2, k1, Blocks.field_150478_aa, 5);
      }

      for(i1 = -9; i1 <= -5; ++i1) {
         for(k1 = 1; k1 <= 12; ++k1) {
            if (i1 == -5 && k1 >= 2 && k1 <= 11) {
               this.setBlockAndMetadata(world, i1, -1, k1, Blocks.field_150346_d, 0);
               this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150355_j, 0);
               this.setBlockAndMetadata(world, i1, 1, k1, this.rockSlabBlock, this.rockSlabMeta);
            } else if (i1 >= -8 && k1 >= 2 && k1 <= 11) {
               this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150458_ak, 7);
               this.setBlockAndMetadata(world, i1, 1, k1, this.cropBlock, this.cropMeta);
            } else {
               this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.dirtPath, 0);
            }
         }
      }

      this.setBlockAndMetadata(world, -10, 2, 6, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -10, 3, 6, Blocks.field_150407_cf, 0);
      this.setBlockAndMetadata(world, -10, 4, 6, Blocks.field_150423_aK, 3);
      LOTREntityGondorMan farmer = new LOTREntityGondorFarmer(world);
      this.spawnNPCAndSetHome(farmer, world, 0, 6, 8, 16);
      k1 = 1 + random.nextInt(3);

      for(l = 0; l < k1; ++l) {
         LOTREntityGondorFarmhand farmhand = new LOTREntityGondorFarmhand(world);
         this.spawnNPCAndSetHome(farmhand, world, -7, 1, 6, 12);
         farmhand.seedsItem = this.seedItem;
      }

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
