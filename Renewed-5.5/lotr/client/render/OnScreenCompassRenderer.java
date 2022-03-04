package lotr.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.client.LOTRClientProxy;
import lotr.client.render.entity.RingPortalRenderer;
import lotr.client.render.entity.model.OnScreenCompassModel;
import lotr.client.render.entity.model.RingPortalModel;
import lotr.client.util.LOTRClientUtil;
import lotr.common.config.LOTRConfig;
import lotr.common.init.LOTRBiomes;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class OnScreenCompassRenderer extends AbstractGui {
   private static final ResourceLocation COMPASS_TEXTURE = new ResourceLocation("lotr", "textures/gui/on_screen_compass.png");
   private OnScreenCompassModel compassModel = new OnScreenCompassModel();
   private Model ringModel = new RingPortalModel(false);
   private Model scriptModel = new RingPortalModel(true);

   public void renderCompassAndInformation(Minecraft mc, PlayerEntity player, World world, float renderTick) {
      MatrixStack matStack = new MatrixStack();
      MainWindow window = mc.func_228018_at_();
      int width = window.func_198107_o();
      int height = window.func_198087_p();
      int compassX = width - 60;
      int compassY = 44;
      matStack.func_227860_a_();
      matStack.func_227861_a_((double)compassX, (double)compassY, 0.0D);
      float rotation = player.field_70126_B + (player.field_70177_z - player.field_70126_B) * renderTick;
      rotation = 180.0F - rotation;
      this.renderCompassModel(mc, matStack, 16.0F, rotation);
      matStack.func_227865_b_();
      if ((Boolean)LOTRConfig.CLIENT.compassInfo.get()) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         matStack.func_227860_a_();
         float scale = 0.5F;
         float invScale = 1.0F / scale;
         compassX = (int)((float)compassX * invScale);
         int compassY = (int)((float)compassY * invScale);
         matStack.func_227862_a_(scale, scale, scale);
         BlockPos playerPos = player.func_233580_cy_();
         ITextComponent coords = new TranslationTextComponent("gui.lotr.compass.coords", new Object[]{playerPos.func_177958_n(), playerPos.func_177956_o(), playerPos.func_177952_p()});
         FontRenderer fontRenderer = mc.field_71466_p;
         int coordsWidth = fontRenderer.func_238414_a_(coords);
         int coordsY = compassY + 68;
         int rectBorder = 2;
         int rectColor = 1056964608;
         int var10001 = compassX - coordsWidth / 2 - rectBorder;
         int var10002 = coordsY - rectBorder;
         int var10003 = compassX + coordsWidth / 2 + rectBorder;
         fontRenderer.getClass();
         func_238467_a_(matStack, var10001, var10002, var10003, coordsY + 9 + rectBorder, rectColor);
         fontRenderer.func_243248_b(matStack, coords, (float)(compassX - coordsWidth / 2), (float)coordsY, 16777215);
         if (LOTRClientUtil.doesClientChunkExist(world, playerPos)) {
            Biome biome = world.func_226691_t_(playerPos);
            ITextComponent biomeName = LOTRBiomes.getBiomeDisplayName(biome, world);
            int biomeNameWidth = fontRenderer.func_238414_a_(biomeName);
            int biomeNameY = compassY - 74;
            var10001 = compassX - biomeNameWidth / 2 - rectBorder;
            var10002 = biomeNameY - rectBorder;
            var10003 = compassX + biomeNameWidth / 2 + rectBorder;
            fontRenderer.getClass();
            func_238467_a_(matStack, var10001, var10002, var10003, biomeNameY + 9 + rectBorder, rectColor);
            fontRenderer.func_243248_b(matStack, biomeName, (float)(compassX - biomeNameWidth / 2), (float)biomeNameY, 16777215);
         }

         matStack.func_227865_b_();
         RenderSystem.disableBlend();
      }

   }

   private void renderCompassModel(Minecraft mc, MatrixStack matStack, float scale, float rotation) {
      RenderSystem.pushLightingAttributes();
      RenderHelper.func_227783_c_();
      matStack.func_227862_a_(1.0F, 1.0F, -1.0F);
      matStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(40.0F));
      matStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(rotation));
      matStack.func_227862_a_(scale, scale, scale);
      IRenderTypeBuffer typeBuffer = mc.func_228019_au_().func_228487_b_();
      int packedLight = LOTRClientProxy.MAX_LIGHTMAP;
      int packedOverlay = LOTRClientUtil.getPackedNoOverlay();
      float r = 1.0F;
      float g = 1.0F;
      float b = 1.0F;
      float a = 1.0F;
      IVertexBuilder buf = typeBuffer.getBuffer(RenderType.func_228640_c_(COMPASS_TEXTURE));
      matStack.func_227860_a_();
      float compassScale = 2.0F;
      matStack.func_227862_a_(compassScale, compassScale, compassScale);
      this.compassModel.func_225598_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      matStack.func_227865_b_();
      buf = typeBuffer.getBuffer(RenderType.func_228640_c_(RingPortalRenderer.RING_TEXTURE));
      this.ringModel.func_225598_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      buf = typeBuffer.getBuffer(RenderType.func_228644_e_(RingPortalRenderer.SCRIPT_TEXTURE));
      matStack.func_227860_a_();
      float outerScriptScale = 1.05F;
      matStack.func_227862_a_(outerScriptScale, outerScriptScale, outerScriptScale);
      this.scriptModel.func_225598_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      matStack.func_227865_b_();
      matStack.func_227860_a_();
      float innerScriptScale = 0.85F;
      matStack.func_227862_a_(innerScriptScale, innerScriptScale, innerScriptScale);
      this.scriptModel.func_225598_a_(matStack, buf, packedLight, packedOverlay, r, g, b, a);
      matStack.func_227865_b_();
      RenderSystem.popAttributes();
   }
}
