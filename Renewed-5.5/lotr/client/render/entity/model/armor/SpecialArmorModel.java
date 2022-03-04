package lotr.client.render.entity.model.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Arrays;
import java.util.List;
import lotr.client.render.entity.model.LOTRBipedModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.vector.Vector3f;

public abstract class SpecialArmorModel extends LOTRBipedModel {
   private final BipedModel referenceBipedModel;
   private boolean savedDefaultAngles;
   private Vector3f defaultHeadRotationPoint;
   private Vector3f defaultRightLegRotationPoint;
   private Vector3f defaultLeftLegRotationPoint;

   public SpecialArmorModel(BipedModel referenceBipedModel, float modelSize) {
      this(referenceBipedModel, modelSize, 0.0F, 64, 32);
   }

   protected SpecialArmorModel(BipedModel referenceBipedModel, float modelSize, float yOffset, int texWidth, int texHeight) {
      super(modelSize, yOffset, texWidth, texHeight, true, false);
      this.referenceBipedModel = referenceBipedModel;
   }

   public void preRenderAllCallback(MatrixStack matStack) {
      if (this.referenceBipedModel instanceof LOTRBipedModel) {
         ((LOTRBipedModel)this.referenceBipedModel).preRenderAllCallback(matStack);
      }

   }

   public void preHeadCallback(MatrixStack matStack) {
      if (this.referenceBipedModel instanceof LOTRBipedModel) {
         ((LOTRBipedModel)this.referenceBipedModel).preHeadCallback(matStack);
      }

   }

   public void postChildHeadCallback(MatrixStack matStack) {
      if (this.referenceBipedModel instanceof LOTRBipedModel) {
         ((LOTRBipedModel)this.referenceBipedModel).postChildHeadCallback(matStack);
      }

   }

   public void preBodyCallback(MatrixStack matStack) {
      if (this.referenceBipedModel instanceof LOTRBipedModel) {
         ((LOTRBipedModel)this.referenceBipedModel).preBodyCallback(matStack);
      }

   }

   public void preRightArmCallback(MatrixStack matStack) {
      if (this.referenceBipedModel instanceof LOTRBipedModel) {
         ((LOTRBipedModel)this.referenceBipedModel).preRightArmCallback(matStack);
      }

   }

   public void preLeftArmCallback(MatrixStack matStack) {
      if (this.referenceBipedModel instanceof LOTRBipedModel) {
         ((LOTRBipedModel)this.referenceBipedModel).preLeftArmCallback(matStack);
      }

   }

   public void preRightLegCallback(MatrixStack matStack) {
      if (this.referenceBipedModel instanceof LOTRBipedModel) {
         ((LOTRBipedModel)this.referenceBipedModel).preRightLegCallback(matStack);
      }

   }

   public void preLeftLegCallback(MatrixStack matStack) {
      if (this.referenceBipedModel instanceof LOTRBipedModel) {
         ((LOTRBipedModel)this.referenceBipedModel).preLeftLegCallback(matStack);
      }

   }

   protected final void clearNonHelmetParts() {
      this.clearArmorPartsExcept(this.field_78116_c);
   }

   protected final void clearNonChestplateParts() {
      this.clearArmorPartsExcept(this.field_78115_e, this.field_178723_h, this.field_178724_i);
   }

   protected final void clearArmorPartsExcept(ModelRenderer... exceptions) {
      List exceptList = Arrays.asList(exceptions);
      if (!exceptList.contains(this.field_78116_c)) {
         this.field_78116_c = new ModelRenderer(this, 0, 0);
      }

      if (!exceptList.contains(this.field_178720_f)) {
         this.field_178720_f = new ModelRenderer(this, 0, 0);
      }

      if (!exceptList.contains(this.field_78115_e)) {
         this.field_78115_e = new ModelRenderer(this, 0, 0);
      }

      if (!exceptList.contains(this.field_178723_h)) {
         this.field_178723_h = new ModelRenderer(this, 0, 0);
      }

      if (!exceptList.contains(this.field_178724_i)) {
         this.field_178724_i = new ModelRenderer(this, 0, 0);
      }

      if (!exceptList.contains(this.field_178721_j)) {
         this.field_178721_j = new ModelRenderer(this, 0, 0);
      }

      if (!exceptList.contains(this.field_178722_k)) {
         this.field_178722_k = new ModelRenderer(this, 0, 0);
      }

   }

   public void func_225597_a_(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!this.savedDefaultAngles) {
         this.savedDefaultAngles = true;
         this.defaultHeadRotationPoint = this.saveRotationPoint(this.field_78116_c);
         this.defaultRightLegRotationPoint = this.saveRotationPoint(this.field_178721_j);
         this.defaultLeftLegRotationPoint = this.saveRotationPoint(this.field_178722_k);
      }

      if (entity instanceof ArmorStandEntity) {
         ArmorStandEntity stand = (ArmorStandEntity)entity;
         this.copyArmorStandRotation(this.field_78116_c, stand.func_175418_s());
         this.field_78116_c.func_78793_a(0.0F, 1.0F, 0.0F);
         this.copyArmorStandRotation(this.field_78115_e, stand.func_175408_t());
         this.copyArmorStandRotation(this.field_178724_i, stand.func_175404_u());
         this.copyArmorStandRotation(this.field_178723_h, stand.func_175411_v());
         this.copyArmorStandRotation(this.field_178722_k, stand.func_175403_w());
         this.field_178722_k.func_78793_a(1.9F, 11.0F, 0.0F);
         this.copyArmorStandRotation(this.field_178721_j, stand.func_175407_x());
         this.field_178721_j.func_78793_a(-1.9F, 11.0F, 0.0F);
         this.field_178720_f.func_217177_a(this.field_78116_c);
      } else {
         this.restoreRotationPoint(this.field_78116_c, this.defaultHeadRotationPoint);
         this.restoreRotationPoint(this.field_178721_j, this.defaultRightLegRotationPoint);
         this.restoreRotationPoint(this.field_178722_k, this.defaultLeftLegRotationPoint);
         this.field_178720_f.func_217177_a(this.field_78116_c);
         super.func_225597_a_(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      }

   }

   private Vector3f saveRotationPoint(ModelRenderer part) {
      return new Vector3f(part.field_78800_c, part.field_78797_d, part.field_78798_e);
   }

   private void restoreRotationPoint(ModelRenderer part, Vector3f savedPos) {
      part.func_78793_a(savedPos.func_195899_a(), savedPos.func_195900_b(), savedPos.func_195902_c());
   }

   private void copyArmorStandRotation(ModelRenderer part, Rotations rotation) {
      part.field_78795_f = (float)Math.toRadians((double)rotation.func_179415_b());
      part.field_78796_g = (float)Math.toRadians((double)rotation.func_179416_c());
      part.field_78808_h = (float)Math.toRadians((double)rotation.func_179413_d());
   }
}
