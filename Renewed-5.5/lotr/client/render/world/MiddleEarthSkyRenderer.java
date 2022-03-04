package lotr.client.render.world;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.render.RandomTextureVariants;
import lotr.common.dim.LOTRDimensionType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.world.DimensionRenderInfo.FogType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.ISkyRenderHandler;

public class MiddleEarthSkyRenderer implements ISkyRenderHandler {
   private static final ResourceLocation SUN_TEXTURE = new ResourceLocation("lotr", "textures/sky/sun.png");
   private static final ResourceLocation MOON_TEXTURE = new ResourceLocation("lotr", "textures/sky/moon.png");
   private static final ResourceLocation EARENDIL_TEXTURE = new ResourceLocation("lotr", "textures/sky/earendil.png");
   private RandomTextureVariants skyTextures;
   private ResourceLocation currentSkyTexture;
   private final VertexFormat skyVertexFormat;
   private VertexBuffer skyVBO;
   private VertexBuffer sky2VBO;

   public MiddleEarthSkyRenderer() {
      this.skyVertexFormat = DefaultVertexFormats.field_181705_e;
      this.skyTextures = RandomTextureVariants.loadSkinsList("lotr", "textures/sky/night");
   }

   private void generateSky() {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder buf = tessellator.func_178180_c();
      if (this.skyVBO != null) {
         this.skyVBO.close();
      }

      this.skyVBO = new VertexBuffer(this.skyVertexFormat);
      this.preRenderSky(buf, 16.0F, false);
      this.skyVBO.func_227875_a_(buf);
   }

   private void generateSky2() {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder buf = tessellator.func_178180_c();
      if (this.sky2VBO != null) {
         this.sky2VBO.close();
      }

      this.sky2VBO = new VertexBuffer(this.skyVertexFormat);
      this.preRenderSky(buf, -16.0F, true);
      this.sky2VBO.func_227875_a_(buf);
   }

   private void preRenderSky(BufferBuilder buf, float posY, boolean reverseX) {
      int i = true;
      int j = true;
      buf.func_181668_a(7, this.skyVertexFormat);

      for(int k = -384; k <= 384; k += 64) {
         for(int l = -384; l <= 384; l += 64) {
            float f = (float)k;
            float f1 = (float)(k + 64);
            if (reverseX) {
               f1 = (float)k;
               f = (float)(k + 64);
            }

            buf.func_225582_a_((double)f, (double)posY, (double)l).func_181675_d();
            buf.func_225582_a_((double)f1, (double)posY, (double)l).func_181675_d();
            buf.func_225582_a_((double)f1, (double)posY, (double)(l + 64)).func_181675_d();
            buf.func_225582_a_((double)f, (double)posY, (double)(l + 64)).func_181675_d();
         }
      }

      buf.func_178977_d();
   }

   public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {
      if (this.skyVBO == null) {
         this.generateSky();
      }

      if (this.sky2VBO == null) {
         this.generateSky2();
      }

      TextureManager texMgr = mc.func_110434_K();
      LOTRDimensionType dimensionType = (LOTRDimensionType)world.func_230315_m_();
      LOTRDimensionRenderInfo dimensionRenderInfo = (LOTRDimensionRenderInfo)world.func_239132_a_();
      long worldTime = dimensionType.getWorldTime(world);
      if (dimensionRenderInfo.func_241683_c_() == FogType.NORMAL) {
         RenderSystem.disableTexture();
         ActiveRenderInfo ari = mc.field_71460_t.func_215316_n();
         Vector3d skyColor = dimensionRenderInfo.getBlendedCompleteSkyColor(world, ari.func_216780_d(), partialTicks);
         float skyR = (float)skyColor.field_72450_a;
         float skyG = (float)skyColor.field_72448_b;
         float skyB = (float)skyColor.field_72449_c;
         FogRenderer.func_228373_b_();
         BufferBuilder buf = Tessellator.func_178181_a().func_178180_c();
         RenderSystem.depthMask(false);
         RenderSystem.enableFog();
         RenderSystem.color3f(skyR, skyG, skyB);
         this.skyVBO.func_177359_a();
         this.skyVertexFormat.func_227892_a_(0L);
         this.skyVBO.func_227874_a_(matrixStack.func_227866_c_().func_227870_a_(), 7);
         VertexBuffer.func_177361_b();
         this.skyVertexFormat.func_227895_d_();
         RenderSystem.disableFog();
         RenderSystem.disableAlphaTest();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         float celestialAngle = world.func_242415_f(partialTicks);
         float[] sunriseColors = dimensionRenderInfo.func_230492_a_(celestialAngle, partialTicks);
         float skyFeatureBrightness;
         float sunriseR;
         float starBrightness;
         float rSun;
         int moonPhase;
         float eMax;
         float rMoon;
         float moonUMin;
         if (sunriseColors != null) {
            RenderSystem.disableTexture();
            RenderSystem.shadeModel(7425);
            matrixStack.func_227860_a_();
            matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
            skyFeatureBrightness = MathHelper.func_76126_a(world.func_72929_e(partialTicks)) < 0.0F ? 180.0F : 0.0F;
            matrixStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(skyFeatureBrightness));
            matrixStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90.0F));
            sunriseR = sunriseColors[0];
            starBrightness = sunriseColors[1];
            float sunriseB = sunriseColors[2];
            rSun = sunriseColors[3];
            sunriseR *= 1.2F;
            starBrightness *= 1.2F;
            sunriseR = MathHelper.func_76131_a(sunriseR, 0.0F, 1.0F);
            starBrightness = MathHelper.func_76131_a(starBrightness, 0.0F, 1.0F);
            Matrix4f matrix4f = matrixStack.func_227866_c_().func_227870_a_();
            buf.func_181668_a(6, DefaultVertexFormats.field_181706_f);
            buf.func_227888_a_(matrix4f, 0.0F, 100.0F, 0.0F).func_227885_a_(sunriseR, starBrightness, sunriseB, rSun).func_181675_d();

            for(moonPhase = 0; moonPhase <= 16; ++moonPhase) {
               eMax = (float)moonPhase * 6.2831855F / 16.0F;
               rMoon = MathHelper.func_76126_a(eMax);
               moonUMin = MathHelper.func_76134_b(eMax);
               buf.func_227888_a_(matrix4f, rMoon * 120.0F, moonUMin * 120.0F, -moonUMin * 40.0F * rSun).func_227885_a_(sunriseR, starBrightness, sunriseB, 0.0F).func_181675_d();
            }

            buf.func_178977_d();
            WorldVertexBufferUploader.func_181679_a(buf);
            matrixStack.func_227865_b_();
            RenderSystem.shadeModel(7424);
         }

         skyFeatureBrightness = dimensionRenderInfo.getSkyFeatureBrightness(world, ari.func_216780_d(), partialTicks);
         sunriseR = 1.0F - world.func_72867_j(partialTicks);
         skyFeatureBrightness *= sunriseR;
         RenderSystem.enableTexture();
         RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
         matrixStack.func_227860_a_();
         matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(-90.0F));
         matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(celestialAngle * 360.0F));
         starBrightness = world.func_228330_j_(partialTicks) * skyFeatureBrightness;
         if (starBrightness > 0.0F) {
            if (this.currentSkyTexture == null) {
               this.currentSkyTexture = this.skyTextures.getRandomSkin();
            }

            texMgr.func_110577_a(this.currentSkyTexture);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, starBrightness);
            matrixStack.func_227860_a_();
            matrixStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(-90.0F));
            this.renderSkyboxSide(buf, matrixStack, 4);
            matrixStack.func_227860_a_();
            matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
            this.renderSkyboxSide(buf, matrixStack, 1);
            matrixStack.func_227865_b_();
            matrixStack.func_227860_a_();
            matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-90.0F));
            this.renderSkyboxSide(buf, matrixStack, 0);
            matrixStack.func_227865_b_();
            matrixStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90.0F));
            this.renderSkyboxSide(buf, matrixStack, 5);
            matrixStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90.0F));
            this.renderSkyboxSide(buf, matrixStack, 2);
            matrixStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(90.0F));
            this.renderSkyboxSide(buf, matrixStack, 3);
            matrixStack.func_227865_b_();
         } else {
            this.currentSkyTexture = null;
         }

         Matrix4f matrix = matrixStack.func_227866_c_().func_227870_a_();
         float moonUMax;
         float moonVMin;
         float sunriseBlend;
         if (skyFeatureBrightness > 0.0F) {
            RenderSystem.defaultBlendFunc();
            rSun = 12.5F;
            texMgr.func_110577_a(SUN_TEXTURE);
            int pass = 0;

            while(true) {
               if (pass > 1) {
                  RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
                  RenderSystem.color4f(1.0F, 1.0F, 1.0F, skyFeatureBrightness);
                  pass = DimensionType.field_235998_b_.length;
                  moonPhase = dimensionType.func_236035_c_(worldTime);
                  boolean lunarEclipse = dimensionType.isLunarEclipse(world);
                  if (lunarEclipse) {
                     RenderSystem.color3f(1.0F, 0.6F, 0.4F);
                  }

                  texMgr.func_110577_a(MOON_TEXTURE);
                  rMoon = 12.5F;
                  moonUMin = (float)moonPhase / (float)pass;
                  moonUMax = (float)(moonPhase + 1) / (float)pass;
                  moonVMin = 0.0F;
                  float moonVMax = 1.0F;
                  buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
                  buf.func_227888_a_(matrix, -rMoon, -100.0F, rMoon).func_225583_a_(moonUMax, moonVMax).func_181675_d();
                  buf.func_227888_a_(matrix, rMoon, -100.0F, rMoon).func_225583_a_(moonUMin, moonVMax).func_181675_d();
                  buf.func_227888_a_(matrix, rMoon, -100.0F, -rMoon).func_225583_a_(moonUMin, moonVMin).func_181675_d();
                  buf.func_227888_a_(matrix, -rMoon, -100.0F, -rMoon).func_225583_a_(moonUMax, moonVMin).func_181675_d();
                  buf.func_178977_d();
                  WorldVertexBufferUploader.func_181679_a(buf);
                  break;
               }

               label70: {
                  if (pass == 0) {
                     RenderSystem.color4f(1.0F, 1.0F, 1.0F, skyFeatureBrightness);
                  } else if (pass == 1) {
                     if (sunriseColors == null) {
                        break label70;
                     }

                     sunriseBlend = sunriseColors[3];
                     sunriseBlend *= 0.5F;
                     RenderSystem.color4f(1.0F, 0.9F, 0.2F, sunriseBlend * skyFeatureBrightness);
                  }

                  buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
                  buf.func_227888_a_(matrix, -rSun, 100.0F, -rSun).func_225583_a_(0.0F, 0.0F).func_181675_d();
                  buf.func_227888_a_(matrix, rSun, 100.0F, -rSun).func_225583_a_(1.0F, 0.0F).func_181675_d();
                  buf.func_227888_a_(matrix, rSun, 100.0F, rSun).func_225583_a_(1.0F, 1.0F).func_181675_d();
                  buf.func_227888_a_(matrix, -rSun, 100.0F, rSun).func_225583_a_(0.0F, 1.0F).func_181675_d();
                  buf.func_178977_d();
                  WorldVertexBufferUploader.func_181679_a(buf);
               }

               ++pass;
            }
         }

         rSun = celestialAngle - 0.5F;
         float celestialAngleAbs = Math.abs(rSun);
         sunriseBlend = 0.15F;
         eMax = 0.3F;
         if (celestialAngleAbs >= sunriseBlend && celestialAngleAbs <= eMax) {
            rMoon = (sunriseBlend + eMax) / 2.0F;
            moonUMin = eMax - rMoon;
            moonUMax = MathHelper.func_76134_b((celestialAngleAbs - rMoon) / moonUMin * 3.1415927F / 2.0F);
            moonUMax *= moonUMax;
            moonUMax = MathHelper.func_76131_a(moonUMax, 0.0F, 1.0F);
            moonVMin = Math.signum(rSun) * 18.0F;
            matrixStack.func_227860_a_();
            matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(moonVMin));
            Matrix4f matrixE = matrixStack.func_227866_c_().func_227870_a_();
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, moonUMax);
            RenderSystem.enableAlphaTest();
            texMgr.func_110577_a(EARENDIL_TEXTURE);
            float rEarendil = 1.5F;
            buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
            buf.func_227888_a_(matrixE, -rEarendil, 100.0F, -rEarendil).func_225583_a_(0.0F, 0.0F).func_181675_d();
            buf.func_227888_a_(matrixE, rEarendil, 100.0F, -rEarendil).func_225583_a_(1.0F, 0.0F).func_181675_d();
            buf.func_227888_a_(matrixE, rEarendil, 100.0F, rEarendil).func_225583_a_(1.0F, 1.0F).func_181675_d();
            buf.func_227888_a_(matrixE, -rEarendil, 100.0F, rEarendil).func_225583_a_(0.0F, 1.0F).func_181675_d();
            buf.func_178977_d();
            WorldVertexBufferUploader.func_181679_a(buf);
            matrixStack.func_227865_b_();
            RenderSystem.disableAlphaTest();
         }

         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
         RenderSystem.enableAlphaTest();
         RenderSystem.enableFog();
         matrixStack.func_227865_b_();
         RenderSystem.disableTexture();
         RenderSystem.color4f(0.0F, 0.0F, 0.0F, 1.0F);
         double aboveHorizon = mc.field_71439_g.func_174824_e(partialTicks).field_72448_b - world.func_72912_H().func_239159_f_();
         if (aboveHorizon < 0.0D) {
            matrixStack.func_227860_a_();
            matrixStack.func_227861_a_(0.0D, 12.0D, 0.0D);
            this.sky2VBO.func_177359_a();
            this.skyVertexFormat.func_227892_a_(0L);
            this.sky2VBO.func_227874_a_(matrixStack.func_227866_c_().func_227870_a_(), 7);
            VertexBuffer.func_177361_b();
            this.skyVertexFormat.func_227895_d_();
            matrixStack.func_227865_b_();
         }

         if (dimensionRenderInfo.func_239216_b_()) {
            RenderSystem.color3f(skyR * 0.2F + 0.04F, skyG * 0.2F + 0.04F, skyB * 0.6F + 0.1F);
         } else {
            RenderSystem.color3f(skyR, skyG, skyB);
         }

         RenderSystem.enableTexture();
         RenderSystem.depthMask(true);
         RenderSystem.disableFog();
      }

   }

   private void renderSkyboxSide(BufferBuilder buf, MatrixStack matStack, int side) {
      int sideX = side % 3;
      int sideY = side / 3;
      float uMin = (float)sideX / 3.0F;
      float uMax = (float)(sideX + 1) / 3.0F;
      float vMin = (float)sideY / 2.0F;
      float vMax = (float)(sideY + 1) / 2.0F;
      float depth = 100.0F;
      Matrix4f matrix = matStack.func_227866_c_().func_227870_a_();
      buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      buf.func_227888_a_(matrix, -depth, -depth, -depth).func_225583_a_(uMin, vMin).func_181675_d();
      buf.func_227888_a_(matrix, -depth, -depth, depth).func_225583_a_(uMin, vMax).func_181675_d();
      buf.func_227888_a_(matrix, depth, -depth, depth).func_225583_a_(uMax, vMax).func_181675_d();
      buf.func_227888_a_(matrix, depth, -depth, -depth).func_225583_a_(uMax, vMin).func_181675_d();
      buf.func_178977_d();
      WorldVertexBufferUploader.func_181679_a(buf);
   }
}
