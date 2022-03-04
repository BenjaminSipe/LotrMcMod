package lotr.common.item;

import java.util.UUID;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item.ToolMaterial;

public class LOTRItemLance extends LOTRItemPolearmLong {
   public static final UUID lanceSpeedBoost_id = UUID.fromString("4da96302-7457-42ed-9709-f1be0c465ec3");
   public static final AttributeModifier lanceSpeedBoost;

   public LOTRItemLance(LOTRMaterial material) {
      this(material.toToolMaterial());
   }

   public LOTRItemLance(ToolMaterial material) {
      super(material);
   }

   static {
      lanceSpeedBoost = (new AttributeModifier(lanceSpeedBoost_id, "Lance speed boost", -0.2D, 2)).func_111168_a(false);
   }
}
