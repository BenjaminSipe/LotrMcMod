package lotr.client.render.entity;

import lotr.client.render.GenderedRandomTextureVariants;
import lotr.common.entity.npc.GaladhrimElfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class GaladhrimElfRenderer extends AbstractElfRenderer {
   private static final GenderedRandomTextureVariants SKINS = new GenderedRandomTextureVariants("lotr", "textures/entity/elf/galadhrim");

   public GaladhrimElfRenderer(EntityRendererManager mgr) {
      super(mgr);
   }

   public ResourceLocation getEntityTexture(GaladhrimElfEntity elf) {
      return SKINS.getRandomSkin(elf);
   }
}
