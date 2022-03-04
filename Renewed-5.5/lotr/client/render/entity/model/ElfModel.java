package lotr.client.render.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ElfModel extends LOTRBipedModel {
   private final ModelRenderer earRight;
   private final ModelRenderer earLeft;
   private final double legUp;
   private final double bodyUp;
   private final double headUp;
   private final float legStretch;
   private final float bodyStretch;

   public ElfModel(boolean smallArms) {
      this(0.0F, false, smallArms);
   }

   public ElfModel(float f) {
      this(f, true, false);
   }

   public ElfModel(float f, boolean isArmor, boolean smallArms) {
      super(f, 0.0F, isArmor, smallArms);
      this.legUp = 0.0625D;
      this.bodyUp = 0.125D;
      this.headUp = 0.125D;
      this.legStretch = 1.0833334F;
      this.bodyStretch = 1.0833334F;
      if (!isArmor) {
         this.earRight = new ModelRenderer(this, 0, 0);
         this.earRight.func_228300_a_(-4.0F, -6.5F, -1.0F, 1.0F, 4.0F, 2.0F);
         this.earRight.func_78793_a(0.0F, 0.0F, 0.0F);
         this.earRight.field_78808_h = (float)Math.toRadians(-15.0D);
         this.earLeft = new ModelRenderer(this, 26, 0);
         this.earLeft.func_228300_a_(3.0F, -6.5F, -1.0F, 1.0F, 4.0F, 2.0F);
         this.earLeft.func_78793_a(0.0F, 0.0F, 0.0F);
         this.earLeft.field_78808_h = (float)Math.toRadians(15.0D);
         this.field_78116_c.func_78792_a(this.earRight);
         this.field_78116_c.func_78792_a(this.earLeft);
      } else {
         this.earRight = this.earLeft = new ModelRenderer(this);
      }

      if (!isArmor) {
         this.createLongHairModel(0.0F, f);
      }

   }

   public void preHeadCallback(MatrixStack matStack) {
      matStack.func_227861_a_(0.0D, -0.125D, 0.0D);
   }

   public void preBodyCallback(MatrixStack matStack) {
      matStack.func_227861_a_(0.0D, -0.125D, 0.0D);
      matStack.func_227862_a_(1.0F, 1.0833334F, 1.0F);
   }

   public void preRightArmCallback(MatrixStack matStack) {
      matStack.func_227861_a_(0.0D, -0.125D, 0.0D);
      matStack.func_227862_a_(1.0F, 1.0833334F, 1.0F);
   }

   public void preLeftArmCallback(MatrixStack matStack) {
      matStack.func_227861_a_(0.0D, -0.125D, 0.0D);
      matStack.func_227862_a_(1.0F, 1.0833334F, 1.0F);
   }

   public void preRightLegCallback(MatrixStack matStack) {
      matStack.func_227861_a_(0.0D, -0.0625D, 0.0D);
      matStack.func_227862_a_(1.0F, 1.0833334F, 1.0F);
      matStack.func_227861_a_(0.0D, -0.0625D, 0.0D);
   }

   public void preLeftLegCallback(MatrixStack matStack) {
      matStack.func_227861_a_(0.0D, -0.0625D, 0.0D);
      matStack.func_227862_a_(1.0F, 1.0833334F, 1.0F);
      matStack.func_227861_a_(0.0D, -0.0625D, 0.0D);
   }
}
