package lotr.common.tileentity;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityDwarvenForge extends LOTRTileEntityAlloyForgeBase {
   public String getForgeName() {
      return StatCollector.func_74838_a("container.lotr.dwarvenForge");
   }

   public ItemStack getSmeltingResult(ItemStack itemstack) {
      return itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.oreMithril) ? new ItemStack(LOTRMod.mithril) : super.getSmeltingResult(itemstack);
   }

   protected ItemStack getAlloySmeltingResult(ItemStack itemstack, ItemStack alloyItem) {
      if (this.isIron(itemstack) && this.isCoal(alloyItem)) {
         return new ItemStack(LOTRMod.dwarfSteel);
      } else if (this.isIron(itemstack) && alloyItem.func_77973_b() == LOTRMod.quenditeCrystal) {
         return new ItemStack(LOTRMod.galvorn);
      } else {
         return this.isIron(itemstack) && alloyItem.func_77973_b() == Item.func_150898_a(LOTRMod.rock) && alloyItem.func_77960_j() == 3 ? new ItemStack(LOTRMod.blueDwarfSteel) : super.getAlloySmeltingResult(itemstack, alloyItem);
      }
   }
}
