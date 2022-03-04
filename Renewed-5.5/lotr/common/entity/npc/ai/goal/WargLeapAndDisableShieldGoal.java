package lotr.common.entity.npc.ai.goal;

import lotr.common.entity.npc.WargEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;

public class WargLeapAndDisableShieldGoal extends LeapAtTargetGoal {
   private final WargEntity theWarg;

   public WargLeapAndDisableShieldGoal(WargEntity warg, float leapMotionY) {
      super(warg, leapMotionY);
      this.theWarg = warg;
   }

   public boolean func_75250_a() {
      return super.func_75250_a() && this.theWarg.func_70681_au().nextInt(5) == 0;
   }

   public void func_75249_e() {
      super.func_75249_e();
      this.theWarg.startLeaping();
   }

   public void func_75246_d() {
      super.func_75246_d();
      LivingEntity target = this.theWarg.func_70638_az();
      if (target != null) {
         this.theWarg.func_70671_ap().func_75651_a(target, 360.0F, 360.0F);
      }

   }
}
