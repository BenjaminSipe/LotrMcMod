package lotr.common.entity.projectile;

import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityPebble extends EntityThrowable {
   private boolean isSling = false;

   public LOTREntityPebble(World world) {
      super(world);
   }

   public LOTREntityPebble(World world, EntityLivingBase entityliving) {
      super(world, entityliving);
   }

   public LOTREntityPebble(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
   }

   public LOTREntityPebble setSling() {
      this.isSling = true;
      return this;
   }

   public boolean isSling() {
      return this.isSling;
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74757_a("Sling", this.isSling);
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.isSling = nbt.func_74767_n("Sling");
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      float speed = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      if (speed > 0.1F && this.field_70181_x < 0.0D && this.func_70090_H()) {
         float factor = MathHelper.func_151240_a(this.field_70146_Z, 0.4F, 0.8F);
         this.field_70159_w *= (double)factor;
         this.field_70179_y *= (double)factor;
         this.field_70181_x += (double)factor;
      }

   }

   protected void func_70184_a(MovingObjectPosition m) {
      if (m.field_72308_g != null) {
         float damage = this.isSling ? 2.0F : 1.0F;
         m.field_72308_g.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), damage);
      }

      if (!this.field_70170_p.field_72995_K) {
         this.func_70099_a(new ItemStack(LOTRMod.pebble), 0.0F);
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
