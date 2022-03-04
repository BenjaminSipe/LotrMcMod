package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelHarnedorChestplate extends LOTRModelBiped {
   public LOTRModelHarnedorChestplate() {
      this(0.0F);
   }

   public LOTRModelHarnedorChestplate(float f) {
      super(f);
      this.field_78115_e = new ModelRenderer(this, 16, 16);
      this.field_78115_e.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, f);
      this.field_78112_f = new ModelRenderer(this, 40, 16);
      this.field_78112_f.func_78793_a(-5.0F, 2.0F, 0.0F);
      this.field_78112_f.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, f);
      this.field_78112_f.func_78784_a(46, 0);
      this.field_78112_f.func_78790_a(-4.0F - f, -3.0F - f, -2.0F, 5, 1, 4, 0.0F);
      ModelRenderer rightBarbs1 = new ModelRenderer(this, 29, 0);
      rightBarbs1.func_78793_a(-1.5F, -2.5F - f, -2.0F);
      rightBarbs1.func_78790_a(-2.5F, 0.0F, -2.0F, 5, 0, 2, 0.0F);
      rightBarbs1.field_78795_f = (float)Math.toRadians(30.0D);
      this.field_78112_f.func_78792_a(rightBarbs1);
      ModelRenderer rightBarbs2 = new ModelRenderer(this, 29, 3);
      rightBarbs2.func_78793_a(-1.5F, -2.5F - f, 2.0F);
      rightBarbs2.func_78790_a(-2.5F, 0.0F, 0.0F, 5, 0, 2, 0.0F);
      rightBarbs2.field_78795_f = (float)Math.toRadians(-30.0D);
      this.field_78112_f.func_78792_a(rightBarbs2);
      this.field_78113_g = new ModelRenderer(this, 40, 16);
      this.field_78113_g.func_78793_a(5.0F, 2.0F, 0.0F);
      this.field_78113_g.field_78809_i = true;
      this.field_78113_g.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, f);
      this.field_78113_g.func_78784_a(46, 0);
      this.field_78113_g.func_78790_a(-1.0F + f, -3.0F - f, -2.0F, 5, 1, 4, 0.0F);
      ModelRenderer leftBarbs1 = new ModelRenderer(this, 29, 0);
      leftBarbs1.func_78793_a(1.5F, -2.5F - f, -2.0F);
      leftBarbs1.field_78809_i = true;
      leftBarbs1.func_78790_a(-2.5F, 0.0F, -2.0F, 5, 0, 2, 0.0F);
      leftBarbs1.field_78795_f = (float)Math.toRadians(30.0D);
      this.field_78113_g.func_78792_a(leftBarbs1);
      ModelRenderer leftBarbs2 = new ModelRenderer(this, 29, 3);
      leftBarbs2.func_78793_a(1.5F, -2.5F - f, 2.0F);
      leftBarbs2.field_78809_i = true;
      leftBarbs2.func_78790_a(-2.5F, 0.0F, 0.0F, 5, 0, 2, 0.0F);
      leftBarbs2.field_78795_f = (float)Math.toRadians(-30.0D);
      this.field_78113_g.func_78792_a(leftBarbs2);
      this.field_78116_c.field_78804_l.clear();
      this.field_78114_d.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
