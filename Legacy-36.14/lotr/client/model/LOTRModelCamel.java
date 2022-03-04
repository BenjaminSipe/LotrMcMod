package lotr.client.model;

import lotr.common.entity.animal.LOTREntityCamel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelCamel extends ModelBase {
   private ModelRenderer body;
   private ModelRenderer humps;
   private ModelRenderer tail;
   private ModelRenderer head;
   private ModelRenderer leg1;
   private ModelRenderer leg2;
   private ModelRenderer leg3;
   private ModelRenderer leg4;
   private ModelRenderer chest;

   public LOTRModelCamel() {
      this(0.0F);
   }

   public LOTRModelCamel(float f) {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.body = new ModelRenderer(this, 0, 16);
      this.body.func_78793_a(0.0F, 10.0F, 0.0F);
      this.body.func_78790_a(-4.5F, -5.0F, -10.0F, 9, 10, 22, f);
      this.humps = new ModelRenderer(this, 34, 0);
      this.humps.func_78793_a(0.0F, 10.0F, 0.0F);
      this.humps.func_78790_a(-3.0F, -9.0F, -8.0F, 6, 4, 6, f);
      this.humps.func_78790_a(-3.0F, -9.0F, 5.0F, 6, 4, 6, f);
      this.tail = new ModelRenderer(this, 54, 52);
      this.tail.func_78793_a(0.0F, 7.0F, 12.0F);
      this.tail.func_78789_a(-1.0F, -1.0F, 0.0F, 2, 10, 2);
      this.head = new ModelRenderer(this, 0, 0);
      this.head.func_78793_a(0.0F, 6.0F, -10.0F);
      this.head.func_78790_a(-3.0F, -13.0F, -10.5F, 6, 5, 11, f);
      this.head.func_78790_a(-2.5F, -15.0F, -1.0F, 2, 2, 1, f);
      this.head.field_78809_i = true;
      this.head.func_78790_a(0.5F, -15.0F, -1.0F, 2, 2, 1, f);
      this.head.field_78809_i = false;
      this.head.func_78784_a(0, 16).func_78790_a(-2.5F, -9.0F, -5.0F, 5, 14, 5, f);
      this.leg1 = new ModelRenderer(this, 0, 52);
      this.leg1.func_78793_a(-4.5F, 7.0F, 8.0F);
      this.leg1.func_78790_a(-4.0F, -1.0F, -2.5F, 4, 7, 5, f);
      this.leg1.func_78784_a(18, 53).func_78790_a(-3.5F, 6.0F, -1.5F, 3, 8, 3, f);
      this.leg1.func_78784_a(30, 57).func_78790_a(-4.0F, 14.0F, -2.0F, 4, 3, 4, f);
      this.leg2 = new ModelRenderer(this, 0, 52);
      this.leg2.field_78809_i = true;
      this.leg2.func_78793_a(4.5F, 7.0F, 8.0F);
      this.leg2.func_78790_a(0.0F, -1.0F, -2.5F, 4, 7, 5, f);
      this.leg2.func_78784_a(18, 53).func_78790_a(0.5F, 6.0F, -1.5F, 3, 8, 3, f);
      this.leg2.func_78784_a(30, 57).func_78790_a(0.0F, 14.0F, -2.0F, 4, 3, 4, f);
      this.leg3 = new ModelRenderer(this, 0, 52);
      this.leg3.func_78793_a(-4.5F, 7.0F, -5.0F);
      this.leg3.func_78790_a(-4.0F, -1.0F, -2.5F, 4, 7, 5, f);
      this.leg3.func_78784_a(18, 53).func_78790_a(-3.5F, 6.0F, -1.5F, 3, 8, 3, f);
      this.leg3.func_78784_a(30, 57).func_78790_a(-4.0F, 14.0F, -2.0F, 4, 3, 4, f);
      this.leg4 = new ModelRenderer(this, 0, 52);
      this.leg4.field_78809_i = true;
      this.leg4.func_78793_a(4.5F, 7.0F, -5.0F);
      this.leg4.func_78790_a(0.0F, -1.0F, -2.5F, 4, 7, 5, f);
      this.leg4.func_78784_a(18, 53).func_78790_a(0.5F, 6.0F, -1.5F, 3, 8, 3, f);
      this.leg4.func_78784_a(30, 57).func_78790_a(0.0F, 14.0F, -2.0F, 4, 3, 4, f);
      this.chest = new ModelRenderer(this, 40, 22);
      this.chest.func_78793_a(0.0F, 10.0F, 0.0F);
      this.chest.func_78790_a(-7.5F, -4.5F, -2.5F, 3, 8, 8, f);
      this.chest.field_78809_i = true;
      this.chest.func_78790_a(4.5F, -4.5F, -2.5F, 3, 8, 8, f);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      LOTREntityCamel camel = (LOTREntityCamel)entity;
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      if (this.field_78091_s) {
         float f6 = 2.0F;
         GL11.glPushMatrix();
         GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
         GL11.glTranslatef(0.0F, 24.0F * f5, 0.0F);
         this.body.func_78785_a(f5);
         this.tail.func_78785_a(f5);
         this.head.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         GL11.glPopMatrix();
      } else {
         this.body.func_78785_a(f5);
         this.humps.func_78785_a(f5);
         this.tail.func_78785_a(f5);
         this.head.func_78785_a(f5);
         this.leg1.func_78785_a(f5);
         this.leg2.func_78785_a(f5);
         this.leg3.func_78785_a(f5);
         this.leg4.func_78785_a(f5);
         if (camel.func_110261_ca()) {
            this.chest.func_78785_a(f5);
         }
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78795_f = f4 / (float)Math.toDegrees(1.0D);
      this.head.field_78796_g = f3 / (float)Math.toDegrees(1.0D);
      ModelRenderer var10000 = this.head;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.3331F) * 0.1F * f1;
      this.leg1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 0.8F * f1;
      this.leg2.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 0.8F * f1;
      this.leg3.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 0.8F * f1;
      this.leg4.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 0.8F * f1;
      this.tail.field_78808_h = 0.1F * MathHelper.func_76134_b(f * 0.3331F + 3.1415927F) * f1;
   }
}
