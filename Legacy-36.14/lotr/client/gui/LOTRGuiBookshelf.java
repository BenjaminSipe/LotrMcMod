package lotr.client.gui;

import lotr.common.inventory.LOTRContainerBookshelf;
import lotr.common.tileentity.LOTRTileEntityBookshelf;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiBookshelf extends GuiContainer {
   private static final ResourceLocation chestTexture = new ResourceLocation("textures/gui/container/generic_54.png");
   private IInventory playerInv;
   private IInventory shelfInv;
   private int inventoryRows;

   public LOTRGuiBookshelf(IInventory player, LOTRTileEntityBookshelf shelf) {
      super(new LOTRContainerBookshelf(player, shelf));
      this.playerInv = player;
      this.shelfInv = shelf;
      this.field_146291_p = false;
      int i = 222;
      int j = i - 108;
      this.inventoryRows = shelf.func_70302_i_() / 9;
      this.field_147000_g = j + this.inventoryRows * 18;
   }

   protected void func_146979_b(int i, int j) {
      this.field_146289_q.func_78276_b(this.shelfInv.func_145818_k_() ? this.shelfInv.func_145825_b() : StatCollector.func_74838_a(this.shelfInv.func_145825_b()), 8, 6, 4210752);
      this.field_146289_q.func_78276_b(this.playerInv.func_145818_k_() ? this.playerInv.func_145825_b() : StatCollector.func_74838_a(this.playerInv.func_145825_b()), 8, this.field_147000_g - 96 + 2, 4210752);
   }

   protected void func_146976_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(chestTexture);
      int k = (this.field_146294_l - this.field_146999_f) / 2;
      int l = (this.field_146295_m - this.field_147000_g) / 2;
      this.func_73729_b(k, l, 0, 0, this.field_146999_f, this.inventoryRows * 18 + 17);
      this.func_73729_b(k, l + this.inventoryRows * 18 + 17, 0, 126, this.field_146999_f, 96);
   }
}
