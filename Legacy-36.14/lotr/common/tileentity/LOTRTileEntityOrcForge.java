package lotr.common.tileentity;

import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityOrcForge extends LOTRTileEntityAlloyForgeBase {
   public String getForgeName() {
      return StatCollector.func_74838_a("container.lotr.orcForge");
   }

   public ItemStack getSmeltingResult(ItemStack itemstack) {
      if (this.isWood(itemstack)) {
         boolean isCharred = itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.wood) && itemstack.func_77960_j() == 3;
         if (!isCharred) {
            return new ItemStack(LOTRMod.wood, 1, 3);
         }
      }

      if (itemstack.func_77973_b() == Item.func_150898_a(LOTRMod.oreMorgulIron)) {
         return new ItemStack(LOTRMod.orcSteel);
      } else {
         if (itemstack.func_77973_b() instanceof ItemFood) {
            ItemFood food = (ItemFood)itemstack.func_77973_b();
            if (food.func_77845_h()) {
               return new ItemStack(Items.field_151078_bh);
            }
         }

         return super.getSmeltingResult(itemstack);
      }
   }

   protected ItemStack getAlloySmeltingResult(ItemStack itemstack, ItemStack alloyItem) {
      if (this.isIron(itemstack) && this.isCoal(alloyItem)) {
         return new ItemStack(LOTRMod.urukSteel);
      } else if (this.isOrcSteel(itemstack) && alloyItem.func_77973_b() == LOTRMod.guldurilCrystal) {
         return new ItemStack(LOTRMod.morgulSteel);
      } else {
         return this.isOrcSteel(itemstack) && alloyItem.func_77973_b() == LOTRMod.nauriteGem ? new ItemStack(LOTRMod.blackUrukSteel) : super.getAlloySmeltingResult(itemstack, alloyItem);
      }
   }
}
