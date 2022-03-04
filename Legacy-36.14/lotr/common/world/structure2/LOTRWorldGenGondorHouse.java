package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorHouse extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorHouse(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 5);
      this.setupRandomBlocks(random);
      int men;
      int l;
      int j1;
      int k2;
      int j1;
      if (this.restrictions) {
         men = 0;
         l = 0;

         for(j1 = -4; j1 <= 4; ++j1) {
            for(k2 = -5; k2 <= 5; ++k2) {
               j1 = this.getTopBlock(world, j1, k2) - 1;
               if (!this.isSurface(world, j1, j1, k2)) {
                  return false;
               }

               if (j1 < men) {
                  men = j1;
               }

               if (j1 > l) {
                  l = j1;
               }

               if (l - men > 5) {
                  return false;
               }
            }
         }
      }

      for(men = -4; men <= 4; ++men) {
         for(l = -4; l <= 4; ++l) {
            j1 = Math.abs(men);
            k2 = Math.abs(l);
            if (j1 != 4 || k2 != 4) {
               if ((j1 != 3 || k2 != 4) && (k2 != 3 || j1 != 4)) {
                  if (j1 != 4 && k2 != 4) {
                     for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, men, j1, l)) && this.getY(j1) >= 0; --j1) {
                        this.setBlockAndMetadata(world, men, j1, l, this.rockBlock, this.rockMeta);
                        this.setGrassToDirt(world, men, j1 - 1, l);
                     }

                     for(j1 = 1; j1 <= 5; ++j1) {
                        this.setAir(world, men, j1, l);
                     }
                  } else {
                     for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, men, j1, l)) && this.getY(j1) >= 0; --j1) {
                        this.setBlockAndMetadata(world, men, j1, l, this.rockBlock, this.rockMeta);
                        this.setGrassToDirt(world, men, j1 - 1, l);
                     }

                     for(j1 = 1; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, men, j1, l, this.wallBlock, this.wallMeta);
                     }

                     if (k2 == 4) {
                        this.setBlockAndMetadata(world, men, 4, l, this.woodBeamBlock, this.woodBeamMeta | 4);
                     }
                  }
               } else {
                  for(j1 = 4; (j1 >= 0 || !this.isOpaque(world, men, j1, l)) && this.getY(j1) >= 0; --j1) {
                     this.setBlockAndMetadata(world, men, j1, l, this.woodBeamBlock, this.woodBeamMeta);
                     this.setGrassToDirt(world, men, j1 - 1, l);
                  }
               }
            }
         }
      }

      int[] var12 = new int[]{-4, 4};
      l = var12.length;

      for(j1 = 0; j1 < l; ++j1) {
         k2 = var12[j1];

         for(j1 = 1; j1 <= 4; ++j1) {
            this.setBlockAndMetadata(world, k2, j1, 0, this.woodBeamBlock, this.woodBeamMeta);
         }

         this.setBlockAndMetadata(world, k2, 2, -2, this.fenceBlock, this.fenceMeta);
         this.setBlockAndMetadata(world, k2, 2, 2, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -2, 2, -4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 2, 2, -4, this.fenceBlock, this.fenceMeta);
      this.setBlockAndMetadata(world, 0, 0, -4, this.rockBlock, this.rockMeta);
      this.setBlockAndMetadata(world, 0, 1, -4, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -4, this.doorBlock, 8);

      for(men = -5; men <= 5; ++men) {
         this.setBlockAndMetadata(world, -4, 4, men, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, -3, 5, men, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, -2, 5, men, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, -1, 6, men, this.roofStairBlock, 1);
         this.setBlockAndMetadata(world, 0, 6, men, this.woodBeamBlock, this.woodBeamMeta | 8);
         this.setBlockAndMetadata(world, 0, 7, men, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, 1, 6, men, this.roofStairBlock, 0);
         this.setBlockAndMetadata(world, 2, 5, men, this.roofBlock, this.roofMeta);
         this.setBlockAndMetadata(world, 3, 5, men, this.roofSlabBlock, this.roofSlabMeta);
         this.setBlockAndMetadata(world, 4, 4, men, this.roofStairBlock, 0);
         l = Math.abs(men);
         if (l == 5) {
            this.setBlockAndMetadata(world, -3, 4, men, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -1, 5, men, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 1, 5, men, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 3, 4, men, this.roofStairBlock, 5);
         } else if (l == 4) {
            this.setBlockAndMetadata(world, -1, 5, men, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, 0, 5, men, this.wallBlock, this.wallMeta);
            this.setBlockAndMetadata(world, 1, 5, men, this.wallBlock, this.wallMeta);
         }
      }

      for(men = -1; men <= 1; ++men) {
         for(l = 3; l <= 5; ++l) {
            for(j1 = 7; (j1 >= 0 || !this.isOpaque(world, men, j1, l)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, men, j1, l, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, men, j1 - 1, l);
            }
         }

         this.setBlockAndMetadata(world, men, 8, 3, this.brickStairBlock, 2);
         this.setBlockAndMetadata(world, men, 8, 5, this.brickStairBlock, 3);
      }

      this.setBlockAndMetadata(world, -1, 8, 4, this.brickStairBlock, 1);
      this.setBlockAndMetadata(world, 0, 8, 4, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 1, 8, 4, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, 0, 9, 4, this.brickWallBlock, this.brickWallMeta);

      for(men = -3; men <= -2; ++men) {
         this.setBlockAndMetadata(world, men, 1, -3, this.plankStairBlock, 3);
         this.setBlockAndMetadata(world, men, 1, -2, this.plankBlock, this.plankMeta);
         this.setBlockAndMetadata(world, men, 1, -1, this.plankStairBlock, 2);
         if (random.nextBoolean()) {
            this.placePlateWithCertainty(world, random, men, 2, -2, this.plateBlock, LOTRFoods.GONDOR);
         } else {
            l = random.nextInt(4);
            this.placeMug(world, random, men, 2, -2, l, LOTRFoods.GONDOR_DRINK);
         }
      }

      this.setBlockAndMetadata(world, 2, 1, -3, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 3, 1, -3, this.plankBlock, this.plankMeta);
      this.placePlateWithCertainty(world, random, 3, 2, -3, this.plateBlock, LOTRFoods.GONDOR);
      this.setBlockAndMetadata(world, 3, 1, -2, Blocks.field_150383_bp, 3);
      this.setBlockAndMetadata(world, 3, 1, -1, this.tableBlock, 0);
      this.setBlockAndMetadata(world, -2, 1, 1, this.bedBlock, 3);
      this.setBlockAndMetadata(world, -3, 1, 1, this.bedBlock, 11);
      this.setBlockAndMetadata(world, 2, 1, 1, this.bedBlock, 1);
      this.setBlockAndMetadata(world, 3, 1, 1, this.bedBlock, 9);
      this.setBlockAndMetadata(world, -3, 1, 3, this.plankBlock, this.plankMeta);
      this.setBlockAndMetadata(world, 3, 1, 3, this.plankBlock, this.plankMeta);
      this.placeChest(world, random, -2, 1, 3, 2, this.chestContents);
      this.placeChest(world, random, 2, 1, 3, 2, this.chestContents);
      this.setBlockAndMetadata(world, 0, 1, 3, this.barsBlock, 0);
      this.setBlockAndMetadata(world, 0, 2, 3, Blocks.field_150460_al, 2);
      this.setBlockAndMetadata(world, 0, 0, 4, LOTRMod.hearth, 0);
      this.setBlockAndMetadata(world, 0, 1, 4, Blocks.field_150480_ab, 0);

      for(men = 2; men <= 7; ++men) {
         this.setAir(world, 0, men, 4);
      }

      this.spawnItemFrame(world, 0, 3, 3, 2, this.getGondorFramedItem(random));
      men = 1 + random.nextInt(2);

      for(l = 0; l < men; ++l) {
         LOTREntityGondorMan gondorMan = new LOTREntityGondorMan(world);
         this.spawnNPCAndSetHome(gondorMan, world, 0, 1, 0, 16);
      }

      return true;
   }
}
