package lotr.common.block;

import java.util.function.Supplier;
import lotr.common.init.LOTRTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.FourWayBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BranchBlock extends FourWayBlock {
   public BranchBlock(Properties properties) {
      super(4.0F, 3.0F, 16.0F, 16.0F, 16.0F, properties);
      this.func_180632_j((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a(field_196409_a, false)).func_206870_a(field_196411_b, false)).func_206870_a(field_196413_c, false)).func_206870_a(field_196414_y, false)).func_206870_a(field_204514_u, false));
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 5, 5);
   }

   public BranchBlock(Block block) {
      this(Properties.func_200950_a(block));
   }

   public BranchBlock(Supplier block) {
      this((Block)block.get());
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{field_196409_a, field_196411_b, field_196414_y, field_196413_c, field_204514_u});
   }

   public boolean func_196266_a(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
      return false;
   }

   private boolean doesBranchConnect(BlockState state, boolean isSolidSide, Direction oppositeFace) {
      Block block = state.func_177230_c();
      boolean isOtherBranch = block.func_203417_a(LOTRTags.Blocks.BRANCHES);
      boolean isLeaves = block.func_203417_a(BlockTags.field_206952_E);
      boolean isWoodenFence = block.func_203417_a(BlockTags.field_219748_G) && block.func_203417_a(BlockTags.field_219756_j);
      boolean isParallelFenceGate = block instanceof FenceGateBlock && FenceGateBlock.func_220253_a(state, oppositeFace);
      return !func_220073_a(block) && isSolidSide || isOtherBranch || isLeaves || isWoodenFence || isParallelFenceGate;
   }

   private boolean doesBranchConnectDirectional(IWorldReader world, BlockPos pos, Direction dir) {
      BlockPos offsetPos = pos.func_177972_a(dir);
      BlockState adjacentState = world.func_180495_p(offsetPos);
      Direction oppositeFace = dir.func_176734_d();
      return this.doesBranchConnect(adjacentState, adjacentState.func_224755_d(world, offsetPos, oppositeFace), oppositeFace);
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      IWorldReader world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      FluidState fluid = world.func_204610_c(pos);
      boolean waterlogged = fluid.func_206886_c() == Fluids.field_204546_a;
      boolean connectNorth = this.doesBranchConnectDirectional(world, pos, Direction.NORTH);
      boolean connectSouth = this.doesBranchConnectDirectional(world, pos, Direction.SOUTH);
      boolean connectWest = this.doesBranchConnectDirectional(world, pos, Direction.WEST);
      boolean connectEast = this.doesBranchConnectDirectional(world, pos, Direction.EAST);
      return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.func_176223_P().func_206870_a(field_196409_a, connectNorth)).func_206870_a(field_196413_c, connectSouth)).func_206870_a(field_196414_y, connectWest)).func_206870_a(field_196411_b, connectEast)).func_206870_a(field_204514_u, waterlogged);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.func_177229_b(field_204514_u)) {
         world.func_205219_F_().func_205360_a(currentPos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(world));
      }

      if (facing != Direction.DOWN && facing != Direction.UP) {
         boolean connectNorth = this.getUpdatedConnectionOrStateProperty(facing, Direction.NORTH, facingState, world, facingPos, (Boolean)state.func_177229_b(field_196409_a));
         boolean connectEast = this.getUpdatedConnectionOrStateProperty(facing, Direction.EAST, facingState, world, facingPos, (Boolean)state.func_177229_b(field_196411_b));
         boolean connectSouth = this.getUpdatedConnectionOrStateProperty(facing, Direction.SOUTH, facingState, world, facingPos, (Boolean)state.func_177229_b(field_196413_c));
         boolean connectWest = this.getUpdatedConnectionOrStateProperty(facing, Direction.WEST, facingState, world, facingPos, (Boolean)state.func_177229_b(field_196414_y));
         return (BlockState)((BlockState)((BlockState)((BlockState)state.func_206870_a(field_196409_a, connectNorth)).func_206870_a(field_196411_b, connectEast)).func_206870_a(field_196413_c, connectSouth)).func_206870_a(field_196414_y, connectWest);
      } else {
         return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
      }
   }

   private boolean getUpdatedConnectionOrStateProperty(Direction facing, Direction relevantDirection, BlockState facingState, IWorld world, BlockPos facingPos, boolean stateProperty) {
      Direction oppositeFace = facing.func_176734_d();
      return facing == relevantDirection ? this.doesBranchConnect(facingState, facingState.func_224755_d(world, facingPos, oppositeFace), oppositeFace) : stateProperty;
   }
}
