package lotr.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRDimension;
import lotr.common.quest.LOTRMiniQuestWelcome;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiMenu extends LOTRGuiScreenBase {
   public static final ResourceLocation menuIconsTexture = new ResourceLocation("lotr:gui/menu_icons.png");
   public static Class lastMenuScreen = null;

   public void func_73866_w_() {
      super.func_73866_w_();
      resetLastMenuScreen();
      int midX = this.field_146294_l / 2;
      int midY = this.field_146295_m / 2;
      int buttonGap = 10;
      int buttonSize = 32;
      this.field_146292_n.add(new LOTRGuiButtonMenu(this, 2, 0, 0, LOTRGuiAchievements.class, StatCollector.func_74838_a("lotr.gui.achievements"), 30));
      this.field_146292_n.add(new LOTRGuiButtonMenu(this, 3, 0, 0, LOTRGuiMap.class, StatCollector.func_74838_a("lotr.gui.map"), 50));
      this.field_146292_n.add(new LOTRGuiButtonMenu(this, 4, 0, 0, LOTRGuiFactions.class, StatCollector.func_74838_a("lotr.gui.factions"), 33));
      this.field_146292_n.add(new LOTRGuiButtonMenu(this, 0, 0, 0, (Class)null, "?", -1));
      this.field_146292_n.add(new LOTRGuiButtonMenu(this, 6, 0, 0, LOTRGuiFellowships.class, StatCollector.func_74838_a("lotr.gui.fellowships"), 25));
      this.field_146292_n.add(new LOTRGuiButtonMenu(this, 7, 0, 0, LOTRGuiTitles.class, StatCollector.func_74838_a("lotr.gui.titles"), 20));
      this.field_146292_n.add(new LOTRGuiButtonMenu(this, 5, 0, 0, LOTRGuiShields.class, StatCollector.func_74838_a("lotr.gui.shields"), 31));
      this.field_146292_n.add(new LOTRGuiButtonMenu(this, 1, 0, 0, LOTRGuiOptions.class, StatCollector.func_74838_a("lotr.gui.options"), 24));
      List menuButtons = new ArrayList();
      Iterator var6 = this.field_146292_n.iterator();

      while(var6.hasNext()) {
         Object obj = var6.next();
         if (obj instanceof LOTRGuiButtonMenu) {
            LOTRGuiButtonMenu button = (LOTRGuiButtonMenu)obj;
            button.field_146124_l = button.canDisplayMenu();
            menuButtons.add(button);
         }
      }

      int numButtons = menuButtons.size();
      int numTopRowButtons = (numButtons - 1) / 2 + 1;
      int numBtmRowButtons = numButtons - numTopRowButtons;
      int topRowLeft = midX - (numTopRowButtons * buttonSize + (numTopRowButtons - 1) * buttonGap) / 2;
      int btmRowLeft = midX - (numBtmRowButtons * buttonSize + (numBtmRowButtons - 1) * buttonGap) / 2;

      for(int l = 0; l < numButtons; ++l) {
         LOTRGuiButtonMenu button = (LOTRGuiButtonMenu)menuButtons.get(l);
         if (l < numTopRowButtons) {
            button.field_146128_h = topRowLeft + l * (buttonSize + buttonGap);
            button.field_146129_i = midY - buttonGap / 2 - buttonSize;
         } else {
            button.field_146128_h = btmRowLeft + (l - numTopRowButtons) * (buttonSize + buttonGap);
            button.field_146129_i = midY + buttonGap / 2;
         }
      }

   }

   public void func_73863_a(int i, int j, float f) {
      this.func_146276_q_();
      String title = StatCollector.func_74837_a("lotr.gui.menu", new Object[]{LOTRDimension.getCurrentDimensionWithFallback(this.field_146297_k.field_71441_e).getDimensionName()});
      this.field_146289_q.func_78261_a(title, this.field_146294_l / 2 - this.field_146289_q.func_78256_a(title) / 2, this.field_146295_m / 2 - 80, 16777215);
      super.func_73863_a(i, j, f);
      Iterator var5 = this.field_146292_n.iterator();

      while(var5.hasNext()) {
         Object obj = var5.next();
         if (obj instanceof LOTRGuiButtonMenu) {
            LOTRGuiButtonMenu button = (LOTRGuiButtonMenu)obj;
            if (button.func_146115_a() && button.field_146126_j != null) {
               float z = this.field_73735_i;
               int stringWidth = true;
               this.func_146279_a(button.field_146126_j, i, j);
               GL11.glDisable(2896);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               this.field_73735_i = z;
            }
         }
      }

   }

   protected void func_73869_a(char c, int i) {
      Iterator var3 = this.field_146292_n.iterator();

      while(var3.hasNext()) {
         Object obj = var3.next();
         if (obj instanceof LOTRGuiButtonMenu) {
            LOTRGuiButtonMenu button = (LOTRGuiButtonMenu)obj;
            if (button.field_146125_m && button.field_146124_l && button.menuKeyCode >= 0 && i == button.menuKeyCode) {
               this.func_146284_a(button);
               return;
            }
         }
      }

      super.func_73869_a(c, i);
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l && button instanceof LOTRGuiButtonMenu) {
         LOTRGuiButtonMenu buttonMenu = (LOTRGuiButtonMenu)button;
         LOTRGuiMenuBase screen = buttonMenu.openMenu();
         if (screen != null) {
            this.field_146297_k.func_147108_a(screen);
            lastMenuScreen = screen.getClass();
            return;
         }
      }

      super.func_146284_a(button);
   }

   public static void resetLastMenuScreen() {
      lastMenuScreen = null;
   }

   public static GuiScreen openMenu(EntityPlayer entityplayer) {
      boolean[] map_factions = LOTRMiniQuestWelcome.forceMenu_Map_Factions(entityplayer);
      if (map_factions[0]) {
         return new LOTRGuiMap();
      } else if (map_factions[1]) {
         return new LOTRGuiFactions();
      } else {
         if (lastMenuScreen != null) {
            try {
               return (GuiScreen)lastMenuScreen.newInstance();
            } catch (Exception var3) {
               var3.printStackTrace();
            }
         }

         return new LOTRGuiMenu();
      }
   }
}
