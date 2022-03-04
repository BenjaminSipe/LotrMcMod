package lotr.common.inventory;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class LOTRSlotUnsmeltResult extends LOTRSlotProtected {
   public LOTRSlotUnsmeltResult(IInventory inv, int i, int j, int k) {
      super(inv, i, j, k);
   }

   public void func_82870_a(EntityPlayer entityplayer, ItemStack itemstack) {
      super.func_82870_a(entityplayer, itemstack);
      if (!entityplayer.field_70170_p.field_72995_K) {
         LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.unsmelt);
      }

   }
}
