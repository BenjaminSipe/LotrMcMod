package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGondorTownGarden extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorTownGarden(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      if (this.restrictions) {
         for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = 0; k1 <= 3; ++k1) {
               i2 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, i2, k1)) {
                  return false;
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = 0; k1 <= 3; ++k1) {
            i2 = Math.abs(i1);

            int j1;
            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 3; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            if (i2 <= 2 && k1 >= 1 && k1 <= 2) {
               this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150349_c, 0);
            }

            if (i2 == 3 && (k1 == 0 || k1 == 3)) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.rockWallBlock, this.rockWallMeta);
               this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150478_aa, 5);
            }
         }
      }

      for(i1 = 1; i1 <= 2; ++i1) {
         ItemStack flower = this.getRandomFlower(world, random);

         for(i2 = -2; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 1, i1, Block.func_149634_a(flower.func_77973_b()), flower.func_77960_j());
         }
      }

      return true;
   }
}
