package lotr.common.block;

import com.google.common.collect.ImmutableMap;
import java.util.EnumMap;
import java.util.Map;
import lotr.common.init.LOTRTileEntities;
import lotr.common.tileentity.CustomWaypointMarkerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class CustomWaypointMarkerBlock extends Block {
   public static final DirectionProperty FACING;
   private static final Map SHAPES;

   public CustomWaypointMarkerBlock() {
      super(Properties.func_200945_a(Material.field_151575_d).func_200942_a().func_200948_a(-1.0F, 3600000.0F).func_222380_e().func_200947_a(SoundType.field_185848_a));
      this.func_180632_j((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a(FACING, Direction.NORTH));
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{FACING});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return (VoxelShape)SHAPES.get(state.func_177229_b(FACING));
   }

   public boolean hasTileEntity(BlockState state) {
      return true;
   }

   public TileEntity createTileEntity(BlockState state, IBlockReader world) {
      return ((TileEntityType)LOTRTileEntities.CUSTOM_WAYPOINT_MARKER.get()).func_200968_a();
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      Direction facing = (Direction)state.func_177229_b(FACING);
      BlockPos attachedPos = pos.func_177972_a(facing.func_176734_d());
      return world.func_180495_p(attachedPos).func_224755_d(world, attachedPos, facing);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      return facing.func_176734_d() == state.func_177229_b(FACING) && !state.func_196955_c(world, currentPos) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   public void func_196243_a(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.func_177230_c() != newState.func_177230_c()) {
         TileEntity te = world.func_175625_s(pos);
         if (te instanceof CustomWaypointMarkerTileEntity) {
            CustomWaypointMarkerTileEntity marker = (CustomWaypointMarkerTileEntity)te;
            marker.recreateAndDropItemFrame(state);
         }

         super.func_196243_a(state, world, pos, newState, isMoving);
      }

   }

   public BlockState func_185499_a(BlockState state, Rotation rot) {
      return (BlockState)state.func_206870_a(FACING, rot.func_185831_a((Direction)state.func_177229_b(FACING)));
   }

   public BlockState func_185471_a(BlockState state, Mirror mirror) {
      return state.func_185907_a(mirror.func_185800_a((Direction)state.func_177229_b(FACING)));
   }

   static {
      FACING = BlockStateProperties.field_208157_J;
      SHAPES = new EnumMap(ImmutableMap.of(Direction.NORTH, Block.func_208617_a(0.0D, 1.5D, 14.5D, 16.0D, 14.5D, 16.0D), Direction.SOUTH, Block.func_208617_a(0.0D, 1.5D, 0.0D, 16.0D, 14.5D, 1.5D), Direction.EAST, Block.func_208617_a(0.0D, 1.5D, 0.0D, 1.5D, 14.5D, 16.0D), Direction.WEST, Block.func_208617_a(14.5D, 1.5D, 0.0D, 16.0D, 14.5D, 16.0D)));
   }
}
