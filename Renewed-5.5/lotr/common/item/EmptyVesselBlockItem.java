package lotr.common.item;

import lotr.common.block.VesselDrinkBlock;
import lotr.common.init.LOTRItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Item.Properties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EmptyVesselBlockItem extends LOTRBlockItem implements IEmptyVesselItem {
   private final VesselType vesselType;

   public EmptyVesselBlockItem(Block block, VesselType ves) {
      super(block, (new Properties()).func_200916_a(LOTRItemGroups.FOOD));
      this.vesselType = ves;
   }

   public VesselType getVesselType() {
      return this.vesselType;
   }

   public ActionResult func_77659_a(World world, PlayerEntity player, Hand hand) {
      return this.doEmptyVesselRightClick(world, player, hand);
   }

   public ActionResultType func_195939_a(ItemUseContext context) {
      return this.tryToPlaceVesselBlock(context);
   }

   public ActionResultType tryToPlaceVesselBlock(ItemUseContext context) {
      return super.func_195939_a(context);
   }

   protected boolean func_195941_b(BlockItemUseContext context, BlockState state) {
      World world = context.func_195991_k();
      BlockPos pos = context.func_195995_a();
      if (world.func_204610_c(pos).func_206884_a(FluidTags.field_206959_a) && !context.func_195999_j().func_226563_dT_()) {
         return false;
      } else if (super.func_195941_b(context, state)) {
         VesselDrinkBlock.setVesselDrinkItem(world, pos, context.func_195996_i(), this.getVesselType());
         return true;
      } else {
         return false;
      }
   }

   public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
      return this.doEmptyVesselUseOnBlock(context);
   }
}
