package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockFangornRiverweed extends BlockLilyPad {
   public LOTRBlockFangornRiverweed() {
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
   }

   @SideOnly(Side.CLIENT)
   public int func_149635_D() {
      return 16777215;
   }

   @SideOnly(Side.CLIENT)
   public int func_149741_i(int meta) {
      return 16777215;
   }

   @SideOnly(Side.CLIENT)
   public int func_149720_d(IBlockAccess world, int i, int j, int k) {
      return 16777215;
   }
}
