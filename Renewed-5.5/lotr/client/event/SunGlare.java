package lotr.client.event;

import lotr.common.config.LOTRConfig;
import lotr.common.dim.LOTRDimensionType;
import lotr.common.init.LOTRBiomes;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.biome.Biome;

public class SunGlare {
   private float sunGlare;
   private float prevSunGlare;
   private static final float MAX_OVERSATURATED_GLARE = 1.4F;
   private static final float MIN_BRIGHTNESS_FOR_GLARE = 0.45F;
   private float glareYellowness;
   private static final float BRIGHTNESS_YELLOW_THRESHOLD = 0.75F;
   private static final float BRIGHTNESS_YELLOW_FULL = 0.6F;
   private static final float[] NORMAL_RGB = new float[]{1.0F, 1.0F, 1.0F};
   private static final float[] YELLOW_RGB = new float[]{1.0F, 0.6F, 0.0F};

   public void update(ClientWorld world, Entity viewer) {
      if ((Boolean)LOTRConfig.CLIENT.sunGlare.get() && world.func_230315_m_() instanceof LOTRDimensionType && world.func_230315_m_().func_218272_d()) {
         this.prevSunGlare = this.sunGlare;
         float renderTick = 1.0F;
         RayTraceResult look = this.getBlockRayTrace(viewer, 10000.0D);
         boolean lookingAtSky = look.func_216346_c() == Type.MISS;
         Biome biome = world.func_226691_t_(viewer.func_233580_cy_());
         boolean biomeHasSun = LOTRBiomes.getWrapperFor(biome, world).hasSkyFeatures();
         float celestialAngle = world.func_242415_f(renderTick) * 360.0F - 90.0F;
         float sunYaw = 90.0F;
         float yc = MathHelper.func_76134_b((float)Math.toRadians((double)(-sunYaw - 180.0F)));
         float ys = MathHelper.func_76126_a((float)Math.toRadians((double)(-sunYaw - 180.0F)));
         float pc = -MathHelper.func_76134_b((float)Math.toRadians((double)(-celestialAngle)));
         float ps = MathHelper.func_76126_a((float)Math.toRadians((double)(-celestialAngle)));
         Vector3d sunVec = new Vector3d((double)(ys * pc), (double)ps, (double)(yc * pc));
         Vector3d lookVec = viewer.func_70676_i(renderTick);
         double cos = lookVec.func_72430_b(sunVec) / (lookVec.func_72433_c() * sunVec.func_72433_c());
         float cosThreshold = 0.97F;
         float cQ = ((float)cos - 0.97F) / 0.029999971F;
         cQ = Math.max(cQ, 0.0F);
         float brightness = world.func_228326_g_(renderTick);
         float bQ = (brightness - 0.45F) / 0.55F;
         bQ = Math.max(bQ, 0.0F);
         float brightnessForYellowness = this.getSunBrightnessBasedOnlyOnAngle(world, renderTick);
         this.glareYellowness = (0.75F - brightnessForYellowness) / 0.14999998F;
         this.glareYellowness = MathHelper.func_76131_a(this.glareYellowness, 0.0F, 1.0F);
         float maxGlareNow = cQ * bQ;
         float maxGlareNowWithOversaturation = maxGlareNow * 1.4F;
         if (maxGlareNow > 0.0F && lookingAtSky && !world.func_72896_J() && biomeHasSun) {
            if (this.sunGlare < maxGlareNowWithOversaturation) {
               this.sunGlare += 0.1F * maxGlareNow;
               this.sunGlare = Math.min(this.sunGlare, maxGlareNowWithOversaturation);
            } else if (this.sunGlare > maxGlareNowWithOversaturation) {
               this.sunGlare -= 0.02F;
               this.sunGlare = Math.max(this.sunGlare, maxGlareNowWithOversaturation);
            }
         } else {
            if (this.sunGlare > 0.0F) {
               this.sunGlare -= 0.02F;
            }

            this.sunGlare = Math.max(this.sunGlare, 0.0F);
         }
      } else {
         this.reset();
      }

   }

   private float getSunBrightnessBasedOnlyOnAngle(ClientWorld world, float partialTicks) {
      float f = world.func_242415_f(partialTicks);
      float f1 = 1.0F - (MathHelper.func_76134_b(f * 3.1415927F * 2.0F) * 2.0F + 0.2F);
      f1 = MathHelper.func_76131_a(f1, 0.0F, 1.0F);
      f1 = 1.0F - f1;
      return f1 * 0.8F + 0.2F;
   }

   private RayTraceResult getBlockRayTrace(Entity viewer, double distance) {
      return viewer.func_213324_a(distance, 1.0F, true);
   }

   public void reset() {
      this.prevSunGlare = this.sunGlare = 0.0F;
      this.glareYellowness = 0.0F;
   }

   public float getGlareBrightness(float partialTick) {
      float lerp = this.prevSunGlare + (this.sunGlare - this.prevSunGlare) * partialTick;
      return Math.min(lerp, 1.0F);
   }

   public float[] getGlareColorRGB() {
      float[] rgb = new float[3];

      for(int i = 0; i < rgb.length; ++i) {
         rgb[i] = MathHelper.func_219799_g(this.glareYellowness, NORMAL_RGB[i], YELLOW_RGB[i]);
      }

      return rgb;
   }
}
