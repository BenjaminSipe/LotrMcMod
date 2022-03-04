package lotr.common.block;

import lotr.common.init.LOTRTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class LOTRPillarBlock extends RotatedPillarBlock {
   public static final BooleanProperty ABOVE;
   public static final BooleanProperty BELOW;

   public LOTRPillarBlock(Properties properties) {
      super(properties);
      this.func_180632_j((BlockState)((BlockState)this.func_176223_P().func_206870_a(ABOVE, false)).func_206870_a(BELOW, false));
   }

   public LOTRPillarBlock(MaterialColor materialColor) {
      this(materialColor, 1.5F, 6.0F);
   }

   public LOTRPillarBlock(MaterialColor materialColor, float hard, float res) {
      this(Properties.func_200949_a(Material.field_151576_e, materialColor).func_235861_h_().func_200948_a(hard, res));
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{ABOVE});
      builder.func_206894_a(new Property[]{BELOW});
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      BlockState state = super.func_196258_a(context);
      BlockPos pos = context.func_195995_a();
      IWorld world = context.func_195991_k();
      Direction[] var5 = Direction.values();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Direction dir = var5[var7];
         BlockPos facingPos = pos.func_177972_a(dir);
         state = this.checkAdjacentPillars(state, dir, world.func_180495_p(facingPos), world, pos, facingPos);
      }

      return state;
   }

   public BlockState func_196271_a(BlockState state, Direction dir, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      return this.checkAdjacentPillars(state, dir, facingState, world, currentPos, facingPos);
   }

   private BlockState checkAdjacentPillars(BlockState state, Direction dir, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      Axis pillarAxis = (Axis)state.func_177229_b(field_176298_M);
      if (dir.func_176740_k() == pillarAxis) {
         AxisDirection axisDir = dir.func_176743_c();
         boolean matchDir = false;
         if (facingState.func_235714_a_(LOTRTags.Blocks.PILLARS)) {
            if (facingState.func_235901_b_(field_176298_M)) {
               matchDir = facingState.func_177229_b(field_176298_M) == pillarAxis;
            } else {
               matchDir = true;
            }
         }

         if (axisDir == AxisDirection.POSITIVE) {
            return (BlockState)state.func_206870_a(ABOVE, matchDir);
         }

         if (axisDir == AxisDirection.NEGATIVE) {
            return (BlockState)state.func_206870_a(BELOW, matchDir);
         }
      }

      return state;
   }

   static {
      ABOVE = LOTRBlockStates.PILLAR_ABOVE;
      BELOW = LOTRBlockStates.PILLAR_BELOW;
   }
}
