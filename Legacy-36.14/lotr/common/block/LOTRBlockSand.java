package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;

public class LOTRBlockSand extends BlockFalling {
   public LOTRBlockSand() {
      super(Material.field_151595_p);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(0.5F);
      this.func_149672_a(field_149776_m);
   }
}
