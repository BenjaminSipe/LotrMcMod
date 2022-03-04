package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.common.entity.projectile.SpearEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class SpearRenderer extends EntityRenderer {
   public SpearRenderer(EntityRendererManager mgr) {
      super(mgr);
   }

   public ResourceLocation getEntityTexture(SpearEntity spear) {
      return AtlasTexture.field_110575_b;
   }

   public void render(SpearEntity spear, float yaw, float ticks, MatrixStack matStack, IRenderTypeBuffer buf, int light) {
      matStack.func_227860_a_();
      matStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(MathHelper.func_219799_g(ticks, spear.field_70126_B, spear.field_70177_z) - 90.0F));
      matStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(MathHelper.func_219799_g(ticks, spear.field_70127_C, spear.field_70125_A)));
      float shake = (float)spear.field_70249_b - ticks;
      if (shake > 0.0F) {
         float shakeAngle = -MathHelper.func_76126_a(shake * 3.0F) * shake;
         matStack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(shakeAngle));
      }

      matStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(90.0F));
      matStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(100.0F));
      matStack.func_227861_a_(0.0D, -1.0D, 0.0D);
      ItemStack spearItem = spear.getSpearItem();
      Minecraft.func_71410_x().func_175599_af().func_229110_a_(spearItem, TransformType.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.field_229196_a_, matStack, buf);
      matStack.func_227865_b_();
   }
}
