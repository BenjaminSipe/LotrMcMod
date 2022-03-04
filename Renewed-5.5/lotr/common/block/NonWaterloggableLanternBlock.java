package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public abstract class NonWaterloggableLanternBlock extends Block {
   public static final BooleanProperty HANGING;

   public NonWaterloggableLanternBlock(Properties properties) {
      super(properties);
      this.func_180632_j((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a(HANGING, false));
   }

   public NonWaterloggableLanternBlock(int light) {
      this(Properties.func_200945_a(Material.field_151573_f).func_235861_h_().func_200943_b(3.5F).func_200947_a(SoundType.field_222475_v).func_235838_a_(LOTRBlocks.constantLight(light)).func_226896_b_());
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{HANGING});
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      FluidState fluid = context.func_195991_k().func_204610_c(context.func_195995_a());
      Direction[] var3 = context.func_196009_e();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Direction dir = var3[var5];
         if (dir.func_176740_k() == Axis.Y) {
            BlockState state = (BlockState)this.func_176223_P().func_206870_a(HANGING, dir == Direction.UP);
            if (state.func_196955_c(context.func_195991_k(), context.func_195995_a())) {
               return state;
            }
         }
      }

      return null;
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      Direction dir = getBlockConnected(state).func_176734_d();
      return Block.func_220055_a(world, pos.func_177972_a(dir), dir.func_176734_d());
   }

   protected static Direction getBlockConnected(BlockState state) {
      return (Boolean)state.func_177229_b(HANGING) ? Direction.DOWN : Direction.UP;
   }

   public PushReaction func_149656_h(BlockState state) {
      return PushReaction.DESTROY;
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      return getBlockConnected(state).func_176734_d() == facing && !state.func_196955_c(world, currentPos) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   public boolean func_196266_a(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }

   static {
      HANGING = BlockStateProperties.field_222514_j;
   }
}
