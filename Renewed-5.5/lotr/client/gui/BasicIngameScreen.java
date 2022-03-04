package lotr.client.gui;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Iterator;
import java.util.List;
import lotr.client.util.LOTRClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;

public abstract class BasicIngameScreen extends Screen {
   public BasicIngameScreen(ITextComponent titleIn) {
      super(titleIn);
   }

   public boolean func_231177_au__() {
      return false;
   }

   protected void addTextFieldAndSetFocused(TextFieldWidget textField) {
      this.addTextField(textField);
      textField.func_146195_b(true);
      this.func_212928_a(textField);
   }

   protected void addTextField(TextFieldWidget textField) {
      this.field_230705_e_.add(textField);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      if (!this.field_230706_i_.field_71439_g.func_70089_S() || this.field_230706_i_.field_71439_g.field_70128_L) {
         this.field_230706_i_.field_71439_g.func_71053_j();
         this.func_231175_as__();
      }

   }

   public boolean func_231046_a_(int key, int scan, int param3) {
      if (super.func_231046_a_(key, scan, param3)) {
         return true;
      } else {
         IGuiEventListener focused = this.func_241217_q_();
         if (focused instanceof TextFieldWidget) {
            TextFieldWidget focusedTextField = (TextFieldWidget)focused;
            if (focusedTextField.func_212955_f()) {
               return true;
            }
         }

         if (this.func_231178_ax__() && this.isEscapeOrInventoryKey(key, scan)) {
            this.field_230706_i_.field_71439_g.func_71053_j();
            this.func_231175_as__();
            return true;
         } else {
            return false;
         }
      }
   }

   protected boolean isEscapeOrInventoryKey(int key, int scan) {
      return key == 256 || this.field_230706_i_.field_71474_y.field_151445_Q.isActiveAndMatches(InputMappings.func_197954_a(key, scan));
   }

   protected void removeButton(Widget widget) {
      this.field_230710_m_.remove(widget);
      this.field_230705_e_.remove(widget);
   }

   protected void renderButtonTooltipIfHovered(MatrixStack matStack, Button button, ITextProperties tooltip, int mouseX, int mouseY) {
      this.renderButtonTooltipIfHovered(matStack, button, (List)ImmutableList.of(tooltip), mouseX, mouseY);
   }

   protected void renderButtonTooltipIfHovered(MatrixStack matStack, Button button, List tooltipLines, int mouseX, int mouseY) {
      if (button.field_230693_o_ && button.func_230449_g_()) {
         int stringWidth = 200;
         this.func_238654_b_(matStack, LOTRClientUtil.trimEachLineToWidth(tooltipLines, this.field_230712_o_, stringWidth), mouseX, mouseY);
      }

   }

   public static void playButtonClick() {
      Minecraft.func_71410_x().func_147118_V().func_147682_a(SimpleSound.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
   }

   public void drawCenteredStringNoShadow(MatrixStack matStack, FontRenderer fr, String text, int x, int y, int color) {
      fr.func_238421_b_(matStack, text, (float)(x - fr.func_78256_a(text) / 2), (float)y, color);
   }

   public void drawCenteredStringNoShadow(MatrixStack matStack, FontRenderer fr, ITextComponent text, int x, int y, int color) {
      fr.func_243248_b(matStack, text, (float)(x - fr.func_238414_a_(text) / 2), (float)y, color);
   }

   public void drawCenteredStringNoShadow(MatrixStack matStack, FontRenderer fr, IReorderingProcessor text, int x, int y, int color) {
      fr.func_238422_b_(matStack, text, (float)(x - fr.func_243245_a(text) / 2), (float)y, color);
   }

   protected int drawCenteredStringLinesWrappedToWidth(MatrixStack matStack, FontRenderer fr, ITextProperties text, int wrapWidth, int x, int y, int color) {
      List loreLines = this.field_230712_o_.func_238425_b_(text, wrapWidth);

      for(Iterator var9 = loreLines.iterator(); var9.hasNext(); y += 9) {
         IReorderingProcessor line = (IReorderingProcessor)var9.next();
         this.field_230712_o_.func_238407_a_(matStack, line, (float)(x - this.field_230712_o_.func_243245_a(line) / 2), (float)y, color);
         this.field_230712_o_.getClass();
      }

      return y;
   }
}
