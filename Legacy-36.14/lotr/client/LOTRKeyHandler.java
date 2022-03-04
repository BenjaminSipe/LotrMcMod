package lotr.client;

import com.google.common.math.IntMath;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.InputEvent.MouseInputEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRFaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRKeyHandler {
   public static KeyBinding keyBindingMenu = new KeyBinding("Menu", 38, "LOTR");
   public static KeyBinding keyBindingMapTeleport = new KeyBinding("Map Teleport", 50, "LOTR");
   public static KeyBinding keyBindingFastTravel = new KeyBinding("Fast Travel", 33, "LOTR");
   public static KeyBinding keyBindingAlignmentCycleLeft = new KeyBinding("Alignment Cycle Left", 203, "LOTR");
   public static KeyBinding keyBindingAlignmentCycleRight = new KeyBinding("Alignment Cycle Right", 205, "LOTR");
   public static KeyBinding keyBindingAlignmentGroupPrev = new KeyBinding("Alignment Group Prev", 200, "LOTR");
   public static KeyBinding keyBindingAlignmentGroupNext = new KeyBinding("Alignment Group Next", 208, "LOTR");
   private static Minecraft mc = Minecraft.func_71410_x();
   private static int alignmentChangeTick;
   private static final int alignmentChangeTime = 2;

   public LOTRKeyHandler() {
      FMLCommonHandler.instance().bus().register(this);
      ClientRegistry.registerKeyBinding(keyBindingMenu);
      ClientRegistry.registerKeyBinding(keyBindingMapTeleport);
      ClientRegistry.registerKeyBinding(keyBindingFastTravel);
      ClientRegistry.registerKeyBinding(keyBindingAlignmentCycleLeft);
      ClientRegistry.registerKeyBinding(keyBindingAlignmentCycleRight);
      ClientRegistry.registerKeyBinding(keyBindingAlignmentGroupPrev);
      ClientRegistry.registerKeyBinding(keyBindingAlignmentGroupNext);
   }

   @SubscribeEvent
   public void MouseInputEvent(MouseInputEvent event) {
      LOTRAttackTiming.doAttackTiming();
   }

   @SubscribeEvent
   public void KeyInputEvent(KeyInputEvent event) {
      LOTRAttackTiming.doAttackTiming();
      if (keyBindingMenu.func_151470_d() && mc.field_71462_r == null) {
         mc.field_71439_g.openGui(LOTRMod.instance, 11, mc.field_71441_e, 0, 0, 0);
      }

      LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)mc.field_71439_g);
      boolean usedAlignmentKeys = false;
      Map lastViewedRegions = new HashMap();
      LOTRDimension currentDimension = LOTRDimension.getCurrentDimensionWithFallback(mc.field_71441_e);
      LOTRFaction currentFaction = pd.getViewingFaction();
      LOTRDimension.DimensionRegion currentRegion = currentFaction.factionRegion;
      List regionList = currentDimension.dimensionRegions;
      List factionList = currentRegion.factionList;
      if (mc.field_71462_r == null && alignmentChangeTick <= 0) {
         int i;
         if (keyBindingAlignmentCycleLeft.func_151470_d()) {
            i = factionList.indexOf(currentFaction);
            --i;
            i = IntMath.mod(i, factionList.size());
            currentFaction = (LOTRFaction)factionList.get(i);
            usedAlignmentKeys = true;
         }

         if (keyBindingAlignmentCycleRight.func_151470_d()) {
            i = factionList.indexOf(currentFaction);
            ++i;
            i = IntMath.mod(i, factionList.size());
            currentFaction = (LOTRFaction)factionList.get(i);
            usedAlignmentKeys = true;
         }

         if (regionList != null && currentRegion != null) {
            if (keyBindingAlignmentGroupPrev.func_151470_d()) {
               pd.setRegionLastViewedFaction(currentRegion, currentFaction);
               lastViewedRegions.put(currentRegion, currentFaction);
               i = regionList.indexOf(currentRegion);
               --i;
               i = IntMath.mod(i, regionList.size());
               currentRegion = (LOTRDimension.DimensionRegion)regionList.get(i);
               factionList = currentRegion.factionList;
               currentFaction = pd.getRegionLastViewedFaction(currentRegion);
               usedAlignmentKeys = true;
            }

            if (keyBindingAlignmentGroupNext.func_151470_d()) {
               pd.setRegionLastViewedFaction(currentRegion, currentFaction);
               lastViewedRegions.put(currentRegion, currentFaction);
               i = regionList.indexOf(currentRegion);
               ++i;
               i = IntMath.mod(i, regionList.size());
               currentRegion = (LOTRDimension.DimensionRegion)regionList.get(i);
               factionList = currentRegion.factionList;
               currentFaction = pd.getRegionLastViewedFaction(currentRegion);
               usedAlignmentKeys = true;
            }
         }
      }

      if (usedAlignmentKeys) {
         LOTRClientProxy.sendClientInfoPacket(currentFaction, lastViewedRegions);
         alignmentChangeTick = 2;
      }

   }

   public static void update() {
      if (alignmentChangeTick > 0) {
         --alignmentChangeTick;
      }

   }
}
