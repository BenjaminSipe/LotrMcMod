package lotr.common.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityThrownTermite extends EntityThrowable {
   public LOTREntityThrownTermite(World world) {
      super(world);
   }

   public LOTREntityThrownTermite(World world, EntityLivingBase throwingEntity) {
      super(world, throwingEntity);
   }

   public LOTREntityThrownTermite(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
   }

   protected void func_70184_a(MovingObjectPosition movingobjectposition) {
      if (!this.field_70170_p.field_72995_K) {
         this.field_70170_p.func_72876_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2.0F, true);
         this.func_70106_y();
      }

   }
}
