package lotr.client.fx;

import java.awt.Color;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityRiverWaterFX extends EntitySpellParticleFX {
   public LOTREntityRiverWaterFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int color) {
      super(world, d, d1, d2, d3, d4, d5);
      Color c = new Color(color);
      float[] rgb = c.getColorComponents((float[])null);
      this.field_70552_h = MathHelper.func_151240_a(this.field_70146_Z, rgb[0] - 0.3F, rgb[0] + 0.3F);
      this.field_70553_i = MathHelper.func_151240_a(this.field_70146_Z, rgb[1] - 0.3F, rgb[1] + 0.3F);
      this.field_70551_j = MathHelper.func_151240_a(this.field_70146_Z, rgb[2] - 0.3F, rgb[2] + 0.3F);
      this.field_70552_h = MathHelper.func_76131_a(this.field_70552_h, 0.0F, 1.0F);
      this.field_70553_i = MathHelper.func_76131_a(this.field_70553_i, 0.0F, 1.0F);
      this.field_70551_j = MathHelper.func_76131_a(this.field_70551_j, 0.0F, 1.0F);
      this.field_70544_f = 0.5F + this.field_70146_Z.nextFloat() * 0.5F;
      this.field_70547_e = 20 + this.field_70146_Z.nextInt(20);
      this.field_70159_w = d3;
      this.field_70181_x = d4;
      this.field_70179_y = d5;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.field_82339_as = 0.5F + 0.5F * ((float)this.field_70546_d / (float)this.field_70547_e);
   }
}
