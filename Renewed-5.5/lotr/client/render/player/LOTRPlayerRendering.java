package lotr.client.render.player;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.align.AlignmentFormatter;
import lotr.client.gui.util.AlignmentRenderer;
import lotr.client.gui.util.AlignmentTextRenderer;
import lotr.client.util.LOTRClientUtil;
import lotr.common.config.LOTRConfig;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.Faction;
import lotr.common.init.LOTRDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderPlayerEvent.Post;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LOTRPlayerRendering {
   private final Minecraft mc;
   private final AlignmentTextRenderer alignmentTextRenderer = AlignmentTextRenderer.newInWorldRenderer().setDefaultSeethrough(true);
   private static final boolean debugRenderOwnAlignment = false;

   public LOTRPlayerRendering(Minecraft mc) {
      this.mc = mc;
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void postRender(Post event) {
      PlayerEntity player = event.getPlayer();
      float tick = event.getPartialRenderTick();
      MatrixStack matStack = event.getMatrixStack();
      EntityRendererManager renderMgr = this.mc.func_175598_ae();
      ActiveRenderInfo renderInfo = renderMgr.field_217783_c;
      Vector3d viewPos = renderInfo.func_216785_c();
      float yOffset = player.func_70608_bn() ? -1.5F : 0.0F;
      if (this.shouldRenderAlignment(player) && (LOTRDimensions.isModDimension(this.mc.field_71441_e) || (Boolean)LOTRConfig.CLIENT.showAlignmentEverywhere.get())) {
         LOTRPlayerData clientPD = LOTRLevelData.clientInstance().getData(this.mc.field_71439_g);
         LOTRPlayerData otherPD = LOTRLevelData.clientInstance().getData(player);
         Faction currentViewedFaction = clientPD.getAlignmentData().getCurrentViewedFaction();
         float alignment = otherPD.getAlignmentData().getAlignment(currentViewedFaction);
         if (ForgeHooksClient.isNameplateInRenderDistance(player, renderMgr.func_229099_b_(player))) {
            FontRenderer fr = this.mc.field_71466_p;
            matStack.func_227860_a_();
            matStack.func_227861_a_(0.0D, (double)(player.func_213302_cg() + 0.6F + yOffset), 0.0D);
            matStack.func_227863_a_(renderMgr.func_229098_b_());
            float scale = 0.025F;
            matStack.func_227862_a_(-scale, -scale, scale);
            RenderSystem.disableLighting();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            float alpha = 1.0F;
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
            ITextComponent strAlign = new StringTextComponent(AlignmentFormatter.formatAlignForDisplay(alignment));
            this.mc.func_110434_K().func_110577_a(AlignmentRenderer.ALIGNMENT_TEXTURE);
            int ringWidth = 18;
            int x = -MathHelper.func_76128_c((double)(fr.func_238414_a_(strAlign) + ringWidth) / 2.0D);
            int y = -12;
            LOTRClientUtil.blit(matStack, x, y - 5, 0, 36, 16, 16);
            this.alignmentTextRenderer.drawAlignmentText(matStack, fr, x + ringWidth, y, strAlign, alpha);
            RenderSystem.disableBlend();
            matStack.func_227865_b_();
         }
      }

   }

   private boolean shouldRenderPlayerHUD(PlayerEntity player) {
      if (!Minecraft.func_71382_s()) {
         return false;
      } else {
         return player != this.mc.field_175622_Z && !player.func_226273_bm_() && !player.func_98034_c(this.mc.field_71439_g);
      }
   }

   private boolean shouldRenderAlignment(PlayerEntity player) {
      if ((Boolean)LOTRConfig.CLIENT.displayAlignmentAboveHead.get() && this.shouldRenderPlayerHUD(player)) {
         return LOTRLevelData.clientInstance().getData(player).getAlignmentData().displayAlignmentAboveHead();
      } else {
         return false;
      }
   }
}
