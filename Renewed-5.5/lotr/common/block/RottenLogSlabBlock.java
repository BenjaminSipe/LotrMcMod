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

public class RottenLogSlabBlock extends LogSlabBlock {
   private static final VoxelShape HOLLOW_BOTTOM_SHAPE;
   private static final VoxelShape HOLLOW_TOP_SHAPE;
   private static final VoxelShape HOLLOW_NORTH_SHAPE;
   private static final VoxelShape HOLLOW_SOUTH_SHAPE;
   private static final VoxelShape HOLLOW_WEST_SHAPE;
   private static final VoxelShape HOLLOW_EAST_SHAPE;

   public RottenLogSlabBlock(Supplier block) {
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
            return RottenLogBlock.Y_SHAPE;
         }

         if (axis == Axis.X) {
            return RottenLogBlock.X_SHAPE;
         }

         if (axis == Axis.Z) {
            return RottenLogBlock.Z_SHAPE;
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
      HOLLOW_BOTTOM_SHAPE = VoxelShapes.func_197878_a(SlabBlock.field_196506_b, Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D), IBooleanFunction.field_223234_e_);
      HOLLOW_TOP_SHAPE = VoxelShapes.func_197878_a(SlabBlock.field_196507_c, Block.func_208617_a(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D), IBooleanFunction.field_223234_e_);
      HOLLOW_NORTH_SHAPE = VoxelShapes.func_197878_a(AxialSlabBlock.NORTH_SHAPE, Block.func_208617_a(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 8.0D), IBooleanFunction.field_223234_e_);
      HOLLOW_SOUTH_SHAPE = VoxelShapes.func_197878_a(AxialSlabBlock.SOUTH_SHAPE, Block.func_208617_a(2.0D, 2.0D, 8.0D, 14.0D, 14.0D, 16.0D), IBooleanFunction.field_223234_e_);
      HOLLOW_WEST_SHAPE = VoxelShapes.func_197878_a(AxialSlabBlock.WEST_SHAPE, Block.func_208617_a(0.0D, 2.0D, 2.0D, 8.0D, 14.0D, 14.0D), IBooleanFunction.field_223234_e_);
      HOLLOW_EAST_SHAPE = VoxelShapes.func_197878_a(AxialSlabBlock.EAST_SHAPE, Block.func_208617_a(8.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D), IBooleanFunction.field_223234_e_);
   }
}
