package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;
import lotr.client.LOTRClientProxy;
import lotr.client.gui.map.MiddleEarthMapRenderer;
import lotr.client.gui.map.MiddleEarthMapScreen;
import lotr.client.util.LOTRClientUtil;
import lotr.common.init.LOTRSoundEvents;
import lotr.common.util.LOTRUtil;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import lotr.common.world.map.Waypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FastTravelScreen extends BasicIngameScreen {
   private MiddleEarthMapScreen mapGui;
   private MiddleEarthMapRenderer mapRenderer;
   private int tickCounter;
   private Waypoint theWaypoint;
   private int startX;
   private int startZ;
   private boolean chunkLoaded = false;
   private boolean playedSound = false;
   private static final ResourceLocation FAST_TRAVEL_QUOTES = new ResourceLocation("lotr", "fast_travel");
   private ITextComponent loadingQuote;
   private final float zoomBase;
   private final double mapScaleFactor;
   private float currentZoom;
   private float prevZoom;
   private static final float ZOOM_IN_AMOUNT = 0.5F;
   private static final float ZOOM_INCREMENT = 0.008333334F;
   private boolean finishedZoomIn = false;
   private double mapSpeed;
   private double mapVelX;
   private double mapVelY;
   private static final float MAP_SPEED_MAX = 2.0F;
   private static final float MAP_SPEED_INCREMENT = 0.01F;
   private static final float MAP_ACCEL = 0.2F;
   private boolean reachedWP = false;
   private static final float REACHED_WP_DISTANCE = 1.0F;

   public FastTravelScreen(Waypoint waypoint, int x, int z) {
      super(new StringTextComponent("TODO - FAST TRAVEL"));
      this.theWaypoint = waypoint;
      this.startX = x;
      this.startZ = z;
      this.loadingQuote = LOTRClientProxy.getQuoteListLoader().getRandomQuoteComponent(FAST_TRAVEL_QUOTES);
      this.mapGui = new MiddleEarthMapScreen();
      this.mapRenderer = new MiddleEarthMapRenderer(true, true);
      MapSettings mapSettings = MapSettingsManager.clientInstance().getCurrentLoadedMap();
      this.mapRenderer.setInstantaneousPosition(mapSettings.worldToMapX_frac((double)this.startX), mapSettings.worldToMapZ_frac((double)this.startZ));
      double dx = this.theWaypoint.getMapX() - this.mapRenderer.getMapX();
      double dy = this.theWaypoint.getMapZ() - this.mapRenderer.getMapY();
      double distSq = dx * dx + dy * dy;
      double dist = Math.sqrt(distSq);
      this.mapScaleFactor = dist / 100.0D;
      this.zoomBase = -((float)(Math.log(this.mapScaleFactor * 0.30000001192092896D) / Math.log(2.0D)));
      this.currentZoom = this.prevZoom = this.zoomBase + 0.5F;
      this.mapRenderer.setStableZoom((float)Math.pow(2.0D, (double)this.zoomBase));
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      if (!this.chunkLoaded && LOTRClientUtil.doesClientChunkExist(this.field_230706_i_.field_71441_e, this.theWaypoint.getWorldX(), this.theWaypoint.getWorldZ())) {
         this.chunkLoaded = true;
      }

      if (!this.playedSound) {
         this.field_230706_i_.func_147118_V().func_147682_a(SimpleSound.func_239530_b_(LOTRSoundEvents.FAST_TRAVEL));
         this.playedSound = true;
      }

      this.mapRenderer.tick();
      ++this.tickCounter;
      this.prevZoom = this.currentZoom;
      if (!this.reachedWP) {
         double dx = this.theWaypoint.getMapX() - this.mapRenderer.getMapX();
         double dy = this.theWaypoint.getMapZ() - this.mapRenderer.getMapY();
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

         this.mapRenderer.moveBy(this.mapVelX * this.mapScaleFactor, this.mapVelY * this.mapScaleFactor);
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
         this.field_230706_i_.func_147108_a((Screen)null);
      }

   }

   public void func_231158_b_(Minecraft mc, int i, int j) {
      super.func_231158_b_(mc, i, j);
      this.mapGui.func_231158_b_(mc, i, j);
   }

   public boolean func_231178_ax__() {
      return this.chunkLoaded;
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float f) {
      RenderSystem.enableAlphaTest();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      f = Math.min(f, 1.0F);
      this.mapRenderer.setZoomExp(this.prevZoom + (this.currentZoom - this.prevZoom) * f);
      this.mapRenderer.renderMap(matStack, this, this.mapGui, f);
      this.mapRenderer.renderVignettes(matStack, this, (float)this.func_230927_p_(), 4);
      RenderSystem.enableBlend();
      ITextComponent title = new TranslationTextComponent("gui.lotr.fasttravel.travel", new Object[]{this.theWaypoint.getDisplayName()});
      int numEllipses = this.tickCounter / 10 % 4;
      String titleEllipsis = (new String(new char[numEllipses])).replace("\u0000", ".");
      ITextComponent fullTitle = new TranslationTextComponent("%s%s", new Object[]{title, titleEllipsis});
      List loadingQuoteLines = this.field_230712_o_.func_238425_b_(this.loadingQuote, this.field_230708_k_ - 100);
      float boxAlpha = 0.5F;
      int boxColor = LOTRClientUtil.getRGBA(0, boxAlpha);
      this.field_230712_o_.getClass();
      int fh = 9;
      int border = fh * 2;
      if (this.chunkLoaded) {
         func_238467_a_(matStack, 0, 0, this.field_230708_k_, 0 + border + fh * 3 + border, boxColor);
      } else {
         func_238467_a_(matStack, 0, 0, this.field_230708_k_, 0 + border + fh + border, boxColor);
      }

      int messageY = this.field_230709_l_ - border - loadingQuoteLines.size() * fh;
      func_238467_a_(matStack, 0, messageY - border, this.field_230708_k_, this.field_230709_l_, boxColor);
      RenderSystem.disableBlend();
      this.field_230712_o_.func_243246_a(matStack, fullTitle, (float)(this.field_230708_k_ / 2 - this.field_230712_o_.func_238414_a_(title) / 2), (float)(0 + border), 16777215);

      for(Iterator var15 = loadingQuoteLines.iterator(); var15.hasNext(); messageY += fh) {
         IReorderingProcessor line = (IReorderingProcessor)var15.next();
         this.field_230712_o_.func_238407_a_(matStack, line, (float)(this.field_230708_k_ / 2 - this.field_230712_o_.func_243245_a(line) / 2), (float)messageY, 16777215);
      }

      if (this.chunkLoaded) {
         ITextComponent skipText = new TranslationTextComponent("gui.lotr.fasttravel.skip", new Object[]{this.field_230706_i_.field_71474_y.field_151445_Q.func_238171_j_()});
         float skipAlpha = LOTRUtil.normalisedTriangleWave((float)this.tickCounter + f, 160.0F, 0.3F, 1.0F);
         int skipColor = LOTRClientUtil.getRGBAForFontRendering(16777215, skipAlpha);
         RenderSystem.enableBlend();
         this.field_230712_o_.func_243248_b(matStack, skipText, (float)(this.field_230708_k_ / 2 - this.field_230712_o_.func_238414_a_(skipText) / 2), (float)(0 + border + fh * 2), skipColor);
      }

      RenderSystem.disableBlend();
      super.func_230430_a_(matStack, mouseX, mouseY, f);
   }
}
