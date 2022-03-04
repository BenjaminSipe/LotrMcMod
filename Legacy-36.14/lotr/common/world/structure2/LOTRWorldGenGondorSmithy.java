package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityGondorBlacksmith;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorSmithy extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorSmithy(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int k2;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(k2 = -4; k2 <= 4; ++k2) {
            for(j1 = 1; j1 <= 11; ++j1) {
               int j1 = this.getTopBlock(world, k2, j1) - 1;
               if (!this.isSurface(world, k2, j1, j1)) {
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

      for(i1 = 1; i1 <= 11; ++i1) {
         for(k1 = -4; k1 <= 4; ++k1) {
            boolean pillar = Math.abs(k1) == 4 && (i1 == 1 || i1 == 11);
            if (pillar) {
               for(j1 = 4; (j1 >= 0 || !this.isOpaque(world, k1, j1, i1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, k1, j1, i1, this.pillar2Block, this.pillar2Meta);
                  this.setGrassToDirt(world, k1, j1 - 1, i1);
               }
            } else {
               for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, k1, j1, i1)) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, k1, j1, i1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                  this.setGrassToDirt(world, k1, j1 - 1, i1);
               }

               if (Math.abs(k1) != 4 && i1 != 1 && i1 != 11) {
                  for(j1 = 1; j1 <= 5; ++j1) {
                     this.setAir(world, k1, j1, i1);
                  }
               } else {
                  for(j1 = 1; j1 <= 3; ++j1) {
                     this.setBlockAndMetadata(world, k1, j1, i1, this.brickBlock, this.brickMeta);
                  }

                  this.setBlockAndMetadata(world, k1, 4, i1, this.brickWallBlock, this.brickWallMeta);
               }
            }
         }
      }

      for(i1 = 3; i1 <= 7; i1 += 4) {
         for(k1 = -2; k1 <= 1; k1 += 3) {
            for(k2 = i1; k2 <= i1 + 2; ++k2) {
               for(j1 = k1; j1 <= k1 + 1; ++j1) {
                  this.setBlockAndMetadata(world, j1, 0, k2, this.rockBlock, this.rockMeta);
               }
            }
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = 1; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, k1, 1, this.rockBlock, this.rockMeta);
         }
      }

      this.setBlockAndMetadata(world, 0, 1, 1, this.fenceGateBlock, 0);
      this.setAir(world, 0, 2, 1);

      for(i1 = 2; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, -4, 4, i1, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, 4, 4, i1, this.brickWallBlock, this.brickWallMeta);
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 4, 1, this.brickWallBlock, this.brickWallMeta);
         this.setBlockAndMetadata(world, i1, 4, 11, this.brickWallBlock, this.brickWallMeta);
      }

      for(i1 = 2; i1 <= 10; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, k1, 5, i1, this.brick2Block, this.brick2Meta);
         }
      }

      for(i1 = 2; i1 <= 10; ++i1) {
         this.setBlockAndMetadata(world, -4, 5, i1, this.brick2StairBlock, 1);
         this.setBlockAndMetadata(world, 4, 5, i1, this.brick2StairBlock, 0);
      }

      for(i1 = -4; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, i1, 5, 1, this.brick2StairBlock, 2);
         this.setBlockAndMetadata(world, i1, 5, 11, this.brick2StairBlock, 3);
      }

      this.setBlockAndMetadata(world, -3, 1, 2, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, -3, 1, 3, this.tableBlock, 0);
      this.setBlockAndMetadata(world, -3, 1, 4, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, -3, 2, 4, this.brickWallBlock, this.brickWallMeta);

      for(i1 = 2; i1 <= 4; ++i1) {
         this.setBlockAndMetadata(world, -3, 3, i1, this.brickStairBlock, 0);
      }

      for(i1 = 2; i1 <= 6; i1 += 2) {
         this.setBlockAndMetadata(world, 3, 1, i1, Blocks.field_150467_bQ, 0);
      }

      this.placeChest(world, random, 3, 1, 8, Blocks.field_150486_ae, 5, LOTRChestContents.GONDOR_SMITHY);
      this.placeChest(world, random, 3, 1, 9, Blocks.field_150486_ae, 5, LOTRChestContents.GONDOR_SMITHY);
      this.setBlockAndMetadata(world, -1, 2, 2, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 1, 2, 2, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, -3, 2, 6, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, 2, 6, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, -1, 1, 8, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
      this.setBlockAndMetadata(world, -1, 2, 8, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
      this.setBlockAndMetadata(world, -3, 1, 9, Blocks.field_150353_l, 0);
      this.setBlockAndMetadata(world, -2, 1, 9, Blocks.field_150353_l, 0);
      this.setBlockAndMetadata(world, -3, 1, 10, Blocks.field_150353_l, 0);
      this.setBlockAndMetadata(world, -2, 1, 10, Blocks.field_150353_l, 0);
      this.setBlockAndMetadata(world, -3, 3, 8, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -2, 3, 8, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -1, 3, 8, this.brickStairBlock, 2);
      this.setBlockAndMetadata(world, -1, 3, 9, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, -1, 3, 10, this.brickStairBlock, 0);
      this.setBlockAndMetadata(world, -3, 1, 8, LOTRMod.alloyForge, 2);
      this.setBlockAndMetadata(world, -2, 1, 8, LOTRMod.alloyForge, 2);
      this.setBlockAndMetadata(world, -1, 1, 9, LOTRMod.alloyForge, 4);
      this.setBlockAndMetadata(world, -1, 1, 10, LOTRMod.alloyForge, 4);
      world.func_72921_c(-3, 1, 8, 2, 3);
      world.func_72921_c(-2, 1, 8, 2, 3);
      world.func_72921_c(-1, 1, 9, 5, 3);
      world.func_72921_c(-1, 1, 10, 5, 3);
      this.setBlockAndMetadata(world, -3, 2, 8, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -2, 2, 8, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -1, 2, 9, this.barsBlock, 0);
      this.setBlockAndMetadata(world, -1, 2, 10, this.barsBlock, 0);

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            if (i1 != 0 || k1 != 0) {
               this.setBlockAndMetadata(world, -3 + i1, 4, 10 + k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
               this.setBlockAndMetadata(world, -3 + i1, 5, 10 + k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
               this.setBlockAndMetadata(world, -3 + i1, 6, 10 + k1, this.rockSlabBlock, this.rockSlabMeta);
            }
         }
      }

      this.setAir(world, -3, 5, 10);
      LOTREntityGondorBlacksmith blacksmith = new LOTREntityGondorBlacksmith(world);
      this.spawnNPCAndSetHome(blacksmith, world, 0, 1, 6, 4);
      return true;
   }
}
