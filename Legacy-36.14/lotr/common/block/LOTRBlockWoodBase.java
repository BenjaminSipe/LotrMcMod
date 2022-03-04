package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class LOTRBlockWoodBase extends BlockLog {
   @SideOnly(Side.CLIENT)
   private IIcon[][] woodIcons;
   private String[] woodNames;

   public LOTRBlockWoodBase() {
      this.func_149711_c(2.0F);
      this.func_149672_a(Block.field_149766_f);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
   }

   public void setWoodNames(String... s) {
      this.woodNames = s;
   }

   public Item func_149650_a(int i, Random random, int j) {
      return Item.func_150898_a(this);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      int j1 = j & 12;
      j &= 3;
      if (j >= this.woodNames.length) {
         j = 0;
      }

      return (j1 != 0 || i != 0 && i != 1) && (j1 != 4 || i != 4 && i != 5) && (j1 != 8 || i != 2 && i != 3) ? this.woodIcons[j][1] : this.woodIcons[j][0];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.woodIcons = new IIcon[this.woodNames.length][2];

      for(int i = 0; i < this.woodNames.length; ++i) {
         this.woodIcons[i][0] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.woodNames[i] + "_top");
         this.woodIcons[i][1] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.woodNames[i] + "_side");
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.woodNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
