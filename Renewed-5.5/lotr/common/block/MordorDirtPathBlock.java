package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class MordorDirtPathBlock extends LOTRPathBlock {
   public MordorDirtPathBlock(MaterialColor color) {
      super(Properties.func_200949_a(Material.field_151578_c, color).func_200943_b(0.5F).func_200947_a(SoundType.field_185849_b).harvestTool(ToolType.SHOVEL));
   }

   protected BlockState getUnpathedBlockState() {
      return ((Block)LOTRBlocks.MORDOR_DIRT.get()).func_176223_P();
   }
}
