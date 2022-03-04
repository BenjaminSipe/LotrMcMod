package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTownFlowers extends LOTRWorldGenSouthronStructure {
   public LOTRWorldGenSouthronTownFlowers(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 1);
      this.setupRandomBlocks(random);
      ItemStack flower = this.getRandomFlower(world, random);
      Block flowerBlock = Block.func_149634_a(flower.func_77973_b());
      int flowerMeta = flower.func_77960_j();
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
            for(j1 = 1; j1 <= 4; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.stoneBlock, this.stoneMeta);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            if ((k1 == 0 || k1 == 3) && i2 % 2 == 1) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.brickSlabBlock, this.brickSlabMeta);
            }

            if (k1 >= 1 && k1 <= 2 && i2 <= 2) {
               this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150349_c, 0);
               this.setBlockAndMetadata(world, i1, 1, k1, flowerBlock, flowerMeta);
            }
         }
      }

      return true;
   }
}
