package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class LOTRItemSting extends LOTRItemDagger implements LOTRStoryItem {
   public LOTRItemSting() {
      super(LOTRMaterial.HIGH_ELVEN);
      this.func_77656_e(700);
      this.func_77637_a(LOTRCreativeTabs.tabStory);
      ++this.lotrWeaponDamage;
   }

   public float func_150893_a(ItemStack itemstack, Block block) {
      return block == LOTRMod.webUngoliant ? 15.0F : super.func_150893_a(itemstack, block);
   }
}
