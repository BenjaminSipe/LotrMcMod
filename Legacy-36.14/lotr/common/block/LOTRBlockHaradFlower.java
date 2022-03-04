package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRBlockHaradFlower extends LOTRBlockFlower {
   @SideOnly(Side.CLIENT)
   private IIcon[] flowerIcons;
   private static String[] flowerNames = new String[]{"red", "yellow", "daisy", "pink"};

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= flowerNames.length) {
         j = 0;
      }

      return this.flowerIcons[j];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.flowerIcons = new IIcon[flowerNames.length];

      for(int i = 0; i < flowerNames.length; ++i) {
         this.flowerIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + flowerNames[i]);
      }

   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j < flowerNames.length; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }
}
