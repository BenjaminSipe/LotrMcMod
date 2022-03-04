package lotr.client.gui.map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.MapImageTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class MiddleEarthMapRenderer {
   public static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/misc/vignette.png");
   private double mapX;
   private double mapY;
   private double prevMapX;
   private double prevMapY;
   private float zoomExp;
   private float zoomStable;
   private final boolean sepia;
   private final boolean renderFogOfWarIfConfigured;

   public MiddleEarthMapRenderer(boolean sepia, boolean fog) {
      this.sepia = sepia;
      this.renderFogOfWarIfConfigured = fog;
   }

   public void setInstantaneousPosition(double x, double y) {
      this.prevMapX = this.mapX = x;
      this.prevMapY = this.mapY = y;
   }

   public void moveTo(double x, double y) {
      this.mapX = x;
      this.mapY = y;
   }

   public void moveBy(double dx, double dy) {
      this.moveTo(this.mapX + dx, this.mapY + dy);
   }

   public double getMapX() {
      return this.mapX;
   }

   public double getMapY() {
      return this.mapY;
   }

   public void setZoomExp(float z) {
      this.zoomExp = z;
   }

   public void setStableZoom(float z) {
      this.zoomStable = z;
   }

   public void tick() {
      this.prevMapX = this.mapX;
      this.prevMapY = this.mapY;
   }

   public void renderMap(MatrixStack matStack, Screen gui, MiddleEarthMapScreen mapGui, float tick) {
      this.renderMap(matStack, gui, mapGui, tick, 0, 0, gui.field_230708_k_, gui.field_230709_l_);
   }

   public void renderMap(MatrixStack matStack, Screen gui, MiddleEarthMapScreen mapGui, float tick, int x0, int y0, int x1, int y1) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      int seaColor = MapImageTextures.INSTANCE.getMapBackgroundColor(this.sepia);
      Screen.func_238467_a_(matStack, x0, y0, x1, y1, seaColor);
      float zoom = (float)Math.pow(2.0D, (double)this.zoomExp);
      double mapPosX = this.prevMapX + (this.mapX - this.prevMapX) * (double)tick;
      double mapPosY = this.prevMapY + (this.mapY - this.prevMapY) * (double)tick;
      mapGui.setMapViewportAndPositionAndScale(x0, x1, y0, y1, mapPosX, mapPosY, zoom, this.zoomExp, this.zoomStable);
      mapGui.enableZoomOutObjectFading = false;
      mapGui.func_230926_e_(gui.func_230927_p_());
      mapGui.renderMapAndOverlay(matStack, tick, this.sepia, 1.0F, true);
      mapGui.renderRoads(matStack, tick, false);
      if (this.renderFogOfWarIfConfigured) {
         mapGui.renderFogOfWar(matStack, tick);
      }

      mapGui.renderWaypoints(matStack, 0, 0, 0, tick, false);
   }

   public void renderVignette(MatrixStack matStack, Screen gui, float zLevel) {
      this.renderVignette(matStack, gui, zLevel, 0, 0, gui.field_230708_k_, gui.field_230709_l_);
   }

   public void renderVignette(MatrixStack matStack, Screen gui, float zLevel, int x0, int y0, int x1, int y1) {
      Minecraft.func_71410_x().func_110434_K().func_110577_a(VIGNETTE_TEXTURE);
      float alpha = 1.0F;
      RenderSystem.color4f(alpha, alpha, alpha, 1.0F);
      float u0 = (float)x0 / (float)gui.field_230708_k_;
      float u1 = (float)x1 / (float)gui.field_230708_k_;
      float v0 = (float)y0 / (float)gui.field_230709_l_;
      float v1 = (float)y1 / (float)gui.field_230709_l_;
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(SourceFactor.ONE_MINUS_SRC_ALPHA, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO);
      Matrix4f mat = matStack.func_227866_c_().func_227870_a_();
      Tessellator tess = Tessellator.func_178181_a();
      BufferBuilder buf = tess.func_178180_c();
      buf.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      buf.func_227888_a_(mat, (float)x0, (float)y1, zLevel).func_225583_a_(u0, v1).func_181675_d();
      buf.func_227888_a_(mat, (float)x1, (float)y1, zLevel).func_225583_a_(u1, v1).func_181675_d();
      buf.func_227888_a_(mat, (float)x1, (float)y0, zLevel).func_225583_a_(u1, v0).func_181675_d();
      buf.func_227888_a_(mat, (float)x0, (float)y0, zLevel).func_225583_a_(u0, v0).func_181675_d();
      tess.func_78381_a();
      RenderSystem.defaultBlendFunc();
   }

   public void renderVignettes(MatrixStack matStack, Screen gui, float zLevel, int count) {
      for(int l = 0; l < count; ++l) {
         this.renderVignette(matStack, gui, zLevel);
      }

   }

   public void renderVignettes(MatrixStack matStack, Screen gui, float zLevel, int count, int x0, int y0, int x1, int y1) {
      for(int l = 0; l < count; ++l) {
         this.renderVignette(matStack, gui, zLevel, x0, y0, x1, y1);
      }

   }
}
