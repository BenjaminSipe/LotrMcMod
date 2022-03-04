package lotr.client.gui;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lotr.client.LOTRTextBody;
import lotr.common.LOTRDate;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTRSpeech;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.network.LOTRPacketDeleteMiniquest;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMiniquestTrack;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LOTRGuiRedBook extends LOTRGuiScreenBase {
   public static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/quest/redBook.png");
   public static ResourceLocation guiTexture_miniquests = new ResourceLocation("lotr:gui/quest/redBook_miniquests.png");
   private static RenderItem renderItem = new RenderItem();
   public int xSize = 420;
   public int ySize = 256;
   private int guiLeft;
   private int guiTop;
   private int pageWidth = 186;
   private int pageTop = 18;
   private int pageBorder = 10;
   private boolean wasMouseDown;
   private int lastMouseX;
   private int lastMouseY;
   private int scrollBarWidth = 12;
   private int scrollBarHeight = 216;
   private int scrollBarX;
   private int scrollBarY;
   private int scrollBarActiveHeight;
   private int scrollBarActiveYOffset;
   private int scrollBarActiveXBorder;
   private int scrollWidgetWidth;
   private int scrollWidgetHeight;
   private boolean mouseInScrollBar;
   private boolean isScrolling;
   private float currentScroll;
   private Map displayedMiniQuests;
   private int maxDisplayedMiniQuests;
   private int qPanelWidth;
   private int qPanelHeight;
   private int qPanelBorder;
   private int qDelX;
   private int qDelY;
   private int qTrackX;
   private int qTrackY;
   private int qWidgetSize;
   private int diaryWidth;
   private int diaryHeight;
   private int diaryX;
   private int diaryY;
   private int diaryBorder;
   private boolean mouseInDiary;
   private boolean isDiaryScrolling;
   private float diaryScroll;
   private static boolean viewCompleted = false;
   private LOTRMiniQuest selectedMiniquest;
   private LOTRMiniQuest deletingMiniquest;
   private int trackTicks;
   private static final int trackTicksMax = 40;
   private GuiButton buttonViewActive;
   private GuiButton buttonViewCompleted;
   private GuiButton buttonQuestDelete;
   private GuiButton buttonQuestDeleteCancel;
   public static final int textColor = 8019267;
   public static final int textColorDark = 5521198;
   public static final int textColorFaded = 9666921;
   public static final int textColorRed = 16711680;
   private static LOTRGuiRedBook.Page page;

   public LOTRGuiRedBook() {
      this.scrollBarX = this.xSize / 2 + this.pageWidth;
      this.scrollBarY = 18;
      this.scrollBarActiveHeight = 203;
      this.scrollBarActiveYOffset = 3;
      this.scrollBarActiveXBorder = 1;
      this.scrollWidgetWidth = 10;
      this.scrollWidgetHeight = 17;
      this.mouseInScrollBar = false;
      this.isScrolling = false;
      this.currentScroll = 0.0F;
      this.displayedMiniQuests = new HashMap();
      this.maxDisplayedMiniQuests = 4;
      this.qPanelWidth = 170;
      this.qPanelHeight = 45;
      this.qPanelBorder = 4;
      this.qDelX = 158;
      this.qDelY = 4;
      this.qTrackX = 148;
      this.qTrackY = 4;
      this.qWidgetSize = 8;
      this.diaryWidth = 170;
      this.diaryHeight = 218;
      this.diaryX = this.xSize / 2 - this.pageBorder - this.pageWidth / 2 - this.diaryWidth / 2;
      this.diaryY = this.ySize / 2 - this.diaryHeight / 2 - 1;
      this.diaryBorder = 6;
      this.mouseInDiary = false;
      this.isDiaryScrolling = false;
   }

   public void func_73866_w_() {
      if (page == null) {
         page = LOTRGuiRedBook.Page.values()[0];
      }

      this.guiLeft = (this.field_146294_l - this.xSize) / 2;
      this.guiTop = (this.field_146295_m - this.ySize) / 2;
      int buttonX = this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2;
      int buttonY = this.guiTop + 80;
      this.field_146292_n.add(this.buttonViewActive = new LOTRGuiButtonRedBook(2, buttonX - 10 - 60, buttonY, 60, 20, StatCollector.func_74838_a("lotr.gui.redBook.mq.viewActive")));
      this.field_146292_n.add(this.buttonViewCompleted = new LOTRGuiButtonRedBook(3, buttonX + 10, buttonY, 60, 20, StatCollector.func_74838_a("lotr.gui.redBook.mq.viewComplete")));
      buttonX = this.guiLeft + this.xSize / 2 + this.pageBorder + this.pageWidth / 2;
      buttonY = this.guiTop + this.ySize - 60;
      this.field_146292_n.add(this.buttonQuestDelete = new LOTRGuiButtonRedBook(2, buttonX - 10 - 60, buttonY, 60, 20, ""));
      this.field_146292_n.add(this.buttonQuestDeleteCancel = new LOTRGuiButtonRedBook(3, buttonX + 10, buttonY, 60, 20, ""));
   }

   public void func_73876_c() {
      super.func_73876_c();
      if (this.trackTicks > 0) {
         --this.trackTicks;
      }

   }

   public void func_73863_a(int i, int j, float f) {
      this.displayedMiniQuests.clear();
      this.setupScrollBar(i, j);
      this.func_146276_q_();
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512);
      int x = this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2;
      int y = this.guiTop + 30;
      if (page == LOTRGuiRedBook.Page.MINIQUESTS && this.selectedMiniquest == null) {
         float scale = 2.0F;
         float invScale = 1.0F / scale;
         x = (int)((float)x * invScale);
         y = (int)((float)y * invScale);
         GL11.glScalef(scale, scale, scale);
         this.drawCenteredString(page.getTitle(), x, y, 8019267);
         GL11.glScalef(invScale, invScale, invScale);
         x = this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2;
         y = this.guiTop + 50;
         if (viewCompleted) {
            this.drawCenteredString(StatCollector.func_74838_a("lotr.gui.redBook.mq.viewComplete"), x, y, 8019267);
         } else {
            this.drawCenteredString(StatCollector.func_74838_a("lotr.gui.redBook.mq.viewActive"), x, y, 8019267);
         }
      }

      if (page == LOTRGuiRedBook.Page.MINIQUESTS) {
         int min;
         int textBottom;
         String entityName;
         if (this.selectedMiniquest == null) {
            this.drawCenteredString(LOTRDate.ShireReckoning.getShireDate().getDateName(false), this.guiLeft + this.xSize / 2 - this.pageBorder - this.pageWidth / 2, this.guiTop + this.ySize - 30, 8019267);
            this.drawCenteredString(StatCollector.func_74837_a("lotr.gui.redBook.mq.numActive", new Object[]{this.getPlayerData().getActiveMiniQuests().size()}), x, this.guiTop + 120, 8019267);
            this.drawCenteredString(StatCollector.func_74837_a("lotr.gui.redBook.mq.numComplete", new Object[]{this.getPlayerData().getCompletedMiniQuestsTotal()}), x, this.guiTop + 140, 8019267);
         } else {
            LOTRMiniQuest quest = this.selectedMiniquest;
            this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
            float[] questRGB = quest.getQuestColorComponents();
            GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0F);
            x = this.guiLeft + this.diaryX;
            y = this.guiTop + this.diaryY;
            this.drawTexturedModalRect(x, y, 0, 256, this.diaryWidth, this.diaryHeight, 512);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            min = this.diaryWidth - this.diaryBorder * 2;
            textBottom = y + this.diaryHeight - this.diaryBorder;
            x += this.diaryBorder;
            y += this.diaryBorder;
            boolean completed = quest.isCompleted();
            boolean failed = !completed && quest.isFailed();
            entityName = quest.entityName;
            String factionName = quest.getFactionSubtitle();
            LOTRTextBody pageText = new LOTRTextBody(8019267);
            pageText.setTextWidth(min);
            String[] dayYear = LOTRDate.ShireReckoning.getShireDate(quest.dateGiven).getDayAndYearNames(false);
            pageText.add(dayYear[0]);
            pageText.add(dayYear[1]);
            if (quest.biomeGiven != null) {
               pageText.add(quest.biomeGiven.getBiomeDisplayName());
            }

            pageText.add("");
            String startQuote = LOTRSpeech.formatSpeech(quest.quoteStart, this.field_146297_k.field_71439_g, (String)null, quest.getObjectiveInSpeech());
            startQuote = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.quote", new Object[]{startQuote});
            pageText.add(startQuote);
            pageText.add("");
            List quotesStages = quest.quotesStages;
            String s;
            String completeQuote;
            if (!quotesStages.isEmpty()) {
               Iterator var18 = quotesStages.iterator();

               while(var18.hasNext()) {
                  s = (String)var18.next();
                  completeQuote = LOTRSpeech.formatSpeech(s, this.field_146297_k.field_71439_g, (String)null, quest.getObjectiveInSpeech());
                  completeQuote = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.quote", new Object[]{completeQuote});
                  pageText.add(completeQuote);
                  pageText.add("");
               }
            }

            String asked = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.asked", new Object[]{entityName, quest.getQuestObjective()});
            pageText.add(asked);
            pageText.add("");
            s = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.progress", new Object[]{quest.getQuestProgress()});
            pageText.add(s);
            if (quest.willHire) {
               pageText.add("");
               completeQuote = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.willHire", new Object[]{entityName});
               pageText.add(completeQuote);
            }

            String completedText;
            if (failed) {
               for(int l = 0; l < pageText.size(); ++l) {
                  completedText = pageText.getText(l);
                  completedText = EnumChatFormatting.STRIKETHROUGH + completedText;
                  pageText.set(l, completedText);
               }

               completeQuote = quest.getQuestFailure();
               pageText.add(completeQuote, 16711680);
            }

            if (completed) {
               pageText.add("");
               pageText.addLinebreak();
               pageText.add("");
               dayYear = LOTRDate.ShireReckoning.getShireDate(quest.dateCompleted).getDayAndYearNames(false);
               pageText.add(dayYear[0]);
               pageText.add(dayYear[1]);
               pageText.add("");
               completeQuote = LOTRSpeech.formatSpeech(quest.quoteComplete, this.field_146297_k.field_71439_g, (String)null, quest.getObjectiveInSpeech());
               completeQuote = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.quote", new Object[]{completeQuote});
               pageText.add(completeQuote);
               pageText.add("");
               completedText = StatCollector.func_74838_a("lotr.gui.redBook.mq.diary.complete");
               pageText.add(completedText);
               String rewardHired;
               if (quest.anyRewardsGiven()) {
                  pageText.add("");
                  rewardHired = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.reward", new Object[]{entityName});
                  pageText.add(rewardHired);
                  String rewardCoins;
                  String rewardItem;
                  if (quest.alignmentRewarded != 0.0F) {
                     rewardCoins = LOTRAlignmentValues.formatAlignForDisplay(quest.alignmentRewarded);
                     String alignFacName = quest.getAlignmentRewardFaction().factionName();
                     rewardItem = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.reward.align", new Object[]{rewardCoins, alignFacName});
                     pageText.add(rewardItem);
                  }

                  if ((float)quest.coinsRewarded != 0.0F) {
                     rewardCoins = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.reward.coins", new Object[]{quest.coinsRewarded});
                     pageText.add(rewardCoins);
                  }

                  if (!quest.itemsRewarded.isEmpty()) {
                     for(Iterator var46 = quest.itemsRewarded.iterator(); var46.hasNext(); pageText.add(rewardItem)) {
                        ItemStack item = (ItemStack)var46.next();
                        if (item.func_77973_b() instanceof ItemEditableBook) {
                           rewardItem = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.reward.book", new Object[]{item.func_82833_r()});
                        } else {
                           rewardItem = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.reward.item", new Object[]{item.func_82833_r(), item.field_77994_a});
                        }
                     }
                  }
               }

               if (quest.wasHired) {
                  pageText.add("");
                  rewardHired = StatCollector.func_74837_a("lotr.gui.redBook.mq.diary.reward.hired", new Object[]{entityName});
                  pageText.add(rewardHired);
               }
            }

            this.diaryScroll = pageText.renderAndReturnScroll(this.field_146289_q, x, y, textBottom, this.diaryScroll);
         }

         int index;
         if (this.deletingMiniquest == null) {
            List miniquests = this.getMiniQuests();
            List miniquests = new ArrayList(miniquests);
            if (!((List)miniquests).isEmpty()) {
               if (viewCompleted) {
                  miniquests = Lists.reverse((List)miniquests);
               } else {
                  Collections.sort((List)miniquests, new LOTRMiniQuest.SorterAlphabetical());
               }

               int size = ((List)miniquests).size();
               min = 0 + Math.round(this.currentScroll * (float)(size - this.maxDisplayedMiniQuests));
               textBottom = this.maxDisplayedMiniQuests - 1 + Math.round(this.currentScroll * (float)(size - this.maxDisplayedMiniQuests));
               min = Math.max(min, 0);
               textBottom = Math.min(textBottom, size - 1);

               for(index = min; index <= textBottom; ++index) {
                  LOTRMiniQuest quest = (LOTRMiniQuest)((List)miniquests).get(index);
                  int displayIndex = index - min;
                  int questX = this.guiLeft + this.xSize / 2 + this.pageBorder;
                  int questY = this.guiTop + this.pageTop + displayIndex * (4 + this.qPanelHeight);
                  this.renderMiniQuestPanel(quest, questX, questY, i, j);
                  this.displayedMiniQuests.put(quest, Pair.of(questX, questY));
               }
            }
         } else {
            String deleteText;
            if (viewCompleted) {
               deleteText = StatCollector.func_74838_a("lotr.gui.redBook.mq.deleteCmp");
            } else {
               deleteText = StatCollector.func_74838_a("lotr.gui.redBook.mq.delete");
            }

            List deleteTextLines = this.field_146289_q.func_78271_c(deleteText, this.pageWidth);
            min = this.guiLeft + this.xSize / 2 + this.pageBorder + this.pageWidth / 2;
            textBottom = this.guiTop + 50;

            for(Iterator var37 = deleteTextLines.iterator(); var37.hasNext(); textBottom += this.field_146289_q.field_78288_b) {
               Object obj = var37.next();
               entityName = (String)obj;
               this.drawCenteredString(entityName, min, textBottom, 8019267);
            }

            index = this.guiLeft + this.xSize / 2 + this.pageBorder + this.pageWidth / 2 - this.qPanelWidth / 2;
            int questY = this.guiTop + this.pageTop + 80;
            this.renderMiniQuestPanel(this.deletingMiniquest, index, questY, i, j);
         }
      }

      if (this.hasScrollBar()) {
         this.field_146297_k.func_110434_K().func_110577_a(guiTexture_miniquests);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(this.guiLeft + this.scrollBarX, this.guiTop + this.scrollBarY, 244, 0, this.scrollBarWidth, this.scrollBarHeight);
         if (this.canScroll()) {
            int scroll = (int)(this.currentScroll * (float)(this.scrollBarActiveHeight - this.scrollWidgetHeight));
            this.func_73729_b(this.guiLeft + this.scrollBarX + this.scrollBarActiveXBorder, this.guiTop + this.scrollBarY + this.scrollBarActiveYOffset + scroll, 224, 0, this.scrollWidgetWidth, this.scrollWidgetHeight);
         } else {
            this.func_73729_b(this.guiLeft + this.scrollBarX + this.scrollBarActiveXBorder, this.guiTop + this.scrollBarY + this.scrollBarActiveYOffset, 234, 0, this.scrollWidgetWidth, this.scrollWidgetHeight);
         }
      }

      boolean hasQuestViewButtons = page == LOTRGuiRedBook.Page.MINIQUESTS && this.selectedMiniquest == null;
      this.buttonViewActive.field_146124_l = this.buttonViewActive.field_146125_m = hasQuestViewButtons;
      this.buttonViewCompleted.field_146124_l = this.buttonViewCompleted.field_146125_m = hasQuestViewButtons;
      boolean hasQuestDeleteButtons = page == LOTRGuiRedBook.Page.MINIQUESTS && this.deletingMiniquest != null;
      this.buttonQuestDelete.field_146124_l = this.buttonQuestDelete.field_146125_m = hasQuestDeleteButtons;
      this.buttonQuestDeleteCancel.field_146124_l = this.buttonQuestDeleteCancel.field_146125_m = hasQuestDeleteButtons;
      if (viewCompleted) {
         this.buttonQuestDelete.field_146126_j = StatCollector.func_74838_a("lotr.gui.redBook.mq.deleteCmpYes");
         this.buttonQuestDeleteCancel.field_146126_j = StatCollector.func_74838_a("lotr.gui.redBook.mq.deleteCmpNo");
      } else {
         this.buttonQuestDelete.field_146126_j = StatCollector.func_74838_a("lotr.gui.redBook.mq.deleteYes");
         this.buttonQuestDeleteCancel.field_146126_j = StatCollector.func_74838_a("lotr.gui.redBook.mq.deleteNo");
      }

      super.func_73863_a(i, j, f);
   }

   private void renderMiniQuestPanel(LOTRMiniQuest quest, int questX, int questY, int mouseX, int mouseY) {
      GL11.glPushMatrix();
      boolean mouseInPanel = mouseX >= questX && mouseX < questX + this.qPanelWidth && mouseY >= questY && mouseY < questY + this.qPanelHeight;
      boolean mouseInDelete = mouseX >= questX + this.qDelX && mouseX < questX + this.qDelX + this.qWidgetSize && mouseY >= questY + this.qDelY && mouseY < questY + this.qDelY + this.qWidgetSize;
      boolean mouseInTrack = mouseX >= questX + this.qTrackX && mouseX < questX + this.qTrackX + this.qWidgetSize && mouseY >= questY + this.qTrackY && mouseY < questY + this.qTrackY + this.qWidgetSize;
      boolean isTracking = quest == this.getPlayerData().getTrackingMiniQuest();
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture_miniquests);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (!mouseInPanel && quest != this.selectedMiniquest) {
         this.func_73729_b(questX, questY, 0, 0, this.qPanelWidth, this.qPanelHeight);
      } else {
         this.func_73729_b(questX, questY, 0, this.qPanelHeight, this.qPanelWidth, this.qPanelHeight);
      }

      float[] questRGB = quest.getQuestColorComponents();
      GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0F);
      GL11.glEnable(3008);
      this.func_73729_b(questX, questY, 0, this.qPanelHeight * 2, this.qPanelWidth, this.qPanelHeight);
      GL11.glDisable(3008);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      String questName = quest.entityName;
      String factionName = quest.getFactionSubtitle();
      if (quest.isFailed()) {
         questName = EnumChatFormatting.STRIKETHROUGH + questName;
         factionName = EnumChatFormatting.STRIKETHROUGH + factionName;
      }

      this.field_146289_q.func_78276_b(questName, questX + this.qPanelBorder, questY + this.qPanelBorder, 8019267);
      this.field_146289_q.func_78276_b(factionName, questX + this.qPanelBorder, questY + this.qPanelBorder + this.field_146289_q.field_78288_b, 8019267);
      int maxObjLength;
      if (quest.isFailed()) {
         this.field_146289_q.func_78276_b(quest.getQuestFailureShorthand(), questX + this.qPanelBorder, questY + 25, 16711680);
      } else if (isTracking && this.trackTicks > 0) {
         this.field_146289_q.func_78276_b(StatCollector.func_74838_a("lotr.gui.redBook.mq.tracking"), questX + this.qPanelBorder, questY + 25, 8019267);
      } else {
         String objective = quest.getQuestObjective();
         maxObjLength = this.qPanelWidth - this.qPanelBorder * 2 - 18;
         String ellipsis;
         if (this.field_146289_q.func_78256_a(objective) >= maxObjLength) {
            ellipsis = "...";

            while(true) {
               if (this.field_146289_q.func_78256_a(objective + ellipsis) < maxObjLength) {
                  objective = objective + ellipsis;
                  break;
               }

               for(objective = objective.substring(0, objective.length() - 1); Character.isWhitespace(objective.charAt(objective.length() - 1)); objective = objective.substring(0, objective.length() - 1)) {
               }
            }
         }

         this.field_146289_q.func_78276_b(objective, questX + this.qPanelBorder, questY + 25, 8019267);
         ellipsis = quest.getQuestProgress();
         if (quest.isCompleted()) {
            ellipsis = StatCollector.func_74838_a("lotr.gui.redBook.mq.complete");
         }

         this.field_146289_q.func_78276_b(ellipsis, questX + this.qPanelBorder, questY + 25 + this.field_146289_q.field_78288_b, 8019267);
      }

      if (this.deletingMiniquest == null) {
         this.field_146297_k.func_110434_K().func_110577_a(guiTexture_miniquests);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int delU = this.qPanelWidth;
         maxObjLength = 0;
         if (mouseInDelete) {
            maxObjLength += this.qWidgetSize;
         }

         this.func_73729_b(questX + this.qDelX, questY + this.qDelY, delU, maxObjLength, this.qWidgetSize, this.qWidgetSize);
         if (!viewCompleted) {
            int trackU = this.qPanelWidth + this.qWidgetSize;
            int trackV = 0;
            if (mouseInTrack) {
               trackV += this.qWidgetSize;
            }

            if (isTracking) {
               trackU += this.qWidgetSize;
            }

            this.func_73729_b(questX + this.qTrackX, questY + this.qTrackY, trackU, trackV, this.qWidgetSize, this.qWidgetSize);
         }
      }

      RenderHelper.func_74520_c();
      GL11.glDisable(2896);
      GL11.glEnable(32826);
      GL11.glEnable(2896);
      GL11.glEnable(2884);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      renderItem.func_82406_b(this.field_146297_k.field_71466_p, this.field_146297_k.func_110434_K(), quest.getQuestIcon(), questX + 149, questY + 24);
      GL11.glDisable(2896);
      GL11.glEnable(3008);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   private void setupScrollBar(int i, int j) {
      boolean isMouseDown = Mouse.isButtonDown(0);
      int i1 = i - this.guiLeft;
      int j1 = j - this.guiTop;
      if (this.selectedMiniquest != null) {
         this.mouseInDiary = i1 >= this.diaryX && i1 < this.diaryX + this.diaryWidth && j1 >= this.diaryY && j1 < this.diaryY + this.diaryHeight;
      } else {
         this.mouseInDiary = false;
      }

      this.mouseInScrollBar = i1 >= this.scrollBarX + this.scrollBarActiveXBorder && i1 < this.scrollBarX + this.scrollBarWidth - this.scrollBarActiveXBorder * 2 && j1 >= this.scrollBarY + this.scrollBarActiveYOffset && j1 < this.scrollBarY + this.scrollBarActiveYOffset + this.scrollBarActiveHeight;
      if (!this.wasMouseDown && isMouseDown) {
         if (this.mouseInScrollBar) {
            this.isScrolling = this.canScroll();
         } else if (this.mouseInDiary) {
            this.isDiaryScrolling = true;
         }
      }

      if (!isMouseDown) {
         this.isScrolling = false;
         this.isDiaryScrolling = false;
      }

      this.wasMouseDown = isMouseDown;
      if (this.isScrolling) {
         this.currentScroll = ((float)(j - (this.guiTop + this.scrollBarY + this.scrollBarActiveYOffset)) - (float)this.scrollWidgetHeight / 2.0F) / ((float)this.scrollBarActiveHeight - (float)this.scrollWidgetHeight);
         this.currentScroll = Math.max(this.currentScroll, 0.0F);
         this.currentScroll = Math.min(this.currentScroll, 1.0F);
      } else if (this.isDiaryScrolling) {
         float d = (float)(this.lastMouseY - j) / (float)this.field_146289_q.field_78288_b;
         this.diaryScroll -= d;
      }

      this.lastMouseX = i;
      this.lastMouseY = j;
   }

   private boolean hasScrollBar() {
      return page == LOTRGuiRedBook.Page.MINIQUESTS && this.deletingMiniquest == null;
   }

   private boolean canScroll() {
      return this.hasScrollBar() && this.getMiniQuests().size() > this.maxDisplayedMiniQuests;
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button == this.buttonViewActive) {
            viewCompleted = false;
         }

         if (button == this.buttonViewCompleted) {
            viewCompleted = true;
         }

         if (button == this.buttonQuestDelete && this.deletingMiniquest != null) {
            LOTRPacketDeleteMiniquest packet = new LOTRPacketDeleteMiniquest(this.deletingMiniquest);
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
            this.deletingMiniquest = null;
            this.selectedMiniquest = null;
            this.diaryScroll = 0.0F;
         }

         if (button == this.buttonQuestDeleteCancel && this.deletingMiniquest != null) {
            this.deletingMiniquest = null;
         }
      }

   }

   protected void func_73864_a(int i, int j, int mouse) {
      if (mouse == 0) {
         Iterator var4;
         Entry entry;
         LOTRMiniQuest quest;
         int questX;
         int questY;
         int i2;
         int j2;
         if (page == LOTRGuiRedBook.Page.MINIQUESTS && this.deletingMiniquest == null) {
            var4 = this.displayedMiniQuests.entrySet().iterator();

            while(var4.hasNext()) {
               entry = (Entry)var4.next();
               quest = (LOTRMiniQuest)entry.getKey();
               questX = (Integer)((Pair)entry.getValue()).getLeft();
               questY = (Integer)((Pair)entry.getValue()).getRight();
               int i1 = questX + this.qDelX;
               int j1 = questY + this.qDelY;
               i2 = i1 + this.qWidgetSize;
               j2 = j1 + this.qWidgetSize;
               if (i >= i1 && j >= j1 && i < i2 && j < j2) {
                  this.deletingMiniquest = quest;
                  this.selectedMiniquest = this.deletingMiniquest;
                  this.diaryScroll = 0.0F;
                  return;
               }

               if (!viewCompleted) {
                  i1 = questX + this.qTrackX;
                  j1 = questY + this.qTrackY;
                  i2 = i1 + this.qWidgetSize;
                  j2 = j1 + this.qWidgetSize;
                  if (i >= i1 && j >= j1 && i < i2 && j < j2) {
                     this.trackOrUntrack(quest);
                     return;
                  }
               }
            }
         }

         if (page == LOTRGuiRedBook.Page.MINIQUESTS && this.deletingMiniquest == null) {
            var4 = this.displayedMiniQuests.entrySet().iterator();

            while(var4.hasNext()) {
               entry = (Entry)var4.next();
               quest = (LOTRMiniQuest)entry.getKey();
               questX = (Integer)((Pair)entry.getValue()).getLeft();
               questY = (Integer)((Pair)entry.getValue()).getRight();
               i2 = questX + this.qPanelWidth;
               j2 = questY + this.qPanelHeight;
               if (i >= questX && j >= questY && i < i2 && j < j2) {
                  this.selectedMiniquest = quest;
                  this.diaryScroll = 0.0F;
                  return;
               }
            }

            if (!this.mouseInDiary && !this.isScrolling) {
               this.selectedMiniquest = null;
               this.diaryScroll = 0.0F;
            }
         }
      }

      super.func_73864_a(i, j, mouse);
   }

   protected void func_73869_a(char c, int i) {
      if (i == 1 || i == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) {
         if (this.deletingMiniquest != null) {
            this.deletingMiniquest = null;
            return;
         }

         if (this.selectedMiniquest != null) {
            this.selectedMiniquest = null;
            return;
         }
      }

      super.func_73869_a(c, i);
   }

   public void func_146274_d() {
      super.func_146274_d();
      int i = Mouse.getEventDWheel();
      if (i != 0 && (this.canScroll() || this.mouseInDiary)) {
         if (i > 0) {
            i = 1;
         }

         if (i < 0) {
            i = -1;
         }

         if (this.mouseInDiary) {
            this.diaryScroll += (float)i;
         } else {
            int j = this.getMiniQuests().size() - this.maxDisplayedMiniQuests;
            this.currentScroll -= (float)i / (float)j;
            this.currentScroll = Math.max(this.currentScroll, 0.0F);
            this.currentScroll = Math.min(this.currentScroll, 1.0F);
         }
      }

   }

   private LOTRPlayerData getPlayerData() {
      return LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g);
   }

   private List getMiniQuests() {
      return viewCompleted ? this.getPlayerData().getMiniQuestsCompleted() : this.getPlayerData().getMiniQuests();
   }

   private void trackOrUntrack(LOTRMiniQuest quest) {
      LOTRMiniQuest tracking = this.getPlayerData().getTrackingMiniQuest();
      LOTRMiniQuest newTracking = null;
      if (quest == tracking) {
         newTracking = null;
      } else {
         newTracking = quest;
      }

      LOTRPacketMiniquestTrack packet = new LOTRPacketMiniquestTrack(newTracking);
      LOTRPacketHandler.networkWrapper.sendToServer(packet);
      this.getPlayerData().setTrackingMiniQuest(newTracking);
      this.trackTicks = 40;
   }

   private static enum Page {
      MINIQUESTS("miniquests");

      private String name;

      private Page(String s) {
         this.name = s;
      }

      public String getTitle() {
         return StatCollector.func_74838_a("lotr.gui.redBook.page." + this.name);
      }
   }
}
