package lotr.common.block;

import lotr.common.init.LOTRTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.IPlantable;

public abstract class MordorPlantBlock extends Block {
   protected MordorPlantBlock(Properties properties) {
      super(properties);
   }

   protected boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
      return state.func_235714_a_(LOTRTags.Blocks.MORDOR_PLANT_SURFACES) || state.canSustainPlant(world, pos, Direction.UP, (IPlantable)Blocks.field_150349_c);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {
      return !state.func_196955_c(world, pos) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(state, facing, facingState, world, pos, facingPos);
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      BlockPos below = pos.func_177977_b();
      return this.isValidGround(world.func_180495_p(below), world, below);
   }

   public boolean func_200123_i(BlockState state, IBlockReader reader, BlockPos pos) {
      return true;
   }

   public boolean func_196266_a(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
      return type == PathType.AIR && !this.field_235688_at_ ? true : super.func_196266_a(state, world, pos, type);
   }
}
