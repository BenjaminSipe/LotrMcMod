package lotr.client.model;

import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelDeer extends ModelBase {
   private ModelRenderer body;
   private ModelRenderer leg1;
   private ModelRenderer leg2;
   private ModelRenderer leg3;
   private ModelRenderer leg4;
   private ModelRenderer leg1Foot;
   private ModelRenderer leg2Foot;
   private ModelRenderer leg3Foot;
   private ModelRenderer leg4Foot;
   private ModelRenderer tail;
   private ModelRenderer head;
   private ModelRenderer antlers;

   public LOTRModelDeer() {
      this(0.0F);
   }

   public LOTRModelDeer(float f) {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.body = new ModelRenderer(this, 0, 0);
      this.body.func_78793_a(0.0F, 14.0F, 0.0F);
      this.body.func_78790_a(-3.5F, -4.0F, -7.0F, 7, 7, 15, f);
      this.leg1 = new ModelRenderer(this, 12, 46);
      this.leg1.func_78793_a(-4.0F, 14.0F, 5.0F);
      this.leg1.func_78790_a(-1.0F, -2.0F, -2.0F, 3, 6, 4, f);
      this.leg1Foot = new ModelRenderer(this, 12, 56);
      this.leg1Foot.func_78793_a(0.5F, 4.0F, 0.0F);
      this.leg1Foot.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 6, 2, f);
      this.leg1.func_78792_a(this.leg1Foot);
      this.leg2 = new ModelRenderer(this, 12, 46);
      this.leg2.func_78793_a(4.0F, 14.0F, 5.0F);
      this.leg2.field_78809_i = true;
      this.leg2.func_78790_a(-2.0F, -2.0F, -2.0F, 3, 6, 4, f);
      this.leg2Foot = new ModelRenderer(this, 12, 56);
      this.leg2Foot.func_78793_a(-0.5F, 4.0F, 0.0F);
      this.leg2Foot.field_78809_i = true;
      this.leg2Foot.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 6, 2, f);
      this.leg2.func_78792_a(this.leg2Foot);
      this.leg3 = new ModelRenderer(this, 0, 47);
      this.leg3.func_78793_a(-3.0F, 14.0F, -4.0F);
      this.leg3.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 6, 3, f);
      this.leg3Foot = new ModelRenderer(this, 0, 56);
      this.leg3Foot.func_78793_a(0.0F, 4.0F, 0.0F);
      this.leg3Foot.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 6, 2, f);
      this.leg3.func_78792_a(this.leg3Foot);
      this.leg4 = new ModelRenderer(this, 0, 47);
      this.leg4.func_78793_a(3.0F, 14.0F, -4.0F);
      this.leg4.field_78809_i = true;
      this.leg4.func_78790_a(-1.5F, -2.0F, -1.5F, 3, 6, 3, f);
      this.leg4Foot = new ModelRenderer(this, 0, 56);
      this.leg4Foot.func_78793_a(0.0F, 4.0F, 0.0F);
      this.leg4Foot.field_78809_i = true;
      this.leg4Foot.func_78790_a(-1.0F, 0.0F, -1.0F, 2, 6, 2, f);
      this.leg4.func_78792_a(this.leg4Foot);
      this.tail = new ModelRenderer(this, 20, 58);
      this.tail.func_78793_a(0.0F, 14.0F, 0.0F);
      this.tail.func_78790_a(-1.5F, -8.0F, 3.0F, 3, 2, 4, f);
      this.head = new ModelRenderer(this, 0, 22);
      this.head.func_78793_a(0.0F, 11.0F, -5.0F);
      this.head.func_78790_a(-2.5F, -8.0F, -6.0F, 5, 4, 7, f);
      this.head.func_78784_a(24, 22).func_78790_a(-2.0F, -4.0F, -4.0F, 4, 7, 4, f);
      ModelRenderer earRight = new ModelRenderer(this, 0, 22);
      earRight.func_78793_a(-2.0F, -8.0F, 0.0F);
      earRight.func_78790_a(-1.0F, -2.0F, -0.5F, 2, 3, 1, f);
      earRight.field_78796_g = (float)Math.toRadians(30.0D);
      earRight.field_78808_h = (float)Math.toRadians(-50.0D);
      this.head.func_78792_a(earRight);
      ModelRenderer earLeft = new ModelRenderer(this, 0, 22);
      earLeft.func_78793_a(2.0F, -8.0F, 0.0F);
      earLeft.field_78809_i = true;
      earLeft.func_78790_a(-1.0F, -2.0F, -0.5F, 2, 3, 1, f);
      earLeft.field_78796_g = (float)Math.toRadians(-30.0D);
      earLeft.field_78808_h = (float)Math.toRadians(50.0D);
      this.head.func_78792_a(earLeft);
      this.antlers = new ModelRenderer(this, 0, 0);
      this.antlers.func_78793_a(0.0F, 0.0F, 0.0F);
      this.head.func_78792_a(this.antlers);
      ModelRenderer antlerRight1 = new ModelRenderer(this, 0, 33);
      antlerRight1.func_78793_a(-2.0F, -7.0F, 1.0F);
      antlerRight1.func_78790_a(-0.5F, -8.0F, -0.5F, 1, 9, 1, f);
      antlerRight1.field_78795_f = (float)Math.toRadians(-40.0D);
      antlerRight1.field_78808_h = (float)Math.toRadians(-35.0D);
      this.antlers.func_78792_a(antlerRight1);
      ModelRenderer antlerRight2 = new ModelRenderer(this, 4, 33);
      antlerRight2.func_78793_a(-2.0F, -6.0F, 0.0F);
      antlerRight2.func_78790_a(-0.5F, -6.0F, -0.5F, 1, 6, 1, f);
      antlerRight2.field_78795_f = (float)Math.toRadians(-60.0D);
      antlerRight2.field_78796_g = (float)Math.toRadians(-50.0D);
      antlerRight2.field_78808_h = (float)Math.toRadians(-20.0D);
      this.antlers.func_78792_a(antlerRight2);
      ModelRenderer antlerLeft1 = new ModelRenderer(this, 0, 33);
      antlerLeft1.func_78793_a(2.0F, -7.0F, 1.0F);
      antlerLeft1.field_78809_i = true;
      antlerLeft1.func_78790_a(-0.5F, -8.0F, -0.5F, 1, 9, 1, f);
      antlerLeft1.field_78795_f = (float)Math.toRadians(-40.0D);
      antlerLeft1.field_78808_h = (float)Math.toRadians(35.0D);
      this.antlers.func_78792_a(antlerLeft1);
      ModelRenderer antlerLeft2 = new ModelRenderer(this, 4, 33);
      antlerLeft2.func_78793_a(2.0F, -6.0F, 0.0F);
      antlerLeft2.field_78809_i = true;
      antlerLeft2.func_78790_a(-0.5F, -6.0F, -0.5F, 1, 6, 1, f);
      antlerLeft2.field_78795_f = (float)Math.toRadians(-60.0D);
      antlerLeft2.field_78796_g = (float)Math.toRadians(50.0D);
      antlerLeft2.field_78808_h = (float)Math.toRadians(20.0D);
      this.antlers.func_78792_a(antlerLeft2);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      LOTREntityDeer deer = (LOTREntityDeer)entity;
      this.antlers.field_78806_j = deer.isMale() && !this.field_78091_s;
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      if (this.field_78091_s) {
         float f6 = 2.0F;
         GL11.glPushMatrix();
         GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
         GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
         this.head.func_78785_a(f5);
         this.body.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         this.tail.func_78785_a(f5);
         GL11.glPopMatrix();
      } else {
         this.head.func_78785_a(f5);
         this.body.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         this.tail.func_78785_a(f5);
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78795_f = (float)Math.toRadians(30.0D);
      this.head.field_78796_g = 0.0F;
      ModelRenderer var10000 = this.head;
      var10000.field_78795_f += (float)Math.toRadians((double)f4);
      var10000 = this.head;
      var10000.field_78796_g += (float)Math.toRadians((double)f3);
      this.leg1.field_78795_f = MathHelper.func_76134_b(f * 0.8F) * f1 * 1.4F;
      this.leg2.field_78795_f = MathHelper.func_76134_b(f * 0.8F + 3.1415927F) * f1 * 1.4F;
      this.leg3.field_78795_f = MathHelper.func_76134_b(f * 0.8F + 3.1415927F) * f1 * 1.4F;
      this.leg4.field_78795_f = MathHelper.func_76134_b(f * 0.8F) * f1 * 1.4F;
      this.leg1Foot.field_78795_f = this.leg1.field_78795_f * -0.6F;
      this.leg2Foot.field_78795_f = this.leg2.field_78795_f * -0.6F;
      this.leg3Foot.field_78795_f = this.leg3.field_78795_f * -0.6F;
      this.leg4Foot.field_78795_f = this.leg4.field_78795_f * -0.6F;
      this.tail.field_78795_f = (float)Math.toRadians(-50.0D);
   }
}
