package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenDorwinionGarden extends LOTRWorldGenStructureBase2 {
   public LOTRWorldGenDorwinionGarden(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 7);
      int i1;
      int k1;
      if (this.restrictions) {
         int minHeight = 0;
         int maxHeight = 0;
         int maxEdgeHeight = 0;
         int i1 = -7;

         while(true) {
            if (i1 > 7) {
               this.originY = this.getY(maxEdgeHeight);
               break;
            }

            for(i1 = -7; i1 <= 7; ++i1) {
               k1 = this.getTopBlock(world, i1, i1);
               Block block = this.getBlock(world, i1, k1 - 1, i1);
               if (block != Blocks.field_150349_c) {
                  return false;
               }

               if (k1 < minHeight) {
                  minHeight = k1;
               }

               if (k1 > maxHeight) {
                  maxHeight = k1;
               }

               if (maxHeight - minHeight > 4) {
                  return false;
               }

               if ((Math.abs(i1) == 7 || Math.abs(i1) == 7) && k1 > maxEdgeHeight) {
                  maxEdgeHeight = k1;
               }
            }

            ++i1;
         }
      }

      int r = 25;
      int h = 4;
      int gardenR = 6;
      int leafR = 7;

      for(i1 = -r; i1 <= r; ++i1) {
         for(k1 = -r; k1 <= r; ++k1) {
            int i2 = Math.abs(i1);
            int k2 = Math.abs(k1);
            boolean within = false;

            int dh;
            int j1;
            for(dh = 0; dh >= -h; --dh) {
               j1 = dh + r - 2;
               int d = i2 * i2 + j1 * j1 + k2 * k2;
               if (d < r * r) {
                  boolean grass = !this.isOpaque(world, i1, dh + 1, k1);
                  this.setBlockAndMetadata(world, i1, dh, k1, (Block)(grass ? Blocks.field_150349_c : Blocks.field_150346_d), 0);
                  this.setGrassToDirt(world, i1, dh - 1, k1);
                  within = true;
               }
            }

            if (within) {
               for(dh = -h - 1; !this.isOpaque(world, i1, dh, k1) && this.getY(dh) >= 0; --dh) {
                  this.setBlockAndMetadata(world, i1, dh, k1, Blocks.field_150346_d, 0);
                  this.setGrassToDirt(world, i1, dh - 1, k1);
               }

               dh = i2 * i2 + k2 * k2;
               if (dh < gardenR * gardenR) {
                  this.setBlockAndMetadata(world, i1, 0, k1, LOTRMod.brick5, 2);
               } else if (dh < leafR * leafR) {
                  this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.leaves6, 6);
               } else if (random.nextInt(5) == 0) {
                  j1 = this.getTopBlock(world, i1, k1);
                  this.plantFlower(world, random, i1, j1, k1);
               }

               if (i2 == 6 && k2 == 6) {
                  this.setGrassToDirt(world, i1, 0, k1);
                  this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.brick5, 2);
                  this.setBlockAndMetadata(world, i1, 2, k1, LOTRMod.brick5, 2);
                  this.setBlockAndMetadata(world, i1, 3, k1, LOTRMod.wall3, 10);
                  this.setBlockAndMetadata(world, i1, 4, k1, Blocks.field_150478_aa, 5);
               }

               if (i2 == gardenR && k2 <= 1 || k2 == gardenR && i2 <= 1) {
                  this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150406_ce, 0);
                  this.setAir(world, i1, 1, k1);
               }

               if (i2 == gardenR - 1 && k2 == 2 || k2 == gardenR - 1 && i2 == 2) {
                  this.setGrassToDirt(world, i1, 0, k1);
                  this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.brick5, 2);
                  this.setBlockAndMetadata(world, i1, 2, k1, LOTRMod.slabSingle9, 7);
               }

               if (i2 == 3 && k2 <= 2 || k2 == 3 && i2 <= 2) {
                  this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150406_ce, 0);
               }

               if (i2 == 2 && k2 == 2) {
                  this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.slabSingle9, 7);
               }

               if (i2 == 2 && k2 <= 1 || k2 == 2 && i2 <= 1) {
                  for(j1 = -1; j1 <= 1; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.brick5, 2);
                  }
               }

               if (i2 <= 1 && k2 <= 1) {
                  this.setBlockAndMetadata(world, i1, -2, k1, LOTRMod.brick5, 2);

                  for(j1 = -1; j1 <= 1; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150355_j, 0);
                  }
               }
            }
         }
      }

      return true;
   }
}
