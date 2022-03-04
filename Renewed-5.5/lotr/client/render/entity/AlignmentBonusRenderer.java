package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.align.AlignmentFormatter;
import lotr.client.gui.util.AlignmentRenderer;
import lotr.client.gui.util.AlignmentTextRenderer;
import lotr.client.util.LOTRClientUtil;
import lotr.common.LOTRMod;
import lotr.common.data.AlignmentDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.misc.AlignmentBonusEntity;
import lotr.common.fac.Faction;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AlignmentBonusRenderer extends EntityRenderer {
   private static final ResourceLocation ALIGNMENT_TEXTURE;
   private final AlignmentTextRenderer alignmentTextRenderer = AlignmentTextRenderer.newInWorldRenderer().setDefaultSeethrough(true);

   public AlignmentBonusRenderer(EntityRendererManager mgr) {
      super(mgr);
   }

   public ResourceLocation getEntityTexture(AlignmentBonusEntity entity) {
      return ALIGNMENT_TEXTURE;
   }

   public void render(AlignmentBonusEntity bonusEntity, float yaw, float ticks, MatrixStack matStack, IRenderTypeBuffer buf, int packedLight) {
      PlayerEntity player = LOTRMod.PROXY.getClientPlayer();
      AlignmentDataModule alignData = LOTRLevelData.getSidedData(player).getAlignmentData();
      Faction viewingFaction = alignData.getCurrentViewedFaction();
      Faction renderFaction = null;
      boolean showConquest = false;
      if (bonusEntity.shouldDisplayConquestBonus(alignData)) {
         renderFaction = viewingFaction;
         showConquest = true;
      } else {
         renderFaction = bonusEntity.getFactionToDisplay(alignData);
      }

      if (renderFaction != null) {
         float alignBonus = bonusEntity.getAlignmentBonusFor(renderFaction);
         boolean showAlign = alignBonus != 0.0F;
         float conqBonus = bonusEntity.getConquestBonus();
         if (showAlign || showConquest) {
            ITextComponent title = bonusEntity.getBonusDisplayText();
            boolean isViewingFaction = renderFaction == viewingFaction;
            boolean showTitle = bonusEntity.shouldShowBonusText(showAlign, showConquest);
            float bonusAge = bonusEntity.getBonusAgeF(ticks);
            float alpha = bonusAge < 0.75F ? 1.0F : (1.0F - bonusAge) / 0.25F;
            matStack.func_227860_a_();
            matStack.func_227863_a_(this.field_76990_c.func_229098_b_());
            matStack.func_227862_a_(-0.025F, -0.025F, 0.025F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            this.renderBonusText(matStack, isViewingFaction, renderFaction, title, showTitle, alignBonus, showAlign, conqBonus, showConquest, alpha);
            RenderSystem.disableBlend();
            matStack.func_227865_b_();
         }
      }

   }

   private void renderBonusText(MatrixStack matStack, boolean isViewingFaction, Faction renderFaction, ITextComponent title, boolean showTitle, float align, boolean showAlign, float conq, boolean showConq, float alpha) {
      FontRenderer fr = this.field_76990_c.func_78716_a();
      ITextComponent strAlign = new StringTextComponent(AlignmentFormatter.formatAlignForDisplay(align));
      ITextComponent strConq = new StringTextComponent(AlignmentFormatter.formatConqForDisplay(conq, true));
      boolean negativeConq = conq < 0.0F;
      matStack.func_227860_a_();
      if (!isViewingFaction) {
         float scale = 0.5F;
         matStack.func_227862_a_(scale, scale, 1.0F);
         strAlign = new TranslationTextComponent("%s (%s...)", new Object[]{strAlign, renderFaction.getDisplayName()});
      }

      int ringWidth = 18;
      int x = -MathHelper.func_76128_c((double)(fr.func_238414_a_((ITextProperties)strAlign) + ringWidth) / 2.0D);
      int y = -16;
      if (showAlign) {
         this.field_76990_c.field_78724_e.func_110577_a(ALIGNMENT_TEXTURE);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
         LOTRClientUtil.blit(matStack, x, y - 5, 0, 36, 16, 16);
         this.alignmentTextRenderer.drawAlignmentText(matStack, fr, x + ringWidth, y, (ITextComponent)strAlign, alpha);
         y += 14;
      }

      if (showTitle) {
         x = -MathHelper.func_76128_c((double)fr.func_238414_a_(title) / 2.0D);
         if (showAlign) {
            this.alignmentTextRenderer.drawAlignmentText(matStack, fr, x, y, title, alpha);
         } else {
            this.alignmentTextRenderer.drawConquestText(matStack, fr, x, y, title, negativeConq, alpha);
         }

         y += 16;
      }

      if (showConq) {
         x = -MathHelper.func_76128_c((double)(fr.func_238414_a_(strConq) + ringWidth) / 2.0D);
         this.field_76990_c.field_78724_e.func_110577_a(ALIGNMENT_TEXTURE);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
         LOTRClientUtil.blit(matStack, x, y - 5, negativeConq ? 16 : 0, 228, 16, 16);
         this.alignmentTextRenderer.drawConquestText(matStack, fr, x + ringWidth, y, strConq, negativeConq, alpha);
      }

      matStack.func_227865_b_();
   }

   static {
      ALIGNMENT_TEXTURE = AlignmentRenderer.ALIGNMENT_TEXTURE;
   }
}
