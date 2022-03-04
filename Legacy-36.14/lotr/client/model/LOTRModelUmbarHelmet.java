package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelUmbarHelmet extends LOTRModelBiped {
   public LOTRModelUmbarHelmet() {
      this(0.0F);
   }

   public LOTRModelUmbarHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78114_d = new ModelRenderer(this, 32, 0);
      this.field_78114_d.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78114_d.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f + 0.5F);
      this.field_78116_c.func_78784_a(0, 0);
      this.field_78116_c.func_78790_a(-0.5F, -11.0F - f, -3.0F, 1, 3, 1, 0.0F);
      this.field_78116_c.func_78790_a(-0.5F, -10.0F - f, 2.0F, 1, 2, 1, 0.0F);
      this.field_78116_c.func_78784_a(0, 16).func_78790_a(0.0F, -13.0F - f, -6.0F, 0, 4, 12, 0.0F);
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
