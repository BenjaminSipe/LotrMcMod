package lotr.common.world.map;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.LOTRLog;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketMapSettings;
import lotr.common.resources.InstancedJsonReloadListener;
import lotr.common.resources.PostServerLoadedValidator;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;

public class MapSettingsManager extends InstancedJsonReloadListener implements PostServerLoadedValidator {
   private static final String MAP_FOLDER = "map";
   private static final String BIOME_COLORS_SUBFOLDER = "biomecolors/";
   private static final String WAYPOINT_REGION_SUBFOLDER = "waypoints/regions/";
   private static final String WAYPOINT_SUBFOLDER = "waypoints/";
   private static final String ROAD_SUBFOLDER = "roads/";
   private static final String LABEL_SUBFOLDER = "labels/";
   private static final MapSettingsManager CLIENT_INSTANCE;
   private static final MapSettingsManager SERVER_INSTANCE;
   private boolean loadedDefaultMap = false;
   private MapSettings defaultMap;
   private MapSettings currentLoadedMap;

   private MapSettingsManager(LogicalSide side) {
      super("map", "MapSettings", side);
   }

   public static MapSettingsManager clientInstance() {
      return CLIENT_INSTANCE;
   }

   public static MapSettingsManager serverInstance() {
      return SERVER_INSTANCE;
   }

   public static MapSettingsManager sidedInstance(IWorldReader world) {
      return !world.func_201670_d() ? SERVER_INSTANCE : CLIENT_INSTANCE;
   }

   public static MapSettingsManager sidedInstance(LogicalSide side) {
      return side == LogicalSide.SERVER ? SERVER_INSTANCE : CLIENT_INSTANCE;
   }

   public MapSettings getLoadedMapOrLoadDefault(IResourceManager resMgr) {
      this.loadDefaultMapIfNotLoaded(resMgr);
      return this.getCurrentLoadedMap();
   }

   private void loadDefaultMapIfNotLoaded(IResourceManager resMgr) {
      if (!this.loadedDefaultMap) {
         JsonObject mapJson = this.loadDefaultJson(MapSettings.MAP_SETTINGS_PATH);
         JsonObject waterJson = this.loadDefaultJson(BothWaterLatitudeSettings.WATER_SETTINGS_PATH);
         JsonObject nlJson = this.loadDefaultJson(NorthernLightsSettings.NORTHERN_LIGHTS_SETTINGS_PATH);
         Map biomeColorsJsons = this.loadDefaultJsonsInSubFolder("biomecolors/", 3);
         Map wpRegionJsons = this.asMapOfSingletonLists(this.loadDefaultJsonsInSubFolder("waypoints/regions/", 4));
         Map wpJsons = this.loadDefaultJsonsInSubFolder("waypoints/", 3);
         JsonObject menuWpRouteJson = this.loadDefaultJson(MapSettings.MENU_WAYPOINT_ROUTE_PATH);
         Map roadJsons = this.loadDefaultJsonsInSubFolder("roads/", 3);
         Map labelJsons = this.loadDefaultJsonsInSubFolder("labels/", 3);
         this.defaultMap = this.loadMapFromJson(resMgr, mapJson, false, waterJson, nlJson, biomeColorsJsons, wpRegionJsons, wpJsons, menuWpRouteJson, roadJsons, labelJsons);
         this.loadedDefaultMap = true;
         this.logMapLoad("Loaded default map settings", this.defaultMap);
      }

   }

   public void loadClientMapFromServer(IResourceManager resMgr, MapSettings mapSettings) {
      if (mapSettings.isDefaultImage()) {
         this.loadDefaultMapIfNotLoaded(resMgr);
         mapSettings.copyImageBytesFrom(this.defaultMap);
      }

      mapSettings.loadImage(resMgr);
      this.currentLoadedMap = mapSettings;
      this.logMapLoad("Loaded clientside map settings from server", this.currentLoadedMap);
   }

   private MapSettings loadMapFromJson(IResourceManager resMgr, JsonObject mapJson, boolean loadBiomeIds, JsonObject waterJson, JsonObject nlJson, Map biomeColorsJsons, Map waypointRegionJsons, Map waypointJsons, JsonObject menuWpRouteJson, Map roadJsons, Map labelJsons) {
      MapSettings mapSettings = MapSettings.read(this, mapJson);
      mapSettings.loadImage(resMgr);
      BothWaterLatitudeSettings waterLatitudes = BothWaterLatitudeSettings.read(mapSettings, waterJson);
      mapSettings.setWaterLatitudes(waterLatitudes);
      NorthernLightsSettings northernLightsSettings = NorthernLightsSettings.read(mapSettings, nlJson);
      mapSettings.setNorthernLights(northernLightsSettings);
      List biomeColorTables = new ArrayList();
      Iterator var16 = biomeColorsJsons.entrySet().iterator();

      while(var16.hasNext()) {
         Entry entry = (Entry)var16.next();
         ResourceLocation res = (ResourceLocation)entry.getKey();
         ResourceLocation colorTableName = trimSubFolderResource(res, "biomecolors/");
         JsonObject colorTableJson = (JsonObject)entry.getValue();

         try {
            BiomeMapColorTable colorTable = BiomeMapColorTable.read(colorTableName, colorTableJson);
            if (colorTable != null) {
               biomeColorTables.add(colorTable);
            }
         } catch (Exception var33) {
            LOTRLog.warn("Failed to load biome colors table %s from file", colorTableName);
            var33.printStackTrace();
         }
      }

      BiomeMapColorTable combinedBiomeColors = BiomeMapColorTable.combine(biomeColorTables);
      mapSettings.setBiomeColorTable(combinedBiomeColors);
      List waypointRegions = new ArrayList();
      int nextWaypointRegionId = 0;
      Iterator var37 = waypointRegionJsons.entrySet().iterator();

      while(var37.hasNext()) {
         Entry entry = (Entry)var37.next();
         ResourceLocation res = (ResourceLocation)entry.getKey();
         ResourceLocation regionName = trimSubFolderResource(res, "waypoints/regions/");
         List regionJsonVersions = (List)entry.getValue();

         try {
            WaypointRegion region = WaypointRegion.readCombined(mapSettings, regionName, regionJsonVersions, nextWaypointRegionId);
            if (region != null) {
               waypointRegions.add(region);
            }

            ++nextWaypointRegionId;
         } catch (Exception var32) {
            LOTRLog.warn("Failed to load waypoint region %s from file(s) (%d versions in loaded datapacks)", regionName, regionJsonVersions.size());
            var32.printStackTrace();
         }
      }

      mapSettings.setWaypointRegions(waypointRegions);
      List waypoints = new ArrayList();
      int nextWaypointId = 0;
      Iterator var42 = waypointJsons.entrySet().iterator();

      ResourceLocation res;
      while(var42.hasNext()) {
         Entry entry = (Entry)var42.next();
         ResourceLocation res = (ResourceLocation)entry.getKey();
         res = trimSubFolderResource(res, "waypoints/");
         JsonObject wpJson = (JsonObject)entry.getValue();

         try {
            MapWaypoint waypoint = MapWaypoint.read(mapSettings, res, wpJson, nextWaypointId);
            if (waypoint != null) {
               waypoints.add(waypoint);
            }

            ++nextWaypointId;
         } catch (Exception var31) {
            LOTRLog.warn("Failed to load map waypoint %s from file", res);
            var31.printStackTrace();
         }
      }

      mapSettings.setWaypoints(waypoints);
      mapSettings.readMenuWaypointRoute(menuWpRouteJson);
      List roads = new ArrayList();
      Iterator var45 = roadJsons.entrySet().iterator();

      ResourceLocation res;
      while(var45.hasNext()) {
         Entry entry = (Entry)var45.next();
         res = (ResourceLocation)entry.getKey();
         res = trimSubFolderResource(res, "roads/");
         JsonObject roadJson = (JsonObject)entry.getValue();

         try {
            Road road = Road.read(mapSettings, res, roadJson);
            if (road != null) {
               roads.add(road);
            }
         } catch (Exception var30) {
            LOTRLog.warn("Failed to load map road %s from file", res);
            var30.printStackTrace();
         }
      }

      mapSettings.setRoadsAndGenerateCurvesInstantly(roads);
      List labels = new ArrayList();
      Iterator var49 = labelJsons.entrySet().iterator();

      while(var49.hasNext()) {
         Entry entry = (Entry)var49.next();
         res = (ResourceLocation)entry.getKey();
         ResourceLocation labelName = trimSubFolderResource(res, "labels/");
         JsonObject labelJson = (JsonObject)entry.getValue();

         try {
            MapLabel label = MapLabel.read(mapSettings, labelName, labelJson);
            if (label != null) {
               labels.add(label);
            }
         } catch (Exception var29) {
            LOTRLog.warn("Failed to load map label %s from file", labelName);
            var29.printStackTrace();
         }
      }

      mapSettings.setLabels(labels);
      return mapSettings;
   }

   public MapSettings getCurrentLoadedMap() {
      return this.currentLoadedMap != null ? this.currentLoadedMap : this.defaultMap;
   }

   protected void apply(Map jsons, IResourceManager serverResMgr, IProfiler profiler) {
      JsonObject mapJson = this.loadDataJsonIfExists(jsons, MapSettings.MAP_SETTINGS_PATH);
      JsonObject waterJson = this.loadDataJsonIfExists(jsons, BothWaterLatitudeSettings.WATER_SETTINGS_PATH);
      JsonObject nlJson = this.loadDataJsonIfExists(jsons, NorthernLightsSettings.NORTHERN_LIGHTS_SETTINGS_PATH);
      JsonObject menuWpRouteJson = this.loadDataJsonIfExists(jsons, MapSettings.MENU_WAYPOINT_ROUTE_PATH);
      if (mapJson != null && waterJson != null && nlJson != null && menuWpRouteJson != null) {
         Map biomeColorsJsons = this.filterDataJsonsBySubFolder(jsons, "biomecolors/");
         Map waypointRegionJsons = this.loadJsonResourceVersionsFromAllDatapacks(this.filterDataJsonsBySubFolder(jsons, "waypoints/regions/").keySet(), serverResMgr);
         Map waypointJsons = this.filterDataJsonsBySubFolder(jsons, "waypoints/");
         Map roadJsons = this.filterDataJsonsBySubFolder(jsons, "roads/");
         Map labelJsons = this.filterDataJsonsBySubFolder(jsons, "labels/");
         this.currentLoadedMap = this.loadMapFromJson(serverResMgr, mapJson, true, waterJson, nlJson, biomeColorsJsons, waypointRegionJsons, waypointJsons, menuWpRouteJson, roadJsons, labelJsons);
         this.logMapLoad("Loaded serverside map settings", this.currentLoadedMap);
      } else {
         LOTRLog.error("Couldn't load serverside map settings - a necessary json file is missing! Which one could it be? Something is broken...");
      }

   }

   private void logMapLoad(String prefix, MapSettings map) {
      LOTRLog.info("%s - image %s, %d biome-color mappings (combined from %d files), %d waypoint regions encompassing %d biomes, %d waypoints, %d roads, %d labels", prefix, map.getMapImagePath(), map.getBiomeColorTable().size(), map.getBiomeColorTable().getNumCombinedFrom(), map.getWaypointRegions().size(), map.getNumBiomesMappedToWaypointRegions(), map.getWaypoints().size(), map.getRoads().size(), map.getLabels().size());
   }

   public void sendMapToPlayer(ServerPlayerEntity player) {
      SPacketMapSettings packet = new SPacketMapSettings(this.currentLoadedMap);
      LOTRPacketHandler.sendTo(packet, player);
   }

   public void performPostServerLoadValidation(World mainWorld) {
      this.currentLoadedMap.postLoadValidateBiomes(mainWorld);
   }

   static {
      CLIENT_INSTANCE = new MapSettingsManager(LogicalSide.CLIENT);
      SERVER_INSTANCE = new MapSettingsManager(LogicalSide.SERVER);
      PostServerLoadedValidator.validators.add(SERVER_INSTANCE);
   }
}
