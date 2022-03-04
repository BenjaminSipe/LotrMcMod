package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;

public class LOTRGuiButtonOptions extends GuiButton {
   public String baseDisplayString;

   public LOTRGuiButtonOptions(int i, int j, int k, int l, int i1, String s) {
      super(i, j, k, l, i1, s);
      this.baseDisplayString = s;
   }

   private String getDescription() {
      return StatCollector.func_74838_a(this.baseDisplayString + ".desc.on") + "\n\n" + StatCollector.func_74838_a(this.baseDisplayString + ".desc.off");
   }

   public void setState(String s) {
      this.field_146126_j = StatCollector.func_74838_a(this.baseDisplayString) + ": " + s;
   }

   public void setState(boolean flag) {
      this.setState(flag ? StatCollector.func_74838_a("lotr.gui.button.on") : StatCollector.func_74838_a("lotr.gui.button.off"));
   }

   public void drawTooltip(Minecraft mc, int i, int j) {
      if (this.field_146124_l && this.func_146115_a()) {
         String s = this.getDescription();
         int border = 3;
         int stringWidth = 200;
         int stringHeight = mc.field_71466_p.func_78271_c(s, stringWidth).size() * mc.field_71466_p.field_78288_b;
         int offset = 10;
         i += offset;
         j += offset;
         func_73734_a(i, j, i + stringWidth + border * 2, j + stringHeight + border * 2, -1073741824);
         mc.field_71466_p.func_78279_b(s, i + border, j + border, stringWidth, 16777215);
      }

   }
}
