package lotr.common.block;

import lotr.common.event.CompostingHelper;
import lotr.common.init.LOTRTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class MordorMossBlock extends Block {
   private static final VoxelShape SHAPE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

   public MordorMossBlock() {
      super(Properties.func_200945_a(Material.field_151585_k).func_200942_a().func_200943_b(0.2F).func_200947_a(SoundType.field_185850_c));
      CompostingHelper.prepareCompostable(this, 0.5F);
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return SHAPE;
   }

   private boolean isValidGround(BlockState state, IBlockReader world, BlockPos pos) {
      Block block = state.func_177230_c();
      return block.func_203417_a(LOTRTags.Blocks.MORDOR_PLANT_SURFACES);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {
      return !state.func_196955_c(world, pos) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(state, facing, facingState, world, pos, facingPos);
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      BlockPos below = pos.func_177977_b();
      return this.isValidGround(world.func_180495_p(below), world, below);
   }
}
