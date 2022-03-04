package lotr.common.entity.ai.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.PanicGoal;

public class PanicIfBurningGoal extends PanicGoal {
   public PanicIfBurningGoal(CreatureEntity entity, double speed) {
      super(entity, speed);
   }

   public boolean func_75250_a() {
      return this.field_75267_a.func_70027_ad() && super.func_75250_a();
   }
}
