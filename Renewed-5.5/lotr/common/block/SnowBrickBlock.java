package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraftforge.common.ToolType;

public class SnowBrickBlock extends Block {
   public SnowBrickBlock(Properties properties) {
      super(properties);
   }

   public SnowBrickBlock() {
      this(Properties.func_200945_a(LOTRBlockMaterial.SNOW_BRICK).func_235861_h_().func_200943_b(0.4F).func_200947_a(SoundType.field_185856_i).harvestTool(ToolType.SHOVEL));
   }
}
