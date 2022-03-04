package lotr.client.gui;

import java.util.Iterator;
import java.util.List;
import lotr.client.LOTRClientProxy;
import lotr.common.entity.npc.LOTRSpeech;
import lotr.common.util.LOTRFunctions;
import lotr.common.world.map.LOTRAbstractWaypoint;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class LOTRGuiFastTravel extends LOTRGuiScreenBase {
   private LOTRGuiMap mapGui;
   private LOTRGuiRendererMap mapRenderer;
   private int tickCounter;
   private LOTRAbstractWaypoint theWaypoint;
   private int startX;
   private int startZ;
   private String message;
   private boolean chunkLoaded = false;
   private boolean playedSound = false;
   private static final ResourceLocation ftSound = new ResourceLocation("lotr:event.fastTravel");
   private final float zoomBase;
   private final double mapScaleFactor;
   private float currentZoom;
   private float prevZoom;
   private static final float zoomInAmount = 0.5F;
   private static final float zoomInIncr = 0.008333334F;
   private boolean finishedZoomIn = false;
   private double mapSpeed;
   private double mapVelX;
   private double mapVelY;
   private static final float mapSpeedMax = 2.0F;
   private static final float mapSpeedIncr = 0.01F;
   private static final float mapAccel = 0.2F;
   private boolean reachedWP = false;
   private static final float wpReachedDistance = 1.0F;

   public LOTRGuiFastTravel(LOTRAbstractWaypoint waypoint, int x, int z) {
      this.theWaypoint = waypoint;
      this.startX = x;
      this.startZ = z;
      this.message = LOTRSpeech.getRandomSpeech("fastTravel");
      this.mapGui = new LOTRGuiMap();
      this.mapRenderer = new LOTRGuiRendererMap();
      this.mapRenderer.setSepia(true);
      this.mapRenderer.mapX = (double)LOTRWaypoint.worldToMapX((double)this.startX);
      this.mapRenderer.mapY = (double)LOTRWaypoint.worldToMapZ((double)this.startZ);
      double dx = this.theWaypoint.getX() - this.mapRenderer.mapX;
      double dy = this.theWaypoint.getY() - this.mapRenderer.mapY;
      double distSq = dx * dx + dy * dy;
      double dist = Math.sqrt(distSq);
      this.mapScaleFactor = dist / 100.0D;
      this.zoomBase = -((float)(Math.log(this.mapScaleFactor * 0.30000001192092896D) / Math.log(2.0D)));
      this.currentZoom = this.prevZoom = this.zoomBase + 0.5F;
   }

   public void func_73876_c() {
      if (!this.chunkLoaded && LOTRClientProxy.doesClientChunkExist(this.field_146297_k.field_71441_e, this.theWaypoint.getXCoord(), this.theWaypoint.getZCoord())) {
         this.chunkLoaded = true;
      }

      if (!this.playedSound) {
         this.field_146297_k.func_147118_V().func_147682_a(PositionedSoundRecord.func_147673_a(ftSound));
         this.playedSound = true;
      }

      this.mapRenderer.updateTick();
      ++this.tickCounter;
      this.prevZoom = this.currentZoom;
      if (!this.reachedWP) {
         double dx = this.theWaypoint.getX() - this.mapRenderer.mapX;
         double dy = this.theWaypoint.getY() - this.mapRenderer.mapY;
         double distSq = dx * dx + dy * dy;
         double dist = Math.sqrt(distSq);
         if (dist <= 1.0D * this.mapScaleFactor) {
            this.reachedWP = true;
            this.mapSpeed = 0.0D;
            this.mapVelX = 0.0D;
            this.mapVelY = 0.0D;
         } else {
            this.mapSpeed += 0.009999999776482582D;
            this.mapSpeed = Math.min(this.mapSpeed, 2.0D);
            double vXNew = dx / dist * this.mapSpeed;
            double vYNew = dy / dist * this.mapSpeed;
            double a = 0.20000000298023224D;
            this.mapVelX += (vXNew - this.mapVelX) * a;
            this.mapVelY += (vYNew - this.mapVelY) * a;
         }

         LOTRGuiRendererMap var10000 = this.mapRenderer;
         var10000.mapX += this.mapVelX * this.mapScaleFactor;
         var10000 = this.mapRenderer;
         var10000.mapY += this.mapVelY * this.mapScaleFactor;
         this.currentZoom -= 0.008333334F;
         this.currentZoom = Math.max(this.currentZoom, this.zoomBase);
      } else {
         this.currentZoom += 0.008333334F;
         this.currentZoom = Math.min(this.currentZoom, this.zoomBase + 0.5F);
         if (this.currentZoom >= this.zoomBase + 0.5F) {
            this.finishedZoomIn = true;
         }
      }

      if (this.chunkLoaded && this.reachedWP && this.finishedZoomIn) {
         this.field_146297_k.func_147108_a((GuiScreen)null);
      }

   }

   public void func_146280_a(Minecraft mc, int i, int j) {
      super.func_146280_a(mc, i, j);
      this.mapGui.func_146280_a(mc, i, j);
   }

   protected void func_73869_a(char c, int i) {
      if (this.chunkLoaded && (i == 1 || i == this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i())) {
         this.field_146297_k.field_71439_g.func_71053_j();
      }

   }

   public void func_73863_a(int i, int j, float f) {
      GL11.glEnable(3008);
      GL11.glEnable(3042);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      float zoom = this.prevZoom + (this.currentZoom - this.prevZoom) * f;
      this.mapRenderer.zoomExp = zoom;
      this.mapRenderer.zoomStable = (float)Math.pow(2.0D, (double)this.zoomBase);
      this.mapRenderer.renderMap(this, this.mapGui, f);
      this.mapRenderer.renderVignettes(this, (double)this.field_73735_i, 4);
      GL11.glEnable(3042);
      String title = StatCollector.func_74837_a("lotr.fastTravel.travel", new Object[]{this.theWaypoint.getDisplayName()});
      String titleExtra = (new String[]{"", ".", "..", "..."})[this.tickCounter / 10 % 4];
      List messageLines = this.field_146289_q.func_78271_c(this.message, this.field_146294_l - 100);
      String skipText = StatCollector.func_74837_a("lotr.fastTravel.skip", new Object[]{GameSettings.func_74298_c(this.field_146297_k.field_71474_y.field_151445_Q.func_151463_i())});
      float boxAlpha = 0.5F;
      int boxColor = (int)(boxAlpha * 255.0F) << 24 | 0;
      int fh = this.field_146289_q.field_78288_b;
      int border = fh * 2;
      if (this.chunkLoaded) {
         func_73734_a(0, 0, this.field_146294_l, 0 + border + fh * 3 + border, boxColor);
      } else {
         func_73734_a(0, 0, this.field_146294_l, 0 + border + fh + border, boxColor);
      }

      int messageY = this.field_146295_m - border - messageLines.size() * fh;
      func_73734_a(0, messageY - border, this.field_146294_l, this.field_146295_m, boxColor);
      GL11.glDisable(3042);
      this.field_146289_q.func_78261_a(title + titleExtra, this.field_146294_l / 2 - this.field_146289_q.func_78256_a(title) / 2, 0 + border, 16777215);

      for(Iterator var14 = messageLines.iterator(); var14.hasNext(); messageY += fh) {
         Object obj = var14.next();
         String s1 = (String)obj;
         this.func_73732_a(this.field_146289_q, s1, this.field_146294_l / 2, messageY, 16777215);
      }

      if (this.chunkLoaded) {
         float skipAlpha = LOTRFunctions.triangleWave((float)this.tickCounter + f, 0.3F, 1.0F, 80.0F);
         int skipColor = 16777215 | LOTRClientProxy.getAlphaInt(skipAlpha) << 24;
         GL11.glEnable(3042);
         this.field_146289_q.func_78276_b(skipText, this.field_146294_l / 2 - this.field_146289_q.func_78256_a(skipText) / 2, 0 + border + fh * 2, skipColor);
      }

      GL11.glDisable(3042);
      super.func_73863_a(i, j, f);
   }
}
