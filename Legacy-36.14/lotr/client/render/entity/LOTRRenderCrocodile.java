package lotr.client.render.entity;

import lotr.client.model.LOTRModelCrocodile;
import lotr.common.entity.animal.LOTREntityCrocodile;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderCrocodile extends RenderLiving {
   private static ResourceLocation texture = new ResourceLocation("lotr:mob/crocodile.png");

   public LOTRRenderCrocodile() {
      super(new LOTRModelCrocodile(), 0.75F);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      return texture;
   }

   public float func_77044_a(EntityLivingBase entity, float f) {
      float snapTime = (float)((LOTREntityCrocodile)entity).getSnapTime();
      if (snapTime > 0.0F) {
         snapTime -= f;
      }

      return snapTime / 20.0F;
   }
}
