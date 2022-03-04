package lotr.client.event;

import lotr.client.gui.WorldTypeHelpScreen;
import lotr.common.config.LOTRConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Post;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldTypeHelpDisplay {
   public WorldTypeHelpDisplay() {
      MinecraftForge.EVENT_BUS.register(this);
   }

   private boolean shouldDisplay(Screen currentScreen) {
      return currentScreen instanceof CreateWorldScreen && (Boolean)LOTRConfig.CLIENT.showWorldTypeHelp.get();
   }

   @SubscribeEvent
   public void onGuiInit(Post event) {
      Screen screen = event.getGui();
      if (this.shouldDisplay(screen)) {
         CreateWorldScreen cws = (CreateWorldScreen)screen;
         Minecraft.func_71410_x().func_147108_a(new WorldTypeHelpScreen(cws));
      }

   }
}
