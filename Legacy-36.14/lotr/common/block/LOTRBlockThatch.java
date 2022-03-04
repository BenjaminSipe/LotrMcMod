package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRBlockThatch extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon[] thatchIcons;
   private static String[] thatchNames = new String[]{"thatch", "reed"};

   public LOTRBlockThatch() {
      super(Material.field_151577_b);
      this.func_149711_c(0.5F);
      this.func_149672_a(Block.field_149779_h);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= thatchNames.length) {
         j = 0;
      }

      return this.thatchIcons[j];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.thatchIcons = new IIcon[thatchNames.length];

      for(int i = 0; i < thatchNames.length; ++i) {
         this.thatchIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + thatchNames[i]);
      }

   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < thatchNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
