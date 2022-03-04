package lotr.client.render.entity;

import lotr.client.model.LOTRModelDeer;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDeer extends RenderLiving {
   private static LOTRRandomSkins deerSkins;

   public LOTRRenderDeer() {
      super(new LOTRModelDeer(), 0.5F);
      deerSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/deer");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityDeer deer = (LOTREntityDeer)entity;
      ResourceLocation deerSkin = deerSkins.getRandomSkin(deer);
      return deerSkin;
   }
}
