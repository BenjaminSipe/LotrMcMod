package lotr.common.block;

import java.util.Random;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBlockSapling7 extends LOTRBlockSaplingBase {
   public LOTRBlockSapling7() {
      this.setSaplingNames(new String[]{"aspen", "greenOak", "lairelosse", "almond"});
   }

   public void growTree(World world, int i, int j, int k, Random random) {
      int meta = world.func_72805_g(i, j, k) & 7;
      WorldGenerator treeGen = null;
      int trunkNeg = 0;
      int trunkPos = 0;
      int xOffset = 0;
      int zOffset = 0;
      int i1;
      int k1;
      if (meta == 0) {
         label120:
         for(i1 = 0; i1 >= -1; --i1) {
            for(k1 = 0; k1 >= -1; --k1) {
               if (this.isSameSapling(world, i + i1, j, k + k1, meta) && this.isSameSapling(world, i + i1 + 1, j, k + k1, meta) && this.isSameSapling(world, i + i1, j, k + k1 + 1, meta) && this.isSameSapling(world, i + i1 + 1, j, k + k1 + 1, meta)) {
                  treeGen = LOTRTreeType.ASPEN_LARGE.create(true, random);
                  trunkNeg = 0;
                  trunkPos = 1;
                  xOffset = i1;
                  zOffset = k1;
                  break label120;
               }
            }
         }

         if (treeGen == null) {
            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
            treeGen = LOTRTreeType.ASPEN.create(true, random);
         }
      }

      if (meta == 1) {
         int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
         if (partyTree != null) {
            treeGen = LOTRTreeType.GREEN_OAK_LARGE.create(true, random);
            trunkPos = 1;
            trunkNeg = 1;
            xOffset = partyTree[0];
            zOffset = partyTree[1];
         }

         if (treeGen == null) {
            treeGen = LOTRTreeType.GREEN_OAK.create(true, random);
            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
         }
      }

      if (meta == 2) {
         label100:
         for(i1 = 0; i1 >= -1; --i1) {
            for(k1 = 0; k1 >= -1; --k1) {
               if (this.isSameSapling(world, i + i1, j, k + k1, meta) && this.isSameSapling(world, i + i1 + 1, j, k + k1, meta) && this.isSameSapling(world, i + i1, j, k + k1 + 1, meta) && this.isSameSapling(world, i + i1 + 1, j, k + k1 + 1, meta)) {
                  treeGen = LOTRTreeType.LAIRELOSSE_LARGE.create(true, random);
                  trunkNeg = 0;
                  trunkPos = 1;
                  xOffset = i1;
                  zOffset = k1;
                  break label100;
               }
            }
         }

         if (treeGen == null) {
            xOffset = 0;
            zOffset = 0;
            treeGen = LOTRTreeType.LAIRELOSSE.create(true, random);
         }
      }

      if (meta == 3) {
         treeGen = LOTRTreeType.ALMOND.create(true, random);
      }

      for(i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
         for(k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
            world.func_147465_d(i + xOffset + i1, j, k + zOffset + k1, Blocks.field_150350_a, 0, 4);
         }
      }

      if (treeGen != null && !treeGen.func_76484_a(world, random, i + xOffset, j, k + zOffset)) {
         for(i1 = -trunkNeg; i1 <= trunkPos; ++i1) {
            for(k1 = -trunkNeg; k1 <= trunkPos; ++k1) {
               world.func_147465_d(i + xOffset + i1, j, k + zOffset + k1, this, meta, 4);
            }
         }
      }

   }
}
