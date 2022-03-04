package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DolAmrothHelmetModel extends SpecialArmorModel {
   public DolAmrothHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public DolAmrothHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonHelmetParts();
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
      this.field_78116_c.func_78784_a(32, 0).func_228301_a_(-0.5F, -9.0F, -3.5F, 1.0F, 1.0F, 7.0F, f);
      ModelRenderer wingRight = new ModelRenderer(this, 0, 16);
      wingRight.func_228301_a_(-4.0F - f, -6.0F, 1.0F + f, 1.0F, 1.0F, 9.0F, 0.0F);
      wingRight.func_78784_a(20, 16).func_228301_a_(-3.5F - f, -5.0F, 1.9F + f, 0.0F, 6.0F, 8.0F, 0.0F);
      ModelRenderer wingLeft = new ModelRenderer(this, 0, 16);
      wingLeft.field_78809_i = true;
      wingLeft.func_228301_a_(3.0F + f, -6.0F, 1.0F + f, 1.0F, 1.0F, 9.0F, 0.0F);
      wingLeft.func_78784_a(20, 16).func_228301_a_(3.5F + f, -5.0F, 1.9F + f, 0.0F, 6.0F, 8.0F, 0.0F);
      wingRight.field_78796_g = (float)Math.toRadians(-25.0D);
      wingLeft.field_78796_g = -wingRight.field_78796_g;
      wingRight.field_78795_f = wingLeft.field_78795_f = (float)Math.toRadians(20.0D);
      this.field_78116_c.func_78792_a(wingRight);
      this.field_78116_c.func_78792_a(wingLeft);
   }
}
