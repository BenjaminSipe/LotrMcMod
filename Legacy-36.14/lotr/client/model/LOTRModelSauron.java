package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelSauron extends LOTRModelBiped {
   private ModelRenderer bipedCape;

   public LOTRModelSauron() {
      this.field_78116_c = (new ModelRenderer(this, 0, 0)).func_78787_b(64, 64);
      this.field_78116_c.func_78789_a(-4.0F, -8.0F, -4.0F, 8, 8, 8);
      this.field_78116_c.func_78793_a(0.0F, -12.0F, 0.0F);
      this.field_78116_c.func_78784_a(32, 0).func_78789_a(-0.5F, -15.0F, -3.5F, 1, 7, 1);
      this.field_78116_c.func_78784_a(32, 0).func_78789_a(-2.5F, -13.0F, -3.5F, 1, 5, 1);
      this.field_78116_c.func_78784_a(32, 0).func_78789_a(1.5F, -13.0F, -3.5F, 1, 5, 1);
      this.field_78116_c.func_78784_a(32, 0).func_78789_a(-0.5F, -16.0F, 2.5F, 1, 8, 1);
      this.field_78116_c.func_78784_a(32, 0).func_78789_a(-3.5F, -16.0F, -0.5F, 1, 8, 1);
      this.field_78116_c.func_78784_a(32, 0).func_78789_a(2.5F, -16.0F, -0.5F, 1, 8, 1);
      this.field_78115_e = (new ModelRenderer(this, 40, 42)).func_78787_b(64, 64);
      this.field_78115_e.func_78789_a(-4.0F, 0.0F, -2.0F, 8, 18, 4);
      this.field_78115_e.func_78793_a(0.0F, -12.0F, 0.0F);
      this.field_78112_f = (new ModelRenderer(this, 0, 43)).func_78787_b(64, 64);
      this.field_78112_f.func_78789_a(-3.0F, -2.0F, -2.0F, 4, 17, 4);
      this.field_78112_f.func_78793_a(-5.0F, -8.0F, 0.0F);
      this.field_78112_f.func_78784_a(16, 52).func_78789_a(-4.0F, -3.0F, -3.0F, 6, 6, 6);
      this.field_78113_g = (new ModelRenderer(this, 0, 43)).func_78787_b(64, 64);
      this.field_78113_g.field_78809_i = true;
      this.field_78113_g.func_78789_a(-1.0F, -2.0F, -2.0F, 4, 17, 4);
      this.field_78113_g.func_78793_a(5.0F, -8.0F, 0.0F);
      this.field_78113_g.func_78784_a(16, 52).func_78789_a(-2.0F, -3.0F, -3.0F, 6, 6, 6);
      this.field_78123_h = (new ModelRenderer(this, 0, 16)).func_78787_b(64, 64);
      this.field_78123_h.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 18, 4);
      this.field_78123_h.func_78793_a(-2.0F, 6.0F, 0.0F);
      this.field_78124_i = (new ModelRenderer(this, 0, 16)).func_78787_b(64, 64);
      this.field_78124_i.field_78809_i = true;
      this.field_78124_i.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 18, 4);
      this.field_78124_i.func_78793_a(2.0F, 6.0F, 0.0F);
      this.bipedCape = (new ModelRenderer(this, 38, 0)).func_78787_b(64, 64);
      this.bipedCape.func_78789_a(-6.0F, 1.0F, 1.0F, 12, 32, 1);
      this.bipedCape.func_78793_a(0.0F, -12.0F, 0.0F);
      this.bipedCape.field_78795_f = 0.15F;
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.field_78116_c.func_78785_a(f5);
      this.field_78115_e.func_78785_a(f5);
      this.field_78112_f.func_78785_a(f5);
      this.field_78113_g.func_78785_a(f5);
      this.field_78123_h.func_78785_a(f5);
      this.field_78124_i.func_78785_a(f5);
      this.bipedCape.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      if (this.field_78117_n) {
         this.field_78123_h.field_78797_d = 3.0F;
         this.field_78124_i.field_78797_d = 3.0F;
         this.field_78116_c.field_78797_d = -11.0F;
      } else {
         this.field_78123_h.field_78797_d = 6.0F;
         this.field_78124_i.field_78797_d = 6.0F;
         this.field_78116_c.field_78797_d = -12.0F;
      }

   }
}
