package lotr.client.gui.inv;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.common.inv.PouchContainer;
import lotr.common.network.CPacketRenamePouch;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.apache.commons.lang3.StringUtils;

public class PouchScreen extends ContainerScreen {
   private static final ResourceLocation POUCH_TEXTURE = new ResourceLocation("lotr:textures/gui/pouch.png");
   private final int pouchRows;
   private TextFieldWidget renameField;

   public PouchScreen(PouchContainer cont, PlayerInventory inv, ITextComponent title) {
      super(cont, inv, title);
      this.pouchRows = cont.getPouchCapacity() / 9;
      this.field_147000_g = 180;
   }

   public void func_231160_c_() {
      super.func_231160_c_();
      this.renameField = new TextFieldWidget(this.field_230712_o_, this.field_147003_i + this.field_146999_f / 2 - 80, this.field_147009_r + 7, 160, 20, (ITextComponent)null) {
         public void func_146195_b(boolean isFocused) {
            super.func_146195_b(isFocused);
            if (!isFocused && StringUtils.trim(PouchScreen.this.renameField.func_146179_b()).isEmpty()) {
               this.func_146180_a(((PouchContainer)PouchScreen.this.field_147002_h).getPouchDefaultDisplayName().getString());
            }

         }
      };
      this.renameField.func_146203_f(64);
      this.renameField.func_146180_a(((PouchContainer)this.field_147002_h).getPouchDisplayName().getString());
      this.renameField.func_212954_a(this::renamePouch);
      this.field_230705_e_.add(this.renameField);
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float partialTicks) {
      this.func_230446_a_(matStack);
      super.func_230430_a_(matStack, mouseX, mouseY, partialTicks);
      this.func_230459_a_(matStack, mouseX, mouseY);
   }

   protected void func_230451_b_(MatrixStack matStack, int mouseX, int mouseY) {
      this.field_230712_o_.func_243248_b(matStack, this.field_213127_e.func_145748_c_(), 8.0F, (float)(this.field_147000_g - 96 + 2), 4210752);
   }

   protected void func_230450_a_(MatrixStack matStack, float partialTicks, int mouseX, int mouseY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.func_110434_K().func_110577_a(POUCH_TEXTURE);
      this.func_238474_b_(matStack, this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);

      for(int l = 0; l < this.pouchRows; ++l) {
         this.func_238474_b_(matStack, this.field_147003_i + 7, this.field_147009_r + 29 + l * 18, 0, 180, 162, 18);
      }

      this.renameField.func_230430_a_(matStack, mouseX, mouseY, partialTicks);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      this.renameField.func_146178_a();
   }

   public boolean func_231046_a_(int keyCode, int scanCode, int modifiers) {
      return !this.field_230706_i_.field_71474_y.field_151445_Q.isActiveAndMatches(InputMappings.func_197954_a(keyCode, scanCode)) || !this.renameField.func_212955_f() && !this.renameField.func_231046_a_(keyCode, scanCode, modifiers) ? super.func_231046_a_(keyCode, scanCode, modifiers) : true;
   }

   private void renamePouch(String text) {
      if (text.equals(((PouchContainer)this.field_147002_h).getPouchDefaultDisplayName().getString())) {
         text = "";
      }

      ((PouchContainer)this.field_147002_h).renamePouch(text);
      LOTRPacketHandler.sendToServer(new CPacketRenamePouch(text));
   }
}
