package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.gui.RedBookScreen;
import lotr.client.util.LOTRClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.util.text.ITextComponent;

public class RedBookButton extends Button {
   private boolean hasRedText = false;

   public RedBookButton(int xIn, int yIn, int widthIn, int heightIn, ITextComponent textIn, IPressable onPressIn) {
      super(xIn, yIn, widthIn, heightIn, textIn, onPressIn);
   }

   public RedBookButton setRedText() {
      this.hasRedText = true;
      return this;
   }

   public void func_230431_b_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      Minecraft minecraft = Minecraft.func_71410_x();
      FontRenderer fr = minecraft.field_71466_p;
      minecraft.func_110434_K().func_110577_a(RedBookScreen.BOOK_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      int yOffset = this.func_230989_a_(this.func_230449_g_());
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      func_238463_a_(matStack, this.field_230690_l_, this.field_230691_m_, 170.0F, (float)(256 + yOffset * 20), this.field_230688_j_ / 2, this.field_230689_k_, 512, 512);
      func_238463_a_(matStack, this.field_230690_l_ + this.field_230688_j_ / 2, this.field_230691_m_, (float)(370 - this.field_230688_j_ / 2), (float)(256 + yOffset * 20), this.field_230688_j_ / 2, this.field_230689_k_, 512, 512);
      this.func_230441_a_(matStack, minecraft, mouseX, mouseY);
      int textColor = this.getFGColor();
      fr.func_243248_b(matStack, this.func_230458_i_(), (float)(this.field_230690_l_ + this.field_230688_j_ / 2 - fr.func_238414_a_(this.func_230458_i_()) / 2), (float)(this.field_230691_m_ + (this.field_230689_k_ - 8) / 2), LOTRClientUtil.getRGBAForFontRendering(textColor, this.field_230695_q_));
   }

   public int getFGColor() {
      if (this.packedFGColor != -1) {
         return this.packedFGColor;
      } else {
         return this.field_230693_o_ ? (this.hasRedText ? 16711680 : 8019267) : 5521198;
      }
   }
}
