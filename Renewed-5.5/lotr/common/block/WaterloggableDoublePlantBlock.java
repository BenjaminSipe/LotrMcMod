package lotr.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class WaterloggableDoublePlantBlock extends DoublePlantBlock implements IWaterLoggable {
   public static final BooleanProperty WATERLOGGED;

   public WaterloggableDoublePlantBlock(Properties properties) {
      super(properties);
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(WATERLOGGED, false));
   }

   public WaterloggableDoublePlantBlock() {
      this(Properties.func_200945_a(Material.field_151582_l).func_200942_a().func_200943_b(0.0F).func_200947_a(SoundType.field_185850_c));
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{WATERLOGGED});
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

   public void func_196390_a(IWorld world, BlockPos pos, int flags) {
      BlockPos abovePos = pos.func_177984_a();
      boolean waterlogged = world.func_204610_c(pos).func_206886_c() == Fluids.field_204546_a;
      boolean waterloggedAbove = world.func_204610_c(abovePos).func_206886_c() == Fluids.field_204546_a;
      world.func_180501_a(pos, (BlockState)((BlockState)this.func_176223_P().func_206870_a(field_176492_b, DoubleBlockHalf.LOWER)).func_206870_a(WATERLOGGED, waterlogged), flags);
      world.func_180501_a(abovePos, (BlockState)((BlockState)this.func_176223_P().func_206870_a(field_176492_b, DoubleBlockHalf.UPPER)).func_206870_a(WATERLOGGED, waterloggedAbove), flags);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.func_177229_b(WATERLOGGED)) {
         world.func_205219_F_().func_205360_a(currentPos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(world));
      }

      return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   public void func_176208_a(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      DoubleBlockHalf half = (DoubleBlockHalf)state.func_177229_b(field_176492_b);
      BlockPos otherHalfPos = half == DoubleBlockHalf.LOWER ? pos.func_177984_a() : pos.func_177977_b();
      BlockState otherHalfState = world.func_180495_p(otherHalfPos);
      boolean wasOtherHalfWaterlogged = otherHalfState.func_235901_b_(WATERLOGGED) && (Boolean)otherHalfState.func_177229_b(WATERLOGGED);
      super.func_176208_a(world, pos, state, player);
      if (wasOtherHalfWaterlogged) {
         FluidState otherHalfFluidState = otherHalfState.func_204520_s();
         FluidState otherHalfReplacedFluidState = world.func_204610_c(otherHalfPos);
         if (otherHalfReplacedFluidState.func_206886_c() != otherHalfFluidState.func_206886_c()) {
            world.func_180501_a(otherHalfPos, otherHalfFluidState.func_206883_i(), 3);
         }
      }

   }

   public FluidState func_204507_t(BlockState state) {
      return (Boolean)state.func_177229_b(WATERLOGGED) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
   }

   static {
      WATERLOGGED = BlockStateProperties.field_208198_y;
   }
}
