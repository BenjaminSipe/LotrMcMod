package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;

public class DurnorBlockItem extends BlockItem {
   public DurnorBlockItem(Block block, Properties properties) {
      super(block, properties);
   }

   public int getBurnTime(ItemStack itemstack) {
      return 6000;
   }
}
