package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import lotr.common.entity.npc.LOTREntityEnt;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelEnt extends ModelBase implements LOTRGlowingEyes.Model {
   public ModelRenderer trunk;
   public ModelRenderer browRight;
   public ModelRenderer browLeft;
   public ModelRenderer eyeRight;
   public ModelRenderer eyeLeft;
   public ModelRenderer nose;
   public ModelRenderer beard;
   public ModelRenderer trophyBottomPanel;
   public ModelRenderer rightArm;
   public ModelRenderer rightHand;
   public ModelRenderer leftArm;
   public ModelRenderer leftHand;
   public ModelRenderer rightLeg;
   public ModelRenderer rightFoot;
   public ModelRenderer leftLeg;
   public ModelRenderer leftFoot;
   public ModelRenderer branches;

   public LOTRModelEnt() {
      this(0.0F);
   }

   public LOTRModelEnt(float f) {
      this.field_78090_t = 128;
      this.field_78089_u = 128;
      this.trunk = new ModelRenderer(this, 0, 0);
      this.trunk.func_78790_a(-8.0F, -48.0F, -6.0F, 16, 48, 12, f);
      this.trunk.func_78793_a(0.0F, -10.0F, 0.0F);
      this.browRight = new ModelRenderer(this, 56, 26);
      this.browRight.func_78790_a(-6.5F, 0.0F, -8.0F, 5, 1, 2, f);
      this.browRight.func_78793_a(0.0F, -39.0F, 0.0F);
      this.browRight.field_78808_h = (float)Math.toRadians(10.0D);
      this.trunk.func_78792_a(this.browRight);
      this.browLeft = new ModelRenderer(this, 56, 26);
      this.browLeft.field_78809_i = true;
      this.browLeft.func_78790_a(1.5F, 0.0F, -8.0F, 5, 1, 2, f);
      this.browLeft.func_78793_a(0.0F, -39.0F, 0.0F);
      this.browLeft.field_78808_h = (float)Math.toRadians(-10.0D);
      this.trunk.func_78792_a(this.browLeft);
      this.eyeRight = new ModelRenderer(this, 56, 29);
      this.eyeRight.func_78790_a(-1.5F, -2.0F, -7.0F, 3, 3, 1, f + 0.2F);
      this.eyeRight.func_78793_a(-3.5F, -36.0F, 0.0F);
      this.trunk.func_78792_a(this.eyeRight);
      this.eyeLeft = new ModelRenderer(this, 56, 29);
      this.eyeLeft.field_78809_i = true;
      this.eyeLeft.func_78790_a(-1.5F, -2.0F, -7.0F, 3, 3, 1, f + 0.2F);
      this.eyeLeft.func_78793_a(3.5F, -36.0F, 0.0F);
      this.trunk.func_78792_a(this.eyeLeft);
      this.nose = new ModelRenderer(this, 56, 33);
      this.nose.func_78790_a(-1.5F, -2.0F, -9.0F, 3, 6, 3, f);
      this.nose.func_78793_a(0.0F, -36.0F, 0.0F);
      this.trunk.func_78792_a(this.nose);
      this.beard = new ModelRenderer(this, 56, 0);
      this.beard.func_78790_a(-5.0F, 0.0F, -8.0F, 10, 24, 2, f);
      this.beard.func_78793_a(0.0F, -31.0F, 0.0F);
      this.trunk.func_78792_a(this.beard);
      this.trophyBottomPanel = new ModelRenderer(this, 72, 116);
      this.trophyBottomPanel.func_78793_a(0.0F, -24.0F, 0.0F);
      this.trophyBottomPanel.func_78790_a(-8.0F, 0.0F, -6.0F, 16, 0, 12, f);
      this.trunk.func_78792_a(this.trophyBottomPanel);
      this.trophyBottomPanel.field_78806_j = false;
      this.rightArm = new ModelRenderer(this, 96, 28);
      this.rightArm.func_78790_a(-8.0F, 0.0F, -4.0F, 8, 12, 8, f);
      this.rightArm.func_78784_a(112, 48).func_78790_a(-7.0F, 12.0F, -2.0F, 4, 16, 4, f);
      this.rightArm.func_78793_a(-8.0F, -38.0F, 0.0F);
      this.trunk.func_78792_a(this.rightArm);
      this.rightHand = new ModelRenderer(this, 102, 68);
      this.rightHand.func_78790_a(-2.5F, 0.0F, -4.0F, 5, 16, 8, f);
      this.rightHand.func_78784_a(102, 92).func_78790_a(-2.0F, 16.0F, -4.0F, 3, 10, 2, f);
      this.rightHand.func_78784_a(112, 92).func_78790_a(-2.0F, 16.0F, -1.0F, 2, 8, 2, f);
      this.rightHand.func_78784_a(120, 92).func_78790_a(-2.0F, 16.0F, 2.0F, 2, 6, 2, f);
      this.rightHand.func_78793_a(-5.0F, 28.0F, 0.0F);
      this.rightArm.func_78792_a(this.rightHand);
      this.leftArm = new ModelRenderer(this, 96, 28);
      this.leftArm.field_78809_i = true;
      this.leftArm.func_78790_a(0.0F, 0.0F, -4.0F, 8, 12, 8, f);
      this.leftArm.func_78784_a(112, 48).func_78790_a(3.0F, 12.0F, -2.0F, 4, 16, 4, f);
      this.leftArm.func_78793_a(8.0F, -38.0F, 0.0F);
      this.trunk.func_78792_a(this.leftArm);
      this.leftHand = new ModelRenderer(this, 102, 68);
      this.leftHand.field_78809_i = true;
      this.leftHand.func_78790_a(-2.5F, 0.0F, -4.0F, 5, 16, 8, f);
      this.leftHand.func_78784_a(102, 92).func_78790_a(-1.0F, 16.0F, -4.0F, 3, 10, 2, f);
      this.leftHand.func_78784_a(112, 92).func_78790_a(0.0F, 16.0F, -1.0F, 2, 8, 2, f);
      this.leftHand.func_78784_a(120, 92).func_78790_a(0.0F, 16.0F, 2.0F, 2, 6, 2, f);
      this.leftHand.func_78793_a(5.0F, 28.0F, 0.0F);
      this.leftArm.func_78792_a(this.leftHand);
      this.rightLeg = new ModelRenderer(this, 0, 60);
      this.rightLeg.func_78790_a(-7.0F, -4.0F, -4.0F, 6, 22, 8, f);
      this.rightLeg.func_78793_a(-4.0F, -12.0F, 0.0F);
      this.rightFoot = new ModelRenderer(this, 28, 60);
      this.rightFoot.func_78790_a(-4.0F, 0.0F, -5.0F, 8, 12, 10, f);
      this.rightFoot.func_78784_a(0, 90).func_78790_a(-5.0F, 12.0F, -7.0F, 10, 6, 15, f);
      this.rightFoot.func_78784_a(0, 111).func_78790_a(2.0F, 13.0F, -16.0F, 3, 5, 9, f);
      this.rightFoot.func_78784_a(24, 113).func_78790_a(-2.0F, 14.0F, -15.0F, 3, 4, 8, f);
      this.rightFoot.func_78784_a(46, 115).func_78790_a(-5.0F, 15.0F, -14.0F, 2, 3, 7, f);
      this.rightFoot.func_78793_a(-4.0F, 18.0F, 0.0F);
      this.rightLeg.func_78792_a(this.rightFoot);
      this.leftLeg = new ModelRenderer(this, 0, 60);
      this.leftLeg.field_78809_i = true;
      this.leftLeg.func_78790_a(1.0F, -4.0F, -4.0F, 6, 22, 8, f);
      this.leftLeg.func_78793_a(4.0F, -12.0F, 0.0F);
      this.leftFoot = new ModelRenderer(this, 28, 60);
      this.leftFoot.field_78809_i = true;
      this.leftFoot.func_78790_a(-4.0F, 0.0F, -5.0F, 8, 12, 10, f);
      this.leftFoot.func_78784_a(0, 90).func_78790_a(-5.0F, 12.0F, -7.0F, 10, 6, 15, f);
      this.leftFoot.func_78784_a(0, 111).func_78790_a(-5.0F, 13.0F, -16.0F, 3, 5, 9, f);
      this.leftFoot.func_78784_a(24, 113).func_78790_a(-1.0F, 14.0F, -15.0F, 3, 4, 8, f);
      this.leftFoot.func_78784_a(46, 115).func_78790_a(3.0F, 15.0F, -14.0F, 2, 3, 7, f);
      this.leftFoot.func_78793_a(4.0F, 18.0F, 0.0F);
      this.leftLeg.func_78792_a(this.leftFoot);
      this.branches = new ModelRenderer(this, 0, 0);
      this.branches.func_78793_a(0.0F, -48.0F, 0.0F);
      ModelRenderer branch1 = new ModelRenderer(this, 80, 16);
      branch1.func_78790_a(-1.5F, -28.0F, -1.5F, 3, 32, 3, f);
      branch1.func_78784_a(80, 0).func_78790_a(-3.5F, -32.0F, -3.5F, 7, 7, 7, f);
      branch1.func_78793_a(-1.0F, 0.0F, 0.0F);
      this.setRotation(branch1, -7.0F, 17.0F, 0.0F);
      this.branches.func_78792_a(branch1);
      ModelRenderer branch1twig1 = new ModelRenderer(this, 80, 16);
      branch1twig1.func_78790_a(-7.5F, -22.0F, -1.5F, 1, 12, 1, f);
      branch1twig1.func_78784_a(80, 0).func_78790_a(-8.5F, -23.0F, -2.5F, 3, 3, 3, f);
      branch1twig1.func_78793_a(1.0F, -5.0F, -7.0F);
      this.setRotation(branch1twig1, -50.0F, 25.0F, 15.0F);
      this.branches.func_78792_a(branch1twig1);
      ModelRenderer branch1twig2 = new ModelRenderer(this, 80, 16);
      branch1twig2.func_78790_a(-14.0F, -26.0F, -5.5F, 2, 12, 2, f);
      branch1twig2.func_78784_a(80, 0).func_78790_a(-15.5F, -28.0F, -7.0F, 5, 5, 5, f);
      branch1twig2.func_78793_a(-2.0F, 1.0F, 7.0F);
      this.setRotation(branch1twig2, 10.0F, 10.0F, 50.0F);
      this.branches.func_78792_a(branch1twig2);
      ModelRenderer branch1twig3 = new ModelRenderer(this, 80, 16);
      branch1twig3.func_78790_a(-7.5F, -24.0F, -3.5F, 1, 12, 1, f);
      branch1twig3.func_78784_a(80, 0).func_78790_a(-8.5F, -25.0F, -4.5F, 3, 3, 3, f);
      branch1twig3.func_78793_a(8.0F, -6.0F, 9.0F);
      this.setRotation(branch1twig3, 15.0F, -20.0F, -30.0F);
      this.branches.func_78792_a(branch1twig3);
      ModelRenderer branch2 = new ModelRenderer(this, 80, 16);
      branch2.func_78790_a(-0.5F, -10.0F, -0.5F, 1, 14, 1, f);
      branch2.func_78784_a(80, 0).func_78790_a(-1.5F, -12.0F, -1.5F, 3, 3, 3, f);
      branch2.func_78793_a(6.0F, 0.0F, 2.0F);
      this.setRotation(branch2, -20.0F, 42.0F, 0.0F);
      this.branches.func_78792_a(branch2);
      ModelRenderer branch3 = new ModelRenderer(this, 80, 16);
      branch3.func_78790_a(-1.0F, -16.0F, -1.0F, 2, 20, 2, f);
      branch3.func_78784_a(80, 0).func_78790_a(-2.5F, -18.0F, -2.5F, 5, 5, 5, f);
      branch3.func_78793_a(3.0F, 0.0F, -3.0F);
      this.setRotation(branch3, 26.0F, -27.0F, 0.0F);
      this.branches.func_78792_a(branch3);
      ModelRenderer branch4 = new ModelRenderer(this, 80, 16);
      branch4.func_78790_a(-1.0F, -18.0F, -1.0F, 2, 22, 2, f);
      branch4.func_78784_a(80, 0).func_78790_a(-2.5F, -20.0F, -2.5F, 5, 5, 5, f);
      branch4.func_78793_a(-5.0F, 0.0F, -4.0F);
      this.setRotation(branch4, 17.0F, 60.0F, 0.0F);
      this.branches.func_78792_a(branch4);
      ModelRenderer branch4twig1 = new ModelRenderer(this, 80, 16);
      branch4twig1.func_78790_a(8.5F, -21.0F, -7.5F, 1, 12, 1, f);
      branch4twig1.func_78784_a(80, 0).func_78790_a(7.0F, -22.0F, -9.0F, 4, 4, 4, f);
      branch4twig1.func_78793_a(-12.0F, -6.0F, 8.0F);
      this.setRotation(branch4twig1, 50.0F, 15.0F, -10.0F);
      this.branches.func_78792_a(branch4twig1);
      ModelRenderer branch5 = new ModelRenderer(this, 80, 16);
      branch5.func_78790_a(-1.0F, -24.0F, -1.0F, 2, 28, 2, f);
      branch5.func_78784_a(80, 0).func_78790_a(-2.0F, -25.0F, -2.0F, 4, 4, 4, f);
      branch5.func_78793_a(-5.0F, 0.0F, 3.0F);
      this.setRotation(branch5, -20.0F, -36.0F, 0.0F);
      this.branches.func_78792_a(branch5);
      this.trunk.func_78792_a(this.branches);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      LOTREntityEnt ent = (LOTREntityEnt)entity;
      this.func_78087_a(f, f1, f2, f3, f4, f5, ent);
      this.trunk.func_78785_a(f5);
      this.rightLeg.func_78785_a(f5);
      this.leftLeg.func_78785_a(f5);
      if (ent != null) {
         int numBranches = ent.getExtraHeadBranches();

         for(int l = 0; l < numBranches; ++l) {
            GL11.glPushMatrix();
            this.trunk.func_78794_c(f5);
            float angle = 90.0F + (float)l / (float)numBranches * 360.0F;
            GL11.glTranslatef(0.0F, -2.7F, 0.0F);
            GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, 2.6F, 0.0F);
            this.branches.func_78785_a(f5);
            GL11.glPopMatrix();
         }
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      LOTREntityEnt ent = (LOTREntityEnt)entity;
      this.trunk.field_78795_f = 0.0F;
      boolean healing = ent != null && ent.isHealingSapling();
      if (healing) {
         this.trunk.field_78795_f = 0.3F + MathHelper.func_76126_a(f2 * 0.08F) * 0.1F;
      }

      this.eyeRight.field_78806_j = ent != null && ent.eyesClosed > 0;
      this.eyeLeft.field_78806_j = ent != null && ent.eyesClosed > 0;
      if (ent != null && ent.field_70737_aN > 0) {
         this.browRight.field_78808_h = (float)Math.toRadians(30.0D);
         this.browLeft.field_78808_h = -this.browRight.field_78808_h;
         this.browRight.field_78797_d = this.browLeft.field_78797_d = -40.0F;
      } else {
         this.browRight.field_78808_h = (float)Math.toRadians(10.0D);
         this.browLeft.field_78808_h = -this.browRight.field_78808_h;
         this.browRight.field_78797_d = this.browLeft.field_78797_d = -39.0F;
      }

      this.rightArm.field_78795_f = 0.0F;
      this.rightHand.field_78795_f = 0.0F;
      this.leftArm.field_78795_f = 0.0F;
      this.leftHand.field_78795_f = 0.0F;
      this.rightArm.field_78808_h = 0.0F;
      this.leftArm.field_78808_h = 0.0F;
      float armHealing;
      if (this.field_78095_p > -9990.0F) {
         armHealing = this.field_78095_p;
         armHealing = 1.0F - this.field_78095_p;
         armHealing *= armHealing;
         armHealing *= armHealing;
         armHealing = 1.0F - armHealing;
         float f8 = MathHelper.func_76126_a(armHealing * 3.1415927F);
         float f9 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -(this.trunk.field_78795_f - 0.7F) * 0.75F;
         this.rightArm.field_78795_f -= f8 * 1.2F + f9;
         this.leftArm.field_78795_f -= f8 * 1.2F + f9;
      }

      ModelRenderer var10000 = this.rightArm;
      var10000.field_78808_h += MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      var10000 = this.leftArm;
      var10000.field_78808_h -= MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      var10000 = this.rightArm;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.3F + 3.1415927F) * 0.8F * f1;
      var10000 = this.leftArm;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.3F) * 0.8F * f1;
      if (healing) {
         armHealing = -0.5F + MathHelper.func_76126_a(f2 * 0.2F) * 0.4F;
         var10000 = this.rightArm;
         var10000.field_78795_f += armHealing;
         var10000 = this.leftArm;
         var10000.field_78795_f += armHealing;
      }

      if (this.rightArm.field_78795_f < 0.0F) {
         this.rightHand.field_78795_f = this.rightArm.field_78795_f / 3.1415927F * 2.5F;
      }

      if (this.leftArm.field_78795_f < 0.0F) {
         this.leftHand.field_78795_f = this.leftArm.field_78795_f / 3.1415927F * 2.5F;
      }

      this.rightLeg.field_78795_f = 0.0F;
      this.rightFoot.field_78795_f = 0.0F;
      this.leftLeg.field_78795_f = 0.0F;
      this.leftFoot.field_78795_f = 0.0F;
      var10000 = this.rightLeg;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.3F + 3.1415927F) * 1.2F * f1;
      var10000 = this.leftLeg;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.3F) * 1.2F * f1;
      if (this.rightLeg.field_78795_f < 0.0F) {
         this.rightFoot.field_78795_f = -(this.rightLeg.field_78795_f / 3.1415927F) * 2.5F;
      }

      if (this.leftLeg.field_78795_f < 0.0F) {
         this.leftFoot.field_78795_f = -(this.leftLeg.field_78795_f / 3.1415927F) * 2.5F;
      }

   }

   private void setRotation(ModelRenderer part, float x, float y, float z) {
      part.field_78795_f = (float)Math.toRadians((double)x);
      part.field_78796_g = (float)Math.toRadians((double)y);
      part.field_78808_h = (float)Math.toRadians((double)z);
   }

   public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.trunk.func_78785_a(f5);
   }
}
