package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonBanner extends GuiButton {
   public boolean activated;
   private int iconU;
   private int iconV;

   public LOTRGuiButtonBanner(int i, int x, int y, int u, int v) {
      super(i, x, y, 16, 16, "");
      this.iconU = u;
      this.iconV = v;
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         FontRenderer fontrenderer = mc.field_71466_p;
         mc.func_110434_K().func_110577_a(LOTRGuiBanner.bannerTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         int state = this.func_146114_a(this.field_146123_n);
         this.func_73729_b(this.field_146128_h, this.field_146129_i, this.iconU + state % 2 * this.field_146120_f, this.iconV + state / 2 * this.field_146121_g, this.field_146120_f, this.field_146121_g);
         this.func_146119_b(mc, i, j);
      }

   }

   public int func_146114_a(boolean mouseover) {
      return (this.activated ? 0 : 2) + (mouseover ? 1 : 0);
   }
}
