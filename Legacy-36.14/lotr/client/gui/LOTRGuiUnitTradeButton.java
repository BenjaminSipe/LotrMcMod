package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiUnitTradeButton extends GuiButton {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/npc/unit_trade_buttons.png");

   public LOTRGuiUnitTradeButton(int i, int j, int k, int width, int height) {
      super(i, j, k, width, height, "");
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         mc.func_110434_K().func_110577_a(guiTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         boolean flag = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         int k = this.field_146127_k * 19;
         int l = 0;
         if (!this.field_146124_l) {
            l += this.field_146120_f * 2;
         } else if (flag) {
            l += this.field_146120_f;
         }

         this.func_73729_b(this.field_146128_h, this.field_146129_i, l, k, this.field_146120_f, this.field_146121_g);
      }

   }
}
