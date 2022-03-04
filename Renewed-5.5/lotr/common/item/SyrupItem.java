package lotr.common.item;

import net.minecraft.item.SoupItem;
import net.minecraft.item.Item.Properties;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class SyrupItem extends SoupItem {
   public SyrupItem(Properties properties) {
      super(properties);
   }

   public SoundEvent func_225520_U__() {
      return SoundEvents.field_226141_eV_;
   }

   public SoundEvent func_225519_S__() {
      return SoundEvents.field_226141_eV_;
   }
}
