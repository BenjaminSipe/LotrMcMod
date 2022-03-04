package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHarnedorHelmet extends LOTRModelBiped {
   public LOTRModelHarnedorHelmet() {
      this(0.0F);
   }

   public LOTRModelHarnedorHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78784_a(0, 5).func_78790_a(0.0F, -11.0F, -7.0F, 0, 10, 14, 0.0F);
      this.field_78116_c.func_78784_a(16, 19).func_78790_a(-6.0F, -2.0F, -6.0F, 12, 0, 12, 0.0F);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
