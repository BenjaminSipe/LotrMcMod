package lotr.common.block;

import java.util.Random;
import lotr.common.init.LOTRTileEntities;
import lotr.common.item.PocketMatchItem;
import lotr.common.stat.LOTRStats;
import lotr.common.tileentity.GondorBeaconTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GondorBeaconBlock extends Block {
   public static final BooleanProperty FULLY_LIT;
   private static final VoxelShape BEACON_SHAPE;

   public GondorBeaconBlock() {
      super(Properties.func_200945_a(Material.field_151575_d).func_200948_a(2.0F, 3.0F).func_226896_b_().func_200947_a(SoundType.field_185848_a));
      this.func_180632_j((BlockState)this.func_176223_P().func_206870_a(FULLY_LIT, false));
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{FULLY_LIT});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return BEACON_SHAPE;
   }

   public boolean hasTileEntity(BlockState state) {
      return true;
   }

   public TileEntity createTileEntity(BlockState state, IBlockReader world) {
      return ((TileEntityType)LOTRTileEntities.GONDOR_BEACON.get()).func_200968_a();
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      BlockPos belowPos = pos.func_177977_b();
      return world.func_180495_p(belowPos).func_224755_d(world, belowPos, Direction.UP);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      if (facing == Direction.DOWN && !state.func_196955_c(world, currentPos)) {
         return Blocks.field_150350_a.func_176223_P();
      } else {
         if (facing == Direction.UP && isBurning(world, currentPos) && this.isWaterAbove(world, currentPos)) {
            this.playExtinguishSound(world, currentPos);
            if (!world.func_201670_d()) {
               extinguish(world, currentPos);
            }
         }

         return super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
      }
   }

   private boolean isWaterAbove(IWorldReader world, BlockPos pos) {
      return world.func_204610_c(pos.func_177984_a()).func_206884_a(FluidTags.field_206959_a);
   }

   public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult target) {
      ItemStack heldItem = player.func_184586_b(hand);
      if (canItemLightBeacon(heldItem) && !isBurning(world, pos) && !this.isWaterAbove(world, pos)) {
         this.playLightSound(world, pos);
         if (!player.field_71075_bZ.field_75098_d) {
            if (heldItem.func_77984_f()) {
               heldItem.func_222118_a(1, player, (p) -> {
                  p.func_213334_d(hand);
               });
            } else if (heldItem.func_77976_d() > 1) {
               heldItem.func_190918_g(1);
            }
         }

         if (!world.field_72995_K) {
            beginBurning(world, pos);
         }

         player.func_195066_a(LOTRStats.LIGHT_GONDOR_BEACON);
         return ActionResultType.SUCCESS;
      } else if (canItemExtinguishBeacon(heldItem) && isBurning(world, pos)) {
         this.playExtinguishSound(world, pos);
         if (!player.field_71075_bZ.field_75098_d) {
            if (heldItem.hasContainerItem()) {
               player.func_184611_a(hand, heldItem.getContainerItem());
            } else {
               heldItem.func_190918_g(1);
            }
         }

         if (!world.field_72995_K) {
            extinguish(world, pos);
         }

         player.func_195066_a(LOTRStats.EXTINGUISH_GONDOR_BEACON);
         return ActionResultType.SUCCESS;
      } else {
         return super.func_225533_a_(state, world, pos, player, hand, target);
      }
   }

   private static boolean canItemLightBeacon(ItemStack itemstack) {
      Item item = itemstack.func_77973_b();
      return item == Items.field_151033_d || item instanceof PocketMatchItem || item instanceof BlockItem && ((BlockItem)item).func_179223_d() instanceof TorchBlock;
   }

   private static boolean canItemExtinguishBeacon(ItemStack itemstack) {
      Item item = itemstack.func_77973_b();
      return item instanceof BucketItem && ((BucketItem)item).getFluid().func_207185_a(FluidTags.field_206959_a);
   }

   public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
      return isFullyLit(state) ? 15 : 0;
   }

   public static boolean isFullyLit(BlockState state) {
      return (Boolean)state.func_177229_b(FULLY_LIT);
   }

   public static boolean isBurning(IBlockReader world, BlockPos pos) {
      TileEntity te = world.func_175625_s(pos);
      return te instanceof GondorBeaconTileEntity && ((GondorBeaconTileEntity)te).isBurning();
   }

   public static void beginBurning(IBlockReader world, BlockPos pos) {
      TileEntity te = world.func_175625_s(pos);
      if (te instanceof GondorBeaconTileEntity) {
         ((GondorBeaconTileEntity)te).beginBurning();
      }

   }

   public static void extinguish(IBlockReader world, BlockPos pos) {
      TileEntity te = world.func_175625_s(pos);
      if (te instanceof GondorBeaconTileEntity) {
         ((GondorBeaconTileEntity)te).extinguish();
      }

   }

   private void playLightSound(IWorld world, BlockPos pos) {
      world.func_184133_a((PlayerEntity)null, pos, SoundEvents.field_187649_bu, SoundCategory.BLOCKS, 1.0F, world.func_201674_k().nextFloat() * 0.4F + 0.8F);
   }

   private void playExtinguishSound(IWorld world, BlockPos pos) {
      world.func_184133_a((PlayerEntity)null, pos, SoundEvents.field_187646_bt, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.func_201674_k().nextFloat() - world.func_201674_k().nextFloat()) * 0.8F);
   }

   public void func_196262_a(BlockState state, World world, BlockPos pos, Entity entity) {
      if (entity.func_70027_ad() && !isBurning(world, pos) && !this.isWaterAbove(world, pos)) {
         this.playLightSound(world, pos);
         if (!world.field_72995_K) {
            beginBurning(world, pos);
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void func_180655_c(BlockState state, World world, BlockPos pos, Random rand) {
      if (isBurning(world, pos)) {
         if (rand.nextInt(24) == 0) {
            world.func_184134_a((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D, SoundEvents.field_187643_bs, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
         }

         for(int l = 0; l < 3; ++l) {
            double px = (double)((float)pos.func_177958_n() + rand.nextFloat());
            double py = (double)((float)pos.func_177956_o() + 0.5F + rand.nextFloat() * 0.5F);
            double pz = (double)((float)pos.func_177952_p() + rand.nextFloat());
            world.func_195594_a(ParticleTypes.field_197594_E, px, py, pz, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   static {
      FULLY_LIT = LOTRBlockStates.BEACON_FULLY_LIT;
      BEACON_SHAPE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);
   }
}
