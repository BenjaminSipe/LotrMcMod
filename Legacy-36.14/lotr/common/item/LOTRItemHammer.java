package lotr.common.item;

import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemHammer extends LOTRItemSword {
   public LOTRItemHammer(LOTRMaterial material) {
      this(material.toToolMaterial());
      this.lotrWeaponDamage += 2.0F;
   }

   public LOTRItemHammer(ToolMaterial material) {
      super(material);
   }

   public EnumAction func_77661_b(ItemStack itemstack) {
      return EnumAction.none;
   }
}
