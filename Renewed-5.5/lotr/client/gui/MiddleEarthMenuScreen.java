package lotr.client.gui;

import lotr.client.LOTRKeyHandler;
import lotr.client.gui.widget.button.LeftRightButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class MiddleEarthMenuScreen extends BasicIngameScreen {
   public int xSize = 200;
   public int ySize = 256;
   public int guiLeft;
   public int guiTop;
   protected Button buttonMenuReturn;

   public MiddleEarthMenuScreen(ITextComponent titleComponent) {
      super(titleComponent);
   }

   public void func_231160_c_() {
      super.func_231160_c_();
      this.guiLeft = (this.field_230708_k_ - this.xSize) / 2;
      this.guiTop = (this.field_230709_l_ - this.ySize) / 2;
      int buttonW = 100;
      int buttonH = 20;
      int buttonGap = 40;
      this.buttonMenuReturn = (Button)this.func_230480_a_(new LeftRightButton(0, this.guiTop + (this.ySize + buttonH) / 4, buttonW, buttonH, true, new TranslationTextComponent("gui.lotr.menu.return"), (b) -> {
         this.field_230706_i_.func_147108_a(new MiddleEarthMasterMenuScreen());
      }));
      this.buttonMenuReturn.field_230690_l_ = Math.max(0, this.guiLeft - buttonGap - buttonW);
   }

   public boolean func_231046_a_(int key, int scan, int param3) {
      if (LOTRKeyHandler.KEY_BIND_MENU.func_197976_a(key, scan)) {
         this.field_230706_i_.func_147108_a(new MiddleEarthMasterMenuScreen());
         return true;
      } else {
         return super.func_231046_a_(key, scan, param3);
      }
   }
}
