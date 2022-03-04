package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonLeftRight extends GuiButton {
   private static ResourceLocation texture = new ResourceLocation("lotr:gui/widgets.png");
   private boolean leftOrRight;

   public LOTRGuiButtonLeftRight(int i, boolean flag, int j, int k, String s) {
      super(i, j, k, 120, 20, s);
      this.leftOrRight = flag;
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         FontRenderer fontrenderer = mc.field_71466_p;
         mc.func_110434_K().func_110577_a(texture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         int k = this.func_146114_a(this.field_146123_n);
         this.func_73729_b(this.field_146128_h, this.field_146129_i, this.leftOrRight ? 0 : 136, k * 20, this.field_146120_f, this.field_146121_g);
         this.func_146119_b(mc, i, j);
         int l = 14737632;
         if (!this.field_146124_l) {
            l = -6250336;
         } else if (this.field_146123_n) {
            l = 16777120;
         }

         if (this.leftOrRight) {
            this.func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + 67, this.field_146129_i + (this.field_146121_g - 8) / 2, l);
         } else {
            this.func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f - 67, this.field_146129_i + (this.field_146121_g - 8) / 2, l);
         }
      }

   }
}
