package lotr.client.model;

import lotr.client.LOTRTickHandlerClient;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class LOTRModelSwanChestplate extends LOTRModelBiped {
   private ModelRenderer[] wingsRight;
   private ModelRenderer[] wingsLeft;

   public LOTRModelSwanChestplate() {
      this(0.0F);
   }

   public LOTRModelSwanChestplate(float f) {
      super(f);
      this.field_78115_e = new ModelRenderer(this, 0, 0);
      this.field_78115_e.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78115_e.func_78790_a(-4.0F, 0.0F, -2.0F, 8, 12, 4, f);
      int wings = 12;
      this.wingsRight = new ModelRenderer[wings];

      int i;
      ModelRenderer wing;
      for(i = 0; i < this.wingsRight.length; ++i) {
         wing = new ModelRenderer(this, 0, 16);
         wing.func_78793_a(-2.0F, 0.0F, 0.0F);
         wing.func_78790_a(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
         wing.func_78784_a(6, 16).func_78790_a(-2.0F, 1.0F, 0.5F, 2, 10, 0, 0.0F);
         this.wingsRight[i] = wing;
      }

      for(i = 0; i < this.wingsRight.length - 1; ++i) {
         this.wingsRight[i].func_78792_a(this.wingsRight[i + 1]);
      }

      this.wingsRight[0].func_78793_a(-2.0F, 1.0F, 1.0F);
      this.field_78115_e.func_78792_a(this.wingsRight[0]);
      this.wingsLeft = new ModelRenderer[wings];

      for(i = 0; i < this.wingsLeft.length; ++i) {
         wing = new ModelRenderer(this, 0, 16);
         wing.func_78793_a(2.0F, 0.0F, 0.0F);
         wing.field_78809_i = true;
         wing.func_78790_a(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
         wing.func_78784_a(6, 16).func_78790_a(0.0F, 1.0F, 0.5F, 2, 10, 0, 0.0F);
         this.wingsLeft[i] = wing;
      }

      for(i = 0; i < this.wingsLeft.length - 1; ++i) {
         this.wingsLeft[i].func_78792_a(this.wingsLeft[i + 1]);
      }

      this.wingsLeft[0].func_78793_a(2.0F, 1.0F, 1.0F);
      this.field_78115_e.func_78792_a(this.wingsLeft[0]);
      this.field_78112_f = new ModelRenderer(this, 24, 0);
      this.field_78112_f.func_78793_a(-5.0F, 2.0F, 0.0F);
      this.field_78112_f.func_78790_a(-3.0F, -2.0F, -2.0F, 4, 12, 4, f);
      this.field_78113_g = new ModelRenderer(this, 24, 0);
      this.field_78113_g.func_78793_a(5.0F, 2.0F, 0.0F);
      this.field_78113_g.field_78809_i = true;
      this.field_78113_g.func_78790_a(-1.0F, -2.0F, -2.0F, 4, 12, 4, f);
      this.field_78116_c.field_78804_l.clear();
      this.field_78114_d.field_78804_l.clear();
      this.field_78123_h.field_78804_l.clear();
      this.field_78124_i.field_78804_l.clear();
   }

   public void func_78087_a(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.func_78087_a(f, f1, f2, f3, f4, f5, entity);
      float motion = f1;
      float motionPhase = f;
      float wingYaw;
      if (entity != null && entity.field_70154_o instanceof EntityLivingBase) {
         EntityLivingBase mount = (EntityLivingBase)entity.field_70154_o;
         wingYaw = LOTRTickHandlerClient.renderTick;
         motion = mount.field_70722_aY + (mount.field_70721_aZ - mount.field_70722_aY) * wingYaw;
         motionPhase = mount.field_70754_ba - mount.field_70721_aZ * (1.0F - wingYaw);
         motion *= 1.5F;
         motionPhase *= 2.0F;
      }

      float wingAngleBase = (float)Math.toRadians(10.0D);
      wingAngleBase += MathHelper.func_76126_a(f2 * 0.02F) * 0.01F;
      wingAngleBase += MathHelper.func_76126_a(motionPhase * 0.2F) * 0.03F * motion;
      wingYaw = (float)Math.toRadians(50.0D);
      wingYaw += MathHelper.func_76126_a(f2 * 0.03F) * 0.05F;
      wingYaw += MathHelper.func_76126_a(motionPhase * 0.25F) * 0.12F * motion;

      for(int i = 0; i < this.wingsRight.length; ++i) {
         float factor = (float)(i + 1);
         float wingAngle = wingAngleBase / (factor / 3.4F);
         this.wingsRight[i].field_78808_h = wingAngle;
         this.wingsLeft[i].field_78808_h = -wingAngle;
      }

      this.wingsRight[0].field_78796_g = MathHelper.func_76126_a(wingYaw);
      this.wingsRight[0].field_78795_f = MathHelper.func_76134_b(wingYaw);
      this.wingsLeft[0].field_78796_g = MathHelper.func_76126_a(-wingYaw);
      this.wingsLeft[0].field_78795_f = MathHelper.func_76134_b(-wingYaw);
   }
}
