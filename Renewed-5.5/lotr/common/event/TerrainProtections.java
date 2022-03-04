package lotr.common.event;

import lotr.common.item.RedBookItem;
import lotr.common.world.map.CustomWaypointStructureHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.HangingEntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public class TerrainProtections {
   public static boolean isTerrainProtectedFromPlayerEdits(PlayerEvent event, ItemStack heldItem, BlockPos pos, Direction side) {
      PlayerEntity player = event.getPlayer();
      World world = player.field_70170_p;
      if (!world.field_72995_K && !(heldItem.func_77973_b() instanceof RedBookItem)) {
         BlockPos editPos = getEditingPos(event, pos, side, heldItem);
         if (CustomWaypointStructureHandler.INSTANCE.isProtectedByWaypointStructure(world, editPos, player)) {
            return true;
         }
      }

      return false;
   }

   private static BlockPos getEditingPos(PlayerEvent event, BlockPos pos, Direction side, ItemStack heldItem) {
      boolean isOffsetPos = isOffsetPosClick(event, heldItem);
      return isOffsetPos && side != null ? pos.func_177972_a(side) : pos;
   }

   private static boolean isOffsetPosClick(PlayerEvent event, ItemStack heldItem) {
      if (!(event instanceof RightClickBlock) && !(event instanceof FillBucketEvent)) {
         return false;
      } else {
         Item item = heldItem.func_77973_b();
         return item instanceof BlockItem || item instanceof FlintAndSteelItem || item instanceof BucketItem && ((BucketItem)item).getFluid() != Fluids.field_204541_a || item instanceof HangingEntityItem;
      }
   }

   public static boolean isTerrainProtectedFromExplosion(World world, BlockPos pos) {
      return CustomWaypointStructureHandler.INSTANCE.isProtectedByWaypointStructure(world, pos);
   }
}
