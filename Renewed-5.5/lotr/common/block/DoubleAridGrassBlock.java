package lotr.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class DoubleAridGrassBlock extends LOTRDoubleGrassBlock {
   protected boolean func_200014_a_(BlockState state, IBlockReader world, BlockPos pos) {
      return super.func_200014_a_(state, world, pos) || state.func_235714_a_(BlockTags.field_203436_u);
   }
}
