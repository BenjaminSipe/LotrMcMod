package lotr.client.gui;

import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRConfig;
import lotr.common.LOTRLevelData;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiMiniquestTracker extends Gui {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/quest/tracker.png");
   private static RenderItem renderItem = new RenderItem();
   private int width;
   private int height;
   private int barX = 16;
   private int barY = 10;
   private int barWidth = 90;
   private int barHeight = 15;
   private int barEdge = 2;
   private int iconWidth = 20;
   private int iconHeight = 20;
   private int gap = 4;
   private LOTRMiniQuest trackedQuest;
   private boolean holdingComplete;
   private int completeTime;
   private static final int completeTimeMax = 200;

   public void drawTracker(Minecraft mc, EntityPlayer entityplayer) {
      ScaledResolution resolution = new ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
      this.width = resolution.func_78326_a();
      this.height = resolution.func_78328_b();
      FontRenderer fr = mc.field_71466_p;
      boolean flip = LOTRConfig.trackingQuestRight;
      if (entityplayer != null && this.trackedQuest != null) {
         float[] questRGB = this.trackedQuest.getQuestColorComponents();
         ItemStack itemstack = this.trackedQuest.getQuestIcon();
         String objective = this.trackedQuest.getQuestObjective();
         String progress = this.trackedQuest.getQuestProgressShorthand();
         float completion = this.trackedQuest.getCompletionFactor();
         boolean failed = this.trackedQuest.isFailed();
         boolean complete = this.trackedQuest.isCompleted();
         if (failed) {
            objective = this.trackedQuest.getQuestFailureShorthand();
         } else if (complete) {
            objective = StatCollector.func_74838_a("lotr.gui.redBook.mq.diary.complete");
         }

         int x = this.barX;
         int y = this.barY;
         if (flip) {
            x = this.width - this.barX - this.iconWidth;
         }

         GL11.glEnable(3008);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         mc.func_110434_K().func_110577_a(guiTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(x, y, 0, 0, this.iconWidth, this.iconHeight);
         int iconX = x + (this.iconWidth - 16) / 2;
         int iconY = y + (this.iconHeight - 16) / 2;
         if (flip) {
            x -= this.barWidth + this.gap;
         } else {
            x += this.iconWidth + this.gap;
         }

         int meterWidth = this.barWidth - this.barEdge * 2;
         meterWidth = Math.round((float)meterWidth * completion);
         mc.func_110434_K().func_110577_a(guiTexture);
         GL11.glColor4f(questRGB[0], questRGB[1], questRGB[2], 1.0F);
         this.func_73729_b(x + this.barEdge, y, this.iconWidth + this.barEdge, this.barHeight, meterWidth, this.barHeight);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.func_73729_b(x, y, this.iconWidth, 0, this.barWidth, this.barHeight);
         LOTRTickHandlerClient.drawAlignmentText(fr, x + this.barWidth / 2 - fr.func_78256_a(progress) / 2, y + this.barHeight - this.barHeight / 2 - fr.field_78288_b / 2, progress, 1.0F);
         y += this.barHeight + this.gap;
         fr.func_78279_b(objective, x, y, this.barWidth, 16777215);
         GL11.glDisable(3042);
         GL11.glDisable(3008);
         if (itemstack != null) {
            RenderHelper.func_74520_c();
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            renderItem.func_82406_b(mc.field_71466_p, mc.func_110434_K(), itemstack, iconX, iconY);
            GL11.glDisable(2896);
         }
      }

   }

   public void update(Minecraft mc, EntityPlayer entityplayer) {
      if (entityplayer == null) {
         this.trackedQuest = null;
      } else {
         if (this.trackedQuest != null && this.trackedQuest.isCompleted() && !this.holdingComplete) {
            this.completeTime = 200;
            this.holdingComplete = true;
         }

         LOTRMiniQuest currentTrackedQuest = LOTRLevelData.getData(entityplayer).getTrackingMiniQuest();
         if (this.completeTime > 0 && currentTrackedQuest == null) {
            --this.completeTime;
         } else {
            this.trackedQuest = currentTrackedQuest;
            this.holdingComplete = false;
         }
      }

   }

   public void setTrackedQuest(LOTRMiniQuest quest) {
      this.trackedQuest = quest;
   }
}
