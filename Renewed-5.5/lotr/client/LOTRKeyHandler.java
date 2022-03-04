package lotr.client;

import com.google.common.collect.ImmutableMap;
import com.google.common.math.IntMath;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lotr.client.gui.MiddleEarthMasterMenuScreen;
import lotr.common.data.AlignmentDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionRegion;
import lotr.common.fac.FactionSettings;
import lotr.common.fac.FactionSettingsManager;
import lotr.common.init.LOTRDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.RegistryKey;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class LOTRKeyHandler {
   private static final String KEY_CATEGORY_MOD = String.format("key.categories.mod.%s", "lotr");
   public static final KeyBinding KEY_BIND_MENU;
   public static final KeyBinding KEY_BIND_MAP_TELEPORT;
   public static final KeyBinding KEY_BIND_ALIGNMENT_PREVIOUS;
   public static final KeyBinding KEY_BIND_ALIGNMENT_NEXT;
   public static final KeyBinding KEY_BIND_ALIGNMENT_GROUP_PREVIOUS;
   public static final KeyBinding KEY_BIND_ALIGNMENT_GROUP_NEXT;
   private static final Map ALIGNMENT_KEY_ACTIONS;
   private static int alignmentChangeTick;
   private static final int ALIGNMENT_CHANGE_DELAY = 2;

   private static final String keybindName(String name) {
      return String.format("key.%s.%s", "lotr", name);
   }

   public static KeyBinding getFastTravelKey(Minecraft mc) {
      return mc.field_71474_y.field_186718_X;
   }

   public LOTRKeyHandler() {
      MinecraftForge.EVENT_BUS.register(this);
      this.registerKeys();
   }

   private void registerKeys() {
      ClientRegistry.registerKeyBinding(KEY_BIND_MENU);
      ClientRegistry.registerKeyBinding(KEY_BIND_MAP_TELEPORT);
      ClientRegistry.registerKeyBinding(KEY_BIND_ALIGNMENT_PREVIOUS);
      ClientRegistry.registerKeyBinding(KEY_BIND_ALIGNMENT_NEXT);
      ClientRegistry.registerKeyBinding(KEY_BIND_ALIGNMENT_GROUP_PREVIOUS);
      ClientRegistry.registerKeyBinding(KEY_BIND_ALIGNMENT_GROUP_NEXT);
   }

   @SubscribeEvent
   public void onKeyInput(KeyInputEvent event) {
      int key = event.getKey();
      int scancode = event.getScanCode();
      int action = event.getAction();
      Minecraft mc = Minecraft.func_71410_x();
      if (KEY_BIND_MENU.func_197976_a(key, scancode) && mc.field_71462_r == null && mc.field_71439_g != null) {
         Screen menuScreen = MiddleEarthMasterMenuScreen.openMenu(mc.field_71439_g);
         if (menuScreen != null) {
            mc.func_147108_a(menuScreen);
         }
      }

      if (mc.field_71462_r == null && mc.field_71439_g != null && (action == 1 || action == 2) && alignmentChangeTick <= 0) {
         Optional optAlignmentAction = ALIGNMENT_KEY_ACTIONS.entrySet().stream().filter((e) -> {
            return ((KeyBinding)e.getKey()).func_197976_a(key, scancode);
         }).map((e) -> {
            return (LOTRKeyHandler.AlignmentKeyAction)e.getValue();
         }).findFirst();
         optAlignmentAction.ifPresent((alignmentAction) -> {
            FactionSettings factionSettings = FactionSettingsManager.clientInstance().getCurrentLoadedFactions();
            AlignmentDataModule alignData = LOTRLevelData.clientInstance().getData(mc.field_71439_g).getAlignmentData();
            boolean changedAlignmentView = false;
            int factionShift = alignmentAction.factionShift;
            int groupShift = alignmentAction.groupShift;
            RegistryKey currentDimension = LOTRDimensions.getCurrentLOTRDimensionOrFallback(mc.field_71441_e);
            Faction currentFaction = alignData.getCurrentViewedFaction();
            if (currentFaction != null) {
               FactionRegion currentRegion = currentFaction.getRegion();
               List regionList = factionSettings.getRegionsForDimension(currentDimension);
               List factionList = factionSettings.getFactionsForRegion(currentRegion);
               int i;
               if (factionShift != 0) {
                  i = factionList.indexOf(currentFaction);
                  i += factionShift;
                  i = IntMath.mod(i, factionList.size());
                  alignData.setCurrentViewedFaction((Faction)factionList.get(i));
                  changedAlignmentView = true;
               }

               if (groupShift != 0 && regionList != null && currentRegion != null) {
                  alignData.setRegionLastViewedFaction(currentRegion, currentFaction);
                  i = regionList.indexOf(currentRegion);
                  i += groupShift;
                  i = IntMath.mod(i, regionList.size());
                  alignData.setCurrentViewedFaction(alignData.getRegionLastViewedFaction((FactionRegion)regionList.get(i)));
                  changedAlignmentView = true;
               }
            }

            if (changedAlignmentView) {
               alignData.sendViewedFactionsToServer();
               alignmentChangeTick = 2;
            }

         });
      }

   }

   public static void updateAlignmentChange() {
      if (alignmentChangeTick > 0) {
         --alignmentChangeTick;
      }

   }

   static {
      KEY_BIND_MENU = new KeyBinding(keybindName("menu"), 77, KEY_CATEGORY_MOD);
      KEY_BIND_MAP_TELEPORT = new KeyBinding(keybindName("map_teleport"), 257, KEY_CATEGORY_MOD);
      KEY_BIND_ALIGNMENT_PREVIOUS = new KeyBinding(keybindName("alignment_previous"), 263, KEY_CATEGORY_MOD);
      KEY_BIND_ALIGNMENT_NEXT = new KeyBinding(keybindName("alignment_next"), 262, KEY_CATEGORY_MOD);
      KEY_BIND_ALIGNMENT_GROUP_PREVIOUS = new KeyBinding(keybindName("alignment_group_previous"), 265, KEY_CATEGORY_MOD);
      KEY_BIND_ALIGNMENT_GROUP_NEXT = new KeyBinding(keybindName("alignment_group_next"), 264, KEY_CATEGORY_MOD);
      ALIGNMENT_KEY_ACTIONS = ImmutableMap.of(KEY_BIND_ALIGNMENT_PREVIOUS, new LOTRKeyHandler.AlignmentKeyAction(-1, 0), KEY_BIND_ALIGNMENT_NEXT, new LOTRKeyHandler.AlignmentKeyAction(1, 0), KEY_BIND_ALIGNMENT_GROUP_PREVIOUS, new LOTRKeyHandler.AlignmentKeyAction(0, -1), KEY_BIND_ALIGNMENT_GROUP_NEXT, new LOTRKeyHandler.AlignmentKeyAction(0, 1));
   }

   private static final class AlignmentKeyAction {
      public final int factionShift;
      public final int groupShift;

      private AlignmentKeyAction(int faction, int group) {
         this.factionShift = faction;
         this.groupShift = group;
      }

      // $FF: synthetic method
      AlignmentKeyAction(int x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
