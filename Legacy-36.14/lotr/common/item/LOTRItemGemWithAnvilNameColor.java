package lotr.common.item;

import net.minecraft.util.EnumChatFormatting;

public class LOTRItemGemWithAnvilNameColor extends LOTRItemGem implements AnvilNameColorProvider {
   private final EnumChatFormatting anvilNameColor;

   public LOTRItemGemWithAnvilNameColor(EnumChatFormatting color) {
      this.anvilNameColor = color;
   }

   public EnumChatFormatting getAnvilNameColor() {
      return this.anvilNameColor;
   }
}
