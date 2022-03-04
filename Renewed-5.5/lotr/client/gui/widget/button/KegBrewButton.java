package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.gui.inv.KegScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.client.gui.widget.button.Button.ITooltip;
import net.minecraft.util.text.StringTextComponent;

public class KegBrewButton extends Button {
   public KegBrewButton(int xIn, int yIn, IPressable onPressIn, ITooltip tooltip) {
      super(xIn, yIn, 42, 14, StringTextComponent.field_240750_d_, onPressIn, tooltip);
   }

   public void func_230431_b_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      Minecraft minecraft = Minecraft.func_71410_x();
      FontRenderer fr = minecraft.field_71466_p;
      minecraft.func_110434_K().func_110577_a(KegScreen.KEG_SCREEN);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      int yOffset = this.func_230989_a_(this.func_230449_g_());
      this.func_238474_b_(matStack, this.field_230690_l_, this.field_230691_m_, 210, 0 + yOffset * this.field_230689_k_, this.field_230688_j_, this.field_230689_k_);
      this.func_230441_a_(matStack, minecraft, mouseX, mouseY);
      if (this.func_230449_g_()) {
         this.func_230443_a_(matStack, mouseX, mouseY);
      }

   }
}
