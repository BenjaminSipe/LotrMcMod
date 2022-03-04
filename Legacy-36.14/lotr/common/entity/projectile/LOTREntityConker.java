package lotr.common.entity.projectile;

import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityConker extends EntityThrowable {
   public LOTREntityConker(World world) {
      super(world);
   }

   public LOTREntityConker(World world, EntityLivingBase entity) {
      super(world, entity);
   }

   public LOTREntityConker(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
   }

   protected void func_70184_a(MovingObjectPosition m) {
      if (m.field_72308_g != null) {
         m.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), 1.0F);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70099_a(new ItemStack(LOTRMod.chestnut), 0.0F);
         this.func_70106_y();
      }

   }

   protected float func_70182_d() {
      return 1.0F;
   }

   protected float func_70185_h() {
      return 0.04F;
   }
}
