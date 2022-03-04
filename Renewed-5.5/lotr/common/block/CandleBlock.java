package lotr.common.block;

import java.util.Random;
import java.util.function.Supplier;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CandleBlock extends LOTRTorchBlock {
   public static final IntegerProperty NUM_CANDLES;
   private static final VoxelShape ONE_CANDLE_SHAPE;
   private static final VoxelShape MULTI_CANDLE_SHAPE;

   public CandleBlock(int lightBase, int lightStep) {
      super((state) -> {
         int candles = (Integer)state.func_177229_b(NUM_CANDLES);
         return lightBase + (candles - 1) * lightStep;
      }, SoundType.field_185848_a);
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(NUM_CANDLES, 1));
      this.setParticles(new Supplier[]{() -> {
         return ParticleTypes.field_197631_x;
      }});
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{NUM_CANDLES});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      int candles = (Integer)state.func_177229_b(NUM_CANDLES);
      return candles > 1 ? MULTI_CANDLE_SHAPE : ONE_CANDLE_SHAPE;
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      int candles = (Integer)state.func_177229_b(NUM_CANDLES);
      return candles > 1 ? LOTRUtil.hasSolidSide(world, pos.func_177977_b(), Direction.UP) : super.func_196260_a(state, world, pos);
   }

   public boolean func_196253_a(BlockState state, BlockItemUseContext useContext) {
      if (useContext.func_195996_i().func_77973_b() == this.func_199767_j()) {
         int candles = (Integer)state.func_177229_b(NUM_CANDLES);
         if (candles < 4) {
            BlockState oneMoreCandle = (BlockState)state.func_206870_a(NUM_CANDLES, candles + 1);
            if (oneMoreCandle.func_196955_c(useContext.func_195991_k(), useContext.func_195995_a())) {
               return true;
            }
         }
      }

      return super.func_196253_a(state, useContext);
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      BlockState state = context.func_195991_k().func_180495_p(context.func_195995_a());
      if (state.func_177230_c() == this) {
         int candles = (Integer)state.func_177229_b(NUM_CANDLES);
         return (BlockState)state.func_206870_a(NUM_CANDLES, Math.min(4, candles + 1));
      } else {
         return super.func_196258_a(context);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      int candles = (Integer)state.func_177229_b(NUM_CANDLES);
      if (candles == 1) {
         this.animateTorch(world, pos, rand, 0.5D, 0.7D, 0.5D);
      } else if (candles == 2) {
         this.animateTorch(world, pos, rand, 0.3125D, 0.575D, 0.3125D);
         this.animateTorch(world, pos, rand, 0.6875D, 0.7D, 0.6875D);
      } else if (candles == 3) {
         this.animateTorch(world, pos, rand, 0.4375D, 0.575D, 0.25D);
         this.animateTorch(world, pos, rand, 0.25D, 0.575D, 0.625D);
         this.animateTorch(world, pos, rand, 0.75D, 0.7D, 0.6875D);
      } else if (candles == 4) {
         this.animateTorch(world, pos, rand, 0.3125D, 0.6375D, 0.3125D);
         this.animateTorch(world, pos, rand, 0.75D, 0.575D, 0.25D);
         this.animateTorch(world, pos, rand, 0.25D, 0.575D, 0.75D);
         this.animateTorch(world, pos, rand, 0.75D, 0.7D, 0.6875D);
      }

   }

   static {
      NUM_CANDLES = LOTRBlockStates.CANDLES_1_4;
      ONE_CANDLE_SHAPE = Block.func_208617_a(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);
      MULTI_CANDLE_SHAPE = Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
   }
}
