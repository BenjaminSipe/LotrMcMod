package lotr.common.block;

import java.util.Random;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBlockSapling2 extends LOTRBlockSaplingBase {
   public LOTRBlockSapling2() {
      this.setSaplingNames(new String[]{"lebethron", "beech", "holly", "banana"});
   }

   public void growTree(World world, int i, int j, int k, Random random) {
      int meta = world.func_72805_g(i, j, k) & 7;
      WorldGenerator treeGen = null;
      int trunkNeg = 0;
      int trunkPos = 0;
      int xOffset = 0;
      int zOffset = 0;
      int[] partyTree;
      int k1;
      int i1;
      if (meta == 0) {
         partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
         if (partyTree != null) {
            treeGen = LOTRTreeType.LEBETHRON_PARTY.create(true, random);
            trunkPos = 1;
            trunkNeg = 1;
            xOffset = partyTree[0];
            zOffset = partyTree[1];
         }

         if (treeGen == null) {
            if (random.nextInt(10) == 0) {
               treeGen = LOTRTreeType.LEBETHRON_LARGE.create(true, random);
            } else {
               treeGen = LOTRTreeType.LEBETHRON.create(true, random);
            }

            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
         }
      } else if (meta == 1) {
         partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
         if (partyTree != null) {
            treeGen = LOTRTreeType.BEECH_PARTY.create(true, random);
            trunkPos = 1;
            trunkNeg = 1;
            xOffset = partyTree[0];
            zOffset = partyTree[1];
         }

         if (treeGen == null) {
            if (random.nextInt(10) == 0) {
               treeGen = LOTRTreeType.BEECH_LARGE.create(true, random);
            } else {
               treeGen = LOTRTreeType.BEECH.create(true, random);
            }

            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
         }
      } else if (meta == 2) {
         for(i1 = 0; i1 >= -1; --i1) {
            for(k1 = 0; k1 >= -1; --k1) {
               if (this.isSameSapling(world, i + i1, j, k + k1, meta) && this.isSameSapling(world, i + i1 + 1, j, k + k1, meta) && this.isSameSapling(world, i + i1, j, k + k1 + 1, meta) && this.isSameSapling(world, i + i1 + 1, j, k + k1 + 1, meta)) {
                  treeGen = LOTRTreeType.HOLLY_LARGE.create(true, random);
                  trunkNeg = 0;
                  trunkPos = 1;
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
            treeGen = LOTRTreeType.HOLLY.create(true, random);
         }
      } else if (meta == 3) {
         treeGen = LOTRTreeType.BANANA.create(true, random);
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
