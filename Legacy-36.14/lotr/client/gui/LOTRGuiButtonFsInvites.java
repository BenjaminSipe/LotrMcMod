package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonFsInvites extends GuiButton {
   public LOTRGuiButtonFsInvites(int i, int x, int y, String s) {
      super(i, x, y, 16, 16, s);
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         FontRenderer fontrenderer = mc.field_71466_p;
         mc.func_110434_K().func_110577_a(LOTRGuiFellowships.iconsTextures);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         this.func_73729_b(this.field_146128_h, this.field_146129_i, 80, 0 + (this.field_146123_n ? this.field_146121_g : 0), this.field_146120_f, this.field_146121_g);
         this.func_146119_b(mc, i, j);
         int color = 0;
         fontrenderer.func_78276_b(this.field_146126_j, this.field_146128_h + this.field_146120_f / 2 - fontrenderer.func_78256_a(this.field_146126_j) / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, color);
      }

   }
}
