package lotr.common.block;

import java.util.Random;
import lotr.common.init.LOTRSoundEvents;
import lotr.common.init.LOTRTileEntities;
import lotr.common.item.IEmptyVesselItem;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselType;
import lotr.common.stat.LOTRStats;
import lotr.common.tileentity.KegTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class KegBlock extends HorizontalBlock implements IWaterLoggable {
   public static final BooleanProperty UP;
   public static final BooleanProperty OPEN;
   public static final BooleanProperty WATERLOGGED;
   private static final VoxelShape KEG_SHAPE;

   public KegBlock(Properties properties) {
      super(properties);
      this.func_180632_j((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a(field_185512_D, Direction.NORTH)).func_206870_a(UP, false)).func_206870_a(OPEN, false)).func_206870_a(WATERLOGGED, false));
   }

   public KegBlock() {
      this(Properties.func_200949_a(Material.field_151575_d, MaterialColor.field_151650_B).func_200948_a(3.0F, 5.0F).func_200947_a(SoundType.field_185848_a));
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{field_185512_D, UP, OPEN, WATERLOGGED});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return KEG_SHAPE;
   }

   public boolean hasTileEntity(BlockState state) {
      return true;
   }

   public TileEntity createTileEntity(BlockState state, IBlockReader world) {
      return ((TileEntityType)LOTRTileEntities.KEG.get()).func_200968_a();
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      Direction facing = context.func_195992_f().func_176734_d();
      boolean up = context.func_195991_k().func_180495_p(context.func_195995_a().func_177984_a()).func_177230_c() == this;
      FluidState fluid = context.func_195991_k().func_204610_c(context.func_195995_a());
      return (BlockState)((BlockState)((BlockState)this.func_176223_P().func_206870_a(field_185512_D, facing)).func_206870_a(UP, up)).func_206870_a(WATERLOGGED, fluid.func_206886_c() == Fluids.field_204546_a);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.func_177229_b(WATERLOGGED)) {
         world.func_205219_F_().func_205360_a(currentPos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(world));
      }

      if (facing != Direction.UP) {
         return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
      } else {
         Block block = facingState.func_177230_c();
         return (BlockState)state.func_206870_a(UP, block == this);
      }
   }

   public void func_180633_a(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
      if (stack.func_82837_s()) {
         TileEntity te = world.func_175625_s(pos);
         if (te instanceof KegTileEntity) {
            ((KegTileEntity)te).func_213903_a(stack.func_200301_q());
         }
      }

   }

   public void func_225534_a_(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
      TileEntity te = world.func_175625_s(pos);
      if (te instanceof KegTileEntity) {
         ((KegTileEntity)te).kegTick();
      }

   }

   public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult target) {
      ItemStack heldItem = player.func_184586_b(hand);
      TileEntity te = world.func_175625_s(pos);
      if (!(te instanceof KegTileEntity)) {
         return super.func_225533_a_(state, world, pos, player, hand, target);
      } else {
         KegTileEntity keg = (KegTileEntity)te;
         ItemStack kegDrink = keg.getFinishedBrewDrink();
         if (target.func_216354_b() == state.func_177229_b(field_185512_D)) {
            ItemStack kegFill;
            if (!kegDrink.func_190926_b() && heldItem.func_77973_b() instanceof IEmptyVesselItem) {
               VesselType ves = ((IEmptyVesselItem)heldItem.func_77973_b()).getVesselType();
               kegFill = kegDrink.func_77946_l();
               kegFill.func_190920_e(1);
               VesselDrinkItem.setVessel(kegFill, ves);
               if (!player.field_71075_bZ.field_75098_d) {
                  heldItem.func_190918_g(1);
               }

               if (heldItem.func_190926_b()) {
                  player.func_184611_a(hand, kegFill);
               } else if (!player.field_71071_by.func_70441_a(kegFill)) {
                  player.func_71019_a(kegFill, false);
               }

               keg.consumeServing();
               if (!world.field_72995_K) {
                  world.func_184133_a((PlayerEntity)null, pos, LOTRSoundEvents.MUG_FILL, SoundCategory.BLOCKS, 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
               }

               player.func_195066_a(LOTRStats.INTERACT_KEG);
               return ActionResultType.SUCCESS;
            }

            if (!heldItem.func_190926_b() && heldItem.func_77973_b() instanceof VesselDrinkItem && ((VesselDrinkItem)heldItem.func_77973_b()).hasPotencies) {
               boolean match = false;
               if (keg.getKegMode() == KegTileEntity.KegMode.EMPTY) {
                  match = true;
               } else if (!kegDrink.func_190926_b() && kegDrink.func_190916_E() < 16) {
                  match = kegDrink.func_77973_b() == heldItem.func_77973_b() && VesselDrinkItem.getPotency(kegDrink) == VesselDrinkItem.getPotency(heldItem);
               }

               if (match) {
                  if (kegDrink.func_190926_b()) {
                     kegFill = heldItem.func_77946_l();
                     kegFill.func_190920_e(1);
                     VesselDrinkItem.setVessel(kegFill, VesselType.WOODEN_MUG);
                     keg.fillBrewedWith(kegFill);
                  } else {
                     kegDrink.func_190917_f(1);
                     keg.fillBrewedWith(kegDrink);
                  }

                  if (!player.field_71075_bZ.field_75098_d) {
                     VesselType ves = VesselDrinkItem.getVessel(heldItem);
                     ItemStack emptyMug = ves.createEmpty();
                     heldItem.func_190918_g(1);
                     if (heldItem.func_190926_b()) {
                        player.func_184611_a(hand, emptyMug);
                        player.field_71070_bA.func_75142_b();
                     } else if (!player.field_71071_by.func_70441_a(emptyMug)) {
                        player.func_71019_a(emptyMug, false);
                     }
                  }

                  if (!world.field_72995_K) {
                     world.func_184133_a((PlayerEntity)null, pos, LOTRSoundEvents.MUG_FILL, SoundCategory.BLOCKS, 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
                  }

                  player.func_195066_a(LOTRStats.INTERACT_KEG);
                  return ActionResultType.SUCCESS;
               }
            }
         }

         if (!world.field_72995_K) {
            NetworkHooks.openGui((ServerPlayerEntity)player, keg, (extraData) -> {
            });
            player.func_195066_a(LOTRStats.INTERACT_KEG);
         }

         return ActionResultType.SUCCESS;
      }
   }

   public void func_196243_a(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.func_177230_c() != newState.func_177230_c()) {
         TileEntity te = world.func_175625_s(pos);
         if (te instanceof KegTileEntity) {
            KegTileEntity keg = (KegTileEntity)te;
            keg.dropContentsExceptBrew();
            world.func_175666_e(pos, this);
         }

         super.func_196243_a(state, world, pos, newState, isMoving);
      }

   }

   public boolean func_149740_M(BlockState state) {
      return true;
   }

   public int func_180641_l(BlockState state, World world, BlockPos pos) {
      return Container.func_178144_a(world.func_175625_s(pos));
   }

   public FluidState func_204507_t(BlockState state) {
      return (Boolean)state.func_177229_b(WATERLOGGED) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
   }

   static {
      UP = BlockStateProperties.field_208149_B;
      OPEN = BlockStateProperties.field_208193_t;
      WATERLOGGED = BlockStateProperties.field_208198_y;
      KEG_SHAPE = Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
   }
}
