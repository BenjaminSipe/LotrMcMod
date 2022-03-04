package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelSpider extends ModelBase implements LOTRGlowingEyes.Model {
   protected ModelRenderer head;
   protected ModelRenderer thorax;
   protected ModelRenderer abdomen;
   protected ModelRenderer leg1;
   protected ModelRenderer leg2;
   protected ModelRenderer leg3;
   protected ModelRenderer leg4;
   protected ModelRenderer leg5;
   protected ModelRenderer leg6;
   protected ModelRenderer leg7;
   protected ModelRenderer leg8;

   public LOTRModelSpider() {
      this(0.0F);
   }

   public LOTRModelSpider(float f) {
      this.head = new ModelRenderer(this, 32, 0);
      this.head.func_78790_a(-4.0F, -4.0F, -8.0F, 8, 8, 8, f);
      this.head.func_78793_a(0.0F, 17.0F, -3.0F);
      this.thorax = new ModelRenderer(this, 0, 0);
      this.thorax.func_78790_a(-3.0F, -3.0F, -3.0F, 6, 6, 6, f);
      this.thorax.func_78793_a(0.0F, 17.0F, 0.0F);
      this.abdomen = new ModelRenderer(this, 0, 12);
      this.abdomen.func_78790_a(-5.0F, -4.0F, -0.5F, 10, 8, 12, f);
      this.abdomen.func_78793_a(0.0F, 17.0F, 3.0F);
      this.leg1 = new ModelRenderer(this, 36, 16);
      this.leg1.func_78790_a(-11.0F, -1.0F, -1.0F, 12, 2, 2, f);
      this.leg1.func_78793_a(-4.0F, 17.0F, 2.0F);
      this.leg1.func_78784_a(60, 20).func_78790_a(-10.5F, 0.0F, -0.5F, 1, 10, 1, f);
      this.leg2 = new ModelRenderer(this, 36, 16);
      this.leg2.field_78809_i = true;
      this.leg2.func_78790_a(-1.0F, -1.0F, -1.0F, 12, 2, 2, f);
      this.leg2.func_78793_a(4.0F, 17.0F, 2.0F);
      this.leg2.func_78784_a(60, 20).func_78790_a(9.5F, 0.0F, -0.5F, 1, 10, 1, f);
      this.leg3 = new ModelRenderer(this, 36, 16);
      this.leg3.func_78790_a(-11.0F, -1.0F, -1.0F, 12, 2, 2, f);
      this.leg3.func_78793_a(-4.0F, 17.0F, 1.0F);
      this.leg3.func_78784_a(60, 20).func_78790_a(-10.5F, 0.0F, -0.5F, 1, 10, 1, f);
      this.leg4 = new ModelRenderer(this, 36, 16);
      this.leg4.field_78809_i = true;
      this.leg4.func_78790_a(-1.0F, -1.0F, -1.0F, 12, 2, 2, f);
      this.leg4.func_78793_a(4.0F, 17.0F, 1.0F);
      this.leg4.func_78784_a(60, 20).func_78790_a(9.5F, 0.0F, -0.5F, 1, 10, 1, f);
      this.leg5 = new ModelRenderer(this, 36, 16);
      this.leg5.func_78790_a(-11.0F, -1.0F, -1.0F, 12, 2, 2, f);
      this.leg5.func_78793_a(-4.0F, 17.0F, 0.0F);
      this.leg5.func_78784_a(60, 20).func_78790_a(-10.5F, 0.0F, -0.5F, 1, 10, 1, f);
      this.leg6 = new ModelRenderer(this, 36, 16);
      this.leg6.field_78809_i = true;
      this.leg6.func_78790_a(-1.0F, -1.0F, -1.0F, 12, 2, 2, f);
      this.leg6.func_78793_a(4.0F, 17.0F, 0.0F);
      this.leg6.func_78784_a(60, 20).func_78790_a(9.5F, 0.0F, -0.5F, 1, 10, 1, f);
      this.leg7 = new ModelRenderer(this, 36, 16);
      this.leg7.func_78790_a(-11.0F, -1.0F, -1.0F, 12, 2, 2, f);
      this.leg7.func_78793_a(-4.0F, 17.0F, -1.0F);
      this.leg7.func_78784_a(60, 20).func_78790_a(-10.5F, 0.0F, -0.5F, 1, 10, 1, f);
      this.leg8 = new ModelRenderer(this, 36, 16);
      this.leg8.field_78809_i = true;
      this.leg8.func_78790_a(-1.0F, -1.0F, -1.0F, 12, 2, 2, f);
      this.leg8.func_78793_a(4.0F, 17.0F, -1.0F);
      this.leg8.func_78784_a(60, 20).func_78790_a(9.5F, 0.0F, -0.5F, 1, 10, 1, f);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.head.func_78785_a(f5);
      this.thorax.func_78785_a(f5);
      this.abdomen.func_78785_a(f5);
      this.leg1.func_78785_a(f5);
      this.leg2.func_78785_a(f5);
      this.leg3.func_78785_a(f5);
      this.leg4.func_78785_a(f5);
      this.leg5.func_78785_a(f5);
      this.leg6.func_78785_a(f5);
      this.leg7.func_78785_a(f5);
      this.leg8.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78796_g = f3 / 57.295776F;
      this.head.field_78795_f = f4 / 57.295776F;
      this.abdomen.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * 0.5F * f1;
      float f6 = -0.51460177F;
      this.leg1.field_78808_h = -f6;
      this.leg2.field_78808_h = f6;
      this.leg3.field_78808_h = -f6 * 0.74F;
      this.leg4.field_78808_h = f6 * 0.74F;
      this.leg5.field_78808_h = -f6 * 0.74F;
      this.leg6.field_78808_h = f6 * 0.74F;
      this.leg7.field_78808_h = -f6;
      this.leg8.field_78808_h = f6;
      float f7 = -0.0F;
      float f8 = 0.3926991F;
      this.leg1.field_78796_g = f8 * 2.0F + f7;
      this.leg2.field_78796_g = -f8 * 2.0F - f7;
      this.leg3.field_78796_g = f8 * 1.0F + f7;
      this.leg4.field_78796_g = -f8 * 1.0F - f7;
      this.leg5.field_78796_g = -f8 * 1.0F + f7;
      this.leg6.field_78796_g = f8 * 1.0F - f7;
      this.leg7.field_78796_g = -f8 * 2.0F + f7;
      this.leg8.field_78796_g = f8 * 2.0F - f7;
      float f9 = -(MathHelper.func_76134_b(f * 0.6662F * 2.0F + 0.0F) * 0.4F) * f1;
      float f10 = -(MathHelper.func_76134_b(f * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * f1;
      float f11 = -(MathHelper.func_76134_b(f * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * f1;
      float f12 = -(MathHelper.func_76134_b(f * 0.6662F * 2.0F + 4.712389F) * 0.4F) * f1;
      float f13 = Math.abs(MathHelper.func_76126_a(f * 0.6662F + 0.0F) * 0.4F) * f1;
      float f14 = Math.abs(MathHelper.func_76126_a(f * 0.6662F + 3.1415927F) * 0.4F) * f1;
      float f15 = Math.abs(MathHelper.func_76126_a(f * 0.6662F + 1.5707964F) * 0.4F) * f1;
      float f16 = Math.abs(MathHelper.func_76126_a(f * 0.6662F + 4.712389F) * 0.4F) * f1;
      ModelRenderer var10000 = this.leg1;
      var10000.field_78796_g += f9;
      var10000 = this.leg2;
      var10000.field_78796_g += -f9;
      var10000 = this.leg3;
      var10000.field_78796_g += f10;
      var10000 = this.leg4;
      var10000.field_78796_g += -f10;
      var10000 = this.leg5;
      var10000.field_78796_g += f11;
      var10000 = this.leg6;
      var10000.field_78796_g += -f11;
      var10000 = this.leg7;
      var10000.field_78796_g += f12;
      var10000 = this.leg8;
      var10000.field_78796_g += -f12;
      var10000 = this.leg1;
      var10000.field_78808_h += f13;
      var10000 = this.leg2;
      var10000.field_78808_h += -f13;
      var10000 = this.leg3;
      var10000.field_78808_h += f14;
      var10000 = this.leg4;
      var10000.field_78808_h += -f14;
      var10000 = this.leg5;
      var10000.field_78808_h += f15;
      var10000 = this.leg6;
      var10000.field_78808_h += -f15;
      var10000 = this.leg7;
      var10000.field_78808_h += f16;
      var10000 = this.leg8;
      var10000.field_78808_h += -f16;
   }

   public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.head.func_78785_a(f5);
   }
}
