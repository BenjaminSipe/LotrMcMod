package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelWizardHat extends LOTRModelBiped {
   private ModelRenderer hatBrim;
   private ModelRenderer hat1;
   private ModelRenderer hat2;
   private ModelRenderer hat3;

   public LOTRModelWizardHat() {
      this(0.0F);
   }

   public LOTRModelWizardHat(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.hatBrim = new ModelRenderer(this, 0, 17);
      this.hatBrim.func_78793_a(0.0F, 0.0F, 0.0F);
      this.hatBrim.func_78789_a(-7.0F, -8.0F - f, -7.0F, 14, 1, 14);
      this.hat1 = new ModelRenderer(this, 32, 3);
      this.hat1.func_78793_a(0.0F, -8.0F - f, 0.0F);
      this.hat1.func_78789_a(-4.0F, -5.0F, -4.0F, 8, 5, 8);
      this.hat2 = new ModelRenderer(this, 11, 7);
      this.hat2.func_78793_a(0.0F, -4.0F, 0.0F);
      this.hat2.func_78789_a(-2.5F, -4.0F, -2.5F, 5, 4, 5);
      this.hat3 = new ModelRenderer(this, 0, 22);
      this.hat3.func_78793_a(0.0F, -3.5F, 0.0F);
      this.hat3.func_78789_a(-1.5F, -3.0F, -1.0F, 3, 3, 3);
      this.field_78116_c.func_78792_a(this.hatBrim);
      this.hatBrim.func_78792_a(this.hat1);
      this.hat1.func_78792_a(this.hat2);
      this.hat2.func_78792_a(this.hat3);
      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.field_78116_c.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.hat2.field_78795_f = (float)Math.toRadians(-(10.0D + (double)f1 * 10.0D));
      this.hat3.field_78795_f = (float)Math.toRadians(-(10.0D + (double)f1 * 10.0D));
   }
}
