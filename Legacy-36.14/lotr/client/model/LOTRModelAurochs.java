package lotr.client.model;

import lotr.common.entity.animal.LOTREntityAurochs;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelAurochs extends ModelBase {
   private ModelRenderer body;
   private ModelRenderer leg1;
   private ModelRenderer leg2;
   private ModelRenderer leg3;
   private ModelRenderer leg4;
   private ModelRenderer tail;
   private ModelRenderer head;
   private ModelRenderer horns;
   private ModelRenderer hornLeft1;
   private ModelRenderer hornLeft2;
   private ModelRenderer hornRight1;
   private ModelRenderer hornRight2;

   public LOTRModelAurochs() {
      this.field_78090_t = 128;
      this.field_78089_u = 64;
      this.body = new ModelRenderer(this, 0, 0);
      this.body.func_78793_a(0.0F, 2.0F, -1.0F);
      this.body.func_78789_a(-8.0F, -6.0F, -11.0F, 16, 16, 26);
      this.body.func_78784_a(28, 42).func_78789_a(-8.0F, -8.0F, -8.0F, 16, 2, 10);
      this.body.func_78784_a(84, 31).func_78789_a(-3.0F, 10.0F, 4.0F, 6, 1, 6);
      this.leg1 = new ModelRenderer(this, 0, 42);
      this.leg1.func_78793_a(-5.0F, 12.0F, 9.0F);
      this.leg1.func_78789_a(-2.5F, 0.0F, -2.5F, 5, 12, 5);
      this.leg2 = new ModelRenderer(this, 0, 42);
      this.leg2.func_78793_a(5.0F, 12.0F, 9.0F);
      this.leg2.field_78809_i = true;
      this.leg2.func_78789_a(-2.5F, 0.0F, -2.5F, 5, 12, 5);
      this.leg3 = new ModelRenderer(this, 0, 42);
      this.leg3.func_78793_a(-5.0F, 12.0F, -7.0F);
      this.leg3.func_78789_a(-2.5F, 0.0F, -2.5F, 5, 12, 5);
      this.leg4 = new ModelRenderer(this, 0, 42);
      this.leg4.func_78793_a(5.0F, 12.0F, -7.0F);
      this.leg4.field_78809_i = true;
      this.leg4.func_78789_a(-2.5F, 0.0F, -2.5F, 5, 12, 5);
      this.tail = new ModelRenderer(this, 20, 42);
      this.tail.func_78793_a(0.0F, 1.0F, 14.0F);
      this.tail.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 12, 1);
      this.head = new ModelRenderer(this, 58, 0);
      this.head.func_78793_a(0.0F, -1.0F, -10.0F);
      this.head.func_78789_a(-5.0F, -4.0F, -12.0F, 10, 10, 11);
      this.head.func_78784_a(89, 0).func_78789_a(-3.0F, 1.0F, -15.0F, 6, 4, 4);
      this.head.func_78784_a(105, 0);
      this.head.func_78789_a(-8.0F, -2.5F, -7.0F, 3, 2, 1);
      this.head.field_78809_i = true;
      this.head.func_78789_a(5.0F, -2.5F, -7.0F, 3, 2, 1);
      this.head.field_78809_i = false;
      this.horns = new ModelRenderer(this, 98, 21);
      this.horns.func_78793_a(0.0F, -3.5F, -5.0F);
      this.horns.func_78789_a(-6.0F, -1.5F, -1.5F, 12, 3, 3);
      this.head.func_78792_a(this.horns);
      this.hornLeft1 = new ModelRenderer(this, 112, 27);
      this.hornLeft1.func_78793_a(-6.0F, 0.0F, 0.0F);
      this.hornLeft1.func_78789_a(-5.0F, -1.0F, -1.0F, 6, 2, 2);
      this.hornLeft2 = new ModelRenderer(this, 114, 31);
      this.hornLeft2.func_78793_a(-5.0F, 0.0F, 0.0F);
      this.hornLeft2.func_78789_a(-5.0F, -0.5F, -0.5F, 6, 1, 1);
      this.hornLeft1.func_78792_a(this.hornLeft2);
      this.horns.func_78792_a(this.hornLeft1);
      this.hornRight1 = new ModelRenderer(this, 112, 27);
      this.hornRight1.field_78809_i = true;
      this.hornRight1.func_78793_a(6.0F, 0.0F, 0.0F);
      this.hornRight1.func_78789_a(-1.0F, -1.0F, -1.0F, 6, 2, 2);
      this.hornRight2 = new ModelRenderer(this, 114, 31);
      this.hornRight2.field_78809_i = true;
      this.hornRight2.func_78793_a(5.0F, 0.0F, 0.0F);
      this.hornRight2.func_78789_a(-1.0F, -0.5F, -0.5F, 6, 1, 1);
      this.hornRight1.func_78792_a(this.hornRight2);
      this.horns.func_78792_a(this.hornRight1);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      LOTREntityAurochs aurochs = (LOTREntityAurochs)entity;
      this.horns.field_78806_j = !this.field_78091_s;
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      if (this.field_78091_s) {
         float f6 = 2.0F;
         float f7 = 8.0F;
         float f8 = 6.0F;
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, f7 * f5, f8 * f5);
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
         this.body.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         this.tail.func_78785_a(f5);
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      LOTREntityAurochs aurochs = (LOTREntityAurochs)entity;
      this.head.field_78795_f = 0.0F;
      this.head.field_78796_g = 0.0F;
      this.head.field_78808_h = 0.0F;
      ModelRenderer var10000 = this.head;
      var10000.field_78795_f += (float)Math.toRadians((double)f4);
      var10000 = this.head;
      var10000.field_78796_g += (float)Math.toRadians((double)f3);
      if (aurochs.isAurochsEnraged()) {
         var10000 = this.head;
         var10000.field_78795_f += (float)Math.toRadians(15.0D);
      }

      var10000 = this.head;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.2F) * f1 * 0.4F;
      this.hornLeft1.field_78808_h = (float)Math.toRadians(25.0D);
      this.hornLeft2.field_78808_h = (float)Math.toRadians(15.0D);
      this.hornRight1.field_78808_h = -this.hornLeft1.field_78808_h;
      this.hornRight2.field_78808_h = -this.hornLeft2.field_78808_h;
      this.hornLeft1.field_78796_g = (float)Math.toRadians(-25.0D);
      this.hornRight1.field_78796_g = -this.hornLeft1.field_78796_g;
      this.hornLeft1.field_78795_f = (float)Math.toRadians(35.0D);
      this.hornRight1.field_78795_f = this.hornLeft1.field_78795_f;
      this.leg1.field_78795_f = MathHelper.func_76134_b(f * 0.4F) * f1 * 0.8F;
      this.leg2.field_78795_f = MathHelper.func_76134_b(f * 0.4F + 3.1415927F) * f1 * 0.8F;
      this.leg3.field_78795_f = MathHelper.func_76134_b(f * 0.4F + 3.1415927F) * f1 * 0.8F;
      this.leg4.field_78795_f = MathHelper.func_76134_b(f * 0.4F) * f1 * 0.8F;
   }
}
