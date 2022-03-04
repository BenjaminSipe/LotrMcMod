package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelGemsbok extends ModelBase {
   private ModelRenderer head = (new ModelRenderer(this, 28, 0)).func_78787_b(128, 64);
   private ModelRenderer tail;
   private ModelRenderer earLeft;
   private ModelRenderer earRight;
   private ModelRenderer neck;
   private ModelRenderer body;
   private ModelRenderer leg1;
   private ModelRenderer leg2;
   private ModelRenderer leg3;
   private ModelRenderer leg4;
   private ModelRenderer leftHorn;
   private ModelRenderer rightHorn;

   public LOTRModelGemsbok() {
      this.head.func_78789_a(-3.0F, -10.0F, -6.0F, 6, 7, 12);
      this.head.func_78793_a(0.0F, 4.0F, -9.0F);
      this.tail = (new ModelRenderer(this, 0, 0)).func_78787_b(128, 64);
      this.tail.func_78789_a(0.0F, 0.0F, 0.0F, 2, 12, 2);
      this.tail.func_78793_a(-1.0F, 3.0F, 11.0F);
      this.earLeft = (new ModelRenderer(this, 28, 19)).func_78787_b(128, 64);
      this.earLeft.func_78789_a(-3.8F, -12.0F, 3.0F, 1, 3, 2);
      this.earLeft.func_78793_a(0.0F, 4.0F, -9.0F);
      this.earRight = (new ModelRenderer(this, 34, 19)).func_78787_b(128, 64);
      this.earRight.func_78789_a(2.8F, -12.0F, 3.0F, 1, 3, 2);
      this.earRight.func_78793_a(0.0F, 4.0F, -9.0F);
      this.neck = (new ModelRenderer(this, 0, 14)).func_78787_b(128, 64);
      this.neck.func_78789_a(-2.5F, -6.0F, -5.0F, 5, 8, 9);
      this.neck.func_78793_a(0.0F, 4.0F, -9.0F);
      this.body = (new ModelRenderer(this, 0, 31)).func_78787_b(128, 64);
      this.body.func_78789_a(-7.0F, -10.0F, -7.0F, 13, 10, 23);
      this.body.func_78793_a(0.5F, 12.0F, -3.0F);
      this.leg1 = (new ModelRenderer(this, 0, 38)).func_78787_b(128, 64);
      this.leg1.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
      this.leg1.func_78793_a(-4.0F, 12.0F, 10.0F);
      this.leg2 = (new ModelRenderer(this, 0, 38)).func_78787_b(128, 64);
      this.leg2.func_78789_a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
      this.leg2.func_78793_a(4.0F, 12.0F, 10.0F);
      this.leg3 = (new ModelRenderer(this, 0, 38)).func_78787_b(128, 64);
      this.leg3.func_78789_a(-2.0F, 0.0F, -3.0F, 4, 12, 4);
      this.leg3.func_78793_a(-4.0F, 12.0F, -7.0F);
      this.leg4 = (new ModelRenderer(this, 0, 38)).func_78787_b(128, 64);
      this.leg4.func_78789_a(-2.0F, 0.0F, -3.0F, 4, 12, 4);
      this.leg4.func_78793_a(4.0F, 12.0F, -7.0F);
      this.leftHorn = (new ModelRenderer(this, 0, 0)).func_78787_b(128, 64);
      this.leftHorn.func_78789_a(-2.8F, -9.5F, 5.8F, 1, 1, 13);
      this.leftHorn.func_78793_a(0.0F, 4.0F, -9.0F);
      this.rightHorn = (new ModelRenderer(this, 0, 0)).func_78787_b(128, 64);
      this.rightHorn.func_78789_a(1.8F, -9.5F, 5.8F, 1, 1, 13);
      this.rightHorn.func_78793_a(0.0F, 4.0F, -9.0F);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      if (this.field_78091_s) {
         float f6 = 2.0F;
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 8.0F * f5, 4.0F * f5);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
         GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
         this.head.func_78785_a(f5);
         this.earLeft.func_78785_a(f5);
         this.earRight.func_78785_a(f5);
         this.neck.func_78785_a(f5);
         this.body.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         this.tail.func_78785_a(f5);
         GL11.glPopMatrix();
      } else {
         this.head.func_78785_a(f5);
         this.neck.func_78785_a(f5);
         this.leftHorn.func_78785_a(f5);
         this.rightHorn.func_78785_a(f5);
         this.earLeft.func_78785_a(f5);
         this.earRight.func_78785_a(f5);
         this.body.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         this.tail.func_78785_a(f5);
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78795_f = (float)Math.toRadians((double)f4) + 0.4014257F;
      this.head.field_78796_g = (float)Math.toRadians((double)f3);
      this.neck.field_78795_f = (float)Math.toRadians(-61.0D);
      this.neck.field_78796_g = this.head.field_78796_g * 0.7F;
      this.rightHorn.field_78795_f = this.head.field_78795_f;
      this.rightHorn.field_78796_g = this.head.field_78796_g;
      this.leftHorn.field_78795_f = this.head.field_78795_f;
      this.leftHorn.field_78796_g = this.head.field_78796_g;
      this.earLeft.field_78795_f = this.head.field_78795_f;
      this.earLeft.field_78796_g = this.head.field_78796_g;
      this.earRight.field_78795_f = this.head.field_78795_f;
      this.earRight.field_78796_g = this.head.field_78796_g;
      this.leg1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
      this.leg2.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
      this.leg3.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
      this.leg4.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
      this.tail.field_78795_f = (float)Math.toRadians(17.0D);
   }
}
