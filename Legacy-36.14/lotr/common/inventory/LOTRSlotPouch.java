package lotr.common.inventory;

import lotr.common.item.LOTRItemPouch;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRSlotPouch extends Slot {
   public LOTRSlotPouch(IInventory inv, int i, int j, int k) {
      super(inv, i, j, k);
   }

   public boolean func_75214_a(ItemStack itemstack) {
      return itemstack.func_77973_b() instanceof LOTRItemPouch ? false : super.func_75214_a(itemstack);
   }
}
