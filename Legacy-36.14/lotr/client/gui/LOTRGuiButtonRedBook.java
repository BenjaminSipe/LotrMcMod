package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonRedBook extends GuiButton {
   public LOTRGuiButtonRedBook(int i, int x, int y, int w, int h, String s) {
      super(i, x, y, w, h, s);
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         FontRenderer fontrenderer = mc.field_71466_p;
         mc.func_110434_K().func_110577_a(LOTRGuiRedBook.guiTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         int k = this.func_146114_a(this.field_146123_n);
         func_146110_a(this.field_146128_h, this.field_146129_i, 170.0F, (float)(256 + k * 20), this.field_146120_f, this.field_146121_g, 512.0F, 512.0F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         func_146110_a(this.field_146128_h, this.field_146129_i, 170.0F, 316.0F, this.field_146120_f / 2, this.field_146121_g, 512.0F, 512.0F);
         func_146110_a(this.field_146128_h + this.field_146120_f / 2, this.field_146129_i, (float)(370 - this.field_146120_f / 2), 316.0F, this.field_146120_f / 2, this.field_146121_g, 512.0F, 512.0F);
         this.func_146119_b(mc, i, j);
         int color = 8019267;
         if (!this.field_146124_l) {
            color = 5521198;
         } else if (this.field_146123_n) {
            color = 8019267;
         }

         fontrenderer.func_78276_b(this.field_146126_j, this.field_146128_h + this.field_146120_f / 2 - fontrenderer.func_78256_a(this.field_146126_j) / 2, this.field_146129_i + (this.field_146121_g - 8) / 2, color);
      }

   }
}
