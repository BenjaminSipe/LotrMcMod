package lotr.common.item;

import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemPike extends LOTRItemPolearmLong {
   public LOTRItemPike(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemPike(ToolMaterial material) {
      super(material);
   }
}
