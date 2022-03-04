package lotr.client.gui;

import java.util.Arrays;
import java.util.List;
import lotr.client.LOTRClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonPledge extends GuiButton {
   private LOTRGuiFactions parentGUI;
   public boolean isBroken;
   public List displayLines;

   public LOTRGuiButtonPledge(LOTRGuiFactions gui, int i, int x, int y, String s) {
      super(i, x, y, 32, 32, s);
      this.parentGUI = gui;
   }

   public void setDisplayLines(String... s) {
      if (s == null) {
         this.displayLines = null;
      } else {
         this.displayLines = Arrays.asList(s);
      }

   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         FontRenderer fontrenderer = mc.field_71466_p;
         mc.func_110434_K().func_110577_a(LOTRClientProxy.alignmentTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         int state = this.func_146114_a(this.field_146123_n);
         this.func_73729_b(this.field_146128_h, this.field_146129_i, 0 + state * this.field_146120_f, 180, this.field_146120_f, this.field_146121_g);
         this.func_146119_b(mc, i, j);
         if (this.func_146115_a() && this.displayLines != null) {
            float z = this.field_73735_i;
            int stringWidth = true;
            this.parentGUI.drawButtonHoveringText(this.displayLines, i, j);
            GL11.glDisable(2896);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_73735_i = z;
         }
      }

   }

   public int func_146114_a(boolean flag) {
      if (this.isBroken) {
         return flag ? 4 : 3;
      } else if (!this.field_146124_l) {
         return 0;
      } else {
         return flag ? 2 : 1;
      }
   }
}
