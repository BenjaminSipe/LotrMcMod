package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelCrocodile extends ModelBase {
   private ModelRenderer body = (new ModelRenderer(this, 18, 83)).func_78787_b(128, 128);
   private ModelRenderer tail1;
   private ModelRenderer tail2;
   private ModelRenderer tail3;
   private ModelRenderer jaw;
   private ModelRenderer head;
   private ModelRenderer legFrontLeft;
   private ModelRenderer legBackLeft;
   private ModelRenderer legFrontRight;
   private ModelRenderer legBackRight;
   private ModelRenderer spines;

   public LOTRModelCrocodile() {
      this.body.func_78789_a(-8.0F, -5.0F, 0.0F, 16, 9, 36);
      this.body.func_78793_a(0.0F, 17.0F, -16.0F);
      this.tail1 = (new ModelRenderer(this, 0, 28)).func_78787_b(128, 128);
      this.tail1.func_78789_a(-7.0F, 0.0F, 0.0F, 14, 7, 19);
      this.tail1.func_78793_a(0.0F, 13.0F, 18.0F);
      this.tail2 = (new ModelRenderer(this, 0, 55)).func_78787_b(128, 128);
      this.tail2.func_78789_a(-6.0F, 1.5F, 17.0F, 12, 5, 16);
      this.tail2.func_78793_a(0.0F, 13.0F, 18.0F);
      this.tail3 = (new ModelRenderer(this, 0, 77)).func_78787_b(128, 128);
      this.tail3.func_78789_a(-5.0F, 3.0F, 31.0F, 10, 3, 14);
      this.tail3.func_78793_a(0.0F, 13.0F, 18.0F);
      this.jaw = (new ModelRenderer(this, 58, 18)).func_78787_b(128, 128);
      this.jaw.func_78789_a(-6.5F, 0.3F, -19.0F, 13, 4, 19);
      this.jaw.func_78793_a(0.0F, 17.0F, -16.0F);
      this.head = (new ModelRenderer(this, 0, 0)).func_78787_b(128, 128);
      this.head.func_78789_a(-7.5F, -6.0F, -21.0F, 15, 6, 21);
      this.head.func_78793_a(0.0F, 18.5F, -16.0F);
      this.legFrontLeft = (new ModelRenderer(this, 2, 104)).func_78787_b(128, 128);
      this.legFrontLeft.func_78789_a(0.0F, 0.0F, -3.0F, 16, 3, 6);
      this.legFrontLeft.func_78793_a(6.0F, 15.0F, -11.0F);
      this.legBackLeft = (new ModelRenderer(this, 2, 104)).func_78787_b(128, 128);
      this.legBackLeft.func_78789_a(0.0F, 0.0F, -3.0F, 16, 3, 6);
      this.legBackLeft.func_78793_a(6.0F, 15.0F, 15.0F);
      this.legFrontRight = (new ModelRenderer(this, 2, 104)).func_78787_b(128, 128);
      this.legFrontRight.field_78809_i = true;
      this.legFrontRight.func_78789_a(-16.0F, 0.0F, -3.0F, 16, 3, 6);
      this.legFrontRight.func_78793_a(-6.0F, 15.0F, -11.0F);
      this.legBackRight = (new ModelRenderer(this, 2, 104)).func_78787_b(128, 128);
      this.legBackRight.field_78809_i = true;
      this.legBackRight.func_78789_a(-16.0F, 0.0F, -3.0F, 16, 3, 6);
      this.legBackRight.func_78793_a(-6.0F, 15.0F, 15.0F);
      this.spines = (new ModelRenderer(this, 46, 45)).func_78787_b(128, 128);
      this.spines.func_78789_a(-5.0F, 0.0F, 0.0F, 10, 4, 32);
      this.spines.func_78793_a(0.0F, 9.5F, -14.0F);
      this.legBackLeft.field_78808_h = 0.43633232F;
      this.legBackRight.field_78808_h = -0.43633232F;
      this.legFrontLeft.field_78808_h = 0.43633232F;
      this.legFrontRight.field_78808_h = -0.43633232F;
      this.spines.field_78795_f = -0.034906585F;
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.body.func_78785_a(f5);
      this.tail1.func_78785_a(f5);
      this.tail2.func_78785_a(f5);
      this.tail3.func_78785_a(f5);
      this.jaw.func_78785_a(f5);
      this.head.func_78785_a(f5);
      this.legFrontLeft.func_78785_a(f5);
      this.legBackLeft.func_78785_a(f5);
      this.legFrontRight.func_78785_a(f5);
      this.legBackRight.func_78785_a(f5);
      this.spines.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78795_f = f2 * 3.1415927F * -0.3F;
      this.legBackRight.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * f1;
      this.legBackLeft.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * f1;
      this.legFrontRight.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * f1;
      this.legFrontLeft.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * f1;
      this.tail1.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * f1 * 0.5F;
      this.tail2.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * f1 * 0.5625F;
      this.tail3.field_78796_g = MathHelper.func_76134_b(f * 0.6662F) * f1 * 0.59375F;
   }
}
