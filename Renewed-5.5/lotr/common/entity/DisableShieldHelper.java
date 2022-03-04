package lotr.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class DisableShieldHelper {
   public static void disableShieldIfEntityShielding(Entity entity, boolean flag) {
      if (entity instanceof LivingEntity && ((LivingEntity)entity).func_184585_cz()) {
         if (entity instanceof PlayerEntity) {
            ((PlayerEntity)entity).func_190777_m(flag);
         } else if (entity instanceof CanHaveShieldDisabled) {
            ((CanHaveShieldDisabled)entity).disableShield(flag);
         }
      }

   }
}
