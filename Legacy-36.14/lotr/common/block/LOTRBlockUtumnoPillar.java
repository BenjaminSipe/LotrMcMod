package lotr.common.block;

import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;

public class LOTRBlockUtumnoPillar extends LOTRBlockPillarBase implements LOTRWorldProviderUtumno.UtumnoBlock {
   public LOTRBlockUtumnoPillar() {
      this.setPillarNames(new String[]{"fire", "ice", "obsidian"});
      this.func_149711_c(1.5F);
      this.func_149752_b(Float.MAX_VALUE);
      this.func_149672_a(Block.field_149769_e);
   }
}
