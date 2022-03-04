package lotr.common.entity.projectile;

import lotr.common.item.LOTRItemCrossbowBolt;
import lotr.common.item.LOTRItemDagger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityCrossbowBolt extends LOTREntityProjectileBase {
   public double boltDamageFactor = 2.0D;
   public static final float BOLT_RELATIVE_TO_ARROW = 2.0F;

   public LOTREntityCrossbowBolt(World world) {
      super(world);
   }

   public LOTREntityCrossbowBolt(World world, ItemStack item, double d, double d1, double d2) {
      super(world, item, d, d1, d2);
   }

   public LOTREntityCrossbowBolt(World world, EntityLivingBase entityliving, ItemStack item, float charge) {
      super(world, entityliving, item, charge);
   }

   public LOTREntityCrossbowBolt(World world, EntityLivingBase entityliving, EntityLivingBase target, ItemStack item, float charge, float inaccuracy) {
      super(world, entityliving, target, item, charge, inaccuracy);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74780_a("boltDamageFactor", this.boltDamageFactor);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      if (nbt.func_74764_b("boltDamageFactor")) {
         this.boltDamageFactor = nbt.func_74769_h("boltDamageFactor");
      }

   }

   public float getBaseImpactDamage(Entity entity, ItemStack itemstack) {
      float speed = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
      return speed * 2.0F * (float)this.boltDamageFactor;
   }

   protected void onCollideWithTarget(Entity entity) {
      if (!this.field_70170_p.field_72995_K && entity instanceof EntityLivingBase) {
         ItemStack itemstack = this.getProjectileItem();
         if (itemstack != null) {
            Item item = itemstack.func_77973_b();
            if (item instanceof LOTRItemCrossbowBolt && ((LOTRItemCrossbowBolt)item).isPoisoned) {
               LOTRItemDagger.applyStandardPoison((EntityLivingBase)entity);
            }
         }
      }

      super.onCollideWithTarget(entity);
   }

   public int maxTicksInGround() {
      return 1200;
   }
}
