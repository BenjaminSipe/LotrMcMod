package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGondorHelmet extends LOTRModelBiped {
   public LOTRModelGondorHelmet() {
      this(0.0F);
   }

   public LOTRModelGondorHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78784_a(0, 16).func_78790_a(-1.5F, -9.0F, -3.5F, 3, 1, 7, f);
      this.field_78116_c.func_78784_a(20, 16).func_78790_a(-0.5F, -10.0F, -3.5F, 1, 1, 7, f);
      this.field_78116_c.func_78784_a(24, 0).func_78790_a(-1.5F, -10.5F - f, -4.5F - f, 3, 4, 1, 0.0F);
      this.field_78116_c.func_78784_a(24, 5).func_78790_a(-0.5F, -11.5F - f, -4.5F - f, 1, 1, 1, 0.0F);
      this.field_78116_c.func_78784_a(28, 5).func_78790_a(-0.5F, -6.5F - f, -4.5F - f, 1, 1, 1, 0.0F);
      this.field_78116_c.func_78784_a(32, 0).func_78790_a(-1.5F, -9.5F - f, 3.5F + f, 3, 3, 1, 0.0F);
      this.field_78116_c.func_78784_a(32, 4).func_78790_a(-0.5F, -10.5F - f, 3.5F + f, 1, 1, 1, 0.0F);
      this.field_78116_c.func_78784_a(36, 4).func_78790_a(-0.5F, -6.5F - f, 3.5F + f, 1, 1, 1, 0.0F);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
