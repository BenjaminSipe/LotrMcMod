package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorBath extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorBath(boolean flag) {
      super(flag);
   }

   protected LOTREntityNPC createBather(World world) {
      return new LOTREntityGondorMan(world);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 10);
      this.setupRandomBlocks(random);
      int i1;
      int j1;
      int i1;
      int k1;
      int k2;
      if (this.restrictions) {
         i1 = 0;
         j1 = 0;

         for(i1 = -11; i1 <= 11; ++i1) {
            for(k1 = -9; k1 <= 9; ++k1) {
               k2 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, k2, k1)) {
                  return false;
               }

               if (k2 < i1) {
                  i1 = k2;
               }

               if (k2 > j1) {
                  j1 = k2;
               }

               if (j1 - i1 > 6) {
                  return false;
               }
            }
         }
      }

      for(i1 = -11; i1 <= 11; ++i1) {
         for(j1 = -9; j1 <= 9; ++j1) {
            i1 = Math.abs(i1);
            k1 = Math.abs(j1);

            for(k2 = 0; (k2 >= -1 || !this.isOpaque(world, i1, k2, j1)) && this.getY(k2) >= 0; --k2) {
               this.setBlockAndMetadata(world, i1, k2, j1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, k2 - 1, j1);
            }

            for(k2 = 1; k2 <= 8; ++k2) {
               this.setAir(world, i1, k2, j1);
            }

            if (i1 <= 6 && k1 <= 4 && i1 + k1 <= 8) {
               this.setBlockAndMetadata(world, i1, 0, j1, Blocks.field_150355_j, 0);
            }
         }
      }

      for(i1 = -10; i1 <= 10; ++i1) {
         for(j1 = -8; j1 <= 8; ++j1) {
            i1 = Math.abs(i1);
            k1 = Math.abs(j1);
            if (i1 == 10 && k1 % 4 == 0 || k1 == 8 && i1 % 4 == 2) {
               for(k2 = 1; k2 <= 4; ++k2) {
                  this.setBlockAndMetadata(world, i1, k2, j1, this.pillarBlock, this.pillarMeta);
               }

               this.setBlockAndMetadata(world, i1 - 1, 1, j1, this.brickStairBlock, 1);
               this.setBlockAndMetadata(world, i1 + 1, 1, j1, this.brickStairBlock, 0);
               this.setBlockAndMetadata(world, i1, 1, j1 - 1, this.brickStairBlock, 2);
               this.setBlockAndMetadata(world, i1, 1, j1 + 1, this.brickStairBlock, 3);
               this.setBlockAndMetadata(world, i1 - 1, 4, j1, this.brickStairBlock, 5);
               this.setBlockAndMetadata(world, i1 + 1, 4, j1, this.brickStairBlock, 4);
               this.setBlockAndMetadata(world, i1, 4, j1 - 1, this.brickStairBlock, 6);
               this.setBlockAndMetadata(world, i1, 4, j1 + 1, this.brickStairBlock, 7);
            }

            if (i1 == 10 || k1 == 8) {
               this.setBlockAndMetadata(world, i1, 5, j1, this.brickBlock, this.brickMeta);
            }
         }
      }

      int[] var16 = new int[]{-6, 6};
      j1 = var16.length;

      for(i1 = 0; i1 < j1; ++i1) {
         k1 = var16[i1];
         int[] var18 = new int[]{-4, 4};
         int var12 = var18.length;

         for(int var13 = 0; var13 < var12; ++var13) {
            int k1 = var18[var13];

            for(int j1 = 1; j1 <= 7; ++j1) {
               this.setBlockAndMetadata(world, k1, j1, k1, this.pillarBlock, this.pillarMeta);
            }

            this.setBlockAndMetadata(world, k1 - 1, 1, k1, this.brickStairBlock, 1);
            this.setBlockAndMetadata(world, k1 + 1, 1, k1, this.brickStairBlock, 0);
            this.setBlockAndMetadata(world, k1, 1, k1 - 1, this.brickStairBlock, 2);
            this.setBlockAndMetadata(world, k1, 1, k1 + 1, this.brickStairBlock, 3);
            this.setBlockAndMetadata(world, k1 - 1, 7, k1, this.brickStairBlock, 5);
            this.setBlockAndMetadata(world, k1 + 1, 7, k1, this.brickStairBlock, 4);
            this.setBlockAndMetadata(world, k1, 7, k1 - 1, this.brickStairBlock, 6);
            this.setBlockAndMetadata(world, k1, 7, k1 + 1, this.brickStairBlock, 7);
            this.setBlockAndMetadata(world, k1 - 1, 4, k1, Blocks.field_150478_aa, 1);
            this.setBlockAndMetadata(world, k1 + 1, 4, k1, Blocks.field_150478_aa, 2);
            this.setBlockAndMetadata(world, k1, 4, k1 - 1, Blocks.field_150478_aa, 4);
            this.setBlockAndMetadata(world, k1, 4, k1 + 1, Blocks.field_150478_aa, 3);
         }
      }

      for(i1 = 0; i1 <= 3; ++i1) {
         j1 = 5 + i1;
         i1 = 11 - i1;
         k1 = 9 - i1;

         for(k2 = -i1; k2 <= i1; ++k2) {
            this.setBlockAndMetadata(world, k2, j1, -k1, this.brick2StairBlock, 2);
            this.setBlockAndMetadata(world, k2, j1, k1, this.brick2StairBlock, 3);
         }

         for(k2 = -k1 + 1; k2 <= k1 - 1; ++k2) {
            this.setBlockAndMetadata(world, -i1, j1, k2, this.brick2StairBlock, 1);
            this.setBlockAndMetadata(world, i1, j1, k2, this.brick2StairBlock, 0);
         }

         if (i1 >= 2) {
            for(k2 = -i1 + 1; k2 <= i1 - 1; ++k2) {
               this.setBlockAndMetadata(world, k2, j1 - 1, -k1, this.brick2StairBlock, 7);
               this.setBlockAndMetadata(world, k2, j1 - 1, k1, this.brick2StairBlock, 6);
            }

            for(k2 = -k1; k2 <= k1; ++k2) {
               this.setBlockAndMetadata(world, -i1, j1 - 1, k2, this.brick2StairBlock, 4);
               this.setBlockAndMetadata(world, i1, j1 - 1, k2, this.brick2StairBlock, 5);
            }
         }
      }

      for(i1 = -7; i1 <= 7; ++i1) {
         for(j1 = -5; j1 <= 5; ++j1) {
            this.setBlockAndMetadata(world, i1, 8, j1, this.brick2Block, this.brick2Meta);
         }
      }

      for(i1 = -9; i1 <= 9; ++i1) {
         for(j1 = -7; j1 <= 7; ++j1) {
            i1 = Math.abs(i1);
            k1 = Math.abs(j1);
            if (i1 == 9 && k1 % 4 == 0 || k1 == 7 && i1 % 4 == 2) {
               for(k2 = 5; k2 <= 6; ++k2) {
                  this.setBlockAndMetadata(world, i1, k2, j1, this.brickBlock, this.brickMeta);
               }
            }
         }
      }

      i1 = 2 + random.nextInt(4);

      for(j1 = 0; j1 < i1; ++j1) {
         LOTREntityNPC man = this.createBather(world);
         this.spawnNPCAndSetHome(man, world, 0, 0, 0, 16);
      }

      return true;
   }
}
