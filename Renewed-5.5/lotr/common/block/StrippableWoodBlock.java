package lotr.common.block;

import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class StrippableWoodBlock extends LOTRWoodBlock {
   private final Supplier strippedWoodBlock;

   public StrippableWoodBlock(MaterialColor bark, Supplier strippedBlock) {
      super(bark);
      this.strippedWoodBlock = strippedBlock;
   }

   public StrippableWoodBlock(Supplier logBlock, Supplier strippedBlock) {
      this(((LOTRLogBlock)logBlock.get()).barkColor, strippedBlock);
   }

   public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
      return toolType == ToolType.AXE ? (BlockState)((Block)this.strippedWoodBlock.get()).func_176223_P().func_206870_a(field_176298_M, state.func_177229_b(field_176298_M)) : super.getToolModifiedState(state, world, pos, player, stack, toolType);
   }
}
