package lotr.client.model;

import lotr.common.entity.npc.LOTREntityHalfTroll;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class LOTRModelHalfTroll extends LOTRModelBiped {
   private ModelRenderer mohawk;
   private ModelRenderer hornRight1;
   private ModelRenderer hornRight2;
   private ModelRenderer hornLeft1;
   private ModelRenderer hornLeft2;

   public LOTRModelHalfTroll() {
      this(0.0F);
   }

   public LOTRModelHalfTroll(float f) {
      super(f);
      this.field_78090_t = 64;
      this.field_78089_u = 64;
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, -8.0F, 0.0F);
      this.field_78116_c.func_78790_a(-5.0F, -10.0F, -5.0F, 10, 10, 10, f);
      this.field_78116_c.func_78784_a(40, 5).func_78790_a(-4.0F, -3.0F, -7.0F, 8, 3, 2, f);
      ModelRenderer nose = new ModelRenderer(this, 30, 0);
      nose.func_78790_a(-1.0F, -4.5F, -8.0F, 2, 3, 3, f);
      nose.field_78795_f = (float)Math.toRadians(-20.0D);
      this.field_78116_c.func_78792_a(nose);
      ModelRenderer teeth = new ModelRenderer(this, 60, 7);
      teeth.func_78790_a(-3.5F, -7.5F, -5.0F, 1, 2, 1, f);
      teeth.field_78809_i = true;
      teeth.func_78790_a(2.5F, -7.5F, -5.0F, 1, 2, 1, f);
      teeth.field_78795_f = (float)Math.toRadians(30.0D);
      this.field_78116_c.func_78792_a(teeth);
      ModelRenderer earRight = new ModelRenderer(this, 0, 0);
      earRight.func_78790_a(-5.0F, -6.0F, -2.0F, 1, 3, 3, f);
      earRight.field_78796_g = (float)Math.toRadians(-35.0D);
      this.field_78116_c.func_78792_a(earRight);
      ModelRenderer earLeft = new ModelRenderer(this, 0, 0);
      earLeft.field_78809_i = true;
      earLeft.func_78790_a(4.0F, -6.0F, -2.0F, 1, 3, 3, f);
      earLeft.field_78796_g = (float)Math.toRadians(35.0D);
      this.field_78116_c.func_78792_a(earLeft);
      this.mohawk = new ModelRenderer(this, 40, 10);
      this.mohawk.func_78790_a(-1.0F, -12.5F, -1.5F, 2, 10, 8, f);
      this.field_78116_c.func_78792_a(this.mohawk);
      this.hornRight1 = new ModelRenderer(this, 40, 0);
      this.hornRight1.func_78790_a(-10.0F, -8.0F, 1.0F, 3, 2, 2, f);
      this.hornRight1.field_78808_h = (float)Math.toRadians(20.0D);
      this.field_78116_c.func_78792_a(this.hornRight1);
      this.hornRight2 = new ModelRenderer(this, 50, 2);
      this.hornRight2.func_78790_a(-14.5F, -4.0F, 1.5F, 3, 1, 1, f);
      this.hornRight2.field_78808_h = (float)Math.toRadians(40.0D);
      this.field_78116_c.func_78792_a(this.hornRight2);
      this.hornLeft1 = new ModelRenderer(this, 40, 0);
      this.hornLeft1.field_78809_i = true;
      this.hornLeft1.func_78790_a(7.0F, -8.0F, 1.0F, 3, 2, 2, f);
      this.hornLeft1.field_78808_h = (float)Math.toRadians(-20.0D);
      this.field_78116_c.func_78792_a(this.hornLeft1);
      this.hornLeft2 = new ModelRenderer(this, 50, 2);
      this.hornLeft2.field_78809_i = true;
      this.hornLeft2.func_78790_a(11.5F, -4.0F, 1.5F, 3, 1, 1, f);
      this.hornLeft2.field_78808_h = (float)Math.toRadians(-40.0D);
      this.field_78116_c.func_78792_a(this.hornLeft2);
      this.field_78115_e = new ModelRenderer(this, 0, 20);
      this.field_78115_e.func_78793_a(0.0F, -8.0F, 0.0F);
      this.field_78115_e.func_78790_a(-6.0F, 0.0F, -4.0F, 12, 16, 8, f);
      this.field_78112_f = new ModelRenderer(this, 20, 50);
      this.field_78112_f.func_78793_a(-8.5F, -6.0F, 0.0F);
      this.field_78112_f.func_78790_a(-3.5F, -2.0F, -3.0F, 6, 8, 6, f);
      this.field_78112_f.func_78784_a(0, 49).func_78790_a(-3.0F, 6.0F, -2.5F, 5, 10, 5, f);
      this.field_78113_g = new ModelRenderer(this, 20, 50);
      this.field_78113_g.func_78793_a(8.5F, -6.0F, 0.0F);
      this.field_78113_g.field_78809_i = true;
      this.field_78113_g.func_78790_a(-2.5F, -2.0F, -3.0F, 6, 8, 6, f);
      this.field_78113_g.func_78784_a(0, 49).func_78790_a(-2.0F, 6.0F, -2.5F, 5, 10, 5, f);
      this.field_78123_h = new ModelRenderer(this, 40, 28);
      this.field_78123_h.func_78793_a(-3.2F, 8.0F, 0.0F);
      this.field_78123_h.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 16, 6, f);
      this.field_78124_i = new ModelRenderer(this, 40, 28);
      this.field_78124_i.func_78793_a(3.2F, 8.0F, 0.0F);
      this.field_78124_i.field_78809_i = true;
      this.field_78124_i.func_78790_a(-3.0F, 0.0F, -3.0F, 6, 16, 6, f);
      this.field_78114_d.field_78807_k = true;
   }

   public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      LOTREntityHalfTroll halfTroll = (LOTREntityHalfTroll)entity;
      this.mohawk.field_78806_j = halfTroll.hasMohawk();
      this.hornRight1.field_78806_j = this.hornLeft1.field_78806_j = halfTroll.hasHorns();
      this.hornRight2.field_78806_j = this.hornLeft2.field_78806_j = halfTroll.hasFullHorns();
      super.func_78088_a(entity, f, f1, f2, f3, f4, f5);
   }
}
