package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraftforge.common.ToolType;

public class IceBrickBlock extends Block {
   public IceBrickBlock(Properties properties) {
      super(properties);
   }

   public IceBrickBlock() {
      this(Properties.func_200945_a(LOTRBlockMaterial.ICE_BRICK).func_235861_h_().func_200941_a(0.98F).func_200943_b(0.5F).func_200947_a(SoundType.field_185853_f).harvestTool(ToolType.PICKAXE));
   }
}
