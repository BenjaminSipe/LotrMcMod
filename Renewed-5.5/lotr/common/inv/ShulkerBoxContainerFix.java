package lotr.common.inv;

import java.util.HashMap;
import java.util.Map;
import lotr.common.LOTRLog;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ShulkerBoxContainer;
import net.minecraft.inventory.container.ShulkerBoxSlot;
import net.minecraft.inventory.container.Slot;

public class ShulkerBoxContainerFix {
   public static void fixContainerSlots(ShulkerBoxContainer container, PlayerEntity player) {
      Map replacedSlots = new HashMap();

      for(int i = 0; i < container.field_75151_b.size(); ++i) {
         Slot slot = (Slot)container.field_75151_b.get(i);
         if (slot.getClass() == ShulkerBoxSlot.class) {
            Slot replacedSlot = new FixedShulkerBoxSlot(slot.field_75224_c, slot.getSlotIndex(), slot.field_75223_e, slot.field_75221_f);
            replacedSlot.field_75222_d = slot.field_75222_d;
            replacedSlots.put(i, replacedSlot);
         }
      }

      if (replacedSlots.isEmpty()) {
         LOTRLog.warn("Didn't replace any slots in ShulkerBoxContainer opened by player %s! (isRemote = %s) Expected to replace them. Call hierarchy is:", player, player.field_70170_p.field_72995_K);
         Thread.dumpStack();
      } else {
         replacedSlots.forEach((index, slotx) -> {
            Slot var10000 = (Slot)container.field_75151_b.set(index, slotx);
         });
      }

   }
}
