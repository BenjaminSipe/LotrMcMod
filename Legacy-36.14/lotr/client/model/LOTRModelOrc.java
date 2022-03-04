package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelOrc extends LOTRModelBiped implements LOTRGlowingEyes.Model {
   private ModelRenderer nose;
   private ModelRenderer earRight;
   private ModelRenderer earLeft;

   public LOTRModelOrc() {
      this(0.0F);
   }

   public LOTRModelOrc(float f) {
      super(f);
      this.nose = new ModelRenderer(this, 14, 17);
      this.nose.func_78790_a(-0.5F, -4.0F, -4.8F, 1, 2, 1, f);
      this.nose.func_78793_a(0.0F, 0.0F, 0.0F);
      this.earRight = new ModelRenderer(this, 0, 0);
      this.earRight.func_78790_a(-3.5F, -5.5F, 2.0F, 1, 2, 3, f);
      this.earRight.func_78793_a(0.0F, 0.0F, 0.0F);
      this.earRight.field_78795_f = 0.2617994F;
      this.earRight.field_78796_g = -0.5235988F;
      this.earRight.field_78808_h = -0.22689281F;
      this.earLeft = new ModelRenderer(this, 24, 0);
      this.earLeft.func_78790_a(2.5F, -5.5F, 2.0F, 1, 2, 3, f);
      this.earLeft.func_78793_a(0.0F, 0.0F, 0.0F);
      this.earLeft.field_78795_f = 0.2617994F;
      this.earLeft.field_78796_g = 0.5235988F;
      this.earLeft.field_78808_h = 0.22689281F;
      this.field_78116_c.func_78792_a(this.nose);
      this.field_78116_c.func_78792_a(this.earRight);
      this.field_78116_c.func_78792_a(this.earLeft);
   }

   public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.field_78116_c.func_78785_a(f5);
   }
}
