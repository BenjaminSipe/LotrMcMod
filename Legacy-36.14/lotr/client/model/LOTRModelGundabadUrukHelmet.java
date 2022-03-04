package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGundabadUrukHelmet extends LOTRModelBiped {
   public LOTRModelGundabadUrukHelmet() {
      this(0.0F);
   }

   public LOTRModelGundabadUrukHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      ModelRenderer hornRight = new ModelRenderer(this, 32, 0);
      hornRight.func_78793_a(-f, -f, -f);
      hornRight.func_78790_a(-7.0F, -12.0F, 0.5F, 3, 8, 0, 0.0F);
      hornRight.field_78808_h = (float)Math.toRadians(6.0D);
      ModelRenderer hornLeft = new ModelRenderer(this, 32, 0);
      hornLeft.func_78793_a(f, -f, -f);
      hornLeft.field_78809_i = true;
      hornLeft.func_78790_a(4.0F, -12.0F, 0.5F, 3, 8, 0, 0.0F);
      hornLeft.field_78808_h = (float)Math.toRadians(-6.0D);
      this.field_78116_c.func_78792_a(hornRight);
      this.field_78116_c.func_78792_a(hornLeft);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
