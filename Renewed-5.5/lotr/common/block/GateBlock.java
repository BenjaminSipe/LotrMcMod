package lotr.common.block;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lotr.common.init.LOTRSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GateBlock extends Block implements IWaterLoggable {
   public static final DirectionProperty FACING;
   public static final BooleanProperty OPEN;
   public static final BooleanProperty POWERED;
   public static final BooleanProperty WATERLOGGED;
   public static final BooleanProperty UP;
   public static final BooleanProperty DOWN;
   public static final BooleanProperty NORTH;
   public static final BooleanProperty SOUTH;
   public static final BooleanProperty WEST;
   public static final BooleanProperty EAST;
   private static final Map CONNECTED_DIRECTION_TO_PROPERTY_MAP;
   protected static final int MAX_GATE_RANGE = 16;
   private static final VoxelShape TOP_SHAPE;
   private static final VoxelShape BOTTOM_SHAPE;
   private static final VoxelShape WEST_EAST_SHAPE;
   private static final VoxelShape NORTH_SOUTH_SHAPE;
   public final boolean fullBlockGate = false;
   private final boolean isCutoutGate;

   public GateBlock(Properties properties, boolean cutout) {
      super(properties);
      this.func_180632_j((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a(FACING, Direction.NORTH)).func_206870_a(OPEN, false)).func_206870_a(POWERED, false)).func_206870_a(WATERLOGGED, false)).func_206870_a(UP, false)).func_206870_a(DOWN, false)).func_206870_a(NORTH, false)).func_206870_a(SOUTH, false)).func_206870_a(WEST, false)).func_206870_a(EAST, false));
      this.isCutoutGate = cutout;
   }

   public static GateBlock makeWooden() {
      return makeWooden(false);
   }

   public static GateBlock makeWoodenCutout() {
      return makeWooden(true);
   }

   private static GateBlock makeWooden(boolean cutout) {
      return new GateBlock(Properties.func_200945_a(Material.field_151575_d).func_200948_a(4.0F, 3.0F).func_200947_a(SoundType.field_185848_a).func_226896_b_(), cutout);
   }

   public static GateBlock makeStone() {
      return new GateBlock(Properties.func_200945_a(Material.field_151576_e).func_235861_h_().func_200948_a(4.0F, 6.0F).func_200947_a(SoundType.field_185851_d).func_226896_b_(), false);
   }

   public static GateBlock makeMetal() {
      return makeMetal(false);
   }

   public static GateBlock makeMetalCutout() {
      return makeMetal(true);
   }

   private static GateBlock makeMetal(boolean cutout) {
      return new GateBlock(Properties.func_200945_a(Material.field_151573_f).func_235861_h_().func_200948_a(4.0F, 6.0F).func_200947_a(SoundType.field_185852_e).func_226896_b_(), cutout);
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{FACING, OPEN, POWERED, WATERLOGGED, UP, DOWN, NORTH, SOUTH, WEST, EAST});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      if (state.func_177229_b(FACING) == Direction.UP) {
         return TOP_SHAPE;
      } else if (state.func_177229_b(FACING) == Direction.DOWN) {
         return BOTTOM_SHAPE;
      } else {
         return ((Direction)state.func_177229_b(FACING)).func_176740_k() == Axis.X ? WEST_EAST_SHAPE : NORTH_SOUTH_SHAPE;
      }
   }

   public VoxelShape func_220071_b(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return (Boolean)state.func_177229_b(OPEN) ? VoxelShapes.func_197880_a() : super.func_220071_b(state, world, pos, context);
   }

   public boolean func_196266_a(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
      return type != PathType.LAND && type != PathType.AIR ? super.func_196266_a(state, world, pos, type) : (Boolean)state.func_177229_b(OPEN);
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      boolean powered = context.func_195991_k().func_175640_z(context.func_195995_a());
      FluidState fluid = context.func_195991_k().func_204610_c(context.func_195995_a());
      return (BlockState)((BlockState)((BlockState)this.func_176223_P().func_206870_a(FACING, this.getDirectionForPlacement(context))).func_206870_a(POWERED, powered)).func_206870_a(WATERLOGGED, fluid.func_206886_c() == Fluids.field_204546_a);
   }

   private Direction getDirectionForPlacement(BlockItemUseContext context) {
      World world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      Direction clickedSide = context.func_196000_l();
      BlockPos clickedOnPos = context.func_196012_c() ? pos : pos.func_177972_a(clickedSide.func_176734_d());
      BlockState clickedOnState = world.func_180495_p(clickedOnPos);
      if (clickedOnState.func_177230_c() instanceof GateBlock && ((Direction)clickedOnState.func_177229_b(FACING)).func_176740_k() != clickedSide.func_176740_k()) {
         return (Direction)clickedOnState.func_177229_b(FACING);
      } else {
         Direction horizontalFacing = context.func_195992_f();
         float horizontalAngle = 40.0F;
         float pitch = (Float)Optional.ofNullable(context.func_195999_j()).map((p) -> {
            return p.field_70125_A;
         }).orElse(0.0F);
         boolean lookingUp = pitch < -40.0F;
         boolean lookingDown = pitch > 40.0F;
         boolean sneak = context.func_225518_g_();
         if (clickedSide.func_176740_k().func_200128_b()) {
            return horizontalFacing;
         } else if (!lookingUp && !lookingDown) {
            return clickedSide.func_176735_f();
         } else {
            return context.func_221532_j().field_72448_b - (double)pos.func_177956_o() < 0.5D ? Direction.DOWN : Direction.UP;
         }
      }
   }

   protected boolean directionsMatch(Direction facing1, Direction facing2) {
      if (facing1.func_176740_k() != Axis.Y && facing2.func_176740_k() != Axis.Y) {
         return facing1.func_176740_k() == facing2.func_176740_k();
      } else {
         return facing1 == facing2;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_200122_a(BlockState state, BlockState adjacentState, Direction side) {
      Block block = adjacentState.func_177230_c();
      if (block instanceof GateBlock) {
         GateBlock otherGateBlock = (GateBlock)block;
         if (this.isCutoutGate == otherGateBlock.isCutoutGate) {
            Direction thisFacing = (Direction)state.func_177229_b(FACING);
            Direction otherFacing = (Direction)adjacentState.func_177229_b(FACING);
            boolean thisOpen = (Boolean)state.func_177229_b(OPEN);
            boolean otherOpen = (Boolean)adjacentState.func_177229_b(OPEN);
            boolean connectToSide = !this.directionsMatch(thisFacing, side);
            if (connectToSide) {
               return thisOpen == otherOpen && this.directionsMatch(thisFacing, otherFacing) && otherGateBlock.directionsMatch(thisFacing, otherFacing);
            }
         }
      }

      return super.func_200122_a(state, adjacentState, side);
   }

   public static boolean doBlocksConnectVisually(BlockState state, BlockState otherState, List connectOffsets) {
      Block block = state.func_177230_c();
      Block otherBlock = otherState.func_177230_c();
      if (!(block instanceof GateBlock)) {
         return true;
      } else {
         GateBlock gateBlock = (GateBlock)block;
         Direction gateDir = (Direction)state.func_177229_b(FACING);
         boolean open = (Boolean)state.func_177229_b(OPEN);
         if (connectOffsets.stream().anyMatch((dir) -> {
            return dir.func_176740_k() == gateDir.func_176740_k();
         })) {
            return false;
         } else if (!(otherBlock instanceof GateBlock)) {
            return gateDir.func_176740_k().func_176722_c() && open && connectOffsets.contains(Direction.DOWN);
         } else {
            GateBlock otherGateBlock = (GateBlock)otherBlock;
            Direction otherGateDir = (Direction)otherState.func_177229_b(FACING);
            boolean otherOpen = (Boolean)otherState.func_177229_b(OPEN);
            boolean connectToOtherGate = open || otherGateBlock == gateBlock;
            return connectToOtherGate && open == otherOpen && gateBlock.directionsMatch(gateDir, otherGateDir) && otherGateBlock.directionsMatch(gateDir, otherGateDir);
         }
      }
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.func_177229_b(WATERLOGGED)) {
         world.func_205219_F_().func_205360_a(currentPos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(world));
      }

      state = (BlockState)state.func_206870_a((Property)CONNECTED_DIRECTION_TO_PROPERTY_MAP.get(facing), doBlocksConnectVisually(state, facingState, Collections.singletonList(facing)));
      return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   private static boolean isGateOpen(IBlockReader world, BlockPos pos) {
      return (Boolean)world.func_180495_p(pos).func_177229_b(OPEN);
   }

   private static void setGateOpen(IWorld world, BlockPos pos, boolean open) {
      BlockState state = (BlockState)world.func_180495_p(pos).func_206870_a(OPEN, open);
      state = Block.func_199770_b(state, world, pos);
      world.func_180501_a(pos, state, 3);
   }

   private static Direction getGateFacing(IBlockReader world, BlockPos pos) {
      return (Direction)world.func_180495_p(pos).func_177229_b(FACING);
   }

   public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult target) {
      ItemStack heldItem = player.func_184586_b(hand);
      if (!heldItem.func_190926_b()) {
         Item item = heldItem.func_77973_b();
         if (Block.func_149634_a(item) instanceof GateBlock) {
            return ActionResultType.PASS;
         }
      }

      if (!world.field_72995_K) {
         this.activateGate(world, pos);
      }

      return ActionResultType.SUCCESS;
   }

   private void activateGate(World world, BlockPos pos) {
      boolean wasOpen = isGateOpen(world, pos);
      this.activateGate(world, pos, !wasOpen);
   }

   private void activateGate(World world, BlockPos pos, boolean open) {
      this.getConnectedGatePositions(world, pos).forEach((p) -> {
         setGateOpen(world, p, open);
      });
      world.func_184133_a((PlayerEntity)null, pos, this.getGateSound(open), SoundCategory.BLOCKS, 1.0F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
   }

   private SoundEvent getGateSound(boolean open) {
      if (this.field_149764_J == Material.field_151576_e) {
         return open ? LOTRSoundEvents.STONE_GATE_OPEN : LOTRSoundEvents.STONE_GATE_CLOSE;
      } else {
         return open ? LOTRSoundEvents.GATE_OPEN : LOTRSoundEvents.GATE_CLOSE;
      }
   }

   private List getConnectedGatePositions(World world, BlockPos pos) {
      boolean open = isGateOpen(world, pos);
      Direction facing = getGateFacing(world, pos);
      Set allCoords = new HashSet();
      Set lastDepthCoords = new HashSet();
      Set currentDepthCoords = new HashSet();

      for(int depth = 0; depth <= 16; ++depth) {
         if (depth == 0) {
            allCoords.add(pos);
            currentDepthCoords.add(pos);
         } else {
            Iterator var9 = lastDepthCoords.iterator();

            while(var9.hasNext()) {
               BlockPos coords = (BlockPos)var9.next();
               this.gatherAdjacentGates(world, coords, facing, open, allCoords, currentDepthCoords);
            }
         }

         lastDepthCoords.clear();
         lastDepthCoords.addAll(currentDepthCoords);
         currentDepthCoords.clear();
      }

      return new ArrayList(allCoords);
   }

   private void gatherAdjacentGates(World world, BlockPos pos, Direction originalFacing, boolean originalOpen, Set allCoords, Set currentDepthCoords) {
      Direction[] var7 = Direction.values();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Direction dir = var7[var9];
         if (dir.func_176740_k() != originalFacing.func_176740_k()) {
            this.gatherAdjacentGate(world, pos.func_177972_a(dir), originalFacing, originalOpen, allCoords, currentDepthCoords);
         }
      }

   }

   private void gatherAdjacentGate(World world, BlockPos pos, Direction originalFacing, boolean originalOpen, Set allCoords, Set currentDepthCoords) {
      if (!allCoords.contains(pos)) {
         BlockState stateHere = world.func_180495_p(pos);
         Block blockHere = stateHere.func_177230_c();
         if (blockHere instanceof GateBlock) {
            GateBlock gateBlockHere = (GateBlock)blockHere;
            boolean openHere = isGateOpen(world, pos);
            Direction facingHere = getGateFacing(world, pos);
            if (openHere == originalOpen && this.directionsMatch(facingHere, originalFacing) && gateBlockHere.directionsMatch(facingHere, originalFacing)) {
               allCoords.add(pos);
               currentDepthCoords.add(pos);
            }
         }

      }
   }

   public void func_220069_a(BlockState state, World world, BlockPos pos, Block changedBlock, BlockPos changedPos, boolean isMoving) {
      if (!world.field_72995_K && !(changedBlock instanceof GateBlock)) {
         boolean powered = world.func_175640_z(pos);
         if ((Boolean)state.func_177229_b(POWERED) != powered) {
            world.func_175656_a(pos, (BlockState)state.func_206870_a(POWERED, powered));
            if (isGateOpen(world, pos) != powered) {
               this.activateGate(world, pos, powered);
            }
         }
      }

   }

   public BlockState func_185499_a(BlockState state, Rotation rot) {
      return (BlockState)state.func_206870_a(FACING, rot.func_185831_a((Direction)state.func_177229_b(FACING)));
   }

   public BlockState func_185471_a(BlockState state, Mirror mirror) {
      return state.func_185907_a(mirror.func_185800_a((Direction)state.func_177229_b(FACING)));
   }

   public FluidState func_204507_t(BlockState state) {
      return (Boolean)state.func_177229_b(WATERLOGGED) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
   }

   static {
      FACING = BlockStateProperties.field_208155_H;
      OPEN = LOTRBlockStates.GATE_OPEN;
      POWERED = BlockStateProperties.field_208194_u;
      WATERLOGGED = BlockStateProperties.field_208198_y;
      UP = BlockStateProperties.field_208149_B;
      DOWN = BlockStateProperties.field_208150_C;
      NORTH = BlockStateProperties.field_208151_D;
      SOUTH = BlockStateProperties.field_208153_F;
      WEST = BlockStateProperties.field_208154_G;
      EAST = BlockStateProperties.field_208152_E;
      CONNECTED_DIRECTION_TO_PROPERTY_MAP = (Map)Util.func_200696_a(Maps.newEnumMap(Direction.class), (map) -> {
         map.put(Direction.UP, UP);
         map.put(Direction.DOWN, DOWN);
         map.put(Direction.NORTH, NORTH);
         map.put(Direction.SOUTH, SOUTH);
         map.put(Direction.WEST, WEST);
         map.put(Direction.EAST, EAST);
      });
      TOP_SHAPE = Block.func_208617_a(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D);
      BOTTOM_SHAPE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
      WEST_EAST_SHAPE = Block.func_208617_a(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
      NORTH_SOUTH_SHAPE = Block.func_208617_a(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
   }
}
