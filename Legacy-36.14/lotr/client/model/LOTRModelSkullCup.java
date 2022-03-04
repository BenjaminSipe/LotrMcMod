package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelSkullCup extends ModelBase {
   private ModelRenderer base = new ModelRenderer(this, 0, 0);
   private ModelRenderer cup;

   public LOTRModelSkullCup() {
      this.base.func_78793_a(0.0F, -1.0F, 0.0F);
      this.base.func_78789_a(-3.0F, 0.0F, -3.0F, 6, 1, 6);
      this.base.func_78784_a(0, 7).func_78789_a(-1.0F, -3.0F, -1.0F, 2, 3, 2);
      this.cup = new ModelRenderer(this, 32, 0);
      this.cup.func_78793_a(0.0F, -5.0F, 0.0F);
      this.cup.func_78789_a(-4.0F, 0.0F, -4.0F, 8, 1, 8);
      this.cup.func_78784_a(0, 16).func_78789_a(-4.0F, -5.0F, -4.0F, 1, 5, 8);
      this.cup.func_78784_a(18, 23).func_78789_a(-3.0F, -5.0F, -4.0F, 6, 5, 1);
      this.cup.func_78784_a(32, 16).func_78789_a(3.0F, -5.0F, -4.0F, 1, 5, 8);
      this.cup.func_78784_a(50, 23).func_78789_a(-3.0F, -5.0F, 3.0F, 6, 5, 1);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.base.func_78785_a(f5);
      this.cup.func_78785_a(f5);
   }
}
