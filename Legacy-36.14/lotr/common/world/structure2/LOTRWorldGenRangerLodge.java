package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityDunedain;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenRangerLodge extends LOTRWorldGenRangerStructure {
   public LOTRWorldGenRangerLodge(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      this.setupRandomBlocks(random);
      int men;
      int l;
      int j1;
      int i1;
      int amount;
      if (this.restrictions) {
         men = 0;
         l = 0;

         for(j1 = -5; j1 <= 6; ++j1) {
            for(i1 = -4; i1 <= 4; ++i1) {
               amount = this.getTopBlock(world, j1, i1) - 1;
               if (!this.isSurface(world, j1, amount, i1)) {
                  return false;
               }

               if (amount < men) {
                  men = amount;
               }

               if (amount > l) {
                  l = amount;
               }

               if (l - men > 6) {
                  return false;
               }
            }
         }
      }

      int j1;
      for(men = -5; men <= 5; ++men) {
         for(l = -4; l <= 4; ++l) {
            j1 = Math.abs(men);
            i1 = Math.abs(l);
            if (j1 <= 4 || i1 <= 3) {
               for(amount = 0; (amount >= -3 || !this.isOpaque(world, men, amount, l)) && this.getY(amount) >= 0; --amount) {
                  this.setBlockAndMetadata(world, men, amount, l, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, men, amount - 1, l);
               }

               for(amount = 1; amount <= 8; ++amount) {
                  this.setAir(world, men, amount, l);
               }

               if (i1 == 4 || j1 == 5) {
                  boolean beam = false;
                  if (l == -4 && (j1 == 1 || j1 == 4)) {
                     beam = true;
                  }

                  if (l == 4 && (j1 == 0 || j1 == 4)) {
                     beam = true;
                  }

                  if (j1 == 5 && (i1 == 0 || i1 == 3)) {
                     beam = true;
                  }

                  if (beam) {
                     for(j1 = 1; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, men, j1, l, this.woodBeamBlock, this.woodBeamMeta);
                     }
                  } else {
                     for(j1 = 1; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, men, j1, l, this.wallBlock, this.wallMeta);
                     }
                  }
               }

               if (i1 <= 3 && j1 <= 4) {
                  this.setBlockAndMetadata(world, men, 0, l, this.plankSlabBlock, this.plankSlabMeta | 8);
                  if (random.nextInt(3) == 0) {
                     this.setBlockAndMetadata(world, men, 1, l, LOTRMod.thatchFloor, 0);
                  }

                  for(amount = -2; amount <= -1; ++amount) {
                     this.setAir(world, men, amount, l);
                  }
               }
            }
         }
      }

      int[] var16 = new int[]{-4, 4};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];

         for(amount = -4; amount <= 4; ++amount) {
            this.setBlockAndMetadata(world, amount, 4, i1, this.woodBeamBlock, this.woodBeamMeta | 4);
         }
      }

      var16 = new int[]{-5, 5};
      l = var16.length;

      int j1;
      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];

         for(amount = -3; amount <= 3; ++amount) {
            j1 = Math.abs(amount);
            if (j1 == 0) {
               for(j1 = 4; j1 <= 6; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, amount, this.woodBeamBlock, this.woodBeamMeta);
               }
            } else {
               this.setBlockAndMetadata(world, i1, 4, amount, this.woodBeamBlock, this.woodBeamMeta | 8);
               if (j1 <= 2) {
                  this.setBlockAndMetadata(world, i1, 5, amount, this.wallBlock, this.wallMeta);
               }
            }
         }
      }

      for(men = -5; men <= 5; ++men) {
         this.setBlockAndMetadata(world, men, 5, -3, this.woodBeamBlock, this.woodBeamMeta | 4);
         this.setBlockAndMetadata(world, men, 6, -1, this.woodBeamBlock, this.woodBeamMeta | 4);
         this.setBlockAndMetadata(world, men, 7, 0, this.woodBeamBlock, this.woodBeamMeta | 4);
         this.setBlockAndMetadata(world, men, 6, 1, this.woodBeamBlock, this.woodBeamMeta | 4);
         this.setBlockAndMetadata(world, men, 5, 3, this.woodBeamBlock, this.woodBeamMeta | 4);
         this.setBlockAndMetadata(world, men, 5, -4, this.roofStairBlock, 2);
         this.setBlockAndMetadata(world, men, 6, -3, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, men, 6, -2, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, men, 7, -1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, men, 7, 1, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, men, 6, 2, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, men, 6, 3, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, men, 5, 4, this.roofStairBlock, 3);
      }

      for(men = -4; men <= 4; ++men) {
         this.setBlockAndMetadata(world, 0, 4, men, this.woodBeamBlock, this.woodBeamMeta | 8);
      }

      this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);
      this.setBlockAndMetadata(world, 0, 4, -5, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -3, 2, -4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 3, 2, -4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -2, 2, 4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 2, 4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -5, 2, -1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, -5, 2, 1, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 3, 3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -4, 4, 0, Blocks.field_150478_aa, 2);
      var16 = new int[]{-4, 4};
      l = var16.length;

      int k1;
      int j1;
      int[] var18;
      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         var18 = new int[]{-3, 3};
         j1 = var18.length;

         for(j1 = 0; j1 < j1; ++j1) {
            k1 = var18[j1];
            this.setBlockAndMetadata(world, i1, 1, k1, this.plankBlock, this.plankMeta);

            for(j1 = 2; j1 <= 4; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, -2, 1, -3, this.plankBlock, this.plankMeta);
      this.placePlate(world, random, -2, 2, -3, this.plateBlock, LOTRFoods.RANGER);
      this.setBlockAndMetadata(world, -3, 1, -3, this.plankBlock, this.plankMeta);
      this.placePlate(world, random, -3, 2, -3, this.plateBlock, LOTRFoods.RANGER);
      this.setBlockAndMetadata(world, -4, 1, -2, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, -4, 2, -2, 3, LOTRFoods.RANGER_DRINK);
      this.placeChest(world, random, -4, 1, -1, 4, this.chestContentsHouse);
      this.setBlockAndMetadata(world, -4, 1, 0, Blocks.field_150462_ai, 0);
      this.placeChest(world, random, -4, 1, 1, 4, this.chestContentsHouse);
      this.setBlockAndMetadata(world, -4, 1, 2, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, -4, 2, 2, 4, LOTRFoods.RANGER_DRINK);
      this.setBlockAndMetadata(world, -3, 1, 3, this.plankBlock, this.plankMeta);
      this.placeBarrel(world, random, -3, 2, 3, 2, LOTRFoods.RANGER_DRINK);
      this.setBlockAndMetadata(world, -2, 1, 3, this.plankBlock, this.plankMeta);
      this.placeMug(world, random, -2, 2, 3, 0, LOTRFoods.RANGER_DRINK);
      var16 = new int[]{-3, 3};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, 2, 1, i1, this.bedBlock, 1);
         this.setBlockAndMetadata(world, 3, 1, i1, this.bedBlock, 9);
      }

      this.setBlockAndMetadata(world, 4, 1, -2, this.plankBlock, this.plankMeta);

      for(men = 4; men <= 6; ++men) {
         for(l = -1; l <= 1; ++l) {
            for(j1 = 5; (j1 >= 0 || !this.isOpaque(world, men, j1, l)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, men, j1, l, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, men, j1 - 1, l);
            }
         }
      }

      this.setBlockAndMetadata(world, 4, 6, 0, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 6, 5, -1, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, 6, 5, 1, this.brickStairBlock, 3);
      this.setBlockAndMetadata(world, 6, 6, 0, this.brickStairBlock, 0);

      for(men = 6; men <= 8; ++men) {
         this.setBlockAndMetadata(world, 5, men, 0, this.brickBlock, this.brickMeta);
      }

      for(men = 9; men <= 10; ++men) {
         this.setBlockAndMetadata(world, 5, men, 0, this.brickWallBlock, this.brickWallMeta);
      }

      this.setBlockAndMetadata(world, 5, 0, 0, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 5, 1, 0, Blocks.field_150480_ab, 0);

      for(men = 2; men <= 3; ++men) {
         this.setAir(world, 5, men, 0);
      }

      this.setBlockAndMetadata(world, 4, 1, 0, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 4, 2, 0, Blocks.field_150460_al, 5);
      this.spawnItemFrame(world, 4, 4, 0, 3, this.getRangerFramedItem(random));
      this.setBlockAndMetadata(world, 4, 1, 2, this.trapdoorBlock, 3);

      for(men = -2; men <= 0; ++men) {
         this.setBlockAndMetadata(world, 4, men, 2, Blocks.field_150468_ap, 3);
      }

      var16 = new int[]{-4, 4};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         var18 = new int[]{-3, 3};
         j1 = var18.length;

         for(j1 = 0; j1 < j1; ++j1) {
            k1 = var18[j1];
            this.setBlockAndMetadata(world, i1, 0, k1, this.plankBlock, this.plankMeta);

            for(j1 = -2; j1 <= -1; ++j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.woodBeamBlock, this.woodBeamMeta);
            }
         }
      }

      this.setBlockAndMetadata(world, -3, -1, -3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, -1, -3, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -3, -1, 3, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, -1, 3, Blocks.field_150478_aa, 1);
      var16 = new int[]{-2, 0, 2};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.setBlockAndMetadata(world, i1, -2, -2, this.bedBlock, 2);
         this.setBlockAndMetadata(world, i1, -2, -3, this.bedBlock, 10);
      }

      var16 = new int[]{-2, 2};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         ItemStack[] armor = null;
         if (random.nextInt(3) == 0) {
            armor = new ItemStack[]{new ItemStack(LOTRMod.helmetRanger), new ItemStack(LOTRMod.bodyRanger), new ItemStack(LOTRMod.legsRanger), new ItemStack(LOTRMod.bootsRanger)};
         }

         this.placeArmorStand(world, -4, -2, i1, 3, armor);
      }

      var16 = new int[]{-1, 1};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         this.spawnItemFrame(world, -5, -1, i1, 1, this.getRangerFramedItem(random));
      }

      this.setBlockAndMetadata(world, 0, -2, 3, this.tableBlock, 0);
      var16 = new int[]{-1, 1};
      l = var16.length;

      for(j1 = 0; j1 < l; ++j1) {
         i1 = var16[j1];
         amount = 2 + random.nextInt(5);
         this.placeChest(world, random, i1, -2, 3, 2, this.chestContentsRanger, amount);
      }

      men = 1 + random.nextInt(2);

      for(l = 0; l < men; ++l) {
         LOTREntityDunedain dunedain = new LOTREntityDunedain(world);
         this.spawnNPCAndSetHome(dunedain, world, 0, 1, 0, 16);
      }

      return true;
   }
}
