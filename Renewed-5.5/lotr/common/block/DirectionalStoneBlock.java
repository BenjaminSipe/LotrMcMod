package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public class DirectionalStoneBlock extends LOTRStoneBlock {
   public static final DirectionProperty HORIZONTAL_FACING;

   public DirectionalStoneBlock(Properties properties) {
      super(properties);
      this.setDirectionalDefaultState();
   }

   public DirectionalStoneBlock(MaterialColor materialColor) {
      super(materialColor);
      this.setDirectionalDefaultState();
   }

   public DirectionalStoneBlock(Supplier blockSup) {
      super(blockSup);
      this.setDirectionalDefaultState();
   }

   private void setDirectionalDefaultState() {
      this.func_180632_j((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a(HORIZONTAL_FACING, Direction.NORTH));
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{HORIZONTAL_FACING});
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      return (BlockState)this.func_176223_P().func_206870_a(HORIZONTAL_FACING, context.func_195992_f().func_176734_d());
   }

   public BlockState func_185499_a(BlockState state, Rotation rot) {
      return (BlockState)state.func_206870_a(HORIZONTAL_FACING, rot.func_185831_a((Direction)state.func_177229_b(HORIZONTAL_FACING)));
   }

   public BlockState func_185471_a(BlockState state, Mirror mirror) {
      return state.func_185907_a(mirror.func_185800_a((Direction)state.func_177229_b(HORIZONTAL_FACING)));
   }

   static {
      HORIZONTAL_FACING = BlockStateProperties.field_208157_J;
   }
}
