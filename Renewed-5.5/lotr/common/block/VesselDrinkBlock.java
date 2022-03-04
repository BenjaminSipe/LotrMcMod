package lotr.common.block;

import lotr.common.init.LOTRBlocks;
import lotr.common.init.LOTRItems;
import lotr.common.init.LOTRSoundEvents;
import lotr.common.init.LOTRTileEntities;
import lotr.common.item.VesselDrinkItem;
import lotr.common.item.VesselOperations;
import lotr.common.item.VesselType;
import lotr.common.tileentity.VesselDrinkTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class VesselDrinkBlock extends HorizontalBlock {
   public static final DirectionProperty FACING;
   private final VoxelShape vesselShape;

   public VesselDrinkBlock(Properties properties, VoxelShape shape) {
      super(properties);
      this.func_180632_j((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a(FACING, Direction.NORTH));
      this.vesselShape = shape;
   }

   public VesselDrinkBlock(SoundType sound, float width, float height) {
      this(Properties.func_200945_a(Material.field_151594_q).func_200943_b(0.0F).func_200947_a(sound), createVesselShape(width, height));
   }

   private static VoxelShape createVesselShape(float width, float height) {
      float halfWidth = width / 2.0F;
      return Block.func_208617_a(8.0D - (double)halfWidth, 0.0D, 8.0D - (double)halfWidth, 8.0D + (double)halfWidth, (double)height, 8.0D + (double)halfWidth);
   }

   public static VesselDrinkBlock makeWoodenMug() {
      return new VesselDrinkBlock(SoundType.field_185848_a, 4.5F, 6.0F);
   }

   public static VesselDrinkBlock makeCeramicMug() {
      return new VesselDrinkBlock(LOTRBlocks.SOUND_CERAMIC, 4.5F, 6.0F);
   }

   public static VesselDrinkBlock makeMetalGoblet() {
      return new VesselDrinkBlock(SoundType.field_185852_e, 3.75F, 6.75F);
   }

   public static VesselDrinkBlock makeWoodenGoblet() {
      return new VesselDrinkBlock(SoundType.field_185852_e, 3.75F, 6.75F);
   }

   public static VesselDrinkBlock makeWineGlass() {
      return new VesselDrinkBlock(SoundType.field_185853_f, 3.75F, 7.5F);
   }

   public static VesselDrinkBlock makeAleHorn() {
      return new VesselDrinkBlock(SoundType.field_185851_d, 7.5F, 9.0F);
   }

   protected void func_206840_a(Builder builder) {
      builder.func_206894_a(new Property[]{FACING});
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return this.vesselShape;
   }

   public boolean hasTileEntity(BlockState state) {
      return true;
   }

   public TileEntity createTileEntity(BlockState state, IBlockReader world) {
      return ((TileEntityType)LOTRTileEntities.VESSEL_DRINK.get()).func_200968_a();
   }

   public BlockState func_196258_a(BlockItemUseContext context) {
      return (BlockState)this.func_176223_P().func_206870_a(field_185512_D, context.func_195992_f().func_176734_d());
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      return Block.func_220055_a(world, pos.func_177977_b(), Direction.UP);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      return facing == Direction.DOWN && !state.func_196955_c(world, currentPos) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   public void func_176224_k(World world, BlockPos pos) {
      if (world.field_73012_v.nextInt(20) == 0) {
         float temp = world.func_226691_t_(pos).func_225486_c(pos);
         if (temp >= 0.15F) {
            TileEntity te = world.func_175625_s(pos);
            if (te instanceof VesselDrinkTileEntity) {
               VesselDrinkTileEntity vessel = (VesselDrinkTileEntity)te;
               vessel.fillFromRain();
            }
         }
      }

   }

   public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
      ItemStack heldItem = player.func_184614_ca();
      TileEntity te = world.func_175625_s(pos);
      if (te instanceof VesselDrinkTileEntity) {
         VesselDrinkTileEntity vessel = (VesselDrinkTileEntity)te;
         ItemStack itemInVessel = vessel.getVesselItem();
         ItemStack equivalentDrink;
         if (!vessel.isEmpty() && VesselOperations.isItemEmptyVessel(heldItem)) {
            equivalentDrink = itemInVessel.func_77946_l();
            VesselType vesselType = VesselOperations.getVessel(heldItem);
            equivalentDrink = VesselOperations.getWithVesselSet(equivalentDrink, vesselType, true);
            if (player.field_71075_bZ.field_75098_d) {
               player.func_184611_a(hand, equivalentDrink);
            } else {
               heldItem.func_190918_g(1);
               if (heldItem.func_190926_b()) {
                  player.func_184611_a(hand, equivalentDrink);
               } else if (!player.field_71071_by.func_70441_a(equivalentDrink)) {
                  player.func_71019_a(equivalentDrink, false);
               }
            }

            vessel.setEmpty();
            world.func_184133_a(player, pos, LOTRSoundEvents.MUG_FILL, SoundCategory.BLOCKS, 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
            return ActionResultType.SUCCESS;
         }

         if (vessel.isEmpty() && VesselOperations.isItemFullVessel(heldItem)) {
            if (!player.field_71075_bZ.field_75098_d) {
               equivalentDrink = VesselOperations.getVessel(heldItem).createEmpty();
               player.func_184611_a(hand, equivalentDrink);
            }

            equivalentDrink = heldItem.func_77946_l();
            equivalentDrink.func_190920_e(1);
            vessel.setVesselItem(equivalentDrink);
            world.func_184133_a(player, pos, LOTRSoundEvents.MUG_FILL, SoundCategory.BLOCKS, 0.5F, 0.8F + world.field_73012_v.nextFloat() * 0.4F);
            return ActionResultType.SUCCESS;
         }

         if (!vessel.isEmpty()) {
            equivalentDrink = VesselOperations.getEquivalentDrink(itemInVessel);
            Item eqItem = equivalentDrink.func_77973_b();
            boolean canDrink = false;
            if (eqItem instanceof VesselDrinkItem) {
               canDrink = ((VesselDrinkItem)eqItem).canBeginDrinking(player, equivalentDrink);
            }

            if (canDrink) {
               ItemStack mugItemResult = itemInVessel.func_77950_b(world, player);
               ForgeEventFactory.onItemUseFinish(player, itemInVessel, itemInVessel.func_77988_m(), mugItemResult);
               vessel.setEmpty();
               world.func_184133_a(player, pos, SoundEvents.field_187664_bz, SoundCategory.BLOCKS, 0.5F, world.field_73012_v.nextFloat() * 0.1F + 0.9F);
               return ActionResultType.SUCCESS;
            }
         }
      }

      return super.func_225533_a_(state, world, pos, player, hand, trace);
   }

   public static ItemStack getVesselDrinkItem(IWorld world, BlockPos pos) {
      TileEntity te = world.func_175625_s(pos);
      if (te instanceof VesselDrinkTileEntity) {
         VesselDrinkTileEntity vessel = (VesselDrinkTileEntity)te;
         return vessel.getVesselItem();
      } else {
         return new ItemStack((IItemProvider)LOTRItems.WOODEN_MUG.get());
      }
   }

   public static void setVesselDrinkItem(IWorld world, BlockPos pos, ItemStack itemstack, VesselType vesselType) {
      TileEntity te = world.func_175625_s(pos);
      if (te instanceof VesselDrinkTileEntity) {
         VesselDrinkTileEntity vessel = (VesselDrinkTileEntity)te;
         vessel.setVesselItem(itemstack);
         vessel.setVesselType(vesselType);
      }

   }

   public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
      TileEntity te = world.func_175625_s(pos);
      if (te instanceof VesselDrinkTileEntity) {
         VesselDrinkTileEntity vessel = (VesselDrinkTileEntity)te;
         if (!vessel.isEmpty()) {
            ItemStack copy = vessel.getVesselItem().func_77946_l();
            copy.func_190920_e(1);
            return copy;
         }
      }

      return super.getPickBlock(state, target, world, pos, player);
   }

   static {
      FACING = HorizontalBlock.field_185512_D;
   }
}
