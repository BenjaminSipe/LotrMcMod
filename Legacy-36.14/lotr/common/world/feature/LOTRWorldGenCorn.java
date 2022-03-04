package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenCorn extends WorldGenerator {
   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      for(int l = 0; l < 20; ++l) {
         int i1 = i + random.nextInt(4) - random.nextInt(4);
         int j1 = j;
         int k1 = k + random.nextInt(4) - random.nextInt(4);
         Block replace = world.func_147439_a(i1, j, k1);
         if (replace.isReplaceable(world, i1, j, k1) && !replace.func_149688_o().func_76224_d()) {
            boolean adjWater = false;

            int cornHeight;
            int j2;
            label50:
            for(cornHeight = -1; cornHeight <= 1; ++cornHeight) {
               for(j2 = -1; j2 <= 1; ++j2) {
                  if (Math.abs(cornHeight) + Math.abs(j2) == 1 && world.func_147439_a(i1 + cornHeight, j - 1, k1 + j2).func_149688_o() == Material.field_151586_h) {
                     adjWater = true;
                     break label50;
                  }
               }
            }

            if (adjWater) {
               cornHeight = 2 + random.nextInt(2);

               for(j2 = 0; j2 < cornHeight; ++j2) {
                  if (LOTRMod.cornStalk.func_149718_j(world, i1, j1 + j2, k1)) {
                     world.func_147465_d(i1, j1 + j2, k1, LOTRMod.cornStalk, 0, 2);
                  }
               }
            }
         }
      }

      return true;
   }
}
