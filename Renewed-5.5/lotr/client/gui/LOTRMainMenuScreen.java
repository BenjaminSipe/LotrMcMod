package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.client.gui.map.MiddleEarthMapRenderer;
import lotr.client.gui.map.MiddleEarthMapScreen;
import lotr.client.gui.widget.button.RedBookButton;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import lotr.common.world.map.MapWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.gui.NotificationModUpdateScreen;
import net.minecraftforge.fml.BrandingControl;

public class LOTRMainMenuScreen extends MainMenuScreen {
   private static final ResourceLocation TITLE_TEXTURE = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation TITLE_EDITION = new ResourceLocation("textures/gui/title/edition.png");
   private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
   private boolean fadeIn = false;
   private static boolean isFirstMenu = true;
   private long firstRenderTime;
   private boolean minceraft;
   private String splashText;
   private String copyrightText = "Copyright Mojang AB. Do not distribute!";
   private int widthCopyright;
   private int widthCopyrightRest;
   private NotificationModUpdateScreen modUpdateNotification;
   private static final IFormattableTextComponent MOD_TITLE = new TranslationTextComponent("lotr.menu.title");
   private static final IFormattableTextComponent MOD_SUBTITLE;
   private MiddleEarthMapScreen mapGui;
   private static MiddleEarthMapRenderer mapRenderer;
   private static int tickCounter;
   private static Random rand;
   private static List waypointRoute;
   private static int currentWPIndex;
   private static boolean randomWPStart;
   private static float mapSpeed;
   private static float mapVelX;
   private static float mapVelY;
   private static final float wpChangeDistance = 12.0F;
   private static final float mapSpeedMax = 0.8F;
   private static final float mapSpeedIncr = 0.01F;
   private static final float mapAccel = 0.02F;
   private static final float zoomBase = -0.1F;
   private static final float zoomOscilSpeed = 0.003F;
   private static final float zoomOscilMax = 0.8F;

   public LOTRMainMenuScreen() {
      this.fadeIn = isFirstMenu;
      isFirstMenu = false;
      this.minceraft = (double)(new Random()).nextFloat() < 1.0E-4D;
      this.mapGui = new MiddleEarthMapScreen();
      mapRenderer = new MiddleEarthMapRenderer(false, false);
      mapRenderer.setStableZoom((float)Math.pow(2.0D, -0.10000000149011612D));
      setupWaypoints();
      if (!waypointRoute.isEmpty()) {
         if (randomWPStart) {
            currentWPIndex = rand.nextInt(waypointRoute.size());
         } else {
            currentWPIndex = 0;
         }

         MapWaypoint wp = (MapWaypoint)waypointRoute.get(currentWPIndex);
         mapRenderer.setInstantaneousPosition(wp.getMapX(), wp.getMapZ());
      } else {
         MapSettings mapSettings = MapSettingsManager.clientInstance().getCurrentLoadedMap();
         mapRenderer.setInstantaneousPosition((double)mapSettings.getOriginX(), (double)mapSettings.getOriginZ());
      }

   }

   private static void setupWaypoints() {
      waypointRoute.clear();
      MapSettings mapSettings = MapSettingsManager.clientInstance().getCurrentLoadedMap();
      waypointRoute.addAll(mapSettings.getMenuWaypointRoute());
   }

   public void func_231160_c_() {
      super.func_231160_c_();
      if (this.splashText == null) {
         this.splashText = this.field_230706_i_.func_213269_at().func_215276_a();
      }

      this.widthCopyright = this.field_230712_o_.func_78256_a(this.copyrightText);
      this.widthCopyrightRest = this.field_230708_k_ - this.widthCopyright - 2;
      Button modbutton = (Button)this.field_230710_m_.stream().filter((widget) -> {
         return widget instanceof Button && widget.func_230458_i_().getString().equals((new TranslationTextComponent("fml.menu.mods")).getString());
      }).findFirst().orElse((Object)null);
      this.modUpdateNotification = NotificationModUpdateScreen.init(this, modbutton);
      int lowerButtonMaxY = 0;
      Iterator var3 = this.field_230710_m_.iterator();

      int moveDown;
      while(var3.hasNext()) {
         Widget button = (Widget)var3.next();
         moveDown = button.field_230691_m_ + button.func_238483_d_();
         if (moveDown > lowerButtonMaxY) {
            lowerButtonMaxY = moveDown;
         }
      }

      int idealMoveDown = 50;
      int lowestSuitableHeight = this.field_230709_l_ - 25;
      moveDown = Math.min(idealMoveDown, lowestSuitableHeight - lowerButtonMaxY);
      moveDown = Math.max(moveDown, 0);

      for(int i = 0; i < this.field_230710_m_.size(); ++i) {
         Widget button = (Widget)this.field_230710_m_.get(i);
         button.field_230691_m_ += moveDown;
         if (button.getClass() == Button.class) {
            Button bt = (Button)button;
            Widget newButton = new RedBookButton(bt.field_230690_l_, bt.field_230691_m_, bt.func_230998_h_(), bt.func_238483_d_(), bt.func_230458_i_(), (b) -> {
               bt.func_230930_b_();
            });
            this.field_230710_m_.set(i, newButton);
         }
      }

   }

   public void func_231158_b_(Minecraft mc, int i, int j) {
      super.func_231158_b_(mc, i, j);
      this.mapGui.loadCurrentMapTextures();
      this.mapGui.func_231158_b_(mc, i, j);
   }

   public void func_231023_e_() {
      super.func_231023_e_();
      ++tickCounter;
      mapRenderer.tick();
      if (!waypointRoute.isEmpty()) {
         if (currentWPIndex >= waypointRoute.size()) {
            currentWPIndex = 0;
         }

         MapWaypoint wp = (MapWaypoint)waypointRoute.get(currentWPIndex);
         double dx = wp.getMapX() - mapRenderer.getMapX();
         double dy = wp.getMapZ() - mapRenderer.getMapY();
         double distSq = dx * dx + dy * dy;
         double dist = Math.sqrt(distSq);
         if (dist <= 12.0D) {
            ++currentWPIndex;
            if (currentWPIndex >= waypointRoute.size()) {
               currentWPIndex = 0;
            }
         } else {
            mapSpeed += 0.01F;
            mapSpeed = Math.min(mapSpeed, 0.8F);
            float vXNew = (float)(dx / dist) * mapSpeed;
            float vYNew = (float)(dy / dist) * mapSpeed;
            float a = 0.02F;
            mapVelX += (vXNew - mapVelX) * a;
            mapVelY += (vYNew - mapVelY) * a;
         }
      }

      mapRenderer.moveBy((double)mapVelX, (double)mapVelY);
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float tick) {
      tick = this.field_230706_i_.func_184121_ak();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      if (this.firstRenderTime == 0L && this.fadeIn) {
         this.firstRenderTime = Util.func_211177_b();
      }

      float fade = this.fadeIn ? (float)(Util.func_211177_b() - this.firstRenderTime) / 1000.0F : 1.0F;
      func_238467_a_(matStack, 0, 0, this.field_230708_k_, this.field_230709_l_, -1);
      float zoom = -0.1F + MathHelper.func_76134_b(((float)tickCounter + tick) * 0.003F) * 0.8F;
      if (this.fadeIn) {
         float slowerFade = fade * 0.5F;
         float fadeInZoom = MathHelper.func_76131_a(1.0F - slowerFade, 0.0F, 1.0F) * -2.5F;
         zoom += fadeInZoom;
      }

      mapRenderer.setZoomExp(zoom);
      mapRenderer.renderMap(matStack, this, this.mapGui, tick);
      mapRenderer.renderVignettes(matStack, this, (float)this.func_230927_p_(), 2);
      int i = 274;
      int j = this.field_230708_k_ / 2 - i / 2;
      this.field_230706_i_.func_110434_K().func_110577_a(PANORAMA_OVERLAY);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.fadeIn ? MathHelper.func_76131_a(1.0F - fade, 0.0F, 1.0F) : 0.0F);
      func_238466_a_(matStack, 0, 0, this.field_230708_k_, this.field_230709_l_, 0.0F, 0.0F, 16, 128, 16, 128);
      float f1 = this.fadeIn ? MathHelper.func_76131_a(fade - 1.0F, 0.0F, 1.0F) : 1.0F;
      int l = MathHelper.func_76123_f(f1 * 255.0F) << 24;
      if ((l & -67108864) != 0) {
         this.field_230706_i_.func_110434_K().func_110577_a(TITLE_TEXTURE);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, f1);
         if (this.minceraft) {
            this.func_238474_b_(matStack, j + 0, 30, 0, 0, 99, 44);
            this.func_238474_b_(matStack, j + 99, 30, 129, 0, 27, 44);
            this.func_238474_b_(matStack, j + 99 + 26, 30, 126, 0, 3, 44);
            this.func_238474_b_(matStack, j + 99 + 26 + 3, 30, 99, 0, 26, 44);
            this.func_238474_b_(matStack, j + 155, 30, 0, 45, 155, 44);
         } else {
            this.func_238474_b_(matStack, j + 0, 30, 0, 0, 155, 44);
            this.func_238474_b_(matStack, j + 155, 30, 0, 45, 155, 44);
         }

         func_238475_b_(matStack, this.field_230712_o_, MOD_TITLE, this.field_230708_k_ / 2 - this.field_230712_o_.func_238414_a_(MOD_TITLE) / 2, 86, -1);
         func_238475_b_(matStack, this.field_230712_o_, MOD_SUBTITLE, this.field_230708_k_ / 2 - this.field_230712_o_.func_238414_a_(MOD_SUBTITLE) / 2, 96, -2236963);
         this.field_230706_i_.func_110434_K().func_110577_a(TITLE_EDITION);
         func_238463_a_(matStack, j + 88, 67, 0.0F, 0.0F, 98, 14, 128, 16);
         ForgeHooksClient.renderMainMenu(this, matStack, this.field_230712_o_, this.field_230708_k_, this.field_230709_l_);
         String s = "Minecraft " + SharedConstants.func_215069_a().getName();
         if (this.field_230706_i_.func_71355_q()) {
            s = s + " Demo";
         } else {
            s = s + ("release".equalsIgnoreCase(this.field_230706_i_.func_184123_d()) ? "" : "/" + this.field_230706_i_.func_184123_d());
         }

         if (this.field_230706_i_.func_230151_c_()) {
            s = s + I18n.func_135052_a("menu.modded", new Object[0]);
         }

         BrandingControl.forEachLine(true, true, (brdline, brd) -> {
            FontRenderer var10001 = this.field_230712_o_;
            int var10004 = this.field_230709_l_;
            int var10006 = brdline;
            this.field_230712_o_.getClass();
            func_238476_c_(matStack, var10001, brd, 2, var10004 - (10 + var10006 * (9 + 1)), 16777215 | l);
         });
         BrandingControl.forEachAboveCopyrightLine((brdline, brd) -> {
            FontRenderer var10001 = this.field_230712_o_;
            int var10003 = this.field_230708_k_ - this.field_230712_o_.func_78256_a(brd);
            int var10004 = this.field_230709_l_;
            int var10006 = brdline + 1;
            this.field_230712_o_.getClass();
            func_238476_c_(matStack, var10001, brd, var10003, var10004 - (10 + var10006 * (9 + 1)), 16777215 | l);
         });
         func_238476_c_(matStack, this.field_230712_o_, this.copyrightText, this.widthCopyrightRest, this.field_230709_l_ - 10, 16777215 | l);
         if (mouseX > this.widthCopyrightRest && mouseX < this.widthCopyrightRest + this.widthCopyright && mouseY > this.field_230709_l_ - 10 && mouseY < this.field_230709_l_) {
            func_238467_a_(matStack, this.widthCopyrightRest, this.field_230709_l_ - 1, this.widthCopyrightRest + this.widthCopyright, this.field_230709_l_, 16777215 | l);
         }

         Iterator var12 = this.field_230710_m_.iterator();

         Widget widget;
         while(var12.hasNext()) {
            widget = (Widget)var12.next();
            widget.func_230986_a_(f1);
         }

         var12 = this.field_230710_m_.iterator();

         while(var12.hasNext()) {
            widget = (Widget)var12.next();
            widget.func_230430_a_(matStack, mouseX, mouseY, tick);
         }

         this.modUpdateNotification.func_230430_a_(matStack, mouseX, mouseY, tick);
      }

   }

   static {
      MOD_SUBTITLE = (new TranslationTextComponent("lotr.menu.subtitle")).func_240699_a_(TextFormatting.ITALIC);
      rand = new Random();
      waypointRoute = new ArrayList();
      randomWPStart = false;
   }
}
