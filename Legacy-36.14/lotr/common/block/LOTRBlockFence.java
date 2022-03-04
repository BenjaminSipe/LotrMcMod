package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockFence extends BlockFence {
   private Block plankBlock;

   public LOTRBlockFence(Block planks) {
      super("", Material.field_151575_d);
      this.func_149711_c(2.0F);
      this.func_149752_b(5.0F);
      this.func_149672_a(Block.field_149766_f);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.plankBlock = planks;
   }

   public boolean canPlaceTorchOnTop(World world, int i, int j, int k) {
      return true;
   }

   public int func_149692_a(int i) {
      return i;
   }

   public int func_149645_b() {
      return LOTRMod.proxy.isClient() ? LOTRMod.proxy.getFenceRenderID() : super.func_149645_b();
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
   }

   @SideOnly(Side.CLIENT)
   public IIcon func_149691_a(int i, int j) {
      return this.plankBlock.func_149691_a(i, j);
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      List plankTypes = new ArrayList();
      this.plankBlock.func_149666_a(Item.func_150898_a(this.plankBlock), this.plankBlock.func_149708_J(), plankTypes);

      for(int j = 0; j < plankTypes.size(); ++j) {
         Object obj = plankTypes.get(j);
         if (obj instanceof ItemStack) {
            int meta = ((ItemStack)obj).func_77960_j();
            list.add(new ItemStack(this, 1, meta));
         }
      }

   }
}
