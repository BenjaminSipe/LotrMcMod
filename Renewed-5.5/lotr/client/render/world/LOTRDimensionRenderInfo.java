package lotr.client.render.world;

import lotr.common.LOTRMod;
import lotr.common.config.LOTRConfig;
import lotr.common.init.LOTRBiomes;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.client.world.DimensionRenderInfo.FogType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public abstract class LOTRDimensionRenderInfo extends DimensionRenderInfo {
   private int skyR;
   private int skyG;
   private int skyB;
   private double cloudR;
   private double cloudG;
   private double cloudB;
   private static final float PINKISH_CLOUDS_BEGIN_BRIGHT = 0.98F;
   private static final float PINKISH_CLOUDS_MAX_BRIGHT = 0.8F;
   private static final float PINKISH_CLOUDS_MAX_DARK = 0.5F;
   private static final float PINKISH_CLOUDS_BEGIN_DARK = 0.2F;
   private static final float PINKISH_CLOUDS_BRIGHT_MIN = 0.2F;
   private static final double[] PINKISH_CLOUDS_RGB_MULTIPLIER = new double[]{1.07D, 0.85D, 1.07D};

   public LOTRDimensionRenderInfo(float cloudHeight, boolean isSkyColored, FogType fogType) {
      super(cloudHeight, isSkyColored, fogType, false, false);
      if ((Boolean)LOTRConfig.CLIENT.modSky.get()) {
         this.setSkyRenderHandler(new MiddleEarthSkyRenderer());
      }

      if ((Boolean)LOTRConfig.CLIENT.modClouds.get()) {
         this.setCloudRenderHandler(new MiddleEarthCloudRenderer());
      }

      if ((Boolean)LOTRConfig.CLIENT.newWeatherRendering.get()) {
         this.setWeatherRenderHandler(new MiddleEarthWeatherRenderer());
      }

      this.setWeatherParticleRenderHandler(new MiddleEarthWeatherParticleRenderHandler());
   }

   private World getClientWorld() {
      return LOTRMod.PROXY.getClientWorld();
   }

   public Vector3d func_230494_a_(Vector3d fogRgb, float partialTicks) {
      return fogRgb.func_216372_d((double)(partialTicks * 0.94F + 0.06F), (double)(partialTicks * 0.94F + 0.06F), (double)(partialTicks * 0.91F + 0.09F));
   }

   public boolean func_230493_a_(int x, int z) {
      World world = this.getClientWorld();
      Biome biome = world.func_226691_t_(new BlockPos(x, 0, z));
      return LOTRBiomes.getWrapperFor(biome, world).isFoggy();
   }

   private int getBiomeBlendDistance() {
      Minecraft mc = Minecraft.func_71410_x();
      GameSettings settings = mc.field_71474_y;
      int distance = 0;
      if (Minecraft.func_71375_t()) {
         distance = Math.min(settings.field_151451_c * 2, 36);
      }

      return distance;
   }

   private int getBlendedBiomeSkyColor(World world, BlockPos pos, float partialTicks) {
      int distance = this.getBiomeBlendDistance();
      this.skyR = this.skyG = this.skyB = 0;
      int count = 0;

      for(int x = -distance; x <= distance; ++x) {
         for(int z = -distance; z <= distance; ++z) {
            Biome biome = world.func_226691_t_(pos.func_177982_a(x, 0, z));
            int skyHere = biome.func_225529_c_();
            this.skyR += skyHere >> 16 & 255;
            this.skyG += skyHere >> 8 & 255;
            this.skyB += skyHere & 255;
            ++count;
         }
      }

      this.skyR /= count;
      this.skyG /= count;
      this.skyB /= count;
      return this.skyR << 16 | this.skyG << 8 | this.skyB;
   }

   public Vector3d getBlendedCompleteSkyColor(ClientWorld world, BlockPos pos, float partialTicks) {
      float celestialAngleRadians = world.func_72929_e(partialTicks);
      float dayBright = MathHelper.func_76134_b(celestialAngleRadians) * 2.0F + 0.5F;
      dayBright = MathHelper.func_76131_a(dayBright, 0.0F, 1.0F);
      int biomeSky = this.getBlendedBiomeSkyColor(world, pos, partialTicks);
      float r = (float)(biomeSky >> 16 & 255) / 255.0F;
      float g = (float)(biomeSky >> 8 & 255) / 255.0F;
      float b = (float)(biomeSky & 255) / 255.0F;
      r *= dayBright;
      g *= dayBright;
      b *= dayBright;
      float rain = world.func_72867_j(partialTicks);
      float thunder;
      float thunderLerp;
      if (rain > 0.0F) {
         thunder = (r * 0.3F + g * 0.59F + b * 0.11F) * 0.6F;
         thunderLerp = 1.0F - rain * 0.75F;
         r = r * thunderLerp + thunder * (1.0F - thunderLerp);
         g = g * thunderLerp + thunder * (1.0F - thunderLerp);
         b = b * thunderLerp + thunder * (1.0F - thunderLerp);
      }

      thunder = world.func_72819_i(partialTicks);
      float lightningFlashF;
      if (thunder > 0.0F) {
         thunderLerp = (r * 0.3F + g * 0.59F + b * 0.11F) * 0.2F;
         lightningFlashF = 1.0F - thunder * 0.75F;
         r = r * lightningFlashF + thunderLerp * (1.0F - lightningFlashF);
         g = g * lightningFlashF + thunderLerp * (1.0F - lightningFlashF);
         b = b * lightningFlashF + thunderLerp * (1.0F - lightningFlashF);
      }

      int lightningFlash = world.func_228332_n_();
      if (lightningFlash > 0) {
         lightningFlashF = (float)lightningFlash - partialTicks;
         lightningFlashF = Math.min(lightningFlashF, 1.0F);
         lightningFlashF *= 0.45F;
         r = r * (1.0F - lightningFlashF) + 0.8F * lightningFlashF;
         g = g * (1.0F - lightningFlashF) + 0.8F * lightningFlashF;
         b = b * (1.0F - lightningFlashF) + 1.0F * lightningFlashF;
      }

      return new Vector3d((double)r, (double)g, (double)b);
   }

   public float getSkyFeatureBrightness(World world, BlockPos pos, float partialTicks) {
      int distance = this.getBiomeBlendDistance();
      float totalBrightness = 0.0F;
      int count = 0;

      for(int x = -distance; x <= distance; ++x) {
         for(int z = -distance; z <= distance; ++z) {
            Biome biome = world.func_226691_t_(pos.func_177982_a(x, 0, z));
            boolean hasSky = LOTRBiomes.getWrapperFor(biome, world).hasSkyFeatures();
            float skyBr = hasSky ? 1.0F : 0.0F;
            totalBrightness += skyBr;
            ++count;
         }
      }

      totalBrightness /= (float)count;
      return totalBrightness;
   }

   public Vector3d getBlendedCompleteCloudColor(ClientWorld world, BlockPos pos, float partialTicks) {
      int distance = this.getBiomeBlendDistance();
      Vector3d clouds = this.getBaseCloudColor(world, partialTicks);
      this.cloudR = this.cloudG = this.cloudB = 0.0D;
      int count = 0;

      for(int x = -distance; x <= distance; ++x) {
         for(int z = -distance; z <= distance; ++z) {
            Vector3d tempClouds = new Vector3d(clouds.field_72450_a, clouds.field_72448_b, clouds.field_72449_c);
            Biome biome = world.func_226691_t_(pos.func_177982_a(x, 0, z));
            tempClouds = LOTRBiomes.getWrapperFor(biome, world).alterCloudColor(tempClouds);
            this.cloudR += tempClouds.field_72450_a;
            this.cloudG += tempClouds.field_72448_b;
            this.cloudB += tempClouds.field_72449_c;
            ++count;
         }
      }

      this.cloudR /= (double)count;
      this.cloudG /= (double)count;
      this.cloudB /= (double)count;
      return new Vector3d(this.cloudR, this.cloudG, this.cloudB);
   }

   private Vector3d getBaseCloudColor(ClientWorld world, float partialTicks) {
      Vector3d normalClouds = world.func_228328_h_(partialTicks);
      return this.modifyCloudColorForSunriseSunset(normalClouds, world, partialTicks);
   }

   private Vector3d modifyCloudColorForSunriseSunset(Vector3d normalClouds, ClientWorld world, float partialTicks) {
      double[] rgb = new double[]{normalClouds.field_72450_a, normalClouds.field_72448_b, normalClouds.field_72449_c};
      float celestialAngleRadians = world.func_72929_e(partialTicks);
      float dayBright = MathHelper.func_76134_b(celestialAngleRadians) * 2.0F + 0.5F;
      dayBright = MathHelper.func_76131_a(dayBright, 0.0F, 1.0F);
      if (dayBright >= 0.2F && dayBright <= 0.98F) {
         float pinkishness = dayBright >= 0.5F && dayBright <= 0.8F ? 1.0F : (dayBright < 0.5F ? (dayBright - 0.2F) / 0.3F : (0.98F - dayBright) / 0.18F);

         for(int i = 0; i < rgb.length; ++i) {
            double rgbElem = rgb[i];
            rgbElem *= MathHelper.func_219803_d((double)pinkishness, 1.0D, PINKISH_CLOUDS_RGB_MULTIPLIER[i]);
            rgbElem = MathHelper.func_151237_a(rgbElem, 0.0D, 1.0D);
            rgb[i] = rgbElem;
         }
      }

      return new Vector3d(rgb[0], rgb[1], rgb[2]);
   }

   public float getCloudCoverage(World world, BlockPos pos, float partialTicks) {
      int distance = this.getBiomeBlendDistance();
      float cloudCoverage = 0.0F;
      int count = 0;

      for(int x = -distance; x <= distance; ++x) {
         for(int z = -distance; z <= distance; ++z) {
            Biome biome = world.func_226691_t_(pos.func_177982_a(x, 0, z));
            float coverageHere = LOTRBiomes.getWrapperFor(biome, world).getCloudCoverage();
            cloudCoverage += coverageHere;
            ++count;
         }
      }

      cloudCoverage /= (float)count;
      return cloudCoverage;
   }

   public float[] modifyFogIntensity(float farPlane, net.minecraft.client.renderer.FogRenderer.FogType fogType, Entity viewer) {
      int distance = this.getBiomeBlendDistance();
      float fogStart = 0.0F;
      float fogEnd = 0.0F;
      BlockPos viewerPos = viewer.func_233580_cy_();
      int count = 0;

      for(int x = -distance; x <= distance; ++x) {
         for(int z = -distance; z <= distance; ++z) {
            float thisFogStart = 0.0F;
            float thisFogEnd = 0.0F;
            boolean nearFog = this.func_230493_a_(viewerPos.func_177958_n() + x, viewerPos.func_177952_p() + z);
            if (nearFog) {
               thisFogStart = farPlane * 0.05F;
               thisFogEnd = Math.min(farPlane, 192.0F) * 0.5F;
            } else if (fogType == net.minecraft.client.renderer.FogRenderer.FogType.FOG_SKY) {
               thisFogStart = 0.0F;
               thisFogEnd = farPlane;
            } else {
               thisFogStart = farPlane * 0.75F;
               thisFogEnd = farPlane;
            }

            fogStart += thisFogStart;
            fogEnd += thisFogEnd;
            ++count;
         }
      }

      fogStart /= (float)count;
      fogEnd /= (float)count;
      return new float[]{fogStart, fogEnd};
   }
}
