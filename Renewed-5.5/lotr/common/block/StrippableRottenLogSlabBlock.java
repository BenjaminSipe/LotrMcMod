package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class StrippableRottenLogSlabBlock extends RottenLogSlabBlock {
   private final Supplier strippedSlabBlock;

   public StrippableRottenLogSlabBlock(Supplier block, Supplier strippedBlock) {
      super(block);
      this.strippedSlabBlock = strippedBlock;
   }

   public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
      return toolType == ToolType.AXE ? (BlockState)((BlockState)((BlockState)((Block)this.strippedSlabBlock.get()).func_176223_P().func_206870_a(field_196505_a, state.func_177229_b(field_196505_a))).func_206870_a(this.getSlabAxisProperty(), state.func_177229_b(this.getSlabAxisProperty()))).func_206870_a(field_204512_b, state.func_177229_b(field_204512_b)) : super.getToolModifiedState(state, world, pos, player, stack, toolType);
   }
}
