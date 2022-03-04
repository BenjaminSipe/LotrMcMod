package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRBlockStainedGlass extends LOTRBlockGlass {
   private IIcon[] glassIcons = new IIcon[16];

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return this.glassIcons[j % this.glassIcons.length];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      for(int i = 0; i < this.glassIcons.length; ++i) {
         this.glassIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + ItemDye.field_150921_b[BlockStainedGlass.func_149997_b(i)]);
      }

   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public int func_149701_w() {
      return 1;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.glassIcons.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
