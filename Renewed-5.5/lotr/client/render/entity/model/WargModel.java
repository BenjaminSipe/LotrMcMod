package lotr.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.List;
import lotr.client.event.LOTRTickHandlerClient;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class WargModel extends SegmentedModel {
   private final ModelRenderer frontBody;
   private final ModelRenderer backHairL;
   private final ModelRenderer backHairR;
   private final ModelRenderer head;
   private final ModelRenderer earR;
   private final ModelRenderer earL;
   private final ModelRenderer maneTop;
   private final ModelRenderer maneR;
   private final ModelRenderer maneL;
   private final ModelRenderer frontLegR;
   private final ModelRenderer frontLegL;
   private final ModelRenderer backBody;
   private final ModelRenderer tail;
   private final ModelRenderer backLegR;
   private final ModelRenderer backLegL;
   private final List topLevelParts;

   public WargModel(float f) {
      this.field_78090_t = 128;
      this.field_78089_u = 64;
      this.frontBody = new ModelRenderer(this, 0, 38);
      this.frontBody.func_78793_a(0.0F, 7.5F, 2.0F);
      this.frontBody.func_228301_a_(-6.0F, -5.5F, -14.0F, 12.0F, 11.0F, 15.0F, f);
      this.backHairL = new ModelRenderer(this, 36, 13);
      this.backHairL.field_78809_i = true;
      this.backHairL.func_78793_a(1.0F, -5.5F, -7.0F);
      this.backHairL.func_228301_a_(0.0F, -4.0F, -7.0F, 0.0F, 4.0F, 14.0F, 0.0F);
      this.backHairL.field_78808_h = (float)Math.toRadians(22.5D);
      this.frontBody.func_78792_a(this.backHairL);
      this.backHairR = new ModelRenderer(this, 36, 13);
      this.backHairR.func_78793_a(-1.0F, -5.5F, -7.0F);
      this.backHairR.func_228301_a_(0.0F, -4.0F, -7.0F, 0.0F, 4.0F, 14.0F, 0.0F);
      this.backHairR.field_78808_h = (float)Math.toRadians(-22.5D);
      this.frontBody.func_78792_a(this.backHairR);
      this.head = new ModelRenderer(this, 0, 0);
      this.head.func_78793_a(0.0F, 3.5F, -14.0F);
      this.head.func_228301_a_(-5.0F, -8.0F, -8.0F, 10.0F, 8.0F, 8.0F, f);
      this.head.func_78784_a(0, 16).func_228301_a_(-3.0F, -5.0F, -14.0F, 6.0F, 5.0F, 6.0F, f);
      this.frontBody.func_78792_a(this.head);
      this.earR = new ModelRenderer(this, 36, 4);
      this.earR.func_78793_a(-1.8301F, -9.5F, -2.7679F);
      this.earR.func_228301_a_(-0.5F, -2.5F, -3.5F, 2.0F, 5.0F, 4.0F, f);
      this.earR.field_78795_f = (float)Math.toRadians(-165.0D);
      this.earR.field_78796_g = (float)Math.toRadians(60.0D);
      this.earR.field_78808_h = (float)Math.toRadians(-180.0D);
      this.head.func_78792_a(this.earR);
      this.earL = new ModelRenderer(this, 36, 4);
      this.earL.func_78793_a(2.5562F, -9.3876F, -3.0242F);
      this.earL.func_228301_a_(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 4.0F, f);
      this.earL.field_78795_f = (float)Math.toRadians(-15.0D);
      this.earL.field_78796_g = (float)Math.toRadians(60.0D);
      this.head.func_78792_a(this.earL);
      this.maneTop = new ModelRenderer(this, 44, 31);
      this.maneTop.func_78793_a(0.0F, -8.0F, -1.0F);
      this.maneTop.func_228301_a_(-5.0F, -5.0F, 0.0F, 10.0F, 5.0F, 0.0F, 0.0F);
      this.maneTop.field_78795_f = (float)Math.toRadians(-45.0D);
      this.head.func_78792_a(this.maneTop);
      this.maneR = new ModelRenderer(this, 28, 0);
      this.maneR.func_78793_a(-5.0F, -4.0F, -3.0F);
      this.maneR.func_228301_a_(-5.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F);
      this.maneR.field_78796_g = (float)Math.toRadians(45.0D);
      this.head.func_78792_a(this.maneR);
      this.maneL = new ModelRenderer(this, 28, 0);
      this.maneL.field_78809_i = true;
      this.maneL.func_78793_a(5.0F, -4.0F, -3.0F);
      this.maneL.func_228301_a_(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.0F, 0.0F);
      this.maneL.field_78796_g = (float)Math.toRadians(-45.0D);
      this.head.func_78792_a(this.maneL);
      this.frontLegR = new ModelRenderer(this, 66, 0);
      this.frontLegR.func_78793_a(-5.0F, -1.5F, -6.5F);
      this.frontLegR.func_228301_a_(-2.0F, -5.0F, -4.5F, 4.0F, 11.0F, 8.0F, f);
      this.frontLegR.func_78784_a(70, 19).func_228301_a_(-2.0F, 6.0F, -1.5F, 4.0F, 12.0F, 4.0F, f);
      this.frontBody.func_78792_a(this.frontLegR);
      this.frontLegL = new ModelRenderer(this, 66, 0);
      this.frontLegL.field_78809_i = true;
      this.frontLegL.func_78793_a(5.0F, -1.5F, -6.5F);
      this.frontLegL.func_228301_a_(-2.0F, -5.0F, -4.5F, 4.0F, 11.0F, 8.0F, f);
      this.frontLegL.func_78784_a(70, 19).func_228301_a_(-2.0F, 6.0F, -1.5F, 4.0F, 12.0F, 4.0F, f);
      this.frontBody.func_78792_a(this.frontLegL);
      this.backBody = new ModelRenderer(this, 54, 39);
      this.backBody.func_78793_a(0.0F, 7.5F, 2.0F);
      this.backBody.func_228301_a_(-5.0F, -4.7F, -1.0F, 10.0F, 10.0F, 15.0F, f);
      this.tail = new ModelRenderer(this, 92, 33);
      this.tail.func_78793_a(-0.5F, -2.0F, 13.5F);
      this.tail.func_228301_a_(-2.0F, -1.5F, -1.5F, 5.0F, 5.0F, 13.0F, f);
      this.tail.field_78795_f = (float)Math.toRadians(-45.0D);
      this.backBody.func_78792_a(this.tail);
      this.backLegR = new ModelRenderer(this, 102, 16);
      this.backLegR.func_78793_a(-4.0F, 1.5F, 9.0F);
      this.backLegR.func_228301_a_(-2.0F, 5.0F, 0.0F, 4.0F, 10.0F, 4.0F, f);
      this.backLegR.func_78784_a(99, 0).func_228301_a_(-2.0F, -4.0F, -3.0F, 4.0F, 9.0F, 7.0F, f);
      this.backBody.func_78792_a(this.backLegR);
      this.backLegL = new ModelRenderer(this, 102, 16);
      this.backLegL.field_78809_i = true;
      this.backLegL.func_78793_a(4.0F, 1.5F, 9.0F);
      this.backLegL.func_228301_a_(-2.0F, 5.0F, 0.0F, 4.0F, 10.0F, 4.0F, f);
      this.backLegL.func_78784_a(99, 0).func_228301_a_(-2.0F, -4.0F, -3.0F, 4.0F, 9.0F, 7.0F, f);
      this.backBody.func_78792_a(this.backLegL);
      this.topLevelParts = ImmutableList.of(this.backBody, this.frontBody);
   }

   public Iterable func_225601_a_() {
      return this.topLevelParts;
   }

   public void setLivingAnimations(WargEntity warg, float limbSwing, float limbSwingAmount, float partialTick) {
   }

   public void setRotationAngles(WargEntity warg, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.field_78795_f = (float)Math.toRadians((double)headPitch);
      this.head.field_78796_g = (float)Math.toRadians((double)netHeadYaw);
      this.backLegL.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * limbSwingAmount;
      this.backLegR.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * limbSwingAmount;
      this.frontLegL.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * limbSwingAmount;
      this.frontLegR.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * limbSwingAmount;
      this.tail.field_78795_f = (float)Math.toRadians(-40.0D + (double)(MathHelper.func_76126_a(limbSwing * 0.6662F) * limbSwingAmount) * 10.0D);
      if (warg.func_213398_dR()) {
         this.frontBody.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * limbSwingAmount * (float)Math.toRadians(7.0D);
         this.backBody.field_78795_f = -this.frontBody.field_78795_f;
      } else {
         this.backBody.field_78795_f = this.frontBody.field_78795_f = 0.0F;
      }

      float leapLerp = warg.getLeapingProgress(LOTRTickHandlerClient.renderPartialTick);
      if (leapLerp > 0.0F) {
         float leapingBackLegRotX = (float)Math.toRadians(50.0D);
         float leapingFrontLegRotX = (float)Math.toRadians(-50.0D);
         float leapingBodyRotX = 0.0F;
         float leapingTailRotX = (float)Math.toRadians(-30.0D);
         this.backLegL.field_78795_f = MathHelper.func_219799_g(leapLerp, this.backLegL.field_78795_f, leapingBackLegRotX);
         this.backLegR.field_78795_f = MathHelper.func_219799_g(leapLerp, this.backLegR.field_78795_f, leapingBackLegRotX);
         this.frontLegL.field_78795_f = MathHelper.func_219799_g(leapLerp, this.frontLegL.field_78795_f, leapingFrontLegRotX);
         this.frontLegR.field_78795_f = MathHelper.func_219799_g(leapLerp, this.frontLegR.field_78795_f, leapingFrontLegRotX);
         this.backBody.field_78795_f = MathHelper.func_219799_g(leapLerp, this.backBody.field_78795_f, leapingBodyRotX);
         this.frontBody.field_78795_f = MathHelper.func_219799_g(leapLerp, this.frontBody.field_78795_f, leapingBodyRotX);
         this.tail.field_78795_f = MathHelper.func_219799_g(leapLerp, this.tail.field_78795_f, leapingTailRotX);
      }

   }
}
