package lotr.client.gui.map;

import java.util.function.BooleanSupplier;
import lotr.client.gui.BasicIngameScreen;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class MapWidget implements IGuiEventListener {
   private final MiddleEarthMapScreen parent;
   private final int xPos;
   private final int yPos;
   public final int width;
   private String tooltip;
   private Object[] tooltipFormatArgs;
   private final int texUBase;
   private final int texVBase;
   private int texUOffset;
   private int texVOffset;
   public boolean visible = true;
   private final BooleanSupplier onPress;

   public MapWidget(MiddleEarthMapScreen parent, int x, int y, int w, String s, int u, int v, BooleanSupplier press) {
      this.parent = parent;
      this.xPos = x;
      this.yPos = y;
      this.width = w;
      this.tooltip = s;
      this.tooltipFormatArgs = new Object[0];
      this.texUBase = u;
      this.texVBase = v;
      this.onPress = press;
   }

   public int getXPos() {
      return this.xPos;
   }

   public int getYPos() {
      return this.yPos;
   }

   public ITextComponent getTooltip() {
      return new TranslationTextComponent("gui.lotr.map.widget." + this.tooltip, this.tooltipFormatArgs);
   }

   public void setTooltip(String s) {
      this.setTooltip(s);
   }

   public void setTooltip(String s, Object... formatArgs) {
      this.tooltip = s;
      this.tooltipFormatArgs = formatArgs;
   }

   public void setTexUOffset(int i) {
      this.texUOffset = i;
   }

   public void setTexVOffset(int i) {
      this.texVOffset = i;
   }

   public int getTexU() {
      return this.texUBase + this.texUOffset * this.width;
   }

   public int getTexV() {
      return this.texVBase + this.texVOffset * this.width;
   }

   public boolean func_231047_b_(double mouseX, double mouseY) {
      return this.visible && !this.parent.hasOverlay() && mouseX >= (double)this.xPos && mouseX < (double)(this.xPos + this.width) && mouseY >= (double)this.yPos && mouseY < (double)(this.yPos + this.width);
   }

   public boolean func_231044_a_(double x, double y, int mouseCode) {
      if (this.visible && this.func_231047_b_(x, y) && mouseCode == 0) {
         boolean flag = this.onPress.getAsBoolean();
         if (flag) {
            BasicIngameScreen.playButtonClick();
            return true;
         }
      }

      return false;
   }
}
