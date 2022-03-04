package lotr.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class LOTREntityMarshWraithBall extends EntityThrowable {
   public int animationTick;
   public Entity attackTarget;

   public LOTREntityMarshWraithBall(World world) {
      super(world);
      this.func_70105_a(0.75F, 0.75F);
   }

   public LOTREntityMarshWraithBall(World world, EntityLivingBase entityliving) {
      super(world, entityliving);
      this.func_70105_a(0.75F, 0.75F);
   }

   public LOTREntityMarshWraithBall(World world, double d, double d1, double d2) {
      super(world, d, d1, d2);
      this.func_70105_a(0.75F, 0.75F);
   }

   public LOTREntityMarshWraithBall(World world, EntityLivingBase entityliving, EntityLivingBase target) {
      super(world, entityliving);
      this.func_70105_a(0.75F, 0.75F);
      this.attackTarget = target;
      this.field_70165_t = entityliving.field_70165_t;
      this.field_70163_u = entityliving.field_70121_D.field_72338_b + (double)entityliving.func_70047_e() - 0.1D;
      this.field_70161_v = entityliving.field_70161_v;
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      double d = target.field_70165_t - this.field_70165_t;
      double d1 = target.field_70121_D.field_72338_b + (double)(target.field_70131_O / 2.0F) - this.field_70163_u;
      double d2 = target.field_70161_v - this.field_70161_v;
      double d3 = (double)MathHelper.func_76133_a(d * d + d2 * d2);
      if (d3 >= 1.0E-7D) {
         float f2 = (float)(Math.atan2(d2, d) * 180.0D / 3.141592653589793D) - 90.0F;
         float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D));
         double var10000 = d / d3;
         var10000 = d2 / d3;
         this.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, f2, f3);
         this.func_70186_c(d, d1, d2, this.func_70182_d(), 1.0F);
      }

   }

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(16, Short.valueOf((short)0));
   }

   public int getBallAge() {
      return this.field_70180_af.func_75693_b(16);
   }

   public void setBallAge(int age) {
      this.field_70180_af.func_75692_b(16, (short)age);
   }

   public void func_70014_b(NBTTagCompound nbt) {
      super.func_70014_b(nbt);
      nbt.func_74768_a("BallAge", this.getBallAge());
   }

   public void func_70037_a(NBTTagCompound nbt) {
      super.func_70037_a(nbt);
      this.setBallAge(nbt.func_74762_e("BallAge"));
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if (this.field_70173_aa % 2 == 0) {
         ++this.animationTick;
         if (this.animationTick >= 16) {
            this.animationTick = 0;
         }
      }

      if (!this.field_70170_p.field_72995_K) {
         this.setBallAge(this.getBallAge() + 1);
         if (this.getBallAge() >= 200) {
            this.func_70106_y();
         }
      }

   }

   protected void func_70184_a(MovingObjectPosition m) {
      if (m.field_72313_a == MovingObjectType.BLOCK) {
         if (!this.field_70170_p.field_72995_K) {
            this.func_70106_y();
         }
      } else if (m.field_72313_a == MovingObjectType.ENTITY) {
         Entity entity = m.field_72308_g;
         if (this.attackTarget != null && entity == this.attackTarget) {
            if (entity.func_70097_a(DamageSource.func_76356_a(this, this.func_85052_h()), 5.0F) && entity instanceof EntityLivingBase) {
               int duration = 5;
               ((EntityLivingBase)entity).func_70690_d(new PotionEffect(Potion.field_76421_d.field_76415_H, duration * 20, 0));
            }

            if (!this.field_70170_p.field_72995_K) {
               this.func_70106_y();
            }
         }
      }

   }

   protected float func_70182_d() {
      return 0.5F;
   }

   protected float func_70185_h() {
      return 0.0F;
   }
}
