package lotr.common.block;

import lotr.common.event.CompostingHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;

public class LOTRTallFlowerBlock extends TallFlowerBlock {
   public LOTRTallFlowerBlock(Properties properties) {
      super(properties);
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 100);
      CompostingHelper.prepareCompostable(this, 0.65F);
   }

   public LOTRTallFlowerBlock() {
      this(Properties.func_200945_a(Material.field_151582_l).func_200942_a().func_200943_b(0.0F).func_200947_a(SoundType.field_185850_c));
   }
}
