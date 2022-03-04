package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelGoblet extends ModelBase {
   private ModelRenderer base = new ModelRenderer(this, 0, 0);
   private ModelRenderer cup;

   public LOTRModelGoblet() {
      this.base.func_78793_a(0.0F, -1.0F, 0.0F);
      this.base.func_78789_a(-2.5F, 0.0F, -2.5F, 5, 1, 5);
      this.base.func_78784_a(0, 6).func_78789_a(-0.5F, -3.0F, -0.5F, 1, 3, 1);
      this.cup = new ModelRenderer(this, 0, 12);
      this.cup.func_78793_a(0.0F, -5.0F, 0.0F);
      this.cup.func_78789_a(-2.5F, 0.0F, -2.5F, 5, 1, 5);
      this.cup.func_78784_a(0, 18).func_78789_a(-2.5F, -4.0F, -2.5F, 1, 4, 5);
      this.cup.func_78784_a(12, 22).func_78789_a(-1.5F, -4.0F, -2.5F, 3, 4, 1);
      this.cup.func_78784_a(20, 18).func_78789_a(1.5F, -4.0F, -2.5F, 1, 4, 5);
      this.cup.func_78784_a(32, 22).func_78789_a(-1.5F, -4.0F, 1.5F, 3, 4, 1);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.base.func_78785_a(f5);
      this.cup.func_78785_a(f5);
   }
}
