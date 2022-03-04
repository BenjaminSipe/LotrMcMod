package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.npc.LOTREntityElf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelBiped extends ModelBiped {
   private boolean setup = false;
   private float base_bodyRotateX;
   private float base_armX;
   private float base_armY;
   private float base_armZ;
   private float base_legY;
   private float base_legZ;
   private float base_headY;
   private float base_headZ;
   private float base_bodyY;
   private float base_bodyZ;

   public LOTRModelBiped() {
   }

   public LOTRModelBiped(float f) {
      super(f);
   }

   public LOTRModelBiped(float f, float f1, int width, int height) {
      super(f, f1, width, height);
   }

   private void setupModelBiped() {
      this.base_bodyRotateX = this.field_78115_e.field_78795_f;
      this.base_armX = Math.abs(this.field_78112_f.field_78800_c);
      this.base_armY = this.field_78112_f.field_78797_d;
      this.base_armZ = this.field_78112_f.field_78798_e;
      this.base_legY = this.field_78123_h.field_78797_d;
      this.base_legZ = this.field_78123_h.field_78798_e;
      this.base_headY = this.field_78116_c.field_78797_d;
      this.base_headZ = this.field_78116_c.field_78798_e;
      this.base_bodyY = this.field_78115_e.field_78797_d;
      this.base_bodyZ = this.field_78115_e.field_78798_e;
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      if (!this.setup) {
         this.setupModelBiped();
         this.setup = true;
      }

      this.field_78116_c.field_78796_g = f3 / 57.295776F;
      this.field_78116_c.field_78795_f = f4 / 57.295776F;
      this.field_78114_d.field_78796_g = this.field_78116_c.field_78796_g;
      this.field_78114_d.field_78795_f = this.field_78116_c.field_78795_f;
      this.field_78112_f.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
      this.field_78113_g.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 2.0F * f1 * 0.5F;
      this.field_78112_f.field_78808_h = 0.0F;
      this.field_78113_g.field_78808_h = 0.0F;
      this.field_78123_h.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
      this.field_78124_i.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
      if (entity instanceof LOTREntityNPC) {
         this.field_78123_h.field_78796_g = (float)Math.toRadians(5.0D);
         this.field_78124_i.field_78796_g = (float)Math.toRadians(-5.0D);
      }

      ModelRenderer var10000;
      if (this.field_78093_q) {
         var10000 = this.field_78112_f;
         var10000.field_78795_f += -0.62831855F;
         var10000 = this.field_78113_g;
         var10000.field_78795_f += -0.62831855F;
         this.field_78123_h.field_78795_f = -1.2566371F;
         this.field_78124_i.field_78795_f = -1.2566371F;
         this.field_78123_h.field_78796_g = 0.31415927F;
         this.field_78124_i.field_78796_g = -0.31415927F;
      }

      if (this.field_78119_l != 0) {
         this.field_78113_g.field_78795_f = this.field_78113_g.field_78795_f * 0.5F - 0.31415927F * (float)this.field_78119_l;
      }

      if (this.field_78120_m != 0) {
         this.field_78112_f.field_78795_f = this.field_78112_f.field_78795_f * 0.5F - 0.31415927F * (float)this.field_78120_m;
      }

      this.field_78112_f.field_78796_g = 0.0F;
      this.field_78113_g.field_78796_g = 0.0F;
      float f6;
      float bowAmount;
      float bowAmountRad;
      if (this.field_78095_p > -9990.0F) {
         f6 = this.field_78095_p;
         this.field_78115_e.field_78796_g = MathHelper.func_76126_a(MathHelper.func_76129_c(f6) * 3.1415927F * 2.0F) * 0.2F;
         this.field_78112_f.field_78798_e = MathHelper.func_76126_a(this.field_78115_e.field_78796_g) * this.base_armX;
         this.field_78112_f.field_78800_c = -MathHelper.func_76134_b(this.field_78115_e.field_78796_g) * this.base_armX;
         this.field_78113_g.field_78798_e = -MathHelper.func_76126_a(this.field_78115_e.field_78796_g) * this.base_armX;
         this.field_78113_g.field_78800_c = MathHelper.func_76134_b(this.field_78115_e.field_78796_g) * this.base_armX;
         var10000 = this.field_78112_f;
         var10000.field_78796_g += this.field_78115_e.field_78796_g;
         var10000 = this.field_78113_g;
         var10000.field_78796_g += this.field_78115_e.field_78796_g;
         var10000 = this.field_78113_g;
         var10000.field_78795_f += this.field_78115_e.field_78796_g;
         f6 = 1.0F - this.field_78095_p;
         f6 *= f6;
         f6 *= f6;
         f6 = 1.0F - f6;
         bowAmount = MathHelper.func_76126_a(f6 * 3.1415927F);
         bowAmountRad = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -(this.field_78116_c.field_78795_f - 0.7F) * 0.75F;
         this.field_78112_f.field_78795_f = (float)((double)this.field_78112_f.field_78795_f - ((double)bowAmount * 1.2D + (double)bowAmountRad));
         var10000 = this.field_78112_f;
         var10000.field_78796_g += this.field_78115_e.field_78796_g * 2.0F;
         this.field_78112_f.field_78808_h = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      }

      if (this.field_78117_n) {
         this.field_78115_e.field_78795_f = this.base_bodyRotateX + 0.5F;
         var10000 = this.field_78112_f;
         var10000.field_78795_f += 0.4F;
         var10000 = this.field_78113_g;
         var10000.field_78795_f += 0.4F;
         this.field_78123_h.field_78798_e = this.base_legZ + 4.0F;
         this.field_78124_i.field_78798_e = this.base_legZ + 4.0F;
         this.field_78123_h.field_78797_d = this.base_legY - 3.0F;
         this.field_78124_i.field_78797_d = this.base_legY - 3.0F;
         this.field_78116_c.field_78797_d = this.base_headY + 1.0F;
         this.field_78114_d.field_78797_d = this.base_headY + 1.0F;
      } else {
         this.field_78115_e.field_78795_f = this.base_bodyRotateX;
         this.field_78123_h.field_78798_e = this.base_legZ + 0.1F;
         this.field_78124_i.field_78798_e = this.base_legZ + 0.1F;
         this.field_78123_h.field_78797_d = this.base_legY;
         this.field_78124_i.field_78797_d = this.base_legY;
         this.field_78116_c.field_78797_d = this.base_headY;
         this.field_78114_d.field_78797_d = this.base_headY;
      }

      var10000 = this.field_78112_f;
      var10000.field_78808_h += MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      var10000 = this.field_78113_g;
      var10000.field_78808_h -= MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      var10000 = this.field_78112_f;
      var10000.field_78795_f += MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
      var10000 = this.field_78113_g;
      var10000.field_78795_f -= MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
      if (this.field_78118_o) {
         f6 = 0.0F;
         bowAmount = 0.0F;
         this.field_78112_f.field_78808_h = 0.0F;
         this.field_78113_g.field_78808_h = 0.0F;
         this.field_78112_f.field_78796_g = -(0.1F - f6 * 0.6F) + this.field_78116_c.field_78796_g;
         this.field_78113_g.field_78796_g = 0.1F - f6 * 0.6F + this.field_78116_c.field_78796_g + 0.4F;
         this.field_78112_f.field_78795_f = -1.5707964F + this.field_78116_c.field_78795_f;
         this.field_78113_g.field_78795_f = -1.5707964F + this.field_78116_c.field_78795_f;
         var10000 = this.field_78112_f;
         var10000.field_78795_f -= f6 * 1.2F - bowAmount * 0.4F;
         var10000 = this.field_78113_g;
         var10000.field_78795_f -= f6 * 1.2F - bowAmount * 0.4F;
         var10000 = this.field_78112_f;
         var10000.field_78808_h += MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
         var10000 = this.field_78113_g;
         var10000.field_78808_h -= MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
         var10000 = this.field_78112_f;
         var10000.field_78795_f += MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
         var10000 = this.field_78113_g;
         var10000.field_78795_f -= MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
      }

      float bowCos;
      float bowSin;
      if (entity instanceof LOTREntityNPC) {
         LOTREntityNPC npc = (LOTREntityNPC)entity;
         if (npc.isDrunkard()) {
            bowAmount = f2 / 80.0F;
            bowAmountRad = (f2 + 40.0F) / 80.0F;
            bowAmount *= 6.2831855F;
            bowAmountRad *= 6.2831855F;
            bowCos = MathHelper.func_76126_a(bowAmount) * 0.5F;
            bowSin = MathHelper.func_76126_a(bowAmountRad) * 0.5F;
            var10000 = this.field_78116_c;
            var10000.field_78795_f += bowCos;
            var10000 = this.field_78116_c;
            var10000.field_78796_g += bowSin;
            var10000 = this.field_78114_d;
            var10000.field_78795_f += bowCos;
            var10000 = this.field_78114_d;
            var10000.field_78796_g += bowSin;
            if (npc.func_70694_bm() != null) {
               this.field_78112_f.field_78795_f = -1.0471976F;
            }
         }
      }

      boolean bowing = false;
      bowAmount = 0.0F;
      if (entity instanceof LOTREntityElf) {
         bowAmount = ((LOTREntityElf)entity).getBowingAmount(LOTRTickHandlerClient.renderTick);
         bowing = bowAmount != 0.0F;
      }

      if (bowing) {
         bowAmount *= 30.0F;
         bowAmountRad = (float)Math.toRadians((double)bowAmount);
         bowCos = MathHelper.func_76134_b(bowAmountRad);
         bowSin = MathHelper.func_76126_a(bowAmountRad);
         this.field_78116_c.field_78797_d = this.base_headY + 12.0F * (1.0F - bowCos);
         this.field_78116_c.field_78798_e = this.base_headY - 12.0F * bowSin;
         this.field_78114_d.field_78797_d = this.field_78116_c.field_78797_d;
         this.field_78114_d.field_78798_e = this.field_78116_c.field_78798_e;
         this.field_78115_e.field_78797_d = this.base_bodyY + 12.0F * (1.0F - bowCos);
         this.field_78115_e.field_78798_e = this.base_bodyZ - 12.0F * bowSin;
         this.field_78112_f.field_78797_d = this.base_armY + 10.0F * (1.0F - bowCos);
         this.field_78112_f.field_78798_e = this.base_armY - 12.0F * bowSin;
         this.field_78113_g.field_78797_d = this.field_78112_f.field_78797_d;
         this.field_78113_g.field_78798_e = this.field_78112_f.field_78798_e;
         this.field_78116_c.field_78795_f = bowAmountRad;
         this.field_78114_d.field_78795_f = bowAmountRad;
         this.field_78115_e.field_78795_f = bowAmountRad;
         this.field_78112_f.field_78795_f = bowAmountRad;
         this.field_78113_g.field_78795_f = bowAmountRad;
      } else {
         if (!this.field_78117_n) {
            this.field_78116_c.field_78797_d = this.base_headY;
            this.field_78116_c.field_78798_e = this.base_headZ;
            this.field_78114_d.field_78797_d = this.base_headY;
            this.field_78114_d.field_78798_e = this.base_headZ;
         }

         this.field_78115_e.field_78797_d = this.base_bodyY;
         this.field_78115_e.field_78798_e = this.base_bodyZ;
         this.field_78112_f.field_78797_d = this.base_armY;
         this.field_78112_f.field_78798_e = this.base_armZ;
         this.field_78113_g.field_78797_d = this.base_armY;
         this.field_78113_g.field_78798_e = this.base_armZ;
      }

   }
}
