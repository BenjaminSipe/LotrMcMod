package lotr.common.entity;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;

public class LOTRNPCSelectForInfluence extends LOTRNPCSelectByFaction {
   public LOTRNPCSelectForInfluence(LOTRFaction f) {
      super(f);
   }

   public boolean func_82704_a(Entity entity) {
      boolean flag = super.func_82704_a(entity);
      if (flag && entity instanceof LOTREntityNPC) {
         LOTREntityNPC npc = (LOTREntityNPC)entity;
         if (!npc.generatesControlZone()) {
            return false;
         }
      }

      return flag;
   }

   protected boolean matchFaction(Entity entity) {
      return LOTRMod.getNPCFaction(entity, true) == this.faction;
   }
}
