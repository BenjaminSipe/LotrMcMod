package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityTauredainShaman;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainHouseStilts extends LOTRWorldGenTauredainHouse {
   public LOTRWorldGenTauredainHouseStilts(boolean flag) {
      super(flag);
   }

   protected int getOffset() {
      return 5;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
         return false;
      } else {
         this.bedBlock = LOTRMod.strawBed;

         int i1;
         int k1;
         int i2;
         for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
               for(i2 = 3; i2 <= 7; ++i2) {
                  this.setAir(world, i1, i2, k1);
               }
            }
         }

         this.placeStilt(world, -3, -3, false);
         this.placeStilt(world, 3, -3, false);
         this.placeStilt(world, -3, 3, false);
         this.placeStilt(world, 3, 3, false);
         this.placeStilt(world, 0, -4, true);

         int k2;
         for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
               i2 = Math.abs(i1);
               k2 = Math.abs(k1);
               if (i2 == 3 && k2 == 3) {
                  for(int j1 = 3; j1 <= 7; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.woodBlock, this.woodMeta);
                  }
               }

               if (i2 == 3 && k2 <= 2) {
                  this.setBlockAndMetadata(world, i1, 3, k1, this.woodBlock, this.woodMeta | 8);
                  this.setBlockAndMetadata(world, i1, 4, k1, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, i1, 5, k1, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, i1, 6, k1, this.woodBlock, this.woodMeta | 8);
               }

               if (k2 == 3 && i2 <= 2) {
                  this.setBlockAndMetadata(world, i1, 3, k1, this.woodBlock, this.woodMeta | 4);
                  this.setBlockAndMetadata(world, i1, 4, k1, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, i1, 5, k1, this.plankBlock, this.plankMeta);
                  this.setBlockAndMetadata(world, i1, 6, k1, this.woodBlock, this.woodMeta | 4);
               }

               if (i2 <= 2 && k2 <= 2) {
                  this.setBlockAndMetadata(world, i1, 3, k1, this.plankBlock, this.plankMeta);
                  if (random.nextInt(3) == 0) {
                     this.setBlockAndMetadata(world, i1, 4, k1, LOTRMod.thatchFloor, 0);
                  }
               }

               if (i1 == -3 && k2 == 1) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.plankStairBlock, 1);
                  this.setBlockAndMetadata(world, i1, 5, k1, this.fenceBlock, this.fenceMeta);
               }

               if (i1 == 3 && k2 == 1) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.plankStairBlock, 0);
                  this.setBlockAndMetadata(world, i1, 5, k1, this.fenceBlock, this.fenceMeta);
               }

               if (k1 == 3 && i2 == 1) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.plankStairBlock, 3);
                  this.setBlockAndMetadata(world, i1, 5, k1, this.fenceBlock, this.fenceMeta);
               }
            }
         }

         int[] var12 = new int[]{3, 6};
         k1 = var12.length;

         for(i2 = 0; i2 < k1; ++i2) {
            k2 = var12[i2];
            this.setBlockAndMetadata(world, -4, k2, -3, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, 4, k2, -3, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, -4, k2, 3, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, 4, k2, 3, this.woodBlock, this.woodMeta | 4);
            this.setBlockAndMetadata(world, -3, k2, -4, this.woodBlock, this.woodMeta | 8);
            this.setBlockAndMetadata(world, -3, k2, 4, this.woodBlock, this.woodMeta | 8);
            this.setBlockAndMetadata(world, 3, k2, -4, this.woodBlock, this.woodMeta | 8);
            this.setBlockAndMetadata(world, 3, k2, 4, this.woodBlock, this.woodMeta | 8);
         }

         for(i1 = -4; i1 <= 4; ++i1) {
            for(k1 = -4; k1 <= 4; ++k1) {
               i2 = Math.abs(i1);
               k2 = Math.abs(k1);
               if (i2 == 4 && k2 == 2 || k2 == 4 && i2 == 2) {
                  this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
               }

               if (i2 == 4 && k2 <= 3 || k2 == 4 && i2 <= 3) {
                  this.setBlockAndMetadata(world, i1, 7, k1, this.thatchSlabBlock, this.thatchSlabMeta);
               }

               if (i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
                  this.setBlockAndMetadata(world, i1, 7, k1, this.thatchBlock, this.thatchMeta);
               }

               if (i2 == 2 && k2 <= 2 || k2 == 2 && i2 <= 2) {
                  this.setBlockAndMetadata(world, i1, 8, k1, this.thatchSlabBlock, this.thatchSlabMeta);
                  if (i2 == 2 && k2 == 2) {
                     this.setBlockAndMetadata(world, i1, 7, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
                  } else {
                     this.setBlockAndMetadata(world, i1, 7, k1, this.fenceBlock, this.fenceMeta);
                  }
               }

               if (i2 == 1 && k2 <= 1 || k2 == 1 && i2 <= 1) {
                  this.setBlockAndMetadata(world, i1, 8, k1, this.thatchSlabBlock, this.thatchSlabMeta | 8);
               }

               if (i2 == 0 && k2 == 0) {
                  this.setBlockAndMetadata(world, i1, 9, k1, this.thatchSlabBlock, this.thatchSlabMeta);
               }
            }
         }

         for(i1 = -2; i1 <= 2; ++i1) {
            this.setBlockAndMetadata(world, i1, 3, -4, this.plankBlock, this.plankMeta);
            if (i1 != 0) {
               this.setBlockAndMetadata(world, i1, 3, -5, this.plankSlabBlock, this.plankSlabMeta | 8);
               this.setBlockAndMetadata(world, i1, 4, -5, this.fenceBlock, this.fenceMeta);
            }
         }

         this.setBlockAndMetadata(world, -2, 4, -4, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, 2, 4, -4, this.fenceBlock, this.fenceMeta);
         this.setAir(world, 0, 4, -3);
         this.setAir(world, 0, 5, -3);
         this.setBlockAndMetadata(world, 0, 6, -2, Blocks.field_150478_aa, 3);
         this.setBlockAndMetadata(world, -2, 6, 0, Blocks.field_150478_aa, 2);
         this.setBlockAndMetadata(world, 0, 6, 2, Blocks.field_150478_aa, 4);
         this.setBlockAndMetadata(world, 2, 6, 0, Blocks.field_150478_aa, 1);
         this.setBlockAndMetadata(world, -2, 4, -2, this.woodBlock, this.woodMeta);
         this.placeChest(world, random, -2, 5, -2, 3, LOTRChestContents.TAUREDAIN_HOUSE);
         this.setBlockAndMetadata(world, 2, 4, -2, this.woodBlock, this.woodMeta);
         this.placeBarrel(world, random, 2, 5, -2, 3, LOTRFoods.TAUREDAIN_DRINK);
         var12 = new int[]{-2, 2};
         k1 = var12.length;

         for(i2 = 0; i2 < k1; ++i2) {
            k2 = var12[i2];
            this.setBlockAndMetadata(world, k2, 4, 1, this.bedBlock, 0);
            this.setBlockAndMetadata(world, k2, 4, 2, this.bedBlock, 8);
         }

         this.setBlockAndMetadata(world, -1, 4, 2, LOTRMod.tauredainTable, 0);
         this.setBlockAndMetadata(world, 0, 4, 2, this.woodBlock, this.woodMeta);
         this.placeMug(world, random, 0, 5, 2, 0, LOTRFoods.TAUREDAIN_DRINK);
         this.setBlockAndMetadata(world, 1, 4, 2, Blocks.field_150462_ai, 0);
         this.placeTauredainTorch(world, -1, 5, -5);
         this.placeTauredainTorch(world, 1, 5, -5);
         LOTREntityTauredainShaman shaman = new LOTREntityTauredainShaman(world);
         this.spawnNPCAndSetHome(shaman, world, 0, 4, 0, 2);
         return true;
      }
   }

   private void placeStilt(World world, int i, int k, boolean ladder) {
      for(int j = 3; (j == 3 || !this.isOpaque(world, i, j, k)) && this.getY(j) >= 0; --j) {
         this.setBlockAndMetadata(world, i, j, k, this.woodBlock, this.woodMeta);
         this.setGrassToDirt(world, i, j - 1, k);
         if (ladder) {
            this.setBlockAndMetadata(world, i, j, k - 1, Blocks.field_150468_ap, 2);
         }
      }

   }
}
