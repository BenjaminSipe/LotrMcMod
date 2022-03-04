package lotr.common.entity.npc.ai.goal;

import java.util.EnumSet;
import lotr.common.entity.npc.NPCEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.Goal.Flag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class TalkToCurrentGoal extends Goal {
   private final NPCEntity entity;
   private final World world;
   private LivingEntity talkingTo;
   private static final float MAX_DISTANCE_FROM_INITIAL = 4.0F;
   private static final float CLOSE_TALKING_DISTANCE = 3.0F;
   private final double closeApproachSpeed = 1.0D;
   private int repathTimer;

   public TalkToCurrentGoal(NPCEntity entity) {
      this.entity = entity;
      this.world = entity.field_70170_p;
      this.func_220684_a(EnumSet.of(Flag.LOOK, Flag.MOVE));
   }

   public boolean func_75250_a() {
      this.talkingTo = this.entity.getTalkingToEntity();
      if (this.talkingTo != null && FriendlyNPCConversationGoal.isAvailableForTalking(this.talkingTo)) {
         if (this.talkingTo instanceof NPCEntity && ((NPCEntity)this.talkingTo).getTalkingToEntity() != this.entity) {
            return false;
         } else {
            double dSq = this.entity.func_70068_e(this.talkingTo);
            double maxDist = (double)(this.entity.getTalkingToInitialDistance() + 4.0F);
            return dSq <= maxDist * maxDist;
         }
      } else {
         return false;
      }
   }

   public boolean func_75253_b() {
      return this.func_75250_a();
   }

   public void func_75249_e() {
      this.entity.getTalkAnimations().startTalking();
      this.repathTimer = 0;
      this.entity.func_70661_as().func_75499_g();
   }

   public void func_75251_c() {
      this.talkingTo = null;
      this.entity.clearTalkingToEntity();
      this.entity.getTalkAnimations().stopTalking();
      this.repathTimer = 0;
      this.entity.func_70661_as().func_75499_g();
   }

   public void func_75246_d() {
      this.world.func_217381_Z().func_76320_a("TalkToCurrentGoal#tick");
      this.talkingTo = this.entity.getTalkingToEntity();
      if (this.talkingTo != null) {
         this.entity.func_70671_ap().func_220679_a(this.talkingTo.func_226277_ct_(), this.talkingTo.func_226280_cw_(), this.talkingTo.func_226281_cx_());
         if (!(this.talkingTo instanceof PlayerEntity) && this.entity.func_70068_e(this.talkingTo) > 9.0D) {
            --this.repathTimer;
            if (this.repathTimer <= 0) {
               this.repathTimer = 10;
               this.entity.func_70661_as().func_75497_a(this.talkingTo, 1.0D);
            }
         } else {
            this.entity.func_70661_as().func_75499_g();
            this.repathTimer = 0;
         }
      }

      this.world.func_217381_Z().func_76319_b();
   }
}
