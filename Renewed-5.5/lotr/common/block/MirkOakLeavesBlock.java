package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class MirkOakLeavesBlock extends LOTRLeavesBlock {
   public static final BooleanProperty DOWN;

   public MirkOakLeavesBlock() {
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(DOWN, false));
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{DOWN});
   }

   private boolean hasDownState(IWorld world, BlockPos pos) {
      BlockPos belowPos = pos.func_177977_b();
      BlockState belowState = world.func_180495_p(belowPos);
      return Block.func_208061_a(belowState.func_196951_e(world, belowPos), Direction.UP) || belowState.func_177230_c() instanceof HangingWebBlock;
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      return (BlockState)super.func_196258_a(context).func_206870_a(DOWN, this.hasDownState(context.func_195991_k(), context.func_195995_a()));
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      state = super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
      if (facing == Direction.DOWN) {
         state = (BlockState)state.func_206870_a(DOWN, this.hasDownState(world, currentPos));
      }

      return state;
   }

   public int func_200011_d(BlockState state, IBlockReader world, BlockPos pos) {
      return (Boolean)state.func_177229_b(DOWN) ? 15 : super.func_200011_d(state, world, pos);
   }

   static {
      DOWN = BlockStateProperties.field_208150_C;
   }
}
