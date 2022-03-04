package lotr.client.render.entity.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.client.event.LOTRTickHandlerClient;
import lotr.common.config.LOTRConfig;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class DolAmrothChestplateModel extends SpecialArmorModel implements WearerDependentArmorModel {
   private final float modelSize;
   private int numWings;
   private ModelRenderer[] wingsRight;
   private ModelRenderer[] wingsLeft;

   public DolAmrothChestplateModel(BipedModel referenceBipedModel) {
      this(referenceBipedModel, 1.0F);
   }

   public DolAmrothChestplateModel(BipedModel referenceBipedModel, float f) {
      super(referenceBipedModel, f);
      this.modelSize = f;
      this.clearNonChestplateParts();
      this.numWings = getNumWings();
      this.recreateBodyAndWings();
      this.field_178723_h = new ModelRenderer(this, 24, 0);
      this.field_178723_h.func_78793_a(-5.0F, 2.0F, 0.0F);
      this.field_178723_h.func_228301_a_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, f);
      this.field_178724_i = new ModelRenderer(this, 24, 0);
      this.field_178724_i.func_78793_a(5.0F, 2.0F, 0.0F);
      this.field_178724_i.field_78809_i = true;
      this.field_178724_i.func_228301_a_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, f);
   }

   private static int getNumWings() {
      return (Integer)LOTRConfig.CLIENT.dolAmrothChestplateWings.get();
   }

   private void recreateBodyAndWings() {
      float f = this.modelSize;
      this.field_78115_e = new ModelRenderer(this, 0, 0);
      this.field_78115_e.func_78793_a(0.0F, 0.0F, 0.0F);
      this.field_78115_e.func_228301_a_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, f);
      if (this.numWings > 0) {
         this.wingsRight = new ModelRenderer[this.numWings];

         int i;
         ModelRenderer wing;
         for(i = 0; i < this.wingsRight.length; ++i) {
            wing = new ModelRenderer(this, 0, 16);
            wing.func_78793_a(-2.0F, 0.0F, 0.0F);
            wing.func_228301_a_(-2.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F);
            wing.func_78784_a(6, 16).func_228301_a_(-2.0F, 1.0F, 0.5F, 2.0F, 10.0F, 0.0F, 0.0F);
            this.wingsRight[i] = wing;
         }

         for(i = 0; i < this.wingsRight.length - 1; ++i) {
            this.wingsRight[i].func_78792_a(this.wingsRight[i + 1]);
         }

         this.wingsRight[0].func_78793_a(-2.0F, 1.0F, 1.0F);
         this.field_78115_e.func_78792_a(this.wingsRight[0]);
         this.wingsLeft = new ModelRenderer[this.numWings];

         for(i = 0; i < this.wingsLeft.length; ++i) {
            wing = new ModelRenderer(this, 0, 16);
            wing.func_78793_a(2.0F, 0.0F, 0.0F);
            wing.field_78809_i = true;
            wing.func_228301_a_(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F);
            wing.func_78784_a(6, 16).func_228301_a_(0.0F, 1.0F, 0.5F, 2.0F, 10.0F, 0.0F, 0.0F);
            this.wingsLeft[i] = wing;
         }

         for(i = 0; i < this.wingsLeft.length - 1; ++i) {
            this.wingsLeft[i].func_78792_a(this.wingsLeft[i + 1]);
         }

         this.wingsLeft[0].func_78793_a(2.0F, 1.0F, 1.0F);
         this.field_78115_e.func_78792_a(this.wingsLeft[0]);
      } else {
         this.wingsRight = new ModelRenderer[0];
         this.wingsLeft = new ModelRenderer[0];
      }

   }

   public void func_225598_a_(MatrixStack matStack, IVertexBuilder buf, int packedLight, int packedOverlay, float r, float g, float b, float a) {
      int currentConfigNumWings = getNumWings();
      if (this.numWings != currentConfigNumWings) {
         this.numWings = currentConfigNumWings;
         this.recreateBodyAndWings();
      }

      super.func_225598_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
   }

   public void acceptWearingEntity(LivingEntity entity) {
      float partialTicks = LOTRTickHandlerClient.renderPartialTick;
      float limbSwing = 0.0F;
      float limbSwingAmount = 0.0F;
      boolean shouldSit = entity.func_184218_aH() && entity.func_184187_bx() != null && entity.func_184187_bx().shouldRiderSit();
      if (!shouldSit && entity.func_70089_S()) {
         limbSwingAmount = MathHelper.func_219799_g(partialTicks, entity.field_184618_aE, entity.field_70721_aZ);
         limbSwing = entity.field_184619_aG - entity.field_70721_aZ * (1.0F - partialTicks);
         if (entity.func_70631_g_()) {
            limbSwing *= 3.0F;
         }

         limbSwingAmount = Math.min(limbSwingAmount, 1.0F);
      }

      float ageInTicks = (float)entity.field_70173_aa + partialTicks;
      float netHeadYaw = 0.0F;
      float headPitch = 0.0F;
      this.func_225597_a_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   public void func_225597_a_(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.func_225597_a_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      if (this.numWings > 0) {
         float motion = limbSwingAmount;
         float motionPhase = limbSwing;
         float wingYaw;
         if (entity != null && entity.func_184187_bx() instanceof LivingEntity) {
            LivingEntity mount = (LivingEntity)entity.func_184187_bx();
            wingYaw = LOTRTickHandlerClient.renderPartialTick;
            motion = mount.field_184618_aE + (mount.field_70721_aZ - mount.field_184618_aE) * wingYaw;
            motionPhase = mount.field_184619_aG - mount.field_70721_aZ * (1.0F - wingYaw);
            motion *= 1.5F;
            motionPhase *= 2.0F;
         }

         float wingAngleBase = (float)Math.toRadians(10.0D);
         wingAngleBase += MathHelper.func_76126_a(ageInTicks * 0.02F) * 0.01F;
         wingAngleBase += MathHelper.func_76126_a(motionPhase * 0.2F) * 0.03F * motion;
         wingYaw = (float)Math.toRadians(50.0D);
         wingYaw += MathHelper.func_76126_a(ageInTicks * 0.03F) * 0.05F;
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
}
