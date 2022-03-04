package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.common.entity.projectile.ThrownPlateEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class ThrownPlateRenderer extends EntityRenderer {
   private static final double ONE_OVER_SQRT_2;

   public ThrownPlateRenderer(EntityRendererManager mgr) {
      super(mgr);
   }

   public ResourceLocation getEntityTexture(ThrownPlateEntity plate) {
      return AtlasTexture.field_110575_b;
   }

   public void render(ThrownPlateEntity plate, float yaw, float ticks, MatrixStack matStack, IRenderTypeBuffer buf, int light) {
      matStack.func_227860_a_();
      float deg = plate.getPlateSpin(ticks);
      double radsToUse = Math.toRadians((double)(deg - 45.0F));
      double transX = (double)(-MathHelper.func_76134_b((float)radsToUse)) * ONE_OVER_SQRT_2;
      double transZ = (double)MathHelper.func_76126_a((float)radsToUse) * ONE_OVER_SQRT_2;
      matStack.func_227861_a_(transX, 0.0D, transZ);
      matStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(deg));
      Minecraft.func_71410_x().func_175602_ab().func_228791_a_(plate.getPlateBlockState(), matStack, buf, light, OverlayTexture.field_229196_a_);
      matStack.func_227865_b_();
   }

   static {
      ONE_OVER_SQRT_2 = 1.0D / (double)MathHelper.field_180189_a;
   }
}
