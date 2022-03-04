package lotr.client.fx;

import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityWaveFX extends EntityExplodeFX {
   private final float initScale;
   private final float finalScale;
   private final double origMotionX;
   private final double origMotionZ;

   public LOTREntityWaveFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
      super(world, d, d1, d2, d3, d4, d5);
      this.origMotionX = this.field_70159_w = d3;
      this.field_70181_x = d4;
      this.origMotionZ = this.field_70179_y = d5;
      this.field_70544_f = 1.0F + this.field_70146_Z.nextFloat() * 4.0F;
      this.initScale = this.field_70544_f;
      this.finalScale = this.initScale * MathHelper.func_151240_a(this.field_70146_Z, 1.2F, 2.0F);
      this.field_70547_e = MathHelper.func_76136_a(this.field_70146_Z, 60, 80);
      this.field_82339_as = 0.0F;
   }

   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_70106_y();
      }

      if (this.field_82339_as < 1.0F) {
         this.field_82339_as += 0.02F;
         this.field_82339_as = Math.min(this.field_82339_as, 1.0F);
      }

      this.field_70544_f = this.initScale + (float)this.field_70546_d / (float)this.field_70547_e * (this.finalScale - this.initScale);
      this.func_70536_a((this.field_70547_e - this.field_70546_d) % 8);
      this.func_70072_I();
      if (this.field_70171_ac) {
         this.field_70181_x += (double)MathHelper.func_151240_a(this.field_70146_Z, 0.04F, 0.1F);
         this.field_70159_w = this.origMotionX;
         this.field_70179_y = this.origMotionZ;
      } else {
         this.field_70181_x -= 0.02D;
         this.field_70159_w *= 0.98D;
         this.field_70179_y *= 0.98D;
      }

      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      this.field_70181_x *= 0.995D;
   }
}
