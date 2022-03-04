package lotr.client.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import lotr.client.LOTRClientProxy;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiNotificationDisplay extends Gui {
   private static int guiXSize = 190;
   private static int guiYSize = 32;
   private static RenderItem itemRenderer = new RenderItem();
   private Minecraft mc = Minecraft.func_71410_x();
   private int windowWidth;
   private int windowHeight;
   private List notifications = new ArrayList();
   private Set notificationsToRemove = new HashSet();

   public void queueAchievement(LOTRAchievement achievement) {
      this.notifications.add(new LOTRGuiNotificationDisplay.NotificationAchievement(achievement));
   }

   public void queueFellowshipNotification(IChatComponent message) {
      this.notifications.add(new LOTRGuiNotificationDisplay.NotificationFellowship(message));
   }

   public void queueConquest(LOTRFaction fac, float conq, boolean cleansing) {
      this.notifications.add(new LOTRGuiNotificationDisplay.NotificationConquest(fac, conq, cleansing));
   }

   private void updateWindowScale() {
      GL11.glViewport(0, 0, this.mc.field_71443_c, this.mc.field_71440_d);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      this.windowWidth = this.mc.field_71443_c;
      this.windowHeight = this.mc.field_71440_d;
      ScaledResolution scaledresolution = new ScaledResolution(this.mc, this.mc.field_71443_c, this.mc.field_71440_d);
      this.windowWidth = scaledresolution.func_78326_a();
      this.windowHeight = scaledresolution.func_78328_b();
      GL11.glClear(256);
      GL11.glMatrixMode(5889);
      GL11.glLoadIdentity();
      GL11.glOrtho(0.0D, (double)this.windowWidth, (double)this.windowHeight, 0.0D, 1000.0D, 3000.0D);
      GL11.glMatrixMode(5888);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
   }

   public void updateWindow() {
      if (!this.notifications.isEmpty()) {
         int index = 0;

         for(Iterator var2 = this.notifications.iterator(); var2.hasNext(); ++index) {
            LOTRGuiNotificationDisplay.Notification notif = (LOTRGuiNotificationDisplay.Notification)var2.next();
            long notifTime = notif.getNotificationTime();
            double d0 = (double)(Minecraft.func_71386_F() - notifTime) / (double)notif.getDurationMs();
            if (d0 >= 0.0D && d0 <= 1.0D) {
               this.updateWindowScale();
               if (Minecraft.func_71382_s()) {
                  GL11.glEnable(3008);
                  GL11.glDisable(2929);
                  GL11.glDepthMask(false);
                  double d1 = d0 * 2.0D;
                  if (d1 > 1.0D) {
                     d1 = 2.0D - d1;
                  }

                  d1 *= 4.0D;
                  d1 = 1.0D - d1;
                  if (d1 < 0.0D) {
                     d1 = 0.0D;
                  }

                  d1 *= d1;
                  d1 *= d1;
                  int i = this.windowWidth - guiXSize;
                  int j = 0 - (int)(d1 * 36.0D);
                  j += index * (guiYSize + 8);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  GL11.glEnable(3553);
                  this.mc.func_110434_K().func_110577_a(LOTRGuiAchievements.iconsTexture);
                  GL11.glDisable(2896);
                  this.func_73729_b(i, j, 0, 200, guiXSize, guiYSize);
                  notif.renderText(i + 30, j + 7);
                  GL11.glEnable(3008);
                  notif.renderIcon(i + 8, j + 8);
               }
            } else {
               this.notificationsToRemove.add(notif);
            }
         }
      }

      if (!this.notificationsToRemove.isEmpty()) {
         this.notifications.removeAll(this.notificationsToRemove);
      }

   }

   private class NotificationConquest extends LOTRGuiNotificationDisplay.Notification {
      private LOTRFaction conqFac;
      private float conqValue;
      private boolean isCleansing;

      public NotificationConquest(LOTRFaction fac, float conq, boolean clean) {
         super(null);
         this.conqFac = fac;
         this.conqValue = conq;
         this.isCleansing = clean;
      }

      public int getDurationMs() {
         return 6000;
      }

      public void renderText(int x, int y) {
         String conqS = LOTRAlignmentValues.formatConqForDisplay(this.conqValue, false);
         LOTRTickHandlerClient.drawConquestText(LOTRGuiNotificationDisplay.this.mc.field_71466_p, x + 1, y, conqS, this.isCleansing, 1.0F);
         LOTRGuiNotificationDisplay.this.mc.field_71466_p.func_78276_b(StatCollector.func_74838_a("lotr.gui.map.conquest.notif"), x + LOTRGuiNotificationDisplay.this.mc.field_71466_p.func_78256_a(conqS + " ") + 2, y, 8019267);
         LOTRGuiNotificationDisplay.this.mc.field_71466_p.func_78276_b(EnumChatFormatting.ITALIC + this.conqFac.factionName(), x, y + 11, 9666921);
      }

      public void renderIcon(int x, int y) {
         LOTRGuiNotificationDisplay.this.mc.func_110434_K().func_110577_a(LOTRClientProxy.alignmentTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         LOTRGuiNotificationDisplay.this.func_73729_b(x, y, this.isCleansing ? 16 : 0, 228, 16, 16);
      }
   }

   private class NotificationFellowship extends LOTRGuiNotificationDisplay.Notification {
      private IChatComponent message;

      public NotificationFellowship(IChatComponent msg) {
         super(null);
         this.message = msg;
      }

      public int getDurationMs() {
         return 6000;
      }

      public void renderText(int x, int y) {
         LOTRGuiNotificationDisplay.this.mc.field_71466_p.func_78279_b(this.message.func_150254_d(), x, y, 152, 8019267);
      }

      public void renderIcon(int x, int y) {
         LOTRGuiNotificationDisplay.this.mc.func_110434_K().func_110577_a(LOTRGuiFellowships.iconsTextures);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         LOTRGuiNotificationDisplay.this.func_73729_b(x, y, 80, 0, 16, 16);
      }
   }

   private class NotificationAchievement extends LOTRGuiNotificationDisplay.Notification {
      private LOTRAchievement achievement;

      public NotificationAchievement(LOTRAchievement ach) {
         super(null);
         this.achievement = ach;
      }

      public int getDurationMs() {
         return 3000;
      }

      public void renderText(int x, int y) {
         LOTRGuiNotificationDisplay.this.mc.field_71466_p.func_78276_b(StatCollector.func_74838_a("achievement.get"), x, y, 8019267);
         LOTRGuiNotificationDisplay.this.mc.field_71466_p.func_78276_b(this.achievement.getTitle(LOTRGuiNotificationDisplay.this.mc.field_71439_g), x, y + 11, 8019267);
      }

      public void renderIcon(int x, int y) {
         RenderHelper.func_74520_c();
         GL11.glDisable(2896);
         GL11.glEnable(32826);
         GL11.glEnable(2903);
         GL11.glEnable(2896);
         LOTRGuiNotificationDisplay.itemRenderer.func_82406_b(LOTRGuiNotificationDisplay.this.mc.field_71466_p, LOTRGuiNotificationDisplay.this.mc.func_110434_K(), this.achievement.icon, x, y);
         GL11.glDisable(2896);
         GL11.glDepthMask(true);
         GL11.glEnable(2929);
         GL11.glEnable(3008);
         LOTRGuiNotificationDisplay.this.mc.func_110434_K().func_110577_a(LOTRGuiAchievements.iconsTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         LOTRGuiNotificationDisplay.this.func_73729_b(x + 162, y + 1, 190, 17, 16, 16);
      }
   }

   private abstract class Notification {
      private Long notificationTime;

      private Notification() {
         this.notificationTime = Minecraft.func_71386_F();
      }

      public Long getNotificationTime() {
         return this.notificationTime;
      }

      public abstract int getDurationMs();

      public abstract void renderText(int var1, int var2);

      public abstract void renderIcon(int var1, int var2);

      // $FF: synthetic method
      Notification(Object x1) {
         this();
      }
   }
}
