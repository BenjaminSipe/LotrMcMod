package lotr.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiButtonBannerWhitelistSlots extends GuiButton {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/banner_edit.png");

   public LOTRGuiButtonBannerWhitelistSlots(int i, int j, int k) {
      super(i, j, k, 7, 7, "");
   }

   public void func_146112_a(Minecraft mc, int i, int j) {
      if (this.field_146125_m) {
         mc.func_110434_K().func_110577_a(guiTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         boolean over = i >= this.field_146128_h && j >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && j < this.field_146129_i + this.field_146121_g;
         int k = 226 + this.field_146127_k * this.field_146120_f;
         int l = this.func_146114_a(over) * this.field_146120_f;
         this.func_73729_b(this.field_146128_h, this.field_146129_i, k, l, this.field_146120_f, this.field_146121_g);
      }

   }
}
