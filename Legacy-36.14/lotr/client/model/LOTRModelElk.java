package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityElk;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class LOTRModelElk extends ModelBase {
   private ModelRenderer body;
   private ModelRenderer leg1;
   private ModelRenderer leg2;
   private ModelRenderer leg3;
   private ModelRenderer leg4;
   private ModelRenderer head;
   private ModelRenderer nose;
   private ModelRenderer antlersRight_1;
   private ModelRenderer antlersRight_2;
   private ModelRenderer antlersRight_3;
   private ModelRenderer antlersRight_4;
   private ModelRenderer antlersLeft_1;
   private ModelRenderer antlersLeft_2;
   private ModelRenderer antlersLeft_3;
   private ModelRenderer antlersLeft_4;

   public LOTRModelElk() {
      this(0.0F);
   }

   public LOTRModelElk(float f) {
      this.field_78090_t = 128;
      this.field_78089_u = 64;
      this.body = new ModelRenderer(this, 0, 0);
      this.body.func_78793_a(0.0F, 4.0F, 9.0F);
      this.body.func_78790_a(-6.0F, -4.0F, -21.0F, 12, 11, 26, f);
      ModelRenderer tail = new ModelRenderer(this, 0, 54);
      tail.func_78790_a(-1.0F, -5.0F, 2.0F, 2, 2, 8, f);
      tail.field_78795_f = (float)Math.toRadians(-60.0D);
      this.body.func_78792_a(tail);
      this.leg1 = new ModelRenderer(this, 42, 37);
      this.leg1.func_78793_a(-4.0F, 3.0F, 8.0F);
      this.leg1.func_78790_a(-5.5F, 0.0F, -3.0F, 7, 11, 8, f);
      this.leg1.func_78784_a(26, 37).func_78790_a(-4.0F, 11.0F, -1.0F, 4, 10, 4, f);
      this.leg2 = new ModelRenderer(this, 42, 37);
      this.leg2.field_78809_i = true;
      this.leg2.func_78793_a(4.0F, 3.0F, 8.0F);
      this.leg2.func_78790_a(-1.5F, 0.0F, -3.0F, 7, 11, 8, f);
      this.leg2.func_78784_a(26, 37).func_78790_a(0.0F, 11.0F, -1.0F, 4, 10, 4, f);
      this.leg3 = new ModelRenderer(this, 0, 37);
      this.leg3.func_78793_a(-4.0F, 4.0F, -6.0F);
      this.leg3.func_78790_a(-4.5F, 0.0F, -3.0F, 6, 10, 7, f);
      this.leg3.func_78784_a(26, 37).func_78790_a(-3.5F, 10.0F, -2.0F, 4, 10, 4, f);
      this.leg4 = new ModelRenderer(this, 0, 37);
      this.leg4.field_78809_i = true;
      this.leg4.func_78793_a(4.0F, 4.0F, -6.0F);
      this.leg4.func_78790_a(-1.5F, 0.0F, -3.0F, 6, 10, 7, f);
      this.leg4.func_78784_a(26, 37).func_78790_a(-0.5F, 10.0F, -2.0F, 4, 10, 4, f);
      this.head = new ModelRenderer(this, 50, 0);
      this.head.func_78793_a(0.0F, 4.0F, -10.0F);
      this.head.func_78790_a(-2.0F, -10.0F, -4.0F, 4, 12, 8, f);
      this.head.func_78784_a(74, 0).func_78790_a(-3.0F, -16.0F, -8.0F, 6, 6, 13, f);
      this.head.func_78784_a(50, 20);
      this.head.func_78790_a(-2.0F, -18.0F, 3.0F, 1, 2, 1, f);
      this.head.field_78809_i = true;
      this.head.func_78790_a(1.0F, -18.0F, 3.0F, 1, 2, 1, f);
      this.nose = new ModelRenderer(this, 56, 20);
      this.nose.func_78790_a(-1.0F, -14.5F, -9.0F, 2, 2, 1, f);
      this.antlersRight_1 = new ModelRenderer(this, 0, 0);
      this.antlersRight_1.func_78790_a(10.0F, -19.0F, 2.5F, 1, 12, 1, f);
      this.antlersRight_1.field_78808_h = (float)Math.toRadians(-65.0D);
      this.antlersRight_2 = new ModelRenderer(this, 4, 0);
      this.antlersRight_2.func_78790_a(-3.0F, -23.6F, 2.5F, 1, 8, 1, f);
      this.antlersRight_2.field_78808_h = (float)Math.toRadians(-15.0D);
      this.antlersRight_3 = new ModelRenderer(this, 8, 0);
      this.antlersRight_3.func_78790_a(-8.0F, -36.0F, 2.5F, 1, 16, 1, f);
      this.antlersRight_3.field_78808_h = (float)Math.toRadians(-15.0D);
      this.antlersRight_4 = new ModelRenderer(this, 12, 0);
      this.antlersRight_4.func_78790_a(7.5F, -35.0F, 2.5F, 1, 10, 1, f);
      this.antlersRight_4.field_78808_h = (float)Math.toRadians(-50.0D);
      this.head.func_78792_a(this.antlersRight_1);
      this.head.func_78792_a(this.antlersRight_2);
      this.head.func_78792_a(this.antlersRight_3);
      this.head.func_78792_a(this.antlersRight_4);
      this.antlersLeft_1 = new ModelRenderer(this, 0, 0);
      this.antlersLeft_1.field_78809_i = true;
      this.antlersLeft_1.func_78790_a(-11.0F, -19.0F, 2.5F, 1, 12, 1, f);
      this.antlersLeft_1.field_78808_h = (float)Math.toRadians(65.0D);
      this.antlersLeft_2 = new ModelRenderer(this, 4, 0);
      this.antlersLeft_2.field_78809_i = true;
      this.antlersLeft_2.func_78790_a(2.0F, -23.6F, 2.5F, 1, 8, 1, f);
      this.antlersLeft_2.field_78808_h = (float)Math.toRadians(15.0D);
      this.antlersLeft_3 = new ModelRenderer(this, 8, 0);
      this.antlersLeft_3.field_78809_i = true;
      this.antlersLeft_3.func_78790_a(7.0F, -36.0F, 2.5F, 1, 16, 1, f);
      this.antlersLeft_3.field_78808_h = (float)Math.toRadians(15.0D);
      this.antlersLeft_4 = new ModelRenderer(this, 12, 0);
      this.antlersLeft_4.field_78809_i = true;
      this.antlersLeft_4.func_78790_a(-8.5F, -35.0F, 2.5F, 1, 10, 1, f);
      this.antlersLeft_4.field_78808_h = (float)Math.toRadians(50.0D);
      this.head.func_78792_a(this.antlersLeft_1);
      this.head.func_78792_a(this.antlersLeft_2);
      this.head.func_78792_a(this.antlersLeft_3);
      this.head.func_78792_a(this.antlersLeft_4);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      LOTREntityElk elk = (LOTREntityElk)entity;
      this.func_78087_a(f, f1, f2, f3, f4, f5, elk);
      GL11.glPushMatrix();
      float scale = elk.func_110254_bY();
      GL11.glTranslatef(0.0F, 24.0F * (1.0F - scale) * f5, 0.0F);
      GL11.glScalef(scale, scale, scale);
      boolean showAntlers = scale > 0.75F;
      this.antlersRight_1.field_78806_j = showAntlers;
      this.antlersRight_2.field_78806_j = showAntlers;
      this.antlersRight_3.field_78806_j = showAntlers;
      this.antlersRight_4.field_78806_j = showAntlers;
      this.antlersLeft_1.field_78806_j = showAntlers;
      this.antlersLeft_2.field_78806_j = showAntlers;
      this.antlersLeft_3.field_78806_j = showAntlers;
      this.antlersLeft_4.field_78806_j = showAntlers;
      this.body.func_78785_a(f5);
      this.leg1.func_78785_a(f5);
      this.leg2.func_78785_a(f5);
      this.leg3.func_78785_a(f5);
      this.leg4.func_78785_a(f5);
      this.head.func_78785_a(f5);
      if (LOTRMod.isChristmas()) {
         GL11.glColor3f(1.0F, 0.0F, 0.0F);
      }

      this.nose.func_78785_a(f5);
      if (LOTRMod.isChristmas()) {
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
      }

      GL11.glPopMatrix();
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      LOTREntityElk elk = (LOTREntityElk)entity;
      float renderTick = LOTRTickHandlerClient.renderTick;
      float rearAmount = elk.func_110223_p(renderTick);
      float antiRearAmount = 1.0F - rearAmount;
      this.head.field_78797_d = 4.0F;
      this.head.field_78798_e = -10.0F;
      this.head.field_78795_f = (float)Math.toRadians(20.0D);
      this.head.field_78796_g = 0.0F;
      this.head.field_78797_d = rearAmount * -6.0F + antiRearAmount * this.head.field_78797_d;
      this.head.field_78798_e = rearAmount * -1.0F + antiRearAmount * this.head.field_78798_e;
      ModelRenderer var10000 = this.head;
      var10000.field_78795_f = (float)((double)var10000.field_78795_f + Math.toRadians((double)f4));
      var10000 = this.head;
      var10000.field_78796_g = (float)((double)var10000.field_78796_g + Math.toRadians((double)f3));
      this.head.field_78795_f = antiRearAmount * this.head.field_78795_f;
      this.head.field_78796_g = antiRearAmount * this.head.field_78796_g;
      if (f1 > 0.2F) {
         var10000 = this.head;
         var10000.field_78795_f += MathHelper.func_76134_b(f * 0.3F) * 0.1F * f1;
      }

      this.nose.func_78793_a(this.head.field_78800_c, this.head.field_78797_d, this.head.field_78798_e);
      this.nose.field_78795_f = this.head.field_78795_f;
      this.nose.field_78796_g = this.head.field_78796_g;
      this.nose.field_78808_h = this.head.field_78808_h;
      this.body.field_78795_f = 0.0F;
      this.body.field_78795_f = rearAmount * -0.7853982F + antiRearAmount * this.body.field_78795_f;
      float legRotation = MathHelper.func_76134_b(f * 0.4F + 3.1415927F) * f1;
      float f17 = -1.0471976F;
      float f18 = 0.2617994F * rearAmount;
      float f19 = MathHelper.func_76134_b(f2 * 0.4F + 3.1415927F);
      this.leg4.field_78797_d = -2.0F * rearAmount + 4.0F * antiRearAmount;
      this.leg4.field_78798_e = -2.0F * rearAmount + -6.0F * antiRearAmount;
      this.leg3.field_78797_d = this.leg4.field_78797_d;
      this.leg3.field_78798_e = this.leg4.field_78798_e;
      this.leg1.field_78795_f = f18 + legRotation * antiRearAmount;
      this.leg2.field_78795_f = f18 + -legRotation * antiRearAmount;
      this.leg3.field_78795_f = (f17 + -f19) * rearAmount + -legRotation * 0.8F * antiRearAmount;
      this.leg4.field_78795_f = (f17 + f19) * rearAmount + legRotation * 0.8F * antiRearAmount;
   }
}
