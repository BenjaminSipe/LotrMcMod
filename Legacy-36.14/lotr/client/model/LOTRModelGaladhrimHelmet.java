package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGaladhrimHelmet extends LOTRModelBiped {
   public LOTRModelGaladhrimHelmet() {
      this(0.0F);
   }

   public LOTRModelGaladhrimHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      ModelRenderer horn = new ModelRenderer(this, 32, 0);
      horn.func_78790_a(-0.5F, -9.0F - f, 2.0F - f, 1, 3, 3, 0.0F);
      horn.func_78784_a(32, 6).func_78790_a(-0.5F, -10.0F - f, 3.5F - f, 1, 1, 3, 0.0F);
      horn.func_78784_a(32, 10).func_78790_a(-0.5F, -11.0F - f, 5.5F - f, 1, 1, 4, 0.0F);
      horn.field_78795_f = (float)Math.toRadians(45.0D);
      this.field_78116_c.func_78792_a(horn);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
