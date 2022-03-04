package lotr.client.gui.inv;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.common.inv.FactionCraftingContainer;
import lotr.common.network.CPacketFactionCraftingToggle;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FactionCraftingScreen extends ContainerScreen {
   private static final ResourceLocation CRAFTING_GUI_TEXTURES = new ResourceLocation("textures/gui/container/crafting_table.png");
   private static final ResourceLocation FACTION_CRAFTING_GUI_TEXTURES = new ResourceLocation("lotr", "textures/gui/faction_crafting_table.png");
   private static final ITextComponent STANDARD_CRAFTING_TITLE = new TranslationTextComponent("container.crafting");
   private boolean widthTooNarrow;
   private FactionCraftingToggleButton buttonToggleFaction;
   private FactionCraftingToggleButton buttonToggleStandard;

   public FactionCraftingScreen(FactionCraftingContainer c, PlayerInventory inv, ITextComponent title) {
      super(c, inv, title);
   }

   protected void func_231160_c_() {
      super.func_231160_c_();
      this.widthTooNarrow = this.field_230708_k_ < 379;
      this.buttonToggleFaction = (FactionCraftingToggleButton)this.func_230480_a_(new FactionCraftingToggleButton(this.field_147003_i + 5, this.field_147009_r + 25, 18, 18, 0, 0, FACTION_CRAFTING_GUI_TEXTURES, new ItemStack(((FactionCraftingContainer)this.field_147002_h).getCraftingBlock()), (b) -> {
         this.toggleCrafting(false);
      }));
      this.buttonToggleStandard = (FactionCraftingToggleButton)this.func_230480_a_(new FactionCraftingToggleButton(this.field_147003_i + 5, this.field_147009_r + 43, 18, 18, 0, 18, FACTION_CRAFTING_GUI_TEXTURES, new ItemStack(Blocks.field_150462_ai), (b) -> {
         this.toggleCrafting(true);
      }));
      this.updateToggles();
   }

   private void toggleCrafting(boolean standard) {
      ((FactionCraftingContainer)this.field_147002_h).setStandardCraftingActive(standard);
      this.updateToggles();
      CPacketFactionCraftingToggle packet = new CPacketFactionCraftingToggle(standard);
      LOTRPacketHandler.sendToServer(packet);
   }

   private void updateToggles() {
      boolean standard = ((FactionCraftingContainer)this.field_147002_h).isStandardCraftingActive();
      this.buttonToggleFaction.setToggled(!standard);
      this.buttonToggleStandard.setToggled(standard);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
   }

   public void func_230430_a_(MatrixStack matStack, int i, int j, float f) {
      this.func_230446_a_(matStack);
      super.func_230430_a_(matStack, i, j, f);
      this.func_230459_a_(matStack, i, j);
   }

   protected void func_230451_b_(MatrixStack matStack, int mouseX, int mouseY) {
      if (((FactionCraftingContainer)this.field_147002_h).isStandardCraftingActive()) {
         this.field_230712_o_.func_243248_b(matStack, STANDARD_CRAFTING_TITLE, 28.0F, 6.0F, 4210752);
      } else {
         this.field_230712_o_.func_243248_b(matStack, this.field_230704_d_, 28.0F, 6.0F, 4210752);
      }

      this.field_230712_o_.func_243248_b(matStack, this.field_213127_e.func_145748_c_(), 8.0F, (float)(this.field_147000_g - 96 + 2), 4210752);
   }

   protected void func_230450_a_(MatrixStack matStack, float partialTicks, int mouseX, int mouseY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_230706_i_.func_110434_K().func_110577_a(CRAFTING_GUI_TEXTURES);
      int i = this.field_147003_i;
      int j = (this.field_230709_l_ - this.field_147000_g) / 2;
      this.func_238474_b_(matStack, i, j, 0, 0, this.field_146999_f, this.field_147000_g);
   }

   protected boolean func_195359_a(int x, int y, int width, int height, double mouseX, double mouseY) {
      return !this.widthTooNarrow && super.func_195359_a(x, y, width, height, mouseX, mouseY);
   }

   public boolean func_231044_a_(double x, double y, int click) {
      return super.func_231044_a_(x, y, click);
   }

   protected boolean func_195361_a(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
      boolean flag = mouseX < (double)guiLeftIn || mouseY < (double)guiTopIn || mouseX >= (double)(guiLeftIn + this.field_146999_f) || mouseY >= (double)(guiTopIn + this.field_147000_g);
      return flag;
   }

   protected void func_184098_a(Slot slotIn, int slotId, int mouseButton, ClickType type) {
      super.func_184098_a(slotIn, slotId, mouseButton, type);
   }
}
