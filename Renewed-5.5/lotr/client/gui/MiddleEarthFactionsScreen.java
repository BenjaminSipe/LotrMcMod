package lotr.client.gui;

import com.google.common.math.IntMath;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lotr.client.align.AlignmentFormatter;
import lotr.client.gui.map.MiddleEarthMapScreen;
import lotr.client.gui.util.AlignmentRenderer;
import lotr.client.gui.util.AlignmentTextRenderer;
import lotr.client.gui.widget.ScrollPane;
import lotr.client.gui.widget.button.FactionsMapButton;
import lotr.client.gui.widget.button.FactionsPageButton;
import lotr.client.gui.widget.button.FriendlyFireToggleButton;
import lotr.client.gui.widget.button.PledgeButton;
import lotr.client.gui.widget.button.PreferredRankGenderButton;
import lotr.client.gui.widget.button.RedBookButton;
import lotr.client.util.LOTRClientUtil;
import lotr.common.config.LOTRConfig;
import lotr.common.data.AlignmentDataModule;
import lotr.common.data.FactionStats;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.AreasOfInfluence;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionRank;
import lotr.common.fac.FactionRegion;
import lotr.common.fac.FactionRelation;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.fac.MapSquare;
import lotr.common.fac.RankGender;
import lotr.common.init.LOTRDimensions;
import lotr.common.network.CPacketSetPledge;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MiddleEarthFactionsScreen extends MiddleEarthMenuScreen {
   public static final ResourceLocation FACTIONS_TEXTURE = new ResourceLocation("lotr", "textures/gui/factions.png");
   public static final ResourceLocation FACTIONS_TEXTURE_FULL = new ResourceLocation("lotr", "textures/gui/factions_full.png");
   private FactionSettings currentLoadedFactions;
   private static RegistryKey currentDimension;
   private static RegistryKey prevDimension;
   private static FactionRegion currentRegion;
   private static FactionRegion prevRegion;
   private static List currentFactionList;
   private int currentFactionIndex = 0;
   private int prevFactionIndex = 0;
   private Faction currentFaction;
   private static final int MAX_ALIGNMENTS_DISPLAYED = 1;
   private static MiddleEarthFactionsScreen.Page currentPage;
   private int pageY = 46;
   private int pageWidth = 256;
   private int pageHeight = 128;
   private int pageBorderLeft = 16;
   private int pageBorderTop = 12;
   private int pageMapX = 159;
   private int pageMapY = 22;
   private int pageMapSize = 80;
   private MiddleEarthMapScreen mapDrawGui;
   private final AlignmentTextRenderer alignmentTextRenderer = AlignmentTextRenderer.newGUIRenderer();
   private final AlignmentRenderer alignmentRenderer;
   private Button buttonRegions;
   private Button buttonPagePrev;
   private Button buttonPageNext;
   private Button buttonFactionMap;
   private Button buttonPreferredRanksMasc;
   private Button buttonPreferredRanksFem;
   private Button buttonToggleFriendlyFire;
   private PledgeButton buttonOpenPledgeScreen;
   private PledgeButton buttonPledgeConfirm;
   private PledgeButton buttonPledgeRevoke;
   private float currentScroll;
   private boolean isScrolling;
   private boolean wasMouseDown;
   private boolean isMouseDown;
   private int scrollBarWidth;
   private int scrollBarHeight;
   private int scrollBarX;
   private int scrollBarY;
   private int scrollBarBorder;
   private int scrollWidgetWidth;
   private int scrollWidgetHeight;
   private ScrollPane scrollPaneAlliesEnemies;
   private int scrollAlliesEnemiesX;
   private static final int MAX_DISPLAYED_ALLIES_ENEMIES = 10;
   private int numDisplayedAlliesEnemies;
   private List currentAlliesEnemies;
   private boolean isOtherPlayer;
   private String otherPlayerName;
   private Map otherPlayerAlignmentMap;
   private boolean isPledging;
   private boolean isUnpledging;

   public MiddleEarthFactionsScreen() {
      super(new StringTextComponent("FACTIONS"));
      this.alignmentRenderer = new AlignmentRenderer(this.alignmentTextRenderer);
      this.xSize = this.pageWidth;
      this.currentScroll = 0.0F;
      this.isScrolling = false;
      this.scrollBarWidth = 240;
      this.scrollBarHeight = 14;
      this.scrollBarX = this.xSize / 2 - this.scrollBarWidth / 2;
      this.scrollBarY = 180;
      this.scrollBarBorder = 1;
      this.scrollWidgetWidth = 17;
      this.scrollWidgetHeight = 12;
      this.scrollPaneAlliesEnemies = (new ScrollPane(7, 7)).setColors(5521198, 8019267);
      this.scrollAlliesEnemiesX = 138;
      this.isOtherPlayer = false;
      this.isPledging = false;
      this.isUnpledging = false;
      this.mapDrawGui = new MiddleEarthMapScreen();
      this.currentLoadedFactions = FactionSettingsManager.clientInstance().getCurrentLoadedFactions();
   }

   public void setOtherPlayer(String name, Map alignments) {
      this.isOtherPlayer = true;
      this.otherPlayerName = name;
      this.otherPlayerAlignmentMap = alignments;
   }

   public void func_231158_b_(Minecraft mc, int w, int h) {
      super.func_231158_b_(mc, w, h);
      this.mapDrawGui.func_231158_b_(mc, w, h);
   }

   public void func_231160_c_() {
      super.func_231160_c_();
      if (this.isOtherPlayer) {
         this.removeButton(this.buttonMenuReturn);
      }

      this.buttonRegions = (Button)this.func_230480_a_(new RedBookButton(this.guiLeft + this.xSize / 2 - 60, this.guiTop + this.scrollBarY + 20, 120, 20, StringTextComponent.field_240750_d_, (button) -> {
         List regionList = this.currentLoadedFactions.getRegionsForDimension(currentDimension);
         if (!regionList.isEmpty()) {
            int i = regionList.indexOf(currentRegion);
            ++i;
            i = IntMath.mod(i, regionList.size());
            currentRegion = (FactionRegion)regionList.get(i);
            this.updateCurrentDimensionAndFaction();
            this.setCurrentScrollFromFaction();
            this.scrollPaneAlliesEnemies.resetScroll();
            this.isPledging = false;
            this.isUnpledging = false;
         }

      }));
      this.buttonPagePrev = (Button)this.func_230480_a_(new FactionsPageButton(this.guiLeft + 13, this.guiTop + this.pageY + 104, true, new TranslationTextComponent("gui.lotr.factions.page.previous"), (button) -> {
         MiddleEarthFactionsScreen.Page newPage = currentPage.prev();
         if (newPage != null) {
            currentPage = newPage;
            this.scrollPaneAlliesEnemies.resetScroll();
            this.isPledging = false;
            this.isUnpledging = false;
         }

      }));
      this.buttonPageNext = (Button)this.func_230480_a_(new FactionsPageButton(this.guiLeft + this.pageWidth - 29, this.guiTop + this.pageY + 104, false, new TranslationTextComponent("gui.lotr.factions.page.next"), (button) -> {
         MiddleEarthFactionsScreen.Page newPage = currentPage.next();
         if (newPage != null) {
            currentPage = newPage;
            this.scrollPaneAlliesEnemies.resetScroll();
            this.isPledging = false;
            this.isUnpledging = false;
         }

      }));
      this.buttonFactionMap = (Button)this.func_230480_a_(new FactionsMapButton(this.guiLeft + this.pageMapX + this.pageMapSize - 3 - 8, this.guiTop + this.pageY + this.pageMapY + 3, (button) -> {
         MiddleEarthMapScreen factionGuiMap = new MiddleEarthMapScreen();
         factionGuiMap.setAreasOfInfluence(this.currentFaction);
         this.field_230706_i_.func_147108_a(factionGuiMap);
      }));
      this.buttonPreferredRanksMasc = (Button)this.func_230480_a_(new PreferredRankGenderButton(this.guiLeft + this.pageWidth - this.pageBorderLeft - 24, this.guiTop + this.pageY + this.pageBorderTop, RankGender.MASCULINE, PreferredRankGenderButton::sendPreferenceToServer));
      this.buttonPreferredRanksFem = (Button)this.func_230480_a_(new PreferredRankGenderButton(this.guiLeft + this.pageWidth - this.pageBorderLeft - 12, this.guiTop + this.pageY + this.pageBorderTop, RankGender.FEMININE, PreferredRankGenderButton::sendPreferenceToServer));
      this.buttonToggleFriendlyFire = (Button)this.func_230480_a_(new FriendlyFireToggleButton(this.guiLeft + this.scrollBarX + this.scrollBarWidth - 16, this.guiTop + this.scrollBarY + 22, FriendlyFireToggleButton::sendToggleToServer));
      this.buttonOpenPledgeScreen = (PledgeButton)this.func_230480_a_(new PledgeButton(this, this.guiLeft + 14, this.guiTop + this.pageY + this.pageHeight - 42, false, (button) -> {
         if (this.getClientPlayerData().getAlignmentData().isPledgedTo(this.currentFaction)) {
            this.isUnpledging = true;
         } else {
            this.isPledging = true;
         }

      }));
      this.buttonPledgeConfirm = (PledgeButton)this.func_230480_a_(new PledgeButton(this, this.guiLeft + this.pageWidth / 2 - 16, this.guiTop + this.pageY + this.pageHeight - 44, false, (button) -> {
         LOTRPacketHandler.sendToServer(new CPacketSetPledge(this.currentFaction));
         this.isPledging = false;
      }));
      this.buttonPledgeRevoke = (PledgeButton)this.func_230480_a_(new PledgeButton(this, this.guiLeft + this.pageWidth / 2 - 16, this.guiTop + this.pageY + this.pageHeight - 44, true, (button) -> {
         LOTRPacketHandler.sendToServer(new CPacketSetPledge((Faction)null));
         this.isUnpledging = false;
         this.field_230706_i_.func_147108_a((Screen)null);
      }));
      prevDimension = currentDimension = LOTRDimensions.getCurrentLOTRDimensionOrFallback(this.field_230706_i_.field_71441_e);
      AlignmentDataModule alignData = LOTRLevelData.clientInstance().getData(this.field_230706_i_.field_71439_g).getAlignmentData();
      this.currentFaction = alignData.getCurrentViewedFactionOrFallbackToFirstIn(currentDimension);
      if (this.currentFaction != null) {
         prevRegion = currentRegion = this.currentFaction.getRegion();
         currentFactionList = this.currentLoadedFactions.getFactionsForRegion(currentRegion);
         this.prevFactionIndex = this.currentFactionIndex = currentFactionList.indexOf(this.currentFaction);
         this.setCurrentScrollFromFaction();
      }

   }

   private LOTRPlayerData getClientPlayerData() {
      return LOTRLevelData.clientInstance().getData(this.field_230706_i_.field_71439_g);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      this.updateCurrentDimensionAndFaction();
      AlignmentDataModule alignData = this.getClientPlayerData().getAlignmentData();
      if (this.isPledging && !alignData.hasPledgeAlignment(this.currentFaction)) {
         this.isPledging = false;
      }

      if (this.isUnpledging && !alignData.isPledgedTo(this.currentFaction)) {
         this.isUnpledging = false;
      }

   }

   private void updateCurrentDimensionAndFaction() {
      AlignmentDataModule alignData = this.getClientPlayerData().getAlignmentData();
      Map lastViewedRegions = new HashMap();
      if (currentFactionList != null && this.currentFactionIndex != this.prevFactionIndex) {
         this.currentFaction = (Faction)currentFactionList.get(this.currentFactionIndex);
      }

      this.prevFactionIndex = this.currentFactionIndex;
      currentDimension = LOTRDimensions.getCurrentLOTRDimensionOrFallback(this.field_230706_i_.field_71441_e);
      if (currentDimension != prevDimension) {
         currentRegion = (FactionRegion)this.currentLoadedFactions.getRegions().get(0);
      }

      if (currentRegion != prevRegion) {
         alignData.setRegionLastViewedFaction(prevRegion, this.currentFaction);
         lastViewedRegions.put(prevRegion, this.currentFaction);
         currentFactionList = this.currentLoadedFactions.getFactionsForRegion(currentRegion);
         this.currentFaction = alignData.getRegionLastViewedFaction(currentRegion);
         this.prevFactionIndex = this.currentFactionIndex = currentFactionList.indexOf(this.currentFaction);
      }

      prevDimension = currentDimension;
      prevRegion = currentRegion;
      Faction prevFaction = alignData.getCurrentViewedFaction();
      boolean changes = this.currentFaction != prevFaction;
      if (changes) {
         alignData.setCurrentViewedFaction(this.currentFaction);
         lastViewedRegions.forEach(alignData::setRegionLastViewedFaction);
         alignData.sendViewedFactionsToServer();
         this.isPledging = false;
         this.isUnpledging = false;
      }

   }

   private boolean useFullPageTexture() {
      return this.isPledging || this.isUnpledging || currentPage == MiddleEarthFactionsScreen.Page.RANKS;
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float tick) {
      LOTRPlayerData clientPD = this.getClientPlayerData();
      AlignmentDataModule alignData = this.getClientPlayerData().getAlignmentData();
      boolean mouseOverAlignLock = false;
      boolean mouseOverCivilianKills = false;
      if (!this.isPledging && !this.isUnpledging) {
         this.buttonPagePrev.field_230693_o_ = currentPage.prev() != null;
         this.buttonPageNext.field_230693_o_ = currentPage.next() != null;
         this.buttonFactionMap.field_230694_p_ = this.buttonFactionMap.field_230693_o_ = currentPage != MiddleEarthFactionsScreen.Page.RANKS && this.currentFaction != null && this.currentFaction.getMapSquare() != null && LOTRDimensions.getCurrentLOTRDimensionOrFallback(this.field_230706_i_.field_71441_e).equals(this.currentFaction.getDimension());
         if (!AreasOfInfluence.areAreasOfInfluenceEnabled(this.field_230706_i_.field_71441_e)) {
            this.buttonFactionMap.field_230694_p_ = this.buttonFactionMap.field_230693_o_ = false;
         }

         boolean isRanksPage = currentPage == MiddleEarthFactionsScreen.Page.RANKS;
         this.buttonPreferredRanksMasc.field_230694_p_ = this.buttonPreferredRanksMasc.field_230693_o_ = isRanksPage;
         this.buttonPreferredRanksFem.field_230694_p_ = this.buttonPreferredRanksFem.field_230693_o_ = isRanksPage;
         this.buttonToggleFriendlyFire.field_230694_p_ = this.buttonToggleFriendlyFire.field_230693_o_ = true;
         if (!this.isOtherPlayer && currentPage == MiddleEarthFactionsScreen.Page.OVERVIEW) {
            if (alignData.isPledgedTo(this.currentFaction)) {
               this.buttonOpenPledgeScreen.setDisplayAsBroken(this.buttonOpenPledgeScreen.func_230449_g_());
               this.buttonOpenPledgeScreen.field_230694_p_ = this.buttonOpenPledgeScreen.field_230693_o_ = true;
               this.buttonOpenPledgeScreen.setTooltipLines(new TranslationTextComponent("gui.lotr.factions.unpledge"));
            } else {
               this.buttonOpenPledgeScreen.setDisplayAsBroken(false);
               this.buttonOpenPledgeScreen.field_230694_p_ = alignData.getPledgeFaction() == null && this.currentFaction.isPlayableAlignmentFaction() && alignData.getAlignment(this.currentFaction) >= 0.0F;
               this.buttonOpenPledgeScreen.field_230693_o_ = this.buttonOpenPledgeScreen.field_230694_p_ && alignData.hasPledgeAlignment(this.currentFaction);
               ITextComponent desc1 = new TranslationTextComponent("gui.lotr.factions.pledge");
               ITextComponent desc2 = new TranslationTextComponent("gui.lotr.factions.pledge.req", new Object[]{AlignmentFormatter.formatAlignForDisplay(this.currentFaction.getPledgeAlignment())});
               this.buttonOpenPledgeScreen.setTooltipLines(desc1, desc2);
            }
         } else {
            this.buttonOpenPledgeScreen.field_230694_p_ = this.buttonOpenPledgeScreen.field_230693_o_ = false;
         }

         this.buttonPledgeConfirm.field_230694_p_ = this.buttonPledgeConfirm.field_230693_o_ = false;
         this.buttonPledgeRevoke.field_230694_p_ = this.buttonPledgeRevoke.field_230693_o_ = false;
      } else {
         this.buttonPagePrev.field_230693_o_ = false;
         this.buttonPageNext.field_230693_o_ = false;
         this.buttonFactionMap.field_230694_p_ = this.buttonFactionMap.field_230693_o_ = false;
         this.buttonPreferredRanksMasc.field_230694_p_ = this.buttonPreferredRanksMasc.field_230693_o_ = false;
         this.buttonPreferredRanksFem.field_230694_p_ = this.buttonPreferredRanksFem.field_230693_o_ = false;
         this.buttonToggleFriendlyFire.field_230694_p_ = this.buttonToggleFriendlyFire.field_230693_o_ = true;
         this.buttonOpenPledgeScreen.field_230694_p_ = this.buttonOpenPledgeScreen.field_230693_o_ = false;
         if (this.isPledging) {
            this.buttonPledgeConfirm.field_230694_p_ = true;
            this.buttonPledgeConfirm.field_230693_o_ = alignData.canMakeNewPledge() && alignData.canPledgeToNow(this.currentFaction);
            this.buttonPledgeConfirm.setTooltipLines(new TranslationTextComponent("gui.lotr.factions.pledge"));
            this.buttonPledgeRevoke.field_230694_p_ = this.buttonPledgeRevoke.field_230693_o_ = false;
         } else if (this.isUnpledging) {
            this.buttonPledgeConfirm.field_230694_p_ = this.buttonPledgeConfirm.field_230693_o_ = false;
            this.buttonPledgeRevoke.field_230694_p_ = this.buttonPledgeRevoke.field_230693_o_ = true;
            this.buttonPledgeRevoke.setTooltipLines(new TranslationTextComponent("gui.lotr.factions.unpledge"));
         }
      }

      this.processFactionScrollBar(mouseX, mouseY);
      this.func_230446_a_(matStack);
      if (this.useFullPageTexture()) {
         this.field_230706_i_.func_110434_K().func_110577_a(FACTIONS_TEXTURE_FULL);
      } else {
         this.field_230706_i_.func_110434_K().func_110577_a(FACTIONS_TEXTURE);
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_238474_b_(matStack, this.guiLeft, this.guiTop + this.pageY, 0, 0, this.pageWidth, this.pageHeight);
      ITextComponent title = new TranslationTextComponent("gui.lotr.factions.title", new Object[]{LOTRDimensions.getDisplayName(currentDimension)});
      if (this.isOtherPlayer) {
         title = new TranslationTextComponent("gui.lotr.factions.title", new Object[]{this.otherPlayerName});
      }

      this.field_230712_o_.func_243248_b(matStack, title, (float)(this.guiLeft + this.xSize / 2 - this.field_230712_o_.func_238414_a_(title) / 2), (float)(this.guiTop - 30), 16777215);
      if (currentRegion != null && this.currentLoadedFactions.getRegionsForDimension(currentDimension).size() > 1) {
         this.buttonRegions.func_238482_a_(currentRegion.getDisplayName());
         this.buttonRegions.field_230694_p_ = this.buttonRegions.field_230693_o_ = true;
      } else {
         this.buttonRegions.func_238482_a_(StringTextComponent.field_240750_d_);
         this.buttonRegions.field_230694_p_ = this.buttonRegions.field_230693_o_ = false;
      }

      float zoom;
      int x;
      TranslationTextComponent alignmentInfo;
      float conq;
      if (this.currentFaction != null) {
         float alignment;
         if (this.isOtherPlayer && this.otherPlayerAlignmentMap != null) {
            alignment = (Float)this.otherPlayerAlignmentMap.get(this.currentFaction);
         } else {
            alignment = alignData.getAlignment(this.currentFaction);
         }

         x = this.guiLeft + this.xSize / 2;
         int y = this.guiTop;
         this.alignmentRenderer.renderAlignmentBar(matStack, this.field_230706_i_, alignment, this.isOtherPlayer, this.currentFaction, (float)x, (float)y, true, false, true, true);
         this.field_230712_o_.getClass();
         y += 9 + 22;
         this.drawCenteredStringNoShadow(matStack, this.field_230712_o_, this.currentFaction.getDisplaySubtitle(), x, y, 16777215);
         this.field_230712_o_.getClass();
         int var10000 = y + 9 * 3;
         int index;
         int px;
         int py;
         if (!this.useFullPageTexture()) {
            MapSquare mapSquare = this.currentFaction.getMapSquare();
            int wcX;
            if (mapSquare != null) {
               wcX = mapSquare.mapX;
               index = mapSquare.mapZ;
               int mapR = mapSquare.radius;
               int xMin = this.guiLeft + this.pageMapX;
               int xMax = xMin + this.pageMapSize;
               px = this.guiTop + this.pageY + this.pageMapY;
               py = px + this.pageMapSize;
               int mapBorder = 1;
               func_238467_a_(matStack, xMin - mapBorder, px - mapBorder, xMax + mapBorder, py + mapBorder, -16777216);
               zoom = (float)this.pageMapSize / (float)(mapR * 2);
               float zoomExp = (float)Math.log((double)zoom) / (float)Math.log(2.0D);
               this.mapDrawGui.setMapViewportAndPositionAndScale(xMin, xMax, px, py, (double)wcX, (double)index, zoom, zoomExp, zoom);
               this.mapDrawGui.enableZoomOutObjectFading = false;
               boolean sepia = (Boolean)LOTRConfig.CLIENT.sepiaMap.get();
               this.mapDrawGui.renderMapAndOverlay(matStack, tick, sepia, 1.0F, true);
            }

            wcX = this.guiLeft + this.pageMapX + 3;
            index = this.guiTop + this.pageY + this.pageMapY + this.pageMapSize + 5;
            int wcWidth = 8;
            this.field_230706_i_.func_110434_K().func_110577_a(FACTIONS_TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (this.currentFaction.approvesCivilianKills()) {
               this.func_238474_b_(matStack, wcX, index, 33, 142, wcWidth, wcWidth);
            } else {
               this.func_238474_b_(matStack, wcX, index, 41, 142, wcWidth, wcWidth);
            }

            if (mouseX >= wcX && mouseX < wcX + wcWidth && mouseY >= index && mouseY < index + wcWidth) {
               mouseOverCivilianKills = true;
            }
         }

         x = this.guiLeft + this.pageBorderLeft;
         y = this.guiTop + this.pageY + this.pageBorderTop;
         int avgBgColor;
         TranslationTextComponent desc1;
         Object listObj;
         if (!this.isPledging && !this.isUnpledging) {
            TranslationTextComponent pledgeText;
            if (currentPage == MiddleEarthFactionsScreen.Page.OVERVIEW) {
               if (this.isOtherPlayer) {
                  alignmentInfo = new TranslationTextComponent("gui.lotr.factions.overview.otherPlayer", new Object[]{this.otherPlayerName});
                  this.field_230712_o_.func_243248_b(matStack, alignmentInfo, (float)x, (float)y, 8019267);
                  this.field_230712_o_.getClass();
                  y += 9 * 2;
               }

               alignmentInfo = new TranslationTextComponent("gui.lotr.factions.alignment");
               this.field_230712_o_.func_243248_b(matStack, alignmentInfo, (float)x, (float)y, 8019267);
               x += this.field_230712_o_.func_238414_a_(alignmentInfo) + 5;
               String alignmentString = AlignmentFormatter.formatAlignForDisplay(alignment);
               this.alignmentTextRenderer.drawAlignmentText(matStack, this.field_230712_o_, x, y, new StringTextComponent(alignmentString), 1.0F);
               if (alignData.isPledgeEnemyAlignmentLimited(this.currentFaction)) {
                  this.field_230706_i_.func_110434_K().func_110577_a(FACTIONS_TEXTURE);
                  RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                  index = x + this.field_230712_o_.func_78256_a(alignmentString) + 5;
                  int lockWidth = 16;
                  this.func_238474_b_(matStack, index, y, 0, 200, lockWidth, lockWidth);
                  if (mouseX >= index && mouseX < index + lockWidth && mouseY >= y && mouseY < y + lockWidth) {
                     mouseOverAlignLock = true;
                  }
               }

               this.field_230712_o_.getClass();
               y += 9;
               x = this.guiLeft + this.pageBorderLeft;
               FactionRank curRank = this.currentFaction.getRankFor(alignment);
               desc1 = new TranslationTextComponent("gui.lotr.factions.alignment.rank", new Object[]{curRank.getDisplayFullName(clientPD.getMiscData().getPreferredRankGender())});
               this.field_230712_o_.func_243248_b(matStack, desc1, (float)x, (float)y, 8019267);
               this.field_230712_o_.getClass();
               y += 9 * 2;
               if (!this.isOtherPlayer) {
                  FactionStats factionStats = clientPD.getFactionStatsData().getFactionStats(this.currentFaction);
                  if (alignment >= 0.0F) {
                     pledgeText = new TranslationTextComponent("gui.lotr.factions.stats.enemiesKilled", new Object[]{factionStats.getEnemiesKilled()});
                     this.field_230712_o_.func_243248_b(matStack, pledgeText, (float)x, (float)y, 8019267);
                     this.field_230712_o_.getClass();
                     y += 9;
                     pledgeText = new TranslationTextComponent("gui.lotr.factions.stats.trades", new Object[]{factionStats.getTradeCount()});
                     this.field_230712_o_.func_243248_b(matStack, pledgeText, (float)x, (float)y, 8019267);
                     this.field_230712_o_.getClass();
                     y += 9;
                     pledgeText = new TranslationTextComponent("gui.lotr.factions.stats.hires", new Object[]{factionStats.getHireCount()});
                     this.field_230712_o_.func_243248_b(matStack, pledgeText, (float)x, (float)y, 8019267);
                     this.field_230712_o_.getClass();
                     y += 9;
                     pledgeText = new TranslationTextComponent("gui.lotr.factions.stats.miniquests", new Object[]{factionStats.getMiniQuestsCompleted()});
                     this.field_230712_o_.func_243248_b(matStack, pledgeText, (float)x, (float)y, 8019267);
                     this.field_230712_o_.getClass();
                     y += 9;
                     if (alignData.isPledgedTo(this.currentFaction)) {
                        conq = factionStats.getConquestEarned();
                        if (conq != 0.0F) {
                           py = Math.round(conq);
                           pledgeText = new TranslationTextComponent("gui.lotr.factions.stats.conquest", new Object[]{py});
                           this.field_230712_o_.func_243248_b(matStack, pledgeText, (float)x, (float)y, 8019267);
                           this.field_230712_o_.getClass();
                           y += 9;
                        }
                     }
                  }

                  if (alignment <= 0.0F) {
                     pledgeText = new TranslationTextComponent("gui.lotr.factions.stats.membersKilled", new Object[]{factionStats.getMembersKilled()});
                     this.field_230712_o_.func_243248_b(matStack, pledgeText, (float)x, (float)y, 8019267);
                     this.field_230712_o_.getClass();
                     var10000 = y + 9;
                  }

                  if (this.buttonOpenPledgeScreen.field_230694_p_ && alignData.isPledgedTo(this.currentFaction)) {
                     pledgeText = new TranslationTextComponent("gui.lotr.factions.pledged");
                     px = this.buttonOpenPledgeScreen.field_230690_l_ + this.buttonOpenPledgeScreen.func_230998_h_() + 8;
                     var10000 = this.buttonOpenPledgeScreen.field_230691_m_ + this.buttonOpenPledgeScreen.func_238483_d_() / 2;
                     this.field_230712_o_.getClass();
                     py = var10000 - 9 / 2;
                     this.field_230712_o_.func_243248_b(matStack, pledgeText, (float)px, (float)py, 16711680);
                  }
               }
            } else {
               int[] minMax;
               if (currentPage == MiddleEarthFactionsScreen.Page.RANKS) {
                  FactionRank curRank = this.currentFaction.getRankFor(clientPD);
                  minMax = this.scrollPaneAlliesEnemies.getMinMaxIndices(this.currentAlliesEnemies, this.numDisplayedAlliesEnemies);

                  for(index = minMax[0]; index <= minMax[1]; ++index) {
                     listObj = this.currentAlliesEnemies.get(index);
                     if (listObj instanceof String) {
                        String s = (String)listObj;
                        this.field_230712_o_.func_243248_b(matStack, new StringTextComponent(s), (float)x, (float)y, 8019267);
                     } else if (listObj instanceof FactionRank) {
                        FactionRank rank = (FactionRank)listObj;
                        ITextComponent rankName = new StringTextComponent(rank.getDisplayShortName(clientPD.getMiscData().getPreferredRankGender()));
                        String rankAlign = AlignmentFormatter.formatAlignForDisplay(rank.getAlignment());
                        if (rank.isNameEqual("enemy")) {
                           rankAlign = "-";
                        }

                        boolean hiddenRankName = false;
                        if (!alignData.isPledgedTo(this.currentFaction) && rank.getAlignment() > this.currentFaction.getPledgeAlignment() && rank.getAlignment() > this.currentFaction.getRankAbove(curRank).getAlignment()) {
                           hiddenRankName = true;
                        }

                        if (hiddenRankName) {
                           rankName = new TranslationTextComponent("gui.lotr.factions.rank.unknown");
                        }

                        ITextComponent listRank = new TranslationTextComponent("gui.lotr.factions.listRank", new Object[]{rankName, rankAlign});
                        if (rank == curRank) {
                           this.alignmentTextRenderer.drawAlignmentText(matStack, this.field_230712_o_, x, y, listRank, 1.0F);
                        } else {
                           this.field_230712_o_.func_243248_b(matStack, listRank, (float)x, (float)y, 8019267);
                        }
                     }

                     this.field_230712_o_.getClass();
                     y += 9;
                  }
               } else if (currentPage == MiddleEarthFactionsScreen.Page.GOOD_RELATIONS || currentPage == MiddleEarthFactionsScreen.Page.BAD_RELATIONS) {
                  avgBgColor = LOTRClientUtil.computeAverageFactionPageColor(this.field_230706_i_, FACTIONS_TEXTURE, 20, 20, 120, 80);
                  minMax = this.scrollPaneAlliesEnemies.getMinMaxIndices(this.currentAlliesEnemies, this.numDisplayedAlliesEnemies);

                  for(index = minMax[0]; index <= minMax[1]; ++index) {
                     listObj = this.currentAlliesEnemies.get(index);
                     if (listObj instanceof FactionRelation) {
                        FactionRelation rel = (FactionRelation)listObj;
                        pledgeText = new TranslationTextComponent("gui.lotr.factions.relationHeader", new Object[]{rel.getDisplayName()});
                        this.field_230712_o_.func_243248_b(matStack, pledgeText, (float)x, (float)y, 8019267);
                     } else if (listObj instanceof Faction) {
                        Faction fac = (Faction)listObj;
                        pledgeText = new TranslationTextComponent("gui.lotr.factions.list", new Object[]{fac.getDisplayName()});
                        this.field_230712_o_.func_243248_b(matStack, pledgeText, (float)x, (float)y, LOTRClientUtil.findContrastingColor(fac.getColor(), avgBgColor));
                     }

                     this.field_230712_o_.getClass();
                     y += 9;
                  }
               }
            }

            if (this.scrollPaneAlliesEnemies.hasScrollBar) {
               this.scrollPaneAlliesEnemies.drawScrollBar(matStack);
            }
         } else {
            avgBgColor = this.pageWidth - this.pageBorderLeft * 2;
            List displayLines = new ArrayList();
            if (this.isPledging) {
               List facsPreventingPledge = alignData.getFactionsPreventingPledgeTo(this.currentFaction);
               TranslationTextComponent desc2;
               if (facsPreventingPledge.isEmpty()) {
                  if (alignData.canMakeNewPledge()) {
                     if (alignData.canPledgeToNow(this.currentFaction)) {
                        desc1 = new TranslationTextComponent("gui.lotr.factions.pledge.desc.1", new Object[]{this.currentFaction.getDisplayName()});
                        displayLines.addAll(this.field_230712_o_.func_238425_b_(desc1, avgBgColor));
                        displayLines.add(IReorderingProcessor.field_242232_a);
                        desc2 = new TranslationTextComponent("gui.lotr.factions.pledge.desc.2");
                        displayLines.addAll(this.field_230712_o_.func_238425_b_(desc2, avgBgColor));
                     }
                  } else {
                     ITextComponent brokenPledgeName = Faction.getFactionOrUnknownDisplayName(alignData.getBrokenPledgeFaction());
                     desc2 = new TranslationTextComponent("gui.lotr.factions.pledge.breakCooldown", new Object[]{this.currentFaction.getDisplayName(), brokenPledgeName});
                     displayLines.addAll(this.field_230712_o_.func_238425_b_(desc2, avgBgColor));
                     displayLines.add(IReorderingProcessor.field_242232_a);
                     RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                     this.field_230706_i_.func_110434_K().func_110577_a(FACTIONS_TEXTURE);
                     this.func_238474_b_(matStack, this.guiLeft + this.pageWidth / 2 - 97, this.guiTop + this.pageY + 56, 0, 240, 194, 16);
                     this.func_238474_b_(matStack, this.guiLeft + this.pageWidth / 2 - 75, this.guiTop + this.pageY + 60, 22, 232, MathHelper.func_76123_f(alignData.getPledgeBreakCooldownFraction() * 150.0F), 8);
                  }
               } else {
                  alignData.getClass();
                  Collections.sort(facsPreventingPledge, Comparator.comparing(alignData::getAlignment).reversed());
                  listObj = new StringTextComponent("If you are reading this, something has gone hideously wrong.");
                  if (facsPreventingPledge.size() == 1) {
                     listObj = new TranslationTextComponent("gui.lotr.factions.pledge.enemies.1", new Object[]{((Faction)facsPreventingPledge.get(0)).getDisplayName()});
                  } else if (facsPreventingPledge.size() == 2) {
                     listObj = new TranslationTextComponent("gui.lotr.factions.pledge.enemies.2", new Object[]{((Faction)facsPreventingPledge.get(0)).getDisplayName(), ((Faction)facsPreventingPledge.get(1)).getDisplayName()});
                  } else if (facsPreventingPledge.size() == 3) {
                     listObj = new TranslationTextComponent("gui.lotr.factions.pledge.enemies.3", new Object[]{((Faction)facsPreventingPledge.get(0)).getDisplayName(), ((Faction)facsPreventingPledge.get(1)).getDisplayName(), ((Faction)facsPreventingPledge.get(2)).getDisplayName()});
                  } else if (facsPreventingPledge.size() > 3) {
                     listObj = new TranslationTextComponent("gui.lotr.factions.pledge.enemies.3+", new Object[]{((Faction)facsPreventingPledge.get(0)).getDisplayName(), ((Faction)facsPreventingPledge.get(1)).getDisplayName(), ((Faction)facsPreventingPledge.get(2)).getDisplayName(), facsPreventingPledge.size() - 3});
                  }

                  desc2 = new TranslationTextComponent("gui.lotr.factions.pledge.enemies", new Object[]{this.currentFaction.getDisplayName(), listObj});
                  displayLines.addAll(this.field_230712_o_.func_238425_b_(desc2, avgBgColor));
                  displayLines.add(IReorderingProcessor.field_242232_a);
               }
            } else if (this.isUnpledging) {
               ITextComponent desc1 = new TranslationTextComponent("gui.lotr.factions.unpledge.desc.1", new Object[]{this.currentFaction.getDisplayName()});
               displayLines.addAll(this.field_230712_o_.func_238425_b_(desc1, avgBgColor));
               displayLines.add(IReorderingProcessor.field_242232_a);
               desc1 = new TranslationTextComponent("gui.lotr.factions.unpledge.desc.2");
               displayLines.addAll(this.field_230712_o_.func_238425_b_(desc1, avgBgColor));
            }

            for(Iterator var44 = displayLines.iterator(); var44.hasNext(); y += 9) {
               IReorderingProcessor line = (IReorderingProcessor)var44.next();
               this.field_230712_o_.func_238422_b_(matStack, line, (float)x, (float)y, 8019267);
               this.field_230712_o_.getClass();
            }
         }
      }

      if (this.hasScrollBar()) {
         this.field_230706_i_.func_110434_K().func_110577_a(FACTIONS_TEXTURE);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_238474_b_(matStack, this.guiLeft + this.scrollBarX, this.guiTop + this.scrollBarY, 0, 128, this.scrollBarWidth, this.scrollBarHeight);
         int factions = currentFactionList.size();

         for(x = 0; x < factions; ++x) {
            Faction faction = (Faction)currentFactionList.get(x);
            float[] factionColors = faction.getColorComponents();
            float shade = 0.6F;
            RenderSystem.color4f(factionColors[0] * shade, factionColors[1] * shade, factionColors[2] * shade, 1.0F);
            float fracMin = (float)x / (float)factions;
            float fracMax = (float)(x + 1) / (float)factions;
            float uMin = (float)this.scrollBarBorder + fracMin * (float)(this.scrollBarWidth - this.scrollBarBorder * 2);
            float uMax = (float)this.scrollBarBorder + fracMax * (float)(this.scrollBarWidth - this.scrollBarBorder * 2);
            conq = (float)(this.guiLeft + this.scrollBarX) + uMin;
            float xMax = (float)(this.guiLeft + this.scrollBarX) + uMax;
            float yMin = (float)(this.guiTop + this.scrollBarY + this.scrollBarBorder);
            zoom = (float)(this.guiTop + this.scrollBarY + this.scrollBarHeight - this.scrollBarBorder);
            LOTRClientUtil.blitFloat(this, matStack, conq, yMin, 0.0F + uMin, (float)(128 + this.scrollBarBorder), xMax - conq, zoom - yMin);
         }

         this.field_230706_i_.func_110434_K().func_110577_a(FACTIONS_TEXTURE);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         if (this.canScroll()) {
            x = (int)(this.currentScroll * (float)(this.scrollBarWidth - this.scrollBarBorder * 2 - this.scrollWidgetWidth));
            this.func_238474_b_(matStack, this.guiLeft + this.scrollBarX + this.scrollBarBorder + x, this.guiTop + this.scrollBarY + this.scrollBarBorder, 0, 142, this.scrollWidgetWidth, this.scrollWidgetHeight);
         }
      }

      super.func_230430_a_(matStack, mouseX, mouseY, tick);
      this.renderButtonTooltipIfHovered(matStack, this.buttonFactionMap, new TranslationTextComponent("gui.lotr.factions.viewMap"), mouseX, mouseY);
      MiddleEarthFactionsScreen.Page prevPage = currentPage.prev();
      MiddleEarthFactionsScreen.Page nextPage = currentPage.next();
      if (prevPage != null) {
         this.renderButtonTooltipIfHovered(matStack, this.buttonPagePrev, prevPage.getDisplayName(), mouseX, mouseY);
      }

      if (nextPage != null) {
         this.renderButtonTooltipIfHovered(matStack, this.buttonPageNext, nextPage.getDisplayName(), mouseX, mouseY);
      }

      this.renderButtonTooltipIfHovered(matStack, this.buttonPreferredRanksMasc, new TranslationTextComponent("gui.lotr.factions.rankGender.masc"), mouseX, mouseY);
      this.renderButtonTooltipIfHovered(matStack, this.buttonPreferredRanksFem, new TranslationTextComponent("gui.lotr.factions.rankGender.fem"), mouseX, mouseY);
      this.renderButtonTooltipIfHovered(matStack, this.buttonToggleFriendlyFire, FriendlyFireToggleButton.getTooltipLines(), mouseX, mouseY);
      if (mouseOverAlignLock) {
         String alignLimit = AlignmentFormatter.formatAlignForDisplay(alignData.getPledgeEnemyAlignmentLimit(this.currentFaction));
         alignmentInfo = new TranslationTextComponent("gui.lotr.factions.pledgeLocked", new Object[]{alignLimit, alignData.getPledgeFaction().getDisplayName()});
         int stringWidth = 200;
         this.func_238654_b_(matStack, this.field_230712_o_.func_238425_b_(alignmentInfo, stringWidth), mouseX, mouseY);
      }

      if (mouseOverCivilianKills) {
         ITextComponent civilianKills = new TranslationTextComponent(this.currentFaction.approvesCivilianKills() ? "gui.lotr.factions.civilianKills.yes" : "gui.lotr.factions.civilianKills.no");
         int stringWidth = 200;
         this.func_238654_b_(matStack, this.field_230712_o_.func_238425_b_(civilianKills, stringWidth), mouseX, mouseY);
      }

   }

   private boolean hasScrollBar() {
      return currentFactionList.size() > 1;
   }

   private boolean canScroll() {
      return true;
   }

   private void processFactionScrollBar(int mouseX, int mouseY) {
      int i1 = this.guiLeft + this.scrollBarX;
      int j1 = this.guiTop + this.scrollBarY;
      int i2 = i1 + this.scrollBarWidth;
      int j2 = j1 + this.scrollBarHeight;
      if (!this.wasMouseDown && this.isMouseDown && mouseX >= i1 && mouseY >= j1 && mouseX < i2 && mouseY < j2) {
         this.isScrolling = this.canScroll();
      }

      if (!this.isMouseDown) {
         this.isScrolling = false;
      }

      this.wasMouseDown = this.isMouseDown;
      if (this.isScrolling) {
         this.currentScroll = ((float)(mouseX - i1) - (float)this.scrollWidgetWidth / 2.0F) / ((float)(i2 - i1) - (float)this.scrollWidgetWidth);
         this.currentScroll = MathHelper.func_76131_a(this.currentScroll, 0.0F, 1.0F);
         this.currentFactionIndex = Math.round(this.currentScroll * (float)(currentFactionList.size() - 1));
         this.scrollPaneAlliesEnemies.resetScroll();
      }

      if (currentPage != MiddleEarthFactionsScreen.Page.GOOD_RELATIONS && currentPage != MiddleEarthFactionsScreen.Page.BAD_RELATIONS && currentPage != MiddleEarthFactionsScreen.Page.RANKS) {
         this.scrollPaneAlliesEnemies.hasScrollBar = false;
         this.scrollPaneAlliesEnemies.mouseDragScroll(mouseX, mouseY, this.isMouseDown);
      } else {
         List mortals;
         List enemies;
         if (currentPage == MiddleEarthFactionsScreen.Page.GOOD_RELATIONS) {
            this.currentAlliesEnemies = new ArrayList();
            mortals = this.currentFaction.getOthersOfRelation(FactionRelation.ALLY);
            if (!mortals.isEmpty()) {
               this.currentAlliesEnemies.add(FactionRelation.ALLY);
               this.currentAlliesEnemies.addAll(mortals);
            }

            enemies = this.currentFaction.getOthersOfRelation(FactionRelation.FRIEND);
            if (!enemies.isEmpty()) {
               if (!this.currentAlliesEnemies.isEmpty()) {
                  this.currentAlliesEnemies.add((Object)null);
               }

               this.currentAlliesEnemies.add(FactionRelation.FRIEND);
               this.currentAlliesEnemies.addAll(enemies);
            }
         } else if (currentPage == MiddleEarthFactionsScreen.Page.BAD_RELATIONS) {
            this.currentAlliesEnemies = new ArrayList();
            mortals = this.currentFaction.getOthersOfRelation(FactionRelation.MORTAL_ENEMY);
            if (!mortals.isEmpty()) {
               this.currentAlliesEnemies.add(FactionRelation.MORTAL_ENEMY);
               this.currentAlliesEnemies.addAll(mortals);
            }

            enemies = this.currentFaction.getOthersOfRelation(FactionRelation.ENEMY);
            if (!enemies.isEmpty()) {
               if (!this.currentAlliesEnemies.isEmpty()) {
                  this.currentAlliesEnemies.add((Object)null);
               }

               this.currentAlliesEnemies.add(FactionRelation.ENEMY);
               this.currentAlliesEnemies.addAll(enemies);
            }
         } else if (currentPage == MiddleEarthFactionsScreen.Page.RANKS) {
            this.currentAlliesEnemies = new ArrayList();
            this.currentAlliesEnemies.add(new TranslationTextComponent("gui.lotr.factions.ranksHeader"));
            if (LOTRLevelData.clientInstance().getData(this.field_230706_i_.field_71439_g).getAlignmentData().getAlignment(this.currentFaction) <= 0.0F) {
               this.currentAlliesEnemies.add(this.currentFaction.getEnemyRank());
            }

            FactionRank rank = this.currentFaction.getNeutralRank();

            while(true) {
               this.currentAlliesEnemies.add(rank);
               FactionRank nextRank = this.currentFaction.getRankAbove(rank);
               if (nextRank == null || nextRank.isDummyRank() || this.currentAlliesEnemies.contains(nextRank)) {
                  break;
               }

               rank = nextRank;
            }
         }

         this.scrollPaneAlliesEnemies.hasScrollBar = false;
         this.numDisplayedAlliesEnemies = this.currentAlliesEnemies.size();
         if (this.numDisplayedAlliesEnemies > 10) {
            this.numDisplayedAlliesEnemies = 10;
            this.scrollPaneAlliesEnemies.hasScrollBar = true;
         }

         this.scrollPaneAlliesEnemies.paneX0 = this.guiLeft;
         this.scrollPaneAlliesEnemies.scrollBarX0 = this.guiLeft + this.scrollAlliesEnemiesX;
         ScrollPane var10000;
         if (currentPage == MiddleEarthFactionsScreen.Page.RANKS) {
            var10000 = this.scrollPaneAlliesEnemies;
            var10000.scrollBarX0 += 50;
         }

         this.scrollPaneAlliesEnemies.paneY0 = this.guiTop + this.pageY + this.pageBorderTop;
         var10000 = this.scrollPaneAlliesEnemies;
         int var10001 = this.scrollPaneAlliesEnemies.paneY0;
         this.field_230712_o_.getClass();
         var10000.paneY1 = var10001 + 9 * this.numDisplayedAlliesEnemies;
         this.scrollPaneAlliesEnemies.mouseDragScroll(mouseX, mouseY, this.isMouseDown);
      }

   }

   public boolean func_231046_a_(int key, int scan, int param3) {
      if (this.isEscapeOrInventoryKey(key, scan)) {
         if (this.isPledging) {
            this.isPledging = false;
            return true;
         }

         if (this.isUnpledging) {
            this.isUnpledging = false;
            return true;
         }

         if (this.isOtherPlayer) {
            this.field_230706_i_.field_71439_g.func_71053_j();
            return true;
         }
      }

      return super.func_231046_a_(key, scan, param3);
   }

   public boolean func_231044_a_(double x, double y, int code) {
      if (code == 0) {
         this.isMouseDown = true;
      }

      return super.func_231044_a_(x, y, code);
   }

   public boolean func_231048_c_(double x, double y, int code) {
      if (code == 0) {
         this.isMouseDown = false;
      }

      return super.func_231048_c_(x, y, code);
   }

   public boolean func_231043_a_(double x, double y, double scroll) {
      if (super.func_231043_a_(x, y, scroll)) {
         return true;
      } else if (scroll != 0.0D) {
         if (this.scrollPaneAlliesEnemies.hasScrollBar && this.scrollPaneAlliesEnemies.mouseOver) {
            int l = this.currentAlliesEnemies.size() - this.numDisplayedAlliesEnemies;
            this.scrollPaneAlliesEnemies.mouseWheelScroll(scroll, l);
            return true;
         } else {
            if (scroll < 0.0D) {
               this.currentFactionIndex = Math.min(this.currentFactionIndex + 1, Math.max(0, currentFactionList.size() - 1));
            }

            if (scroll > 0.0D) {
               this.currentFactionIndex = Math.max(this.currentFactionIndex - 1, 0);
            }

            this.setCurrentScrollFromFaction();
            this.scrollPaneAlliesEnemies.resetScroll();
            this.isPledging = false;
            this.isUnpledging = false;
            return true;
         }
      } else {
         return false;
      }
   }

   private void setCurrentScrollFromFaction() {
      this.currentScroll = (float)this.currentFactionIndex / (float)(currentFactionList.size() - 1);
   }

   static {
      currentPage = MiddleEarthFactionsScreen.Page.OVERVIEW;
   }

   public static enum Page {
      OVERVIEW("overview"),
      RANKS("ranks"),
      GOOD_RELATIONS("goodRelations"),
      BAD_RELATIONS("badRelations");

      private final String pageName;

      private Page(String name) {
         this.pageName = name;
      }

      public ITextComponent getDisplayName() {
         return new TranslationTextComponent("gui.lotr.factions.page." + this.pageName);
      }

      public MiddleEarthFactionsScreen.Page prev() {
         int i = this.ordinal();
         if (i == 0) {
            return null;
         } else {
            --i;
            return values()[i];
         }
      }

      public MiddleEarthFactionsScreen.Page next() {
         int i = this.ordinal();
         if (i == values().length - 1) {
            return null;
         } else {
            ++i;
            return values()[i];
         }
      }
   }
}
