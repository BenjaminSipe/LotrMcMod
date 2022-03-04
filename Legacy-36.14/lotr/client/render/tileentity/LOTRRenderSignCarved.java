package lotr.client.render.tileentity;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import lotr.common.tileentity.LOTRTileEntitySignCarved;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class LOTRRenderSignCarved extends TileEntitySpecialRenderer implements IResourceManagerReloadListener {
   private BufferedImage cachedBlockAtlasImage;
   private final Map iconAverageColors = new HashMap();
   private final Map iconContrastColors = new HashMap();

   public void func_110549_a(IResourceManager resourceManager) {
      this.cachedBlockAtlasImage = null;
      this.iconAverageColors.clear();
      this.iconContrastColors.clear();
   }

   public void func_147500_a(TileEntity tileentity, double d, double d1, double d2, float f) {
      LOTRTileEntitySignCarved sign = (LOTRTileEntitySignCarved)tileentity;
      int meta = tileentity.func_145832_p();
      float rotation = (float)Direction.field_71579_d[meta] * 90.0F;
      float f1 = 0.6666667F;
      float f3 = 0.016666668F * f1;
      GL11.glDisable(32826);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)d + 0.5F, (float)d1 + 0.75F * f1, (float)d2 + 0.5F);
      GL11.glRotatef(-rotation, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
      GL11.glTranslatef(0.0F, 0.5F * f1, -0.09F * f1);
      GL11.glScalef(f3, -f3, f3);
      GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
      GL11.glDepthMask(false);
      FontRenderer fontrenderer = this.func_147498_b();
      int color = this.getTextColor(sign, f);
      int signLines = sign.signText.length;
      int lineHeight = fontrenderer.field_78288_b + 1;
      int lineBase = -signLines * 5;
      if (signLines > 4) {
         lineBase = -((signLines - 1) * lineHeight) / 2;
      }

      for(int l = 0; l < signLines; ++l) {
         String s = sign.signText[l];
         if (l == sign.lineBeingEdited) {
            s = "> " + s + " <";
         }

         int lineX = -fontrenderer.func_78256_a(s) / 2;
         int lineY = lineBase + l * lineHeight;
         fontrenderer.func_78276_b(s, lineX, lineY, color);
      }

      GL11.glDepthMask(true);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   protected int getTextColor(LOTRTileEntitySignCarved sign, float f) {
      return this.getContrastingColor(sign.getOnBlockIcon());
   }

   private int getContrastingColor(IIcon icon) {
      if (this.iconContrastColors.containsKey(icon)) {
         return (Integer)this.iconContrastColors.get(icon);
      } else {
         int color = this.averageIconColor(icon);
         color = this.calculateContrast(icon, color);
         this.iconContrastColors.put(icon, color);
         return color;
      }
   }

   private int calculateContrast(IIcon icon, int color) {
      Color c = new Color(color);
      float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), (float[])null);
      float h = hsb[0];
      float s = hsb[1];
      float b = hsb[2];
      s *= 0.5F;
      if (b > 0.6F) {
         b -= 0.6F;
      } else {
         b += 0.4F;
      }

      b = MathHelper.func_76131_a(b, 0.0F, 1.0F);
      return Color.HSBtoRGB(h, s, b);
   }

   private int averageIconColor(IIcon icon) {
      if (this.iconAverageColors.containsKey(icon)) {
         return (Integer)this.iconAverageColors.get(icon);
      } else {
         if (this.cachedBlockAtlasImage == null) {
            this.cachedBlockAtlasImage = this.loadAndCacheBlockTextureAtlas();
         }

         int width = this.cachedBlockAtlasImage.getWidth();
         int height = this.cachedBlockAtlasImage.getHeight();
         int u0 = (int)Math.round((double)icon.func_94209_e() * (double)width);
         int u1 = (int)Math.round((double)icon.func_94212_f() * (double)width);
         int v0 = (int)Math.round((double)icon.func_94206_g() * (double)height);
         int v1 = (int)Math.round((double)icon.func_94210_h() * (double)height);
         int totalR = 0;
         int totalG = 0;
         int totalB = 0;
         int count = 0;

         int y;
         int x;
         int rgb;
         int r;
         for(y = v0; y < v1; ++y) {
            for(x = u0; x < u1; ++x) {
               rgb = this.cachedBlockAtlasImage.getRGB(x, y);
               r = rgb >> 16 & 255;
               int g = rgb >> 8 & 255;
               int b = rgb >> 0 & 255;
               totalR += r;
               totalG += g;
               totalB += b;
               ++count;
            }
         }

         y = totalR / count & 255;
         x = totalG / count & 255;
         rgb = totalB / count & 255;
         r = -16777216 | y << 16 | x << 8 | rgb << 0;
         this.iconAverageColors.put(icon, r);
         return r;
      }
   }

   private BufferedImage loadAndCacheBlockTextureAtlas() {
      Minecraft mc = Minecraft.func_71410_x();
      mc.func_110434_K().func_110577_a(TextureMap.field_110575_b);
      int mipmapLvl = 0;
      int width = GL11.glGetTexLevelParameteri(3553, mipmapLvl, 4096);
      int height = GL11.glGetTexLevelParameteri(3553, mipmapLvl, 4097);
      int pixelSize = width * height;
      BufferedImage atlasImage = new BufferedImage(width, height, 2);
      IntBuffer buffer = BufferUtils.createIntBuffer(pixelSize);
      int[] imgData = new int[pixelSize];
      GL11.glGetTexImage(3553, 0, 32993, 33639, buffer);
      buffer.get(imgData);
      atlasImage.setRGB(0, 0, width, height, imgData, 0, width);
      return atlasImage;
   }
}
