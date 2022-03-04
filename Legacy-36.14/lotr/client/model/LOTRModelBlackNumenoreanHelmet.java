package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelBlackNumenoreanHelmet extends LOTRModelBiped {
   public LOTRModelBlackNumenoreanHelmet() {
      this(0.0F);
   }

   public LOTRModelBlackNumenoreanHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      ModelRenderer wingLeft = new ModelRenderer(this, 33, 0);
      wingLeft.func_78793_a(-4.0F - f, -8.0F - f, 0.0F);
      wingLeft.func_78790_a(-6.0F, -6.0F, 0.0F, 6, 16, 0, 0.0F);
      wingLeft.field_78796_g = (float)Math.toRadians(25.0D);
      this.field_78116_c.func_78792_a(wingLeft);
      ModelRenderer wingRight = new ModelRenderer(this, 33, 0);
      wingRight.field_78809_i = true;
      wingRight.func_78793_a(4.0F + f, -8.0F - f, 0.0F);
      wingRight.func_78790_a(0.0F, -6.0F, 0.0F, 6, 16, 0, 0.0F);
      wingRight.field_78796_g = (float)Math.toRadians(-25.0D);
      this.field_78116_c.func_78792_a(wingRight);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
