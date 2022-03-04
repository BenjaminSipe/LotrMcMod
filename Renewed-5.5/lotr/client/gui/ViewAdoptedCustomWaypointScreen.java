package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.gui.widget.button.RedBookButton;
import lotr.common.world.map.AdoptedCustomWaypoint;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ViewAdoptedCustomWaypointScreen extends CustomWaypointScreen {
   private final AdoptedCustomWaypoint theWaypoint;
   private final String createdPlayerName;
   private Button forsakeButton;

   public ViewAdoptedCustomWaypointScreen(AdoptedCustomWaypoint wp, String playerName) {
      super(new StringTextComponent("CWP"));
      this.theWaypoint = wp;
      this.createdPlayerName = playerName;
   }

   public void func_231160_c_() {
      super.func_231160_c_();
      this.forsakeButton = (Button)this.func_230480_a_(new RedBookButton(this.field_230708_k_ / 2 - 60, this.field_230709_l_ / 2 + 65, 120, 20, new TranslationTextComponent("gui.lotr.cwp.adopted.forsake"), (b) -> {
         this.field_230706_i_.func_147108_a(new ForsakeAdoptedCustomWaypointScreen(this, this.theWaypoint));
      }));
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      this.func_230446_a_(matStack);
      ITextComponent title = new TranslationTextComponent("gui.lotr.cwp.adopted.title");
      func_238472_a_(matStack, this.field_230712_o_, title, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 90, 16777215);
      ITextComponent wpName = this.theWaypoint.getDisplayName();
      ITextComponent byline = new TranslationTextComponent("gui.lotr.cwp.adopted.owner", new Object[]{this.createdPlayerName});
      func_238472_a_(matStack, this.field_230712_o_, wpName, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 55, 16777215);
      func_238472_a_(matStack, this.field_230712_o_, byline, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 40, 12632256);
      ITextComponent lore = this.theWaypoint.getDisplayLore();
      if (lore != null) {
         this.drawCenteredStringLinesWrappedToWidth(matStack, this.field_230712_o_, lore, 300, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 20, 12632256);
      }

      super.func_230430_a_(matStack, mouseX, mouseY, f);
   }
}
