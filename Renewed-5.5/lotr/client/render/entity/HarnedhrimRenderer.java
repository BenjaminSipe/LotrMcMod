package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.common.entity.npc.HarnedhrimEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class HarnedhrimRenderer extends AbstractManRenderer {
   private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/near_harad/harnennor");

   public HarnedhrimRenderer(EntityRendererManager mgr) {
      super(mgr);
   }

   public ResourceLocation getEntityTexture(HarnedhrimEntity man) {
      return SKINS.getRandomSkin(man);
   }
}
