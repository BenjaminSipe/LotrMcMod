package lotr.client.render.entity;

import lotr.client.model.LOTRModelGiraffe;
import lotr.common.entity.animal.LOTREntityGiraffe;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGiraffe extends RenderLiving {
   public static ResourceLocation texture = new ResourceLocation("lotr:mob/giraffe/giraffe.png");
   private static ResourceLocation saddleTexture = new ResourceLocation("lotr:mob/giraffe/saddle.png");

   public LOTRRenderGiraffe() {
      super(new LOTRModelGiraffe(0.0F), 0.5F);
      this.func_77042_a(new LOTRModelGiraffe(0.5F));
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      return texture;
   }

   protected int func_77032_a(EntityLivingBase entity, int pass, float f) {
      if (pass == 0 && ((LOTREntityGiraffe)entity).isMountSaddled()) {
         this.func_110776_a(saddleTexture);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }
}
