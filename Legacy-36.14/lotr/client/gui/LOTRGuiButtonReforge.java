package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonReforge extends GuiButton {
   private final int minU;
   private final int minV;

   public LOTRGuiButtonReforge(int i, int x, int y, int u, int v) {
      super(i, x, y, 20, 20, "");
      this.minU = u;
      this.minV = v;
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         mc.func_110434_K().func_110577_a(LOTRGuiAnvil.anvilTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_146123_n = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         this.func_73729_b(this.field_146128_h, this.field_146129_i, this.minU + (this.field_146123_n ? this.field_146120_f : 0), this.minV, this.field_146120_f, this.field_146121_g);
         this.func_146119_b(mc, i, j);
      }

   }
}
