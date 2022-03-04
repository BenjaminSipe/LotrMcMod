package lotr.client.gui.inv;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class FactionCraftingToggleButton extends Button {
   private final int baseU;
   private final int baseV;
   private final ResourceLocation texture;
   private final ItemStack buttonIcon;
   private boolean toggledOn;

   public FactionCraftingToggleButton(int x, int y, int width, int height, int u, int v, ResourceLocation tex, ItemStack icon, IPressable pressFunc) {
      super(x, y, width, height, StringTextComponent.field_240750_d_, pressFunc);
      this.baseU = u;
      this.baseV = v;
      this.texture = tex;
      this.buttonIcon = icon;
   }

   public void setToggled(boolean flag) {
      this.toggledOn = flag;
   }

   public void func_230431_b_(MatrixStack matStack, int mouseX, int mouseY, float tick) {
      Minecraft mc = Minecraft.func_71410_x();
      mc.func_110434_K().func_110577_a(this.texture);
      RenderSystem.disableDepthTest();
      int u = this.baseU;
      if (this.func_230449_g_()) {
         u += this.field_230688_j_ * 2;
      } else if (this.toggledOn) {
         u += this.field_230688_j_;
      }

      this.func_238474_b_(matStack, this.field_230690_l_, this.field_230691_m_, u, this.baseV, this.field_230688_j_, this.field_230689_k_);
      RenderSystem.enableDepthTest();
      ItemRenderer ir = mc.func_175599_af();
      ir.func_180450_b(this.buttonIcon, this.field_230690_l_ + 1, this.field_230691_m_ + 1);
   }
}
