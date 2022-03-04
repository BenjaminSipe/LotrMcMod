package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainVillageTree extends LOTRWorldGenTauredainHouse {
   public LOTRWorldGenTauredainVillageTree(boolean flag) {
      super(flag);
   }

   protected int getOffset() {
      return 4;
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
         return false;
      } else {
         int i1;
         int k1;
         int i2;
         for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
               this.layFoundation(world, i1, k1);

               for(i2 = 1; i2 <= 12; ++i2) {
                  this.setAir(world, i1, i2, k1);
               }
            }
         }

         int k2;
         for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
               i2 = Math.abs(i1);
               k2 = Math.abs(k1);
               if (i2 != 3 && k2 != 3) {
                  this.setBlockAndMetadata(world, i1, 1, k1, LOTRMod.mudGrass, 0);
                  if (random.nextInt(2) == 0) {
                     if (random.nextBoolean()) {
                        this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150329_H, 1);
                     } else {
                        this.setBlockAndMetadata(world, i1, 2, k1, Blocks.field_150329_H, 2);
                     }
                  }
               } else if (i2 == 3 && k2 == 3) {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.woodBlock, this.woodMeta);
                  this.setBlockAndMetadata(world, i1, 2, k1, this.woodBlock, this.woodMeta);
               } else {
                  this.setBlockAndMetadata(world, i1, 1, k1, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, i1, 2, k1, this.brickSlabBlock, this.brickSlabMeta);
               }
            }
         }

         int treeX = 0;
         int treeY = 2;
         int treeZ = 0;
         this.setAir(world, treeX, treeY, treeZ);

         for(k2 = 0; k2 < 20; ++k2) {
            LOTRTreeType treeType = null;
            int randomTree = random.nextInt(4);
            if (randomTree == 0 || randomTree == 1) {
               treeType = LOTRTreeType.JUNGLE;
            }

            if (randomTree == 2) {
               treeType = LOTRTreeType.MANGO;
            }

            if (randomTree == 3) {
               treeType = LOTRTreeType.BANANA;
            }

            if (treeType.create(this.notifyChanges, random).func_76484_a(world, random, this.getX(treeX, treeZ), this.getY(treeY), this.getZ(treeX, treeZ))) {
               break;
            }
         }

         return true;
      }
   }
}
