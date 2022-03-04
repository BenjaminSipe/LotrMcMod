package lotr.common.world.structure2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenSouthronTownTree extends LOTRWorldGenSouthronStructure {
   public LOTRWorldGenSouthronTownTree(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 3);
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

      for(i1 = -2; i1 <= 2; ++i1) {
         for(k1 = -2; k1 <= 2; ++k1) {
            i2 = Math.abs(i1);
            int k2 = Math.abs(k1);

            int j1;
            for(j1 = 1; j1 <= 12; ++j1) {
               this.setAir(world, i1, j1, k1);
            }

            for(j1 = 0; (j1 >= 0 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.stoneBlock, this.stoneMeta);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }

            if (i2 != 2 && k2 != 2) {
               this.setBlockAndMetadata(world, i1, 1, k1, Blocks.field_150349_c, 0);
            } else {
               this.setBlockAndMetadata(world, i1, 1, k1, this.stoneBlock, this.stoneMeta);
               if ((i2 + k2) % 2 == 0) {
                  this.setBlockAndMetadata(world, i1, 2, k1, this.brickSlabBlock, this.brickSlabMeta);
               }
            }
         }
      }

      for(i1 = 0; i1 < 16; ++i1) {
         int i1 = 0;
         int j1 = 2;
         int k1 = 0;
         LOTRTreeType tree = this.getRandomTree(random);
         WorldGenerator treeGen = tree.create(this.notifyChanges, random);
         if (treeGen != null && treeGen.func_76484_a(world, random, this.getX(i1, k1), this.getY(j1), this.getZ(i1, k1))) {
            break;
         }
      }

      for(i1 = -1; i1 <= 1; ++i1) {
         for(k1 = -1; k1 <= 1; ++k1) {
            if ((i1 != 0 || k1 != 0) && this.isAir(world, i1, 2, k1)) {
               this.plantTallGrass(world, random, i1, 2, k1);
            }
         }
      }

      return true;
   }

   private LOTRTreeType getRandomTree(Random random) {
      List treeList = new ArrayList();
      treeList.add(LOTRTreeType.CEDAR);
      treeList.add(LOTRTreeType.CYPRESS);
      treeList.add(LOTRTreeType.PALM);
      treeList.add(LOTRTreeType.DATE_PALM);
      treeList.add(LOTRTreeType.OLIVE);
      return (LOTRTreeType)treeList.get(random.nextInt(treeList.size()));
   }
}
