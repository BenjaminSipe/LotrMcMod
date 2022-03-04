package lotr.client.gui;

import com.google.common.math.IntMath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lotr.client.LOTRClientProxy;
import lotr.client.LOTRTextures;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRConfig;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionData;
import lotr.common.fac.LOTRFactionRank;
import lotr.common.fac.LOTRFactionRelations;
import lotr.common.fac.LOTRMapRegion;
import lotr.common.network.LOTRPacketClientMQEvent;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketPledgeSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LOTRGuiFactions extends LOTRGuiMenuBase {
   public static final ResourceLocation factionsTexture = new ResourceLocation("lotr:gui/factions.png");
   public static final ResourceLocation factionsTextureFull = new ResourceLocation("lotr:gui/factions_full.png");
   private static LOTRDimension currentDimension;
   private static LOTRDimension prevDimension;
   private static LOTRDimension.DimensionRegion currentRegion;
   private static LOTRDimension.DimensionRegion prevRegion;
   private static List currentFactionList;
   private int currentFactionIndex = 0;
   private int prevFactionIndex = 0;
   private LOTRFaction currentFaction;
   private static final int maxAlignmentsDisplayed = 1;
   private static LOTRGuiFactions.Page currentPage;
   private int pageY = 46;
   private int pageWidth = 256;
   private int pageHeight = 128;
   private int pageBorderLeft = 16;
   private int pageBorderTop = 12;
   private int pageMapX = 159;
   private int pageMapY = 22;
   private int pageMapSize = 80;
   private LOTRGuiMap mapDrawGui;
   private GuiButton buttonRegions;
   private GuiButton buttonPagePrev;
   private GuiButton buttonPageNext;
   private GuiButton buttonFactionMap;
   private LOTRGuiButtonPledge buttonPledge;
   private LOTRGuiButtonPledge buttonPledgeConfirm;
   private LOTRGuiButtonPledge buttonPledgeRevoke;
   private float currentScroll;
   private boolean isScrolling;
   private boolean wasMouseDown;
   private int scrollBarWidth;
   private int scrollBarHeight;
   private int scrollBarX;
   private int scrollBarY;
   private int scrollBarBorder;
   private int scrollWidgetWidth;
   private int scrollWidgetHeight;
   private LOTRGuiScrollPane scrollPaneAlliesEnemies;
   private int scrollAlliesEnemiesX;
   private static final int maxDisplayedAlliesEnemies = 10;
   private int numDisplayedAlliesEnemies;
   private List currentAlliesEnemies;
   private boolean isOtherPlayer;
   private String otherPlayerName;
   private Map playerAlignmentMap;
   private boolean isPledging;
   private boolean isUnpledging;

   public LOTRGuiFactions() {
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
      this.scrollPaneAlliesEnemies = (new LOTRGuiScrollPane(7, 7)).setColors(5521198, 8019267);
      this.scrollAlliesEnemiesX = 138;
      this.isOtherPlayer = false;
      this.isPledging = false;
      this.isUnpledging = false;
      this.mapDrawGui = new LOTRGuiMap();
   }

   public void setOtherPlayer(String name, Map alignments) {
      this.isOtherPlayer = true;
      this.otherPlayerName = name;
      this.playerAlignmentMap = alignments;
   }

   public void func_146280_a(Minecraft mc, int i, int j) {
      super.func_146280_a(mc, i, j);
      this.mapDrawGui.func_146280_a(mc, i, j);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      if (this.isOtherPlayer) {
         this.field_146292_n.remove(this.buttonMenuReturn);
      }

      this.field_146292_n.add(this.buttonRegions = new LOTRGuiButtonRedBook(0, this.guiLeft + this.xSize / 2 - 60, this.guiTop + 200, 120, 20, ""));
      this.field_146292_n.add(this.buttonPagePrev = new LOTRGuiButtonFactionsPage(1, this.guiLeft + 8, this.guiTop + this.pageY + 104, false));
      this.field_146292_n.add(this.buttonPageNext = new LOTRGuiButtonFactionsPage(2, this.guiLeft + 232, this.guiTop + this.pageY + 104, true));
      this.field_146292_n.add(this.buttonFactionMap = new LOTRGuiButtonFactionsMap(3, this.guiLeft + this.pageMapX + this.pageMapSize - 3 - 8, this.guiTop + this.pageY + this.pageMapY + 3));
      this.field_146292_n.add(this.buttonPledge = new LOTRGuiButtonPledge(this, 4, this.guiLeft + 14, this.guiTop + this.pageY + this.pageHeight - 42, ""));
      this.field_146292_n.add(this.buttonPledgeConfirm = new LOTRGuiButtonPledge(this, 5, this.guiLeft + this.pageWidth / 2 - 16, this.guiTop + this.pageY + this.pageHeight - 44, ""));
      this.field_146292_n.add(this.buttonPledgeRevoke = new LOTRGuiButtonPledge(this, 6, this.guiLeft + this.pageWidth / 2 - 16, this.guiTop + this.pageY + this.pageHeight - 44, ""));
      this.buttonPledgeRevoke.isBroken = true;
      prevDimension = currentDimension = LOTRDimension.getCurrentDimensionWithFallback(this.field_146297_k.field_71441_e);
      this.currentFaction = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getViewingFaction();
      prevRegion = currentRegion = this.currentFaction.factionRegion;
      currentFactionList = currentRegion.factionList;
      this.prevFactionIndex = this.currentFactionIndex = currentFactionList.indexOf(this.currentFaction);
      this.setCurrentScrollFromFaction();
      if (this.field_146297_k.field_71462_r == this) {
         LOTRPacketClientMQEvent packet = new LOTRPacketClientMQEvent(LOTRPacketClientMQEvent.ClientMQEvent.FACTIONS);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }

   public void func_73876_c() {
      super.func_73876_c();
      this.updateCurrentDimensionAndFaction();
      LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g);
      if (this.isPledging && !playerData.hasPledgeAlignment(this.currentFaction)) {
         this.isPledging = false;
      }

      if (this.isUnpledging && !playerData.isPledgedTo(this.currentFaction)) {
         this.isUnpledging = false;
      }

   }

   private void updateCurrentDimensionAndFaction() {
      LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g);
      Map lastViewedRegions = new HashMap();
      if (this.currentFactionIndex != this.prevFactionIndex) {
         this.currentFaction = (LOTRFaction)currentFactionList.get(this.currentFactionIndex);
      }

      this.prevFactionIndex = this.currentFactionIndex;
      currentDimension = LOTRDimension.getCurrentDimensionWithFallback(this.field_146297_k.field_71441_e);
      if (currentDimension != prevDimension) {
         currentRegion = (LOTRDimension.DimensionRegion)currentDimension.dimensionRegions.get(0);
      }

      if (currentRegion != prevRegion) {
         pd.setRegionLastViewedFaction(prevRegion, this.currentFaction);
         lastViewedRegions.put(prevRegion, this.currentFaction);
         currentFactionList = currentRegion.factionList;
         this.currentFaction = pd.getRegionLastViewedFaction(currentRegion);
         this.prevFactionIndex = this.currentFactionIndex = currentFactionList.indexOf(this.currentFaction);
      }

      prevDimension = currentDimension;
      prevRegion = currentRegion;
      LOTRFaction prevFaction = pd.getViewingFaction();
      boolean changes = this.currentFaction != prevFaction;
      if (changes) {
         pd.setViewingFaction(this.currentFaction);
         LOTRClientProxy.sendClientInfoPacket(this.currentFaction, lastViewedRegions);
         this.isPledging = false;
         this.isUnpledging = false;
      }

   }

   private boolean useFullPageTexture() {
      return this.isPledging || this.isUnpledging || currentPage == LOTRGuiFactions.Page.RANKS;
   }

   public void func_73863_a(int i, int j, float f) {
      final LOTRPlayerData clientPD = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g);
      boolean mouseOverAlignLock = false;
      boolean mouseOverWarCrimes = false;
      String title;
      if (!this.isPledging && !this.isUnpledging) {
         this.buttonPagePrev.field_146124_l = currentPage.prev() != null;
         this.buttonPageNext.field_146124_l = currentPage.next() != null;
         this.buttonFactionMap.field_146125_m = this.buttonFactionMap.field_146124_l = currentPage != LOTRGuiFactions.Page.RANKS && this.currentFaction.isPlayableAlignmentFaction() && LOTRDimension.getCurrentDimensionWithFallback(this.field_146297_k.field_71441_e) == this.currentFaction.factionDimension;
         if (!LOTRFaction.controlZonesEnabled(this.field_146297_k.field_71441_e)) {
            this.buttonFactionMap.field_146125_m = this.buttonFactionMap.field_146124_l = false;
         }

         if (!this.isOtherPlayer && currentPage == LOTRGuiFactions.Page.FRONT) {
            if (clientPD.isPledgedTo(this.currentFaction)) {
               this.buttonPledge.isBroken = this.buttonPledge.func_146115_a();
               this.buttonPledge.field_146125_m = this.buttonPledge.field_146124_l = true;
               this.buttonPledge.setDisplayLines(StatCollector.func_74838_a("lotr.gui.factions.unpledge"));
            } else {
               this.buttonPledge.isBroken = false;
               this.buttonPledge.field_146125_m = clientPD.getPledgeFaction() == null && this.currentFaction.isPlayableAlignmentFaction() && clientPD.getAlignment(this.currentFaction) >= 0.0F;
               this.buttonPledge.field_146124_l = this.buttonPledge.field_146125_m && clientPD.hasPledgeAlignment(this.currentFaction);
               title = StatCollector.func_74838_a("lotr.gui.factions.pledge");
               String desc2 = StatCollector.func_74837_a("lotr.gui.factions.pledgeReq", new Object[]{LOTRAlignmentValues.formatAlignForDisplay(this.currentFaction.getPledgeAlignment())});
               this.buttonPledge.setDisplayLines(title, desc2);
            }
         } else {
            this.buttonPledge.field_146125_m = this.buttonPledge.field_146124_l = false;
         }

         this.buttonPledgeConfirm.field_146125_m = this.buttonPledgeConfirm.field_146124_l = false;
         this.buttonPledgeRevoke.field_146125_m = this.buttonPledgeRevoke.field_146124_l = false;
      } else {
         this.buttonPagePrev.field_146124_l = false;
         this.buttonPageNext.field_146124_l = false;
         this.buttonFactionMap.field_146125_m = this.buttonFactionMap.field_146124_l = false;
         this.buttonPledge.field_146125_m = this.buttonPledge.field_146124_l = false;
         if (this.isPledging) {
            this.buttonPledgeConfirm.field_146125_m = true;
            this.buttonPledgeConfirm.field_146124_l = clientPD.canMakeNewPledge() && clientPD.canPledgeTo(this.currentFaction);
            this.buttonPledgeConfirm.setDisplayLines(StatCollector.func_74838_a("lotr.gui.factions.pledge"));
            this.buttonPledgeRevoke.field_146125_m = this.buttonPledgeRevoke.field_146124_l = false;
         } else if (this.isUnpledging) {
            this.buttonPledgeConfirm.field_146125_m = this.buttonPledgeConfirm.field_146124_l = false;
            this.buttonPledgeRevoke.field_146125_m = this.buttonPledgeRevoke.field_146124_l = true;
            this.buttonPledgeRevoke.setDisplayLines(StatCollector.func_74838_a("lotr.gui.factions.unpledge"));
         }
      }

      this.setupScrollBar(i, j);
      this.func_146276_q_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.useFullPageTexture()) {
         this.field_146297_k.func_110434_K().func_110577_a(factionsTextureFull);
      } else {
         this.field_146297_k.func_110434_K().func_110577_a(factionsTexture);
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_73729_b(this.guiLeft, this.guiTop + this.pageY, 0, 0, this.pageWidth, this.pageHeight);
      title = StatCollector.func_74837_a("lotr.gui.factions.title", new Object[]{currentDimension.getDimensionName()});
      if (this.isOtherPlayer) {
         title = StatCollector.func_74837_a("lotr.gui.factions.titleOther", new Object[]{this.otherPlayerName});
      }

      this.field_146289_q.func_78276_b(title, this.guiLeft + this.xSize / 2 - this.field_146289_q.func_78256_a(title) / 2, this.guiTop - 30, 16777215);
      if (currentRegion != null && currentDimension.dimensionRegions.size() > 1) {
         this.buttonRegions.field_146126_j = currentRegion.getRegionName();
         this.buttonRegions.field_146125_m = this.buttonRegions.field_146124_l = true;
      } else {
         this.buttonRegions.field_146126_j = "";
         this.buttonRegions.field_146125_m = this.buttonRegions.field_146124_l = false;
      }

      int x;
      float alignment;
      float cdFrac;
      float conq;
      if (this.currentFaction != null) {
         if (this.isOtherPlayer && this.playerAlignmentMap != null) {
            alignment = (Float)this.playerAlignmentMap.get(this.currentFaction);
         } else {
            alignment = clientPD.getAlignment(this.currentFaction);
         }

         x = this.guiLeft + this.xSize / 2;
         int y = this.guiTop;
         LOTRTickHandlerClient.renderAlignmentBar(alignment, this.isOtherPlayer, this.currentFaction, (float)x, (float)y, true, false, true, true);
         y += this.field_146289_q.field_78288_b + 22;
         String s = this.currentFaction.factionSubtitle();
         this.drawCenteredString(s, x, y, 16777215);
         int var10000 = y + this.field_146289_q.field_78288_b * 3;
         int index;
         int px;
         int py;
         int avgBgColor;
         if (!this.useFullPageTexture()) {
            int wcY;
            if (this.currentFaction.factionMapInfo != null) {
               LOTRMapRegion mapInfo = this.currentFaction.factionMapInfo;
               wcY = mapInfo.mapX;
               index = mapInfo.mapY;
               int mapR = mapInfo.radius;
               int xMin = this.guiLeft + this.pageMapX;
               px = xMin + this.pageMapSize;
               py = this.guiTop + this.pageY + this.pageMapY;
               int yMax = py + this.pageMapSize;
               int mapBorder = 1;
               func_73734_a(xMin - mapBorder, py - mapBorder, px + mapBorder, yMax + mapBorder, -16777216);
               float zoom = (float)this.pageMapSize / (float)(mapR * 2);
               float zoomExp = (float)Math.log((double)zoom) / (float)Math.log(2.0D);
               this.mapDrawGui.setFakeMapProperties((float)wcY, (float)index, zoom, zoomExp, zoom);
               LOTRGuiMap var68 = this.mapDrawGui;
               int[] statics = LOTRGuiMap.setFakeStaticProperties(this.pageMapSize, this.pageMapSize, xMin, px, py, yMax);
               this.mapDrawGui.enableZoomOutWPFading = false;
               boolean sepia = LOTRConfig.enableSepiaMap;
               this.mapDrawGui.renderMapAndOverlay(sepia, 1.0F, true);
               var68 = this.mapDrawGui;
               LOTRGuiMap.setFakeStaticProperties(statics[0], statics[1], statics[2], statics[3], statics[4], statics[5]);
            }

            avgBgColor = this.guiLeft + this.pageMapX + 3;
            wcY = this.guiTop + this.pageY + this.pageMapY + this.pageMapSize + 5;
            int wcWidth = 8;
            this.field_146297_k.func_110434_K().func_110577_a(factionsTexture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            if (this.currentFaction.approvesWarCrimes) {
               this.func_73729_b(avgBgColor, wcY, 33, 142, wcWidth, wcWidth);
            } else {
               this.func_73729_b(avgBgColor, wcY, 41, 142, wcWidth, wcWidth);
            }

            if (i >= avgBgColor && i < avgBgColor + wcWidth && j >= wcY && j < wcY + wcWidth) {
               mouseOverWarCrimes = true;
            }
         }

         x = this.guiLeft + this.pageBorderLeft;
         y = this.guiTop + this.pageY + this.pageBorderTop;
         String desc;
         String rankName;
         if (!this.isPledging && !this.isUnpledging) {
            if (currentPage == LOTRGuiFactions.Page.FRONT) {
               if (this.isOtherPlayer) {
                  s = StatCollector.func_74837_a("lotr.gui.factions.pageOther", new Object[]{this.otherPlayerName});
                  this.field_146289_q.func_78276_b(s, x, y, 8019267);
                  y += this.field_146289_q.field_78288_b * 2;
               }

               String alignmentInfo = StatCollector.func_74838_a("lotr.gui.factions.alignment");
               this.field_146289_q.func_78276_b(alignmentInfo, x, y, 8019267);
               x += this.field_146289_q.func_78256_a(alignmentInfo) + 5;
               String alignmentString = LOTRAlignmentValues.formatAlignForDisplay(alignment);
               LOTRTickHandlerClient.drawAlignmentText(this.field_146289_q, x, y, alignmentString, 1.0F);
               if (clientPD.isPledgeEnemyAlignmentLimited(this.currentFaction)) {
                  this.field_146297_k.func_110434_K().func_110577_a(factionsTexture);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  index = x + this.field_146289_q.func_78256_a(alignmentString) + 5;
                  int lockWidth = 16;
                  this.func_73729_b(index, y, 0, 200, lockWidth, lockWidth);
                  if (i >= index && i < index + lockWidth && j >= y && j < y + lockWidth) {
                     mouseOverAlignLock = true;
                  }
               }

               y += this.field_146289_q.field_78288_b;
               x = this.guiLeft + this.pageBorderLeft;
               LOTRFactionRank curRank = this.currentFaction.getRank(alignment);
               desc = curRank.getFullNameWithGender(clientPD);
               desc = StatCollector.func_74837_a("lotr.gui.factions.alignment.state", new Object[]{desc});
               this.field_146289_q.func_78276_b(desc, x, y, 8019267);
               y += this.field_146289_q.field_78288_b * 2;
               if (!this.isOtherPlayer) {
                  LOTRFactionData factionData = clientPD.getFactionData(this.currentFaction);
                  if (alignment >= 0.0F) {
                     s = StatCollector.func_74837_a("lotr.gui.factions.data.enemiesKilled", new Object[]{factionData.getEnemiesKilled()});
                     this.field_146289_q.func_78276_b(s, x, y, 8019267);
                     y += this.field_146289_q.field_78288_b;
                     s = StatCollector.func_74837_a("lotr.gui.factions.data.trades", new Object[]{factionData.getTradeCount()});
                     this.field_146289_q.func_78276_b(s, x, y, 8019267);
                     y += this.field_146289_q.field_78288_b;
                     s = StatCollector.func_74837_a("lotr.gui.factions.data.hires", new Object[]{factionData.getHireCount()});
                     this.field_146289_q.func_78276_b(s, x, y, 8019267);
                     y += this.field_146289_q.field_78288_b;
                     s = StatCollector.func_74837_a("lotr.gui.factions.data.miniquests", new Object[]{factionData.getMiniQuestsCompleted()});
                     this.field_146289_q.func_78276_b(s, x, y, 8019267);
                     y += this.field_146289_q.field_78288_b;
                     if (clientPD.isPledgedTo(this.currentFaction)) {
                        conq = factionData.getConquestEarned();
                        if (conq != 0.0F) {
                           py = Math.round(conq);
                           s = StatCollector.func_74837_a("lotr.gui.factions.data.conquest", new Object[]{py});
                           this.field_146289_q.func_78276_b(s, x, y, 8019267);
                           y += this.field_146289_q.field_78288_b;
                        }
                     }
                  }

                  if (alignment <= 0.0F) {
                     s = StatCollector.func_74837_a("lotr.gui.factions.data.npcsKilled", new Object[]{factionData.getNPCsKilled()});
                     this.field_146289_q.func_78276_b(s, x, y, 8019267);
                     var10000 = y + this.field_146289_q.field_78288_b;
                  }

                  if (this.buttonPledge.field_146125_m && clientPD.isPledgedTo(this.currentFaction)) {
                     s = StatCollector.func_74838_a("lotr.gui.factions.pledged");
                     px = this.buttonPledge.field_146128_h + this.buttonPledge.field_146120_f + 8;
                     py = this.buttonPledge.field_146129_i + this.buttonPledge.field_146121_g / 2 - this.field_146289_q.field_78288_b / 2;
                     this.field_146289_q.func_78276_b(s, px, py, 16711680);
                  }
               }
            } else {
               int[] minMax;
               Object listObj;
               if (currentPage == LOTRGuiFactions.Page.RANKS) {
                  LOTRFactionRank curRank = this.currentFaction.getRank(clientPD);
                  minMax = this.scrollPaneAlliesEnemies.getMinMaxIndices(this.currentAlliesEnemies, this.numDisplayedAlliesEnemies);

                  for(index = minMax[0]; index <= minMax[1]; ++index) {
                     listObj = this.currentAlliesEnemies.get(index);
                     if (listObj instanceof String) {
                        s = (String)listObj;
                        this.field_146289_q.func_78276_b(s, x, y, 8019267);
                     } else if (listObj instanceof LOTRFactionRank) {
                        LOTRFactionRank rank = (LOTRFactionRank)listObj;
                        rankName = rank.getShortNameWithGender(clientPD);
                        String rankAlign = LOTRAlignmentValues.formatAlignForDisplay(rank.alignment);
                        if (rank == LOTRFactionRank.RANK_ENEMY) {
                           rankAlign = "-";
                        }

                        boolean hiddenRankName = false;
                        if (!clientPD.isPledgedTo(this.currentFaction) && rank.alignment > this.currentFaction.getPledgeAlignment() && rank.alignment > this.currentFaction.getRankAbove(curRank).alignment) {
                           hiddenRankName = true;
                        }

                        if (hiddenRankName) {
                           rankName = StatCollector.func_74838_a("lotr.gui.factions.rank?");
                        }

                        s = StatCollector.func_74837_a("lotr.gui.factions.listRank", new Object[]{rankName, rankAlign});
                        if (rank == curRank) {
                           LOTRTickHandlerClient.drawAlignmentText(this.field_146289_q, x, y, s, 1.0F);
                        } else {
                           this.field_146289_q.func_78276_b(s, x, y, 8019267);
                        }
                     }

                     y += this.field_146289_q.field_78288_b;
                  }
               } else if (currentPage == LOTRGuiFactions.Page.ALLIES || currentPage == LOTRGuiFactions.Page.ENEMIES) {
                  avgBgColor = LOTRTextures.computeAverageFactionPageColor(factionsTexture, 20, 20, 120, 80);
                  minMax = this.scrollPaneAlliesEnemies.getMinMaxIndices(this.currentAlliesEnemies, this.numDisplayedAlliesEnemies);

                  for(index = minMax[0]; index <= minMax[1]; ++index) {
                     listObj = this.currentAlliesEnemies.get(index);
                     if (listObj instanceof LOTRFactionRelations.Relation) {
                        LOTRFactionRelations.Relation rel = (LOTRFactionRelations.Relation)listObj;
                        s = StatCollector.func_74837_a("lotr.gui.factions.relationHeader", new Object[]{rel.getDisplayName()});
                        this.field_146289_q.func_78276_b(s, x, y, 8019267);
                     } else if (listObj instanceof LOTRFaction) {
                        LOTRFaction fac = (LOTRFaction)listObj;
                        s = StatCollector.func_74837_a("lotr.gui.factions.list", new Object[]{fac.factionName()});
                        this.field_146289_q.func_78276_b(s, x, y, LOTRTextures.findContrastingColor(fac.getFactionColor(), avgBgColor));
                     }

                     y += this.field_146289_q.field_78288_b;
                  }
               }
            }

            if (this.scrollPaneAlliesEnemies.hasScrollBar) {
               this.scrollPaneAlliesEnemies.drawScrollBar();
            }
         } else {
            avgBgColor = this.pageWidth - this.pageBorderLeft * 2;
            List displayLines = new ArrayList();
            if (this.isPledging) {
               List facsPreventingPledge = clientPD.getFactionsPreventingPledgeTo(this.currentFaction);
               String desc;
               if (facsPreventingPledge.isEmpty()) {
                  if (clientPD.canMakeNewPledge()) {
                     if (clientPD.canPledgeTo(this.currentFaction)) {
                        desc = StatCollector.func_74837_a("lotr.gui.factions.pledgeDesc1", new Object[]{this.currentFaction.factionName()});
                        displayLines.addAll(this.field_146289_q.func_78271_c(desc, avgBgColor));
                        displayLines.add("");
                        desc = StatCollector.func_74837_a("lotr.gui.factions.pledgeDesc2", new Object[0]);
                        displayLines.addAll(this.field_146289_q.func_78271_c(desc, avgBgColor));
                     }
                  } else {
                     LOTRFaction brokenPledge = clientPD.getBrokenPledgeFaction();
                     desc = brokenPledge == null ? StatCollector.func_74838_a("lotr.gui.factions.pledgeUnknown") : brokenPledge.factionName();
                     rankName = StatCollector.func_74837_a("lotr.gui.factions.pledgeBreakCooldown", new Object[]{this.currentFaction.factionName(), desc});
                     displayLines.addAll(this.field_146289_q.func_78271_c(rankName, avgBgColor));
                     displayLines.add("");
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     this.field_146297_k.func_110434_K().func_110577_a(factionsTexture);
                     this.func_73729_b(this.guiLeft + this.pageWidth / 2 - 97, this.guiTop + this.pageY + 56, 0, 240, 194, 16);
                     cdFrac = (float)clientPD.getPledgeBreakCooldown() / (float)clientPD.getPledgeBreakCooldownStart();
                     this.func_73729_b(this.guiLeft + this.pageWidth / 2 - 75, this.guiTop + this.pageY + 60, 22, 232, MathHelper.func_76123_f(cdFrac * 150.0F), 8);
                  }
               } else {
                  Collections.sort(facsPreventingPledge, new Comparator() {
                     public int compare(LOTRFaction o1, LOTRFaction o2) {
                        float align1 = clientPD.getAlignment(o1);
                        float align2 = clientPD.getAlignment(o2);
                        return -Float.valueOf(align1).compareTo(align2);
                     }
                  });
                  desc = "If you are reading this, something has gone hideously wrong.";
                  if (facsPreventingPledge.size() == 1) {
                     desc = StatCollector.func_74837_a("lotr.gui.factions.enemies1", new Object[]{((LOTRFaction)facsPreventingPledge.get(0)).factionName()});
                  } else if (facsPreventingPledge.size() == 2) {
                     desc = StatCollector.func_74837_a("lotr.gui.factions.enemies2", new Object[]{((LOTRFaction)facsPreventingPledge.get(0)).factionName(), ((LOTRFaction)facsPreventingPledge.get(1)).factionName()});
                  } else if (facsPreventingPledge.size() == 3) {
                     desc = StatCollector.func_74837_a("lotr.gui.factions.enemies3", new Object[]{((LOTRFaction)facsPreventingPledge.get(0)).factionName(), ((LOTRFaction)facsPreventingPledge.get(1)).factionName(), ((LOTRFaction)facsPreventingPledge.get(2)).factionName()});
                  } else if (facsPreventingPledge.size() > 3) {
                     desc = StatCollector.func_74837_a("lotr.gui.factions.enemies3+", new Object[]{((LOTRFaction)facsPreventingPledge.get(0)).factionName(), ((LOTRFaction)facsPreventingPledge.get(1)).factionName(), ((LOTRFaction)facsPreventingPledge.get(2)).factionName(), facsPreventingPledge.size() - 3});
                  }

                  desc = StatCollector.func_74837_a("lotr.gui.factions.pledgeEnemies", new Object[]{this.currentFaction.factionName(), desc});
                  displayLines.addAll(this.field_146289_q.func_78271_c(desc, avgBgColor));
                  displayLines.add("");
               }
            } else if (this.isUnpledging) {
               String desc = StatCollector.func_74837_a("lotr.gui.factions.unpledgeDesc1", new Object[]{this.currentFaction.factionName()});
               displayLines.addAll(this.field_146289_q.func_78271_c(desc, avgBgColor));
               displayLines.add("");
               desc = StatCollector.func_74837_a("lotr.gui.factions.unpledgeDesc2", new Object[0]);
               displayLines.addAll(this.field_146289_q.func_78271_c(desc, avgBgColor));
            }

            for(Iterator var45 = displayLines.iterator(); var45.hasNext(); y += this.field_146297_k.field_71466_p.field_78288_b) {
               desc = (String)var45.next();
               this.field_146289_q.func_78276_b(desc, x, y, 8019267);
            }
         }
      }

      if (this.hasScrollBar()) {
         this.field_146297_k.func_110434_K().func_110577_a(factionsTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(this.guiLeft + this.scrollBarX, this.guiTop + this.scrollBarY, 0, 128, this.scrollBarWidth, this.scrollBarHeight);
         int factions = currentFactionList.size();

         for(x = 0; x < factions; ++x) {
            LOTRFaction faction = (LOTRFaction)currentFactionList.get(x);
            float[] factionColors = faction.getFactionRGB();
            float shade = 0.6F;
            GL11.glColor4f(factionColors[0] * shade, factionColors[1] * shade, factionColors[2] * shade, 1.0F);
            float xMin = (float)x / (float)factions;
            float xMax = (float)(x + 1) / (float)factions;
            xMin = (float)(this.guiLeft + this.scrollBarX + this.scrollBarBorder) + xMin * (float)(this.scrollBarWidth - this.scrollBarBorder * 2);
            xMax = (float)(this.guiLeft + this.scrollBarX + this.scrollBarBorder) + xMax * (float)(this.scrollBarWidth - this.scrollBarBorder * 2);
            float yMin = (float)(this.guiTop + this.scrollBarY + this.scrollBarBorder);
            float yMax = (float)(this.guiTop + this.scrollBarY + this.scrollBarHeight - this.scrollBarBorder);
            conq = (float)(0 + this.scrollBarBorder) / 256.0F;
            cdFrac = (float)(0 + this.scrollBarWidth - this.scrollBarBorder) / 256.0F;
            float minV = (float)(128 + this.scrollBarBorder) / 256.0F;
            float maxV = (float)(128 + this.scrollBarHeight - this.scrollBarBorder) / 256.0F;
            Tessellator tessellator = Tessellator.field_78398_a;
            tessellator.func_78382_b();
            tessellator.func_78374_a((double)xMin, (double)yMax, (double)this.field_73735_i, (double)conq, (double)maxV);
            tessellator.func_78374_a((double)xMax, (double)yMax, (double)this.field_73735_i, (double)cdFrac, (double)maxV);
            tessellator.func_78374_a((double)xMax, (double)yMin, (double)this.field_73735_i, (double)cdFrac, (double)minV);
            tessellator.func_78374_a((double)xMin, (double)yMin, (double)this.field_73735_i, (double)conq, (double)minV);
            tessellator.func_78381_a();
         }

         this.field_146297_k.func_110434_K().func_110577_a(factionsTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         if (this.canScroll()) {
            x = (int)(this.currentScroll * (float)(this.scrollBarWidth - this.scrollBarBorder * 2 - this.scrollWidgetWidth));
            this.func_73729_b(this.guiLeft + this.scrollBarX + this.scrollBarBorder + x, this.guiTop + this.scrollBarY + this.scrollBarBorder, 0, 142, this.scrollWidgetWidth, this.scrollWidgetHeight);
         }
      }

      super.func_73863_a(i, j, f);
      String warCrimes;
      short stringWidth;
      List desc;
      if (this.buttonFactionMap.field_146124_l && this.buttonFactionMap.func_146115_a()) {
         alignment = this.field_73735_i;
         warCrimes = StatCollector.func_74838_a("lotr.gui.factions.viewMap");
         stringWidth = 200;
         desc = this.field_146289_q.func_78271_c(warCrimes, stringWidth);
         this.func_146283_a(desc, i, j);
         GL11.glDisable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73735_i = alignment;
      }

      if (mouseOverAlignLock) {
         alignment = this.field_73735_i;
         warCrimes = LOTRAlignmentValues.formatAlignForDisplay(clientPD.getPledgeEnemyAlignmentLimit(this.currentFaction));
         String lockDesc = StatCollector.func_74837_a("lotr.gui.factions.pledgeLocked", new Object[]{warCrimes, clientPD.getPledgeFaction().factionName()});
         int stringWidth = 200;
         List desc = this.field_146289_q.func_78271_c(lockDesc, stringWidth);
         this.func_146283_a(desc, i, j);
         GL11.glDisable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73735_i = alignment;
      }

      if (mouseOverWarCrimes) {
         alignment = this.field_73735_i;
         warCrimes = this.currentFaction.approvesWarCrimes ? "lotr.gui.factions.warCrimesYes" : "lotr.gui.factions.warCrimesNo";
         warCrimes = StatCollector.func_74838_a(warCrimes);
         stringWidth = 200;
         desc = this.field_146289_q.func_78271_c(warCrimes, stringWidth);
         this.func_146283_a(desc, i, j);
         GL11.glDisable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73735_i = alignment;
      }

   }

   private boolean hasScrollBar() {
      return currentFactionList.size() > 1;
   }

   private boolean canScroll() {
      return true;
   }

   private void setupScrollBar(int i, int j) {
      boolean isMouseDown = Mouse.isButtonDown(0);
      int i1 = this.guiLeft + this.scrollBarX;
      int j1 = this.guiTop + this.scrollBarY;
      int i2 = i1 + this.scrollBarWidth;
      int j2 = j1 + this.scrollBarHeight;
      if (!this.wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
         this.isScrolling = this.canScroll();
      }

      if (!isMouseDown) {
         this.isScrolling = false;
      }

      this.wasMouseDown = isMouseDown;
      if (this.isScrolling) {
         this.currentScroll = ((float)(i - i1) - (float)this.scrollWidgetWidth / 2.0F) / ((float)(i2 - i1) - (float)this.scrollWidgetWidth);
         this.currentScroll = MathHelper.func_76131_a(this.currentScroll, 0.0F, 1.0F);
         this.currentFactionIndex = Math.round(this.currentScroll * (float)(currentFactionList.size() - 1));
         this.scrollPaneAlliesEnemies.resetScroll();
      }

      if (currentPage != LOTRGuiFactions.Page.ALLIES && currentPage != LOTRGuiFactions.Page.ENEMIES && currentPage != LOTRGuiFactions.Page.RANKS) {
         this.scrollPaneAlliesEnemies.hasScrollBar = false;
         this.scrollPaneAlliesEnemies.mouseDragScroll(i, j);
      } else {
         List mortals;
         List enemies;
         if (currentPage == LOTRGuiFactions.Page.ALLIES) {
            this.currentAlliesEnemies = new ArrayList();
            mortals = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.ALLY);
            if (!mortals.isEmpty()) {
               this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.ALLY);
               this.currentAlliesEnemies.addAll(mortals);
            }

            enemies = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.FRIEND);
            if (!enemies.isEmpty()) {
               if (!this.currentAlliesEnemies.isEmpty()) {
                  this.currentAlliesEnemies.add((Object)null);
               }

               this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.FRIEND);
               this.currentAlliesEnemies.addAll(enemies);
            }
         } else if (currentPage == LOTRGuiFactions.Page.ENEMIES) {
            this.currentAlliesEnemies = new ArrayList();
            mortals = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.MORTAL_ENEMY);
            if (!mortals.isEmpty()) {
               this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.MORTAL_ENEMY);
               this.currentAlliesEnemies.addAll(mortals);
            }

            enemies = this.currentFaction.getOthersOfRelation(LOTRFactionRelations.Relation.ENEMY);
            if (!enemies.isEmpty()) {
               if (!this.currentAlliesEnemies.isEmpty()) {
                  this.currentAlliesEnemies.add((Object)null);
               }

               this.currentAlliesEnemies.add(LOTRFactionRelations.Relation.ENEMY);
               this.currentAlliesEnemies.addAll(enemies);
            }
         } else if (currentPage == LOTRGuiFactions.Page.RANKS) {
            this.currentAlliesEnemies = new ArrayList();
            this.currentAlliesEnemies.add(StatCollector.func_74838_a("lotr.gui.factions.rankHeader"));
            if (LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getAlignment(this.currentFaction) <= 0.0F) {
               this.currentAlliesEnemies.add(LOTRFactionRank.RANK_ENEMY);
            }

            LOTRFactionRank rank = LOTRFactionRank.RANK_NEUTRAL;

            while(true) {
               this.currentAlliesEnemies.add(rank);
               LOTRFactionRank nextRank = this.currentFaction.getRankAbove(rank);
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
         if (currentPage == LOTRGuiFactions.Page.RANKS) {
            LOTRGuiScrollPane var10000 = this.scrollPaneAlliesEnemies;
            var10000.scrollBarX0 += 50;
         }

         this.scrollPaneAlliesEnemies.paneY0 = this.guiTop + this.pageY + this.pageBorderTop;
         this.scrollPaneAlliesEnemies.paneY1 = this.scrollPaneAlliesEnemies.paneY0 + this.field_146289_q.field_78288_b * this.numDisplayedAlliesEnemies;
         this.scrollPaneAlliesEnemies.mouseDragScroll(i, j);
      }

   }

   protected void func_73869_a(char c, int i) {
      if (i == 1 || i == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) {
         if (this.isPledging) {
            this.isPledging = false;
            return;
         }

         if (this.isUnpledging) {
            this.isUnpledging = false;
            return;
         }

         if (this.isOtherPlayer) {
            this.field_146297_k.field_71439_g.func_71053_j();
            return;
         }
      }

      super.func_73869_a(c, i);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button == this.buttonRegions) {
            List regionList = currentDimension.dimensionRegions;
            if (!regionList.isEmpty()) {
               int i = regionList.indexOf(currentRegion);
               ++i;
               i = IntMath.mod(i, regionList.size());
               currentRegion = (LOTRDimension.DimensionRegion)regionList.get(i);
               this.updateCurrentDimensionAndFaction();
               this.setCurrentScrollFromFaction();
               this.scrollPaneAlliesEnemies.resetScroll();
               this.isPledging = false;
               this.isUnpledging = false;
            }
         } else {
            LOTRGuiFactions.Page newPage;
            if (button == this.buttonPagePrev) {
               newPage = currentPage.prev();
               if (newPage != null) {
                  currentPage = newPage;
                  this.scrollPaneAlliesEnemies.resetScroll();
                  this.isPledging = false;
                  this.isUnpledging = false;
               }
            } else if (button == this.buttonPageNext) {
               newPage = currentPage.next();
               if (newPage != null) {
                  currentPage = newPage;
                  this.scrollPaneAlliesEnemies.resetScroll();
                  this.isPledging = false;
                  this.isUnpledging = false;
               }
            } else if (button == this.buttonFactionMap) {
               LOTRGuiMap factionGuiMap = new LOTRGuiMap();
               factionGuiMap.setControlZone(this.currentFaction);
               this.field_146297_k.func_147108_a(factionGuiMap);
            } else if (button == this.buttonPledge) {
               if (LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).isPledgedTo(this.currentFaction)) {
                  this.isUnpledging = true;
               } else {
                  this.isPledging = true;
               }
            } else {
               LOTRPacketPledgeSet packet;
               if (button == this.buttonPledgeConfirm) {
                  packet = new LOTRPacketPledgeSet(this.currentFaction);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  this.isPledging = false;
               } else if (button == this.buttonPledgeRevoke) {
                  packet = new LOTRPacketPledgeSet((LOTRFaction)null);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  this.isUnpledging = false;
                  this.field_146297_k.func_147108_a((GuiScreen)null);
               } else {
                  super.func_146284_a(button);
               }
            }
         }
      }

   }

   public void func_146274_d() {
      super.func_146274_d();
      int k = Mouse.getEventDWheel();
      if (k != 0) {
         k = Integer.signum(k);
         if (this.scrollPaneAlliesEnemies.hasScrollBar && this.scrollPaneAlliesEnemies.mouseOver) {
            int l = this.currentAlliesEnemies.size() - this.numDisplayedAlliesEnemies;
            this.scrollPaneAlliesEnemies.mouseWheelScroll(k, l);
         } else {
            if (k < 0) {
               this.currentFactionIndex = Math.min(this.currentFactionIndex + 1, Math.max(0, currentFactionList.size() - 1));
            }

            if (k > 0) {
               this.currentFactionIndex = Math.max(this.currentFactionIndex - 1, 0);
            }

            this.setCurrentScrollFromFaction();
            this.scrollPaneAlliesEnemies.resetScroll();
            this.isPledging = false;
            this.isUnpledging = false;
         }
      }

   }

   private void setCurrentScrollFromFaction() {
      this.currentScroll = (float)this.currentFactionIndex / (float)(currentFactionList.size() - 1);
   }

   public void drawButtonHoveringText(List list, int i, int j) {
      this.func_146283_a(list, i, j);
   }

   static {
      currentPage = LOTRGuiFactions.Page.FRONT;
   }

   public static enum Page {
      FRONT,
      RANKS,
      ALLIES,
      ENEMIES;

      public LOTRGuiFactions.Page prev() {
         int i = this.ordinal();
         if (i == 0) {
            return null;
         } else {
            --i;
            return values()[i];
         }
      }

      public LOTRGuiFactions.Page next() {
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
