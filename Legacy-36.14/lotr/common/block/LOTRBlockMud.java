package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockMud extends Block {
   public LOTRBlockMud() {
      super(Material.field_151578_c);
      this.func_149711_c(0.5F);
      this.func_149672_a(Block.field_149767_g);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
   }

   public int func_149692_a(int i) {
      return i;
   }

   @SideOnly(Side.CLIENT)
   public void func_149666_a(Item item, CreativeTabs tab, List list) {
      for(int i = 0; i < 2; ++i) {
         list.add(new ItemStack(item, 1, i));
      }

   }

   public int func_149643_k(World world, int i, int j, int k) {
      return world.func_72805_g(i, j, k);
   }

   public boolean canSustainPlant(IBlockAccess world, int i, int j, int k, ForgeDirection direction, IPlantable plantable) {
      return Blocks.field_150346_d.canSustainPlant(world, i, j, k, direction, plantable) || plantable instanceof BlockStem;
   }
}
