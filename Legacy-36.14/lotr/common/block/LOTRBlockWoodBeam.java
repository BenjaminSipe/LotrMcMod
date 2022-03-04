package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class LOTRBlockWoodBeam extends BlockRotatedPillar {
   @SideOnly(Side.CLIENT)
   private IIcon[] sideIcons;
   @SideOnly(Side.CLIENT)
   private IIcon[] topIcons;
   private String[] woodNames;

   public LOTRBlockWoodBeam() {
      super(Material.field_151575_d);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(2.0F);
      this.func_149672_a(Block.field_149766_f);
   }

   protected void setWoodNames(String... s) {
      this.woodNames = s;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.sideIcons = new IIcon[this.woodNames.length];
      this.topIcons = new IIcon[this.woodNames.length];

      for(int i = 0; i < this.woodNames.length; ++i) {
         this.topIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.woodNames[i] + "_top");
         this.sideIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.woodNames[i] + "_side");
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j < this.woodNames.length; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }

   @SideOnly(Side.CLIENT)
   protected IIcon func_150163_b(int i) {
      if (i < 0 || i >= this.woodNames.length) {
         i = 0;
      }

      return this.sideIcons[i];
   }

   @SideOnly(Side.CLIENT)
   protected IIcon func_150161_d(int i) {
      if (i < 0 || i >= this.woodNames.length) {
         i = 0;
      }

      return this.topIcons[i];
   }

   public int func_149645_b() {
      return LOTRMod.proxy.getBeamRenderID();
   }
}
