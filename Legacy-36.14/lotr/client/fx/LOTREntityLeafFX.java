package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class LOTREntityLeafFX extends EntityFX {
   private int[] texIndices;
   private static final int animationRate = 4;

   public LOTREntityLeafFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int[] tex) {
      super(world, d, d1, d2, d3, d4, d5);
      this.field_70159_w = d3;
      this.field_70181_x = d4;
      this.field_70179_y = d5;
      this.field_70544_f = 0.15F + this.field_70146_Z.nextFloat() * 0.5F;
      this.texIndices = tex;
      this.field_70547_e = 600;
   }

   public LOTREntityLeafFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int[] tex, int age) {
      this(world, d, d1, d2, d3, d4, d5, tex);
      this.field_70547_e = age;
   }

   public void func_70071_h_() {
      this.field_70169_q = this.field_70165_t;
      this.field_70167_r = this.field_70163_u;
      this.field_70166_s = this.field_70161_v;
      this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
      ++this.field_70546_d;
      this.func_70536_a(this.texIndices[this.field_70546_d / 4 % this.texIndices.length]);
      if (this.field_70122_E || this.field_70546_d >= this.field_70547_e || this.field_70163_u < 0.0D) {
         this.func_70106_y();
      }

   }

   public int func_70537_b() {
      return 1;
   }

   public void func_70536_a(int i) {
      this.field_94054_b = i % 8;
      this.field_94055_c = i / 8;
   }

   public void func_70539_a(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
      float uMin = (float)this.field_94054_b / 8.0F;
      float uMax = uMin + 0.125F;
      float vMin = (float)this.field_94055_c / 8.0F;
      float vMax = vMin + 0.125F;
      float scale = 0.25F * this.field_70544_f;
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
