package lotr.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;
import lotr.common.LOTRLog;
import lotr.common.config.LOTRConfig;
import lotr.common.world.map.MapSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.NativeImage.PixelFormat;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

public class MapImageTextures implements ISelectiveResourceReloadListener {
   private static Minecraft mc = Minecraft.func_71410_x();
   private static final ResourceLocation MAP_TEXTURE = new ResourceLocation("lotr", "textures/map/loaded_map.png");
   private static final ResourceLocation SEPIA_MAP_TEXTURE = new ResourceLocation("lotr", "textures/map/loaded_map_sepia.png");
   private ResourceLocation currentMapImagePath;
   private int backgroundColor;
   private int sepiaBackgroundColor;
   public static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation("lotr", "textures/map/overlay.png");
   public static final ResourceLocation MAP_ICONS = new ResourceLocation("lotr", "textures/map/screen.png");
   public static final ResourceLocation MAP_TERRAIN = new ResourceLocation("lotr", "textures/map/terrain.png");
   public static final ResourceLocation FOG_OF_WAR_TEXTURE = new ResourceLocation("lotr", "textures/map/fog_of_war.png");
   public static final ResourceLocation SEPIA_FOG_OF_WAR_TEXTURE = new ResourceLocation("lotr", "textures/map/fog_of_war_sepia.png");
   public static final MapImageTextures INSTANCE = new MapImageTextures();
   public static final ResourceLocation OSRS_ICONS = new ResourceLocation("lotr", "map/osrs.png");
   public static final int OSRS_WATER = 6453158;
   public static final int OSRS_GRASS = 5468426;
   public static final int OSRS_BEACH = 9279778;
   public static final int OSRS_HILL = 6575407;
   public static final int OSRS_MOUNTAIN = 14736861;
   public static final int OSRS_MOUNTAIN_EDGE = 9005125;
   public static final int OSRS_SNOW = 14215139;
   public static final int OSRS_TUNDRA = 9470587;
   public static final int OSRS_SAND = 13548147;
   public static final int OSRS_TREE = 2775058;
   public static final int OSRS_WILD = 3290677;
   public static final int OSRS_PATH = 6575407;
   public static final int OSRS_KINGDOM_COLOR = 16755200;

   private MapImageTextures() {
      IReloadableResourceManager resMgr = (IReloadableResourceManager)mc.func_195551_G();
      resMgr.func_219534_a(this);
   }

   public void onResourceManagerReload(IResourceManager resMgr, Predicate resPredicate) {
      if (resPredicate.test(VanillaResourceType.TEXTURES)) {
         this.currentMapImagePath = null;
         this.backgroundColor = 0;
         this.sepiaBackgroundColor = 0;
      }

   }

   public void loadMapTexturesIfNew(MapSettings mapSettings) {
      if (this.currentMapImagePath == null || !mapSettings.getMapImagePath().equals(this.currentMapImagePath)) {
         try {
            this.currentMapImagePath = mapSettings.getMapImagePath();
            NativeImage mapImage = this.readMapImageOrOverride(mapSettings);
            NativeImage sepiaImage = convertToSepia(mapImage);
            this.backgroundColor = this.determineBackgroundColor(mapImage);
            this.sepiaBackgroundColor = this.determineBackgroundColor(sepiaImage);
            mc.func_110434_K().func_229263_a_(MAP_TEXTURE, new DynamicTexture(mapImage));
            mc.func_110434_K().func_229263_a_(SEPIA_MAP_TEXTURE, new DynamicTexture(sepiaImage));
         } catch (IOException var4) {
            LOTRLog.error("Failed to load map image textures for %s", mapSettings.getMapImagePath());
            var4.printStackTrace();
         }

      }
   }

   private NativeImage readMapImageOrOverride(MapSettings mapSettings) throws IOException {
      InputStream overrideStream = this.overrideExists(mapSettings.getMapImagePath());
      return overrideStream != null ? NativeImage.func_195713_a(overrideStream) : NativeImage.func_195713_a(mapSettings.createCachedImageInputStream());
   }

   private InputStream overrideExists(ResourceLocation mapImage) throws IOException {
      ResourceLocation potentialOverride = new ResourceLocation(mapImage.func_110624_b(), "textures/mapoverride/" + mapImage.func_110623_a());
      IResourceManager resMgr = mc.func_195551_G();
      return resMgr.func_219533_b(potentialOverride) ? resMgr.func_199002_a(potentialOverride).func_199027_b() : null;
   }

   public static void drawMap(MatrixStack matStack, PlayerEntity player, float x0, float x1, float y0, float y1, int z, float minU, float maxU, float minV, float maxV) {
      boolean sepia = (Boolean)LOTRConfig.CLIENT.sepiaMap.get();
      drawMap(matStack, player, sepia, x0, x1, y0, y1, z, minU, maxU, minV, maxV, 1.0F);
   }

   public static void drawMap(MatrixStack matStack, PlayerEntity player, boolean sepia, float x0, float x1, float y0, float y1, int z, float minU, float maxU, float minV, float maxV, float alpha) {
      Matrix4f mat = matStack.func_227866_c_().func_227870_a_();
      Tessellator tess = Tessellator.func_178181_a();
      BufferBuilder buf = tess.func_178180_c();
      mc.func_110434_K().func_110577_a(getMapTexture(player, sepia));
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
      buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      buf.func_227888_a_(mat, x0, y1, (float)z).func_225583_a_(minU, maxV).func_181675_d();
      buf.func_227888_a_(mat, x1, y1, (float)z).func_225583_a_(maxU, maxV).func_181675_d();
      buf.func_227888_a_(mat, x1, y0, (float)z).func_225583_a_(maxU, minV).func_181675_d();
      buf.func_227888_a_(mat, x0, y0, (float)z).func_225583_a_(minU, minV).func_181675_d();
      tess.func_78381_a();
   }

   public static void drawMapOverlay(MatrixStack matStack, PlayerEntity entityplayer, float x0, float x1, float y0, float y1, float z, float minU, float maxU, float minV, float maxV) {
      Matrix4f mat = matStack.func_227866_c_().func_227870_a_();
      Tessellator tess = Tessellator.func_178181_a();
      BufferBuilder buf = tess.func_178180_c();
      mc.func_110434_K().func_110577_a(OVERLAY_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.2F);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      buf.func_227888_a_(mat, x0, y1, z).func_225583_a_(0.0F, 1.0F).func_181675_d();
      buf.func_227888_a_(mat, x1, y1, z).func_225583_a_(1.0F, 1.0F).func_181675_d();
      buf.func_227888_a_(mat, x1, y0, z).func_225583_a_(1.0F, 0.0F).func_181675_d();
      buf.func_227888_a_(mat, x0, y0, z).func_225583_a_(0.0F, 0.0F).func_181675_d();
      tess.func_78381_a();
   }

   public static void drawMapCompassBottomLeft(MatrixStack matStack, float x, float y, float z, float scale) {
      mc.func_110434_K().func_110577_a(MAP_ICONS);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      Matrix4f mat = matStack.func_227866_c_().func_227870_a_();
      int width = 32;
      int height = 32;
      float x1 = x + (float)width * scale;
      float y0 = y - (float)height * scale;
      int texU = 224;
      int texV = 200;
      float u0 = (float)texU / 256.0F;
      float u1 = (float)(texU + width) / 256.0F;
      float v0 = (float)texV / 256.0F;
      float v1 = (float)(texV + height) / 256.0F;
      Tessellator tess = Tessellator.func_178181_a();
      BufferBuilder buf = tess.func_178180_c();
      buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      buf.func_227888_a_(mat, x, y, z).func_225583_a_(u0, v1).func_181675_d();
      buf.func_227888_a_(mat, x1, y, z).func_225583_a_(u1, v1).func_181675_d();
      buf.func_227888_a_(mat, x1, y0, z).func_225583_a_(u1, v0).func_181675_d();
      buf.func_227888_a_(mat, x, y0, z).func_225583_a_(u0, v0).func_181675_d();
      tess.func_78381_a();
   }

   private static ResourceLocation getMapTexture(PlayerEntity player, boolean sepia) {
      return sepia ? SEPIA_MAP_TEXTURE : MAP_TEXTURE;
   }

   public int getMapBackgroundColor(boolean sepia) {
      return sepia ? this.sepiaBackgroundColor : this.backgroundColor;
   }

   private static NativeImage convertToSepia(NativeImage srcImage) {
      int mapWidth = srcImage.func_195702_a();
      int mapHeight = srcImage.func_195714_b();
      int[] colors = new int[mapWidth * mapHeight];
      NativeImage newMapImage = new NativeImage(PixelFormat.RGBA, mapWidth, mapHeight, true);

      for(int y = 0; y < mapHeight; ++y) {
         for(int x = 0; x < mapWidth; ++x) {
            int colorNative = srcImage.func_195709_a(x, y);
            int colorRGB = nativeImageColorToNormal(colorNative);
            colorRGB = getSepia(colorRGB);
            colorNative = normalColorToNative(colorRGB);
            newMapImage.func_195700_a(x, y, colorNative);
         }
      }

      return newMapImage;
   }

   private static int nativeImageColorToNormal(int abgr) {
      int a = abgr >> 24 & 255;
      int b = abgr >> 16 & 255;
      int g = abgr >> 8 & 255;
      int r = abgr & 255;
      return a << 24 | r << 16 | g << 8 | b;
   }

   private static int normalColorToNative(int argb) {
      int a = argb >> 24 & 255;
      int r = argb >> 16 & 255;
      int g = argb >> 8 & 255;
      int b = argb & 255;
      return a << 24 | b << 16 | g << 8 | r;
   }

   private static int getSepia(int rgb) {
      Color color = new Color(rgb);
      int alpha = rgb >> 24 & 255;
      float[] colors = color.getColorComponents((float[])null);
      float r = colors[0];
      float g = colors[1];
      float b = colors[2];
      float newR = r * 0.79F + g * 0.39F + b * 0.26F;
      float newG = r * 0.52F + g * 0.35F + b * 0.19F;
      float newB = r * 0.35F + g * 0.26F + b * 0.15F;
      newR = MathHelper.func_76131_a(newR, 0.0F, 1.0F);
      newG = MathHelper.func_76131_a(newG, 0.0F, 1.0F);
      newB = MathHelper.func_76131_a(newB, 0.0F, 1.0F);
      int sepia = (new Color(newR, newG, newB)).getRGB();
      sepia |= alpha << 24;
      return sepia;
   }

   private int determineBackgroundColor(NativeImage mapImage) {
      int colorNative = mapImage.func_195709_a(0, 0);
      int colorRGB = nativeImageColorToNormal(colorNative);
      return colorRGB;
   }
}
