package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonShieldsArrows extends GuiButton {
   private static ResourceLocation texture = new ResourceLocation("lotr:gui/widgets.png");
   private boolean leftOrRight;

   public LOTRGuiButtonShieldsArrows(int i, boolean flag, int j, int k) {
      super(i, j, k, 20, 20, "");
      this.leftOrRight = flag;
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         mc.func_110434_K().func_110577_a(texture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         int k = this.func_146114_a(this.field_146123_n);
         this.func_73729_b(this.field_146128_h, this.field_146129_i, this.leftOrRight ? 0 : 20, 60 + k * 20, this.field_146120_f, this.field_146121_g);
      }

   }
}
