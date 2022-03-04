package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispenseCrossbowBolt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

public class LOTRItemCrossbowBolt extends Item {
   public boolean isPoisoned = false;

   public LOTRItemCrossbowBolt() {
      this.func_77637_a(LOTRCreativeTabs.tabCombat);
      BlockDispenser.field_149943_a.func_82595_a(this, new LOTRDispenseCrossbowBolt(this));
   }

   public LOTRItemCrossbowBolt setPoisoned() {
      this.isPoisoned = true;
      return this;
   }
}
