package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.gui.widget.button.RedBookButton;
import lotr.common.network.CPacketUpdateCustomWaypoint;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.world.map.CustomWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class UpdateCustomWaypointScreen extends CustomWaypointScreen {
   private static final ITextComponent PUBLIC_TEXT = new TranslationTextComponent("gui.lotr.cwp.create.public.yes");
   private static final ITextComponent NOT_PUBLIC_TEXT = new TranslationTextComponent("gui.lotr.cwp.create.public.no");
   private final CustomWaypoint theWaypoint;
   private final String initialName;
   private final String initialLore;
   private final boolean initialIsPublic;
   private Button updateButton;
   private TextFieldWidget nameField;
   private TextFieldWidget loreField;
   private boolean isPublic;
   private Button publicButton;
   private Button destroyButton;

   public UpdateCustomWaypointScreen(CustomWaypoint wp) {
      super(new StringTextComponent("CWP"));
      this.theWaypoint = wp;
      this.initialName = this.theWaypoint.getRawName();
      this.initialLore = this.theWaypoint.getRawLore();
      this.initialIsPublic = this.isPublic = this.theWaypoint.isPublic();
   }

   public void func_231160_c_() {
      super.func_231160_c_();
      this.updateButton = (Button)this.func_230480_a_(new RedBookButton(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 2 + 65, 97, 20, new TranslationTextComponent("gui.lotr.cwp.update.do"), (b) -> {
         LOTRPacketHandler.sendToServer(new CPacketUpdateCustomWaypoint(this.theWaypoint, this.nameField.func_146179_b(), this.loreField.func_146179_b(), this.isPublic));
         this.field_230706_i_.field_71439_g.func_71053_j();
      }));
      this.updateButton.field_230693_o_ = false;
      this.nameField = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 2 - 40, 200, 20, (ITextComponent)null);
      this.nameField.func_146203_f(40);
      this.nameField.func_146180_a(this.initialName);
      this.addTextFieldAndSetFocused(this.nameField);
      this.loreField = new TextFieldWidget(this.field_230712_o_, this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 2, 200, 20, (ITextComponent)null);
      this.loreField.func_146203_f(160);
      this.loreField.func_146180_a(this.initialLore);
      this.addTextField(this.loreField);
      this.nameField.func_212954_a((text) -> {
         this.checkChangesForUpdate();
      });
      this.loreField.func_212954_a((text) -> {
         this.checkChangesForUpdate();
      });
      this.checkChangesForUpdate();
      this.publicButton = (Button)this.func_230480_a_(new RedBookButton(this.field_230708_k_ / 2 - 30, this.field_230709_l_ / 2 + 30, 60, 20, NOT_PUBLIC_TEXT, (b) -> {
         this.isPublic = !this.isPublic;
         this.checkChangesForUpdate();
      }));
      if (this.initialIsPublic) {
         this.publicButton.field_230693_o_ = false;
      }

      this.updatePublicButton();
      this.destroyButton = (Button)this.func_230480_a_(new RedBookButton(this.field_230708_k_ / 2 + 4, this.field_230709_l_ / 2 + 65, 97, 20, new TranslationTextComponent("gui.lotr.cwp.update.destroy"), (b) -> {
         this.field_230706_i_.func_147108_a(new DestroyCustomWaypointScreen(this, this.theWaypoint));
      }));
   }

   private void checkChangesForUpdate() {
      boolean anyChanges = !this.nameField.func_146179_b().trim().equals(this.initialName.trim()) || !this.loreField.func_146179_b().trim().equals(this.initialLore.trim()) || this.isPublic != this.initialIsPublic;
      this.updateButton.field_230693_o_ = anyChanges;
   }

   public void func_231152_a_(Minecraft mc, int w, int h) {
      String name = this.nameField.func_146179_b();
      String lore = this.loreField.func_146179_b();
      super.func_231152_a_(mc, w, h);
      this.nameField.func_146180_a(name);
      this.loreField.func_146180_a(lore);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      this.nameField.func_146178_a();
      this.loreField.func_146178_a();
      this.updatePublicButton();
   }

   private void updatePublicButton() {
      this.publicButton.func_238482_a_(this.isPublic ? PUBLIC_TEXT : NOT_PUBLIC_TEXT);
      this.publicButton.setFGColor(this.isPublic ? 16711680 : 8019267);
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      this.func_230446_a_(matStack);
      ITextComponent title = new TranslationTextComponent("gui.lotr.cwp.update.title");
      func_238472_a_(matStack, this.field_230712_o_, title, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 90, 16777215);
      this.nameField.func_230430_a_(matStack, mouseX, mouseY, f);
      ITextComponent nameFieldLabel = new TranslationTextComponent("gui.lotr.cwp.update.name");
      FontRenderer var10000 = this.field_230712_o_;
      float var10003 = (float)this.nameField.field_230690_l_;
      int var10004 = this.nameField.field_230691_m_;
      this.field_230712_o_.getClass();
      var10000.func_243248_b(matStack, nameFieldLabel, var10003, (float)(var10004 - 9 - 3), 16777215);
      this.loreField.func_230430_a_(matStack, mouseX, mouseY, f);
      ITextComponent loreFieldLabel = new TranslationTextComponent("gui.lotr.cwp.update.lore");
      var10000 = this.field_230712_o_;
      var10003 = (float)this.loreField.field_230690_l_;
      var10004 = this.loreField.field_230691_m_;
      this.field_230712_o_.getClass();
      var10000.func_243248_b(matStack, loreFieldLabel, var10003, (float)(var10004 - 9 - 3), 16777215);
      ITextComponent publicLabel = new TranslationTextComponent("gui.lotr.cwp.create.public");
      var10000 = this.field_230712_o_;
      var10003 = (float)(this.publicButton.field_230690_l_ - 4 - this.field_230712_o_.func_238414_a_(publicLabel));
      var10004 = this.publicButton.field_230691_m_ + this.publicButton.func_238483_d_() / 2;
      this.field_230712_o_.getClass();
      var10000.func_243248_b(matStack, publicLabel, var10003, (float)(var10004 - 9 / 2), 16777215);
      super.func_230430_a_(matStack, mouseX, mouseY, f);
      this.renderPublicButtonTooltip(this.publicButton, matStack, mouseX, mouseY);
   }
}
