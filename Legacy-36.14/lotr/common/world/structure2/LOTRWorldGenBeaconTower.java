package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityGondorSoldier;
import lotr.common.entity.npc.LOTREntityGondorTowerGuard;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBeaconTower extends LOTRWorldGenGondorStructure {
   public boolean generateRoom = true;

   public LOTRWorldGenBeaconTower(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      return this.generateWithSetRotationAndHeight(world, random, i, j, k, rotation, random.nextInt(4));
   }

   public boolean generateWithSetRotationAndHeight(World world, Random random, int i, int j, int k, int rotation, int height) {
      int doorBase = j - 1;
      j += height;
      this.setOriginAndRotation(world, i, j, k, rotation, 3);
      doorBase -= this.getY(0);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int j1;
      if (this.restrictions) {
         for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
               j1 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, j1, k1)) {
                  return false;
               }
            }
         }
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            for(j1 = 9; j1 <= 13; ++j1) {
               this.setAir(world, i1, j1, k1);
            }
         }
      }

      int j1;
      int j1;
      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            j1 = Math.abs(i1);
            j1 = Math.abs(k1);

            for(j1 = 8; j1 >= doorBase || !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
               if (j1 == 2 && j1 == 2) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.pillarBlock, this.pillarMeta);
               } else {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
               }

               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            if (j1 == 2 && j1 == 2) {
               for(j1 = 9; j1 <= 12; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.pillarBlock, this.pillarMeta);
               }
            } else if (j1 == 2 || j1 == 2) {
               this.setBlockAndMetadata(world, i1, 9, k1, this.fenceBlock, this.fenceMeta);
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            j1 = Math.abs(i1);
            j1 = Math.abs(k1);
            if (j1 == 3 && j1 == 1 || j1 == 3 && j1 == 1) {
               for(j1 = 4; j1 >= 1 || !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }
            }
         }
      }

      int[] var17 = new int[]{-1, 1};
      k1 = var17.length;

      for(j1 = 0; j1 < k1; ++j1) {
         j1 = var17[j1];
         this.setBlockAndMetadata(world, j1, 5, -3, this.brickStairBlock, 2);
         this.setBlockAndMetadata(world, j1, 5, 3, this.brickStairBlock, 3);
      }

      var17 = new int[]{-1, 1};
      k1 = var17.length;

      for(j1 = 0; j1 < k1; ++j1) {
         j1 = var17[j1];
         this.setBlockAndMetadata(world, -3, 5, j1, this.brickStairBlock, 1);
         this.setBlockAndMetadata(world, 3, 5, j1, this.brickStairBlock, 0);
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            this.setBlockAndMetadata(world, i1, 8, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
         }
      }

      this.setBlockAndMetadata(world, 0, 9, 0, this.rockBlock, this.rockMeta);
      this.setBlockAndMetadata(world, 0, 10, 0, LOTRMod.beacon, 0);
      this.setBlockAndMetadata(world, -2, 9, 0, this.fenceGateBlock, 3);

      for(i1 = 8; !this.isOpaque(world, -3, i1, 0) && this.getY(i1) >= 0; --i1) {
         this.setBlockAndMetadata(world, -3, i1, 0, Blocks.field_150468_ap, 5);
      }

      this.setBlockAndMetadata(world, -2, 12, -1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 2, 12, -1, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -2, 12, 1, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 2, 12, 1, Blocks.field_150478_aa, 4);

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            j1 = Math.abs(i1);
            j1 = Math.abs(k1);
            if (j1 != 3 && j1 != 3) {
               if (j1 != 2 && j1 != 2) {
                  if (j1 == 1 || j1 == 1) {
                     this.setBlockAndMetadata(world, i1, 14, k1, this.brickBlock, this.brickMeta);
                  }
               } else {
                  if (j1 == 2 && j1 == 2) {
                     this.setBlockAndMetadata(world, i1, 13, k1, this.brickBlock, this.brickMeta);
                  } else {
                     this.setBlockAndMetadata(world, i1, 13, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
                  }

                  this.setBlockAndMetadata(world, i1, 14, k1, this.brickSlabBlock, this.brickSlabMeta);
               }
            } else {
               this.setBlockAndMetadata(world, i1, 13, k1, this.brickSlabBlock, this.brickSlabMeta | 8);
            }
         }
      }

      var17 = new int[]{-2, 2};
      k1 = var17.length;

      for(j1 = 0; j1 < k1; ++j1) {
         j1 = var17[j1];
         int[] var18 = new int[]{-2, 2};
         int var14 = var18.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            int k1 = var18[var15];
            this.setBlockAndMetadata(world, j1, 13, k1 - 1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, j1, 13, k1 + 1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, j1 - 1, 13, k1, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, j1 + 1, 13, k1, this.brickStairBlock, 4);
         }
      }

      if (this.generateRoom) {
         this.setBlockAndMetadata(world, 0, doorBase, -2, this.brickBlock, this.brickMeta);
         this.setBlockAndMetadata(world, 0, doorBase + 1, -2, this.doorBlock, 1);
         this.setBlockAndMetadata(world, 0, doorBase + 2, -2, this.doorBlock, 9);

         for(i1 = -1; i1 <= 1; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
               this.setBlockAndMetadata(world, i1, doorBase, k1, this.brickBlock, this.brickMeta);

               for(j1 = doorBase + 1; j1 <= doorBase + 4; ++j1) {
                  this.setAir(world, i1, j1, k1);
               }
            }
         }

         this.setBlockAndMetadata(world, 0, doorBase + 3, -1, Blocks.field_150478_aa, 3);
         this.setBlockAndMetadata(world, 1, doorBase + 1, -1, this.tableBlock, 0);
         this.placeWallBanner(world, 2, doorBase + 4, -1, this.bannerType, 3);
         this.placeChest(world, random, -1, doorBase + 1, -1, LOTRMod.chestLebethron, 3, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);

         for(i1 = doorBase + 1; i1 <= doorBase + 4; ++i1) {
            this.setBlockAndMetadata(world, 1, i1, 1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 1, i1, 0, Blocks.field_150468_ap, 2);
         }

         this.setBlockAndMetadata(world, -1, doorBase + 2, 1, this.brickSlabBlock, this.brickSlabMeta | 8);
         this.setBlockAndMetadata(world, 0, doorBase + 2, 1, this.brickSlabBlock, this.brickSlabMeta | 8);
         var17 = new int[]{doorBase + 1, doorBase + 3};
         k1 = var17.length;

         for(j1 = 0; j1 < k1; ++j1) {
            j1 = var17[j1];
            this.setBlockAndMetadata(world, -1, j1, 1, this.bedBlock, 1);
            this.setBlockAndMetadata(world, 0, j1, 1, this.bedBlock, 9);
         }
      }

      i1 = 1 + random.nextInt(2);

      for(k1 = 0; k1 < i1; ++k1) {
         LOTREntityGondorSoldier soldier = new LOTREntityGondorTowerGuard(world);
         soldier.spawnRidingHorse = false;
         this.spawnNPCAndSetHome(soldier, world, -1, 9, 0, 16);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClass(LOTREntityGondorTowerGuard.class);
      respawner.setCheckRanges(16, -12, 12, 4);
      respawner.setSpawnRanges(2, -2, 2, 16);
      this.placeNPCRespawner(respawner, world, 0, 9, 0);
      return true;
   }
}
