package lotr.common.block;

import lotr.common.init.LOTRTags;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class MorgulShroomBlock extends LOTRMushroomBlock {
   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      BlockState belowState = world.func_180495_p(pos.func_177977_b());
      return belowState.func_235714_a_(LOTRTags.Blocks.MORDOR_PLANT_SURFACES) ? true : super.func_196260_a(state, world, pos);
   }
}
