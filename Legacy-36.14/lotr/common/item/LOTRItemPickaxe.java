package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemPickaxe extends ItemPickaxe {
   public LOTRItemPickaxe(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemPickaxe(ToolMaterial material) {
      super(material);
      this.func_77637_a(LOTRCreativeTabs.tabTools);
   }
}
