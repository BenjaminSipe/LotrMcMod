package lotr.common.inv;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.FurnaceFuelSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ForgeFuelSlot extends Slot {
   private final AbstractAlloyForgeContainer container;

   public ForgeFuelSlot(AbstractAlloyForgeContainer forge, IInventory inv, int i, int x, int y) {
      super(inv, i, x, y);
      this.container = forge;
   }

   public boolean func_75214_a(ItemStack stack) {
      return this.container.isFuel(stack) || FurnaceFuelSlot.func_178173_c_(stack);
   }

   public int func_178170_b(ItemStack stack) {
      return FurnaceFuelSlot.func_178173_c_(stack) ? 1 : super.func_178170_b(stack);
   }
}
