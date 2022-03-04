package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LOTRBlockBone extends Block {
   public LOTRBlockBone() {
      super(Material.field_151576_e);
      this.func_149647_a(LOTRCreativeTabs.tabBlock);
      this.func_149711_c(1.0F);
      this.func_149752_b(5.0F);
      this.func_149672_a(Block.field_149769_e);
   }
}
