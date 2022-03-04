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
import net.minecraft.world.IBlockAccess;

public abstract class LOTRBlockOreStorageBase extends Block {
   @SideOnly(Side.CLIENT)
   protected IIcon[] oreStorageIcons;
   protected String[] oreStorageNames;

   public LOTRBlockOreStorageBase() {
      super(Material.field_151573_f);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(5.0F);
      this.func_149752_b(10.0F);
      this.func_149672_a(Block.field_149777_j);
   }

   protected void setOreStorageNames(String... names) {
      this.oreStorageNames = names;
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.oreStorageIcons = new IIcon[this.oreStorageNames.length];

      for(int i = 0; i < this.oreStorageNames.length; ++i) {
         this.oreStorageIcons[i] = iconregister.func_94245_a(this.func_149641_N() + "_" + this.oreStorageNames[i]);
      }

   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      if (j >= this.oreStorageNames.length) {
         j = 0;
      }

      return this.oreStorageIcons[j];
   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < this.oreStorageNames.length; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   public boolean isBeaconBase(IBlockAccess world, int i, int j, int k, int beaconX, int beaconY, int beaconZ) {
      return true;
   }

   protected boolean func_149700_E() {
      return true;
   }
}
