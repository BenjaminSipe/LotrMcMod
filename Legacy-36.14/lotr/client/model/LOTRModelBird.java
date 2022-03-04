package lotr.client.model;

import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelBird extends ModelBase {
   public ModelRenderer body = new ModelRenderer(this, 0, 7);
   public ModelRenderer head;
   public ModelRenderer wingRight;
   public ModelRenderer wingLeft;
   public ModelRenderer legRight;
   public ModelRenderer legLeft;

   public LOTRModelBird() {
      this.body.func_78789_a(-1.5F, -2.0F, -2.0F, 3, 3, 5);
      this.body.func_78784_a(8, 0).func_78789_a(-1.0F, -1.5F, 3.0F, 2, 1, 3);
      this.body.func_78784_a(8, 4).func_78789_a(-1.0F, -0.5F, 3.0F, 2, 1, 2);
      this.body.func_78793_a(0.0F, 21.0F, 0.0F);
      this.head = new ModelRenderer(this, 0, 0);
      this.head.func_78789_a(-1.0F, -1.5F, -1.5F, 2, 2, 2);
      this.head.func_78784_a(0, 4).func_78789_a(-0.5F, -0.5F, -2.5F, 1, 1, 1);
      this.head.func_78784_a(15, 0).func_78789_a(-0.5F, -0.5F, -3.5F, 1, 1, 2);
      this.head.func_78793_a(0.0F, -2.0F, -2.0F);
      this.body.func_78792_a(this.head);
      this.wingRight = new ModelRenderer(this, 16, 7);
      this.wingRight.func_78789_a(0.0F, 0.0F, -2.0F, 0, 5, 4);
      this.wingRight.func_78793_a(-1.5F, -1.5F, 0.5F);
      this.body.func_78792_a(this.wingRight);
      this.wingLeft = new ModelRenderer(this, 16, 7);
      this.wingLeft.field_78809_i = true;
      this.wingLeft.func_78789_a(0.0F, 0.0F, -2.0F, 0, 5, 4);
      this.wingLeft.func_78793_a(1.5F, -1.5F, 0.5F);
      this.body.func_78792_a(this.wingLeft);
      this.legRight = new ModelRenderer(this, 0, 16);
      this.legRight.func_78789_a(-1.0F, 0.0F, -1.5F, 1, 2, 2);
      this.legRight.func_78793_a(-0.3F, 1.0F, 0.5F);
      this.body.func_78792_a(this.legRight);
      this.legLeft = new ModelRenderer(this, 0, 16);
      this.legLeft.field_78809_i = true;
      this.legLeft.func_78789_a(0.0F, 0.0F, -1.5F, 1, 2, 2);
      this.legLeft.func_78793_a(0.3F, 1.0F, 0.5F);
      this.body.func_78792_a(this.legLeft);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.body.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      LOTREntityBird bird = (LOTREntityBird)entity;
      if (bird.isBirdStill()) {
         this.body.field_78795_f = (float)Math.toRadians(-10.0D);
         this.head.field_78795_f = (float)Math.toRadians(20.0D);
         if (bird.flapTime > 0) {
            this.wingRight.field_78808_h = (float)Math.toRadians(90.0D) + MathHelper.func_76134_b(f2 * 1.5F) * (float)Math.toRadians(30.0D);
         } else {
            this.wingRight.field_78808_h = (float)Math.toRadians(30.0D);
         }

         this.wingLeft.field_78808_h = -this.wingRight.field_78808_h;
         this.legRight.field_78795_f = this.legLeft.field_78795_f = -this.body.field_78795_f;
         ModelRenderer var10000 = this.legRight;
         var10000.field_78795_f += MathHelper.func_76134_b(f * 0.6662F) * f1;
         var10000 = this.legLeft;
         var10000.field_78795_f += MathHelper.func_76134_b(f * 0.6662F + 3.141593F) * f1;
         this.legRight.field_78797_d = 1.0F;
         this.legLeft.field_78797_d = 1.0F;
      } else {
         this.body.field_78795_f = 0.0F;
         this.head.field_78795_f = 0.0F;
         this.wingRight.field_78808_h = (float)Math.toRadians(90.0D) + MathHelper.func_76134_b(f2 * 1.5F) * (float)Math.toRadians(30.0D);
         this.wingLeft.field_78808_h = -this.wingRight.field_78808_h;
         this.legRight.field_78795_f = this.legLeft.field_78795_f = 0.0F;
         this.legRight.field_78797_d = 0.0F;
         this.legLeft.field_78797_d = 0.0F;
      }

   }
}
