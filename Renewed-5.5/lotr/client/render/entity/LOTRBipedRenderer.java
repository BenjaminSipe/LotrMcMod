package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.render.entity.model.LOTRBipedModel;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel.ArmPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;

public class LOTRBipedRenderer extends BipedRenderer {
   public static final float PLAYER_SCALE = 0.9375F;
   protected static final float BIPED_SHADOW_SIZE = 0.5F;
   private final LOTRBipedModel standardArmsModel;
   private final LOTRBipedModel smallArmsModel;

   public LOTRBipedRenderer(EntityRendererManager mgr, ArmsStyleModelProvider armsStyleModelProvider, LOTRBipedModel leggingsModel, LOTRBipedModel mainArmorModel, float shadowSize) {
      super(mgr, armsStyleModelProvider.getModelForArmsStyle(false), shadowSize);
      this.standardArmsModel = (LOTRBipedModel)this.field_77045_g;
      this.smallArmsModel = armsStyleModelProvider.getModelForArmsStyle(true);
      this.func_177094_a(new BipedArmorLayer(this, leggingsModel, mainArmorModel));
   }

   protected void preRenderCallback(NPCEntity entity, MatrixStack matStack, float f) {
      super.func_225620_a_(entity, matStack, f);
      matStack.func_227862_a_(0.9375F, 0.9375F, 0.9375F);
   }

   public void render(NPCEntity entity, float yaw, float partialTicks, MatrixStack matStack, IRenderTypeBuffer buf, int packedLight) {
      this.selectEntityModelForArmsStyle(entity);
      ((LOTRBipedModel)this.field_77045_g).field_228270_o_ = entity.func_225608_bj_();
      ((LOTRBipedModel)this.field_77045_g).showChest = entity.shouldRenderNPCChest();
      ((LOTRBipedModel)this.field_77045_g).isEating = entity.getNPCItemsInv().getIsEating();
      ((LOTRBipedModel)this.field_77045_g).setTalkAnimation(entity.getTalkAnimations(), partialTicks);
      this.setArmPoses(entity);
      super.func_225623_a_(entity, yaw, partialTicks, matStack, buf, packedLight);
   }

   private void selectEntityModelForArmsStyle(NPCEntity entity) {
      if (entity.useSmallArmsModel()) {
         this.field_77045_g = this.smallArmsModel;
      } else {
         this.field_77045_g = this.standardArmsModel;
      }

   }

   private void setArmPoses(NPCEntity entity) {
      ArmPose mainArmPose = getArmPose(entity, Hand.MAIN_HAND);
      ArmPose offArmPose = getArmPose(entity, Hand.OFF_HAND);
      if (mainArmPose.func_241657_a_()) {
         offArmPose = entity.func_184592_cb().func_190926_b() ? ArmPose.EMPTY : ArmPose.ITEM;
      }

      if (entity.func_184591_cq() == HandSide.RIGHT) {
         ((LOTRBipedModel)this.field_77045_g).field_187076_m = mainArmPose;
         ((LOTRBipedModel)this.field_77045_g).field_187075_l = offArmPose;
      } else {
         ((LOTRBipedModel)this.field_77045_g).field_187076_m = offArmPose;
         ((LOTRBipedModel)this.field_77045_g).field_187075_l = mainArmPose;
      }

   }

   private static ArmPose getArmPose(LivingEntity entity, Hand hand) {
      ItemStack heldItem = entity.func_184586_b(hand);
      if (heldItem.func_190926_b()) {
         return ArmPose.EMPTY;
      } else {
         if (entity.func_184600_cs() == hand && entity.func_184605_cv() > 0) {
            UseAction useaction = heldItem.func_77975_n();
            if (useaction == UseAction.BLOCK) {
               return ArmPose.BLOCK;
            }

            if (useaction == UseAction.BOW) {
               return ArmPose.BOW_AND_ARROW;
            }

            if (useaction == UseAction.SPEAR) {
               return ArmPose.THROW_SPEAR;
            }

            if (useaction == UseAction.CROSSBOW && hand == entity.func_184600_cs()) {
               return ArmPose.CROSSBOW_CHARGE;
            }
         } else if (!entity.field_82175_bq && heldItem.func_77973_b() == Items.field_222114_py && CrossbowItem.func_220012_d(heldItem)) {
            return ArmPose.CROSSBOW_HOLD;
         }

         return ArmPose.ITEM;
      }
   }
}
