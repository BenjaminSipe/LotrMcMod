package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.animal.LOTREntitySwan;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelSwan extends ModelBase {
   private ModelRenderer body;
   private ModelRenderer tail;
   private ModelRenderer neck;
   private ModelRenderer head;
   private ModelRenderer wingRight;
   private ModelRenderer wingLeft;
   private ModelRenderer legRight;
   private ModelRenderer legLeft;

   public LOTRModelSwan() {
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.body = new ModelRenderer(this, 0, 0);
      this.body.func_78793_a(0.0F, 18.0F, 0.0F);
      this.body.func_78789_a(-4.0F, -3.0F, -7.0F, 8, 6, 14);
      this.tail = new ModelRenderer(this, 24, 20);
      this.tail.func_78793_a(0.0F, -2.0F, 7.0F);
      this.tail.func_78789_a(-3.0F, -1.5F, -1.0F, 6, 4, 4);
      this.tail.func_78784_a(24, 28).func_78789_a(-2.0F, -1.0F, 3.0F, 4, 2, 3);
      this.body.func_78792_a(this.tail);
      this.neck = new ModelRenderer(this, 44, 11);
      this.neck.func_78793_a(0.0F, 0.0F, -5.5F);
      this.neck.func_78789_a(-1.0F, -11.0F, -3.0F, 2, 13, 2);
      this.body.func_78792_a(this.neck);
      this.head = new ModelRenderer(this, 44, 0);
      this.head.func_78793_a(0.0F, -10.0F, -2.0F);
      this.head.func_78789_a(-1.5F, -2.0F, -4.0F, 3, 3, 4);
      this.head.func_78784_a(44, 7).func_78789_a(-1.0F, -0.5F, -7.0F, 2, 1, 3);
      this.neck.func_78792_a(this.head);
      this.wingRight = new ModelRenderer(this, 0, 20);
      this.wingRight.func_78793_a(-4.0F, 18.0F, -5.0F);
      this.wingRight.func_78789_a(-1.0F, -3.5F, -1.0F, 1, 7, 8);
      this.wingRight.func_78784_a(0, 35).func_78789_a(-1.0F, -4.5F, 7.0F, 1, 6, 3);
      this.wingRight.func_78784_a(8, 35).func_78789_a(-1.0F, -5.5F, 10.0F, 1, 5, 3);
      this.wingLeft = new ModelRenderer(this, 0, 20);
      this.wingLeft.field_78809_i = true;
      this.wingLeft.func_78793_a(4.0F, 18.0F, -5.0F);
      this.wingLeft.func_78789_a(0.0F, -3.5F, -1.0F, 1, 7, 8);
      this.wingLeft.func_78784_a(0, 35).func_78789_a(0.0F, -4.5F, 7.0F, 1, 6, 3);
      this.wingLeft.func_78784_a(8, 35).func_78789_a(0.0F, -5.5F, 10.0F, 1, 5, 3);
      this.legRight = new ModelRenderer(this, 24, 33);
      this.legRight.func_78793_a(-2.0F, 21.0F, 1.0F);
      this.legRight.func_78789_a(-1.5F, 0.0F, -3.0F, 3, 3, 3);
      this.legLeft = new ModelRenderer(this, 24, 33);
      this.legLeft.field_78809_i = true;
      this.legLeft.func_78793_a(2.0F, 21.0F, 1.0F);
      this.legLeft.func_78789_a(-1.5F, 0.0F, -3.0F, 3, 3, 3);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.body.func_78785_a(f5);
      this.wingRight.func_78785_a(f5);
      this.wingLeft.func_78785_a(f5);
      this.legRight.func_78785_a(f5);
      this.legLeft.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      float tick = LOTRTickHandlerClient.renderTick;
      LOTREntitySwan swan = (LOTREntitySwan)entity;
      float f6 = swan.prevFlapPhase + (swan.flapPhase - swan.prevFlapPhase) * tick;
      float f7 = swan.prevFlapPower + (swan.flapPower - swan.prevFlapPower) * tick;
      float flapping = (MathHelper.func_76126_a(f6) + 1.0F) * f7;
      this.neck.field_78795_f = (float)Math.toRadians(-12.0D);
      ModelRenderer var10000 = this.neck;
      var10000.field_78795_f += f4 / 57.295776F * 0.4F;
      var10000 = this.neck;
      var10000.field_78795_f += swan.getPeckAngle(tick) * 1.0F;
      this.neck.field_78796_g = f3 / 57.295776F;
      this.head.field_78795_f = -this.neck.field_78795_f;
      this.tail.field_78795_f = (float)Math.toRadians(20.0D);
      var10000 = this.tail;
      var10000.field_78795_f += MathHelper.func_76134_b(f * 0.4F) * f1 * 0.5F;
      var10000 = this.tail;
      var10000.field_78795_f += MathHelper.func_76134_b(f2 * 0.1F) * 0.1F;
      float wingX = (float)Math.toRadians(10.0D);
      float wingY = (1.0F + MathHelper.func_76134_b(f * 0.4F + 3.1415927F)) * f1 * 0.5F;
      wingY += (1.0F + MathHelper.func_76134_b(f2 * 0.15F)) * 0.1F;
      wingY += flapping * 0.2F;
      float wingZ = MathHelper.func_76134_b(f * 0.4F + 3.1415927F) * f1 * 0.2F;
      wingZ += flapping * 0.5F;
      this.wingRight.field_78795_f = wingX;
      this.wingLeft.field_78795_f = wingX;
      this.wingRight.field_78796_g = -wingY;
      this.wingLeft.field_78796_g = wingY;
      this.wingRight.field_78808_h = wingZ;
      this.wingLeft.field_78808_h = -wingZ;
      this.legRight.field_78795_f = MathHelper.func_76134_b(f * 0.7F + 3.1415927F) * f1 * 1.0F;
      this.legLeft.field_78795_f = MathHelper.func_76134_b(f * 0.7F) * f1 * 1.0F;
   }
}
