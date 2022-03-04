package lotr.common.item;

import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class LOTRPoisonedDrinks {
   private static final int POISON_DURATION = 300;
   public static Potion killingPoison;

   public static void registerPotion() {
      killingPoison = new LOTRPotionPoisonKilling();
   }

   public static void addPoisonEffect(EntityPlayer entityplayer, ItemStack itemstack) {
      int duration = 300;
      entityplayer.func_70690_d(new PotionEffect(killingPoison.field_76415_H, duration));
   }

   public static boolean canPoison(ItemStack itemstack) {
      if (itemstack == null) {
         return false;
      } else {
         return LOTRItemMug.isItemFullDrink(itemstack);
      }
   }

   public static boolean isDrinkPoisoned(ItemStack itemstack) {
      return itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("PoisonDrink") ? itemstack.func_77978_p().func_74767_n("PoisonDrink") : false;
   }

   public static void setDrinkPoisoned(ItemStack itemstack, boolean flag) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74757_a("PoisonDrink", flag);
   }

   public static UUID getPoisonerUUID(ItemStack itemstack) {
      if (itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("PoisonerUUID")) {
         String s = itemstack.func_77978_p().func_74779_i("PoisonerUUID");
         return UUID.fromString(s);
      } else {
         return null;
      }
   }

   public static void setPoisonerPlayer(ItemStack itemstack, EntityPlayer entityplayer) {
      setPoisonerUUID(itemstack, entityplayer.func_110124_au());
   }

   public static void setPoisonerUUID(ItemStack itemstack, UUID uuid) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74778_a("PoisonerUUID", uuid.toString());
   }

   public static boolean canPlayerSeePoisoned(ItemStack itemstack, EntityPlayer entityplayer) {
      UUID uuid = getPoisonerUUID(itemstack);
      if (uuid == null) {
         return true;
      } else {
         return entityplayer.func_110124_au().equals(uuid) || entityplayer.field_71075_bZ.field_75098_d;
      }
   }
}
