package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class RottenWoodBeamSlabBlock extends LogSlabBlock {
   public static final VoxelShape HOLLOW_BOTTOM_SHAPE;
   public static final VoxelShape HOLLOW_TOP_SHAPE;
   public static final VoxelShape HOLLOW_NORTH_SHAPE;
   public static final VoxelShape HOLLOW_SOUTH_SHAPE;
   public static final VoxelShape HOLLOW_WEST_SHAPE;
   public static final VoxelShape HOLLOW_EAST_SHAPE;

   public RottenWoodBeamSlabBlock(Supplier block) {
      super(block);
   }

   protected boolean canDoubleSlabBeWaterlogged() {
      return true;
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      SlabType slabType = (SlabType)state.func_177229_b(field_196505_a);
      Axis axis = (Axis)state.func_177229_b(this.getSlabAxisProperty());
      if (slabType == SlabType.DOUBLE) {
         if (axis == Axis.Y) {
            return RottenWoodBeamBlock.Y_SHAPE;
         }

         if (axis == Axis.X) {
            return RottenWoodBeamBlock.X_SHAPE;
         }

         if (axis == Axis.Z) {
            return RottenWoodBeamBlock.Z_SHAPE;
         }
      } else {
         boolean top = slabType == SlabType.TOP;
         if (axis == Axis.Y) {
            return top ? HOLLOW_TOP_SHAPE : HOLLOW_BOTTOM_SHAPE;
         }

         if (axis == Axis.X) {
            return top ? HOLLOW_EAST_SHAPE : HOLLOW_WEST_SHAPE;
         }

         if (axis == Axis.Z) {
            return top ? HOLLOW_SOUTH_SHAPE : HOLLOW_NORTH_SHAPE;
         }
      }

      return VoxelShapes.func_197868_b();
   }

   static {
      HOLLOW_BOTTOM_SHAPE = VoxelShapes.func_197878_a(SlabBlock.field_196506_b, VoxelShapes.func_216384_a(Block.func_208617_a(2.0D, 0.0D, 2.0D, 6.0D, 8.0D, 6.0D), new VoxelShape[]{Block.func_208617_a(10.0D, 0.0D, 2.0D, 14.0D, 8.0D, 6.0D), Block.func_208617_a(2.0D, 0.0D, 10.0D, 6.0D, 8.0D, 14.0D), Block.func_208617_a(10.0D, 0.0D, 10.0D, 14.0D, 8.0D, 14.0D)}), IBooleanFunction.field_223234_e_);
      HOLLOW_TOP_SHAPE = VoxelShapes.func_197878_a(SlabBlock.field_196507_c, VoxelShapes.func_216384_a(Block.func_208617_a(2.0D, 8.0D, 2.0D, 6.0D, 16.0D, 6.0D), new VoxelShape[]{Block.func_208617_a(10.0D, 8.0D, 2.0D, 14.0D, 16.0D, 6.0D), Block.func_208617_a(2.0D, 8.0D, 10.0D, 6.0D, 16.0D, 14.0D), Block.func_208617_a(10.0D, 8.0D, 10.0D, 14.0D, 16.0D, 14.0D)}), IBooleanFunction.field_223234_e_);
      HOLLOW_NORTH_SHAPE = VoxelShapes.func_197878_a(AxialSlabBlock.NORTH_SHAPE, VoxelShapes.func_216384_a(Block.func_208617_a(2.0D, 2.0D, 0.0D, 6.0D, 6.0D, 8.0D), new VoxelShape[]{Block.func_208617_a(10.0D, 2.0D, 0.0D, 14.0D, 6.0D, 8.0D), Block.func_208617_a(2.0D, 10.0D, 0.0D, 6.0D, 14.0D, 8.0D), Block.func_208617_a(10.0D, 10.0D, 0.0D, 14.0D, 14.0D, 8.0D)}), IBooleanFunction.field_223234_e_);
      HOLLOW_SOUTH_SHAPE = VoxelShapes.func_197878_a(AxialSlabBlock.SOUTH_SHAPE, VoxelShapes.func_216384_a(Block.func_208617_a(2.0D, 2.0D, 8.0D, 6.0D, 6.0D, 16.0D), new VoxelShape[]{Block.func_208617_a(10.0D, 2.0D, 8.0D, 14.0D, 6.0D, 16.0D), Block.func_208617_a(2.0D, 10.0D, 8.0D, 6.0D, 14.0D, 16.0D), Block.func_208617_a(10.0D, 10.0D, 8.0D, 14.0D, 14.0D, 16.0D)}), IBooleanFunction.field_223234_e_);
      HOLLOW_WEST_SHAPE = VoxelShapes.func_197878_a(AxialSlabBlock.WEST_SHAPE, VoxelShapes.func_216384_a(Block.func_208617_a(0.0D, 2.0D, 2.0D, 8.0D, 6.0D, 6.0D), new VoxelShape[]{Block.func_208617_a(0.0D, 10.0D, 2.0D, 8.0D, 14.0D, 6.0D), Block.func_208617_a(0.0D, 2.0D, 10.0D, 8.0D, 6.0D, 14.0D), Block.func_208617_a(0.0D, 10.0D, 10.0D, 8.0D, 14.0D, 14.0D)}), IBooleanFunction.field_223234_e_);
      HOLLOW_EAST_SHAPE = VoxelShapes.func_197878_a(AxialSlabBlock.EAST_SHAPE, VoxelShapes.func_216384_a(Block.func_208617_a(8.0D, 2.0D, 2.0D, 16.0D, 6.0D, 6.0D), new VoxelShape[]{Block.func_208617_a(8.0D, 10.0D, 2.0D, 16.0D, 14.0D, 6.0D), Block.func_208617_a(8.0D, 2.0D, 10.0D, 16.0D, 6.0D, 14.0D), Block.func_208617_a(8.0D, 10.0D, 10.0D, 16.0D, 14.0D, 14.0D)}), IBooleanFunction.field_223234_e_);
   }
}
