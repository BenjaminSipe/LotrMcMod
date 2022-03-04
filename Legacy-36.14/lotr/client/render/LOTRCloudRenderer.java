package lotr.client.render;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.client.LOTRReflectionClient;
import lotr.common.LOTRConfig;
import lotr.common.LOTRDate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class LOTRCloudRenderer extends IRenderHandler {
   public static final ResourceLocation cloudTexture = new ResourceLocation("lotr:sky/clouds.png");
   private static int cloudRange;
   private static Random cloudRand = new Random();
   private static LOTRCloudRenderer.CloudProperty cloudOpacity = new LOTRCloudRenderer.CloudProperty(233591206262L, 0.1F, 1.0F, 0.001F);
   private static LOTRCloudRenderer.CloudProperty cloudSpeed = new LOTRCloudRenderer.CloudProperty(6283905602629L, 0.0F, 0.5F, 0.001F);
   private static LOTRCloudRenderer.CloudProperty cloudAngle = new LOTRCloudRenderer.CloudProperty(360360635650636L, 0.0F, 6.2831855F, 0.01F);
   private static double cloudPosXPre;
   private static double cloudPosX;
   private static double cloudPosZPre;
   private static double cloudPosZ;

   public static void updateClouds(WorldClient world) {
      cloudOpacity.update(world);
      cloudSpeed.update(world);
      cloudAngle.update(world);
      float angle = cloudAngle.getValue(1.0F);
      float speed = cloudSpeed.getValue(1.0F);
      cloudPosXPre = cloudPosX;
      cloudPosX += (double)(MathHelper.func_76134_b(angle) * speed);
      cloudPosZPre = cloudPosZ;
      cloudPosZ += (double)(MathHelper.func_76126_a(angle) * speed);
   }

   public static void resetClouds() {
      cloudOpacity.reset();
      cloudSpeed.reset();
      cloudAngle.reset();
   }

   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      world.field_72984_F.func_76320_a("lotrClouds");
      if (world.field_73011_w.func_76569_d()) {
         Block block = ActiveRenderInfo.func_151460_a(world, mc.field_71451_h, partialTicks);
         if (block.func_149688_o() == Material.field_151586_h) {
            return;
         }

         cloudRange = LOTRConfig.cloudRange;
         GL11.glMatrixMode(5889);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         float fov = LOTRReflectionClient.getFOVModifier(mc.field_71460_t, partialTicks, true);
         Project.gluPerspective(fov, (float)mc.field_71443_c / (float)mc.field_71440_d, 0.05F, (float)cloudRange);
         GL11.glMatrixMode(5888);
         GL11.glPushMatrix();
         GL11.glDisable(2884);
         GL11.glDepthMask(false);
         GL11.glEnable(3008);
         float alphaFunc = GL11.glGetFloat(3010);
         GL11.glAlphaFunc(516, 0.01F);
         GL11.glEnable(3042);
         OpenGlHelper.func_148821_a(770, 771, 1, 0);
         GL11.glFogi(2917, 9729);
         GL11.glFogf(2915, (float)cloudRange * 0.9F);
         GL11.glFogf(2916, (float)cloudRange);
         if (GLContext.getCapabilities().GL_NV_fog_distance) {
            GL11.glFogi(34138, 34139);
         }

         Tessellator tessellator = Tessellator.field_78398_a;
         mc.field_71446_o.func_110577_a(cloudTexture);
         Vec3 cloudColor = world.func_72824_f(partialTicks);
         float r = (float)cloudColor.field_72450_a;
         float g = (float)cloudColor.field_72448_b;
         float b = (float)cloudColor.field_72449_c;
         if (mc.field_71474_y.field_74337_g) {
            float r1 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
            float g1 = (r * 30.0F + g * 70.0F) / 100.0F;
            float b1 = (r * 30.0F + b * 70.0F) / 100.0F;
            r = r1;
            g = g1;
            b = b1;
         }

         Vec3 pos = mc.field_71451_h.func_70666_h(partialTicks);

         for(int pass = 0; pass < 2; ++pass) {
            int scale = 4096 * IntMath.pow(2, pass);
            double invScaleD = 1.0D / (double)scale;
            double posX = pos.field_72450_a;
            double posZ = pos.field_72449_c;
            double posY = pos.field_72448_b;
            double cloudPosXAdd = cloudPosXPre + (cloudPosX - cloudPosXPre) * (double)partialTicks;
            double cloudPosZAdd = cloudPosZPre + (cloudPosZ - cloudPosZPre) * (double)partialTicks;
            cloudPosXAdd /= (double)(pass + 1);
            cloudPosZAdd /= (double)(pass + 1);
            posX += cloudPosXAdd;
            posZ += cloudPosZAdd;
            int x = MathHelper.func_76128_c(posX / (double)scale);
            int z = MathHelper.func_76128_c(posZ / (double)scale);
            double cloudX = posX - (double)(x * scale);
            double cloudZ = posZ - (double)(z * scale);
            double cloudY = (double)world.field_73011_w.func_76571_f() - posY + 0.33000001311302185D + (double)((float)pass * 50.0F);
            tessellator.func_78382_b();
            tessellator.func_78369_a(r, g, b, (0.8F - (float)pass * 0.5F) * cloudOpacity.getValue(partialTicks));
            int interval = cloudRange;

            for(int i = -cloudRange; i < cloudRange; i += interval) {
               for(int k = -cloudRange; k < cloudRange; k += interval) {
                  int xMin = i + 0;
                  int xMax = i + interval;
                  int zMin = k + 0;
                  int zMax = k + interval;
                  double uMin = ((double)xMin + cloudX) * invScaleD;
                  double uMax = ((double)xMax + cloudX) * invScaleD;
                  double vMin = ((double)zMin + cloudZ) * invScaleD;
                  double vMax = ((double)zMax + cloudZ) * invScaleD;
                  tessellator.func_78374_a((double)xMin, cloudY, (double)zMax, uMin, vMax);
                  tessellator.func_78374_a((double)xMax, cloudY, (double)zMax, uMax, vMax);
                  tessellator.func_78374_a((double)xMax, cloudY, (double)zMin, uMax, vMin);
                  tessellator.func_78374_a((double)xMin, cloudY, (double)zMin, uMin, vMin);
               }
            }

            tessellator.func_78381_a();
         }

         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEnable(2884);
         GL11.glDepthMask(true);
         GL11.glAlphaFunc(516, alphaFunc);
         GL11.glDisable(3042);
         GL11.glMatrixMode(5889);
         GL11.glPopMatrix();
         GL11.glMatrixMode(5888);
         GL11.glPopMatrix();
      }

      world.field_72984_F.func_76319_b();
   }

   private static class CloudProperty {
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

      public void update(WorldClient world) {
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

      private float getCurrentDayValue(WorldClient world) {
         int day = LOTRDate.ShireReckoning.currentDay;
         long seed = (long)day * this.baseSeed + (long)day + 83025820626792L;
         LOTRCloudRenderer.cloudRand.setSeed(seed);
         float f = MathHelper.func_151240_a(LOTRCloudRenderer.cloudRand, this.minValue, this.maxValue);
         return f;
      }
   }
}
