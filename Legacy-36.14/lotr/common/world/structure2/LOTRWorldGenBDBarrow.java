package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenBDBarrow extends LOTRWorldGenStructureBase2 {
   private LOTRWorldGenStructureBase2 ruinsGen = new LOTRWorldGenStoneRuin.STONE(3, 3);

   public LOTRWorldGenBDBarrow(boolean flag) {
      super(flag);
      this.ruinsGen.restrictions = false;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      int radius = 12;
      int height = 7;
      int base = -4;
      this.setOriginAndRotation(world, i, j, k, rotation, this.usingPlayer != null ? radius : 0);
      int i1;
      int k1;
      int j1;
      int j1;
      int rY;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(j1 = -radius; j1 <= radius; ++j1) {
            for(j1 = -radius; j1 <= radius; ++j1) {
               rY = this.getTopBlock(world, j1, j1) - 1;
               if (this.getBlock(world, j1, rY, j1) != Blocks.field_150349_c) {
                  return false;
               }

               if (rY < i1) {
                  i1 = rY;
               }

               if (rY > k1) {
                  k1 = rY;
               }
            }
         }

         if (k1 - i1 > 5) {
            return false;
         }
      }

      for(i1 = -radius; i1 <= radius; ++i1) {
         for(k1 = height; k1 >= base; --k1) {
            for(j1 = -radius; j1 <= radius; ++j1) {
               if (i1 * i1 + (k1 - base) * (k1 - base) + j1 * j1 <= radius * radius) {
                  boolean grass = !this.isOpaque(world, i1, k1 + 1, j1);
                  this.setBlockAndMetadata(world, i1, k1, j1, (Block)(grass ? Blocks.field_150349_c : Blocks.field_150346_d), 0);
                  this.setGrassToDirt(world, i1, k1 - 1, j1);
               }
            }
         }
      }

      for(i1 = -radius; i1 <= radius; ++i1) {
         for(k1 = -radius; k1 <= radius; ++k1) {
            for(j1 = base - 1; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
               if (i1 * i1 + k1 * k1 <= radius * radius) {
                  this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }
            }
         }
      }

      int innerR = 5;
      int innerH = 5;
      int innerB = -2;

      int j1;
      int i1;
      for(j1 = -innerR - 1; j1 <= innerR + 1; ++j1) {
         for(rY = -innerR - 1; rY <= innerR + 1; ++rY) {
            for(j1 = innerB + 1; j1 <= innerB + innerH + 1; ++j1) {
               i1 = j1 * j1 + (j1 - innerB - 1) * (j1 - innerB - 1) + rY * rY;
               if (i1 < innerR * innerR) {
                  this.setAir(world, j1, j1, rY);
                  if (i1 > (innerR - 1) * (innerR - 1) && random.nextInt(3) == 0) {
                     this.placeRandomBrick(world, random, j1, j1, rY);
                  }
               } else if (i1 < (innerR + 1) * (innerR + 1)) {
                  this.placeRandomBrick(world, random, j1, j1, rY);
               }
            }

            this.placeRandomBrick(world, random, j1, innerB, rY);
         }
      }

      this.placeSpawnerChest(world, random, 0, innerB + 1, 0, LOTRMod.spawnerChestStone, 0, LOTREntityBarrowWight.class, LOTRChestContents.BARROW_DOWNS);
      this.setBlockAndMetadata(world, 1, innerB + 1, 0, Blocks.field_150446_ar, 0);
      this.setBlockAndMetadata(world, -1, innerB + 1, 0, Blocks.field_150446_ar, 1);
      this.setBlockAndMetadata(world, 0, innerB + 1, -1, Blocks.field_150446_ar, 2);
      this.setBlockAndMetadata(world, 0, innerB + 1, 1, Blocks.field_150446_ar, 3);

      for(j1 = -radius + 2; j1 <= -innerR + 1; ++j1) {
         for(rY = -1; rY <= 1; ++rY) {
            this.placeRandomBrick(world, random, rY, 0, j1);

            for(j1 = 1; j1 <= 3; ++j1) {
               this.setAir(world, rY, j1, j1);
            }

            this.placeRandomBrick(world, random, rY, 4, j1);
         }

         for(rY = 0; rY <= 4; ++rY) {
            this.placeRandomBrick(world, random, -2, rY, j1);
            this.placeRandomBrick(world, random, 2, rY, j1);
         }
      }

      for(j1 = -1; j1 <= 1; ++j1) {
         this.setBlockAndMetadata(world, j1, innerB + 1, -innerR + 1, Blocks.field_150446_ar, 3);

         for(rY = innerB + 2; rY <= 3; ++rY) {
            this.setAir(world, j1, rY, -innerR + 1);
            this.setAir(world, j1, rY, -innerR + 2);
         }

         this.setBlockAndMetadata(world, j1, innerB + 2, -innerR + 0, Blocks.field_150446_ar, 3);
      }

      this.placeRandomBrick(world, random, -2, innerB + 1, -innerR + 1);
      this.placeRandomBrick(world, random, 2, innerB + 1, -innerR + 1);
      int[] var21 = new int[]{-3, 3};
      rY = var21.length;

      for(j1 = 0; j1 < rY; ++j1) {
         i1 = var21[j1];
         this.placeRandomBrick(world, random, i1, 1, -radius + 1);
         this.placeRandomBrick(world, random, i1, 0, -radius + 1);
         this.placeRandomBrick(world, random, i1, -1, -radius + 1);
         this.placeRandomBrick(world, random, i1, 2, -radius + 2);
         this.placeRandomBrick(world, random, i1, 1, -radius + 2);
      }

      for(j1 = -2; j1 <= 2; ++j1) {
         this.placeRandomBrick(world, random, j1, 5, -radius + 4);
         if (Math.abs(j1) <= 1) {
            this.placeRandomBrick(world, random, j1, 5, -radius + 3);
         }
      }

      for(j1 = 1; j1 <= 3; ++j1) {
         this.placeRandomBrick(world, random, -1, j1, -radius + 4);
         this.placeRandomBrick(world, random, 0, j1, -radius + 6);
         this.placeRandomBrick(world, random, 1, j1, -radius + 4);
      }

      int rX = 0;
      rY = height + 1;
      int rZ = 0;
      this.ruinsGen.generateWithSetRotation(world, random, this.getX(rX, rZ), this.getY(rY), this.getZ(rX, rZ), this.getRotationMode());
      return true;
   }

   private void placeRandomBrick(World world, Random random, int i, int j, int k) {
      if (random.nextBoolean()) {
         this.setBlockAndMetadata(world, i, j, k, Blocks.field_150348_b, 0);
      } else {
         int l;
         if (random.nextInt(3) > 0) {
            l = random.nextInt(2);
            if (l == 0) {
               this.setBlockAndMetadata(world, i, j, k, Blocks.field_150347_e, 0);
            }

            if (l == 1) {
               this.setBlockAndMetadata(world, i, j, k, Blocks.field_150341_Y, 0);
            }
         } else {
            l = random.nextInt(3);
            if (l == 0) {
               this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 0);
            }

            if (l == 1) {
               this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 0);
            }

            if (l == 2) {
               this.setBlockAndMetadata(world, i, j, k, Blocks.field_150417_aV, 0);
            }
         }
      }

   }
}
