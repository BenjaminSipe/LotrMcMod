package lotr.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;

public class DurnorItem extends Item {
   public DurnorItem(Properties properties) {
      super(properties);
   }

   public int getBurnTime(ItemStack itemstack) {
      return 600;
   }
}
