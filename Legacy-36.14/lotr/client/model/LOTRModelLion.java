package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelLion extends ModelBase {
   protected ModelRenderer head;
   protected ModelRenderer mane;
   protected ModelRenderer body;
   protected ModelRenderer leg1;
   protected ModelRenderer leg2;
   protected ModelRenderer leg3;
   protected ModelRenderer leg4;
   protected ModelRenderer tail;

   public LOTRModelLion() {
      this.field_78090_t = 128;
      this.field_78089_u = 64;
      this.head = new ModelRenderer(this, 48, 0);
      this.head.func_78793_a(0.0F, 3.0F, -10.0F);
      this.head.func_78789_a(-5.0F, -6.0F, -10.0F, 10, 10, 10);
      this.head.func_78784_a(78, 0).func_78789_a(-3.0F, -1.0F, -14.0F, 6, 5, 4);
      this.head.func_78784_a(98, 0).func_78789_a(-1.0F, -2.0F, -14.2F, 2, 2, 5);
      this.head.func_78784_a(0, 0);
      this.head.func_78789_a(-4.0F, -9.0F, -7.5F, 3, 3, 1);
      this.head.field_78809_i = true;
      this.head.func_78789_a(1.0F, -9.0F, -7.5F, 3, 3, 1);
      this.mane = new ModelRenderer(this, 0, 0);
      this.mane.func_78793_a(0.0F, 3.0F, -10.0F);
      this.mane.func_78789_a(-8.0F, -10.0F, -6.0F, 16, 16, 8);
      this.body = new ModelRenderer(this, 0, 24);
      this.body.func_78793_a(0.0F, 6.0F, 1.0F);
      this.body.func_78789_a(-7.0F, -6.5F, -11.0F, 14, 14, 24);
      this.leg1 = new ModelRenderer(this, 52, 24);
      this.leg1.func_78793_a(-4.0F, 4.0F, 11.0F);
      this.leg1.func_78789_a(-6.0F, -2.0F, -3.5F, 6, 10, 8);
      this.leg1.func_78784_a(106, 24).func_78789_a(-5.5F, 8.0F, -2.5F, 5, 12, 5);
      this.leg2 = new ModelRenderer(this, 52, 24);
      this.leg2.func_78793_a(4.0F, 4.0F, 11.0F);
      this.leg2.field_78809_i = true;
      this.leg2.func_78789_a(0.0F, -2.0F, -3.5F, 6, 10, 8);
      this.leg2.func_78784_a(106, 24).func_78789_a(0.5F, 8.0F, -2.5F, 5, 12, 5);
      this.leg3 = new ModelRenderer(this, 80, 24);
      this.leg3.func_78793_a(-4.0F, 5.0F, -5.0F);
      this.leg3.func_78789_a(-6.0F, -2.0F, -3.5F, 6, 9, 7);
      this.leg3.func_78784_a(106, 24).func_78789_a(-5.5F, 7.0F, -2.5F, 5, 12, 5);
      this.leg4 = new ModelRenderer(this, 80, 24);
      this.leg4.func_78793_a(4.0F, 5.0F, -5.0F);
      this.leg4.field_78809_i = true;
      this.leg4.func_78789_a(0.0F, -2.0F, -3.5F, 6, 9, 7);
      this.leg4.func_78784_a(106, 24).func_78789_a(0.5F, 7.0F, -2.5F, 5, 12, 5);
      this.tail = new ModelRenderer(this, 100, 50);
      this.tail.func_78793_a(0.0F, 4.0F, 13.0F);
      this.tail.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 2, 12);
      this.tail.func_78784_a(86, 57).func_78789_a(-1.5F, -1.5F, 12.0F, 3, 3, 4);
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
         this.tail.func_78785_a(f5);
         GL11.glPopMatrix();
      } else {
         this.head.func_78785_a(f5);
         this.mane.func_78785_a(f5);
         this.body.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         this.tail.func_78785_a(f5);
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78795_f = (float)Math.toRadians((double)f4);
      this.head.field_78796_g = (float)Math.toRadians((double)f3);
      this.mane.field_78795_f = this.head.field_78795_f;
      this.mane.field_78796_g = this.head.field_78796_g;
      this.leg1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.0F * f1;
      this.leg2.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.0F * f1;
      this.leg3.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.0F * f1;
      this.leg4.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.0F * f1;
      this.tail.field_78795_f = (float)Math.toRadians(-60.0D);
      ModelRenderer var10000 = this.tail;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.3F) * 0.5F * f1;
   }
}
