package lotr.client.fx;

import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityChillFX extends EntitySmokeFX {
   public LOTREntityChillFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
      super(world, d, d1, d2, d3, d4, d5);
      float grey = MathHelper.func_151240_a(this.field_70146_Z, 0.8F, 1.0F);
      this.field_70552_h = this.field_70553_i = this.field_70551_j = grey;
      this.func_70536_a(this.field_70146_Z.nextInt(8));
      this.field_70547_e *= 6;
      float blue = this.field_70146_Z.nextFloat() * 0.25F;
      this.field_70552_h *= 1.0F - blue;
      this.field_70553_i *= 1.0F - blue;
   }

   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      ++this.field_70546_d;
      if (this.field_70546_d >= this.field_70547_e) {
         this.func_70106_y();
      }

      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      this.field_70159_w *= 0.96D;
      this.field_70181_x *= 0.96D;
      this.field_70179_y *= 0.96D;
      this.field_70181_x -= 0.005D;
      if (this.field_70122_E) {
         this.field_70159_w *= 0.7D;
         this.field_70179_y *= 0.7D;
      }

   }
}
