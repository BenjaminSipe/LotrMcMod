package lotr.common.entity.npc;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import lotr.common.data.LOTRLevelData;
import lotr.common.fac.AlignmentPredicate;
import lotr.common.fac.AlignmentPredicates;
import lotr.common.fac.EntityFactionHelper;
import lotr.common.fac.Faction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class NPCPredicates {
   public static Predicate selectByFaction(Faction fac) {
      return (entity) -> {
         return entity.func_70089_S() && EntityFactionHelper.getFaction(entity) == fac;
      };
   }

   public static Predicate selectForLocalAreaOfInfluence(Faction fac) {
      return selectByFaction(fac).and((entity) -> {
         return entity instanceof NPCEntity ? ((NPCEntity)entity).generatesLocalAreaOfInfluence() : true;
      });
   }

   public static Predicate selectAngerableByKill(Faction killedFaction, LivingEntity killerResponsible) {
      return (entity) -> {
         return entity.func_70089_S() && EntityFactionHelper.getFaction(entity).isGoodRelation(killedFaction);
      };
   }

   public static Predicate selectFriends(NPCEntity theEntity) {
      return selectPlayersOrOthers(theEntity, AlignmentPredicates.POSITIVE, Faction::isGoodRelation).and((e) -> {
         return e != theEntity.func_70638_az();
      });
   }

   public static Predicate selectFoes(NPCEntity theEntity) {
      return selectPlayersOrOthers(theEntity, AlignmentPredicates.NEGATIVE, Faction::isBadRelation).or((e) -> {
         return e == theEntity.func_70638_az();
      });
   }

   private static Predicate selectPlayersOrOthers(NPCEntity theEntity, AlignmentPredicate playerTest, BiPredicate npcFactionTest) {
      Faction entityFaction = EntityFactionHelper.getFaction(theEntity);
      return (otherEntity) -> {
         if (otherEntity != theEntity && otherEntity.func_70089_S()) {
            if (otherEntity instanceof PlayerEntity) {
               PlayerEntity player = (PlayerEntity)otherEntity;
               return LOTRLevelData.getSidedData(player).getAlignmentData().hasAlignment(entityFaction, playerTest);
            } else {
               Faction otherFaction = EntityFactionHelper.getFaction(otherEntity);
               return npcFactionTest.test(otherFaction, entityFaction);
            }
         } else {
            return false;
         }
      };
   }
}
