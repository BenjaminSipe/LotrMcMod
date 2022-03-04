package lotr.common.world.feature;

import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenWebOfUngoliant extends WorldGenerator {
   private int attempts;

   public LOTRWorldGenWebOfUngoliant(boolean flag, int i) {
      super(flag);
      this.attempts = i;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      for(int l = 0; l < this.attempts; ++l) {
         int i1 = i - random.nextInt(8) + random.nextInt(8);
         int j1 = j - random.nextInt(6) + random.nextInt(6);
         int k1 = k - random.nextInt(8) + random.nextInt(8);
         if (world.func_147437_c(i1, j1, k1)) {
            boolean flag = false;
            if (this.isSuitableBlock(world, i1 - 1, j1, k1)) {
               flag = true;
            }

            if (this.isSuitableBlock(world, i1 + 1, j1, k1)) {
               flag = true;
            }

            if (this.isSuitableBlock(world, i1, j1 - 1, k1)) {
               flag = true;
            }

            if (this.isSuitableBlock(world, i1, j1 + 1, k1)) {
               flag = true;
            }

            if (this.isSuitableBlock(world, i1, j1, k1 - 1)) {
               flag = true;
            }

            if (this.isSuitableBlock(world, i1, j1, k1 + 1)) {
               flag = true;
            }

            if (flag) {
               this.func_150516_a(world, i1, j1, k1, LOTRMod.webUngoliant, 0);
            }
         }
      }

      return true;
   }

   private boolean isSuitableBlock(World world, int i, int j, int k) {
      return world.func_147439_a(i, j, k).func_149721_r();
   }
}
