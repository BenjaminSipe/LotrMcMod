package lotr.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class LOTRSlotImmutable extends LOTRSlotProtected {
   public LOTRSlotImmutable(IInventory inv, int i, int j, int k) {
      super(inv, i, j, k);
   }

   public boolean func_82869_a(EntityPlayer player) {
      return false;
   }
}
