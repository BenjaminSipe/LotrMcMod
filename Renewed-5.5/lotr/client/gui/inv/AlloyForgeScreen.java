package lotr.client.gui.inv;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.common.inv.AbstractAlloyForgeContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AlloyForgeScreen extends ContainerScreen {
   private static final ResourceLocation FORGE_TEXTURE = new ResourceLocation("lotr:textures/gui/alloy_forge.png");

   public AlloyForgeScreen(AbstractAlloyForgeContainer cont, PlayerInventory inv, ITextComponent title) {
      super(cont, inv, title);
      this.field_147000_g = 233;
   }

   public void func_231160_c_() {
      super.func_231160_c_();
   }

   public void func_231023_e_() {
      super.func_231023_e_();
   }

   public void func_230430_a_(MatrixStack matStack, int x, int y, float f) {
      this.func_230446_a_(matStack);
      super.func_230430_a_(matStack, x, y, f);
      this.func_230459_a_(matStack, x, y);
   }

   protected void func_230451_b_(MatrixStack matStack, int x, int y) {
      this.field_230712_o_.func_243248_b(matStack, this.field_230704_d_, (float)(this.field_146999_f / 2 - this.field_230712_o_.func_238414_a_(this.field_230704_d_) / 2), 6.0F, 4210752);
      this.field_230712_o_.func_243248_b(matStack, this.field_213127_e.func_145748_c_(), 8.0F, 139.0F, 4210752);
   }

   protected void func_230450_a_(MatrixStack matStack, float partialTicks, int mouseX, int mouseY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.func_110434_K().func_110577_a(FORGE_TEXTURE);
      int left = this.field_147003_i;
      int top = this.field_147009_r;
      this.func_238474_b_(matStack, left, top, 0, 0, this.field_146999_f, this.field_147000_g);
      int cook;
      if (((AbstractAlloyForgeContainer)this.field_147002_h).isBurning()) {
         cook = ((AbstractAlloyForgeContainer)this.field_147002_h).getBurnLeftScaled();
         this.func_238474_b_(matStack, left + 80, top + 112 + 12 - cook, 176, 12 - cook, 14, cook + 2);
      }

      cook = ((AbstractAlloyForgeContainer)this.field_147002_h).getCookProgressionScaled();
      this.func_238474_b_(matStack, left + 80, top + 58, 176, 14, 16, cook + 1);
   }
}
