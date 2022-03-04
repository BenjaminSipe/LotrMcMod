package lotr.client.event;

import com.google.common.base.Function;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import lotr.client.ClientsideAttackTargetCache;
import lotr.client.LOTRKeyHandler;
import lotr.client.gui.map.MapPlayerLocationHolder;
import lotr.client.gui.util.AlignmentRenderer;
import lotr.client.gui.util.AlignmentTextRenderer;
import lotr.client.render.LOTRGameRenderer;
import lotr.client.render.OnScreenCompassRenderer;
import lotr.client.render.speech.ImmersiveSpeechRenderer;
import lotr.client.render.world.GeographicalWaterColors;
import lotr.client.render.world.LOTRDimensionRenderInfo;
import lotr.client.render.world.MiddleEarthCloudRenderer;
import lotr.client.render.world.MiddleEarthWorldRenderer;
import lotr.client.render.world.NorthernLightsRenderer;
import lotr.client.sound.LOTRAmbience;
import lotr.client.speech.ImmersiveSpeech;
import lotr.common.LOTRLog;
import lotr.common.config.LOTRConfig;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.capabilities.PlateFallingDataProvider;
import lotr.common.event.BeeAdjustments;
import lotr.common.event.LOTRTickHandlerServer;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRDimensions;
import lotr.common.init.LOTRParticles;
import lotr.common.time.LOTRDate;
import lotr.common.time.LOTRTime;
import lotr.common.util.LOTRUtil;
import lotr.common.world.biome.LOTRBiomeWrapper;
import lotr.common.world.biome.MirkwoodBiome;
import lotr.common.world.biome.MorgulValeBiome;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.FogRenderer.FogType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LOTRTickHandlerClient {
   private static final ResourceLocation PORTAL_OVERLAY = new ResourceLocation("lotr", "textures/overlay/portal.png");
   private static final ResourceLocation MIST_OVERLAY = new ResourceLocation("lotr", "textures/overlay/mist.png");
   private final Minecraft mc;
   private Screen lastScreenOpen;
   private boolean inRingPortal = false;
   private int ringPortalTick;
   private final AdvancedDrunkEffect drunkEffect = new AdvancedDrunkEffect();
   private final DateDisplay dateDisplay = new DateDisplay();
   private final LOTRAmbience ambienceTicker = new LOTRAmbience();
   private final SunGlare sunGlare = new SunGlare();
   private final RainMist rainMist = new RainMist();
   private final MistyMountainsMist mountainsMist = new MistyMountainsMist();
   private final SandstormFog sandstormFog = new SandstormFog();
   private final AshfallFog ashfallFog = new AshfallFog();
   private final WorldTypeHelpDisplay worldTypeHelpDisplay = new WorldTypeHelpDisplay();
   public static float renderPartialTick;
   private final NorthernLightsRenderer northernLights = new NorthernLightsRenderer();
   private final AlignmentRenderer alignmentRenderer = new AlignmentRenderer(AlignmentTextRenderer.newGUIRenderer());
   private final OnScreenCompassRenderer compassRenderer = new OnScreenCompassRenderer();

   public LOTRTickHandlerClient(Minecraft mc) {
      this.mc = mc;
      MinecraftForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onClientTick(ClientTickEvent event) {
      Phase phase = event.phase;
      ClientPlayerEntity player = this.mc.field_71439_g;
      Entity viewer = this.mc.field_175622_Z;
      if (phase == Phase.START) {
         this.replaceGameRendererIfNotReplaced();
         this.replaceWorldRendererIfNotReplaced();
         if (player != null) {
            GeographicalWaterColors.updateGeographicalWaterColorInBiomes(this.mc);
         }
      }

      if (phase == Phase.END) {
         if (player != null) {
            ClientWorld world = (ClientWorld)player.field_70170_p;
            this.alignmentRenderer.updateHUD(this.mc, player);
            LOTRKeyHandler.updateAlignmentChange();
            if (!this.mc.func_147113_T()) {
               if (LOTRDimensions.isModDimension(world)) {
                  LOTRTime.updateTime(world);
                  ICloudRenderHandler clouds = DimensionRenderInfo.func_243495_a(world.func_230315_m_()).getCloudRenderHandler();
                  if (clouds instanceof MiddleEarthCloudRenderer) {
                     ((MiddleEarthCloudRenderer)clouds).updateClouds(world);
                  }
               }

               if ((Boolean)LOTRConfig.CLIENT.northernLights.get()) {
                  this.northernLights.update(viewer);
               }

               if ((Boolean)LOTRConfig.CLIENT.immersiveSpeech.get()) {
                  ImmersiveSpeech.update();
               }

               if (viewer instanceof LivingEntity) {
                  this.drunkEffect.update((LivingEntity)viewer);
               }

               this.dateDisplay.update();
               this.ambienceTicker.updateAmbience(this.mc, world, player);
               this.sunGlare.update(world, viewer);
               this.rainMist.update(world, viewer);
               this.mountainsMist.update(world, viewer);
               this.sandstormFog.update(world, viewer);
               this.ashfallFog.update(world, viewer);
               this.spawnExtraEnvironmentParticles(world, viewer);
               if (this.inRingPortal) {
                  boolean stillInPortal = LOTRTickHandlerServer.checkInRingPortal(player);
                  if (stillInPortal) {
                     ++this.ringPortalTick;
                  } else {
                     this.inRingPortal = false;
                     this.ringPortalTick = 0;
                  }
               }
            }
         }

         Screen screen = this.mc.field_71462_r;
         if (screen != null) {
            if (this.isOutOfWorldMenuScreen(screen) && !this.isOutOfWorldMenuScreen(this.lastScreenOpen)) {
               LOTRLevelData.serverInstance().resetNeedsLoad();
               LOTRLevelData.clientInstance().resetNeedsLoad();
               LOTRTime.resetNeedsLoad();
               LOTRDate.resetWorldTimeInMenu();
               MapPlayerLocationHolder.clearPlayerLocations();
               ClientsideAttackTargetCache.clearAll();
               GeographicalWaterColors.resetInMenu();
               ImmersiveSpeech.clearAll();
               this.drunkEffect.reset();
               this.dateDisplay.reset();
               this.sunGlare.reset();
               this.rainMist.reset();
               this.mountainsMist.reset();
               this.sandstormFog.reset();
               this.ashfallFog.reset();
               this.alignmentRenderer.resetInMenu();
            }

            this.lastScreenOpen = screen;
         }
      }

   }

   private boolean isOutOfWorldMenuScreen(Screen screen) {
      return screen instanceof MainMenuScreen || screen instanceof MultiplayerScreen;
   }

   private void replaceGameRendererIfNotReplaced() {
      replaceFinalMinecraftFieldIfNotReplaced(this.mc, GameRenderer.class, LOTRGameRenderer.class, (mc) -> {
         return mc.field_71460_t;
      }, () -> {
         return new LOTRGameRenderer(this.mc, this.mc.func_195551_G(), this.mc.func_228019_au_());
      }, "GameRenderer");
   }

   private void replaceWorldRendererIfNotReplaced() {
      replaceFinalMinecraftFieldIfNotReplaced(this.mc, WorldRenderer.class, MiddleEarthWorldRenderer.class, (mc) -> {
         return mc.field_71438_f;
      }, () -> {
         return new MiddleEarthWorldRenderer(this.mc, this.mc.func_228019_au_());
      }, "WorldRenderer");
   }

   private static void replaceFinalMinecraftFieldIfNotReplaced(Minecraft mc, Class baseClass, Class subClass, Function getter, Supplier newSubClassSupplier, String loggingNameOfField) {
      Object object = getter.apply(mc);
      if (!subClass.isAssignableFrom(object.getClass())) {
         Object newSubClassObject = newSubClassSupplier.get();
         Optional opt_mcField = Stream.of(Minecraft.class.getDeclaredFields()).filter((f) -> {
            return f.getType() == baseClass;
         }).findFirst();
         if (!opt_mcField.isPresent()) {
            LOTRLog.error("Could not locate %s field in Minecraft game class", loggingNameOfField);
         } else {
            Field mcField = (Field)opt_mcField.get();
            LOTRUtil.unlockFinalField(mcField);

            try {
               mcField.set(mc, newSubClassObject);
               if (newSubClassObject instanceof IFutureReloadListener) {
                  IReloadableResourceManager resMgr = (IReloadableResourceManager)mc.func_195551_G();
                  IFutureReloadListener objectAsListener = (IFutureReloadListener)newSubClassObject;
                  resMgr.func_219534_a(objectAsListener);
                  if (objectAsListener instanceof IResourceManagerReloadListener) {
                     ((IResourceManagerReloadListener)objectAsListener).func_195410_a(resMgr);
                  }
               }

               LOTRLog.info("Successfully replaced %s instance with subclass instance", loggingNameOfField);
            } catch (IllegalAccessException | IllegalArgumentException var12) {
               LOTRLog.error("Failed to set new %s", loggingNameOfField);
               var12.printStackTrace();
            }
         }
      }

   }

   private void spawnExtraEnvironmentParticles(World world, Entity viewer) {
      world.func_217381_Z().func_76320_a("lotrEnvironmentFX");
      Mutable movingParticlePos = new Mutable();

      for(int l = 0; l < 667; ++l) {
         this.spawnExtraEnvironmentParticles(world, viewer, 16, movingParticlePos);
         this.spawnExtraEnvironmentParticles(world, viewer, 32, movingParticlePos);
      }

      world.func_217381_Z().func_76319_b();
   }

   private void spawnExtraEnvironmentParticles(World world, Entity viewer, int range, Mutable movingPos) {
      BlockPos playerPos = viewer.func_233580_cy_();
      Random rand = world.field_73012_v;
      int x = playerPos.func_177958_n() + rand.nextInt(range) - rand.nextInt(range);
      int y = playerPos.func_177956_o() + rand.nextInt(range) - rand.nextInt(range);
      int z = playerPos.func_177952_p() + rand.nextInt(range) - rand.nextInt(range);
      movingPos.func_181079_c(x, y, z);
      BlockState blockState = world.func_180495_p(movingPos);
      if (blockState.func_185904_a() == Material.field_151586_h) {
         LOTRBiomeWrapper biome = LOTRBiomes.getWrapperFor(world.func_226691_t_(movingPos), world);
         if (biome instanceof MirkwoodBiome && rand.nextInt(20) == 0) {
            world.func_195594_a((IParticleData)LOTRParticles.MIRKWOOD_WATER_EFFECT.get(), (double)((float)x + rand.nextFloat()), (double)y + 0.75D, (double)((float)z + rand.nextFloat()), 0.0D, 0.05D, 0.0D);
         }

         if (biome instanceof MorgulValeBiome && rand.nextInt(40) == 0) {
            world.func_195594_a((IParticleData)LOTRParticles.MORGUL_WATER_EFFECT.get(), (double)((float)x + rand.nextFloat()), (double)y + 0.75D, (double)((float)z + rand.nextFloat()), 0.0D, 0.05D, 0.0D);
         }
      }

      if (blockState.func_185904_a() == Material.field_151586_h && !world.func_204610_c(movingPos).func_206889_d()) {
         BlockPos belowPos = movingPos.func_177977_b();
         BlockState below = world.func_180495_p(belowPos);
         if (below.func_185904_a() == Material.field_151586_h) {
            int waterRange = 1;
            Mutable waterMovingPos = new Mutable();

            for(int i = -waterRange; i <= waterRange; ++i) {
               for(int k = -waterRange; k <= waterRange; ++k) {
                  waterMovingPos.func_181079_c(x + i, y - 1, z + k);
                  BlockState adjBlock = world.func_180495_p(waterMovingPos);
                  if (adjBlock.func_185904_a() == Material.field_151586_h && world.func_204610_c(waterMovingPos).func_206889_d() && world.func_175623_d(waterMovingPos.func_177984_a())) {
                     for(int l = 0; l < 2; ++l) {
                        double px = (double)x + 0.5D + (double)((float)i * rand.nextFloat());
                        double py = (double)((float)y + rand.nextFloat() * 0.2F);
                        double pz = (double)z + 0.5D + (double)((float)k * rand.nextFloat());
                        double speed = MathHelper.func_82716_a(rand, 0.03D, 0.07D);
                        world.func_195594_a((IParticleData)LOTRParticles.WATERFALL.get(), px, py, pz, 0.0D, speed, 0.0D);
                     }
                  }
               }
            }
         }
      }

   }

   @SubscribeEvent
   public void onAttachCapabilities(AttachCapabilitiesEvent event) {
      Entity entity = (Entity)event.getObject();
      if (entity.field_70170_p.field_72995_K && entity instanceof LivingEntity) {
         event.addCapability(PlateFallingDataProvider.KEY, new PlateFallingDataProvider());
      }

   }

   @SubscribeEvent
   public void onLivingUpdate(LivingUpdateEvent event) {
      LivingEntity entity = event.getEntityLiving();
      World world = entity.field_70170_p;
      if (world.field_72995_K) {
         entity.getCapability(PlateFallingDataProvider.CAPABILITY).ifPresent((plateFalling) -> {
            if (!plateFalling.isEntitySet()) {
               plateFalling.setEntity(entity);
            }

            plateFalling.update();
         });
      }

   }

   @SubscribeEvent
   public void onWorldLoad(Load event) {
      IWorld world = event.getWorld();
      if (world instanceof ClientWorld) {
         ClientWorldDimensionTypeHelper.fixDimensionType((ClientWorld)world);
      }

   }

   @SubscribeEvent
   public void onRenderTick(RenderTickEvent event) {
      Phase phase = event.phase;
      PlayerEntity player = this.mc.field_71439_g;
      Entity viewer = this.mc.field_175622_Z;
      if (phase == Phase.START) {
         renderPartialTick = event.renderTickTime;
      }

      if (phase == Phase.END) {
         Screen gui = this.mc.field_71462_r;
         if (player != null) {
            World world = player.field_70170_p;
            boolean guiEnabled = Minecraft.func_71382_s();
            boolean isModDimension = LOTRDimensions.isModDimension(world);
            MatrixStack matStack;
            if (guiEnabled && (isModDimension || (Boolean)LOTRConfig.CLIENT.showAlignmentEverywhere.get())) {
               matStack = new MatrixStack();
               this.alignmentRenderer.renderAlignmentHUDBar(matStack, this.mc, player, renderPartialTick);
            }

            if (guiEnabled && gui == null) {
               if (isModDimension && (Boolean)LOTRConfig.CLIENT.compass.get() && !this.mc.field_71474_y.field_74330_P) {
                  this.compassRenderer.renderCompassAndInformation(this.mc, player, world, renderPartialTick);
               }

               if (LOTRDimensions.isDimension(world, LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)) {
                  matStack = new MatrixStack();
                  this.dateDisplay.render(matStack, this.mc);
               }
            }
         }
      }

   }

   public void setInRingPortal(Entity entity) {
      if (entity == this.mc.field_71439_g) {
         this.inRingPortal = true;
      }

   }

   @SubscribeEvent
   public void onCameraSetup(CameraSetup event) {
      this.drunkEffect.handle(event);
   }

   @SubscribeEvent
   public void getItemTooltip(ItemTooltipEvent event) {
      ItemTooltipFeatures.handleTooltipEvent(event);
   }

   @SubscribeEvent
   public void onPreRenderGameOverlay(Pre event) {
      World world = this.mc.field_71441_e;
      PlayerEntity player = this.mc.field_71439_g;
      Entity viewer = this.mc.field_175622_Z;
      float partialTick = event.getPartialTicks();
      IngameGui guiIngame = this.mc.field_71456_v;
      if (world != null && viewer != null && event.getType() == ElementType.HELMET) {
         float sunGlareBrightness = this.sunGlare.getGlareBrightness(partialTick);
         if (sunGlareBrightness > 0.0F && this.mc.field_71474_y.func_243230_g() == PointOfView.FIRST_PERSON) {
            sunGlareBrightness *= 1.0F;
            this.renderColoredOverlay(this.sunGlare.getGlareColorRGB(), sunGlareBrightness);
         }

         float mountainsMistFactor;
         if (this.inRingPortal) {
            mountainsMistFactor = (float)this.ringPortalTick / 100.0F;
            mountainsMistFactor = Math.min(mountainsMistFactor, 1.0F);
            this.renderTexturedOverlay(PORTAL_OVERLAY, 0.1F + mountainsMistFactor * 0.6F);
         }

         mountainsMistFactor = this.mountainsMist.getCurrentMistFactor(viewer, partialTick);
         if (mountainsMistFactor > 0.0F) {
            this.renderTexturedOverlay(MIST_OVERLAY, mountainsMistFactor * 0.75F);
         }
      }

   }

   private void renderTexturedOverlay(ResourceLocation texture, float alpha) {
      this.renderOverlay(texture, (float[])null, alpha);
   }

   private void renderColoredOverlay(float[] rgb, float alpha) {
      this.renderOverlay((ResourceLocation)null, rgb, alpha);
   }

   private void renderOverlay(@Nullable ResourceLocation texture, @Nullable float[] rgb, float alpha) {
      MainWindow mainWindow = this.mc.func_228018_at_();
      int width = mainWindow.func_198107_o();
      int height = mainWindow.func_198087_p();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableAlphaTest();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      if (rgb != null) {
         RenderSystem.color4f(rgb[0], rgb[1], rgb[2], alpha);
      } else {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
      }

      if (texture != null) {
         this.mc.func_110434_K().func_110577_a(texture);
      } else {
         RenderSystem.disableTexture();
      }

      double depth = -90.0D;
      Tessellator tess = Tessellator.func_178181_a();
      BufferBuilder buf = tess.func_178180_c();
      buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      buf.func_225582_a_(0.0D, (double)height, depth).func_225583_a_(0.0F, 1.0F).func_181675_d();
      buf.func_225582_a_((double)width, (double)height, depth).func_225583_a_(1.0F, 1.0F).func_181675_d();
      buf.func_225582_a_((double)width, 0.0D, depth).func_225583_a_(1.0F, 0.0F).func_181675_d();
      buf.func_225582_a_(0.0D, 0.0D, depth).func_225583_a_(0.0F, 0.0F).func_181675_d();
      tess.func_78381_a();
      if (texture == null) {
         RenderSystem.enableTexture();
      }

      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableAlphaTest();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      Minecraft mc = Minecraft.func_71410_x();
      ClientWorld world = mc.field_71441_e;
      float tick = event.getPartialTicks();
      MatrixStack matStack = event.getMatrixStack();
      if ((Boolean)LOTRConfig.CLIENT.northernLights.get() && LOTRDimensions.isDimension((World)world, LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)) {
         this.northernLights.render(mc, world, matStack, tick);
      }

      if ((Boolean)LOTRConfig.CLIENT.immersiveSpeech.get() && Minecraft.func_71382_s() && mc.field_71474_y.func_243230_g() == PointOfView.FIRST_PERSON) {
         world.func_217381_Z().func_76320_a("lotrImmersiveSpeech");
         ImmersiveSpeechRenderer.renderAllSpeeches(mc, world, matStack, tick);
         world.func_217381_Z().func_76319_b();
      }

   }

   @SubscribeEvent
   public void onRenderFog(RenderFogEvent event) {
      Minecraft mc = Minecraft.func_71410_x();
      Entity viewer = event.getInfo().func_216773_g();
      World world = viewer.field_70170_p;
      DimensionType dimension = world.func_230315_m_();
      world.func_226691_t_(viewer.func_233580_cy_());
      float farPlane = event.getFarPlaneDistance();
      FogType fogType = event.getType();
      DimensionRenderInfo dimRenderInfo = DimensionRenderInfo.func_243495_a(dimension);
      if (dimRenderInfo instanceof LOTRDimensionRenderInfo) {
         LOTRDimensionRenderInfo lDimRenderInfo = (LOTRDimensionRenderInfo)dimRenderInfo;
         float[] fogStartEnd = lDimRenderInfo.modifyFogIntensity(farPlane, fogType, viewer);
         float fogStart = fogStartEnd[0];
         float fogEnd = fogStartEnd[1];
         float rain = this.rainMist.getRainMistStrength(renderPartialTick);
         float mountainsMistFactor;
         float sandFog;
         if (rain > 0.0F) {
            mountainsMistFactor = 0.95F;
            sandFog = 0.2F;
            fogStart -= fogStart * rain * mountainsMistFactor;
            fogEnd -= fogEnd * rain * sandFog;
         }

         mountainsMistFactor = this.mountainsMist.getCurrentMistFactor(viewer, renderPartialTick);
         float ashFog;
         if (mountainsMistFactor > 0.0F) {
            sandFog = 0.95F;
            ashFog = 0.7F;
            fogStart -= fogStart * mountainsMistFactor * sandFog;
            fogEnd -= fogEnd * mountainsMistFactor * ashFog;
         }

         sandFog = this.sandstormFog.getWeatherFogStrength(renderPartialTick);
         float ashOpacityStart;
         if (sandFog > 0.0F) {
            ashFog = 0.99F;
            ashOpacityStart = 0.75F;
            fogStart -= fogStart * sandFog * ashFog;
            fogEnd -= fogEnd * sandFog * ashOpacityStart;
         }

         ashFog = this.ashfallFog.getWeatherFogStrength(renderPartialTick);
         if (ashFog > 0.0F) {
            ashOpacityStart = 0.95F;
            float ashOpacityEnd = 0.6F;
            fogStart -= fogStart * ashFog * ashOpacityStart;
            fogEnd -= fogEnd * ashFog * ashOpacityEnd;
         }

         RenderSystem.fogStart(fogStart);
         RenderSystem.fogEnd(fogEnd);
      }

   }

   @SubscribeEvent
   public void onFogColors(FogColors event) {
      this.sandstormFog.modifyFogColors(event, renderPartialTick);
      this.ashfallFog.modifyFogColors(event, renderPartialTick);
   }

   public void displayNewDate() {
      this.dateDisplay.displayNewDate();
   }

   public void displayAlignmentDrain(int numFactions) {
      this.alignmentRenderer.displayAlignmentDrain(numFactions);
   }

   public float getCurrentSandstormFogStrength() {
      return this.sandstormFog.getWeatherFogStrength(renderPartialTick);
   }

   @SubscribeEvent
   public void onPreRenderLiving(net.minecraftforge.client.event.RenderLivingEvent.Pre event) {
      LivingEntity entity = event.getEntity();
      World world = entity.field_70170_p;
      MatrixStack matStack = event.getMatrixStack();
      if (BeeAdjustments.shouldApply(entity, world)) {
         float scale = 0.35F;
         matStack.func_227862_a_(scale, scale, scale);
      }

   }
}
