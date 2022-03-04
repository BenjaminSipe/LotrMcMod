package lotr.common.entity.npc.ai.goal;

import com.google.common.base.Predicates;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import lotr.common.data.LOTRLevelData;
import lotr.common.entity.npc.NPCEntity;
import lotr.common.entity.npc.ai.NPCTargetSorter;
import lotr.common.fac.AlignmentPredicates;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class NPCNearestAttackableTargetGoal extends NearestAttackableTargetGoal {
   private final NPCEntity theNPC;
   private final NPCTargetSorter targetSorter;

   public NPCNearestAttackableTargetGoal(NPCEntity entity, Class targetClass, boolean checkSight) {
      this(entity, targetClass, checkSight, Predicates.alwaysTrue());
   }

   public NPCNearestAttackableTargetGoal(NPCEntity entity, Class targetClass, boolean checkSight, Predicate selector) {
      super(entity, targetClass, checkSight);
      this.theNPC = entity;
      this.field_220779_d.func_221012_a((e) -> {
         return this.canNPCTarget(e) && selector.test(e);
      });
      this.targetSorter = new NPCTargetSorter(this.theNPC);
   }

   public boolean func_75250_a() {
      return this.theNPC.func_70631_g_() ? false : super.func_75250_a();
   }

   protected AxisAlignedBB func_188511_a(double targetDistance) {
      double rangeY = Math.min(targetDistance, 8.0D);
      return this.theNPC.func_174813_aQ().func_72314_b(targetDistance, rangeY, targetDistance);
   }

   protected void func_220778_g() {
      List potentialTargets = this.theNPC.field_70170_p.func_175647_a(this.field_75307_b, this.func_188511_a(this.func_111175_f()), (e) -> {
         return this.field_220779_d.func_221015_a(this.theNPC, e);
      });
      Collections.sort(potentialTargets, this.targetSorter);
      if (!potentialTargets.isEmpty()) {
         this.field_75309_a = (LivingEntity)potentialTargets.get(0);
      } else {
         this.field_75309_a = null;
      }

   }

   protected boolean canNPCTarget(LivingEntity entity) {
      if (entity != this.theNPC.func_184187_bx() && !this.theNPC.func_184196_w(entity)) {
         return entity instanceof PlayerEntity ? this.isPlayerSuitableTarget((PlayerEntity)entity) : true;
      } else {
         return false;
      }
   }

   protected boolean isPlayerSuitableTarget(PlayerEntity player) {
      return this.isPlayerSuitableAlignmentTarget(player);
   }

   protected boolean isPlayerSuitableAlignmentTarget(PlayerEntity player) {
      return LOTRLevelData.getSidedData(player).getAlignmentData().hasAlignment(this.theNPC.getFaction(), AlignmentPredicates.NEGATIVE);
   }
}
