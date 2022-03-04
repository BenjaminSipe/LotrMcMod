package lotr.common.block;

import net.minecraft.block.LadderBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;

public class LOTRLadderBlock extends LadderBlock {
   public LOTRLadderBlock(Properties properties) {
      super(properties);
   }

   public LOTRLadderBlock() {
      this(Properties.func_200945_a(Material.field_151594_q).func_200943_b(0.4F).func_200947_a(SoundType.field_185857_j).func_226896_b_());
   }
}
