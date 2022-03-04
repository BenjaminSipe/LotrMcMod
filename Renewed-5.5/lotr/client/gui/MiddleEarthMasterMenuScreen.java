package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.client.gui.map.MiddleEarthMapScreen;
import lotr.client.gui.widget.button.MiddleEarthMenuButton;
import lotr.common.init.LOTRDimensions;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MiddleEarthMasterMenuScreen extends BasicIngameScreen {
   public static final ResourceLocation MENU_ICONS = new ResourceLocation("lotr", "textures/gui/menu_icons.png");
   public static Class lastMenuScreen = null;

   public MiddleEarthMasterMenuScreen() {
      super(new StringTextComponent("MENU"));
   }

   public void func_231160_c_() {
      super.func_231160_c_();
      resetLastMenuScreen();
      int midX = this.field_230708_k_ / 2;
      int midY = this.field_230709_l_ / 2;
      int buttonGap = 10;
      int buttonSize = 32;
      this.func_230480_a_(new MiddleEarthMenuButton(0, 0, MiddleEarthMapScreen.class, new TranslationTextComponent("gui.lotr.menu.map"), 3, 77));
      this.func_230480_a_(new MiddleEarthMenuButton(0, 0, MiddleEarthFactionsScreen.class, new TranslationTextComponent("gui.lotr.menu.factions"), 4, 70));
      this.func_230480_a_(new MiddleEarthMenuButton(0, 0, (Class)null, new StringTextComponent("?"), 0, -1));
      List menuButtonsToArrange = new ArrayList();
      Iterator var6 = this.field_230710_m_.iterator();

      while(var6.hasNext()) {
         Widget widget = (Widget)var6.next();
         if (widget instanceof MiddleEarthMenuButton) {
            MiddleEarthMenuButton menuButton = (MiddleEarthMenuButton)widget;
            menuButton.field_230693_o_ = menuButton.canDisplayMenu();
            menuButtonsToArrange.add(menuButton);
         }
      }

      int numButtons = menuButtonsToArrange.size();
      int numTopRowButtons = (numButtons - 1) / 2 + 1;
      int numBtmRowButtons = numButtons - numTopRowButtons;
      int topRowLeft = midX - (numTopRowButtons * buttonSize + (numTopRowButtons - 1) * buttonGap) / 2;
      int btmRowLeft = midX - (numBtmRowButtons * buttonSize + (numBtmRowButtons - 1) * buttonGap) / 2;

      for(int l = 0; l < numButtons; ++l) {
         MiddleEarthMenuButton button = (MiddleEarthMenuButton)menuButtonsToArrange.get(l);
         if (l < numTopRowButtons) {
            button.field_230690_l_ = topRowLeft + l * (buttonSize + buttonGap);
            button.field_230691_m_ = midY - buttonGap / 2 - buttonSize;
         } else {
            button.field_230690_l_ = btmRowLeft + (l - numTopRowButtons) * (buttonSize + buttonGap);
            button.field_230691_m_ = midY + buttonGap / 2;
         }
      }

   }

   public static void resetLastMenuScreen() {
      lastMenuScreen = null;
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float tick) {
      this.func_230446_a_(matStack);
      ITextComponent dimensionName = LOTRDimensions.getDisplayName(LOTRDimensions.getCurrentLOTRDimensionOrFallback(this.field_230706_i_.field_71441_e));
      ITextComponent title = new TranslationTextComponent("gui.lotr.menu", new Object[]{dimensionName});
      this.field_230712_o_.func_243246_a(matStack, title, (float)(this.field_230708_k_ / 2 - this.field_230712_o_.func_238414_a_(title) / 2), (float)(this.field_230709_l_ / 2 - 80), 16777215);
      super.func_230430_a_(matStack, mouseX, mouseY, tick);
      Iterator var7 = this.field_230710_m_.iterator();

      while(var7.hasNext()) {
         Widget widget = (Widget)var7.next();
         if (widget instanceof MiddleEarthMenuButton) {
            MiddleEarthMenuButton menuButton = (MiddleEarthMenuButton)widget;
            if (menuButton.func_230449_g_() && menuButton.func_230458_i_() != null) {
               this.func_238652_a_(matStack, menuButton.func_230458_i_(), mouseX, mouseY);
            }
         }
      }

   }

   public boolean func_231046_a_(int key, int scan, int param3) {
      Iterator var4 = this.field_230710_m_.iterator();

      while(var4.hasNext()) {
         Widget widget = (Widget)var4.next();
         if (widget instanceof MiddleEarthMenuButton) {
            MiddleEarthMenuButton menuButton = (MiddleEarthMenuButton)widget;
            if (menuButton.field_230694_p_ && menuButton.field_230693_o_ && menuButton.menuKeyCode >= 0 && key == menuButton.menuKeyCode) {
               menuButton.func_230930_b_();
               return true;
            }
         }
      }

      return super.func_231046_a_(key, scan, param3);
   }

   public static Screen openMenu(PlayerEntity player) {
      if (lastMenuScreen != null) {
         try {
            return (Screen)lastMenuScreen.newInstance();
         } catch (Exception var2) {
            var2.printStackTrace();
         }
      }

      return new MiddleEarthMasterMenuScreen();
   }
}
