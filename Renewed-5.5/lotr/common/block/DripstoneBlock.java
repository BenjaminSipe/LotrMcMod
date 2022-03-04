package lotr.common.block;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import lotr.common.init.LOTRDamageSources;
import lotr.common.util.LOTRUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DripstoneBlock extends Block implements IWaterLoggable {
   public static final Map BLOCK_TO_DRIPSTONE = new HashMap();
   public static final EnumProperty DRIPSTONE_TYPE;
   public static final BooleanProperty WATERLOGGED;
   private static final VoxelShape STALACTITE_SHAPE;
   private static final VoxelShape STALAGMITE_SHAPE;
   private static final VoxelShape STALACTITE_DOUBLE_BASE_SHAPE;
   private static final VoxelShape STALACTITE_DOUBLE_POINT_SHAPE;
   private static final VoxelShape STALAGMITE_DOUBLE_BASE_SHAPE;
   private static final VoxelShape STALAGMITE_DOUBLE_POINT_SHAPE;
   private final Block modelBlock;
   private final IParticleData particleType;
   public final boolean isWaterloggable;

   public DripstoneBlock(Block block, IParticleData particle, boolean waterlog) {
      super(Properties.func_200950_a(block).func_226896_b_());
      this.modelBlock = block;
      this.particleType = particle;
      this.isWaterloggable = waterlog;
      this.func_180632_j((BlockState)((BlockState)this.func_176223_P().func_206870_a(DRIPSTONE_TYPE, DripstoneBlock.Type.STALACTITE)).func_206870_a(WATERLOGGED, false));
      BLOCK_TO_DRIPSTONE.put(block, this);
   }

   public DripstoneBlock(Block block) {
      this(block, true);
   }

   public DripstoneBlock(Block block, IParticleData particle) {
      this(block, particle, true);
   }

   public DripstoneBlock(Block block, boolean waterlog) {
      this(block, ParticleTypes.field_197618_k, waterlog);
   }

   public DripstoneBlock(Supplier blockSup) {
      this((Block)blockSup.get());
   }

   public DripstoneBlock(Supplier blockSup, IParticleData particle) {
      this((Block)blockSup.get(), particle);
   }

   protected void func_206840_a(Builder builder) {
      super.func_206840_a(builder);
      builder.func_206894_a(new Property[]{DRIPSTONE_TYPE, WATERLOGGED});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      switch((DripstoneBlock.Type)state.func_177229_b(DRIPSTONE_TYPE)) {
      case STALACTITE:
         return STALACTITE_SHAPE;
      case STALAGMITE:
      default:
         return STALAGMITE_SHAPE;
      case STALACTITE_DOUBLE_BASE:
         return STALACTITE_DOUBLE_BASE_SHAPE;
      case STALACTITE_DOUBLE_POINT:
         return STALACTITE_DOUBLE_POINT_SHAPE;
      case STALAGMITE_DOUBLE_BASE:
         return STALAGMITE_DOUBLE_BASE_SHAPE;
      case STALAGMITE_DOUBLE_POINT:
         return STALAGMITE_DOUBLE_POINT_SHAPE;
      }
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      World world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      BlockState state = context.func_195991_k().func_180495_p(pos);
      BlockState placeState = this.func_176223_P();
      if (this.isWaterloggable) {
         FluidState fluid = context.func_195991_k().func_204610_c(pos);
         placeState = (BlockState)placeState.func_206870_a(WATERLOGGED, fluid.func_206886_c() == Fluids.field_204546_a);
      }

      Direction hitFace = context.func_196000_l();
      boolean pointingDown = hitFace != Direction.DOWN && (hitFace == Direction.UP || context.func_221532_j().field_72448_b - (double)pos.func_177956_o() <= 0.5D);
      BlockState stalagmiteDouble = (BlockState)placeState.func_206870_a(DRIPSTONE_TYPE, DripstoneBlock.Type.STALAGMITE_DOUBLE_POINT);
      BlockState stalactiteDouble = (BlockState)placeState.func_206870_a(DRIPSTONE_TYPE, DripstoneBlock.Type.STALACTITE_DOUBLE_POINT);
      boolean stalagmiteDoubleValid = stalagmiteDouble.func_196955_c(world, pos);
      boolean stalactiteDoubleValid = stalactiteDouble.func_196955_c(world, pos);
      if (stalagmiteDoubleValid && stalactiteDoubleValid) {
         return pointingDown ? stalagmiteDouble : stalactiteDouble;
      } else if (stalagmiteDoubleValid && pointingDown) {
         return stalagmiteDouble;
      } else if (stalactiteDoubleValid && !pointingDown) {
         return stalactiteDouble;
      } else {
         BlockState stalagmite = (BlockState)placeState.func_206870_a(DRIPSTONE_TYPE, DripstoneBlock.Type.STALAGMITE);
         BlockState stalactite = (BlockState)placeState.func_206870_a(DRIPSTONE_TYPE, DripstoneBlock.Type.STALACTITE);
         boolean stalagmiteValid = stalagmite.func_196955_c(world, pos);
         boolean stalactiteValid = stalactite.func_196955_c(world, pos);
         if (stalagmiteValid && stalactiteValid) {
            return pointingDown ? stalagmite : stalactite;
         } else if (stalagmiteValid) {
            return stalagmite;
         } else {
            return stalactiteValid ? stalactite : null;
         }
      }
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if (this.isWaterloggable && (Boolean)state.func_177229_b(WATERLOGGED)) {
         world.func_205219_F_().func_205360_a(currentPos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(world));
      }

      boolean check = false;
      DripstoneBlock.Type type = (DripstoneBlock.Type)state.func_177229_b(DRIPSTONE_TYPE);
      if (type == DripstoneBlock.Type.STALACTITE && facing == Direction.UP) {
         check = true;
      } else if (type == DripstoneBlock.Type.STALAGMITE && facing == Direction.DOWN) {
         check = true;
      } else if ((type == DripstoneBlock.Type.STALACTITE_DOUBLE_BASE || type == DripstoneBlock.Type.STALACTITE_DOUBLE_POINT || type == DripstoneBlock.Type.STALAGMITE_DOUBLE_BASE || type == DripstoneBlock.Type.STALAGMITE_DOUBLE_POINT) && (facing == Direction.DOWN || facing == Direction.UP)) {
         check = true;
      }

      if (check && !state.func_196955_c(world, currentPos)) {
         BlockState singleState;
         if (type == DripstoneBlock.Type.STALACTITE_DOUBLE_BASE) {
            singleState = (BlockState)state.func_206870_a(DRIPSTONE_TYPE, DripstoneBlock.Type.STALACTITE);
            if (singleState.func_196955_c(world, currentPos)) {
               return singleState;
            }
         } else if (type == DripstoneBlock.Type.STALAGMITE_DOUBLE_BASE) {
            singleState = (BlockState)state.func_206870_a(DRIPSTONE_TYPE, DripstoneBlock.Type.STALAGMITE);
            if (singleState.func_196955_c(world, currentPos)) {
               return singleState;
            }
         }

         return Blocks.field_150350_a.func_176223_P();
      } else if (type == DripstoneBlock.Type.STALAGMITE && facing == Direction.UP && this.matchType(world, currentPos.func_177984_a(), DripstoneBlock.Type.STALAGMITE_DOUBLE_POINT)) {
         return (BlockState)state.func_206870_a(DRIPSTONE_TYPE, DripstoneBlock.Type.STALAGMITE_DOUBLE_BASE);
      } else {
         return type == DripstoneBlock.Type.STALACTITE && facing == Direction.DOWN && this.matchType(world, currentPos.func_177977_b(), DripstoneBlock.Type.STALACTITE_DOUBLE_POINT) ? (BlockState)state.func_206870_a(DRIPSTONE_TYPE, DripstoneBlock.Type.STALACTITE_DOUBLE_BASE) : super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
      }
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      boolean isPresent = world.func_180495_p(pos).func_177230_c() == this;
      DripstoneBlock.Type type = (DripstoneBlock.Type)state.func_177229_b(DRIPSTONE_TYPE);
      if (type == DripstoneBlock.Type.STALACTITE) {
         return this.checkSolidSide(world, pos.func_177984_a(), Direction.DOWN);
      } else if (type == DripstoneBlock.Type.STALAGMITE) {
         return this.checkSolidSide(world, pos.func_177977_b(), Direction.UP);
      } else if (type == DripstoneBlock.Type.STALACTITE_DOUBLE_BASE) {
         return this.checkSolidSide(world, pos.func_177984_a(), Direction.DOWN) && this.matchType(world, pos.func_177977_b(), DripstoneBlock.Type.STALACTITE_DOUBLE_POINT);
      } else if (type == DripstoneBlock.Type.STALACTITE_DOUBLE_POINT) {
         return isPresent ? this.matchType(world, pos.func_177984_a(), DripstoneBlock.Type.STALACTITE_DOUBLE_BASE) : this.matchType(world, pos.func_177984_a(), DripstoneBlock.Type.STALACTITE);
      } else if (type != DripstoneBlock.Type.STALAGMITE_DOUBLE_BASE) {
         if (type == DripstoneBlock.Type.STALAGMITE_DOUBLE_POINT) {
            return isPresent ? this.matchType(world, pos.func_177977_b(), DripstoneBlock.Type.STALAGMITE_DOUBLE_BASE) : this.matchType(world, pos.func_177977_b(), DripstoneBlock.Type.STALAGMITE);
         } else {
            return true;
         }
      } else {
         return this.checkSolidSide(world, pos.func_177977_b(), Direction.UP) && this.matchType(world, pos.func_177984_a(), DripstoneBlock.Type.STALAGMITE_DOUBLE_POINT);
      }
   }

   private boolean checkSolidSide(IWorldReader world, BlockPos pos, Direction dir) {
      BlockState state = world.func_180495_p(pos);
      return LOTRUtil.hasSolidSide(world, pos, dir) || dir.func_176740_k().func_200128_b() && state.func_177230_c() instanceof WallBlock;
   }

   private boolean matchType(IWorldReader world, BlockPos pos, DripstoneBlock.Type type) {
      BlockState state = world.func_180495_p(pos);
      return state.func_177230_c() == this && state.func_177229_b(DRIPSTONE_TYPE) == type;
   }

   public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
      ItemStack heldItem = player.func_184614_ca();
      if (heldItem.func_77973_b() == this.func_199767_j()) {
         BlockItemUseContext useContext = new BlockItemUseContext(new ItemUseContext(player, hand, trace));
         DripstoneBlock.Type type = (DripstoneBlock.Type)state.func_177229_b(DRIPSTONE_TYPE);
         BlockPos placePos = null;
         BlockState placeState = null;
         if (type == DripstoneBlock.Type.STALACTITE) {
            placePos = pos.func_177977_b();
            placeState = (BlockState)this.func_176223_P().func_206870_a(DRIPSTONE_TYPE, DripstoneBlock.Type.STALACTITE_DOUBLE_POINT);
         } else if (type == DripstoneBlock.Type.STALAGMITE) {
            placePos = pos.func_177984_a();
            placeState = (BlockState)this.func_176223_P().func_206870_a(DRIPSTONE_TYPE, DripstoneBlock.Type.STALAGMITE_DOUBLE_POINT);
         }

         if (placePos != null && placeState != null) {
            boolean canDouble = world.func_180495_p(placePos).func_196953_a(useContext);
            if (canDouble) {
               BlockState placeStateFull = placeState;
               if (this.isWaterloggable) {
                  placeStateFull = (BlockState)placeState.func_206870_a(WATERLOGGED, world.func_204610_c(placePos).func_206886_c() == Fluids.field_204546_a);
               }

               world.func_180501_a(placePos, placeStateFull, 3);
               SoundType sound = this.getSoundType(placeStateFull, world, placePos, player);
               world.func_184133_a(player, placePos, sound.func_185841_e(), SoundCategory.BLOCKS, (sound.func_185843_a() + 1.0F) / 2.0F, sound.func_185847_b() * 0.8F);
               if (!player.field_71075_bZ.field_75098_d) {
                  heldItem.func_190918_g(1);
               }

               return ActionResultType.SUCCESS;
            }
         }
      }

      return super.func_225533_a_(state, world, pos, player, hand, trace);
   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      this.modelBlock.func_180655_c(state, world, pos, rand);
      DripstoneBlock.Type type = (DripstoneBlock.Type)state.func_177229_b(DRIPSTONE_TYPE);
      if ((type == DripstoneBlock.Type.STALACTITE || type == DripstoneBlock.Type.STALACTITE_DOUBLE_POINT) && rand.nextInt(50) == 0) {
         BlockPos abovePos = pos.func_177984_a();
         BlockState above = world.func_180495_p(abovePos);
         if (above.func_200015_d(world, abovePos) && above.func_185904_a() == Material.field_151576_e) {
            BlockPos belowPos = pos.func_177977_b();
            BlockState below = world.func_180495_p(belowPos);
            if (!below.func_200132_m() || !below.func_224755_d(world, belowPos, Direction.UP)) {
               world.func_195594_a(this.particleType, (double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() - 0.05D, (double)pos.func_177952_p() + 0.5D, 0.0D, 0.0D, 0.0D);
            }
         }
      }

   }

   public void func_180658_a(World world, BlockPos pos, Entity entity, float fallDistance) {
      if (entity instanceof LivingEntity) {
         DripstoneBlock.Type type = (DripstoneBlock.Type)world.func_180495_p(pos).func_177229_b(DRIPSTONE_TYPE);
         if (type == DripstoneBlock.Type.STALAGMITE || type == DripstoneBlock.Type.STALAGMITE_DOUBLE_POINT) {
            float damage = fallDistance * 2.0F + 1.0F;
            entity.func_70097_a(LOTRDamageSources.STALAGMITE, damage);
         }
      }

   }

   public boolean func_225541_a_(BlockState state, Fluid fluid) {
      return !this.isWaterloggable ? true : super.func_225541_a_(state, fluid);
   }

   public PushReaction func_149656_h(BlockState state) {
      return PushReaction.DESTROY;
   }

   public FluidState func_204507_t(BlockState state) {
      return this.isWaterloggable && (Boolean)state.func_177229_b(WATERLOGGED) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
   }

   public boolean func_204510_a(IBlockReader world, BlockPos pos, BlockState state, Fluid fluid) {
      return this.isWaterloggable ? super.func_204510_a(world, pos, state, fluid) : false;
   }

   public boolean func_204509_a(IWorld world, BlockPos pos, BlockState state, FluidState fluid) {
      return this.isWaterloggable ? super.func_204509_a(world, pos, state, fluid) : false;
   }

   public Fluid func_204508_a(IWorld world, BlockPos pos, BlockState state) {
      return this.isWaterloggable ? super.func_204508_a(world, pos, state) : Fluids.field_204541_a;
   }

   static {
      DRIPSTONE_TYPE = LOTRBlockStates.DRIPSTONE_TYPE;
      WATERLOGGED = BlockStateProperties.field_208198_y;
      STALACTITE_SHAPE = VoxelShapes.func_216384_a(Block.func_208617_a(4.5D, 12.0D, 4.5D, 11.5D, 16.0D, 11.5D), new VoxelShape[]{Block.func_208617_a(5.5D, 8.0D, 5.5D, 10.5D, 12.0D, 10.5D), Block.func_208617_a(6.5D, 4.0D, 6.5D, 9.5D, 8.0D, 9.5D), Block.func_208617_a(7.5D, 0.0D, 7.5D, 8.5D, 4.0D, 8.5D)});
      STALAGMITE_SHAPE = VoxelShapes.func_216384_a(Block.func_208617_a(4.5D, 0.0D, 4.5D, 11.5D, 4.0D, 11.5D), new VoxelShape[]{Block.func_208617_a(5.5D, 4.0D, 5.5D, 10.5D, 8.0D, 10.5D), Block.func_208617_a(6.5D, 8.0D, 6.5D, 9.5D, 12.0D, 9.5D), Block.func_208617_a(7.5D, 12.0D, 7.5D, 8.5D, 16.0D, 8.5D)});
      STALACTITE_DOUBLE_BASE_SHAPE = VoxelShapes.func_197872_a(Block.func_208617_a(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D), Block.func_208617_a(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D));
      STALACTITE_DOUBLE_POINT_SHAPE = VoxelShapes.func_197872_a(Block.func_208617_a(6.0D, 8.0D, 6.0D, 10.0D, 16.0D, 10.0D), Block.func_208617_a(7.0D, 0.0D, 7.0D, 9.0D, 8.0D, 9.0D));
      STALAGMITE_DOUBLE_BASE_SHAPE = VoxelShapes.func_197872_a(Block.func_208617_a(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D), Block.func_208617_a(5.0D, 8.0D, 5.0D, 11.0D, 16.0D, 11.0D));
      STALAGMITE_DOUBLE_POINT_SHAPE = VoxelShapes.func_197872_a(Block.func_208617_a(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D), Block.func_208617_a(7.0D, 8.0D, 7.0D, 9.0D, 16.0D, 9.0D));
   }

   public static enum Type implements IStringSerializable {
      STALACTITE("stalactite"),
      STALAGMITE("stalagmite"),
      STALACTITE_DOUBLE_BASE("stalactite_double_base"),
      STALACTITE_DOUBLE_POINT("stalactite_double_point"),
      STALAGMITE_DOUBLE_BASE("stalagmite_double_base"),
      STALAGMITE_DOUBLE_POINT("stalagmite_double_point");

      private final String typeName;

      private Type(String s) {
         this.typeName = s;
      }

      public String func_176610_l() {
         return this.typeName;
      }
   }
}
