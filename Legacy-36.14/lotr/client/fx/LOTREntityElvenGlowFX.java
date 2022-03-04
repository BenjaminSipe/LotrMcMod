package lotr.client.fx;

import java.awt.Color;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityElvenGlowFX extends EntityFlameFX {
   public LOTREntityElvenGlowFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
      super(world, d, d1, d2, d3, d4, d5);
      this.field_70552_h = 0.15F;
      this.field_70553_i = 0.9F;
      this.field_70551_j = 1.0F;
      this.field_70547_e *= 3;
   }

   public LOTREntityElvenGlowFX setElvenGlowColor(int color) {
      float[] rgb = (new Color(color)).getColorComponents((float[])null);
      this.field_70552_h = rgb[0];
      this.field_70553_i = rgb[1];
      this.field_70551_j = rgb[2];
      return this;
   }

   public void func_70539_a(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
      float minU = 0.25F;
      float maxU = minU + 0.25F;
      float minV = 0.125F;
      float maxV = minV + 0.25F;
      float var12 = 0.25F + 0.002F * MathHelper.func_76126_a(((float)this.field_70546_d + f - 1.0F) * 0.25F * 3.1415927F);
      this.field_82339_as = 0.9F - ((float)this.field_70546_d + f - 1.0F) * 0.02F;
      float var13 = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * (double)f - field_70556_an);
      float var14 = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * (double)f - field_70554_ao);
      float var15 = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * (double)f - field_70555_ap);
      tessellator.func_78369_a(this.field_70552_h, this.field_70553_i, this.field_70551_j, this.field_82339_as);
      tessellator.func_78374_a((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12), (double)maxU, (double)maxV);
      tessellator.func_78374_a((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12), (double)maxU, (double)minV);
      tessellator.func_78374_a((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12), (double)minU, (double)minV);
      tessellator.func_78374_a((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12), (double)minU, (double)maxV);
   }
}
