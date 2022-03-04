package lotr.common.dispenser;

import lotr.common.entity.projectile.LOTREntityPlate;
import net.minecraft.block.Block;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class LOTRDispensePlate extends BehaviorProjectileDispense {
   private Block plateBlock;

   public LOTRDispensePlate(Block block) {
      this.plateBlock = block;
   }

   protected IProjectile func_82499_a(World world, IPosition position) {
      return new LOTREntityPlate(world, this.plateBlock, position.func_82615_a(), position.func_82617_b(), position.func_82616_c());
   }
}
