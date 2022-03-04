package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemGandalfStaffGrey extends LOTRItemSword implements LOTRStoryItem {
   public LOTRItemGandalfStaffGrey() {
      super(ToolMaterial.WOOD);
      this.func_77656_e(1000);
      this.func_77637_a(LOTRCreativeTabs.tabStory);
      this.lotrWeaponDamage = 4.0F;
   }
}
