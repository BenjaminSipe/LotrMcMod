package lotr.client.render.world;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.client.render.ProjectionUtil;
import lotr.common.LOTRLog;
import lotr.common.init.LOTRBiomes;
import lotr.common.init.LOTRWorldTypes;
import lotr.common.time.MiddleEarthCalendar;
import lotr.common.time.ShireReckoning;
import lotr.common.util.CalendarUtil;
import lotr.common.world.biome.LOTRBiomeBase;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import lotr.common.world.map.NorthernLightsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.RainType;

public class NorthernLightsRenderer {
   private int nlTick;
   private int currentNightNum = -1;
   private float brightnessTonight;
   private float maxNorthTonight;
   private float minNorthTonight;
   private int rainingTick;
   private int rainingTickPrev;
   private static final int rainingChangeTime = 80;
   private boolean atUtumno = false;
   private int utumnoChange = 0;
   private static final int utumnoChangeTime = 200;
   private final Random rand = new Random(54382502529626502L);
   private final Random dateRand = new Random(664292528855626902L);
   private float[] colorTopCurrent;
   private float[] colorMidCurrent;
   private float[] colorBottomCurrent;
   private float[] colorTopNext;
   private float[] colorMidNext;
   private float[] colorBottomNext;
   private int colorChangeTime;
   private int colorChangeTick;
   private int timeUntilColorChange;
   private int utumnoCheckTime;
   private final NorthernLightsRenderer.AuroraCycle wave0 = new NorthernLightsRenderer.AuroraCycle(4.0F, 0.01F, 0.9F);
   private List waveOscillations = new ArrayList();
   private List glowOscillations = new ArrayList();
   private final NorthernLightsRenderer.AuroraCycle glow0 = new NorthernLightsRenderer.AuroraCycle(20.0F, 0.02F, 0.6F);

   public void render(Minecraft mc, ClientWorld world, MatrixStack matStack, float tick) {
      if (mc.field_71474_y.field_151451_c >= 4) {
         IProfiler profiler = world.func_217381_Z();
         float minSun = 0.2F;
         float daylight = (world.func_228326_g_(tick) - minSun) / (1.0F - minSun);
         float maxDaylight = 0.3F;
         float nlBrightness = (1.0F - daylight - (1.0F - maxDaylight)) / maxDaylight;
         if (nlBrightness > 0.0F) {
            float tonight = this.brightnessTonight;
            float utumno = (float)this.utumnoChange / 200.0F;
            tonight += (1.0F - tonight) * utumno;
            if (tonight > 0.0F) {
               nlBrightness *= tonight;
               float northernness = this.getNorthernness(mc, mc.field_175622_Z, world);
               if (northernness > 0.0F) {
                  nlBrightness *= northernness;
                  float raininess = ((float)this.rainingTickPrev + (float)(this.rainingTick - this.rainingTickPrev) * tick) / 80.0F;
                  if (raininess < 1.0F) {
                     nlBrightness *= 1.0F - raininess;
                     nlBrightness *= 0.3F + (1.0F - world.func_72867_j(tick)) * 0.7F;
                     profiler.func_76320_a("aurora");
                     float nlScale = 2000.0F;
                     float nlDistance = (1.0F - northernness) * nlScale * 2.0F;
                     float nlHeight = nlScale * 0.48F;
                     Matrix4f projectMatrix = ProjectionUtil.getProjection(mc, tick, nlScale * 5.0F);
                     RenderSystem.matrixMode(5889);
                     RenderSystem.pushMatrix();
                     RenderSystem.loadIdentity();
                     RenderSystem.multMatrix(projectMatrix);
                     RenderSystem.matrixMode(5888);
                     RenderSystem.pushMatrix();
                     RenderSystem.loadIdentity();
                     matStack.func_227860_a_();
                     matStack.func_227861_a_(0.0D, (double)nlHeight, (double)(-nlDistance));
                     RenderSystem.multMatrix(matStack.func_227866_c_().func_227870_a_());
                     RenderSystem.disableTexture();
                     RenderSystem.disableAlphaTest();
                     RenderSystem.depthMask(false);
                     RenderSystem.enableBlend();
                     RenderSystem.defaultBlendFunc();
                     RenderSystem.alphaFunc(516, 0.01F);
                     RenderSystem.shadeModel(7425);
                     RenderSystem.disableCull();
                     profiler.func_76320_a("sheet");
                     boolean fancy = Minecraft.func_71375_t();
                     this.renderSheet(matStack, fancy, nlScale * -0.5F, nlBrightness * 0.8F, nlScale * 1.0F, nlScale * 0.23F, 0.25502F, tick, profiler);
                     this.renderSheet(matStack, fancy, nlScale * 0.0F, nlBrightness * 1.0F, nlScale * 1.5F, nlScale * 0.27F, 0.15696F, tick, profiler);
                     this.renderSheet(matStack, fancy, nlScale * 0.5F, nlBrightness * 0.8F, nlScale * 1.0F, nlScale * 0.23F, 0.67596F, tick, profiler);
                     profiler.func_76319_b();
                     RenderSystem.enableCull();
                     RenderSystem.shadeModel(7424);
                     RenderSystem.defaultAlphaFunc();
                     RenderSystem.disableBlend();
                     RenderSystem.depthMask(true);
                     RenderSystem.enableAlphaTest();
                     RenderSystem.enableTexture();
                     RenderSystem.matrixMode(5889);
                     RenderSystem.popMatrix();
                     matStack.func_227865_b_();
                     RenderSystem.matrixMode(5888);
                     RenderSystem.popMatrix();
                     profiler.func_76319_b();
                  }
               }
            }
         }
      }
   }

   private float getNorthernness(Minecraft mc, Entity entity, ClientWorld world) {
      if (!LOTRWorldTypes.hasMapFeaturesClientside()) {
         return getBiomeDependentBrightness(mc, entity, world);
      } else {
         float minNorth = this.minNorthTonight;
         float maxNorth = this.maxNorthTonight;
         float northernness = ((float)entity.func_226281_cx_() - minNorth) / (maxNorth - minNorth);
         northernness = MathHelper.func_76131_a(northernness, 0.0F, 1.0F);
         return northernness;
      }
   }

   private static float getBiomeDependentBrightness(Minecraft mc, Entity entity, ClientWorld world) {
      int sampled = 0;
      int total = 0;
      int range = Math.min(mc.field_71474_y.field_151451_c * 2, 36);
      BlockPos entityPos = entity.func_233580_cy_();
      Mutable movingPos = new Mutable();

      for(int x = -range; x <= range; ++x) {
         for(int z = -range; z <= range; ++z) {
            movingPos.func_189533_g(entityPos).func_196234_d(x, 0, z);
            Biome biome = world.func_226691_t_(movingPos);
            if (biome.func_201851_b() == RainType.SNOW) {
               ++total;
            }

            ++sampled;
         }
      }

      return (float)total / (float)sampled;
   }

   private static boolean isRainLayerAt(Entity entity) {
      World world = entity.func_130014_f_();
      BlockPos pos = entity.func_233580_cy_();
      if (!world.func_72896_J()) {
         return false;
      } else {
         Biome biome = world.func_226691_t_(pos);
         if (biome.func_201851_b() == RainType.SNOW) {
            return false;
         } else {
            float temp = LOTRBiomes.getWrapperFor(biome, world).getTemperatureForSnowWeatherRendering(world, pos);
            return !LOTRBiomeBase.isTemperatureSuitableForSnow(temp) && biome.func_201851_b() == RainType.RAIN;
         }
      }
   }

   private void renderSheet(MatrixStack matStack, boolean fancy, float nlDistance, float nlBrightness, float halfWidth, float halfHeight, float tickExtra, float tick, IProfiler profiler) {
      float r1 = this.colorTopCurrent[0];
      float g1 = this.colorTopCurrent[1];
      float b1 = this.colorTopCurrent[2];
      float r2 = this.colorMidCurrent[0];
      float g2 = this.colorMidCurrent[1];
      float b2 = this.colorMidCurrent[2];
      float r3 = this.colorBottomCurrent[0];
      float g3 = this.colorBottomCurrent[1];
      float b3 = this.colorBottomCurrent[2];
      float a1;
      if (this.colorChangeTime > 0) {
         a1 = (float)this.colorChangeTick / (float)this.colorChangeTime;
         a1 = 1.0F - a1;
         r1 = this.colorTopCurrent[0] + (this.colorTopNext[0] - this.colorTopCurrent[0]) * a1;
         g1 = this.colorTopCurrent[1] + (this.colorTopNext[1] - this.colorTopCurrent[1]) * a1;
         b1 = this.colorTopCurrent[2] + (this.colorTopNext[2] - this.colorTopCurrent[2]) * a1;
         r2 = this.colorMidCurrent[0] + (this.colorMidNext[0] - this.colorMidCurrent[0]) * a1;
         g2 = this.colorMidCurrent[1] + (this.colorMidNext[1] - this.colorMidCurrent[1]) * a1;
         b2 = this.colorMidCurrent[2] + (this.colorMidNext[2] - this.colorMidCurrent[2]) * a1;
         r3 = this.colorBottomCurrent[0] + (this.colorBottomNext[0] - this.colorBottomCurrent[0]) * a1;
         g3 = this.colorBottomCurrent[1] + (this.colorBottomNext[1] - this.colorBottomCurrent[1]) * a1;
         b3 = this.colorBottomCurrent[2] + (this.colorBottomNext[2] - this.colorBottomCurrent[2]) * a1;
      }

      a1 = 0.0F;
      float a2 = 0.4F;
      float a3 = 0.8F;
      a1 *= nlBrightness;
      a2 *= nlBrightness;
      a3 *= nlBrightness;
      float fullTick = (float)this.nlTick + tick + tickExtra;
      Tessellator tess = Tessellator.func_178181_a();
      BufferBuilder buf = tess.func_178180_c();
      buf.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      profiler.func_76320_a("vertexLoop");
      int strips = 500;
      if (!fancy) {
         strips = 80;
      }

      for(int l = 0; l < strips; ++l) {
         float t = (float)l / (float)strips;
         float t1 = (float)(l + 1) / (float)strips;
         float a1_here = a1;
         float a2_here = a2;
         float a3_here = a3;
         float fadeEdge = 0.3F;
         float fadePos = Math.min(t, 1.0F - t);
         float randomFade;
         if (fadePos < fadeEdge) {
            randomFade = fadePos / fadeEdge;
            a1_here = a1 * randomFade;
            a2_here = a2 * randomFade;
            a3_here = a3 * randomFade;
         }

         randomFade = 0.5F + this.glowEquation(t, fullTick, tick, fancy) * 0.5F;
         a1_here *= randomFade;
         a2_here *= randomFade;
         a3_here *= randomFade;
         float x0 = -halfWidth + halfWidth * 2.0F * t;
         float x1 = x0 + halfWidth * 2.0F / (float)strips;
         float yMin = -halfHeight;
         float yMid = -halfHeight * 0.3F;
         float maxWaveDisplacement = halfWidth * 0.15F;
         float z0 = nlDistance + this.waveEquation(t, fullTick, tick, fancy) * maxWaveDisplacement;
         float z1 = nlDistance + this.waveEquation(t1, fullTick, tick, fancy) * maxWaveDisplacement;
         float extra = halfHeight * 0.25F;
         buf.func_225582_a_((double)x0, (double)(yMin - extra), (double)z0).func_227885_a_(r3, g3, b3, 0.0F).func_181675_d();
         buf.func_225582_a_((double)x1, (double)(yMin - extra), (double)z1).func_227885_a_(r3, g3, b3, 0.0F).func_181675_d();
         buf.func_225582_a_((double)x1, (double)yMin, (double)z1).func_227885_a_(r3, g3, b3, a3_here).func_181675_d();
         buf.func_225582_a_((double)x0, (double)yMin, (double)z0).func_227885_a_(r3, g3, b3, a3_here).func_181675_d();
         buf.func_225582_a_((double)x0, (double)yMin, (double)z0).func_227885_a_(r3, g3, b3, a3_here).func_181675_d();
         buf.func_225582_a_((double)x1, (double)yMin, (double)z1).func_227885_a_(r3, g3, b3, a3_here).func_181675_d();
         buf.func_225582_a_((double)x1, (double)yMid, (double)z1).func_227885_a_(r2, g2, b2, a2_here).func_181675_d();
         buf.func_225582_a_((double)x0, (double)yMid, (double)z0).func_227885_a_(r2, g2, b2, a2_here).func_181675_d();
         buf.func_225582_a_((double)x0, (double)yMid, (double)z0).func_227885_a_(r2, g2, b2, a2_here).func_181675_d();
         buf.func_225582_a_((double)x1, (double)yMid, (double)z1).func_227885_a_(r2, g2, b2, a2_here).func_181675_d();
         buf.func_225582_a_((double)x1, (double)halfHeight, (double)z1).func_227885_a_(r1, g1, b1, a1_here).func_181675_d();
         buf.func_225582_a_((double)x0, (double)halfHeight, (double)z0).func_227885_a_(r1, g1, b1, a1_here).func_181675_d();
      }

      profiler.func_76319_b();
      profiler.func_76320_a("draw");
      tess.func_78381_a();
      profiler.func_76319_b();
   }

   public void update(Entity viewer) {
      ++this.nlTick;
      World world = viewer.func_130014_f_();
      int effectiveDay = MiddleEarthCalendar.currentDay;
      float daytime = (float)(world.func_72820_D() % 48000L) / 48000.0F;
      if (daytime < 0.25F) {
         --effectiveDay;
      }

      if (effectiveDay != this.currentNightNum) {
         this.currentNightNum = effectiveDay;
         this.dateRand.setSeed((long)this.currentNightNum * 35920558925051L + (long)this.currentNightNum + 83025820626792L);
         ShireReckoning.Month month = ((ShireReckoning.ShireDate)ShireReckoning.INSTANCE.getDate(this.currentNightNum)).month;
         boolean isYule = month == ShireReckoning.Month.YULE_1 || month == ShireReckoning.Month.YULE_2;
         MapSettings loadedMapSettings = MapSettingsManager.clientInstance().getLoadedMapOrLoadDefault(Minecraft.func_71410_x().func_195551_G());
         if (loadedMapSettings == null) {
            LOTRLog.error("No MapSettings instance is loaded on the client! This should not happen and is very bad!");
         }

         NorthernLightsSettings northernLightsSettings = loadedMapSettings.getNorthernLights();
         int fullNorth = northernLightsSettings.getFullNorth_world();
         int minSouth = northernLightsSettings.getStartSouth_world();
         int maxSouth = northernLightsSettings.getFurthestPossibleSouth_world();
         this.maxNorthTonight = (float)fullNorth;
         float southRand = this.dateRand.nextFloat();
         float southRandAmount;
         if (!isYule && southRand >= 0.01F) {
            if (southRand < 0.1F) {
               southRandAmount = MathHelper.func_151240_a(this.dateRand, 0.5F, 0.75F);
            } else if (southRand < 0.5F) {
               southRandAmount = MathHelper.func_151240_a(this.dateRand, 0.25F, 0.5F);
            } else {
               southRandAmount = MathHelper.func_151240_a(this.dateRand, 0.0F, 0.25F);
            }
         } else {
            southRandAmount = MathHelper.func_151240_a(this.dateRand, 0.75F, 1.0F);
         }

         this.minNorthTonight = MathHelper.func_219799_g(southRandAmount, (float)minSouth, (float)maxSouth);
         if (CalendarUtil.isChristmas()) {
            this.minNorthTonight = 1.0E8F;
         }

         float appearChance = 0.5F;
         if (!isYule && effectiveDay != 0 && this.dateRand.nextFloat() >= appearChance) {
            this.brightnessTonight = 0.0F;
         } else {
            this.brightnessTonight = MathHelper.func_151240_a(this.dateRand, 0.4F, 1.0F);
         }
      }

      this.rainingTickPrev = this.rainingTick;
      boolean raining = isRainLayerAt(viewer);
      if (raining) {
         if (this.rainingTick < 80) {
            ++this.rainingTick;
         }
      } else if (this.rainingTick > 0) {
         --this.rainingTick;
      }

      Color[] cs;
      if (this.colorTopCurrent == null) {
         cs = this.generateColorSet();
         this.colorTopCurrent = cs[0].getColorComponents((float[])null);
         this.colorMidCurrent = cs[1].getColorComponents((float[])null);
         this.colorBottomCurrent = cs[2].getColorComponents((float[])null);
      }

      if (this.timeUntilColorChange > 0) {
         --this.timeUntilColorChange;
      } else if (this.rand.nextInt(1200) == 0) {
         cs = this.generateColorSet();
         this.colorTopNext = cs[0].getColorComponents((float[])null);
         this.colorMidNext = cs[1].getColorComponents((float[])null);
         this.colorBottomNext = cs[2].getColorComponents((float[])null);
         this.colorChangeTime = MathHelper.func_76136_a(this.rand, 100, 200);
         this.colorChangeTick = this.colorChangeTime;
         this.utumnoCheckTime = 0;
      }

      if (this.colorChangeTick > 0) {
         --this.colorChangeTick;
         if (this.colorChangeTick <= 0) {
            this.colorChangeTime = 0;
            this.colorTopCurrent = this.colorTopNext;
            this.colorMidCurrent = this.colorMidNext;
            this.colorBottomCurrent = this.colorBottomNext;
            this.colorTopNext = null;
            this.colorMidNext = null;
            this.colorBottomNext = null;
            this.timeUntilColorChange = MathHelper.func_76136_a(this.rand, 1200, 2400);
         }
      }

      if (this.utumnoCheckTime > 0) {
         --this.utumnoCheckTime;
      } else {
         double range = 256.0D;
         this.atUtumno = false;
         this.utumnoCheckTime = 200;
      }

      if (this.atUtumno) {
         if (this.utumnoChange < 200) {
            ++this.utumnoChange;
         }
      } else if (this.utumnoChange > 0) {
         --this.utumnoChange;
      }

      float freq;
      float speed;
      float amp;
      NorthernLightsRenderer.AuroraCycle cycle;
      if (this.rand.nextInt(50) == 0) {
         freq = MathHelper.func_151240_a(this.rand, 8.0F, 100.0F);
         speed = freq * 5.0E-4F;
         amp = MathHelper.func_151240_a(this.rand, 0.05F, 0.3F);
         cycle = new NorthernLightsRenderer.AuroraCycle(freq, speed, amp);
         cycle.age = cycle.maxAge = MathHelper.func_76136_a(this.rand, 100, 400);
         this.waveOscillations.add(cycle);
      }

      HashSet removes;
      Iterator var21;
      NorthernLightsRenderer.AuroraCycle c;
      if (!this.waveOscillations.isEmpty()) {
         removes = new HashSet();
         var21 = this.waveOscillations.iterator();

         while(var21.hasNext()) {
            c = (NorthernLightsRenderer.AuroraCycle)var21.next();
            c.update();
            if (c.age <= 0) {
               removes.add(c);
            }
         }

         this.waveOscillations.removeAll(removes);
      }

      if (this.rand.nextInt(120) == 0) {
         freq = MathHelper.func_151240_a(this.rand, 30.0F, 150.0F);
         speed = freq * 0.002F;
         amp = MathHelper.func_151240_a(this.rand, 0.05F, 0.5F);
         cycle = new NorthernLightsRenderer.AuroraCycle(freq, speed, amp);
         cycle.age = cycle.maxAge = MathHelper.func_76136_a(this.rand, 100, 400);
         this.glowOscillations.add(cycle);
      }

      if (this.rand.nextInt(300) == 0) {
         freq = MathHelper.func_151240_a(this.rand, 400.0F, 500.0F);
         speed = freq * 0.004F;
         amp = MathHelper.func_151240_a(this.rand, 0.1F, 0.2F);
         cycle = new NorthernLightsRenderer.AuroraCycle(freq, speed, amp);
         cycle.age = cycle.maxAge = MathHelper.func_76136_a(this.rand, 100, 200);
         this.glowOscillations.add(cycle);
      }

      if (!this.glowOscillations.isEmpty()) {
         removes = new HashSet();
         var21 = this.glowOscillations.iterator();

         while(var21.hasNext()) {
            c = (NorthernLightsRenderer.AuroraCycle)var21.next();
            c.update();
            if (c.age <= 0) {
               removes.add(c);
            }
         }

         this.glowOscillations.removeAll(removes);
      }

   }

   private Color[] generateColorSet() {
      float h1 = MathHelper.func_151240_a(this.rand, 0.22F, 0.48F);
      float h2 = MathHelper.func_151240_a(this.rand, 0.22F, 0.48F);
      float h3 = MathHelper.func_151240_a(this.rand, 0.22F, 0.48F);
      if (this.rand.nextInt(5) == 0) {
         h3 = MathHelper.func_151240_a(this.rand, 0.94F, 1.01F);
      } else {
         if (this.rand.nextInt(6) == 0) {
            h1 = MathHelper.func_151240_a(this.rand, 0.78F, 1.08F);
         }

         if (this.rand.nextInt(6) == 0) {
            h1 = MathHelper.func_151240_a(this.rand, 0.78F, 1.08F);
            h2 = MathHelper.func_151240_a(this.rand, 0.85F, 1.08F);
         }
      }

      if (this.rand.nextInt(50) == 0) {
         h1 = MathHelper.func_151240_a(this.rand, 0.7F, 1.08F);
         h2 = MathHelper.func_151240_a(this.rand, 0.54F, 0.77F);
         h3 = MathHelper.func_151240_a(this.rand, 0.48F, 0.7F);
      }

      Color topColor = new Color(Color.HSBtoRGB(h1, 1.0F, 1.0F));
      Color midColor = new Color(Color.HSBtoRGB(h2, 1.0F, 1.0F));
      Color bottomColor = new Color(Color.HSBtoRGB(h3, 1.0F, 1.0F));
      return new Color[]{topColor, midColor, bottomColor};
   }

   private float waveEquation(float t, float tick, float renderTick, boolean fancy) {
      float f = 0.0F;
      f += this.wave0.calc(t, tick);

      NorthernLightsRenderer.AuroraCycle c;
      for(Iterator var6 = this.waveOscillations.iterator(); var6.hasNext(); f += c.calc(t, tick)) {
         c = (NorthernLightsRenderer.AuroraCycle)var6.next();
      }

      return f;
   }

   private float glowEquation(float t, float tick, float renderTick, boolean fancy) {
      float f = 0.0F;
      f += this.glow0.calc(t, tick);
      NorthernLightsRenderer.AuroraCycle c;
      if (fancy) {
         for(Iterator var6 = this.glowOscillations.iterator(); var6.hasNext(); f += c.calc(t, tick)) {
            c = (NorthernLightsRenderer.AuroraCycle)var6.next();
         }
      }

      return f;
   }

   private static class AuroraCycle {
      public final float freq;
      public final float tickMultiplier;
      public final float amp;
      public int age;
      public int maxAge = -1;
      private float ampModifier = 1.0F;

      public AuroraCycle(float f, float t, float a) {
         this.freq = f;
         this.tickMultiplier = t;
         this.amp = a;
      }

      public float calc(float t, float tick) {
         return MathHelper.func_76134_b(t * this.freq + tick * this.tickMultiplier) * this.amp * this.ampModifier;
      }

      public void update() {
         if (this.age >= 0) {
            --this.age;
            float a = (float)(this.maxAge - this.age) / (float)this.maxAge;
            this.ampModifier = Math.min(a, 1.0F - a);
         }

      }
   }
}
