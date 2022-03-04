package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelSwanHelmet extends LOTRModelBiped {
   private ModelRenderer wingRight;
   private ModelRenderer wingLeft;

   public LOTRModelSwanHelmet() {
      this(0.0F);
   }

   public LOTRModelSwanHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78784_a(32, 0).func_78790_a(-0.5F, -9.0F, -3.5F, 1, 1, 7, f);
      this.wingRight = new ModelRenderer(this, 0, 16);
      this.wingRight.func_78790_a(-4.0F - f, -6.0F, 1.0F + f, 1, 1, 9, 0.0F);
      this.wingRight.func_78784_a(20, 16).func_78790_a(-3.5F - f, -5.0F, 1.9F + f, 0, 6, 8, 0.0F);
      this.wingLeft = new ModelRenderer(this, 0, 16);
      this.wingLeft.field_78809_i = true;
      this.wingLeft.func_78790_a(3.0F + f, -6.0F, 1.0F + f, 1, 1, 9, 0.0F);
      this.wingLeft.func_78784_a(20, 16).func_78790_a(3.5F + f, -5.0F, 1.9F + f, 0, 6, 8, 0.0F);
      this.field_78116_c.func_78792_a(this.wingRight);
      this.field_78116_c.func_78792_a(this.wingLeft);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      float wingYaw = (float)Math.toRadians(-25.0D);
      float wingPitch = (float)Math.toRadians(20.0D);
      this.wingRight.field_78796_g = wingYaw;
      this.wingLeft.field_78796_g = -wingYaw;
      this.wingRight.field_78795_f = wingPitch;
      this.wingLeft.field_78795_f = wingPitch;
   }
}
