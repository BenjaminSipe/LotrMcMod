package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonCoinExchange extends GuiButton {
   public LOTRGuiButtonCoinExchange(int i, int j, int k) {
      super(i, j, k, 32, 17, "");
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         FontRenderer fontrenderer = mc.field_71466_p;
         mc.func_110434_K().func_110577_a(LOTRGuiCoinExchange.guiTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         int k = this.func_146114_a(this.field_146123_n);
         int u = 176 + this.field_146127_k * this.field_146120_f;
         int v = 0 + k * this.field_146121_g;
         this.func_73729_b(this.field_146128_h, this.field_146129_i, u, v, this.field_146120_f, this.field_146121_g);
         this.func_146119_b(mc, i, j);
      }

   }
}
