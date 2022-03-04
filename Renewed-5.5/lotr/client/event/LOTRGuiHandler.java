package lotr.client.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lotr.client.gui.LOTRMainMenuScreen;
import lotr.client.gui.inv.KegScreen;
import lotr.client.gui.inv.PouchScreen;
import lotr.client.gui.widget.button.PouchRestockButton;
import lotr.common.config.LOTRConfig;
import lotr.common.inv.ShulkerBoxContainerFix;
import lotr.common.network.CPacketRestockPouches;
import lotr.common.network.LOTRPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.AnvilScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.ShulkerBoxScreen;
import net.minecraft.inventory.container.ShulkerBoxContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent.Post;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.tuple.Pair;

public class LOTRGuiHandler {
   public static final Map pouchRestockPositionerByScreen = new HashMap();
   public static final Set pouchRestockExclusionScreens = new HashSet();
   public static final LOTRGuiHandler.PouchRestockButtonPositioner DEFAULT_ABOVE_TOP_RIGHT_SLOT = (topLeftPlayerSlot, topRightPlayerSlot) -> {
      return Pair.of(topRightPlayerSlot.field_75223_e + 7, topRightPlayerSlot.field_75221_f - 14);
   };
   public static final LOTRGuiHandler.PouchRestockButtonPositioner RIGHT_FROM_TOP_RIGHT_SLOT = (topLeftPlayerSlot, topRightPlayerSlot) -> {
      return Pair.of(topRightPlayerSlot.field_75223_e + 21, topRightPlayerSlot.field_75221_f - 1);
   };
   public static final LOTRGuiHandler.PouchRestockButtonPositioner ABOVE_TOP_LEFT_SLOT = (topLeftPlayerSlot, topRightPlayerSlot) -> {
      return Pair.of(topLeftPlayerSlot.field_75223_e - 1, topLeftPlayerSlot.field_75221_f - 14);
   };
   private final Minecraft MC;

   public LOTRGuiHandler(Minecraft mc) {
      this.MC = mc;
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onGuiOpen(GuiOpenEvent event) {
      Screen gui = event.getGui();
      if ((Boolean)LOTRConfig.CLIENT.modMainMenu.get() && gui != null && gui.getClass() == MainMenuScreen.class) {
         gui = new LOTRMainMenuScreen();
         event.setGui((Screen)gui);
      }

      if (gui instanceof ShulkerBoxScreen) {
         ShulkerBoxContainerFix.fixContainerSlots((ShulkerBoxContainer)((ShulkerBoxScreen)gui).func_212873_a_(), this.MC.field_71439_g);
      }

   }

   @SubscribeEvent
   public void postInitGui(Post event) {
      Screen gui = event.getGui();
      PouchRestockButton restockButton = this.constructPouchRestockButton(gui);
      if (restockButton != null) {
         event.addWidget(restockButton);
      }

   }

   private PouchRestockButton constructPouchRestockButton(Screen gui) {
      if (gui instanceof ContainerScreen && !pouchRestockExclusionScreens.contains(gui.getClass())) {
         ContainerScreen containerScreen = (ContainerScreen)gui;
         LOTRGuiHandler.PouchRestockButtonPositioner positioner = (LOTRGuiHandler.PouchRestockButtonPositioner)pouchRestockPositionerByScreen.getOrDefault(gui.getClass(), DEFAULT_ABOVE_TOP_RIGHT_SLOT);
         Optional optButtonCoords = PouchRestockButton.getRestockButtonPosition(this.MC, containerScreen, positioner);
         if (optButtonCoords.isPresent()) {
            Pair buttonCoords = (Pair)optButtonCoords.get();
            int buttonX = (Integer)buttonCoords.getLeft();
            int buttonY = (Integer)buttonCoords.getRight();
            return new PouchRestockButton(containerScreen, buttonX, buttonY, positioner, (b) -> {
               LOTRPacketHandler.sendToServer(new CPacketRestockPouches());
            });
         }
      }

      return null;
   }

   static {
      pouchRestockPositionerByScreen.put(AnvilScreen.class, (topLeftPlayerSlot, topRightPlayerSlot) -> {
         return Pair.of(topRightPlayerSlot.field_75223_e + 25, topRightPlayerSlot.field_75221_f - 1);
      });
      pouchRestockPositionerByScreen.put(KegScreen.class, RIGHT_FROM_TOP_RIGHT_SLOT);
      pouchRestockExclusionScreens.add(PouchScreen.class);
   }

   @FunctionalInterface
   public interface PouchRestockButtonPositioner {
      Pair getButtonPosition(Slot var1, Slot var2);
   }
}
