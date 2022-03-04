package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.util.LOTRClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class LeftRightButton extends Button {
   private static final ResourceLocation LEFT_RIGHT_BUTTON_TEXTURE = new ResourceLocation("lotr", "textures/gui/widgets.png");
   private static final int FULL_WIDTH = 120;
   private static final int FULL_HEIGHT = 20;
   private static final int ARROW_PART_WIDTH = 16;
   private final boolean isLeftHanded;

   public LeftRightButton(int xIn, int yIn, int widthIn, int heightIn, boolean left, ITextComponent text, IPressable onPress) {
      super(xIn, yIn, widthIn, heightIn, text, onPress);
      this.isLeftHanded = left;
   }

   public void func_230431_b_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      Minecraft mc = Minecraft.func_71410_x();
      FontRenderer font = mc.field_71466_p;
      mc.func_110434_K().func_110577_a(LEFT_RIGHT_BUTTON_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      int yOffset = this.func_230989_a_(this.func_230449_g_());
      int u0 = this.isLeftHanded ? 0 : 136;
      int v0 = 0 + yOffset * 20;
      this.func_238474_b_(matStack, this.field_230690_l_, this.field_230691_m_, u0, v0, this.field_230688_j_ / 2, this.field_230689_k_);
      this.func_238474_b_(matStack, this.field_230690_l_ + this.field_230688_j_ / 2, this.field_230691_m_, u0 + 120 - this.field_230688_j_ / 2, v0, this.field_230688_j_ / 2, this.field_230689_k_);
      this.func_230441_a_(matStack, mc, mouseX, mouseY);
      int textColor = this.getFGColor();
      int textX = this.isLeftHanded ? this.field_230690_l_ + 16 + (this.field_230688_j_ - 16) / 2 : this.field_230690_l_ + (this.field_230688_j_ - 16) / 2;
      func_238472_a_(matStack, font, this.func_230458_i_(), textX, this.field_230691_m_ + (this.field_230689_k_ - 8) / 2, LOTRClientUtil.getRGBAForFontRendering(textColor, this.field_230695_q_));
   }
}
