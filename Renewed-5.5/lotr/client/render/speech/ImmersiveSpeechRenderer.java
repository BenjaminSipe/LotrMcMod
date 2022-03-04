package lotr.client.render.speech;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import lotr.client.LOTRClientProxy;
import lotr.client.speech.ImmersiveSpeech;
import lotr.client.util.LOTRClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ImmersiveSpeechRenderer {
   private static final int SPEECH_WIDTH = 150;
   private static final float SPEECH_SCALE = 0.015F;
   private static final double SPEECH_RENDER_RANGE = 64.0D;

   public static void renderAllSpeeches(Minecraft mc, World world, MatrixStack matStack, float tick) {
      matStack.func_227860_a_();
      RenderHelper.func_227780_a_();
      RenderSystem.alphaFunc(516, 0.01F);
      Vector3d renderPos = mc.field_71460_t.func_215316_n().func_216785_c();
      double x = renderPos.func_82615_a();
      double y = renderPos.func_82617_b();
      double z = renderPos.func_82616_c();
      Set removes = new HashSet();
      ImmersiveSpeech.forEach((entityId, timedSpeech) -> {
         Entity entity = world.func_73045_a(entityId);
         if (entity != null && entity.func_70089_S()) {
            boolean inRange = entity.func_145770_h(x, y, z);
            if (inRange) {
               double entityX = entity.field_70169_q + (entity.func_226277_ct_() - entity.field_70169_q) * (double)tick;
               double entityY = entity.field_70167_r + (entity.func_226278_cu_() - entity.field_70167_r) * (double)tick;
               double entityZ = entity.field_70166_s + (entity.func_226281_cx_() - entity.field_70166_s) * (double)tick;
               renderSpeech(mc, matStack, entity, timedSpeech.getSpeech(), timedSpeech.getAge(), entityX - x, entityY - y, entityZ - z);
            }
         } else {
            removes.add(entityId);
         }

      });
      removes.forEach(ImmersiveSpeech::removeSpeech);
      RenderSystem.defaultAlphaFunc();
      RenderHelper.func_74518_a();
      mc.field_71460_t.func_228384_l_().func_205108_b();
      matStack.func_227865_b_();
   }

   private static void renderSpeech(Minecraft mc, MatrixStack matStack, Entity entity, String speech, float speechAge, double x, double y, double z) {
      World world = entity.func_130014_f_();
      world.func_217381_Z().func_76320_a("renderNPCSpeech");
      TextureManager textureManager = mc.func_110434_K();
      EntityRendererManager renderManager = mc.func_175598_ae();
      FontRenderer fr = mc.field_71466_p;
      double distance = 64.0D;
      double distanceSq = entity.func_70068_e(renderManager.field_217783_c.func_216773_g());
      if (distanceSq <= distance * distance) {
         ITextComponent name = TextComponentUtils.func_240648_a_(entity.func_200200_C_().func_230532_e_(), Style.field_240709_b_.func_240712_a_(TextFormatting.YELLOW));
         fr.getClass();
         int fontHeight = 9;
         List speechLines = fr.func_238425_b_(new StringTextComponent(speech), 150);
         float alpha = 0.8F;
         if (speechAge < 0.1F) {
            alpha *= speechAge / 0.1F;
         }

         matStack.func_227860_a_();
         matStack.func_227861_a_(x, y + (double)entity.func_213302_cg() + 0.30000001192092896D, z);
         matStack.func_227863_a_(renderManager.field_217783_c.func_227995_f_());
         RenderSystem.disableLighting();
         RenderSystem.depthMask(false);
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int fullbright = LOTRClientProxy.MAX_LIGHTMAP;
         float scale = 0.015F;
         matStack.func_227862_a_(-scale, -scale, scale);
         matStack.func_227861_a_(0.0D, (double)(-fontHeight * (3 + speechLines.size())), 0.0D);
         Tessellator tessellator = Tessellator.func_178181_a();
         BufferBuilder buf = Tessellator.func_178181_a().func_178180_c();
         float blackBoxAlpha = 0.35F * alpha;
         int halfNameW = fr.func_238414_a_(name) / 2;
         RenderSystem.disableTexture();
         RenderSystem.disableAlphaTest();
         Matrix4f matrix = matStack.func_227866_c_().func_227870_a_();
         buf.func_181668_a(7, DefaultVertexFormats.field_181706_f);
         buf.func_227888_a_(matrix, (float)(-halfNameW - 1), 0.0F, 0.0F).func_227885_a_(0.0F, 0.0F, 0.0F, blackBoxAlpha).func_181675_d();
         buf.func_227888_a_(matrix, (float)(-halfNameW - 1), (float)fontHeight, 0.0F).func_227885_a_(0.0F, 0.0F, 0.0F, blackBoxAlpha).func_181675_d();
         buf.func_227888_a_(matrix, (float)(halfNameW + 1), (float)fontHeight, 0.0F).func_227885_a_(0.0F, 0.0F, 0.0F, blackBoxAlpha).func_181675_d();
         buf.func_227888_a_(matrix, (float)(halfNameW + 1), 0.0F, 0.0F).func_227885_a_(0.0F, 0.0F, 0.0F, blackBoxAlpha).func_181675_d();
         tessellator.func_78381_a();
         RenderSystem.enableTexture();
         LOTRClientUtil.drawSeethroughText(fr, (ITextComponent)name, -halfNameW, 0, LOTRClientUtil.getRGBAForFontRendering(16777215, alpha), matStack);
         matStack.func_227861_a_(0.0D, (double)fontHeight, 0.0D);
         Iterator var30 = speechLines.iterator();

         while(var30.hasNext()) {
            IReorderingProcessor line = (IReorderingProcessor)var30.next();
            matStack.func_227861_a_(0.0D, (double)fontHeight, 0.0D);
            int halfLineW = fr.func_243245_a(line) / 2;
            RenderSystem.disableTexture();
            RenderSystem.disableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            matrix = matStack.func_227866_c_().func_227870_a_();
            buf.func_181668_a(7, DefaultVertexFormats.field_181706_f);
            buf.func_227888_a_(matrix, (float)(-halfLineW - 1), 0.0F, 0.0F).func_227885_a_(0.0F, 0.0F, 0.0F, blackBoxAlpha).func_181675_d();
            buf.func_227888_a_(matrix, (float)(-halfLineW - 1), (float)fontHeight, 0.0F).func_227885_a_(0.0F, 0.0F, 0.0F, blackBoxAlpha).func_181675_d();
            buf.func_227888_a_(matrix, (float)(halfLineW + 1), (float)fontHeight, 0.0F).func_227885_a_(0.0F, 0.0F, 0.0F, blackBoxAlpha).func_181675_d();
            buf.func_227888_a_(matrix, (float)(halfLineW + 1), 0.0F, 0.0F).func_227885_a_(0.0F, 0.0F, 0.0F, blackBoxAlpha).func_181675_d();
            tessellator.func_78381_a();
            RenderSystem.enableTexture();
            LOTRClientUtil.drawSeethroughText(fr, (IReorderingProcessor)line, -halfLineW, 0, LOTRClientUtil.getRGBAForFontRendering(16777215, alpha), matStack);
         }

         RenderSystem.disableBlend();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(true);
         RenderSystem.enableLighting();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         matStack.func_227865_b_();
      }

      world.func_217381_Z().func_76319_b();
   }

   private static float calcSpeechDisplacement(Minecraft mc, LivingEntity entity) {
      ImmersiveSpeech.TimedSpeech timedSpeech = ImmersiveSpeech.getSpeechFor(entity);
      if (timedSpeech != null) {
         FontRenderer fr = mc.field_71466_p;
         fr.getClass();
         int fontHeight = 9;
         int numLines = fr.func_238425_b_(new StringTextComponent(timedSpeech.getSpeech()), 150).size();
         float f = (float)(fontHeight * (3 + numLines));
         return f * 0.015F;
      } else {
         return 0.0F;
      }
   }
}
