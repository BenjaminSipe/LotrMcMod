package lotr.client.model;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelBoar extends ModelPig {
   private ModelRenderer tusks;

   public LOTRModelBoar() {
      this(0.0F);
   }

   public LOTRModelBoar(float f) {
      super(f);
      this.field_78150_a.func_78784_a(24, 0).func_78790_a(-3.0F, 0.0F, -10.0F, 6, 4, 2, f);
      this.field_78150_a.func_78784_a(40, 0).func_78790_a(-5.0F, -5.0F, -6.0F, 1, 2, 2, f);
      this.field_78150_a.field_78809_i = true;
      this.field_78150_a.func_78790_a(4.0F, -5.0F, -6.0F, 1, 2, 2, f);
      this.tusks = new ModelRenderer(this, 0, 0);
      this.tusks.func_78790_a(-4.0F, 2.0F, -11.0F, 1, 1, 2, f);
      this.tusks.func_78784_a(1, 1).func_78790_a(-4.0F, 1.0F, -11.5F, 1, 1, 1, f);
      this.tusks.field_78809_i = true;
      this.tusks.func_78784_a(0, 0).func_78790_a(3.0F, 2.0F, -11.0F, 1, 1, 2, f);
      this.tusks.func_78784_a(1, 1).func_78790_a(3.0F, 1.0F, -11.5F, 1, 1, 1, f);
      this.field_78150_a.func_78792_a(this.tusks);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.tusks.field_78806_j = !this.field_78091_s;
      super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
   }
}
