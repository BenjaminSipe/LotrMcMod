package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelFish extends ModelBase {
   private ModelRenderer body = new ModelRenderer(this, 0, 0);
   private ModelRenderer finTop;
   private ModelRenderer finRight;
   private ModelRenderer finLeft;
   private ModelRenderer finBack;

   public LOTRModelFish() {
      this.body.func_78793_a(0.0F, 22.0F, -1.0F);
      this.body.func_78789_a(-0.5F, -2.0F, -3.0F, 1, 3, 6);
      this.finTop = new ModelRenderer(this, 14, 0);
      this.finTop.func_78793_a(0.0F, 0.0F, -1.5F);
      this.finTop.func_78789_a(0.0F, -2.0F, 0.0F, 0, 2, 4);
      this.body.func_78792_a(this.finTop);
      this.finRight = new ModelRenderer(this, 22, 0);
      this.finRight.func_78793_a(0.0F, 0.0F, -1.0F);
      this.finRight.func_78789_a(-0.5F, -1.0F, 0.0F, 0, 2, 3);
      this.body.func_78792_a(this.finRight);
      this.finLeft = new ModelRenderer(this, 22, 0);
      this.finLeft.func_78793_a(0.0F, 0.0F, -1.0F);
      this.finLeft.func_78789_a(0.5F, -1.0F, 0.0F, 0, 2, 3);
      this.body.func_78792_a(this.finLeft);
      this.finBack = new ModelRenderer(this, 0, 9);
      this.finBack.func_78793_a(0.0F, -0.5F, 1.5F);
      this.finBack.func_78789_a(0.0F, -5.0F, 0.0F, 0, 5, 5);
      this.body.func_78792_a(this.finBack);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.body.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.finTop.field_78795_f = (float)Math.toRadians(27.0D);
      this.finRight.field_78795_f = (float)Math.toRadians(-15.0D);
      this.finRight.field_78796_g = (float)Math.toRadians(-30.0D);
      ModelRenderer var10000 = this.finRight;
      var10000.field_78796_g += MathHelper.func_76134_b(f2 * 0.5F + 3.1415927F) * (float)Math.toRadians(10.0D);
      this.finLeft.field_78795_f = this.finRight.field_78795_f;
      this.finLeft.field_78796_g = -this.finRight.field_78796_g;
      this.finBack.field_78795_f = (float)Math.toRadians(-45.0D);
   }
}
