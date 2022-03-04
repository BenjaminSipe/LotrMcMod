package lotr.common.entity.projectile;

import lotr.common.item.LOTRItemSpear;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntitySpear extends LOTREntityProjectileBase {
   public LOTREntitySpear(World world) {
      super(world);
   }

   public LOTREntitySpear(World world, ItemStack item, double d, double d1, double d2) {
      super(world, item, d, d1, d2);
   }

   public LOTREntitySpear(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
      super(world, entityliving, item, charge);
   }

   public LOTREntitySpear(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
      super(world, entityliving, target, item, charge, inaccuracy);
   }

   public float getBaseImpactDamage(Entity entity, ItemStack itemstack) {
      float speed = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
      float damage = ((LOTRItemSpear)itemstack.func_77973_b()).getRangedDamageMultiplier(itemstack, this.shootingEntity, entity);
      return speed * damage;
   }
}
