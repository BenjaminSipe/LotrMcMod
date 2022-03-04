package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenMarshLights extends WorldGenerator {
   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      for(int l = 0; l < 4; ++l) {
         int i1 = i + random.nextInt(8) - random.nextInt(8);
         int k1 = k + random.nextInt(8) - random.nextInt(8);
         if (world.func_147437_c(i1, j, k1) && LOTRMod.marshLights.func_149742_c(world, i1, j, k1)) {
            world.func_147465_d(i1, j, k1, LOTRMod.marshLights, 0, 2);
         }
      }

      return true;
   }
}
