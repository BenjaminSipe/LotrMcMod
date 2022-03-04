package lotr.client.gui;

import lotr.client.LOTRTextures;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LOTRGuiRendererMap {
   private static final ResourceLocation vignetteTexture = new ResourceLocation("textures/misc/vignette.png");
   public double prevMapX;
   public double mapX;
   public double prevMapY;
   public double mapY;
   public float zoomExp;
   public float zoomStable;
   private boolean sepia = false;

   public void setSepia(boolean flag) {
      this.sepia = flag;
   }

   public void updateTick() {
      this.prevMapX = this.mapX;
      this.prevMapY = this.mapY;
   }

   public void renderMap(GuiScreen gui, LOTRGuiMap mapGui, float f) {
      this.renderMap(gui, mapGui, f, 0, 0, gui.field_146294_l, gui.field_146295_m);
   }

   public void renderMap(GuiScreen gui, LOTRGuiMap mapGui, float f, int x0, int y0, int x1, int y1) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      int oceanColor = LOTRTextures.getMapOceanColor(this.sepia);
      GuiScreen.func_73734_a(x0, y0, x1, y1, oceanColor);
      float zoom = (float)Math.pow(2.0D, (double)this.zoomExp);
      double mapPosX = this.prevMapX + (this.mapX - this.prevMapX) * (double)f;
      double mapPosY = this.prevMapY + (this.mapY - this.prevMapY) * (double)f;
      mapGui.setFakeMapProperties((float)mapPosX, (float)mapPosY, zoom, this.zoomExp, this.zoomStable);
      int[] statics = LOTRGuiMap.setFakeStaticProperties(x1 - x0, y1 - y0, x0, x1, y0, y1);
      mapGui.enableZoomOutWPFading = false;
      mapGui.renderMapAndOverlay(this.sepia, 1.0F, true);
      mapGui.renderRoads(false);
      mapGui.renderWaypoints(LOTRWaypoint.listAllWaypoints(), 0, 0, 0, false, true);
      LOTRGuiMap.setFakeStaticProperties(statics[0], statics[1], statics[2], statics[3], statics[4], statics[5]);
   }

   public void renderVignette(GuiScreen gui, double zLevel) {
      this.renderVignette(gui, zLevel, 0, 0, gui.field_146294_l, gui.field_146295_m);
   }

   public void renderVignette(GuiScreen gui, double zLevel, int x0, int y0, int x1, int y1) {
      GL11.glEnable(3042);
      OpenGlHelper.func_148821_a(771, 769, 1, 0);
      float alpha = 1.0F;
      GL11.glColor4f(alpha, alpha, alpha, 1.0F);
      gui.field_146297_k.func_110434_K().func_110577_a(vignetteTexture);
      double u0 = (double)x0 / (double)gui.field_146294_l;
      double u1 = (double)x1 / (double)gui.field_146294_l;
      double v0 = (double)y0 / (double)gui.field_146295_m;
      double v1 = (double)y1 / (double)gui.field_146295_m;
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78382_b();
      tessellator.func_78374_a((double)x0, (double)y1, zLevel, u0, v1);
      tessellator.func_78374_a((double)x1, (double)y1, zLevel, u1, v1);
      tessellator.func_78374_a((double)x1, (double)y0, zLevel, u1, v0);
      tessellator.func_78374_a((double)x0, (double)y0, zLevel, u0, v0);
      tessellator.func_78381_a();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
   }

   public void renderVignettes(GuiScreen gui, double zLevel, int count) {
      for(int l = 0; l < count; ++l) {
         this.renderVignette(gui, zLevel);
      }

   }

   public void renderVignettes(GuiScreen gui, double zLevel, int count, int x0, int y0, int x1, int y1) {
      for(int l = 0; l < count; ++l) {
         this.renderVignette(gui, zLevel, x0, y0, x1, y1);
      }

   }
}
