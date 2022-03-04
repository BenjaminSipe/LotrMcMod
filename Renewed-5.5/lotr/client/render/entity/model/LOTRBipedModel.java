package lotr.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.function.Consumer;
import java.util.function.Function;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.ai.NPCTalkAnimations;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class LOTRBipedModel extends BipedModel {
   protected static final int STANDARD_BIPED_SKIN_WIDTH = 128;
   protected static final int STANDARD_BIPED_SKIN_HEIGHT = 64;
   protected static final int STANDARD_BIPED_ARMOR_WIDTH = 64;
   protected static final int STANDARD_BIPED_ARMOR_HEIGHT = 32;
   private final boolean isChildHeadScaled;
   private final float childHeadOffsetY;
   private final float childHeadOffsetZ;
   private final float childHeadScale;
   private final float childBodyScale;
   private final float childBodyOffsetY;
   protected ModelRenderer bipedChest;
   protected final boolean isArmor;
   public boolean showChest;
   public boolean isEating;
   private float talkingHeadYawRadians;
   private float talkingHeadPitchRadians;
   private float talkingGestureMainhand;
   private float talkingGestureOffhand;
   protected final ModelRenderer bipedLeftArmwear;
   protected final ModelRenderer bipedRightArmwear;
   protected final ModelRenderer bipedLeftLegwear;
   protected final ModelRenderer bipedRightLegwear;
   protected final ModelRenderer bipedBodywear;
   protected final boolean smallArms;

   public LOTRBipedModel(float modelSize, float yOff, boolean isArmor, boolean smallArms) {
      this(modelSize, yOff, isArmor ? 64 : 128, isArmor ? 32 : 64, isArmor, smallArms);
   }

   public LOTRBipedModel(float modelSize, float yOff, int texW, int texH, boolean isArmor, boolean smallArms) {
      this(RenderType::func_228640_c_, modelSize, yOff, texW, texH, isArmor, smallArms);
   }

   public LOTRBipedModel(Function renderType, float modelSize, float yOff, int texW, int texH, boolean isArmor, boolean smallArms) {
      super(renderType, modelSize, yOff, texW, texH);
      this.isChildHeadScaled = true;
      this.childHeadOffsetY = 16.0F;
      this.childHeadOffsetZ = 0.0F;
      this.childHeadScale = 2.0F;
      this.childBodyScale = 2.0F;
      this.childBodyOffsetY = 24.0F;
      this.showChest = false;
      this.isEating = false;
      this.talkingHeadYawRadians = 0.0F;
      this.talkingHeadPitchRadians = 0.0F;
      this.talkingGestureMainhand = 0.0F;
      this.talkingGestureOffhand = 0.0F;
      this.isArmor = isArmor;
      this.smallArms = smallArms;
      this.bipedChest = new ModelRenderer(this, 32, 8);
      if (!isArmor) {
         this.bipedChest.func_228301_a_(-3.0F, 2.0F, -4.0F, 6.0F, 3.0F, 2.0F, modelSize);
         this.bipedChest.func_78793_a(0.0F, 0.0F, 0.0F);
         this.field_78115_e.func_78792_a(this.bipedChest);
      }

      if (!isArmor) {
         if (smallArms) {
            this.field_178724_i = new ModelRenderer(this, 32, 48);
            this.field_178724_i.func_228301_a_(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize);
            this.field_178724_i.func_78793_a(5.0F, 2.5F, 0.0F);
            this.field_178723_h = new ModelRenderer(this, 40, 16);
            this.field_178723_h.func_228301_a_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize);
            this.field_178723_h.func_78793_a(-5.0F, 2.5F, 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.func_228301_a_(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize + 0.25F);
            this.bipedLeftArmwear.func_78793_a(5.0F, 2.5F, 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.func_228301_a_(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelSize + 0.25F);
            this.bipedRightArmwear.func_78793_a(-5.0F, 2.5F, 10.0F);
         } else {
            this.field_178724_i = new ModelRenderer(this, 32, 48);
            this.field_178724_i.func_228301_a_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
            this.field_178724_i.func_78793_a(5.0F, 2.0F, 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.func_228301_a_(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
            this.bipedLeftArmwear.func_78793_a(5.0F, 2.0F, 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.func_228301_a_(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
            this.bipedRightArmwear.func_78793_a(-5.0F, 2.0F, 10.0F);
         }

         this.field_178722_k = new ModelRenderer(this, 16, 48);
         this.field_178722_k.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize);
         this.field_178722_k.func_78793_a(1.9F, 12.0F, 0.0F);
         this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
         this.bipedLeftLegwear.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
         this.bipedLeftLegwear.func_78793_a(1.9F, 12.0F, 0.0F);
         this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
         this.bipedRightLegwear.func_228301_a_(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelSize + 0.25F);
         this.bipedRightLegwear.func_78793_a(-1.9F, 12.0F, 0.0F);
         this.bipedBodywear = new ModelRenderer(this, 16, 32);
         this.bipedBodywear.func_228301_a_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelSize + 0.25F);
         this.bipedBodywear.func_78793_a(0.0F, 0.0F, 0.0F);
      } else {
         this.bipedLeftArmwear = this.bipedRightArmwear = this.bipedLeftLegwear = this.bipedRightLegwear = this.bipedBodywear = new ModelRenderer(this, 0, 0);
      }

   }

   protected void createLongHairModel(float headRotationY, float f) {
      this.field_178720_f = new ModelRenderer(this, 56, 0);
      this.field_178720_f.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 24.0F, 8.0F, 0.5F + f);
      this.field_178720_f.func_78793_a(0.0F, headRotationY, 0.0F);
   }

   protected Iterable func_225600_b_() {
      Iterable superBodyParts = super.func_225600_b_();
      return this.isArmor ? superBodyParts : Iterables.concat(superBodyParts, ImmutableList.of(this.bipedLeftLegwear, this.bipedRightLegwear, this.bipedLeftArmwear, this.bipedRightArmwear, this.bipedBodywear));
   }

   public void func_225598_a_(MatrixStack matStack, IVertexBuilder buf, int packedLight, int packedOverlay, float r, float g, float b, float a) {
      matStack.func_227860_a_();
      this.preRenderAllCallback(matStack);
      matStack.func_227860_a_();
      this.preHeadCallback(matStack);
      float scale;
      if (this.field_217114_e) {
         scale = 0.75F;
         matStack.func_227862_a_(scale, scale, scale);
         matStack.func_227861_a_(0.0D, 1.0D, 0.0D);
         this.postChildHeadCallback(matStack);
      }

      this.field_78116_c.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      this.field_178720_f.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      matStack.func_227865_b_();
      matStack.func_227860_a_();
      if (this.field_217114_e) {
         scale = 0.5F;
         matStack.func_227862_a_(scale, scale, scale);
         matStack.func_227861_a_(0.0D, 1.5D, 0.0D);
      }

      matStack.func_227860_a_();
      this.preBodyCallback(matStack);
      this.field_78115_e.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      this.bipedBodywear.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      matStack.func_227865_b_();
      matStack.func_227860_a_();
      this.preRightArmCallback(matStack);
      this.field_178723_h.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      this.bipedRightArmwear.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      matStack.func_227865_b_();
      matStack.func_227860_a_();
      this.preLeftArmCallback(matStack);
      this.field_178724_i.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      this.bipedLeftArmwear.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      matStack.func_227865_b_();
      matStack.func_227860_a_();
      this.preRightLegCallback(matStack);
      this.field_178721_j.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      this.bipedRightLegwear.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      matStack.func_227865_b_();
      matStack.func_227860_a_();
      this.preLeftLegCallback(matStack);
      this.field_178722_k.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      this.bipedLeftLegwear.func_228309_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      matStack.func_227865_b_();
      matStack.func_227865_b_();
      matStack.func_227865_b_();
   }

   public void preRenderAllCallback(MatrixStack matStack) {
   }

   public void preHeadCallback(MatrixStack matStack) {
   }

   public void postChildHeadCallback(MatrixStack matStack) {
   }

   public void preBodyCallback(MatrixStack matStack) {
   }

   public void preRightArmCallback(MatrixStack matStack) {
   }

   public void preLeftArmCallback(MatrixStack matStack) {
   }

   public void preRightLegCallback(MatrixStack matStack) {
   }

   public void preLeftLegCallback(MatrixStack matStack) {
   }

   public void setTalkAnimation(NPCTalkAnimations talkAnims, float f) {
      this.talkingHeadYawRadians = talkAnims.getHeadYawRadians(f);
      this.talkingHeadPitchRadians = talkAnims.getHeadPitchRadians(f);
      this.talkingGestureMainhand = talkAnims.getMainhandGestureAmount(f);
      this.talkingGestureOffhand = talkAnims.getOffhandGestureAmount(f);
   }

   public void func_217111_a(EntityModel other) {
      super.func_217111_a(other);
      if (other instanceof BipedModel) {
         BipedModel otherBiped = (BipedModel)other;
         otherBiped.field_187075_l = this.field_187075_l;
         otherBiped.field_187076_m = this.field_187076_m;
         otherBiped.field_228270_o_ = this.field_228270_o_;
      }

      if (other instanceof LOTRBipedModel) {
         LOTRBipedModel otherBiped = (LOTRBipedModel)other;
         otherBiped.showChest = this.showChest;
         otherBiped.isEating = this.isEating;
         otherBiped.talkingHeadYawRadians = this.talkingHeadYawRadians;
         otherBiped.talkingHeadPitchRadians = this.talkingHeadPitchRadians;
         otherBiped.talkingGestureMainhand = this.talkingGestureMainhand;
         otherBiped.talkingGestureOffhand = this.talkingGestureOffhand;
      }

   }

   public void func_225597_a_(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.func_225597_a_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      this.bipedChest.field_78806_j = this.showChest;
      if (this.isEating) {
         this.onArm(entity, Hand.MAIN_HAND, (arm) -> {
            arm.field_78795_f = Math.min(arm.field_78795_f, (float)Math.toRadians(-60.0D));
         });
      }

      ModelRenderer var10000 = this.field_78116_c;
      var10000.field_78796_g += this.talkingHeadYawRadians;
      var10000 = this.field_78116_c;
      var10000.field_78795_f += this.talkingHeadPitchRadians;
      var10000 = this.field_178720_f;
      var10000.field_78796_g += this.talkingHeadYawRadians;
      var10000 = this.field_178720_f;
      var10000.field_78795_f += this.talkingHeadPitchRadians;
      float rightGestureAmount = entity.func_184591_cq() == HandSide.RIGHT ? this.talkingGestureMainhand : this.talkingGestureOffhand;
      float leftGestureAmount = entity.func_184591_cq() == HandSide.RIGHT ? this.talkingGestureOffhand : this.talkingGestureMainhand;
      var10000 = this.field_178723_h;
      var10000.field_78795_f = (float)((double)var10000.field_78795_f + (double)rightGestureAmount * Math.toRadians(-45.0D));
      var10000 = this.field_178723_h;
      var10000.field_78808_h = (float)((double)var10000.field_78808_h + (double)rightGestureAmount * Math.toRadians(20.0D));
      var10000 = this.field_178724_i;
      var10000.field_78795_f = (float)((double)var10000.field_78795_f + (double)leftGestureAmount * Math.toRadians(-45.0D));
      var10000 = this.field_178724_i;
      var10000.field_78808_h = (float)((double)var10000.field_78808_h + (double)leftGestureAmount * Math.toRadians(-20.0D));
      if (entity instanceof NPCEntity && !this.field_217113_d) {
         this.field_178721_j.field_78796_g = (float)Math.toRadians(5.0D);
         this.field_178722_k.field_78796_g = (float)Math.toRadians(-5.0D);
      }

      if (entity instanceof NPCEntity && ((NPCEntity)entity).isDrunk()) {
         float f6 = ageInTicks / 80.0F;
         float f7 = (ageInTicks + 40.0F) / 80.0F;
         f6 *= 6.2831855F;
         f7 *= 6.2831855F;
         float f8 = MathHelper.func_76126_a(f6) * 0.5F;
         float f9 = MathHelper.func_76126_a(f7) * 0.5F;
         var10000 = this.field_78116_c;
         var10000.field_78795_f += f8;
         var10000 = this.field_78116_c;
         var10000.field_78796_g += f9;
         var10000 = this.field_178720_f;
         var10000.field_78795_f += f8;
         var10000 = this.field_178720_f;
         var10000.field_78796_g += f9;
         Hand[] var13 = Hand.values();
         int var14 = var13.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            Hand hand = var13[var15];
            if (!entity.func_184586_b(hand).func_190926_b()) {
               this.onArm(entity, hand, (arm) -> {
                  arm.field_78795_f = (float)Math.toRadians(-60.0D);
               });
            }
         }
      }

      this.bipedLeftLegwear.func_217177_a(this.field_178722_k);
      this.bipedRightLegwear.func_217177_a(this.field_178721_j);
      this.bipedLeftArmwear.func_217177_a(this.field_178724_i);
      this.bipedRightArmwear.func_217177_a(this.field_178723_h);
      this.bipedBodywear.func_217177_a(this.field_78115_e);
   }

   private void onArm(LivingEntity entity, Hand handType, Consumer action) {
      HandSide mainHandSide = entity.func_184591_cq();
      if (mainHandSide == HandSide.RIGHT) {
         action.accept(handType == Hand.MAIN_HAND ? this.field_178723_h : this.field_178724_i);
      } else {
         action.accept(handType == Hand.MAIN_HAND ? this.field_178724_i : this.field_178723_h);
      }

   }

   public void func_178719_a(boolean visible) {
      super.func_178719_a(visible);
      this.bipedLeftArmwear.field_78806_j = visible;
      this.bipedRightArmwear.field_78806_j = visible;
      this.bipedLeftLegwear.field_78806_j = visible;
      this.bipedRightLegwear.field_78806_j = visible;
      this.bipedBodywear.field_78806_j = visible;
   }

   public void func_225599_a_(HandSide side, MatrixStack matStack) {
      ModelRenderer arm = this.func_187074_a(side);
      if (this.smallArms) {
         float f = 0.5F * (float)(side == HandSide.RIGHT ? 1 : -1);
         arm.field_78800_c += f;
         arm.func_228307_a_(matStack);
         arm.field_78800_c -= f;
      } else {
         arm.func_228307_a_(matStack);
      }

   }
}
