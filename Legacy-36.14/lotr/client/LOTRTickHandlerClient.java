package lotr.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import lotr.client.fx.LOTREntityDeadMarshFace;
import lotr.client.gui.LOTRGuiMap;
import lotr.client.gui.LOTRGuiMenu;
import lotr.client.gui.LOTRGuiMessage;
import lotr.client.gui.LOTRGuiMiniquestTracker;
import lotr.client.gui.LOTRGuiNotificationDisplay;
import lotr.client.model.LOTRModelCompass;
import lotr.client.render.LOTRCloudRenderer;
import lotr.client.render.LOTRRenderNorthernLights;
import lotr.client.render.entity.LOTRNPCRendering;
import lotr.client.render.tileentity.LOTRTileEntityMobSpawnerRenderer;
import lotr.client.sound.LOTRAmbience;
import lotr.client.sound.LOTRMusicTicker;
import lotr.client.sound.LOTRMusicTrack;
import lotr.common.LOTRConfig;
import lotr.common.LOTRDate;
import lotr.common.LOTRDimension;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.LOTRSquadrons;
import lotr.common.LOTRTime;
import lotr.common.block.LOTRBlockLeavesBase;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.LOTRInvasionStatus;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.entity.item.LOTREntityPortal;
import lotr.common.entity.npc.LOTREntityBalrog;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntitySpiderBase;
import lotr.common.fac.LOTRAlignmentValues;
import lotr.common.fac.LOTRFaction;
import lotr.common.fac.LOTRFactionBounties;
import lotr.common.fac.LOTRFactionRank;
import lotr.common.fac.LOTRFactionRelations;
import lotr.common.fellowship.LOTRFellowshipData;
import lotr.common.item.LOTRItemBanner;
import lotr.common.item.LOTRItemBlowgun;
import lotr.common.item.LOTRItemBow;
import lotr.common.item.LOTRItemCrossbow;
import lotr.common.item.LOTRItemOwnership;
import lotr.common.item.LOTRItemSpear;
import lotr.common.item.LOTRPoisonedDrinks;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.quest.IPickpocketable;
import lotr.common.util.LOTRColorUtil;
import lotr.common.util.LOTRFunctions;
import lotr.common.util.LOTRLog;
import lotr.common.util.LOTRVersionChecker;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenBarrowDowns;
import lotr.common.world.biome.LOTRBiomeGenDeadMarshes;
import lotr.common.world.biome.LOTRBiomeGenMirkwoodCorrupted;
import lotr.common.world.biome.LOTRBiomeGenMistyMountains;
import lotr.common.world.biome.LOTRBiomeGenMorgulVale;
import lotr.common.world.biome.LOTRBiomeGenUtumno;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.map.LOTRConquestGrid;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import org.lwjgl.opengl.GL11;

public class LOTRTickHandlerClient {
   private static final ResourceLocation portalOverlay = new ResourceLocation("lotr:misc/portal_overlay.png");
   private static final ResourceLocation elvenPortalOverlay = new ResourceLocation("lotr:misc/elvenportal_overlay.png");
   private static final ResourceLocation morgulPortalOverlay = new ResourceLocation("lotr:misc/morgulportal_overlay.png");
   private static final ResourceLocation mistOverlay = new ResourceLocation("lotr:misc/mist_overlay.png");
   private static final ResourceLocation frostOverlay = new ResourceLocation("lotr:misc/frost_overlay.png");
   private static final float[] frostRGBMiddle = new float[]{0.4F, 0.46F, 0.74F};
   private static final float[] frostRGBEdge = new float[]{1.0F, 1.0F, 1.0F};
   private static final ResourceLocation burnOverlay = new ResourceLocation("lotr:misc/burn_overlay.png");
   private static final ResourceLocation wightOverlay = new ResourceLocation("lotr:misc/wight.png");
   public static HashMap playersInPortals = new HashMap();
   public static HashMap playersInElvenPortals = new HashMap();
   public static HashMap playersInMorgulPortals = new HashMap();
   private LOTRAmbience ambienceTicker;
   public static int clientTick;
   public static float renderTick;
   private GuiScreen lastGuiOpen;
   private int mistTick;
   private int prevMistTick;
   private static final int mistTickMax = 80;
   private float mistFactor;
   private float sunGlare;
   private float prevSunGlare;
   private float rainFactor;
   private float prevRainFactor;
   private int alignmentXBase;
   private int alignmentYBase;
   private int alignmentXCurrent;
   private int alignmentYCurrent;
   private int alignmentXPrev;
   private int alignmentYPrev;
   private static final int alignmentYOffscreen = -20;
   private boolean firstAlignmentRender = true;
   public static int alignDrainTick;
   public static final int alignDrainTickMax = 200;
   public static int alignDrainNum;
   public static LOTRInvasionStatus watchedInvasion = new LOTRInvasionStatus();
   public static LOTRGuiNotificationDisplay notificationDisplay;
   public static LOTRGuiMiniquestTracker miniquestTracker;
   private boolean wasShowingBannerRepossessMessage;
   private int bannerRepossessDisplayTick;
   private static final int bannerRepossessDisplayTickMax = 60;
   private int frostTick;
   private static final int frostTickMax = 80;
   private int burnTick;
   private static final int burnTickMax = 40;
   private int drunkennessDirection = 1;
   private int newDate = 0;
   private static final int newDateMax = 200;
   private float utumnoCamRoll = 0.0F;
   public boolean inUtumnoReturnPortal = false;
   public int utumnoReturnX;
   public int utumnoReturnZ;
   private double lastUtumnoReturnY = -1.0D;
   private int prevWightLookTick = 0;
   private int wightLookTick = 0;
   private static final int wightLookTickMax = 100;
   public static boolean anyWightsViewed;
   private int prevWightNearTick = 0;
   private int wightNearTick = 0;
   private static final int wightNearTickMax = 100;
   private int prevBalrogNearTick = 0;
   private int balrogNearTick = 0;
   private static final int balrogNearTickMax = 100;
   private float balrogFactor;
   public static int scrapTraderMisbehaveTick = 0;
   private float[] storedLightTable;
   private int storedScrapID;
   private boolean addedClientPoisonEffect = false;
   private LOTRMusicTrack lastTrack = null;
   private int musicTrackTick = 0;
   private static final int musicTrackTickMax = 200;
   private static final int musicTrackTickFadeTime = 60;
   public boolean cancelItemHighlight = false;
   private ItemStack lastHighlightedItemstack;
   private String highlightedItemstackName;

   public LOTRTickHandlerClient() {
      FMLCommonHandler.instance().bus().register(this);
      MinecraftForge.EVENT_BUS.register(this);
      this.ambienceTicker = new LOTRAmbience();
      notificationDisplay = new LOTRGuiNotificationDisplay();
      miniquestTracker = new LOTRGuiMiniquestTracker();
   }

   @SubscribeEvent
   public void onClientTick(ClientTickEvent event) {
      Minecraft minecraft = Minecraft.func_71410_x();
      EntityClientPlayerMP entityplayer = minecraft.field_71439_g;
      WorldClient world = minecraft.field_71441_e;
      int i;
      if (event.phase == Phase.START) {
         ++clientTick;
         if (LOTRConfig.fixRenderDistance && !FMLClientHandler.instance().hasOptifine()) {
            GameSettings gs = Minecraft.func_71410_x().field_71474_y;
            i = gs.field_151451_c;
            if (i > 16) {
               int renderDistance = 16;
               gs.field_151451_c = renderDistance;
               gs.func_74303_b();
               LOTRLog.logger.info("LOTR: Render distance was above 16 - set to 16 to prevent a vanilla crash");
            }
         }

         if (minecraft.field_71460_t != null && !(minecraft.field_71460_t instanceof LOTREntityRenderer)) {
            minecraft.field_71460_t = new LOTREntityRenderer(minecraft, minecraft.func_110442_L());
            ((IReloadableResourceManager)minecraft.func_110442_L()).func_110542_a(minecraft.field_71460_t);
            FMLLog.info("LOTR: Successfully replaced entityrenderer", new Object[0]);
         }
      }

      if (event.phase == Phase.END) {
         LOTRTileEntityMobSpawnerRenderer.onClientTick();
         if (minecraft.field_71462_r == null) {
            this.lastGuiOpen = null;
         }

         boolean inPortal;
         if (FMLClientHandler.instance().hasOptifine()) {
            int optifineSetting = 0;

            try {
               Object field = GameSettings.class.getField("ofTrees").get(minecraft.field_71474_y);
               if (field instanceof Integer) {
                  optifineSetting = (Integer)field;
               }
            } catch (Exception var36) {
            }

            inPortal = optifineSetting == 0 ? minecraft.field_71474_y.field_74347_j : (optifineSetting == 1 ? false : optifineSetting == 2);
            LOTRBlockLeavesBase.setAllGraphicsLevels(inPortal);
         } else {
            LOTRBlockLeavesBase.setAllGraphicsLevels(minecraft.field_71474_y.field_74347_j);
         }

         if (entityplayer != null && world != null) {
            if (LOTRConfig.checkUpdates) {
               LOTRVersionChecker.checkForUpdates();
            }

            int i;
            if (!this.isGamePaused(minecraft)) {
               miniquestTracker.update(minecraft, entityplayer);
               LOTRAlignmentTicker.updateAll(entityplayer, false);
               if (alignDrainTick > 0) {
                  --alignDrainTick;
                  if (alignDrainTick <= 0) {
                     alignDrainNum = 0;
                  }
               }

               watchedInvasion.tick();
               if (!LOTRItemBanner.hasChoiceToKeepOriginalOwner(entityplayer)) {
                  this.bannerRepossessDisplayTick = 0;
                  this.wasShowingBannerRepossessMessage = false;
               } else {
                  boolean showBannerRespossessMessage = LOTRItemBanner.isHoldingBannerWithExistingProtection(entityplayer);
                  if (showBannerRespossessMessage && !this.wasShowingBannerRepossessMessage) {
                     this.bannerRepossessDisplayTick = 60;
                  } else {
                     --this.bannerRepossessDisplayTick;
                  }

                  this.wasShowingBannerRepossessMessage = showBannerRespossessMessage;
               }

               EntityLivingBase viewer = minecraft.field_71451_h;
               i = MathHelper.func_76128_c(viewer.field_70165_t);
               i = MathHelper.func_76128_c(viewer.field_70121_D.field_72338_b);
               int k = MathHelper.func_76128_c(viewer.field_70161_v);
               BiomeGenBase biome = world.func_72807_a(i, k);
               LOTRBiome.updateWaterColor(i, i, k);
               LOTRBiomeGenUtumno.updateFogColor(i, i, k);
               LOTRCloudRenderer.updateClouds(world);
               if (LOTRConfig.aurora) {
                  LOTRRenderNorthernLights.update(viewer);
               }

               LOTRSpeechClient.update();
               LOTRKeyHandler.update();
               LOTRAttackTiming.update();
               this.prevMistTick = this.mistTick;
               if (viewer.field_70163_u >= 72.0D && biome instanceof LOTRBiomeGenMistyMountains && biome != LOTRBiome.mistyMountainsFoothills && world.func_72937_j(i, i, k) && world.func_72972_b(EnumSkyBlock.Block, i, i, k) < 7) {
                  if (this.mistTick < 80) {
                     ++this.mistTick;
                  }
               } else if (this.mistTick > 0) {
                  --this.mistTick;
               }

               if (this.frostTick > 0) {
                  --this.frostTick;
               }

               if (this.burnTick > 0) {
                  --this.burnTick;
               }

               this.prevWightLookTick = this.wightLookTick;
               if (anyWightsViewed) {
                  if (this.wightLookTick < 100) {
                     ++this.wightLookTick;
                  }
               } else if (this.wightLookTick > 0) {
                  --this.wightLookTick;
               }

               this.prevWightNearTick = this.wightNearTick;
               double wightRange = 32.0D;
               List nearbyWights = world.func_72872_a(LOTREntityBarrowWight.class, viewer.field_70121_D.func_72314_b(wightRange, wightRange, wightRange));
               if (!nearbyWights.isEmpty()) {
                  if (this.wightNearTick < 100) {
                     ++this.wightNearTick;
                  }
               } else if (this.wightNearTick > 0) {
                  --this.wightNearTick;
               }

               this.prevBalrogNearTick = this.balrogNearTick;
               double balrogRange = 24.0D;
               List nearbyBalrogs = world.func_72872_a(LOTREntityBalrog.class, viewer.field_70121_D.func_72314_b(balrogRange, balrogRange, balrogRange));
               if (!nearbyBalrogs.isEmpty()) {
                  if (this.balrogNearTick < 100) {
                     ++this.balrogNearTick;
                  }
               } else if (this.balrogNearTick > 0) {
                  --this.balrogNearTick;
               }

               MovingObjectPosition target;
               if (LOTRConfig.enableSunFlare && world.field_73011_w instanceof LOTRWorldProvider && !world.field_73011_w.field_76576_e) {
                  this.prevSunGlare = this.sunGlare;
                  target = viewer.func_70614_a(10000.0D, renderTick);
                  boolean lookingAtSky = target == null || target.field_72313_a == MovingObjectType.MISS;
                  boolean biomeHasSun = true;
                  if (biome instanceof LOTRBiome) {
                     biomeHasSun = ((LOTRBiome)biome).hasSky();
                  }

                  float celestialAngle = world.func_72826_c(renderTick) * 360.0F - 90.0F;
                  float sunYaw = 90.0F;
                  float yc = MathHelper.func_76134_b((float)Math.toRadians((double)(-sunYaw - 180.0F)));
                  float ys = MathHelper.func_76126_a((float)Math.toRadians((double)(-sunYaw - 180.0F)));
                  float pc = -MathHelper.func_76134_b((float)Math.toRadians((double)(-celestialAngle)));
                  float ps = MathHelper.func_76126_a((float)Math.toRadians((double)(-celestialAngle)));
                  Vec3 sunVec = Vec3.func_72443_a((double)(ys * pc), (double)ps, (double)(yc * pc));
                  Vec3 lookVec = viewer.func_70676_i(renderTick);
                  double cos = lookVec.func_72430_b(sunVec) / (lookVec.func_72433_c() * sunVec.func_72433_c());
                  float cosThreshold = 0.95F;
                  float cQ = ((float)cos - cosThreshold) / (1.0F - cosThreshold);
                  cQ = Math.max(cQ, 0.0F);
                  float brightness = world.func_72971_b(renderTick);
                  float brightnessThreshold = 0.7F;
                  float bQ = (brightness - brightnessThreshold) / (1.0F - brightnessThreshold);
                  bQ = Math.max(bQ, 0.0F);
                  float maxGlare = cQ * bQ;
                  if (maxGlare > 0.0F && lookingAtSky && !world.func_72896_J() && biomeHasSun) {
                     if (this.sunGlare < maxGlare) {
                        this.sunGlare += 0.1F * maxGlare;
                        this.sunGlare = Math.min(this.sunGlare, maxGlare);
                     } else if (this.sunGlare > maxGlare) {
                        this.sunGlare -= 0.02F;
                        this.sunGlare = Math.max(this.sunGlare, maxGlare);
                     }
                  } else {
                     if (this.sunGlare > 0.0F) {
                        this.sunGlare -= 0.02F;
                     }

                     this.sunGlare = Math.max(this.sunGlare, 0.0F);
                  }
               } else {
                  this.prevSunGlare = this.sunGlare = 0.0F;
               }

               if (LOTRConfig.newWeather) {
                  this.prevRainFactor = this.rainFactor;
                  if (world.func_72896_J()) {
                     if (this.rainFactor < 1.0F) {
                        this.rainFactor += 0.008333334F;
                        this.rainFactor = Math.min(this.rainFactor, 1.0F);
                     }
                  } else if (this.rainFactor > 0.0F) {
                     this.rainFactor -= 0.0016666667F;
                     this.rainFactor = Math.max(this.rainFactor, 0.0F);
                  }
               } else {
                  this.prevRainFactor = this.rainFactor = 0.0F;
               }

               if (minecraft.field_71474_y.field_74362_aa < 2) {
                  this.spawnEnvironmentFX(entityplayer, world);
               }

               LOTRClientProxy.customEffectRenderer.updateEffects();
               if (minecraft.field_71451_h.func_82165_m(Potion.field_76431_k.field_76415_H)) {
                  float drunkenness = (float)minecraft.field_71451_h.func_70660_b(Potion.field_76431_k).func_76459_b();
                  drunkenness /= 20.0F;
                  if (drunkenness > 100.0F) {
                     drunkenness = 100.0F;
                  }

                  EntityLivingBase var10000 = minecraft.field_71451_h;
                  var10000.field_70177_z += (float)this.drunkennessDirection * drunkenness / 20.0F;
                  var10000 = minecraft.field_71451_h;
                  var10000.field_70125_A += MathHelper.func_76134_b((float)minecraft.field_71451_h.field_70173_aa / 10.0F) * drunkenness / 20.0F;
                  if (world.field_73012_v.nextInt(100) == 0) {
                     this.drunkennessDirection *= -1;
                  }
               }

               if (LOTRDimension.getCurrentDimension(world) == LOTRDimension.UTUMNO) {
                  if (this.inUtumnoReturnPortal) {
                     if (this.utumnoCamRoll < 180.0F) {
                        this.utumnoCamRoll += 5.0F;
                        this.utumnoCamRoll = Math.min(this.utumnoCamRoll, 180.0F);
                        LOTRReflectionClient.setCameraRoll(minecraft.field_71460_t, this.utumnoCamRoll);
                     }
                  } else if (this.utumnoCamRoll > 0.0F) {
                     this.utumnoCamRoll -= 5.0F;
                     this.utumnoCamRoll = Math.max(this.utumnoCamRoll, 0.0F);
                     LOTRReflectionClient.setCameraRoll(minecraft.field_71460_t, this.utumnoCamRoll);
                  }
               } else if (this.utumnoCamRoll != 0.0F) {
                  this.utumnoCamRoll = 0.0F;
                  LOTRReflectionClient.setCameraRoll(minecraft.field_71460_t, this.utumnoCamRoll);
               }

               if (this.newDate > 0) {
                  --this.newDate;
               }

               this.ambienceTicker.updateAmbience(world, entityplayer);
               if (scrapTraderMisbehaveTick > 0) {
                  --scrapTraderMisbehaveTick;
                  if (scrapTraderMisbehaveTick <= 0) {
                     world.field_73011_w.field_76573_f = Arrays.copyOf(this.storedLightTable, this.storedLightTable.length);
                     Entity scrap = world.func_73045_a(this.storedScrapID);
                     if (scrap != null) {
                        scrap.field_70158_ak = false;
                     }
                  }
               } else {
                  target = minecraft.field_71476_x;
                  if (target != null && target.field_72313_a == MovingObjectType.ENTITY && target.field_72308_g instanceof LOTREntityScrapTrader) {
                     LOTREntityScrapTrader scrap = (LOTREntityScrapTrader)target.field_72308_g;
                     if (minecraft.field_71462_r == null && world.field_73012_v.nextInt(50000) == 0) {
                        scrapTraderMisbehaveTick = 400;
                        scrap.field_70158_ak = true;
                        this.storedScrapID = scrap.func_145782_y();
                        float[] lightTable = world.field_73011_w.field_76573_f;
                        this.storedLightTable = Arrays.copyOf(lightTable, lightTable.length);

                        for(int l = 0; l < lightTable.length; ++l) {
                           lightTable[l] = 1.0E-7F;
                        }
                     }
                  }
               }
            }

            if ((entityplayer.field_71093_bK == 0 || entityplayer.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) && playersInPortals.containsKey(entityplayer)) {
               List portals = world.func_72872_a(LOTREntityPortal.class, entityplayer.field_70121_D.func_72314_b(8.0D, 8.0D, 8.0D));
               inPortal = false;

               for(i = 0; i < portals.size(); ++i) {
                  LOTREntityPortal portal = (LOTREntityPortal)portals.get(i);
                  if (portal.field_70121_D.func_72326_a(entityplayer.field_70121_D)) {
                     inPortal = true;
                     break;
                  }
               }

               if (inPortal) {
                  i = (Integer)playersInPortals.get(entityplayer);
                  ++i;
                  playersInPortals.put(entityplayer, i);
                  if (i >= 100) {
                     minecraft.func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), world.field_73012_v.nextFloat() * 0.4F + 0.8F));
                     playersInPortals.remove(entityplayer);
                  }
               } else {
                  playersInPortals.remove(entityplayer);
               }
            }

            this.updatePlayerInPortal(entityplayer, playersInElvenPortals, LOTRMod.elvenPortal);
            this.updatePlayerInPortal(entityplayer, playersInMorgulPortals, LOTRMod.morgulPortal);
            if (this.inUtumnoReturnPortal) {
               entityplayer.func_70107_b((double)this.utumnoReturnX + 0.5D, entityplayer.field_70163_u, (double)this.utumnoReturnZ + 0.5D);
               if (this.lastUtumnoReturnY >= 0.0D && entityplayer.field_70163_u < this.lastUtumnoReturnY) {
                  entityplayer.func_70107_b(entityplayer.field_70165_t, this.lastUtumnoReturnY, entityplayer.field_70161_v);
               }

               this.lastUtumnoReturnY = entityplayer.field_70163_u;
            } else {
               this.lastUtumnoReturnY = -1.0D;
            }

            this.inUtumnoReturnPortal = false;
         }

         LOTRClientProxy.musicHandler.update();
         if (LOTRConfig.displayMusicTrack) {
            LOTRMusicTrack nowPlaying = LOTRMusicTicker.currentTrack;
            if (nowPlaying != this.lastTrack) {
               this.lastTrack = nowPlaying;
               this.musicTrackTick = 200;
            }

            if (this.lastTrack != null && this.musicTrackTick > 0) {
               --this.musicTrackTick;
            }
         }

         GuiScreen guiscreen = minecraft.field_71462_r;
         if (guiscreen != null) {
            if (guiscreen instanceof GuiMainMenu && !(this.lastGuiOpen instanceof GuiMainMenu)) {
               LOTRLevelData.needsLoad = true;
               LOTRTime.needsLoad = true;
               LOTRFellowshipData.needsLoad = true;
               LOTRFactionBounties.needsLoad = true;
               LOTRFactionRelations.needsLoad = true;
               LOTRDate.resetWorldTimeInMenu();
               LOTRConquestGrid.needsLoad = true;
               LOTRSpeechClient.clearAll();
               LOTRAttackTiming.reset();
               LOTRGuiMenu.resetLastMenuScreen();
               LOTRGuiMap.clearPlayerLocations();
               LOTRCloudRenderer.resetClouds();
               this.firstAlignmentRender = true;
               watchedInvasion.clear();
            }

            this.lastGuiOpen = guiscreen;
         }

         anyWightsViewed = false;
      }

   }

   @SubscribeEvent
   public void onPlayerTick(PlayerTickEvent event) {
      EntityPlayer player = event.player;
      if (event.phase == Phase.END && player instanceof EntityClientPlayerMP) {
         EntityClientPlayerMP clientPlayer = (EntityClientPlayerMP)player;
         if (clientPlayer.func_70115_ae()) {
            LOTRMountFunctions.sendControlToServer(clientPlayer);
         }
      }

   }

   @SubscribeEvent
   public void onRenderTick(RenderTickEvent event) {
      Minecraft minecraft = Minecraft.func_71410_x();
      EntityClientPlayerMP entityplayer = minecraft.field_71439_g;
      World world = minecraft.field_71441_e;
      if (event.phase == Phase.START) {
         renderTick = event.renderTickTime;
         if (this.cancelItemHighlight) {
            GuiIngame guiIngame = minecraft.field_71456_v;
            int highlightTicks = LOTRReflectionClient.getHighlightedItemTicks(guiIngame);
            if (highlightTicks > 0) {
               LOTRReflectionClient.setHighlightedItemTicks(guiIngame, 0);
               this.cancelItemHighlight = false;
            }
         }
      }

      if (event.phase == Phase.END) {
         if (entityplayer != null && world != null) {
            ScaledResolution resolution;
            int w;
            int h;
            int compassX;
            float invScale;
            float alpha;
            String line;
            int x;
            if ((world.field_73011_w instanceof LOTRWorldProvider || LOTRConfig.alwaysShowAlignment) && Minecraft.func_71382_s()) {
               this.alignmentXPrev = this.alignmentXCurrent;
               this.alignmentYPrev = this.alignmentYCurrent;
               this.alignmentXCurrent = this.alignmentXBase;
               int yMove = (int)((float)(this.alignmentYBase - -20) / 10.0F);
               boolean alignmentOnscreen = (minecraft.field_71462_r == null || minecraft.field_71462_r instanceof LOTRGuiMessage) && !minecraft.field_71474_y.field_74321_H.func_151470_d() && !minecraft.field_71474_y.field_74330_P;
               if (alignmentOnscreen) {
                  this.alignmentYCurrent = Math.min(this.alignmentYCurrent + yMove, this.alignmentYBase);
               } else {
                  this.alignmentYCurrent = Math.max(this.alignmentYCurrent - yMove, -20);
               }

               this.renderAlignment(minecraft, renderTick);
               if (LOTRConfig.enableOnscreenCompass && minecraft.field_71462_r == null && !minecraft.field_71474_y.field_74330_P) {
                  GL11.glPushMatrix();
                  resolution = new ScaledResolution(minecraft, minecraft.field_71443_c, minecraft.field_71440_d);
                  w = resolution.func_78326_a();
                  h = resolution.func_78328_b();
                  compassX = w - 60;
                  int compassY = 40;
                  GL11.glTranslatef((float)compassX, (float)compassY, 0.0F);
                  invScale = entityplayer.field_70126_B + (entityplayer.field_70177_z - entityplayer.field_70126_B) * event.renderTickTime;
                  invScale = 180.0F - invScale;
                  LOTRModelCompass.compassModel.render(1.0F, invScale);
                  GL11.glPopMatrix();
                  if (LOTRConfig.compassExtraInfo) {
                     GL11.glPushMatrix();
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     alpha = 0.5F;
                     float invScale = 1.0F / alpha;
                     compassX = (int)((float)compassX * invScale);
                     x = (int)((float)compassY * invScale);
                     GL11.glScalef(alpha, alpha, alpha);
                     line = MathHelper.func_76128_c(entityplayer.field_70165_t) + ", " + MathHelper.func_76128_c(entityplayer.field_70121_D.field_72338_b) + ", " + MathHelper.func_76128_c(entityplayer.field_70161_v);
                     FontRenderer fontRenderer = minecraft.field_71466_p;
                     fontRenderer.func_78276_b(line, compassX - fontRenderer.func_78256_a(line) / 2, x + 70, 16777215);
                     int playerX = MathHelper.func_76128_c(entityplayer.field_70165_t);
                     int playerZ = MathHelper.func_76128_c(entityplayer.field_70161_v);
                     if (LOTRClientProxy.doesClientChunkExist(world, playerX, playerZ)) {
                        BiomeGenBase biome = world.func_72807_a(playerX, playerZ);
                        if (biome instanceof LOTRBiome) {
                           String biomeName = ((LOTRBiome)biome).getBiomeDisplayName();
                           fontRenderer.func_78276_b(biomeName, compassX - fontRenderer.func_78256_a(biomeName) / 2, x - 70, 16777215);
                        }
                     }

                     GL11.glPopMatrix();
                  }
               }
            }

            String authors;
            if (entityplayer.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID && minecraft.field_71462_r == null && this.newDate > 0) {
               int halfMaxDate = 100;
               float alpha = 0.0F;
               if (this.newDate > halfMaxDate) {
                  alpha = (float)(200 - this.newDate) / (float)halfMaxDate;
               } else {
                  alpha = (float)this.newDate / (float)halfMaxDate;
               }

               authors = LOTRDate.ShireReckoning.getShireDate().getDateName(true);
               ScaledResolution resolution = new ScaledResolution(minecraft, minecraft.field_71443_c, minecraft.field_71440_d);
               h = resolution.func_78326_a();
               compassX = resolution.func_78328_b();
               float scale = 1.5F;
               invScale = 1.0F / scale;
               h = (int)((float)h * invScale);
               compassX = (int)((float)compassX * invScale);
               int x = (h - minecraft.field_71466_p.func_78256_a(authors)) / 2;
               int y = (compassX - minecraft.field_71466_p.field_78288_b) * 2 / 5;
               GL11.glScalef(scale, scale, scale);
               GL11.glEnable(3042);
               OpenGlHelper.func_148821_a(770, 771, 1, 0);
               minecraft.field_71466_p.func_78276_b(authors, x, y, 16777215 + (LOTRClientProxy.getAlphaInt(alpha) << 24));
               GL11.glDisable(3042);
               GL11.glScalef(invScale, invScale, invScale);
            }

            if (LOTRConfig.displayMusicTrack && minecraft.field_71462_r == null && this.lastTrack != null && this.musicTrackTick > 0) {
               List lines = new ArrayList();
               lines.add(StatCollector.func_74838_a("lotr.music.nowPlaying"));
               String title = this.lastTrack.getTitle();
               lines.add(title);
               if (!this.lastTrack.getAuthors().isEmpty()) {
                  authors = "(";
                  w = 0;

                  for(Iterator var29 = this.lastTrack.getAuthors().iterator(); var29.hasNext(); ++w) {
                     String auth = (String)var29.next();
                     authors = authors + auth;
                     if (w < this.lastTrack.getAuthors().size() - 1) {
                        authors = authors + ", ";
                     }
                  }

                  authors = authors + ")";
                  lines.add(authors);
               }

               resolution = new ScaledResolution(minecraft, minecraft.field_71443_c, minecraft.field_71440_d);
               w = resolution.func_78326_a();
               h = resolution.func_78328_b();
               int border = 20;
               int var10000 = w - border;
               int y = h - border - lines.size() * minecraft.field_71466_p.field_78288_b;
               alpha = 1.0F;
               if (this.musicTrackTick >= 140) {
                  alpha = (float)(200 - this.musicTrackTick) / 60.0F;
               } else if (this.musicTrackTick <= 60) {
                  alpha = (float)this.musicTrackTick / 60.0F;
               }

               for(Iterator var37 = lines.iterator(); var37.hasNext(); y += minecraft.field_71466_p.field_78288_b) {
                  line = (String)var37.next();
                  x = w - border - minecraft.field_71466_p.func_78256_a(line);
                  minecraft.field_71466_p.func_78276_b(line, x, y, 16777215 + (LOTRClientProxy.getAlphaInt(alpha) << 24));
               }
            }
         }

         notificationDisplay.updateWindow();
         if (LOTRConfig.enableQuestTracker && minecraft.field_71462_r == null && !minecraft.field_71474_y.field_74330_P) {
            miniquestTracker.drawTracker(minecraft, entityplayer);
         }
      }

   }

   private void updatePlayerInPortal(EntityPlayer entityplayer, HashMap players, Block portalBlock) {
      if ((entityplayer.field_71093_bK == 0 || entityplayer.field_71093_bK == LOTRDimension.MIDDLE_EARTH.dimensionID) && players.containsKey(entityplayer)) {
         boolean inPortal = entityplayer.field_70170_p.func_147439_a(MathHelper.func_76128_c(entityplayer.field_70165_t), MathHelper.func_76128_c(entityplayer.field_70121_D.field_72338_b), MathHelper.func_76128_c(entityplayer.field_70161_v)) == portalBlock;
         if (inPortal) {
            int i = (Integer)players.get(entityplayer);
            ++i;
            players.put(entityplayer, i);
            if (i >= entityplayer.func_82145_z()) {
               Minecraft.func_71410_x().func_147118_V().func_147682_a(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), entityplayer.field_70170_p.field_73012_v.nextFloat() * 0.4F + 0.8F));
               players.remove(entityplayer);
            }
         } else {
            players.remove(entityplayer);
         }
      }

   }

   private void spawnEnvironmentFX(EntityPlayer entityplayer, World world) {
      world.field_72984_F.func_76320_a("lotrEnvironmentFX");
      int i = MathHelper.func_76128_c(entityplayer.field_70165_t);
      int j = MathHelper.func_76128_c(entityplayer.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(entityplayer.field_70161_v);
      byte range = 16;

      for(int l = 0; l < 1000; ++l) {
         int i1 = i + world.field_73012_v.nextInt(range) - world.field_73012_v.nextInt(range);
         int j1 = j + world.field_73012_v.nextInt(range) - world.field_73012_v.nextInt(range);
         int k1 = k + world.field_73012_v.nextInt(range) - world.field_73012_v.nextInt(range);
         Block block = world.func_147439_a(i1, j1, k1);
         int meta = world.func_72805_g(i1, j1, k1);
         if (block.func_149688_o() == Material.field_151586_h) {
            BiomeGenBase biome = world.func_72807_a(i1, k1);
            if (biome instanceof LOTRBiomeGenMirkwoodCorrupted && world.field_73012_v.nextInt(20) == 0) {
               LOTRMod.proxy.spawnParticle("mirkwoodWater", (double)((float)i1 + world.field_73012_v.nextFloat()), (double)j1 + 0.75D, (double)((float)k1 + world.field_73012_v.nextFloat()), 0.0D, 0.05D, 0.0D);
            }

            if (biome instanceof LOTRBiomeGenMorgulVale && world.field_73012_v.nextInt(40) == 0) {
               LOTRMod.proxy.spawnParticle("morgulWater", (double)((float)i1 + world.field_73012_v.nextFloat()), (double)j1 + 0.75D, (double)((float)k1 + world.field_73012_v.nextFloat()), 0.0D, 0.05D, 0.0D);
            }

            if (biome instanceof LOTRBiomeGenDeadMarshes && world.field_73012_v.nextInt(800) == 0) {
               world.func_72838_d(new LOTREntityDeadMarshFace(world, (double)((float)i1 + world.field_73012_v.nextFloat()), (double)j1 + 0.25D - (double)world.field_73012_v.nextFloat(), (double)((float)k1 + world.field_73012_v.nextFloat())));
            }
         }

         if (block.func_149688_o() == Material.field_151586_h && meta != 0) {
            Block below = world.func_147439_a(i1, j1 - 1, k1);
            if (below.func_149688_o() == Material.field_151586_h) {
               for(int i2 = i1 - 1; i2 <= i1 + 1; ++i2) {
                  for(int k2 = k1 - 1; k2 <= k1 + 1; ++k2) {
                     Block adjBlock = world.func_147439_a(i2, j1 - 1, k2);
                     int adjMeta = world.func_72805_g(i2, j1 - 1, k2);
                     if (adjBlock.func_149688_o() == Material.field_151586_h && adjMeta == 0 && world.func_147437_c(i2, j1, k2)) {
                        for(int l1 = 0; l1 < 2; ++l1) {
                           double d = (double)i1 + 0.5D + (double)((float)(i2 - i1) * world.field_73012_v.nextFloat());
                           double d1 = (double)((float)j1 + world.field_73012_v.nextFloat() * 0.2F);
                           double d2 = (double)k1 + 0.5D + (double)((float)(k2 - k1) * world.field_73012_v.nextFloat());
                           world.func_72869_a("explode", d, d1, d2, 0.0D, 0.0D, 0.0D);
                        }
                     }
                  }
               }
            }
         }
      }

      world.field_72984_F.func_76319_b();
   }

   @SubscribeEvent
   public void onWorldLoad(Load event) {
      if (event.world instanceof WorldClient) {
         LOTRClientProxy.customEffectRenderer.clearEffectsAndSetWorld(event.world);
      }

   }

   @SubscribeEvent
   public void onPreRenderGameOverlay(Pre event) {
      Minecraft mc = Minecraft.func_71410_x();
      World world = mc.field_71441_e;
      EntityPlayer entityplayer = mc.field_71439_g;
      float partialTicks = event.partialTicks;
      GuiIngame guiIngame = mc.field_71456_v;
      if (world != null && entityplayer != null) {
         if (event.type == ElementType.ALL) {
            mc.field_71441_e.field_72984_F.func_76320_a("lotr_fixHighlightedItemName");
            ItemStack itemstack = LOTRReflectionClient.getHighlightedItemStack(guiIngame);
            if (itemstack != null && !itemstack.func_82837_s()) {
               List enchants = LOTREnchantmentHelper.getEnchantList(itemstack);
               if (!enchants.isEmpty()) {
                  this.lastHighlightedItemstack = itemstack;
                  this.highlightedItemstackName = itemstack.func_82837_s() ? itemstack.func_82833_r() : null;
                  itemstack.func_151001_c(LOTREnchantmentHelper.getFullEnchantedName(itemstack, itemstack.func_82833_r()));
               }
            }

            mc.field_71441_e.field_72984_F.func_76319_b();
         }

         if (event.type == ElementType.HELMET) {
            float frostAlpha;
            if (this.sunGlare > 0.0F && mc.field_71474_y.field_74320_O == 0) {
               frostAlpha = this.prevSunGlare + (this.sunGlare - this.prevSunGlare) * partialTicks;
               frostAlpha *= 1.0F;
               this.renderOverlay((float[])null, frostAlpha, mc, (ResourceLocation)null);
            }

            int i;
            if (playersInPortals.containsKey(entityplayer)) {
               i = (Integer)playersInPortals.get(entityplayer);
               if (i > 0) {
                  this.renderOverlay((float[])null, 0.1F + (float)i / 100.0F * 0.6F, mc, portalOverlay);
               }
            }

            if (playersInElvenPortals.containsKey(entityplayer)) {
               i = (Integer)playersInElvenPortals.get(entityplayer);
               if (i > 0) {
                  this.renderOverlay((float[])null, 0.1F + (float)i / (float)entityplayer.func_82145_z() * 0.6F, mc, elvenPortalOverlay);
               }
            }

            if (playersInMorgulPortals.containsKey(entityplayer)) {
               i = (Integer)playersInMorgulPortals.get(entityplayer);
               if (i > 0) {
                  this.renderOverlay((float[])null, 0.1F + (float)i / (float)entityplayer.func_82145_z() * 0.6F, mc, morgulPortalOverlay);
               }
            }

            float frostAlphaEdge;
            if (LOTRConfig.enableMistyMountainsMist) {
               frostAlpha = (float)this.prevMistTick + (float)(this.mistTick - this.prevMistTick) * partialTicks;
               frostAlpha /= 80.0F;
               frostAlphaEdge = (float)entityplayer.field_70163_u / 256.0F;
               this.mistFactor = frostAlpha * frostAlphaEdge;
               if (this.mistFactor > 0.0F) {
                  this.renderOverlay((float[])null, this.mistFactor * 0.75F, mc, mistOverlay);
               }
            } else {
               this.mistFactor = 0.0F;
            }

            if (this.frostTick > 0) {
               frostAlpha = (float)this.frostTick / 80.0F;
               frostAlpha *= 0.9F;
               frostAlphaEdge = (float)Math.sqrt((double)frostAlpha);
               this.renderOverlayWithVerticalGradients(frostRGBEdge, frostRGBMiddle, frostAlphaEdge, frostAlpha, mc);
               this.renderOverlay((float[])null, frostAlpha * 0.6F, mc, frostOverlay);
            }

            if (this.burnTick > 0) {
               this.renderOverlay((float[])null, (float)this.burnTick / 40.0F * 0.6F, mc, burnOverlay);
            }

            if (this.wightLookTick > 0) {
               this.renderOverlay((float[])null, (float)this.wightLookTick / 100.0F * 0.95F, mc, wightOverlay);
            }
         }

         int width;
         int height;
         int level;
         int i;
         ScaledResolution resolution;
         if (event.type == ElementType.HOTBAR) {
            if (LOTRConfig.meleeAttackMeter) {
               LOTRAttackTiming.renderAttackMeter(event.resolution, partialTicks);
            }

            if (entityplayer.field_70154_o instanceof LOTREntitySpiderBase) {
               LOTREntitySpiderBase spider = (LOTREntitySpiderBase)entityplayer.field_70154_o;
               if (spider.shouldRenderClimbingMeter()) {
                  mc.func_110434_K().func_110577_a(GuiIngame.field_110324_m);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  GL11.glDisable(3042);
                  mc.field_71424_I.func_76320_a("spiderClimb");
                  resolution = event.resolution;
                  width = resolution.func_78326_a();
                  height = resolution.func_78328_b();
                  float charge = spider.getClimbFractionRemaining();
                  int barWidth = true;
                  level = width / 2 - 91;
                  i = (int)(charge * 183.0F);
                  int top = height - 32 + 3;
                  guiIngame.func_73729_b(level, top, 0, 84, 182, 5);
                  if (i > 0) {
                     guiIngame.func_73729_b(level, top, 0, 89, i, 5);
                  }

                  GL11.glEnable(3042);
                  mc.field_71424_I.func_76319_b();
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               }
            }
         }

         if (event.type == ElementType.HEALTH && entityplayer.func_70644_a(LOTRPoisonedDrinks.killingPoison) && !entityplayer.func_70644_a(Potion.field_76436_u)) {
            entityplayer.func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, 20));
            this.addedClientPoisonEffect = true;
         }

         boolean enchantingDisabled = !LOTRLevelData.clientside_thisServer_enchanting && world.field_73011_w instanceof LOTRWorldProvider;
         if (event.type == ElementType.EXPERIENCE && enchantingDisabled) {
            event.setCanceled(true);
            return;
         }

         if (event.type == ElementType.ALL && enchantingDisabled && entityplayer.field_70154_o == null) {
            GuiIngameForge.left_height -= 6;
            GuiIngameForge.right_height -= 6;
         }

         if (event.type == ElementType.ARMOR) {
            event.setCanceled(true);
            resolution = event.resolution;
            width = resolution.func_78326_a();
            height = resolution.func_78328_b();
            mc.field_71424_I.func_76320_a("armor");
            GL11.glEnable(3042);
            int left = width / 2 - 91;
            int top = height - GuiIngameForge.left_height;
            level = LOTRWeaponStats.getTotalArmorValue(mc.field_71439_g);
            if (level > 0) {
               for(i = 1; i < 20; i += 2) {
                  if (i < level) {
                     guiIngame.func_73729_b(left, top, 34, 9, 9, 9);
                  } else if (i == level) {
                     guiIngame.func_73729_b(left, top, 25, 9, 9, 9);
                  } else if (i > level) {
                     guiIngame.func_73729_b(left, top, 16, 9, 9, 9);
                  }

                  left += 8;
               }
            }

            GuiIngameForge.left_height += 10;
            GL11.glDisable(3042);
            mc.field_71424_I.func_76319_b();
            return;
         }
      }

   }

   @SubscribeEvent
   public void onPostRenderGameOverlay(Post event) {
      Minecraft mc = Minecraft.func_71410_x();
      World world = mc.field_71441_e;
      EntityPlayer entityplayer = mc.field_71439_g;
      GuiIngame guiIngame = mc.field_71456_v;
      if (world != null && entityplayer != null) {
         if (event.type == ElementType.ALL && this.lastHighlightedItemstack != null) {
            if (this.highlightedItemstackName != null) {
               this.lastHighlightedItemstack.func_151001_c(this.highlightedItemstackName);
            } else {
               this.lastHighlightedItemstack.func_135074_t();
            }

            this.lastHighlightedItemstack = null;
            this.highlightedItemstackName = null;
         }

         int opacity;
         int width;
         int y;
         if (event.type == ElementType.BOSSHEALTH && watchedInvasion.isActive()) {
            GL11.glEnable(3042);
            FontRenderer fr = mc.field_71466_p;
            ScaledResolution scaledresolution = event.resolution;
            opacity = scaledresolution.func_78326_a();
            int barWidth = 182;
            width = (int)(watchedInvasion.getHealth() * (float)(barWidth - 2));
            int barHeight = 5;
            y = opacity / 2 - barWidth / 2;
            int barY = 12;
            if (isBossActive()) {
               barY += 20;
            }

            mc.func_110434_K().func_110577_a(LOTRClientProxy.alignmentTexture);
            guiIngame.func_73729_b(y, barY, 64, 64, barWidth, barHeight);
            if (width > 0) {
               float[] rgb = watchedInvasion.getRGB();
               GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0F);
               guiIngame.func_73729_b(y + 1, barY + 1, 65, 70, width, barHeight - 2);
            }

            String s = watchedInvasion.getTitle();
            fr.func_78261_a(s, opacity / 2 - fr.func_78256_a(s) / 2, barY - 10, 16777215);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.func_110434_K().func_110577_a(Gui.field_110324_m);
            GL11.glDisable(3042);
         }

         if (event.type == ElementType.HEALTH && this.addedClientPoisonEffect) {
            entityplayer.func_70618_n(Potion.field_76436_u.field_76415_H);
            this.addedClientPoisonEffect = false;
         }

         if (event.type == ElementType.TEXT && this.bannerRepossessDisplayTick > 0) {
            String text = StatCollector.func_74837_a("item.lotr.banner.toggleRepossess", new Object[]{GameSettings.func_74298_c(mc.field_71474_y.field_74311_E.func_151463_i())});
            int fadeAtTick = 10;
            opacity = (int)((float)this.bannerRepossessDisplayTick * 255.0F / (float)fadeAtTick);
            opacity = Math.min(opacity, 255);
            if (opacity > 0) {
               ScaledResolution scaledresolution = event.resolution;
               width = scaledresolution.func_78326_a();
               int height = scaledresolution.func_78328_b();
               y = height - 59;
               y -= 12;
               if (!mc.field_71442_b.func_78755_b()) {
                  y += 14;
               }

               GL11.glPushMatrix();
               GL11.glEnable(3042);
               OpenGlHelper.func_148821_a(770, 771, 1, 0);
               FontRenderer fr = mc.field_71466_p;
               int x = (width - fr.func_78256_a(text)) / 2;
               fr.func_78276_b(text, x, y, 16777215 | opacity << 24);
               GL11.glDisable(3042);
               GL11.glPopMatrix();
            }
         }
      }

   }

   @SubscribeEvent
   public void onRenderDebugText(Text event) {
      Minecraft mc = Minecraft.func_71410_x();
      if (mc.field_71474_y.field_74330_P && mc.field_71441_e != null && mc.field_71439_g != null && mc.field_71441_e.func_72959_q() instanceof LOTRWorldChunkManager) {
         mc.field_71441_e.field_72984_F.func_76320_a("lotrBiomeDisplay");
         LOTRWorldChunkManager chunkManager = (LOTRWorldChunkManager)mc.field_71441_e.func_72959_q();
         int i = MathHelper.func_76128_c(mc.field_71439_g.field_70165_t);
         int j = MathHelper.func_76128_c(mc.field_71439_g.field_70121_D.field_72338_b);
         int k = MathHelper.func_76128_c(mc.field_71439_g.field_70161_v);
         LOTRBiome biome = (LOTRBiome)mc.field_71441_e.func_72807_a(i, k);
         LOTRBiomeVariant variant = chunkManager.getBiomeVariantAt(i, k);
         event.left.add((Object)null);
         biome.addBiomeF3Info(event.left, mc.field_71441_e, variant, i, j, k);
         mc.field_71441_e.field_72984_F.func_76319_b();
      }

   }

   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      Minecraft mc = Minecraft.func_71410_x();
      float f = event.partialTicks;
      if (LOTRConfig.aurora && LOTRDimension.getCurrentDimension(mc.field_71441_e) == LOTRDimension.MIDDLE_EARTH) {
         LOTRRenderNorthernLights.render(mc, mc.field_71441_e, f);
      }

      mc.field_71460_t.func_78463_b((double)f);
      RenderHelper.func_74518_a();
      LOTRClientProxy.customEffectRenderer.renderParticles(mc.field_71451_h, f);
      mc.field_71460_t.func_78483_a((double)f);
      if (Minecraft.func_71382_s() && mc.field_71460_t.field_78532_q == 0) {
         mc.field_71424_I.func_76320_a("lotrSpeech");
         LOTRNPCRendering.renderAllNPCSpeeches(mc, mc.field_71441_e, f);
         mc.field_71424_I.func_76319_b();
      }

   }

   @SubscribeEvent
   public void getItemTooltip(ItemTooltipEvent event) {
      ItemStack itemstack = event.itemStack;
      List tooltip = event.toolTip;
      EntityPlayer entityplayer = event.entityPlayer;
      FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
      List enchantments = LOTREnchantmentHelper.getEnchantList(itemstack);
      String squadron;
      if (!itemstack.func_82837_s() && !enchantments.isEmpty()) {
         squadron = (String)tooltip.get(0);
         squadron = LOTREnchantmentHelper.getFullEnchantedName(itemstack, squadron);
         tooltip.set(0, squadron);
      }

      ArrayList enchGood;
      int kb;
      if (itemstack.func_77973_b() instanceof LOTRSquadrons.SquadronItem) {
         squadron = LOTRSquadrons.getSquadron(itemstack);
         if (!StringUtils.func_151246_b(squadron)) {
            enchGood = new ArrayList();
            enchGood.add(tooltip.get(0));
            enchGood.add(StatCollector.func_74837_a("item.lotr.generic.squadron", new Object[]{squadron}));

            for(kb = 1; kb < tooltip.size(); ++kb) {
               enchGood.add(tooltip.get(kb));
            }

            tooltip.clear();
            tooltip.addAll(enchGood);
         }
      }

      float range;
      int i;
      int i;
      int dmgIndex;
      int pcProtection;
      String ownerFormatted;
      if (LOTRWeaponStats.isMeleeWeapon(itemstack)) {
         dmgIndex = -1;

         for(pcProtection = 0; pcProtection < tooltip.size(); ++pcProtection) {
            ownerFormatted = (String)tooltip.get(pcProtection);
            if (ownerFormatted.startsWith(EnumChatFormatting.BLUE.toString())) {
               dmgIndex = pcProtection;
               break;
            }
         }

         if (dmgIndex >= 0) {
            enchGood = new ArrayList();

            for(kb = 0; kb <= dmgIndex - 1; ++kb) {
               enchGood.add(tooltip.get(kb));
            }

            float meleeDamage = LOTRWeaponStats.getMeleeDamageBonus(itemstack);
            enchGood.add(EnumChatFormatting.BLUE + StatCollector.func_74837_a("lotr.weaponstat.meleeDamage", new Object[]{meleeDamage}));
            range = LOTRWeaponStats.getMeleeSpeed(itemstack);
            i = Math.round(range * 100.0F);
            enchGood.add(EnumChatFormatting.BLUE + StatCollector.func_74837_a("lotr.weaponstat.meleeSpeed", new Object[]{i}));
            float reach = LOTRWeaponStats.getMeleeReachFactor(itemstack);
            i = Math.round(reach * 100.0F);
            enchGood.add(EnumChatFormatting.BLUE + StatCollector.func_74837_a("lotr.weaponstat.reach", new Object[]{i}));
            int kb = LOTRWeaponStats.getTotalKnockback(itemstack);
            if (kb > 0) {
               enchGood.add(EnumChatFormatting.BLUE + StatCollector.func_74837_a("lotr.weaponstat.kb", new Object[]{kb}));
            }

            for(int i = dmgIndex + 1; i < tooltip.size(); ++i) {
               enchGood.add(tooltip.get(i));
            }

            tooltip.clear();
            tooltip.addAll(enchGood);
         }
      }

      if (LOTRWeaponStats.isRangedWeapon(itemstack)) {
         tooltip.add("");
         float drawSpeed = LOTRWeaponStats.getRangedSpeed(itemstack);
         if (drawSpeed > 0.0F) {
            pcProtection = Math.round(drawSpeed * 100.0F);
            tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.func_74837_a("lotr.weaponstat.rangedSpeed", new Object[]{pcProtection}));
         }

         float damage = LOTRWeaponStats.getRangedDamageFactor(itemstack, false);
         if (damage > 0.0F) {
            kb = Math.round(damage * 100.0F);
            tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.func_74837_a("lotr.weaponstat.rangedDamage", new Object[]{kb}));
            if (itemstack.func_77973_b() instanceof ItemBow || itemstack.func_77973_b() instanceof LOTRItemCrossbow) {
               range = LOTRWeaponStats.getRangedDamageFactor(itemstack, true);
               i = Math.round(range * 100.0F);
               tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.func_74837_a("lotr.weaponstat.range", new Object[]{i}));
            }
         }

         kb = LOTRWeaponStats.getRangedKnockback(itemstack);
         if (kb > 0) {
            tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.func_74837_a("lotr.weaponstat.kb", new Object[]{kb}));
         }
      }

      if (LOTRWeaponStats.isPoisoned(itemstack)) {
         tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.func_74837_a("lotr.weaponstat.poison", new Object[0]));
      }

      dmgIndex = LOTRWeaponStats.getArmorProtection(itemstack);
      if (dmgIndex > 0) {
         tooltip.add("");
         pcProtection = Math.round((float)dmgIndex / 25.0F * 100.0F);
         tooltip.add(EnumChatFormatting.BLUE + StatCollector.func_74837_a("lotr.weaponstat.protection", new Object[]{dmgIndex, pcProtection}));
      }

      String line;
      if (!enchantments.isEmpty()) {
         tooltip.add("");
         enchGood = new ArrayList();
         List enchBad = new ArrayList();
         Iterator var22 = enchantments.iterator();

         while(var22.hasNext()) {
            LOTREnchantment ench = (LOTREnchantment)var22.next();
            line = ench.getNamedFormattedDescription(itemstack);
            if (ench.isBeneficial()) {
               enchGood.add(line);
            } else {
               enchBad.add(line);
            }
         }

         tooltip.addAll(enchGood);
         tooltip.addAll(enchBad);
      }

      if (LOTRPoisonedDrinks.isDrinkPoisoned(itemstack) && LOTRPoisonedDrinks.canPlayerSeePoisoned(itemstack, entityplayer)) {
         tooltip.add(EnumChatFormatting.DARK_GREEN + StatCollector.func_74838_a("item.lotr.drink.poison"));
      }

      String currentOwner = LOTRItemOwnership.getCurrentOwner(itemstack);
      if (currentOwner != null) {
         tooltip.add("");
         ownerFormatted = StatCollector.func_74837_a("item.lotr.generic.currentOwner", new Object[]{currentOwner});
         List ownerLines = fontRenderer.func_78271_c(ownerFormatted, 150);

         for(i = 0; i < ownerLines.size(); ++i) {
            line = (String)ownerLines.get(i);
            if (i > 0) {
               line = "  " + line;
            }

            tooltip.add(line);
         }
      }

      List previousOwners = LOTRItemOwnership.getPreviousOwners(itemstack);
      String wanter;
      if (!previousOwners.isEmpty()) {
         tooltip.add("");
         List ownerLines = new ArrayList();
         if (previousOwners.size() == 1) {
            wanter = EnumChatFormatting.ITALIC + StatCollector.func_74837_a("item.lotr.generic.previousOwner", new Object[]{previousOwners.get(0)});
            ownerLines.addAll(fontRenderer.func_78271_c(wanter, 150));
         } else {
            wanter = EnumChatFormatting.ITALIC + StatCollector.func_74838_a("item.lotr.generic.previousOwnerList");
            ownerLines.add(wanter);
            Iterator var33 = previousOwners.iterator();

            while(var33.hasNext()) {
               String previousOwner = (String)var33.next();
               previousOwner = EnumChatFormatting.ITALIC + previousOwner;
               ownerLines.addAll(fontRenderer.func_78271_c(previousOwner, 150));
            }
         }

         for(i = 0; i < ownerLines.size(); ++i) {
            line = (String)ownerLines.get(i);
            if (i > 0) {
               line = "  " + line;
            }

            tooltip.add(line);
         }
      }

      String name;
      if (IPickpocketable.Helper.isPickpocketed(itemstack)) {
         tooltip.add("");
         name = IPickpocketable.Helper.getOwner(itemstack);
         name = StatCollector.func_74837_a("item.lotr.generic.stolen", new Object[]{name});
         wanter = IPickpocketable.Helper.getWanter(itemstack);
         wanter = StatCollector.func_74837_a("item.lotr.generic.stolenWanted", new Object[]{wanter});
         List robbedLines = new ArrayList();
         robbedLines.addAll(fontRenderer.func_78271_c(name, 200));
         robbedLines.addAll(fontRenderer.func_78271_c(wanter, 200));

         for(i = 0; i < robbedLines.size(); ++i) {
            String line = (String)robbedLines.get(i);
            if (i > 0) {
               line = "  " + line;
            }

            tooltip.add(line);
         }
      }

      if (itemstack.func_77973_b() == Item.func_150898_a(Blocks.field_150418_aU)) {
         tooltip.set(0, EnumChatFormatting.RED + (String)tooltip.get(0));
      }

      if (LOTRMod.isAprilFools()) {
         name = (String)tooltip.get(0);
         name = name.replace("kebab", "gyros");
         name = name.replace("Kebab", "Gyros");
         tooltip.set(0, name);
      }

   }

   @SubscribeEvent
   public void onFOVUpdate(FOVUpdateEvent event) {
      EntityPlayerSP entityplayer = event.entity;
      float fov = event.newfov;
      ItemStack itemstack = entityplayer.func_70694_bm();
      Item item = itemstack == null ? null : itemstack.func_77973_b();
      float usage = -1.0F;
      if (entityplayer.func_71039_bw()) {
         float maxDrawTime = 0.0F;
         if (item instanceof LOTRItemBow) {
            maxDrawTime = (float)((LOTRItemBow)item).getMaxDrawTime();
         } else if (item instanceof LOTRItemCrossbow) {
            maxDrawTime = (float)((LOTRItemCrossbow)item).getMaxDrawTime();
         } else if (item instanceof LOTRItemSpear) {
            maxDrawTime = (float)((LOTRItemSpear)item).getMaxDrawTime();
         } else if (item instanceof LOTRItemBlowgun) {
            maxDrawTime = (float)((LOTRItemBlowgun)item).getMaxDrawTime();
         }

         if (maxDrawTime > 0.0F) {
            int i = entityplayer.func_71057_bx();
            usage = (float)i / maxDrawTime;
            if (usage > 1.0F) {
               usage = 1.0F;
            } else {
               usage *= usage;
            }
         }
      }

      if (LOTRItemCrossbow.isLoaded(itemstack)) {
         usage = 1.0F;
      }

      if (usage >= 0.0F) {
         fov *= 1.0F - usage * 0.15F;
      }

      event.newfov = fov;
   }

   @SubscribeEvent
   public void onRenderFog(RenderFogEvent event) {
      Minecraft mc = Minecraft.func_71410_x();
      EntityLivingBase viewer = event.entity;
      World world = mc.field_71441_e;
      WorldProvider provider = world.field_73011_w;
      int i = MathHelper.func_76128_c(viewer.field_70165_t);
      int j = MathHelper.func_76128_c(viewer.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(viewer.field_70161_v);
      BiomeGenBase biome = world.func_72807_a(i, k);
      float farPlane = event.farPlaneDistance;
      int fogMode = event.fogMode;
      if (provider instanceof LOTRWorldProvider) {
         LOTRBiome lotrbiome = (LOTRBiome)biome;
         float[] fogStartEnd = ((LOTRWorldProvider)provider).modifyFogIntensity(farPlane, fogMode);
         float fogStart = fogStartEnd[0];
         float fogEnd = fogStartEnd[1];
         float wightFactor;
         float balrogOpacityStart;
         float balrogOpacityEnd;
         if (LOTRConfig.newWeather && (lotrbiome.getEnableRain() || lotrbiome.func_76746_c())) {
            wightFactor = this.prevRainFactor + (this.rainFactor - this.prevRainFactor) * renderTick;
            if (wightFactor > 0.0F) {
               balrogOpacityStart = 0.95F;
               balrogOpacityEnd = 0.2F;
               fogStart -= fogStart * wightFactor * balrogOpacityStart;
               fogEnd -= fogEnd * wightFactor * balrogOpacityEnd;
            }
         }

         if (this.mistFactor > 0.0F) {
            wightFactor = 0.95F;
            balrogOpacityStart = 0.7F;
            fogStart -= fogStart * this.mistFactor * wightFactor;
            fogEnd -= fogEnd * this.mistFactor * balrogOpacityStart;
         }

         wightFactor = (float)this.prevWightNearTick + (float)(this.wightNearTick - this.prevWightNearTick) * renderTick;
         wightFactor /= 100.0F;
         if (wightFactor > 0.0F) {
            balrogOpacityStart = 0.97F;
            balrogOpacityEnd = 0.75F;
            fogStart -= fogStart * wightFactor * balrogOpacityStart;
            fogEnd -= fogEnd * wightFactor * balrogOpacityEnd;
         }

         if (lotrbiome instanceof LOTRBiomeGenBarrowDowns) {
            if (wightFactor > 0.0F) {
               int sky0 = lotrbiome.getBaseSkyColorByTemp(i, j, k);
               int sky1 = 9674385;
               int clouds0 = 16777215;
               int clouds1 = 11842740;
               int fog0 = 16777215;
               int fog1 = 10197915;
               lotrbiome.biomeColors.setSky(LOTRColorUtil.lerpColors_I(sky0, sky1, wightFactor));
               lotrbiome.biomeColors.setClouds(LOTRColorUtil.lerpColors_I(clouds0, clouds1, wightFactor));
               lotrbiome.biomeColors.setFog(LOTRColorUtil.lerpColors_I(fog0, fog1, wightFactor));
            } else {
               lotrbiome.biomeColors.resetSky();
               lotrbiome.biomeColors.resetClouds();
               lotrbiome.biomeColors.resetFog();
            }
         }

         this.balrogFactor = (float)this.prevBalrogNearTick + (float)(this.balrogNearTick - this.prevBalrogNearTick) * renderTick;
         this.balrogFactor /= 100.0F;
         if (this.balrogFactor > 0.0F) {
            balrogOpacityStart = 0.98F;
            balrogOpacityEnd = 0.75F;
            fogStart -= fogStart * this.balrogFactor * balrogOpacityStart;
            fogEnd -= fogEnd * this.balrogFactor * balrogOpacityEnd;
         }

         GL11.glFogf(2915, fogStart);
         GL11.glFogf(2916, fogEnd);
      }

   }

   @SubscribeEvent
   public void onFogColors(FogColors event) {
      Minecraft mc = Minecraft.func_71410_x();
      World world = mc.field_71441_e;
      WorldProvider provider = world.field_73011_w;
      if (provider instanceof LOTRWorldProvider) {
         float[] rgb = new float[]{event.red, event.green, event.blue};
         rgb = ((LOTRWorldProvider)provider).handleFinalFogColors(event.entity, event.renderPartialTicks, rgb);
         event.red = rgb[0];
         event.green = rgb[1];
         event.blue = rgb[2];
      }

      if (this.balrogFactor > 0.0F) {
         int shadowColor = 1114112;
         float[] rgb = new float[]{event.red, event.green, event.blue};
         rgb = LOTRColorUtil.lerpColors(rgb, shadowColor, this.balrogFactor);
         event.red = rgb[0];
         event.green = rgb[1];
         event.blue = rgb[2];
      }

   }

   private boolean isGamePaused(Minecraft mc) {
      return mc.func_71356_B() && mc.field_71462_r != null && mc.field_71462_r.func_73868_f() && !mc.func_71401_C().func_71344_c();
   }

   private void renderOverlay(float[] rgb, float alpha, Minecraft mc, ResourceLocation texture) {
      ScaledResolution resolution = new ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
      int width = resolution.func_78326_a();
      int height = resolution.func_78328_b();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      if (rgb != null) {
         GL11.glColor4f(rgb[0], rgb[1], rgb[2], alpha);
      } else {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
      }

      GL11.glDisable(3008);
      if (texture != null) {
         mc.func_110434_K().func_110577_a(texture);
      } else {
         GL11.glDisable(3553);
      }

      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78374_a(0.0D, (double)height, -90.0D, 0.0D, 1.0D);
      tessellator.func_78374_a((double)width, (double)height, -90.0D, 1.0D, 1.0D);
      tessellator.func_78374_a((double)width, 0.0D, -90.0D, 1.0D, 0.0D);
      tessellator.func_78374_a(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
      tessellator.func_78381_a();
      if (texture == null) {
         GL11.glEnable(3553);
      }

      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glEnable(3008);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderOverlayWithVerticalGradients(float[] rgbEdge, float[] rgbCentre, float alphaEdge, float alphaCentre, Minecraft mc) {
      ScaledResolution resolution = new ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
      int width = resolution.func_78326_a();
      int height = resolution.func_78328_b();
      int heightThird = height / 3;
      int heightTwoThirds = height * 2 / 3;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glDisable(3008);
      GL11.glDisable(3553);
      GL11.glShadeModel(7425);
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78369_a(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
      tessellator.func_78377_a(0.0D, (double)heightThird, -90.0D);
      tessellator.func_78377_a((double)width, (double)heightThird, -90.0D);
      tessellator.func_78369_a(rgbEdge[0], rgbEdge[1], rgbEdge[2], alphaEdge);
      tessellator.func_78377_a((double)width, 0.0D, -90.0D);
      tessellator.func_78377_a(0.0D, 0.0D, -90.0D);
      tessellator.func_78381_a();
      tessellator.func_78382_b();
      tessellator.func_78369_a(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
      tessellator.func_78377_a(0.0D, (double)heightTwoThirds, -90.0D);
      tessellator.func_78377_a((double)width, (double)heightTwoThirds, -90.0D);
      tessellator.func_78369_a(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
      tessellator.func_78377_a((double)width, (double)heightThird, -90.0D);
      tessellator.func_78377_a(0.0D, (double)heightThird, -90.0D);
      tessellator.func_78381_a();
      tessellator.func_78382_b();
      tessellator.func_78369_a(rgbEdge[0], rgbEdge[1], rgbEdge[2], alphaEdge);
      tessellator.func_78377_a(0.0D, (double)height, -90.0D);
      tessellator.func_78377_a((double)width, (double)height, -90.0D);
      tessellator.func_78369_a(rgbCentre[0], rgbCentre[1], rgbCentre[2], alphaCentre);
      tessellator.func_78377_a((double)width, (double)heightTwoThirds, -90.0D);
      tessellator.func_78377_a(0.0D, (double)heightTwoThirds, -90.0D);
      tessellator.func_78381_a();
      GL11.glShadeModel(7424);
      GL11.glEnable(3553);
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glEnable(3008);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderAlignment(Minecraft mc, float f) {
      EntityPlayer entityplayer = mc.field_71439_g;
      LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
      LOTRFaction viewingFac = pd.getViewingFaction();
      ScaledResolution resolution = new ScaledResolution(mc, mc.field_71443_c, mc.field_71440_d);
      int width = resolution.func_78326_a();
      int height = resolution.func_78328_b();
      this.alignmentXBase = width / 2 + LOTRConfig.alignmentXOffset;
      this.alignmentYBase = 4 + LOTRConfig.alignmentYOffset;
      if (isBossActive()) {
         this.alignmentYBase += 20;
      }

      if (watchedInvasion.isActive()) {
         this.alignmentYBase += 20;
      }

      if (this.firstAlignmentRender) {
         LOTRAlignmentTicker.updateAll(entityplayer, true);
         this.alignmentXPrev = this.alignmentXCurrent = this.alignmentXBase;
         this.alignmentYPrev = this.alignmentYCurrent = -20;
         this.firstAlignmentRender = false;
      }

      float alignmentXF = (float)this.alignmentXPrev + (float)(this.alignmentXCurrent - this.alignmentXPrev) * f;
      float alignmentYF = (float)this.alignmentYPrev + (float)(this.alignmentYCurrent - this.alignmentYPrev) * f;
      boolean text = this.alignmentYCurrent == this.alignmentYBase;
      float alignment = LOTRAlignmentTicker.forFaction(viewingFac).getInterpolatedAlignment(f);
      renderAlignmentBar(alignment, false, viewingFac, alignmentXF, alignmentYF, text, text, text, false);
      if (alignDrainTick > 0 && text) {
         float alpha = 1.0F;
         int fadeTick = 20;
         if (alignDrainTick < fadeTick) {
            alpha = (float)alignDrainTick / (float)fadeTick;
         }

         renderAlignmentDrain(mc, (int)alignmentXF - 155, (int)alignmentYF + 2, alignDrainNum, alpha);
      }

   }

   public static void renderAlignmentBar(float alignment, boolean isOtherPlayer, LOTRFaction faction, float x, float y, boolean renderFacName, boolean renderValue, boolean renderLimits, boolean renderLimitValues) {
      Minecraft mc = Minecraft.func_71410_x();
      EntityPlayer entityplayer = mc.field_71439_g;
      LOTRPlayerData clientPD = LOTRLevelData.getData((EntityPlayer)entityplayer);
      LOTRFactionRank rank = faction.getRank(alignment);
      boolean pledged = clientPD.isPledgedTo(faction);
      LOTRAlignmentTicker ticker = LOTRAlignmentTicker.forFaction(faction);
      float alignMin = 0.0F;
      float alignMax = 0.0F;
      LOTRFactionRank rankMin = null;
      LOTRFactionRank rankMax = null;
      float pastRankMultiplier = 10.0F;
      float firstRankAlign;
      if (!rank.isDummyRank()) {
         alignMin = rank.alignment;
         rankMin = rank;
         LOTRFactionRank nextRank = faction.getRankAbove(rank);
         if (nextRank != null && !nextRank.isDummyRank() && nextRank != rank) {
            alignMax = nextRank.alignment;
            rankMax = nextRank;
         } else {
            alignMax = rank.alignment * 10.0F;

            for(rankMax = rank; alignment >= alignMax; alignMax *= 10.0F) {
               alignMin = alignMax;
            }
         }
      } else {
         LOTRFactionRank firstRank = faction.getFirstRank();
         if (firstRank != null && !firstRank.isDummyRank()) {
            firstRankAlign = firstRank.alignment;
         } else {
            firstRankAlign = 10.0F;
         }

         if (Math.abs(alignment) < firstRankAlign) {
            alignMin = -firstRankAlign;
            alignMax = firstRankAlign;
            rankMin = LOTRFactionRank.RANK_ENEMY;
            rankMax = firstRank != null && !firstRank.isDummyRank() ? firstRank : LOTRFactionRank.RANK_NEUTRAL;
         } else if (alignment < 0.0F) {
            alignMax = -firstRankAlign;
            alignMin = alignMax * 10.0F;

            for(rankMin = rankMax = LOTRFactionRank.RANK_ENEMY; alignment <= alignMin; alignMin = alignMax * 10.0F) {
               alignMax *= 10.0F;
            }
         } else {
            alignMin = firstRankAlign;
            alignMax = firstRankAlign * 10.0F;

            for(rankMin = rankMax = LOTRFactionRank.RANK_NEUTRAL; alignment >= alignMax; alignMax *= 10.0F) {
               alignMin = alignMax;
            }
         }
      }

      firstRankAlign = (alignment - alignMin) / (alignMax - alignMin);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      mc.func_110434_K().func_110577_a(LOTRClientProxy.alignmentTexture);
      int barWidth = 232;
      int barHeight = 14;
      int activeBarWidth = 220;
      float[] factionColors = faction.getFactionRGB();
      GL11.glColor4f(factionColors[0], factionColors[1], factionColors[2], 1.0F);
      drawTexturedModalRect((double)(x - (float)(barWidth / 2)), (double)y, 0, 14, barWidth, barHeight);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      drawTexturedModalRect((double)(x - (float)(barWidth / 2)), (double)y, 0, 0, barWidth, barHeight);
      float ringProgressAdj = (firstRankAlign - 0.5F) * 2.0F;
      int ringSize = 16;
      float ringX = x - (float)(ringSize / 2) + ringProgressAdj * (float)activeBarWidth / 2.0F;
      float ringY = y + (float)(barHeight / 2) - (float)(ringSize / 2);
      int flashTick = ticker.flashTick;
      if (pledged) {
         drawTexturedModalRect((double)ringX, (double)ringY, 16 * Math.round((float)(flashTick / 3)), 212, ringSize, ringSize);
      } else {
         drawTexturedModalRect((double)ringX, (double)ringY, 16 * Math.round((float)(flashTick / 3)), 36, ringSize, ringSize);
      }

      if (faction.isPlayableAlignmentFaction()) {
         float alpha = 0.0F;
         boolean definedZone = false;
         if (faction.inControlZone(entityplayer)) {
            alpha = 1.0F;
            definedZone = faction.inDefinedControlZone(entityplayer);
         } else {
            alpha = faction.getControlZoneAlignmentMultiplier(entityplayer);
            definedZone = true;
         }

         if (alpha > 0.0F) {
            int arrowSize = 14;
            int y0 = definedZone ? 60 : 88;
            int y1 = definedZone ? 74 : 102;
            GL11.glEnable(3042);
            OpenGlHelper.func_148821_a(770, 771, 1, 0);
            GL11.glColor4f(factionColors[0], factionColors[1], factionColors[2], alpha);
            drawTexturedModalRect((double)(x - (float)(barWidth / 2) - (float)arrowSize), (double)y, 0, y1, arrowSize, arrowSize);
            drawTexturedModalRect((double)(x + (float)(barWidth / 2)), (double)y, arrowSize, y1, arrowSize, arrowSize);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
            drawTexturedModalRect((double)(x - (float)(barWidth / 2) - (float)arrowSize), (double)y, 0, y0, arrowSize, arrowSize);
            drawTexturedModalRect((double)(x + (float)(barWidth / 2)), (double)y, arrowSize, y0, arrowSize, arrowSize);
            GL11.glDisable(3042);
         }
      }

      FontRenderer fr = mc.field_71466_p;
      int textX = Math.round(x);
      int textY = Math.round(y + (float)barHeight + 4.0F);
      int numericalTick;
      String alignS;
      if (renderLimits) {
         alignS = rankMin.getShortNameWithGender(clientPD);
         String sMax = rankMax.getShortNameWithGender(clientPD);
         if (renderLimitValues) {
            alignS = StatCollector.func_74837_a("lotr.gui.factions.alignment.limits", new Object[]{alignS, LOTRAlignmentValues.formatAlignForDisplay(alignMin)});
            sMax = StatCollector.func_74837_a("lotr.gui.factions.alignment.limits", new Object[]{sMax, LOTRAlignmentValues.formatAlignForDisplay(alignMax)});
         }

         numericalTick = barWidth / 2 - 6;
         int xMin = Math.round(x - (float)numericalTick);
         int xMax = Math.round(x + (float)numericalTick);
         GL11.glPushMatrix();
         GL11.glScalef(0.5F, 0.5F, 0.5F);
         drawAlignmentText(fr, xMin * 2 - fr.func_78256_a(alignS) / 2, textY * 2, alignS, 1.0F);
         drawAlignmentText(fr, xMax * 2 - fr.func_78256_a(sMax) / 2, textY * 2, sMax, 1.0F);
         GL11.glPopMatrix();
      }

      if (renderFacName) {
         alignS = faction.factionName();
         drawAlignmentText(fr, textX - fr.func_78256_a(alignS) / 2, textY, alignS, 1.0F);
      }

      if (renderValue) {
         numericalTick = ticker.numericalTick;
         float alignAlpha;
         if (numericalTick > 0) {
            alignS = LOTRAlignmentValues.formatAlignForDisplay(alignment);
            alignAlpha = LOTRFunctions.triangleWave((float)numericalTick, 0.7F, 1.0F, 30.0F);
            int fadeTick = 15;
            if (numericalTick < fadeTick) {
               alignAlpha *= (float)numericalTick / (float)fadeTick;
            }
         } else {
            alignS = rank.getShortNameWithGender(clientPD);
            alignAlpha = 1.0F;
         }

         GL11.glEnable(3042);
         OpenGlHelper.func_148821_a(770, 771, 1, 0);
         drawAlignmentText(fr, textX - fr.func_78256_a(alignS) / 2, textY + fr.field_78288_b + 3, alignS, alignAlpha);
         GL11.glDisable(3042);
      }

   }

   public static void renderAlignmentDrain(Minecraft mc, int x, int y, int numFactions) {
      renderAlignmentDrain(mc, x, y, numFactions, 1.0F);
   }

   public static void renderAlignmentDrain(Minecraft mc, int x, int y, int numFactions, float alpha) {
      GL11.glEnable(3042);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
      mc.func_110434_K().func_110577_a(LOTRClientProxy.alignmentTexture);
      drawTexturedModalRect((double)x, (double)y, 0, 128, 16, 16);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      String s = "-" + numFactions;
      FontRenderer fr = mc.field_71466_p;
      drawBorderedText(fr, x + 8 - fr.func_78256_a(s) / 2, y + 8 - fr.field_78288_b / 2, s, 16777215, alpha);
      GL11.glDisable(3042);
   }

   public static void drawTexturedModalRect(double x, double y, int u, int v, int width, int height) {
      float f = 0.00390625F;
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78374_a(x + 0.0D, y + (double)height, 0.0D, (double)((float)(u + 0) * f), (double)((float)(v + height) * f));
      tessellator.func_78374_a(x + (double)width, y + (double)height, 0.0D, (double)((float)(u + width) * f), (double)((float)(v + height) * f));
      tessellator.func_78374_a(x + (double)width, y + 0.0D, 0.0D, (double)((float)(u + width) * f), (double)((float)(v + 0) * f));
      tessellator.func_78374_a(x + 0.0D, y + 0.0D, 0.0D, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f));
      tessellator.func_78381_a();
   }

   public static void drawAlignmentText(FontRenderer f, int x, int y, String s, float alphaF) {
      drawBorderedText(f, x, y, s, 16772620, alphaF);
   }

   public static void drawConquestText(FontRenderer f, int x, int y, String s, boolean cleanse, float alphaF) {
      drawBorderedText(f, x, y, s, cleanse ? 16773846 : 14833677, alphaF);
   }

   public static void drawBorderedText(FontRenderer f, int x, int y, String s, int color, float alphaF) {
      int alpha = (int)(alphaF * 255.0F);
      alpha = MathHelper.func_76125_a(alpha, 4, 255);
      alpha <<= 24;
      f.func_78276_b(s, x - 1, y - 1, 0 | alpha);
      f.func_78276_b(s, x, y - 1, 0 | alpha);
      f.func_78276_b(s, x + 1, y - 1, 0 | alpha);
      f.func_78276_b(s, x + 1, y, 0 | alpha);
      f.func_78276_b(s, x + 1, y + 1, 0 | alpha);
      f.func_78276_b(s, x, y + 1, 0 | alpha);
      f.func_78276_b(s, x - 1, y + 1, 0 | alpha);
      f.func_78276_b(s, x - 1, y, 0 | alpha);
      f.func_78276_b(s, x, y, color | alpha);
   }

   public void onFrostDamage() {
      this.frostTick = 80;
   }

   public void onBurnDamage() {
      this.burnTick = 40;
   }

   public void updateDate() {
      this.newDate = 200;
   }

   public float getWightLookFactor() {
      float f = (float)this.prevWightLookTick + (float)(this.wightLookTick - this.prevWightLookTick) * renderTick;
      f /= 100.0F;
      return f;
   }

   private static boolean isBossActive() {
      return BossStatus.field_82827_c != null && BossStatus.field_82826_b > 0;
   }
}
