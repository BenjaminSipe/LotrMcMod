package lotr.common.entity.ai.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.util.math.vector.Vector3d;

public class RandomWalkingEvenWhenRiddenGoal extends WaterAvoidingRandomWalkingGoal {
   private final boolean stopWhenIdle = true;

   public RandomWalkingEvenWhenRiddenGoal(CreatureEntity creature, double speed) {
      super(creature, speed);
   }

   public boolean func_75250_a() {
      if (!this.field_179482_g) {
         if (this.field_75457_a.func_70654_ax() >= 100) {
            return false;
         }

         if (this.field_75457_a.func_70681_au().nextInt(this.field_179481_f) != 0) {
            return false;
         }
      }

      Vector3d target = this.func_190864_f();
      if (target == null) {
         return false;
      } else {
         this.field_75455_b = target.field_72450_a;
         this.field_75456_c = target.field_72448_b;
         this.field_75453_d = target.field_72449_c;
         this.field_179482_g = false;
         return true;
      }
   }

   public boolean func_75253_b() {
      return !this.field_75457_a.func_70661_as().func_75500_f();
   }
}
