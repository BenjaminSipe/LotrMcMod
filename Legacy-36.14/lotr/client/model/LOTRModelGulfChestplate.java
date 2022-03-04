package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;

public class LOTRModelGulfChestplate extends LOTRModelBiped {
   public LOTRModelGulfChestplate() {
      this(0.0F);
   }

   public LOTRModelGulfChestplate(float f) {
      super(f);
      this.field_78115_e = new ModelRenderer(this, 16, 16);
      this.field_78115_e.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, f);
      this.field_78115_e.func_78784_a(16, 0);
      this.field_78115_e.func_78790_a(-4.0F, 0.0F, -3.0F - f, 8, 3, 1, 0.0F);
      ModelRenderer chestHorn1 = new ModelRenderer(this, 0, 0);
      chestHorn1.func_78793_a(-2.5F - f, 2.5F, -3.0F - f);
      chestHorn1.func_78790_a(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
      chestHorn1.field_78795_f = (float)Math.toRadians(-25.0D);
      chestHorn1.field_78808_h = (float)Math.toRadians(25.0D);
      this.field_78115_e.func_78792_a(chestHorn1);
      ModelRenderer chestHorn2 = new ModelRenderer(this, 0, 0);
      chestHorn2.func_78793_a(0.0F, 3.0F, -3.0F - f);
      chestHorn2.func_78790_a(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
      chestHorn2.field_78795_f = (float)Math.toRadians(-25.0D);
      chestHorn2.field_78808_h = (float)Math.toRadians(0.0D);
      this.field_78115_e.func_78792_a(chestHorn2);
      ModelRenderer chestHorn3 = new ModelRenderer(this, 0, 0);
      chestHorn3.func_78793_a(2.5F + f, 2.5F, -3.0F - f);
      chestHorn3.func_78790_a(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
      chestHorn3.field_78795_f = (float)Math.toRadians(-25.0D);
      chestHorn3.field_78808_h = (float)Math.toRadians(-25.0D);
      this.field_78115_e.func_78792_a(chestHorn3);
      this.field_78112_f = new ModelRenderer(this, 40, 16);
      this.field_78112_f.func_78793_a(-5.0F, 2.0F, 0.0F);
      this.field_78112_f.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, f);
      this.field_78112_f.func_78784_a(40, 0);
      this.field_78112_f.func_78790_a(-4.0F, -2.0F - f, -2.5F, 5, 1, 5, 0.0F);
      ModelRenderer rightHorn1 = new ModelRenderer(this, 4, 0);
      rightHorn1.func_78793_a(-2.5F, -2.0F - f, 0.0F);
      rightHorn1.func_78790_a(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
      rightHorn1.field_78808_h = (float)Math.toRadians(-10.0D);
      this.field_78112_f.func_78792_a(rightHorn1);
      ModelRenderer rightHorn2 = new ModelRenderer(this, 8, 0);
      rightHorn2.func_78793_a(-0.5F, -2.0F - f, 0.0F);
      rightHorn2.func_78790_a(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
      rightHorn2.field_78808_h = (float)Math.toRadians(-10.0D);
      this.field_78112_f.func_78792_a(rightHorn2);
      this.field_78113_g = new ModelRenderer(this, 40, 16);
      this.field_78113_g.func_78793_a(5.0F, 2.0F, 0.0F);
      this.field_78113_g.field_78809_i = true;
      this.field_78113_g.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, f);
      this.field_78113_g.func_78784_a(40, 0);
      this.field_78113_g.func_78790_a(-1.0F, -2.0F - f, -2.5F, 5, 1, 5, 0.0F);
      ModelRenderer leftHorn1 = new ModelRenderer(this, 4, 0);
      leftHorn1.func_78793_a(2.5F, -2.0F - f, 0.0F);
      leftHorn1.field_78809_i = true;
      leftHorn1.func_78790_a(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
      leftHorn1.field_78808_h = (float)Math.toRadians(10.0D);
      this.field_78113_g.func_78792_a(leftHorn1);
      ModelRenderer leftHorn2 = new ModelRenderer(this, 8, 0);
      leftHorn2.func_78793_a(0.5F, -2.0F - f, 0.0F);
      leftHorn2.field_78809_i = true;
      leftHorn2.func_78790_a(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
      leftHorn2.field_78808_h = (float)Math.toRadians(10.0D);
      this.field_78113_g.func_78792_a(leftHorn2);
      this.field_78116_c.field_78804_l.clear();
      this.field_78114_d.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }
}
