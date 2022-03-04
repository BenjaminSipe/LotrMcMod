package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class BrickWithAboveBlock extends LOTRStoneBlock {
   public static final BooleanProperty ABOVE;
   private final ITag alikeBlocks;

   public BrickWithAboveBlock(MaterialColor materialColor, ITag blocks) {
      super(materialColor);
      this.alikeBlocks = blocks;
      this.initBrickWithAbove();
   }

   public BrickWithAboveBlock(Supplier blockSup, ITag blocks) {
      super(blockSup);
      this.alikeBlocks = blocks;
      this.initBrickWithAbove();
   }

   private void initBrickWithAbove() {
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(ABOVE, false));
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{ABOVE});
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      BlockState state = super.func_196258_a(context);
      IWorld world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      BlockPos abovePos = pos.func_177984_a();
      BlockState aboveState = world.func_180495_p(abovePos);
      return this.checkAboveBlock(world, pos, state, abovePos, aboveState);
   }

   public BlockState func_196271_a(BlockState state, Direction dir, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      return dir == Direction.UP ? this.checkAboveBlock(world, currentPos, state, facingPos, facingState) : super.func_196271_a(state, dir, facingState, world, currentPos, facingPos);
   }

   private BlockState checkAboveBlock(IWorld world, BlockPos pos, BlockState state, BlockPos abovePos, BlockState aboveState) {
      boolean hasAbove = aboveState.func_235714_a_(this.alikeBlocks) && aboveState.func_224755_d(world, abovePos, Direction.DOWN);
      return (BlockState)state.func_206870_a(ABOVE, hasAbove);
   }

   static {
      ABOVE = LOTRBlockStates.BRICK_ABOVE;
   }
}
