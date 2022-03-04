package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRItemKaftan extends LOTRItemHaradRobes {
   @SideOnly(Side.CLIENT)
   private IIcon overlayIcon;

   public LOTRItemKaftan(int slot) {
      super(LOTRMaterial.KAFTAN, slot);
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IIconRegister iconregister) {
      super.func_94581_a(iconregister);
      this.overlayIcon = iconregister.func_94245_a(this.func_111208_A() + "_overlay");
   }

   @SideOnly(Side.CLIENT)
   public boolean func_77623_v() {
      return true;
   }

   public IIcon getIcon(ItemStack itemstack, int pass) {
      return pass >= 1 ? this.overlayIcon : super.getIcon(itemstack, pass);
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack itemstack, int pass) {
      return pass >= 1 ? 16777215 : super.func_82790_a(itemstack, pass);
   }

   public int func_82814_b(ItemStack itemstack) {
      return getRobesColor(itemstack);
   }
}
