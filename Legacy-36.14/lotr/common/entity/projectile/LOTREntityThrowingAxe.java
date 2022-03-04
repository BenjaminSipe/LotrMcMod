package lotr.common.entity.projectile;

import lotr.common.item.LOTRItemThrowingAxe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityThrowingAxe extends LOTREntityProjectileBase {
   private int axeRotation;

   public LOTREntityThrowingAxe(World world) {
      super(world);
   }

   public LOTREntityThrowingAxe(World world, ItemStack item, double d, double d1, double d2) {
      super(world, item, d, d1, d2);
   }

   public LOTREntityThrowingAxe(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
      super(world, entityliving, item, charge);
   }

   public LOTREntityThrowingAxe(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
      super(world, entityliving, target, item, charge, inaccuracy);
   }

   private boolean isThrowingAxe() {
      Item item = this.getProjectileItem().func_77973_b();
      return item instanceof LOTRItemThrowingAxe;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (!this.inGround) {
         ++this.axeRotation;
         if (this.axeRotation > 9) {
            this.axeRotation = 0;
         }

         this.field_70125_A = (float)this.axeRotation / 9.0F * 360.0F;
      }

      if (!this.isThrowingAxe()) {
         this.func_70106_y();
      }

   }

   public float getBaseImpactDamage(Entity entity, ItemStack itemstack) {
      if (!this.isThrowingAxe()) {
         return 0.0F;
      } else {
         float speed = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
         float damage = ((LOTRItemThrowingAxe)itemstack.func_77973_b()).getRangedDamageMultiplier(itemstack, this.shootingEntity, entity);
         return speed * damage;
      }
   }
}
