package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHaradrimTrader extends LOTRRenderNearHaradrim {
   private ResourceLocation traderOutfit;

   public LOTRRenderHaradrimTrader(String s) {
      this.traderOutfit = new ResourceLocation("lotr:mob/nearHarad/" + s + ".png");
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityNearHaradrimBase haradrim = (LOTREntityNearHaradrimBase)entity;
      if (pass == 1 && haradrim.func_71124_b(3) == null) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(this.traderOutfit);
         return 1;
      } else {
         return super.func_77032_a(haradrim, pass, f);
      }
   }
}
