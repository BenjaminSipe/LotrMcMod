package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelBlock extends ModelBase {
   private ModelRenderer block = new ModelRenderer(this, 0, 0);

   public LOTRModelBlock(float f) {
      this.block.func_78790_a(-8.0F, -8.0F, -8.0F, 16, 16, 16, f);
      this.block.func_78793_a(0.0F, 0.0F, 0.0F);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.block.func_78785_a(f5);
   }
}
