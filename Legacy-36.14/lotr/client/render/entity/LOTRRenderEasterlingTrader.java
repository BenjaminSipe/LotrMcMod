package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityEasterling;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderEasterlingTrader extends LOTRRenderEasterling {
   private ResourceLocation traderOutfit;

   public LOTRRenderEasterlingTrader(String s) {
      this.traderOutfit = new ResourceLocation("lotr:mob/rhun/" + s + ".png");
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityEasterling easterling = (LOTREntityEasterling)entity;
      if (pass == 1 && easterling.func_71124_b(3) == null) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(this.traderOutfit);
         return 1;
      } else {
         return super.func_77032_a(easterling, pass, f);
      }
   }
}
