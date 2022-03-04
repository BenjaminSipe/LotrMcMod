package lotr.client.render.entity;

import lotr.client.model.LOTRModelHalfTroll;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHalfTrollScavenger extends LOTRRenderHalfTroll {
   private static ResourceLocation outfitTexture = new ResourceLocation("lotr:mob/halfTroll/scavenger.png");
   private ModelBiped outfitModel = new LOTRModelHalfTroll(0.5F);

   public LOTRRenderHalfTrollScavenger() {
      this.func_77042_a(this.outfitModel);
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      if (pass == 0) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(outfitTexture);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }
}
