package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRBlockPipeweedPlant extends LOTRBlockFlower {
   public LOTRBlockPipeweedPlant() {
      this.setFlowerBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
   }

   @SideOnly(Side.CLIENT)
   public void func_149734_b(World world, int i, int j, int k, Random random) {
      if (random.nextInt(4) == 0) {
         double d = (double)((float)i + MathHelper.func_151240_a(random, 0.1F, 0.9F));
         double d1 = (double)((float)j + MathHelper.func_151240_a(random, 0.5F, 0.75F));
         double d2 = (double)((float)k + MathHelper.func_151240_a(random, 0.1F, 0.9F));
         world.func_72869_a("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
      }

   }
}
