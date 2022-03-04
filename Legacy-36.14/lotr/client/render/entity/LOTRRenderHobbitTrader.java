package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityHobbit;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHobbitTrader extends LOTRRenderHobbit {
   private ResourceLocation traderOutfit;

   public LOTRRenderHobbitTrader(String s) {
      this.traderOutfit = new ResourceLocation("lotr:mob/hobbit/" + s + ".png");
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityHobbit hobbit = (LOTREntityHobbit)entity;
      if (pass == 1 && hobbit.func_71124_b(3) == null) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(this.traderOutfit);
         return 1;
      } else {
         return super.func_77032_a(hobbit, pass, f);
      }
   }
}
