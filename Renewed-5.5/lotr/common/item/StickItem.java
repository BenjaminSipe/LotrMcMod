package lotr.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;

public class StickItem extends Item {
   public StickItem(Properties properties) {
      super(properties);
   }

   public int getBurnTime(ItemStack itemstack) {
      return 100;
   }
}
