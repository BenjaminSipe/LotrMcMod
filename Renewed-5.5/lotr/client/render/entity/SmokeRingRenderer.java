package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.matrix.MatrixStack.Entry;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Arrays;
import lotr.client.render.LOTRRenderTypes;
import lotr.client.render.entity.model.SmokeShipModel;
import lotr.common.entity.projectile.SmokeRingEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class SmokeRingRenderer extends EntityRenderer {
   private static final ResourceLocation TEXTURE = new ResourceLocation("lotr", "textures/entity/misc/smoke_ring.png");
   private final Model shipModel = new SmokeShipModel();

   public SmokeRingRenderer(EntityRendererManager mgr) {
      super(mgr);
   }

   public ResourceLocation getEntityTexture(SmokeRingEntity smokeRing) {
      return TEXTURE;
   }

   public void render(SmokeRingEntity smokeRing, float yaw, float ticks, MatrixStack matStack, IRenderTypeBuffer buf, int light) {
      matStack.func_227860_a_();
      matStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(MathHelper.func_219799_g(ticks, smokeRing.field_70126_B, smokeRing.field_70177_z)));
      matStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(-MathHelper.func_219799_g(ticks, smokeRing.field_70127_C, smokeRing.field_70125_A)));
      float age = smokeRing.getRenderSmokeAge(ticks);
      float alpha = 1.0F - age;
      float[] rgb = Arrays.copyOf(smokeRing.getSmokeColor().func_193349_f(), 3);
      float colorIntensity = 0.65F;

      int overlay;
      for(overlay = 0; overlay < rgb.length; ++overlay) {
         rgb[overlay] = MathHelper.func_219799_g(colorIntensity, 1.0F, rgb[overlay]);
      }

      overlay = OverlayTexture.field_229196_a_;
      float scale = smokeRing.getSmokeScale();
      float shipScale;
      RenderType renderType;
      if (smokeRing.isMagicSmoke()) {
         shipScale = 0.3F * scale;
         matStack.func_227862_a_(shipScale, shipScale, shipScale);
         matStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
         renderType = LOTRRenderTypes.ENTITY_TRANSLUCENT_NO_TEXTURE;
         this.shipModel.func_225598_a_(matStack, buf.getBuffer(renderType), light, overlay, rgb[0], rgb[1], rgb[2], alpha * 0.75F);
      } else {
         shipScale = (0.1F + 0.9F * age) * scale;
         matStack.func_227862_a_(shipScale, shipScale, shipScale);
         renderType = RenderType.func_228644_e_(this.getEntityTexture(smokeRing));
         this.renderSprite(matStack, buf.getBuffer(renderType), light, overlay, rgb[0], rgb[1], rgb[2], alpha);
      }

      matStack.func_227865_b_();
   }

   private void renderSprite(MatrixStack matStack, IVertexBuilder vb, int light, int overlay, float r, float g, float b, float a) {
      Entry last = matStack.func_227866_c_();
      Matrix4f mat = last.func_227870_a_();
      Matrix3f normal = last.func_227872_b_();
      float halfWidth = 0.5F;
      float z = 0.0F;
      float u0 = 0.0F;
      float u1 = 1.0F;
      float v0 = 0.0F;
      float v1 = 1.0F;
      vb.func_227888_a_(mat, -halfWidth, halfWidth, z).func_227885_a_(r, g, b, a).func_225583_a_(u0, v1).func_227891_b_(overlay).func_227886_a_(light).func_227887_a_(normal, 0.0F, 1.0F, 0.0F).func_181675_d();
      vb.func_227888_a_(mat, halfWidth, halfWidth, z).func_227885_a_(r, g, b, a).func_225583_a_(u1, v1).func_227891_b_(overlay).func_227886_a_(light).func_227887_a_(normal, 0.0F, 1.0F, 0.0F).func_181675_d();
      vb.func_227888_a_(mat, halfWidth, -halfWidth, z).func_227885_a_(r, g, b, a).func_225583_a_(u1, v0).func_227891_b_(overlay).func_227886_a_(light).func_227887_a_(normal, 0.0F, 1.0F, 0.0F).func_181675_d();
      vb.func_227888_a_(mat, -halfWidth, -halfWidth, z).func_227885_a_(r, g, b, a).func_225583_a_(u0, v0).func_227891_b_(overlay).func_227886_a_(light).func_227887_a_(normal, 0.0F, 1.0F, 0.0F).func_181675_d();
   }
}
