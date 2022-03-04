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

public abstract class LOTRBlockPlanksBase extends Block {
   @SideOnly(Side.CLIENT)
   private IIcon[] plankIcons;
   private String[] plankTypes;

   public LOTRBlockPlanksBase() {
      super(Material.field_151575_d);
      this.func_149711_c(2.0F);
      this.func_149752_b(5.0F);
      this.func_149672_a(Block.field_149766_f);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
   }

   protected void setPlankTypes(String... types) {
      this.plankTypes = types;
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= this.plankTypes.length) {
         j = 0;
      }

      return this.plankIcons[j];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.plankIcons = new IIcon[this.plankTypes.length];

      for(int i = 0; i < this.plankTypes.length; ++i) {
         this.plankIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.plankTypes[i]);
      }

   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j < this.plankTypes.length; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }
}
