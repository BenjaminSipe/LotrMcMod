package lotr.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lotr.client.LOTRReflectionClient;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRTitle;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketSelectTitle;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LOTRGuiTitles extends LOTRGuiMenuBase {
   private LOTRTitle.PlayerTitle currentTitle;
   private List displayedTitles = new ArrayList();
   private static final int maxDisplayedTitles = 12;
   private Map displayedTitleInfo = new HashMap();
   private LOTRTitle selectedTitle;
   private EnumChatFormatting selectedColor;
   private int colorBoxWidth;
   private int colorBoxGap;
   private Map displayedColorBoxes;
   private GuiButton selectButton;
   private GuiButton removeButton;
   private float currentScroll;
   private boolean isScrolling;
   private boolean wasMouseDown;
   private int scrollBarWidth;
   private int scrollBarHeight;
   private int scrollBarX;
   private int scrollBarY;
   private int scrollWidgetWidth;
   private int scrollWidgetHeight;

   public LOTRGuiTitles() {
      this.selectedColor = EnumChatFormatting.WHITE;
      this.colorBoxWidth = 8;
      this.colorBoxGap = 4;
      this.displayedColorBoxes = new HashMap();
      this.currentScroll = 0.0F;
      this.isScrolling = false;
      this.scrollBarWidth = 11;
      this.scrollBarHeight = 144;
      this.scrollBarX = 197 - (this.scrollBarWidth - 1) / 2;
      this.scrollBarY = 30;
      this.scrollWidgetWidth = 11;
      this.scrollWidgetHeight = 8;
   }

   public void func_73866_w_() {
      this.xSize = 256;
      super.func_73866_w_();
      this.field_146292_n.add(this.selectButton = new GuiButton(0, this.guiLeft + this.xSize / 2 - 10 - 80, this.guiTop + 220, 80, 20, StatCollector.func_74838_a("lotr.gui.titles.select")));
      this.field_146292_n.add(this.removeButton = new GuiButton(1, this.guiLeft + this.xSize / 2 + 10, this.guiTop + 220, 80, 20, StatCollector.func_74838_a("lotr.gui.titles.remove")));
      this.func_73876_c();
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.currentTitle = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getPlayerTitle();
      this.displayedTitles.clear();
      List availableTitles = new ArrayList();
      List unavailableTitles = new ArrayList();
      Iterator var3 = LOTRTitle.allTitles.iterator();

      while(var3.hasNext()) {
         LOTRTitle title = (LOTRTitle)var3.next();
         if (title.canPlayerUse(this.field_146297_k.field_71439_g)) {
            availableTitles.add(title);
         } else if (title.canDisplay(this.field_146297_k.field_71439_g)) {
            unavailableTitles.add(title);
         }
      }

      Comparator sorter = LOTRTitle.createTitleSorter(this.field_146297_k.field_71439_g);
      Collections.sort(availableTitles, sorter);
      Collections.sort(unavailableTitles, sorter);
      this.displayedTitles.addAll(availableTitles);
      this.displayedTitles.add((Object)null);
      this.displayedTitles.addAll(unavailableTitles);
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_146276_q_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.setupScrollBar(i, j);
      String s = StatCollector.func_74838_a("lotr.gui.titles.title");
      this.drawCenteredString(s, this.guiLeft + this.xSize / 2, this.guiTop - 30, 16777215);
      String titleName = this.currentTitle == null ? StatCollector.func_74838_a("lotr.gui.titles.currentTitle.none") : this.currentTitle.getTitle().getDisplayName(this.field_146297_k.field_71439_g);
      EnumChatFormatting currentColor = this.currentTitle == null ? EnumChatFormatting.WHITE : this.currentTitle.getColor();
      titleName = currentColor + titleName + EnumChatFormatting.RESET;
      this.drawCenteredString(StatCollector.func_74837_a("lotr.gui.titles.currentTitle", new Object[]{titleName}), this.guiLeft + this.xSize / 2, this.guiTop, 16777215);
      this.displayedTitleInfo.clear();
      int titleX = this.guiLeft + this.xSize / 2;
      int titleY = this.guiTop + 30;
      int yIncrement = 12;
      this.func_73728_b(titleX - 70, titleY - 1, titleY + yIncrement * 12, -1711276033);
      this.func_73728_b(titleX + 70 - 1, titleY - 1, titleY + yIncrement * 12, -1711276033);
      int size = this.displayedTitles.size();
      int min = 0 + Math.round(this.currentScroll * (float)(size - 12));
      int max = 11 + Math.round(this.currentScroll * (float)(size - 12));
      min = Math.max(min, 0);
      max = Math.min(max, size - 1);

      int scroll;
      String desc;
      int y2;
      int color;
      int y;
      for(scroll = min; scroll <= max; ++scroll) {
         LOTRTitle title = (LOTRTitle)this.displayedTitles.get(scroll);
         boolean isCurrentTitle = this.currentTitle != null && this.currentTitle.getTitle() == title;
         if (title != null) {
            desc = title.getDisplayName(this.field_146297_k.field_71439_g);
            if (isCurrentTitle) {
               desc = "[" + desc + "]";
               desc = this.currentTitle.getColor() + desc;
            }
         } else {
            desc = "---";
         }

         y2 = this.field_146289_q.func_78256_a(desc);
         int nameHeight = this.field_146297_k.field_71466_p.field_78288_b;
         color = titleX - y2 / 2;
         int nameXMax = titleX + y2 / 2;
         y = titleY + nameHeight;
         boolean mouseOver = i >= color && i < nameXMax && j >= titleY && j < y;
         if (title != null) {
            this.displayedTitleInfo.put(title, Pair.of(mouseOver, Pair.of(titleX, titleY)));
         }

         int textColor;
         if (title != null) {
            if (title.canPlayerUse(this.field_146297_k.field_71439_g)) {
               textColor = mouseOver ? 16777120 : 16777215;
            } else {
               textColor = mouseOver ? 12303291 : 7829367;
            }
         } else {
            textColor = 7829367;
         }

         this.drawCenteredString(desc, titleX, titleY, textColor);
         titleY += yIncrement;
      }

      this.displayedColorBoxes.clear();
      int colorX;
      int colorY;
      if (this.selectedTitle != null) {
         String title = this.selectedColor + this.selectedTitle.getDisplayName(this.field_146297_k.field_71439_g);
         this.drawCenteredString(title, this.guiLeft + this.xSize / 2, this.guiTop + 185, 16777215);
         List colorCodes = new ArrayList();
         EnumChatFormatting[] var30 = EnumChatFormatting.values();
         colorY = var30.length;

         EnumChatFormatting code;
         for(y2 = 0; y2 < colorY; ++y2) {
            code = var30[y2];
            if (code.func_96302_c()) {
               colorCodes.add(code);
            }
         }

         colorX = this.guiLeft + this.xSize / 2 - (this.colorBoxWidth * colorCodes.size() + this.colorBoxGap * (colorCodes.size() - 1)) / 2;
         colorY = this.guiTop + 200;

         for(Iterator var34 = colorCodes.iterator(); var34.hasNext(); colorX += this.colorBoxWidth + this.colorBoxGap) {
            code = (EnumChatFormatting)var34.next();
            color = LOTRReflectionClient.getFormattingColor(code);
            float[] rgb = (new Color(color)).getColorComponents((float[])null);
            GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0F);
            boolean mouseOver = i >= colorX && i < colorX + this.colorBoxWidth && j >= colorY && j < colorY + this.colorBoxWidth;
            GL11.glDisable(3553);
            this.func_73729_b(colorX, colorY + (mouseOver ? -1 : 0), 0, 0, this.colorBoxWidth, this.colorBoxWidth);
            GL11.glEnable(3553);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.displayedColorBoxes.put(code, Pair.of(colorX, colorY));
         }
      }

      if (this.displayedTitles.size() > 12) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         scroll = (int)(this.currentScroll * (float)(this.scrollBarHeight - this.scrollWidgetHeight));
         int x1 = this.guiLeft + this.scrollBarX;
         colorX = this.guiTop + this.scrollBarY + scroll;
         colorY = x1 + this.scrollWidgetWidth;
         y2 = colorX + this.scrollWidgetHeight;
         func_73734_a(x1, colorX, colorY, y2, -1426063361);
      }

      this.selectButton.field_146124_l = this.selectedTitle != null;
      this.removeButton.field_146124_l = this.currentTitle != null;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      super.func_73863_a(i, j, f);
      Iterator var27 = this.displayedTitleInfo.entrySet().iterator();

      while(var27.hasNext()) {
         Entry entry = (Entry)var27.next();
         LOTRTitle title = (LOTRTitle)entry.getKey();
         desc = title.getDescription(this.field_146297_k.field_71439_g);
         titleX = (Integer)((Pair)((Pair)entry.getValue()).getRight()).getLeft();
         titleY = (Integer)((Pair)((Pair)entry.getValue()).getRight()).getRight();
         boolean mouseOver = (Boolean)((Pair)entry.getValue()).getLeft();
         if (mouseOver) {
            int stringWidth = 200;
            List titleLines = this.field_146289_q.func_78271_c(desc, stringWidth);
            int offset = 10;
            int x = i + offset;
            y = j + offset;
            this.func_146283_a(titleLines, x, y);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }

   }

   private void setupScrollBar(int i, int j) {
      boolean isMouseDown = Mouse.isButtonDown(0);
      int i1 = this.guiLeft + this.scrollBarX;
      int j1 = this.guiTop + this.scrollBarY;
      int i2 = i1 + this.scrollBarWidth;
      int j2 = j1 + this.scrollBarHeight;
      if (!this.wasMouseDown && isMouseDown && i >= i1 && j >= j1 && i < i2 && j < j2) {
         this.isScrolling = true;
      }

      if (!isMouseDown) {
         this.isScrolling = false;
      }

      this.wasMouseDown = isMouseDown;
      if (this.isScrolling) {
         this.currentScroll = ((float)(j - j1) - (float)this.scrollWidgetHeight / 2.0F) / ((float)(j2 - j1) - (float)this.scrollWidgetHeight);
         this.currentScroll = MathHelper.func_76131_a(this.currentScroll, 0.0F, 1.0F);
      }

   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         LOTRPacketSelectTitle packet;
         if (button != this.selectButton || this.currentTitle != null && this.selectedTitle == this.currentTitle.getTitle() && this.selectedColor == this.currentTitle.getColor()) {
            if (button == this.removeButton) {
               packet = new LOTRPacketSelectTitle((LOTRTitle.PlayerTitle)null);
               LOTRPacketHandler.networkWrapper.sendToServer(packet);
            } else {
               super.func_146284_a(button);
            }
         } else {
            packet = new LOTRPacketSelectTitle(new LOTRTitle.PlayerTitle(this.selectedTitle, this.selectedColor));
            LOTRPacketHandler.networkWrapper.sendToServer(packet);
         }
      }

   }

   protected void func_73864_a(int i, int j, int mouse) {
      if (mouse == 0) {
         Iterator var4 = this.displayedTitleInfo.entrySet().iterator();

         Entry entry;
         while(var4.hasNext()) {
            entry = (Entry)var4.next();
            LOTRTitle title = (LOTRTitle)entry.getKey();
            boolean mouseOver = (Boolean)((Pair)entry.getValue()).getLeft();
            if (mouseOver && title.canPlayerUse(this.field_146297_k.field_71439_g)) {
               this.selectedTitle = title;
               this.selectedColor = EnumChatFormatting.WHITE;
               return;
            }
         }

         if (!this.displayedColorBoxes.isEmpty()) {
            var4 = this.displayedColorBoxes.entrySet().iterator();

            while(var4.hasNext()) {
               entry = (Entry)var4.next();
               EnumChatFormatting color = (EnumChatFormatting)entry.getKey();
               int colorX = (Integer)((Pair)entry.getValue()).getLeft();
               int colorY = (Integer)((Pair)entry.getValue()).getRight();
               if (i >= colorX && i < colorX + this.colorBoxWidth && j >= colorY && j < colorY + this.colorBoxWidth) {
                  this.selectedColor = color;
                  break;
               }
            }
         }
      }

      super.func_73864_a(i, j, mouse);
   }

   public void func_146274_d() {
      super.func_146274_d();
      int i = Mouse.getEventDWheel();
      if (i != 0) {
         i = Integer.signum(i);
         int j = this.displayedTitles.size() - 12;
         this.currentScroll -= (float)i / (float)j;
         this.currentScroll = MathHelper.func_76131_a(this.currentScroll, 0.0F, 1.0F);
      }

   }
}
