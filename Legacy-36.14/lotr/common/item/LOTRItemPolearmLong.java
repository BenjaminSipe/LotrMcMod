package lotr.common.item;

import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemPolearmLong extends LOTRItemPolearm {
   public LOTRItemPolearmLong(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemPolearmLong(ToolMaterial material) {
      super(material);
   }
}
