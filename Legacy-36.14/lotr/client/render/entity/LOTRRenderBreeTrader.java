package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityBreeMan;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderBreeTrader extends LOTRRenderBreeMan {
   private ResourceLocation traderOutfit;

   public LOTRRenderBreeTrader(String s) {
      this.traderOutfit = new ResourceLocation("lotr:mob/bree/" + s + ".png");
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityBreeMan man = (LOTREntityBreeMan)entity;
      if (pass == 1 && man.func_71124_b(3) == null) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(this.traderOutfit);
         return 1;
      } else {
         return super.func_77032_a(man, pass, f);
      }
   }
}
