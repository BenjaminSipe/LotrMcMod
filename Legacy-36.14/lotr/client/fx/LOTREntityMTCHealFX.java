package lotr.client.fx;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.world.World;

public class LOTREntityMTCHealFX extends EntitySpellParticleFX {
   private int baseTextureIndex;

   public LOTREntityMTCHealFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
      super(world, d, d1, d2, d3, d4, d5);
      this.field_70552_h = 1.0F;
      this.field_70553_i = 0.3F;
      this.field_70551_j = 0.3F;
      this.field_70544_f *= 3.0F;
      this.field_70547_e = 30;
      this.field_70159_w = d3;
      this.field_70181_x = d4;
      this.field_70179_y = d5;
      this.field_70155_l = 10.0D;
      this.field_70145_X = true;
      this.func_70589_b(128);
   }

   @SideOnly(Side.CLIENT)
   public int func_70070_b(float f) {
      return 15728880;
   }

   public float func_70013_c(float f) {
      return 1.0F;
   }

   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_70106_y();
      }

      this.func_70536_a(this.baseTextureIndex + (7 - this.field_70546_d * 8 / this.field_70547_e));
      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      this.field_82339_as = 0.5F + 0.5F * ((float)this.field_70546_d / (float)this.field_70547_e);
   }

   public void func_70589_b(int i) {
      super.func_70589_b(i);
      this.baseTextureIndex = i;
   }
}
