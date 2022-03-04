package lotr.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelLionOld extends ModelBase {
   private ModelRenderer head;
   private ModelRenderer headwear;
   private ModelRenderer mane;
   private ModelRenderer body;
   private ModelRenderer leg1;
   private ModelRenderer leg2;
   private ModelRenderer leg3;
   private ModelRenderer leg4;

   public LOTRModelLionOld() {
      this.field_78090_t = 64;
      this.field_78089_u = 96;
      this.head = new ModelRenderer(this, 0, 0);
      this.head.func_78793_a(0.0F, 4.0F, -9.0F);
      this.head.func_78790_a(-4.0F, -4.0F, -7.0F, 8, 8, 8, 0.0F);
      this.head.func_78784_a(52, 34).func_78789_a(-2.0F, 0.0F, -9.0F, 4, 4, 2);
      this.headwear = new ModelRenderer(this, 32, 0);
      this.headwear.func_78793_a(0.0F, 4.0F, -9.0F);
      this.headwear.func_78790_a(-4.0F, -4.0F, -7.0F, 8, 8, 8, 0.5F);
      this.mane = new ModelRenderer(this, 0, 36);
      this.mane.func_78793_a(0.0F, 4.0F, -9.0F);
      this.mane.func_78790_a(-7.0F, -7.0F, -5.0F, 14, 14, 9, 0.0F);
      this.body = new ModelRenderer(this, 0, 68);
      this.body.func_78790_a(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
      this.body.func_78793_a(0.0F, 5.0F, 2.0F);
      this.leg1 = new ModelRenderer(this, 0, 19);
      this.leg1.func_78793_a(-4.0F, 12.0F, 7.0F);
      this.leg1.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
      this.leg2 = new ModelRenderer(this, 0, 19);
      this.leg2.field_78809_i = true;
      this.leg2.func_78793_a(4.0F, 12.0F, 7.0F);
      this.leg2.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
      this.leg3 = new ModelRenderer(this, 0, 19);
      this.leg3.func_78793_a(-4.0F, 12.0F, -5.0F);
      this.leg3.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
      this.leg4 = new ModelRenderer(this, 0, 19);
      this.leg4.field_78809_i = true;
      this.leg4.func_78793_a(4.0F, 12.0F, -5.0F);
      this.leg4.func_78790_a(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      if (this.field_78091_s) {
         float f6 = 2.0F;
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 8.0F * f5, 4.0F * f5);
         this.head.func_78785_a(f5);
         this.headwear.func_78785_a(f5);
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
         this.headwear.func_78785_a(f5);
         this.mane.func_78785_a(f5);
         this.body.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78795_f = (float)Math.toRadians((double)f4);
      this.head.field_78796_g = (float)Math.toRadians((double)f3);
      this.headwear.field_78795_f = this.head.field_78795_f;
      this.headwear.field_78796_g = this.head.field_78796_g;
      this.mane.field_78795_f = this.head.field_78795_f;
      this.mane.field_78796_g = this.head.field_78796_g;
      this.body.field_78795_f = 1.5707964F;
      this.leg1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
      this.leg2.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
      this.leg3.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 1.4F * f1;
      this.leg4.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 1.4F * f1;
   }
}
