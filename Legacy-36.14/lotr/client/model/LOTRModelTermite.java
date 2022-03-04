package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelTermite extends ModelBase {
   private ModelRenderer body = new ModelRenderer(this, 10, 5);
   private ModelRenderer head;
   private ModelRenderer leg1;
   private ModelRenderer leg2;
   private ModelRenderer leg3;
   private ModelRenderer leg5;
   private ModelRenderer leg4;
   private ModelRenderer leg6;
   private ModelRenderer rightfeeler;
   private ModelRenderer leftfeeler;

   public LOTRModelTermite() {
      this.body.func_78789_a(0.0F, 0.0F, 0.0F, 6, 6, 21);
      this.body.func_78793_a(-3.0F, 17.0F, -5.0F);
      this.head = new ModelRenderer(this, 0, 0);
      this.head.func_78789_a(0.0F, 0.0F, 0.0F, 8, 8, 7);
      this.head.func_78793_a(-4.0F, 14.0F, -10.0F);
      this.leg1 = new ModelRenderer(this, 34, 0);
      this.leg1.func_78789_a(-12.0F, -1.0F, -1.0F, 13, 2, 2);
      this.leg1.func_78793_a(-2.0F, 19.0F, 1.0F);
      this.leg2 = new ModelRenderer(this, 34, 0);
      this.leg2.func_78789_a(-1.0F, -1.0F, -1.0F, 13, 2, 2);
      this.leg2.func_78793_a(2.0F, 19.0F, 1.0F);
      this.leg3 = new ModelRenderer(this, 34, 0);
      this.leg3.func_78789_a(-12.0F, -1.0F, -1.0F, 13, 2, 2);
      this.leg3.func_78793_a(-2.0F, 19.0F, 0.0F);
      this.leg4 = new ModelRenderer(this, 34, 0);
      this.leg4.func_78789_a(-1.0F, -1.0F, -1.0F, 13, 2, 2);
      this.leg4.func_78793_a(2.0F, 19.0F, 0.0F);
      this.leg5 = new ModelRenderer(this, 34, 0);
      this.leg5.func_78789_a(-12.0F, -1.0F, -1.0F, 13, 2, 2);
      this.leg5.func_78793_a(-2.0F, 19.0F, -1.0F);
      this.leg6 = new ModelRenderer(this, 34, 0);
      this.leg6.func_78789_a(-1.0F, -1.0F, -1.0F, 13, 2, 2);
      this.leg6.func_78793_a(2.0F, 19.0F, -1.0F);
      this.rightfeeler = new ModelRenderer(this, 50, 18);
      this.rightfeeler.func_78789_a(0.0F, 0.0F, -8.0F, 1, 1, 6);
      this.rightfeeler.func_78793_a(-3.0F, 15.0F, -8.0F);
      this.rightfeeler.field_78796_g = -0.1F;
      this.leftfeeler = new ModelRenderer(this, 50, 18);
      this.leftfeeler.func_78789_a(0.0F, 0.0F, -8.0F, 1, 1, 6);
      this.leftfeeler.func_78793_a(2.0F, 15.0F, -8.0F);
      this.leftfeeler.field_78796_g = -0.1F;
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.body.func_78785_a(f5);
      this.head.func_78785_a(f5);
      this.leg1.func_78785_a(f5);
      this.leg2.func_78785_a(f5);
      this.leg3.func_78785_a(f5);
      this.leg4.func_78785_a(f5);
      this.leg5.func_78785_a(f5);
      this.leg6.func_78785_a(f5);
      this.rightfeeler.func_78785_a(f5);
      this.leftfeeler.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      float f6 = -0.51460177F;
      this.leg1.field_78808_h = f6;
      this.leg2.field_78808_h = -f6;
      this.leg3.field_78808_h = f6 * 0.74F;
      this.leg4.field_78808_h = -f6 * 0.74F;
      this.leg5.field_78808_h = f6 * 0.74F;
      this.leg6.field_78808_h = -f6 * 0.74F;
      float f7 = -0.0F;
      float f8 = 0.3926991F;
      this.leg1.field_78796_g = f8 * 2.0F + f7;
      this.leg2.field_78796_g = -f8 * 2.0F - f7;
      this.leg3.field_78796_g = f8 * 1.0F + f7;
      this.leg4.field_78796_g = -f8 * 1.0F - f7;
      this.leg5.field_78796_g = -f8 * 1.0F + f7;
      this.leg6.field_78796_g = f8 * 1.0F - f7;
      float f9 = -(MathHelper.func_76134_b(f * 0.6662F * 2.0F + 0.0F) * 0.4F) * f1;
      float f10 = -(MathHelper.func_76134_b(f * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * f1;
      float f11 = -(MathHelper.func_76134_b(f * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * f1;
      float f12 = -(MathHelper.func_76134_b(f * 0.6662F * 2.0F + 4.712389F) * 0.4F) * f1;
      float f13 = Math.abs(MathHelper.func_76126_a(f * 0.6662F + 0.0F) * 0.4F) * f1;
      float f14 = Math.abs(MathHelper.func_76126_a(f * 0.6662F + 3.1415927F) * 0.4F) * f1;
      float f15 = Math.abs(MathHelper.func_76126_a(f * 0.6662F + 1.5707964F) * 0.4F) * f1;
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
   }
}
