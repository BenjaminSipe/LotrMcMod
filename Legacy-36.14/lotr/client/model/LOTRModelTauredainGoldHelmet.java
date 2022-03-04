package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelTauredainGoldHelmet extends LOTRModelBiped {
   public LOTRModelTauredainGoldHelmet() {
      this(0.0F);
   }

   public LOTRModelTauredainGoldHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      ModelRenderer crest = new ModelRenderer(this, 32, 0);
      crest.func_78793_a(0.0F, -f, 0.0F);
      crest.func_78790_a(-7.0F, -20.0F, 0.0F, 14, 12, 0, 0.0F);
      crest.field_78795_f = (float)Math.toRadians(-4.0D);
      ModelRenderer tusks1 = new ModelRenderer(this, 0, 16);
      tusks1.func_78793_a(-3.5F - f, 0.0F + f, -4.0F - f);
      tusks1.func_78790_a(0.0F, -6.0F, -5.0F, 0, 6, 6, 0.0F);
      tusks1.field_78795_f = (float)Math.toRadians(20.0D);
      tusks1.field_78796_g = (float)Math.toRadians(30.0D);
      ModelRenderer tusks2 = new ModelRenderer(this, 0, 16);
      tusks2.func_78793_a(-3.5F - f, 0.0F + f, -4.0F - f);
      tusks2.func_78790_a(0.0F, -6.0F, -5.0F, 0, 6, 6, 0.0F);
      tusks2.field_78795_f = (float)Math.toRadians(20.0D);
      tusks2.field_78796_g = (float)Math.toRadians(-20.0D);
      ModelRenderer tusks3 = new ModelRenderer(this, 0, 16);
      tusks3.func_78793_a(3.5F + f, 0.0F + f, -4.0F - f);
      tusks3.func_78790_a(0.0F, -6.0F, -5.0F, 0, 6, 6, 0.0F);
      tusks3.field_78795_f = (float)Math.toRadians(20.0D);
      tusks3.field_78796_g = (float)Math.toRadians(20.0D);
      ModelRenderer tusks4 = new ModelRenderer(this, 0, 16);
      tusks4.func_78793_a(3.5F + f, 0.0F + f, -4.0F - f);
      tusks4.func_78790_a(0.0F, -6.0F, -5.0F, 0, 6, 6, 0.0F);
      tusks4.field_78795_f = (float)Math.toRadians(20.0D);
      tusks4.field_78796_g = (float)Math.toRadians(-30.0D);
      this.field_78116_c.func_78792_a(crest);
      this.field_78116_c.func_78792_a(tusks1);
      this.field_78116_c.func_78792_a(tusks2);
      this.field_78116_c.func_78792_a(tusks3);
      this.field_78116_c.func_78792_a(tusks4);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
