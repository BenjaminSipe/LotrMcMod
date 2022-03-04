package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class CustomWaypointScreen extends BasicIngameScreen {
   public static final int TEXT_WRAP_WIDTH = 300;

   public CustomWaypointScreen(ITextComponent titleIn) {
      super(titleIn);
   }

   protected final void renderPublicButtonTooltip(Button publicButton, MatrixStack matStack, int mouseX, int mouseY) {
      if (publicButton.field_230693_o_ && publicButton.func_230449_g_()) {
         List lines = new ArrayList();
         int stringWidth = 200;
         lines.addAll(this.field_230712_o_.func_238425_b_(new TranslationTextComponent("gui.lotr.cwp.create.public.help.1"), stringWidth));
         lines.add(IReorderingProcessor.field_242232_a);
         lines.addAll(this.field_230712_o_.func_238425_b_((new TranslationTextComponent("gui.lotr.cwp.create.public.help.2")).func_240699_a_(TextFormatting.RED), stringWidth));
         this.func_238654_b_(matStack, lines, mouseX, mouseY);
      }

   }
}
