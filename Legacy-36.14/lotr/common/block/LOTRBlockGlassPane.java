package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockGlassPane extends LOTRBlockPane {
   public LOTRBlockGlassPane() {
      super("lotr:glass", "lotr:glass_pane_top", Material.field_151592_s, false);
      this.func_149711_c(0.3F);
      this.func_149672_a(Block.field_149778_k);
      this.func_149647_a(LOTRCreativeTabs.tabDeco);
   }

   public boolean canPaneConnectTo(IBlockAccess world, int i, int j, int k, ForgeDirection dir) {
      return super.canPaneConnectTo(world, i, j, k, dir) || world.func_147439_a(i, j, k) instanceof LOTRBlockGlass;
   }
}
