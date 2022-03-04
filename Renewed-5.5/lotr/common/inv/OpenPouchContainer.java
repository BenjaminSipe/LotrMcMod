package lotr.common.inv;

import net.minecraft.item.ItemStack;

public interface OpenPouchContainer {
   boolean isOpenPouch(ItemStack var1);

   int getPouchCapacity();

   PouchInventory getPouchInventory();

   void reloadPouchFromPickup();
}
