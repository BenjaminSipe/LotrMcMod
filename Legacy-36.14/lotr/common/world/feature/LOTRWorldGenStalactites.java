package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenStalactites extends WorldGenerator {
   private Block stalactiteBlock;

   public LOTRWorldGenStalactites() {
      this(LOTRMod.stalactite);
   }

   public LOTRWorldGenStalactites(Block block) {
      this.stalactiteBlock = block;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      for(int l = 0; l < 64; ++l) {
         int i1 = i - random.nextInt(8) + random.nextInt(8);
         int j1 = j - random.nextInt(4) + random.nextInt(4);
         int k1 = k - random.nextInt(8) + random.nextInt(8);
         if (world.func_147437_c(i1, j1, k1)) {
            Block above = world.func_147439_a(i1, j1 + 1, k1);
            Block below = world.func_147439_a(i1, j1 - 1, k1);
            if (above.func_149662_c() && above.func_149688_o() == Material.field_151576_e) {
               world.func_147465_d(i1, j1, k1, this.stalactiteBlock, 0, 2);
            } else if (below.func_149662_c() && below.func_149688_o() == Material.field_151576_e) {
               world.func_147465_d(i1, j1, k1, this.stalactiteBlock, 1, 2);
            }
         }
      }

      return true;
   }
}
