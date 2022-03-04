package lotr.common.item;

import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class LOTRItemOrcSkullStaff extends LOTRItemSword {
   public LOTRItemOrcSkullStaff() {
      super(LOTRMaterial.MORDOR);
   }

   public boolean func_82789_a(ItemStack itemstack, ItemStack repairItem) {
      return repairItem.func_77973_b() == Items.field_151144_bL;
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return null;
   }
}
