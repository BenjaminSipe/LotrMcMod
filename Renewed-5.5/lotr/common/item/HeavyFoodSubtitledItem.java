package lotr.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;

public class HeavyFoodSubtitledItem extends SubtitledItem {
   public HeavyFoodSubtitledItem(Properties properties) {
      super(properties);
   }

   public int func_77626_a(ItemStack stack) {
      return super.func_77626_a(stack) * 2;
   }
}
