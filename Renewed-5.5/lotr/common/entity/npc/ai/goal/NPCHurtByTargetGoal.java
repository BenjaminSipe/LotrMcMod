package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.util.math.BlockPos;

public class NPCHurtByTargetGoal extends HurtByTargetGoal {
   private final NPCEntity theNPC;

   public NPCHurtByTargetGoal(NPCEntity entity) {
      super(entity, new Class[0]);
      this.theNPC = entity;
   }

   protected boolean func_220777_a(LivingEntity potentialTarget, EntityPredicate targetPredicate) {
      if (potentialTarget != this.theNPC.func_184187_bx() && !this.theNPC.func_184196_w(potentialTarget)) {
         BlockPos homePos = this.theNPC.func_213384_dI();
         int homeRange = (int)this.theNPC.func_213391_dJ();
         this.theNPC.clearHomePos();
         boolean superSuitable = super.func_220777_a(potentialTarget, targetPredicate);
         this.theNPC.func_213390_a(homePos, homeRange);
         return superSuitable;
      } else {
         return false;
      }
   }
}
