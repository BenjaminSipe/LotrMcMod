package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class StrippableLogStairsBlock extends LogStairsBlock {
   private final Supplier strippedStairsBlock;

   public StrippableLogStairsBlock(Block block, Supplier strippedBlock) {
      super(block);
      this.strippedStairsBlock = strippedBlock;
   }

   public StrippableLogStairsBlock(Supplier block, Supplier strippedBlock) {
      this((Block)block.get(), strippedBlock);
   }

   public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
      return toolType == ToolType.AXE ? (BlockState)((BlockState)((BlockState)((BlockState)((Block)this.strippedStairsBlock.get()).func_176223_P().func_206870_a(field_176309_a, state.func_177229_b(field_176309_a))).func_206870_a(field_176308_b, state.func_177229_b(field_176308_b))).func_206870_a(field_176310_M, state.func_177229_b(field_176310_M))).func_206870_a(field_204513_t, state.func_177229_b(field_204513_t)) : super.getToolModifiedState(state, world, pos, player, stack, toolType);
   }
}
