package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelDikDik extends ModelBase {
   private ModelRenderer head = new ModelRenderer(this, 42, 23);
   private ModelRenderer neck;
   private ModelRenderer body;
   private ModelRenderer leg1;
   private ModelRenderer leg2;
   private ModelRenderer leg3;
   private ModelRenderer leg4;

   public LOTRModelDikDik() {
      this.head.func_78789_a(-2.0F, -9.0F, -3.0F, 4, 4, 5);
      this.head.func_78793_a(0.0F, 11.0F, -4.5F);
      this.head.func_78784_a(18, 28).func_78789_a(-1.0F, -7.3F, -5.0F, 2, 2, 2);
      this.head.func_78784_a(0, 27).func_78789_a(-2.8F, -11.0F, 0.5F, 1, 3, 2);
      this.head.func_78784_a(8, 27).func_78789_a(1.8F, -11.0F, 0.5F, 1, 3, 2);
      this.head.func_78784_a(0, 21).func_78789_a(-1.5F, -11.0F, 0.0F, 1, 2, 1);
      this.head.func_78784_a(0, 21).func_78789_a(0.5F, -11.0F, 0.0F, 1, 2, 1);
      this.head.func_78784_a(28, 22).func_78789_a(-1.5F, -8.0F, -2.0F, 3, 7, 3);
      this.body = new ModelRenderer(this, 0, 0);
      this.body.func_78789_a(-3.0F, 0.0F, 0.0F, 6, 6, 14);
      this.body.func_78793_a(0.0F, 9.0F, -7.0F);
      this.leg1 = new ModelRenderer(this, 56, 0);
      this.leg1.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 10, 2);
      this.leg1.func_78793_a(-1.7F, 14.0F, 5.0F);
      this.leg2 = new ModelRenderer(this, 56, 0);
      this.leg2.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 10, 2);
      this.leg2.func_78793_a(1.7F, 14.0F, 5.0F);
      this.leg3 = new ModelRenderer(this, 56, 0);
      this.leg3.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 10, 2);
      this.leg3.func_78793_a(-1.7F, 14.0F, -5.0F);
      this.leg4 = new ModelRenderer(this, 56, 0);
      this.leg4.func_78789_a(-1.0F, 0.0F, -1.0F, 2, 10, 2);
      this.leg4.func_78793_a(1.7F, 14.0F, -5.0F);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.head.func_78785_a(f5);
      this.body.func_78785_a(f5);
      this.leg1.func_78785_a(f5);
      this.leg2.func_78785_a(f5);
      this.leg3.func_78785_a(f5);
      this.leg4.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78795_f = f4 / 57.29578F;
      this.head.field_78796_g = f3 / 57.29578F;
      this.leg1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
      this.leg2.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
      this.leg3.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
      this.leg4.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
   }
}
