package lotr.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class VesselOperations {
   public static boolean isItemEmptyVessel(ItemStack itemstack) {
      return itemstack.func_77973_b() instanceof IEmptyVesselItem;
   }

   public static boolean isItemFullVessel(ItemStack itemstack) {
      return itemstack.func_77973_b() instanceof VesselDrinkItem;
   }

   public static VesselType getVessel(ItemStack itemstack) {
      Item item = itemstack.func_77973_b();
      if (item instanceof IEmptyVesselItem) {
         return ((IEmptyVesselItem)item).getVesselType();
      } else {
         return item instanceof VesselDrinkItem ? VesselDrinkItem.getVessel(itemstack) : VesselType.WOODEN_MUG;
      }
   }

   public static ItemStack getWithVesselSet(ItemStack itemstack, VesselType vesselType, boolean correctItem) {
      if (isItemEmptyVessel(itemstack)) {
         return getVessel(itemstack).createEmpty();
      } else if (isItemFullVessel(itemstack)) {
         ItemStack copy = itemstack.func_77946_l();
         if (correctItem) {
         }

         VesselDrinkItem.setVessel(copy, vesselType);
         return copy;
      } else {
         return itemstack;
      }
   }

   public static ItemStack getEquivalentDrink(ItemStack itemstack) {
      return itemstack;
   }

   public static ItemStack getRealDrink(ItemStack itemstack) {
      return itemstack;
   }
}
