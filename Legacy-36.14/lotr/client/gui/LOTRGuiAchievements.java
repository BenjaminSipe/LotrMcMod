package lotr.client.gui;

import com.google.common.math.IntMath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LOTRGuiAchievements extends LOTRGuiMenuBase {
   public static ResourceLocation pageTexture = new ResourceLocation("lotr:gui/achievements/page.png");
   public static ResourceLocation iconsTexture = new ResourceLocation("lotr:gui/achievements/icons.png");
   private static LOTRDimension currentDimension;
   private static LOTRDimension prevDimension;
   private static LOTRAchievement.Category currentCategory;
   private ArrayList currentCategoryTakenAchievements = new ArrayList();
   private ArrayList currentCategoryUntakenAchievements = new ArrayList();
   private int currentCategoryTakenCount;
   private int currentCategoryUntakenCount;
   private LOTRGuiButtonAchievements buttonCategoryPrev;
   private LOTRGuiButtonAchievements buttonCategoryNext;
   private int totalTakenCount;
   private int totalAvailableCount;
   private float currentScroll = 0.0F;
   private boolean isScrolling = false;
   private boolean wasMouseDown;
   private static final int scrollBarWidth = 12;
   private static final int scrollBarHeight = 200;
   private static final int scrollWidgetWidth = 10;
   private static final int scrollWidgetHeight = 17;
   private static final int catScrollWidth = 152;
   private static final int catScrollHeight = 10;
   private int catScrollAreaX0;
   private int catScrollAreaX1;
   private int catScrollAreaY0;
   private int catScrollAreaY1;
   private boolean wasInCategoryScrollBar;
   private int prevMouseX;
   private int prevMouseY;
   private int mouseX;
   private int mouseY;

   public void func_73866_w_() {
      this.xSize = 220;
      super.func_73866_w_();
      this.field_146292_n.add(this.buttonCategoryPrev = new LOTRGuiButtonAchievements(0, true, this.guiLeft + 14, this.guiTop + 13));
      this.field_146292_n.add(this.buttonCategoryNext = new LOTRGuiButtonAchievements(1, false, this.guiLeft + 191, this.guiTop + 13));
      this.updateAchievementLists();
   }

   public void func_73876_c() {
      super.func_73876_c();
      this.updateAchievementLists();
      this.prevMouseX = this.mouseX;
      this.prevMouseY = this.mouseY;
      this.wasInCategoryScrollBar = this.isMouseInCategoryScrollBar();
   }

   private boolean isMouseInCategoryScrollBar() {
      return Mouse.isButtonDown(0) && this.mouseX >= this.catScrollAreaX0 && this.mouseX < this.catScrollAreaX1 && this.mouseY >= this.catScrollAreaY0 && this.mouseY < this.catScrollAreaY1;
   }

   public void func_73863_a(int i, int j, float f) {
      this.mouseX = i;
      this.mouseY = j;
      if (this.wasInCategoryScrollBar) {
         int diff = this.mouseX - this.prevMouseX;
         boolean changed = false;
         if (diff >= 4) {
            this.prevCategory();
            changed = true;
         } else if (diff <= -4) {
            this.nextCategory();
            changed = true;
         }

         if (changed) {
            this.mouseX = this.prevMouseX;
            this.wasInCategoryScrollBar = false;
         }
      }

      boolean isMouseDown = Mouse.isButtonDown(0);
      int scrollBarX0 = this.guiLeft + 201;
      int scrollBarX1 = scrollBarX0 + 12;
      int scrollBarY0 = this.guiTop + 48;
      int scrollBarY1 = scrollBarY0 + 200;
      if (!this.wasMouseDown && isMouseDown && i >= scrollBarX0 && i < scrollBarX1 && j >= scrollBarY0 && j < scrollBarY1) {
         this.isScrolling = this.hasScrollBar();
      }

      if (!isMouseDown) {
         this.isScrolling = false;
      }

      this.wasMouseDown = isMouseDown;
      if (this.isScrolling) {
         this.currentScroll = ((float)(j - scrollBarY0) - 8.5F) / ((float)(scrollBarY1 - scrollBarY0) - 17.0F);
         this.currentScroll = Math.max(this.currentScroll, 0.0F);
         this.currentScroll = Math.min(this.currentScroll, 1.0F);
      }

      this.func_146276_q_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(pageTexture);
      this.func_73729_b(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
      String title = StatCollector.func_74837_a("lotr.gui.achievements.title", new Object[]{currentDimension.getDimensionName(), this.totalTakenCount, this.totalAvailableCount});
      this.drawCenteredString(title, this.guiLeft + this.xSize / 2, this.guiTop - 30, 16777215);
      String categoryName = currentCategory.getDisplayName();
      categoryName = StatCollector.func_74837_a("lotr.gui.achievements.category", new Object[]{categoryName, this.currentCategoryTakenCount, this.currentCategoryTakenCount + this.currentCategoryUntakenCount});
      this.drawCenteredString(categoryName, this.guiLeft + this.xSize / 2, this.guiTop + 28, 8019267);
      this.buttonCategoryPrev.buttonCategory = this.getCategoryAtRelativeIndex(-1);
      this.buttonCategoryNext.buttonCategory = this.getCategoryAtRelativeIndex(1);
      super.func_73863_a(i, j, f);
      int catScrollCentre = this.guiLeft + this.xSize / 2;
      int catScrollX = catScrollCentre - 76;
      int catScrollY = this.guiTop + 13;
      this.field_146297_k.func_110434_K().func_110577_a(iconsTexture);
      this.func_73729_b(catScrollX, catScrollY, 0, 100, 152, 10);
      this.catScrollAreaX0 = catScrollX;
      this.catScrollAreaX1 = catScrollX + 152;
      this.catScrollAreaY0 = catScrollY;
      this.catScrollAreaY1 = catScrollY + 10;
      int catWidth = 16;
      int catCentreWidth = 50;
      int catsEitherSide = (this.catScrollAreaX1 - this.catScrollAreaX0) / catWidth + 1;

      int l;
      for(l = -catsEitherSide; l <= catsEitherSide; ++l) {
         int thisCatWidth = l == 0 ? catCentreWidth : catWidth;
         int catX = catScrollCentre;
         int catX0;
         if (l != 0) {
            catX0 = Integer.signum(l);
            catX = catScrollCentre + (catCentreWidth + catWidth) / 2 * catX0;
            catX += (Math.abs(l) - 1) * catX0 * catWidth;
         }

         catX0 = catX - thisCatWidth / 2;
         int catX1 = catX + thisCatWidth;
         if (catX0 < this.catScrollAreaX0) {
            catX0 = this.catScrollAreaX0;
         }

         if (catX1 > this.catScrollAreaX1) {
            catX1 = this.catScrollAreaX1;
         }

         int catY0 = this.catScrollAreaY0;
         int catY1 = this.catScrollAreaY1;
         LOTRAchievement.Category thisCategory = this.getCategoryAtRelativeIndex(l);
         float[] catColors = thisCategory.getCategoryRGB();
         this.field_146297_k.func_110434_K().func_110577_a(iconsTexture);
         GL11.glColor4f(catColors[0], catColors[1], catColors[2], 1.0F);
         this.func_73729_b(catX0, catY0, catX0 - this.catScrollAreaX0 + 0, 100, catX1 - catX0, catY1 - catY0);
      }

      this.field_146297_k.func_110434_K().func_110577_a(iconsTexture);
      this.func_73729_b(catScrollX, catScrollY, 0, 110, 152, 10);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(iconsTexture);
      if (this.hasScrollBar()) {
         l = (int)(this.currentScroll * 181.0F);
         this.func_73729_b(scrollBarX0, scrollBarY0 + l, 190, 0, 10, 17);
      } else {
         this.func_73729_b(scrollBarX0, scrollBarY0, 200, 0, 10, 17);
      }

      this.drawAchievements(i, j);
   }

   private void drawAchievements(int mouseX, int mouseY) {
      RenderHelper.func_74520_c();
      GL11.glDisable(2896);
      GL11.glEnable(32826);
      GL11.glEnable(2903);
      int size = this.currentCategoryTakenCount + this.currentCategoryUntakenCount;
      int min = 0 + Math.round(this.currentScroll * (float)(size - 4));
      int max = 3 + Math.round(this.currentScroll * (float)(size - 4));
      if (max > size - 1) {
         max = size - 1;
      }

      for(int i = min; i <= max; ++i) {
         LOTRAchievement achievement;
         boolean hasAchievement;
         if (i < this.currentCategoryTakenCount) {
            achievement = (LOTRAchievement)this.currentCategoryTakenAchievements.get(i);
            hasAchievement = true;
         } else {
            achievement = (LOTRAchievement)this.currentCategoryUntakenAchievements.get(i - this.currentCategoryTakenCount);
            hasAchievement = false;
         }

         int offset = 47 + 50 * (i - min);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146297_k.func_110434_K().func_110577_a(iconsTexture);
         this.func_73729_b(this.guiLeft + 9, this.guiTop + offset, 0, hasAchievement ? 0 : 50, 190, 50);
         int iconLeft = this.guiLeft + 12;
         int iconTop = this.guiTop + offset + 3;
         GL11.glEnable(2896);
         GL11.glEnable(2884);
         renderItem.func_82406_b(this.field_146297_k.field_71466_p, this.field_146297_k.func_110434_K(), achievement.icon, iconLeft, iconTop);
         GL11.glDisable(2896);
         if (!hasAchievement) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 300.0F);
            func_73734_a(iconLeft, iconTop, iconLeft + 16, iconTop + 16, -2013265920);
            GL11.glPopMatrix();
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int textColour = hasAchievement ? 8019267 : 5652783;
         this.field_146297_k.field_71466_p.func_78276_b(achievement.getTitle(this.field_146297_k.field_71439_g), this.guiLeft + 33, this.guiTop + offset + 5, textColour);
         this.field_146297_k.field_71466_p.func_78279_b(achievement.getDescription(this.field_146297_k.field_71439_g), this.guiLeft + 12, this.guiTop + offset + 24, 184, textColour);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         if (hasAchievement) {
            this.field_146297_k.func_110434_K().func_110577_a(iconsTexture);
            this.func_73729_b(this.guiLeft + 179, this.guiTop + offset + 2, 190, 17, 16, 16);
         }
      }

      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button == this.buttonCategoryPrev) {
            this.prevCategory();
         } else if (button == this.buttonCategoryNext) {
            this.nextCategory();
         } else {
            super.func_146284_a(button);
         }
      }

   }

   private LOTRAchievement.Category getCategoryAtRelativeIndex(int i) {
      List categories = currentDimension.achievementCategories;
      int index = categories.indexOf(currentCategory);
      index += i;
      index = IntMath.mod(index, currentDimension.achievementCategories.size());
      return (LOTRAchievement.Category)currentDimension.achievementCategories.get(index);
   }

   private void prevCategory() {
      currentCategory = this.getCategoryAtRelativeIndex(-1);
      this.currentScroll = 0.0F;
   }

   private void nextCategory() {
      currentCategory = this.getCategoryAtRelativeIndex(1);
      this.currentScroll = 0.0F;
   }

   public void func_146274_d() {
      super.func_146274_d();
      int i = Mouse.getEventDWheel();
      if (i != 0 && this.hasScrollBar()) {
         int j = this.currentCategoryTakenCount + this.currentCategoryUntakenCount - 4;
         if (i > 0) {
            i = 1;
         }

         if (i < 0) {
            i = -1;
         }

         this.currentScroll = (float)((double)this.currentScroll - (double)i / (double)j);
         if (this.currentScroll < 0.0F) {
            this.currentScroll = 0.0F;
         }

         if (this.currentScroll > 1.0F) {
            this.currentScroll = 1.0F;
         }
      }

   }

   private boolean hasScrollBar() {
      return this.currentCategoryTakenCount + this.currentCategoryUntakenCount > 4;
   }

   private void updateAchievementLists() {
      currentDimension = LOTRDimension.getCurrentDimensionWithFallback(this.field_146297_k.field_71441_e);
      if (currentDimension != prevDimension) {
         currentCategory = (LOTRAchievement.Category)currentDimension.achievementCategories.get(0);
      }

      prevDimension = currentDimension;
      this.currentCategoryTakenAchievements.clear();
      this.currentCategoryUntakenAchievements.clear();
      Iterator it = currentCategory.list.iterator();

      LOTRAchievement achievement;
      while(it.hasNext()) {
         achievement = (LOTRAchievement)it.next();
         if (achievement.canPlayerEarn(this.field_146297_k.field_71439_g)) {
            if (LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).hasAchievement(achievement)) {
               this.currentCategoryTakenAchievements.add(achievement);
            } else {
               this.currentCategoryUntakenAchievements.add(achievement);
            }
         }
      }

      this.currentCategoryTakenCount = this.currentCategoryTakenAchievements.size();
      this.currentCategoryUntakenCount = this.currentCategoryUntakenAchievements.size();
      this.totalTakenCount = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getEarnedAchievements(currentDimension).size();
      this.totalAvailableCount = 0;
      it = currentDimension.allAchievements.iterator();

      while(it.hasNext()) {
         achievement = (LOTRAchievement)it.next();
         if (achievement.canPlayerEarn(this.field_146297_k.field_71439_g)) {
            ++this.totalAvailableCount;
         }
      }

      Comparator sorter = LOTRAchievement.sortForDisplay(this.field_146297_k.field_71439_g);
      Collections.sort(this.currentCategoryTakenAchievements, sorter);
      Collections.sort(this.currentCategoryUntakenAchievements, sorter);
   }
}
