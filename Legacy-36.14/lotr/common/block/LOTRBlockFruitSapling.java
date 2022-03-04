package lotr.common.block;

import java.util.Random;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBlockFruitSapling extends LOTRBlockSaplingBase {
   public LOTRBlockFruitSapling() {
      this.setSaplingNames(new String[]{"apple", "pear", "cherry", "mango"});
   }

   public void growTree(World world, int i, int j, int k, Random random) {
      int meta = world.func_72805_g(i, j, k) & 7;
      WorldGenerator treeGen = null;
      if (meta == 0) {
         treeGen = LOTRTreeType.APPLE.create(true, random);
      } else if (meta == 1) {
         treeGen = LOTRTreeType.PEAR.create(true, random);
      } else if (meta == 2) {
         treeGen = LOTRTreeType.CHERRY.create(true, random);
      } else if (meta == 3) {
         treeGen = LOTRTreeType.MANGO.create(true, random);
      }

      world.func_147465_d(i, j, k, Blocks.field_150350_a, 0, 4);
      if (treeGen != null && !treeGen.func_76484_a(world, random, i, j, k)) {
         world.func_147465_d(i, j, k, this, meta, 4);
      }

   }
}
