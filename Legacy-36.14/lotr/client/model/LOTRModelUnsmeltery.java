package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelUnsmeltery extends ModelBase {
   private ModelRenderer base;
   private ModelRenderer body;
   private ModelRenderer standRight;
   private ModelRenderer standLeft;

   public LOTRModelUnsmeltery() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.base = new ModelRenderer(this, 0, 0);
      this.base.func_78793_a(0.0F, 21.0F, 0.0F);
      this.base.func_78789_a(-7.0F, 0.0F, -7.0F, 14, 3, 14);
      this.body = new ModelRenderer(this, 0, 17);
      this.body.func_78793_a(0.0F, 12.0F, 0.0F);
      this.body.func_78789_a(-7.0F, -2.0F, -7.0F, 14, 10, 14);
      this.body.func_78784_a(0, 41).func_78789_a(-7.0F, -4.0F, -7.0F, 14, 2, 1);
      this.body.func_78789_a(-7.0F, -4.0F, 6.0F, 14, 2, 1);
      this.body.func_78784_a(0, 44).func_78789_a(-7.0F, -4.0F, -6.0F, 1, 2, 12);
      this.body.func_78789_a(6.0F, -4.0F, -6.0F, 1, 2, 12);
      this.standRight = new ModelRenderer(this, 56, 6);
      this.standRight.func_78793_a(-7.0F, 23.0F, 0.0F);
      this.standRight.func_78789_a(-0.9F, -12.0F, -1.0F, 1, 12, 2);
      ModelRenderer panelRight = new ModelRenderer(this, 56, 0);
      panelRight.func_78793_a(0.0F, -11.0F, 0.0F);
      panelRight.func_78789_a(-1.0F, -2.0F, -1.0F, 1, 3, 3);
      panelRight.field_78795_f = (float)Math.toRadians(45.0D);
      this.standRight.func_78792_a(panelRight);
      this.standLeft = new ModelRenderer(this, 56, 6);
      this.standLeft.func_78793_a(7.0F, 23.0F, 0.0F);
      this.standLeft.field_78809_i = true;
      this.standLeft.func_78789_a(-0.1F, -12.0F, -1.0F, 1, 12, 2);
      ModelRenderer panelLeft = new ModelRenderer(this, 56, 0);
      panelLeft.func_78793_a(0.0F, -11.0F, 0.0F);
      panelLeft.field_78809_i = true;
      panelLeft.func_78789_a(0.0F, -2.0F, -1.0F, 1, 3, 3);
      panelLeft.field_78795_f = (float)Math.toRadians(45.0D);
      this.standLeft.func_78792_a(panelLeft);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.body.field_78795_f = f * (float)Math.toRadians(20.0D);
      this.base.func_78785_a(f5);
      this.body.func_78785_a(f5);
      this.standRight.func_78785_a(f5);
      this.standLeft.func_78785_a(f5);
   }
}
