package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityThrownTermite;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class LOTRDispenseTermite extends BehaviorProjectileDispense {
   protected IProjectile func_82499_a(World world, IPosition position) {
      return new LOTREntityThrownTermite(world, position.func_82615_a(), position.func_82617_b(), position.func_82616_c());
   }
}
