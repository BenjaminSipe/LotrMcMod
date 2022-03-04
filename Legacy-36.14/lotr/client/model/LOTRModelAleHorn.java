package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelAleHorn extends ModelBase {
   private ModelRenderer horn = new ModelRenderer(this, 28, 16);
   private ModelRenderer horn1;
   private ModelRenderer horn2;
   private ModelRenderer horn3;
   private ModelRenderer stand;

   public LOTRModelAleHorn() {
      this.horn.func_78793_a(-4.0F, -5.0F, 0.0F);
      this.horn.func_78789_a(-1.0F, -1.0F, -1.0F, 2, 6, 2);
      this.horn1 = new ModelRenderer(this, 16, 16);
      this.horn1.func_78793_a(0.0F, 0.0F, 0.0F);
      this.horn1.func_78789_a(-1.5F, -6.0F, -1.5F, 3, 6, 3);
      this.horn.func_78792_a(this.horn1);
      this.horn2 = new ModelRenderer(this, 0, 16);
      this.horn2.func_78793_a(0.0F, -5.0F, 0.0F);
      this.horn2.func_78789_a(-2.0F, -6.0F, -2.0F, 4, 6, 4);
      this.horn1.func_78792_a(this.horn2);
      this.horn3 = new ModelRenderer(this, 0, 0);
      this.horn3.func_78793_a(0.0F, -5.0F, 0.0F);
      this.horn3.func_78789_a(-2.5F, -1.0F, -2.5F, 5, 1, 5);
      this.horn3.func_78784_a(0, 6).func_78789_a(-2.5F, -6.0F, -1.5F, 1, 5, 3);
      this.horn3.func_78784_a(8, 8).func_78789_a(-2.5F, -6.0F, -2.5F, 5, 5, 1);
      this.horn3.func_78784_a(20, 6).func_78789_a(1.5F, -6.0F, -1.5F, 1, 5, 3);
      this.horn3.func_78784_a(28, 8).func_78789_a(-2.5F, -6.0F, 1.5F, 5, 5, 1);
      this.horn2.func_78792_a(this.horn3);
      this.horn.field_78808_h = (float)Math.toRadians(90.0D);
      this.horn1.field_78808_h = (float)Math.toRadians(-20.0D);
      this.horn2.field_78808_h = (float)Math.toRadians(-20.0D);
      this.horn3.field_78808_h = (float)Math.toRadians(-20.0D);
      this.stand = new ModelRenderer(this, 40, 16);
      this.stand.func_78793_a(0.0F, -1.0F, 0.0F);
      this.stand.func_78789_a(1.5F, -8.0F, -2.5F, 1, 9, 1);
      this.stand.func_78789_a(1.5F, -8.0F, 1.5F, 1, 9, 1);
      this.stand.func_78784_a(44, 16).func_78789_a(-2.5F, -6.0F, -0.5F, 1, 7, 1);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.horn.func_78785_a(f5);
      this.stand.func_78785_a(f5);
   }

   public void prepareLiquid(float f) {
      this.horn.func_78794_c(f);
      this.horn1.func_78794_c(f);
      this.horn2.func_78794_c(f);
      this.horn3.func_78794_c(f);
   }
}
