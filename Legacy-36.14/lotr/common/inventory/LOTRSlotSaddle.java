package lotr.common.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class LOTRSlotSaddle extends Slot {
   public LOTRSlotSaddle(IInventory inv, int i, int j, int k) {
      super(inv, i, j, k);
   }

   public boolean func_75214_a(ItemStack itemstack) {
      return super.func_75214_a(itemstack) && itemstack.func_77973_b() == Items.field_151141_av && !this.func_75216_d();
   }
}
