package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRBlockClayTileDyed extends LOTRBlockClayTile {
   @SideOnly(Side.CLIENT)
   private IIcon[] clayIcons;

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.clayIcons = new IIcon[16];

      for(int i = 0; i < this.clayIcons.length; ++i) {
         int dyeMeta = BlockColored.func_150031_c(i);
         this.clayIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + ItemDye.field_150923_a[dyeMeta]);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= 16) {
         j = 0;
      }

      return this.clayIcons[j];
   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < 16; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
