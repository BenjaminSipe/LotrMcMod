package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class StrippableBranchBlock extends BranchBlock {
   private final Supplier strippedBranchBlock;

   public StrippableBranchBlock(Block block, Supplier strippedBlock) {
      super(block);
      this.strippedBranchBlock = strippedBlock;
   }

   public StrippableBranchBlock(Supplier block, Supplier strippedBlock) {
      this((Block)block.get(), strippedBlock);
   }

   public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
      return toolType == ToolType.AXE ? (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((Block)this.strippedBranchBlock.get()).func_176223_P().func_206870_a(field_196409_a, state.func_177229_b(field_196409_a))).func_206870_a(field_196413_c, state.func_177229_b(field_196413_c))).func_206870_a(field_196414_y, state.func_177229_b(field_196414_y))).func_206870_a(field_196411_b, state.func_177229_b(field_196411_b))).func_206870_a(field_204514_u, state.func_177229_b(field_204514_u)) : super.getToolModifiedState(state, world, pos, player, stack, toolType);
   }
}
