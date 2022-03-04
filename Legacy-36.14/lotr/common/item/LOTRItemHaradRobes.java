package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class LOTRItemHaradRobes extends LOTRItemArmor {
   public static int ROBES_WHITE = 16777215;

   public LOTRItemHaradRobes(int slot) {
      this(LOTRMaterial.HARAD_ROBES, slot);
   }

   public LOTRItemHaradRobes(LOTRMaterial material, int slot) {
      super(material, slot);
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int pass) {
      return getRobesColor(itemstack);
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      if (areRobesDyed(itemstack)) {
         list.add(StatCollector.func_74838_a("item.lotr.haradRobes.dyed"));
      }

   }

   public static int getRobesColor(ItemStack itemstack) {
      int dye = getSavedDyeColor(itemstack);
      return dye != -1 ? dye : ROBES_WHITE;
   }

   private static int getSavedDyeColor(ItemStack itemstack) {
      return itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("RobesColor") ? itemstack.func_77978_p().func_74762_e("RobesColor") : -1;
   }

   public static boolean areRobesDyed(ItemStack itemstack) {
      return getSavedDyeColor(itemstack) != -1;
   }

   public static void setRobesColor(ItemStack itemstack, int i) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74768_a("RobesColor", i);
   }

   public static void removeRobeDye(ItemStack itemstack) {
      if (itemstack.func_77978_p() != null) {
         itemstack.func_77978_p().func_82580_o("RobesColor");
      }

   }
}
