package lotr.common.block;

import lotr.common.init.LOTRTileEntities;
import lotr.common.tileentity.PlateTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class PlateBlock extends Block {
   private static final VoxelShape PLATE_SHAPE = Block.func_208617_a(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D);

   public PlateBlock(Properties properties) {
      super(properties);
   }

   public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
      return PLATE_SHAPE;
   }

   public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
      return Block.func_220055_a(world, pos.func_177977_b(), Direction.UP);
   }

   public BlockState func_196271_a(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
      return facing == Direction.DOWN && !state.func_196955_c(world, currentPos) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(state, facing, facingState, world, currentPos, facingPos);
   }

   public boolean hasTileEntity(BlockState state) {
      return true;
   }

   public TileEntity createTileEntity(BlockState state, IBlockReader world) {
      return ((TileEntityType)LOTRTileEntities.PLATE.get()).func_200968_a();
   }

   public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
      ItemStack heldItem = player.func_184614_ca();
      TileEntity te = world.func_175625_s(pos);
      if (te instanceof PlateTileEntity) {
         PlateTileEntity plate = (PlateTileEntity)te;
         ItemStack plateItem = plate.getFoodItem();
         SoundType sound;
         if (plateItem.func_190926_b() && PlateTileEntity.isValidFoodItem(heldItem)) {
            if (!world.field_72995_K) {
               plateItem = heldItem.func_77946_l();
               plateItem.func_190920_e(1);
               plate.setFoodItem(plateItem);
            }

            if (!player.field_71075_bZ.field_75098_d) {
               heldItem.func_190918_g(1);
            }

            sound = state.getSoundType(world, pos, player);
            world.func_184133_a(player, pos, sound.func_185841_e(), SoundCategory.BLOCKS, (sound.func_185843_a() + 1.0F) / 4.0F, sound.func_185847_b() * 1.0F);
            return ActionResultType.SUCCESS;
         }

         if (!plateItem.func_190926_b()) {
            if (heldItem.func_77969_a(plateItem) && ItemStack.func_77970_a(heldItem, plateItem) && plateItem.func_190916_E() < PlateTileEntity.getMaxStackSizeOnPlate(plateItem)) {
               if (!world.field_72995_K) {
                  plateItem.func_190917_f(1);
                  plate.setFoodItem(plateItem);
               }

               if (!player.field_71075_bZ.field_75098_d) {
                  heldItem.func_190918_g(1);
               }

               sound = state.getSoundType(world, pos, player);
               world.func_184133_a(player, pos, sound.func_185841_e(), SoundCategory.BLOCKS, (sound.func_185843_a() + 1.0F) / 4.0F, sound.func_185847_b() * 1.0F);
               return ActionResultType.SUCCESS;
            }

            if (!plateItem.func_222117_E()) {
               if (!world.field_72995_K) {
                  this.popOffOneItem(plate, world, pos, player);
               }

               return ActionResultType.SUCCESS;
            }

            if (player.func_71043_e(false)) {
               ItemStack onEaten = plateItem.func_77950_b(world, player);
               if (!world.field_72995_K) {
                  plate.setFoodItem(onEaten);
               }

               return ActionResultType.SUCCESS;
            }
         }
      }

      return super.func_225533_a_(state, world, pos, player, hand, trace);
   }

   public boolean popOffOneItem(World world, BlockPos pos, PlayerEntity player) {
      TileEntity te = world.func_175625_s(pos);
      if (te instanceof PlateTileEntity) {
         PlateTileEntity plate = (PlateTileEntity)te;
         return this.popOffOneItem(plate, world, pos, player);
      } else {
         return false;
      }
   }

   private boolean popOffOneItem(PlateTileEntity plate, World world, BlockPos pos, PlayerEntity player) {
      ItemStack plateItem = plate.getFoodItem();
      if (!plateItem.func_190926_b()) {
         ItemStack dropItem = plateItem.func_77946_l();
         dropItem.func_190920_e(1);
         InventoryHelper.func_180173_a(world, (double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), dropItem);
         plateItem.func_190918_g(1);
         plate.setFoodItem(plateItem);
         return true;
      } else {
         return false;
      }
   }

   public void func_196243_a(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.func_177230_c() != newState.func_177230_c()) {
         TileEntity te = world.func_175625_s(pos);
         if (te instanceof PlateTileEntity) {
            PlateTileEntity plate = (PlateTileEntity)te;
            InventoryHelper.func_180173_a(world, (double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), plate.getFoodItem());
            world.func_175666_e(pos, this);
         }

         super.func_196243_a(state, world, pos, newState, isMoving);
      }

   }

   public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
      PlateTileEntity plate = (PlateTileEntity)world.func_175625_s(pos);
      ItemStack foodItem = plate.getFoodItem();
      if (!foodItem.func_190926_b()) {
         ItemStack copy = foodItem.func_77946_l();
         copy.func_190920_e(1);
         return copy;
      } else {
         return super.getPickBlock(state, target, world, pos, player);
      }
   }
}
