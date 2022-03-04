package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenEasterlingWell extends LOTRWorldGenEasterlingStructure {
   public LOTRWorldGenEasterlingWell(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 2);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      if (this.restrictions) {
         for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
               i2 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, i2, k1)) {
                  return false;
               }
            }
         }
      }

      int wellBottom;
      int j1;
      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            i2 = Math.abs(i1);
            wellBottom = Math.abs(k1);

            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 5; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            if (i2 == 1 && wellBottom == 1) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.logBlock, this.logMeta);

               for(j1 = 2; j1 <= 3; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
               }

               this.setBlockAndMetadata(world, i1, 4, k1, this.plankSlabBlock, this.plankSlabMeta);
            }

            if (i2 == 0 && wellBottom == 1 || wellBottom == 0 && i2 == 1) {
               this.setBlockAndMetadata(world, i1, 4, k1, this.plankSlabBlock, this.plankSlabMeta | 8);
            }

            if (i2 == 0 && wellBottom == 0) {
               this.setBlockAndMetadata(world, i1, 5, k1, this.plankBlock, this.plankMeta);
               this.setBlockAndMetadata(world, i1, 6, k1, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, i1, 7, k1, Blocks.field_150478_aa, 5);

               for(j1 = 3; j1 <= 4; ++j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 0, 0, LOTRMod.gateWoodenCross, 0);
      i1 = 0 + random.nextInt(2);
      k1 = 2 + random.nextInt(4);
      int wellTop = -1;
      wellBottom = wellTop - i1 - k1 - 1;

      for(j1 = wellBottom; j1 <= wellTop; ++j1) {
         for(int i1 = -1; i1 <= 1; ++i1) {
            for(int k1 = -1; k1 <= 1; ++k1) {
               int i2 = Math.abs(i1);
               int k2 = Math.abs(k1);
               if (j1 == wellBottom) {
                  this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
               } else if (i2 == 0 && k2 == 0) {
                  if (j1 <= wellBottom + k1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, Blocks.field_150355_j, 0);
                  } else {
                     this.setAir(world, i1, j1, k1);
                  }
               } else {
                  this.setBlockAndMetadata(world, i1, j1, k1, LOTRMod.dirtPath, 0);
               }
            }
         }
      }

      for(j1 = wellBottom + k1 + 1; j1 <= wellTop; ++j1) {
         this.setBlockAndMetadata(world, 0, j1, 0, Blocks.field_150468_ap, 2);
      }

      return true;
   }
}
