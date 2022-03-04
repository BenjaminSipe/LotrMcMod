package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelRohanMarshalHelmet extends LOTRModelBiped {
   private ModelRenderer[] manes;

   public LOTRModelRohanMarshalHelmet() {
      this(0.0F);
   }

   public LOTRModelRohanMarshalHelmet(float f) {
      super(f);
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_78790_a(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
      this.field_78116_c.func_78784_a(0, 16).func_78790_a(-1.0F, -11.5F - f, -4.5F - f, 2, 7, 6, 0.0F);
      this.manes = new ModelRenderer[3];

      for(int i = 0; i < this.manes.length; ++i) {
         ModelRenderer mane = new ModelRenderer(this, 32, 0);
         this.manes[i] = mane;
         mane.func_78793_a(0.0F, -f, f);
         mane.func_78790_a(0.0F, -11.0F, -1.0F, 0, 14, 12, 0.0F);
         this.field_78116_c.func_78792_a(mane);
      }

      this.field_78114_d.field_78804_l.clear();
      this.field_78115_e.field_78804_l.clear();
      this.field_78112_f.field_78804_l.clear();
      this.field_78113_g.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      float mid = (float)this.manes.length / 2.0F - 0.5F;

      for(int i = 0; i < this.manes.length; ++i) {
         ModelRenderer mane = this.manes[i];
         mane.field_78795_f = (mid - Math.abs((float)i - mid)) / mid * 0.22F;
         mane.field_78796_g = ((float)i - mid) / mid * 0.17F;
         mane.field_78795_f += MathHelper.func_76126_a(f * 0.4F) * f1 * 0.2F;
      }

   }
}
