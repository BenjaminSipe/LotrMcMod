package lotr.client.render.entity;

import lotr.client.model.LOTRModelFlamingo;
import lotr.common.entity.animal.LOTREntityFlamingo;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderFlamingo extends RenderLiving {
   private static ResourceLocation texture = new ResourceLocation("lotr:mob/flamingo/flamingo.png");
   private static ResourceLocation textureChick = new ResourceLocation("lotr:mob/flamingo/chick.png");

   public LOTRRenderFlamingo() {
      super(new LOTRModelFlamingo(), 0.5F);
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return ((LOTREntityFlamingo)entity).func_70631_g_() ? textureChick : texture;
   }

   protected float func_77044_a(EntityLivingBase entity, float f) {
      LOTREntityFlamingo flamingo = (LOTREntityFlamingo)entity;
      float f1 = flamingo.field_756_e + (flamingo.field_752_b - flamingo.field_756_e) * f;
      float f2 = flamingo.field_757_d + (flamingo.destPos - flamingo.field_757_d) * f;
      return (MathHelper.func_76126_a(f1) + 1.0F) * f2;
   }
}
