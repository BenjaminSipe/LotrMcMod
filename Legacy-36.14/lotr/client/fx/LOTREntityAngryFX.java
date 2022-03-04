package lotr.client.fx;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.util.LOTRFunctions;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityAngryFX extends EntityFX {
   private float angryScale;

   public LOTREntityAngryFX(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      super(world, x, y, z, 0.0D, 0.0D, 0.0D);
      this.field_70159_w = xSpeed;
      this.field_70181_x = ySpeed;
      this.field_70179_y = zSpeed;
      this.angryScale = this.field_70544_f = 7.5F;
      this.field_70547_e = 40 + this.field_70146_Z.nextInt(20);
      this.field_70545_g = 0.0F;
      this.field_70145_X = false;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      this.func_70536_a(12 + this.field_70546_d / 4 % 4);
      float fade = 0.8F;
      float ageF = (float)this.field_70546_d / (float)this.field_70547_e;
      if (ageF >= fade) {
         this.field_82339_as = 1.0F - (ageF - fade) / (1.0F - fade);
         if (this.field_82339_as <= 0.0F) {
            this.func_70106_y();
         }
      }

   }

   public void func_70536_a(int i) {
      this.field_94054_b = i % 4;
      this.field_94055_c = i / 4;
   }

   @SideOnly(Side.CLIENT)
   public int func_70070_b(float f) {
      return 15728880;
   }

   public float func_70013_c(float f) {
      return 1.0F;
   }

   public void func_70539_a(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
      float modScale = LOTRFunctions.normalisedCos(((float)this.field_70546_d + f) / 12.0F);
      modScale = MathHelper.func_76131_a(modScale, 0.0F, 1.0F);
      this.field_70544_f = this.angryScale * (0.7F + modScale * 0.3F);
      float uMin = (float)this.field_94054_b / 4.0F;
      float uMax = uMin + 0.25F;
      float vMin = (float)this.field_94055_c / 4.0F;
      float vMax = vMin + 0.25F;
      float scale = 0.125F * this.field_70544_f;
      float x = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * (double)f - field_70556_an);
      float y = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * (double)f - field_70554_ao);
      float z = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * (double)f - field_70555_ap);
      tessellator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as);
      tessellator.func_78374_a((double)(x - f1 * scale - f4 * scale), (double)(y - f2 * scale), (double)(z - f3 * scale - f5 * scale), (double)uMax, (double)vMax);
      tessellator.func_78374_a((double)(x - f1 * scale + f4 * scale), (double)(y + f2 * scale), (double)(z - f3 * scale + f5 * scale), (double)uMax, (double)vMin);
      tessellator.func_78374_a((double)(x + f1 * scale + f4 * scale), (double)(y + f2 * scale), (double)(z + f3 * scale + f5 * scale), (double)uMin, (double)vMin);
      tessellator.func_78374_a((double)(x + f1 * scale - f4 * scale), (double)(y - f2 * scale), (double)(z + f3 * scale - f5 * scale), (double)uMin, (double)vMax);
   }
}
