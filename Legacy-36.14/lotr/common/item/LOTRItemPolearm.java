package lotr.common.item;

import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemPolearm extends LOTRItemSword {
   public LOTRItemPolearm(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemPolearm(ToolMaterial material) {
      super(material);
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.none;
   }
}
