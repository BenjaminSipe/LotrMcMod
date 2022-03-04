package lotr.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;

public class LOTRModelSkeleton extends ModelZombie {
   public LOTRModelSkeleton() {
      this(0.0F);
   }

   public LOTRModelSkeleton(float f) {
      super(f, 0.0F, 64, 32);
      if (f == 0.0F) {
         this.field_78112_f = new ModelRenderer(this, 40, 16);
         this.field_78112_f.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 12, 2, f);
         this.field_78112_f.func_78793_a(-5.0F, 2.0F, 0.0F);
         this.field_78113_g = new ModelRenderer(this, 40, 16);
         this.field_78113_g.field_78809_i = true;
         this.field_78113_g.func_78790_a(-1.0F, -2.0F, -1.0F, 2, 12, 2, f);
         this.field_78113_g.func_78793_a(5.0F, 2.0F, 0.0F);
         this.field_78123_h = new ModelRenderer(this, 0, 16);
         this.field_78123_h.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 12, 2, f);
         this.field_78123_h.func_78793_a(-2.0F, 12.0F, 0.0F);
         this.field_78124_i = new ModelRenderer(this, 0, 16);
         this.field_78124_i.field_78809_i = true;
         this.field_78124_i.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 12, 2, f);
         this.field_78124_i.func_78793_a(2.0F, 12.0F, 0.0F);
      }

   }
}
