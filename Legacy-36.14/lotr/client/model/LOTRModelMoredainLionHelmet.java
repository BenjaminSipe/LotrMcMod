package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelMoredainLionHelmet extends LOTRModelBiped {
   private ModelRenderer panelRight;
   private ModelRenderer panelLeft;
   private ModelRenderer panelBack;
   private ModelRenderer panelTop;

   public LOTRModelMoredainLionHelmet() {
      this(0.0F);
   }

   public LOTRModelMoredainLionHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78784_a(34, 16).func_78790_a(-4.5F, -9.0F, -2.5F, 9, 2, 5, f);
      this.field_78116_c.func_78784_a(0, 17).func_78790_a(-2.5F, -10.0F, -7.0F, 5, 3, 12, f);
      this.field_78116_c.func_78784_a(34, 23).func_78790_a(-1.0F, -10.4F, -7.2F, 2, 2, 7, f);
      this.field_78116_c.func_78784_a(0, 0).func_78790_a(-2.0F, -8.0F, -6.8F - f, 1, 3, 1, 0.0F);
      this.field_78116_c.field_78809_i = true;
      this.field_78116_c.func_78790_a(1.0F, -8.0F, -6.8F - f, 1, 3, 1, 0.0F);
      this.panelRight = new ModelRenderer(this, 32, 0);
      this.panelRight.func_78790_a(-5.0F - f, -8.0F, -3.0F, 0, 8, 8, 0.0F);
      this.panelRight.field_78808_h = (float)Math.toRadians(4.0D);
      this.panelLeft = new ModelRenderer(this, 32, 0);
      this.panelLeft.field_78809_i = true;
      this.panelLeft.func_78790_a(5.0F + f, -8.0F, -3.0F, 0, 8, 8, 0.0F);
      this.panelLeft.field_78808_h = (float)Math.toRadians(-4.0D);
      this.panelBack = new ModelRenderer(this, 44, 0);
      this.panelBack.func_78790_a(-4.0F, -8.0F, 4.8F + f, 8, 10, 0, 0.0F);
      this.panelBack.field_78795_f = (float)Math.toRadians(4.0D);
      this.panelTop = new ModelRenderer(this, 52, 25);
      this.panelTop.func_78790_a(-2.5F, -16.0F - f, -2.0F, 5, 7, 0, 0.0F);
      this.panelTop.field_78795_f = (float)Math.toRadians(-10.0D);
      this.field_78116_c.func_78792_a(this.panelRight);
      this.field_78116_c.func_78792_a(this.panelLeft);
      this.field_78116_c.func_78792_a(this.panelBack);
      this.field_78116_c.func_78792_a(this.panelTop);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
