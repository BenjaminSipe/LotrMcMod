package lotr.common.dispenser;

import lotr.common.entity.projectile.ThrownPlateEntity;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DispensePlate extends ProjectileDispenseBehavior {
   protected ProjectileEntity func_82499_a(World world, IPosition pos, ItemStack stack) {
      return new ThrownPlateEntity(world, stack, pos.func_82615_a(), pos.func_82617_b(), pos.func_82616_c());
   }
}
