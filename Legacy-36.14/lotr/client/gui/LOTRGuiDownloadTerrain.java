package lotr.client.gui;

import lotr.common.LOTRDimension;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiDownloadTerrain extends GuiDownloadTerrain {
   private LOTRGuiMap mapGui = new LOTRGuiMap();
   private LOTRGuiRendererMap mapRenderer = new LOTRGuiRendererMap();
   private static final int mapBorder = 40;
   private static final float MAP_ZOOM = -0.3F;
   private int tickCounter;

   public LOTRGuiDownloadTerrain(NetHandlerPlayClient handler) {
      super(handler);
      this.mapRenderer.setSepia(true);
   }

   public void func_146280_a(Minecraft mc, int i, int j) {
      super.func_146280_a(mc, i, j);
      this.mapGui.func_146280_a(mc, i, j);
   }

   public void func_73876_c() {
      super.func_73876_c();
      ++this.tickCounter;
   }

   public void func_73863_a(int i, int j, float f) {
      int dimension = this.field_146297_k.field_71439_g.field_71093_bK;
      int brightnessI;
      if (dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
         this.func_146278_c(0);
         GL11.glEnable(3008);
         GL11.glEnable(3042);
         OpenGlHelper.func_148821_a(770, 771, 1, 0);
         this.mapRenderer.prevMapX = this.mapRenderer.mapX = (double)LOTRWaypoint.worldToMapX(this.field_146297_k.field_71439_g.field_70165_t);
         this.mapRenderer.prevMapY = this.mapRenderer.mapY = (double)LOTRWaypoint.worldToMapZ(this.field_146297_k.field_71439_g.field_70161_v);
         this.mapRenderer.zoomExp = -0.3F;
         this.mapRenderer.zoomStable = (float)Math.pow(2.0D, -0.30000001192092896D);
         int x0 = 0;
         brightnessI = this.field_146294_l;
         int y0 = 40;
         int y1 = this.field_146295_m - 40;
         this.mapRenderer.renderMap(this, this.mapGui, f, x0, y0, brightnessI, y1);
         this.mapRenderer.renderVignettes(this, (double)this.field_73735_i, 1, x0, y0, brightnessI, y1);
         GL11.glDisable(3042);
         String s = StatCollector.func_74838_a("lotr.loading.enterME");
         this.func_73732_a(this.field_146289_q, s, this.field_146294_l / 2, this.field_146295_m / 2 - 50, 16777215);
      } else if (dimension == LOTRDimension.UTUMNO.dimensionID) {
         func_73734_a(0, 0, this.field_146294_l, this.field_146295_m, -16777216);
         float brightness = 1.0F - (float)this.tickCounter / 120.0F;
         brightnessI = (int)(brightness * 255.0F);
         brightnessI = MathHelper.func_76125_a(brightnessI, 0, 255);
         String s = StatCollector.func_74838_a("lotr.loading.enterUtumno");
         this.func_73732_a(this.field_146289_q, s, this.field_146294_l / 2, this.field_146295_m / 2 - 50, brightnessI << 16 | brightnessI << 8 | brightnessI);
      } else {
         super.func_73863_a(i, j, f);
      }

   }
}
