package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRWorldGenYams extends WorldGenerator {
   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      for(int l = 0; l < 64; ++l) {
         int i1 = i + random.nextInt(8) - random.nextInt(8);
         int j1 = j + random.nextInt(4) - random.nextInt(4);
         int k1 = k + random.nextInt(8) - random.nextInt(8);
         Block block = LOTRMod.yamCrop;
         int meta = 8;
         if (world.func_147439_a(i1, j1 - 1, k1).canSustainPlant(world, i1, j1 - 1, k1, ForgeDirection.UP, Blocks.field_150329_H) && world.func_147437_c(i1, j1, k1)) {
            world.func_147465_d(i1, j1, k1, block, meta, 2);
         }
      }

      return true;
   }
}
