package lotr.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class PlayerInventorySlotsHelper {
   private static final int OFFHAND_SLOT_INDEX = 40;

   public static int getHandHeldItemIndex(PlayerEntity player, Hand hand) {
      return hand == Hand.MAIN_HAND ? player.field_71071_by.field_70461_c : 40;
   }
}
