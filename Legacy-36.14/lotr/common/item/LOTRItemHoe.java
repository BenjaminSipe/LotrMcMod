package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemHoe extends ItemHoe {
   public LOTRItemHoe(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemHoe(ToolMaterial material) {
      super(material);
      this.func_77637_a(LOTRCreativeTabs.tabTools);
   }
}
