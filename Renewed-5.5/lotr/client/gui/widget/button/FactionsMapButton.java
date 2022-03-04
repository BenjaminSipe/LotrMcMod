package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.gui.MiddleEarthFactionsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.util.text.StringTextComponent;

public class FactionsMapButton extends Button {
   public FactionsMapButton(int xIn, int yIn, IPressable onPress) {
      super(xIn, yIn, 8, 8, StringTextComponent.field_240750_d_, onPress);
   }

   public void func_230431_b_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      Minecraft mc = Minecraft.func_71410_x();
      mc.func_110434_K().func_110577_a(MiddleEarthFactionsScreen.FACTIONS_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      int yOffset = this.func_230449_g_() ? 1 : 0;
      this.func_238474_b_(matStack, this.field_230690_l_, this.field_230691_m_, 17 + yOffset * 8, 142, this.field_230688_j_, this.field_230689_k_);
      this.func_230441_a_(matStack, mc, mouseX, mouseY);
   }
}
