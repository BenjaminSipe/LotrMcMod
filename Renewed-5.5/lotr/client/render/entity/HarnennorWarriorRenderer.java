package lotr.client.render.entity;

import lotr.client.render.RandomTextureVariants;
import lotr.common.entity.npc.HarnedhrimEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class HarnennorWarriorRenderer extends HarnedhrimRenderer {
   private static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/near_harad/harnennor_warrior");

   public HarnennorWarriorRenderer(EntityRendererManager mgr) {
      super(mgr);
   }

   public ResourceLocation getEntityTexture(HarnedhrimEntity man) {
      return SKINS.getRandomSkin(man);
   }
}
