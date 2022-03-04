package lotr.common.world.structure2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenGondorTownTrees extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorTownTrees(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 2);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      if (this.restrictions) {
         for(i1 = -6; i1 <= 6; ++i1) {
            for(k1 = -2; k1 <= 2; ++k1) {
               i2 = this.getTopBlock(world, i1, k1) - 1;
               if (!this.isSurface(world, i1, i2, k1)) {
                  return false;
               }
            }
         }
      }

      int k2;
      for(i1 = -6; i1 <= 6; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);

            int j1;
            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            for(j1 = 1; j1 <= 10; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            if (i2 % 4 != 2 && k2 <= 1) {
               this.setBlockAndMetadata(world, i1, 0, k1, Blocks.field_150349_c, 0);
            }

            if (i2 % 4 == 2 && k2 == 2) {
               this.setBlockAndMetadata(world, i1, 1, k1, this.rockWallBlock, this.rockWallMeta);
               this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150478_aa, 5);
            }
         }
      }

      int[] var16 = new int[]{-4, 0, 4};
      k1 = var16.length;

      for(i2 = 0; i2 < k1; ++i2) {
         k2 = var16[i2];
         int j1 = 1;
         int k1 = 0;

         for(int l = 0; l < 16; ++l) {
            LOTRTreeType tree = getRandomTree(random);
            WorldGenerator treeGen = tree.create(this.notifyChanges, random);
            if (treeGen != null && treeGen.func_76484_a(world, random, this.getX(k2, k1), this.getY(j1), this.getZ(k2, k1))) {
               break;
            }
         }
      }

      return true;
   }

   public static LOTRTreeType getRandomTree(Random random) {
      List treeList = new ArrayList();
      treeList.add(LOTRTreeType.CYPRESS);
      return (LOTRTreeType)treeList.get(random.nextInt(treeList.size()));
   }
}
