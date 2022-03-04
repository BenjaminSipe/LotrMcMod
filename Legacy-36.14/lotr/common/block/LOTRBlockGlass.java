package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LOTRBlockGlass extends BlockGlass {
   private boolean thirdParam = false;

   public LOTRBlockGlass() {
      super(Material.field_151592_s, false);
      this.func_149711_c(0.3F);
      this.func_149672_a(Block.field_149778_k);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
   }

   @SideOnly(Side.CLIENT)
   public boolean func_149646_a(IBlockAccess world, int i, int j, int k, int side) {
      Block block = world.func_147439_a(i, j, k);
      if (world.func_72805_g(i, j, k) != world.func_72805_g(i - Facing.field_71586_b[side], j - Facing.field_71587_c[side], k - Facing.field_71585_d[side])) {
         return true;
      } else if (block == this) {
         return false;
      } else {
         return !this.thirdParam && block == this ? false : super.func_149646_a(world, i, j, k, side);
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_149651_a(IIconRegister iconregister) {
      this.field_149761_L = iconregister.func_94245_a(this.func_149641_N());
   }

   public boolean canPlaceTorchOnTop(World world, int i, int j, int k) {
      return true;
   }
}
