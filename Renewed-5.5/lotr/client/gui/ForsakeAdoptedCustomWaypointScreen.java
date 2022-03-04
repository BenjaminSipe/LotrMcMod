package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.gui.widget.button.RedBookButton;
import lotr.common.network.CPacketForsakeAdoptedCustomWaypoint;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.world.map.AdoptedCustomWaypoint;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ForsakeAdoptedCustomWaypointScreen extends CustomWaypointScreen {
   private final ViewAdoptedCustomWaypointScreen parentScreen;
   private final AdoptedCustomWaypoint theWaypoint;
   private Button forsakeButton;
   private Button cancelButton;
   private int forsakeTimer;

   public ForsakeAdoptedCustomWaypointScreen(ViewAdoptedCustomWaypointScreen parent, AdoptedCustomWaypoint wp) {
      super(new StringTextComponent("CWP"));
      this.parentScreen = parent;
      this.theWaypoint = wp;
      this.forsakeTimer = 20;
   }

   public void func_231160_c_() {
      super.func_231160_c_();
      this.forsakeButton = (Button)this.func_230480_a_((new RedBookButton(this.field_230708_k_ / 2 + 4, this.field_230709_l_ / 2 + 65, 120, 20, new TranslationTextComponent("gui.lotr.cwp.forsake.do"), (b) -> {
         LOTRPacketHandler.sendToServer(new CPacketForsakeAdoptedCustomWaypoint(this.theWaypoint));
         this.field_230706_i_.field_71439_g.func_71053_j();
      })).setRedText());
      this.forsakeButton.field_230693_o_ = false;
      this.cancelButton = (Button)this.func_230480_a_(new RedBookButton(this.field_230708_k_ / 2 - 124, this.field_230709_l_ / 2 + 65, 120, 20, new TranslationTextComponent("gui.lotr.cwp.forsake.cancel"), (b) -> {
         this.func_231175_as__();
      }));
   }

   public void func_231175_as__() {
      this.field_230706_i_.func_147108_a(this.parentScreen);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      --this.forsakeTimer;
      if (this.forsakeTimer <= 0) {
         this.forsakeButton.field_230693_o_ = true;
      }

   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      this.func_230446_a_(matStack);
      ITextComponent title = new TranslationTextComponent("gui.lotr.cwp.forsake.title");
      func_238472_a_(matStack, this.field_230712_o_, title, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 90, 16777215);
      ITextComponent warning1 = new TranslationTextComponent("gui.lotr.cwp.forsake.warning.1", new Object[]{this.theWaypoint.getDisplayName()});
      ITextComponent warning2 = new TranslationTextComponent("gui.lotr.cwp.forsake.warning.2");
      int warningY = this.drawCenteredStringLinesWrappedToWidth(matStack, this.field_230712_o_, warning1, 300, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 55, 16777215);
      warningY += 30;
      this.drawCenteredStringLinesWrappedToWidth(matStack, this.field_230712_o_, warning2, 300, this.field_230708_k_ / 2, warningY, 16777215);
      super.func_230430_a_(matStack, mouseX, mouseY, f);
   }
}
