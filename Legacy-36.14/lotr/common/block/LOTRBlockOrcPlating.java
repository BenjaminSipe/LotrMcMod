package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRBlockOrcPlating extends Block {
   @SideOnly(Side.CLIENT)
   protected IIcon[] blockIcons;
   protected String[] blockNames = new String[]{"iron", "rust"};

   public LOTRBlockOrcPlating() {
      super(Material.field_151573_f);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(3.0F);
      this.func_149752_b(10.0F);
      this.func_149672_a(Block.field_149777_j);
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= this.blockNames.length) {
         j = 0;
      }

      return this.blockIcons[j];
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.blockIcons = new IIcon[this.blockNames.length];

      for(int i = 0; i < this.blockNames.length; ++i) {
         this.blockIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.blockNames[i]);
      }

   }

   public int func_149692_a(int i) {
      return i;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getOrcPlatingRenderID();
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.blockNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }
}
