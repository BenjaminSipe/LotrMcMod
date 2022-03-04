package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotr.client.gui.util.AlignmentRenderer;
import lotr.client.gui.util.AlignmentTextRenderer;
import lotr.client.gui.widget.button.RedBookButton;
import lotr.common.data.PlayerMessageType;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PlayerMessageScreen extends BasicIngameScreen {
   private static final ResourceLocation MESSAGE_TEXTURE = new ResourceLocation("lotr", "textures/gui/message.png");
   private final PlayerMessageType messageType;
   private final boolean isCommandSent;
   private final String displayText;
   private final int xSize = 240;
   private final int ySize = 160;
   private final int border = 12;
   private int guiLeft;
   private int guiTop;
   private Button buttonDismiss;
   private static final int TIME_UNTIL_DISMISS = 60;
   private int buttonTimer = 60;
   private final AlignmentRenderer alignmentRenderer = new AlignmentRenderer(AlignmentTextRenderer.newGUIRenderer());

   public PlayerMessageScreen(PlayerMessageType type, boolean command, String custom) {
      super(new StringTextComponent("MESSAGE"));
      this.messageType = type;
      this.isCommandSent = command;
      if (custom != null) {
         this.displayText = custom;
      } else {
         this.displayText = type.getDisplayMessage().getString();
      }

   }

   public void func_231160_c_() {
      super.func_231160_c_();
      this.guiLeft = (this.field_230708_k_ - 240) / 2;
      this.guiTop = (this.field_230709_l_ - 160) / 2;
      this.buttonDismiss = (Button)this.func_230480_a_(new RedBookButton(this.guiLeft + 120 - 40, this.guiTop + 160 + 20, 80, 20, new TranslationTextComponent("gui.lotr.message.dismiss"), (b) -> {
         this.field_230706_i_.field_71439_g.func_71053_j();
      }));
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      if (this.buttonTimer > 0) {
         --this.buttonTimer;
      }

      this.buttonDismiss.field_230693_o_ = this.buttonTimer == 0;
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      this.func_230446_a_(matStack);
      this.field_230706_i_.func_110434_K().func_110577_a(MESSAGE_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.func_238474_b_(matStack, this.guiLeft, this.guiTop, 0, 0, 240, 160);
      int pageWidth = 216;
      String[] splitNewline = this.displayText.split(Pattern.quote("\\n"));
      List messageLines = (List)Stream.of(splitNewline).map(StringTextComponent::new).flatMap((lineComp) -> {
         return this.field_230712_o_.func_238425_b_(lineComp, pageWidth).stream();
      }).collect(Collectors.toList());
      int x = this.guiLeft + 12;
      int y = this.guiTop + 12;

      for(Iterator var10 = messageLines.iterator(); var10.hasNext(); y += 9) {
         IReorderingProcessor line = (IReorderingProcessor)var10.next();
         this.field_230712_o_.func_238422_b_(matStack, line, (float)x, (float)y, 8019267);
         this.field_230712_o_.getClass();
      }

      if (!this.isCommandSent) {
         ITextComponent bottomText = new TranslationTextComponent("gui.lotr.message.notDisplayedAgain");
         FontRenderer var10002 = this.field_230712_o_;
         int var10004 = this.guiLeft + 120;
         int var10005 = this.guiTop + 160 - 6;
         this.field_230712_o_.getClass();
         this.drawCenteredStringNoShadow(matStack, var10002, bottomText, var10004, var10005 - 9, 9666921);
      }

      if (this.messageType == PlayerMessageType.ALIGN_DRAIN) {
         int numIcons = 3;
         int iconGap = 40;

         for(int l = 0; l < numIcons; ++l) {
            int iconX = this.guiLeft + 120;
            iconX -= (numIcons - 1) * iconGap / 2;
            iconX += l * iconGap - 8;
            int iconY = this.guiTop + 12 + 14;
            int numFactions = l + 1;
            this.alignmentRenderer.renderAlignmentDrain(matStack, this.field_230706_i_, iconX, iconY, numFactions);
         }
      }

      super.func_230430_a_(matStack, mouseX, mouseY, f);
   }

   public boolean func_231178_ax__() {
      return false;
   }
}
