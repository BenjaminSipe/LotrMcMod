package lotr.client.fx;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.world.World;

public class LOTREntityMorgulPortalFX extends EntitySpellParticleFX {
   public LOTREntityMorgulPortalFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
      super(world, d, d1, d2, d3, d4, d5);
      this.field_70552_h = 0.2F;
      this.field_70553_i = 0.8F;
      this.field_70551_j = 0.4F;
      this.field_70544_f = 0.5F + this.field_70146_Z.nextFloat() * 0.5F;
      this.field_70547_e = 20 + this.field_70146_Z.nextInt(20);
      this.field_70159_w = d3;
      this.field_70181_x = d4;
      this.field_70179_y = d5;
   }

   @SideOnly(Side.CLIENT)
   public int func_70070_b(float f) {
      return 15728880;
   }

   public float func_70013_c(float f) {
      return 1.0F;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.field_82339_as = 0.5F + 0.5F * ((float)this.field_70546_d / (float)this.field_70547_e);
      this.field_70159_w *= 1.1D;
      this.field_70179_y *= 1.1D;
   }
}
