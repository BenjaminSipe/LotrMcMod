package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRBlockFangornPlant extends LOTRBlockFlower {
   @SideOnly(Side.CLIENT)
   private IIcon[] plantIcons;
   private String[] plantNames = new String[]{"green", "brown", "gold", "yellow", "red", "silver"};

   public LOTRBlockFangornPlant() {
      this.setFlowerBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.8F, 0.8F);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= this.plantNames.length) {
         j = 0;
      }

      return this.plantIcons[j];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.plantIcons = new IIcon[this.plantNames.length];

      for(int i = 0; i < this.plantNames.length; ++i) {
         this.plantIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.plantNames[i]);
      }

   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j < this.plantNames.length; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }
}
