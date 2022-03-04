package lotr.client.render.entity.model.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class RohanMarshalHelmetModel extends SpecialArmorModel {
   private ModelRenderer[] manes;

   public RohanMarshalHelmetModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public RohanMarshalHelmetModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.clearNonHelmetParts();
      this.field_78116_c = new ModelRenderer(this, 0, 0);
      this.field_78116_c.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78116_c.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, f);
      this.field_78116_c.func_78784_a(0, 16).func_228301_a_(-1.0F, -11.5F - f, -4.5F - f, 2.0F, 7.0F, 6.0F, 0.0F);
      this.manes = new ModelRenderer[3];

      for(int i = 0; i < this.manes.length; ++i) {
         ModelRenderer mane = new ModelRenderer(this, 32, 0);
         this.manes[i] = mane;
         mane.func_78793_a(0.0F, -f, f);
         mane.func_228301_a_(0.0F, -11.0F, -1.0F, 0.0F, 14.0F, 12.0F, 0.0F);
         this.field_78116_c.func_78792_a(mane);
      }

   }

   public void func_225597_a_(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.func_225597_a_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float mid = (float)this.manes.length / 2.0F - 0.5F;

      for(int i = 0; i < this.manes.length; ++i) {
         ModelRenderer mane = this.manes[i];
         mane.field_78795_f = (mid - Math.abs((float)i - mid)) / mid * 0.22F;
         mane.field_78796_g = ((float)i - mid) / mid * 0.17F;
         mane.field_78795_f += MathHelper.func_76126_a(limbSwing * 0.4F) * limbSwingAmount * 0.2F;
      }

   }
}
