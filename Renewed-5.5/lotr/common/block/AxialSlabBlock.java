package lotr.common.block;

import java.util.Iterator;
import java.util.function.Supplier;
import lotr.common.LOTRLog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class AxialSlabBlock extends LOTRSlabBlock {
   public static final VoxelShape NORTH_SHAPE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
   public static final VoxelShape SOUTH_SHAPE = Block.func_208617_a(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
   public static final VoxelShape WEST_SHAPE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
   public static final VoxelShape EAST_SHAPE = Block.func_208617_a(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

   public AxialSlabBlock(Block block) {
      super(block);
      Axis defaultAxis = this.getSlabAxisProperty().func_177700_c().contains(Axis.Y) ? Axis.Y : Axis.X;
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(this.getSlabAxisProperty(), defaultAxis));
   }

   public AxialSlabBlock(Supplier block) {
      this((Block)block.get());
   }

   protected EnumProperty getSlabAxisProperty() {
      return LOTRBlockStates.SLAB_AXIS;
   }

   protected boolean canDoubleSlabBeWaterlogged() {
      return false;
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{this.getSlabAxisProperty()});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      SlabType slabType = (SlabType)state.func_177229_b(field_196505_a);
      if (slabType == SlabType.DOUBLE) {
         return VoxelShapes.func_197868_b();
      } else {
         boolean top = slabType == SlabType.TOP;
         Axis axis = (Axis)state.func_177229_b(this.getSlabAxisProperty());
         if (axis == Axis.Y) {
            return top ? SlabBlock.field_196507_c : SlabBlock.field_196506_b;
         } else if (axis == Axis.X) {
            return top ? EAST_SHAPE : WEST_SHAPE;
         } else if (axis == Axis.Z) {
            return top ? SOUTH_SHAPE : NORTH_SHAPE;
         } else {
            return VoxelShapes.func_197868_b();
         }
      }
   }

   private static Axis getSlabAxis(BlockState state) {
      Block block = state.func_177230_c();
      if (block instanceof SlabBlock) {
         Iterator var2 = state.func_235904_r_().iterator();

         Property prop;
         do {
            if (!var2.hasNext()) {
               if (block.getClass() == SlabBlock.class) {
                  return Axis.Y;
               }

               LOTRLog.warn("Unknown SlabBlock subclass: %s with no axis-based property. Assuming axis = Y", block.getClass().toString());
               return Axis.Y;
            }

            prop = (Property)var2.next();
         } while(!(prop instanceof EnumProperty) || ((EnumProperty)prop).func_177699_b() != Axis.class);

         return (Axis)state.func_177229_b(prop);
      } else {
         throw new IllegalArgumentException("This method should only get called on known instances of SlabBlock");
      }
   }

   protected boolean isSameSlab(SlabBlock otherSlab) {
      return otherSlab == this;
   }

   protected final boolean isSameSlab(BlockState otherBlockState) {
      Block otherBlock = otherBlockState.func_177230_c();
      return otherBlock instanceof SlabBlock && this.isSameSlab((SlabBlock)otherBlock);
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      AxialSlabBlock.AxialSlabPlacement placement = this.getSlabPlacementState(context);
      return (BlockState)((BlockState)((BlockState)this.func_176223_P().func_206870_a(this.getSlabAxisProperty(), placement.axis)).func_206870_a(field_196505_a, placement.slabType)).func_206870_a(field_204512_b, placement.waterlogged);
   }

   protected final AxialSlabBlock.AxialSlabPlacement getSlabPlacementState(BlockItemUseContext context) {
      World world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      BlockState state = world.func_180495_p(pos);
      Direction dir = context.func_196000_l();
      Axis axis = dir.func_176740_k();
      FluidState fluid = context.func_195991_k().func_204610_c(pos);
      boolean waterlogged = fluid.func_206886_c() == Fluids.field_204546_a;
      if (this.isSameSlab(state)) {
         waterlogged &= this.canDoubleSlabBeWaterlogged();
         return AxialSlabBlock.AxialSlabPlacement.of(getSlabAxis(state), SlabType.DOUBLE, waterlogged);
      } else {
         BlockPos clickedPos = pos.func_177972_a(dir.func_176734_d());
         BlockState clickedState = world.func_180495_p(clickedPos);
         boolean sneaking = context.func_225518_g_();
         if (sneaking) {
            if (axis.func_176722_c()) {
               axis = Axis.Y;
            } else if (axis.func_200128_b() && (!isSingleSlab(clickedState) || !getSlabAxis(clickedState).func_176722_c())) {
               dir = context.func_195992_f();
               axis = dir.func_176740_k();
            }
         } else if (isSingleSlab(clickedState)) {
            axis = getSlabAxis(clickedState);
         }

         Direction axisPosDir = Direction.func_181076_a(AxisDirection.POSITIVE, axis);
         Direction axisNegDir = Direction.func_181076_a(AxisDirection.NEGATIVE, axis);
         double relevantHitVecCoord = axis.func_196051_a(context.func_221532_j().field_72450_a, context.func_221532_j().field_72448_b, context.func_221532_j().field_72449_c);
         double relevantPosCoord = (double)axis.func_196052_a(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
         return dir == axisNegDir || dir != axisPosDir && relevantHitVecCoord - relevantPosCoord > 0.5D ? AxialSlabBlock.AxialSlabPlacement.of(axis, SlabType.TOP, waterlogged) : AxialSlabBlock.AxialSlabPlacement.of(axis, SlabType.BOTTOM, waterlogged);
      }
   }

   private static boolean isSingleSlab(BlockState state) {
      return state.func_177230_c() instanceof SlabBlock && state.func_177229_b(field_196505_a) != SlabType.DOUBLE;
   }

   public boolean func_196253_a(BlockState state, BlockItemUseContext context) {
      return this.isSlabReplaceable(state, context);
   }

   protected final boolean isSlabReplaceable(BlockState state, BlockItemUseContext context) {
      World world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      ItemStack itemstack = context.func_195996_i();
      boolean holdingSameSlab = false;
      if (itemstack.func_77973_b() instanceof BlockItem) {
         Block itemBlock = ((BlockItem)itemstack.func_77973_b()).func_179223_d();
         if (itemBlock instanceof SlabBlock) {
            holdingSameSlab = this.isSameSlab((SlabBlock)itemBlock);
         }
      }

      Direction dir = context.func_196000_l();
      boolean sneaking = context.func_225518_g_();
      SlabType slabType = (SlabType)state.func_177229_b(field_196505_a);
      Axis existingAxis = getSlabAxis(state);
      if (sneaking && existingAxis != Axis.Y) {
         BlockPos offsetPos = pos.func_177972_a(dir);
         if (world.func_180495_p(offsetPos).func_196953_a(AxialSlabBlock.AxialSlabUseContext.makeReplacementContext(context, offsetPos, dir))) {
            return false;
         }
      }

      if (slabType != SlabType.DOUBLE && holdingSameSlab) {
         if (context.func_196012_c()) {
            double relevantHitVecCoord = existingAxis.func_196051_a(context.func_221532_j().field_72450_a, context.func_221532_j().field_72448_b, context.func_221532_j().field_72449_c);
            double relevantPosCoord = (double)existingAxis.func_196052_a(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
            boolean flag = relevantHitVecCoord - relevantPosCoord > 0.5D;
            if (slabType == SlabType.BOTTOM) {
               return dir == Direction.func_181076_a(AxisDirection.POSITIVE, existingAxis) || flag && dir.func_176740_k() != existingAxis;
            } else {
               return dir == Direction.func_181076_a(AxisDirection.NEGATIVE, existingAxis) || !flag && dir.func_176740_k() != existingAxis;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public BlockState func_185499_a(BlockState state, Rotation rot) {
      SlabType slabType = (SlabType)state.func_177229_b(field_196505_a);
      Axis axis = (Axis)state.func_177229_b(this.getSlabAxisProperty());
      AxisDirection axisDir = slabType == SlabType.BOTTOM ? AxisDirection.NEGATIVE : AxisDirection.POSITIVE;
      Direction dir = Direction.func_211699_a(axis, axisDir);
      Direction rotatedDir = rot.func_185831_a(dir);
      Axis rotatedAxis = rotatedDir.func_176740_k();
      AxisDirection rotatedAxisDir = rotatedDir.func_176743_c();
      if (this.getSlabAxisProperty().func_177700_c().contains(rotatedAxis)) {
         SlabType rotatedSlabType = slabType == SlabType.DOUBLE ? slabType : (rotatedAxisDir == AxisDirection.NEGATIVE ? SlabType.BOTTOM : (rotatedAxisDir == AxisDirection.POSITIVE ? SlabType.TOP : slabType));
         return (BlockState)((BlockState)state.func_206870_a(this.getSlabAxisProperty(), rotatedAxis)).func_206870_a(field_196505_a, rotatedSlabType);
      } else {
         return state;
      }
   }

   public BlockState func_185471_a(BlockState state, Mirror mirror) {
      Axis axis = (Axis)state.func_177229_b(this.getSlabAxisProperty());
      SlabType type = (SlabType)state.func_177229_b(field_196505_a);
      if (mirror == Mirror.LEFT_RIGHT && axis == Axis.Z || mirror == Mirror.FRONT_BACK && axis == Axis.X) {
         if (type == SlabType.BOTTOM) {
            type = SlabType.TOP;
         } else if (type == SlabType.TOP) {
            type = SlabType.BOTTOM;
         }
      }

      return (BlockState)state.func_206870_a(field_196505_a, type);
   }

   public boolean func_204509_a(IWorld world, BlockPos pos, BlockState state, FluidState fluidState) {
      return this.canDoubleSlabBeWaterlogged() ? (new AxialSlabBlock.DefaultImplWaterLoggable()).func_204509_a(world, pos, state, fluidState) : super.func_204509_a(world, pos, state, fluidState);
   }

   public boolean func_204510_a(IBlockReader world, BlockPos pos, BlockState state, Fluid fluid) {
      return this.canDoubleSlabBeWaterlogged() ? (new AxialSlabBlock.DefaultImplWaterLoggable()).func_204510_a(world, pos, state, fluid) : super.func_204510_a(world, pos, state, fluid);
   }

   private final class DefaultImplWaterLoggable implements IWaterLoggable {
      private DefaultImplWaterLoggable() {
      }

      // $FF: synthetic method
      DefaultImplWaterLoggable(Object x1) {
         this();
      }
   }

   protected static class AxialSlabUseContext extends BlockItemUseContext {
      protected AxialSlabUseContext(World w, PlayerEntity pl, Hand h, ItemStack stack, BlockRayTraceResult rayTrace) {
         super(w, pl, h, stack, rayTrace);
         BlockState state = this.func_195991_k().func_180495_p(rayTrace.func_216350_a());
         if (state.func_177230_c() instanceof SlabBlock) {
            SlabBlock slabBlock = (SlabBlock)state.func_177230_c();
            VerticalOnlySlabBlock verticalSlab = VerticalOnlySlabBlock.getVerticalSlabFor(slabBlock);
            if (verticalSlab != null) {
               this.field_196013_a = verticalSlab.func_196253_a(state, this);
            }
         }

      }

      public AxialSlabUseContext(ItemUseContext context) {
         super(context);
      }

      public static AxialSlabBlock.AxialSlabUseContext makeReplacementContext(BlockItemUseContext context, BlockPos pos, Direction dir) {
         Vector3d blockVec = new Vector3d((double)pos.func_177958_n() + 0.5D + (double)dir.func_82601_c() * 0.5D, (double)pos.func_177956_o() + 0.5D + (double)dir.func_96559_d() * 0.5D, (double)pos.func_177952_p() + 0.5D + (double)dir.func_82599_e() * 0.5D);
         BlockRayTraceResult rayTrace = new BlockRayTraceResult(blockVec, dir, pos, false);
         return new AxialSlabBlock.AxialSlabUseContext(context.func_195991_k(), context.func_195999_j(), context.func_221531_n(), context.func_195996_i(), rayTrace);
      }
   }

   public static class AxialSlabPlacement {
      public final Axis axis;
      public final SlabType slabType;
      public final boolean waterlogged;

      private AxialSlabPlacement(Axis ax, SlabType type, boolean water) {
         this.axis = ax;
         this.slabType = type;
         this.waterlogged = water;
      }

      public static AxialSlabBlock.AxialSlabPlacement of(Axis ax, SlabType type, boolean water) {
         return new AxialSlabBlock.AxialSlabPlacement(ax, type, water);
      }
   }
}
