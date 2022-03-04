package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class LOTRDirtBlock extends Block {
   public LOTRDirtBlock(Properties properties) {
      super(properties);
   }

   public LOTRDirtBlock(MaterialColor materialColor) {
      this(Properties.func_200949_a(Material.field_151578_c, materialColor).func_200943_b(0.5F).func_200947_a(SoundType.field_185849_b).harvestTool(ToolType.SHOVEL));
   }
}
