package lotr.common.inv;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ShulkerBoxSlot;
import net.minecraft.item.ItemStack;

public class FixedShulkerBoxSlot extends ShulkerBoxSlot {
   public FixedShulkerBoxSlot(IInventory inv, int index, int x, int y) {
      super(inv, index, x, y);
   }

   public boolean func_75214_a(ItemStack stack) {
      return super.func_75214_a(stack) && PouchSlot.isItemValidForPouchSlotExcludingShulkerBoxes(stack);
   }
}
