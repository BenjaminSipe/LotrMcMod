package lotr.common.block;

import java.util.Random;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DoubleTorchBlock extends Block {
   public static final EnumProperty HALF;
   private static final VoxelShape LOWER_SHAPE;
   private static final VoxelShape UPPER_SHAPE;

   public DoubleTorchBlock(int light) {
      this(Properties.func_200945_a(Material.field_151594_q).func_200942_a().func_200943_b(0.0F).func_235838_a_(getDoubleTorchLightLevel(light)).func_200947_a(SoundType.field_185848_a));
   }

   public DoubleTorchBlock(Properties properties) {
      super(properties);
      this.func_180632_j((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a(HALF, DoubleBlockHalf.LOWER));
   }

   private static ToIntFunction getDoubleTorchLightLevel(int level) {
      return (state) -> {
         DoubleBlockHalf half = (DoubleBlockHalf)state.func_177229_b(HALF);
         return half == DoubleBlockHalf.UPPER ? level : 0;
      };
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      DoubleBlockHalf half = (DoubleBlockHalf)state.func_177229_b(HALF);
      return half == DoubleBlockHalf.UPPER ? UPPER_SHAPE : LOWER_SHAPE;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      DoubleBlockHalf half = (DoubleBlockHalf)state.func_177229_b(HALF);
      if (half == DoubleBlockHalf.UPPER) {
         double d0 = (double)pos.func_177958_n() + 0.5D;
         double d1 = (double)pos.func_177956_o() + 0.6D;
         double d2 = (double)pos.func_177952_p() + 0.5D;
         world.func_195594_a(ParticleTypes.field_197601_L, d0, d1, d2, 0.0D, 0.0D, 0.0D);
         world.func_195594_a(ParticleTypes.field_197631_x, d0, d1, d2, 0.0D, 0.0D, 0.0D);
      }

   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      DoubleBlockHalf half = (DoubleBlockHalf)state.func_177229_b(HALF);
      if (facing.func_176740_k() != Axis.Y || half == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.func_177230_c() == this && facingState.func_177229_b(HALF) != half) {
         if (half == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.func_196955_c(world, currentPos)) {
            return Blocks.field_150350_a.func_176223_P();
         }

         if (state.func_196955_c(world, currentPos)) {
            return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
         }
      }

      return Blocks.field_150350_a.func_176223_P();
   }

   @Nullable
   public BlockState func_196258_a(BlockItemUseContext context) {
      BlockPos pos = context.func_195995_a();
      return pos.func_177956_o() < context.func_195991_k().func_230315_m_().func_241513_m_() - 1 && context.func_195991_k().func_180495_p(pos.func_177984_a()).func_196953_a(context) ? super.func_196258_a(context) : null;
   }

   public void func_180633_a(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      world.func_180501_a(pos.func_177984_a(), (BlockState)this.func_176223_P().func_206870_a(HALF, DoubleBlockHalf.UPPER), 3);
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      if (state.func_177229_b(HALF) == DoubleBlockHalf.UPPER) {
         boolean isPresent = state.func_177230_c() == this;
         if (!isPresent) {
            return true;
         } else {
            BlockState belowState = world.func_180495_p(pos.func_177977_b());
            return belowState.func_177230_c() == this && belowState.func_177229_b(HALF) == DoubleBlockHalf.LOWER;
         }
      } else {
         return func_220055_a(world, pos.func_177977_b(), Direction.UP);
      }
   }

   public void placeTorchAt(IWorld world, BlockPos pos, int flags) {
      world.func_180501_a(pos, (BlockState)this.func_176223_P().func_206870_a(HALF, DoubleBlockHalf.LOWER), flags);
      world.func_180501_a(pos.func_177984_a(), (BlockState)this.func_176223_P().func_206870_a(HALF, DoubleBlockHalf.UPPER), flags);
   }

   public void func_176208_a(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!world.field_72995_K && player.func_184812_l_()) {
         LOTRDoubleGrassBlock.accessRemoveBottomHalf(world, pos, state, player);
      }

      super.func_176208_a(world, pos, state, player);
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{HALF});
   }

   static {
      HALF = BlockStateProperties.field_208163_P;
      LOWER_SHAPE = Block.func_208617_a(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
      UPPER_SHAPE = Block.func_208617_a(7.0D, 0.0D, 7.0D, 9.0D, 9.0D, 9.0D);
   }
}
