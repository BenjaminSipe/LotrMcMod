package lotr.client.render.entity;

import lotr.client.model.LOTRModelSwan;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderSwan extends RenderLiving {
   public static ResourceLocation swanSkin = new ResourceLocation("lotr:mob/swan.png");

   public LOTRRenderSwan() {
      super(new LOTRModelSwan(), 0.5F);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return swanSkin;
   }
}
