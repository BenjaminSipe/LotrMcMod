package lotr.client.render.entity;

import lotr.client.model.LOTRModelElk;
import lotr.common.entity.animal.LOTREntityElk;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderElk extends RenderLiving {
   private static LOTRRandomSkins elkSkins;
   private static ResourceLocation saddleTexture = new ResourceLocation("lotr:mob/elk/saddle.png");

   public LOTRRenderElk() {
      super(new LOTRModelElk(), 0.5F);
      this.func_77042_a(new LOTRModelElk(0.5F));
      elkSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/elk/elk");
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityElk elk = (LOTREntityElk)entity;
      ResourceLocation elkSkin = elkSkins.getRandomSkin(elk);
      return LOTRRenderHorse.getLayeredMountTexture(elk, elkSkin);
   }

   protected int func_77032_a(EntityLivingBase entity, int pass, float f) {
      if (pass == 0 && ((LOTREntityElk)entity).isMountSaddled()) {
         this.func_110776_a(saddleTexture);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }
}
