package lotr.common.item;

import lotr.common.init.LOTRItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EmptyVesselBlocklessItem extends Item implements IEmptyVesselItem {
   private final VesselType vesselType;

   public EmptyVesselBlocklessItem(VesselType ves) {
      super((new Properties()).func_200916_a(LOTRItemGroups.FOOD));
      this.vesselType = ves;
   }

   public VesselType getVesselType() {
      return this.vesselType;
   }

   public ActionResult func_77659_a(World world, PlayerEntity player, Hand hand) {
      return this.doEmptyVesselRightClick(world, player, hand);
   }

   public ActionResultType func_195939_a(ItemUseContext context) {
      return this.doEmptyVesselUseOnBlock(context);
   }
}
