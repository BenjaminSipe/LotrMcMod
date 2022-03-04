package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityTauredain;
import net.minecraft.entity.EntityLiving;

public class LOTRRenderTauredainShaman extends LOTRRenderTauredain {
   private static LOTRRandomSkins outfits;

   public LOTRRenderTauredainShaman() {
      outfits = LOTRRandomSkins.loadSkinsList("lotr:mob/tauredain/shaman_outfit");
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityTauredain tauredain = (LOTREntityTauredain)entity;
      if (pass == 1 && tauredain.func_71124_b(3) == null) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(outfits.getRandomSkin(tauredain));
         return 1;
      } else {
         return super.func_77032_a(tauredain, pass, f);
      }
   }
}
