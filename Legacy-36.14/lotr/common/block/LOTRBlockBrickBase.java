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

public abstract class LOTRBlockBrickBase extends Block {
   @SideOnly(Side.CLIENT)
   protected IIcon[] brickIcons;
   protected String[] brickNames;

   public LOTRBlockBrickBase() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(1.5F);
      this.func_149752_b(10.0F);
      this.func_149672_a(Block.field_149769_e);
   }

   protected void setBrickNames(String... names) {
      this.brickNames = names;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= this.brickNames.length) {
         j = 0;
      }

      return this.brickIcons[j];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.brickIcons = new IIcon[this.brickNames.length];

      for(int i = 0; i < this.brickNames.length; ++i) {
         this.brickIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.brickNames[i]);
      }

   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.brickNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
