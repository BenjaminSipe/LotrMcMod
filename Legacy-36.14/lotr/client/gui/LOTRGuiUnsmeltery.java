package lotr.client.gui;

import lotr.common.inventory.LOTRContainerUnsmeltery;
import lotr.common.tileentity.LOTRTileEntityUnsmeltery;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiUnsmeltery extends GuiContainer {
   private static ResourceLocation guiTexture = new ResourceLocation("lotr:gui/unsmelter.png");
   private LOTRTileEntityUnsmeltery theUnsmeltery;

   public LOTRGuiUnsmeltery(InventoryPlayer inv, LOTRTileEntityUnsmeltery unsmeltery) {
      super(new LOTRContainerUnsmeltery(inv, unsmeltery));
      this.theUnsmeltery = unsmeltery;
      this.field_147000_g = 176;
   }

   protected void func_146979_b(int i, int j) {
      String s = this.theUnsmeltery.func_145825_b();
      this.field_146289_q.func_78276_b(s, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(s) / 2, 6, 4210752);
      this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.inventory"), 8, 72, 4210752);
   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(guiTexture);
      this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
      int l;
      if (this.theUnsmeltery.isSmelting()) {
         l = this.theUnsmeltery.getSmeltTimeRemainingScaled(13);
         this.func_73729_b(this.field_147003_i + 56, this.field_147009_r + 36 + 12 - l, 176, 12 - l, 14, l + 1);
      }

      l = this.theUnsmeltery.getSmeltProgressScaled(24);
      this.func_73729_b(this.field_147003_i + 79, this.field_147009_r + 34, 176, 14, l + 1, 16);
   }
}
