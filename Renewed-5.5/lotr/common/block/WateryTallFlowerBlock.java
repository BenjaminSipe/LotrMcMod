package lotr.common.block;

import java.util.Random;
import lotr.common.event.CompostingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WateryTallFlowerBlock extends WaterloggableDoublePlantBlock implements IGrowable {
   public WateryTallFlowerBlock() {
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 100);
      CompostingHelper.prepareCompostable(this, 0.65F);
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      if (state.func_177229_b(field_176492_b) == DoubleBlockHalf.UPPER && (Boolean)state.func_177229_b(WATERLOGGED)) {
         return false;
      } else {
         return state.func_177229_b(field_176492_b) == DoubleBlockHalf.LOWER && world.func_204610_c(pos.func_177984_a()).func_206886_c() == Fluids.field_204546_a ? false : super.func_196260_a(state, world, pos);
      }
   }

   public boolean func_196253_a(BlockState state, BlockItemUseContext useContext) {
      return false;
   }

   public boolean func_176473_a(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
      return true;
   }

   public boolean func_180670_a(World world, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void func_225535_a_(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
      func_180635_a(world, pos, new ItemStack(this));
   }
}
