package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.enchant.LOTREnchantment;
import net.minecraft.item.Item;

public class LOTRItemEnchantment extends Item {
   public final LOTREnchantment theEnchant;

   public LOTRItemEnchantment(LOTREnchantment ench) {
      this.func_77637_a(LOTRCreativeTabs.tabMaterials);
      this.theEnchant = ench;
   }
}
