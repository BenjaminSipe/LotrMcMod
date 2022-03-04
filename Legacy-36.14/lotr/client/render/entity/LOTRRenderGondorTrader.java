package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityGondorMan;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderGondorTrader extends LOTRRenderGondorMan {
   private ResourceLocation traderOutfit;

   public LOTRRenderGondorTrader(String s) {
      this.traderOutfit = new ResourceLocation("lotr:mob/gondor/" + s + ".png");
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityGondorMan gondorian = (LOTREntityGondorMan)entity;
      if (pass == 1 && gondorian.func_71124_b(3) == null) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(this.traderOutfit);
         return 1;
      } else {
         return super.func_77032_a(gondorian, pass, f);
      }
   }
}
