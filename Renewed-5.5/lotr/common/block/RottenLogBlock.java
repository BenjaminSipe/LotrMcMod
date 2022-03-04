package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class RottenLogBlock extends LOTRLogBlock implements IWaterLoggable {
   public static final BooleanProperty WATERLOGGED;
   public static final VoxelShape Y_SHAPE;
   public static final VoxelShape X_SHAPE;
   public static final VoxelShape Z_SHAPE;

   public RottenLogBlock(MaterialColor wood, MaterialColor bark) {
      super(Properties.func_235836_a_(Material.field_151575_d, LOTRLogBlock.logStateToMaterialColor(wood, bark)).func_200943_b(2.0F).func_226896_b_().func_200947_a(SoundType.field_185848_a), bark, wood);
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(WATERLOGGED, false));
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{WATERLOGGED});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      Axis axis = (Axis)state.func_177229_b(field_176298_M);
      switch(axis) {
      case Y:
      default:
         return Y_SHAPE;
      case X:
         return X_SHAPE;
      case Z:
         return Z_SHAPE;
      }
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      BlockState superState = super.func_196258_a(context);
      if (superState != null) {
         FluidState fluid = context.func_195991_k().func_204610_c(context.func_195995_a());
         return (BlockState)superState.func_206870_a(WATERLOGGED, fluid.func_206886_c() == Fluids.field_204546_a);
      } else {
         return null;
      }
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.func_177229_b(WATERLOGGED)) {
         world.func_205219_F_().func_205360_a(currentPos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(world));
      }

      return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   public FluidState func_204507_t(BlockState state) {
      return (Boolean)state.func_177229_b(WATERLOGGED) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
   }

   public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
      return true;
   }

   static {
      WATERLOGGED = BlockStateProperties.field_208198_y;
      Y_SHAPE = VoxelShapes.func_197878_a(VoxelShapes.func_197868_b(), Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D), IBooleanFunction.field_223234_e_);
      X_SHAPE = VoxelShapes.func_197878_a(VoxelShapes.func_197868_b(), Block.func_208617_a(0.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D), IBooleanFunction.field_223234_e_);
      Z_SHAPE = VoxelShapes.func_197878_a(VoxelShapes.func_197868_b(), Block.func_208617_a(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 16.0D), IBooleanFunction.field_223234_e_);
   }
}
