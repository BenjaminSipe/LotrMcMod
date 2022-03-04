package lotr.client.model;

import lotr.common.LOTRConfig;
import lotr.common.entity.npc.LOTREntityBalrog;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelBalrog extends ModelBase {
   public ModelRenderer body;
   public ModelRenderer neck;
   public ModelRenderer head;
   public ModelRenderer rightArm;
   public ModelRenderer leftArm;
   public ModelRenderer rightLeg;
   public ModelRenderer leftLeg;
   public ModelRenderer tail;
   public ModelRenderer rightWing;
   public ModelRenderer leftWing;
   private boolean isFireModel;
   public int heldItemRight;

   public LOTRModelBalrog() {
      this(0.0F);
   }

   public LOTRModelBalrog(float f) {
      this.field_78090_t = 128;
      this.field_78089_u = 256;
      this.body = new ModelRenderer(this, 0, 38);
      this.body.func_78793_a(0.0F, 7.0F, 3.0F);
      this.body.func_78790_a(-8.0F, -15.0F, -6.0F, 16, 18, 12, f);
      this.body.func_78784_a(0, 207);
      this.body.func_78790_a(-9.0F, -6.5F, -7.0F, 7, 1, 14, f);
      this.body.func_78790_a(-9.0F, -9.5F, -7.0F, 7, 1, 14, f);
      this.body.func_78790_a(-9.0F, -12.5F, -7.0F, 7, 1, 14, f);
      this.body.field_78809_i = true;
      this.body.func_78790_a(2.0F, -6.5F, -7.0F, 7, 1, 14, f);
      this.body.func_78790_a(2.0F, -9.5F, -7.0F, 7, 1, 14, f);
      this.body.func_78790_a(2.0F, -12.5F, -7.0F, 7, 1, 14, f);
      this.body.field_78809_i = false;
      this.body.func_78784_a(0, 0).func_78790_a(-9.0F, -29.0F, -7.0F, 18, 14, 15, f);
      this.body.func_78784_a(81, 163).func_78790_a(-2.0F, -21.0F, 5.5F, 4, 25, 2, f);
      this.neck = new ModelRenderer(this, 76, 0);
      this.neck.func_78793_a(0.0F, -25.0F, -3.0F);
      this.neck.func_78790_a(-6.0F, -5.0F, -10.0F, 12, 12, 14, f);
      this.body.func_78792_a(this.neck);
      this.head = new ModelRenderer(this, 92, 48);
      this.head.func_78793_a(0.0F, 0.0F, -10.0F);
      this.head.func_78790_a(-4.0F, -6.0F, -6.0F, 8, 10, 7, f);
      this.head.func_78784_a(57, 58).func_78790_a(-6.0F, -7.0F, -4.0F, 12, 4, 4, f);
      this.head.field_78795_f = (float)Math.toRadians(10.0D);
      this.neck.func_78792_a(this.head);
      ModelRenderer rightHorn1 = new ModelRenderer(this, 57, 47);
      rightHorn1.func_78793_a(-6.0F, -5.0F, -2.0F);
      rightHorn1.func_78790_a(-7.0F, -1.5F, -1.5F, 8, 3, 3, f);
      rightHorn1.field_78796_g = (float)Math.toRadians(-35.0D);
      this.head.func_78792_a(rightHorn1);
      ModelRenderer rightHorn2 = new ModelRenderer(this, 57, 35);
      rightHorn2.func_78793_a(-7.0F, 0.0F, 0.0F);
      rightHorn2.func_78790_a(-1.0F, -1.0F, -6.0F, 2, 2, 6, f);
      rightHorn2.field_78796_g = (float)Math.toRadians(45.0D);
      rightHorn1.func_78792_a(rightHorn2);
      ModelRenderer leftHorn1 = new ModelRenderer(this, 57, 47);
      leftHorn1.func_78793_a(6.0F, -5.0F, -2.0F);
      leftHorn1.field_78809_i = true;
      leftHorn1.func_78790_a(-1.0F, -1.5F, -1.5F, 8, 3, 3, f);
      leftHorn1.field_78796_g = (float)Math.toRadians(35.0D);
      this.head.func_78792_a(leftHorn1);
      ModelRenderer leftHorn2 = new ModelRenderer(this, 57, 35);
      leftHorn2.func_78793_a(7.0F, 0.0F, 0.0F);
      leftHorn2.field_78809_i = true;
      leftHorn2.func_78790_a(-1.0F, -1.0F, -6.0F, 2, 2, 6, f);
      leftHorn2.field_78796_g = (float)Math.toRadians(-45.0D);
      leftHorn1.func_78792_a(leftHorn2);
      this.rightArm = new ModelRenderer(this, 59, 136);
      this.rightArm.func_78793_a(-9.0F, -25.0F, 0.0F);
      this.rightArm.func_78790_a(-7.0F, -2.0F, -4.0F, 7, 10, 8, f);
      this.rightArm.func_78784_a(93, 136).func_78790_a(-6.5F, 8.0F, -3.0F, 6, 16, 6, f);
      this.body.func_78792_a(this.rightArm);
      this.leftArm = new ModelRenderer(this, 59, 136);
      this.leftArm.func_78793_a(9.0F, -25.0F, 0.0F);
      this.leftArm.field_78809_i = true;
      this.leftArm.func_78790_a(0.0F, -2.0F, -4.0F, 7, 10, 8, f);
      this.leftArm.func_78784_a(93, 136).func_78790_a(0.5F, 8.0F, -3.0F, 6, 16, 6, f);
      this.body.func_78792_a(this.leftArm);
      this.rightLeg = new ModelRenderer(this, 46, 230);
      this.rightLeg.func_78793_a(-6.0F, 6.0F, 3.0F);
      this.rightLeg.func_78790_a(-7.0F, -2.0F, -4.0F, 7, 9, 8, f);
      this.rightLeg.func_78784_a(46, 208).func_78790_a(-6.5F, 2.0F, 4.0F, 6, 13, 5, f);
      ModelRenderer rightFoot = new ModelRenderer(this, 0, 243);
      rightFoot.func_78793_a(0.0F, 0.0F, 0.0F);
      rightFoot.func_78790_a(-7.0F, 15.0F, -6.0F, 7, 3, 9, f);
      rightFoot.field_78795_f = (float)Math.toRadians(25.0D);
      this.rightLeg.func_78792_a(rightFoot);
      this.leftLeg = new ModelRenderer(this, 46, 230);
      this.leftLeg.func_78793_a(6.0F, 6.0F, 3.0F);
      this.leftLeg.field_78809_i = true;
      this.leftLeg.func_78790_a(0.0F, -2.0F, -4.0F, 7, 9, 8, f);
      this.leftLeg.func_78784_a(46, 208).func_78790_a(0.5F, 2.0F, 4.0F, 6, 13, 5, f);
      ModelRenderer leftFoot = new ModelRenderer(this, 0, 243);
      leftFoot.func_78793_a(0.0F, 0.0F, 0.0F);
      leftFoot.field_78809_i = true;
      leftFoot.func_78790_a(0.0F, 15.0F, -6.0F, 7, 3, 9, f);
      leftFoot.field_78795_f = (float)Math.toRadians(25.0D);
      this.leftLeg.func_78792_a(leftFoot);
      this.tail = new ModelRenderer(this, 79, 200);
      this.tail.func_78793_a(0.0F, -3.0F, 3.0F);
      this.tail.func_78790_a(-3.5F, -3.0F, 2.0F, 7, 7, 10, f);
      this.tail.func_78784_a(80, 225).func_78790_a(-2.5F, -2.5F, 11.0F, 5, 5, 14, f);
      this.tail.func_78784_a(96, 175).func_78790_a(-1.5F, -2.0F, 24.0F, 3, 3, 12, f);
      this.body.func_78792_a(this.tail);
      this.rightWing = new ModelRenderer(this, 0, 137);
      this.rightWing.func_78793_a(-6.0F, -27.0F, 4.0F);
      this.rightWing.func_78790_a(-1.5F, -1.5F, 0.0F, 3, 3, 25, f);
      this.rightWing.func_78784_a(0, 167).func_78790_a(-1.0F, -2.0F, 25.0F, 2, 24, 2, f);
      this.rightWing.func_78784_a(0, 30).func_78790_a(-0.5F, -7.0F, 25.5F, 1, 5, 1, f);
      this.rightWing.func_78784_a(0, 69).func_78790_a(0.0F, 0.0F, 0.0F, 0, 35, 25, f);
      this.body.func_78792_a(this.rightWing);
      this.leftWing = new ModelRenderer(this, 0, 137);
      this.leftWing.func_78793_a(6.0F, -27.0F, 4.0F);
      this.leftWing.field_78809_i = true;
      this.leftWing.func_78790_a(-1.5F, -1.5F, 0.0F, 3, 3, 25, f);
      this.leftWing.func_78784_a(0, 167).func_78790_a(-1.0F, -2.0F, 25.0F, 2, 24, 2, f);
      this.leftWing.func_78784_a(0, 30).func_78790_a(-0.5F, -7.0F, 25.5F, 1, 5, 1, f);
      this.leftWing.func_78784_a(0, 69).func_78790_a(0.0F, 0.0F, 0.0F, 0, 35, 25, f);
      this.body.func_78792_a(this.leftWing);
   }

   public void setFireModel() {
      this.isFireModel = true;
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
      if (this.isFireModel) {
         this.leftWing.field_78806_j = this.rightWing.field_78806_j = false;
      } else {
         this.leftWing.field_78806_j = this.rightWing.field_78806_j = LOTRConfig.balrogWings && balrog.isWreathedInFlame();
      }

      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.body.func_78785_a(f5);
      this.rightLeg.func_78785_a(f5);
      this.leftLeg.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      LOTREntityBalrog balrog = (LOTREntityBalrog)entity;
      this.neck.field_78795_f = (float)Math.toRadians(-10.0D);
      this.neck.field_78796_g = 0.0F;
      ModelRenderer var10000 = this.neck;
      var10000.field_78795_f += f4 / (float)Math.toDegrees(1.0D);
      var10000 = this.neck;
      var10000.field_78796_g += f3 / (float)Math.toDegrees(1.0D);
      this.body.field_78795_f = (float)Math.toRadians(10.0D);
      var10000 = this.body;
      var10000.field_78795_f += MathHelper.func_76134_b(f2 * 0.03F) * 0.15F;
      this.rightArm.field_78795_f = 0.0F;
      this.leftArm.field_78795_f = 0.0F;
      this.rightArm.field_78808_h = 0.0F;
      this.leftArm.field_78808_h = 0.0F;
      var10000 = this.rightArm;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.4F + 3.1415927F) * 0.8F * f1;
      var10000 = this.leftArm;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.4F) * 0.8F * f1;
      var10000 = this.rightArm;
      var10000.field_78808_h += MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      var10000 = this.leftArm;
      var10000.field_78808_h -= MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      if (this.field_78095_p > -9990.0F) {
         float f6 = this.field_78095_p;
         var10000 = this.rightArm;
         var10000.field_78796_g += this.body.field_78796_g;
         var10000 = this.leftArm;
         var10000.field_78796_g += this.body.field_78796_g;
         var10000 = this.leftArm;
         var10000.field_78795_f += this.body.field_78796_g;
         f6 = 1.0F - this.field_78095_p;
         f6 *= f6;
         f6 *= f6;
         f6 = 1.0F - f6;
         float f7 = MathHelper.func_76126_a(f6 * 3.1415927F);
         float f8 = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -(this.head.field_78795_f - 0.7F) * 0.75F;
         this.rightArm.field_78795_f = (float)((double)this.rightArm.field_78795_f - ((double)f7 * 1.2D + (double)f8));
         var10000 = this.rightArm;
         var10000.field_78796_g += this.body.field_78796_g * 2.0F;
         this.rightArm.field_78808_h = MathHelper.func_76126_a(this.field_78095_p * 3.1415927F) * -0.4F;
      }

      if (this.heldItemRight != 0) {
         this.rightArm.field_78795_f = this.rightArm.field_78795_f * 0.5F - 0.31415927F * (float)this.heldItemRight;
      }

      this.rightLeg.field_78795_f = (float)Math.toRadians(-25.0D);
      this.leftLeg.field_78795_f = (float)Math.toRadians(-25.0D);
      var10000 = this.rightLeg;
      var10000.field_78795_f += MathHelper.func_76126_a(f * 0.4F) * 1.2F * f1;
      var10000 = this.leftLeg;
      var10000.field_78795_f += MathHelper.func_76126_a(f * 0.4F + 3.1415927F) * 1.2F * f1;
      this.rightWing.field_78795_f = (float)Math.toRadians(40.0D);
      this.leftWing.field_78795_f = (float)Math.toRadians(40.0D);
      this.rightWing.field_78796_g = (float)Math.toRadians(-40.0D);
      this.leftWing.field_78796_g = (float)Math.toRadians(40.0D);
      var10000 = this.rightWing;
      var10000.field_78796_g += MathHelper.func_76134_b(f2 * 0.04F) * 0.5F;
      var10000 = this.leftWing;
      var10000.field_78796_g -= MathHelper.func_76134_b(f2 * 0.04F) * 0.5F;
      this.tail.field_78795_f = (float)Math.toRadians(-40.0D);
      this.tail.field_78796_g = 0.0F;
      var10000 = this.tail;
      var10000.field_78796_g += MathHelper.func_76134_b(f2 * 0.05F) * 0.15F;
      var10000 = this.tail;
      var10000.field_78796_g += MathHelper.func_76126_a(f * 0.1F) * 0.6F * f1;
   }
}
