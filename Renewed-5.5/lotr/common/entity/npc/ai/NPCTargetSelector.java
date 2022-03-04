package lotr.common.entity.npc.ai;

import java.util.function.Predicate;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.fac.EntityFactionHelper;
import lotr.common.fac.Faction;
import lotr.common.fac.FactionPointers;
import net.minecraft.entity.LivingEntity;

public class NPCTargetSelector implements Predicate {
   private NPCEntity owner;

   public NPCTargetSelector(NPCEntity entity) {
      this.owner = entity;
   }

   public boolean test(LivingEntity target) {
      Faction ownerFaction = this.owner.getFaction();
      if (!FactionPointers.HOSTILE.matches(ownerFaction) || !target.getClass().isAssignableFrom(this.owner.getClass()) && !this.owner.getClass().isAssignableFrom(target.getClass())) {
         if (target.func_70089_S()) {
            if (target instanceof NPCEntity && !((NPCEntity)target).canBeFreelyTargetedBy(this.owner)) {
               return false;
            }

            if (!ownerFaction.approvesCivilianKills() && target instanceof NPCEntity && ((NPCEntity)target).isCivilianNPC()) {
               return false;
            }

            Faction targetFaction = EntityFactionHelper.getFaction(target);
            if (ownerFaction.isBadRelation(targetFaction)) {
               return true;
            }

            if (ownerFaction.isNeutral(targetFaction)) {
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
