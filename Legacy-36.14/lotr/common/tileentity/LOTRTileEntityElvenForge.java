package lotr.common.tileentity;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityElvenForge extends LOTRTileEntityAlloyForgeBase {
   public String getForgeName() {
      return StatCollector.func_74838_a("container.lotr.elvenForge");
   }

   protected ItemStack getAlloySmeltingResult(ItemStack itemstack, ItemStack alloyItem) {
      if (this.isIron(itemstack) && this.isCoal(alloyItem)) {
         return new ItemStack(LOTRMod.elfSteel);
      } else {
         return this.isSilver(itemstack) && this.isMithrilNugget(alloyItem) ? new ItemStack(LOTRMod.ithildin) : super.getAlloySmeltingResult(itemstack, alloyItem);
      }
   }
}
