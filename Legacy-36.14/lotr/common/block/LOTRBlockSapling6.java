package lotr.common.block;

import java.util.Random;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBlockSapling6 extends LOTRBlockSaplingBase {
   public LOTRBlockSapling6() {
      this.setSaplingNames(new String[]{"mahogany", "willow", "cypress", "olive"});
   }

   public void growTree(World world, int i, int j, int k, Random random) {
      int meta = world.func_72805_g(i, j, k) & 7;
      WorldGenerator treeGen = null;
      int extraTrunkWidth = 0;
      int xOffset = 0;
      int zOffset = 0;
      int i1;
      int k1;
      if (meta == 0) {
         treeGen = LOTRTreeType.MAHOGANY.create(true, random);
      } else if (meta == 1) {
         treeGen = LOTRTreeType.WILLOW.create(true, random);
      } else if (meta == 2) {
         for(i1 = 0; i1 >= -1; --i1) {
            for(k1 = 0; k1 >= -1; --k1) {
               if (this.isSameSapling(world, i + i1, j, k + k1, meta) && this.isSameSapling(world, i + i1 + 1, j, k + k1, meta) && this.isSameSapling(world, i + i1, j, k + k1 + 1, meta) && this.isSameSapling(world, i + i1 + 1, j, k + k1 + 1, meta)) {
                  treeGen = LOTRTreeType.CYPRESS_LARGE.create(true, random);
                  extraTrunkWidth = 1;
                  xOffset = i1;
                  zOffset = k1;
                  break;
               }
            }

            if (treeGen != null) {
               break;
            }
         }

         if (treeGen == null) {
            xOffset = 0;
            zOffset = 0;
            treeGen = LOTRTreeType.CYPRESS.create(true, random);
         }
      } else if (meta == 3) {
         for(i1 = 0; i1 >= -1; --i1) {
            for(k1 = 0; k1 >= -1; --k1) {
               if (this.isSameSapling(world, i + i1, j, k + k1, meta) && this.isSameSapling(world, i + i1 + 1, j, k + k1, meta) && this.isSameSapling(world, i + i1, j, k + k1 + 1, meta) && this.isSameSapling(world, i + i1 + 1, j, k + k1 + 1, meta)) {
                  treeGen = LOTRTreeType.OLIVE_LARGE.create(true, random);
                  extraTrunkWidth = 1;
                  xOffset = i1;
                  zOffset = k1;
                  break;
               }
            }

            if (treeGen != null) {
               break;
            }
         }

         if (treeGen == null) {
            xOffset = 0;
            zOffset = 0;
            treeGen = LOTRTreeType.OLIVE.create(true, random);
         }
      }

      for(i1 = 0; i1 <= extraTrunkWidth; ++i1) {
         for(k1 = 0; k1 <= extraTrunkWidth; ++k1) {
            world.func_147465_d(i + xOffset + i1, j, k + zOffset + k1, Blocks.field_150350_a, 0, 4);
         }
      }

      if (treeGen != null && !treeGen.func_76484_a(world, random, i + xOffset, j, k + zOffset)) {
         for(i1 = 0; i1 <= extraTrunkWidth; ++i1) {
            for(k1 = 0; k1 <= extraTrunkWidth; ++k1) {
               world.func_147465_d(i + xOffset + i1, j, k + zOffset + k1, this, meta, 4);
            }
         }
      }

   }
}
