package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelMidge extends ModelBase {
   private ModelRenderer body = new ModelRenderer(this, 0, 0);
   private ModelRenderer rightWing;
   private ModelRenderer leftWing;

   public LOTRModelMidge() {
      this.body.func_78789_a(-0.5F, -1.5F, -0.5F, 1, 5, 1);
      this.rightWing = new ModelRenderer(this, 0, 6);
      this.rightWing.func_78789_a(-5.0F, -2.5F, 0.0F, 5, 5, 1);
      this.leftWing = new ModelRenderer(this, 0, 6);
      this.leftWing.field_78809_i = true;
      this.leftWing.func_78789_a(0.0F, -2.5F, 0.0F, 5, 5, 1);
      this.body.func_78792_a(this.rightWing);
      this.body.func_78792_a(this.leftWing);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.body.func_78793_a(0.0F, 8.0F, 0.0F);
      this.body.field_78795_f = 0.7853982F + MathHelper.func_76134_b(f2 * 0.1F) * 0.15F;
      this.rightWing.field_78796_g = MathHelper.func_76134_b(f2 * 4.0F) * 3.1415927F * 0.25F;
      this.leftWing.field_78796_g = -this.rightWing.field_78796_g;
      this.body.func_78785_a(f5);
   }
}
