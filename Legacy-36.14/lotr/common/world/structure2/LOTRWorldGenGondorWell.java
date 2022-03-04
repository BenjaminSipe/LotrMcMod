package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorWell extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorWell(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int k2;
      int d;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -3; i2 <= 3; ++i2) {
            for(k2 = -3; k2 <= 3; ++k2) {
               d = this.getTopBlock(world, i2, k2) - 1;
               if (!this.isSurface(world, i2, d, k2)) {
                  return false;
               }

               if (d < i1) {
                  i1 = d;
               }

               if (d > k1) {
                  k1 = d;
               }

               if (k1 - i1 > 8) {
                  return false;
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);

            for(d = 0; (d >= 0 || !this.isOpaque(world, i1, d, k1)) && this.getY(d) >= 0; --d) {
               this.setBlockAndMetadata(world, i1, d, k1, this.rockBlock, this.rockMeta);
               this.setGrassToDirt(world, i1, d - 1, k1);
            }

            for(d = 1; d <= 6; ++d) {
               this.setAir(world, i1, d, k1);
            }

            if (i2 == 2 && k2 == 2) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.rockBlock, this.rockMeta);
               this.setBlockAndMetadata(world, i1, 2, k1, this.rockBlock, this.rockMeta);
               this.setBlockAndMetadata(world, i1, 3, k1, this.rockWallBlock, this.rockWallMeta);
               this.setBlockAndMetadata(world, i1, 4, k1, this.rockBlock, this.rockMeta);
               this.setBlockAndMetadata(world, i1, 5, k1, this.rockSlabBlock, this.rockSlabMeta);
            }

            if (i2 <= 2 && k2 <= 2) {
               d = i2 + k2;
               if (d == 3) {
                  this.setBlockAndMetadata(world, i1, 4, k1, this.rockSlabBlock, this.rockSlabMeta | 8);
                  this.setBlockAndMetadata(world, i1, 5, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
               }

               if (d == 2) {
                  this.setBlockAndMetadata(world, i1, 5, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                  this.setBlockAndMetadata(world, i1, 6, k1, this.rockSlabBlock, this.rockSlabMeta);
               }

               if (d == 1) {
                  this.setBlockAndMetadata(world, i1, 5, k1, this.rockSlabBlock, this.rockSlabMeta | 8);
                  this.setBlockAndMetadata(world, i1, 6, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
               }

               if (d == 0) {
                  this.setBlockAndMetadata(world, i1, 6, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                  this.setBlockAndMetadata(world, i1, 7, k1, this.rockSlabBlock, this.rockSlabMeta);
               }
            }

            if (i2 == 2 && k2 <= 1 || k2 == 2 && i2 <= 1) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.fenceBlock, this.fenceMeta);
               this.setBlockAndMetadata(world, i1, 0, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
            }
         }
      }

      i1 = 1 + random.nextInt(4);
      k1 = i1 + 1 + random.nextInt(3);

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = 0 - i2;
         boolean watery = i2 >= k1 - i1;

         for(int i1 = -1; i1 <= 1; ++i1) {
            for(int k1 = -1; k1 <= 1; ++k1) {
               if (watery) {
                  this.setBlockAndMetadata(world, i1, k2, k1, Blocks.field_150355_j, 0);
               } else {
                  this.setAir(world, i1, k2, k1);
               }
            }
         }

         if (!watery) {
            this.setBlockAndMetadata(world, 0, k2, -1, Blocks.field_150468_ap, 3);
            this.setBlockAndMetadata(world, 0, k2, 1, Blocks.field_150468_ap, 2);
            this.setBlockAndMetadata(world, -1, k2, 0, Blocks.field_150468_ap, 4);
            this.setBlockAndMetadata(world, 1, k2, 0, Blocks.field_150468_ap, 5);
         }
      }

      this.setBlockAndMetadata(world, 0, 1, -2, this.fenceGateBlock, 0);
      this.setBlockAndMetadata(world, 0, 1, 2, this.fenceGateBlock, 2);
      this.setBlockAndMetadata(world, -2, 1, 0, this.fenceGateBlock, 1);
      this.setBlockAndMetadata(world, 2, 1, 0, this.fenceGateBlock, 3);

      for(i2 = 4; i2 <= 5; ++i2) {
         this.setBlockAndMetadata(world, 0, i2, 0, this.fenceBlock, this.fenceMeta);
      }

      this.setBlockAndMetadata(world, -3, 5, 0, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 3, 5, 0, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 0, 5, -3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 5, 3, Blocks.field_150478_aa, 3);
      return true;
   }
}
