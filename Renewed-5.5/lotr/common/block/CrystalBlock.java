package lotr.common.block;

import java.util.Random;
import lotr.common.init.LOTRBlocks;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class CrystalBlock extends Block implements IWaterLoggable, IBeaconBeamColorProvider {
   public static final EnumProperty CRYSTAL_FACING;
   public static final BooleanProperty WATERLOGGED;
   private static final VoxelShape SHAPE_UP;
   private static final VoxelShape SHAPE_DOWN;
   private static final VoxelShape SHAPE_WEST;
   private static final VoxelShape SHAPE_EAST;
   private static final VoxelShape SHAPE_NORTH;
   private static final VoxelShape SHAPE_SOUTH;
   private final DyeColor beaconBeamColor;

   public CrystalBlock(Properties properties, DyeColor color) {
      super(properties);
      this.func_180632_j((BlockState)((BlockState)this.func_176223_P().func_206870_a(CRYSTAL_FACING, Direction.UP)).func_206870_a(WATERLOGGED, false));
      this.beaconBeamColor = color;
   }

   public CrystalBlock(int light, int harvestLvl, DyeColor color) {
      this(Properties.func_200945_a(LOTRBlockMaterial.CRYSTAL).func_235861_h_().func_200948_a(3.0F, 3.0F).func_226896_b_().func_235838_a_(LOTRBlocks.constantLight(light)).func_200947_a(SoundType.field_185853_f).harvestTool(ToolType.PICKAXE).harvestLevel(harvestLvl), color);
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{CRYSTAL_FACING, WATERLOGGED});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      Direction crystalFacing = (Direction)state.func_177229_b(CRYSTAL_FACING);
      switch(crystalFacing) {
      case UP:
      default:
         return SHAPE_UP;
      case DOWN:
         return SHAPE_DOWN;
      case WEST:
         return SHAPE_WEST;
      case EAST:
         return SHAPE_EAST;
      case NORTH:
         return SHAPE_NORTH;
      case SOUTH:
         return SHAPE_SOUTH;
      }
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      FluidState fluid = context.func_195991_k().func_204610_c(context.func_195995_a());
      return (BlockState)((BlockState)this.func_176223_P().func_206870_a(CRYSTAL_FACING, context.func_196000_l())).func_206870_a(WATERLOGGED, fluid.func_206886_c() == Fluids.field_204546_a);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.func_177229_b(WATERLOGGED)) {
         world.func_205219_F_().func_205360_a(currentPos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(world));
      }

      Direction crystalFacing = (Direction)state.func_177229_b(CRYSTAL_FACING);
      return facing == crystalFacing.func_176734_d() && !this.func_196260_a(state, world, currentPos) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      Direction crystalFacing = (Direction)state.func_177229_b(CRYSTAL_FACING);
      BlockPos supportPos = pos.func_177972_a(crystalFacing.func_176734_d());
      return LOTRUtil.hasSolidSide(world, supportPos, crystalFacing);
   }

   protected int getExperience(Random rand) {
      if (this == LOTRBlocks.GLOWSTONE_CRYSTAL.get()) {
         return MathHelper.func_76136_a(rand, 2, 4);
      } else if (this == LOTRBlocks.EDHELVIR_CRYSTAL.get()) {
         return MathHelper.func_76136_a(rand, 2, 5);
      } else {
         return this == LOTRBlocks.GULDURIL_CRYSTAL.get() ? MathHelper.func_76136_a(rand, 2, 5) : 0;
      }
   }

   public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
      return silktouch == 0 ? this.getExperience(this.RANDOM) : 0;
   }

   public BlockState func_185499_a(BlockState state, Rotation rot) {
      return (BlockState)state.func_206870_a(CRYSTAL_FACING, rot.func_185831_a((Direction)state.func_177229_b(CRYSTAL_FACING)));
   }

   public BlockState func_185471_a(BlockState state, Mirror mirror) {
      return state.func_185907_a(mirror.func_185800_a((Direction)state.func_177229_b(CRYSTAL_FACING)));
   }

   public FluidState func_204507_t(BlockState state) {
      return (Boolean)state.func_177229_b(WATERLOGGED) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
   }

   public DyeColor func_196457_d() {
      return this.beaconBeamColor;
   }

   static {
      CRYSTAL_FACING = BlockStateProperties.field_208155_H;
      WATERLOGGED = BlockStateProperties.field_208198_y;
      SHAPE_UP = Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);
      SHAPE_DOWN = Block.func_208617_a(2.0D, 1.0D, 2.0D, 14.0D, 16.0D, 14.0D);
      SHAPE_WEST = Block.func_208617_a(1.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D);
      SHAPE_EAST = Block.func_208617_a(0.0D, 2.0D, 2.0D, 15.0D, 14.0D, 14.0D);
      SHAPE_NORTH = Block.func_208617_a(2.0D, 2.0D, 1.0D, 14.0D, 14.0D, 16.0D);
      SHAPE_SOUTH = Block.func_208617_a(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 15.0D);
   }
}
