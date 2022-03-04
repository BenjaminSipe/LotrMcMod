package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHighElvenHelmet extends LOTRModelBiped {
   private ModelRenderer crest;

   public LOTRModelHighElvenHelmet() {
      this(0.0F);
   }

   public LOTRModelHighElvenHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-0.5F, -11.0F, -2.0F, 1, 3, 1, 0.0F);
      this.field_78116_c.func_78784_a(0, 4).func_78790_a(-0.5F, -10.0F, 2.0F, 1, 2, 1, 0.0F);
      this.crest = new ModelRenderer(this, 32, 0);
      this.crest.func_78790_a(-1.0F, -11.0F, -8.0F, 2, 1, 11, 0.0F);
      this.crest.func_78784_a(32, 12).func_78790_a(-1.0F, -10.0F, -8.0F, 2, 1, 1, 0.0F);
      this.crest.field_78795_f = (float)Math.toRadians(-16.0D);
      this.field_78116_c.func_78792_a(this.crest);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
