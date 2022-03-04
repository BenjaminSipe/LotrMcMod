package lotr.client.fx;

import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityWhiteSmokeFX extends EntitySmokeFX {
   public LOTREntityWhiteSmokeFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
      super(world, d, d1, d2, d3, d4, d5);
      float grey = MathHelper.func_151240_a(this.field_70146_Z, 0.6F, 1.0F);
      this.field_70552_h = this.field_70553_i = this.field_70551_j = grey;
   }
}
