package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemAnduril extends LOTRItemSword implements LOTRStoryItem {
   public LOTRItemAnduril() {
      super(ToolMaterial.IRON);
      this.func_77656_e(1500);
      this.lotrWeaponDamage = 9.0F;
      this.func_77637_a(LOTRCreativeTabs.tabStory);
   }
}
