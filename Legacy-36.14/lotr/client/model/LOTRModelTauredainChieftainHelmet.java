package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelTauredainChieftainHelmet extends LOTRModelBiped {
   public LOTRModelTauredainChieftainHelmet() {
      this(0.0F);
   }

   public LOTRModelTauredainChieftainHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78784_a(32, 0).func_78790_a(-5.0F, -9.0F, 0.0F, 10, 6, 3, f);
      ModelRenderer crest = new ModelRenderer(this, 0, 16);
      crest.func_78793_a(0.0F, -f, 0.0F);
      crest.func_78790_a(-8.0F, -23.0F, 0.0F, 16, 14, 0, 0.0F);
      crest.field_78795_f = (float)Math.toRadians(-10.0D);
      this.field_78116_c.func_78792_a(crest);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
