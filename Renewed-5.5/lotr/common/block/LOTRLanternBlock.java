package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;

public class LOTRLanternBlock extends LanternBlock {
   public LOTRLanternBlock(Properties properties) {
      super(properties);
   }

   public LOTRLanternBlock(int light) {
      this(Properties.func_200945_a(Material.field_151573_f).func_235861_h_().func_200943_b(3.5F).func_200947_a(SoundType.field_222475_v).func_235838_a_(LOTRBlocks.constantLight(light)).func_226896_b_());
   }

   public LOTRLanternBlock() {
      this(15);
   }
}
