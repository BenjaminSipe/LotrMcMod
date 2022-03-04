package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRohanVillageGarden extends LOTRWorldGenRohanStructure {
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenRohanVillageGarden(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.leafBlock = Blocks.field_150362_t;
      this.leafMeta = 4;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 2);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      if (this.restrictions) {
         for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -1; k1 <= 1; ++k1) {
               i2 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, i2, k1)) {
                  return false;
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            i2 = Math.abs(i1);
            int k2 = Math.abs(k1);
            int j1 = 5;

            boolean foundSurface;
            for(foundSurface = false; j1 >= -5; --j1) {
               if (this.isSurface(world, i1, j1 - 1, k1)) {
                  foundSurface = true;
                  break;
               }
            }

            if (foundSurface) {
               if (i2 <= 2) {
                  if (random.nextInt(3) == 0) {
                     this.plantFlower(world, random, i1, j1, k1);
                  } else {
                     int j2 = j1;
                     if (random.nextInt(5) == 0) {
                        j2 = j1 + 1;
                     }

                     for(int j3 = j1; j3 <= j2; ++j3) {
                        this.setBlockAndMetadata(world, i1, j3, k1, this.leafBlock, this.leafMeta);
                     }
                  }
               }

               if (i2 == 3) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.fenceBlock, this.fenceMeta);
               }
            }
         }
      }

      return true;
   }
}
