package lotr.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lotr.client.gui.map.MiddleEarthMapRenderer;
import lotr.client.gui.map.MiddleEarthMapScreen;
import lotr.common.init.LOTRDimensions;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class LOTRLoadingTerrainScreen extends Screen {
   private static final ITextComponent ENTERING_DIMENSION = new TranslationTextComponent("gui.lotr.loading.enter_middle_earth");
   private final Screen parentScreen;
   private final MiddleEarthMapScreen mapGui;
   private final MiddleEarthMapRenderer mapRenderer;
   private static final int MAP_BORDER = 40;
   private static final float MAP_ZOOM = -0.3F;
   private int tickCounter;

   public LOTRLoadingTerrainScreen(Screen parent) {
      super(NarratorChatListener.field_216868_a);
      this.parentScreen = parent;
      this.mapGui = new MiddleEarthMapScreen();
      this.mapRenderer = new MiddleEarthMapRenderer(true, false);
      this.mapRenderer.setZoomExp(-0.3F);
      this.mapRenderer.setStableZoom((float)Math.pow(2.0D, -0.30000001192092896D));
   }

   public void func_231158_b_(Minecraft mc, int w, int h) {
      super.func_231158_b_(mc, w, h);
      this.parentScreen.func_231158_b_(mc, w, h);
      this.mapGui.func_231158_b_(mc, w, h);
   }

   public void func_231023_e_() {
      this.parentScreen.func_231023_e_();
      ++this.tickCounter;
   }

   public boolean func_231178_ax__() {
      return this.parentScreen.func_231178_ax__();
   }

   public boolean func_231177_au__() {
      return this.parentScreen.func_231177_au__();
   }

   public void func_230430_a_(MatrixStack matStack, int mouseX, int mouseY, float tick) {
      World world = this.field_230706_i_.field_71441_e;
      PlayerEntity player = this.field_230706_i_.field_71439_g;
      System.out.println(world + ", " + (world == null ? null : world.func_234923_W_().func_240901_a_()));
      if (world != null && LOTRDimensions.isDimension((World)world, LOTRDimensions.MIDDLE_EARTH_WORLD_KEY)) {
         tick = this.field_230706_i_.func_184121_ak();
         this.func_231165_f_(0);
         RenderSystem.enableAlphaTest();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         MapSettings mapSettings = MapSettingsManager.clientInstance().getCurrentLoadedMap();
         this.mapRenderer.setInstantaneousPosition((double)mapSettings.worldToMapX(player.func_226277_ct_()), (double)mapSettings.worldToMapX(player.func_226281_cx_()));
         int x0 = 0;
         int x1 = this.field_230708_k_;
         int y0 = 40;
         int y1 = this.field_230709_l_ - 40;
         this.mapRenderer.renderMap(matStack, this, this.mapGui, tick, x0, y0, x1, y1);
         this.mapRenderer.renderVignette(matStack, this, (float)this.func_230927_p_(), x0, y0, x1, y1);
         RenderSystem.disableBlend();
         func_238472_a_(matStack, this.field_230712_o_, ENTERING_DIMENSION, this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 50, 16777215);
      } else {
         this.parentScreen.func_230430_a_(matStack, mouseX, mouseY, tick);
      }

   }
}
