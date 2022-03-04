package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemShovel extends ItemSpade {
   public LOTRItemShovel(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemShovel(ToolMaterial material) {
      super(material);
      this.func_77637_a(LOTRCreativeTabs.tabTools);
   }
}
