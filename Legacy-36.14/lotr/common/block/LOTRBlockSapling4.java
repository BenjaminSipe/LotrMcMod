package lotr.common.block;

import java.util.Random;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBlockSapling4 extends LOTRBlockSaplingBase {
   public LOTRBlockSapling4() {
      this.setSaplingNames(new String[]{"chestnut", "baobab", "cedar", "fir"});
   }

   public void incrementGrowth(World world, int i, int j, int k, Random random) {
      int meta = world.func_72805_g(i, j, k) & 7;
      if (meta != 1 || random.nextInt(4) <= 0) {
         super.incrementGrowth(world, i, j, k, random);
      }
   }

   public void growTree(World world, int i, int j, int k, Random random) {
      int meta = world.func_72805_g(i, j, k) & 7;
      WorldGenerator treeGen = null;
      int trunkNeg = 0;
      int trunkPos = 0;
      int xOffset = 0;
      int zOffset = 0;
      if (meta == 0) {
         int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, this, meta);
         if (partyTree != null) {
            treeGen = LOTRTreeType.CHESTNUT_PARTY.create(true, random);
            trunkPos = 1;
            trunkNeg = 1;
            xOffset = partyTree[0];
            zOffset = partyTree[1];
         }

         if (treeGen == null) {
            if (random.nextInt(10) == 0) {
               treeGen = LOTRTreeType.CHESTNUT_LARGE.create(true, random);
            } else {
               treeGen = LOTRTreeType.CHESTNUT.create(true, random);
            }

            trunkPos = 0;
            trunkNeg = 0;
            xOffset = 0;
            zOffset = 0;
         }
      }

      if (meta == 1) {
         treeGen = LOTRTreeType.BAOBAB.create(true, random);
      }

      if (meta == 2) {
         treeGen = LOTRTreeType.CEDAR.create(true, random);
      }

      if (meta == 3) {
         treeGen = LOTRTreeType.FIR.create(true, random);
      }

      int k1;
      int i1;
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
