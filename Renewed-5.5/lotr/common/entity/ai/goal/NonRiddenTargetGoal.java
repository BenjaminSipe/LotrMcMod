package lotr.common.entity.ai.goal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

public class NonRiddenTargetGoal extends NearestAttackableTargetGoal {
   public NonRiddenTargetGoal(MobEntity entity, Class targetClass, int targetChance, boolean checkSight, boolean nearbyOnly, @Nullable Predicate targetPredicate) {
      super(entity, targetClass, targetChance, checkSight, nearbyOnly, targetPredicate);
   }

   public boolean func_75250_a() {
      return !this.field_75299_d.func_184207_aI() && super.func_75250_a();
   }
}
