package lotr.client.render.entity;

import lotr.common.entity.npc.LOTREntityGondorRenegade;
import net.minecraft.entity.EntityLiving;

public class LOTRRenderGondorRenegade extends LOTRRenderGondorMan {
   private static LOTRRandomSkins hoodSkins;

   public LOTRRenderGondorRenegade() {
      hoodSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/nearHarad/renegade_hood");
   }

   public int func_77032_a(EntityLiving entity, int pass, float f) {
      LOTREntityGondorRenegade renegade = (LOTREntityGondorRenegade)entity;
      if (pass == 0 && renegade.func_71124_b(4) == null) {
         this.func_77042_a(this.outfitModel);
         this.func_110776_a(hoodSkins.getRandomSkin(renegade));
         return 1;
      } else {
         return super.func_77032_a(renegade, pass, f);
      }
   }
}
