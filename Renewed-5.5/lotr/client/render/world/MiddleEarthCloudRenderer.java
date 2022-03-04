package lotr.client.render.world;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Random;
import lotr.client.render.ProjectionUtil;
import lotr.common.LOTRMod;
import lotr.common.config.LOTRConfig;
import lotr.common.time.MiddleEarthCalendar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.client.ICloudRenderHandler;

public class MiddleEarthCloudRenderer implements ICloudRenderHandler {
   private static final ResourceLocation CLOUDS_LOW = new ResourceLocation("lotr", "textures/sky/clouds_low.png");
   private static final ResourceLocation CLOUDS_MID = new ResourceLocation("lotr", "textures/sky/clouds_mid.png");
   private static final ResourceLocation CLOUDS_HIGH = new ResourceLocation("lotr", "textures/sky/clouds_high.png");
   private static final Random CLOUD_RNG = new Random(81747493362629326L);
   private final MiddleEarthCloudRenderer.CloudProperty cloudOpacitySeed = new MiddleEarthCloudRenderer.CloudProperty(233591206262L, 0.0F, 1.0F, 0.1F);
   private final MiddleEarthCloudRenderer.CloudProperty cloudSpeed = new MiddleEarthCloudRenderer.CloudProperty(6283905602629L, 0.0F, 0.5F, 0.001F);
   private final MiddleEarthCloudRenderer.CloudProperty cloudAngle = new MiddleEarthCloudRenderer.CloudProperty(360360635650636L, 0.0F, 6.2831855F, 0.01F);
   private double cloudPosXPre;
   private double cloudPosX;
   private double cloudPosZPre;
   private double cloudPosZ;

   public void updateClouds(World world) {
      this.cloudOpacitySeed.update(world);
      this.cloudSpeed.update(world);
      this.cloudAngle.update(world);
      float angle = this.cloudAngle.getValue(1.0F);
      float speed = this.cloudSpeed.getValue(1.0F);
      this.cloudPosXPre = this.cloudPosX;
      this.cloudPosX += (double)(MathHelper.func_76134_b(angle) * speed);
      this.cloudPosZPre = this.cloudPosZ;
      this.cloudPosZ += (double)(MathHelper.func_76126_a(angle) * speed);
   }

   public void resetClouds() {
      this.cloudOpacitySeed.reset();
      this.cloudSpeed.reset();
      this.cloudAngle.reset();
   }

   public void render(int ticks, float partialTicks, MatrixStack matStack, ClientWorld world, Minecraft mc, double viewEntityX, double viewEntityY, double viewEntityZ) {
      world.func_217381_Z().func_76320_a("lotrClouds");
      LOTRDimensionRenderInfo dimensionRenderInfo = (LOTRDimensionRenderInfo)world.func_239132_a_();
      float cloudHeight = dimensionRenderInfo.func_239213_a_();
      if (!Float.isNaN(cloudHeight)) {
         BlockPos viewPos = mc.field_71460_t.func_215316_n().func_216780_d();
         float cloudOpacity = this.getCloudOpacity(world, viewPos, partialTicks);
         cloudOpacity *= 1.0F - LOTRMod.PROXY.getCurrentSandstormFogStrength();
         if (cloudOpacity > 0.0F) {
            int configCloudRange = (Integer)LOTRConfig.CLIENT.cloudRange.get();
            int farCloudRange = configCloudRange * 3;
            Matrix4f projectMatrix = ProjectionUtil.getProjection(mc, partialTicks, (float)farCloudRange);
            RenderSystem.matrixMode(5889);
            RenderSystem.pushMatrix();
            RenderSystem.loadIdentity();
            RenderSystem.multMatrix(projectMatrix);
            RenderSystem.matrixMode(5888);
            RenderSystem.pushMatrix();
            RenderSystem.loadIdentity();
            matStack.func_227860_a_();
            RenderSystem.multMatrix(matStack.func_227866_c_().func_227870_a_());
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.enableDepthTest();
            RenderSystem.enableAlphaTest();
            RenderSystem.alphaFunc(516, 0.01F);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            Vector3d cloudColor = this.getCloudColor(world, viewPos, partialTicks);
            this.renderCloudLayer(mc, partialTicks, CLOUDS_LOW, cloudColor, cloudOpacity * 0.9F, cloudHeight, configCloudRange, 1.0D);
            if (Minecraft.func_71375_t()) {
               this.renderCloudLayer(mc, partialTicks, CLOUDS_MID, cloudColor, cloudOpacity * 0.6F, cloudHeight + 50.0F, configCloudRange, 0.5D);
               this.renderCloudLayer(mc, partialTicks, CLOUDS_HIGH, cloudColor, cloudOpacity * 0.7F, cloudHeight + 500.0F, farCloudRange, 0.25D);
            }

            FogRenderer.func_228370_a_();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableCull();
            RenderSystem.depthMask(true);
            RenderSystem.defaultAlphaFunc();
            RenderSystem.disableBlend();
            RenderSystem.matrixMode(5889);
            RenderSystem.popMatrix();
            matStack.func_227865_b_();
            RenderSystem.matrixMode(5888);
            RenderSystem.popMatrix();
         }
      }

      world.func_217381_Z().func_76319_b();
   }

   private void renderCloudLayer(Minecraft mc, float partialTicks, ResourceLocation texture, Vector3d cloudColor, float alpha, float layerHeight, int layerRenderRange, double layerSpeed) {
      Tessellator tess = Tessellator.func_178181_a();
      BufferBuilder buf = tess.func_178180_c();
      Vector3d pos = mc.field_71460_t.func_215316_n().func_216785_c();
      int scale = 4096;
      double invScaleD = 1.0D / (double)scale;
      RenderSystem.fogMode(9729);
      RenderSystem.fogStart((float)layerRenderRange * 0.9F);
      RenderSystem.fogEnd((float)layerRenderRange);
      RenderSystem.setupNvFogDistance();
      mc.func_110434_K().func_110577_a(texture);
      double posX = pos.field_72450_a;
      double posY = pos.field_72448_b;
      double posZ = pos.field_72449_c;
      double cloudPosXAdd = this.cloudPosXPre + (this.cloudPosX - this.cloudPosXPre) * (double)partialTicks;
      double cloudPosZAdd = this.cloudPosZPre + (this.cloudPosZ - this.cloudPosZPre) * (double)partialTicks;
      cloudPosXAdd *= layerSpeed;
      cloudPosZAdd *= layerSpeed;
      posX += cloudPosXAdd;
      posZ += cloudPosZAdd;
      int x = MathHelper.func_76128_c(posX / (double)scale);
      int z = MathHelper.func_76128_c(posZ / (double)scale);
      double cloudX = posX - (double)(x * scale);
      double cloudZ = posZ - (double)(z * scale);
      float cloudY = layerHeight - (float)posY + 0.33F;
      buf.func_181668_a(7, DefaultVertexFormats.field_227851_o_);
      float r = (float)cloudColor.field_72450_a;
      float g = (float)cloudColor.field_72448_b;
      float b = (float)cloudColor.field_72449_c;
      int interval = layerRenderRange;

      for(int i = -layerRenderRange; i < layerRenderRange; i += interval) {
         for(int k = -layerRenderRange; k < layerRenderRange; k += interval) {
            int xMin = i + 0;
            int xMax = i + interval;
            int zMin = k + 0;
            int zMax = k + interval;
            float uMin = (float)(((double)xMin + cloudX) * invScaleD);
            float uMax = (float)(((double)xMax + cloudX) * invScaleD);
            float vMin = (float)(((double)zMin + cloudZ) * invScaleD);
            float vMax = (float)(((double)zMax + cloudZ) * invScaleD);
            buf.func_225582_a_((double)xMin, (double)cloudY, (double)zMax).func_227885_a_(r, g, b, alpha).func_225583_a_(uMin, vMax).func_181675_d();
            buf.func_225582_a_((double)xMax, (double)cloudY, (double)zMax).func_227885_a_(r, g, b, alpha).func_225583_a_(uMax, vMax).func_181675_d();
            buf.func_225582_a_((double)xMax, (double)cloudY, (double)zMin).func_227885_a_(r, g, b, alpha).func_225583_a_(uMax, vMin).func_181675_d();
            buf.func_225582_a_((double)xMin, (double)cloudY, (double)zMin).func_227885_a_(r, g, b, alpha).func_225583_a_(uMin, vMin).func_181675_d();
         }
      }

      tess.func_78381_a();
   }

   private Vector3d getCloudColor(ClientWorld world, BlockPos viewPos, float partialTicks) {
      return ((LOTRDimensionRenderInfo)world.func_239132_a_()).getBlendedCompleteCloudColor(world, viewPos, partialTicks);
   }

   private float getCloudOpacity(ClientWorld world, BlockPos viewPos, float partialTicks) {
      float opacitySeed = this.cloudOpacitySeed.getValue(partialTicks);
      float coverageHere = ((LOTRDimensionRenderInfo)world.func_239132_a_()).getCloudCoverage(world, viewPos, partialTicks);
      float maxOpacityAtFullCoverage = 1.0F;
      float maxOpacity = MathHelper.func_219799_g(coverageHere, 0.5F, maxOpacityAtFullCoverage);
      float minOpacityAtFullCoverage = 0.2F;
      float x0 = 0.0F;
      float x1 = 1.0F;
      float dx = x1 - x0;
      float gradientAtFullCoverage = (maxOpacityAtFullCoverage - minOpacityAtFullCoverage) / dx;
      float xInterceptAtFullCoverage = -minOpacityAtFullCoverage / gradientAtFullCoverage;
      float xIntercept = MathHelper.func_219799_g(coverageHere, x1, xInterceptAtFullCoverage);
      float gradient = (maxOpacity - 0.0F) / Math.max(1.0F - xIntercept, 1.0E-7F);
      float opacity = gradient * (opacitySeed - xIntercept);
      return Math.max(opacity, 0.0F);
   }

   private class CloudProperty {
      private final long baseSeed;
      private float currentDayValue;
      private float value;
      private float prevValue;
      private final float minValue;
      private final float maxValue;
      private final float interval;

      public CloudProperty(long l, float min, float max, float i) {
         this.baseSeed = l;
         this.value = -1.0F;
         this.minValue = min;
         this.maxValue = max;
         this.interval = i;
      }

      public void reset() {
         this.value = -1.0F;
      }

      public float getValue(float f) {
         return this.prevValue + (this.value - this.prevValue) * f;
      }

      public void update(World world) {
         this.currentDayValue = this.getCurrentDayValue(world);
         if (this.value == -1.0F) {
            this.prevValue = this.value = this.currentDayValue;
         } else {
            this.prevValue = this.value;
            if (this.value > this.currentDayValue) {
               this.value -= this.interval;
               this.value = Math.max(this.value, this.currentDayValue);
            } else if (this.value < this.currentDayValue) {
               this.value += this.interval;
               this.value = Math.min(this.value, this.currentDayValue);
            }
         }

      }

      private float getCurrentDayValue(World world) {
         int day = MiddleEarthCalendar.currentDay;
         long seed = (long)day * this.baseSeed + (long)day + 83025820626792L;
         MiddleEarthCloudRenderer.CLOUD_RNG.setSeed(seed);
         float f = MathHelper.func_151240_a(MiddleEarthCloudRenderer.CLOUD_RNG, this.minValue, this.maxValue);
         return f;
      }
   }
}
