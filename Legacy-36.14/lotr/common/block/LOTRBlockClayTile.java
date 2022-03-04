package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LOTRBlockClayTile extends Block {
   public LOTRBlockClayTile() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(1.25F);
      this.func_149752_b(7.0F);
      this.func_149672_a(Block.field_149769_e);
   }
}
