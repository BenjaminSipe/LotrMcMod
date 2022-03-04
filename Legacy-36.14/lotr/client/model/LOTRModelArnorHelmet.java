package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelArnorHelmet extends LOTRModelBiped {
   public LOTRModelArnorHelmet() {
      this(0.0F);
   }

   public LOTRModelArnorHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78784_a(32, 0).func_78790_a(-4.5F - f, -13.0F - f, -1.0F, 1, 8, 1, 0.0F);
      this.field_78116_c.func_78784_a(36, 0).func_78790_a(-4.5F - f, -12.0F - f, 0.0F, 1, 7, 1, 0.0F);
      this.field_78116_c.func_78784_a(40, 0).func_78790_a(-4.5F - f, -11.0F - f, 1.0F, 1, 5, 1, 0.0F);
      this.field_78116_c.field_78809_i = true;
      this.field_78116_c.func_78784_a(32, 0).func_78790_a(3.5F + f, -13.0F - f, -1.0F, 1, 8, 1, 0.0F);
      this.field_78116_c.func_78784_a(36, 0).func_78790_a(3.5F + f, -12.0F - f, 0.0F, 1, 7, 1, 0.0F);
      this.field_78116_c.func_78784_a(40, 0).func_78790_a(3.5F + f, -11.0F - f, 1.0F, 1, 5, 1, 0.0F);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
