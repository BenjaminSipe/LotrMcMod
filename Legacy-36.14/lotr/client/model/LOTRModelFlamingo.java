package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.animal.LOTREntityFlamingo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelFlamingo extends ModelBase {
   private ModelRenderer head = new ModelRenderer(this, 8, 24);
   private ModelRenderer body;
   private ModelRenderer tail;
   private ModelRenderer wingLeft;
   private ModelRenderer wingRight;
   private ModelRenderer legLeft;
   private ModelRenderer legRight;
   private ModelRenderer head_child;
   private ModelRenderer body_child;
   private ModelRenderer tail_child;
   private ModelRenderer wingLeft_child;
   private ModelRenderer wingRight_child;
   private ModelRenderer legLeft_child;
   private ModelRenderer legRight_child;

   public LOTRModelFlamingo() {
      this.head.func_78789_a(-2.0F, -17.0F, -2.0F, 4, 4, 4);
      this.head.func_78793_a(0.0F, 5.0F, -2.0F);
      this.head.func_78784_a(24, 27).func_78789_a(-1.5F, -16.0F, -5.0F, 3, 2, 3);
      this.head.func_78784_a(36, 30).func_78789_a(-1.0F, -14.0F, -5.0F, 2, 1, 1);
      this.head.func_78784_a(0, 16).func_78789_a(-1.0F, -15.0F, -1.0F, 2, 14, 2);
      this.body = new ModelRenderer(this, 0, 0);
      this.body.func_78789_a(-3.0F, 0.0F, -4.0F, 6, 7, 8);
      this.body.func_78793_a(0.0F, 3.0F, 0.0F);
      this.tail = new ModelRenderer(this, 42, 23);
      this.tail.func_78789_a(-2.5F, 0.0F, 0.0F, 5, 3, 6);
      this.tail.func_78793_a(0.0F, 4.0F, 3.0F);
      this.wingLeft = new ModelRenderer(this, 36, 0);
      this.wingLeft.func_78789_a(-1.0F, 0.0F, -3.0F, 1, 8, 6);
      this.wingLeft.func_78793_a(-3.0F, 3.0F, 0.0F);
      this.wingRight = new ModelRenderer(this, 50, 0);
      this.wingRight.func_78789_a(0.0F, 0.0F, -3.0F, 1, 8, 6);
      this.wingRight.func_78793_a(3.0F, 3.0F, 0.0F);
      this.legLeft = new ModelRenderer(this, 30, 0);
      this.legLeft.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 16, 1);
      this.legLeft.func_78793_a(-2.0F, 8.0F, 0.0F);
      this.legLeft.func_78784_a(30, 17).func_78789_a(-1.5F, 14.9F, -3.5F, 3, 1, 3);
      this.legRight = new ModelRenderer(this, 30, 0);
      this.legRight.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 16, 1);
      this.legRight.func_78793_a(2.0F, 8.0F, 0.0F);
      this.legRight.func_78784_a(30, 17).func_78789_a(-1.5F, 14.9F, -3.5F, 3, 1, 3);
      this.head_child = new ModelRenderer(this, 0, 24);
      this.head_child.func_78789_a(-2.0F, -4.0F, -4.0F, 4, 4, 4);
      this.head_child.func_78793_a(0.0F, 15.0F, -3.0F);
      this.head_child.func_78784_a(16, 28).func_78789_a(-1.0F, -2.0F, -6.0F, 2, 2, 2);
      this.body_child = new ModelRenderer(this, 0, 0);
      this.body_child.func_78789_a(-3.0F, 0.0F, -4.0F, 6, 5, 7);
      this.body_child.func_78793_a(0.0F, 14.0F, 0.0F);
      this.tail_child = new ModelRenderer(this, 0, 14);
      this.tail_child.func_78789_a(-2.0F, 0.0F, 0.0F, 4, 2, 3);
      this.tail_child.func_78793_a(0.0F, 14.5F, 3.0F);
      this.wingLeft_child = new ModelRenderer(this, 40, 0);
      this.wingLeft_child.func_78789_a(-1.0F, 0.0F, -3.0F, 1, 4, 5);
      this.wingLeft_child.func_78793_a(-3.0F, 14.0F, 0.0F);
      this.wingRight_child = new ModelRenderer(this, 52, 0);
      this.wingRight_child.func_78789_a(0.0F, 0.0F, -3.0F, 1, 4, 5);
      this.wingRight_child.func_78793_a(3.0F, 14.0F, 0.0F);
      this.legLeft_child = new ModelRenderer(this, 27, 0);
      this.legLeft_child.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 5, 1);
      this.legLeft_child.func_78793_a(-2.0F, 19.0F, 0.0F);
      this.legLeft_child.func_78784_a(27, 7).func_78789_a(-1.5F, 3.9F, -3.5F, 3, 1, 3);
      this.legRight_child = new ModelRenderer(this, 27, 0);
      this.legRight_child.func_78789_a(-0.5F, 0.0F, -0.5F, 1, 5, 1);
      this.legRight_child.func_78793_a(2.0F, 19.0F, 0.0F);
      this.legRight_child.func_78784_a(27, 7).func_78789_a(-1.5F, 3.9F, -3.5F, 3, 1, 3);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      if (this.field_78091_s) {
         this.head_child.func_78785_a(f5);
         this.body_child.func_78785_a(f5);
         this.tail_child.func_78785_a(f5);
         this.wingLeft_child.func_78785_a(f5);
         this.wingRight_child.func_78785_a(f5);
         this.legLeft_child.func_78785_a(f5);
         this.legRight_child.func_78785_a(f5);
      } else {
         this.head.func_78785_a(f5);
         this.body.func_78785_a(f5);
         this.tail.func_78785_a(f5);
         this.wingLeft.func_78785_a(f5);
         this.wingRight.func_78785_a(f5);
         this.legLeft.func_78785_a(f5);
         this.legRight.func_78785_a(f5);
      }

   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      LOTREntityFlamingo flamingo = (LOTREntityFlamingo)entity;
      if (this.field_78091_s) {
         this.head_child.field_78795_f = f4 / 57.29578F;
         this.head_child.field_78796_g = f3 / 57.29578F;
         this.legLeft_child.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 0.9F * f1;
         this.legRight_child.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 0.9F * f1;
         this.wingLeft_child.field_78808_h = f2 * 0.4F;
         this.wingRight_child.field_78808_h = -f2 * 0.4F;
         this.tail_child.field_78795_f = -0.25F;
      } else {
         this.head.field_78795_f = f4 / 57.29578F;
         this.head.field_78796_g = f3 / 57.29578F;
         this.legLeft.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 0.9F * f1;
         this.legRight.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 0.9F * f1;
         this.wingLeft.field_78808_h = f2 * 0.4F;
         this.wingRight.field_78808_h = -f2 * 0.4F;
         this.tail.field_78795_f = -0.25F;
         int cur = flamingo.getFishingTickCur();
         int pre = flamingo.getFishingTickPre();
         float fishing = (float)pre + (float)(cur - pre) * LOTRTickHandlerClient.renderTick;
         if (cur > 160 + 20) {
            this.head.field_78795_f = 3.1415927F * (200.0F - fishing) / 20.0F;
         } else if (cur > 20) {
            this.head.field_78795_f = 3.1415927F;
         } else if (cur > 0) {
            this.head.field_78795_f = 3.1415927F * fishing / 20.0F;
         }
      }

   }
}
