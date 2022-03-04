package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemAxe extends ItemAxe {
   public LOTRItemAxe(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemAxe(ToolMaterial material) {
      super(material);
      this.func_77637_a(LOTRCreativeTabs.tabTools);
      this.setHarvestLevel("axe", material.func_77996_d());
   }
}
