package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelGiraffe extends ModelBase {
   public ModelRenderer body = (new ModelRenderer(this, 0, 0)).func_78787_b(128, 64);
   public ModelRenderer neck;
   public ModelRenderer head;
   public ModelRenderer tail;
   public ModelRenderer leg1;
   public ModelRenderer leg2;
   public ModelRenderer leg3;
   public ModelRenderer leg4;

   public LOTRModelGiraffe(float f) {
      this.body.func_78790_a(-6.0F, -8.0F, -13.0F, 12, 16, 26, f);
      this.body.func_78793_a(0.0F, -11.0F, 0.0F);
      this.neck = (new ModelRenderer(this, 0, 44)).func_78787_b(128, 64);
      this.neck.func_78790_a(-4.5F, -13.0F, -4.5F, 9, 11, 9, f);
      this.neck.func_78784_a(78, 0).func_78790_a(-3.0F, -37.0F, -3.0F, 6, 40, 6, f);
      this.neck.func_78793_a(0.0F, -14.0F, -7.0F);
      this.head = (new ModelRenderer(this, 96, 48)).func_78787_b(128, 64);
      this.head.func_78790_a(-3.0F, -43.0F, -6.0F, 6, 6, 10, f);
      this.head.func_78784_a(10, 0).func_78790_a(-4.0F, -45.0F, 1.5F, 1, 3, 2, f);
      this.head.func_78784_a(17, 0).func_78790_a(3.0F, -45.0F, 1.5F, 1, 3, 2, f);
      this.head.func_78784_a(0, 0).func_78790_a(-2.5F, -47.0F, 0.0F, 1, 4, 1, f);
      this.head.func_78784_a(5, 0).func_78790_a(1.5F, -47.0F, 0.0F, 1, 4, 1, f);
      this.head.func_78784_a(76, 56).func_78790_a(-2.0F, -41.0F, -11.0F, 4, 3, 5, f);
      this.head.func_78793_a(0.0F, -14.0F, -7.0F);
      this.tail = (new ModelRenderer(this, 104, 0)).func_78787_b(128, 64);
      this.tail.func_78790_a(-0.5F, 0.0F, 0.0F, 1, 24, 1, f);
      this.tail.func_78793_a(0.0F, -12.0F, 13.0F);
      this.leg1 = (new ModelRenderer(this, 112, 0)).func_78787_b(128, 64);
      this.leg1.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 27, 4, f);
      this.leg1.func_78793_a(-3.9F, -3.0F, 8.0F);
      this.leg2 = (new ModelRenderer(this, 112, 0)).func_78787_b(128, 64);
      this.leg2.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 27, 4, f);
      this.leg2.func_78793_a(3.9F, -3.0F, 8.0F);
      this.leg2.field_78809_i = true;
      this.leg3 = (new ModelRenderer(this, 112, 0)).func_78787_b(128, 64);
      this.leg3.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 27, 4, f);
      this.leg3.func_78793_a(-3.9F, -3.0F, -7.0F);
      this.leg4 = (new ModelRenderer(this, 112, 0)).func_78787_b(128, 64);
      this.leg4.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 27, 4, f);
      this.leg4.func_78793_a(3.9F, -3.0F, -7.0F);
      this.leg4.field_78809_i = true;
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      if (entity.field_70153_n != null && entity.field_70153_n instanceof EntityPlayer) {
         this.setRiddenHeadNeckRotation(f, f1, f2, f3, f4, f5);
      } else {
         this.setDefaultHeadNeckRotation(f, f1, f2, f3, f4, f5);
      }

      if (this.field_78091_s) {
         float f6 = 2.0F;
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 8.0F * f5, 4.0F * f5);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
         GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
         this.head.func_78785_a(f5);
         this.body.func_78785_a(f5);
         this.neck.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         this.tail.func_78785_a(f5);
         GL11.glPopMatrix();
      } else {
         this.head.func_78785_a(f5);
         this.body.func_78785_a(f5);
         this.neck.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         this.tail.func_78785_a(f5);
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.leg1.field_78795_f = 0.5F * MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
      this.leg2.field_78795_f = 0.5F * MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
      this.leg3.field_78795_f = 0.5F * MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
      this.leg4.field_78795_f = 0.5F * MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
      this.tail.field_78808_h = 0.2F * MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
   }

   public void setRiddenHeadNeckRotation(float f, float f1, float f2, float f3, float f4, float f5) {
      this.head.func_78793_a(0.0F, 25.0F, -48.0F);
      this.neck.field_78795_f = 1.5707964F;
      this.neck.field_78796_g = 0.0F;
      this.head.field_78795_f = 0.0F;
      this.head.field_78796_g = 0.0F;
   }

   public void setDefaultHeadNeckRotation(float f, float f1, float f2, float f3, float f4, float f5) {
      this.head.func_78793_a(0.0F, -14.0F, -7.0F);
      this.neck.field_78795_f = 0.17453294F + f4 / 57.29578F;
      this.head.field_78795_f = 0.17453294F + f4 / 57.29578F;
      this.neck.field_78796_g = f3 / 57.29578F;
      this.head.field_78796_g = f3 / 57.29578F;
   }
}
