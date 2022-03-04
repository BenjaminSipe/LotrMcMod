package lotr.client.render.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderZebra extends LOTRRenderHorse {
   private static ResourceLocation zebraTexture = new ResourceLocation("lotr:mob/zebra.png");

   public ResourceLocation func_110775_a(Entity entity) {
      return zebraTexture;
   }
}
