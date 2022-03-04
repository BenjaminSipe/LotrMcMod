package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelNearHaradWarlordHelmet extends LOTRModelBiped {
   private ModelRenderer stickRight;
   private ModelRenderer stickCentre;
   private ModelRenderer stickLeft;

   public LOTRModelNearHaradWarlordHelmet() {
      this(0.0F);
   }

   public LOTRModelNearHaradWarlordHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78784_a(6, 24).func_78790_a(-2.5F, -3.0F, 4.1F, 5, 3, 2, 0.0F);
      this.field_78116_c.func_78784_a(0, 16).func_78790_a(-9.0F, -16.0F, 5.5F, 18, 8, 0, 0.0F);
      this.stickRight = new ModelRenderer(this, 36, 0);
      this.stickRight.func_78790_a(-0.5F, -19.0F, 5.0F, 1, 18, 1, 0.0F);
      this.stickRight.func_78784_a(0, 24).func_78790_a(-1.5F, -24.0F, 5.5F, 3, 5, 0, 0.0F);
      this.stickRight.field_78808_h = (float)Math.toRadians(-28.0D);
      this.field_78116_c.func_78792_a(this.stickRight);
      this.stickCentre = new ModelRenderer(this, 36, 0);
      this.stickCentre.func_78790_a(-0.5F, -19.0F, 5.0F, 1, 18, 1, 0.0F);
      this.stickCentre.func_78784_a(0, 24).func_78790_a(-1.5F, -24.0F, 5.5F, 3, 5, 0, 0.0F);
      this.stickCentre.field_78808_h = (float)Math.toRadians(0.0D);
      this.field_78116_c.func_78792_a(this.stickCentre);
      this.stickLeft = new ModelRenderer(this, 36, 0);
      this.stickLeft.func_78790_a(-0.5F, -19.0F, 5.0F, 1, 18, 1, 0.0F);
      this.stickLeft.func_78784_a(0, 24).func_78790_a(-1.5F, -24.0F, 5.5F, 3, 5, 0, 0.0F);
      this.stickLeft.field_78808_h = (float)Math.toRadians(28.0D);
      this.field_78116_c.func_78792_a(this.stickLeft);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
