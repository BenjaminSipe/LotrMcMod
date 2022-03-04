package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockVine extends BlockVine {
   public LOTRBlockVine() {
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
      this.func_149711_c(0.2F);
      this.func_149672_a(Block.field_149779_h);
   }

   @SideOnly(Side.CLIENT)
   public int func_149635_D() {
      return 16777215;
   }

   @SideOnly(Side.CLIENT)
   public int func_149741_i(int i) {
      return 16777215;
   }

   @SideOnly(Side.CLIENT)
   public int func_149720_d(IBlockAccess world, int i, int j, int k) {
      return 16777215;
   }
}
