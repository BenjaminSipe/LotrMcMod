package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityEntJar;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenEntJars extends WorldGenerator {
   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      for(int l = 0; l < 16; ++l) {
         int i1 = i - random.nextInt(6) + random.nextInt(6);
         int j1 = j - random.nextInt(2) + random.nextInt(2);
         int k1 = k - random.nextInt(6) + random.nextInt(6);
         if (world.func_147439_a(i1, j1 - 1, k1) == Blocks.field_150349_c && !world.func_147439_a(i1, j1, k1).func_149721_r() && world.func_72874_g(i1, k1) == j1 && world.func_72807_a(i1, k1) instanceof LOTRBiomeGenFangorn) {
            world.func_147465_d(i1, j1, k1, LOTRMod.entJar, 0, 2);
            TileEntity tileentity = world.func_147438_o(i1, j1, k1);
            if (tileentity instanceof LOTRTileEntityEntJar) {
               int amount = random.nextInt(LOTRTileEntityEntJar.MAX_CAPACITY + 1);

               for(int l1 = 0; l1 < amount; ++l1) {
                  ((LOTRTileEntityEntJar)tileentity).fillWithWater();
               }
            }
         }
      }

      return true;
   }
}
