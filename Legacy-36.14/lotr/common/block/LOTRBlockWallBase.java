package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class LOTRBlockWallBase extends BlockWall {
   private int subtypes;

   public LOTRBlockWallBase(Block block, int i) {
      super(block);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.subtypes = i;
   }

   public boolean canPlaceTorchOnTop(World world, int i, int j, int k) {
      return true;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int j = 0; j < this.subtypes; ++j) {
         list.add(new ItemStack(item, 1, j));
      }

   }
}
