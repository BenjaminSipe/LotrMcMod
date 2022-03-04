package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class TransformableModelRenderer extends ModelRenderer {
   private float scaleX;
   private float scaleY;
   private float scaleZ;
   private double transX;
   private double transY;
   private double transZ;

   public TransformableModelRenderer(Model model, int texOffX, int texOffY) {
      super(model, texOffX, texOffY);
      this.resetScaleAndTranslation();
   }

   public void resetScaleAndTranslation() {
      this.setScaleAndTranslation(1.0F, 1.0F, 1.0F, 0.0D, 0.0D, 0.0D);
   }

   public void setScaleAndTranslation(float x, float y, float z, double tx, double ty, double tz) {
      this.scaleX = x;
      this.scaleY = y;
      this.scaleZ = z;
      this.transX = tx;
      this.transY = ty;
      this.transZ = tz;
   }

   private boolean hasTransform() {
      return this.scaleX != 1.0F || this.scaleY != 1.0F || this.scaleZ != 1.0F || this.transX != 0.0D || this.transY != 0.0D || this.transZ != 0.0D;
   }

   public void func_228309_a_(MatrixStack matStack, IVertexBuilder buf, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      boolean hasTransform = this.hasTransform();
      if (hasTransform) {
         matStack.func_227860_a_();
         matStack.func_227862_a_(this.scaleX, this.scaleY, this.scaleZ);
         matStack.func_227861_a_(this.transX / 16.0D, this.transY / 16.0D, this.transZ / 16.0D);
      }

      super.func_228309_a_(matStack, buf, packedLight, packedOverlay, red, green, blue, alpha);
      if (hasTransform) {
         matStack.func_227865_b_();
      }

   }
}
