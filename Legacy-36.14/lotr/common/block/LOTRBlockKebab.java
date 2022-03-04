package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class LOTRBlockKebab extends Block {
   public LOTRBlockKebab() {
      super(Material.field_151595_p);
      this.func_149647_a(LOTRCreativeTabs.tabFood);
      this.func_149711_c(0.5F);
      this.func_149672_a(Block.field_149766_f);
   }
}
