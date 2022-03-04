package lotr.common.inv;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class KegResultSlot extends Slot {
   public static final ResourceLocation EMPTY_MUG_TEXTURE = new ResourceLocation("lotr", "item/empty_keg_slot_mug");

   public KegResultSlot(IInventory inv, int i, int j, int k) {
      super(inv, i, j, k);
      this.setBackground(PlayerContainer.field_226615_c_, EMPTY_MUG_TEXTURE);
   }

   public boolean func_75214_a(ItemStack stack) {
      return false;
   }

   public boolean func_82869_a(PlayerEntity entityplayer) {
      return false;
   }
}
