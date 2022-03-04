package lotr.client.fx;

import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.world.World;

public class LOTREntityMarshLightFX extends EntityFlameFX {
   public LOTREntityMarshLightFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
      super(world, d, d1, d2, d3, d4, d5);
      this.field_70159_w = d3;
      this.field_70181_x = d4;
      this.field_70179_y = d5;
      this.func_70536_a(49);
      this.field_70547_e = 40 + this.field_70146_Z.nextInt(20);
      this.field_70552_h = this.field_70553_i = this.field_70551_j = 0.75F + this.field_70146_Z.nextFloat() * 0.25F;
   }
}
