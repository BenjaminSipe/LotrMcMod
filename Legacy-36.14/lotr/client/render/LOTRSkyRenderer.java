package lotr.client.render;

import lotr.client.render.entity.LOTRRandomSkins;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

public class LOTRSkyRenderer extends IRenderHandler {
   private static final ResourceLocation moonTexture = new ResourceLocation("lotr:sky/moon.png");
   private static final ResourceLocation sunTexture = new ResourceLocation("lotr:sky/sun.png");
   private static final ResourceLocation earendilTexture = new ResourceLocation("lotr:sky/earendil.png");
   private LOTRWorldProvider worldProvider;
   private LOTRRandomSkins skyTextures;
   private ResourceLocation currentSkyTexture;
   private int glSkyList;
   private int glSkyList2;

   public LOTRSkyRenderer(LOTRWorldProvider provider) {
      this.worldProvider = provider;
      this.skyTextures = LOTRRandomSkins.loadSkinsList("lotr:sky/night");
      Tessellator tessellator = Tessellator.field_78398_a;
      this.glSkyList = GLAllocation.func_74526_a(3);
      GL11.glNewList(this.glSkyList, 4864);
      byte b2 = 64;
      int i = 256 / b2 + 2;
      float f = 16.0F;

      int j;
      int k;
      for(j = -b2 * i; j <= b2 * i; j += b2) {
         for(k = -b2 * i; k <= b2 * i; k += b2) {
            tessellator.func_78382_b();
            tessellator.func_78377_a((double)(j + 0), (double)f, (double)(k + 0));
            tessellator.func_78377_a((double)(j + b2), (double)f, (double)(k + 0));
            tessellator.func_78377_a((double)(j + b2), (double)f, (double)(k + b2));
            tessellator.func_78377_a((double)(j + 0), (double)f, (double)(k + b2));
            tessellator.func_78381_a();
         }
      }

      GL11.glEndList();
      this.glSkyList2 = this.glSkyList + 1;
      GL11.glNewList(this.glSkyList2, 4864);
      f = -16.0F;
      tessellator.func_78382_b();

      for(j = -b2 * i; j <= b2 * i; j += b2) {
         for(k = -b2 * i; k <= b2 * i; k += b2) {
            tessellator.func_78377_a((double)(j + b2), (double)f, (double)(k + 0));
            tessellator.func_78377_a((double)(j + 0), (double)f, (double)(k + 0));
            tessellator.func_78377_a((double)(j + 0), (double)f, (double)(k + b2));
            tessellator.func_78377_a((double)(j + b2), (double)f, (double)(k + b2));
         }
      }

      tessellator.func_78381_a();
      GL11.glEndList();
   }

   public void render(float partialTicks, WorldClient world, Minecraft mc) {
      world.field_72984_F.func_76320_a("lotrSky");
      boolean renderSkyFeatures = world.field_73011_w.func_76569_d();
      int i = MathHelper.func_76128_c(mc.field_71451_h.field_70165_t);
      int k = MathHelper.func_76128_c(mc.field_71451_h.field_70161_v);
      BiomeGenBase biome = world.func_72807_a(i, k);
      if (biome instanceof LOTRBiome && renderSkyFeatures) {
         renderSkyFeatures = ((LOTRBiome)biome).hasSky();
      }

      GL11.glDisable(3553);
      Vec3 skyColor = world.func_72833_a(mc.field_71451_h, partialTicks);
      float skyR = (float)skyColor.field_72450_a;
      float skyG = (float)skyColor.field_72448_b;
      float skyB = (float)skyColor.field_72449_c;
      float rainBrightness;
      if (mc.field_71474_y.field_74337_g) {
         float newSkyR = (skyR * 30.0F + skyG * 59.0F + skyB * 11.0F) / 100.0F;
         float newSkyG = (skyR * 30.0F + skyG * 70.0F) / 100.0F;
         rainBrightness = (skyR * 30.0F + skyB * 70.0F) / 100.0F;
         skyR = newSkyR;
         skyG = newSkyG;
         skyB = rainBrightness;
      }

      GL11.glColor3f(skyR, skyG, skyB);
      Tessellator tessellator = Tessellator.field_78398_a;
      GL11.glDepthMask(false);
      GL11.glEnable(2912);
      GL11.glColor3f(skyR, skyG, skyB);
      GL11.glCallList(this.glSkyList);
      GL11.glDisable(2912);
      GL11.glDisable(3008);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      RenderHelper.func_74518_a();
      float[] sunrise = world.field_73011_w.func_76560_a(world.func_72826_c(partialTicks), partialTicks);
      float x;
      float f8;
      float f9;
      float starBrightness;
      float rSun;
      float sunriseBlend;
      if (sunrise != null) {
         GL11.glDisable(3553);
         GL11.glShadeModel(7425);
         GL11.glPushMatrix();
         GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(MathHelper.func_76126_a(world.func_72929_e(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
         rainBrightness = sunrise[0];
         x = sunrise[1];
         x *= 1.2F;
         f8 = sunrise[2];
         if (mc.field_71474_y.field_74337_g) {
            f9 = (rainBrightness * 30.0F + x * 59.0F + f8 * 11.0F) / 100.0F;
            starBrightness = (rainBrightness * 30.0F + x * 70.0F) / 100.0F;
            rSun = (rainBrightness * 30.0F + f8 * 70.0F) / 100.0F;
            rainBrightness = f9;
            x = starBrightness;
            f8 = rSun;
         }

         tessellator.func_78371_b(6);
         tessellator.func_78369_a(rainBrightness, x, f8, sunrise[3]);
         tessellator.func_78377_a(0.0D, 100.0D, 0.0D);
         tessellator.func_78369_a(sunrise[0], sunrise[1], sunrise[2], 0.0F);
         int passes = 16;

         for(int l = 0; l <= passes; ++l) {
            rSun = (float)l * 3.1415927F * 2.0F / (float)passes;
            float sin = MathHelper.func_76126_a(rSun);
            sunriseBlend = MathHelper.func_76134_b(rSun);
            tessellator.func_78377_a((double)(sin * 120.0F), (double)(sunriseBlend * 120.0F), (double)(-sunriseBlend * 40.0F * sunrise[3]));
         }

         tessellator.func_78381_a();
         GL11.glPopMatrix();
         GL11.glShadeModel(7424);
      }

      GL11.glPushMatrix();
      if (renderSkyFeatures) {
         GL11.glEnable(3553);
         GL11.glBlendFunc(770, 1);
         rainBrightness = 1.0F - world.func_72867_j(partialTicks);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, rainBrightness);
         x = 0.0F;
         f8 = 0.0F;
         f9 = 0.0F;
         GL11.glTranslatef(x, f8, f9);
         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
         starBrightness = world.func_72880_h(partialTicks) * rainBrightness;
         if (starBrightness > 0.0F) {
            if (this.currentSkyTexture == null) {
               this.currentSkyTexture = this.skyTextures.getRandomSkin();
            }

            mc.field_71446_o.func_110577_a(this.currentSkyTexture);
            GL11.glPushMatrix();
            GL11.glRotatef(world.func_72826_c(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, starBrightness);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSkyboxSide(tessellator, 4);
            GL11.glPushMatrix();
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            this.renderSkyboxSide(tessellator, 1);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            this.renderSkyboxSide(tessellator, 0);
            GL11.glPopMatrix();
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSkyboxSide(tessellator, 5);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSkyboxSide(tessellator, 2);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            this.renderSkyboxSide(tessellator, 3);
            GL11.glPopMatrix();
         } else {
            this.currentSkyTexture = null;
         }

         GL11.glRotatef(world.func_72826_c(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
         GL11.glBlendFunc(770, 771);
         mc.field_71446_o.func_110577_a(sunTexture);
         rSun = 10.0F;

         int pass;
         for(pass = 0; pass <= 1; ++pass) {
            if (pass == 0) {
               GL11.glColor4f(1.0F, 1.0F, 1.0F, rainBrightness);
            } else if (pass == 1) {
               if (sunrise == null) {
                  continue;
               }

               sunriseBlend = sunrise[3];
               sunriseBlend *= 0.5F;
               GL11.glColor4f(1.0F, 0.9F, 0.2F, sunriseBlend * rainBrightness);
            }

            tessellator.func_78382_b();
            tessellator.func_78374_a((double)(-rSun), 100.0D, (double)(-rSun), 0.0D, 0.0D);
            tessellator.func_78374_a((double)rSun, 100.0D, (double)(-rSun), 1.0D, 0.0D);
            tessellator.func_78374_a((double)rSun, 100.0D, (double)rSun, 1.0D, 1.0D);
            tessellator.func_78374_a((double)(-rSun), 100.0D, (double)rSun, 0.0D, 1.0D);
            tessellator.func_78381_a();
         }

         GL11.glBlendFunc(770, 1);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, rainBrightness);
         LOTRWorldProvider var10000 = this.worldProvider;
         pass = LOTRWorldProvider.MOON_PHASES;
         var10000 = this.worldProvider;
         int moonPhase = LOTRWorldProvider.getLOTRMoonPhase();
         var10000 = this.worldProvider;
         boolean lunarEclipse = LOTRWorldProvider.isLunarEclipse();
         if (lunarEclipse) {
            GL11.glColor3f(1.0F, 0.6F, 0.4F);
         }

         mc.field_71446_o.func_110577_a(moonTexture);
         float rMoon = 10.0F;
         float f14 = (float)moonPhase / (float)pass;
         float f15 = 0.0F;
         float f16 = (float)(moonPhase + 1) / (float)pass;
         float f17 = 1.0F;
         tessellator.func_78382_b();
         tessellator.func_78374_a((double)(-rMoon), -100.0D, (double)rMoon, (double)f16, (double)f17);
         tessellator.func_78374_a((double)rMoon, -100.0D, (double)rMoon, (double)f14, (double)f17);
         tessellator.func_78374_a((double)rMoon, -100.0D, (double)(-rMoon), (double)f14, (double)f15);
         tessellator.func_78374_a((double)(-rMoon), -100.0D, (double)(-rMoon), (double)f16, (double)f15);
         tessellator.func_78381_a();
         GL11.glColor3f(1.0F, 1.0F, 1.0F);
         float celestialAngle = world.func_72826_c(partialTicks);
         float f0 = celestialAngle - 0.5F;
         float f1 = Math.abs(f0);
         float eMin = 0.15F;
         float eMax = 0.3F;
         if (f1 >= eMin && f1 <= eMax) {
            float eMid = (eMin + eMax) / 2.0F;
            float eHalfWidth = eMax - eMid;
            float eBright = MathHelper.func_76134_b((f1 - eMid) / eHalfWidth * 3.1415927F / 2.0F);
            eBright *= eBright;
            float eAngle = Math.signum(f0) * 18.0F;
            GL11.glPushMatrix();
            GL11.glRotatef(eAngle, 1.0F, 0.0F, 0.0F);
            GL11.glBlendFunc(770, 1);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, eBright * rainBrightness);
            mc.field_71446_o.func_110577_a(earendilTexture);
            float rEarendil = 1.5F;
            tessellator.func_78382_b();
            tessellator.func_78374_a((double)(-rEarendil), 100.0D, (double)(-rEarendil), 0.0D, 0.0D);
            tessellator.func_78374_a((double)rEarendil, 100.0D, (double)(-rEarendil), 1.0D, 0.0D);
            tessellator.func_78374_a((double)rEarendil, 100.0D, (double)rEarendil, 1.0D, 1.0D);
            tessellator.func_78374_a((double)(-rEarendil), 100.0D, (double)rEarendil, 0.0D, 1.0D);
            tessellator.func_78381_a();
            GL11.glPopMatrix();
         }

         GL11.glDisable(3553);
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
      GL11.glEnable(3008);
      GL11.glEnable(2912);
      GL11.glPopMatrix();
      GL11.glDisable(3553);
      GL11.glColor3f(0.0F, 0.0F, 0.0F);
      double d0 = mc.field_71439_g.func_70666_h(partialTicks).field_72448_b - world.func_72919_O();
      if (d0 < 0.0D) {
         GL11.glPushMatrix();
         GL11.glTranslatef(0.0F, 12.0F, 0.0F);
         GL11.glCallList(this.glSkyList2);
         GL11.glPopMatrix();
         f8 = 1.0F;
         f9 = -((float)(d0 + 65.0D));
         starBrightness = -f8;
         tessellator.func_78382_b();
         tessellator.func_78384_a(0, 255);
         tessellator.func_78377_a((double)(-f8), (double)f9, (double)f8);
         tessellator.func_78377_a((double)f8, (double)f9, (double)f8);
         tessellator.func_78377_a((double)f8, (double)starBrightness, (double)f8);
         tessellator.func_78377_a((double)(-f8), (double)starBrightness, (double)f8);
         tessellator.func_78377_a((double)(-f8), (double)starBrightness, (double)(-f8));
         tessellator.func_78377_a((double)f8, (double)starBrightness, (double)(-f8));
         tessellator.func_78377_a((double)f8, (double)f9, (double)(-f8));
         tessellator.func_78377_a((double)(-f8), (double)f9, (double)(-f8));
         tessellator.func_78377_a((double)f8, (double)starBrightness, (double)(-f8));
         tessellator.func_78377_a((double)f8, (double)starBrightness, (double)f8);
         tessellator.func_78377_a((double)f8, (double)f9, (double)f8);
         tessellator.func_78377_a((double)f8, (double)f9, (double)(-f8));
         tessellator.func_78377_a((double)(-f8), (double)f9, (double)(-f8));
         tessellator.func_78377_a((double)(-f8), (double)f9, (double)f8);
         tessellator.func_78377_a((double)(-f8), (double)starBrightness, (double)f8);
         tessellator.func_78377_a((double)(-f8), (double)starBrightness, (double)(-f8));
         tessellator.func_78377_a((double)(-f8), (double)starBrightness, (double)(-f8));
         tessellator.func_78377_a((double)(-f8), (double)starBrightness, (double)f8);
         tessellator.func_78377_a((double)f8, (double)starBrightness, (double)f8);
         tessellator.func_78377_a((double)f8, (double)starBrightness, (double)(-f8));
         tessellator.func_78381_a();
      }

      if (world.field_73011_w.func_76561_g()) {
         GL11.glColor3f(skyR * 0.2F + 0.04F, skyG * 0.2F + 0.04F, skyB * 0.6F + 0.1F);
      } else {
         GL11.glColor3f(skyR, skyG, skyB);
      }

      GL11.glPushMatrix();
      GL11.glTranslatef(0.0F, -((float)(d0 - 16.0D)), 0.0F);
      GL11.glCallList(this.glSkyList2);
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDepthMask(true);
      world.field_72984_F.func_76319_b();
   }

   private void renderSkyboxSide(Tessellator tessellator, int side) {
      double u = (double)(side % 3) / 3.0D;
      double v = (double)(side / 3) / 2.0D;
      tessellator.func_78382_b();
      tessellator.func_78374_a(-100.0D, -100.0D, -100.0D, u, v);
      tessellator.func_78374_a(-100.0D, -100.0D, 100.0D, u, v + 0.5D);
      tessellator.func_78374_a(100.0D, -100.0D, 100.0D, u + 0.3333333333333333D, v + 0.5D);
      tessellator.func_78374_a(100.0D, -100.0D, -100.0D, u + 0.3333333333333333D, v);
      tessellator.func_78381_a();
   }
}
