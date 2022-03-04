package lotr.common.block;

import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class LOTRBlockUtumnoSlabBase extends LOTRBlockSlabBase implements LOTRWorldProviderUtumno.UtumnoBlock {
   public LOTRBlockUtumnoSlabBase(boolean flag, int n) {
      super(flag, Material.field_151576_e, n);
      this.func_149711_c(1.5F);
      this.func_149752_b(Float.MAX_VALUE);
      this.func_149672_a(Block.field_149769_e);
   }
}
