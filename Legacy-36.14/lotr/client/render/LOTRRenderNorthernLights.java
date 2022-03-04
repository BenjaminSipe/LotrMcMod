package lotr.client.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.client.LOTRReflectionClient;
import lotr.common.LOTRDate;
import lotr.common.LOTRMod;
import lotr.common.LOTRTime;
import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class LOTRRenderNorthernLights {
   private static int nlTick;
   private static int currentNightNum;
   private static float brightnessTonight;
   private static float maxNorthTonight;
   private static float minNorthTonight;
   private static int rainingTick;
   private static int rainingTickPrev;
   private static final int rainingChangeTime = 80;
   private static boolean atUtumno = false;
   private static int utumnoChange = 0;
   private static final int utumnoChangeTime = 200;
   private static final Random rand = new Random();
   private static final Random dateRand = new Random();
   private static float[] colorTopCurrent;
   private static float[] colorMidCurrent;
   private static float[] colorBottomCurrent;
   private static float[] colorTopNext;
   private static float[] colorMidNext;
   private static float[] colorBottomNext;
   private static int colorChangeTime;
   private static int colorChangeTick;
   private static int timeUntilColorChange;
   private static int utumnoCheckTime;
   private static LOTRRenderNorthernLights.AuroraCycle wave0 = new LOTRRenderNorthernLights.AuroraCycle(4.0F, 0.01F, 0.9F);
   private static List waveOscillations = new ArrayList();
   private static List glowOscillations = new ArrayList();
   private static LOTRRenderNorthernLights.AuroraCycle glow0 = new LOTRRenderNorthernLights.AuroraCycle(20.0F, 0.02F, 0.6F);

   public static void render(Minecraft mc, WorldClient world, float tick) {
      if (mc.field_71474_y.field_151451_c >= 6) {
         float minSun = 0.2F;
         float daylight = (world.func_72971_b(tick) - minSun) / (1.0F - minSun);
         float maxDaylight = 0.3F;
         float nlBrightness = (1.0F - daylight - (1.0F - maxDaylight)) / maxDaylight;
         if (nlBrightness > 0.0F) {
            float tonight = brightnessTonight;
            float utumno = (float)utumnoChange / 200.0F;
            tonight += (1.0F - tonight) * utumno;
            if (tonight > 0.0F) {
               nlBrightness *= tonight;
               float northernness = getNorthernness(mc.field_71451_h);
               if (northernness > 0.0F) {
                  nlBrightness *= northernness;
                  float raininess = ((float)rainingTickPrev + (float)(rainingTick - rainingTickPrev) * tick) / 80.0F;
                  if (raininess < 1.0F) {
                     nlBrightness *= 1.0F - raininess;
                     nlBrightness *= 0.3F + (1.0F - world.func_72867_j(tick)) * 0.7F;
                     world.field_72984_F.func_76320_a("aurora");
                     float nlScale = 2000.0F;
                     float nlDistance = (1.0F - northernness) * nlScale * 2.0F;
                     float nlHeight = nlScale * 0.5F;
                     GL11.glMatrixMode(5889);
                     GL11.glPushMatrix();
                     GL11.glLoadIdentity();
                     float fov = LOTRReflectionClient.getFOVModifier(mc.field_71460_t, tick, true);
                     Project.gluPerspective(fov, (float)mc.field_71443_c / (float)mc.field_71440_d, 0.05F, nlScale * 5.0F);
                     GL11.glMatrixMode(5888);
                     GL11.glPushMatrix();
                     GL11.glTranslatef(0.0F, nlHeight, -nlDistance);
                     GL11.glScalef(1.0F, 1.0F, 1.0F);
                     GL11.glDisable(3553);
                     GL11.glDisable(3008);
                     GL11.glDepthMask(false);
                     GL11.glEnable(3042);
                     OpenGlHelper.func_148821_a(770, 771, 1, 0);
                     float alphaFunc = GL11.glGetFloat(3010);
                     GL11.glAlphaFunc(516, 0.01F);
                     GL11.glShadeModel(7425);
                     GL11.glDisable(2884);
                     world.field_72984_F.func_76320_a("sheet");
                     renderSheet(mc, world, nlScale * -0.5F, nlBrightness * 0.8F, (double)(nlScale * 1.0F), (double)(nlScale * 0.25F), 0.25502F, tick);
                     renderSheet(mc, world, 0.0F, nlBrightness * 1.0F, (double)(nlScale * 1.5F), (double)(nlScale * 0.3F), 0.15696F, tick);
                     renderSheet(mc, world, nlScale * 0.5F, nlBrightness * 0.8F, (double)(nlScale * 1.0F), (double)(nlScale * 0.25F), 0.67596F, tick);
                     world.field_72984_F.func_76319_b();
                     GL11.glEnable(2884);
                     GL11.glShadeModel(7424);
                     GL11.glAlphaFunc(516, alphaFunc);
                     GL11.glDisable(3042);
                     GL11.glDepthMask(true);
                     GL11.glEnable(3008);
                     GL11.glEnable(3553);
                     GL11.glMatrixMode(5889);
                     GL11.glPopMatrix();
                     GL11.glMatrixMode(5888);
                     GL11.glPopMatrix();
                     world.field_72984_F.func_76319_b();
                  }
               }
            }
         }
      }
   }

   private static float getNorthernness(EntityLivingBase entity) {
      float minNorth = minNorthTonight;
      float maxNorth = maxNorthTonight;
      float northernness = ((float)entity.field_70161_v - minNorth) / (maxNorth - minNorth);
      northernness = MathHelper.func_76131_a(northernness, 0.0F, 1.0F);
      return northernness;
   }

   private static boolean isRainLayerAt(EntityLivingBase entity) {
      World world = entity.field_70170_p;
      int i = MathHelper.func_76128_c(entity.field_70165_t);
      int j = MathHelper.func_76128_c(entity.field_70121_D.field_72338_b);
      int k = MathHelper.func_76128_c(entity.field_70161_v);
      if (!world.func_72896_J()) {
         return false;
      } else {
         BiomeGenBase biomegenbase = world.func_72807_a(i, k);
         if (biomegenbase.func_76746_c()) {
            return false;
         } else {
            return biomegenbase.func_150564_a(i, j, k) >= 0.15F && biomegenbase.func_76738_d();
         }
      }
   }

   private static void renderSheet(Minecraft mc, World world, float nlDistance, float nlBrightness, double halfWidth, double halfHeight, float tickExtra, float tick) {
      float r1 = colorTopCurrent[0];
      float g1 = colorTopCurrent[1];
      float b1 = colorTopCurrent[2];
      float r2 = colorMidCurrent[0];
      float g2 = colorMidCurrent[1];
      float b2 = colorMidCurrent[2];
      float r3 = colorBottomCurrent[0];
      float g3 = colorBottomCurrent[1];
      float b3 = colorBottomCurrent[2];
      float a1;
      if (colorChangeTime > 0) {
         a1 = (float)colorChangeTick / (float)colorChangeTime;
         a1 = 1.0F - a1;
         r1 = colorTopCurrent[0] + (colorTopNext[0] - colorTopCurrent[0]) * a1;
         g1 = colorTopCurrent[1] + (colorTopNext[1] - colorTopCurrent[1]) * a1;
         b1 = colorTopCurrent[2] + (colorTopNext[2] - colorTopCurrent[2]) * a1;
         r2 = colorMidCurrent[0] + (colorMidNext[0] - colorMidCurrent[0]) * a1;
         g2 = colorMidCurrent[1] + (colorMidNext[1] - colorMidCurrent[1]) * a1;
         b2 = colorMidCurrent[2] + (colorMidNext[2] - colorMidCurrent[2]) * a1;
         r3 = colorBottomCurrent[0] + (colorBottomNext[0] - colorBottomCurrent[0]) * a1;
         g3 = colorBottomCurrent[1] + (colorBottomNext[1] - colorBottomCurrent[1]) * a1;
         b3 = colorBottomCurrent[2] + (colorBottomNext[2] - colorBottomCurrent[2]) * a1;
      }

      a1 = 0.0F;
      float a2 = 0.4F;
      float a3 = 0.8F;
      a1 *= nlBrightness;
      a2 *= nlBrightness;
      a3 *= nlBrightness;
      float fullTick = (float)nlTick + tick + tickExtra;
      Tessellator tess = Tessellator.field_78398_a;
      tess.func_78382_b();
      tess.func_78380_c(15728880);
      world.field_72984_F.func_76320_a("vertexLoop");
      int strips = 500;
      if (!mc.field_71474_y.field_74347_j) {
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

         randomFade = 0.5F + glowEquation(mc, t, fullTick, tick) * 0.5F;
         a1_here *= randomFade;
         a2_here *= randomFade;
         a3_here *= randomFade;
         double x0 = -halfWidth + halfWidth * 2.0D * (double)t;
         double x1 = x0 + halfWidth * 2.0D / (double)((float)strips);
         double yMin = -halfHeight;
         double yMid = -halfHeight * 0.4D;
         double z0 = (double)nlDistance;
         double z1 = (double)nlDistance;
         z0 += (double)waveEquation(mc, t, fullTick, tick) * halfWidth * 0.15D;
         z1 += (double)waveEquation(mc, t1, fullTick, tick) * halfWidth * 0.15D;
         double extra = halfHeight * 0.15D;
         tess.func_78369_a(r3, g3, b3, 0.0F);
         tess.func_78377_a(x0, yMin - extra, z0);
         tess.func_78377_a(x1, yMin - extra, z1);
         tess.func_78369_a(r3, g3, b3, a3_here);
         tess.func_78377_a(x1, yMin, z1);
         tess.func_78377_a(x0, yMin, z0);
         tess.func_78369_a(r3, g3, b3, a3_here);
         tess.func_78377_a(x0, yMin, z0);
         tess.func_78377_a(x1, yMin, z1);
         tess.func_78369_a(r2, g2, b2, a2_here);
         tess.func_78377_a(x1, yMid, z1);
         tess.func_78377_a(x0, yMid, z0);
         tess.func_78369_a(r2, g2, b2, a2_here);
         tess.func_78377_a(x0, yMid, z0);
         tess.func_78377_a(x1, yMid, z1);
         tess.func_78369_a(r1, g1, b1, a1_here);
         tess.func_78377_a(x1, halfHeight, z1);
         tess.func_78377_a(x0, halfHeight, z0);
      }

      world.field_72984_F.func_76319_b();
      world.field_72984_F.func_76320_a("draw");
      tess.func_78381_a();
      world.field_72984_F.func_76319_b();
   }

   public static void update(EntityLivingBase viewer) {
      ++nlTick;
      World world = viewer.field_70170_p;
      int effectiveDay = LOTRDate.ShireReckoning.currentDay;
      float daytime = (float)(world.func_72820_D() % (long)LOTRTime.DAY_LENGTH);
      if (daytime < 0.25F) {
         --effectiveDay;
      }

      float speed;
      float amp;
      if (effectiveDay != currentNightNum) {
         currentNightNum = effectiveDay;
         dateRand.setSeed((long)currentNightNum * 35920558925051L + (long)currentNightNum + 83025820626792L);
         LOTRDate.ShireReckoning.Month month = LOTRDate.ShireReckoning.getShireDate(currentNightNum).month;
         boolean isYule = month == LOTRDate.ShireReckoning.Month.YULE_1 || month == LOTRDate.ShireReckoning.Month.YULE_2;
         maxNorthTonight = -35000.0F;
         minNorthTonight = MathHelper.func_151240_a(dateRand, -20000.0F, -15000.0F);
         speed = dateRand.nextFloat();
         if (!isYule && speed >= 0.01F) {
            if (speed < 0.1F) {
               minNorthTonight += 10000.0F;
            } else if (speed < 0.5F) {
               minNorthTonight += 5000.0F;
            }
         } else {
            minNorthTonight += 15000.0F;
         }

         if (LOTRMod.isChristmas()) {
            minNorthTonight = 1000000.0F;
         }

         amp = 0.5F;
         if (!isYule && dateRand.nextFloat() >= amp) {
            brightnessTonight = 0.0F;
         } else {
            brightnessTonight = MathHelper.func_151240_a(dateRand, 0.4F, 1.0F);
         }
      }

      rainingTickPrev = rainingTick;
      boolean raining = isRainLayerAt(viewer);
      if (raining) {
         if (rainingTick < 80) {
            ++rainingTick;
         }
      } else if (rainingTick > 0) {
         --rainingTick;
      }

      Color[] cs;
      if (colorTopCurrent == null) {
         cs = generateColorSet();
         colorTopCurrent = cs[0].getColorComponents((float[])null);
         colorMidCurrent = cs[1].getColorComponents((float[])null);
         colorBottomCurrent = cs[2].getColorComponents((float[])null);
      }

      if (timeUntilColorChange > 0) {
         --timeUntilColorChange;
      } else if (rand.nextInt(1200) == 0) {
         cs = generateColorSet();
         colorTopNext = cs[0].getColorComponents((float[])null);
         colorMidNext = cs[1].getColorComponents((float[])null);
         colorBottomNext = cs[2].getColorComponents((float[])null);
         colorChangeTime = MathHelper.func_76136_a(rand, 100, 200);
         colorChangeTick = colorChangeTime;
         utumnoCheckTime = 0;
      }

      if (colorChangeTick > 0) {
         --colorChangeTick;
         if (colorChangeTick <= 0) {
            colorChangeTime = 0;
            colorTopCurrent = colorTopNext;
            colorMidCurrent = colorMidNext;
            colorBottomCurrent = colorBottomNext;
            colorTopNext = null;
            colorMidNext = null;
            colorBottomNext = null;
            timeUntilColorChange = MathHelper.func_76136_a(rand, 1200, 2400);
         }
      }

      if (utumnoCheckTime > 0) {
         --utumnoCheckTime;
      } else {
         double range = 256.0D;
         if (LOTRFixedStructures.UTUMNO_ENTRANCE.distanceSqTo(viewer) <= range * range) {
            atUtumno = true;
            timeUntilColorChange = 0;
            colorTopNext = new float[]{1.0F, 0.4F, 0.0F};
            colorMidNext = new float[]{1.0F, 0.0F, 0.0F};
            colorBottomNext = new float[]{1.0F, 0.0F, 0.3F};
            colorChangeTime = MathHelper.func_76136_a(rand, 100, 200);
            colorChangeTick = colorChangeTime;
         } else {
            atUtumno = false;
         }

         utumnoCheckTime = 200;
      }

      if (atUtumno) {
         if (utumnoChange < 200) {
            ++utumnoChange;
         }
      } else if (utumnoChange > 0) {
         --utumnoChange;
      }

      LOTRRenderNorthernLights.AuroraCycle cycle;
      float freq;
      if (rand.nextInt(50) == 0) {
         freq = MathHelper.func_151240_a(rand, 8.0F, 100.0F);
         speed = freq * 5.0E-4F;
         amp = MathHelper.func_151240_a(rand, 0.05F, 0.3F);
         cycle = new LOTRRenderNorthernLights.AuroraCycle(freq, speed, amp);
         cycle.age = cycle.maxAge = MathHelper.func_76136_a(rand, 100, 400);
         waveOscillations.add(cycle);
      }

      HashSet removes;
      Iterator var14;
      LOTRRenderNorthernLights.AuroraCycle c;
      if (!waveOscillations.isEmpty()) {
         removes = new HashSet();
         var14 = waveOscillations.iterator();

         while(var14.hasNext()) {
            c = (LOTRRenderNorthernLights.AuroraCycle)var14.next();
            c.update();
            if (c.age <= 0) {
               removes.add(c);
            }
         }

         waveOscillations.removeAll(removes);
      }

      if (rand.nextInt(120) == 0) {
         freq = MathHelper.func_151240_a(rand, 30.0F, 150.0F);
         speed = freq * 0.002F;
         amp = MathHelper.func_151240_a(rand, 0.05F, 0.5F);
         cycle = new LOTRRenderNorthernLights.AuroraCycle(freq, speed, amp);
         cycle.age = cycle.maxAge = MathHelper.func_76136_a(rand, 100, 400);
         glowOscillations.add(cycle);
      }

      if (rand.nextInt(300) == 0) {
         freq = MathHelper.func_151240_a(rand, 400.0F, 500.0F);
         speed = freq * 0.004F;
         amp = MathHelper.func_151240_a(rand, 0.1F, 0.2F);
         cycle = new LOTRRenderNorthernLights.AuroraCycle(freq, speed, amp);
         cycle.age = cycle.maxAge = MathHelper.func_76136_a(rand, 100, 200);
         glowOscillations.add(cycle);
      }

      if (!glowOscillations.isEmpty()) {
         removes = new HashSet();
         var14 = glowOscillations.iterator();

         while(var14.hasNext()) {
            c = (LOTRRenderNorthernLights.AuroraCycle)var14.next();
            c.update();
            if (c.age <= 0) {
               removes.add(c);
            }
         }

         glowOscillations.removeAll(removes);
      }

   }

   private static Color[] generateColorSet() {
      float h1 = MathHelper.func_151240_a(rand, 0.22F, 0.48F);
      float h2 = MathHelper.func_151240_a(rand, 0.22F, 0.48F);
      float h3 = MathHelper.func_151240_a(rand, 0.22F, 0.48F);
      if (rand.nextInt(3) == 0) {
         h1 = MathHelper.func_151240_a(rand, 0.78F, 1.08F);
      }

      if (rand.nextInt(5) == 0) {
         h1 = MathHelper.func_151240_a(rand, 0.78F, 1.08F);
         h2 = MathHelper.func_151240_a(rand, 0.85F, 1.08F);
      }

      if (rand.nextInt(50) == 0) {
         h1 = MathHelper.func_151240_a(rand, 0.7F, 1.08F);
         h2 = MathHelper.func_151240_a(rand, 0.54F, 0.77F);
         h3 = MathHelper.func_151240_a(rand, 0.48F, 0.7F);
      }

      Color topColor = new Color(Color.HSBtoRGB(h1, 1.0F, 1.0F));
      Color midColor = new Color(Color.HSBtoRGB(h2, 1.0F, 1.0F));
      Color bottomColor = new Color(Color.HSBtoRGB(h3, 1.0F, 1.0F));
      return new Color[]{topColor, midColor, bottomColor};
   }

   private static float waveEquation(Minecraft mc, float t, float tick, float renderTick) {
      float f = 0.0F;
      f += wave0.calc(t, tick);

      LOTRRenderNorthernLights.AuroraCycle c;
      for(Iterator var5 = waveOscillations.iterator(); var5.hasNext(); f += c.calc(t, tick)) {
         c = (LOTRRenderNorthernLights.AuroraCycle)var5.next();
      }

      return f;
   }

   private static float glowEquation(Minecraft mc, float t, float tick, float renderTick) {
      float f = 0.0F;
      f += glow0.calc(t, tick);
      LOTRRenderNorthernLights.AuroraCycle c;
      if (mc.field_71474_y.field_74347_j) {
         for(Iterator var5 = glowOscillations.iterator(); var5.hasNext(); f += c.calc(t, tick)) {
            c = (LOTRRenderNorthernLights.AuroraCycle)var5.next();
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
