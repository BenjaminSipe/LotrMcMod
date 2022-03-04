package lotr.client.model;

import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelGollum extends ModelBase {
   public ModelRenderer head = new ModelRenderer(this, 0, 0);
   public ModelRenderer body;
   public ModelRenderer rightShoulder;
   public ModelRenderer rightArm;
   public ModelRenderer leftShoulder;
   public ModelRenderer leftArm;
   public ModelRenderer rightThigh;
   public ModelRenderer rightLeg;
   public ModelRenderer leftThigh;
   public ModelRenderer leftLeg;

   public LOTRModelGollum() {
      this.head.func_78789_a(-3.5F, -6.5F, -6.5F, 7, 7, 7);
      this.head.func_78793_a(0.0F, 5.0F, -5.5F);
      this.head.func_78789_a(3.5F, -4.0F, -4.0F, 1, 2, 2);
      this.head.field_78809_i = true;
      this.head.func_78789_a(-4.5F, -4.0F, -4.0F, 1, 2, 2);
      this.body = new ModelRenderer(this, 20, 17);
      this.body.func_78789_a(-5.0F, -12.0F, -2.0F, 10, 12, 3);
      this.body.func_78793_a(0.0F, 11.0F, 5.0F);
      this.body.func_78784_a(32, 0).func_78789_a(-5.5F, -2.0F, -3.5F, 11, 4, 5);
      this.body.field_78795_f = 1.0471976F;
      this.rightShoulder = new ModelRenderer(this, 0, 23);
      this.rightShoulder.func_78789_a(-0.5F, -1.0F, -2.0F, 3, 6, 3);
      this.rightShoulder.func_78793_a(5.0F, 6.0F, -4.5F);
      this.rightShoulder.field_78795_f = 0.5235988F;
      this.rightArm = new ModelRenderer(this, 12, 22);
      this.rightArm.func_78789_a(0.0F, 4.0F, 0.5F, 2, 8, 2);
      this.rightArm.func_78793_a(5.0F, 6.0F, -4.5F);
      this.leftShoulder = new ModelRenderer(this, 0, 23);
      this.leftShoulder.field_78809_i = true;
      this.leftShoulder.func_78789_a(-1.5F, -1.0F, -2.0F, 3, 6, 3);
      this.leftShoulder.func_78793_a(-5.0F, 6.0F, -4.5F);
      this.leftShoulder.field_78795_f = 0.5235988F;
      this.leftArm = new ModelRenderer(this, 12, 22);
      this.leftArm.field_78809_i = true;
      this.leftArm.func_78789_a(-1.0F, 4.0F, 0.5F, 2, 8, 2);
      this.leftArm.func_78793_a(-5.0F, 6.0F, -4.5F);
      this.rightThigh = new ModelRenderer(this, 0, 23);
      this.rightThigh.func_78789_a(-0.5F, -1.0F, -1.0F, 3, 6, 3);
      this.rightThigh.func_78793_a(2.0F, 12.0F, 4.0F);
      this.rightThigh.field_78795_f = -0.43633232F;
      this.rightLeg = new ModelRenderer(this, 12, 22);
      this.rightLeg.func_78789_a(0.0F, 4.0F, -2.5F, 2, 8, 2);
      this.rightLeg.func_78793_a(2.0F, 12.0F, 4.0F);
      this.leftThigh = new ModelRenderer(this, 0, 23);
      this.leftThigh.func_78789_a(-2.5F, -1.0F, -1.0F, 3, 6, 3);
      this.leftThigh.func_78793_a(-2.0F, 12.0F, 4.0F);
      this.leftThigh.field_78795_f = -0.43633232F;
      this.leftLeg = new ModelRenderer(this, 12, 22);
      this.leftLeg.func_78789_a(-2.0F, 4.0F, -2.5F, 2, 8, 2);
      this.leftLeg.func_78793_a(-2.0F, 12.0F, 4.0F);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.head.func_78785_a(f5);
      this.body.func_78785_a(f5);
      this.rightShoulder.func_78785_a(f5);
      this.rightArm.func_78785_a(f5);
      this.leftShoulder.func_78785_a(f5);
      this.leftArm.func_78785_a(f5);
      this.rightThigh.func_78785_a(f5);
      this.rightLeg.func_78785_a(f5);
      this.leftThigh.func_78785_a(f5);
      this.leftLeg.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78796_g = f3 / 57.295776F;
      this.head.field_78795_f = f4 / 57.295776F;
      this.rightArm.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 2.0F * f1 * 0.5F;
      this.leftArm.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 2.0F * f1 * 0.5F;
      this.rightArm.field_78808_h = 0.0F;
      this.leftArm.field_78808_h = 0.0F;
      this.rightLeg.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
      this.leftLeg.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
      this.rightLeg.field_78796_g = 0.0F;
      this.leftLeg.field_78796_g = 0.0F;
      this.rightArm.field_78796_g = 0.0F;
      this.leftArm.field_78796_g = 0.0F;
      ModelRenderer var10000 = this.rightArm;
      var10000.field_78808_h += MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      var10000 = this.leftArm;
      var10000.field_78808_h -= MathHelper.func_76134_b(f2 * 0.09F) * 0.05F + 0.05F;
      var10000 = this.rightArm;
      var10000.field_78795_f += MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
      var10000 = this.leftArm;
      var10000.field_78795_f -= MathHelper.func_76126_a(f2 * 0.067F) * 0.05F;
      if (((LOTREntityGollum)entity).isGollumSitting()) {
         float f6 = f2 / 20.0F;
         f6 *= 6.2831855F;
         this.rightArm.field_78795_f = MathHelper.func_76126_a(f6) * 3.0F;
         this.leftArm.field_78795_f = MathHelper.func_76126_a(f6) * -3.0F;
         this.rightLeg.field_78795_f = MathHelper.func_76126_a(f6) * 0.5F;
         this.leftLeg.field_78795_f = MathHelper.func_76126_a(f6) * -0.5F;
      } else if (((LOTREntityGollum)entity).isGollumFleeing()) {
         var10000 = this.rightArm;
         var10000.field_78795_f += 3.1415927F;
         var10000 = this.leftArm;
         var10000.field_78795_f += 3.1415927F;
      }

      this.body.field_78808_h = MathHelper.func_76134_b(f * 0.6662F) * 0.25F * f1;
      this.syncRotationAngles(this.rightArm, this.rightShoulder, 30.0F);
      this.syncRotationAngles(this.leftArm, this.leftShoulder, 30.0F);
      this.syncRotationAngles(this.rightLeg, this.rightThigh, -25.0F);
      this.syncRotationAngles(this.leftLeg, this.leftThigh, -25.0F);
   }

   private void syncRotationAngles(ModelRenderer source, ModelRenderer target, float additionalAngle) {
      target.field_78800_c = source.field_78800_c;
      target.field_78797_d = source.field_78797_d;
      target.field_78798_e = source.field_78798_e;
      target.field_78795_f = source.field_78795_f + 0.017453292F * additionalAngle;
      target.field_78796_g = source.field_78796_g;
      target.field_78808_h = source.field_78808_h;
   }
}
