package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.gui.MiddleEarthFactionsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.util.text.ITextComponent;

public class FactionsPageButton extends Button {
   private final boolean isLeftHanded;

   public FactionsPageButton(int xIn, int yIn, boolean left, ITextComponent text, IPressable onPress) {
      super(xIn, yIn, 16, 16, text, onPress);
      this.isLeftHanded = left;
   }

   public void func_230431_b_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      Minecraft mc = Minecraft.func_71410_x();
      FontRenderer font = mc.field_71466_p;
      mc.func_110434_K().func_110577_a(MiddleEarthFactionsScreen.FACTIONS_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      int yOffset = this.func_230989_a_(this.func_230449_g_());
      this.func_238474_b_(matStack, this.field_230690_l_, this.field_230691_m_, 0 + yOffset * 16, this.isLeftHanded ? 160 : 176, this.field_230688_j_, this.field_230689_k_);
      this.func_230441_a_(matStack, mc, mouseX, mouseY);
      if (this.field_230693_o_) {
         int stringBorder = -1;
         int var10000 = this.field_230691_m_ + this.field_230689_k_ / 2;
         font.getClass();
         int stringY = var10000 - 9 / 2;
         int textColor = 0;
         ITextComponent msg = this.func_230458_i_();
         if (this.isLeftHanded) {
            font.func_243248_b(matStack, msg, (float)(this.field_230690_l_ + this.field_230688_j_ + stringBorder), (float)stringY, textColor);
         } else {
            font.func_243248_b(matStack, msg, (float)(this.field_230690_l_ - stringBorder - font.func_238414_a_(msg)), (float)stringY, textColor);
         }
      }

   }
}
