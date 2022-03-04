package lotr.client.render.entity;

import lotr.client.model.LOTRModelSkeleton;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderSkeleton extends RenderBiped {
   private static ResourceLocation skin = new ResourceLocation("textures/entity/skeleton/skeleton.png");

   public LOTRRenderSkeleton() {
      super(new LOTRModelSkeleton(), 0.5F);
   }

   protected void func_82421_b() {
      this.field_82423_g = new LOTRModelSkeleton(1.0F);
      this.field_82425_h = new LOTRModelSkeleton(0.5F);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return skin;
   }
}
