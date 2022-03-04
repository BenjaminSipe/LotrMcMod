package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelBlackUrukHelmet extends LOTRModelBiped {
   private ModelRenderer crest;

   public LOTRModelBlackUrukHelmet() {
      this(0.0F);
   }

   public LOTRModelBlackUrukHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.crest = new ModelRenderer(this, 32, 0);
      this.crest.func_78790_a(-8.0F, -16.0F, -3.0F, 16, 10, 0, 0.0F);
      this.crest.field_78795_f = (float)Math.toRadians(-20.0D);
      this.field_78116_c.func_78792_a(this.crest);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
