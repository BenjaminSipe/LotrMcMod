package lotr.client.render.world;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Random;
import lotr.common.init.LOTRBiomes;
import lotr.common.world.biome.ExtendedWeatherType;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.biome.LOTRBiomeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.RainType;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraftforge.client.IWeatherRenderHandler;

public class MiddleEarthWeatherRenderer implements IWeatherRenderHandler {
   private static final ResourceLocation RAIN_TEXTURE = new ResourceLocation("lotr", "textures/weather/rain.png");
   private static final ResourceLocation SNOW_TEXTURE = new ResourceLocation("lotr", "textures/weather/snow.png");
   private static final ResourceLocation ASH_TEXTURE = new ResourceLocation("lotr", "textures/weather/ash.png");
   private static final ResourceLocation SANDSTORM_TEXTURE = new ResourceLocation("lotr", "textures/weather/sandstorm.png");
   private static final int RAIN_COORDS_SCALE = 5;
   private static final int RAIN_COORDS_SIZE = 32;
   private static final int RAIN_COORDS_SIZE_SQ = 1024;
   private final float[] rainCoordsX = new float[1024];
   private final float[] rainCoordsZ = new float[1024];

   public MiddleEarthWeatherRenderer() {
      this.setupRainCoords();
   }

   private void setupRainCoords() {
      for(int x = 0; x < 32; ++x) {
         for(int z = 0; z < 32; ++z) {
            float xf = (float)(z - 16);
            float zf = (float)(x - 16);
            float dist = MathHelper.func_76129_c(xf * xf + zf * zf);
            this.rainCoordsX[x << 5 | z] = -zf / dist;
            this.rainCoordsZ[x << 5 | z] = xf / dist;
         }
      }

   }

   public void render(int fullTicks, float partialTicks, ClientWorld world, Minecraft mc, LightTexture lightmap, double xIn, double yIn, double zIn) {
      float f = mc.field_71441_e.func_72867_j(partialTicks);
      if (f > 0.0F) {
         lightmap.func_205109_c();
         int x = MathHelper.func_76128_c(xIn);
         int y = MathHelper.func_76128_c(yIn);
         int z = MathHelper.func_76128_c(zIn);
         Tessellator tess = Tessellator.func_178181_a();
         BufferBuilder buf = tess.func_178180_c();
         RenderSystem.disableCull();
         RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.defaultAlphaFunc();
         int rainRange = 5;
         if (Minecraft.func_71375_t()) {
            rainRange = 10;
         }

         int drawn = -1;
         float tick = (float)fullTicks + partialTicks;
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         Mutable movingPos = new Mutable();

         for(int z1 = z - rainRange; z1 <= z + rainRange; ++z1) {
            for(int x1 = x - rainRange; x1 <= x + rainRange; ++x1) {
               int rainIndex = (z1 - z + 16) * 32 + x1 - x + 16;
               double rainX = (double)this.rainCoordsX[rainIndex] * 0.5D;
               double rainZ = (double)this.rainCoordsZ[rainIndex] * 0.5D;
               movingPos.func_181079_c(x1, 0, z1);
               Biome biome = world.func_226691_t_(movingPos);
               LOTRBiomeWrapper biomeWrapper = LOTRBiomes.getWrapperFor(biome, world);
               RainType rainType = biomeWrapper.getPrecipitationVisually();
               ExtendedWeatherType extWeatherType = biomeWrapper.getExtendedWeatherVisually();
               if (rainType != RainType.NONE || extWeatherType != ExtendedWeatherType.NONE) {
                  int rainHeight = world.func_205770_a(Type.MOTION_BLOCKING, movingPos).func_177956_o();
                  int rainMinY = y - rainRange;
                  int rainMaxY = y + rainRange;
                  if (rainMinY < rainHeight) {
                     rainMinY = rainHeight;
                  }

                  if (rainMaxY < rainHeight) {
                     rainMaxY = rainHeight;
                  }

                  int l2 = rainHeight;
                  if (rainHeight < y) {
                     l2 = y;
                  }

                  if (rainMinY != rainMaxY) {
                     Random rand = new Random((long)(x1 * x1 * 3121 + x1 * 45238971 ^ z1 * z1 * 418711 + z1 * 13761));
                     movingPos.func_181079_c(x1, rainMinY, z1);
                     float f6;
                     float f3;
                     float f8;
                     double d3;
                     double d5;
                     float f5;
                     float f10;
                     int k3;
                     int l3;
                     int i4;
                     int j4;
                     int k4;
                     if (extWeatherType == ExtendedWeatherType.ASHFALL) {
                        if (drawn != 1) {
                           if (drawn >= 0) {
                              tess.func_78381_a();
                           }

                           drawn = 1;
                           mc.func_110434_K().func_110577_a(ASH_TEXTURE);
                           buf.func_181668_a(7, DefaultVertexFormats.field_181704_d);
                        }

                        f6 = -((float)(fullTicks & 511) + partialTicks) / 512.0F;
                        f3 = (float)(rand.nextDouble() * 0.30000001192092896D + (double)tick * 0.003D * (double)((float)rand.nextGaussian()));
                        f8 = (float)(rand.nextDouble() + (double)(tick * (float)rand.nextGaussian()) * 0.001D);
                        d3 = (double)((float)x1 + 0.5F) - xIn;
                        d5 = (double)((float)z1 + 0.5F) - zIn;
                        f5 = MathHelper.func_76133_a(d3 * d3 + d5 * d5) / (float)rainRange;
                        f10 = ((1.0F - f5 * f5) * 0.3F + 0.5F) * f;
                        movingPos.func_181079_c(x1, l2, z1);
                        k3 = WorldRenderer.func_228421_a_(world, movingPos);
                        l3 = k3 >> 16 & '\uffff';
                        i4 = (k3 & '\uffff') * 3;
                        j4 = (l3 * 3 + 240) / 4;
                        k4 = (i4 * 3 + 240) / 4;
                        buf.func_225582_a_((double)x1 - xIn - rainX + 0.5D, (double)rainMaxY - yIn, (double)z1 - zIn - rainZ + 0.5D).func_225583_a_(0.0F + f3, (float)rainMinY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn + rainX + 0.5D, (double)rainMaxY - yIn, (double)z1 - zIn + rainZ + 0.5D).func_225583_a_(1.0F + f3, (float)rainMinY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn + rainX + 0.5D, (double)rainMinY - yIn, (double)z1 - zIn + rainZ + 0.5D).func_225583_a_(1.0F + f3, (float)rainMaxY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn - rainX + 0.5D, (double)rainMinY - yIn, (double)z1 - zIn - rainZ + 0.5D).func_225583_a_(0.0F + f3, (float)rainMaxY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                     } else if (extWeatherType == ExtendedWeatherType.SANDSTORM) {
                        if (drawn != 1) {
                           if (drawn >= 0) {
                              tess.func_78381_a();
                           }

                           drawn = 1;
                           mc.func_110434_K().func_110577_a(SANDSTORM_TEXTURE);
                           buf.func_181668_a(7, DefaultVertexFormats.field_181704_d);
                        }

                        f6 = -((float)(fullTicks & 511) + partialTicks) / 512.0F;
                        f3 = tick * (0.07F + (float)rand.nextGaussian() * 0.01F);
                        f8 = (float)(rand.nextDouble() + (double)(tick * (float)rand.nextGaussian()) * 0.001D);
                        d3 = (double)((float)x1 + 0.5F) - xIn;
                        d5 = (double)((float)z1 + 0.5F) - zIn;
                        f5 = MathHelper.func_76133_a(d3 * d3 + d5 * d5) / (float)rainRange;
                        f10 = ((1.0F - f5 * f5) * 0.3F + 0.5F) * f;
                        movingPos.func_181079_c(x1, l2, z1);
                        k3 = WorldRenderer.func_228421_a_(world, movingPos);
                        l3 = k3 >> 16 & '\uffff';
                        i4 = (k3 & '\uffff') * 3;
                        j4 = (l3 * 3 + 240) / 4;
                        k4 = (i4 * 3 + 240) / 4;
                        buf.func_225582_a_((double)x1 - xIn - rainX + 0.5D, (double)rainMaxY - yIn, (double)z1 - zIn - rainZ + 0.5D).func_225583_a_(0.0F + f3, (float)rainMinY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn + rainX + 0.5D, (double)rainMaxY - yIn, (double)z1 - zIn + rainZ + 0.5D).func_225583_a_(1.0F + f3, (float)rainMinY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn + rainX + 0.5D, (double)rainMinY - yIn, (double)z1 - zIn + rainZ + 0.5D).func_225583_a_(1.0F + f3, (float)rainMaxY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn - rainX + 0.5D, (double)rainMinY - yIn, (double)z1 - zIn - rainZ + 0.5D).func_225583_a_(0.0F + f3, (float)rainMaxY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                     } else if (!LOTRBiomeBase.isSnowingVisually(biomeWrapper, world, movingPos)) {
                        if (drawn != 0) {
                           if (drawn >= 0) {
                              tess.func_78381_a();
                           }

                           drawn = 0;
                           mc.func_110434_K().func_110577_a(RAIN_TEXTURE);
                           buf.func_181668_a(7, DefaultVertexFormats.field_181704_d);
                        }

                        int i3 = fullTicks + x1 * x1 * 3121 + x1 * 45238971 + z1 * z1 * 418711 + z1 * 13761 & 31;
                        f3 = -((float)i3 + partialTicks) / 32.0F * (3.0F + rand.nextFloat());
                        double d2 = (double)((float)x1 + 0.5F) - xIn;
                        double d4 = (double)((float)z1 + 0.5F) - zIn;
                        float f4 = MathHelper.func_76133_a(d2 * d2 + d4 * d4) / (float)rainRange;
                        f5 = ((1.0F - f4 * f4) * 0.5F + 0.5F) * f;
                        movingPos.func_181079_c(x1, l2, z1);
                        int j3 = WorldRenderer.func_228421_a_(world, movingPos);
                        buf.func_225582_a_((double)x1 - xIn - rainX + 0.5D, (double)rainMaxY - yIn, (double)z1 - zIn - rainZ + 0.5D).func_225583_a_(0.0F, (float)rainMinY * 0.25F + f3).func_227885_a_(1.0F, 1.0F, 1.0F, f5).func_227886_a_(j3).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn + rainX + 0.5D, (double)rainMaxY - yIn, (double)z1 - zIn + rainZ + 0.5D).func_225583_a_(1.0F, (float)rainMinY * 0.25F + f3).func_227885_a_(1.0F, 1.0F, 1.0F, f5).func_227886_a_(j3).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn + rainX + 0.5D, (double)rainMinY - yIn, (double)z1 - zIn + rainZ + 0.5D).func_225583_a_(1.0F, (float)rainMaxY * 0.25F + f3).func_227885_a_(1.0F, 1.0F, 1.0F, f5).func_227886_a_(j3).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn - rainX + 0.5D, (double)rainMinY - yIn, (double)z1 - zIn - rainZ + 0.5D).func_225583_a_(0.0F, (float)rainMaxY * 0.25F + f3).func_227885_a_(1.0F, 1.0F, 1.0F, f5).func_227886_a_(j3).func_181675_d();
                     } else {
                        if (drawn != 1) {
                           if (drawn >= 0) {
                              tess.func_78381_a();
                           }

                           drawn = 1;
                           mc.func_110434_K().func_110577_a(SNOW_TEXTURE);
                           buf.func_181668_a(7, DefaultVertexFormats.field_181704_d);
                        }

                        f6 = -((float)(fullTicks & 511) + partialTicks) / 512.0F;
                        f3 = (float)(rand.nextDouble() + (double)tick * 0.01D * (double)((float)rand.nextGaussian()));
                        f8 = (float)(rand.nextDouble() + (double)(tick * (float)rand.nextGaussian()) * 0.001D);
                        d3 = (double)((float)x1 + 0.5F) - xIn;
                        d5 = (double)((float)z1 + 0.5F) - zIn;
                        f5 = MathHelper.func_76133_a(d3 * d3 + d5 * d5) / (float)rainRange;
                        f10 = ((1.0F - f5 * f5) * 0.3F + 0.5F) * f;
                        movingPos.func_181079_c(x1, l2, z1);
                        k3 = WorldRenderer.func_228421_a_(world, movingPos);
                        l3 = k3 >> 16 & '\uffff';
                        i4 = (k3 & '\uffff') * 3;
                        j4 = (l3 * 3 + 240) / 4;
                        k4 = (i4 * 3 + 240) / 4;
                        buf.func_225582_a_((double)x1 - xIn - rainX + 0.5D, (double)rainMaxY - yIn, (double)z1 - zIn - rainZ + 0.5D).func_225583_a_(0.0F + f3, (float)rainMinY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn + rainX + 0.5D, (double)rainMaxY - yIn, (double)z1 - zIn + rainZ + 0.5D).func_225583_a_(1.0F + f3, (float)rainMinY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn + rainX + 0.5D, (double)rainMinY - yIn, (double)z1 - zIn + rainZ + 0.5D).func_225583_a_(1.0F + f3, (float)rainMaxY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                        buf.func_225582_a_((double)x1 - xIn - rainX + 0.5D, (double)rainMinY - yIn, (double)z1 - zIn - rainZ + 0.5D).func_225583_a_(0.0F + f3, (float)rainMaxY * 0.25F + f6 + f8).func_227885_a_(1.0F, 1.0F, 1.0F, f10).func_225587_b_(k4, j4).func_181675_d();
                     }
                  }
               }
            }
         }

         if (drawn >= 0) {
            tess.func_78381_a();
         }

         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.defaultAlphaFunc();
         lightmap.func_205108_b();
      }

   }
}
