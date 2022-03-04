package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.gui.widget.button.RedBookButton;
import lotr.common.network.CPacketAdoptCustomWaypoint;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.world.map.CustomWaypoint;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class AdoptCustomWaypointScreen extends CustomWaypointScreen {
   private final CustomWaypoint waypointToAdopt;
   private final String createdPlayerName;
   private Button adoptButton;

   public AdoptCustomWaypointScreen(CustomWaypoint wp, String playerName) {
      super(new StringTextComponent("CWP"));
      this.waypointToAdopt = wp;
      this.createdPlayerName = playerName;
   }

   public void func_231160_c_() {
      super.func_231160_c_();
      this.adoptButton = (Button)this.func_230480_a_(new RedBookButton(this.field_230708_k_ / 2 - 60, this.field_230709_l_ / 2 + 65, 120, 20, new TranslationTextComponent("gui.lotr.cwp.adopt.do"), (b) -> {
         LOTRPacketHandler.sendToServer(new CPacketAdoptCustomWaypoint(this.waypointToAdopt));
         this.field_230706_i_.field_71439_g.func_71053_j();
      }));
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      this.func_230446_a_(matStack);
      ITextComponent title = new TranslationTextComponent("gui.lotr.cwp.adopt.title");
      func_238472_a_(matStack, this.field_230712_o_, title, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 90, 16777215);
      ITextComponent wpName = this.waypointToAdopt.getDisplayName();
      ITextComponent byline = new TranslationTextComponent("gui.lotr.cwp.adopt.owner", new Object[]{this.createdPlayerName});
      func_238472_a_(matStack, this.field_230712_o_, wpName, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 55, 16777215);
      func_238472_a_(matStack, this.field_230712_o_, byline, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 40, 12632256);
      ITextComponent lore = this.waypointToAdopt.getDisplayLore();
      if (lore != null) {
         this.drawCenteredStringLinesWrappedToWidth(matStack, this.field_230712_o_, lore, 300, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 20, 12632256);
      }

      ITextComponent helpLine1 = new TranslationTextComponent("gui.lotr.cwp.adopt.help.1");
      ITextComponent helpLine2 = new TranslationTextComponent("gui.lotr.cwp.adopt.help.2");
      int help1Y = this.drawCenteredStringLinesWrappedToWidth(matStack, this.field_230712_o_, helpLine1, 300, this.field_230708_k_ / 2, this.field_230709_l_ / 2 + 20, 16777215);
      this.drawCenteredStringLinesWrappedToWidth(matStack, this.field_230712_o_, helpLine2, 300, this.field_230708_k_ / 2, help1Y + 6, 16777215);
      super.func_230430_a_(matStack, mouseX, mouseY, f);
   }
}
