package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelArmorStand extends ModelBase {
   private ModelRenderer base = new ModelRenderer(this, 0, 0);
   private ModelRenderer head;
   private ModelRenderer spine;
   private ModelRenderer rightArm;
   private ModelRenderer leftArm;
   private ModelRenderer rightHip;
   private ModelRenderer leftHip;
   private ModelRenderer rightLeg;
   private ModelRenderer leftLeg;
   private ModelRenderer rightFoot;
   private ModelRenderer leftFoot;

   public LOTRModelArmorStand() {
      this.base.func_78789_a(-8.0F, -8.0F, -8.0F, 16, 16, 2);
      this.base.func_78793_a(0.0F, 30.0F, 0.0F);
      this.base.field_78795_f = -1.5707964F;
      this.head = new ModelRenderer(this, 40, 0);
      this.head.func_78789_a(-3.0F, 0.0F, -2.0F, 6, 8, 4);
      this.head.func_78793_a(0.0F, -11.0F, 0.0F);
      this.spine = new ModelRenderer(this, 60, 0);
      this.spine.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 25, 1);
      this.spine.func_78793_a(0.0F, -3.0F, 0.0F);
      this.rightArm = new ModelRenderer(this, 44, 12);
      this.rightArm.func_78789_a(-7.5F, 0.0F, -0.5F, 7, 1, 1);
      this.rightArm.func_78793_a(0.0F, -2.0F, 0.0F);
      this.leftArm = new ModelRenderer(this, 44, 12);
      this.leftArm.field_78809_i = true;
      this.leftArm.func_78789_a(0.5F, 0.0F, -0.5F, 7, 1, 1);
      this.leftArm.func_78793_a(0.0F, -2.0F, 0.0F);
      this.rightHip = new ModelRenderer(this, 42, 30);
      this.rightHip.func_78789_a(-3.5F, 0.0F, -0.5F, 3, 1, 1);
      this.rightHip.func_78793_a(0.0F, 9.0F, 0.0F);
      this.leftHip = new ModelRenderer(this, 42, 30);
      this.leftHip.field_78809_i = true;
      this.leftHip.func_78789_a(0.5F, 0.0F, -0.5F, 3, 1, 1);
      this.leftHip.func_78793_a(0.0F, 9.0F, 0.0F);
      this.rightLeg = new ModelRenderer(this, 50, 23);
      this.rightLeg.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 8, 1);
      this.rightLeg.func_78793_a(-2.0F, 10.0F, 0.0F);
      this.leftLeg = new ModelRenderer(this, 50, 23);
      this.leftLeg.field_78809_i = true;
      this.leftLeg.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 8, 1);
      this.leftLeg.func_78793_a(2.0F, 10.0F, 0.0F);
      this.rightFoot = new ModelRenderer(this, 54, 27);
      this.rightFoot.func_78789_a(-2.0F, 0.0F, -1.0F, 3, 3, 2);
      this.rightFoot.func_78793_a(-2.0F, 18.0F, 0.0F);
      this.leftFoot = new ModelRenderer(this, 54, 27);
      this.leftFoot.field_78809_i = true;
      this.leftFoot.func_78789_a(-1.0F, 0.0F, -1.0F, 3, 3, 2);
      this.leftFoot.func_78793_a(2.0F, 18.0F, 0.0F);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.base.func_78785_a(f5);
      this.head.func_78785_a(f5);
      this.spine.func_78785_a(f5);
      this.rightArm.func_78785_a(f5);
      this.leftArm.func_78785_a(f5);
      this.rightHip.func_78785_a(f5);
      this.leftHip.func_78785_a(f5);
      this.rightLeg.func_78785_a(f5);
      this.leftLeg.func_78785_a(f5);
      this.rightFoot.func_78785_a(f5);
      this.leftFoot.func_78785_a(f5);
   }
}
