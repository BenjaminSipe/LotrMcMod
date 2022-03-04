package lotr.client.render.entity;

import lotr.client.render.RandomTextureVariants;
import lotr.common.entity.npc.ManEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class CoastSouthronWarriorRenderer extends CoastSouthronRenderer {
   private static final RandomTextureVariants SKINS = RandomTextureVariants.loadSkinsList("lotr", "textures/entity/near_harad/coast_southron_warrior");

   public CoastSouthronWarriorRenderer(EntityRendererManager mgr) {
      super(mgr);
   }

   public ResourceLocation getEntityTexture(ManEntity man) {
      return SKINS.getRandomSkin(man);
   }
}
