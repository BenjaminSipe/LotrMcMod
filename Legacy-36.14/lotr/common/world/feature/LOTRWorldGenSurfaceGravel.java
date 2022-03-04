package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenSurfaceGravel extends WorldGenerator {
   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      int r = MathHelper.func_76136_a(random, 2, 8);
      int chance = MathHelper.func_76136_a(random, 3, 9);
      Block surfBlock;
      byte surfMeta;
      if (random.nextBoolean()) {
         surfBlock = Blocks.field_150351_n;
         surfMeta = 0;
      } else {
         surfBlock = Blocks.field_150346_d;
         surfMeta = 1;
      }

      for(int i1 = -r; i1 <= r; ++i1) {
         for(int k1 = -r; k1 <= r; ++k1) {
            int i2 = i + i1;
            int k2 = k + k1;
            int d = i1 * i1 + k1 * k1;
            if (d < r * r && random.nextInt(chance) == 0) {
               int j1 = world.func_72825_h(i2, k2) - 1;
               Block block = world.func_147439_a(i2, j1, k2);
               Material mt = block.func_149688_o();
               if (block.func_149662_c() && (mt == Material.field_151578_c || mt == Material.field_151577_b)) {
                  world.func_147465_d(i2, j1, k2, surfBlock, surfMeta, 2);
               }
            }
         }
      }

      return true;
   }
}
