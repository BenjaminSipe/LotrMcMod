package lotr.client.render.tileentity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class PalantirModel extends Model {
   private final boolean innerOrbOnly;
   private ModelRenderer outer;
   private ModelRenderer middle;
   private ModelRenderer inner;
   private ModelRenderer stand1;
   private ModelRenderer stand2;

   public PalantirModel(boolean innerOrbOnly) {
      super(innerOrbOnly ? RenderType::func_228640_c_ : RenderType::func_228644_e_);
      this.innerOrbOnly = innerOrbOnly;
      float innerOrbSize = 8.0F;
      float middleOrbSize = 9.0F;
      float outerOrbSize = 10.0F;
      if (innerOrbOnly) {
         this.field_78090_t = 32;
         this.field_78089_u = 16;
      } else {
         this.field_78090_t = 64;
         this.field_78089_u = 64;
         this.outer = new ModelRenderer(this, 0, 44);
         this.outer.func_78793_a(0.0F, 0.0F, 0.0F);
         this.outer.field_78796_g = (float)Math.toRadians(-45.0D);
         this.outer.func_228300_a_(-outerOrbSize / 2.0F, -outerOrbSize / 2.0F, -outerOrbSize / 2.0F, outerOrbSize, outerOrbSize, outerOrbSize);
         this.middle = new ModelRenderer(this, 0, 26);
         this.middle.func_78793_a(0.0F, 0.0F, 0.0F);
         this.middle.field_78796_g = (float)Math.toRadians(-45.0D);
         this.middle.func_228300_a_(-middleOrbSize / 2.0F, -middleOrbSize / 2.0F, -middleOrbSize / 2.0F, middleOrbSize, middleOrbSize, middleOrbSize);
         this.stand1 = new ModelRenderer(this, 0, 0);
         this.stand1.func_78793_a(0.0F, 0.0F, 0.0F);
         this.stand1.field_78796_g = (float)Math.toRadians(-45.0D);
         this.stand1.func_228300_a_(0.0F, -2.0F, -8.0F, 0.0F, 10.0F, 16.0F);
         this.stand2 = new ModelRenderer(this, 0, 0);
         this.stand2.func_78793_a(0.0F, 0.0F, 0.0F);
         this.stand2.field_78796_g = (float)Math.toRadians(45.0D);
         this.stand2.field_78809_i = true;
         this.stand2.func_228300_a_(0.0F, -2.0F, -8.0F, 0.0F, 10.0F, 16.0F);
      }

      this.inner = new ModelRenderer(this, 0, 0);
      this.inner.func_78793_a(0.0F, 0.0F, 0.0F);
      this.inner.field_78796_g = (float)Math.toRadians(-45.0D);
      this.inner.func_228300_a_(-innerOrbSize / 2.0F, -innerOrbSize / 2.0F, -innerOrbSize / 2.0F, innerOrbSize, innerOrbSize, innerOrbSize);
   }

   public void func_225598_a_(MatrixStack mat, IVertexBuilder buf, int light, int overlay, float r, float g, float b, float a) {
      if (this.innerOrbOnly) {
         this.inner.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
      } else {
         this.outer.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
         this.middle.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
         this.stand1.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
         this.stand2.func_228309_a_(mat, buf, light, overlay, r, g, b, a);
      }

   }
}
