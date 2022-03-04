package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class LOTRItemPartyHat extends LOTRItemArmor {
   public static final int HAT_WHITE = 16777215;

   public LOTRItemPartyHat() {
      super(LOTRMaterial.COSMETIC, 0);
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int pass) {
      return getHatColor(itemstack);
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      if (isHatDyed(itemstack) && getHatColor(itemstack) != 16777215) {
         list.add(StatCollector.func_74838_a("item.lotr.hat.dyed"));
      }

   }

   public static int getHatColor(ItemStack itemstack) {
      int dye = getSavedDyeColor(itemstack);
      return dye != -1 ? dye : 16777215;
   }

   private static int getSavedDyeColor(ItemStack itemstack) {
      return itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("HatColor") ? itemstack.func_77978_p().func_74762_e("HatColor") : -1;
   }

   public static boolean isHatDyed(ItemStack itemstack) {
      return getSavedDyeColor(itemstack) != -1;
   }

   public static ItemStack setHatColor(ItemStack itemstack, int i) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74768_a("HatColor", i);
      return itemstack;
   }

   public static ItemStack createDyedHat(int i) {
      return setHatColor(new ItemStack(LOTRMod.partyHat), i);
   }

   public static void removeHatDye(ItemStack itemstack) {
      if (itemstack.func_77978_p() != null) {
         itemstack.func_77978_p().func_82580_o("HatColor");
      }

   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
      return "lotr:armor/partyhat.png";
   }
}
