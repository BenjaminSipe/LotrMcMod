package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class ChalkPathBlock extends LOTRPathBlock {
   public ChalkPathBlock(MaterialColor color, float hard, float res) {
      super(Properties.func_200949_a(Material.field_151576_e, color).func_200948_a(hard, res).func_235861_h_());
   }

   protected BlockState getUnpathedBlockState() {
      return ((Block)LOTRBlocks.DIRTY_CHALK.get()).func_176223_P();
   }
}
