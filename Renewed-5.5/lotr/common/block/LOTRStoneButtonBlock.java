package lotr.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;

public class LOTRStoneButtonBlock extends WoodButtonBlock {
   public LOTRStoneButtonBlock() {
      super(Properties.func_200945_a(Material.field_151594_q).func_200942_a().func_200943_b(0.5F).func_200947_a(SoundType.field_185851_d));
   }
}
