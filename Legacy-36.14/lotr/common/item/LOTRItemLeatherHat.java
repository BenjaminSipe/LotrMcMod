package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class LOTRItemLeatherHat extends LOTRItemArmor {
   public static final int HAT_LEATHER = 6834742;
   public static final int HAT_SHIRRIFF_CHIEF = 2301981;
   public static final int HAT_BLACK = 0;
   public static final int FEATHER_WHITE = 16777215;
   public static final int FEATHER_SHIRRIFF_CHIEF = 3381529;
   public static final int FEATHER_BREE_CAPTAIN = 40960;
   @SideOnly(Side.CLIENT)
   private IIcon featherIcon;

   public LOTRItemLeatherHat() {
      super(LOTRMaterial.COSMETIC, 0);
      this.func_77637_a(LOTRCreativeTabs.tabMisc);
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      super.func_94581_a(iconregister);
      this.featherIcon = iconregister.func_94245_a(this.func_111208_A() + "_feather");
   }

   @SideOnly(Side.CLIENT)
   public boolean func_77623_v() {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(ItemStack itemstack, int pass) {
      return pass == 1 && hasFeather(itemstack) ? this.featherIcon : this.field_77791_bV;
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int pass) {
      return pass == 1 && hasFeather(itemstack) ? getFeatherColor(itemstack) : getHatColor(itemstack);
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag) {
      if (isHatDyed(itemstack)) {
         list.add(StatCollector.func_74838_a("item.lotr.hat.dyed"));
      }

      if (hasFeather(itemstack)) {
         list.add(StatCollector.func_74838_a("item.lotr.hat.feathered"));
      }

   }

   public static int getHatColor(ItemStack itemstack) {
      int dye = getSavedDyeColor(itemstack);
      return dye != -1 ? dye : 6834742;
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

   public static int getFeatherColor(ItemStack itemstack) {
      int i = -1;
      if (itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("FeatherColor")) {
         i = itemstack.func_77978_p().func_74762_e("FeatherColor");
      }

      return i;
   }

   public static boolean hasFeather(ItemStack itemstack) {
      return getFeatherColor(itemstack) != -1;
   }

   public static boolean isFeatherDyed(ItemStack itemstack) {
      return hasFeather(itemstack) && getFeatherColor(itemstack) != 16777215;
   }

   public static ItemStack setFeatherColor(ItemStack itemstack, int i) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74768_a("FeatherColor", i);
      return itemstack;
   }

   public static void removeHatAndFeatherDye(ItemStack itemstack) {
      if (itemstack.func_77978_p() != null) {
         itemstack.func_77978_p().func_82580_o("HatColor");
      }

      if (hasFeather(itemstack) && isFeatherDyed(itemstack)) {
         setFeatherColor(itemstack, 16777215);
      }

   }

   public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
      return "lotr:armor/hat.png";
   }
}
