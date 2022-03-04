package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class LOTRBlockMorgulShroom extends LOTRBlockMordorPlant {
   public LOTRBlockMorgulShroom() {
      float f = 0.2F;
      this.func_149676_a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
      this.func_149675_a(true);
      this.func_149647_a(LOTRCreativeTabs.tabFood);
   }

   public void func_149674_a(World world, int i, int j, int k, Random random) {
      if (random.nextInt(10) == 0) {
         boolean nearbyWater = false;

         int i1;
         int j1;
         int k1;
         for(i1 = i - 2; i1 <= i + 2 && !nearbyWater; ++i1) {
            for(j1 = j - 2; j1 <= j + 2 && !nearbyWater; ++j1) {
               for(k1 = k - 2; k1 <= k + 2 && !nearbyWater; ++k1) {
                  if (world.func_147439_a(i1, j - 1, k1).func_149688_o() == Material.field_151586_h) {
                     nearbyWater = true;
                  }
               }
            }
         }

         if (nearbyWater) {
            i1 = i - 1 + random.nextInt(3);
            j1 = j - 1 + random.nextInt(3);
            k1 = k - 1 + random.nextInt(3);
            if (world.func_147437_c(i1, j1, k1) && this.func_149718_j(world, i1, j1, k1)) {
               world.func_147449_b(i1, j1, k1, this);
            }
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public String func_149702_O() {
      return this.func_149641_N();
   }
}
