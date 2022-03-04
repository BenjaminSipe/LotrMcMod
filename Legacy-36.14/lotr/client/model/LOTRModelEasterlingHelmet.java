package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelEasterlingHelmet extends LOTRModelBiped {
   public LOTRModelEasterlingHelmet() {
      this(0.0F, false);
   }

   public LOTRModelEasterlingHelmet(float f, boolean kineHorns) {
      super(f);
      if (kineHorns) {
         this.field_78090_t = 64;
         this.field_78089_u = 64;
      } else {
         this.field_78090_t = 64;
         this.field_78089_u = 32;
      }

      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78784_a(0, 16).func_78790_a(-5.5F, -8.5F - f, -5.5F, 11, 2, 11, 0.0F);
      this.field_78116_c.func_78784_a(32, 8).func_78790_a(-3.5F, -9.5F - f, -3.5F, 7, 1, 7, 0.0F);
      this.field_78116_c.func_78784_a(50, 16).func_78790_a(0.0F, -10.5F - f, -4.5F - f, 0, 3, 4, 0.0F);
      ModelRenderer horn = new ModelRenderer(this, 44, 16);
      horn.func_78793_a(0.0F, 0.0F, 0.0F);
      horn.func_78790_a(-0.5F, -14.0F - f, -2.0F - f, 1, 8, 2, 0.0F);
      horn.field_78795_f = (float)Math.toRadians(20.0D);
      this.field_78116_c.func_78792_a(horn);
      this.field_78116_c.func_78784_a(24, 0).func_78790_a(-1.0F, -8.0F - f, 4.0F + f, 2, 4, 1, 0.0F);
      this.field_78116_c.func_78784_a(32, 2).func_78790_a(-6.0F, -12.0F - f, 5.0F + f, 12, 4, 0, 0.0F);
      ModelRenderer crest = new ModelRenderer(this, 32, 0);
      crest.func_78793_a(0.0F, -12.0F - f, 5.0F + f);
      crest.func_78790_a(-6.0F, -2.0F, 0.0F, 12, 2, 0, 0.0F);
      crest.field_78795_f = (float)Math.toRadians(30.0D);
      this.field_78116_c.func_78792_a(crest);
      if (kineHorns) {
         ModelRenderer kineHornRight = new ModelRenderer(this, 0, 32);
         kineHornRight.func_78793_a(-1.0F - f, -8.0F - f, 0.0F);
         kineHornRight.func_78790_a(-7.0F, -1.5F, -1.5F, 7, 3, 3, 0.0F);
         ModelRenderer kineHornRight1 = new ModelRenderer(this, 0, 38);
         kineHornRight1.func_78793_a(-7.0F, 0.0F, 0.0F);
         kineHornRight1.func_78790_a(-5.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
         ModelRenderer kineHornRight2 = new ModelRenderer(this, 0, 42);
         kineHornRight2.func_78793_a(-5.0F, 0.0F, 0.0F);
         kineHornRight2.func_78790_a(-3.0F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
         ModelRenderer kineHornLeft = new ModelRenderer(this, 0, 32);
         kineHornLeft.field_78809_i = true;
         kineHornLeft.func_78793_a(1.0F + f, -8.0F - f, 0.0F);
         kineHornLeft.func_78790_a(0.0F, -1.5F, -1.5F, 7, 3, 3, 0.0F);
         ModelRenderer kineHornLeft1 = new ModelRenderer(this, 0, 38);
         kineHornLeft1.field_78809_i = true;
         kineHornLeft1.func_78793_a(7.0F, 0.0F, 0.0F);
         kineHornLeft1.func_78790_a(-1.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
         ModelRenderer kineHornLeft2 = new ModelRenderer(this, 0, 42);
         kineHornLeft2.field_78809_i = true;
         kineHornLeft2.func_78793_a(5.0F, 0.0F, 0.0F);
         kineHornLeft2.func_78790_a(-1.0F, -0.5F, -0.5F, 4, 1, 1, 0.0F);
         kineHornRight.field_78808_h = (float)Math.toRadians(40.0D);
         kineHornLeft.field_78808_h = -kineHornRight.field_78808_h;
         kineHornRight1.field_78808_h = (float)Math.toRadians(-30.0D);
         kineHornLeft1.field_78808_h = -kineHornRight1.field_78808_h;
         kineHornRight2.field_78808_h = (float)Math.toRadians(-30.0D);
         kineHornLeft2.field_78808_h = -kineHornRight2.field_78808_h;
         this.field_78116_c.func_78792_a(kineHornRight);
         kineHornRight.func_78792_a(kineHornRight1);
         kineHornRight1.func_78792_a(kineHornRight2);
         this.field_78116_c.func_78792_a(kineHornLeft);
         kineHornLeft.func_78792_a(kineHornLeft1);
         kineHornLeft1.func_78792_a(kineHornLeft2);
      }

      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
