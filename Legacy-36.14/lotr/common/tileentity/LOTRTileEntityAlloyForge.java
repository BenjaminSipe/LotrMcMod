package lotr.common.tileentity;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityAlloyForge extends LOTRTileEntityAlloyForgeBase {
   public String getForgeName() {
      return StatCollector.func_74838_a("container.lotr.alloyForge");
   }

   public ItemStack getSmeltingResult(ItemStack itemstack) {
      return super.getSmeltingResult(itemstack);
   }

   protected ItemStack getAlloySmeltingResult(ItemStack itemstack, ItemStack alloyItem) {
      return this.isIron(itemstack) && this.isGoldNugget(alloyItem) ? new ItemStack(LOTRMod.gildedIron) : super.getAlloySmeltingResult(itemstack, alloyItem);
   }
}
