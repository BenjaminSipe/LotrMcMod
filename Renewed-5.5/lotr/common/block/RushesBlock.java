package lotr.common.block;

import java.util.Random;
import lotr.common.event.CompostingHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RushesBlock extends WaterloggableDoublePlantBlock implements IGrowable {
   private static final VoxelShape SHAPE = Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

   public RushesBlock() {
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 100);
      CompostingHelper.prepareCompostable(this, 0.5F);
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return SHAPE;
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      if (state.func_177229_b(field_176492_b) != DoubleBlockHalf.UPPER || !(Boolean)state.func_177229_b(WATERLOGGED) && world.func_204610_c(pos.func_177977_b()).func_206886_c() == Fluids.field_204546_a) {
         return state.func_177229_b(field_176492_b) != DoubleBlockHalf.LOWER || world.func_204610_c(pos).func_206886_c() == Fluids.field_204546_a && world.func_204610_c(pos.func_177984_a()).func_206886_c() != Fluids.field_204546_a ? super.func_196260_a(state, world, pos) : false;
      } else {
         return false;
      }
   }

   protected boolean func_200014_a_(BlockState state, IBlockReader world, BlockPos pos) {
      return super.func_200014_a_(state, world, pos) || state.func_177230_c().func_203417_a(BlockTags.field_203436_u) || state.func_177230_c() instanceof QuagmireBlock;
   }

   public boolean func_176473_a(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
      return true;
   }

   public boolean func_180670_a(World world, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void func_225535_a_(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
      int xzRange = 2;
      int yRange = 1;
      Mutable movingPos = new Mutable();
      int tries = 10;

      for(int l = 0; l < tries; ++l) {
         int x = MathHelper.func_76136_a(rand, -xzRange, xzRange);
         int y = MathHelper.func_76136_a(rand, -yRange, yRange);
         int z = MathHelper.func_76136_a(rand, -xzRange, xzRange);
         movingPos.func_189533_g(pos).func_196234_d(x, y, z);
         BlockState placeState = (BlockState)this.func_176223_P().func_206870_a(field_176492_b, DoubleBlockHalf.LOWER);
         if (placeState.func_196955_c(world, movingPos) && world.func_175623_d(movingPos.func_177984_a())) {
            this.func_196390_a(world, movingPos, 3);
         }
      }

   }
}
