package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class LOTRItemFeatherDyed extends Item {
   public LOTRItemFeatherDyed() {
      this.func_77625_d(1);
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_77617_a(int i) {
      return Items.field_151008_G.func_77617_a(i);
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int pass) {
      return getFeatherColor(itemstack);
   }

   public static int getFeatherColor(ItemStack itemstack) {
      return itemstack.func_77978_p() != null && itemstack.func_77978_p().func_74764_b("FeatherColor") ? itemstack.func_77978_p().func_74762_e("FeatherColor") : 16777215;
   }

   public static boolean isFeatherDyed(ItemStack itemstack) {
      return getFeatherColor(itemstack) != 16777215;
   }

   public static void setFeatherColor(ItemStack itemstack, int i) {
      if (itemstack.func_77978_p() == null) {
         itemstack.func_77982_d(new NBTTagCompound());
      }

      itemstack.func_77978_p().func_74768_a("FeatherColor", i);
   }

   public static void removeFeatherDye(ItemStack itemstack) {
      setFeatherColor(itemstack, 16777215);
   }
}
