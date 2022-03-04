package lotr.common.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;

public class LOTRStainedGlassPaneBlock extends StainedGlassPaneBlock {
   public LOTRStainedGlassPaneBlock(DyeColor color, Properties properties) {
      super(color, properties);
   }

   public LOTRStainedGlassPaneBlock(DyeColor color) {
      this(color, Properties.func_200945_a(Material.field_151592_s).func_200943_b(0.3F).func_200947_a(SoundType.field_185853_f).func_226896_b_());
   }
}
