package lotr.client.render.entity;

import lotr.client.render.entity.model.WargModel;
import lotr.common.entity.npc.WargEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class WargRenderer extends MobRenderer {
   public WargRenderer(EntityRendererManager mgr) {
      super(mgr, new WargModel(0.0F), 0.5F);
   }

   public ResourceLocation getEntityTexture(WargEntity warg) {
      return warg.getWargType().getTexture();
   }
}
