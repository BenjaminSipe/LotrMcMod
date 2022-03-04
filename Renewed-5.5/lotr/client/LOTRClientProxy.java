package lotr.client;

import java.io.File;
import java.util.List;
import java.util.Optional;
import lotr.client.align.NotifyAlignmentRequirement;
import lotr.client.event.LOTRGuiHandler;
import lotr.client.event.LOTRTickHandlerClient;
import lotr.client.gui.AdoptCustomWaypointScreen;
import lotr.client.gui.CreateCustomWaypointScreen;
import lotr.client.gui.FastTravelScreen;
import lotr.client.gui.PlayerMessageScreen;
import lotr.client.gui.UpdateCustomWaypointScreen;
import lotr.client.gui.ViewAdoptedCustomWaypointScreen;
import lotr.client.gui.inv.ContainerScreenHelper;
import lotr.client.gui.map.MapPlayerLocationHolder;
import lotr.client.gui.map.MiddleEarthMapScreen;
import lotr.client.render.BlockRenderHelper;
import lotr.client.render.LOTRClientParticles;
import lotr.client.render.entity.LOTREntityRenderers;
import lotr.client.render.model.HandheldItemModels;
import lotr.client.render.model.PlateFoodModels;
import lotr.client.render.model.connectedtex.ConnectedTextureUnbakedModel;
import lotr.client.render.model.scatter.ScatterUnbakedModel;
import lotr.client.render.model.vessel.VesselDrinkUnbakedModel;
import lotr.client.render.player.LOTRPlayerRendering;
import lotr.client.render.world.MiddleEarthDimensionRenderInfo;
import lotr.client.speech.NPCSpeechReceiver;
import lotr.client.speech.SpeechbankResourceManager;
import lotr.client.text.QuoteListLoader;
import lotr.common.LOTRServerProxy;
import lotr.common.block.LOTRSignTypes;
import lotr.common.data.PlayerMessageType;
import lotr.common.entity.item.RingPortalEntity;
import lotr.common.entity.misc.AlignmentBonusEntity;
import lotr.common.init.LOTRDimensions;
import lotr.common.inv.KegResultSlot;
import lotr.common.inv.KegSlot;
import lotr.common.item.LOTRItemProperties;
import lotr.common.network.SPacketAlignmentBonus;
import lotr.common.network.SPacketNotifyAlignRequirement;
import lotr.common.network.SPacketOpenScreen;
import lotr.common.network.SPacketSetAttackTarget;
import lotr.common.network.SPacketSpeechbank;
import lotr.common.world.map.AdoptedCustomWaypoint;
import lotr.common.world.map.CustomWaypoint;
import lotr.common.world.map.Waypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.ColorHandlerEvent.Block;
import net.minecraftforge.client.event.ColorHandlerEvent.Item;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class LOTRClientProxy extends LOTRServerProxy {
   private static final Minecraft MC = Minecraft.func_71410_x();
   public static final int MAX_LIGHTMAP = LightTexture.func_228451_a_(15, 15);
   private LOTRTickHandlerClient clientTickHandler;
   private LOTRGuiHandler guiHandler;
   private LOTRKeyHandler keyHandler;
   private LOTRPlayerRendering specialPlayerRendering;
   private static QuoteListLoader QUOTE_LIST_LOADER;
   private static SpeechbankResourceManager SPEECHBANK_RESOURCE_MANAGER;

   public static QuoteListLoader getQuoteListLoader() {
      return QUOTE_LIST_LOADER;
   }

   public static SpeechbankResourceManager getSpeechbankResourceManager() {
      return SPEECHBANK_RESOURCE_MANAGER;
   }

   @SubscribeEvent
   public void onClientSetup(FMLClientSetupEvent event) {
      this.clientTickHandler = new LOTRTickHandlerClient(MC);
      this.guiHandler = new LOTRGuiHandler(MC);
      this.keyHandler = new LOTRKeyHandler();
      this.specialPlayerRendering = new LOTRPlayerRendering(MC);
      QUOTE_LIST_LOADER = new QuoteListLoader(MC);
      SPEECHBANK_RESOURCE_MANAGER = new SpeechbankResourceManager(MC);
      BlockRenderHelper.setupBlocks();
      LOTRSignTypes.forEach(Atlases::addWoodType);
      LOTRItemProperties.registerProperties();
      DimensionRenderInfo.field_239208_a_.put(LOTRDimensions.MIDDLE_EARTH_ID, new MiddleEarthDimensionRenderInfo());
      ContainerScreenHelper.registerScreens();
      LOTREntityRenderers.registerEntityRenderers();
      LOTREntityRenderers.registerTileEntityRenderers();
   }

   @SubscribeEvent
   public void onTextureStitchedPre(Pre event) {
      AtlasTexture atlas = event.getMap();
      if (atlas.func_229223_g_() == PlayerContainer.field_226615_c_) {
         event.addSprite(KegSlot.EMPTY_BUCKET_TEXTURE);
         event.addSprite(KegResultSlot.EMPTY_MUG_TEXTURE);
      }

   }

   @SubscribeEvent
   public void onModelRegistry(ModelRegistryEvent event) {
      HandheldItemModels.INSTANCE.setupAndDetectModels(MC);
      PlateFoodModels.INSTANCE.setupAndLoadModels(MC);
      ModelLoaderRegistry.registerLoader(new ResourceLocation("lotr", "connected_textures"), ConnectedTextureUnbakedModel.Loader.INSTANCE);
      ModelLoaderRegistry.registerLoader(new ResourceLocation("lotr", "vessel_drink"), VesselDrinkUnbakedModel.Loader.INSTANCE);
      ModelLoaderRegistry.registerLoader(new ResourceLocation("lotr", "scatter"), ScatterUnbakedModel.Loader.INSTANCE);
   }

   @SubscribeEvent
   public void onModelBake(ModelBakeEvent event) {
      HandheldItemModels.INSTANCE.onModelBake(event);
   }

   @SubscribeEvent
   public void onBlockColors(Block event) {
      BlockRenderHelper.setupBlockColors(event);
   }

   @SubscribeEvent
   public void onItemColors(Item event) {
      BlockRenderHelper.setupItemColors(event);
   }

   @SubscribeEvent
   public void onParticleRegistry(ParticleFactoryRegisterEvent event) {
      LOTRClientParticles.register(event);
   }

   public boolean isClient() {
      return EffectiveSide.get() == LogicalSide.CLIENT;
   }

   public boolean isSingleplayer() {
      return MC.func_71356_B();
   }

   public File getGameRootDirectory() {
      return MC.field_71412_D;
   }

   public World getClientWorld() {
      return MC.field_71441_e;
   }

   public ClientPlayerEntity getClientPlayer() {
      return MC.field_71439_g;
   }

   public Optional getSidedAttackTarget(MobEntity entity) {
      return !entity.field_70170_p.field_72995_K ? super.getSidedAttackTarget(entity) : ClientsideAttackTargetCache.getAttackTarget(entity);
   }

   public void receiveClientAttackTarget(SPacketSetAttackTarget packet) {
      ClientsideAttackTargetCache.receivePacket(packet);
   }

   public float getCurrentSandstormFogStrength() {
      return this.clientTickHandler.getCurrentSandstormFogStrength();
   }

   public void setInRingPortal(Entity entity, RingPortalEntity portal) {
      if (!entity.field_70170_p.field_72995_K) {
         super.setInRingPortal(entity, portal);
      } else {
         this.clientTickHandler.setInRingPortal(entity);
      }

   }

   public void mapHandleIsOp(boolean isOp) {
      Screen screen = MC.field_71462_r;
      if (screen instanceof MiddleEarthMapScreen) {
         ((MiddleEarthMapScreen)screen).receiveIsOp(isOp);
      }

   }

   public void mapHandlePlayerLocations(List playerLocations) {
      MapPlayerLocationHolder.refreshPlayerLocations(playerLocations);
   }

   public void displayNewDate() {
      this.clientTickHandler.displayNewDate();
   }

   public void spawnAlignmentBonus(SPacketAlignmentBonus packet) {
      ClientWorld world = MC.field_71441_e;
      AlignmentBonusEntity entity = AlignmentBonusEntity.createBonusEntityForClientSpawn(world, packet.entityId, packet.source, packet.mainFaction, packet.prevMainAlignment, packet.factionBonusMap, packet.conquestBonus, packet.pos);
      world.func_217411_a(entity.func_145782_y(), entity);
   }

   public void displayAlignmentDrain(int numFactions) {
      this.clientTickHandler.displayAlignmentDrain(numFactions);
   }

   public void displayMessageType(PlayerMessageType messageType, boolean isCommandSent, String customText) {
      MC.func_147108_a(new PlayerMessageScreen(messageType, isCommandSent, customText));
   }

   public void displayPacketOpenScreen(SPacketOpenScreen.Type type) {
      if (type == SPacketOpenScreen.Type.CREATE_CUSTOM_WAYPOINT) {
         MC.func_147108_a(new CreateCustomWaypointScreen());
      }

   }

   public void displayFastTravelScreen(Waypoint waypoint, int startX, int startZ) {
      MC.func_147108_a(new FastTravelScreen(waypoint, startX, startZ));
   }

   public void displayUpdateCustomWaypointScreen(CustomWaypoint waypoint) {
      MC.func_147108_a(new UpdateCustomWaypointScreen(waypoint));
   }

   public void displayAdoptCustomWaypointScreen(CustomWaypoint waypoint, String createdPlayerName) {
      MC.func_147108_a(new AdoptCustomWaypointScreen(waypoint, createdPlayerName));
   }

   public void displayViewAdoptedCustomWaypointScreen(AdoptedCustomWaypoint waypoint, String createdPlayerName) {
      MC.func_147108_a(new ViewAdoptedCustomWaypointScreen(waypoint, createdPlayerName));
   }

   public void receiveSpeechbankPacket(SPacketSpeechbank packet) {
      NPCSpeechReceiver.receiveSpeech(this.getClientWorld(), this.getClientPlayer(), packet);
   }

   public void receiveNotifyAlignRequirementPacket(SPacketNotifyAlignRequirement packet) {
      NotifyAlignmentRequirement.displayMessage(this.getClientPlayer(), packet);
   }
}
