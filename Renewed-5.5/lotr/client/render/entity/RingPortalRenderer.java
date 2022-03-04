package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import lotr.client.LOTRClientProxy;
import lotr.client.render.entity.model.RingPortalModel;
import lotr.client.util.LOTRClientUtil;
import lotr.common.entity.item.RingPortalEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

public class RingPortalRenderer extends EntityRenderer {
   public static final ResourceLocation RING_TEXTURE = new ResourceLocation("lotr", "textures/entity/portal/ring.png");
   public static final ResourceLocation SCRIPT_TEXTURE = new ResourceLocation("lotr", "textures/entity/portal/script.png");
   private static final RingPortalModel MODEL_RING = new RingPortalModel(false);
   private static final RingPortalModel MODEL_SCRIPT = new RingPortalModel(true);
   private static final float OUTER_SCALE = 1.05F;
   private static final float INNER_SCALE = 0.85F;

   public RingPortalRenderer(EntityRendererManager mgr) {
      super(mgr);
   }

   public ResourceLocation getEntityTexture(RingPortalEntity entity) {
      return RING_TEXTURE;
   }

   protected int getBlockLight(RingPortalEntity entity, BlockPos pos) {
      return 15;
   }

   public void render(RingPortalEntity portal, float yaw, float ticks, MatrixStack matStack, IRenderTypeBuffer buf, int packedLight) {
      matStack.func_227860_a_();
      matStack.func_227861_a_(0.0D, 0.75D, 0.0D);
      matStack.func_227862_a_(1.0F, -1.0F, -1.0F);
      float portalScale = portal.getPortalScale(ticks);
      matStack.func_227862_a_(portalScale, portalScale, portalScale);
      matStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(portal.getPortalRotation(ticks)));
      matStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(10.0F));
      this.renderRingModel(portal, MODEL_RING, RenderType.func_228640_c_(RING_TEXTURE), ticks, matStack, buf, packedLight, 1.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      int fullbright = LOTRClientProxy.MAX_LIGHTMAP;
      float scriptBr = portal.getScriptBrightness(ticks);
      matStack.func_227860_a_();
      matStack.func_227862_a_(1.05F, 1.05F, 1.05F);
      this.renderRingModel(portal, MODEL_SCRIPT, RenderType.func_228644_e_(SCRIPT_TEXTURE), ticks, matStack, buf, fullbright, scriptBr);
      matStack.func_227865_b_();
      matStack.func_227860_a_();
      matStack.func_227862_a_(0.85F, 0.85F, 0.85F);
      this.renderRingModel(portal, MODEL_SCRIPT, RenderType.func_228644_e_(SCRIPT_TEXTURE), ticks, matStack, buf, fullbright, scriptBr);
      matStack.func_227865_b_();
      RenderSystem.disableBlend();
      matStack.func_227865_b_();
   }

   private void renderRingModel(RingPortalEntity portal, Model model, RenderType renType, float ticks, MatrixStack mat, IRenderTypeBuffer buf, int light, float alpha) {
      IVertexBuilder vb = buf.getBuffer(renType);
      int overlay = LOTRClientUtil.getPackedNoOverlay();
      model.func_225598_a_(mat, vb, light, overlay, 1.0F, 1.0F, 1.0F, alpha);
   }
}
