package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class DirtyChalkBlock extends ChalkBlock {
   public static final BooleanProperty BELOW;

   public DirtyChalkBlock(Supplier blockSup) {
      super(blockSup);
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(BELOW, false));
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{BELOW});
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      BlockState state = super.func_196258_a(context);
      IWorld world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      BlockPos belowPos = pos.func_177977_b();
      BlockState belowState = world.func_180495_p(belowPos);
      return this.checkDirtyChalkBelow(world, pos, state, belowPos, belowState);
   }

   public BlockState func_196271_a(BlockState state, Direction dir, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      return dir == Direction.DOWN ? this.checkDirtyChalkBelow(world, currentPos, state, facingPos, facingState) : super.func_196271_a(state, dir, facingState, world, currentPos, facingPos);
   }

   private BlockState checkDirtyChalkBelow(IWorld world, BlockPos pos, BlockState state, BlockPos belowPos, BlockState belowState) {
      boolean hasBelow = belowState.func_177230_c() == this;
      return (BlockState)state.func_206870_a(BELOW, hasBelow);
   }

   static {
      BELOW = LOTRBlockStates.DIRTY_CHALK_BELOW;
   }
}
