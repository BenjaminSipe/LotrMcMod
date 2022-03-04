package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class LOTRModelTrollTotem extends ModelBase {
   private ModelRenderer head;
   private ModelRenderer jaw;
   private ModelRenderer body;
   private ModelRenderer rightArm;
   private ModelRenderer leftArm;
   private ModelRenderer rightLeg;
   private ModelRenderer leftLeg;
   private ModelRenderer base;

   public LOTRModelTrollTotem() {
      this.field_78090_t = 128;
      this.field_78089_u = 64;
      this.head = new ModelRenderer(this, 0, 0);
      this.head.func_78789_a(-6.0F, -10.0F, -10.0F, 12, 10, 12);
      this.head.func_78793_a(0.0F, 22.0F, 4.0F);
      this.head.func_78789_a(-1.0F, -5.0F, -12.0F, 2, 3, 2);
      this.head.func_78784_a(40, 0).func_78789_a(-7.0F, -6.0F, -6.0F, 1, 4, 3);
      this.head.field_78809_i = true;
      this.head.func_78789_a(6.0F, -6.0F, -6.0F, 1, 4, 3);
      this.jaw = new ModelRenderer(this, 48, 0);
      this.jaw.func_78789_a(-6.0F, -2.0F, -6.0F, 12, 2, 12);
      this.jaw.func_78793_a(0.0F, 24.0F, 0.0F);
      this.body = new ModelRenderer(this, 0, 24);
      this.body.func_78789_a(-5.0F, 0.0F, -5.0F, 10, 16, 10);
      this.body.func_78793_a(0.0F, 8.0F, 0.0F);
      this.rightArm = new ModelRenderer(this, 40, 24);
      this.rightArm.func_78789_a(-3.0F, 0.0F, -3.0F, 3, 10, 6);
      this.rightArm.func_78793_a(-5.0F, 9.0F, 0.0F);
      this.leftArm = new ModelRenderer(this, 40, 24);
      this.leftArm.field_78809_i = true;
      this.leftArm.func_78789_a(0.0F, 0.0F, -3.0F, 3, 10, 6);
      this.leftArm.func_78793_a(5.0F, 9.0F, 0.0F);
      this.rightLeg = new ModelRenderer(this, 0, 50);
      this.rightLeg.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 7, 6);
      this.rightLeg.func_78793_a(-4.0F, 8.0F, 0.0F);
      this.rightLeg.func_78784_a(24, 50).func_78789_a(-2.5F, 7.0F, -2.5F, 5, 7, 5);
      this.leftLeg = new ModelRenderer(this, 0, 50);
      this.leftLeg.field_78809_i = true;
      this.leftLeg.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 7, 6);
      this.leftLeg.func_78793_a(4.0F, 8.0F, 0.0F);
      this.leftLeg.func_78784_a(24, 50).func_78789_a(-2.5F, 7.0F, -2.5F, 5, 7, 5);
      this.base = new ModelRenderer(this, 48, 46);
      this.base.func_78789_a(-8.0F, 0.0F, -8.0F, 16, 2, 16);
      this.base.func_78793_a(0.0F, 22.0F, 0.0F);
   }

   public void renderHead(float f, float f1) {
      this.head.field_78795_f = f1 / 180.0F * 3.1415927F;
      this.head.func_78785_a(f);
      this.jaw.func_78785_a(f);
   }

   public void renderBody(float f) {
      this.body.func_78785_a(f);
      this.rightArm.func_78785_a(f);
      this.leftArm.func_78785_a(f);
   }

   public void renderBase(float f) {
      this.rightLeg.func_78785_a(f);
      this.leftLeg.func_78785_a(f);
      this.base.func_78785_a(f);
   }
}
