package lotr.common.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class LOTREntityAIRangedAttack extends EntityAIBase {
   private EntityLiving theOwner;
   private IRangedAttackMob theOwnerRanged;
   private EntityLivingBase attackTarget;
   private int rangedAttackTime;
   private double moveSpeed;
   private double moveSpeedFlee;
   private int repathDelay;
   private int attackTimeMin;
   private int attackTimeMax;
   private float attackRange;
   private float attackRangeSq;

   public LOTREntityAIRangedAttack(IRangedAttackMob entity, double speed, int time, float range) {
      this(entity, speed, time, time, range);
   }

   public LOTREntityAIRangedAttack(IRangedAttackMob entity, double speed, int min, int max, float range) {
      this.moveSpeedFlee = 1.5D;
      this.rangedAttackTime = -1;
      this.theOwnerRanged = entity;
      this.theOwner = (EntityLiving)entity;
      this.moveSpeed = speed;
      this.attackTimeMin = min;
      this.attackTimeMax = max;
      this.attackRange = range;
      this.attackRangeSq = range * range;
      this.func_75248_a(3);
   }

   public boolean func_75250_a() {
      EntityLivingBase target = this.theOwner.func_70638_az();
      if (target == null) {
         return false;
      } else {
         this.attackTarget = target;
         return true;
      }
   }

   public boolean func_75253_b() {
      if (!this.theOwner.func_70089_S()) {
         return false;
      } else {
         this.attackTarget = this.theOwner.func_70638_az();
         return this.attackTarget != null && this.attackTarget.func_70089_S();
      }
   }

   public void func_75251_c() {
      this.attackTarget = null;
      this.repathDelay = 0;
      this.rangedAttackTime = -1;
   }

   public void func_75246_d() {
      double distanceSq = this.theOwner.func_70092_e(this.attackTarget.field_70165_t, this.attackTarget.field_70121_D.field_72338_b, this.attackTarget.field_70161_v);
      boolean canSee = this.theOwner.func_70635_at().func_75522_a(this.attackTarget);
      if (canSee) {
         ++this.repathDelay;
      } else {
         this.repathDelay = 0;
      }

      if (distanceSq <= (double)this.attackRangeSq) {
         if (this.theOwner.func_70068_e(this.attackTarget) < 25.0D) {
            Vec3 vec = findPositionAwayFrom(this.theOwner, this.attackTarget, 8, 16);
            if (vec != null) {
               this.theOwner.func_70661_as().func_75492_a(vec.field_72450_a, vec.field_72448_b, vec.field_72449_c, this.moveSpeedFlee);
            }
         } else if (this.repathDelay >= 20) {
            this.theOwner.func_70661_as().func_75499_g();
            this.repathDelay = 0;
         }
      } else {
         this.theOwner.func_70661_as().func_75497_a(this.attackTarget, this.moveSpeed);
      }

      this.theOwner.func_70671_ap().func_75651_a(this.attackTarget, 30.0F, 30.0F);
      --this.rangedAttackTime;
      float distanceRatio;
      if (this.rangedAttackTime == 0) {
         if (distanceSq > (double)this.attackRangeSq || !canSee) {
            return;
         }

         distanceRatio = MathHelper.func_76133_a(distanceSq) / this.attackRange;
         float power = MathHelper.func_76131_a(distanceRatio, 0.1F, 1.0F);
         this.theOwnerRanged.func_82196_d(this.attackTarget, power);
         this.rangedAttackTime = MathHelper.func_76141_d(distanceRatio * (float)(this.attackTimeMax - this.attackTimeMin) + (float)this.attackTimeMin);
      } else if (this.rangedAttackTime < 0) {
         distanceRatio = MathHelper.func_76133_a(distanceSq) / this.attackRange;
         this.rangedAttackTime = MathHelper.func_76141_d(distanceRatio * (float)(this.attackTimeMax - this.attackTimeMin) + (float)this.attackTimeMin);
      }

   }

   public static Vec3 findPositionAwayFrom(EntityLivingBase entity, EntityLivingBase target, int min, int max) {
      Random random = entity.func_70681_au();

      for(int l = 0; l < 24; ++l) {
         int i = MathHelper.func_76128_c(entity.field_70165_t) - max + random.nextInt(max * 2 + 1);
         int j = MathHelper.func_76128_c(entity.field_70121_D.field_72338_b) - 4 + random.nextInt(9);
         int k = MathHelper.func_76128_c(entity.field_70161_v) - max + random.nextInt(max * 2 + 1);
         double d = target.func_70092_e((double)i, (double)j, (double)k);
         if (d > (double)(min * min) && d < (double)(max * max)) {
            return Vec3.func_72443_a((double)i, (double)j, (double)k);
         }
      }

      return null;
   }
}
