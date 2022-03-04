package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityDaleMan;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderDaleTrader extends LOTRRenderDaleMan {
   private ResourceLocation traderOutfit;

   public LOTRRenderDaleTrader(String s) {
      this.traderOutfit = new ResourceLocation("lotr:mob/dale/" + s + ".png");
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityDaleMan man = (LOTREntityDaleMan)entity;
      if (pass == 1 && man.func_71124_b(3) == null) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(this.traderOutfit);
         return 1;
      } else {
         return super.func_77032_a(man, pass, f);
      }
   }
}
