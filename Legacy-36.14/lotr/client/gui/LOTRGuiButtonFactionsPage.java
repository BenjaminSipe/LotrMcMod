package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonFactionsPage extends GuiButton {
   private boolean leftOrRight;

   public LOTRGuiButtonFactionsPage(int i, int x, int y, boolean flag) {
      super(i, x, y, 16, 16, "");
      this.leftOrRight = flag;
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         FontRenderer fontrenderer = mc.field_71466_p;
         mc.func_110434_K().func_110577_a(LOTRGuiFactions.factionsTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         int k = this.func_146114_a(this.field_146123_n);
         this.func_73729_b(this.field_146128_h, this.field_146129_i, 0 + k * 16, this.leftOrRight ? 176 : 160, this.field_146120_f, this.field_146121_g);
         this.func_146119_b(mc, i, j);
         if (this.field_146124_l) {
            FontRenderer fr = mc.field_71466_p;
            int stringBorder = 0;
            int stringY = this.field_146129_i + this.field_146121_g / 2 - fr.field_78288_b / 2;
            String s;
            if (this.leftOrRight) {
               s = "->";
               fr.func_78276_b(s, this.field_146128_h - stringBorder - fr.func_78256_a(s), stringY, 0);
            } else {
               s = "<-";
               fr.func_78276_b(s, this.field_146128_h + this.field_146120_f + stringBorder, stringY, 0);
            }
         }
      }

   }
}
