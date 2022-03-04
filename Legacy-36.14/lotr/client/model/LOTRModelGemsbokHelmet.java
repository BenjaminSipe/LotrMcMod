package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGemsbokHelmet extends LOTRModelBiped {
   private ModelRenderer hornRight;
   private ModelRenderer hornLeft;

   public LOTRModelGemsbokHelmet() {
      this(0.0F);
   }

   public LOTRModelGemsbokHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.hornRight = new ModelRenderer(this, 32, 0);
      this.hornRight.func_78789_a(-4.9F, -7.0F, 7.5F, 1, 1, 13);
      this.hornLeft = new ModelRenderer(this, 32, 0);
      this.hornLeft.field_78809_i = true;
      this.hornLeft.func_78789_a(3.9F, -7.0F, 7.5F, 1, 1, 13);
      this.hornRight.field_78795_f = this.hornLeft.field_78795_f = (float)Math.toRadians(20.0D);
      this.field_78116_c.func_78792_a(this.hornRight);
      this.field_78116_c.func_78792_a(this.hornLeft);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
