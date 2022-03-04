package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispenseArrowPoisoned;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

public class LOTRItemArrowPoisoned extends Item {
   public LOTRItemArrowPoisoned() {
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
      BlockDispenser.field_149943_a.func_82595_a(this, new LOTRDispenseArrowPoisoned());
   }
}
