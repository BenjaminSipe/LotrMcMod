package lotr.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class LOTRItemFenceVanilla extends LOTRItemBlockMetadata {
   public LOTRItemFenceVanilla(Block block) {
      super(block);
   }

   public String func_77667_c(ItemStack itemstack) {
      return "tile.lotr.fenceVanilla." + itemstack.func_77960_j();
   }
}
