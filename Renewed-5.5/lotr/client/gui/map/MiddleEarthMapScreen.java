package lotr.client.gui.map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotr.client.LOTRKeyHandler;
import lotr.client.MapImageTextures;
import lotr.client.gui.MiddleEarthFactionsScreen;
import lotr.client.gui.MiddleEarthMenuScreen;
import lotr.client.gui.util.AlignmentTextRenderer;
import lotr.client.util.LOTRClientUtil;
import lotr.common.config.LOTRConfig;
import lotr.common.data.FastTravelDataModule;
import lotr.common.data.FogDataModule;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.fac.AreaBorders;
import lotr.common.fac.AreaOfInfluence;
import lotr.common.fac.AreasOfInfluence;
import lotr.common.fac.Faction;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTRItems;
import lotr.common.network.CPacketCreateMapMarker;
import lotr.common.network.CPacketDeleteMapMarker;
import lotr.common.network.CPacketFastTravel;
import lotr.common.network.CPacketIsOpRequest;
import lotr.common.network.CPacketMapTp;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.util.LOTRUtil;
import lotr.common.world.map.MapLabel;
import lotr.common.world.map.MapMarker;
import lotr.common.world.map.MapPlayerLocation;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import lotr.common.world.map.MapWaypoint;
import lotr.common.world.map.Road;
import lotr.common.world.map.RoadPoint;
import lotr.common.world.map.RoadSection;
import lotr.common.world.map.RouteRoadPoint;
import lotr.common.world.map.SelectableMapObject;
import lotr.common.world.map.Waypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.Direction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.lwjgl.opengl.GL11;

public class MiddleEarthMapScreen extends MiddleEarthMenuScreen {
   private static final ItemStack QUEST_ICON;
   public static final int BLACK = -16777216;
   public static final int BORDER_COLOR = -6156032;
   private static final int MAP_BORDER = 30;
   private static boolean fullscreen;
   private final MapSettings loadedMapSettings;
   private int viewportWidth;
   private int viewportHeight;
   private int viewportXMin;
   private int viewportXMax;
   private int viewportYMin;
   private int viewportYMax;
   private List mapWidgets = new ArrayList();
   private MapWidget widgetZoomIn;
   private MapWidget widgetZoomOut;
   private MapWidget widgetRecentre;
   private MapWidget widgetFullScreen;
   private MapWidget widgetSepia;
   private MapWidget widgetLabels;
   private MapWidget widgetToggleMapWPs;
   private MapWidget widgetToggleCustomWPs;
   private MapWidget widgetToggleShowLocation;
   private MapWidget widgetToggleMarkers;
   private MapWidget widgetNewMarker;
   private int tickCounter;
   private int renderedMapObjects;
   private double posX;
   private double posY;
   private double prevPosX;
   private double prevPosY;
   private double posXMove;
   private double posYMove;
   private int isMouseButtonDown;
   private int prevMouseX;
   private int prevMouseY;
   private boolean isMouseWithinMap;
   private int mouseXCoord;
   private int mouseZCoord;
   private double recentreToX;
   private double recentreToY;
   private double recentreFromX;
   private double recentreFromY;
   private int recentreTicks;
   private static final int RECENTRE_TIME = 6;
   private static final int MIN_ZOOM = -3;
   private static final int MAX_ZOOM = 4;
   private static float zoomPower;
   private float prevZoomPower;
   private float zoomScale;
   private float zoomScaleStable;
   private float zoomExp;
   private static final int ZOOM_TIME = 6;
   private int zoomTicks;
   private int prevZoomTicks;
   public boolean enableZoomOutObjectFading;
   private static final DecimalFormat ZOOM_DISPLAY_FORMAT;
   private int zoomingMessageDisplayTicks;
   private static final int ZOOMING_MESSAGE_DISPLAY_TIME = 30;
   private boolean zoomingMessageIsZoomIn;
   private SelectableMapObject mouseOverObject;
   private SelectableMapObject selectedObject;
   private static final int OBJECT_SELECT_RANGE = 5;
   private int objectSelectTick;
   private int prevObjectSelectTick;
   private static final int OBJECT_SELECT_TIME = 6;
   private final WaypointTooltipRenderer waypointTooltip;
   private final MarkerTooltipRenderer markerTooltip;
   private boolean creatingMarker;
   private static final int MARKER_W = 10;
   private boolean hasOverlay;
   private boolean isPlayerOp;
   private boolean sentOpRequestPacket;
   private Faction areaOfInfluenceFaction;
   private boolean mouseInAreaOfInfluence;
   private boolean mouseInReducedAreaOfInfluence;
   private final AlignmentTextRenderer alignmentTextRenderer;
   private boolean isFacScrolling;

   public MiddleEarthMapScreen() {
      super(new StringTextComponent("MAP"));
      this.prevZoomPower = zoomPower;
      this.enableZoomOutObjectFading = true;
      this.waypointTooltip = new WaypointTooltipRenderer();
      this.markerTooltip = new MarkerTooltipRenderer();
      this.isPlayerOp = false;
      this.sentOpRequestPacket = false;
      this.alignmentTextRenderer = AlignmentTextRenderer.newGUIRenderer();
      this.isFacScrolling = false;
      this.loadedMapSettings = MapSettingsManager.clientInstance().getLoadedMapOrLoadDefault(Minecraft.func_71410_x().func_195551_G());
      this.loadCurrentMapTextures();
   }

   public void loadCurrentMapTextures() {
      MapImageTextures.INSTANCE.loadMapTexturesIfNew(this.loadedMapSettings);
   }

   public void setAreasOfInfluence(Faction faction) {
      this.areaOfInfluenceFaction = faction;
   }

   private boolean hasAreasOfInfluence() {
      return this.areaOfInfluenceFaction != null && AreasOfInfluence.areAreasOfInfluenceEnabled(this.field_230706_i_.field_71441_e);
   }

   public void func_231160_c_() {
      this.xSize = 256;
      this.ySize = 256;
      super.func_231160_c_();
      if (fullscreen) {
         int midX = this.field_230708_k_ / 2;
         int titleWidth = this.field_230712_o_.func_238414_a_(this.getMapTitle());
         int idealMargin = 24;
         int minimumGapFromTitle = 4;
         this.buttonMenuReturn.field_230690_l_ = Math.min(0 + idealMargin, midX - titleWidth / 2 - minimumGapFromTitle - this.buttonMenuReturn.func_230998_h_());
         this.buttonMenuReturn.field_230691_m_ = 4;
      }

      if (this.hasAreasOfInfluence()) {
         this.removeButton(this.buttonMenuReturn);
      }

      this.setupMapDimensions();
      this.setupMapWidgets();
      this.field_230705_e_.add(new MiddleEarthMapScreen.MapDragListener());
      if (this.hasAreasOfInfluence()) {
         this.setupMapDimensions();
         AreaBorders zoneBorders = this.areaOfInfluenceFaction.getAreasOfInfluence().calculateAreaOfInfluenceBordersIncludingReduced();
         this.posX = this.loadedMapSettings.worldToMapX_frac(zoneBorders.getXCentre());
         this.posY = this.loadedMapSettings.worldToMapZ_frac(zoneBorders.getZCentre());
         double mapZoneWidth = (double)this.loadedMapSettings.worldToMapDistance(zoneBorders.getWidth());
         double mapZoneHeight = (double)this.loadedMapSettings.worldToMapDistance(zoneBorders.getHeight());
         int zoomPowerWidth = MathHelper.func_76128_c(Math.log((double)this.viewportWidth / mapZoneWidth) / Math.log(2.0D));
         int zoomPowerHeight = MathHelper.func_76128_c(Math.log((double)this.viewportHeight / mapZoneHeight) / Math.log(2.0D));
         this.prevZoomPower = zoomPower = (float)Math.min(zoomPowerWidth, zoomPowerHeight);
      } else if (this.field_230706_i_.field_71439_g != null) {
         this.centreMapOnPlayer();
      }

      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.refreshZoomVariables(1.0F);
      this.waypointTooltip.init(this, this.field_230706_i_, this.field_230712_o_);
      this.markerTooltip.init(this, this.field_230706_i_, this.field_230712_o_);
   }

   private void setupMapWidgets() {
      this.mapWidgets.clear();
      this.mapWidgets.add(this.widgetZoomIn = new MapWidget(this, this.viewportXMin + 6, this.viewportYMin + 6, 10, "zoomIn", 30, 0, () -> {
         if (this.zoomTicks == 0 && zoomPower < 4.0F) {
            this.zoomIn();
            return true;
         } else {
            return false;
         }
      }));
      this.mapWidgets.add(this.widgetZoomOut = new MapWidget(this, this.viewportXMin + 6, this.viewportYMin + 20, 10, "zoomOut", 40, 0, () -> {
         if (this.zoomTicks == 0 && zoomPower > -3.0F) {
            this.zoomOut();
            return true;
         } else {
            return false;
         }
      }));
      this.mapWidgets.add(this.widgetRecentre = new MapWidget(this, this.viewportXMin + 20, this.viewportYMin + 6, 10, "recentre", 50, 10, () -> {
         if (!this.isRecentringOnPlayer()) {
            this.recentreMapOnPlayer();
            return true;
         } else {
            return false;
         }
      }));
      this.mapWidgets.add(this.widgetFullScreen = new MapWidget(this, this.viewportXMin + 34, this.viewportYMin + 6, 10, "fullScreen", 50, 0, () -> {
         fullscreen = !fullscreen;
         this.func_231158_b_(this.field_230706_i_, this.field_230706_i_.func_228018_at_().func_198107_o(), this.field_230706_i_.func_228018_at_().func_198087_p());
         return true;
      }));
      this.mapWidgets.add(this.widgetSepia = new MapWidget(this, this.viewportXMin + 48, this.viewportYMin + 6, 10, "sepia", 60, 0, () -> {
         LOTRConfig.CLIENT.toggleSepia();
         return true;
      }));
      this.mapWidgets.add(this.widgetLabels = new MapWidget(this, this.viewportXMax - 16, this.viewportYMin + 6, 10, "labels", 70, 0, () -> {
         this.toggleMapLabels();
         return true;
      }));
      this.mapWidgets.add(this.widgetToggleMapWPs = new MapWidget(this, this.viewportXMax - 72, this.viewportYMin + 6, 10, "toggleMapWPs", 80, 0, () -> {
         this.getOptClientPlayerData().ifPresent((pd) -> {
            pd.getFastTravelData().toggleShowMapWaypointsAndSendToServer();
         });
         return true;
      }));
      this.mapWidgets.add(this.widgetToggleCustomWPs = new MapWidget(this, this.viewportXMax - 58, this.viewportYMin + 6, 10, "toggleCustomWPs", 90, 0, () -> {
         this.getOptClientPlayerData().ifPresent((pd) -> {
            pd.getFastTravelData().toggleShowCustomWaypointsAndSendToServer();
         });
         return true;
      }));
      this.mapWidgets.add(this.widgetToggleShowLocation = new MapWidget(this, this.viewportXMax - 16, this.viewportYMin + 20, 10, "toggleShowLocation", 60, 10, () -> {
         this.getOptClientPlayerData().ifPresent((pd) -> {
            pd.getMiscData().toggleShowMapLocationAndSendToServer();
         });
         return true;
      }));
      this.mapWidgets.add(this.widgetToggleMarkers = new MapWidget(this, this.viewportXMax - 44, this.viewportYMin + 6, 10, "toggleMarkers", 110, 0, () -> {
         this.getOptClientPlayerData().ifPresent((pd) -> {
            pd.getMapMarkerData().toggleShowMarkersAndSendToServer();
         });
         return true;
      }));
      this.mapWidgets.add(this.widgetNewMarker = new MapWidget(this, this.viewportXMax - 30, this.viewportYMin + 6, 10, "newMarker", 120, 0, () -> {
         if (!this.creatingMarker && this.canCreateNewMarker()) {
            this.creatingMarker = true;
            return true;
         } else {
            return false;
         }
      }));
      this.field_230705_e_.addAll(this.mapWidgets);
   }

   public boolean hasOverlay() {
      return this.hasOverlay;
   }

   private void setupMapDimensions() {
      if (fullscreen) {
         this.viewportXMin = 30;
         this.viewportXMax = this.field_230708_k_ - 30;
         this.viewportYMin = 30;
         this.viewportYMax = this.field_230709_l_ - 30;
      } else {
         int windowWidth = 312;
         this.viewportXMin = this.field_230708_k_ / 2 - windowWidth / 2;
         this.viewportXMax = this.field_230708_k_ / 2 + windowWidth / 2;
         this.viewportYMin = this.guiTop;
         this.viewportYMax = this.guiTop + 200;
      }

      this.viewportWidth = this.viewportXMax - this.viewportXMin;
      this.viewportHeight = this.viewportYMax - this.viewportYMin;
   }

   private void centreMapOnPlayer() {
      this.posX = this.getPlayerMapPosX();
      this.posY = this.getPlayerMapPosY();
   }

   private double getPlayerMapPosX() {
      return this.loadedMapSettings.worldToMapX_frac(this.field_230706_i_.field_71439_g.func_226277_ct_());
   }

   private double getPlayerMapPosY() {
      return this.loadedMapSettings.worldToMapZ_frac(this.field_230706_i_.field_71439_g.func_226281_cx_());
   }

   private double lerpPosX(float tick) {
      return MathHelper.func_151238_b(this.prevPosX, this.posX, (double)tick);
   }

   private double lerpPosY(float tick) {
      return MathHelper.func_151238_b(this.prevPosY, this.posY, (double)tick);
   }

   public double[] transformWorldCoords(double x, double z, float tick) {
      x = this.loadedMapSettings.worldToMapX_frac(x);
      z = this.loadedMapSettings.worldToMapZ_frac(z);
      return this.transformMapCoords(x, z, tick);
   }

   public double[] transformMapCoords(double x, double z, float tick) {
      x -= this.lerpPosX(tick);
      z -= this.lerpPosY(tick);
      x *= (double)this.zoomScale;
      z *= (double)this.zoomScale;
      x += (double)(this.viewportXMin + this.viewportWidth / 2);
      z += (double)(this.viewportYMin + this.viewportHeight / 2);
      return new double[]{x, z};
   }

   private double[] convertMouseCoordsToMapCoords(int mouseX, int mouseY, float tick) {
      double mapPosX = this.lerpPosX(tick) + (double)((float)(mouseX - this.viewportXMin - this.viewportWidth / 2) / this.zoomScale);
      double mapPosZ = this.lerpPosY(tick) + (double)((float)(mouseY - this.viewportYMin - this.viewportHeight / 2) / this.zoomScale);
      return new double[]{mapPosX, mapPosZ};
   }

   private ITextComponent getMapTitle() {
      return new TranslationTextComponent("gui.lotr.map.title", new Object[]{this.loadedMapSettings.getTitle()});
   }

   private Optional getOptClientPlayerData() {
      Optional var10000 = Optional.ofNullable(this.field_230706_i_.field_71439_g);
      LOTRLevelData var10001 = LOTRLevelData.clientInstance();
      var10001.getClass();
      return var10000.map(var10001::getData);
   }

   public LOTRPlayerData getClientPlayerData() {
      return (LOTRPlayerData)this.getOptClientPlayerData().get();
   }

   private boolean showLocationToOthers() {
      return (Boolean)this.getOptClientPlayerData().map((pd) -> {
         return pd.getMiscData().getShowMapLocation();
      }).orElse(true);
   }

   private boolean showMapWaypoints() {
      return (Boolean)this.getOptClientPlayerData().map((pd) -> {
         return pd.getFastTravelData().getShowMapWaypoints();
      }).orElse(true);
   }

   private boolean showCustomWaypoints() {
      return (Boolean)this.getOptClientPlayerData().map((pd) -> {
         return pd.getFastTravelData().getShowCustomWaypoints();
      }).orElse(false);
   }

   private boolean showHiddenSharedCustomWaypoints() {
      return true;
   }

   private boolean showMarkers() {
      return (Boolean)this.getOptClientPlayerData().map((pd) -> {
         return pd.getMapMarkerData().getShowMarkers();
      }).orElse(false);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      ++this.tickCounter;
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.handleMapKeyboardMovement();
      this.posX += this.posXMove;
      this.posY += this.posYMove;
      if (this.recentreTicks > 0) {
         --this.recentreTicks;
         float lerp = (float)(6 - this.recentreTicks) / 6.0F;
         this.posX = MathHelper.func_219803_d((double)lerp, this.recentreFromX, this.recentreToX);
         this.posY = MathHelper.func_219803_d((double)lerp, this.recentreFromY, this.recentreToY);
      }

      this.keepMapPositionWithinBounds();
      if (this.zoomTicks <= 0 && this.prevZoomTicks > 0) {
         this.prevZoomPower = zoomPower;
      }

      this.prevZoomTicks = this.zoomTicks;
      if (this.zoomTicks > 0) {
         --this.zoomTicks;
      }

      if (this.zoomingMessageDisplayTicks > 0) {
         --this.zoomingMessageDisplayTicks;
      }

      this.prevObjectSelectTick = this.objectSelectTick;
      if (this.objectSelectTick > 0) {
         --this.objectSelectTick;
      }

      this.waypointTooltip.tick();
      this.markerTooltip.tick();
   }

   private void refreshZoomVariables(float tick) {
      float zoomStableExp;
      if (this.zoomTicks <= 0 && this.prevZoomTicks <= 0) {
         this.zoomExp = zoomPower;
         zoomStableExp = this.zoomExp;
      } else {
         float zoomTickLerp = (float)this.prevZoomTicks + (float)(this.zoomTicks - this.prevZoomTicks) * tick;
         float progress = (6.0F - zoomTickLerp) / 6.0F;
         this.zoomExp = this.prevZoomPower + (zoomPower - this.prevZoomPower) * progress;
         zoomStableExp = Math.min(zoomPower, this.prevZoomPower);
      }

      this.zoomScale = (float)Math.pow(2.0D, (double)this.zoomExp);
      this.zoomScaleStable = (float)Math.pow(2.0D, (double)zoomStableExp);
      this.keepMapPositionWithinBounds();
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float tick) {
      tick = this.field_230706_i_.func_184121_ak();
      World world = this.field_230706_i_.field_71441_e;
      Tessellator tess = Tessellator.func_178181_a();
      BufferBuilder buf = tess.func_178180_c();
      int blit0 = false;
      this.func_230926_e_(0);
      this.renderedMapObjects = 0;
      this.refreshZoomVariables(tick);
      this.isMouseWithinMap = mouseX >= this.viewportXMin && mouseX < this.viewportXMax && mouseY >= this.viewportYMin && mouseY < this.viewportYMax;
      boolean isSepia = (Boolean)LOTRConfig.CLIENT.sepiaMap.get();
      if (fullscreen) {
         this.field_230706_i_.func_110434_K().func_110577_a(MapImageTextures.OVERLAY_TEXTURE);
         RenderSystem.color4f(0.65F, 0.5F, 0.35F, 1.0F);
         float z = (float)this.func_230927_p_();
         buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         Matrix4f mat = matStack.func_227866_c_().func_227870_a_();
         buf.func_227888_a_(mat, 0.0F, (float)this.field_230709_l_, z).func_225583_a_(0.0F, 1.0F).func_181675_d();
         buf.func_227888_a_(mat, (float)this.field_230708_k_, (float)this.field_230709_l_, z).func_225583_a_(1.0F, 1.0F).func_181675_d();
         buf.func_227888_a_(mat, (float)this.field_230708_k_, 0.0F, z).func_225583_a_(1.0F, 0.0F).func_181675_d();
         buf.func_227888_a_(mat, 0.0F, 0.0F, z).func_225583_a_(0.0F, 0.0F).func_181675_d();
         tess.func_78381_a();
         int redW = 4;
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.renderGraduatedRects(matStack, this.viewportXMin - 1, this.viewportYMin - 1, this.viewportXMax + 1, this.viewportYMax + 1, -6156032, -16777216, redW);
      } else {
         this.func_230446_a_(matStack);
         this.renderGraduatedRects(matStack, this.viewportXMin - 1, this.viewportYMin - 1, this.viewportXMax + 1, this.viewportYMax + 1, -6156032, -16777216, 4);
      }

      this.setupScrollBars(mouseX, mouseY);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      int seaColor = MapImageTextures.INSTANCE.getMapBackgroundColor(isSepia);
      func_238467_a_(matStack, this.viewportXMin, this.viewportYMin, this.viewportXMax, this.viewportYMax, seaColor);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      ITextComponent title = this.getMapTitle();
      if (fullscreen) {
         this.drawCenteredStringNoShadow(matStack, this.field_230712_o_, title, this.field_230708_k_ / 2, 10, 16777215);
      } else {
         this.drawCenteredStringNoShadow(matStack, this.field_230712_o_, title, this.field_230708_k_ / 2, this.guiTop - 30, 16777215);
      }

      if (this.hasAreasOfInfluence()) {
         this.renderMapAndOverlay(matStack, tick, isSepia, 1.0F, false);
         this.renderAreasOfInfluence(matStack, mouseX, mouseY, tick);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         this.renderMapAndOverlay(matStack, tick, isSepia, 0.5F, true);
         RenderSystem.disableBlend();
      } else {
         this.renderMapAndOverlay(matStack, tick, isSepia, 1.0F, true);
      }

      this.renderRoads(matStack, tick);
      this.renderLabels(matStack, tick);
      this.renderFogOfWar(matStack, tick);
      this.renderPlayers(matStack, mouseX, mouseY, tick);
      this.determineMouseOverObject(mouseX, mouseY, tick);
      this.renderWaypoints(matStack, 0, mouseX, mouseY, tick);
      this.renderMarkers(matStack, 0, mouseX, mouseY, tick);
      RenderSystem.enableAlphaTest();
      MapImageTextures.drawMapCompassBottomLeft(matStack, (float)(this.viewportXMin + 12), (float)(this.viewportYMax - 12), 100.0F, 1.0F);
      this.renderWaypoints(matStack, 1, mouseX, mouseY, tick);
      this.renderMarkers(matStack, 1, mouseX, mouseY, tick);
      if (this.creatingMarker) {
         this.renderCreatingMarker(matStack, mouseX, mouseY);
      }

      if (this.hasAreasOfInfluence()) {
         ITextComponent tooltip = this.mouseInAreaOfInfluence ? new TranslationTextComponent("gui.lotr.map.areaOfInfluence.full") : (this.mouseInReducedAreaOfInfluence ? new TranslationTextComponent("gui.lotr.map.areaOfInfluence.reduced") : null);
         if (tooltip != null) {
            this.renderInFront(() -> {
               int strWidth = this.field_230706_i_.field_71466_p.func_238414_a_(tooltip);
               this.field_230706_i_.field_71466_p.getClass();
               int strHeight = 9;
               int rectX = mouseX + 12;
               int rectY = mouseY - 12;
               int border = 3;
               int rectWidth = strWidth + border * 2;
               int rectHeight = strHeight + border * 2;
               int mapBorder2 = 2;
               rectX = Math.max(rectX, this.viewportXMin + mapBorder2);
               rectX = Math.min(rectX, this.viewportXMax - mapBorder2 - rectWidth);
               rectY = Math.max(rectY, this.viewportYMin + mapBorder2);
               rectY = Math.min(rectY, this.viewportYMax - mapBorder2 - rectHeight);
               this.drawFancyRect(matStack, rectX, rectY, rectX + rectWidth, rectY + rectHeight);
               this.field_230712_o_.func_243248_b(matStack, tooltip, (float)(rectX + border), (float)(rectY + border), 16777215);
            });
         }
      }

      Waypoint selectedWp;
      if (!this.hasOverlay && this.selectedObject != null) {
         if (this.selectedObject instanceof Waypoint) {
            selectedWp = (Waypoint)this.selectedObject;
            if (this.isWaypointVisible(selectedWp)) {
               this.renderWaypointTooltip(matStack, selectedWp, true, mouseX, mouseY, tick);
            } else {
               this.selectObject((SelectableMapObject)null);
            }
         } else if (this.selectedObject instanceof MapMarker) {
            MapMarker selectedMarker = (MapMarker)this.selectedObject;
            if (this.showMarkers()) {
               this.renderMarkerTooltip(matStack, selectedMarker, true, mouseX, mouseY, tick);
            } else {
               this.selectObject((SelectableMapObject)null);
            }
         }
      }

      this.func_230926_e_(100);
      if (!this.hasOverlay) {
         int biomePosX_int;
         int strX;
         if (this.isMiddleEarth() && this.selectedObject instanceof Waypoint) {
            selectedWp = (Waypoint)this.selectedObject;
            int blit1 = this.func_230927_p_();
            this.func_230926_e_(500);
            LOTRPlayerData pd = this.getClientPlayerData();
            FastTravelDataModule ftData = pd.getFastTravelData();
            boolean hasUnlocked = selectedWp.hasPlayerUnlocked(this.field_230706_i_.field_71439_g);
            int ftSince = ftData.getTimeSinceFT();
            int wpTimeThreshold = ftData.getWaypointFTTime(selectedWp, this.field_230706_i_.field_71439_g);
            biomePosX_int = wpTimeThreshold - ftSince;
            boolean canFastTravel = hasUnlocked && biomePosX_int <= 0;
            ITextComponent notUnlockedMessage = selectedWp.getNotUnlockedMessage(this.field_230706_i_.field_71439_g);
            ITextComponent ftPrompt = new TranslationTextComponent("gui.lotr.map.fastTravel.prompt", new Object[]{LOTRKeyHandler.getFastTravelKey(this.field_230706_i_).func_238171_j_()});
            ITextComponent ftMoreTime = new TranslationTextComponent("gui.lotr.map.fastTravel.moreTime", new Object[]{LOTRUtil.getHMSTime_Ticks(biomePosX_int)});
            ITextComponent ftWaitTime = new TranslationTextComponent("gui.lotr.map.fastTravel.waitTime", new Object[]{LOTRUtil.getHMSTime_Ticks(wpTimeThreshold)});
            if (fullscreen) {
               if (!hasUnlocked) {
                  this.renderFullscreenSubtitles(matStack, notUnlockedMessage);
               } else if (canFastTravel) {
                  this.renderFullscreenSubtitles(matStack, ftPrompt, ftWaitTime);
               } else {
                  this.renderFullscreenSubtitles(matStack, ftMoreTime, ftWaitTime);
               }
            } else {
               List lines = new ArrayList();
               if (!hasUnlocked) {
                  lines.add(notUnlockedMessage);
               } else if (canFastTravel) {
                  lines.add(ftPrompt);
                  lines.add(ftWaitTime);
               } else {
                  lines.add(ftMoreTime);
                  lines.add(ftWaitTime);
               }

               this.field_230712_o_.getClass();
               int stringHeight = 9;
               strX = this.viewportWidth;
               int border = 3;
               int rectHeight = border + (stringHeight + border) * lines.size();
               int x = this.viewportXMin + this.viewportWidth / 2 - strX / 2;
               int y = this.viewportYMax + 10;
               int strX = this.viewportXMin + this.viewportWidth / 2;
               int strY = y + border;
               this.drawFancyRect(matStack, x, y, x + strX, y + rectHeight);

               for(Iterator var33 = lines.iterator(); var33.hasNext(); strY += stringHeight + border) {
                  ITextComponent s = (ITextComponent)var33.next();
                  this.drawCenteredStringNoShadow(matStack, this.field_230712_o_, s, strX, strY, 16777215);
               }
            }

            this.func_230926_e_(blit1);
         } else if (this.isMouseWithinMap) {
            int blit1 = this.func_230927_p_();
            this.func_230926_e_(500);
            double[] biomeMapCoords = this.convertMouseCoordsToMapCoords(mouseX, mouseY, tick);
            double biomeMapX = biomeMapCoords[0];
            double biomeMapZ = biomeMapCoords[1];
            this.mouseXCoord = this.loadedMapSettings.mapToWorldX(biomeMapX);
            this.mouseZCoord = this.loadedMapSettings.mapToWorldZ(biomeMapZ);
            Object biomeName;
            if (this.isPositionFogged(biomeMapX, biomeMapZ)) {
               biomeName = new TranslationTextComponent("gui.lotr.map.unexplored");
            } else {
               biomePosX_int = MathHelper.func_76128_c(biomeMapX);
               int biomePosZ_int = MathHelper.func_76128_c(biomeMapZ);
               Biome biome = LOTRBiomes.getBiomeByID(this.loadedMapSettings.getBiomeIdAt(biomePosX_int, biomePosZ_int, world), world);
               biomeName = LOTRBiomes.getBiomeDisplayName(biome, world);
            }

            ITextComponent coords = new TranslationTextComponent("gui.lotr.map.coords", new Object[]{this.mouseXCoord, this.mouseZCoord});
            ITextComponent teleport = new TranslationTextComponent("gui.lotr.map.tp", new Object[]{LOTRKeyHandler.KEY_BIND_MAP_TELEPORT.func_238171_j_()});
            this.field_230712_o_.getClass();
            int stringHeight = 9;
            if (fullscreen) {
               this.renderFullscreenSubtitles(matStack, (ITextComponent)biomeName, coords);
               if (this.canTeleport()) {
                  matStack.func_227860_a_();
                  matStack.func_227861_a_((double)(this.field_230708_k_ / 2 - 30 - this.field_230712_o_.func_238414_a_(teleport) / 2), 0.0D, 0.0D);
                  this.renderFullscreenSubtitles(matStack, teleport);
                  matStack.func_227865_b_();
               }
            } else {
               int rectWidth = this.viewportWidth;
               int border = 3;
               int rectHeight = border * 3 + stringHeight * 2;
               if (this.canTeleport()) {
                  rectHeight += (stringHeight + border) * 2;
               }

               int x = this.viewportXMin + this.viewportWidth / 2 - rectWidth / 2;
               int y = this.viewportYMax + 10;
               this.drawFancyRect(matStack, x, y, x + rectWidth, y + rectHeight);
               strX = this.viewportXMin + this.viewportWidth / 2;
               int strY = y + border;
               this.drawCenteredStringNoShadow(matStack, this.field_230712_o_, (ITextComponent)biomeName, strX, strY, 16777215);
               strY += stringHeight + border;
               this.drawCenteredStringNoShadow(matStack, this.field_230712_o_, coords, strX, strY, 16777215);
               if (this.canTeleport()) {
                  this.drawCenteredStringNoShadow(matStack, this.field_230712_o_, teleport, strX, strY + (stringHeight + border) * 2, 16777215);
               }
            }

            this.func_230926_e_(blit1);
         }
      }

      if (!this.hasOverlay && this.hasAreasOfInfluence()) {
         this.renderInFront(() -> {
            ITextComponent s = new TranslationTextComponent("gui.lotr.map.areaOfInfluence.title", new Object[]{this.areaOfInfluenceFaction.getDisplayName()});
            int x = this.viewportXMin + this.viewportWidth / 2;
            int y = this.viewportYMin + 20;
            this.alignmentTextRenderer.drawAlignmentText(matStack, this.field_230712_o_, x - this.field_230712_o_.func_238414_a_(s) / 2, y, s, 1.0F);
            if (!AreasOfInfluence.areAreasOfInfluenceEnabled(world)) {
               s = new TranslationTextComponent("gui.lotr.map.areaOfInfluence.disabled");
               this.alignmentTextRenderer.drawAlignmentText(matStack, this.field_230712_o_, x - this.field_230712_o_.func_238414_a_(s) / 2, this.viewportYMin + this.viewportHeight / 2, s, 1.0F);
            }

         });
      }

      if (this.zoomingMessageDisplayTicks > 0) {
         this.renderInFront(() -> {
            ITextComponent s = this.getZoomingDisplayMessage();
            int x = this.viewportXMin + this.viewportWidth / 2;
            int y = this.viewportYMax - 30;
            float alpha = LOTRUtil.trapezoidalIntensitySinglePulse((float)(30 - this.zoomingMessageDisplayTicks), 30.0F, 0.16F, 0.0F, 1.0F);
            this.drawCenteredStringNoShadow(matStack, this.field_230712_o_, s, x, y, LOTRClientUtil.getRGBAForFontRendering(16777215, alpha));
         });
      }

      super.func_230430_a_(matStack, mouseX, mouseY, tick);
      this.renderMapWidgets(matStack, mouseX, mouseY);
   }

   private void setupScrollBars(int mouseX, int mouseY) {
   }

   private void keepMapPositionWithinBounds() {
      float mapScaleX = (float)this.viewportWidth / this.zoomScale;
      float mapScaleY = (float)this.viewportHeight / this.zoomScale;
      float minPosY;
      if (this.loadedMapSettings.isScreenSideLocked(Direction.WEST)) {
         minPosY = mapScaleX / 2.0F;
         this.posX = Math.max(this.posX, (double)minPosY);
         this.prevPosX = Math.max(this.prevPosX, (double)minPosY);
      }

      if (this.loadedMapSettings.isScreenSideLocked(Direction.EAST)) {
         minPosY = (float)this.loadedMapSettings.getWidth() - mapScaleX / 2.0F;
         this.posX = Math.min(this.posX, (double)minPosY);
         this.prevPosX = Math.min(this.prevPosX, (double)minPosY);
      }

      if (this.loadedMapSettings.isScreenSideLocked(Direction.NORTH)) {
         minPosY = mapScaleY / 2.0F;
         this.posY = Math.max(this.posY, (double)minPosY);
         this.prevPosY = Math.max(this.prevPosY, (double)minPosY);
      }

      if (this.loadedMapSettings.isScreenSideLocked(Direction.SOUTH)) {
         minPosY = (float)this.loadedMapSettings.getHeight() - mapScaleY / 2.0F;
         this.posY = Math.min(this.posY, (double)minPosY);
         this.prevPosY = Math.min(this.prevPosY, (double)minPosY);
      }

   }

   public void renderMapAndOverlay(MatrixStack matStack, float tick, boolean sepia, float alpha, boolean overlay) {
      double lerpPosX = this.lerpPosX(tick);
      double lerpPosY = this.lerpPosY(tick);
      int mapXMin_W = this.viewportXMin;
      int mapXMax_W = this.viewportXMax;
      int mapYMin_W = this.viewportYMin;
      int mapYMax_W = this.viewportYMax;
      float mapScaleX = (float)this.viewportWidth / this.zoomScale;
      float mapScaleY = (float)this.viewportHeight / this.zoomScale;
      float minU = (float)(lerpPosX - (double)(mapScaleX / 2.0F)) / (float)this.loadedMapSettings.getWidth();
      float maxU = (float)(lerpPosX + (double)(mapScaleX / 2.0F)) / (float)this.loadedMapSettings.getWidth();
      float minV = (float)(lerpPosY - (double)(mapScaleY / 2.0F)) / (float)this.loadedMapSettings.getHeight();
      float maxV = (float)(lerpPosY + (double)(mapScaleY / 2.0F)) / (float)this.loadedMapSettings.getHeight();
      if (minU < 0.0F) {
         mapXMin_W = this.viewportXMin + Math.round((0.0F - minU) * (float)this.loadedMapSettings.getWidth() * this.zoomScale);
         minU = 0.0F;
         if (maxU < 0.0F) {
            maxU = 0.0F;
            mapXMax_W = mapXMin_W;
         }
      }

      if (maxU > 1.0F) {
         mapXMax_W = this.viewportXMax - Math.round((maxU - 1.0F) * (float)this.loadedMapSettings.getWidth() * this.zoomScale);
         maxU = 1.0F;
         if (minU > 1.0F) {
            minU = 1.0F;
            mapXMin_W = mapXMax_W;
         }
      }

      if (minV < 0.0F) {
         mapYMin_W = this.viewportYMin + Math.round((0.0F - minV) * (float)this.loadedMapSettings.getHeight() * this.zoomScale);
         minV = 0.0F;
         if (maxV < 0.0F) {
            maxV = 0.0F;
            mapYMax_W = mapYMin_W;
         }
      }

      if (maxV > 1.0F) {
         mapYMax_W = this.viewportYMax - Math.round((maxV - 1.0F) * (float)this.loadedMapSettings.getHeight() * this.zoomScale);
         maxV = 1.0F;
         if (minV > 1.0F) {
            minV = 1.0F;
            mapYMin_W = mapYMax_W;
         }
      }

      MapImageTextures.drawMap(matStack, this.field_230706_i_.field_71439_g, sepia, (float)mapXMin_W, (float)mapXMax_W, (float)mapYMin_W, (float)mapYMax_W, this.func_230927_p_(), minU, maxU, minV, maxV, alpha);
      if (overlay) {
         MapImageTextures.drawMapOverlay(matStack, this.field_230706_i_.field_71439_g, (float)this.viewportXMin, (float)this.viewportXMax, (float)this.viewportYMin, (float)this.viewportYMax, (float)this.func_230927_p_(), minU, maxU, minV, maxV);
      }

   }

   private void renderGraduatedRects(MatrixStack matStack, int x1, int y1, int x2, int y2, int c1, int c2, int w) {
      float[] rgb1 = (new Color(c1)).getColorComponents((float[])null);
      float[] rgb2 = (new Color(c2)).getColorComponents((float[])null);

      for(int l = w - 1; l >= 0; --l) {
         float f = (float)l / (float)(w - 1);
         float r = rgb1[0] + (rgb2[0] - rgb1[0]) * f;
         float g = rgb1[1] + (rgb2[1] - rgb1[1]) * f;
         float b = rgb1[2] + (rgb2[2] - rgb1[2]) * f;
         int color = LOTRClientUtil.getRGBA((new Color(r, g, b)).getRGB(), 1.0F);
         func_238467_a_(matStack, x1 - l, y1 - l, x2 + l, y2 + l, color);
      }

   }

   private void renderMapWidgets(MatrixStack matStack, int mouseX, int mouseY) {
      boolean fineZoom = this.isFineMapMovement();
      boolean quickZoom = this.isQuickMapMovement();
      this.widgetZoomIn.visible = !this.hasOverlay;
      this.widgetZoomIn.setTexVOffset(zoomPower < 4.0F ? 0 : 1);
      this.widgetZoomIn.setTooltip(quickZoom ? "zoomIn.quick" : (fineZoom ? "zoomIn.fine" : "zoomIn"));
      this.widgetZoomOut.visible = !this.hasOverlay;
      this.widgetZoomOut.setTexVOffset(zoomPower > -3.0F ? 0 : 1);
      this.widgetZoomOut.setTooltip(quickZoom ? "zoomOut.quick" : (fineZoom ? "zoomOut.fine" : "zoomOut"));
      this.widgetFullScreen.visible = !this.hasOverlay;
      this.widgetSepia.visible = !this.hasOverlay;
      this.widgetLabels.visible = !this.hasOverlay;
      this.widgetToggleMapWPs.visible = !this.hasOverlay;
      this.widgetToggleMapWPs.setTexVOffset(this.showMapWaypoints() ? 0 : 1);
      this.widgetToggleCustomWPs.visible = !this.hasOverlay;
      this.widgetToggleCustomWPs.setTexVOffset(this.showCustomWaypoints() ? 0 : 1);
      this.widgetToggleMarkers.visible = !this.hasOverlay;
      this.widgetToggleMarkers.setTexVOffset(this.showMarkers() ? 0 : 1);
      this.widgetNewMarker.visible = !this.hasOverlay;
      this.widgetNewMarker.setTexVOffset(this.canCreateNewMarker() ? 0 : 1);
      int numMarkers = (Integer)this.getOptClientPlayerData().map((pd) -> {
         return pd.getMapMarkerData().getMarkers().size();
      }).orElse(0);
      if (numMarkers > 0) {
         this.widgetNewMarker.setTooltip("newMarker.count", numMarkers, 64);
      } else {
         this.widgetNewMarker.setTooltip("newMarker");
      }

      this.widgetToggleShowLocation.visible = !this.hasOverlay;
      this.widgetToggleShowLocation.setTexUOffset(this.showLocationToOthers() ? 0 : 1);
      this.widgetToggleShowLocation.setTooltip(this.showLocationToOthers() ? "toggleShowLocation.shown" : "toggleShowLocation.hidden");
      MapWidget mouseOverWidget = null;
      Iterator var8 = this.mapWidgets.iterator();

      while(var8.hasNext()) {
         MapWidget widget = (MapWidget)var8.next();
         if (widget.visible) {
            this.field_230706_i_.func_110434_K().func_110577_a(MapImageTextures.MAP_ICONS);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.func_238474_b_(matStack, widget.getXPos(), widget.getYPos(), widget.getTexU(), widget.getTexV(), widget.width, widget.width);
            if (widget.func_231047_b_((double)mouseX, (double)mouseY)) {
               mouseOverWidget = widget;
            }
         }
      }

      if (mouseOverWidget != null) {
         int stringWidth = 200;
         List desc = this.field_230712_o_.func_238425_b_(mouseOverWidget.getTooltip(), stringWidth);
         this.func_238654_b_(matStack, desc, mouseX, mouseY);
      }

   }

   private void renderFullscreenSubtitles(MatrixStack matStack, ITextComponent... lines) {
      int strX = this.viewportXMin + this.viewportWidth / 2;
      int strY = this.viewportYMax + 7;
      this.field_230712_o_.getClass();
      int border = 9 + 3;
      if (lines.length == 1) {
         strY += border / 2;
      }

      ITextComponent[] var6 = lines;
      int var7 = lines.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         ITextComponent s = var6[var8];
         this.drawCenteredStringNoShadow(matStack, this.field_230712_o_, s, strX, strY, 16777215);
         strY += border;
      }

   }

   private void renderInFront(Runnable renderer) {
      RenderSystem.pushMatrix();
      RenderSystem.translatef(0.0F, 0.0F, 300.0F);
      renderer.run();
      RenderSystem.popMatrix();
   }

   private void renderPlayers(MatrixStack matStack, int mouseX, int mouseY, float tick) {
      String mouseOverPlayerName = null;
      double mouseOverPlayerX = 0.0D;
      double mouseOverPlayerY = 0.0D;
      double distanceMouseOverPlayer = Double.MAX_VALUE;
      int iconWidthHalf = 4;
      Map playersToRender = new HashMap();
      playersToRender.putAll(MapPlayerLocationHolder.getPlayerLocations());
      if (this.isMiddleEarth()) {
         playersToRender.put(this.field_230706_i_.field_71439_g.func_110124_au(), MapPlayerLocation.ofPlayer(this.field_230706_i_.field_71439_g));
      }

      Iterator var14 = playersToRender.entrySet().iterator();

      while(var14.hasNext()) {
         Entry entry = (Entry)var14.next();
         MapPlayerLocation info = (MapPlayerLocation)entry.getValue();
         GameProfile profile = info.profile;
         String playerName = profile.getName();
         double playerPosX = info.posX;
         double playerPosZ = info.posZ;
         double[] pos = this.transformWorldCoords(playerPosX, playerPosZ, tick);
         float playerX = (float)pos[0];
         float playerY = (float)pos[1];
         float distToPlayer = this.renderPlayerIconAndReturnDistance(matStack, profile, playerX, playerY, mouseX, mouseY);
         ++this.renderedMapObjects;
         if (distToPlayer <= (float)(iconWidthHalf + 3) && (double)distToPlayer <= distanceMouseOverPlayer) {
            mouseOverPlayerName = playerName;
            mouseOverPlayerX = (double)playerX;
            mouseOverPlayerY = (double)playerY;
            distanceMouseOverPlayer = (double)distToPlayer;
         }
      }

      if (mouseOverPlayerName != null && !this.hasOverlay) {
         this.renderInFront(() -> {
            int strWidth = this.field_230712_o_.func_78256_a(mouseOverPlayerName);
            this.field_230712_o_.getClass();
            int strHeight = 9;
            int rectX = (int)Math.round(mouseOverPlayerX);
            int rectY = (int)Math.round(mouseOverPlayerY);
            rectY += iconWidthHalf + 3;
            int border = 3;
            int rectWidth = strWidth + border * 2;
            rectX -= rectWidth / 2;
            int rectHeight = strHeight + border * 2;
            int mapBorder2 = 2;
            rectX = Math.max(rectX, this.viewportXMin + mapBorder2);
            rectX = Math.min(rectX, this.viewportXMax - mapBorder2 - rectWidth);
            rectY = Math.max(rectY, this.viewportYMin + mapBorder2);
            rectY = Math.min(rectY, this.viewportYMax - mapBorder2 - rectHeight);
            this.drawFancyRect(matStack, rectX, rectY, rectX + rectWidth, rectY + rectHeight);
            this.field_230712_o_.func_243248_b(matStack, new StringTextComponent(mouseOverPlayerName), (float)(rectX + border), (float)(rectY + border), 16777215);
         });
      }

   }

   private float renderPlayerIconAndReturnDistance(MatrixStack matStack, GameProfile profile, float playerX, float playerY, int mouseX, int mouseY) {
      NetworkPlayerInfo playerInfo = this.field_230706_i_.func_147114_u().func_175102_a(profile.getId());
      if (playerInfo != null && playerInfo.func_178856_e()) {
         ResourceLocation skin = playerInfo.func_178837_g();
         Matrix4f mat = matStack.func_227866_c_().func_227870_a_();
         Tessellator tess = Tessellator.func_178181_a();
         BufferBuilder buf = tess.func_178180_c();
         int iconWidthHalf = 4;
         int iconBorder = iconWidthHalf + 1;
         playerX = Math.max((float)(this.viewportXMin + iconBorder), playerX);
         playerX = Math.min((float)(this.viewportXMax - iconBorder - 1), playerX);
         playerY = Math.max((float)(this.viewportYMin + iconBorder), playerY);
         playerY = Math.min((float)(this.viewportYMax - iconBorder - 1), playerY);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.enableAlphaTest();
         this.field_230706_i_.func_110434_K().func_110577_a(skin);
         float skinWidth = 64.0F;
         float skinHeight = 64.0F;
         float iconMinU = 8.0F / skinWidth;
         float iconMaxU = 16.0F / skinWidth;
         float iconMinV = 8.0F / skinHeight;
         float iconMaxV = 16.0F / skinHeight;
         float playerX_f = playerX + 0.5F;
         float playerY_f = playerY + 0.5F;
         int z = this.func_230927_p_();
         buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         buf.func_227888_a_(mat, playerX_f - (float)iconWidthHalf, playerY_f + (float)iconWidthHalf, (float)z).func_225583_a_(iconMinU, iconMaxV).func_181675_d();
         buf.func_227888_a_(mat, playerX_f + (float)iconWidthHalf, playerY_f + (float)iconWidthHalf, (float)z).func_225583_a_(iconMaxU, iconMaxV).func_181675_d();
         buf.func_227888_a_(mat, playerX_f + (float)iconWidthHalf, playerY_f - (float)iconWidthHalf, (float)z).func_225583_a_(iconMaxU, iconMinV).func_181675_d();
         buf.func_227888_a_(mat, playerX_f - (float)iconWidthHalf, playerY_f - (float)iconWidthHalf, (float)z).func_225583_a_(iconMinU, iconMinV).func_181675_d();
         tess.func_78381_a();
         iconMinU = 40.0F / skinWidth;
         iconMaxU = 48.0F / skinWidth;
         iconMinV = 8.0F / skinHeight;
         iconMaxV = 16.0F / skinHeight;
         buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         buf.func_227888_a_(mat, playerX_f - (float)iconWidthHalf - 0.5F, playerY_f + (float)iconWidthHalf + 0.5F, (float)z).func_225583_a_(iconMinU, iconMaxV).func_181675_d();
         buf.func_227888_a_(mat, playerX_f + (float)iconWidthHalf + 0.5F, playerY_f + (float)iconWidthHalf + 0.5F, (float)z).func_225583_a_(iconMaxU, iconMaxV).func_181675_d();
         buf.func_227888_a_(mat, playerX_f + (float)iconWidthHalf + 0.5F, playerY_f - (float)iconWidthHalf - 0.5F, (float)z).func_225583_a_(iconMaxU, iconMinV).func_181675_d();
         buf.func_227888_a_(mat, playerX_f - (float)iconWidthHalf - 0.5F, playerY_f - (float)iconWidthHalf - 0.5F, (float)z).func_225583_a_(iconMinU, iconMinV).func_181675_d();
         tess.func_78381_a();
         RenderSystem.disableAlphaTest();
         float dx = playerX - (float)mouseX;
         float dy = playerY - (float)mouseY;
         float distToPlayer = MathHelper.func_76129_c(dx * dx + dy * dy);
         return distToPlayer;
      } else {
         return Float.MAX_VALUE;
      }
   }

   private void renderAreasOfInfluence(MatrixStack matStack, int mouseX, int mouseY, float tick) {
      this.mouseInAreaOfInfluence = false;
      this.mouseInReducedAreaOfInfluence = false;
      Faction faction = this.areaOfInfluenceFaction;
      if (LOTRDimensions.isDimension(faction, LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)) {
         List areasOfInfluence = faction.getAreasOfInfluence().getAreas();
         if (!areasOfInfluence.isEmpty()) {
            Tessellator tess = Tessellator.func_178181_a();
            BufferBuilder buf = tess.func_178180_c();
            this.setupMapClipping();
            RenderSystem.disableTexture();

            label64:
            for(int pass = 0; pass <= 2; ++pass) {
               int areaRgb = 16711680;
               if (pass == 1) {
                  areaRgb = 5570560;
               }

               if (pass == 0) {
                  areaRgb = 16733525;
               }

               Color areaColor = new Color(areaRgb);
               Iterator var12 = areasOfInfluence.iterator();

               while(true) {
                  AreaOfInfluence area;
                  float radius;
                  do {
                     if (!var12.hasNext()) {
                        continue label64;
                     }

                     area = (AreaOfInfluence)var12.next();
                     radius = (float)area.getMapRadius();
                     if (pass == 2) {
                        --radius;
                     }

                     if (pass == 0) {
                        radius = (float)(area.getMapRadius() + faction.getAreasOfInfluence().getReducedInfluenceRange());
                     }

                     float radiusWorld = (float)this.loadedMapSettings.mapToWorldDistance((double)radius);
                     buf.func_181668_a(9, DefaultVertexFormats.field_181706_f);
                     int sides = 100;

                     for(int l = sides - 1; l >= 0; --l) {
                        float angle = (float)l / (float)sides * 2.0F * 3.1415927F;
                        double x = area.getWorldX();
                        double z = area.getWorldZ();
                        x += (double)(MathHelper.func_76134_b(angle) * radiusWorld);
                        z += (double)(MathHelper.func_76126_a(angle) * radiusWorld);
                        double[] coords = this.transformWorldCoords(x, z, tick);
                        buf.func_225582_a_(coords[0], coords[1], (double)this.func_230927_p_()).func_225586_a_(areaColor.getRed(), areaColor.getGreen(), areaColor.getBlue(), areaColor.getAlpha()).func_181675_d();
                     }

                     tess.func_78381_a();
                  } while(this.mouseInAreaOfInfluence && this.mouseInReducedAreaOfInfluence);

                  double[] coords = this.transformWorldCoords(area.getWorldX(), area.getWorldZ(), tick);
                  double dx = (double)mouseX - coords[0];
                  double dy = (double)mouseY - coords[1];
                  float rScaled = radius * this.zoomScale;
                  if (dx * dx + dy * dy <= (double)(rScaled * rScaled)) {
                     if (pass >= 1) {
                        this.mouseInAreaOfInfluence = true;
                     } else if (pass == 0) {
                        this.mouseInReducedAreaOfInfluence = true;
                     }
                  }
               }
            }

            RenderSystem.enableTexture();
            this.endMapClipping();
         }
      }

   }

   private void renderRoads(MatrixStack matStack, float tick) {
      if (this.showMapWaypoints() || this.showCustomWaypoints()) {
         this.renderRoads(matStack, tick, this.hasMapLabels());
      }
   }

   public void renderRoads(MatrixStack matStack, float tick, boolean labels) {
      float roadAlpha = this.calcZoomedWaypointAlpha();
      if (roadAlpha > 0.0F) {
         float mapScale = (float)this.loadedMapSettings.getScaleFactor();
         int interval = Math.round(mapScale * 3.125F / this.zoomScaleStable);
         interval = Math.max(interval, 1);
         Iterator var7 = this.loadedMapSettings.getRoads().iterator();

         while(var7.hasNext()) {
            Road road = (Road)var7.next();
            Iterator var9 = road.getSections().iterator();

            while(var9.hasNext()) {
               RoadSection section = (RoadSection)var9.next();
               RoadPoint[] sectionPoints = section.getRoutePoints();

               for(int pointIndex = 0; pointIndex < sectionPoints.length; pointIndex += interval) {
                  RoadPoint point = sectionPoints[pointIndex];
                  double[] pos = this.transformMapCoords(point.getMapX(), point.getMapZ(), tick);
                  float x = (float)pos[0];
                  float y = (float)pos[1];
                  float roadWidth = 1.0F;
                  float roadWidthLess1 = roadWidth - 1.0F;
                  float x0 = x - roadWidthLess1;
                  float x1 = x + roadWidth;
                  float y0 = y - roadWidthLess1;
                  float y1 = y + roadWidth;
                  float zoomlerp;
                  float roadNameScale;
                  if (x0 >= (float)this.viewportXMin && x1 <= (float)this.viewportXMax && y0 >= (float)this.viewportYMin && y1 <= (float)this.viewportYMax) {
                     float roadR = 0.0F;
                     zoomlerp = 0.0F;
                     roadNameScale = 0.0F;
                     float z = (float)this.func_230927_p_();
                     RenderSystem.disableTexture();
                     RenderSystem.enableBlend();
                     RenderSystem.defaultBlendFunc();
                     Matrix4f mat = matStack.func_227866_c_().func_227870_a_();
                     Tessellator tess = Tessellator.func_178181_a();
                     BufferBuilder buf = tess.func_178180_c();
                     buf.func_181668_a(7, DefaultVertexFormats.field_181706_f);
                     buf.func_227888_a_(mat, x0, y1, z).func_227885_a_(roadR, zoomlerp, roadNameScale, roadAlpha).func_181675_d();
                     buf.func_227888_a_(mat, x1, y1, z).func_227885_a_(roadR, zoomlerp, roadNameScale, roadAlpha).func_181675_d();
                     buf.func_227888_a_(mat, x1, y0, z).func_227885_a_(roadR, zoomlerp, roadNameScale, roadAlpha).func_181675_d();
                     buf.func_227888_a_(mat, x0, y0, z).func_227885_a_(roadR, zoomlerp, roadNameScale, roadAlpha).func_181675_d();
                     tess.func_78381_a();
                     ++this.renderedMapObjects;
                     RenderSystem.disableBlend();
                     RenderSystem.enableTexture();
                  }

                  if (labels) {
                     int clip = 100;
                     if (x >= (float)(this.viewportXMin - clip) && x <= (float)(this.viewportXMax + clip) && y >= (float)(this.viewportYMin - clip) && y <= (float)(this.viewportYMax + clip)) {
                        zoomlerp = (this.zoomExp - -1.0F) / 4.0F;
                        zoomlerp = Math.min(zoomlerp, 1.0F);
                        roadNameScale = zoomlerp * 0.75F;
                        ITextComponent name = road.getDisplayName();
                        int roadNameWidth = this.field_230712_o_.func_238414_a_(name);
                        int nameInterval = (int)((float)(roadNameWidth * 2 + 200) * mapScale * 0.78125F / this.zoomScaleStable);
                        if (pointIndex % nameInterval < interval) {
                           boolean endNear = false;
                           RouteRoadPoint[] var30 = section.getStartAndEndPoints();
                           int var31 = var30.length;

                           for(int var32 = 0; var32 < var31; ++var32) {
                              RouteRoadPoint end = var30[var32];
                              int endpointOverlapDistance = 10;
                              MapWaypoint endWp = end.getCorrespondingWaypoint();
                              if (endWp != null) {
                                 ITextComponent endWpName = endWp.getDisplayName();
                                 int endWpNameWidth = this.field_230712_o_.func_238414_a_(endWpName);
                                 endpointOverlapDistance = endWpNameWidth / 2 + 10;
                              }

                              double overlapWidth = (double)roadNameWidth / 2.0D * (double)roadNameScale + (double)endpointOverlapDistance;
                              double overlapWidthSq = overlapWidth * overlapWidth;
                              double[] endPos = this.transformMapCoords(end.getMapX(), end.getMapZ(), tick);
                              double endX = endPos[0];
                              double endY = endPos[1];
                              double dx = (double)x - endX;
                              double dy = (double)y - endY;
                              double dSq = dx * dx + dy * dy;
                              if (dSq < overlapWidthSq) {
                                 endNear = true;
                              }
                           }

                           if (!endNear && zoomlerp > 0.0F) {
                              this.setupMapClipping();
                              matStack.func_227860_a_();
                              matStack.func_227861_a_((double)x, (double)y, 0.0D);
                              matStack.func_227862_a_(roadNameScale, roadNameScale, roadNameScale);
                              RoadPoint nextPoint = sectionPoints[Math.min(pointIndex + 1, sectionPoints.length - 1)];
                              RoadPoint prevPoint = sectionPoints[Math.max(pointIndex - 1, 0)];
                              double grad = (nextPoint.getMapZ() - prevPoint.getMapZ()) / (nextPoint.getMapX() - prevPoint.getMapX());
                              float angle = (float)Math.atan(grad);
                              angle = (float)Math.toDegrees((double)angle);
                              if (Math.abs(angle) > 90.0F) {
                                 angle += 180.0F;
                              }

                              matStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(angle));
                              float alpha = zoomlerp * 0.8F;
                              RenderSystem.enableBlend();
                              RenderSystem.defaultBlendFunc();
                              int strX = -roadNameWidth / 2;
                              int strY = -15;
                              this.field_230712_o_.func_243248_b(matStack, name, (float)(strX + 1), (float)(strY + 1), LOTRClientUtil.getRGBAForFontRendering(0, alpha));
                              this.field_230712_o_.func_243248_b(matStack, name, (float)strX, (float)strY, LOTRClientUtil.getRGBAForFontRendering(16777215, alpha));
                              RenderSystem.disableBlend();
                              matStack.func_227865_b_();
                              this.endMapClipping();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private boolean hasFogOfWar() {
      return this.field_230706_i_.field_71441_e != null && FogDataModule.isFogOfWarEnabledClientside();
   }

   public void renderFogOfWar(MatrixStack matStack, float tick) {
      if (this.hasFogOfWar()) {
         this.getOptClientPlayerData().ifPresent((playerData) -> {
            IProfiler profiler = this.field_230706_i_.func_213239_aq();
            profiler.func_76320_a("renderFogOfWar");
            double[] minMapCoords = this.convertMouseCoordsToMapCoords(this.viewportXMin, this.viewportYMin, tick);
            double[] maxMapCoords = this.convertMouseCoordsToMapCoords(this.viewportXMax, this.viewportYMax, tick);
            double fogMapXMin = minMapCoords[0];
            double fogMapXMax = maxMapCoords[0];
            double fogMapZMin = minMapCoords[1];
            double fogMapZMax = maxMapCoords[1];
            this.field_230706_i_.func_110434_K().func_110577_a(MapImageTextures.FOG_OF_WAR_TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            profiler.func_76320_a("iterateTiles");
            playerData.getFogData().streamTilesForRendering(fogMapXMin, fogMapXMax, fogMapZMin, fogMapZMax, profiler).forEach((tile) -> {
               profiler.func_76320_a("transformCoords");
               double[] leftTop = this.transformMapCoords((double)tile.getMapLeft(), (double)tile.getMapTop(), tick);
               double[] rightBottom = this.transformMapCoords((double)tile.getMapRight(), (double)tile.getMapBottom(), tick);
               profiler.func_76319_b();
               float initX0 = (float)leftTop[0];
               float initX1 = (float)rightBottom[0];
               float initY0 = (float)leftTop[1];
               float initY1 = (float)rightBottom[1];
               float x0 = Math.max(initX0, (float)this.viewportXMin);
               float x1 = Math.min(initX1, (float)this.viewportXMax);
               float y0 = Math.max(initY0, (float)this.viewportYMin);
               float y1 = Math.min(initY1, (float)this.viewportYMax);
               int texSize = 256;
               int tileSize = tile.getSize();
               int tileU;
               int tileV;
               int randTexture;
               if (tile.isThickFog()) {
                  randTexture = tile.getPositionalHash() % 4;
                  tileU = randTexture % 4 * tileSize;
                  tileV = (randTexture / 4 + 2) * tileSize;
               } else {
                  randTexture = tile.getPositionalHash() % 7;
                  tileU = randTexture % 4 * tileSize;
                  tileV = randTexture / 4 * tileSize;
               }

               float initWidth = initX1 - initX0;
               float initHeight = initY1 - initY0;
               float u0 = ((float)tileU + (x0 - initX0) / initWidth * (float)tileSize) / (float)texSize;
               float u1 = ((float)tileU + (x1 - initX0) / initWidth * (float)tileSize) / (float)texSize;
               float v0 = ((float)tileV + (y0 - initY0) / initHeight * (float)tileSize) / (float)texSize;
               float v1 = ((float)tileV + (y1 - initY0) / initHeight * (float)tileSize) / (float)texSize;
               profiler.func_76320_a("drawFogTile");
               this.renderFogOfWarTile(matStack, x0, x1, y0, y1, u0, u1, v0, v1);
               ++this.renderedMapObjects;
               profiler.func_76319_b();
            });
            profiler.func_76319_b();
            RenderSystem.disableBlend();
            profiler.func_76319_b();
         });
      }
   }

   private void renderFogOfWarTile(MatrixStack matStack, float x0, float x1, float y0, float y1, float u0, float u1, float v0, float v1) {
      float z = (float)this.func_230927_p_();
      Matrix4f mat = matStack.func_227866_c_().func_227870_a_();
      Tessellator tess = Tessellator.func_178181_a();
      BufferBuilder buf = tess.func_178180_c();
      buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      buf.func_227888_a_(mat, x0, y1, z).func_225583_a_(u0, v1).func_181675_d();
      buf.func_227888_a_(mat, x1, y1, z).func_225583_a_(u1, v1).func_181675_d();
      buf.func_227888_a_(mat, x1, y0, z).func_225583_a_(u1, v0).func_181675_d();
      buf.func_227888_a_(mat, x0, y0, z).func_225583_a_(u0, v0).func_181675_d();
      tess.func_78381_a();
   }

   private boolean isPositionFogged(double mapX, double mapZ) {
      return !this.hasFogOfWar() ? false : this.getClientPlayerData().getFogData().isFogged(MathHelper.func_76128_c(mapX), MathHelper.func_76128_c(mapZ));
   }

   private void selectObject(SelectableMapObject object) {
      this.selectedObject = object;
      if (this.selectedObject != null) {
         this.objectSelectTick = 6;
      } else {
         this.objectSelectTick = 0;
      }

      this.prevObjectSelectTick = this.objectSelectTick;
      this.waypointTooltip.onSelect(this.selectedObject instanceof Waypoint ? (Waypoint)this.selectedObject : null);
      this.markerTooltip.onSelect(this.selectedObject instanceof MapMarker ? (MapMarker)this.selectedObject : null);
      if (this.selectedObject instanceof Waypoint) {
         playWaypointSelectSound();
      } else if (this.selectedObject instanceof MapMarker) {
         playMarkerSelectSound();
      }

   }

   private void determineMouseOverObject(int mouseX, int mouseY, float tick) {
      this.mouseOverObject = null;
      double distanceMouseOverObject = Double.MAX_VALUE;
      List visibleObjects = new ArrayList();
      visibleObjects.addAll(this.getVisibleWaypoints());
      visibleObjects.addAll(this.getVisibleMarkers());
      Iterator var7 = visibleObjects.iterator();

      while(var7.hasNext()) {
         SelectableMapObject object = (SelectableMapObject)var7.next();
         double[] pos = this.transformWorldCoords((double)object.getWorldX(), (double)object.getWorldZ(), tick);
         double x = pos[0];
         double y = pos[1];
         if (object != this.selectedObject) {
            int halfW = object.getMapIconWidth() / 2;
            if (x >= (double)(this.viewportXMin - halfW) && x <= (double)(this.viewportXMax + halfW) && y >= (double)(this.viewportYMin - halfW) && y <= (double)(this.viewportYMax + halfW)) {
               double dx = x - (double)mouseX;
               double dy = y - (double)mouseY;
               double distToObject = Math.sqrt(dx * dx + dy * dy);
               if (distToObject <= 5.0D && distToObject <= distanceMouseOverObject) {
                  this.mouseOverObject = object;
                  distanceMouseOverObject = distToObject;
               }
            }
         }
      }

   }

   private boolean isWaypointVisible(Waypoint wp) {
      if (wp.getDisplayState(this.field_230706_i_.field_71439_g) == Waypoint.WaypointDisplayState.HIDDEN) {
         return false;
      } else if (this.isPositionFogged(wp.getMapX(), wp.getMapZ()) && !wp.hasPlayerUnlocked(this.field_230706_i_.field_71439_g)) {
         return false;
      } else if (wp.isCustom()) {
         return wp.isSharedCustom() && wp.isSharedHidden() && !this.showHiddenSharedCustomWaypoints() ? false : this.showCustomWaypoints();
      } else {
         return this.showMapWaypoints();
      }
   }

   private void renderWaypoints(MatrixStack matStack, int pass, int mouseX, int mouseY, float tick) {
      if (this.showMapWaypoints() || this.showCustomWaypoints() || this.showHiddenSharedCustomWaypoints()) {
         this.renderWaypoints(matStack, pass, mouseX, mouseY, tick, this.hasMapLabels());
      }
   }

   private List getVisibleWaypoints() {
      Stream mapWps = this.loadedMapSettings.getWaypoints().stream();
      Stream customWps = (Stream)this.getOptClientPlayerData().map((pd) -> {
         return pd.getFastTravelData().getCustomWaypoints().stream();
      }).orElse(Stream.empty());
      Stream adoptedCustomWps = (Stream)this.getOptClientPlayerData().map((pd) -> {
         return pd.getFastTravelData().getAdoptedCustomWaypoints().stream();
      }).orElse(Stream.empty());
      return (List)Streams.concat(new Stream[]{mapWps, customWps, adoptedCustomWps}).filter(this::isWaypointVisible).collect(Collectors.toList());
   }

   public void renderWaypoints(MatrixStack matStack, int pass, int mouseX, int mouseY, float tick, boolean mapLabels) {
      this.renderWaypoints(matStack, this.getVisibleWaypoints(), pass, mouseX, mouseY, tick, mapLabels);
   }

   private void renderWaypoints(MatrixStack matStack, List waypoints, int pass, int mouseX, int mouseY, float tick, boolean mapLabels) {
      this.setupMapClipping();
      float wpAlpha = this.calcZoomedWaypointAlpha();
      if (wpAlpha > 0.0F) {
         Iterator var9 = waypoints.iterator();

         label76:
         while(true) {
            Waypoint waypoint;
            float x;
            float y;
            byte clip;
            do {
               do {
                  do {
                     do {
                        do {
                           if (!var9.hasNext()) {
                              break label76;
                           }

                           waypoint = (Waypoint)var9.next();
                           double[] pos = this.transformWorldCoords((double)waypoint.getWorldX(), (double)waypoint.getWorldZ(), tick);
                           x = (float)pos[0];
                           y = (float)pos[1];
                           clip = 100;
                        } while(x < (float)(this.viewportXMin - clip));
                     } while(x > (float)(this.viewportXMax + clip));
                  } while(y < (float)(this.viewportYMin - clip));
               } while(y > (float)(this.viewportYMax + clip));
            } while(pass != 0);

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            float zoomlerp;
            if (isOSRS()) {
               zoomlerp = 0.33F;
               matStack.func_227860_a_();
               matStack.func_227862_a_(0.33F, 0.33F, 1.0F);
               this.field_230706_i_.func_110434_K().func_110577_a(MapImageTextures.OSRS_ICONS);
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               LOTRClientUtil.blitFloat(this, matStack, x / 0.33F - 8.0F, y / 0.33F - 8.0F, 0.0F, 0.0F, 15.0F, 15.0F);
               matStack.func_227865_b_();
            } else {
               Waypoint.WaypointDisplayState state = waypoint.getDisplayState(this.field_230706_i_.field_71439_g);
               this.field_230706_i_.func_110434_K().func_110577_a(MapImageTextures.MAP_ICONS);
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, wpAlpha);
               boolean highlight = waypoint == this.mouseOverObject || waypoint == this.selectedObject;
               LOTRClientUtil.blitFloat(this, matStack, x - 3.0F, y - 3.0F, highlight ? (float)state.highlightIconU : (float)state.iconU, highlight ? (float)state.highlightIconV : (float)state.iconV, 6.0F, 6.0F);
            }

            ++this.renderedMapObjects;
            RenderSystem.disableBlend();
            if (mapLabels) {
               zoomlerp = (this.zoomExp - -1.0F) / 4.0F;
               zoomlerp = Math.min(zoomlerp, 1.0F);
               if (zoomlerp > 0.0F) {
                  matStack.func_227860_a_();
                  matStack.func_227861_a_((double)x, (double)y, 0.0D);
                  matStack.func_227862_a_(zoomlerp, zoomlerp, 1.0F);
                  float alpha = zoomlerp * 0.8F;
                  RenderSystem.enableBlend();
                  RenderSystem.defaultBlendFunc();
                  ITextComponent s = waypoint.getDisplayName();
                  int strX = -this.field_230712_o_.func_238414_a_(s) / 2;
                  int strY = -15;
                  this.field_230712_o_.func_243248_b(matStack, s, (float)(strX + 1), (float)(strY + 1), LOTRClientUtil.getRGBAForFontRendering(0, alpha));
                  this.field_230712_o_.func_243248_b(matStack, s, (float)strX, (float)strY, LOTRClientUtil.getRGBAForFontRendering(16777215, alpha));
                  RenderSystem.disableBlend();
                  matStack.func_227865_b_();
               }
            }
         }
      }

      if (pass == 1 && !this.hasOverlay && this.mouseOverObject instanceof Waypoint) {
         this.renderWaypointTooltip(matStack, (Waypoint)this.mouseOverObject, false, mouseX, mouseY, 1.0F);
      }

      this.endMapClipping();
   }

   private float calcZoomedWaypointAlpha() {
      if (this.enableZoomOutObjectFading) {
         float alpha = (this.zoomExp - -3.3F) / 2.2F;
         return Math.min(alpha, 1.0F);
      } else {
         return 1.0F;
      }
   }

   private void renderWaypointTooltip(MatrixStack matStack, Waypoint waypoint, boolean selected, int mouseX, int mouseY, float tick) {
      this.waypointTooltip.setMapDimensions(this.viewportXMin, this.viewportXMax, this.viewportYMin, this.viewportYMax);
      this.waypointTooltip.setSelectionProgress(this.prevObjectSelectTick, this.objectSelectTick, 6, tick);
      this.waypointTooltip.render(matStack, waypoint, selected, mouseX, mouseY, tick);
   }

   private void renderMarkerTooltip(MatrixStack matStack, MapMarker marker, boolean selected, int mouseX, int mouseY, float tick) {
      this.markerTooltip.setMapDimensions(this.viewportXMin, this.viewportXMax, this.viewportYMin, this.viewportYMax);
      this.markerTooltip.setSelectionProgress(this.prevObjectSelectTick, this.objectSelectTick, 6, tick);
      this.markerTooltip.render(matStack, marker, selected, mouseX, mouseY, tick);
   }

   private boolean canCreateNewMarker() {
      return this.field_230706_i_.field_71439_g != null && this.getClientPlayerData().getMapMarkerData().canCreateNewMarker();
   }

   private List getVisibleMarkers() {
      return (List)(!this.showMarkers() ? ImmutableList.of() : (List)this.getOptClientPlayerData().map((pd) -> {
         return pd.getMapMarkerData().getMarkers();
      }).orElse(ImmutableList.of()));
   }

   private void renderMarkers(MatrixStack matStack, int pass, int mouseX, int mouseY, float tick) {
      if (this.showMarkers()) {
         float markerAlpha = this.calcZoomedWaypointAlpha();
         if (markerAlpha > 0.0F) {
            this.setupMapClipping();
            List markers = this.getVisibleMarkers();
            Iterator var8 = markers.iterator();

            label59:
            while(true) {
               MapMarker marker;
               float x;
               float y;
               byte clip;
               do {
                  do {
                     do {
                        do {
                           do {
                              if (!var8.hasNext()) {
                                 this.endMapClipping();
                                 break label59;
                              }

                              marker = (MapMarker)var8.next();
                              double[] pos = this.transformWorldCoords((double)marker.getWorldX(), (double)marker.getWorldZ(), tick);
                              x = (float)pos[0];
                              y = (float)pos[1];
                              clip = 100;
                           } while(x < (float)(this.viewportXMin - clip));
                        } while(x > (float)(this.viewportXMax + clip));
                     } while(y < (float)(this.viewportYMin - clip));
                  } while(y > (float)(this.viewportYMax + clip));
               } while(pass != 0);

               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               boolean highlight = marker == this.mouseOverObject || marker == this.selectedObject;
               this.renderMarker(matStack, marker, x, y, highlight, markerAlpha);
               ++this.renderedMapObjects;
               RenderSystem.disableBlend();
            }
         }

         if (pass == 1 && !this.hasOverlay && this.mouseOverObject instanceof MapMarker) {
            this.renderMarkerTooltip(matStack, (MapMarker)this.mouseOverObject, false, mouseX, mouseY, 1.0F);
         }

      }
   }

   private void renderMarker(MatrixStack matStack, MapMarker marker, float markerX, float markerY, boolean mouseOver, float alpha) {
      int w = true;
      int halfW = true;
      int u = marker.getIcon().getU(mouseOver);
      int v = marker.getIcon().getV(mouseOver);
      this.field_230706_i_.func_110434_K().func_110577_a(MapImageTextures.MAP_ICONS);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
      LOTRClientUtil.blitFloat(this, matStack, markerX - 5.0F, markerY - 5.0F, (float)u, (float)v, 10.0F, 10.0F);
   }

   private void renderCreatingMarker(MatrixStack matStack, int mouseX, int mouseY) {
      int w = true;
      int halfW = true;
      int u = 0;
      int v = 236;
      int markerX = this.boundCreatingMarkerMouseX((double)mouseX);
      int markerZ = this.boundCreatingMarkerMouseZ((double)mouseY);
      if (!this.isCreatingMarkerAtValidMousePosition(markerX, markerZ)) {
         v -= 10;
      }

      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      this.field_230706_i_.func_110434_K().func_110577_a(MapImageTextures.MAP_ICONS);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.6F);
      LOTRClientUtil.blitFloat(this, matStack, (float)(markerX - 5), (float)(markerZ - 5), (float)u, (float)v, 10.0F, 10.0F);
      RenderSystem.disableBlend();
   }

   private int boundCreatingMarkerMouseX(double mouseX) {
      int halfW = true;
      int markerX = (int)mouseX;
      markerX = Math.max(this.viewportXMin + 5, markerX);
      markerX = Math.min(this.viewportXMax - 5, markerX);
      return markerX;
   }

   private int boundCreatingMarkerMouseZ(double mouseY) {
      int halfW = true;
      int markerZ = (int)mouseY;
      markerZ = Math.max(this.viewportYMin + 5, markerZ);
      markerZ = Math.min(this.viewportYMax - 5, markerZ);
      return markerZ;
   }

   private boolean isCreatingMarkerAtValidMousePosition(int mouseX, int mouseZ) {
      int[] worldCoords = this.getCreatingMarkerWorldCoords(mouseX, mouseZ);
      return MapMarker.isValidMapMarkerPosition(this.loadedMapSettings, worldCoords[0], worldCoords[1]);
   }

   private int[] getCreatingMarkerWorldCoords(int mouseX, int mouseZ) {
      double[] markerMapCoords = this.convertMouseCoordsToMapCoords(mouseX, mouseZ, 1.0F);
      int worldX = this.loadedMapSettings.mapToWorldX(markerMapCoords[0]);
      int worldZ = this.loadedMapSettings.mapToWorldZ(markerMapCoords[1]);
      return new int[]{worldX, worldZ};
   }

   private void renderLabels(MatrixStack matStack, float tick) {
      if (this.hasMapLabels()) {
         this.setupMapClipping();
         List labels = this.loadedMapSettings.getLabels();
         Iterator var4 = labels.iterator();

         while(true) {
            MapLabel label;
            double x;
            double y;
            float alpha;
            while(true) {
               float zoomlerp;
               do {
                  do {
                     if (!var4.hasNext()) {
                        this.endMapClipping();
                        return;
                     }

                     label = (MapLabel)var4.next();
                     double[] pos = this.transformMapCoords((double)label.getMapX(), (double)label.getMapZ(), tick);
                     x = pos[0];
                     y = pos[1];
                     zoomlerp = (this.zoomExp - label.getMinZoom()) / (label.getMaxZoom() - label.getMinZoom());
                  } while(zoomlerp <= 0.0F);
               } while(zoomlerp >= 1.0F);

               alpha = (0.5F - Math.abs(zoomlerp - 0.5F)) / 0.5F;
               alpha *= 0.7F;
               if (!isOSRS()) {
                  break;
               }

               if (alpha >= 0.3F) {
                  alpha = 1.0F;
                  break;
               }
            }

            matStack.func_227860_a_();
            matStack.func_227861_a_(x, y, 0.0D);
            float labelScale = this.zoomScale * label.getScale();
            matStack.func_227862_a_(labelScale, labelScale, labelScale);
            if (!isOSRS()) {
               matStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(label.getAngle()));
            }

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.alphaFunc(516, 0.01F);
            ITextComponent labelName = label.getDisplayName(this.field_230706_i_.field_71441_e);
            int strX = -this.field_230712_o_.func_238414_a_(labelName) / 2;
            this.field_230712_o_.getClass();
            int strY = -9 / 2;
            if (isOSRS()) {
               if (label.getScale() > 2.5F) {
                  this.field_230712_o_.func_243248_b(matStack, labelName, (float)(strX + 1), (float)(strY + 1), LOTRClientUtil.getRGBAForFontRendering(0, alpha));
                  this.field_230712_o_.func_243248_b(matStack, labelName, (float)strX, (float)strY, LOTRClientUtil.getRGBAForFontRendering(16755200, alpha));
               } else {
                  this.field_230712_o_.func_243248_b(matStack, labelName, (float)(strX + 1), (float)(strY + 1), LOTRClientUtil.getRGBAForFontRendering(0, alpha));
                  this.field_230712_o_.func_243248_b(matStack, labelName, (float)strX, (float)strY, LOTRClientUtil.getRGBAForFontRendering(16777215, alpha));
               }
            } else {
               this.field_230712_o_.func_243248_b(matStack, labelName, (float)strX, (float)strY, LOTRClientUtil.getRGBAForFontRendering(16777215, alpha));
            }

            ++this.renderedMapObjects;
            RenderSystem.defaultAlphaFunc();
            RenderSystem.disableBlend();
            matStack.func_227865_b_();
         }
      }
   }

   private boolean hasMapLabels() {
      return (Boolean)LOTRConfig.CLIENT.mapLabels.get();
   }

   private void toggleMapLabels() {
      LOTRConfig.CLIENT.toggleMapLabels();
   }

   private void setupMapClipping() {
      double scale = this.field_230706_i_.func_228018_at_().func_198100_s();
      GL11.glEnable(3089);
      GL11.glScissor((int)((double)this.viewportXMin * scale), (int)((double)(this.field_230709_l_ - this.viewportYMax) * scale), (int)((double)this.viewportWidth * scale), (int)((double)this.viewportHeight * scale));
   }

   private void endMapClipping() {
      GL11.glDisable(3089);
   }

   public void drawFancyRect(MatrixStack matStack, int x1, int y1, int x2, int y2) {
      func_238467_a_(matStack, x1, y1, x2, y2, -1073741824);
      this.func_238465_a_(matStack, x1 - 1, x2, y1 - 1, -6156032);
      this.func_238465_a_(matStack, x1 - 1, x2, y2, -6156032);
      this.func_238473_b_(matStack, x1 - 1, y1 - 1, y2, -6156032);
      this.func_238473_b_(matStack, x2, y1 - 1, y2, -6156032);
   }

   public boolean func_231046_a_(int key, int scan, int param3) {
      if (!this.hasOverlay) {
         if (this.selectedObject != null && key == 257) {
            this.selectObject((SelectableMapObject)null);
            return true;
         }

         if (this.selectedObject instanceof Waypoint) {
            if (this.waypointTooltip.keyPressed(key, scan, param3)) {
               return true;
            }

            if (this.waypointTooltip.isTextFieldFocused()) {
               return false;
            }
         } else if (this.selectedObject instanceof MapMarker) {
            if (this.markerTooltip.keyPressed(key, scan, param3)) {
               return true;
            }

            if (this.markerTooltip.isTextFieldFocused()) {
               return false;
            }
         }

         if (!this.isRecentringOnPlayer() && this.field_230706_i_.field_71474_y.field_74314_A.func_197976_a(key, scan)) {
            this.recentreMapOnPlayer();
            return true;
         }

         Waypoint selectedWp = this.selectedObject instanceof Waypoint ? (Waypoint)this.selectedObject : null;
         if (selectedWp != null && LOTRKeyHandler.getFastTravelKey(this.field_230706_i_).func_197976_a(key, scan) && this.isMiddleEarth()) {
            FastTravelDataModule ftData = this.getClientPlayerData().getFastTravelData();
            if (selectedWp.hasPlayerUnlocked(this.field_230706_i_.field_71439_g) && ftData.getTimeSinceFT() >= ftData.getWaypointFTTime(selectedWp, this.field_230706_i_.field_71439_g)) {
               CPacketFastTravel packet = new CPacketFastTravel(selectedWp);
               LOTRPacketHandler.sendToServer(packet);
               this.field_230706_i_.field_71439_g.func_71053_j();
               return true;
            }
         }

         if (selectedWp == null && LOTRKeyHandler.KEY_BIND_MAP_TELEPORT.func_197976_a(key, scan) && this.isMouseWithinMap && this.canTeleport()) {
            CPacketMapTp packet = new CPacketMapTp(this.mouseXCoord, this.mouseZCoord);
            LOTRPacketHandler.sendToServer(packet);
            this.field_230706_i_.field_71439_g.func_71053_j();
            return true;
         }

         if (this.hasAreasOfInfluence() && this.isEscapeOrInventoryKey(key, scan)) {
            this.field_230706_i_.func_147108_a(new MiddleEarthFactionsScreen());
            return true;
         }
      }

      return super.func_231046_a_(key, scan, param3);
   }

   private void recentreMapOnPlayer() {
      this.recentreToX = this.getPlayerMapPosX();
      this.recentreToY = this.getPlayerMapPosY();
      this.recentreFromX = this.posX;
      this.recentreFromY = this.posY;
      this.recentreTicks = 6;
      this.selectObject((SelectableMapObject)null);
   }

   private boolean isRecentringOnPlayer() {
      return this.recentreTicks > 0;
   }

   public boolean func_231042_a_(char c, int modifiers) {
      if (!this.hasOverlay) {
         if (this.selectedObject instanceof Waypoint) {
            if (this.waypointTooltip.charTyped(c, modifiers)) {
               return true;
            }
         } else if (this.selectedObject instanceof MapMarker && this.markerTooltip.charTyped(c, modifiers)) {
            return true;
         }
      }

      return super.func_231042_a_(c, modifiers);
   }

   private void handleMapKeyboardMovement() {
      this.posXMove = 0.0D;
      this.posYMove = 0.0D;
      if (!this.hasOverlay && !this.isRecentringOnPlayer()) {
         if (this.waypointTooltip.isTextFieldFocused() || this.markerTooltip.isTextFieldFocused()) {
            return;
         }

         float move = 12.0F / (float)Math.pow((double)this.zoomScale, 0.800000011920929D);
         if (this.isQuickMapMovement()) {
            move *= 2.0F;
         } else if (this.isFineMapMovement()) {
            move *= 0.5F;
         }

         if (this.isKeyboardKeyDown(this.field_230706_i_.field_71474_y.field_74370_x.getKey().func_197937_c()) || this.isKeyboardKeyDown(263)) {
            this.posXMove -= (double)move;
         }

         if (this.isKeyboardKeyDown(this.field_230706_i_.field_71474_y.field_74366_z.getKey().func_197937_c()) || this.isKeyboardKeyDown(262)) {
            this.posXMove += (double)move;
         }

         if (this.isKeyboardKeyDown(this.field_230706_i_.field_71474_y.field_74351_w.getKey().func_197937_c()) || this.isKeyboardKeyDown(265)) {
            this.posYMove -= (double)move;
         }

         if (this.isKeyboardKeyDown(this.field_230706_i_.field_71474_y.field_74368_y.getKey().func_197937_c()) || this.isKeyboardKeyDown(264)) {
            this.posYMove += (double)move;
         }

         if (this.posXMove != 0.0D || this.posYMove != 0.0D) {
            this.selectObject((SelectableMapObject)null);
         }
      }

   }

   private boolean isFineMapMovement() {
      return this.isKeyboardKeyDown(this.field_230706_i_.field_71474_y.field_228046_af_.getKey().func_197937_c());
   }

   private boolean isQuickMapMovement() {
      return this.isKeyboardKeyDown(this.field_230706_i_.field_71474_y.field_151444_V.getKey().func_197937_c());
   }

   private boolean isKeyboardKeyDown(int glfwKey) {
      return InputMappings.func_216506_a(this.field_230706_i_.func_228018_at_().func_198092_i(), glfwKey);
   }

   public boolean func_231044_a_(double x, double y, int code) {
      if (this.creatingMarker) {
         if (code == 0) {
            int markerX = this.boundCreatingMarkerMouseX(x);
            int markerZ = this.boundCreatingMarkerMouseZ(y);
            if (this.isCreatingMarkerAtValidMousePosition(markerX, markerZ)) {
               if (this.canCreateNewMarker()) {
                  int[] worldCoords = this.getCreatingMarkerWorldCoords(markerX, markerZ);
                  String defaultName = I18n.func_135052_a("gui.lotr.map.widget.newMarker", new Object[0]);
                  CPacketCreateMapMarker packet = new CPacketCreateMapMarker(worldCoords[0], worldCoords[1], defaultName);
                  LOTRPacketHandler.sendToServer(packet);
                  playMarkerUpdateSound();
               }

               this.creatingMarker = false;
               return true;
            }
         } else if (code == 1) {
            this.creatingMarker = false;
            return true;
         }
      } else if (this.mouseOverObject instanceof MapMarker) {
         MapMarker mouseOverMarker = (MapMarker)this.mouseOverObject;
         if (code == 1) {
            CPacketDeleteMapMarker packet = new CPacketDeleteMapMarker(mouseOverMarker);
            LOTRPacketHandler.sendToServer(packet);
            this.selectObject((SelectableMapObject)null);
            playMarkerSelectSound();
            return true;
         }
      }

      if (this.selectedObject instanceof Waypoint) {
         if (this.waypointTooltip.mouseClicked(x, y, code)) {
            return true;
         }
      } else if (this.selectedObject instanceof MapMarker && this.markerTooltip.mouseClicked(x, y, code)) {
         return true;
      }

      return super.func_231044_a_(x, y, code);
   }

   public static void playWaypointSelectSound() {
      Minecraft.func_71410_x().func_147118_V().func_147682_a(SimpleSound.func_184371_a(SoundEvents.field_187719_p, 1.0F));
   }

   public static void playMarkerUpdateSound() {
      Minecraft.func_71410_x().func_147118_V().func_147682_a(SimpleSound.func_184371_a(SoundEvents.field_219718_mk, 1.0F));
   }

   public static void playMarkerSelectSound() {
      Minecraft.func_71410_x().func_147118_V().func_147682_a(SimpleSound.func_184371_a(SoundEvents.field_187546_ae, 1.0F));
   }

   public boolean func_231043_a_(double x, double y, double scroll) {
      if (super.func_231043_a_(x, y, scroll)) {
         return true;
      } else {
         if (!this.hasOverlay && this.zoomTicks == 0) {
            if (scroll < 0.0D && zoomPower > -3.0F) {
               this.zoomOut();
               return true;
            }

            if (scroll > 0.0D && zoomPower < 4.0F) {
               this.zoomIn();
               return true;
            }
         }

         return false;
      }
   }

   private void zoomOut() {
      this.setZoom(zoomPower - this.getZoomIncrement());
   }

   private void zoomIn() {
      this.setZoom(zoomPower + this.getZoomIncrement());
   }

   private float getZoomIncrement() {
      return this.isQuickMapMovement() ? 1.5F : (this.isFineMapMovement() ? 0.25F : 1.0F);
   }

   private void setZoom(float newZoomPower) {
      this.prevZoomPower = zoomPower;
      zoomPower = MathHelper.func_76131_a(newZoomPower, -3.0F, 4.0F);
      this.prevZoomTicks = this.zoomTicks = 6;
      this.zoomingMessageDisplayTicks = 30;
      this.zoomingMessageIsZoomIn = zoomPower > this.prevZoomPower;
      this.selectObject((SelectableMapObject)null);
   }

   private ITextComponent getZoomingDisplayMessage() {
      int numLevels = 7;
      float currentRelativeLevel = zoomPower - -3.0F;
      return new TranslationTextComponent(this.zoomingMessageIsZoomIn ? "gui.lotr.map.zoomingIn" : "gui.lotr.map.zoomingOut", new Object[]{ZOOM_DISPLAY_FORMAT.format((double)currentRelativeLevel), Integer.valueOf(numLevels)});
   }

   private boolean isMiddleEarth() {
      return this.field_230706_i_.field_71439_g != null && LOTRDimensions.isDimension(this.field_230706_i_.field_71439_g.field_70170_p, LOTRDimensions.MIDDLE_EARTH_WORLD_KEY);
   }

   private boolean canTeleport() {
      if (!this.isMiddleEarth()) {
         return false;
      } else if (!this.field_230706_i_.field_71441_e.func_72863_F().func_217204_a(this.field_230706_i_.field_71439_g)) {
         return false;
      } else {
         this.requestIsOp();
         return this.isPlayerOp;
      }
   }

   private void requestIsOp() {
      if (!this.sentOpRequestPacket) {
         CPacketIsOpRequest packet = new CPacketIsOpRequest();
         LOTRPacketHandler.sendToServer(packet);
         this.sentOpRequestPacket = true;
      }

   }

   public void receiveIsOp(boolean isOp) {
      this.isPlayerOp = isOp;
   }

   public void setMapViewportAndPositionAndScale(int xMin, int xMax, int yMin, int yMax, double x, double y, float scale, float scaleExp, float scaleStable) {
      this.viewportXMin = xMin;
      this.viewportXMax = xMax;
      this.viewportYMin = yMin;
      this.viewportYMax = yMax;
      this.viewportWidth = this.viewportXMax - this.viewportXMin;
      this.viewportHeight = this.viewportYMax - this.viewportYMin;
      this.prevPosX = this.posX = x;
      this.prevPosY = this.posY = y;
      this.zoomScale = scale;
      this.zoomExp = scaleExp;
      this.zoomScaleStable = scaleStable;
      this.keepMapPositionWithinBounds();
   }

   private static boolean isOSRS() {
      return false;
   }

   static {
      QUEST_ICON = new ItemStack((IItemProvider)LOTRItems.RED_BOOK.get());
      fullscreen = true;
      zoomPower = 0.0F;
      ZOOM_DISPLAY_FORMAT = new DecimalFormat("0.##");
   }

   public class MapDragListener implements IGuiEventListener {
      private boolean mouseDown;

      public boolean func_231047_b_(double x, double y) {
         if (!MiddleEarthMapScreen.this.isMouseWithinMap) {
            return false;
         } else {
            return !MiddleEarthMapScreen.this.hasOverlay && !MiddleEarthMapScreen.this.isFacScrolling && !MiddleEarthMapScreen.this.isRecentringOnPlayer();
         }
      }

      public boolean func_231044_a_(double x, double y, int code) {
         if (code == 0 && this.func_231047_b_(x, y)) {
            this.mouseDown = true;
            MiddleEarthMapScreen.this.selectObject(MiddleEarthMapScreen.this.mouseOverObject);
            return true;
         } else {
            return false;
         }
      }

      public boolean func_231048_c_(double x, double y, int code) {
         if (code == 0) {
            this.mouseDown = false;
            return true;
         } else {
            return false;
         }
      }

      public boolean func_231045_a_(double x, double y, int code, double dx, double dy) {
         if (this.mouseDown && code == 0) {
            MiddleEarthMapScreen.this.posX = MiddleEarthMapScreen.this.posX - dx / (double)MiddleEarthMapScreen.this.zoomScale;
            MiddleEarthMapScreen.this.posY = MiddleEarthMapScreen.this.posY - dy / (double)MiddleEarthMapScreen.this.zoomScale;
            MiddleEarthMapScreen.this.keepMapPositionWithinBounds();
            if (dx != 0.0D || dy != 0.0D) {
               MiddleEarthMapScreen.this.selectObject((SelectableMapObject)null);
            }

            return true;
         } else {
            return false;
         }
      }
   }
}
