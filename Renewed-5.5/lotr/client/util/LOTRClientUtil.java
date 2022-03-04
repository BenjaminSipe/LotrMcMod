package lotr.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import lotr.client.LOTRClientProxy;
import lotr.common.LOTRLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.IRenderTypeBuffer.Impl;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class LOTRClientUtil {
   private static final AbstractGui GUI_BLIT_PROXY = new AbstractGui() {
   };
   private static final Map averagedPageColors = new HashMap();

   public static void blit(MatrixStack matStack, int x, int y, int u, int v, int width, int height) {
      GUI_BLIT_PROXY.func_230926_e_(0);
      GUI_BLIT_PROXY.func_238474_b_(matStack, x, y, u, v, width, height);
   }

   public static void blitFloat(AbstractGui gui, MatrixStack matStack, float x0, float y0, float u0, float v0, float w, float h) {
      blitFloat(matStack, x0, y0, (float)gui.func_230927_p_(), u0, v0, w, h);
   }

   public static void blitFloat(MatrixStack matStack, float x0, float y0, float z, float u0, float v0, float w, float h) {
      blitFloat(matStack, x0, y0, z, u0, v0, w, h, 256, 256);
   }

   public static void blitFloat(MatrixStack matStack, float x0, float y0, float z, float u0, float v0, float w, float h, int texW, int texH) {
      innerBlitFloat(matStack, x0, x0 + w, y0, y0 + h, z, w, h, u0, v0, texW, texH);
   }

   private static void innerBlitFloat(MatrixStack matStack, float x0, float x1, float y0, float y1, float z, float w, float h, float u0, float v0, int texW, int texH) {
      doBlitFloat(matStack.func_227866_c_().func_227870_a_(), x0, x1, y0, y1, z, u0 / (float)texW, (u0 + w) / (float)texW, v0 / (float)texH, (v0 + h) / (float)texH);
   }

   private static void doBlitFloat(Matrix4f matrix, float x0, float x1, float y0, float y1, float z, float u0, float u1, float v0, float v1) {
      BufferBuilder buf = Tessellator.func_178181_a().func_178180_c();
      buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      buf.func_227888_a_(matrix, x0, y1, z).func_225583_a_(u0, v1).func_181675_d();
      buf.func_227888_a_(matrix, x1, y1, z).func_225583_a_(u1, v1).func_181675_d();
      buf.func_227888_a_(matrix, x1, y0, z).func_225583_a_(u1, v0).func_181675_d();
      buf.func_227888_a_(matrix, x0, y0, z).func_225583_a_(u0, v0).func_181675_d();
      buf.func_178977_d();
      RenderSystem.enableAlphaTest();
      WorldVertexBufferUploader.func_181679_a(buf);
   }

   public static int getAlphaInt(float alphaF) {
      int alphaI = (int)(alphaF * 255.0F);
      return MathHelper.func_76125_a(alphaI, 0, 255);
   }

   public static int getAlphaIntForFontRendering(float alphaF) {
      int alphaI = getAlphaInt(alphaF);
      return Math.max(alphaI, 4);
   }

   public static int getRGBA(int rgb, float alphaF) {
      return rgb | getAlphaInt(alphaF) << 24;
   }

   public static int getRGBAForFontRendering(int rgb, float alphaF) {
      return rgb | getAlphaIntForFontRendering(alphaF) << 24;
   }

   public static int doDrawEntityText(FontRenderer fr, IReorderingProcessor rText, int x, int y, int color, boolean dropShadow, Matrix4f matrix, boolean seethrough, int colorBg, int packedLight) {
      Impl irendertypebuffer$impl = IRenderTypeBuffer.func_228455_a_(Tessellator.func_178181_a().func_178180_c());
      int ret = fr.func_238416_a_(rText, (float)x, (float)y, color, dropShadow, matrix, irendertypebuffer$impl, seethrough, colorBg, packedLight);
      irendertypebuffer$impl.func_228461_a_();
      return ret;
   }

   public static int drawSeethroughText(FontRenderer fr, IReorderingProcessor rText, int x, int y, int color, MatrixStack matStack) {
      RenderSystem.enableAlphaTest();
      return doDrawEntityText(fr, rText, x, y, color, false, matStack.func_227866_c_().func_227870_a_(), true, 0, LOTRClientProxy.MAX_LIGHTMAP);
   }

   public static int drawSeethroughText(FontRenderer fr, ITextComponent text, int x, int y, int color, MatrixStack matStack) {
      return drawSeethroughText(fr, text.func_241878_f(), x, y, color, matStack);
   }

   public static int computeAverageFactionPageColor(Minecraft mc, ResourceLocation texture, int u0, int v0, int u1, int v1) {
      if (!averagedPageColors.containsKey(texture)) {
         boolean var6 = false;

         int avgColor;
         try {
            BufferedImage pageImage = ImageIO.read(mc.func_195551_G().func_199002_a(texture).func_199027_b());
            long totalR = 0L;
            long totalG = 0L;
            long totalB = 0L;
            long totalA = 0L;
            int count = 0;

            int u;
            int v;
            int rgb;
            for(u = u0; u < u1; ++u) {
               for(v = v0; v < v1; ++v) {
                  rgb = pageImage.getRGB(u, v);
                  Color color = new Color(rgb);
                  totalR += (long)color.getRed();
                  totalG += (long)color.getGreen();
                  totalB += (long)color.getBlue();
                  totalA += (long)color.getAlpha();
                  ++count;
               }
            }

            u = (int)(totalR / (long)count);
            v = (int)(totalG / (long)count);
            rgb = (int)(totalB / (long)count);
            int avgA = (int)(totalA / (long)count);
            avgColor = (new Color(u, v, rgb, avgA)).getRGB();
         } catch (IOException var21) {
            LOTRLog.error("LOTR: Failed to generate average page colour for %s", texture);
            var21.printStackTrace();
            avgColor = 0;
         }

         averagedPageColors.put(texture, avgColor);
         return avgColor;
      } else {
         return (Integer)averagedPageColors.get(texture);
      }
   }

   public static int findContrastingColor(int text, int bg) {
      Color cText = new Color(text);
      Color cBg = new Color(bg);
      float[] hsbText = Color.RGBtoHSB(cText.getRed(), cText.getGreen(), cText.getBlue(), (float[])null);
      float[] hsbBg = Color.RGBtoHSB(cBg.getRed(), cBg.getGreen(), cBg.getBlue(), (float[])null);
      float bText = hsbText[2];
      float bBg = hsbBg[2];
      float limit = 0.4F;
      if (Math.abs(bText - bBg) < limit) {
         if (bBg > 0.66F) {
            bText = bBg - limit;
         } else {
            bText = bBg + limit;
         }
      }

      return Color.HSBtoRGB(hsbText[0], hsbText[1], bText);
   }

   public static List trimEachLineToWidth(List lines, FontRenderer fr, int stringWidth) {
      return (List)lines.stream().flatMap((line) -> {
         return fr.func_238425_b_(line, stringWidth).stream();
      }).collect(Collectors.toList());
   }

   public static int getPackedNoOverlay() {
      return OverlayTexture.func_229201_a_(OverlayTexture.func_229199_a_(0.0F), OverlayTexture.func_229202_a_(false));
   }

   public static boolean doesClientChunkExist(World world, BlockPos pos) {
      return doesClientChunkExist(world, pos.func_177958_n(), pos.func_177952_p());
   }

   public static boolean doesClientChunkExist(World world, int x, int z) {
      return world.func_217354_b(x >> 4, z >> 4);
   }
}
