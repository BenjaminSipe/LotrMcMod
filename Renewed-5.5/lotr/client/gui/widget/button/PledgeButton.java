package lotr.client.gui.widget.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lotr.client.gui.util.AlignmentRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class PledgeButton extends Button {
   private final Screen parentScreen;
   private boolean isBroken;
   private List tooltipLines = new ArrayList();

   public PledgeButton(Screen parent, int xIn, int yIn, boolean broken, IPressable onPressIn) {
      super(xIn, yIn, 32, 32, StringTextComponent.field_240750_d_, onPressIn);
      this.parentScreen = parent;
      this.isBroken = broken;
   }

   public void setDisplayAsBroken(boolean flag) {
      this.isBroken = flag;
   }

   public void setTooltipLines(ITextComponent... lines) {
      if (lines == null) {
         lines = new ITextComponent[0];
      }

      this.tooltipLines = Arrays.asList(lines);
   }

   public void func_230431_b_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      Minecraft minecraft = Minecraft.func_71410_x();
      FontRenderer fr = minecraft.field_71466_p;
      minecraft.func_110434_K().func_110577_a(AlignmentRenderer.ALIGNMENT_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.field_230695_q_);
      int yOffset = this.func_230989_a_(this.func_230449_g_());
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      this.func_238474_b_(matStack, this.field_230690_l_, this.field_230691_m_, 0 + yOffset * this.field_230688_j_, 180, this.field_230688_j_, this.field_230689_k_);
      this.func_230441_a_(matStack, minecraft, mouseX, mouseY);
      if (!this.tooltipLines.isEmpty() && this.func_230449_g_()) {
         this.parentScreen.func_243308_b(matStack, this.tooltipLines, mouseX, mouseY);
      }

   }

   protected int func_230989_a_(boolean hovered) {
      if (this.isBroken) {
         return hovered ? 4 : 3;
      } else if (!this.field_230693_o_) {
         return 0;
      } else {
         return hovered ? 2 : 1;
      }
   }
}
