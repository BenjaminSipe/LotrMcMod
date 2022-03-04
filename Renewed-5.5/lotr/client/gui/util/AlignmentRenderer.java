package lotr.client.gui.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lotr.client.align.AlignmentFormatter;
import lotr.client.gui.PlayerMessageScreen;
import lotr.client.util.LOTRClientUtil;
import lotr.common.config.LOTRConfig;
import lotr.common.data.AlignmentDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.AreasOfInfluence;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionRank;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.fac.RankGender;
import lotr.common.util.LOTRUtil;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class AlignmentRenderer {
   public static final ResourceLocation ALIGNMENT_TEXTURE = new ResourceLocation("lotr", "textures/gui/alignment.png");
   private final AlignmentTextRenderer textRenderer;
   private int alignmentXBase;
   private int alignmentYBase;
   private int alignmentXCurrent;
   private int alignmentYCurrent;
   private int alignmentXPrev;
   private int alignmentYPrev;
   private static final int ALIGNMENT_Y_WHEN_OFFSCREEN = -20;
   private boolean firstAlignmentRender = true;
   private final Map factionTickers = new HashMap();
   private int alignDrainTick;
   private static final int ALIGN_DRAIN_DISPLAY_TIME = 200;
   private int alignDrainNum;

   public AlignmentRenderer(AlignmentTextRenderer textRenderer) {
      this.textRenderer = textRenderer;
   }

   private AlignmentTicker getOrCreateTickerForFaction(Faction fac) {
      return (AlignmentTicker)this.factionTickers.computeIfAbsent(fac.getName(), AlignmentTicker::new);
   }

   private boolean initialiseTickers() {
      FactionSettings currentFactionSettings = FactionSettingsManager.clientInstance().getCurrentLoadedFactions();
      if (currentFactionSettings != null) {
         List allFactions = currentFactionSettings.getAllPlayableAlignmentFactions();
         allFactions.forEach(this::getOrCreateTickerForFaction);
         return true;
      } else {
         return false;
      }
   }

   private void updateAllTickers(PlayerEntity player, boolean forceInstant) {
      this.factionTickers.values().forEach((ticker) -> {
         ticker.update(player, forceInstant);
      });
   }

   public void updateHUD(Minecraft mc, PlayerEntity player) {
      this.setBarBasePosition(mc);
      this.updateBarCurrentPosition(mc, player);
      if (this.firstAlignmentRender) {
         if (this.initialiseTickers()) {
            this.updateAllTickers(player, true);
            this.alignmentXPrev = this.alignmentXCurrent = this.alignmentXBase;
            this.alignmentYPrev = this.alignmentYCurrent = -20;
            this.firstAlignmentRender = false;
         }
      } else {
         this.updateAllTickers(player, false);
      }

      if (this.alignDrainTick > 0) {
         --this.alignDrainTick;
         if (this.alignDrainTick <= 0) {
            this.alignDrainNum = 0;
         }
      }

   }

   private void setBarBasePosition(Minecraft mc) {
      MainWindow mainWindow = mc.func_228018_at_();
      int width = mainWindow.func_198107_o();
      int height = mainWindow.func_198087_p();
      this.alignmentXBase = width / 2 + (Integer)LOTRConfig.CLIENT.alignmentXOffset.get();
      this.alignmentYBase = 4 + (Integer)LOTRConfig.CLIENT.alignmentYOffset.get();
      if (this.isBossActive()) {
         this.alignmentYBase += 20;
      }

      if (this.isInvasionWatched()) {
         this.alignmentYBase += 20;
      }

   }

   private void updateBarCurrentPosition(Minecraft mc, PlayerEntity player) {
      this.alignmentXPrev = this.alignmentXCurrent;
      this.alignmentYPrev = this.alignmentYCurrent;
      this.alignmentXCurrent = this.alignmentXBase;
      int yMove = (int)((float)(this.alignmentYBase - -20) / 3.0F);
      boolean alignmentOnscreen = (mc.field_71462_r == null || mc.field_71462_r instanceof PlayerMessageScreen) && !mc.field_71474_y.field_74321_H.func_151470_d() && !mc.field_71474_y.field_74330_P;
      if (alignmentOnscreen) {
         this.alignmentYCurrent = Math.min(this.alignmentYCurrent + yMove, this.alignmentYBase);
      } else {
         this.alignmentYCurrent = Math.max(this.alignmentYCurrent - yMove, -20);
      }

   }

   public void displayAlignmentDrain(int numFactions) {
      this.alignDrainTick = 200;
      this.alignDrainNum = numFactions;
   }

   public void resetInMenu() {
      this.firstAlignmentRender = true;
      this.factionTickers.clear();
      this.alignDrainTick = 0;
      this.alignDrainNum = 0;
   }

   public void renderAlignmentHUDBar(MatrixStack matStack, Minecraft mc, PlayerEntity player, float partialTick) {
      if (!this.firstAlignmentRender) {
         LOTRPlayerData pd = LOTRLevelData.clientInstance().getData(player);
         AlignmentDataModule alignData = pd.getAlignmentData();
         Faction viewingFac = alignData.getCurrentViewedFaction();
         if (viewingFac != null) {
            float alignmentXF = (float)this.alignmentXPrev + (float)(this.alignmentXCurrent - this.alignmentXPrev) * partialTick;
            float alignmentYF = (float)this.alignmentYPrev + (float)(this.alignmentYCurrent - this.alignmentYPrev) * partialTick;
            boolean text = this.alignmentYCurrent == this.alignmentYBase;
            float alignment = this.getOrCreateTickerForFaction(viewingFac).getInterpolatedAlignment(partialTick);
            this.renderAlignmentBar(matStack, mc, player, alignment, false, viewingFac, alignmentXF, alignmentYF, text, text, text, false);
            if (this.alignDrainTick > 0 && text) {
               float alpha = 1.0F;
               int fadeTick = 20;
               if (this.alignDrainTick < fadeTick) {
                  alpha = (float)this.alignDrainTick / (float)fadeTick;
               }

               this.renderAlignmentDrain(matStack, mc, (int)alignmentXF - 155, (int)alignmentYF + 2, this.alignDrainNum, alpha);
            }
         }

      }
   }

   private boolean isBossActive() {
      return false;
   }

   private boolean isInvasionWatched() {
      return false;
   }

   public void renderAlignmentBar(MatrixStack matStack, Minecraft mc, float alignment, boolean isOtherPlayer, Faction faction, float x, float y, boolean renderFacName, boolean renderValue, boolean renderLimits, boolean renderLimitValues) {
      this.renderAlignmentBar(matStack, mc, mc.field_71439_g, alignment, isOtherPlayer, faction, x, y, renderFacName, renderValue, renderLimits, renderLimitValues);
   }

   private void renderAlignmentBar(MatrixStack matStack, Minecraft mc, PlayerEntity player, float alignment, boolean isOtherPlayer, Faction faction, float x, float y, boolean renderFacName, boolean renderValue, boolean renderLimits, boolean renderLimitValues) {
      LOTRPlayerData clientPD = LOTRLevelData.clientInstance().getData(player);
      AlignmentDataModule alignData = clientPD.getAlignmentData();
      FactionRank rank = faction.getRankFor(alignment);
      RankGender preferredRankGender = clientPD.getMiscData().getPreferredRankGender();
      boolean pledged = alignData.isPledgedTo(faction);
      AlignmentTicker ticker = this.getOrCreateTickerForFaction(faction);
      float alignMin = 0.0F;
      float alignMax = 0.0F;
      FactionRank rankMin = null;
      FactionRank rankMax = null;
      float pastRankMultiplier = 10.0F;
      float firstRankAlign;
      if (!rank.isDummyRank()) {
         alignMin = rank.getAlignment();
         rankMin = rank;
         FactionRank nextRank = faction.getRankAbove(rank);
         if (nextRank != null && !nextRank.isDummyRank() && nextRank != rank) {
            alignMax = nextRank.getAlignment();
            rankMax = nextRank;
         } else {
            alignMax = rank.getAlignment() * 10.0F;

            for(rankMax = rank; alignment >= alignMax; alignMax *= 10.0F) {
               alignMin = alignMax;
            }
         }
      } else {
         FactionRank firstRank = faction.getFirstRank();
         if (firstRank != null && !firstRank.isDummyRank()) {
            firstRankAlign = firstRank.getAlignment();
         } else {
            firstRankAlign = 10.0F;
         }

         if (Math.abs(alignment) < firstRankAlign) {
            alignMin = -firstRankAlign;
            alignMax = firstRankAlign;
            rankMin = faction.getEnemyRank();
            rankMax = firstRank != null && !firstRank.isDummyRank() ? firstRank : faction.getNeutralRank();
         } else if (alignment < 0.0F) {
            alignMax = -firstRankAlign;
            alignMin = alignMax * 10.0F;

            for(rankMin = rankMax = faction.getEnemyRank(); alignment <= alignMin; alignMin = alignMax * 10.0F) {
               alignMax *= 10.0F;
            }
         } else {
            alignMin = firstRankAlign;
            alignMax = firstRankAlign * 10.0F;

            for(rankMin = rankMax = faction.getNeutralRank(); alignment >= alignMax; alignMax *= 10.0F) {
               alignMin = alignMax;
            }
         }
      }

      firstRankAlign = (alignment - alignMin) / (alignMax - alignMin);
      mc.func_110434_K().func_110577_a(ALIGNMENT_TEXTURE);
      int barWidth = 232;
      int barHeight = 14;
      int activeBarWidth = 220;
      float z = 0.0F;
      float[] factionColors = faction.getColorComponents();
      RenderSystem.color4f(factionColors[0], factionColors[1], factionColors[2], 1.0F);
      LOTRClientUtil.blitFloat(matStack, x - (float)(barWidth / 2), y, z, 0.0F, 14.0F, (float)barWidth, (float)barHeight);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      LOTRClientUtil.blitFloat(matStack, x - (float)(barWidth / 2), y, z, 0.0F, 0.0F, (float)barWidth, (float)barHeight);
      float ringProgressAdj = (firstRankAlign - 0.5F) * 2.0F;
      int ringSize = 16;
      float ringX = x - (float)(ringSize / 2) + ringProgressAdj * (float)activeBarWidth / 2.0F;
      float ringY = y + (float)(barHeight / 2) - (float)(ringSize / 2);
      int flashTick = ticker.getFlashTick();
      if (pledged) {
         LOTRClientUtil.blitFloat(matStack, ringX, ringY, z, (float)(16 * Math.round((float)(flashTick / 3))), 212.0F, (float)ringSize, (float)ringSize);
      } else {
         LOTRClientUtil.blitFloat(matStack, ringX, ringY, z, (float)(16 * Math.round((float)(flashTick / 3))), 36.0F, (float)ringSize, (float)ringSize);
      }

      int numericalTick;
      if (faction.isPlayableAlignmentFaction()) {
         float alpha = 0.0F;
         boolean definedZone = false;
         AreasOfInfluence areasOfInfluence = faction.getAreasOfInfluence();
         if (areasOfInfluence.isInArea(player)) {
            alpha = 1.0F;
            definedZone = areasOfInfluence.isInDefinedArea(player);
         } else {
            alpha = areasOfInfluence.getAlignmentMultiplier(player);
            definedZone = true;
         }

         if (alpha > 0.0F) {
            int arrowSize = 14;
            int y0 = definedZone ? 60 : 88;
            numericalTick = definedZone ? 74 : 102;
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(factionColors[0], factionColors[1], factionColors[2], alpha);
            LOTRClientUtil.blitFloat(matStack, x - (float)(barWidth / 2) - (float)arrowSize, y, z, 0.0F, (float)numericalTick, (float)arrowSize, (float)arrowSize);
            LOTRClientUtil.blitFloat(matStack, x + (float)(barWidth / 2), y, z, (float)arrowSize, (float)numericalTick, (float)arrowSize, (float)arrowSize);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
            LOTRClientUtil.blitFloat(matStack, x - (float)(barWidth / 2) - (float)arrowSize, y, z, 0.0F, (float)y0, (float)arrowSize, (float)arrowSize);
            LOTRClientUtil.blitFloat(matStack, x + (float)(barWidth / 2), y, z, (float)arrowSize, (float)y0, (float)arrowSize, (float)arrowSize);
            RenderSystem.disableBlend();
         }
      }

      FontRenderer fr = mc.field_71466_p;
      int textX = Math.round(x);
      int textY = Math.round(y + (float)barHeight + 4.0F);
      String alignS;
      if (renderLimits) {
         alignS = rankMin.getDisplayShortName(preferredRankGender);
         String sMax = rankMax.getDisplayShortName(preferredRankGender);
         if (renderLimitValues) {
            alignS = I18n.func_135052_a("gui.lotr.alignment.limits", new Object[]{alignS, AlignmentFormatter.formatAlignForDisplay(alignMin)});
            sMax = I18n.func_135052_a("gui.lotr.alignment.limits", new Object[]{sMax, AlignmentFormatter.formatAlignForDisplay(alignMax)});
         }

         numericalTick = barWidth / 2 - 6;
         int xMin = Math.round(x - (float)numericalTick);
         int xMax = Math.round(x + (float)numericalTick);
         matStack.func_227860_a_();
         matStack.func_227862_a_(0.5F, 0.5F, 0.5F);
         this.textRenderer.drawAlignmentText(matStack, fr, xMin * 2 - fr.func_78256_a(alignS) / 2, textY * 2, new StringTextComponent(alignS), 1.0F);
         this.textRenderer.drawAlignmentText(matStack, fr, xMax * 2 - fr.func_78256_a(sMax) / 2, textY * 2, new StringTextComponent(sMax), 1.0F);
         matStack.func_227865_b_();
      }

      if (renderFacName) {
         ITextComponent name = faction.getDisplayName();
         this.textRenderer.drawAlignmentText(matStack, fr, textX - fr.func_238414_a_(name) / 2, textY, name, 1.0F);
      }

      if (renderValue) {
         alignS = AlignmentFormatter.formatAlignForDisplay(alignment);
         float alignAlpha = 1.0F;
         numericalTick = ticker.getDisplayNumericalTick();
         if (numericalTick > 0) {
            alignS = AlignmentFormatter.formatAlignForDisplay(alignment);
            alignAlpha = LOTRUtil.normalisedTriangleWave((float)numericalTick, 30.0F, 0.7F, 1.0F);
            int fadeTick = 15;
            if (numericalTick < fadeTick) {
               alignAlpha *= (float)numericalTick / (float)fadeTick;
            }
         } else {
            alignS = rank.getDisplayShortName(preferredRankGender);
            alignAlpha = 1.0F;
         }

         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         AlignmentTextRenderer var10000 = this.textRenderer;
         int var10003 = textX - fr.func_78256_a(alignS) / 2;
         fr.getClass();
         var10000.drawAlignmentText(matStack, fr, var10003, textY + 9 + 3, new StringTextComponent(alignS), alignAlpha);
         RenderSystem.disableBlend();
      }

   }

   public void renderAlignmentDrain(MatrixStack matStack, Minecraft mc, int x, int y, int numFactions) {
      this.renderAlignmentDrain(matStack, mc, x, y, numFactions, 1.0F);
   }

   public void renderAlignmentDrain(MatrixStack matStack, Minecraft mc, int x, int y, int numFactions, float alpha) {
      float z = 0.0F;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
      mc.func_110434_K().func_110577_a(ALIGNMENT_TEXTURE);
      LOTRClientUtil.blitFloat(matStack, (float)x, (float)y, z, 0.0F, 128.0F, 16.0F, 16.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      ITextComponent drainText = new StringTextComponent("-" + numFactions);
      FontRenderer fr = mc.field_71466_p;
      AlignmentTextRenderer var10000 = this.textRenderer;
      int var10003 = x + 8 - fr.func_238414_a_(drainText) / 2;
      int var10004 = y + 8;
      fr.getClass();
      var10000.drawBorderedText(matStack, fr, var10003, var10004 - 9 / 2, drainText, 16777215, alpha);
      RenderSystem.disableBlend();
   }
}
