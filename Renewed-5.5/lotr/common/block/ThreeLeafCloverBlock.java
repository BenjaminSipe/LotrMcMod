package lotr.common.block;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ThreeLeafCloverBlock extends CloverBlock implements IGrowable {
   public static final IntegerProperty NUM_CLOVERS;

   public ThreeLeafCloverBlock() {
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(NUM_CLOVERS, 1));
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{NUM_CLOVERS});
   }

   public boolean func_196253_a(BlockState state, BlockItemUseContext useContext) {
      if (useContext.func_195996_i().func_77973_b() == this.func_199767_j()) {
         int clovers = (Integer)state.func_177229_b(NUM_CLOVERS);
         if (clovers < 4) {
            BlockState oneMoreClover = (BlockState)state.func_206870_a(NUM_CLOVERS, clovers + 1);
            if (oneMoreClover.func_196955_c(useContext.func_195991_k(), useContext.func_195995_a())) {
               return true;
            }
         }
      }

      return super.func_196253_a(state, useContext);
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      BlockState state = context.func_195991_k().func_180495_p(context.func_195995_a());
      if (state.func_177230_c() == this) {
         int clovers = (Integer)state.func_177229_b(NUM_CLOVERS);
         return (BlockState)state.func_206870_a(NUM_CLOVERS, Math.min(4, clovers + 1));
      } else {
         return super.func_196258_a(context);
      }
   }

   public boolean func_176473_a(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
      int clovers = (Integer)state.func_177229_b(NUM_CLOVERS);
      return clovers < 4;
   }

   public boolean func_180670_a(World world, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void func_225535_a_(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
      int clovers = (Integer)state.func_177229_b(NUM_CLOVERS);
      clovers += MathHelper.func_76136_a(rand, 1, 3);
      clovers = Math.min(clovers, 4);
      world.func_180501_a(pos, (BlockState)state.func_206870_a(NUM_CLOVERS, clovers), 2);
   }

   static {
      NUM_CLOVERS = LOTRBlockStates.CLOVERS_1_4;
   }
}
