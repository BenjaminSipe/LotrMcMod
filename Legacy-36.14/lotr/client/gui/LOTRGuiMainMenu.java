package lotr.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.world.map.LOTRWaypoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

public class LOTRGuiMainMenu extends GuiMainMenu {
   private static final ResourceLocation titleTexture = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation vignetteTexture = new ResourceLocation("textures/misc/vignette.png");
   private static final ResourceLocation menuOverlay = new ResourceLocation("lotr:gui/menu_overlay.png");
   private LOTRGuiMap mapGui;
   private static LOTRGuiRendererMap mapRenderer;
   private static int tickCounter;
   private static Random rand = new Random();
   private boolean fadeIn = false;
   private static boolean isFirstMenu = true;
   private long firstRenderTime;
   private static List waypointRoute = new ArrayList();
   private static int currentWPIndex;
   private static boolean randomWPStart = false;
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

   public LOTRGuiMainMenu() {
      this.fadeIn = isFirstMenu;
      isFirstMenu = false;
      this.mapGui = new LOTRGuiMap();
      mapRenderer = new LOTRGuiRendererMap();
      mapRenderer.setSepia(false);
      if (waypointRoute.isEmpty()) {
         setupWaypoints();
         if (randomWPStart) {
            currentWPIndex = rand.nextInt(waypointRoute.size());
         } else {
            currentWPIndex = 0;
         }
      }

      LOTRWaypoint wp = (LOTRWaypoint)waypointRoute.get(currentWPIndex);
      mapRenderer.prevMapX = mapRenderer.mapX = wp.getX();
      mapRenderer.prevMapY = mapRenderer.mapY = wp.getY();
   }

   private static void setupWaypoints() {
      waypointRoute.clear();
      waypointRoute.add(LOTRWaypoint.HOBBITON);
      waypointRoute.add(LOTRWaypoint.BRANDYWINE_BRIDGE);
      waypointRoute.add(LOTRWaypoint.BUCKLEBURY);
      waypointRoute.add(LOTRWaypoint.WITHYWINDLE_VALLEY);
      waypointRoute.add(LOTRWaypoint.BREE);
      waypointRoute.add(LOTRWaypoint.WEATHERTOP);
      waypointRoute.add(LOTRWaypoint.RIVENDELL);
      waypointRoute.add(LOTRWaypoint.WEST_GATE);
      waypointRoute.add(LOTRWaypoint.DIMRILL_DALE);
      waypointRoute.add(LOTRWaypoint.CERIN_AMROTH);
      waypointRoute.add(LOTRWaypoint.CARAS_GALADHON);
      waypointRoute.add(LOTRWaypoint.NORTH_UNDEEP);
      waypointRoute.add(LOTRWaypoint.SOUTH_UNDEEP);
      waypointRoute.add(LOTRWaypoint.ARGONATH);
      waypointRoute.add(LOTRWaypoint.RAUROS);
      waypointRoute.add(LOTRWaypoint.EDORAS);
      waypointRoute.add(LOTRWaypoint.HELMS_DEEP);
      waypointRoute.add(LOTRWaypoint.ISENGARD);
      waypointRoute.add(LOTRWaypoint.DUNHARROW);
      waypointRoute.add(LOTRWaypoint.ERECH);
      waypointRoute.add(LOTRWaypoint.MINAS_TIRITH);
      waypointRoute.add(LOTRWaypoint.MINAS_MORGUL);
      waypointRoute.add(LOTRWaypoint.MOUNT_DOOM);
      waypointRoute.add(LOTRWaypoint.MORANNON);
      waypointRoute.add(LOTRWaypoint.EAST_RHOVANION_ROAD);
      waypointRoute.add(LOTRWaypoint.OLD_RHOVANION);
      waypointRoute.add(LOTRWaypoint.RUNNING_FORD);
      waypointRoute.add(LOTRWaypoint.DALE_CITY);
      waypointRoute.add(LOTRWaypoint.THRANDUIL_HALLS);
      waypointRoute.add(LOTRWaypoint.ENCHANTED_RIVER);
      waypointRoute.add(LOTRWaypoint.FOREST_GATE);
      waypointRoute.add(LOTRWaypoint.BEORN);
      waypointRoute.add(LOTRWaypoint.EAGLES_EYRIE);
      waypointRoute.add(LOTRWaypoint.GOBLIN_TOWN);
      waypointRoute.add(LOTRWaypoint.MOUNT_GRAM);
      waypointRoute.add(LOTRWaypoint.FORNOST);
      waypointRoute.add(LOTRWaypoint.ANNUMINAS);
      waypointRoute.add(LOTRWaypoint.MITHLOND_NORTH);
      waypointRoute.add(LOTRWaypoint.TOWER_HILLS);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      int lowerButtonMaxY = 0;
      Iterator var2 = this.field_146292_n.iterator();

      int i;
      while(var2.hasNext()) {
         Object obj = var2.next();
         GuiButton button = (GuiButton)obj;
         i = button.field_146129_i + button.field_146121_g;
         if (i > lowerButtonMaxY) {
            lowerButtonMaxY = i;
         }
      }

      int idealMoveDown = 50;
      int lowestSuitableHeight = this.field_146295_m - 25;
      int moveDown = Math.min(idealMoveDown, lowestSuitableHeight - lowerButtonMaxY);
      moveDown = Math.max(moveDown, 0);

      for(i = 0; i < this.field_146292_n.size(); ++i) {
         GuiButton button = (GuiButton)this.field_146292_n.get(i);
         button.field_146129_i += moveDown;
         if (button.getClass() == GuiButton.class) {
            GuiButton newButton = new LOTRGuiButtonRedBook(button.field_146127_k, button.field_146128_h, button.field_146129_i, button.field_146120_f, button.field_146121_g, button.field_146126_j);
            this.field_146292_n.set(i, newButton);
         }
      }

   }

   public void func_146280_a(Minecraft mc, int i, int j) {
      super.func_146280_a(mc, i, j);
      this.mapGui.func_146280_a(mc, i, j);
   }

   public void func_73876_c() {
      super.func_73876_c();
      ++tickCounter;
      mapRenderer.updateTick();
      LOTRWaypoint wp = (LOTRWaypoint)waypointRoute.get(currentWPIndex);
      double dx = wp.getX() - mapRenderer.mapX;
      double dy = wp.getY() - mapRenderer.mapY;
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
         double vXNew = dx / dist * (double)mapSpeed;
         double vYNew = dy / dist * (double)mapSpeed;
         float a = 0.02F;
         mapVelX = (float)((double)mapVelX + (vXNew - (double)mapVelX) * (double)a);
         mapVelY = (float)((double)mapVelY + (vYNew - (double)mapVelY) * (double)a);
      }

      LOTRGuiRendererMap var10000 = mapRenderer;
      var10000.mapX += (double)mapVelX;
      var10000 = mapRenderer;
      var10000.mapY += (double)mapVelY;
   }

   public void func_73863_a(int i, int j, float f) {
      GL11.glEnable(3008);
      GL11.glEnable(3042);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      if (this.firstRenderTime == 0L && this.fadeIn) {
         this.firstRenderTime = System.currentTimeMillis();
      }

      float fade = this.fadeIn ? (float)(System.currentTimeMillis() - this.firstRenderTime) / 1000.0F : 1.0F;
      float fadeAlpha = this.fadeIn ? MathHelper.func_76131_a(fade - 1.0F, 0.0F, 1.0F) : 1.0F;
      mapRenderer.zoomExp = -0.1F + MathHelper.func_76134_b(((float)tickCounter + f) * 0.003F) * 0.8F;
      if (this.fadeIn) {
         float slowerFade = fade * 0.5F;
         float fadeInZoom = MathHelper.func_76131_a(1.0F - slowerFade, 0.0F, 1.0F) * -1.5F;
         LOTRGuiRendererMap var10000 = mapRenderer;
         var10000.zoomExp += fadeInZoom;
      }

      mapRenderer.zoomStable = (float)Math.pow(2.0D, -0.10000000149011612D);
      mapRenderer.renderMap(this, this.mapGui, f);
      mapRenderer.renderVignettes(this, (double)this.field_73735_i, 2);
      GL11.glEnable(3042);
      OpenGlHelper.func_148821_a(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, this.fadeIn ? MathHelper.func_76131_a(1.0F - fade, 0.0F, 1.0F) : 0.0F);
      this.field_146297_k.func_110434_K().func_110577_a(menuOverlay);
      func_152125_a(0, 0, 0.0F, 0.0F, 16, 128, this.field_146294_l, this.field_146295_m, 16.0F, 128.0F);
      int fadeAlphaI = MathHelper.func_76123_f(fadeAlpha * 255.0F) << 24;
      if ((fadeAlphaI & -67108864) != 0) {
         Tessellator tessellator = Tessellator.field_78398_a;
         short short1 = 274;
         int k = this.field_146294_l / 2 - short1 / 2;
         byte b0 = 30;
         this.field_146297_k.func_110434_K().func_110577_a(titleTexture);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, fadeAlpha);
         this.func_73729_b(k + 0, b0 + 0, 0, 0, 155, 44);
         this.func_73729_b(k + 155, b0 + 0, 0, 45, 155, 44);
         String modSubtitle = StatCollector.func_74838_a("lotr.menu.title");
         this.func_73731_b(this.field_146289_q, modSubtitle, this.field_146294_l / 2 - this.field_146289_q.func_78256_a(modSubtitle) / 2, 80, -1);
         List brandings = Lists.reverse(FMLCommonHandler.instance().getBrandings(true));

         String field_92025_p;
         for(int l = 0; l < brandings.size(); ++l) {
            field_92025_p = (String)brandings.get(l);
            if (!Strings.isNullOrEmpty(field_92025_p)) {
               this.func_73731_b(this.field_146289_q, field_92025_p, 2, this.field_146295_m - (10 + l * (this.field_146289_q.field_78288_b + 1)), -1);
            }
         }

         ForgeHooksClient.renderMainMenu(this, this.field_146289_q, this.field_146294_l, this.field_146295_m);
         String copyright = "Copyright Mojang AB. Do not distribute!";
         this.func_73731_b(this.field_146289_q, copyright, this.field_146294_l - this.field_146289_q.func_78256_a(copyright) - 2, this.field_146295_m - 10, -1);
         field_92025_p = (String)ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, new String[]{"field_92025_p"});
         String field_146972_A = (String)ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, new String[]{"field_146972_A"});
         int field_92024_r = (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, new String[]{"field_92024_r"});
         int field_92022_t = (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, new String[]{"field_92022_t"});
         int field_92021_u = (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, new String[]{"field_92021_u"});
         int field_92020_v = (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, new String[]{"field_92020_v"});
         int field_92019_w = (Integer)ObfuscationReflectionHelper.getPrivateValue(GuiMainMenu.class, this, new String[]{"field_92019_w"});
         if (field_92025_p != null && field_92025_p.length() > 0) {
            func_73734_a(field_92022_t - 2, field_92021_u - 2, field_92020_v + 2, field_92019_w - 1, 1428160512);
            this.func_73731_b(this.field_146289_q, field_92025_p, field_92022_t, field_92021_u, -1);
            this.func_73731_b(this.field_146289_q, field_146972_A, (this.field_146294_l - field_92024_r) / 2, ((GuiButton)this.field_146292_n.get(0)).field_146129_i - 12, -1);
         }

         Iterator var21 = this.field_146292_n.iterator();

         Object label;
         while(var21.hasNext()) {
            label = var21.next();
            ((GuiButton)label).func_146112_a(this.field_146297_k, i, j);
         }

         var21 = this.field_146293_o.iterator();

         while(var21.hasNext()) {
            label = var21.next();
            ((GuiLabel)label).func_146159_a(this.field_146297_k, i, j);
         }
      }

   }
}
