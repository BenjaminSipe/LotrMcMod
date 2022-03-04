package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;

public class HobbitModel extends LOTRBipedModel {
   private static final float F_10_12 = 0.8333333F;

   public HobbitModel(boolean smallArms) {
      this(0.0F, false, smallArms);
   }

   public HobbitModel(float f) {
      this(f, true, false);
   }

   public HobbitModel(float f, boolean isArmor, boolean smallArms) {
      super(f, 0.0F, isArmor, smallArms);
      if (!isArmor) {
         this.createLongHairModel(2.0F, f);
      }

      if (!isArmor) {
         ModelRenderer rightFoot = new ModelRenderer(this, 64, 48);
         rightFoot.func_228301_a_(-2.0F, 10.0F, -5.0F, 4.0F, 2.0F, 3.0F, f);
         rightFoot.field_78796_g = (float)Math.toRadians(10.0D);
         this.field_178721_j.func_78792_a(rightFoot);
         ModelRenderer leftFoot = new ModelRenderer(this, 78, 48);
         leftFoot.func_228301_a_(-2.0F, 10.0F, -5.0F, 4.0F, 2.0F, 3.0F, f);
         leftFoot.field_78796_g = (float)Math.toRadians(-10.0D);
         this.field_178722_k.func_78792_a(leftFoot);
      }

   }

   public void preRenderAllCallback(MatrixStack matStack) {
      float hobbitOffsetY = 4.0F;
      matStack.func_227861_a_(0.0D, 0.25D, 0.0D);
   }

   public void postChildHeadCallback(MatrixStack matStack) {
      matStack.func_227861_a_(0.0D, -0.0625D, 0.0D);
   }

   public void preBodyCallback(MatrixStack matStack) {
      matStack.func_227862_a_(1.0F, 0.8333333F, 1.0F);
   }

   public void preRightArmCallback(MatrixStack matStack) {
      matStack.func_227862_a_(1.0F, 0.8333333F, 1.0F);
   }

   public void preLeftArmCallback(MatrixStack matStack) {
      matStack.func_227862_a_(1.0F, 0.8333333F, 1.0F);
   }

   public void preRightLegCallback(MatrixStack matStack) {
      matStack.func_227862_a_(1.0F, 0.8333333F, 1.0F);
   }

   public void preLeftLegCallback(MatrixStack matStack) {
      matStack.func_227862_a_(1.0F, 0.8333333F, 1.0F);
   }

   public void func_225599_a_(HandSide handSide, MatrixStack matStack) {
      ModelRenderer arm = this.func_187074_a(handSide);
      float y = arm.field_78797_d;
      arm.field_78797_d += 3.0F;
      arm.func_228307_a_(matStack);
      arm.field_78797_d = y;
   }
}
