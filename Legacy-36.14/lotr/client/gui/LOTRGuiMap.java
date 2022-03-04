package lotr.client.gui;

import com.google.common.math.IntMath;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import lotr.client.LOTRClientProxy;
import lotr.client.LOTRKeyHandler;
import lotr.client.LOTRTextures;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.LOTRConfig;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRControlZone;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionRank;
import lotr.common.fellowship.LOTRFellowshipClient;
import lotr.common.network.LOTRPacketCWPSharedHide;
import lotr.common.network.LOTRPacketClientMQEvent;
import lotr.common.network.LOTRPacketConquestGridRequest;
import lotr.common.network.LOTRPacketCreateCWP;
import lotr.common.network.LOTRPacketDeleteCWP;
import lotr.common.network.LOTRPacketFastTravel;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketIsOpRequest;
import lotr.common.network.LOTRPacketMapTp;
import lotr.common.network.LOTRPacketRenameCWP;
import lotr.common.network.LOTRPacketShareCWP;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import lotr.common.world.map.LOTRAbstractWaypoint;
import lotr.common.world.map.LOTRConquestGrid;
import lotr.common.world.map.LOTRConquestZone;
import lotr.common.world.map.LOTRCustomWaypoint;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.chunk.EmptyChunk;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LOTRGuiMap extends LOTRGuiMenuBase {
   private static Map playerLocations = new HashMap();
   public static final ResourceLocation mapIconsTexture = new ResourceLocation("lotr:map/mapScreen.png");
   public static final ResourceLocation conquestTexture = new ResourceLocation("lotr:map/conquest.png");
   private static final ItemStack questBookIcon;
   public static final int BLACK = -16777216;
   public static final int BORDER_COLOR = -6156032;
   private static final int MIN_ZOOM = -3;
   private static final int MAX_ZOOM = 4;
   private static final int mapBorder = 30;
   private static boolean fullscreen;
   private static int mapWidth;
   private static int mapHeight;
   private static int mapXMin;
   private static int mapXMax;
   private static int mapYMin;
   private static int mapYMax;
   private static int mapXMin_W;
   private static int mapXMax_W;
   private static int mapYMin_W;
   private static int mapYMax_W;
   private static List mapWidgets;
   private LOTRGuiMapWidget widgetAddCWP;
   private LOTRGuiMapWidget widgetDelCWP;
   private LOTRGuiMapWidget widgetRenameCWP;
   private LOTRGuiMapWidget widgetToggleWPs;
   private LOTRGuiMapWidget widgetToggleCWPs;
   private LOTRGuiMapWidget widgetToggleHiddenSWPs;
   private LOTRGuiMapWidget widgetZoomIn;
   private LOTRGuiMapWidget widgetZoomOut;
   private LOTRGuiMapWidget widgetFullScreen;
   private LOTRGuiMapWidget widgetSepia;
   private LOTRGuiMapWidget widgetLabels;
   private LOTRGuiMapWidget widgetShareCWP;
   private LOTRGuiMapWidget widgetHideSWP;
   private LOTRGuiMapWidget widgetUnhideSWP;
   private float posX;
   private float posY;
   private int isMouseButtonDown;
   private int prevMouseX;
   private int prevMouseY;
   private boolean isMouseWithinMap;
   private int mouseXCoord;
   private int mouseZCoord;
   private float posXMove;
   private float posYMove;
   private float prevPosX;
   private float prevPosY;
   private static int zoomPower;
   private int prevZoomPower;
   private float zoomScale;
   private float zoomScaleStable;
   private float zoomExp;
   private static int zoomTicksMax;
   private int zoomTicks;
   public boolean enableZoomOutWPFading;
   private LOTRAbstractWaypoint selectedWaypoint;
   private static final int waypointSelectRange = 5;
   public static boolean showWP;
   public static boolean showCWP;
   public static boolean showHiddenSWP;
   private boolean hasOverlay;
   private String[] overlayLines;
   private GuiButton buttonOverlayFunction;
   private GuiTextField nameWPTextField;
   private boolean creatingWaypoint;
   private boolean creatingWaypointNew;
   private boolean deletingWaypoint;
   private boolean renamingWaypoint;
   private boolean sharingWaypoint;
   private boolean sharingWaypointNew;
   private LOTRGuiFellowships fellowshipDrawGUI;
   private LOTRFellowshipClient mouseOverRemoveSharedFellowship;
   private LOTRGuiScrollPane scrollPaneWPShares;
   private List displayedWPShareList;
   private static int maxDisplayedWPShares;
   private int displayedWPShares;
   public boolean isPlayerOp;
   private int tickCounter;
   private boolean hasControlZones;
   private LOTRFaction controlZoneFaction;
   private boolean mouseControlZone;
   private boolean mouseControlZoneReduced;
   private boolean isConquestGrid;
   private static final int conqBorderW = 8;
   private static final int conqBorderUp = 22;
   private static final int conqBorderDown = 54;
   private boolean loadingConquestGrid;
   private Map facConquestGrids;
   private Set requestedFacGrids;
   private Set receivedFacGrids;
   private int ticksUntilRequestFac;
   private static final int REQUEST_FAC_WAIT = 40;
   private float highestViewedConqStr;
   private static final int conqKeyGrades = 10;
   public static final int CONQUEST_COLOR = 12255232;
   private static final int CONQUEST_COLOR_OPQ = -4521984;
   private static final int CONQUEST_COLOR_NO_EFFECT = 1973790;
   private static LOTRDimension.DimensionRegion currentRegion;
   private static LOTRDimension.DimensionRegion prevRegion;
   private static List currentFactionList;
   private int currentFactionIndex;
   private int prevFactionIndex;
   private LOTRFaction conquestViewingFaction;
   private static Map lastViewedRegions;
   private float currentFacScroll;
   private boolean isFacScrolling;
   private boolean wasMouseDown;
   private boolean mouseInFacScroll;
   private int facScrollWidth;
   private int facScrollHeight;
   private int facScrollX;
   private int facScrollY;
   private int facScrollBorder;
   private int facScrollWidgetWidth;
   private int facScrollWidgetHeight;
   private GuiButton buttonConquestRegions;

   public static void addPlayerLocationInfo(GameProfile player, double x, double z) {
      if (player.isComplete()) {
         playerLocations.put(player.getId(), new LOTRGuiMap.PlayerLocationInfo(player, x, z));
      }

   }

   public static void clearPlayerLocations() {
      playerLocations.clear();
   }

   public LOTRGuiMap() {
      this.prevZoomPower = zoomPower;
      this.enableZoomOutWPFading = true;
      this.scrollPaneWPShares = new LOTRGuiScrollPane(9, 8);
      this.isPlayerOp = false;
      this.hasControlZones = false;
      this.isConquestGrid = false;
      this.loadingConquestGrid = false;
      this.facConquestGrids = new HashMap();
      this.requestedFacGrids = new HashSet();
      this.receivedFacGrids = new HashSet();
      this.ticksUntilRequestFac = 40;
      this.currentFactionIndex = 0;
      this.prevFactionIndex = 0;
      this.currentFacScroll = 0.0F;
      this.isFacScrolling = false;
      this.facScrollWidth = 240;
      this.facScrollHeight = 14;
      this.facScrollBorder = 1;
      this.facScrollWidgetWidth = 17;
      this.facScrollWidgetHeight = 12;
      if (!LOTRGenLayerWorld.loadedBiomeImage()) {
         new LOTRGenLayerWorld();
      }

   }

   public LOTRGuiMap setConquestGrid() {
      this.isConquestGrid = true;
      return this;
   }

   public void setControlZone(LOTRFaction f) {
      this.hasControlZones = true;
      this.controlZoneFaction = f;
   }

   public void func_73866_w_() {
      this.xSize = 256;
      this.ySize = 256;
      super.func_73866_w_();
      if (fullscreen) {
         int midX = this.field_146294_l / 2;
         int d = 100;
         this.buttonMenuReturn.field_146128_h = midX - d - this.buttonMenuReturn.field_146120_f;
         this.buttonMenuReturn.field_146129_i = 4;
      }

      if (this.isConquestGrid || this.hasControlZones) {
         this.field_146292_n.remove(this.buttonMenuReturn);
      }

      this.setupMapWidgets();
      if (this.isConquestGrid) {
         this.loadingConquestGrid = true;
         this.setupMapDimensions();
         this.conquestViewingFaction = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getPledgeFaction();
         if (this.conquestViewingFaction == null) {
            this.conquestViewingFaction = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getViewingFaction();
         }

         prevRegion = currentRegion = this.conquestViewingFaction.factionRegion;
         currentFactionList = currentRegion.factionList;
         this.prevFactionIndex = this.currentFactionIndex = currentFactionList.indexOf(this.conquestViewingFaction);
         lastViewedRegions.put(currentRegion, this.conquestViewingFaction);
         this.facScrollX = mapXMin;
         this.facScrollY = mapYMax + 8;
         this.setCurrentScrollFromFaction();
         this.field_146292_n.add(this.buttonConquestRegions = new LOTRGuiButtonRedBook(200, mapXMax - 120, mapYMax + 5, 120, 20, ""));
      }

      if (this.hasControlZones) {
         this.setupMapDimensions();
         int[] zoneBorders = this.controlZoneFaction.calculateFullControlZoneWorldBorders();
         int xMin = zoneBorders[0];
         int xMax = zoneBorders[1];
         int zMin = zoneBorders[2];
         int zMax = zoneBorders[3];
         float x = (float)(xMin + xMax) / 2.0F;
         float z = (float)(zMin + zMax) / 2.0F;
         this.posX = x / (float)LOTRGenLayerWorld.scale + 810.0F;
         this.posY = z / (float)LOTRGenLayerWorld.scale + 730.0F;
         int zoneWidth = xMax - xMin;
         int zoneHeight = zMax - zMin;
         double mapZoneWidth = (double)zoneWidth / (double)LOTRGenLayerWorld.scale;
         double mapZoneHeight = (double)zoneHeight / (double)LOTRGenLayerWorld.scale;
         int zoomPowerWidth = MathHelper.func_76128_c(Math.log((double)mapWidth / mapZoneWidth) / Math.log(2.0D));
         int zoomPowerHeight = MathHelper.func_76128_c(Math.log((double)mapHeight / mapZoneHeight) / Math.log(2.0D));
         this.prevZoomPower = zoomPower = Math.min(zoomPowerWidth, zoomPowerHeight);
      } else if (this.field_146297_k.field_71439_g != null) {
         this.posX = (float)(this.field_146297_k.field_71439_g.field_70165_t / (double)LOTRGenLayerWorld.scale) + 810.0F;
         this.posY = (float)(this.field_146297_k.field_71439_g.field_70161_v / (double)LOTRGenLayerWorld.scale) + 730.0F;
      }

      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.setupZoomVariables(1.0F);
      this.buttonOverlayFunction = new GuiButton(0, 0, 0, 160, 20, "");
      this.buttonOverlayFunction.field_146124_l = this.buttonOverlayFunction.field_146125_m = false;
      this.field_146292_n.add(this.buttonOverlayFunction);
      this.nameWPTextField = new GuiTextField(this.field_146289_q, mapXMin + mapWidth / 2 - 80, mapYMin + 50, 160, 20);
      this.fellowshipDrawGUI = new LOTRGuiFellowships();
      this.fellowshipDrawGUI.func_146280_a(this.field_146297_k, this.field_146294_l, this.field_146295_m);
      if (this.field_146297_k.field_71462_r == this) {
         LOTRPacketClientMQEvent packet = new LOTRPacketClientMQEvent(LOTRPacketClientMQEvent.ClientMQEvent.MAP);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }

   private void setupMapWidgets() {
      mapWidgets.clear();
      mapWidgets.add(this.widgetAddCWP = new LOTRGuiMapWidget(-16, 6, 10, "addCWP", 0, 0));
      mapWidgets.add(this.widgetDelCWP = new LOTRGuiMapWidget(-16, 20, 10, "delCWP", 10, 0));
      mapWidgets.add(this.widgetRenameCWP = new LOTRGuiMapWidget(-16, 34, 10, "renameCWP", 0, 20));
      mapWidgets.add(this.widgetToggleWPs = new LOTRGuiMapWidget(-58, 6, 10, "toggleWPs", 80, 0));
      mapWidgets.add(this.widgetToggleCWPs = new LOTRGuiMapWidget(-44, 6, 10, "toggleCWPs", 90, 0));
      mapWidgets.add(this.widgetToggleHiddenSWPs = new LOTRGuiMapWidget(-30, 6, 10, "toggleHiddenSWPs", 100, 0));
      mapWidgets.add(this.widgetZoomIn = new LOTRGuiMapWidget(6, 6, 10, "zoomIn", 30, 0));
      mapWidgets.add(this.widgetZoomOut = new LOTRGuiMapWidget(6, 20, 10, "zoomOut", 40, 0));
      mapWidgets.add(this.widgetFullScreen = new LOTRGuiMapWidget(20, 6, 10, "fullScreen", 50, 0));
      mapWidgets.add(this.widgetSepia = new LOTRGuiMapWidget(34, 6, 10, "sepia", 60, 0));
      mapWidgets.add(this.widgetLabels = new LOTRGuiMapWidget(-72, 6, 10, "labels", 70, 0));
      mapWidgets.add(this.widgetShareCWP = new LOTRGuiMapWidget(-16, 48, 10, "shareCWP", 10, 10));
      mapWidgets.add(this.widgetHideSWP = new LOTRGuiMapWidget(-16, 20, 10, "hideSWP", 20, 0));
      mapWidgets.add(this.widgetUnhideSWP = new LOTRGuiMapWidget(-16, 20, 10, "unhideSWP", 20, 10));
      if (this.isConquestGrid) {
         mapWidgets.clear();
         mapWidgets.add(this.widgetToggleWPs);
         mapWidgets.add(this.widgetToggleCWPs);
         mapWidgets.add(this.widgetToggleHiddenSWPs);
         mapWidgets.add(this.widgetZoomIn);
         mapWidgets.add(this.widgetZoomOut);
         mapWidgets.add(this.widgetLabels);
      }

   }

   private void setupMapDimensions() {
      short windowWidth;
      if (this.isConquestGrid) {
         windowWidth = 400;
         int windowHeight = 240;
         mapXMin = this.field_146294_l / 2 - windowWidth / 2;
         mapXMax = this.field_146294_l / 2 + windowWidth / 2;
         mapYMin = this.field_146295_m / 2 - 16 - windowHeight / 2;
         mapYMax = mapYMin + windowHeight;
      } else if (fullscreen) {
         mapXMin = 30;
         mapXMax = this.field_146294_l - 30;
         mapYMin = 30;
         mapYMax = this.field_146295_m - 30;
      } else {
         windowWidth = 312;
         mapXMin = this.field_146294_l / 2 - windowWidth / 2;
         mapXMax = this.field_146294_l / 2 + windowWidth / 2;
         mapYMin = this.guiTop;
         mapYMax = this.guiTop + 200;
      }

      mapWidth = mapXMax - mapXMin;
      mapHeight = mapYMax - mapYMin;
   }

   public void func_73876_c() {
      super.func_73876_c();
      ++this.tickCounter;
      if (this.zoomTicks > 0) {
         --this.zoomTicks;
      }

      if (this.creatingWaypointNew || this.renamingWaypoint || this.sharingWaypointNew) {
         this.nameWPTextField.func_146178_a();
      }

      this.handleMapKeyboardMovement();
      if (this.isConquestGrid) {
         this.updateCurrentDimensionAndFaction();
         if (this.ticksUntilRequestFac > 0) {
            --this.ticksUntilRequestFac;
         }
      }

   }

   private void updateCurrentDimensionAndFaction() {
      if (this.currentFactionIndex != this.prevFactionIndex) {
         this.conquestViewingFaction = (LOTRFaction)currentFactionList.get(this.currentFactionIndex);
         this.ticksUntilRequestFac = 40;
      }

      this.prevFactionIndex = this.currentFactionIndex;
      if (currentRegion != prevRegion) {
         lastViewedRegions.put(prevRegion, this.conquestViewingFaction);
         currentFactionList = currentRegion.factionList;
         this.conquestViewingFaction = lastViewedRegions.containsKey(currentRegion) ? (LOTRFaction)lastViewedRegions.get(currentRegion) : (LOTRFaction)currentRegion.factionList.get(0);
         this.prevFactionIndex = this.currentFactionIndex = currentFactionList.indexOf(this.conquestViewingFaction);
         this.ticksUntilRequestFac = 40;
      }

      prevRegion = currentRegion;
   }

   public void setupZoomVariables(float f) {
      this.zoomExp = (float)zoomPower;
      if (this.zoomTicks > 0) {
         float progress = ((float)zoomTicksMax - ((float)this.zoomTicks - f)) / (float)zoomTicksMax;
         this.zoomExp = (float)this.prevZoomPower + (float)(zoomPower - this.prevZoomPower) * progress;
      }

      this.zoomScale = (float)Math.pow(2.0D, (double)this.zoomExp);
      this.zoomScaleStable = (float)Math.pow(2.0D, (double)(this.zoomTicks == 0 ? zoomPower : Math.min(zoomPower, this.prevZoomPower)));
   }

   public void func_73863_a(int i, int j, float f) {
      Tessellator tess = Tessellator.field_78398_a;
      this.field_73735_i = 0.0F;
      this.setupMapDimensions();
      this.setupZoomVariables(f);
      if (this.isConquestGrid) {
         this.loadingConquestGrid = !this.receivedFacGrids.contains(this.conquestViewingFaction);
         this.highestViewedConqStr = 0.0F;
         this.setupConquestScrollBar(i, j);
         this.buttonConquestRegions.field_146126_j = currentRegion.getRegionName();
         this.buttonConquestRegions.field_146125_m = this.buttonConquestRegions.field_146124_l = true;
      }

      this.posX = this.prevPosX;
      this.posY = this.prevPosY;
      this.isMouseWithinMap = i >= mapXMin && i < mapXMax && j >= mapYMin && j < mapYMax;
      if (!this.hasOverlay && !this.isFacScrolling && this.zoomTicks == 0 && Mouse.isButtonDown(0)) {
         if ((this.isMouseButtonDown == 0 || this.isMouseButtonDown == 1) && this.isMouseWithinMap) {
            if (this.isMouseButtonDown == 0) {
               this.isMouseButtonDown = 1;
            } else {
               float x = (float)(i - this.prevMouseX) / this.zoomScale;
               float y = (float)(j - this.prevMouseY) / this.zoomScale;
               this.posX -= x;
               this.posY -= y;
               if (x != 0.0F || y != 0.0F) {
                  this.selectedWaypoint = null;
               }
            }

            this.prevMouseX = i;
            this.prevMouseY = j;
         }
      } else {
         this.isMouseButtonDown = 0;
      }

      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.posX += this.posXMove * f;
      this.posY += this.posYMove * f;
      boolean isSepia = this.isConquestGrid || LOTRConfig.enableSepiaMap;
      if (this.isConquestGrid) {
         this.func_146276_q_();
      }

      byte overlayBorder;
      int redW;
      if (!fullscreen && !this.isConquestGrid) {
         this.func_146276_q_();
         this.renderGraduatedRects(mapXMin - 1, mapYMin - 1, mapXMax + 1, mapYMax + 1, -6156032, -16777216, 4);
      } else {
         this.field_146297_k.func_110434_K().func_110577_a(LOTRTextures.overlayTexture);
         if (this.conquestViewingFaction != null) {
            float[] cqColors = this.conquestViewingFaction.getFactionRGB();
            GL11.glColor4f(cqColors[0], cqColors[1], cqColors[2], 1.0F);
         } else {
            GL11.glColor4f(0.65F, 0.5F, 0.35F, 1.0F);
         }

         tess.func_78382_b();
         if (this.isConquestGrid) {
            int w = 8;
            int up = 22;
            overlayBorder = 54;
            tess.func_78374_a((double)(mapXMin - w), (double)(mapYMax + overlayBorder), (double)this.field_73735_i, 0.0D, 1.0D);
            tess.func_78374_a((double)(mapXMax + w), (double)(mapYMax + overlayBorder), (double)this.field_73735_i, 1.0D, 1.0D);
            tess.func_78374_a((double)(mapXMax + w), (double)(mapYMin - up), (double)this.field_73735_i, 1.0D, 0.0D);
            tess.func_78374_a((double)(mapXMin - w), (double)(mapYMin - up), (double)this.field_73735_i, 0.0D, 0.0D);
         } else {
            tess.func_78374_a(0.0D, (double)this.field_146295_m, (double)this.field_73735_i, 0.0D, 1.0D);
            tess.func_78374_a((double)this.field_146294_l, (double)this.field_146295_m, (double)this.field_73735_i, 1.0D, 1.0D);
            tess.func_78374_a((double)this.field_146294_l, 0.0D, (double)this.field_73735_i, 1.0D, 0.0D);
            tess.func_78374_a(0.0D, 0.0D, (double)this.field_73735_i, 0.0D, 0.0D);
         }

         tess.func_78381_a();
         redW = this.isConquestGrid ? 2 : 4;
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.renderGraduatedRects(mapXMin - 1, mapYMin - 1, mapXMax + 1, mapYMax + 1, -6156032, -16777216, redW);
      }

      this.setupScrollBars(i, j);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      redW = LOTRTextures.getMapOceanColor(isSepia);
      func_73734_a(mapXMin, mapYMin, mapXMax, mapYMax, redW);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      String s;
      if (!this.isConquestGrid) {
         s = StatCollector.func_74838_a("lotr.gui.map.title");
         if (fullscreen) {
            this.drawCenteredString(s, this.field_146294_l / 2, 10, 16777215);
         } else {
            this.drawCenteredString(s, this.field_146294_l / 2, this.guiTop - 30, 16777215);
         }
      }

      int stringX;
      float strength;
      float minX;
      float biomePosX;
      int x;
      int rectX0;
      int rectY0;
      int rectX1;
      byte keyHeight;
      int stringY;
      float yMax;
      float minU;
      float frac;
      int index;
      int y;
      int strX;
      if (this.hasControlZones && LOTRFaction.controlZonesEnabled(this.field_146297_k.field_71441_e)) {
         this.renderMapAndOverlay(isSepia, 1.0F, false);
         this.renderControlZone(i, j);
         GL11.glEnable(3042);
         this.renderMapAndOverlay(isSepia, 0.5F, true);
         GL11.glDisable(3042);
         s = null;
         if (this.mouseControlZone) {
            s = StatCollector.func_74838_a("lotr.gui.map.controlZoneFull");
         } else if (this.mouseControlZoneReduced) {
            s = StatCollector.func_74838_a("lotr.gui.map.controlZoneReduced");
         }

         if (s != null) {
            x = this.field_146297_k.field_71466_p.func_78256_a(s);
            rectX0 = this.field_146297_k.field_71466_p.field_78288_b;
            rectY0 = i + 12;
            rectX1 = j - 12;
            keyHeight = 3;
            stringX = x + keyHeight * 2;
            stringY = rectX0 + keyHeight * 2;
            int mapBorder2 = 2;
            rectY0 = Math.max(rectY0, mapXMin + mapBorder2);
            rectY0 = Math.min(rectY0, mapXMax - mapBorder2 - stringX);
            rectX1 = Math.max(rectX1, mapYMin + mapBorder2);
            rectX1 = Math.min(rectX1, mapYMax - mapBorder2 - stringY);
            GL11.glTranslatef(0.0F, 0.0F, 300.0F);
            this.drawFancyRect(rectY0, rectX1, rectY0 + stringX, rectX1 + stringY);
            this.field_146297_k.field_71466_p.func_78276_b(s, rectY0 + keyHeight, rectX1 + keyHeight, 16777215);
            GL11.glTranslatef(0.0F, 0.0F, -300.0F);
         }
      } else {
         this.renderMapAndOverlay(isSepia, 1.0F, true);
         if (this.isConquestGrid && this.conquestViewingFaction != null) {
            this.requestConquestGridIfNeed(this.conquestViewingFaction);
            if (!this.loadingConquestGrid) {
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               this.setupMapClipping();
               biomePosX = GL11.glGetFloat(3010);
               GL11.glAlphaFunc(516, 0.005F);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               Collection allZones = (Collection)this.facConquestGrids.get(this.conquestViewingFaction);
               if (allZones == null) {
                  allZones = new ArrayList();
               }

               Collection zonesInView = new ArrayList();
               this.highestViewedConqStr = 0.0F;
               float mouseOverStr = 0.0F;
               LOTRConquestZone mouseOverZone = null;
               LOTRConquestGrid.ConquestEffective mouseOverEffect = null;

               float strFrac;
               float strAlpha;
               int zoneColor;
               int zoneColor2;
               byte strips;
               int l;
               label563:
               for(stringX = 0; stringX <= 1; ++stringX) {
                  if (stringX != 1 || this.highestViewedConqStr > 0.0F) {
                     Collection zoneList = stringX == 0 ? allZones : zonesInView;
                     Iterator var15 = ((Collection)zoneList).iterator();

                     while(true) {
                        while(true) {
                           LOTRConquestZone zone;
                           float maxX;
                           float minY;
                           float maxY;
                           do {
                              do {
                                 do {
                                    do {
                                       if (!var15.hasNext()) {
                                          continue label563;
                                       }

                                       zone = (LOTRConquestZone)var15.next();
                                       strength = zone.getConquestStrength(this.conquestViewingFaction, this.field_146297_k.field_71441_e);
                                       int[] min = LOTRConquestGrid.getMinCoordsOnMap(zone);
                                       int[] max = LOTRConquestGrid.getMaxCoordsOnMap(zone);
                                       float[] minF = this.transformMapCoords((float)min[0], (float)min[1]);
                                       float[] maxF = this.transformMapCoords((float)max[0], (float)max[1]);
                                       minX = minF[0];
                                       maxX = maxF[0];
                                       minY = minF[1];
                                       maxY = maxF[1];
                                    } while(maxX < (float)mapXMin);
                                 } while(minX > (float)mapXMax);
                              } while(maxY < (float)mapYMin);
                           } while(minY > (float)mapYMax);

                           if (stringX == 0) {
                              if (strength > this.highestViewedConqStr) {
                                 this.highestViewedConqStr = strength;
                              }

                              zonesInView.add(zone);
                           } else if (stringX == 1 && strength > 0.0F) {
                              strFrac = strength / this.highestViewedConqStr;
                              strFrac = MathHelper.func_76131_a(strFrac, 0.0F, 1.0F);
                              strAlpha = strFrac;
                              if (strength > 0.0F) {
                                 strAlpha = Math.max(strFrac, 0.1F);
                              }

                              LOTRConquestGrid.ConquestEffective effect = LOTRConquestGrid.getConquestEffectIn(this.field_146297_k.field_71441_e, zone, this.conquestViewingFaction);
                              zoneColor = 12255232 | Math.round(strAlpha * 255.0F) << 24;
                              if (effect == LOTRConquestGrid.ConquestEffective.EFFECTIVE) {
                                 drawFloatRect(minX, minY, maxX, maxY, zoneColor);
                              } else {
                                 zoneColor2 = 1973790 | Math.round(strAlpha * 255.0F) << 24;
                                 if (effect == LOTRConquestGrid.ConquestEffective.ALLY_BOOST) {
                                    float zoneYSize = maxY - minY;
                                    strips = 8;

                                    for(l = 0; l < strips; ++l) {
                                       float stripYSize = zoneYSize / (float)strips;
                                       drawFloatRect(minX, minY + stripYSize * (float)l, maxX, minY + stripYSize * (float)(l + 1), l % 2 == 0 ? zoneColor : zoneColor2);
                                    }
                                 } else if (effect == LOTRConquestGrid.ConquestEffective.NO_EFFECT) {
                                    drawFloatRect(minX, minY, maxX, maxY, zoneColor2);
                                 }
                              }

                              if ((float)i >= minX && (float)i < maxX && (float)j >= minY && (float)j < maxY) {
                                 mouseOverStr = strength;
                                 mouseOverZone = zone;
                                 mouseOverEffect = effect;
                              }
                           }
                        }
                     }
                  }
               }

               GL11.glAlphaFunc(516, biomePosX);
               if (mouseOverZone != null && i >= mapXMin && i < mapXMax && j >= mapYMin && j < mapYMax) {
                  int[] min = LOTRConquestGrid.getMinCoordsOnMap(mouseOverZone);
                  int[] max = LOTRConquestGrid.getMaxCoordsOnMap(mouseOverZone);
                  float[] minF = this.transformMapCoords((float)min[0], (float)min[1]);
                  float[] maxF = this.transformMapCoords((float)max[0], (float)max[1]);
                  strength = minF[0];
                  yMax = maxF[0];
                  minU = minF[1];
                  frac = maxF[1];
                  drawFloatRect(strength - 1.0F, minU - 1.0F, yMax + 1.0F, minU, -16777216);
                  drawFloatRect(strength - 1.0F, frac, yMax + 1.0F, frac + 1.0F, -16777216);
                  drawFloatRect(strength - 1.0F, minU, strength, frac, -16777216);
                  drawFloatRect(yMax, minU, yMax + 1.0F, frac, -16777216);
                  drawFloatRect(strength - 2.0F, minU - 2.0F, yMax + 2.0F, minU - 1.0F, -4521984);
                  drawFloatRect(strength - 2.0F, frac + 1.0F, yMax + 2.0F, frac + 2.0F, -4521984);
                  drawFloatRect(strength - 2.0F, minU - 1.0F, strength - 1.0F, frac + 1.0F, -4521984);
                  drawFloatRect(yMax + 1.0F, minU - 1.0F, yMax + 2.0F, frac + 1.0F, -4521984);
                  String tooltip = LOTRAlignmentValues.formatConqForDisplay(mouseOverStr, false);
                  String subtip = null;
                  if (mouseOverEffect == LOTRConquestGrid.ConquestEffective.ALLY_BOOST) {
                     subtip = StatCollector.func_74837_a("lotr.gui.map.conquest.allyBoost", new Object[]{this.conquestViewingFaction.factionName()});
                  } else if (mouseOverEffect == LOTRConquestGrid.ConquestEffective.NO_EFFECT) {
                     subtip = StatCollector.func_74838_a("lotr.gui.map.conquest.noEffect");
                  }

                  index = this.field_146297_k.field_71466_p.func_78256_a(tooltip);
                  y = subtip == null ? 0 : this.field_146297_k.field_71466_p.func_78256_a(subtip);
                  strX = this.field_146297_k.field_71466_p.field_78288_b;
                  strFrac = (float)(new ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d)).func_78325_e();
                  strAlpha = strFrac <= 2.0F ? strFrac : strFrac - 1.0F;
                  float subScaleRel = strAlpha / strFrac;
                  zoneColor = i + 12;
                  zoneColor2 = j - 12;
                  int border = 3;
                  strips = 16;
                  l = border * 2 + Math.max(index + strips + border, (int)((float)y * subScaleRel));
                  int rectHeight = Math.max(strX, strips) + border * 2;
                  if (subtip != null) {
                     rectHeight += border + (int)((float)strX * subScaleRel);
                  }

                  int mapBorder2 = 2;
                  zoneColor = Math.max(zoneColor, mapXMin + mapBorder2);
                  zoneColor = Math.min(zoneColor, mapXMax - mapBorder2 - l);
                  zoneColor2 = Math.max(zoneColor2, mapYMin + mapBorder2);
                  zoneColor2 = Math.min(zoneColor2, mapYMax - mapBorder2 - rectHeight);
                  GL11.glTranslatef(0.0F, 0.0F, 300.0F);
                  this.drawFancyRect(zoneColor, zoneColor2, zoneColor + l, zoneColor2 + rectHeight);
                  this.field_146297_k.func_110434_K().func_110577_a(LOTRClientProxy.alignmentTexture);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  this.func_73729_b(zoneColor + border, zoneColor2 + border, 0, 228, strips, strips);
                  this.field_146297_k.field_71466_p.func_78276_b(tooltip, zoneColor + strips + border * 2, zoneColor2 + border + (strips - strX) / 2, 16777215);
                  if (subtip != null) {
                     GL11.glPushMatrix();
                     GL11.glScalef(subScaleRel, subScaleRel, 1.0F);
                     int subX = zoneColor + border;
                     int subY = zoneColor2 + border * 2 + strips;
                     this.field_146297_k.field_71466_p.func_78276_b(subtip, Math.round((float)subX / subScaleRel), Math.round((float)subY / subScaleRel), 16777215);
                     GL11.glPopMatrix();
                  }

                  GL11.glTranslatef(0.0F, 0.0F, -300.0F);
               }

               this.endMapClipping();
               GL11.glDisable(3042);
            }
         }
      }

      this.field_73735_i += 50.0F;
      LOTRTextures.drawMapCompassBottomLeft((double)(mapXMin + 12), (double)(mapYMax - 12), (double)this.field_73735_i, 1.0D);
      this.field_73735_i -= 50.0F;
      this.renderRoads();
      this.renderPlayers(i, j);
      if (!this.loadingConquestGrid) {
         this.renderMiniQuests(this.field_146297_k.field_71439_g, i, j);
      }

      this.renderWaypoints(0, i, j);
      this.renderLabels();
      this.renderWaypoints(1, i, j);
      if (!this.loadingConquestGrid && this.selectedWaypoint != null && this.isWaypointVisible(this.selectedWaypoint)) {
         if (!this.hasOverlay) {
            this.renderWaypointTooltip(this.selectedWaypoint, true, i, j);
         }
      } else {
         this.selectedWaypoint = null;
      }

      String biomeName;
      int rectY1;
      String notUnlocked;
      String ftPrompt;
      if (this.isConquestGrid) {
         if (this.loadingConquestGrid) {
            func_73734_a(mapXMin, mapYMin, mapXMax, mapYMax, -1429949539);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            this.field_146297_k.func_110434_K().func_110577_a(LOTRTextures.overlayTexture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.2F);
            tess.func_78382_b();
            tess.func_78374_a((double)mapXMin, (double)mapYMax, 0.0D, 0.0D, 1.0D);
            tess.func_78374_a((double)mapXMax, (double)mapYMax, 0.0D, 1.0D, 1.0D);
            tess.func_78374_a((double)mapXMax, (double)mapYMin, 0.0D, 1.0D, 0.0D);
            tess.func_78374_a((double)mapXMin, (double)mapYMin, 0.0D, 0.0D, 0.0D);
            tess.func_78381_a();
            s = "";
            LOTRConquestGrid.ConquestViewableQuery query = LOTRConquestGrid.canPlayerViewConquest(this.field_146297_k.field_71439_g, this.conquestViewingFaction);
            if (query.result == LOTRConquestGrid.ConquestViewable.CAN_VIEW) {
               s = StatCollector.func_74838_a("lotr.gui.map.conquest.wait");
               rectX0 = 1 + this.tickCounter / 10 % 3;

               for(rectY0 = 0; rectY0 < rectX0; ++rectY0) {
                  s = s + ".";
               }
            } else if (query.result == LOTRConquestGrid.ConquestViewable.UNPLEDGED) {
               s = StatCollector.func_74838_a("lotr.gui.map.conquest.noPledge");
            } else {
               LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g);
               LOTRFaction pledgeFac = pd.getPledgeFaction();
               LOTRFactionRank needRank = query.needRank;
               biomeName = LOTRAlignmentValues.formatAlignForDisplay(needRank.alignment);
               notUnlocked = "";
               if (query.result == LOTRConquestGrid.ConquestViewable.NEED_RANK) {
                  notUnlocked = "lotr.gui.map.conquest.needRank";
               }

               s = StatCollector.func_74837_a(notUnlocked, new Object[]{pledgeFac.factionName(), needRank.getFullNameWithGender(pd), biomeName});
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int stringWidth = 250;
            String[] splitNewline = s.split(Pattern.quote("\\n"));
            List loadLines = new ArrayList();
            String[] var63 = splitNewline;
            stringX = splitNewline.length;

            for(stringY = 0; stringY < stringX; ++stringY) {
               ftPrompt = var63[stringY];
               loadLines.addAll(this.field_146289_q.func_78271_c(ftPrompt, stringWidth));
            }

            rectY1 = mapXMin + mapWidth / 2;
            stringX = (mapYMin + mapYMax) / 2 - loadLines.size() * this.field_146289_q.field_78288_b / 2;

            for(Iterator var75 = loadLines.iterator(); var75.hasNext(); stringX += this.field_146289_q.field_78288_b) {
               ftPrompt = (String)var75.next();
               this.drawCenteredString(ftPrompt, rectY1, stringX, 3748142);
            }

            GL11.glDisable(3042);
         }

         this.field_146297_k.func_110434_K().func_110577_a(conquestTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(mapXMin - 8, mapYMin - 22, 0, 0, mapWidth + 16, mapHeight + 22 + 54, 512);
      }

      this.field_73735_i = 100.0F;
      int stringWidth;
      byte iconWidth;
      String alreadyShared;
      int sx;
      int fullWidth;
      int fsX;
      int fsY;
      int strY;
      int iconRemoveX;
      if (!this.hasOverlay) {
         String teleport;
         if (this.isMiddleEarth() && this.selectedWaypoint != null) {
            this.field_73735_i += 500.0F;
            LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g);
            boolean hasUnlocked = this.selectedWaypoint.hasPlayerUnlocked(this.field_146297_k.field_71439_g);
            rectX0 = pd.getTimeSinceFT();
            rectY0 = pd.getWaypointFTTime(this.selectedWaypoint, this.field_146297_k.field_71439_g);
            rectX1 = rectY0 - rectX0;
            boolean canFastTravel = hasUnlocked && rectX1 <= 0;
            notUnlocked = "If you can read this, something has gone hideously wrong";
            if (this.selectedWaypoint instanceof LOTRCustomWaypoint) {
               LOTRCustomWaypoint cwp = (LOTRCustomWaypoint)this.selectedWaypoint;
               if (cwp.isShared()) {
                  notUnlocked = StatCollector.func_74838_a("lotr.gui.map.locked.shared");
               }
            } else {
               LOTRWaypoint wp = (LOTRWaypoint)this.selectedWaypoint;
               if (!wp.isCompatibleAlignment(this.field_146297_k.field_71439_g)) {
                  notUnlocked = StatCollector.func_74838_a("lotr.gui.map.locked.enemy");
               } else {
                  notUnlocked = StatCollector.func_74838_a("lotr.gui.map.locked.region");
               }
            }

            teleport = pd.getPledgeFaction() == null ? "" : StatCollector.func_74837_a("lotr.gui.map.locked.conquerable", new Object[]{pd.getPledgeFaction().factionName()});
            ftPrompt = StatCollector.func_74837_a("lotr.gui.map.fastTravel.prompt", new Object[]{GameSettings.func_74298_c(LOTRKeyHandler.keyBindingFastTravel.func_151463_i())});
            String ftMoreTime = StatCollector.func_74837_a("lotr.gui.map.fastTravel.moreTime", new Object[]{LOTRLevelData.getHMSTime_Ticks(rectX1)});
            alreadyShared = StatCollector.func_74837_a("lotr.gui.map.fastTravel.waitTime", new Object[]{LOTRLevelData.getHMSTime_Ticks(rectY0)});
            if (!fullscreen && !this.isConquestGrid) {
               List lines = new ArrayList();
               if (!hasUnlocked) {
                  lines.add(notUnlocked);
                  if (this.selectedWaypoint instanceof LOTRWaypoint && ((LOTRWaypoint)this.selectedWaypoint).isConquestUnlockable(this.field_146297_k.field_71439_g)) {
                     lines.add(teleport);
                  }
               } else if (canFastTravel) {
                  lines.add(ftPrompt);
                  lines.add(alreadyShared);
               } else {
                  lines.add(ftMoreTime);
                  lines.add(alreadyShared);
               }

               fullWidth = this.field_146289_q.field_78288_b;
               fsX = mapWidth;
               int border = 3;
               strY = border + (fullWidth + border) * lines.size();
               index = mapXMin + mapWidth / 2 - fsX / 2;
               y = mapYMax + 10;
               strX = mapXMin + mapWidth / 2;
               iconRemoveX = y + border;
               this.drawFancyRect(index, y, index + fsX, y + strY);

               for(Iterator var119 = lines.iterator(); var119.hasNext(); iconRemoveX += fullWidth + border) {
                  String s = (String)var119.next();
                  this.drawCenteredString(s, strX, iconRemoveX, 16777215);
               }
            } else if (!hasUnlocked) {
               if (this.selectedWaypoint instanceof LOTRWaypoint && ((LOTRWaypoint)this.selectedWaypoint).isConquestUnlockable(this.field_146297_k.field_71439_g)) {
                  this.renderFullscreenSubtitles(notUnlocked, teleport);
               } else {
                  this.renderFullscreenSubtitles(notUnlocked);
               }
            } else if (canFastTravel) {
               this.renderFullscreenSubtitles(ftPrompt, alreadyShared);
            } else {
               this.renderFullscreenSubtitles(ftMoreTime, alreadyShared);
            }

            this.field_73735_i -= 500.0F;
         } else if (this.isMouseWithinMap) {
            this.field_73735_i += 500.0F;
            biomePosX = this.posX + (float)(i - mapXMin - mapWidth / 2) / this.zoomScale;
            float biomePosZ = this.posY + (float)(j - mapYMin - mapHeight / 2) / this.zoomScale;
            rectX0 = MathHelper.func_76128_c((double)biomePosX);
            rectY0 = MathHelper.func_76128_c((double)biomePosZ);
            LOTRBiome biome = LOTRGenLayerWorld.getBiomeOrOcean(rectX0, rectY0);
            if (biome.isHiddenBiome() && !LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).hasAchievement(biome.getBiomeAchievement())) {
               biome = LOTRBiome.ocean;
            }

            this.mouseXCoord = Math.round((biomePosX - 810.0F) * (float)LOTRGenLayerWorld.scale);
            this.mouseZCoord = Math.round((biomePosZ - 730.0F) * (float)LOTRGenLayerWorld.scale);
            biomeName = biome.getBiomeDisplayName();
            notUnlocked = StatCollector.func_74837_a("lotr.gui.map.coords", new Object[]{this.mouseXCoord, this.mouseZCoord});
            teleport = StatCollector.func_74837_a("lotr.gui.map.tp", new Object[]{GameSettings.func_74298_c(LOTRKeyHandler.keyBindingMapTeleport.func_151463_i())});
            stringWidth = this.field_146289_q.field_78288_b;
            if (!fullscreen && !this.isConquestGrid) {
               int rectWidth = mapWidth;
               iconWidth = 3;
               sx = iconWidth * 3 + stringWidth * 2;
               if (this.canTeleport()) {
                  sx += (stringWidth + iconWidth) * 2;
               }

               fullWidth = mapXMin + mapWidth / 2 - rectWidth / 2;
               fsX = mapYMax + 10;
               this.drawFancyRect(fullWidth, fsX, fullWidth + rectWidth, fsX + sx);
               fsY = mapXMin + mapWidth / 2;
               strY = fsX + iconWidth;
               this.drawCenteredString(biomeName, fsY, strY, 16777215);
               strY += stringWidth + iconWidth;
               this.drawCenteredString(notUnlocked, fsY, strY, 16777215);
               if (this.canTeleport()) {
                  this.drawCenteredString(teleport, fsY, strY + (stringWidth + iconWidth) * 2, 16777215);
               }
            } else {
               this.renderFullscreenSubtitles(biomeName, notUnlocked);
               if (this.canTeleport()) {
                  GL11.glPushMatrix();
                  if (this.isConquestGrid) {
                     GL11.glTranslatef((float)((mapXMax - mapXMin) / 2 - 8 - this.field_146289_q.func_78256_a(teleport) / 2), 0.0F, 0.0F);
                  } else {
                     GL11.glTranslatef((float)(this.field_146294_l / 2 - 30 - this.field_146289_q.func_78256_a(teleport) / 2), 0.0F, 0.0F);
                  }

                  this.renderFullscreenSubtitles(teleport);
                  GL11.glPopMatrix();
               }
            }

            this.field_73735_i -= 500.0F;
         }
      }

      if (this.isConquestGrid) {
         s = StatCollector.func_74837_a("lotr.gui.map.conquest.title", new Object[]{this.conquestViewingFaction.factionName()});
         x = mapXMin + mapWidth / 2;
         rectX0 = mapYMin - 14;
         LOTRTickHandlerClient.drawAlignmentText(this.field_146289_q, x - this.field_146289_q.func_78256_a(s) / 2, rectX0, s, 1.0F);
         float xMin;
         float xMax;
         float strFrac;
         if (!this.loadingConquestGrid) {
            int keyBorder = 8;
            int keyWidth = 24;
            keyHeight = 12;
            int iconSize = 16;
            stringY = keyBorder / 2;
            xMin = (float)(new ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d)).func_78325_e();
            xMax = xMin <= 2.0F ? xMin : xMin - 1.0F;
            strength = xMax / xMin;
            this.field_146297_k.func_110434_K().func_110577_a(LOTRClientProxy.alignmentTexture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.func_73729_b(mapXMax - keyBorder - iconSize, mapYMax - keyBorder - iconSize, 0, 228, iconSize, iconSize);

            for(sx = 0; sx <= 1; ++sx) {
               for(fullWidth = 0; fullWidth <= 10; ++fullWidth) {
                  frac = (float)(10 - fullWidth) / 10.0F;
                  strFrac = frac * this.highestViewedConqStr;
                  strY = mapXMax - keyBorder - iconSize - stringY - fullWidth * keyWidth;
                  index = strY - keyWidth;
                  y = mapYMax - keyBorder - (iconSize - keyHeight) / 2;
                  strX = y - keyHeight;
                  if (sx == 0) {
                     iconRemoveX = 12255232 | Math.round(frac * 255.0F) << 24;
                     func_73734_a(index, strX, strY, y, iconRemoveX);
                  } else if (sx == 1 && fullWidth % 2 == 0) {
                     String keyLabel = LOTRAlignmentValues.formatConqForDisplay(strFrac, false);
                     int strX = (int)((float)(index + keyWidth / 2) / strength);
                     int strY = (int)((float)(strX + keyHeight / 2) / strength) - this.field_146289_q.field_78288_b / 2;
                     GL11.glPushMatrix();
                     GL11.glScalef(strength, strength, 1.0F);
                     this.drawCenteredString(keyLabel, strX, strY, 16777215);
                     GL11.glPopMatrix();
                  }
               }
            }
         }

         if (this.hasConquestScrollBar()) {
            this.field_146297_k.func_110434_K().func_110577_a(LOTRGuiFactions.factionsTexture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.func_73729_b(this.facScrollX, this.facScrollY, 0, 128, this.facScrollWidth, this.facScrollHeight);
            rectY0 = currentFactionList.size();

            for(rectX1 = 0; rectX1 < rectY0; ++rectX1) {
               LOTRFaction faction = (LOTRFaction)currentFactionList.get(rectX1);
               float[] factionColors = faction.getFactionRGB();
               float shade = 0.6F;
               GL11.glColor4f(factionColors[0] * shade, factionColors[1] * shade, factionColors[2] * shade, 1.0F);
               xMin = (float)rectX1 / (float)rectY0;
               xMax = (float)(rectX1 + 1) / (float)rectY0;
               xMin = (float)(this.facScrollX + this.facScrollBorder) + xMin * (float)(this.facScrollWidth - this.facScrollBorder * 2);
               xMax = (float)(this.facScrollX + this.facScrollBorder) + xMax * (float)(this.facScrollWidth - this.facScrollBorder * 2);
               strength = (float)(this.facScrollY + this.facScrollBorder);
               yMax = (float)(this.facScrollY + this.facScrollHeight - this.facScrollBorder);
               minU = (float)(0 + this.facScrollBorder) / 256.0F;
               frac = (float)(0 + this.facScrollWidth - this.facScrollBorder) / 256.0F;
               strFrac = (float)(128 + this.facScrollBorder) / 256.0F;
               minX = (float)(128 + this.facScrollHeight - this.facScrollBorder) / 256.0F;
               tess.func_78382_b();
               tess.func_78374_a((double)xMin, (double)yMax, (double)this.field_73735_i, (double)minU, (double)minX);
               tess.func_78374_a((double)xMax, (double)yMax, (double)this.field_73735_i, (double)frac, (double)minX);
               tess.func_78374_a((double)xMax, (double)strength, (double)this.field_73735_i, (double)frac, (double)strFrac);
               tess.func_78374_a((double)xMin, (double)strength, (double)this.field_73735_i, (double)minU, (double)strFrac);
               tess.func_78381_a();
            }

            this.field_146297_k.func_110434_K().func_110577_a(LOTRGuiFactions.factionsTexture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            rectX1 = (int)(this.currentFacScroll * (float)(this.facScrollWidth - this.facScrollBorder * 2 - this.facScrollWidgetWidth));
            this.func_73729_b(this.facScrollX + this.facScrollBorder + rectX1, this.facScrollY + this.facScrollBorder, 0, 142, this.facScrollWidgetWidth, this.facScrollWidgetHeight);
         }
      }

      if (!this.hasOverlay && this.hasControlZones) {
         s = StatCollector.func_74837_a("lotr.gui.map.controlZones", new Object[]{this.controlZoneFaction.factionName()});
         x = mapXMin + mapWidth / 2;
         rectX0 = mapYMin + 20;
         LOTRTickHandlerClient.drawAlignmentText(this.field_146289_q, x - this.field_146289_q.func_78256_a(s) / 2, rectX0, s, 1.0F);
         if (!LOTRFaction.controlZonesEnabled(this.field_146297_k.field_71441_e)) {
            s = StatCollector.func_74838_a("lotr.gui.map.controlZonesDisabled");
            LOTRTickHandlerClient.drawAlignmentText(this.field_146289_q, x - this.field_146289_q.func_78256_a(s) / 2, mapYMin + mapHeight / 2, s, 1.0F);
         }
      }

      boolean buttonVisible = this.buttonOverlayFunction.field_146125_m;
      this.buttonOverlayFunction.field_146125_m = false;
      super.func_73863_a(i, j, f);
      this.buttonOverlayFunction.field_146125_m = buttonVisible;
      this.renderMapWidgets(i, j);
      if (this.hasOverlay) {
         GL11.glTranslatef(0.0F, 0.0F, 500.0F);
         overlayBorder = 10;
         if (fullscreen) {
            overlayBorder = 40;
         }

         rectX0 = mapXMin + overlayBorder;
         rectY0 = mapYMin + overlayBorder;
         rectX1 = mapXMax - overlayBorder;
         rectY1 = mapYMax - overlayBorder;
         this.drawFancyRect(rectX0, rectY0, rectX1, rectY1);
         if (this.overlayLines != null) {
            stringX = mapXMin + mapWidth / 2;
            stringY = rectY0 + 3 + this.field_146297_k.field_71466_p.field_78288_b;
            stringWidth = (int)((float)(mapWidth - overlayBorder * 2) * 0.75F);
            List displayLines = new ArrayList();
            String[] var93 = this.overlayLines;
            sx = var93.length;

            for(fullWidth = 0; fullWidth < sx; ++fullWidth) {
               String s = var93[fullWidth];
               displayLines.addAll(this.field_146289_q.func_78271_c(s, stringWidth));
            }

            for(Iterator var96 = displayLines.iterator(); var96.hasNext(); stringY += this.field_146297_k.field_71466_p.field_78288_b) {
               String s = (String)var96.next();
               this.drawCenteredString(s, stringX, stringY, 16777215);
            }

            stringY += this.field_146297_k.field_71466_p.field_78288_b;
            if (this.sharingWaypoint) {
               this.fellowshipDrawGUI.clearMouseOverFellowship();
               this.mouseOverRemoveSharedFellowship = null;
               iconWidth = 8;
               int iconGap = 8;
               fullWidth = this.fellowshipDrawGUI.xSize + iconGap + iconWidth;
               fsX = mapXMin + mapWidth / 2 - fullWidth / 2;
               fsY = stringY;
               this.scrollPaneWPShares.paneX0 = fsX;
               int var10001 = fsX + fullWidth;
               LOTRGuiFellowships var10002 = this.fellowshipDrawGUI;
               this.scrollPaneWPShares.scrollBarX0 = var10001 + 2 + 2;
               this.scrollPaneWPShares.paneY0 = stringY;
               LOTRGuiFellowships var10003 = this.fellowshipDrawGUI;
               this.scrollPaneWPShares.paneY1 = stringY + (this.field_146297_k.field_71466_p.field_78288_b + 5) * this.displayedWPShares;
               this.scrollPaneWPShares.mouseDragScroll(i, j);
               int[] sharesMinMax = this.scrollPaneWPShares.getMinMaxIndices(this.displayedWPShareList, this.displayedWPShares);

               for(index = sharesMinMax[0]; index <= sharesMinMax[1]; ++index) {
                  UUID fsID = (UUID)this.displayedWPShareList.get(index);
                  LOTRFellowshipClient fs = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getClientFellowshipByID(fsID);
                  if (fs != null) {
                     this.fellowshipDrawGUI.drawFellowshipEntry(fs, fsX, fsY, i, j, false, fullWidth);
                     iconRemoveX = fsX + this.fellowshipDrawGUI.xSize + iconGap;
                     boolean mouseOverRemove = false;
                     if (fs == this.fellowshipDrawGUI.getMouseOverFellowship()) {
                        mouseOverRemove = i >= iconRemoveX && i <= iconRemoveX + iconWidth && j >= fsY && j <= fsY + iconWidth;
                        if (mouseOverRemove) {
                           this.mouseOverRemoveSharedFellowship = fs;
                        }
                     }

                     this.field_146297_k.func_110434_K().func_110577_a(LOTRGuiFellowships.iconsTextures);
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     this.func_73729_b(iconRemoveX, fsY, 8, 16 + (mouseOverRemove ? 0 : iconWidth), iconWidth, iconWidth);
                     var10002 = this.fellowshipDrawGUI;
                     stringY += this.field_146297_k.field_71466_p.field_78288_b + 5;
                     fsY = stringY;
                  }
               }

               if (this.scrollPaneWPShares.hasScrollBar) {
                  this.scrollPaneWPShares.drawScrollBar();
               }
            }

            if (this.creatingWaypointNew || this.renamingWaypoint || this.sharingWaypointNew) {
               this.nameWPTextField.field_146209_f = (rectX0 + rectX1) / 2 - this.nameWPTextField.field_146218_h / 2;
               this.nameWPTextField.field_146210_g = stringY;
               GL11.glPushMatrix();
               GL11.glTranslatef(0.0F, 0.0F, this.field_73735_i);
               this.nameWPTextField.func_146194_f();
               GL11.glPopMatrix();
               if (this.sharingWaypointNew && this.isFellowshipAlreadyShared(this.nameWPTextField.func_146179_b(), (LOTRCustomWaypoint)this.selectedWaypoint)) {
                  alreadyShared = StatCollector.func_74838_a("lotr.gui.map.customWaypoint.shareNew.already");
                  sx = this.nameWPTextField.field_146209_f + this.nameWPTextField.field_146218_h + 6;
                  fullWidth = this.nameWPTextField.field_146210_g + this.nameWPTextField.field_146219_i / 2 - this.field_146289_q.field_78288_b / 2;
                  this.field_146289_q.func_78276_b(alreadyShared, sx, fullWidth, 16711680);
               }

               stringY += this.nameWPTextField.field_146219_i + this.field_146297_k.field_71466_p.field_78288_b;
            }

            stringY += this.field_146297_k.field_71466_p.field_78288_b;
            if (this.buttonOverlayFunction.field_146125_m) {
               this.buttonOverlayFunction.field_146124_l = true;
               if (this.creatingWaypointNew || this.renamingWaypoint) {
                  this.buttonOverlayFunction.field_146124_l = this.isValidWaypointName(this.nameWPTextField.func_146179_b());
               }

               if (this.sharingWaypointNew) {
                  this.buttonOverlayFunction.field_146124_l = this.isExistingUnsharedFellowshipName(this.nameWPTextField.func_146179_b(), (LOTRCustomWaypoint)this.selectedWaypoint);
               }

               this.buttonOverlayFunction.field_146128_h = stringX - this.buttonOverlayFunction.field_146120_f / 2;
               this.buttonOverlayFunction.field_146129_i = stringY;
               if (this.sharingWaypoint) {
                  this.buttonOverlayFunction.field_146129_i = rectY1 - 3 - this.field_146297_k.field_71466_p.field_78288_b - this.buttonOverlayFunction.field_146121_g;
               }

               this.buttonOverlayFunction.func_146112_a(this.field_146297_k, i, j);
            }
         }

         GL11.glTranslatef(0.0F, 0.0F, -500.0F);
      }

   }

   private void setupScrollBars(int i, int j) {
      maxDisplayedWPShares = fullscreen ? 8 : 5;
      if (this.selectedWaypoint != null && this.hasOverlay && this.sharingWaypoint) {
         this.displayedWPShareList = ((LOTRCustomWaypoint)this.selectedWaypoint).getSharedFellowshipIDs();
         this.displayedWPShares = this.displayedWPShareList.size();
         this.scrollPaneWPShares.hasScrollBar = false;
         if (this.displayedWPShares > maxDisplayedWPShares) {
            this.displayedWPShares = maxDisplayedWPShares;
            this.scrollPaneWPShares.hasScrollBar = true;
         }
      } else {
         this.displayedWPShareList = null;
         this.displayedWPShares = 0;
         this.scrollPaneWPShares.hasScrollBar = false;
         this.scrollPaneWPShares.mouseDragScroll(i, j);
      }

   }

   public void renderMapAndOverlay(boolean sepia, float alpha, boolean overlay) {
      mapXMin_W = mapXMin;
      mapXMax_W = mapXMax;
      mapYMin_W = mapYMin;
      mapYMax_W = mapYMax;
      float mapScaleX = (float)mapWidth / this.zoomScale;
      float mapScaleY = (float)mapHeight / this.zoomScale;
      double minU = (double)(this.posX - mapScaleX / 2.0F) / (double)LOTRGenLayerWorld.imageWidth;
      double maxU = (double)(this.posX + mapScaleX / 2.0F) / (double)LOTRGenLayerWorld.imageWidth;
      double minV = (double)(this.posY - mapScaleY / 2.0F) / (double)LOTRGenLayerWorld.imageHeight;
      double maxV = (double)(this.posY + mapScaleY / 2.0F) / (double)LOTRGenLayerWorld.imageHeight;
      if (minU < 0.0D) {
         mapXMin_W = mapXMin + (int)Math.round((0.0D - minU) * (double)LOTRGenLayerWorld.imageWidth * (double)this.zoomScale);
         minU = 0.0D;
         if (maxU < 0.0D) {
            maxU = 0.0D;
            mapXMax_W = mapXMin_W;
         }
      }

      if (maxU > 1.0D) {
         mapXMax_W = mapXMax - (int)Math.round((maxU - 1.0D) * (double)LOTRGenLayerWorld.imageWidth * (double)this.zoomScale);
         maxU = 1.0D;
         if (minU > 1.0D) {
            minU = 1.0D;
            mapXMin_W = mapXMax_W;
         }
      }

      if (minV < 0.0D) {
         mapYMin_W = mapYMin + (int)Math.round((0.0D - minV) * (double)LOTRGenLayerWorld.imageHeight * (double)this.zoomScale);
         minV = 0.0D;
         if (maxV < 0.0D) {
            maxV = 0.0D;
            mapYMax_W = mapYMin_W;
         }
      }

      if (maxV > 1.0D) {
         mapYMax_W = mapYMax - (int)Math.round((maxV - 1.0D) * (double)LOTRGenLayerWorld.imageHeight * (double)this.zoomScale);
         maxV = 1.0D;
         if (minV > 1.0D) {
            minV = 1.0D;
            mapYMin_W = mapYMax_W;
         }
      }

      LOTRTextures.drawMap(this.field_146297_k.field_71439_g, sepia, (double)mapXMin_W, (double)mapXMax_W, (double)mapYMin_W, (double)mapYMax_W, (double)this.field_73735_i, minU, maxU, minV, maxV, alpha);
      if (overlay && !isOSRS()) {
         LOTRTextures.drawMapOverlay(this.field_146297_k.field_71439_g, (double)mapXMin, (double)mapXMax, (double)mapYMin, (double)mapYMax, (double)this.field_73735_i, minU, maxU, minV, maxV);
      }

   }

   private void renderGraduatedRects(int x1, int y1, int x2, int y2, int c1, int c2, int w) {
      float[] rgb1 = (new Color(c1)).getColorComponents((float[])null);
      float[] rgb2 = (new Color(c2)).getColorComponents((float[])null);

      for(int l = w - 1; l >= 0; --l) {
         float f = (float)l / (float)(w - 1);
         float r = rgb1[0] + (rgb2[0] - rgb1[0]) * f;
         float g = rgb1[1] + (rgb2[1] - rgb1[1]) * f;
         float b = rgb1[2] + (rgb2[2] - rgb1[2]) * f;
         int color = (new Color(r, g, b)).getRGB() + -16777216;
         func_73734_a(x1 - l, y1 - l, x2 + l, y2 + l, color);
      }

   }

   private void renderMapWidgets(int mouseX, int mouseY) {
      this.widgetAddCWP.visible = !this.hasOverlay && this.isMiddleEarth();
      this.widgetAddCWP.setTexVIndex(this.canCreateWaypointAtPosition() ? 0 : 1);
      this.widgetDelCWP.visible = !this.hasOverlay && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint && !((LOTRCustomWaypoint)this.selectedWaypoint).isShared();
      this.widgetRenameCWP.visible = !this.hasOverlay && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint && !((LOTRCustomWaypoint)this.selectedWaypoint).isShared();
      this.widgetToggleWPs.visible = !this.hasOverlay;
      this.widgetToggleWPs.setTexVIndex(showWP ? 0 : 1);
      this.widgetToggleCWPs.visible = !this.hasOverlay;
      this.widgetToggleCWPs.setTexVIndex(showCWP ? 0 : 1);
      this.widgetToggleHiddenSWPs.visible = !this.hasOverlay;
      this.widgetToggleHiddenSWPs.setTexVIndex(showHiddenSWP ? 1 : 0);
      this.widgetZoomIn.visible = !this.hasOverlay;
      this.widgetZoomIn.setTexVIndex(zoomPower < 4 ? 0 : 1);
      this.widgetZoomOut.visible = !this.hasOverlay;
      this.widgetZoomOut.setTexVIndex(zoomPower > -3 ? 0 : 1);
      this.widgetFullScreen.visible = !this.hasOverlay;
      this.widgetSepia.visible = !this.hasOverlay;
      this.widgetLabels.visible = !this.hasOverlay;
      this.widgetShareCWP.visible = !this.hasOverlay && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint && !((LOTRCustomWaypoint)this.selectedWaypoint).isShared();
      this.widgetHideSWP.visible = !this.hasOverlay && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint && ((LOTRCustomWaypoint)this.selectedWaypoint).isShared() && !((LOTRCustomWaypoint)this.selectedWaypoint).isSharedHidden();
      this.widgetUnhideSWP.visible = !this.hasOverlay && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint && ((LOTRCustomWaypoint)this.selectedWaypoint).isShared() && ((LOTRCustomWaypoint)this.selectedWaypoint).isSharedHidden();
      LOTRGuiMapWidget mouseOverWidget = null;
      Iterator var4 = mapWidgets.iterator();

      while(var4.hasNext()) {
         LOTRGuiMapWidget widget = (LOTRGuiMapWidget)var4.next();
         if (widget.visible) {
            this.field_146297_k.func_110434_K().func_110577_a(mapIconsTexture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.func_73729_b(mapXMin + widget.getMapXPos(mapWidth), mapYMin + widget.getMapYPos(mapHeight), widget.getTexU(), widget.getTexV(), widget.width, widget.width);
            if (widget.isMouseOver(mouseX - mapXMin, mouseY - mapYMin, mapWidth, mapHeight)) {
               mouseOverWidget = widget;
            }
         }
      }

      if (mouseOverWidget != null) {
         float z = this.field_73735_i;
         int stringWidth = 200;
         List desc = this.field_146289_q.func_78271_c(mouseOverWidget.getTranslatedName(), stringWidth);
         this.func_146283_a(desc, mouseX, mouseY);
         GL11.glDisable(2896);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73735_i = z;
      }

   }

   private boolean canCreateWaypointAtPosition() {
      int minY = LOTRConfig.getCustomWaypointMinY(this.field_146297_k.field_71441_e);
      return minY < 0 || this.field_146297_k.field_71439_g.field_70121_D.field_72338_b >= (double)minY;
   }

   private void renderFullscreenSubtitles(String... lines) {
      int strX = mapXMin + mapWidth / 2;
      int strY = mapYMax + 7;
      if (this.isConquestGrid) {
         strY = mapYMax + 26;
      }

      int border = this.field_146289_q.field_78288_b + 3;
      if (lines.length == 1) {
         strY += border / 2;
      }

      String[] var5 = lines;
      int var6 = lines.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String s = var5[var7];
         this.drawCenteredString(s, strX, strY, 16777215);
         strY += border;
      }

   }

   private void renderPlayers(int mouseX, int mouseY) {
      String mouseOverPlayerName = null;
      double mouseOverPlayerX = 0.0D;
      double mouseOverPlayerY = 0.0D;
      double distanceMouseOverPlayer = Double.MAX_VALUE;
      int iconWidthHalf = 4;
      Map playersToRender = new HashMap();
      playersToRender.putAll(playerLocations);
      if (this.isMiddleEarth()) {
         playersToRender.put(this.field_146297_k.field_71439_g.func_110124_au(), new LOTRGuiMap.PlayerLocationInfo(this.field_146297_k.field_71439_g.func_146103_bH(), this.field_146297_k.field_71439_g.field_70165_t, this.field_146297_k.field_71439_g.field_70161_v));
      }

      Iterator var12 = playersToRender.entrySet().iterator();

      while(var12.hasNext()) {
         Entry entry = (Entry)var12.next();
         UUID player = (UUID)entry.getKey();
         LOTRGuiMap.PlayerLocationInfo info = (LOTRGuiMap.PlayerLocationInfo)entry.getValue();
         GameProfile profile = info.profile;
         String playerName = profile.getName();
         double playerPosX = info.posX;
         double playerPosZ = info.posZ;
         float[] pos = this.transformCoords(playerPosX, playerPosZ);
         int playerX = Math.round(pos[0]);
         int playerY = Math.round(pos[1]);
         double distToPlayer = this.renderPlayerIcon(profile, playerName, (double)playerX, (double)playerY, mouseX, mouseY);
         if (distToPlayer <= (double)(iconWidthHalf + 3) && distToPlayer <= distanceMouseOverPlayer) {
            mouseOverPlayerName = playerName;
            mouseOverPlayerX = (double)playerX;
            mouseOverPlayerY = (double)playerY;
            distanceMouseOverPlayer = distToPlayer;
         }
      }

      if (mouseOverPlayerName != null && !this.hasOverlay && !this.loadingConquestGrid) {
         int strWidth = this.field_146297_k.field_71466_p.func_78256_a(mouseOverPlayerName);
         int strHeight = this.field_146297_k.field_71466_p.field_78288_b;
         int rectX = (int)Math.round(mouseOverPlayerX);
         int rectY = (int)Math.round(mouseOverPlayerY);
         rectY += iconWidthHalf + 3;
         int border = 3;
         int rectWidth = strWidth + border * 2;
         rectX -= rectWidth / 2;
         int rectHeight = strHeight + border * 2;
         int mapBorder2 = 2;
         rectX = Math.max(rectX, mapXMin + mapBorder2);
         rectX = Math.min(rectX, mapXMax - mapBorder2 - rectWidth);
         rectY = Math.max(rectY, mapYMin + mapBorder2);
         rectY = Math.min(rectY, mapYMax - mapBorder2 - rectHeight);
         GL11.glTranslatef(0.0F, 0.0F, 300.0F);
         this.drawFancyRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight);
         this.field_146297_k.field_71466_p.func_78276_b(mouseOverPlayerName, rectX + border, rectY + border, 16777215);
         GL11.glTranslatef(0.0F, 0.0F, -300.0F);
      }

   }

   private double renderPlayerIcon(GameProfile profile, String playerName, double playerX, double playerY, int mouseX, int mouseY) {
      Tessellator tessellator = Tessellator.field_78398_a;
      int iconWidthHalf = 4;
      int iconBorder = iconWidthHalf + 1;
      playerX = Math.max((double)(mapXMin + iconBorder), playerX);
      playerX = Math.min((double)(mapXMax - iconBorder - 1), playerX);
      playerY = Math.max((double)(mapYMin + iconBorder), playerY);
      playerY = Math.min((double)(mapYMax - iconBorder - 1), playerY);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      ResourceLocation skin = AbstractClientPlayer.field_110314_b;
      if (profile.getId().equals(this.field_146297_k.field_71439_g.func_110124_au())) {
         skin = this.field_146297_k.field_71439_g.func_110306_p();
      } else {
         Map map = this.field_146297_k.func_152342_ad().func_152788_a(profile);
         Type type = Type.SKIN;
         if (map.containsKey(type)) {
            skin = this.field_146297_k.func_152342_ad().func_152792_a((MinecraftProfileTexture)map.get(type), type);
         }
      }

      this.field_146297_k.func_110434_K().func_110577_a(skin);
      double iconMinU = 0.125D;
      double iconMaxU = 0.25D;
      double iconMinV = 0.25D;
      double iconMaxV = 0.5D;
      double playerX_d = playerX + 0.5D;
      double playerY_d = playerY + 0.5D;
      tessellator.func_78382_b();
      tessellator.func_78374_a(playerX_d - (double)iconWidthHalf, playerY_d + (double)iconWidthHalf, (double)this.field_73735_i, iconMinU, iconMaxV);
      tessellator.func_78374_a(playerX_d + (double)iconWidthHalf, playerY_d + (double)iconWidthHalf, (double)this.field_73735_i, iconMaxU, iconMaxV);
      tessellator.func_78374_a(playerX_d + (double)iconWidthHalf, playerY_d - (double)iconWidthHalf, (double)this.field_73735_i, iconMaxU, iconMinV);
      tessellator.func_78374_a(playerX_d - (double)iconWidthHalf, playerY_d - (double)iconWidthHalf, (double)this.field_73735_i, iconMinU, iconMinV);
      tessellator.func_78381_a();
      iconMinU = 0.625D;
      iconMaxU = 0.75D;
      iconMinV = 0.25D;
      iconMaxV = 0.5D;
      tessellator.func_78382_b();
      tessellator.func_78374_a(playerX_d - (double)iconWidthHalf - 0.5D, playerY_d + (double)iconWidthHalf + 0.5D, (double)this.field_73735_i, iconMinU, iconMaxV);
      tessellator.func_78374_a(playerX_d + (double)iconWidthHalf + 0.5D, playerY_d + (double)iconWidthHalf + 0.5D, (double)this.field_73735_i, iconMaxU, iconMaxV);
      tessellator.func_78374_a(playerX_d + (double)iconWidthHalf + 0.5D, playerY_d - (double)iconWidthHalf - 0.5D, (double)this.field_73735_i, iconMaxU, iconMinV);
      tessellator.func_78374_a(playerX_d - (double)iconWidthHalf - 0.5D, playerY_d - (double)iconWidthHalf - 0.5D, (double)this.field_73735_i, iconMinU, iconMinV);
      tessellator.func_78381_a();
      double dx = playerX - (double)mouseX;
      double dy = playerY - (double)mouseY;
      double distToPlayer = Math.sqrt(dx * dx + dy * dy);
      return distToPlayer;
   }

   private void renderMiniQuests(EntityPlayer entityplayer, int mouseX, int mouseY) {
      if (!this.hasOverlay) {
         LOTRMiniQuest mouseOverQuest = null;
         int mouseOverQuestX = 0;
         int mouseOverQuestY = 0;
         double distanceMouseOverQuest = Double.MAX_VALUE;
         List quests = LOTRLevelData.getData(entityplayer).getActiveMiniQuests();
         Iterator var10 = quests.iterator();

         int questX;
         while(var10.hasNext()) {
            LOTRMiniQuest quest = (LOTRMiniQuest)var10.next();
            ChunkCoordinates location = quest.getLastLocation();
            if (location != null) {
               float[] pos = this.transformCoords((float)location.field_71574_a, (float)location.field_71573_c);
               questX = Math.round(pos[0]);
               int questY = Math.round(pos[1]);
               float scale = 0.5F;
               float invScale = 1.0F / scale;
               IIcon icon = questBookIcon.func_77954_c();
               int iconWidthHalf = icon.func_94211_a() / 2;
               iconWidthHalf = (int)((float)iconWidthHalf * scale);
               int iconBorder = iconWidthHalf + 1;
               questX = Math.max(mapXMin + iconBorder, questX);
               questX = Math.min(mapXMax - iconBorder - 1, questX);
               questY = Math.max(mapYMin + iconBorder, questY);
               questY = Math.min(mapYMax - iconBorder - 1, questY);
               int iconX = Math.round((float)questX * invScale);
               int iconY = Math.round((float)questY * invScale);
               iconX -= iconWidthHalf;
               iconY -= iconWidthHalf;
               GL11.glScalef(scale, scale, scale);
               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               GL11.glEnable(2896);
               GL11.glEnable(2884);
               renderItem.func_82406_b(this.field_146297_k.field_71466_p, this.field_146297_k.func_110434_K(), questBookIcon, iconX, iconY);
               GL11.glDisable(2896);
               GL11.glEnable(3008);
               GL11.glScalef(invScale, invScale, invScale);
               double dx = (double)(questX - mouseX);
               double dy = (double)(questY - mouseY);
               double distToQuest = Math.sqrt(dx * dx + dy * dy);
               if (distToQuest <= (double)(iconWidthHalf + 3) && distToQuest <= distanceMouseOverQuest) {
                  mouseOverQuest = quest;
                  mouseOverQuestX = questX;
                  mouseOverQuestY = questY;
                  distanceMouseOverQuest = distToQuest;
               }
            }
         }

         if (mouseOverQuest != null && !this.hasOverlay) {
            String name = mouseOverQuest.entityName;
            int stringWidth = this.field_146297_k.field_71466_p.func_78256_a(name);
            int stringHeight = this.field_146297_k.field_71466_p.field_78288_b;
            questX = mouseOverQuestY + 7;
            int border = 3;
            int rectWidth = stringWidth + border * 2;
            int x = mouseOverQuestX - rectWidth / 2;
            int rectHeight = stringHeight + border * 2;
            int mapBorder2 = 2;
            x = Math.max(x, mapXMin + mapBorder2);
            x = Math.min(x, mapXMax - mapBorder2 - rectWidth);
            questX = Math.max(questX, mapYMin + mapBorder2);
            questX = Math.min(questX, mapYMax - mapBorder2 - rectHeight);
            GL11.glTranslatef(0.0F, 0.0F, 300.0F);
            this.drawFancyRect(x, questX, x + rectWidth, questX + rectHeight);
            this.field_146297_k.field_71466_p.func_78276_b(name, x + border, questX + border, 16777215);
            GL11.glTranslatef(0.0F, 0.0F, -300.0F);
         }

      }
   }

   private void renderControlZone(int mouseX, int mouseY) {
      this.mouseControlZone = false;
      this.mouseControlZoneReduced = false;
      LOTRFaction faction = this.controlZoneFaction;
      if (faction.factionDimension == LOTRDimension.MIDDLE_EARTH) {
         List controlZones = faction.getControlZones();
         if (!controlZones.isEmpty()) {
            Tessellator tessellator = Tessellator.field_78398_a;
            this.setupMapClipping();
            GL11.glDisable(3553);

            label64:
            for(int pass = 0; pass <= 2; ++pass) {
               int color = 16711680;
               if (pass == 1) {
                  color = 5570560;
               }

               if (pass == 0) {
                  color = 16733525;
               }

               Iterator var8 = controlZones.iterator();

               while(true) {
                  LOTRControlZone zone;
                  float radius;
                  float dx;
                  do {
                     if (!var8.hasNext()) {
                        continue label64;
                     }

                     zone = (LOTRControlZone)var8.next();
                     radius = (float)zone.radius;
                     if (pass == 2) {
                        --radius;
                     }

                     if (pass == 0) {
                        radius = (float)(zone.radius + this.controlZoneFaction.getControlZoneReducedRange());
                     }

                     float radiusWorld = (float)LOTRWaypoint.mapToWorldR((double)radius);
                     tessellator.func_78371_b(9);
                     tessellator.func_78378_d(color);
                     int sides = 100;

                     for(int l = sides - 1; l >= 0; --l) {
                        dx = (float)l / (float)sides * 2.0F * 3.1415927F;
                        double x = (double)zone.xCoord;
                        double z = (double)zone.zCoord;
                        x += (double)(MathHelper.func_76134_b(dx) * radiusWorld);
                        z += (double)(MathHelper.func_76126_a(dx) * radiusWorld);
                        float[] trans = this.transformCoords(x, z);
                        tessellator.func_78377_a((double)trans[0], (double)trans[1], (double)this.field_73735_i);
                     }

                     tessellator.func_78381_a();
                  } while(this.mouseControlZone && this.mouseControlZoneReduced);

                  float[] trans = this.transformCoords((float)zone.xCoord, (float)zone.zCoord);
                  dx = (float)mouseX - trans[0];
                  float dy = (float)mouseY - trans[1];
                  float rScaled = radius * this.zoomScale;
                  if (dx * dx + dy * dy <= rScaled * rScaled) {
                     if (pass >= 1) {
                        this.mouseControlZone = true;
                     } else if (pass == 0) {
                        this.mouseControlZoneReduced = true;
                     }
                  }
               }
            }

            GL11.glEnable(3553);
            this.endMapClipping();
         }
      }

   }

   private void renderRoads() {
      if ((showWP || showCWP) && LOTRFixedStructures.hasMapFeatures(this.field_146297_k.field_71441_e)) {
         this.renderRoads(this.hasMapLabels());
      }
   }

   public void renderRoads(boolean labels) {
      float roadZoomlerp = (this.zoomExp - -3.3F) / 2.2F;
      roadZoomlerp = Math.min(roadZoomlerp, 1.0F);
      if (!this.enableZoomOutWPFading) {
         roadZoomlerp = 1.0F;
      }

      if (roadZoomlerp > 0.0F) {
         Iterator roadIterator = LOTRRoads.getAllRoadsForDisplay();

         while(roadIterator.hasNext()) {
            LOTRRoads road = (LOTRRoads)roadIterator.next();
            int interval = Math.round(400.0F / this.zoomScaleStable);
            interval = Math.max(interval, 1);

            for(int i = 0; i < road.roadPoints.length; i += interval) {
               LOTRRoads.RoadPoint point = road.roadPoints[i];
               float[] pos = this.transformCoords(point.x, point.z);
               float x = pos[0];
               float y = pos[1];
               if (x >= (float)mapXMin && x < (float)mapXMax && y >= (float)mapYMin && y < (float)mapYMax) {
                  double roadWidth = 1.0D;
                  int roadColor = 0;
                  float roadAlpha = roadZoomlerp;
                  if (isOSRS()) {
                     roadWidth = 3.0D * (double)this.zoomScale;
                     roadColor = 6575407;
                     roadAlpha = 1.0F;
                  }

                  double roadWidthLess1 = roadWidth - 1.0D;
                  GL11.glDisable(3553);
                  GL11.glEnable(3042);
                  GL11.glBlendFunc(770, 771);
                  Tessellator tessellator = Tessellator.field_78398_a;
                  tessellator.func_78382_b();
                  tessellator.func_78384_a(roadColor, (int)(roadAlpha * 255.0F));
                  tessellator.func_78377_a((double)x - roadWidthLess1, (double)y + roadWidth, (double)this.field_73735_i);
                  tessellator.func_78377_a((double)x + roadWidth, (double)y + roadWidth, (double)this.field_73735_i);
                  tessellator.func_78377_a((double)x + roadWidth, (double)y - roadWidthLess1, (double)this.field_73735_i);
                  tessellator.func_78377_a((double)x - roadWidthLess1, (double)y - roadWidthLess1, (double)this.field_73735_i);
                  tessellator.func_78381_a();
                  GL11.glDisable(3042);
                  GL11.glEnable(3553);
               }

               if (labels) {
                  int clip = 200;
                  if (x >= (float)(mapXMin - clip) && x <= (float)(mapXMax + clip) && y >= (float)(mapYMin - clip) && y <= (float)(mapYMax + clip)) {
                     float zoomlerp = (this.zoomExp - -1.0F) / 4.0F;
                     zoomlerp = Math.min(zoomlerp, 1.0F);
                     String name = road.getDisplayName();
                     int nameWidth = this.field_146289_q.func_78256_a(name);
                     int nameInterval = (int)((float)(nameWidth * 2 + 100) * 200.0F / this.zoomScaleStable);
                     if (i % nameInterval < interval) {
                        boolean endNear = false;
                        double dMax = ((double)nameWidth / 2.0D + 25.0D) * (double)zoomlerp;
                        double dMaxSq = dMax * dMax;
                        Iterator var22 = road.endpoints.iterator();

                        LOTRRoads.RoadPoint prevPoint;
                        float angle;
                        float alpha;
                        while(var22.hasNext()) {
                           prevPoint = (LOTRRoads.RoadPoint)var22.next();
                           float[] endPos = this.transformCoords(prevPoint.x, prevPoint.z);
                           float endX = endPos[0];
                           angle = endPos[1];
                           alpha = x - endX;
                           float dy = y - angle;
                           double dSq = (double)(alpha * alpha + dy * dy);
                           if (dSq < dMaxSq) {
                              endNear = true;
                           }
                        }

                        if (!endNear && zoomlerp > 0.0F) {
                           this.setupMapClipping();
                           GL11.glPushMatrix();
                           GL11.glTranslatef(x, y, 0.0F);
                           GL11.glScalef(zoomlerp, zoomlerp, zoomlerp);
                           LOTRRoads.RoadPoint nextPoint = road.roadPoints[Math.min(i + 1, road.roadPoints.length - 1)];
                           prevPoint = road.roadPoints[Math.max(i - 1, 0)];
                           double grad = (nextPoint.z - prevPoint.z) / (nextPoint.x - prevPoint.x);
                           angle = (float)Math.atan(grad);
                           angle = (float)Math.toDegrees((double)angle);
                           if (Math.abs(angle) > 90.0F) {
                              angle += 180.0F;
                           }

                           GL11.glRotatef(angle, 0.0F, 0.0F, 1.0F);
                           alpha = zoomlerp * 0.8F;
                           int alphaI = LOTRClientProxy.getAlphaInt(alpha);
                           GL11.glEnable(3042);
                           GL11.glBlendFunc(770, 771);
                           int strX = -nameWidth / 2;
                           int strY = -15;
                           this.field_146289_q.func_78276_b(name, strX + 1, strY + 1, 0 + (alphaI << 24));
                           this.field_146289_q.func_78276_b(name, strX, strY, 16777215 + (alphaI << 24));
                           GL11.glDisable(3042);
                           GL11.glPopMatrix();
                           this.endMapClipping();
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private boolean isWaypointVisible(LOTRAbstractWaypoint wp) {
      if (wp instanceof LOTRCustomWaypoint) {
         LOTRCustomWaypoint cwp = (LOTRCustomWaypoint)wp;
         return cwp.isShared() && cwp.isSharedHidden() && !showHiddenSWP ? false : showCWP;
      } else {
         return showWP;
      }
   }

   private void renderWaypoints(int pass, int mouseX, int mouseY) {
      if ((showWP || showCWP || showHiddenSWP) && LOTRFixedStructures.hasMapFeatures(this.field_146297_k.field_71441_e)) {
         this.renderWaypoints(LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getAllAvailableWaypoints(), pass, mouseX, mouseY, this.hasMapLabels(), false);
      }
   }

   public void renderWaypoints(List waypoints, int pass, int mouseX, int mouseY, boolean labels, boolean overrideToggles) {
      this.setupMapClipping();
      LOTRAbstractWaypoint mouseOverWP = null;
      double distanceMouseOverWP = Double.MAX_VALUE;
      float wpZoomlerp = (this.zoomExp - -3.3F) / 2.2F;
      wpZoomlerp = Math.min(wpZoomlerp, 1.0F);
      if (!this.enableZoomOutWPFading) {
         wpZoomlerp = 1.0F;
      }

      if (wpZoomlerp > 0.0F) {
         Iterator var11 = waypoints.iterator();

         label104:
         while(true) {
            LOTRAbstractWaypoint waypoint;
            boolean unlocked;
            boolean hidden;
            do {
               do {
                  if (!var11.hasNext()) {
                     break label104;
                  }

                  waypoint = (LOTRAbstractWaypoint)var11.next();
                  unlocked = this.field_146297_k.field_71439_g != null && this.field_146297_k.field_71439_g.field_70170_p != null && waypoint.hasPlayerUnlocked(this.field_146297_k.field_71439_g);
                  hidden = waypoint.isHidden();
                  boolean custom = waypoint instanceof LOTRCustomWaypoint;
                  boolean var10000;
                  if (waypoint instanceof LOTRCustomWaypoint && ((LOTRCustomWaypoint)waypoint).isShared()) {
                     var10000 = true;
                  } else {
                     var10000 = false;
                  }
               } while(!this.isWaypointVisible(waypoint) && !overrideToggles);
            } while(hidden && !unlocked);

            float[] pos = this.transformCoords((float)waypoint.getXCoord(), (float)waypoint.getZCoord());
            float x = pos[0];
            float y = pos[1];
            int clip = 200;
            if (x >= (float)(mapXMin - clip) && x <= (float)(mapXMax + clip) && y >= (float)(mapYMin - clip) && y <= (float)(mapYMax + clip)) {
               if (pass == 0) {
                  GL11.glEnable(3042);
                  GL11.glBlendFunc(770, 771);
                  float zoomlerp;
                  if (isOSRS()) {
                     zoomlerp = 0.33F;
                     GL11.glPushMatrix();
                     GL11.glScalef(0.33F, 0.33F, 1.0F);
                     this.field_146297_k.func_110434_K().func_110577_a(LOTRTextures.osrsTexture);
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     this.drawTexturedModalRectFloat(x / 0.33F - 8.0F, y / 0.33F - 8.0F, 0, 0, 15.0F, 15.0F);
                     GL11.glPopMatrix();
                  } else {
                     LOTRAbstractWaypoint.WaypointLockState state = this.field_146297_k.field_71439_g != null ? waypoint.getLockState(this.field_146297_k.field_71439_g) : LOTRAbstractWaypoint.WaypointLockState.STANDARD_UNLOCKED;
                     this.field_146297_k.func_110434_K().func_110577_a(mapIconsTexture);
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, wpZoomlerp);
                     this.drawTexturedModalRectFloat(x - 2.0F, y - 2.0F, state.iconU, state.iconV, 4.0F, 4.0F);
                  }

                  GL11.glDisable(3042);
                  if (labels) {
                     zoomlerp = (this.zoomExp - -1.0F) / 4.0F;
                     zoomlerp = Math.min(zoomlerp, 1.0F);
                     if (zoomlerp > 0.0F) {
                        GL11.glPushMatrix();
                        GL11.glTranslatef(x, y, 0.0F);
                        GL11.glScalef(zoomlerp, zoomlerp, zoomlerp);
                        float alpha = zoomlerp * 0.8F;
                        int alphaI = (int)(alpha * 255.0F);
                        alphaI = MathHelper.func_76125_a(alphaI, 4, 255);
                        GL11.glEnable(3042);
                        GL11.glBlendFunc(770, 771);
                        String s = waypoint.getDisplayName();
                        int strX = -this.field_146289_q.func_78256_a(s) / 2;
                        int strY = -15;
                        this.field_146289_q.func_78276_b(s, strX + 1, strY + 1, 0 + (alphaI << 24));
                        this.field_146289_q.func_78276_b(s, strX, strY, 16777215 + (alphaI << 24));
                        GL11.glDisable(3042);
                        GL11.glPopMatrix();
                     }
                  }
               }

               if (pass == 1 && waypoint != this.selectedWaypoint && x >= (float)(mapXMin - 2) && x <= (float)(mapXMax + 2) && y >= (float)(mapYMin - 2) && y <= (float)(mapYMax + 2)) {
                  double dx = (double)(x - (float)mouseX);
                  double dy = (double)(y - (float)mouseY);
                  double distToWP = Math.sqrt(dx * dx + dy * dy);
                  if (distToWP <= 5.0D && distToWP <= distanceMouseOverWP) {
                     mouseOverWP = waypoint;
                     distanceMouseOverWP = distToWP;
                  }
               }
            }
         }
      }

      if (pass == 1 && mouseOverWP != null && !this.hasOverlay && !this.loadingConquestGrid) {
         this.renderWaypointTooltip(mouseOverWP, false, mouseX, mouseY);
      }

      this.endMapClipping();
   }

   private void renderWaypointTooltip(LOTRAbstractWaypoint waypoint, boolean selected, int mouseX, int mouseY) {
      String name = waypoint.getDisplayName();
      int wpX = waypoint.getXCoord();
      int wpZ = waypoint.getZCoord();
      int wpY = waypoint.getYCoordSaved();
      String coords;
      if (wpY >= 0) {
         coords = StatCollector.func_74837_a("lotr.gui.map.coordsY", new Object[]{wpX, wpY, wpZ});
      } else {
         coords = StatCollector.func_74837_a("lotr.gui.map.coords", new Object[]{wpX, wpZ});
      }

      String loreText = waypoint.getLoreText(this.field_146297_k.field_71439_g);
      float guiScale = (float)(new ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d)).func_78325_e();
      float loreScale = guiScale - 1.0F;
      if (guiScale <= 2.0F) {
         loreScale = guiScale;
      }

      float loreScaleRel = loreScale / guiScale;
      float loreScaleRelInv = 1.0F / loreScaleRel;
      int loreFontHeight = MathHelper.func_76143_f((double)((float)this.field_146289_q.field_78288_b * loreScaleRel));
      float[] pos = this.transformCoords((float)wpX, (float)wpZ);
      int rectX = Math.round(pos[0]);
      int rectY = Math.round(pos[1]);
      rectY += 5;
      int border = 3;
      int stringHeight = this.field_146289_q.field_78288_b;
      int innerRectWidth = this.field_146289_q.func_78256_a(name);
      if (selected) {
         innerRectWidth = Math.max(innerRectWidth, this.field_146289_q.func_78256_a(coords));
         if (loreText != null) {
            innerRectWidth += 50;
            innerRectWidth = Math.round((float)innerRectWidth * (loreScaleRel / 0.66667F));
         }
      }

      int rectWidth = innerRectWidth + border * 2;
      rectX -= rectWidth / 2;
      int rectHeight = border * 2 + stringHeight;
      if (selected) {
         rectHeight += border + stringHeight;
         if (loreText != null) {
            rectHeight += border;
            rectHeight += this.field_146289_q.func_78271_c(loreText, (int)((float)innerRectWidth * loreScaleRelInv)).size() * loreFontHeight;
            rectHeight += border;
         }
      }

      int mapBorder2 = 2;
      rectX = Math.max(rectX, mapXMin + mapBorder2);
      rectX = Math.min(rectX, mapXMax - mapBorder2 - rectWidth);
      rectY = Math.max(rectY, mapYMin + mapBorder2);
      rectY = Math.min(rectY, mapYMax - mapBorder2 - rectHeight);
      int stringX = rectX + rectWidth / 2;
      int stringY = rectY + border;
      GL11.glTranslatef(0.0F, 0.0F, 300.0F);
      this.drawFancyRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight);
      this.drawCenteredString(name, stringX, stringY, 16777215);
      stringY += this.field_146289_q.field_78288_b + border;
      if (selected) {
         this.drawCenteredString(coords, stringX, stringY, 16777215);
         stringY += this.field_146289_q.field_78288_b + border * 2;
         if (loreText != null) {
            GL11.glPushMatrix();
            GL11.glScalef(loreScaleRel, loreScaleRel, 1.0F);
            List loreLines = this.field_146289_q.func_78271_c(loreText, (int)((float)innerRectWidth * loreScaleRelInv));

            for(int l = 0; l < loreLines.size(); ++l) {
               String line = (String)loreLines.get(l);
               this.drawCenteredString(line, (int)((float)stringX * loreScaleRelInv), (int)((float)stringY * loreScaleRelInv), 16777215);
               stringY += loreFontHeight;
            }

            GL11.glPopMatrix();
         }
      }

      GL11.glTranslatef(0.0F, 0.0F, -300.0F);
   }

   private void renderLabels() {
      if (this.hasMapLabels()) {
         this.setupMapClipping();
         LOTRMapLabels[] allLabels = LOTRMapLabels.allMapLabels();
         LOTRMapLabels[] var2 = allLabels;
         int var3 = allLabels.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            LOTRMapLabels label = var2[var4];
            float[] pos = this.transformMapCoords((float)label.posX, (float)label.posY);
            float x = pos[0];
            float y = pos[1];
            float zoomlerp = (this.zoomExp - label.minZoom) / (label.maxZoom - label.minZoom);
            if (zoomlerp > 0.0F && zoomlerp < 1.0F) {
               float alpha = (0.5F - Math.abs(zoomlerp - 0.5F)) / 0.5F;
               alpha *= 0.7F;
               if (isOSRS()) {
                  if (alpha < 0.3F) {
                     continue;
                  }

                  alpha = 1.0F;
               }

               GL11.glPushMatrix();
               GL11.glTranslatef(x, y, 0.0F);
               float scale = this.zoomScale * label.scale;
               GL11.glScalef(scale, scale, scale);
               if (!isOSRS()) {
                  GL11.glRotatef((float)label.angle, 0.0F, 0.0F, 1.0F);
               }

               int alphaI = (int)(alpha * 255.0F);
               alphaI = MathHelper.func_76125_a(alphaI, 4, 255);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               float alphaFunc = GL11.glGetFloat(3010);
               GL11.glAlphaFunc(516, 0.01F);
               String s = label.getDisplayName();
               int strX = -this.field_146289_q.func_78256_a(s) / 2;
               int strY = -this.field_146289_q.field_78288_b / 2;
               if (isOSRS()) {
                  if (label.scale > 2.5F) {
                     this.field_146289_q.func_78276_b(s, strX + 1, strY + 1, 0 + (alphaI << 24));
                     this.field_146289_q.func_78276_b(s, strX, strY, 16755200 + (alphaI << 24));
                  } else {
                     this.field_146289_q.func_78276_b(s, strX + 1, strY + 1, 0 + (alphaI << 24));
                     this.field_146289_q.func_78276_b(s, strX, strY, 16777215 + (alphaI << 24));
                  }
               } else {
                  this.field_146289_q.func_78276_b(s, strX, strY, 16777215 + (alphaI << 24));
               }

               GL11.glAlphaFunc(516, alphaFunc);
               GL11.glDisable(3042);
               GL11.glPopMatrix();
            }
         }

         this.endMapClipping();
      }
   }

   private void setupMapClipping() {
      ScaledResolution sr = new ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
      int scale = sr.func_78325_e();
      GL11.glEnable(3089);
      GL11.glScissor(mapXMin * scale, (this.field_146295_m - mapYMax) * scale, mapWidth * scale, mapHeight * scale);
   }

   private void endMapClipping() {
      GL11.glDisable(3089);
   }

   private float[] transformCoords(float x, float z) {
      x = x / (float)LOTRGenLayerWorld.scale + 810.0F;
      z = z / (float)LOTRGenLayerWorld.scale + 730.0F;
      return this.transformMapCoords(x, z);
   }

   private float[] transformCoords(double x, double z) {
      return this.transformCoords((float)x, (float)z);
   }

   private float[] transformMapCoords(float x, float z) {
      x -= this.posX;
      z -= this.posY;
      x *= this.zoomScale;
      z *= this.zoomScale;
      x += (float)(mapXMin + mapWidth / 2);
      z += (float)(mapYMin + mapHeight / 2);
      return new float[]{x, z};
   }

   private void drawFancyRect(int x1, int y1, int x2, int y2) {
      func_73734_a(x1, y1, x2, y2, -1073741824);
      this.func_73730_a(x1 - 1, x2, y1 - 1, -6156032);
      this.func_73730_a(x1 - 1, x2, y2, -6156032);
      this.func_73728_b(x1 - 1, y1 - 1, y2, -6156032);
      this.func_73728_b(x2, y1 - 1, y2, -6156032);
   }

   private boolean isValidWaypointName(String name) {
      return !StringUtils.isBlank(name);
   }

   private LOTRFellowshipClient getFellowshipByName(String name) {
      String fsName = StringUtils.strip(name);
      return LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getClientFellowshipByName(fsName);
   }

   private boolean isExistingFellowshipName(String name) {
      LOTRFellowshipClient fs = this.getFellowshipByName(name);
      return fs != null;
   }

   private boolean isExistingUnsharedFellowshipName(String name, LOTRCustomWaypoint waypoint) {
      LOTRFellowshipClient fs = this.getFellowshipByName(name);
      return fs != null && !waypoint.hasSharedFellowship(fs.getFellowshipID());
   }

   private boolean isFellowshipAlreadyShared(String name, LOTRCustomWaypoint waypoint) {
      return this.isExistingFellowshipName(name) && !this.isExistingUnsharedFellowshipName(name, waypoint);
   }

   protected void func_73869_a(char c, int i) {
      if (this.hasOverlay) {
         if (this.creatingWaypointNew && this.nameWPTextField.func_146201_a(c, i)) {
            return;
         }

         if (this.renamingWaypoint && this.nameWPTextField.func_146201_a(c, i)) {
            return;
         }

         if (this.sharingWaypointNew && this.nameWPTextField.func_146201_a(c, i)) {
            return;
         }

         if (i == 1 || i == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i()) {
            if (this.creatingWaypointNew) {
               this.openOverlayCreate();
            } else if (this.sharingWaypointNew) {
               this.openOverlayShare();
            } else {
               this.closeOverlay();
            }

            return;
         }
      } else {
         if (!this.loadingConquestGrid) {
            LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g);
            if (i == LOTRKeyHandler.keyBindingFastTravel.func_151463_i() && this.isMiddleEarth() && this.selectedWaypoint != null && this.selectedWaypoint.hasPlayerUnlocked(this.field_146297_k.field_71439_g) && pd.getTimeSinceFT() >= pd.getWaypointFTTime(this.selectedWaypoint, this.field_146297_k.field_71439_g)) {
               LOTRPacketFastTravel packet = new LOTRPacketFastTravel(this.selectedWaypoint);
               LOTRPacketHandler.networkWrapper.sendToServer(packet);
               this.field_146297_k.field_71439_g.func_71053_j();
               return;
            }

            if (this.selectedWaypoint == null && i == LOTRKeyHandler.keyBindingMapTeleport.func_151463_i() && this.isMouseWithinMap && this.canTeleport()) {
               LOTRPacketMapTp packet = new LOTRPacketMapTp(this.mouseXCoord, this.mouseZCoord);
               LOTRPacketHandler.networkWrapper.sendToServer(packet);
               this.field_146297_k.field_71439_g.func_71053_j();
               return;
            }
         }

         if (this.hasControlZones && (i == 1 || i == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i())) {
            Minecraft.func_71410_x().func_147108_a(new LOTRGuiFactions());
            return;
         }

         super.func_73869_a(c, i);
      }

   }

   private void closeOverlay() {
      this.hasOverlay = false;
      this.overlayLines = null;
      this.creatingWaypoint = false;
      this.creatingWaypointNew = false;
      this.deletingWaypoint = false;
      this.renamingWaypoint = false;
      this.sharingWaypoint = false;
      this.sharingWaypointNew = false;
      this.buttonOverlayFunction.field_146124_l = this.buttonOverlayFunction.field_146125_m = false;
      this.nameWPTextField.func_146180_a("");
   }

   private void openOverlayCreate() {
      this.closeOverlay();
      this.hasOverlay = true;
      this.creatingWaypoint = true;
      int numWaypoints = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getCustomWaypoints().size();
      int maxWaypoints = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getMaxCustomWaypoints();
      int remaining = maxWaypoints - numWaypoints;
      if (remaining <= 0) {
         this.overlayLines = new String[]{StatCollector.func_74837_a("lotr.gui.map.customWaypoint.allUsed.1", new Object[]{maxWaypoints}), "", StatCollector.func_74838_a("lotr.gui.map.customWaypoint.allUsed.2")};
      } else {
         this.overlayLines = new String[]{StatCollector.func_74837_a("lotr.gui.map.customWaypoint.create.1", new Object[]{numWaypoints, maxWaypoints}), "", StatCollector.func_74837_a("lotr.gui.map.customWaypoint.create.2", new Object[0])};
         this.buttonOverlayFunction.field_146125_m = true;
         this.buttonOverlayFunction.field_146126_j = StatCollector.func_74838_a("lotr.gui.map.customWaypoint.create.button");
      }

   }

   private void openOverlayCreateNew() {
      this.closeOverlay();
      this.hasOverlay = true;
      this.creatingWaypointNew = true;
      this.overlayLines = new String[]{StatCollector.func_74838_a("lotr.gui.map.customWaypoint.createNew.1")};
      this.buttonOverlayFunction.field_146125_m = true;
      this.buttonOverlayFunction.field_146126_j = StatCollector.func_74838_a("lotr.gui.map.customWaypoint.createNew.button");
   }

   private void openOverlayDelete() {
      this.closeOverlay();
      this.hasOverlay = true;
      this.deletingWaypoint = true;
      this.overlayLines = new String[]{StatCollector.func_74837_a("lotr.gui.map.customWaypoint.delete.1", new Object[]{this.selectedWaypoint.getDisplayName()})};
      this.buttonOverlayFunction.field_146125_m = true;
      this.buttonOverlayFunction.field_146126_j = StatCollector.func_74838_a("lotr.gui.map.customWaypoint.delete.button");
   }

   private void openOverlayRename() {
      this.closeOverlay();
      this.hasOverlay = true;
      this.renamingWaypoint = true;
      this.overlayLines = new String[]{StatCollector.func_74837_a("lotr.gui.map.customWaypoint.rename.1", new Object[]{this.selectedWaypoint.getDisplayName()})};
      this.buttonOverlayFunction.field_146125_m = true;
      this.buttonOverlayFunction.field_146126_j = StatCollector.func_74838_a("lotr.gui.map.customWaypoint.rename.button");
   }

   private void openOverlayShare() {
      this.closeOverlay();
      this.hasOverlay = true;
      this.sharingWaypoint = true;
      this.overlayLines = new String[]{StatCollector.func_74837_a("lotr.gui.map.customWaypoint.share.1", new Object[]{this.selectedWaypoint.getDisplayName()})};
      this.buttonOverlayFunction.field_146125_m = true;
      this.buttonOverlayFunction.field_146126_j = StatCollector.func_74838_a("lotr.gui.map.customWaypoint.share.button");
   }

   private void openOverlayShareNew() {
      this.closeOverlay();
      this.hasOverlay = true;
      this.sharingWaypointNew = true;
      this.overlayLines = new String[]{StatCollector.func_74837_a("lotr.gui.map.customWaypoint.shareNew.1", new Object[]{this.selectedWaypoint.getDisplayName()})};
      this.buttonOverlayFunction.field_146125_m = true;
      this.buttonOverlayFunction.field_146126_j = StatCollector.func_74838_a("lotr.gui.map.customWaypoint.shareNew.button");
   }

   protected void func_73864_a(int i, int j, int k) {
      LOTRGuiMapWidget mouseWidget = null;
      Iterator var5 = mapWidgets.iterator();

      while(var5.hasNext()) {
         LOTRGuiMapWidget widget = (LOTRGuiMapWidget)var5.next();
         if (widget.isMouseOver(i - mapXMin, j - mapYMin, mapWidth, mapHeight)) {
            mouseWidget = widget;
            break;
         }
      }

      if (this.hasOverlay && (this.creatingWaypointNew || this.renamingWaypoint || this.sharingWaypointNew)) {
         this.nameWPTextField.func_146192_a(i, j, k);
      }

      if (this.hasOverlay && k == 0 && this.sharingWaypoint && this.mouseOverRemoveSharedFellowship != null) {
         String fsName = this.mouseOverRemoveSharedFellowship.getName();
         LOTRPacketShareCWP packet = new LOTRPacketShareCWP((LOTRCustomWaypoint)this.selectedWaypoint, fsName, false);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      } else {
         if (!this.hasOverlay && k == 0 && this.isMiddleEarth() && this.selectedWaypoint instanceof LOTRCustomWaypoint) {
            LOTRCustomWaypoint cwp = (LOTRCustomWaypoint)this.selectedWaypoint;
            if (!cwp.isShared()) {
               if (mouseWidget == this.widgetDelCWP) {
                  this.openOverlayDelete();
                  return;
               }

               if (mouseWidget == this.widgetRenameCWP) {
                  this.openOverlayRename();
                  return;
               }

               if (mouseWidget == this.widgetShareCWP) {
                  this.openOverlayShare();
                  return;
               }
            } else {
               LOTRPacketCWPSharedHide packet;
               if (mouseWidget == this.widgetHideSWP) {
                  packet = new LOTRPacketCWPSharedHide(cwp, true);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  this.selectedWaypoint = null;
                  return;
               }

               if (mouseWidget == this.widgetUnhideSWP) {
                  packet = new LOTRPacketCWPSharedHide(cwp, false);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  return;
               }
            }
         }

         if (!this.hasOverlay && k == 0 && this.isMiddleEarth() && mouseWidget == this.widgetAddCWP && this.canCreateWaypointAtPosition()) {
            this.openOverlayCreate();
         } else {
            if (!this.hasOverlay && k == 0) {
               if (mouseWidget == this.widgetToggleWPs) {
                  showWP = !showWP;
                  LOTRClientProxy.sendClientInfoPacket((LOTRFaction)null, (Map)null);
                  return;
               }

               if (mouseWidget == this.widgetToggleCWPs) {
                  showCWP = !showCWP;
                  LOTRClientProxy.sendClientInfoPacket((LOTRFaction)null, (Map)null);
                  return;
               }

               if (mouseWidget == this.widgetToggleHiddenSWPs) {
                  showHiddenSWP = !showHiddenSWP;
                  LOTRClientProxy.sendClientInfoPacket((LOTRFaction)null, (Map)null);
                  return;
               }

               if (this.zoomTicks == 0) {
                  if (mouseWidget == this.widgetZoomIn && zoomPower < 4) {
                     this.zoomIn();
                     return;
                  }

                  if (mouseWidget == this.widgetZoomOut && zoomPower > -3) {
                     this.zoomOut();
                     return;
                  }
               }

               if (mouseWidget == this.widgetFullScreen) {
                  fullscreen = !fullscreen;
                  ScaledResolution sr = new ScaledResolution(this.field_146297_k, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
                  this.func_146280_a(this.field_146297_k, sr.func_78326_a(), sr.func_78328_b());
                  return;
               }

               if (mouseWidget == this.widgetSepia) {
                  LOTRConfig.toggleSepia();
                  return;
               }

               if (mouseWidget == this.widgetLabels) {
                  this.toggleMapLabels();
                  return;
               }
            }

            if (!this.hasOverlay && !this.loadingConquestGrid && k == 0 && this.isMouseWithinMap) {
               this.selectedWaypoint = null;
               double distanceSelectedWP = Double.MAX_VALUE;
               List waypoints = LOTRLevelData.getData((EntityPlayer)this.field_146297_k.field_71439_g).getAllAvailableWaypoints();
               Iterator var8 = waypoints.iterator();

               label150:
               while(true) {
                  LOTRAbstractWaypoint waypoint;
                  boolean unlocked;
                  boolean hidden;
                  do {
                     do {
                        if (!var8.hasNext()) {
                           break label150;
                        }

                        waypoint = (LOTRAbstractWaypoint)var8.next();
                        unlocked = waypoint.hasPlayerUnlocked(this.field_146297_k.field_71439_g);
                        hidden = waypoint.isHidden();
                     } while(!this.isWaypointVisible(waypoint));
                  } while(hidden && !unlocked);

                  float[] pos = this.transformCoords((float)waypoint.getXCoord(), (float)waypoint.getZCoord());
                  float x = pos[0];
                  float y = pos[1];
                  float dx = x - (float)i;
                  float dy = y - (float)j;
                  double distToWP = Math.sqrt((double)(dx * dx + dy * dy));
                  if (distToWP <= 5.0D && distToWP <= distanceSelectedWP) {
                     this.selectedWaypoint = waypoint;
                     distanceSelectedWP = distToWP;
                  }
               }
            }

            super.func_73864_a(i, j, k);
         }
      }
   }

   protected void func_146284_a(GuiButton button) {
      if (button.field_146124_l) {
         if (button == this.buttonOverlayFunction) {
            if (this.creatingWaypoint) {
               this.openOverlayCreateNew();
            } else {
               String fsName;
               if (this.creatingWaypointNew && this.isValidWaypointName(this.nameWPTextField.func_146179_b())) {
                  fsName = this.nameWPTextField.func_146179_b();
                  LOTRPacketCreateCWP packet = new LOTRPacketCreateCWP(fsName);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  this.closeOverlay();
               } else if (this.deletingWaypoint) {
                  LOTRPacketDeleteCWP packet = new LOTRPacketDeleteCWP((LOTRCustomWaypoint)this.selectedWaypoint);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  this.closeOverlay();
                  this.selectedWaypoint = null;
               } else if (this.renamingWaypoint && this.isValidWaypointName(this.nameWPTextField.func_146179_b())) {
                  fsName = this.nameWPTextField.func_146179_b();
                  LOTRPacketRenameCWP packet = new LOTRPacketRenameCWP((LOTRCustomWaypoint)this.selectedWaypoint, fsName);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  this.closeOverlay();
               } else if (this.sharingWaypoint) {
                  this.openOverlayShareNew();
               } else if (this.sharingWaypointNew && this.isExistingUnsharedFellowshipName(this.nameWPTextField.func_146179_b(), (LOTRCustomWaypoint)this.selectedWaypoint)) {
                  fsName = this.nameWPTextField.func_146179_b();
                  LOTRPacketShareCWP packet = new LOTRPacketShareCWP((LOTRCustomWaypoint)this.selectedWaypoint, fsName, true);
                  LOTRPacketHandler.networkWrapper.sendToServer(packet);
                  this.openOverlayShare();
               }
            }
         } else if (button == this.buttonConquestRegions) {
            List regionList = LOTRDimension.MIDDLE_EARTH.dimensionRegions;
            if (!regionList.isEmpty()) {
               int i = regionList.indexOf(currentRegion);
               ++i;
               i = IntMath.mod(i, regionList.size());
               currentRegion = (LOTRDimension.DimensionRegion)regionList.get(i);
               this.updateCurrentDimensionAndFaction();
               this.setCurrentScrollFromFaction();
            }
         } else {
            super.func_146284_a(button);
         }
      }

   }

   private void handleMapKeyboardMovement() {
      this.prevPosX += this.posXMove;
      this.prevPosY += this.posYMove;
      this.posXMove = 0.0F;
      this.posYMove = 0.0F;
      if (!this.hasOverlay) {
         float move = 12.0F / (float)Math.pow((double)this.zoomScale, 0.800000011920929D);
         if (this.isKeyDownAndNotMouse(this.field_146297_k.field_71474_y.field_74370_x) || Keyboard.isKeyDown(203)) {
            this.posXMove -= move;
         }

         if (this.isKeyDownAndNotMouse(this.field_146297_k.field_71474_y.field_74366_z) || Keyboard.isKeyDown(205)) {
            this.posXMove += move;
         }

         if (this.isKeyDownAndNotMouse(this.field_146297_k.field_71474_y.field_74351_w) || Keyboard.isKeyDown(200)) {
            this.posYMove -= move;
         }

         if (this.isKeyDownAndNotMouse(this.field_146297_k.field_71474_y.field_74368_y) || Keyboard.isKeyDown(208)) {
            this.posYMove += move;
         }

         if (this.posXMove != 0.0F || this.posYMove != 0.0F) {
            this.selectedWaypoint = null;
         }
      }

   }

   private boolean isKeyDownAndNotMouse(KeyBinding keybinding) {
      return keybinding.func_151463_i() >= 0 && GameSettings.func_100015_a(keybinding);
   }

   public void func_146274_d() {
      super.func_146274_d();
      int k = Mouse.getEventDWheel();
      if (this.isConquestGrid && this.hasConquestScrollBar() && this.mouseInFacScroll && k != 0) {
         if (k < 0) {
            this.currentFactionIndex = Math.min(this.currentFactionIndex + 1, Math.max(0, currentFactionList.size() - 1));
         } else if (k > 0) {
            this.currentFactionIndex = Math.max(this.currentFactionIndex - 1, 0);
         }

         this.setCurrentScrollFromFaction();
      } else {
         if (!this.hasOverlay && this.zoomTicks == 0) {
            if (k < 0 && zoomPower > -3) {
               this.zoomOut();
               return;
            }

            if (k > 0 && zoomPower < 4) {
               this.zoomIn();
               return;
            }
         }

         if (this.hasOverlay && k != 0) {
            k = Integer.signum(k);
            if (this.scrollPaneWPShares.hasScrollBar && this.scrollPaneWPShares.mouseOver) {
               int l = this.displayedWPShareList.size() - this.displayedWPShares;
               this.scrollPaneWPShares.mouseWheelScroll(k, l);
               return;
            }
         }

      }
   }

   private void zoomOut() {
      this.zoom(-1);
   }

   private void zoomIn() {
      this.zoom(1);
   }

   private void zoom(int boost) {
      this.prevZoomPower = zoomPower;
      zoomPower += boost;
      this.zoomTicks = zoomTicksMax;
      this.selectedWaypoint = null;
   }

   public void setCWPProtectionMessage(IChatComponent message) {
      this.closeOverlay();
      this.hasOverlay = true;
      this.creatingWaypoint = false;
      this.creatingWaypointNew = false;
      String protection = StatCollector.func_74837_a("lotr.gui.map.customWaypoint.protected.1", new Object[]{message.func_150254_d()});
      String warning = StatCollector.func_74838_a("lotr.gui.map.customWaypoint.protected.2");
      this.overlayLines = new String[]{protection, "", warning};
   }

   private boolean canTeleport() {
      if (!this.isMiddleEarth()) {
         return false;
      } else if (this.loadingConquestGrid) {
         return false;
      } else {
         int chunkX = MathHelper.func_76128_c(this.field_146297_k.field_71439_g.field_70165_t) >> 4;
         int chunkZ = MathHelper.func_76128_c(this.field_146297_k.field_71439_g.field_70161_v) >> 4;
         if (this.field_146297_k.field_71441_e.func_72863_F().func_73154_d(chunkX, chunkZ) instanceof EmptyChunk) {
            return false;
         } else {
            this.requestIsOp();
            return this.isPlayerOp;
         }
      }
   }

   private void requestIsOp() {
      if (this.field_146297_k.func_71356_B()) {
         MinecraftServer server = this.field_146297_k.func_71401_C();
         this.isPlayerOp = server.field_71305_c[0].func_72912_H().func_76086_u() && server.func_71214_G().equalsIgnoreCase(this.field_146297_k.field_71439_g.func_146103_bH().getName());
      } else {
         LOTRPacketIsOpRequest packet = new LOTRPacketIsOpRequest();
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }

   private boolean isMiddleEarth() {
      return this.field_146297_k.field_71439_g.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID;
   }

   private void requestConquestGridIfNeed(LOTRFaction conqFac) {
      if (!this.requestedFacGrids.contains(conqFac) && this.ticksUntilRequestFac <= 0) {
         this.requestedFacGrids.add(conqFac);
         LOTRPacketConquestGridRequest packet = new LOTRPacketConquestGridRequest(conqFac);
         LOTRPacketHandler.networkWrapper.sendToServer(packet);
      }

   }

   public void receiveConquestGrid(LOTRFaction conqFac, List allZones) {
      if (this.isConquestGrid) {
         this.receivedFacGrids.add(conqFac);
         this.facConquestGrids.put(conqFac, allZones);
      }

   }

   private boolean hasConquestScrollBar() {
      return this.isConquestGrid && currentFactionList.size() > 1;
   }

   private void setupConquestScrollBar(int i, int j) {
      boolean isMouseDown = Mouse.isButtonDown(0);
      int i1 = this.facScrollX;
      int j1 = this.facScrollY;
      int i2 = i1 + this.facScrollWidth;
      int j2 = j1 + this.facScrollHeight;
      this.mouseInFacScroll = i >= i1 && j >= j1 && i < i2 && j < j2;
      if (!this.wasMouseDown && isMouseDown && this.mouseInFacScroll) {
         this.isFacScrolling = true;
      }

      if (!isMouseDown) {
         this.isFacScrolling = false;
      }

      this.wasMouseDown = isMouseDown;
      if (this.isFacScrolling) {
         this.currentFacScroll = ((float)(i - i1) - (float)this.facScrollWidgetWidth / 2.0F) / ((float)(i2 - i1) - (float)this.facScrollWidgetWidth);
         this.currentFacScroll = MathHelper.func_76131_a(this.currentFacScroll, 0.0F, 1.0F);
         this.currentFactionIndex = Math.round(this.currentFacScroll * (float)(currentFactionList.size() - 1));
      }

   }

   private void setCurrentScrollFromFaction() {
      this.currentFacScroll = (float)this.currentFactionIndex / (float)(currentFactionList.size() - 1);
   }

   private boolean hasMapLabels() {
      return this.isConquestGrid ? LOTRConfig.mapLabelsConquest : LOTRConfig.mapLabels;
   }

   private void toggleMapLabels() {
      if (this.isConquestGrid) {
         LOTRConfig.toggleMapLabelsConquest();
      } else {
         LOTRConfig.toggleMapLabels();
      }

   }

   public void setFakeMapProperties(float x, float y, float scale, float scaleExp, float scaleStable) {
      this.posX = x;
      this.posY = y;
      this.zoomScale = scale;
      this.zoomExp = scaleExp;
      this.zoomScaleStable = scaleStable;
   }

   public static int[] setFakeStaticProperties(int w, int h, int xmin, int xmax, int ymin, int ymax) {
      int[] ret = new int[]{mapWidth, mapHeight, mapXMin, mapXMax, mapYMin, mapYMax};
      mapWidth = w;
      mapHeight = h;
      mapXMin = xmin;
      mapXMax = xmax;
      mapYMin = ymin;
      mapYMax = ymax;
      return ret;
   }

   private static boolean isOSRS() {
      return LOTRConfig.osrsMap;
   }

   static {
      questBookIcon = new ItemStack(LOTRMod.redBook);
      fullscreen = true;
      mapWidgets = new ArrayList();
      zoomPower = 0;
      zoomTicksMax = 6;
      showWP = true;
      showCWP = true;
      showHiddenSWP = false;
      lastViewedRegions = new HashMap();
   }

   private static class PlayerLocationInfo {
      public GameProfile profile;
      public double posX;
      public double posZ;

      public PlayerLocationInfo(GameProfile p, double x, double z) {
         this.profile = p;
         this.posX = x;
         this.posZ = z;
      }
   }
}
