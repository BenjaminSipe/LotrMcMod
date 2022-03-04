package lotr.common.quest;

import java.util.UUID;
import lotr.common.util.LOTRLog;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IPickpocketable {
   boolean canPickpocket();

   ItemStack createPickpocketItem();

   public static class Helper {
      public static void setPickpocketData(ItemStack itemstack, String ownerName, String wanterName, UUID wantedID) {
         NBTTagCompound data = new NBTTagCompound();
         data.func_74778_a("Owner", ownerName);
         data.func_74778_a("Wanter", wanterName);
         data.func_74778_a("WanterID", wantedID.toString());
         itemstack.func_77983_a("LOTRPickpocket", data);
      }

      public static boolean isPickpocketed(ItemStack itemstack) {
         return itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("LOTRPickpocket");
      }

      public static String getOwner(ItemStack itemstack) {
         return itemstack.func_77942_o() ? itemstack.func_77978_p().func_74775_l("LOTRPickpocket").func_74779_i("Owner") : null;
      }

      public static String getWanter(ItemStack itemstack) {
         return itemstack.func_77942_o() ? itemstack.func_77978_p().func_74775_l("LOTRPickpocket").func_74779_i("Wanter") : null;
      }

      public static UUID getWanterID(ItemStack itemstack) {
         if (itemstack.func_77942_o()) {
            String id = itemstack.func_77978_p().func_74775_l("LOTRPickpocket").func_74779_i("WanterID");

            try {
               return UUID.fromString(id);
            } catch (IllegalArgumentException var3) {
               LOTRLog.logger.warn("Item %s has invalid pickpocketed wanter ID %s", new Object[]{itemstack.func_82833_r(), id});
            }
         }

         return null;
      }
   }
}
