package lotr.common.block;

import java.util.Random;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class LOTRPathBlock extends Block {
   private static final VoxelShape SHAPE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

   public LOTRPathBlock(Properties basicProperties) {
      super(basicProperties.func_235847_c_(LOTRBlocks::posPredicateTrue).func_235842_b_(LOTRBlocks::posPredicateTrue));
   }

   protected abstract BlockState getUnpathedBlockState();

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return SHAPE;
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      World world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      BlockState defState = this.func_176223_P();
      if (!defState.func_196955_c(world, pos)) {
         Block.func_199601_a(defState, this.getUnpathedBlockState(), world, pos);
      }

      return super.func_196258_a(context);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if (facing == Direction.UP && !state.func_196955_c(world, currentPos)) {
         world.func_205220_G_().func_205360_a(currentPos, this, 1);
      }

      return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   public void func_225534_a_(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
      world.func_175656_a(pos, Block.func_199601_a(state, this.getUnpathedBlockState(), world, pos));
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      BlockState aboveState = world.func_180495_p(pos.func_177984_a());
      return !aboveState.func_185904_a().func_76220_a() || aboveState.func_177230_c() instanceof FenceGateBlock;
   }

   public boolean func_196266_a(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
      return false;
   }

   public boolean func_220074_n(BlockState state) {
      return true;
   }
}
