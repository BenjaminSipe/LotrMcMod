package lotr.common.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityTrollSnowball extends EntitySnowball {
   public LOTREntityTrollSnowball(World world) {
      super(world);
   }

   public LOTREntityTrollSnowball(World world, EntityLivingBase entity) {
      super(world, entity);
   }

   public LOTREntityTrollSnowball(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
   }

   protected void func_70184_a(MovingObjectPosition target) {
      if (target.field_72308_g != null) {
         float damage = 3.0F;
         target.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), damage);
      }

      for(int i = 0; i < 8; ++i) {
         this.field_70170_p.func_72869_a("snowballpoof", this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.0D, 0.0D);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70106_y();
      }

   }
}
