package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelBear extends ModelBase {
   public ModelRenderer head;
   public ModelRenderer nose;
   public ModelRenderer body;
   public ModelRenderer leg1;
   public ModelRenderer leg2;
   public ModelRenderer leg3;
   public ModelRenderer leg4;

   public LOTRModelBear() {
      this.field_78090_t = 128;
      this.field_78089_u = 64;
      this.head = new ModelRenderer(this, 32, 0);
      this.head.func_78793_a(0.0F, 8.0F, -9.0F);
      this.head.func_78789_a(-4.0F, -5.0F, -4.0F, 8, 9, 6);
      this.head.func_78784_a(0, 0).func_78789_a(-4.5F, -5.5F, -11.0F, 9, 10, 7);
      this.nose = new ModelRenderer(this, 0, 17);
      this.nose.func_78793_a(0.0F, 0.0F, 0.0F);
      this.nose.func_78789_a(-2.5F, -2.0F, -17.0F, 5, 6, 6);
      this.nose.func_78784_a(0, 29).func_78789_a(-1.5F, -2.5F, -17.5F, 3, 3, 7);
      this.head.func_78792_a(this.nose);
      ModelRenderer earRight = new ModelRenderer(this, 23, 17);
      earRight.func_78793_a(0.0F, 0.0F, 0.0F);
      earRight.func_78789_a(-4.0F, -8.0F, -6.0F, 3, 3, 1);
      earRight.field_78808_h = (float)Math.toRadians(-15.0D);
      this.head.func_78792_a(earRight);
      ModelRenderer earLeft = new ModelRenderer(this, 23, 17);
      earLeft.field_78809_i = true;
      earLeft.func_78793_a(0.0F, 0.0F, 0.0F);
      earLeft.func_78789_a(1.0F, -8.0F, -6.0F, 3, 3, 1);
      earLeft.field_78808_h = (float)Math.toRadians(15.0D);
      this.head.func_78792_a(earLeft);
      this.body = new ModelRenderer(this, 40, 0);
      this.body.func_78793_a(0.0F, 10.0F, -2.0F);
      this.body.func_78789_a(-6.0F, -8.0F, -9.0F, 12, 14, 28);
      this.body.func_78784_a(92, 0).func_78789_a(-2.5F, -6.0F, 19.0F, 5, 5, 2);
      this.leg1 = new ModelRenderer(this, 56, 44);
      this.leg1.func_78793_a(-4.0F, 6.0F, 10.0F);
      this.leg1.func_78789_a(-6.0F, -2.0F, -3.5F, 6, 9, 9);
      this.leg1.func_78784_a(86, 44).func_78789_a(-5.5F, 7.0F, -1.5F, 5, 11, 5);
      this.leg2 = new ModelRenderer(this, 56, 44);
      this.leg2.field_78809_i = true;
      this.leg2.func_78793_a(4.0F, 6.0F, 10.0F);
      this.leg2.func_78789_a(0.0F, -2.0F, -3.5F, 6, 9, 9);
      this.leg2.func_78784_a(86, 44).func_78789_a(0.5F, 7.0F, -1.5F, 5, 11, 5);
      this.leg3 = new ModelRenderer(this, 0, 44);
      this.leg3.func_78793_a(-3.0F, 6.0F, -5.0F);
      this.leg3.func_78789_a(-6.0F, -2.0F, -3.0F, 6, 9, 8);
      this.leg3.func_78784_a(28, 44).func_78789_a(-5.5F, 7.0F, -1.5F, 5, 12, 5);
      this.leg4 = new ModelRenderer(this, 0, 44);
      this.leg4.field_78809_i = true;
      this.leg4.func_78793_a(3.0F, 6.0F, -5.0F);
      this.leg4.func_78789_a(0.0F, -2.0F, -3.0F, 6, 9, 8);
      this.leg4.func_78784_a(28, 44).func_78789_a(0.5F, 7.0F, -1.5F, 5, 12, 5);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      if (this.field_78091_s) {
         float f6 = 2.0F;
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 8.0F * f5, 4.0F * f5);
         this.head.func_78785_a(f5);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
         GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
         this.body.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         GL11.glPopMatrix();
      } else {
         this.head.func_78785_a(f5);
         this.body.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78795_f = (float)Math.toRadians(10.0D);
      this.head.field_78796_g = 0.0F;
      ModelRenderer var10000 = this.head;
      var10000.field_78795_f += (float)Math.toRadians((double)f4);
      var10000 = this.head;
      var10000.field_78796_g += (float)Math.toRadians((double)f3);
      this.leg1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.0F * f1;
      this.leg2.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.0F * f1;
      this.leg3.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.0F * f1;
      this.leg4.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.0F * f1;
      this.nose.field_78798_e = this.field_78091_s ? 3.0F : 0.0F;
   }
}
