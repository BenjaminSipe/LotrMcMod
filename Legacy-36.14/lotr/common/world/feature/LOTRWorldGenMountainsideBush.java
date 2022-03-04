package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenMountainsideBush extends WorldGenerator {
   private Block leafBlock;
   private int leafMeta;

   public LOTRWorldGenMountainsideBush(Block block, int meta) {
      this.leafBlock = block;
      this.leafMeta = meta;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      for(int l = 0; l < 64; ++l) {
         int i1 = i + MathHelper.func_76136_a(random, -2, 2);
         int j1 = j + MathHelper.func_76136_a(random, -2, 2);
         int k1 = k + MathHelper.func_76136_a(random, -2, 2);
         if (world.func_147437_c(i1, j1, k1) && (this.isStone(world, i1 - 1, j1, k1) || this.isStone(world, i1 + 1, j1, k1) || this.isStone(world, i1, j1, k1 - 1) || this.isStone(world, i1, j1, k1 + 1))) {
            world.func_147465_d(i1, j1, k1, this.leafBlock, this.leafMeta | 4, 2);
         }
      }

      return true;
   }

   private boolean isStone(World world, int i, int j, int k) {
      return world.func_147439_a(i, j, k).func_149688_o() == Material.field_151576_e;
   }
}
