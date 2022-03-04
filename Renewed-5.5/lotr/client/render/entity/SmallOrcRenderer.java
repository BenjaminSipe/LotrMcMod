package lotr.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import lotr.client.render.entity.model.OrcModel;
import lotr.common.entity.npc.OrcEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;

public class SmallOrcRenderer extends AbstractOrcRenderer {
   private static final float SMALL_ORC_SCALE = 0.8F;

   public SmallOrcRenderer(EntityRendererManager mgr) {
      super(mgr, OrcModel::new, new OrcModel(0.5F), new OrcModel(1.0F), 0.4F);
   }

   protected void preRenderCallback(OrcEntity orc, MatrixStack matStack, float f) {
      super.preRenderCallback(orc, matStack, f);
      float scale = 0.8F;
      matStack.func_227862_a_(scale, scale, scale);
   }
}
