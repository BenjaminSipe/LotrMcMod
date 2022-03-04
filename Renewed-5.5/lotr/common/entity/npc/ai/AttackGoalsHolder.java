package lotr.common.entity.npc.ai;

import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.ai.goal.Goal;

public class AttackGoalsHolder {
   private final NPCEntity theEntity;
   private Goal meleeAttackGoal;
   private Goal rangedAttackGoal;

   public AttackGoalsHolder(NPCEntity npc) {
      this.theEntity = npc;
   }

   public void setMeleeAttackGoal(Goal goal) {
      if (this.meleeAttackGoal != null) {
         throw new IllegalStateException("meleeAttackGoal is already set!");
      } else {
         this.meleeAttackGoal = goal;
      }
   }

   public void setRangedAttackGoal(Goal goal) {
      if (this.rangedAttackGoal != null) {
         throw new IllegalStateException("rangedAttackGoal is already set!");
      } else {
         this.rangedAttackGoal = goal;
      }
   }

   public Goal getMeleeAttackGoal() {
      return this.meleeAttackGoal;
   }

   public Goal getRangedAttackGoal() {
      return this.rangedAttackGoal;
   }

   public Goal getNonNullMeleeAttackGoal() {
      if (this.meleeAttackGoal == null) {
         throw new IllegalStateException("Tried to fetch the melee attack goal for an NPC (" + this.theEntity.func_200200_C_().getString() + ") without such a goal defined - this is a development error!");
      } else {
         return this.meleeAttackGoal;
      }
   }

   public Goal getNonNullRangedAttackGoal() {
      if (this.rangedAttackGoal == null) {
         throw new IllegalStateException("Tried to fetch the ranged attack goal for an NPC (" + this.theEntity.func_200200_C_().getString() + ") without such a goal defined - this is a development error!");
      } else {
         return this.rangedAttackGoal;
      }
   }

   public Goal getInitialAttackGoal() {
      if (this.meleeAttackGoal != null) {
         return this.meleeAttackGoal;
      } else if (this.rangedAttackGoal != null) {
         return this.rangedAttackGoal;
      } else {
         throw new IllegalStateException("Tried to fetch the initial attack goal for an NPC (" + this.theEntity.func_200200_C_().getString() + ") without any goals defined - this is a development error!");
      }
   }
}
