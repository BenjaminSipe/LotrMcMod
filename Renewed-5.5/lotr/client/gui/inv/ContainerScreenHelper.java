package lotr.client.gui.inv;

import lotr.common.init.LOTRContainers;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;

public class ContainerScreenHelper {
   public static void registerScreens() {
      ScreenManager.func_216911_a((ContainerType)LOTRContainers.FACTION_CRAFTING.get(), FactionCraftingScreen::new);
      ScreenManager.func_216911_a((ContainerType)LOTRContainers.ALLOY_FORGE.get(), AlloyForgeScreen::new);
      ScreenManager.func_216911_a((ContainerType)LOTRContainers.KEG.get(), KegScreen::new);
      ScreenManager.func_216911_a((ContainerType)LOTRContainers.POUCH.get(), PouchScreen::new);
   }
}
