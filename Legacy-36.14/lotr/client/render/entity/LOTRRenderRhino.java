package lotr.client.render.entity;

import lotr.client.model.LOTRModelRhino;
import lotr.common.entity.animal.LOTREntityRhino;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderRhino extends RenderLiving {
   private static ResourceLocation rhinoTexture = new ResourceLocation("lotr:mob/rhino/rhino.png");
   private static ResourceLocation saddleTexture = new ResourceLocation("lotr:mob/rhino/saddle.png");

   public LOTRRenderRhino() {
      super(new LOTRModelRhino(), 0.5F);
      this.func_77042_a(new LOTRModelRhino(0.5F));
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityRhino rhino = (LOTREntityRhino)entity;
      return LOTRRenderHorse.getLayeredMountTexture(rhino, rhinoTexture);
   }

   protected int func_77032_a(EntityLivingBase entity, int pass, float f) {
      if (pass == 0 && ((LOTREntityRhino)entity).isMountSaddled()) {
         this.func_110776_a(saddleTexture);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }
}
