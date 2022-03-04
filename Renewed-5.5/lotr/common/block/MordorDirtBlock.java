package lotr.common.block;

import java.util.Random;
import lotr.common.init.LOTRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

public class MordorDirtBlock extends LOTRDirtBlock implements IGrowable {
   public MordorDirtBlock(MaterialColor materialColor) {
      super(materialColor);
   }

   public boolean func_176473_a(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
      BlockPos abovePos = pos.func_177984_a();
      return world.func_180495_p(abovePos).isAir(world, abovePos);
   }

   public boolean func_180670_a(World world, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void func_225535_a_(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
      MordorRockBlock.growMordorPlants(world, rand, pos, state);
   }

   public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
      return toolType == ToolType.SHOVEL ? ((Block)LOTRBlocks.MORDOR_DIRT_PATH.get()).func_176223_P() : super.getToolModifiedState(state, world, pos, player, stack, toolType);
   }
}
