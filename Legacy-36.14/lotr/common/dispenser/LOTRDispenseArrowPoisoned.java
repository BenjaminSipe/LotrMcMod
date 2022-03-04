package lotr.common.dispenser;

import lotr.common.entity.item.LOTREntityArrowPoisoned;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class LOTRDispenseArrowPoisoned extends BehaviorProjectileDispense {
   protected IProjectile func_82499_a(World world, IPosition iposition) {
      EntityArrow arrow = new LOTREntityArrowPoisoned(world, iposition.func_82615_a(), iposition.func_82617_b(), iposition.func_82616_c());
      arrow.field_70251_a = 1;
      return arrow;
   }
}
