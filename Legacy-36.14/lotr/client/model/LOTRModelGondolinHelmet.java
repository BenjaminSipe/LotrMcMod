package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGondolinHelmet extends LOTRModelBiped {
   public LOTRModelGondolinHelmet() {
      this(0.0F);
   }

   public LOTRModelGondolinHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78784_a(46, 0).func_78790_a(-0.5F, -14.0F - f, -4.5F, 1, 6, 1, 0.0F);
      this.field_78116_c.func_78784_a(50, 0).func_78790_a(-0.5F, -12.0F - f, -0.5F, 1, 4, 1, 0.0F);
      this.field_78116_c.func_78784_a(54, 0).func_78790_a(-0.5F, -10.0F - f, 3.5F, 1, 2, 1, 0.0F);
      this.field_78116_c.func_78784_a(32, -7).func_78790_a(0.0F, -13.5F - f, -3.5F, 0, 6, 7, 0.0F);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
