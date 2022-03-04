package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenSkullPile extends WorldGenerator {
   public LOTRWorldGenSkullPile() {
      super(false);
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      for(int l = 0; l < 4; ++l) {
         int i1 = i - 4 + random.nextInt(9);
         int k1 = k - 4 + random.nextInt(9);
         int j1 = world.func_72976_f(i1, k1);
         if (world.func_147439_a(i1, j1 - 1, k1).func_149662_c() && world.func_147439_a(i1, j1, k1).isReplaceable(world, i1, j1, k1)) {
            world.func_147465_d(i1, j1, k1, Blocks.field_150465_bP, 1, 2);
            TileEntity tileentity = world.func_147438_o(i1, j1, k1);
            if (tileentity instanceof TileEntitySkull) {
               TileEntitySkull skull = (TileEntitySkull)tileentity;
               skull.func_145903_a(random.nextInt(16));
            }
         }
      }

      return true;
   }
}
