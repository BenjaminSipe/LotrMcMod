package lotr.common.block;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;

public class LOTRBlockTrapdoor extends BlockTrapDoor {
   public LOTRBlockTrapdoor() {
      this(Material.field_151575_d);
      this.func_149672_a(Block.field_149766_f);
      this.func_149711_c(3.0F);
   }

   public LOTRBlockTrapdoor(Material material) {
      super(material);
      this.func_149647_a(LOTRCreativeTabs.tabUtil);
   }
}
