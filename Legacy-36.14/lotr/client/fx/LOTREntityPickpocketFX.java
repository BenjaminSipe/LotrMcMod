package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class LOTREntityPickpocketFX extends EntityFX {
   protected float bounciness;
   private double motionBeforeGround;

   public LOTREntityPickpocketFX(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      super(world, x, y, z, 0.0D, 0.0D, 0.0D);
      this.field_70159_w = xSpeed;
      this.field_70181_x = ySpeed;
      this.field_70179_y = zSpeed;
      this.field_70545_g = 1.0F;
      this.field_70547_e = 30 + this.field_70146_Z.nextInt(40);
      this.field_70145_X = false;
      this.bounciness = 1.0F;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.updatePickpocketIcon();
      if (!this.field_70122_E) {
         this.motionBeforeGround = this.field_70181_x;
      } else {
         this.field_70181_x = this.motionBeforeGround * (double)(-this.bounciness);
      }

   }

   protected void updatePickpocketIcon() {
      this.func_70536_a(160 + this.field_70546_d / 2 % 8);
   }
}
