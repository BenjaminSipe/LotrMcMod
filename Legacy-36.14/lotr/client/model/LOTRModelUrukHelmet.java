package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelUrukHelmet extends LOTRModelBiped {
   private ModelRenderer crest;
   private ModelRenderer jaw;

   public LOTRModelUrukHelmet() {
      this(0.0F);
   }

   public LOTRModelUrukHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.crest = new ModelRenderer(this, 0, 22);
      this.crest.func_78790_a(-10.0F, -16.0F, -1.0F, 20, 10, 0, 0.0F);
      this.crest.field_78795_f = (float)Math.toRadians(-10.0D);
      this.field_78116_c.func_78792_a(this.crest);
      this.jaw = new ModelRenderer(this, 0, 16);
      this.jaw.func_78790_a(-6.0F, 2.0F, -4.0F, 12, 6, 0, 0.0F);
      this.jaw.field_78795_f = (float)Math.toRadians(-60.0D);
      this.field_78116_c.func_78792_a(this.jaw);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
