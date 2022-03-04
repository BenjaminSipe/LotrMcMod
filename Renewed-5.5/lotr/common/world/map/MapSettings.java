package lotr.common.world.map;

import com.google.common.collect.ImmutableList;
import com.google.common.math.IntMath;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import lotr.common.LOTRLog;
import lotr.common.LOTRMod;
import lotr.common.init.LOTRBiomes;
import lotr.common.util.LOTRUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.LogicalSide;

public class MapSettings {
   public static final ResourceLocation MAP_SETTINGS_PATH = new ResourceLocation("lotr", "map/map.json");
   public static final ResourceLocation DEFAULT_MAP_IMAGE_PATH = new ResourceLocation("lotr", "map/middle_earth.png");
   public static final ResourceLocation MENU_WAYPOINT_ROUTE_PATH = new ResourceLocation("lotr", "map/menu_waypoint_route.json");
   private final MapSettingsManager manager;
   private final ResourceLocation mapImagePath;
   private int imageWidth;
   private int imageHeight;
   private byte[] cachedImageBytes;
   private int[] deferredImageRgb;
   private int[] imageBiomeIds;
   private int fallbackBiomeId;
   private BiomeMapColorTable biomeColorTable;
   private final int originX;
   private final int originZ;
   private final int scalePower;
   private final int scaleFactor;
   private final String title;
   private final boolean translateTitle;
   private final Set lockSides;
   private final boolean proceduralRivers;
   private BothWaterLatitudeSettings waterLatitudes;
   private NorthernLightsSettings northernLights;
   private List waypointRegions;
   private Map waypointRegionsById;
   private Map waypointRegionsByName;
   private Map waypointRegionsForBiome;
   private List waypoints;
   private Map waypointsById;
   private Map waypointsByName;
   private List menuWaypointRoute;
   private List roads;
   private final RoadPointCache roadPointCache = new RoadPointCache();
   private List labels;

   public MapSettings(MapSettingsManager mgr, ResourceLocation map, int origX, int origZ, int scale, String ttl, boolean translate, Set locks, boolean rivers) {
      this.manager = mgr;
      this.mapImagePath = map;
      this.originX = origX;
      this.originZ = origZ;
      this.scalePower = scale;
      this.scaleFactor = IntMath.pow(2, this.scalePower);
      this.title = ttl;
      this.translateTitle = translate;
      this.lockSides = locks;
      this.proceduralRivers = rivers;
   }

   public static MapSettings read(MapSettingsManager manager, JsonObject json) {
      String imagePath = json.get("image").getAsString();
      int originX = json.get("origin_x").getAsInt();
      int originZ = json.get("origin_z").getAsInt();
      int scale = json.get("scale_power").getAsInt();
      int minScale = true;
      int maxScale = true;
      int clampedScale = MathHelper.func_76125_a(scale, 2, 10);
      if (clampedScale != scale) {
         scale = clampedScale;
         LOTRLog.warn("Map scale power must be between %d and %d - clamping value provided in map.json to %d", 2, 10, clampedScale);
      }

      String title = json.get("title").getAsString();
      boolean translateTitle = json.get("translate_title").getAsBoolean();
      Set lockSides = new HashSet();
      json.get("lock_sides").getAsJsonArray().forEach((jsonElem) -> {
         String dirName = jsonElem.getAsString();
         Direction dir = Direction.func_176739_a(dirName);
         if (dir != null && dir.func_176740_k().func_176722_c()) {
            lockSides.add(dir);
         } else {
            LOTRLog.warn("Invalid direction '%s' for map locked sides", dirName);
         }

      });
      boolean proceduralRivers = true;
      if (json.has("procedural_rivers")) {
         proceduralRivers = json.get("procedural_rivers").getAsBoolean();
      }

      return new MapSettings(manager, new ResourceLocation(imagePath), originX, originZ, scale, title, translateTitle, lockSides, proceduralRivers);
   }

   public void readMenuWaypointRoute(JsonObject json) {
      if (this.waypoints == null) {
         LOTRLog.error("Cannot load menu waypoint route - waypoints aren't loaded yet!");
      }

      JsonArray wpList = json.get("waypoint_route").getAsJsonArray();
      this.menuWaypointRoute = new ArrayList();
      Iterator var3 = wpList.iterator();

      while(var3.hasNext()) {
         JsonElement elem = (JsonElement)var3.next();

         try {
            String wpName = elem.getAsString();
            ResourceLocation wpRes = new ResourceLocation(wpName);
            MapWaypoint waypoint = (MapWaypoint)this.waypointsByName.get(wpRes);
            if (waypoint != null) {
               this.menuWaypointRoute.add(waypoint);
            } else {
               LOTRLog.warn("Tried to add a map waypoint to the menu route that doesn't exist for this map - name %s. Check the route list", wpRes);
            }
         } catch (Exception var8) {
            LOTRLog.warn("Invalid array element '%s' in menu waypoint route json. Must be a list of waypoints by their resource location names. See the mod's default file for an example", elem.toString());
            var8.printStackTrace();
         }
      }

   }

   public static MapSettings read(MapSettingsManager manager, PacketBuffer buf) {
      String imagePath = buf.func_218666_n();
      int originX = buf.readInt();
      int originZ = buf.readInt();
      int scale = buf.readByte();
      String title = buf.func_218666_n();
      boolean translateTitle = buf.readBoolean();
      Set lockSides = new HashSet();
      int numLockSides = buf.readByte();

      for(int i = 0; i < numLockSides; ++i) {
         int dirIndex = buf.readByte();
         Direction dir = Direction.func_82600_a(dirIndex);
         if (dir != null && dir.func_176740_k().func_176722_c()) {
            lockSides.add(dir);
         } else {
            LOTRLog.warn("Invalid direction index %d for map locked sides", Integer.valueOf(dirIndex));
         }
      }

      boolean proceduralRivers = buf.readBoolean();
      MapSettings mapSettings = new MapSettings(manager, new ResourceLocation(imagePath), originX, originZ, scale, title, translateTitle, lockSides, proceduralRivers);
      if (!mapSettings.isDefaultImage()) {
         byte[] imgBytes = buf.func_179251_a();
         mapSettings.setImageBytes(imgBytes);
      }

      mapSettings.setBiomeColorTable(BiomeMapColorTable.read(buf));
      mapSettings.setWaterLatitudes(BothWaterLatitudeSettings.read(mapSettings, buf));
      mapSettings.setNorthernLights(NorthernLightsSettings.read(mapSettings, buf));
      List waypointRegions = new ArrayList();
      int numWaypointRegions = buf.readInt();

      for(int i = 0; i < numWaypointRegions; ++i) {
         try {
            WaypointRegion region = WaypointRegion.read(buf);
            waypointRegions.add(region);
         } catch (Exception var27) {
            LOTRLog.warn("Error loading a waypoint region from server");
            var27.printStackTrace();
         }
      }

      mapSettings.setWaypointRegions(waypointRegions);
      List waypoints = new ArrayList();
      int numWaypoints = buf.readInt();

      for(int i = 0; i < numWaypoints; ++i) {
         try {
            MapWaypoint waypoint = MapWaypoint.read(mapSettings, buf);
            waypoints.add(waypoint);
         } catch (Exception var26) {
            LOTRLog.warn("Error loading a map waypoint from server");
            var26.printStackTrace();
         }
      }

      mapSettings.setWaypoints(waypoints);
      List menuWaypointRoute = new ArrayList();
      int numMenuWaypoints = buf.readInt();

      int numRoads;
      for(int i = 0; i < numMenuWaypoints; ++i) {
         numRoads = buf.func_150792_a();
         MapWaypoint waypoint = (MapWaypoint)mapSettings.waypointsById.get(numRoads);
         if (waypoint != null) {
            menuWaypointRoute.add(waypoint);
         } else {
            LOTRLog.error("Tried to add a map waypoint to the menu route that doesn't exist - assigned ID %d. Something has broken!", numRoads);
         }
      }

      mapSettings.menuWaypointRoute = menuWaypointRoute;
      List roads = new ArrayList();
      numRoads = buf.readInt();

      for(int i = 0; i < numRoads; ++i) {
         try {
            Road road = Road.read(mapSettings, buf);
            roads.add(road);
         } catch (Exception var25) {
            LOTRLog.warn("Error loading a map road from server");
            var25.printStackTrace();
         }
      }

      mapSettings.setRoadsAndGenerateCurvesOnThread(roads);
      List labels = new ArrayList();
      int numLabels = buf.readInt();

      for(int i = 0; i < numLabels; ++i) {
         try {
            MapLabel label = MapLabel.read(mapSettings, buf);
            labels.add(label);
         } catch (Exception var24) {
            LOTRLog.warn("Error loading a map label from server");
            var24.printStackTrace();
         }
      }

      mapSettings.setLabels(labels);
      return mapSettings;
   }

   public void write(PacketBuffer buf) {
      buf.func_180714_a(this.mapImagePath.toString());
      buf.writeInt(this.originX);
      buf.writeInt(this.originZ);
      buf.writeByte(this.scalePower);
      buf.func_180714_a(this.title);
      buf.writeBoolean(this.translateTitle);
      buf.writeByte(this.lockSides.size());
      this.lockSides.forEach((dir) -> {
         buf.writeByte(dir.func_176745_a());
      });
      buf.writeBoolean(this.proceduralRivers);
      if (!this.isDefaultImage()) {
         buf.func_179250_a(this.cachedImageBytes);
      }

      this.biomeColorTable.write(buf);
      this.waterLatitudes.write(buf);
      this.northernLights.write(buf);
      buf.writeInt(this.waypointRegions.size());
      this.waypointRegions.forEach((region) -> {
         region.write(buf);
      });
      buf.writeInt(this.waypoints.size());
      this.waypoints.forEach((waypoint) -> {
         waypoint.write(buf);
      });
      buf.writeInt(this.menuWaypointRoute.size());
      this.menuWaypointRoute.forEach((waypoint) -> {
         buf.func_150787_b(waypoint.getAssignedId());
      });
      buf.writeInt(this.roads.size());
      this.roads.forEach((road) -> {
         road.write(buf);
      });
      buf.writeInt(this.labels.size());
      this.labels.forEach((label) -> {
         label.write(buf);
      });
   }

   public LogicalSide getSide() {
      return this.manager.getSide();
   }

   public boolean isDefaultImage() {
      return this.mapImagePath.equals(DEFAULT_MAP_IMAGE_PATH);
   }

   public boolean loadedImage() {
      return this.cachedImageBytes != null && this.imageBiomeIds != null;
   }

   public void loadImage(IResourceManager resMgr) {
      if (!this.loadedImage()) {
         try {
            if (this.cachedImageBytes == null) {
               InputStream is;
               if (this.isDefaultImage()) {
                  is = LOTRMod.getDefaultModResourceStream(ResourcePackType.SERVER_DATA, this.mapImagePath);
               } else {
                  is = resMgr.func_199002_a(this.mapImagePath).func_199027_b();
               }

               this.cachedImageBytes = readInputStreamBytesFully(is);
            }

            BufferedImage biomeImage = ImageIO.read(this.createCachedImageInputStream());
            if (biomeImage == null) {
               throw new RuntimeException("Fatal error: Could not load LOTR biome map image " + this.mapImagePath);
            }

            this.imageWidth = biomeImage.getWidth();
            this.imageHeight = biomeImage.getHeight();
            this.deferredImageRgb = biomeImage.getRGB(0, 0, this.imageWidth, this.imageHeight, (int[])null, 0, this.imageWidth);
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

   }

   private static byte[] readInputStreamBytesFully(InputStream is) throws IOException {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      byte[] data = new byte[16384];

      int nRead;
      while((nRead = is.read(data, 0, data.length)) != -1) {
         buffer.write(data, 0, nRead);
      }

      return buffer.toByteArray();
   }

   public void setImageBytes(byte[] bytes) {
      if (this.cachedImageBytes != null) {
         throw new IllegalArgumentException("Map's cachedImageBytes are already set, cannot replace them!");
      } else {
         this.cachedImageBytes = bytes;
      }
   }

   public void copyImageBytesFrom(MapSettings other) {
      this.setImageBytes(other.cachedImageBytes);
   }

   public InputStream createCachedImageInputStream() {
      return new ByteArrayInputStream(this.cachedImageBytes);
   }

   private void loadImageBiomeIds(IWorld world) {
      if (this.imageBiomeIds != null) {
         throw new IllegalStateException("Cannot load image biome IDs again - already loaded");
      } else if (this.deferredImageRgb == null) {
         throw new IllegalStateException("Cannot load image biome IDs - map image file has not yet been loaded");
      } else {
         int[] mapRgb = this.deferredImageRgb;
         this.deferredImageRgb = null;
         this.imageBiomeIds = new int[this.imageWidth * this.imageHeight];
         this.fallbackBiomeId = LOTRBiomes.getBiomeID(LOTRBiomes.SEA, world);
         Map cachedBiomeIDs = new HashMap();
         Set unknownColorCache = new HashSet();

         for(int i = 0; i < mapRgb.length; ++i) {
            int color = mapRgb[i] & 16777215;
            Integer biomeId = (Integer)cachedBiomeIDs.get(color);
            if (biomeId == null) {
               Biome biome = this.biomeColorTable.getBiome(color, world);
               if (biome != null) {
                  biomeId = LOTRBiomes.getBiomeID(biome, world);
               } else {
                  biomeId = this.fallbackBiomeId;
                  if (!unknownColorCache.contains(color)) {
                     unknownColorCache.add(color);
                     LOTRLog.error("Found unknown biome color on map: %s, substituting sea", LOTRUtil.toPaddedHexString(color));
                  }
               }

               cachedBiomeIDs.put(color, biomeId);
            }

            this.imageBiomeIds[i] = biomeId;
         }

      }
   }

   public int getBiomeIdAt(int mapX, int mapZ, IWorld world) {
      if (this.imageBiomeIds == null) {
         this.loadImageBiomeIds(world);
      }

      if (mapX >= 0 && mapX < this.imageWidth && mapZ >= 0 && mapZ < this.imageHeight) {
         int index = mapZ * this.imageWidth + mapX;
         return this.imageBiomeIds[index];
      } else {
         return this.fallbackBiomeId;
      }
   }

   public ResourceLocation getMapImagePath() {
      return this.mapImagePath;
   }

   public int getWidth() {
      return this.imageWidth;
   }

   public int getHeight() {
      return this.imageHeight;
   }

   public BiomeMapColorTable getBiomeColorTable() {
      return this.biomeColorTable;
   }

   public void setBiomeColorTable(BiomeMapColorTable table) {
      if (this.biomeColorTable != null) {
         throw new IllegalArgumentException("Cannot set map's biome color table - already set!");
      } else {
         this.biomeColorTable = table;
      }
   }

   public int getOriginX() {
      return this.originX;
   }

   public int getOriginZ() {
      return this.originZ;
   }

   public int getScalePower() {
      return this.scalePower;
   }

   public int getScaleFactor() {
      return this.scaleFactor;
   }

   public ITextComponent getTitle() {
      return (ITextComponent)(this.translateTitle ? new TranslationTextComponent(this.title) : new StringTextComponent(this.title));
   }

   public boolean isScreenSideLocked(Direction dir) {
      return this.lockSides.contains(dir);
   }

   public boolean getProceduralRivers() {
      return this.proceduralRivers;
   }

   public int mapToWorldX(double x) {
      return (int)Math.round(this.mapToWorldX_frac(x));
   }

   public int mapToWorldZ(double z) {
      return (int)Math.round(this.mapToWorldZ_frac(z));
   }

   public double mapToWorldX_frac(double x) {
      return (x - (double)this.originX) * (double)this.scaleFactor;
   }

   public double mapToWorldZ_frac(double z) {
      return (z - (double)this.originZ) * (double)this.scaleFactor;
   }

   public int mapToWorldDistance(double dist) {
      return (int)Math.round(dist * (double)this.scaleFactor);
   }

   public int worldToMapX(double x) {
      return (int)Math.round(this.worldToMapX_frac(x));
   }

   public int worldToMapZ(double z) {
      return (int)Math.round(this.worldToMapZ_frac(z));
   }

   public double worldToMapX_frac(double x) {
      return x / (double)this.scaleFactor + (double)this.originX;
   }

   public double worldToMapZ_frac(double z) {
      return z / (double)this.scaleFactor + (double)this.originZ;
   }

   public int worldToMapDistance(double dist) {
      return (int)Math.round(dist / (double)this.scaleFactor);
   }

   public BothWaterLatitudeSettings getWaterLatitudes() {
      return this.waterLatitudes;
   }

   public void setWaterLatitudes(BothWaterLatitudeSettings water) {
      if (this.waterLatitudes != null) {
         throw new IllegalArgumentException("Cannot set map's water latitudes - already set!");
      } else {
         this.waterLatitudes = water;
      }
   }

   public NorthernLightsSettings getNorthernLights() {
      return this.northernLights;
   }

   public void setNorthernLights(NorthernLightsSettings nls) {
      if (this.northernLights != null) {
         throw new IllegalArgumentException("Cannot set map's northern lights - already set!");
      } else {
         this.northernLights = nls;
      }
   }

   public List getWaypointRegions() {
      return this.waypointRegions;
   }

   public void setWaypointRegions(List regions) {
      if (this.waypointRegions != null) {
         throw new IllegalArgumentException("Cannot set map's waypoint regions - already set!");
      } else {
         this.waypointRegions = regions;
         this.waypointRegionsById = (Map)this.waypointRegions.stream().collect(Collectors.toMap(WaypointRegion::getAssignedId, UnaryOperator.identity()));
         this.waypointRegionsByName = (Map)this.waypointRegions.stream().collect(Collectors.toMap(WaypointRegion::getName, UnaryOperator.identity()));
         this.waypointRegionsForBiome = new HashMap();
         this.waypointRegions.forEach((region) -> {
            region.getBiomeNames().forEach((biomeName) -> {
               ((List)this.waypointRegionsForBiome.computeIfAbsent(biomeName, (b) -> {
                  return new ArrayList();
               })).add(region);
            });
         });
      }
   }

   public WaypointRegion getWaypointRegionByID(int id) {
      return (WaypointRegion)this.waypointRegionsById.get(id);
   }

   public WaypointRegion getWaypointRegionByName(ResourceLocation name) {
      return (WaypointRegion)this.waypointRegionsByName.get(name);
   }

   public List getWaypointRegionsForBiome(Biome biome, IWorld world) {
      ResourceLocation biomeName = LOTRBiomes.getBiomeRegistryName(biome, world);
      return (List)this.waypointRegionsForBiome.getOrDefault(biomeName, ImmutableList.of());
   }

   public int getNumBiomesMappedToWaypointRegions() {
      return this.waypointRegionsForBiome.size();
   }

   public List getWaypointRegionNames() {
      return (List)this.waypointRegions.stream().map(WaypointRegion::getName).collect(Collectors.toList());
   }

   public List getWaypoints() {
      return this.waypoints;
   }

   public void setWaypoints(List wps) {
      if (this.waypoints != null) {
         throw new IllegalArgumentException("Cannot set map's waypoints - already set!");
      } else {
         this.waypoints = wps;
         this.waypointsById = (Map)this.waypoints.stream().collect(Collectors.toMap(MapWaypoint::getAssignedId, UnaryOperator.identity()));
         this.waypointsByName = (Map)this.waypoints.stream().collect(Collectors.toMap(MapWaypoint::getName, UnaryOperator.identity()));
      }
   }

   public MapWaypoint getWaypointByID(int id) {
      return (MapWaypoint)this.waypointsById.get(id);
   }

   public MapWaypoint getWaypointByName(ResourceLocation name) {
      return (MapWaypoint)this.waypointsByName.get(name);
   }

   public List getMenuWaypointRoute() {
      return this.menuWaypointRoute;
   }

   public List getRoads() {
      return this.roads;
   }

   private void setRoads(List inputRoads) {
      if (this.roads != null) {
         throw new IllegalArgumentException("Cannot set map's roads - already set!");
      } else {
         this.roads = inputRoads;
      }
   }

   public void setRoadsAndGenerateCurvesInstantly(List inputRoads) {
      this.setRoads(inputRoads);
      Iterator var2 = this.roads.iterator();

      while(var2.hasNext()) {
         Road road = (Road)var2.next();
         road.generateCurves();
      }

   }

   public void setRoadsAndGenerateCurvesOnThread(List inputRoads) {
      this.setRoads(inputRoads);
      Thread roadCurveThread = new Thread(() -> {
         Iterator var1 = this.roads.iterator();

         while(var1.hasNext()) {
            Road road = (Road)var1.next();
            road.generateCurves();
         }

      }, "Road curve generator clientside thread");
      roadCurveThread.start();
   }

   public RoadPointCache getRoadPointCache() {
      return this.roadPointCache;
   }

   public List getLabels() {
      return this.labels;
   }

   public void setLabels(List lbls) {
      if (this.labels != null) {
         throw new IllegalArgumentException("Cannot set map's labels - already set!");
      } else {
         this.labels = lbls;
      }
   }

   public void postLoadValidateBiomes(World world) {
      this.waypointRegions.forEach((region) -> {
         region.postLoadValidateBiomes(world);
      });
   }
}
