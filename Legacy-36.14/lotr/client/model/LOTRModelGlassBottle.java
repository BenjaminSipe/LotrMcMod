package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelGlassBottle extends ModelBase {
   private ModelRenderer bottle = new ModelRenderer(this, 0, 0);

   public LOTRModelGlassBottle() {
      this.bottle.func_78793_a(0.0F, -1.0F, 0.0F);
      this.bottle.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 1, 4);
      this.bottle.func_78784_a(0, 6).func_78789_a(-3.0F, -4.0F, -2.0F, 1, 4, 4);
      this.bottle.func_78784_a(10, 9).func_78789_a(-2.0F, -4.0F, -3.0F, 4, 4, 1);
      this.bottle.func_78784_a(20, 6).func_78789_a(2.0F, -4.0F, -2.0F, 1, 4, 4);
      this.bottle.func_78784_a(30, 9).func_78789_a(-2.0F, -4.0F, 2.0F, 4, 4, 1);
      this.bottle.func_78784_a(16, 0).func_78789_a(-2.0F, -5.0F, -2.0F, 4, 1, 4);
      this.bottle.func_78784_a(0, 16).func_78789_a(-1.0F, -6.0F, -1.0F, 2, 1, 2);
      this.bottle.func_78784_a(0, 19).func_78789_a(-1.5F, -7.0F, -1.5F, 3, 1, 3);
      this.bottle.func_78784_a(12, 19).func_78789_a(-1.0F, -8.5F, -1.0F, 2, 2, 2);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.bottle.func_78785_a(f5);
   }
}
