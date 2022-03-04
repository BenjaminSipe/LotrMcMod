package lotr.common.block;

import java.util.Iterator;
import java.util.Random;
import lotr.common.event.CompostingHelper;
import lotr.common.init.LOTRTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Direction.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

public class ReedsBlock extends Block implements IWaterLoggable, IGrowable {
   public static final EnumProperty REEDS_TYPE;
   public static final IntegerProperty AGE;
   private static final int MAX_AGE = 15;
   public static final BooleanProperty WATERLOGGED;
   private static final VoxelShape SHAPE;
   private final boolean canReedGrow;
   private final boolean canPlaceByIce;

   protected ReedsBlock(Properties properties, boolean canGrow) {
      super(properties);
      this.func_180632_j((BlockState)((BlockState)((BlockState)this.func_176223_P().func_206870_a(REEDS_TYPE, ReedsBlock.Type.ONE)).func_206870_a(AGE, 0)).func_206870_a(WATERLOGGED, false));
      this.canReedGrow = canGrow;
      this.canPlaceByIce = !canGrow;
      ((FireBlock)Blocks.field_150480_ab).func_180686_a(this, 60, 100);
      CompostingHelper.prepareCompostable(this, 0.5F);
   }

   protected ReedsBlock(boolean canGrow) {
      this(Properties.func_200945_a(Material.field_151585_k).func_200942_a().func_200944_c().func_200943_b(0.0F).func_200947_a(SoundType.field_185850_c), canGrow);
   }

   public ReedsBlock() {
      this(true);
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{REEDS_TYPE, AGE, WATERLOGGED});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return SHAPE;
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      BlockState placeState = this.func_176223_P();
      World world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      BlockState belowState = world.func_180495_p(pos.func_177977_b());
      if (belowState.func_177230_c() == this) {
         ReedsBlock.Type reedType = (ReedsBlock.Type)belowState.func_177229_b(REEDS_TYPE);
         if (reedType == ReedsBlock.Type.ONE) {
            placeState = (BlockState)placeState.func_206870_a(REEDS_TYPE, ReedsBlock.Type.TWO_TOP);
         } else if (reedType == ReedsBlock.Type.TWO_TOP) {
            placeState = (BlockState)placeState.func_206870_a(REEDS_TYPE, ReedsBlock.Type.THREE_TOP);
         }
      }

      FluidState fluid = context.func_195991_k().func_204610_c(context.func_195995_a());
      return (BlockState)placeState.func_206870_a(WATERLOGGED, fluid.func_206886_c() == Fluids.field_204546_a);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if (!state.func_196955_c(world, currentPos)) {
         world.func_205220_G_().func_205360_a(currentPos, this, 1);
      } else if (facing == Direction.UP) {
         ReedsBlock.Type thisReedType = (ReedsBlock.Type)state.func_177229_b(REEDS_TYPE);
         if (facingState.func_177230_c() == this) {
            ReedsBlock.Type aboveReedType = (ReedsBlock.Type)facingState.func_177229_b(REEDS_TYPE);
            if (thisReedType == ReedsBlock.Type.ONE && aboveReedType == ReedsBlock.Type.TWO_TOP) {
               return (BlockState)state.func_206870_a(REEDS_TYPE, ReedsBlock.Type.TWO_BOTTOM);
            }

            if (thisReedType == ReedsBlock.Type.TWO_TOP && aboveReedType == ReedsBlock.Type.THREE_TOP) {
               return (BlockState)state.func_206870_a(REEDS_TYPE, ReedsBlock.Type.THREE_MIDDLE);
            }

            if (thisReedType == ReedsBlock.Type.TWO_BOTTOM && aboveReedType == ReedsBlock.Type.THREE_MIDDLE) {
               return (BlockState)state.func_206870_a(REEDS_TYPE, ReedsBlock.Type.THREE_BOTTOM);
            }

            if (thisReedType == ReedsBlock.Type.THREE_BOTTOM && aboveReedType == ReedsBlock.Type.TWO_TOP) {
               return (BlockState)state.func_206870_a(REEDS_TYPE, ReedsBlock.Type.TWO_BOTTOM);
            }
         } else {
            if (thisReedType == ReedsBlock.Type.TWO_BOTTOM || thisReedType == ReedsBlock.Type.THREE_BOTTOM) {
               return (BlockState)state.func_206870_a(REEDS_TYPE, ReedsBlock.Type.ONE);
            }

            if (thisReedType == ReedsBlock.Type.THREE_MIDDLE) {
               return (BlockState)state.func_206870_a(REEDS_TYPE, ReedsBlock.Type.TWO_TOP);
            }
         }
      }

      if ((Boolean)state.func_177229_b(WATERLOGGED)) {
         world.func_205219_F_().func_205360_a(currentPos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(world));
      }

      return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      ReedsBlock.Type reedType = (ReedsBlock.Type)state.func_177229_b(REEDS_TYPE);
      BlockPos belowPos = pos.func_177977_b();
      BlockState belowState = world.func_180495_p(belowPos);
      if (reedType != ReedsBlock.Type.ONE && reedType != ReedsBlock.Type.TWO_BOTTOM && reedType != ReedsBlock.Type.THREE_BOTTOM) {
         if (reedType == ReedsBlock.Type.THREE_TOP && (Boolean)state.func_177229_b(WATERLOGGED)) {
            return false;
         } else {
            return belowState.func_177230_c() == this;
         }
      } else {
         if (belowState.func_235714_a_(LOTRTags.Blocks.REEDS_PLACEABLE_ON)) {
            if (world.func_204610_c(pos).func_206886_c() == Fluids.field_204546_a) {
               boolean canPotentiallyReachAir = false;
               if (this.isAirOrReedsInAir(world, pos.func_177984_a())) {
                  canPotentiallyReachAir = true;
               } else if (this.isWaterOrReedsInWater(world, pos.func_177984_a()) && this.isAirOrReedsInAir(world, pos.func_177981_b(2))) {
                  canPotentiallyReachAir = true;
               }

               return canPotentiallyReachAir;
            }

            Iterator var7 = Plane.HORIZONTAL.iterator();

            while(var7.hasNext()) {
               Direction horizontalDir = (Direction)var7.next();
               BlockState adjacentBelowState = world.func_180495_p(belowPos.func_177972_a(horizontalDir));
               FluidState fluid = adjacentBelowState.func_204520_s();
               if (fluid.func_206884_a(FluidTags.field_206959_a) || adjacentBelowState.func_177230_c() == Blocks.field_185778_de || this.canPlaceByIce && adjacentBelowState.func_185904_a() == Material.field_151588_w) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean isAirOrReedsInAir(IWorldReader world, BlockPos pos) {
      if (world.func_175623_d(pos)) {
         return true;
      } else {
         BlockState state = world.func_180495_p(pos);
         return state.func_177230_c() == this && !(Boolean)state.func_177229_b(WATERLOGGED);
      }
   }

   private boolean isWaterOrReedsInWater(IWorldReader world, BlockPos pos) {
      BlockState state = world.func_180495_p(pos);
      if (state.func_204520_s().func_206886_c() == Fluids.field_204546_a) {
         return true;
      } else {
         return state.func_177230_c() == this && (Boolean)state.func_177229_b(WATERLOGGED);
      }
   }

   public void func_225534_a_(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
      if (!state.func_196955_c(world, pos)) {
         world.func_175655_b(pos, true);
      } else if (this.canReedGrow && this.canReedGrowUpwards(world, pos, state)) {
         int age = (Integer)state.func_177229_b(AGE);
         if (ForgeHooks.onCropsGrowPre(world, pos, state, true)) {
            if (age == 15) {
               this.growReedAbove(world, pos, state);
            } else {
               world.func_180501_a(pos, (BlockState)state.func_206870_a(AGE, age + 1), 4);
            }

            ForgeHooks.onCropsGrowPost(world, pos, state);
         }
      }

   }

   private boolean canReedGrowUpwards(IBlockReader world, BlockPos pos, BlockState state) {
      ReedsBlock.Type reedType = (ReedsBlock.Type)state.func_177229_b(REEDS_TYPE);
      if (reedType == ReedsBlock.Type.ONE || reedType == ReedsBlock.Type.TWO_TOP) {
         BlockPos abovePos = pos.func_177984_a();
         if (world.func_180495_p(abovePos).isAir(world, abovePos)) {
            return true;
         }

         BlockPos twoAbovePos = abovePos.func_177984_a();
         if (world.func_204610_c(abovePos).func_206886_c() == Fluids.field_204546_a && world.func_180495_p(twoAbovePos).isAir(world, twoAbovePos)) {
            return true;
         }
      }

      return false;
   }

   private void growReedAbove(World world, BlockPos pos, BlockState state) {
      BlockPos abovePos = pos.func_177984_a();
      BlockState growAboveState = (BlockState)this.func_176223_P().func_206870_a(WATERLOGGED, world.func_204610_c(abovePos).func_206886_c() == Fluids.field_204546_a);
      ReedsBlock.Type reedType = (ReedsBlock.Type)state.func_177229_b(REEDS_TYPE);
      if (reedType == ReedsBlock.Type.ONE) {
         world.func_175656_a(pos.func_177984_a(), (BlockState)growAboveState.func_206870_a(REEDS_TYPE, ReedsBlock.Type.TWO_TOP));
      } else if (reedType == ReedsBlock.Type.TWO_TOP) {
         world.func_175656_a(pos.func_177984_a(), (BlockState)growAboveState.func_206870_a(REEDS_TYPE, ReedsBlock.Type.THREE_TOP));
      }

      BlockState updatedStateHere = world.func_180495_p(pos);
      world.func_180501_a(pos, (BlockState)updatedStateHere.func_206870_a(AGE, 0), 4);
   }

   public FluidState func_204507_t(BlockState state) {
      return (Boolean)state.func_177229_b(WATERLOGGED) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
   }

   public boolean func_176473_a(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
      return this.canReedGrow && this.canReedGrowUpwards(world, pos, state);
   }

   public boolean func_180670_a(World world, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void func_225535_a_(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
      int age = (Integer)state.func_177229_b(AGE);
      age += MathHelper.func_76136_a(rand, 7, 15);
      if (age >= 15) {
         this.growReedAbove(world, pos, state);
         int ageRemaining = age - 15;
         if (ageRemaining > 0) {
            BlockPos abovePos = pos.func_177984_a();
            BlockState aboveState = world.func_180495_p(abovePos);
            if (this.canReedGrowUpwards(world, abovePos, aboveState)) {
               int aboveAge = (Integer)aboveState.func_177229_b(AGE);
               aboveAge += ageRemaining;
               world.func_180501_a(abovePos, (BlockState)aboveState.func_206870_a(AGE, aboveAge), 4);
            }
         }
      } else {
         world.func_180501_a(pos, (BlockState)state.func_206870_a(AGE, age), 4);
      }

   }

   static {
      REEDS_TYPE = LOTRBlockStates.REEDS_TYPE;
      AGE = BlockStateProperties.field_208171_X;
      WATERLOGGED = BlockStateProperties.field_208198_y;
      SHAPE = Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
   }

   public static enum Type implements IStringSerializable {
      ONE("1"),
      TWO_BOTTOM("2_bottom"),
      TWO_TOP("2_top"),
      THREE_BOTTOM("3_bottom"),
      THREE_MIDDLE("3_middle"),
      THREE_TOP("3_top");

      private final String typeName;

      private Type(String s) {
         this.typeName = s;
      }

      public String func_176610_l() {
         return this.typeName;
      }
   }
}
