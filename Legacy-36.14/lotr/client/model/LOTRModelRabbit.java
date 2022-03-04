package lotr.client.model;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityRabbit;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelRabbit extends ModelBase {
   private ModelRenderer head = new ModelRenderer(this, 0, 0);
   private ModelRenderer body;
   private ModelRenderer rightArm;
   private ModelRenderer leftArm;
   private ModelRenderer rightLeg;
   private ModelRenderer leftLeg;

   public LOTRModelRabbit() {
      this.head.func_78789_a(-2.0F, -2.0F, -2.0F, 4, 4, 4);
      this.head.func_78793_a(0.0F, -7.0F, 0.0F);
      this.head.func_78784_a(0, 8).func_78789_a(-1.5F, 0.0F, -3.0F, 3, 2, 2);
      ModelRenderer rightEar = new ModelRenderer(this, 16, 0);
      rightEar.func_78789_a(-1.2F, -4.5F, -0.5F, 2, 5, 1);
      rightEar.func_78793_a(-1.0F, -1.5F, 0.0F);
      rightEar.field_78795_f = (float)Math.toRadians(-20.0D);
      ModelRenderer leftEar = new ModelRenderer(this, 16, 0);
      leftEar.field_78809_i = true;
      leftEar.func_78789_a(-0.8F, -4.5F, -0.5F, 2, 5, 1);
      leftEar.func_78793_a(1.0F, -1.5F, 0.0F);
      leftEar.field_78795_f = (float)Math.toRadians(-20.0D);
      this.head.func_78792_a(rightEar);
      this.head.func_78792_a(leftEar);
      this.body = new ModelRenderer(this, 0, 19);
      this.body.func_78789_a(-2.5F, -4.0F, -2.5F, 5, 8, 5);
      this.body.func_78793_a(0.0F, 18.5F, 0.0F);
      this.body.func_78784_a(0, 14).func_78789_a(-1.5F, -6.0F, -1.5F, 3, 2, 3);
      ModelRenderer tail = new ModelRenderer(this, 32, 30);
      tail.func_78789_a(-0.5F, -0.5F, -0.5F, 1, 1, 1);
      tail.func_78793_a(0.0F, 4.5F, 2.5F);
      tail.field_78795_f = (float)Math.toRadians(-45.0D);
      this.body.func_78792_a(this.head);
      this.body.func_78792_a(tail);
      this.rightArm = new ModelRenderer(this, 32, 0);
      this.rightArm.func_78789_a(-0.5F, -0.5F, -0.5F, 1, 4, 1);
      this.rightArm.func_78793_a(-1.5F, -2.0F, -2.5F);
      this.leftArm = new ModelRenderer(this, 32, 0);
      this.leftArm.field_78809_i = true;
      this.leftArm.func_78789_a(-0.5F, -0.5F, -0.5F, 1, 4, 1);
      this.leftArm.func_78793_a(1.5F, -2.0F, -2.5F);
      this.body.func_78792_a(this.rightArm);
      this.body.func_78792_a(this.leftArm);
      this.rightLeg = new ModelRenderer(this, 32, 8);
      this.rightLeg.func_78789_a(-1.0F, -2.0F, -2.0F, 2, 4, 4);
      this.rightLeg.func_78793_a(-3.0F, 21.5F, 1.0F);
      ModelRenderer rightFoot = new ModelRenderer(this, 32, 16);
      rightFoot.func_78789_a(-1.0F, -0.5F, -2.5F, 2, 1, 3);
      rightFoot.func_78793_a(0.0F, 2.0F, -1.0F);
      rightFoot.field_78795_f = (float)Math.toRadians(-15.0D);
      this.rightLeg.func_78792_a(rightFoot);
      this.leftLeg = new ModelRenderer(this, 32, 8);
      this.leftLeg.field_78809_i = true;
      this.leftLeg.func_78789_a(-1.0F, -2.0F, -2.0F, 2, 4, 4);
      this.leftLeg.func_78793_a(3.0F, 21.5F, 1.0F);
      ModelRenderer leftFoot = new ModelRenderer(this, 32, 16);
      leftFoot.field_78809_i = true;
      leftFoot.func_78789_a(-1.0F, -0.5F, -2.5F, 2, 1, 3);
      leftFoot.func_78793_a(0.0F, 2.0F, -1.0F);
      leftFoot.field_78795_f = (float)Math.toRadians(-15.0D);
      this.leftLeg.func_78792_a(leftFoot);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.body.func_78785_a(f5);
      this.rightLeg.func_78785_a(f5);
      this.leftLeg.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.body.field_78795_f = (float)Math.toRadians(45.0D);
      this.head.field_78795_f = (float)Math.toRadians(-45.0D);
      this.rightArm.field_78795_f = (float)Math.toRadians(-55.0D);
      this.leftArm.field_78795_f = (float)Math.toRadians(-55.0D);
      float f6 = (float)Math.toRadians(45.0D);
      if (LOTRMod.isAprilFools()) {
         f6 *= f2;
      } else {
         f6 *= f1;
      }

      ModelRenderer var10000 = this.body;
      var10000.field_78795_f += f6;
      var10000 = this.head;
      var10000.field_78795_f -= f6;
      var10000 = this.rightArm;
      var10000.field_78795_f -= f6;
      var10000 = this.leftArm;
      var10000.field_78795_f -= f6;
      if (((LOTREntityRabbit)entity).isRabbitEating()) {
         float f7 = (float)Math.toRadians(30.0D);
         var10000 = this.body;
         var10000.field_78795_f += f7;
         var10000 = this.rightArm;
         var10000.field_78795_f += f7;
         var10000 = this.leftArm;
         var10000.field_78795_f += f7;
         var10000 = this.head;
         var10000.field_78795_f += f7 * 2.0F;
      } else {
         var10000 = this.head;
         var10000.field_78795_f += f4 / 57.295776F;
         this.head.field_78796_g = MathHelper.func_76134_b(this.head.field_78795_f) * f3 / 57.295776F;
         this.head.field_78808_h = MathHelper.func_76126_a(this.head.field_78795_f) * f3 / 57.295776F;
      }

      var10000 = this.rightArm;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * f1;
      var10000 = this.leftArm;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.6662F) * f1;
      this.body.field_78808_h = MathHelper.func_76134_b(f * 0.6662F) * f1 * 0.3F;
      this.rightLeg.field_78795_f = (float)Math.toRadians(15.0D);
      this.leftLeg.field_78795_f = (float)Math.toRadians(15.0D);
      var10000 = this.rightLeg;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
      var10000 = this.leftLeg;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
   }
}
