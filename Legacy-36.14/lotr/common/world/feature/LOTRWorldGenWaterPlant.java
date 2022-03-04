package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenWaterPlant extends WorldGenerator {
   private Block plant;

   public LOTRWorldGenWaterPlant(Block block) {
      this.plant = block;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      for(int l = 0; l < 32; ++l) {
         int i1 = i + random.nextInt(4) - random.nextInt(4);
         int k1 = k + random.nextInt(4) - random.nextInt(4);
         if (world.func_147437_c(i1, j, k1) && this.plant.func_149742_c(world, i1, j, k1)) {
            world.func_147465_d(i1, j, k1, this.plant, 0, 2);
         }
      }

      return true;
   }
}
