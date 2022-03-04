package lotr.common.inv;

import lotr.common.item.PouchItem;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class PouchSlot extends Slot {
   public PouchSlot(IInventory inv, int index, int x, int y) {
      super(inv, index, x, y);
   }

   public boolean func_75214_a(ItemStack stack) {
      return isItemValidForPouchSlot(stack);
   }

   public static boolean isItemValidForPouchSlot(ItemStack stack) {
      return isItemValidForPouchSlotExcludingShulkerBoxes(stack) && !(Block.func_149634_a(stack.func_77973_b()) instanceof ShulkerBoxBlock);
   }

   public static boolean isItemValidForPouchSlotExcludingShulkerBoxes(ItemStack stack) {
      return !(stack.func_77973_b() instanceof PouchItem);
   }
}
