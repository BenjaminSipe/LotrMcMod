package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelRhino extends ModelBase {
   private ModelRenderer head;
   private ModelRenderer neck;
   private ModelRenderer horn1;
   private ModelRenderer horn2;
   private ModelRenderer body;
   private ModelRenderer tail;
   private ModelRenderer leg1;
   private ModelRenderer leg2;
   private ModelRenderer leg3;
   private ModelRenderer leg4;

   public LOTRModelRhino() {
      this(0.0F);
   }

   public LOTRModelRhino(float f) {
      this.field_78090_t = 128;
      this.field_78089_u = 128;
      this.head = new ModelRenderer(this, 0, 0);
      this.head.func_78793_a(0.0F, 3.0F, -12.0F);
      this.head.func_78790_a(-5.0F, -2.0F, -22.0F, 10, 10, 16, f);
      this.head.func_78790_a(-4.0F, -4.0F, -10.0F, 1, 2, 2, f);
      this.head.field_78809_i = true;
      this.head.func_78790_a(3.0F, -4.0F, -10.0F, 1, 2, 2, f);
      this.neck = new ModelRenderer(this, 52, 0);
      this.neck.func_78793_a(0.0F, 3.0F, -12.0F);
      this.neck.func_78790_a(-7.0F, -4.0F, -7.0F, 14, 13, 8, f);
      this.horn1 = new ModelRenderer(this, 36, 0);
      this.horn1.func_78790_a(-1.0F, -14.0F, -20.0F, 2, 8, 2, f);
      this.horn1.field_78795_f = (float)Math.toRadians(15.0D);
      this.head.func_78792_a(this.horn1);
      this.horn2 = new ModelRenderer(this, 44, 0);
      this.horn2.func_78790_a(-1.0F, -3.0F, -17.0F, 2, 4, 2, f);
      this.horn2.field_78795_f = (float)Math.toRadians(-10.0D);
      this.head.func_78792_a(this.horn2);
      this.body = new ModelRenderer(this, 0, 26);
      this.body.func_78793_a(0.0F, 5.0F, 0.0F);
      this.body.func_78790_a(-8.0F, -7.0F, -13.0F, 16, 16, 34, f);
      this.tail = new ModelRenderer(this, 100, 63);
      this.tail.func_78793_a(0.0F, 7.0F, 21.0F);
      this.tail.func_78790_a(-1.5F, -1.0F, -1.0F, 3, 8, 2, f);
      this.leg1 = new ModelRenderer(this, 30, 76);
      this.leg1.func_78793_a(-8.0F, 3.0F, 14.0F);
      this.leg1.func_78790_a(-8.0F, -3.0F, -5.0F, 8, 12, 10, f);
      this.leg1.func_78784_a(0, 95).func_78790_a(-7.0F, 9.0F, -3.0F, 6, 12, 6, f);
      this.leg2 = new ModelRenderer(this, 30, 76);
      this.leg2.func_78793_a(8.0F, 3.0F, 14.0F);
      this.leg2.field_78809_i = true;
      this.leg2.func_78790_a(0.0F, -3.0F, -5.0F, 8, 12, 10, f);
      this.leg2.func_78784_a(0, 95).func_78790_a(1.0F, 9.0F, -3.0F, 6, 12, 6, f);
      this.leg3 = new ModelRenderer(this, 0, 76);
      this.leg3.func_78793_a(-8.0F, 4.0F, -6.0F);
      this.leg3.func_78790_a(-7.0F, -3.0F, -4.0F, 7, 11, 8, f);
      this.leg3.func_78784_a(0, 95).func_78790_a(-6.5F, 8.0F, -3.0F, 6, 12, 6, f);
      this.leg4 = new ModelRenderer(this, 0, 76);
      this.leg4.func_78793_a(8.0F, 4.0F, -6.0F);
      this.leg4.field_78809_i = true;
      this.leg4.func_78790_a(0.0F, -3.0F, -4.0F, 7, 11, 8, f);
      this.leg4.func_78784_a(0, 95).func_78790_a(0.5F, 8.0F, -3.0F, 6, 12, 6, f);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.horn1.field_78806_j = this.horn2.field_78806_j = !this.field_78091_s;
      if (this.field_78091_s) {
         float f6 = 2.0F;
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 8.0F * f5, 12.0F * f5);
         this.head.func_78785_a(f5);
         GL11.glPopMatrix();
         GL11.glPushMatrix();
         GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
         GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F * f5);
         this.body.func_78785_a(f5);
         this.tail.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         GL11.glPopMatrix();
      } else {
         this.head.func_78785_a(f5);
         this.neck.func_78785_a(f5);
         this.body.func_78785_a(f5);
         this.tail.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78795_f = (float)Math.toRadians(12.0D);
      this.head.field_78796_g = 0.0F;
      ModelRenderer var10000 = this.head;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.2F) * 0.3F * f1;
      var10000 = this.head;
      var10000.field_78795_f += (float)Math.toRadians((double)f4);
      var10000 = this.head;
      var10000.field_78796_g += (float)Math.toRadians((double)f3);
      this.neck.field_78795_f = this.head.field_78795_f;
      this.neck.field_78796_g = this.head.field_78796_g;
      this.neck.field_78808_h = this.head.field_78808_h;
      this.tail.field_78795_f = (float)Math.toRadians(40.0D);
      var10000 = this.tail;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.3F) * 0.5F * f1;
      this.leg1.field_78795_f = MathHelper.func_76134_b(f * 0.4F) * 1.0F * f1;
      this.leg2.field_78795_f = MathHelper.func_76134_b(f * 0.4F + 3.1415927F) * 1.0F * f1;
      this.leg3.field_78795_f = MathHelper.func_76134_b(f * 0.4F + 3.1415927F) * 1.0F * f1;
      this.leg4.field_78795_f = MathHelper.func_76134_b(f * 0.4F) * 1.0F * f1;
   }
}
