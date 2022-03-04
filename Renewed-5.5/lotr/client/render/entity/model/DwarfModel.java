package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.HandSide;

public class DwarfModel extends LOTRBipedModel {
   private final float bodyScaleX;
   private final double armTranslateX;
   private final double legTranslateX;

   public DwarfModel(boolean smallArms) {
      this(0.0F, false, smallArms);
   }

   public DwarfModel(float f) {
      this(f, true, false);
   }

   public DwarfModel(float f, boolean isArmor, boolean smallArms) {
      super(f, 0.0F, isArmor, smallArms);
      this.bodyScaleX = 1.25F;
      this.armTranslateX = 0.0625D;
      this.legTranslateX = 0.015625D;
      if (!isArmor) {
         this.createLongHairModel(2.0F, f);
      }

   }

   public void preBodyCallback(MatrixStack matStack) {
      matStack.func_227862_a_(1.25F, 1.0F, 1.0F);
   }

   public void preRightArmCallback(MatrixStack matStack) {
      matStack.func_227861_a_(-0.0625D, 0.0D, 0.0D);
   }

   public void preLeftArmCallback(MatrixStack matStack) {
      matStack.func_227861_a_(0.0625D, 0.0D, 0.0D);
   }

   public void preRightLegCallback(MatrixStack matStack) {
      matStack.func_227861_a_(-0.015625D, 0.0D, 0.0D);
      matStack.func_227862_a_(1.25F, 1.0F, 1.0F);
   }

   public void preLeftLegCallback(MatrixStack matStack) {
      matStack.func_227861_a_(0.015625D, 0.0D, 0.0D);
      matStack.func_227862_a_(1.25F, 1.0F, 1.0F);
   }

   public void func_225599_a_(HandSide side, MatrixStack matStack) {
      double x = 0.0625D * (double)(side == HandSide.RIGHT ? -1 : 1);
      matStack.func_227861_a_(x, 0.0D, 0.0D);
      super.func_225599_a_(side, matStack);
   }
}
