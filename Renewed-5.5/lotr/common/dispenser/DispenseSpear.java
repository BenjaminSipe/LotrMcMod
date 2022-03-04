package lotr.common.dispenser;

import lotr.common.entity.projectile.SpearEntity;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity.PickupStatus;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DispenseSpear extends ProjectileDispenseBehavior {
   protected ProjectileEntity func_82499_a(World world, IPosition pos, ItemStack stack) {
      SpearEntity spear = new SpearEntity(world, pos.func_82615_a(), pos.func_82617_b(), pos.func_82616_c(), stack);
      spear.field_70251_a = PickupStatus.ALLOWED;
      return spear;
   }
}
