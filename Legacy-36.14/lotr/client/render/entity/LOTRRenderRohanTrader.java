package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityRohanMan;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderRohanTrader extends LOTRRenderRohirrim {
   private ResourceLocation traderOutfit;

   public LOTRRenderRohanTrader(String s) {
      this.traderOutfit = new ResourceLocation("lotr:mob/rohan/" + s + ".png");
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityRohanMan rohirrim = (LOTREntityRohanMan)entity;
      if (pass == 1 && rohirrim.func_71124_b(3) == null) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(this.traderOutfit);
         return 1;
      } else {
         return super.func_77032_a(rohirrim, pass, f);
      }
   }
}
