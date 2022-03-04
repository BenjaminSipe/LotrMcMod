package lotr.client.model;

import lotr.client.render.entity.LOTRGlowingEyes;
import lotr.common.entity.npc.LOTREntityWarg;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class LOTRModelWarg extends ModelBase implements LOTRGlowingEyes.Model {
   public ModelRenderer body;
   public ModelRenderer tail;
   public ModelRenderer head;
   public ModelRenderer leg1;
   public ModelRenderer leg2;
   public ModelRenderer leg3;
   public ModelRenderer leg4;

   public LOTRModelWarg() {
      this(0.0F);
   }

   public LOTRModelWarg(float f) {
      this.body = (new ModelRenderer(this, 0, 0)).func_78787_b(128, 64);
      this.body.func_78790_a(-8.0F, -2.0F, -14.0F, 16, 14, 14, f);
      this.body.func_78793_a(0.0F, 2.0F, 1.0F);
      this.body.func_78784_a(0, 28).func_78790_a(-6.5F, 0.0F, 0.0F, 13, 11, 18, f);
      this.tail = (new ModelRenderer(this, 98, 55)).func_78787_b(128, 64);
      this.tail.func_78790_a(-1.0F, -1.0F, 0.0F, 2, 1, 8, f);
      this.tail.func_78793_a(0.0F, 4.0F, 18.0F);
      this.head = (new ModelRenderer(this, 92, 0)).func_78787_b(128, 64);
      this.head.func_78790_a(-5.0F, -5.0F, -8.0F, 10, 10, 8, f);
      this.head.func_78793_a(0.0F, 8.0F, -13.0F);
      this.head.func_78784_a(108, 18).func_78790_a(-3.0F, -1.0F, -12.0F, 6, 5, 4, f);
      this.head.func_78784_a(102, 18).func_78790_a(-4.0F, -7.8F, -3.0F, 2, 3, 1, f);
      this.head.func_78784_a(102, 18).func_78790_a(2.0F, -7.8F, -3.0F, 2, 3, 1, f);
      this.leg1 = (new ModelRenderer(this, 62, 0)).func_78787_b(128, 64);
      this.leg1.field_78809_i = true;
      this.leg1.func_78790_a(-6.0F, -1.0F, -2.5F, 6, 9, 8, f);
      this.leg1.func_78793_a(-4.0F, 6.0F, 12.0F);
      this.leg1.func_78784_a(66, 17).func_78790_a(-5.5F, 8.0F, -1.0F, 5, 10, 5, f);
      this.leg2 = (new ModelRenderer(this, 62, 0)).func_78787_b(128, 64);
      this.leg2.func_78790_a(0.0F, -1.0F, -2.5F, 6, 9, 8, f);
      this.leg2.func_78793_a(4.0F, 6.0F, 12.0F);
      this.leg2.func_78784_a(66, 17).func_78790_a(0.5F, 8.0F, -1.0F, 5, 10, 5, f);
      this.leg3 = (new ModelRenderer(this, 62, 0)).func_78787_b(128, 64);
      this.leg3.field_78809_i = true;
      this.leg3.func_78790_a(-6.0F, -1.0F, -2.5F, 6, 9, 8, f);
      this.leg3.func_78793_a(-6.0F, 5.0F, -8.0F);
      this.leg3.func_78784_a(66, 17).func_78790_a(-5.5F, 8.0F, -1.0F, 5, 11, 5, f);
      this.leg4 = (new ModelRenderer(this, 62, 0)).func_78787_b(128, 64);
      this.leg4.func_78790_a(0.0F, -1.0F, -2.5F, 6, 9, 8, f);
      this.leg4.func_78793_a(6.0F, 5.0F, -8.0F);
      this.leg4.func_78784_a(66, 17).func_78790_a(0.5F, 8.0F, -1.0F, 5, 11, 5, f);
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.body.func_78785_a(f5);
      this.tail.func_78785_a(f5);
      this.head.func_78785_a(f5);
      this.leg1.func_78785_a(f5);
      this.leg2.func_78785_a(f5);
      this.leg3.func_78785_a(f5);
      this.leg4.func_78785_a(f5);
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      this.head.field_78795_f = f4 / 57.295776F;
      this.head.field_78796_g = f3 / 57.295776F;
      this.leg1.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 0.9F * f1;
      this.leg2.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 0.9F * f1;
      this.leg3.field_78795_f = MathHelper.func_76134_b(f * 0.6662F + 3.1415927F) * 0.9F * f1;
      this.leg4.field_78795_f = MathHelper.func_76134_b(f * 0.6662F) * 0.9F * f1;
      this.tail.field_78795_f = ((LOTREntityWarg)entity).getTailRotation();
   }

   public void renderGlowingEyes(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      this.head.func_78785_a(f5);
   }
}
