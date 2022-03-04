package lotr.common.fac;

import com.google.common.base.Predicates;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lotr.common.LOTRMod;
import lotr.common.data.LOTRLevelData;
import lotr.common.data.LOTRPlayerData;
import lotr.common.data.PlayerMessageType;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.data.NPCEntitySettingsManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityFactionHelper {
   public static Faction getFaction(Entity entity) {
      return entity instanceof NPCEntity ? ((NPCEntity)entity).getFaction() : NPCEntitySettingsManager.getEntityTypeFaction(entity);
   }

   public static boolean isCivilian(Entity entity) {
      return entity instanceof NPCEntity && ((NPCEntity)entity).isCivilianNPC();
   }

   public static boolean canEntityCauseDamageToTarget(LivingEntity attacker, Entity target, boolean warnFriendlyFireForPlayer) {
      return attacker instanceof PlayerEntity ? canPlayerCauseDamageToTarget((PlayerEntity)attacker, target, warnFriendlyFireForPlayer) : canNonPlayerEntityCauseDamageToTarget(attacker, target, false);
   }

   public static boolean canPlayerCauseDamageToTarget(PlayerEntity attacker, Entity target, boolean warnFriendlyFire) {
      LOTRPlayerData playerData = LOTRLevelData.getSidedData(attacker);
      boolean friendlyFire = false;
      boolean friendlyFireEnabled = playerData.getAlignmentData().isFriendlyFireEnabled();
      if (target instanceof PlayerEntity && target != attacker) {
         PlayerEntity var6 = (PlayerEntity)target;
      }

      Entity alignedTarget = isAlignedToSomeFaction(target) ? target : (Entity)target.func_184188_bt().stream().filter((rider) -> {
         return isAlignedToSomeFaction(rider);
      }).findFirst().orElse((Object)null);
      if (alignedTarget != null) {
         Faction targetFaction = getFaction(alignedTarget);
         if (alignedTarget instanceof MobEntity && !LOTRMod.PROXY.getSidedAttackTarget((MobEntity)alignedTarget).filter(Predicates.equalTo(attacker)).isPresent() && playerData.getAlignmentData().hasAlignment(targetFaction, AlignmentPredicates.POSITIVE)) {
            friendlyFire = true;
         }
      }

      if (!friendlyFireEnabled && friendlyFire) {
         if (warnFriendlyFire && !attacker.field_70170_p.field_72995_K) {
            playerData.getMessageData().sendMessageIfNotReceived(PlayerMessageType.FRIENDLY_FIRE);
         }

         return false;
      } else {
         return true;
      }
   }

   private static boolean isAlignedToSomeFaction(Entity entity) {
      return !FactionPointers.UNALIGNED.matches(getFaction(entity));
   }

   public static boolean canNonPlayerEntityCauseDamageToTarget(LivingEntity attacker, Entity target, boolean isPlayerDirectedAttack) {
      Faction attackerFaction = getFaction(attacker);
      if (attacker instanceof NPCEntity) {
         NPCEntity var4 = (NPCEntity)attacker;
      }

      Optional attackerTarget = getEntityAttackTarget(attacker);
      Predicate isNotAttackerTarget = (e) -> {
         return !attackerTarget.filter(Predicates.equalTo(e)).isPresent();
      };
      Predicate shouldNotHitNpc = isNotAttackerTarget.and((e) -> {
         return attackerFaction.isGoodRelation(getFaction(e));
      });
      Predicate shouldNotHitPlayer = isNotAttackerTarget.and((e) -> {
         return e instanceof PlayerEntity && LOTRLevelData.getSidedData((PlayerEntity)e).getAlignmentData().hasAlignment(attackerFaction, AlignmentPredicates.POSITIVE);
      });
      if (isNotAttackerTarget.test(target)) {
         if (shouldNotHitNpc.test(target)) {
            return false;
         }

         if (isListNonemptyAndAllMatch(target.func_184188_bt(), shouldNotHitNpc)) {
            return false;
         }

         if (!isPlayerDirectedAttack) {
            if (shouldNotHitPlayer.test(target)) {
               return false;
            }

            if (isListNonemptyAndAllMatch(target.func_184188_bt(), shouldNotHitPlayer)) {
               return false;
            }
         }
      }

      return true;
   }

   private static Optional getEntityAttackTarget(LivingEntity entity) {
      return entity instanceof MobEntity ? LOTRMod.PROXY.getSidedAttackTarget((MobEntity)entity) : Optional.empty();
   }

   private static boolean isListNonemptyAndAllMatch(List list, Predicate predicate) {
      return !list.isEmpty() && list.stream().allMatch(predicate);
   }
}
