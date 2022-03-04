package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiTradeButton extends GuiButton {
   public LOTRGuiTradeButton(int i, int j, int k) {
      super(i, j, k, 18, 18, "Trade");
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      mc.func_110434_K().func_110577_a(LOTRGuiTrade.guiTexture);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
      int hoverState = this.func_146114_a(this.field_146123_n);
      func_146110_a(this.field_146128_h, this.field_146129_i, 176.0F, (float)(hoverState * 18), this.field_146120_f, this.field_146121_g, 512.0F, 512.0F);
      this.func_146119_b(mc, i, j);
   }
}
