package lotr.client.gui;

import lotr.client.LOTRKeyHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.StatCollector;

public abstract class LOTRGuiMenuBase extends LOTRGuiScreenBase {
   public static RenderItem renderItem = new RenderItem();
   public int xSize = 200;
   public int ySize = 256;
   public int guiLeft;
   public int guiTop;
   protected GuiButton buttonMenuReturn;

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_146294_l - this.xSize) / 2;
      this.guiTop = (this.field_146295_m - this.ySize) / 2;
      int buttonW = true;
      int buttonH = 20;
      int buttonGap = 35;
      int minGap = 10;
      this.field_146292_n.add(this.buttonMenuReturn = new LOTRGuiButtonLeftRight(1000, true, 0, this.guiTop + (this.ySize + buttonH) / 4, StatCollector.func_74838_a("lotr.gui.menuButton")));
      this.buttonMenuReturn.field_146128_h = Math.min(0 + buttonGap, this.guiLeft - minGap - this.buttonMenuReturn.field_146120_f);
   }

   protected void func_73869_a(char c, int i) {
      if (i == LOTRKeyHandler.keyBindingMenu.func_151463_i()) {
         this.field_146297_k.func_147108_a(new LOTRGuiMenu());
      } else {
         super.func_73869_a(c, i);
      }
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l && button == this.buttonMenuReturn) {
         this.field_146297_k.func_147108_a(new LOTRGuiMenu());
      }

      super.func_146284_a(button);
   }
}
