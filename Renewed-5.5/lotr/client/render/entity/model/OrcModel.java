package lotr.client.render.entity.model;

import net.minecraft.client.renderer.model.ModelRenderer;

public class OrcModel extends LOTRBipedModel {
   private ModelRenderer nose;
   private ModelRenderer earRight;
   private ModelRenderer earLeft;

   public OrcModel(boolean smallArms) {
      this(0.0F, false, smallArms);
   }

   public OrcModel(float f) {
      this(f, true, false);
   }

   public OrcModel(float f, boolean isArmor, boolean smallArms) {
      super(f, 0.0F, isArmor, smallArms);
      if (!isArmor) {
         this.createLongHairModel(0.0F, f);
      }

      this.nose = new ModelRenderer(this, 14, 17);
      this.nose.func_228301_a_(-0.5F, -4.0F, -4.8F, 1.0F, 2.0F, 1.0F, f);
      this.nose.func_78793_a(0.0F, 0.0F, 0.0F);
      this.earRight = new ModelRenderer(this, 0, 0);
      this.earRight.func_228301_a_(-3.5F, -5.5F, 2.0F, 1.0F, 2.0F, 3.0F, f);
      this.earRight.func_78793_a(0.0F, 0.0F, 0.0F);
      this.earRight.field_78795_f = (float)Math.toRadians(15.0D);
      this.earRight.field_78796_g = (float)Math.toRadians(-30.0D);
      this.earRight.field_78808_h = (float)Math.toRadians(-13.0D);
      this.earLeft = new ModelRenderer(this, 24, 0);
      this.earLeft.func_228301_a_(2.5F, -5.5F, 2.0F, 1.0F, 2.0F, 3.0F, f);
      this.earLeft.func_78793_a(0.0F, 0.0F, 0.0F);
      this.earLeft.field_78795_f = this.earRight.field_78795_f;
      this.earLeft.field_78796_g = -this.earRight.field_78796_g;
      this.earLeft.field_78808_h = -this.earRight.field_78808_h;
      this.field_78116_c.func_78792_a(this.nose);
      this.field_78116_c.func_78792_a(this.earRight);
      this.field_78116_c.func_78792_a(this.earLeft);
   }
}
