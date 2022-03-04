package lotr.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import lotr.common.entity.animal.CaracalEntity;
import lotr.common.util.LOTRUtil;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class CaracalModel extends AgeableModel {
   private static final float QUARTER_PHASE = 1.5707964F;
   private final TransformableModelRenderer body;
   private final ModelRenderer head;
   private final ModelRenderer noseBridge;
   private final ModelRenderer noseMark;
   private final ModelRenderer earL;
   private final ModelRenderer earR;
   private final ModelRenderer tuftL;
   private final ModelRenderer tuftR;
   private final ModelRenderer tailMain;
   private final ModelRenderer tailEnd;
   private final ModelRenderer backLegL;
   private final ModelRenderer backLegR;
   private final ModelRenderer frontLegL;
   private final ModelRenderer frontLegR;
   private CaracalModel.State state;

   public CaracalModel(float f) {
      super(true, 10.0F, 4.0F);
      this.state = CaracalModel.State.NORMAL;
      this.body = new TransformableModelRenderer(this, 22, 0);
      this.body.func_78793_a(0.0F, 17.0F, 0.0F);
      this.body.func_228303_a_(-3.0F, -8.0F, -1.0F, 6.0F, 16.0F, 6.0F, f, false);
      this.head = new ModelRenderer(this, 0, 0);
      this.head.func_78793_a(0.0F, 14.0F, -10.0F);
      this.head.func_228303_a_(-3.0F, -4.0F, -3.0F, 6.0F, 5.0F, 5.0F, f, false);
      this.head.func_78784_a(0, 15).func_228303_a_(-1.5F, -1.0F, -4.5F, 3.0F, 2.0F, 2.0F, f, false);
      this.noseBridge = new ModelRenderer(this, 0, 10);
      this.noseBridge.func_78793_a(0.0F, -1.0F, -4.0F);
      this.noseBridge.func_228303_a_(-1.0F, -2.75F, -0.435F, 2.0F, 3.0F, 2.0F, f, false);
      this.head.func_78792_a(this.noseBridge);
      this.noseMark = new ModelRenderer(this, 8, 13);
      this.noseMark.func_78793_a(1.7F, -2.0F, -3.1F);
      this.noseMark.func_228303_a_(-1.7F, -0.5F, -1.335F, 1.0F, 1.0F, 1.0F, f, false);
      this.noseMark.field_78796_g = (float)Math.toRadians(30.0D);
      this.head.func_78792_a(this.noseMark);
      this.earL = new ModelRenderer(this, 0, 22);
      this.earL.func_78793_a(2.0F, -3.0F, 0.0F);
      this.earL.func_228303_a_(-0.5F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, f, true);
      this.head.func_78792_a(this.earL);
      this.earR = new ModelRenderer(this, 0, 22);
      this.earR.func_78793_a(-2.0F, -3.0F, 0.0F);
      this.earR.func_228303_a_(-0.5F, -3.0F, -1.0F, 1.0F, 2.0F, 2.0F, f, false);
      this.head.func_78792_a(this.earR);
      this.tuftL = new ModelRenderer(this, 6, 23);
      this.tuftL.func_78793_a(0.0F, -3.0F, 0.0F);
      this.tuftL.func_228303_a_(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, f, true);
      this.earL.func_78792_a(this.tuftL);
      this.tuftR = new ModelRenderer(this, 6, 23);
      this.tuftR.func_78793_a(0.0F, -3.0F, 0.0F);
      this.tuftR.func_228303_a_(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, f, false);
      this.earR.func_78792_a(this.tuftR);
      this.tailMain = new ModelRenderer(this, 22, 23);
      this.tailMain.func_78793_a(0.0F, 14.5F, 6.0F);
      this.tailMain.func_228303_a_(-0.5F, 1.0F, 1.0F, 1.0F, 8.0F, 1.0F, f, false);
      this.tailEnd = new ModelRenderer(this, 26, 23);
      this.tailEnd.func_78793_a(0.0F, 9.0F, 1.0F);
      this.tailEnd.func_228303_a_(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, f, false);
      this.tailMain.func_78792_a(this.tailEnd);
      this.backLegL = new ModelRenderer(this, 54, 0);
      this.backLegL.func_78793_a(1.1F, 14.0F, 6.0F);
      this.backLegL.func_228303_a_(-0.4F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, f, true);
      this.backLegR = new ModelRenderer(this, 54, 0);
      this.backLegR.func_78793_a(-1.1F, 14.0F, 6.0F);
      this.backLegR.func_228303_a_(-1.6F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, f, false);
      this.frontLegL = new ModelRenderer(this, 46, 0);
      this.frontLegL.func_78793_a(1.2F, 14.0F, -5.0F);
      this.frontLegL.func_228303_a_(-0.4F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, f, true);
      this.frontLegR = new ModelRenderer(this, 46, 0);
      this.frontLegR.func_78793_a(-1.2F, 14.0F, -5.0F);
      this.frontLegR.func_228303_a_(-1.6F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, f, false);
   }

   public ModelRenderer getHead() {
      return this.head;
   }

   protected Iterable func_225602_a_() {
      return ImmutableList.of(this.head);
   }

   protected Iterable func_225600_b_() {
      return ImmutableList.of(this.body, this.backLegL, this.backLegR, this.frontLegL, this.frontLegR, this.tailMain);
   }

   private boolean isCaracalAsleepOrOnBed(CaracalEntity caracal) {
      return caracal.func_70608_bn() || caracal.func_213416_eg();
   }

   public void setLivingAnimations(CaracalEntity caracal, float limbSwing, float limbSwingAmount, float partialTick) {
      this.body.field_78800_c = 0.0F;
      this.body.field_78797_d = 17.0F;
      this.body.field_78798_e = 0.0F;
      this.head.field_78800_c = 0.0F;
      this.head.field_78797_d = 14.0F;
      this.head.field_78798_e = -10.0F;
      this.tailMain.field_78800_c = 0.0F;
      this.tailMain.field_78797_d = 14.5F;
      this.tailMain.field_78798_e = 6.0F;
      this.frontLegL.field_78800_c = 1.2F;
      this.frontLegL.field_78797_d = 14.0F;
      this.frontLegL.field_78798_e = -5.0F;
      this.frontLegR.field_78800_c = -1.2F;
      this.frontLegR.field_78797_d = 14.0F;
      this.frontLegR.field_78798_e = -5.0F;
      this.backLegL.field_78800_c = 1.1F;
      this.backLegL.field_78797_d = 14.0F;
      this.backLegL.field_78798_e = 6.0F;
      this.backLegR.field_78800_c = -1.1F;
      this.backLegR.field_78797_d = 14.0F;
      this.backLegR.field_78798_e = 6.0F;
      ModelRenderer var10000;
      if (caracal.isFloppa()) {
         this.body.setScaleAndTranslation(1.25F, 1.1F, 1.0F, 0.0D, -1.7D, 0.0D);
         float legShift = 0.3F;
         var10000 = this.frontLegL;
         var10000.field_78800_c += legShift;
         var10000 = this.frontLegR;
         var10000.field_78800_c -= legShift;
         var10000 = this.backLegL;
         var10000.field_78800_c += legShift;
         var10000 = this.backLegR;
         var10000.field_78800_c -= legShift;
      } else {
         this.body.resetScaleAndTranslation();
      }

      this.noseMark.field_78806_j = caracal.isFloppa();
      if (!caracal.func_233684_eK_() && !this.isCaracalAsleepOrOnBed(caracal)) {
         if (caracal.func_213453_ef()) {
            ++this.body.field_78797_d;
            var10000 = this.head;
            var10000.field_78797_d += 2.0F;
            ++this.tailMain.field_78797_d;
            this.tailMain.field_78795_f = (float)Math.toRadians(90.0D);
            this.state = CaracalModel.State.SNEAKING;
         } else if (caracal.func_70051_ag()) {
            this.tailMain.field_78795_f = (float)Math.toRadians(90.0D);
            this.state = CaracalModel.State.SPRINTING;
         } else {
            this.state = CaracalModel.State.NORMAL;
         }
      } else {
         this.state = CaracalModel.State.LYING;
         TransformableModelRenderer var6 = this.body;
         var6.field_78797_d += 5.0F;
         var6 = this.body;
         var6.field_78800_c -= 2.0F;
         var10000 = this.head;
         var10000.field_78797_d += 5.0F;
         --this.head.field_78800_c;
         var10000 = this.tailMain;
         var10000.field_78797_d += 5.0F;
         var10000 = this.tailMain;
         var10000.field_78800_c -= 2.0F;
         var10000 = this.frontLegL;
         var10000.field_78797_d += 8.0F;
         var10000 = this.frontLegR;
         var10000.field_78797_d += 8.0F;
         var10000 = this.backLegL;
         var10000.field_78797_d += 8.0F;
         var10000 = this.backLegR;
         var10000.field_78797_d += 8.0F;
         var10000 = this.frontLegR;
         var10000.field_78800_c -= 2.0F;
         var10000 = this.frontLegR;
         var10000.field_78798_e -= 3.0F;
         var10000 = this.backLegR;
         var10000.field_78798_e -= 2.0F;
      }

   }

   public void setRotationAngles(CaracalEntity caracal, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.head.field_78795_f = (float)Math.toRadians((double)headPitch);
      this.head.field_78796_g = (float)Math.toRadians((double)netHeadYaw);
      this.noseBridge.field_78795_f = (float)Math.toRadians(-27.5D);
      ModelRenderer var10000;
      if (this.isCaracalAsleepOrOnBed(caracal)) {
         var10000 = this.head;
         var10000.field_78795_f += (float)Math.toRadians(30.0D) + MathHelper.func_76134_b(ageInTicks / 30.0F) * (float)Math.toRadians(10.0D);
      }

      float idleFlop;
      float idleTuftFlop;
      float motionFlop;
      if (caracal.isFlopping()) {
         idleFlop = 0.75F;
         idleTuftFlop = LOTRUtil.normalisedCos(ageInTicks * idleFlop);
         motionFlop = LOTRUtil.normalisedCos(ageInTicks * idleFlop + 1.5707964F);
         this.earL.field_78795_f = (float)Math.toRadians(-4.0D - (double)idleTuftFlop * 12.0D);
         this.earL.field_78796_g = (float)Math.toRadians(6.5D + (double)idleTuftFlop * 35.0D);
         this.earL.field_78808_h = (float)Math.toRadians(5.0D + (double)idleTuftFlop * 50.0D);
         this.earR.field_78795_f = this.earL.field_78795_f;
         this.earR.field_78796_g = -this.earL.field_78796_g;
         this.earR.field_78808_h = -this.earL.field_78808_h;
         this.tuftL.field_78808_h = (float)Math.toRadians(3.0D + (double)motionFlop * 40.0D);
         this.tuftR.field_78808_h = -this.tuftL.field_78808_h;
      } else if (caracal.areEarsAlert()) {
         this.earL.field_78795_f = 0.0F;
         this.earL.field_78796_g = (float)Math.toRadians(10.5D);
         this.earL.field_78808_h = (float)Math.toRadians(6.0D);
         this.earR.field_78795_f = this.earL.field_78795_f;
         this.earR.field_78796_g = -this.earL.field_78796_g;
         this.earR.field_78808_h = -this.earL.field_78808_h;
         this.tuftL.field_78808_h = (float)Math.toRadians(4.0D);
         this.tuftR.field_78808_h = -this.tuftL.field_78808_h;
      } else {
         idleFlop = MathHelper.func_76134_b(ageInTicks / 20.0F);
         idleTuftFlop = MathHelper.func_76134_b(ageInTicks / 20.0F + 1.5707964F);
         motionFlop = LOTRUtil.normalisedCos(limbSwing * 1.0F) * limbSwingAmount;
         float motionTuftFlop = LOTRUtil.normalisedCos(limbSwing * 1.0F + 1.5707964F) * limbSwingAmount;
         float floppaHealth = caracal.func_110143_aJ() / caracal.func_110138_aP();
         double baseTuftTlop = MathHelper.func_219803_d((double)floppaHealth, 100.0D, 20.0D);
         this.earL.field_78795_f = (float)Math.toRadians(-4.0D - (double)motionFlop * 30.0D - (double)idleFlop * 4.0D);
         this.earL.field_78796_g = (float)Math.toRadians(17.5D + (double)motionFlop * 5.0D + (double)idleFlop * 2.0D);
         this.earL.field_78808_h = (float)Math.toRadians(12.5D + (double)motionFlop * 20.0D + (double)idleFlop * 3.0D);
         if (this.state == CaracalModel.State.LYING) {
            var10000 = this.earL;
            var10000.field_78808_h += (float)Math.toRadians(8.0D);
         }

         this.earR.field_78795_f = this.earL.field_78795_f;
         this.earR.field_78796_g = -this.earL.field_78796_g;
         this.earR.field_78808_h = -this.earL.field_78808_h;
         this.tuftL.field_78808_h = (float)Math.toRadians(baseTuftTlop + (double)motionTuftFlop * 30.0D + (double)idleTuftFlop * 4.0D);
         this.tuftR.field_78808_h = -this.tuftL.field_78808_h;
      }

      this.body.field_78795_f = (float)Math.toRadians(90.0D);
      this.tailMain.field_78795_f = (float)Math.toRadians(45.0D);
      this.tailEnd.field_78795_f = (float)Math.toRadians(45.0D);
      this.tailMain.field_78796_g = this.tailEnd.field_78796_g = 0.0F;
      if (this.state != CaracalModel.State.LYING) {
         this.backLegL.field_78808_h = this.backLegR.field_78808_h = this.frontLegL.field_78808_h = this.frontLegR.field_78808_h = 0.0F;
         if (this.state == CaracalModel.State.SPRINTING) {
            this.backLegL.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * limbSwingAmount;
            this.backLegR.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 0.3F) * limbSwingAmount;
            this.frontLegL.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F + 0.3F) * limbSwingAmount;
            this.frontLegR.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * limbSwingAmount;
            this.tailEnd.field_78795_f = (float)Math.toRadians(44.0D + 18.0D * (double)MathHelper.func_76134_b(limbSwing * 0.6662F) * (double)limbSwingAmount);
         } else {
            this.backLegL.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * limbSwingAmount;
            this.backLegR.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * limbSwingAmount;
            this.frontLegL.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F + 3.1415927F) * limbSwingAmount;
            this.frontLegR.field_78795_f = MathHelper.func_76134_b(limbSwing * 0.6662F) * limbSwingAmount;
            if (this.state == CaracalModel.State.NORMAL) {
               this.tailEnd.field_78795_f = (float)Math.toRadians(44.0D + 45.0D * (double)MathHelper.func_76134_b(limbSwing * 0.6662F) * (double)limbSwingAmount);
            } else {
               this.tailEnd.field_78795_f = (float)Math.toRadians(44.0D + 27.0D * (double)MathHelper.func_76134_b(limbSwing * 0.6662F) * (double)limbSwingAmount);
            }
         }
      } else {
         this.backLegL.field_78808_h = this.backLegR.field_78808_h = this.frontLegL.field_78808_h = this.frontLegR.field_78808_h = (float)Math.toRadians(-85.0D);
         this.frontLegL.field_78795_f = (float)Math.toRadians(20.0D);
         this.frontLegR.field_78795_f = (float)Math.toRadians(-20.0D);
         this.backLegL.field_78795_f = (float)Math.toRadians(20.0D);
         this.backLegR.field_78795_f = (float)Math.toRadians(-20.0D);
         this.tailMain.field_78795_f = (float)Math.toRadians(60.0D);
         this.tailEnd.field_78795_f = (float)Math.toRadians(30.0D);
         this.tailMain.field_78796_g = MathHelper.func_76134_b(ageInTicks / 30.0F) * (float)Math.toRadians(35.0D);
         this.tailEnd.field_78796_g = this.tailMain.field_78796_g * 1.3F;
      }

      if (caracal.isRaidingChest()) {
         this.frontLegL.field_78795_f = MathHelper.func_76134_b(ageInTicks * 1.3F + 3.1415927F) * limbSwingAmount;
         this.frontLegR.field_78795_f = MathHelper.func_76134_b(ageInTicks * 1.3F) * limbSwingAmount;
      }

   }

   private static enum State {
      SNEAKING,
      NORMAL,
      SPRINTING,
      LYING;
   }
}
