package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelBarrowWight extends LOTRModelBiped {
   public LOTRModelBarrowWight() {
      this(0.0F);
   }

   public LOTRModelBarrowWight(float f) {
      super(f);
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, -8.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78784_a(32, 0).func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f + 1.0F);
      this.field_78115_e = new ModelRenderer(this, 0, 16);
      this.field_78115_e.func_78793_a(0.0F, -8.0F, 0.0F);
      this.field_78115_e.func_78790_a(-4.0F, 0.0F, -3.0F, 8, 32, 6, f);
      this.field_78112_f = new ModelRenderer(this, 28, 16);
      this.field_78112_f.func_78793_a(-6.0F, -5.0F, 0.0F);
      this.field_78112_f.func_78790_a(-3.0F, -2.0F, -2.5F, 5, 9, 5, f);
      this.field_78112_f.func_78784_a(28, 30).func_78790_a(-2.0F, 7.0F, -1.5F, 3, 10, 3, f);
      this.field_78113_g = new ModelRenderer(this, 28, 16);
      this.field_78113_g.func_78793_a(6.0F, -5.0F, 0.0F);
      this.field_78113_g.field_78809_i = true;
      this.field_78113_g.func_78790_a(-2.0F, -2.0F, -2.5F, 5, 9, 5, f);
      this.field_78113_g.func_78784_a(28, 30).func_78790_a(-1.0F, 7.0F, -1.5F, 3, 10, 3, f);
      this.field_78114_d.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      f1 = 0.0F;
      super.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.field_78113_g.field_78795_f = this.field_78112_f.field_78795_f;
      this.field_78113_g.field_78796_g = -this.field_78112_f.field_78796_g;
      this.field_78113_g.field_78808_h = -this.field_78112_f.field_78808_h;
   }
}
