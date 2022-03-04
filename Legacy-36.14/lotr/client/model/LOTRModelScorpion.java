package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelScorpion extends LOTRModelSpider {
   private ModelRenderer armRight;
   private ModelRenderer armLeft;
   private ModelRenderer tail;

   public LOTRModelScorpion() {
      this.abdomen.field_78804_l.clear();
      this.armRight = new ModelRenderer(this, 36, 16);
      this.armRight.func_78789_a(-16.0F, -1.0F, 0.0F, 16, 2, 2);
      this.armRight.func_78793_a(-3.0F, 18.5F, -4.0F);
      ModelRenderer clawRight = new ModelRenderer(this, 0, 12);
      clawRight.func_78789_a(-13.0F, -2.0F, -16.0F, 4, 3, 5);
      clawRight.func_78789_a(-13.0F, -1.0F, -20.0F, 1, 1, 4);
      clawRight.func_78789_a(-10.0F, -1.0F, -20.0F, 1, 1, 4);
      clawRight.field_78796_g = (float)Math.toRadians(50.0D);
      this.armRight.func_78792_a(clawRight);
      this.armLeft = new ModelRenderer(this, 36, 16);
      this.armLeft.field_78809_i = true;
      this.armLeft.func_78789_a(0.0F, -1.0F, 0.0F, 16, 2, 2);
      this.armLeft.func_78793_a(3.0F, 18.5F, -4.0F);
      ModelRenderer clawLeft = new ModelRenderer(this, 0, 12);
      clawLeft.field_78809_i = true;
      clawLeft.func_78789_a(9.0F, -2.0F, -16.0F, 4, 3, 5);
      clawLeft.func_78789_a(12.0F, -1.0F, -20.0F, 1, 1, 4);
      clawLeft.func_78789_a(9.0F, -1.0F, -20.0F, 1, 1, 4);
      clawLeft.field_78796_g = (float)Math.toRadians(-50.0D);
      this.armLeft.func_78792_a(clawLeft);
      this.tail = new ModelRenderer(this, 0, 12);
      this.tail.func_78789_a(-2.5F, -3.0F, 0.0F, 5, 5, 11);
      this.tail.func_78793_a(0.0F, 19.5F, 3.0F);
      ModelRenderer tail1 = new ModelRenderer(this, 0, 12);
      tail1.func_78789_a(-2.0F, -2.0F, 0.0F, 4, 4, 10);
      tail1.func_78793_a(0.0F, -0.5F, 11.0F);
      tail1.field_78795_f = (float)Math.toRadians(40.0D);
      this.tail.func_78792_a(tail1);
      ModelRenderer tail2 = new ModelRenderer(this, 0, 12);
      tail2.func_78789_a(-1.5F, -2.0F, 0.0F, 3, 4, 10);
      tail2.func_78793_a(0.0F, 0.0F, 11.0F);
      tail2.field_78795_f = (float)Math.toRadians(40.0D);
      tail1.func_78792_a(tail2);
      ModelRenderer sting = new ModelRenderer(this, 0, 12);
      sting.func_78789_a(-1.0F, -0.5F, 0.0F, 2, 3, 5);
      sting.func_78789_a(-0.5F, 0.0F, 5.0F, 1, 1, 3);
      sting.func_78793_a(0.0F, 0.0F, 9.0F);
      sting.field_78795_f = (float)Math.toRadians(90.0D);
      tail2.func_78792_a(sting);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
      this.armRight.func_78785_a(f5);
      this.armLeft.func_78785_a(f5);
      this.tail.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.armRight.field_78796_g = (float)Math.toRadians(-50.0D) + MathHelper.func_76134_b(f * 0.4F) * f1 * 0.4F;
      ModelRenderer var10000 = this.armRight;
      var10000.field_78796_g += f2 * (float)Math.toRadians(-40.0D);
      this.armLeft.field_78796_g = -this.armRight.field_78796_g;
      this.tail.field_78795_f = (float)Math.toRadians(30.0D) + MathHelper.func_76134_b(f * 0.4F) * f1 * 0.15F;
      var10000 = this.tail;
      var10000.field_78795_f += f2 * (float)Math.toRadians(90.0D);
   }
}
